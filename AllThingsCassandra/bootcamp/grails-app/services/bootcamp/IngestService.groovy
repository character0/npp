package bootcamp

import groovy.constants.DateFormats
import groovy.constants.IngestSourceType
import groovy.constants.SettingNames
import groovy.state.IngestSourceState

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

import grails.transaction.Transactional

@Transactional
class IngestService {

    static transactional = false

    def grailsApplication
    def settingService
    def cassandraService
    def publisherService

//******************************************* Start Generic Ingest ****************************************************//

    private static final String QUARANTINE_PREFIX = "ERROR_PROCESSING_"
    private static final int TRANSFER_COMPLETE_SECS = 15

    private static Map<String, Long> fileCache = new TreeMap<String, Long>()

    /**
     * Helper method that safely updates the state of the IngestSource objects
     *
     * @param ingestSource the Ingest Source object to update
     * @param oldState the old state of the ingest source
     * @param newState the new state to update the ingest source to.
     */
    private boolean safeUpdateIngestSourceState(IngestSource ingestSource, IngestSourceState oldState, IngestSourceState newState) {
        boolean retval = false;

        retval = safeUpdateIngestSource(ingestSource) {
            if (it.state == oldState) {
                log.info "safeUpdateIngestSourceState -> Setting ingest source ${it.id} state to ${newState}."
                it.state = newState;
                it.currentProcessor = grailsApplication.config.server.id
                retval = true;
            } else {
                log.info "safeUpdateIngestSourceState -> Another thread updated the state of ingest source ${it.id} to ${it.state}; returning false."
                retval = false;
            }
            return retval;
        }

        return retval;
    }

    /**
     * Helper method that provides pessimistic locking then performs the passed in closure operation for a safe operation.
     *
     * @param ingestSource the ingest source object to perform the safe operation on
     * @param safeOperation the closure to be performed on the ingest source object
     */
    private boolean safeUpdateIngestSource(IngestSource ingestSource, Closure safeOperation) {
        IngestSource.withTransaction {
            ingestSource.lock();
            ingestSource.refresh();
            if (safeOperation(ingestSource)) {
                ingestSource.save(flush: true);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Helper method that returns the state of an IngestSource. Pull fresh from the DB to ensure accuracy,
     *
     * @param ingestSource
     * @return
     */
    private safeRetrieveIngestSourceState(IngestSource ingestSource) {

        IngestSource.withTransaction {
            ingestSource.lock();
            ingestSource.refresh();
        }

        return ingestSource.state
    }

    /**
     * Creates a new IngestSource object for ingest. In the development environment the drop folder itself is created as well as
     * the object.
     *
     * @param user the user the drop folder is created for
     * @param name the name of the new ingestSource
     * @param location the directory location of the ingestSource
     * @param description a description attribute for the ingestSource
     * @param mode optional mode for the ingestSource
     * @param prefix the prefix string to ignore on files
     * @param suffix the suffix string to ignore on files
     * @param polling the amount of time to wait before processing content in the ingestSource.
     */
    public IngestSource createIngestSource(String name, String location, String description,
                                           String prefix, String suffix, int polling) {

        Receiver source = new Receiver(name: name, location: location, description: description,
                state: IngestSourceState.ON, pollFrequencyInMinutes: polling,
                currentProcessor: grailsApplication.config.server.id,
                prefixesToIgnore: prefix, suffixesToIgnore: suffix, ingestSourceType: IngestSourceType.RECEIVER)

        IngestSource.withTransaction {
            log.info "Saving ingestSource ${name}"
            if (!source.save(flush: true)) {
                for (e in source.errors) {
                    log.warn "Unable to save ingestSource ${name}: ${e}";
                }
                source = null;
                return;
            }
        }

        def createdLocation = createFTPDropLocation(location)
        if (!createdLocation) {
            def errorMsg = "Was not able to create FTP drop location $location for ingestSource ${source.id}"
            log.error errorMsg
            deactivateIngestSource(source, errorMsg)
        }

        return source
    }

    /**
     * Marks a given ingest source for delete by the garbage collector.
     */
    public void deleteIngestSource(IngestSource ingestSource) {
        if (!ingestSource) return

        log.info "Setting ingestSource ${ingestSource.id} state to DELETED"
        IngestSource.withTransaction {
            ingestSource.lock()
            ingestSource.refresh()
            ingestSource.state = IngestSourceState.DELETED
            ingestSource.save(flush: true)
        }
    }

    /**
     * Method that is called by the IngestSourceJob. Checks through ingest source objects and begins processing contents.
     */
    def checkIngestSources() {

        def ingestSourceCriteria = IngestSource.createCriteria();
        def ingestSources = ingestSourceCriteria.list() {
            eq('state', IngestSourceState.ON)
            eq('ingestSourceType', IngestSourceType.RECEIVER)
            or {
                le('nextPoll', new Date())
                isNull('nextPoll')
            }
        }

        boolean canProcessIngestSource = false
        log.info "Found ${ingestSources.size()} ingestSource(s) to process (state = ON & time is past next poll date)."
        for (IngestSource ingestSource in ingestSources) {

            try {
                canProcessIngestSource = safeUpdateIngestSourceState(ingestSource, IngestSourceState.ON, IngestSourceState.PROCESSING);
                if (canProcessIngestSource) {
                    safeUpdateIngestSource(ingestSource) { it.lastPolled = new Date() }
                    processIngestSource(ingestSource)
                }
            } finally {
                if (canProcessIngestSource) {
                    canProcessIngestSource = safeUpdateIngestSourceState(ingestSource, IngestSourceState.PROCESSING, IngestSourceState.ON);
                    if (!canProcessIngestSource) {
                        def errorMsg = "Was not able to change ingestSource ${ingestSource.id} from PROCESSING to ON, shutting down"
                        log.error errorMsg
                        safeUpdateIngestSource(ingestSource) {
                            ingestSource.state = IngestSourceState.ERROR
                        }
                    } else {
                        log.debug "Current next poll date $ingestSource.nextPoll"
                        def nextPollDate = DateUtils.addMinutes(new Date(), ingestSource.pollFrequencyInMinutes)
                        log.debug "Updating ingestSource $ingestSource next poll date to $nextPollDate"
                        safeUpdateIngestSource(ingestSource) {
                            ingestSource.nextPoll = nextPollDate
                        }
                    }
                }
            }//end finally

        }//end for loop
    }

    /**
     *
     * @param ingestSource the ingestSource object to process
     */
    def processIngestSource(IngestSource ingestSource) {
        log.info "Processing ingestSource '${ingestSource.name}'."
        // Don't create drop directories here (base or ingestSource).
        // They should be configured manually or by other parts of the system.
        String baseDir = settingService.getStringSystemSetting(SettingNames.RECEIVER_DROP_FOLDER_BASE_DIR, "/")
        if (!baseDir) {
            log.warn("IngestSource drop folder base directory setting (RECEIVER_DROP_FOLDER_BASE_DIR) not set or is an empty string. " +
                    "If you want to enable drop folders, configure the setting.")
            return
        }
        File baseDirFile = new File(baseDir)
        if (!baseDirFile.exists() || !baseDirFile.isDirectory()) {
            log.warn("IngestSource drop folder base directory '${baseDir}' does not exist or is not a directory.")
            return
        }

//********************************************************************************************************************//
        File dropFolder = new File(baseDir + '/' + ingestSource.location)
        if (!dropFolder.exists() || !dropFolder.isDirectory()) {
            log.warn("IngestSource drop folder (location field) '${dropFolder}' does not exist or is not a directory. ")
            return
        }
        // Create list of Files for each input
        File[] stockDataFiles = dropFolder.listFiles({ dir, file -> file ==~ /.*?\.[c|C][s|S][v|V]/ } as FilenameFilter)
        //?.toList()
        File[] newsDataFiles = dropFolder.listFiles({ dir, file -> file ==~ /.*?\.[x|X][m|M][l|L]/ } as FilenameFilter)
        //?.toList()
        File[] reviewDataFiles = dropFolder.listFiles({ dir, file -> file ==~ /.*?\.[t|T][x|X][t|T]/ } as FilenameFilter)
        //?.toList()
        def dataFiles = stockDataFiles + newsDataFiles + reviewDataFiles
        log.info "IngestSource '${ingestSource.name}' drop folder '${dropFolder}' data files ${dataFiles}\n" +
                "Cache size of files '${fileCache.size()}'"
//********************************************************************************************************************//

        for (file in dataFiles) {
            if (safeRetrieveIngestSourceState(ingestSource) == IngestSourceState.PROCESSING) {
                try {
                    if (!file.isDirectory()) {
                        handleDrop(ingestSource, file)
                    }
                } catch (Exception e) {
                    // Log the exception and send Ingest Failed event, regardless
                    String errorMsg = "Error processing file '${file}': ${e.message}"
                    log.warn(errorMsg, e)
                    log.info "Caught Exception ${e.getMessage()} ingesting an stock data from ${file.name}, deactivating ingestSource ${ingestSource.id}"
                    deactivateIngestSource(ingestSource, errorMsg);
                    break;
                }
            } else {
                log.info "IngestSource ${ingestSource.id} is no longer in the PROCESSING state; aborting job."
                break;
            }
        } // loop over files
    }

    /**
     *
     * @param ingestSource
     * @param f
     * @return
     */
    private File getFileFromCache(IngestSource ingestSource, File f) {
        log.info "getFileFromCache -> ingestSource: ${ingestSource}; file: ${f}"
        if (f.name.startsWith(QUARANTINE_PREFIX)) {
            log.warn "getFileFromCache -> Skipping file '${f.name}' - error processing it previously; requires manual cleanup/reset"
            return;
        } else if (f.name.startsWith(".")) {
            log.info "getFileFromCache -> Skipping file '${f.name}' - invalid prefix '.'"
            return;
        } else {
            Long cacheSize = fileCache.get(f.absolutePath)
            if (cacheSize == null) {
                log.info "getFileFromCache -> Adding to cache '${f.absolutePath}' - '${f.length()}'"
                fileCache.put(f.absolutePath, new Long(f.length()))
                return;
            } else {
                Date time = new Date()
                log.info "getFileFromCache -> Cache size '${cacheSize.longValue()}' - current size '${f.length()}'"
                log.info "getFileFromCache -> Time elapsed since putting file into cache: ${time.getTime() - f.lastModified()}"
                if (f.length() == cacheSize.longValue() && isFileTransferComplete(f) && f.length() > 0) {
                    log.info "getFileFromCache -> Removing from cache and processing '${f.absolutePath}'"
                    fileCache.remove(f.absolutePath)
                    return f
                } else {
                    log.info "getFileFromCache -> Skipping file '${f.name}' - transfer not complete or ingestSource still polling"
                    log.info "getFileFromCache -> Updating cache '${f.absolutePath}' - '${f.length()}'"
                    fileCache.put(f.absolutePath, new Long(f.length()))
                    return;
                }
            }
        }
    }

    /**
     *
     * @param directory
     * @param ingestSource
     */
    private void deleteEmptyDirs(File rootDirectory, IngestSource ingestSource) throws Exception {
        if (ingestSource.removeEmptySubDirs) {
            log.info "Looking for empty directories for clean-up in folder ${rootDirectory.name}"
            rootDirectory.eachDir {
                log.info "Processing directory ${it.name} for delete"
                deleteEmptyDirsHelper(it, ingestSource, rootDirectory)
            }
        } else {
            log.debug "IngestSource ${ingestSource.name} not configured to clean-up emtpy sub-directories"
        }
    }

    /**
     *
     * @param directory
     * @param ingestSource
     */
    private void deleteEmptyDirsHelper(File currentDirectory, IngestSource ingestSource, File rootDirectory) {

        currentDirectory.eachDir { dir ->
            deleteEmptyDirsHelper(dir, ingestSource, rootDirectory)
        }

        def dirFiles = currentDirectory?.listFiles()
        if (dirFiles?.size() < 1) {
            log.info "Directory ${currentDirectory.name} is an empty sub directory."
            Date time = new Date()
            if ((time.getTime() - currentDirectory.lastModified()) > convertMinutesToMilliSeconds(ingestSource.pollFrequencyInMinutes)) {
                def parentDir = currentDirectory.getParentFile()
                if (currentDirectory.delete()) {
                    log.info "Directory ${currentDirectory.name} successfully deleted"
                } else {
                    log.warn "Directory ${currentDirectory.name} was not deleted"
                    return;
                }

            } else {
                log.info "Directory ${currentDirectory.name} will not be deleted, ingestSource still polling"
                return;
            }
        }
    }

    /**
     * Determines if 30 seconds have passed since the last time the
     * given file has been modified.
     *
     * @param file the file to test if the transfer is complete
     * @param return whether the file transfer is complete
     */
    private boolean isFileTransferComplete(File f) {
        log.info "isFileTransferComplete: Verifying file ${f} has finished transferring"

        // Check for how long it's been since file has been modified
        Date compareDate = DateUtils.addSeconds(new Date(), -1 * TRANSFER_COMPLETE_SECS)
        Date lastModDate = new Date(f?.lastModified()) ?: new Date()
        if (!lastModDate.before(compareDate)) {
            log.info "isFileTransferComplete: File has been modified in last ${TRANSFER_COMPLETE_SECS} secs. Returning false."
            return false
        }

        log.info "isFileTransferComplete: Transfer is complete. Returning true."
        return true
    }

    /**
     * Unzips a archive file. Unzips each asset folder to a new folder in a temporary directory for processing.
     *
     * @param f the archived file to unzip
     */
    private File unzipToTempDir(File f) throws Exception {

        String folderName
        String fileName
        File dir
        boolean deleteSuccess

        ZipFile zf = new ZipFile(f);

        dir = safeCreateTempDir()
        log.info "Unzipping file ${f.name} to ${dir.getAbsolutePath()}"
        for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {

            ZipEntry ze = entries.nextElement()
            String zipEntryName = ze.getName()

            createFileFromZip(dir.getAbsolutePath() + "/" + zipEntryName, ze, zf)
        }

        return dir
    }

    /**
     * Creates a temporary and unique directory for ingest processing.
     */
    public File safeCreateTempDir() {
        File tmpdir = null;
        String tmpBaseDir = settingService.getStringSystemSetting(SettingNames.FILE_ARCHIVE_EXTRACT_DIR,
                "/Users/nickpanahi/Workspace/scratch")

        boolean done = false
        while (!done) {
            tmpdir = new File(tmpBaseDir + "/" + System.currentTimeMillis());
            if (!tmpdir.exists()) {
                log.info "Creating temporary directory $tmpdir"

                synchronized (this) {
                    FileUtils.forceMkdir(tmpdir)
                }

                done = true
            }
        }
        return tmpdir
    }

    /**
     * writes a compressed file from a zip file out to a new uncompressed directory.
     *
     * @param filePath the new output path to write the files out to
     * @param ze a entry in the zipfile
     * @param zf a zipped file object int the zip file
     */
    public void createFileFromZip(String filePath, ZipEntry ze, ZipFile zf) {

        log.info "Creating file $filePath"
        OutputStream out = new FileOutputStream(filePath);
        InputStream input = zf.getInputStream(ze);

        byte[] buf = new byte[1024];
        int len;
        while ((len = input.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        // Close streams
        out.close();
        input.close();
    }

    /**
     * Renames a file name X to ERROR_PROCESSING_X to be ignored by the ingestSource for processing. If an
     * exception is thrown renaming, then the ingestSource is shut off.
     *
     * @param file the file to quarantine (rename)
     * @param ingestSource the ingest source to shut off if the quarantine does not work
     */
    private void quarantineFile(File file, IngestSource ingestSource) {
        try {
            // If file still exists in drop directory, rename it so that it will no longer be processed.
            if (file.exists()) {
                boolean success = file.renameTo(new File(file.parent, QUARANTINE_PREFIX + file.name))
                if (!success)
                    throw new Exception("Failed to quarantine file: ${file.name}")
            }
            log.info "Quarantined file ${file.absolutePath} @ ${DateFormats.DEFAULT_SECS.format(new Date())}"
        } catch (Exception e2) {
            String errorMsg = "Problem quarantining file '${file.absolutePath}'; inactivating ingest source '${ingestSource.name}':  ${e2.message}"
            log.error(errorMsg, e2)

            deactivateIngestSource(ingestSource, errorMsg);
        }
    }

    /**
     * Helper method that takes in a filename, parses out the prefix and calls another method
     * that will verifiy whether the prefix is valid or not
     *
     * @param filename the name of the file
     * @param r the ingestSource which has the prefix and suffix extensions
     */
    public boolean isValidPrefix(String filename, IngestSource r) {
        String prefix
        int firstIndexOfDot = filename.indexOf(".")

        if (firstIndexOfDot < 0) {
            log.info "No PREFIX or suffix found for file $filename"
            return true
        } else if (firstIndexOfDot == filename.lastIndexOf(".")) {
            log.info "Only 1 prefix/suffix on file $filename, setting prefix"
            prefix = filename[0..firstIndexOfDot - 1]
            log.info "Prefix: $prefix"
        } else {
            log.info "Found both a prefix and a suffix for file $filename, setting prefix"
            prefix = filename[0..firstIndexOfDot - 1]
            log.info "Prefix: $prefix"
        }

        String ignoredPrefixes = r.prefixesToIgnore
        return isValidPrefixSuffix(prefix, ignoredPrefixes)
    }

    /**
     * Helper method that takes in a filename, parses out the suffix and calls another method
     * that will verifiy whether the suffix is valid or not.
     *
     * @param filename the name of the file
     * @param ingestSource the ingest source which has the prefix and suffix extensions
     */
    public boolean isValidSuffix(String filename, IngestSource ingestSource) {
        String suffix = IngestUtil.getFileExtension(filename)

        if (!suffix) {
            log.info "No prefix or SUFFIX found for file $filename"
            return true
        } else {
            log.info "Found a suffix: ${suffix}"
        }

        return isValidPrefixSuffix(suffix, ingestSource.suffixesToIgnore)
    }

    /**
     * Method will take an extension, prefix or suffix and a string of allowed
     * prefixes or suffixes, break out the allowed extensions and compare the passed
     * in extension to the list. If there are any matches the the ext is not valid.
     *
     * @param ext the extension to evaluate
     * @param ignoredfixes the list of extensions that should be ignored.
     */
    private boolean isValidPrefixSuffix(String ext, String ignoredfixes) {
        if (!ignoredfixes) return true

        try {
            String invalidfixes = ignoredfixes.replace('.', '').replace('*', '')
            String[] fixes = invalidfixes.split(';')

            for (int i = 0; i < fixes.length; i++) {

                log.debug "Comparing *ix ${ext.toUpperCase()} to ${fixes[i].toUpperCase()}"
                if (ext.toUpperCase() == fixes[i].toUpperCase()) {
                    log.info "Found invalid *ix: $ext"
                    return false
                }
            }
        }
        catch (Exception e) {
            log.info "Caught exception trying to validate ignoredFixes \"${ignoredfixes}\":\n${e.message}"
        }

        return true
    }

    /**
     * Helper method that converts a file size in Bytes to MegaBytes.
     *
     * @param fileSize the value to convert
     */
    public int convertToMB(long fileSize) {
        return (fileSize / (1000 * 1000))
    }

    /**
     * Helper method that given a value of minutes will return that
     * value in milliseconds.
     *
     * @param minutes the value to convert
     */
    private long convertMinutesToMilliSeconds(int minutes) {
        return ((minutes * 60) * 1000)
    }

    /**
     *
     * @param location
     */
    public boolean createFTPDropLocation(String location) {

        String baseDir = settingService.getStringSystemSetting(SettingNames.RECEIVER_DROP_FOLDER_BASE_DIR, ".")
        try {
            def dropFileLocation = new File(baseDir + '/' + location)
            if (!dropFileLocation.exists()) {
                log.info "Creating ingestSource drop location ${dropFileLocation}"
                FileUtils.forceMkdir(dropFileLocation)
            } else {
                log.debug "${dropFileLocation} already exists"
            }
        } catch (Exception e) {
            log.error "Caught ${e.getMessage()} creating ingest source drop location $location.", e
            return false
        }

        return true
    }

    private void deactivateIngestSource(IngestSource ingestSource, String errorMsg) {
        safeUpdateIngestSourceState(ingestSource, ingestSource.state, IngestSourceState.ERROR)
    }

    /**
     * Handles files for ingestSources.  Verifies they're aged enough in the cache, then ingests.
     *
     * @param ingestSource the ingestSource thats contents are being processed
     * @param f the file that is being processed in the ingestSource
     */
    private handleDrop(IngestSource ingestSource, File f) {
        log.debug "handleDrop -> ingestSource: ${ingestSource}; file: ${f}"

        def cleanFile = getFileFromCache(ingestSource, f)
        if (cleanFile) {
//********************************************************************************************************************//
            handleFile(ingestSource, f);
//********************************************************************************************************************//
        }
    }

    /**
     * Implement This for Ingest
     *
     * Handles ingesting a file.  Decides whether the content is a directory,
     * archived file, or content file and makes the appropriate call to the
     * processing method.
     *
     * Used by IngestService and ApiController
     *
     * @param ingestSource The ingestSource to use when ingesting this file.
     * @param file The file to be ingested
     */
    public void handleFile(IngestSource ingestSource, File file) {

        String filename
        String filenameExt
        try {
            filename = IngestUtil.validateAndCleanFilename(file?.name)
            filenameExt = IngestUtil.getFileExtension(filename)
            if (!isValidPrefix(file.name, ingestSource) || !isValidSuffix(file.name, ingestSource) || file.name.startsWith(".")) {
                log.info "Skipping file '${file.name}' - invalid prefix and/or suffix found"
                return
            }

//********************************************************************************************************************//
            switch (filenameExt) {
                case 'csv':
                    insertTickerData(parseTickerDataFile(file))
                    break
                case 'xml':
                    insertNewsData(parseNewsDataFile(file))
                    break
                case 'txt':
                    insertReviewsData(parseReviewsDataFile(file))
                    break
                default:
                    return
            }
//********************************************************************************************************************//

        } catch (Exception sle) {
            log.error "Got Exception ${sle.getMessage()}", sle;
        } finally {
            FileUtils.forceDelete(file) // Clean up.
        }
    }

//**************************************** End Generic Ingest Methods ************************************************//

//*************************************** Start Custom Ingest Methods ************************************************//

    /**
     *
     * @param tickerDataFile
     * @return
     */
    def parseTickerDataFile(File tickerDataFile) {

        def currentTime = System.currentTimeMillis()
        def parsedRows = []
        log.info "parseTickerDataFile -> File path: ${tickerDataFile.absolutePath}"

        String[] lines = tickerDataFile.text.split('\n')
        List<String[]> rows = lines.collect { it.split(',') }
        log.info "parseTickerDataFile -> Collected ${rows.size()} rows of stock data."

        Calendar tradeDateAndTime = new GregorianCalendar()
        def rowNumber = 0
        for (row in rows) {
            rowNumber++
            log.debug "parseTickerDataFile -> Parsed Row $rowNumber: $row"

            def tradeDataSet = [:]
            SimpleDateFormat sdf
            try {
                sdf = new SimpleDateFormat(DateFormats.TRANSACTION_DATE)
                def parsedTransactionDate = sdf.parse(row[1].trim())
                tradeDateAndTime.setTime(parsedTransactionDate)
                tradeDataSet.date = parsedTransactionDate // Set transaction date
            } catch (ParseException pe) {
                log.warn "parseTickerDataFile -> Parse exception parsing transaction date from row data ${row[1].trim()}"
                continue;
            }

            sdf = new SimpleDateFormat(DateFormats.TRANSACTION_TIME)
            def timeAndSequence = row[2].trim()
            // parse out value that reps time of transaction and transaction sequence
            def time
            def indexOfDecimal = timeAndSequence.indexOf('.')
            def lengthOfEntry = timeAndSequence.size()
            // Pad and/or parse transaction time and sequence data
            if (indexOfDecimal < 0) {
                log.warn "parseTickerDataFile -> Row contains bad data for time executed and sequence ${timeAndSequence}"
                continue
            } else if (indexOfDecimal < 4) {
                // Pad time value to represent 4 digit time
                time = timeAndSequence.substring(0, indexOfDecimal)
                for (int i = time.size(); i < 4; i++) {
                    time = "0" + time
                }
                log.info "parseTickerDataFile -> Padded time from '${timeAndSequence.substring(0, indexOfDecimal)}' to '$time'"
            } else if (indexOfDecimal > 4) {
                log.warn "parseTickerDataFile -> Row contains bad data for time executed and sequence ${timeAndSequence}"
                continue
            } else {
                time = timeAndSequence.substring(0, indexOfDecimal)
            }

            try {
                time = sdf.parse(time)
                log.debug "parseTickerDataFile -> Parsed out transaction time $time"
                tradeDateAndTime.set(Calendar.HOUR, time.getHours())
                tradeDateAndTime.set(Calendar.MINUTE, time.getMinutes())

                tradeDataSet.time = tradeDateAndTime.getTime()
                tradeDataSet.hour = tradeDateAndTime.get(Calendar.HOUR_OF_DAY)
                tradeDataSet.minute = tradeDateAndTime.get(Calendar.MINUTE)

            } catch (ParseException pe) {
                log.warn "parseTickerDataFile -> Parse exception parsing transaction time from row data ${row[1].trim()}"
                continue;
            }

            tradeDataSet.sequence = Integer.parseInt(timeAndSequence.substring(indexOfDecimal + 1, lengthOfEntry))
            log.debug "parseTickerDataFile -> Parsed out transaction sequence ${tradeDataSet.sequence}"
            tradeDataSet.price = Float.parseFloat(row[3].trim())
            log.debug "parseTickerDataFile -> Parsed out transaction price ${tradeDataSet.price}"
            tradeDataSet.quantity = Integer.parseInt(row[4].trim())
            log.debug "parseTickerDataFile -> Parsed out transaction quantity ${tradeDataSet.quantity}"
            log.info "parseTickerDataFile -> Map data: $tradeDataSet"
            parsedRows << tradeDataSet
        }

        def timeElapsed = System.currentTimeMillis() - currentTime
        log.info "parseTickerDataFile -> METRIC: Took $timeElapsed ms to parse stock data from file ${tickerDataFile.name}"
        return parsedRows
    }

    /**
     *
     * @param listOfStockDataRows
     * @return
     */
    def insertTickerData(listOfStockDataRows) {

        log.info "insertTickerData -> Inserting ${listOfStockDataRows.size()} rows of tick data."
        def currentTime = System.currentTimeMillis()

        for (row in listOfStockDataRows) {
            log.info "insertTickerData -> Processing stock data row: $row"

            def writeToCassandra = settingService.getBooleanSystemSetting(SettingNames.INSERT_INTO_CASSANDRA, false)
            if (writeToCassandra) {
                cassandraService.insertTradeByPriceRow(row.time, row.hour, row.price, row.minute, row.sequence, row.quantity)
            }

            def publishToQueue = settingService.getBooleanSystemSetting(SettingNames.PUBLISH_TO_MQ, false)
            if (publishToQueue) {
                // TODO publisherService.publishStockData(row.date, row.title, row.symbol, row.text, row.id, row.source)
            }
        }
        log.info "insertTickerData -> METRIC: Took ${System.currentTimeMillis() - currentTime} ms to insert ${listOfStockDataRows.size()} rows of tick data."
    }


    /**
     *
     * @param newsDataFile
     */
    def parseNewsDataFile(File newsDataFile) {

        def allNewsData = []
        def parsedXml
        def xmlSlurper = new XmlSlurper()
        try {
            parsedXml = xmlSlurper.parse(newsDataFile)
        } catch (ParseException pe) {
            log.warn "parseNewsDataFile -> Exception ${pe.message} parsing XML file ${newsDataFile.name}"
            return allNewsData
        }

        def newsDataPages = parsedXml.page
        for (newsDataPage in newsDataPages) {
            def newsData = [:]
            Calendar newsDate = new GregorianCalendar()

            newsData.put("title", newsDataPage.title.text())
            newsData.put("symbol", newsDataPage.symbol.text())
            newsData.put("text", newsDataPage.'text'.text())
            newsData.put("source", newsDataPage.source.text())

            SimpleDateFormat sdf
            try {
                // News ID
                newsData.put("id", Integer.parseInt(newsDataPage.id.text()))
                //newsData.put("id", newsDataPage.id.text())
                // News date
                sdf = new SimpleDateFormat(DateFormats.TRADE_NEWS_DATE)
                def parsedTransactionDate = sdf.parse(newsDataPage.date.text())
                newsDate.setTime(parsedTransactionDate)
                //newsData.put("date", newsDate.time) // Set news date
                newsData.put("date", newsDataPage.date.text())
            } catch (Exception e) {
                log.warn "parseTickerDataFile -> Exception parsing. ${e.message}"
                continue;
            }

            allNewsData << newsData
            log.info "parseNewsDataFile -> $newsData"
        }

        log.info "parseNewsDataFile -> $allNewsData"
        return allNewsData
    }

    /**
     *
     * @param listOfStockNewsRows
     * @return
     */
    def insertNewsData(listOfStockNewsRows) {

        log.info "insertNewsData -> Inserting ${listOfStockNewsRows.size()} rows of tick data."
        def currentTime = System.currentTimeMillis()
        for (row in listOfStockNewsRows) {
            log.info "insertNewsData -> Processing news data row: $row"

            def writeToCassandra = settingService.getBooleanSystemSetting(SettingNames.INSERT_INTO_CASSANDRA, false)
            if (writeToCassandra) {
                cassandraService.insertStockNewsRow(row.date, row.title, row.symbol, row.text, row.id, row.source)
            }

            def publishToQueue = settingService.getBooleanSystemSetting(SettingNames.PUBLISH_TO_MQ, false)
            if (publishToQueue) {
                // TODO publisherService.publishNewsData(row.date, row.title, row.symbol, row.text, row.id, row.source)
            }

        }
        log.info "insertNewsData -> METRIC: Took ${System.currentTimeMillis() - currentTime} ms to insert ${listOfStockNewsRows.size()} rows of news data."

    }

    public void parseReviewsDataFile(File reviewsDataFile) {

        def allReviews = []
        String line = "";
        long rowsRead = 0;

        try {
            def br = new BufferedReader(new FileReader(reviewsDataFile));
            while ((line = (br.readLine())) != null) {
                def review = [:]
                // start with a new object for a new review, read in the fields
                for (int i = 0; i < 8; i++) {
                    String[] parts = line.split(":");
                    log.debug "parts: ${parts}"
                    def value = stripGarbage(parts[1])

                    String[] firstPart = parts[0].split("/");
                    log.debug "firstPart: $firstPart"

                    log.debug "first.1: ${firstPart[0]}"
                    log.debug "first.2: ${firstPart[1]}"
                    def field = firstPart[1]

                    switch (field.toLowerCase()) {
                        case "helpfulness":
                            String [] helpfulReview = value.split("/");
                            int helpful = helpfulReview[0].toInteger();
                            int out_of = helpfulReview[1].toInteger();
                            review.put("helpful", helpful)
                            review.put("out_of", out_of)
                            break;
                        case "score":
                            int score = value.toInteger()
                            review.put(field, score);
                            break;
                        case "time":
                            // TODO try catch for time parsing.
                            def calendar = new GregorianCalendar()
                            def timeInLong = value.toLong()
                            log.info "Time: $timeInLong"
                            review.put(field, calendar.setTimeInMillis(timeInLong) );
                            break;
                        default:
                            review.put(field, value);
                    }
// review: [productId:B002ZX3LOW, userId:A1RCNMMZNX9GNP, profileName:jk, helpfulness:0/0, score:50,
// time:1347235200, summary:Love Green mountain Hazelnut K cups, text:The K Cups are easy to use and extremely flavorful
// They arrive from Amazon in a very timely way and are tasty  at all times  It is an easy way to make my favorite flavor
// coffee in a jiffy  JK]
                    review.put('unique_id', UUID.randomUUID().toString())
                    log.info "review: ${review}"
                    // advance
                    line = br.readLine();
                }

                publisherService.publishReviews(review.'unique_id', review.'summary', review.'time', review.'userId', review.'score',
                        review.'helpful', review.'out_of', review.'profileName', review.'productId', review.'text')
                //allReviews << review
                log.info "Entities read: ${++rowsRead}"
                // 568,454 occurrences of product/productId
            }
        } catch (Exception e) {
            log.error "Caught Exception parsing line $line", e
            System.exit(1)
        }
    }

    private String stripGarbage(String s) {
        String validChars = " :/abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String result = "";

        s = s.trim()
        for (int i = 0; i < s.length(); i++) {
            if (validChars.indexOf(s[i]) >= 0)
                result += s.charAt(i);
        }
        return result;
    }


    public void insertReviewsData(def listOfReviews) {

        try {

            log.info "insertReviewsData -> Inserting ${listOfReviews.size()} rows of tick data."
            def currentTime = System.currentTimeMillis()

            for (row in listOfReviews) {
                log.info "insertReviewsData -> Processing news data row: $row"

                def writeToCassandra = settingService.getBooleanSystemSetting(SettingNames.INSERT_INTO_CASSANDRA, false)
                if (writeToCassandra) {
                    cassandraService.insertReviewsRow(UUID.randomUUID().toString(), row.summary, row.time, row.user_id, row.score,
                            row.helpful, row.out_of, row.profile_name, row.product_id, row.text)
                }

                def publishToQueue = settingService.getBooleanSystemSetting(SettingNames.PUBLISH_TO_MQ, false)
                if (publishToQueue) {
                    publisherService.publishReviews(UUID.randomUUID().toString(), row.summary, row.time, row.user_id, row.score,
                            row.helpful, row.out_of, row.profile_name, row.product_id, row.text)
                }

            }
            log.info "insertReviewsData -> METRIC: Took ${System.currentTimeMillis() - currentTime} ms to insert ${listOfReviews.size()} rows of news data."


//            insert = "INSERT INTO bootcamp.ratings (user_id, profile_name, score, product_id)"
//            + " VALUES ('"
//            + reviews.getString("userId") + "','"
//            + stripGarbage(reviews.getString("profileName")) +"',"
//            + reviews.getString("score") + ",'"
//            + stripGarbage(reviews.getString("productId")) + "');";
//            //System.out.println(insert);
//            session.execute(insert);

//            def words = stripGarbage(reviews.getString("text")).split(" ");
//            for (int i=0; i<words.length; i++) {
//                insert = "INSERT INTO bootcamp.word_cloud (product_id, word)"
//                + " VALUES ('"
//                + "ALL','"
//                + stripGarbage(words[i]) +"');";
//                //System.out.println(insert);
//                //session.execute(insert);
//                insert = "INSERT INTO bootcamp.word_cloud (product_id, word)"
//                + " VALUES ('"
//                + stripGarbage(reviews.getString("productId")) + "','"
//                + stripGarbage(words[i]) +"');";
//                //System.out.println(insert);
//                //session.execute(insert);
//            }


            //System.out.println("Entities written = "+(++rowRead));
        } catch (Exception e) {
            log.error "Oops", e
        }
    }


}