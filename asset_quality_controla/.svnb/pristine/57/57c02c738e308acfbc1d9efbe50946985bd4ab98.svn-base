package clquality

import clsetting.Setting
import grails.util.GrailsUtil
import com.clearleap.core.asset.ContentState
import com.clearleap.core.asset.ContentType
import com.clearleap.core.util.AdiUtil
import com.clearleap.core.util.DateUtils
import com.clearleap.core.util.FileUtils
import com.clearleap.core.util.UpdateUtil
import groovy.sql.Sql
import groovy.util.slurpersupport.GPathResult
import java.sql.SQLException
import java.text.SimpleDateFormat

import settings.SettingNames
import states.AssetStates
import states.QCResultStates
import states.ManzanitaResultStates
import com.clearleap.core.distribution.SFTPDistributor
import util.DateFormatter

class AssetService {

    boolean transactional = false

    MailService mailService
    FileService fileService
    TransferService transferService
	
	def grailsApplication

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")


    /**
     * Approved assets have passed the QC process. Contents set qcDate (now) and qcResult (PASSED) and Asset becomes
     * Approved.
     */
    def approveAsset(Asset asset) {

        UpdateUtil.safeUpdateObject(asset) {

            asset.contents.each { Content content ->
                //TODO overwrite or don't?
                content.qcDate = new Date()
                content.qcResult = QCResultStates.PASSED
            }

            asset.assetState = AssetStates.APPROVED
            return true
        }
    }

    /**
     * Rejected assets will be QCed again. Contents set qcDate (now) and qcResult (FAILED) and asset becomes Rejected.
     */
    def rejectAsset(Asset asset) {

        UpdateUtil.safeUpdateObject(asset) {

            asset.contents.each { Content content ->
                //TODO overwrite or don't?
                content.qcDate = new Date()
                content.qcResult = QCResultStates.FAILED
            }

            asset.assetState = AssetStates.REJECTED
            return true
        }
    }

    /**
     * DELETE JOB
     * @return
     */
    def cleanupQCedContent() {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def distributor

        def assetsToDelete = Asset.createCriteria().list {
            and {
                'in'("assetState", [AssetStates.APPROVED, AssetStates.REJECTED])
                isNull("cleanupDate")
                eq("processor", grailsApplication?.config?.qc?.id)
            }
            order("centralId", "asc")
        }
        log.info "Found ${assetsToDelete.size()} APPROVED/REJECTED assets to clean up."

        def serverUrl = Setting.getSetting(SettingNames.SFTP_SERVER_URL)
        def serverPort = Setting.getSetting(SettingNames.SFTP_PORT)
        def serverUser = Setting.getSetting(SettingNames.SFTP_USER)
        def serverPass = Setting.getSetting(SettingNames.SFTP_PASSWORD)
        def serverDropDir = Setting.getSetting(SettingNames.SFTP_DROP_DIR_PATH)

        assetsToDelete.each { asset ->

            try {

                def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName
                log.info "Cleaning up ${asset.assetState} asset ${asset.centralId} and contents in ${assetBaseDir}"
                FileUtils.deleteDirectory(new File(assetBaseDir))
                log.info "Asset ${asset.centralId} has been cleaned off of the local disk."

                if (transferService.shouldAttemptTransfers(grailsApplication?.config?.qc?.id)) {

                    distributor = new SFTPDistributor()
                    // Set up the the distributor
                    distributor.initializeFTPConnection(serverUrl, serverPort, serverUser, serverPass)

                    def removedFromServer = false
                    log.info "Cleaning up ${asset.assetState} asset ${asset.centralId} and contents from SFTP server ${serverUrl}"
                    distributor.deleteFile(serverDropDir + File.separator + asset.stagedDirectoryName + File.separator + "source" + File.separator + "*")
                    distributor.deleteDirectory(serverDropDir + File.separator + asset.stagedDirectoryName + File.separator + "source")
                    distributor.deleteFile(serverDropDir + File.separator + asset.stagedDirectoryName + File.separator + "*")
                    removedFromServer = distributor.deleteDirectory(serverDropDir + File.separator + asset.stagedDirectoryName)
                    if (removedFromServer) {
                        log.info "Asset ${asset.centralId} has been cleaned off of SFTP server."
                        UpdateUtil.safeUpdateObject(asset) {
                            asset.cleanupDate = new Date()
                        }
                    }
                    /*
                    def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
                    def metricInMinutes = metricInSecs / 60
                    log.info "METRIC $metricInSecs seconds ($metricInMinutes minutes) to SFTP files for asset ${asset.centralId} to server."
                    */
                }

            } catch (IOException ioe) {
                log.error ioe, ioe
                mailService.sendMail("Exception cleaning up Asset ${asset.centralId}",
                        "IO Exception deleting directory ${asset.stagedDirectoryName} for asset ${asset.centralId}")
            } catch (Throwable e) {
                log.error "cleanupQCedContent: Exception '${e.message}' deleting directories via SFTP.", e
            } finally {
                if (distributor)
                    distributor.closeFTPConnection()
            }
        }

        fileService.deleteEmptyDirs(new File(baseDir))
    }

    /**
     * CREATE JOB
     * @return
     */
    def findNewAsset() {

        def hboAccountId = Setting.getSetting(SettingNames.HBO_ACCOUNT)
        Sql db

        try {
            db = createDBSQLConnection()
            //Full list of Assets that need to be processed.
            log.info "Looking for new assets to CREATE for QC"

            def clcoreAssetsToProcess = db.rows("""select id, assetstate, name, description, mediaid, startdate, rating, source_provider_id,
                short_title, title, episodename, ingest_source_id, enddate, priority, qc_level from asset where user_id in
                (select id from cl_user where account_id = ${hboAccountId}) and assetstate in ('SUBMITTED') order by priority, enddate, startdate""")
            //Find the newest Asset that needs to be processed.
            log.info "There are ${clcoreAssetsToProcess?.size()} clcore asset candidates"

            Asset assetToProcess
            for (asset in clcoreAssetsToProcess) {

                Asset existingAsset = Asset.findByCentralId(asset.id)
                if (existingAsset) {
                    log.debug "Asset ${existingAsset.centralId} already exists and is ${existingAsset.assetState}"
                    log.debug asset.dump()
                } else {
                    log.info "Asset ${asset.id} not found in DB. Creating new asset to stage."
                    if (asset.startdate && asset.enddate && asset.mediaid) {
                        try {
                            assetToProcess = new Asset(centralId: asset.id, mediaId: asset.mediaid, name: asset.name,
                                    startDate: asset.startdate, createDate: new Date(), assetState: AssetStates.CREATED,
                                    assetId: asset.source_provider_id, rating: asset.rating, title: asset.title,
                                    shortTitle: asset.short_title, episode: asset.episodename, ingestSourceId: asset.ingest_source_id,
                                    endDate: asset.enddate, description: asset.description, qcLevel: asset.qc_level)
                            log.debug "Asset: ${asset.dump()}"

                            Asset.withTransaction {
                                assetToProcess.save(flush: true)
                            }
                            //create one asset at a time initially
                            break;
                        } catch (Exception e) {
                            log.info e, e
                            mailService.sendMail("Exception ${e.getMessage()} CREATING asset", "Exception creating asset ${asset?.id} " + e)
                        }
                    } else {
                        log.warn "Asset ${asset?.id} is missing a required field for staging: startDate - ${asset?.startdate} endDate - ${asset?.enddate} mediaId - ${asset?.mediaid}"
                        mailService.sendMail("Asset ${asset?.id} is missing a required field for staging",
                                "Asset ${asset?.id} is missing a required field for staging: startDate - ${asset?.startdate} endDate - ${asset?.enddate} mediaId - ${asset?.mediaid}")
                    }
                }
            }

            return assetToProcess
        } finally {
            db?.close()
        }
    }

    /**
     * STAGEJOB
     * @return
     */
    def processAssets() {
        def asset = getNextAssetToStage()
        if (asset) {
            if (fileService.haveSpace()) {
                stageAsset(asset)
            } else {
                def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
                log.error "Not enough disk space in $baseDir to further stage assets."
                mailService.sendMail("QC1 Reached Storage Threshold", "There is not enough space on qc1-atl to further stage qc assets.")
                //Setting.setValue(SettingNames.DO_JOBS, false)
            }
        }
    }

    /**
     * Finds the next CREATED asset ready to be STAGED.
     * @return
     */
    def getNextAssetToStage() {

        log.info "Finding the next asset to STAGE"
        Asset nextAssetToProcess
        //Get list of all Assets to be staged
        def assetsToStage = Asset.createCriteria().list() {
            and {
                eq("assetState", AssetStates.CREATED)
                order("endDate", "asc")
                order("startDate", "asc")
            }
        }
        log.info "${assetsToStage?.size()} assets are waiting to be staged"
        if (assetsToStage?.size() > 0) {
            //process one asset at a time for now.
            nextAssetToProcess = assetsToStage.first()
            log.info "Next asset to stage ${nextAssetToProcess.dump()}"
        }

        return nextAssetToProcess
    }

    /**
     * Stages a created asset.
     * @param assetToProcess
     * @return
     */
    def stageAsset(Asset assetToProcess) {

        def profileMap = [:]
        Sql db

        boolean processAsset = false
        processAsset = safeUpdateState(assetToProcess, AssetStates.CREATED, AssetStates.PROCESSING) {
            assetToProcess.stagedDirectoryName = getAssetBaseDirectory(assetToProcess)
            setAssetQCType(assetToProcess)
            assetToProcess.processor = grailsApplication?.config?.qc?.id
        }

        if (processAsset) {
            try {

                db = createDBSQLConnection()

                def profile_row = db.rows("select id, name from profile")
                profile_row.each { profileMap.put(it.id, it.name) }
                log.debug "Created profile map: $profileMap"

                def allIgnoredProfiles = constructListOfProfilesToIgnore()

                long taskStartTime = System.currentTimeMillis();
                log.info "Processing asset ${assetToProcess?.centralId} for STAGING"

                log.debug "Asset: ${assetToProcess.dump()}"
                db.eachRow("""select id, original, name, contenttype, contentstate, uri, size, checksum,
                                    profile_id, audiotype, data_service_xml, runtime, qc_complete from content where
                                    asset_id = ${assetToProcess.centralId} and uri like 'hdfs%' order by id desc""") { content_row ->
                    //Update asset with ADI info
                    if (content_row.contenttype == ContentType.METADATA.toString()) {
                        log.info "Found a METADATA content for asset ${assetToProcess.centralId}"
                        if (content_row.original == true) {
                            log.info "Found original ADI.XML content, updating asset properties with ADI fields"
                            updateAssetWithADI(assetToProcess, new URI(content_row.uri))
                        } else {
                            log.debug "Found generated ADI.XML content, ignoring"
                            return
                        }
                    }

                    def profile = profileMap.get(content_row.profile_id)

                    def processContent = true
                    allIgnoredProfiles.each { ignoredProfile ->
                        if (ignoredProfile == profile) {
                            log.info "Content profile $profile matches ignored profile $ignoredProfile"
                            processContent = false
                            return
                        }
                    }

                    Content content
                    if (processContent) {
                        //check if content exists:
                        def existingContents = Content.createCriteria().list() {
                            eq('centralId', content_row.id)
                            isNull('qcDate')
                            order("id", "asc")
                        }

                        if (existingContents.size() == 0) {
                            Asset.withTransaction {
                                content = new Content(centralId: content_row.id, original: content_row.original,
                                        name: content_row.name, checksum: content_row.checksum, audioType: content_row.audiotype,
                                        dataServiceXml: content_row.data_service_xml, size: content_row.size, runtime: content_row.runtime,
                                        contentType: content_row.contenttype, asset: assetToProcess, profile: profile,
                                        contentState: ContentState.INCOMPLETE.toString(), qcComplete: content_row.qc_complete)
                                log.debug "Content: ${content.dump()}"
                                assetToProcess.addToContents(content)
                                assetToProcess.save(flush: true)
                            }
                        } else if (existingContents.size() == 1) {
                            def existingContent = existingContents.first()
                            log.info "Content already exists: ${existingContent}"
                            content = existingContent
                        } else {
                            log.warn "Found more than 1 content $existingContents to process for asset $assetToProcess.centralId"
                            content = existingContents.first()
                        }

                        copyAndCompleteContent(content_row.uri, content)

                    } else {
                        log.info "Skipping content ${content_row.id}; Profile $profile configured to be skipped."
                    }
                }//end content_row loop

                def runCrosscheck = Setting.getSetting(SettingNames.RUN_MANZANITA)
                if (runCrosscheck) { // If we're not running manzanita on contents, no need to run through this.
                    //PASS preview contents with only close captioning errors
                    updatePreviewContentsManzanitaResult(assetToProcess)
                }
                //create metadata.html file
                dumpAssetMetadataFile(assetToProcess)
                //verify contents are all complete and metadata file exists
                verifyAsset(assetToProcess)

                def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
                def metricInMinutes = metricInSecs / 60
                log.info "METRIC asset ${assetToProcess.centralId} took $metricInSecs seconds ($metricInMinutes minutes) to STAGE."

            } catch (SQLException sqle) {
                log.info sqle, sqle
                mailService.sendMail("SQLException syncing Assets", "SQLException ${sqle.getMessage()} thrown syncing assets: " + sqle)
            } catch (Exception e) {
                log.info e, e
                mailService.sendMail("Exception syncing Assets", "Exception ${e.getMessage()} thrown syncing assets: " + e)
            } finally {
                db?.close()
            }

        } else {
            log.warn "Was not able to update asset ${assetToProcess.centralId} to PROCESSING. Another thread or instance may be already processing this asset."
            return
        }
    }

    /**
     * Looks for any new contents added to the asset to pull in for QC. Only pulls in new content(s).
     * Checks to see if source files still exist as well.
     */
    def qcNewContents(Asset asset) {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def WEB_PREVIEW_PROFILES = Setting.getSetting(SettingNames.WEB_PREVIEW_PROFILES)
        def profileMap = [:]
        Sql db

        if ([AssetStates.STAGED, AssetStates.APPROVED, AssetStates.REJECTED].contains(asset.assetState) == false) {
            log.info "Asset ${asset.centralId} has not been STAGED, APPROVED or REJECTED yet to qc new contents."
            return
        }

        try {
            //Create Profiel map (map of profile names with IDs)
            db = createDBSQLConnection()
            def profile_row = db.rows("select id, name from profile")
            profile_row.each { profileMap.put(it.id, it.name) }

            log.info "Processing asset ${asset.centralId} to qc new content it may have."
            UpdateUtil.safeUpdateObject(asset) {
                asset.assetState = AssetStates.PROCESSING
            }

            db.eachRow("""select id, original, name, contenttype, contentstate,
                uri, size, checksum, profile_id, audiotype, data_service_xml, runtime from content where
                asset_id = ${asset.centralId} and uri like 'hdfs%' order by id desc""") { content_row ->

                Content content
                def profile = profileMap.get(content_row.profile_id)
                if (!WEB_PREVIEW_PROFILES.contains(profile) && content_row.contenttype != ContentType.METADATA.toString()) {

                    def existingContent = Content.findByCentralId(content_row.id)
                    if (asset.contents.contains(existingContent) == false) {
                        log.info "Found a content ${content_row.id} that is not a part of asset ${asset.centralId}"
                        Asset.withTransaction {
                            content = new Content(centralId: content_row.id, original: content_row.original, name: content_row.name,
                                    checksum: content_row.checksum, audioType: content_row.audiotype, dataServiceXml: content_row.data_service_xml,
                                    size: content_row.size, runtime: content_row.runtime, contentType: content_row.contenttype, asset: asset,
                                    profile: profile, contentState: ContentState.INCOMPLETE.toString())
                            log.debug "Content: ${content.dump()}"
                            asset.addToContents(content)
                            asset.save(flush: true)
                        }

                        copyAndCompleteContent(content_row.uri, content)

                    } else {
                        log.info "Asset ${asset.centralId} already contains content ${content_row.id}"
                        log.debug "Content: ${content_row.dump()}"

                        if (content_row.original && content_row.contenttype != ContentType.METADATA.toString()) {
                            checkSourceContentFile(existingContent, content_row.uri)
                        }
                    }
                }

            }//end content loop

            //PASS preview contents with only close captioning errors
            updatePreviewContentsManzanitaResult(asset)

            dumpAssetMetadataFile(asset)

            UpdateUtil.safeUpdateObject(asset) {
                asset.cleanupDate = null
                asset.assetState = AssetStates.STAGED
            }

        } catch (Exception e) {
            log.info "Caught an Exception qcing any new contents for asset ${asset.centralId}", e
        } finally {
            db?.close()
        }

    }

    /**
     * Repulls all of the asset's contents to be re-qced. For any existing contents where the qcDate is null, qcDate will be N/A.
     */
    def qcAllContent(Asset asset) {
        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def WEB_PREVIEW_PROFILES = Setting.getSetting(SettingNames.WEB_PREVIEW_PROFILES)
        def profileMap = [:]
        Sql db

        if ([AssetStates.STAGED, AssetStates.APPROVED, AssetStates.REJECTED].contains(asset.assetState) == false) {
            log.info "Asset ${asset.centralId} has not been STAGED, APPROVED or REJECTED yet to re-qc contents."
            return
        }

        try {
            db = createDBSQLConnection()

            def profile_row = db.rows("select id, name from profile")
            profile_row.each { profileMap.put(it.id, it.name) }

            log.info "re-QCings asset ${asset.centralId} to qc all of its contents again."
            UpdateUtil.safeUpdateObject(asset) {
                asset.assetState = AssetStates.PROCESSING
            }
            //for all existing contents, for any where qcResult is null set to N/A, set qcDate
            asset.contents.each {
                if (it.original == false) {
                    if (!it.qcResult) {
                        log.info "Content ${it.centralId} has not been qc-ed yet; Setting qcResult to ${QCResultStates.NA}"
                        UpdateUtil.safeUpdateObject(it) {
                            it.qcDate = new Date()
                            it.qcResult = QCResultStates.NA
                        }
                    }
                }
            }

            log.info "Cleaning up contents in directory $asset.stagedDirectoryName before re-qcing all content."
            def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName
            FileUtils.deleteDirectory(new File(assetBaseDir))

            db.eachRow("""select id, original, name, contenttype, contentstate,
                uri, size, checksum, profile_id, audiotype, data_service_xml, runtime from content where
                asset_id = ${asset.centralId} and uri like 'hdfs%' order by id desc""") { content_row ->

                Content content
                def profile = profileMap.get(content_row.profile_id)
                if (!WEB_PREVIEW_PROFILES.contains(profile) && content_row.contenttype != ContentType.METADATA.toString()) {

                    if (content_row.original) {
                        if (content_row.contenttype != ContentType.METADATA.toString()) {
                            def existingSrcContent = asset.contents.find { it.centralId == content_row.id }
                            log.debug "original src: ${existingSrcContent.dump()}"
                            checkSourceContentFile(existingSrcContent, content_row.uri)
                        }

                    } else {
                        Asset.withTransaction {
                            content = new Content(centralId: content_row.id, original: content_row.original, name: content_row.name,
                                    checksum: content_row.checksum, audioType: content_row.audiotype, dataServiceXml: content_row.data_service_xml,
                                    size: content_row.size, runtime: content_row.runtime, contentType: content_row.contenttype, asset: asset,
                                    profile: profile, contentState: ContentState.INCOMPLETE.toString())
                            log.debug "Content: ${content.dump()}"
                            asset.addToContents(content)
                            asset.save(flush: true)
                        }

                        copyAndCompleteContent(content_row.uri, content)
                    }
                }
            }//end content loop

            updatePreviewContentsManzanitaResult(asset)

            dumpAssetMetadataFile(asset)

            UpdateUtil.safeUpdateObject(asset) {
                asset.cleanupDate = null
                asset.assetState = AssetStates.STAGED
            }

        } catch (Exception e) {
            log.info "Caught an Exception re-qcing contents for asset ${asset.centralId}", e
        } finally {
            db?.close()
        }
    }

    ////////////////////////////////////////HELPER METHODS GALORE///////////////////////////////////////////////////////

    /**
     * Creates and returns a Sql connection to the clcore DB from DB settings throws an exception otherwise
     * @return
     */
    def createDBSQLConnection() throws Exception {

        def db_url = Setting.getSetting(SettingNames.CLCORE_DB_URL)
        def db_driver = Setting.getSetting(SettingNames.CLCORE_DB_DRIVER)
        def db_username = Setting.getSetting(SettingNames.CLCORE_DB_USERNAME)
        def db_password = Setting.getSetting(SettingNames.CLCORE_DB_PASSWORD)
        Sql db
        try {
            return Sql.newInstance(db_url, db_username, db_password, db_driver)
        } catch (Exception e) {
            log.info "Caught exception ${e.getMessage()} initializing DB connection to clcore"
            throw e
        }
    }

    /**
     *
     * @param clcoreURI
     * @param content
     * @return
     */
    def copyAndCompleteContent(String clcoreURI, Content content) {

        //copy over original source/images/previews and generated primaries/previews
        if ((content.original == true && content.contentType != ContentType.METADATA.toString()) ||
                (content.original == false && [ContentType.ARTWORK.toString(), ContentType.METADATA.toString()].contains(content.contentType) == false)) {

            File contentFile = copyContentToQCDir(clcoreURI, content)
            if (contentFile?.exists() && contentFile.size() == content.size) {
                def fileExt = FileUtils.getExtension(clcoreURI)
                if (fileExt == ".mpg") {
                    def successfulCheck = runManzanitaOnContent(content)
                    if (successfulCheck) {
                        def successfulCC = runCCOnContent(content)
                        if (!successfulCC) {
                            log.warn "CCExtracter did not return successfully for content ${content.centralId}"
                            return false
                        }
                    } else { // manzanita was not able to run
                        log.warn "Manzanita did not return successfully for content ${content.centralId}"
                        return false
                    }

                    def hbo3DIngestSourceId = Setting.getSetting(SettingNames.HBO_3D_INGEST_SOURCE)
                    if (content.original == false && content.contentType == ContentType.PRIMARY.toString() &&
                            content.asset.ingestSourceId != hbo3DIngestSourceId &&
                            content.dataServiceXml?.length() <= 0) {
                        log.warn "Generated PRIMARY content ${content.centralId} does not contain a Nielsen XML"
                        mailService.sendMail("Content ${content.centralId} Missing Nielsen XML",
                                "Content ${content.centralId} for Asset ${content.asset.centralId} - ${content.asset.title} is missing its Data Service XML")
                        return false
                    }

                } else {
                    log.info "Content ${content.centralId} file extension ${fileExt} which does not require crosscheck. Setting to COMPLETE"
                }
            } else {//copy was not successful
                log.warn "File copy from hadoop was not successful for content ${content.centralId}"
                mailService.sendMail("Failed to copy content ${content.centralId} from Hadoop.",
                                "Content ${content.centralId} does not exist or size is different on disk than expected [${content?.size} vs. ${contentFile?.size()}]")
                return false
            }
        } else {
            log.info "Content ${content.centralId} is type ${content.contentType} which does not require a copy. Setting to COMPLETE"
            if (content.original == true && content.contentType == ContentType.METADATA.toString()) {
                log.info "Setting URI of original metadata content to hadoop URI."
                content.uri = clcoreURI
            }
        }// contents we don't want to copy

        Content.withTransaction {
            content.contentState = ContentState.COMPLETE.toString()
            content.stagedDate = new Date()
            content.save(flush: true)
        }
        return true
    }

    /**
     *
     * @param asset
     * @return
     */
    def checkSourceContentFile(Content content, String uri) {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def contentNewPath = baseDir + '/' + content.asset.stagedDirectoryName + '/source/' + content.name
        File contentNewPathFile = new File(contentNewPath)
        if (!contentNewPathFile.exists()) {
            log.info "File $contentNewPathFile does not exist in the source directory in the assets's staged directory"
            copyContentToQCDir(uri, content)
        }
    }

    /**
     *
     * @param asset
     * @return
     */
    def verifyAsset(Asset asset) {
        log.info "Verifying asset ${asset.centralId}"
        if (allContentComplete(asset)) {
            def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
            def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName
            File metadataFile = new File(assetBaseDir + '/metadata.html')
            if (metadataFile?.exists() && metadataFile.size() > 0) {
                //Staging was successful				
				// Determine last staged content date
				def lastContentStagedDate = null
				asset.contents?.each { content ->
					if (content.stagedDate) {
						lastContentStagedDate = (!lastContentStagedDate || content.stagedDate >= lastContentStagedDate) ? content.stagedDate : lastContentStagedDate
					}
				}
                Asset.withTransaction {
					asset.stagedDate = lastContentStagedDate
                    asset.assetState = AssetStates.STAGED
                    asset.save(flush: true)
                }
                log.info "Asset ${asset.centralId} has been STAGED for QC"
            } else {
                //metadata.html was not successful
                log.info "Creating the metadata.html file for asset ${asset.centralId} was not successful"
                mailService.sendMail("Creating metadata.html failed", "Creating the metadata.html file for asset ${asset.centralId} was not successful")
                //TODO delete or re-try

            }
        } else {
            log.error "Asset ${asset.centralId} contents are not COMPLETE to STAGE"
            mailService.sendMail("Asset ${asset.centralId} contents are not COMPLETE",
                    "Asset ${asset.centralId} contents are not COMPLETE for staging.")
            //TODO delete or re-try
        }
    }

    /**
     * Given an asset, iterates asset.content and checks to see if all of the asset's contents are complete. Returning boolen.
     *
     * @param asset
     * @return true or false whether all the asset's contents are complete
     */
    def boolean allContentComplete(Asset asset) {
        boolean complete = true
        asset.contents.each { Content content ->
            if (content.contentState == ContentState.INCOMPLETE.toString()) {
                complete = false
            }
        }

        return complete
    }

    /**
     * Returns the asset staging directory path
     * @param asset
     * @return
     */
    def getAssetBaseDirectory(Asset asset) {

        def dirToRet = ""
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.DECEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 31)
            cal.set(Calendar.HOUR, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            Date lastDayOfTheYear = cal.getTime()

            if (asset.endDate < lastDayOfTheYear) {
                log.info "Asset ${asset.centralId} is a high priority package"
                dirToRet = "high-priority"
            } else {
                log.info "Asset ${asset.centralId} is a library package"
                dirToRet = "library"
            }

            def qcByDate = calculateQCDate(asset)
            String formattedDate = sdf.format(qcByDate)
            String scrubbedName = fileService.cleanStringForDirectoryName(asset?.name)
            dirToRet += "/${formattedDate}/${scrubbedName}/${asset.mediaId}"
            log.info "Returning asset path $dirToRet for asset ${asset.centralId}"
        } catch (Exception e) {
            log.info "Caught exception determining the asset base dir.", e
        }
        return dirToRet
    }

    /**
     * Calculates the QC by date for an asset based on the start date
     * @param asset
     * @return
     */
    def calculateQCDate(Asset asset) {
        def affiliateLeadTime = Setting.getSetting(SettingNames.AFFILIATE_LEAD_TIME)
        def distributionTime = Setting.getSetting(SettingNames.DISTRIBUTION_TIME_IN_DAYS)
        Date qcByDate = asset.startDate - affiliateLeadTime - distributionTime
        log.info "qcByDate for asset ${asset.centralId}: $qcByDate"
        return qcByDate
    }

    /**
     * Writes out the metadata.html file for a STAGED asset
     * @param asset
     * @return
     */
    def dumpAssetMetadataFile(Asset asset) {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName
        if (new File(assetBaseDir)?.exists()) {

            if (allContentComplete(asset)) {
                File metadataFile = new File(assetBaseDir + '/metadata.html')
                if (metadataFile?.exists()) {
                    metadataFile.delete()
                }
                log.info "Creating ${metadataFile.canonicalPath} file for asset ${asset.centralId}"

                metadataFile << "<html>"
                metadataFile << "<body>"
                metadataFile << "<h1>${asset.name}</h1>"

                try {
                    def originalADI = asset.contents.find { it.original == true && it.contentType == ContentType.METADATA.toString() }
                    GPathResult adiXML = fileService.parseAdiXMLUri(new URI(originalADI?.uri))

                    //Find original data to compare to ingest values set on asset.
                    def title = AdiUtil.getFieldValues(adiXML, "title/App_Data/Title")[0]
                    def shortTitle = AdiUtil.getFieldValues(adiXML, "title/App_Data/Title_Brief")[0]
                    def description = AdiUtil.getFieldValues(adiXML, "title/App_Data/Summary_Short")[0]
                    def episode = AdiUtil.getFieldValues(adiXML, "title/App_Data/Episode_Name")[0]
                    def startDate = AdiUtil.getFieldValues(adiXML, "title/App_Data/Licensing_Window_Start")[0]
                    def endDate = AdiUtil.getFieldValues(adiXML, "title/App_Data/Licensing_Window_End")[0]
                    def rating = AdiUtil.getFieldValues(adiXML, "title/App_Data/Rating")[0]
                    def rawRuntime = AdiUtil.getFieldValues(adiXML, "title/App_Data/Run_Time")[0]
                    def runtime = computeRuntime(rawRuntime)
                    def qcType = AdiUtil.getFieldValues(adiXML, "title/App_Data/qcType")[0]
                    def advisories = AdiUtil.getFieldValues(adiXML, "title/App_Data/Advisories")
                    def previews = AdiUtil.getFieldValues(adiXML, "preview/App_Data/Type")

                    metadataFile << "<table border='1'>"
                    metadataFile << "<tr>"
                    metadataFile << "<td></td>"
                    metadataFile << "<th>Asset Metadata</th>"
                    metadataFile << "<th>Original Metadata</th>"
                    metadataFile << "</tr>"
                    //Asset only
                    metadataFile << "<tr>"
                    metadataFile << "<th>Internal Name</th>"
                    metadataFile << "<td>${asset.name.encodeAsHTML()}</td>"
                    metadataFile << "<td>N/A</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Asset ID</th>"
                    metadataFile << "<td>${asset.centralId?.encodeAsHTML()}</td>"
                    metadataFile << "<td>N/A</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Media ID</th>"
                    metadataFile << "<td>${asset.mediaId?.encodeAsHTML()}</td>"
                    metadataFile << "<td>N/A</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>QC Required By</th>"
                    metadataFile << "<td>${sdf.format(calculateQCDate(asset))}</td>"
                    metadataFile << "<td>N/A</td>"
                    metadataFile << "</tr>"

                    metadataFile << "<tr>"
                    metadataFile << "<th>Title</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, 'Title', title, asset.title)}</td>"
                    metadataFile << "<td>${title?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Short Title</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Title_Brief", shortTitle, asset.shortTitle)}</td>"
                    metadataFile << "<td>${shortTitle?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Description</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Summary_Short", description, asset.description)}</td>"
                    metadataFile << "<td>${description?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Episode</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Episode_Name", episode, asset.episode)}</td>"
                    metadataFile << "<td>${episode?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Start Date</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Licensing_Window_Start", startDate, asset.startDate)}</td>"
                    metadataFile << "<td>${startDate?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>End Date</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Licensing_Window_End", endDate, asset.endDate)}</td>"
                    metadataFile << "<td>${endDate?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Rating</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Rating", rating, asset.rating)}</td>"
                    metadataFile << "<td>${rating?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Runtime</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Run_Time", runtime, asset.runtime)}</td>"
                    metadataFile << "<td>${runtime?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>QC Type</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "qcType", qcType, asset.qcType)}</td>"
                    metadataFile << "<td>${qcType?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Advisories</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, "Advisories", advisories, asset.advisory)}</td>"
                    metadataFile << "<td>${advisories?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "<tr>"
                    metadataFile << "<th>Has Previews</th>"
                    metadataFile << "<td>${validateMetadataValue(adiXML, 'Previews', (previews.size() > 0), asset.hasPreviews)}</td>"
                    metadataFile << "<td>${asset.hasPreviews?.encodeAsHTML()}</td>"
                    metadataFile << "</tr>"
                    metadataFile << "</table>"

                    //Additional metadata
                    def someSettingList = Setting.getSetting(SettingNames.METADATA_TO_SHOW, "")
                    if (someSettingList?.size() > 0) {
                        List splitSettings = someSettingList.split(";")
                        log.info "$splitSettings - ${splitSettings.size()}"
                        if (splitSettings.isEmpty() == false) {
                            metadataFile << "<h3>Additional Metadata</h3>"
                            for (int i = 0; i < splitSettings.size(); i++) {
                                def path = splitSettings[i]
                                def field = path.split("/")
                                def fieldName = ""
                                if (field.size() == 3) {
                                    fieldName = field?.getAt(2)
                                    log.info "$fieldName"
                                }
                                def adiMetadata = AdiUtil.getFieldValues(adiXML, path)
                                adiMetadata.each { metadataFile << "${fieldName.encodeAsHTML()}: ${it.encodeAsHTML()} <br>" }
                            }
                        }
                    }

                } catch (IOException e) {
                    def errorMessage = "Problem parsing ADIXML file for asset ${asset.centralId}"
                    log.warn errorMessage
                    metadataFile << "<font color='red'><b>${errorMessage?.encodeAsHTML()}</b></font>"
                }

                def contentsToParse = asset.contents
                def sourceContents = asset.contents.findAll { it.original == true &&
                        it.contentType != ContentType.METADATA.toString() &&
                        it.contentType != ContentType.ARTWORK.toString() }
                if (sourceContents) {
                    metadataFile << "<h2>Source file(s)</h2>"
                    metadataFile << "<p>"
                    sourceContents.each { sourceContent ->
                        metadataFile << "File Name: <a href=file:///mnt/content/${asset.stagedDirectoryName}/source/${sourceContent.name}>${sourceContent.name}</a><br>"
                        metadataFile << "Type: ${sourceContent.contentType?.encodeAsHTML()}<br>"
                        metadataFile << "Profile: ${sourceContent.profile?.encodeAsHTML()}<br>"
                        metadataFile << "Runtime: ${formatRuntime(sourceContent.runtime)}<br>"
                        metadataFile << "Manzanita Result: ${encodeManzanitaResult(sourceContent.manzanitaResult)}<br>"
                        metadataFile << "Manzanita Runtime: ${formatRuntime(sourceContent.manzanitaRuntime)}<br>"
                        metadataFile << "V Chip: ${sourceContent.manzanitaVChip?.encodeAsHTML()}<br>"
                        metadataFile << "CGMS: ${sourceContent.manzanitaCGMS?.encodeAsHTML()}<br>"
                        metadataFile << '</p>'
                        contentsToParse = contentsToParse - sourceContent
                    }
                }

                metadataFile << "<h2>Content files</h2>"
                metadataFile << "<p>"
                contentsToParse.each { Content content ->
                    if (content.contentType != ContentType.METADATA.toString()
                            && content.contentType != ContentType.ARTWORK.toString()
                            && content.qcDate == null) {

                        if (content.original) {
                            metadataFile << "File Name: <a href=file:///mnt/content/${asset.stagedDirectoryName}/source/${content.name}>${content.name}</a><br>"
                        } else {
                            metadataFile << "File Name: <a href=file:///mnt/content/${asset.stagedDirectoryName}/${content.name}>${content.name}</a><br>"
                        }

                        metadataFile << "Type: ${content.contentType?.encodeAsHTML()}<br>"
                        metadataFile << "Profile: ${content.profile?.encodeAsHTML()}<br>"
                        metadataFile << "Audio Type: ${content.audioType?.encodeAsHTML()}<br>"
                        metadataFile << "Runtime: ${formatRuntime(content.runtime)}<br>"
                        if (content.dataServiceXml?.length() > 0) {
                            metadataFile << "Nielsen XML present: True<br>"
                        } else {
                            if (content.original == false) {
                                metadataFile << "Nielsen XML present: <font color='red'><b>False</b></font><br>"
                            } else {
                                metadataFile << "Nielsen XML present: False<br>"
                            }
                        }
                        metadataFile << "Manzanita Result: ${encodeManzanitaResult(content.manzanitaResult)}<br>"
                        metadataFile << "Manzanita Runtime: ${formatRuntime(content.manzanitaRuntime)}<br>"
                        metadataFile << "V Chip: ${content.manzanitaVChip?.encodeAsHTML()}<br>"
                        metadataFile << "CGMS: ${content.manzanitaCGMS?.encodeAsHTML()}<br>"
                        metadataFile << '<br>'
                    }
                }
                metadataFile << "<p>"
                metadataFile << "</body>"
                metadataFile << "</html>"
            } else {
                log.info "Asset ${asset.centralId} has INCOMPLETE content(s); Not dumping metadata."
                return false
            }
        } else {
            log.error "Path ${assetBaseDir} does not exist; Not dumping metadata"
            return false
        }

        return true
    }

    /**
     * Copies the asset's contents to its staging directory
     * @param clcoreURI
     * @param content
     * @return
     */
    def copyContentToQCDir(String clcoreURI, Content content) {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def contentNewPath = baseDir + '/' + content.asset.stagedDirectoryName
        if (content.original) {
            contentNewPath += "/source"
        }
        contentNewPath += '/' + content.name

        boolean success = false
        File contentNewPathFile = new File(contentNewPath)
        long taskStartTime = System.currentTimeMillis();
        if (contentNewPathFile?.exists() && contentNewPathFile.size() == content.size) {
            log.info "File ${contentNewPathFile} exists and is the same size as expected; assuming copied already."
            success = true
        } else {
            log.info "File ${contentNewPathFile} exists...cleaning up."
            contentNewPathFile.delete()
            success = fileService.copyPath(new URI(clcoreURI), contentNewPathFile)
        }

        if (success) {
            Content.withTransaction {
                content.uri = contentNewPath
                content.save(flush: true)
            }

            def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
            def metricInMinutes = metricInSecs / 60
            log.info "METRIC $metricInSecs seconds ($metricInMinutes minutes) to COPY file ${content.uri} out of HADOOP"

            return contentNewPathFile
        }

        return null
    }

    /**
     *
     * @param runtime
     * @return
     */
    def computeRuntime(String runtime) {

        int toRet = 0;
        if (runtime && runtime.length() > 0) {
            def holder = runtime.tokenize(":")
            if (holder.size() == 3) {
                def seconds = holder.get(2)
                def minutes = holder.get(1)
                def hours = holder.get(0)
                toRet = seconds.toDouble() + (minutes.toInteger() * 60) + (hours.toInteger() * 60 * 60)
            } else {
                log.info "computeRuntime - Runtime $runtime is in wrong format, so returning 0"
            }
        }
        return toRet
    }

    /**
     *
     * @param runtime
     * @return
     */
    def computeManzanitaRuntime(String runtime) {

        //runtime comes in form 0:00:00, 0:00:00:00, 0:00:00;00
        int toRet = 0;
        if (runtime && runtime.length() > 0) {
            log.debug "Parsing Manzanita runtime: $runtime"
            def holder
            if (runtime.indexOf(";") >= 0) {
                def time_millis = runtime.split(";")
                runtime = time_millis[0]
            }

            holder = runtime.tokenize(":")
            switch (holder.size()) {
                case 3:
                    def seconds = holder.get(2)
                    def minutes = holder.get(1)
                    def hours = holder.get(0)
                    toRet = seconds.toDouble() + (minutes.toInteger() * 60) + (hours.toInteger() * 60 * 60)
                    break;
                case 4:
                    def millis = holder.get(3)
                    def seconds = holder.get(2)
                    def minutes = holder.get(1)
                    def hours = holder.get(0)
                    toRet = (millis.toInteger() / 60) + seconds.toDouble() + (minutes.toInteger() * 60) + (hours.toInteger() * 60 * 60)
                    break;
                default:
                    log.info "computeRuntime - Runtime $runtime is in uenxpected format, so returning 0"
            }
        }

        return toRet
    }

    /**
     *
     * @param runtime
     * @return
     */
    def formatRuntime(runtime) {
        if (runtime) {
            int hours
            int minutes
            int seconds = runtime % 60
            String toRet

            minutes = runtime / 60
            hours = minutes / 60
            minutes = minutes % 60

            toRet = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            return toRet
        } else {
            return "00:00:00"
        }
    }

    /**
     *
     * @param content
     * @return
     */
    boolean runManzanitaOnContent(Content content) {

        def runCrosscheck = Setting.getSetting(SettingNames.RUN_MANZANITA)
        if (runCrosscheck) {
            def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
            def contentPath = baseDir + '/' + content.asset.stagedDirectoryName
            try {
                //build command string
                def crosscheck = ["crosscheck", "-f", "x", "-c", "/usr/local/clearleap/conf/crosscheck/" + content.profile, content.uri]
                def crosscheckXML = crosscheck + ["-o"]
                def cleanedContentName = fileService.cleanFilenameForDirectoryName(content.name)
                if (content.original) {
                    crosscheckXML += [contentPath + "/source/${cleanedContentName}.xml"]
                } else {
                    crosscheckXML += [contentPath + "/${cleanedContentName}.xml"]
                }
                //execute
                log.info "Executing command: $crosscheckXML"
                long taskStartTime = System.currentTimeMillis();
                def process = crosscheckXML.execute()

                def outStream = new ByteArrayOutputStream(4096)
                def errStream = new ByteArrayOutputStream(4096)
                process.consumeProcessOutput(outStream, errStream)
                process.waitFor()

                def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
                def metricInMinutes = metricInSecs / 60
                log.info("METRIC $metricInSecs seconds ($metricInMinutes minutes) to run CROSSCHECK on ${content.centralId}")

                def manzanitaResult = process.exitValue()
                log.info "Content ${content.name} ran crosscheck with a result of ${manzanitaResult}"
                log.debug "Out: $outStream"
                log.debug "Err: $errStream"

                content.manzanitaResult = ManzanitaResultStates.stringValue(manzanitaResult)
                //analyze result and take action if needed
                if ([0, 1, 2].contains(manzanitaResult) == false) {
                    log.info "Crosscheck returned unexpected exit code ${manzanitaResult} for content ${content.centralId}"
                    mailService.sendMail("Crosscheck returned unexpected exit code for content ${content.centralId}",
                            "Crosscheck returned unexpected exit code ${manzanitaResult} for content ${content.centralId}")
                    return false
                }
                //update content with the manzanita results
                return updateContentWithManzanitaResults(content)
            } catch (java.lang.reflect.UndeclaredThrowableException ute) {
                log.error "NO"
            } catch (Exception e) {
                log.error e, e
                mailService.sendMail("Exception running crosscheck on content ${content.centralId}",
                        "Exception running crosscheck on content ${content.centralId}: " + e)
                return false
            }
        } else {
            log.info "Manzanita NOT configured to run on content."
            if (GrailsUtil.environment != "production") {
                log.info "Populating manzanita fields with dummy data for testing."
                content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
                content.manzanitaReport = """
<?xml version="1.0" encoding="UTF-8"?>
<!-- Created by Manzanita CrossCheck -->
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing SCTE 20 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
                content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
                content.manzanitaVChip = "TV-MA, L"
                content.manzanitaRuntime = 1750
            }
        }

        return true
    }

    /**
     * Executes the ccextractor process on a content file. Creates .srt file that contains closed captioning data.
     *
     * @param content
     * @return
     */
    def runCCOnContent(Content content) {
        //ccextractor <input_file> -o <output file>
        //ccextractor  Game_S1_03_comp_HBOD000000115264_P7D-418789.mpg –o Game_S1_03_comp_HBOD000000115264_P7D-418789.srt
        def runCCExtractor = Setting.getSetting(SettingNames.RUN_CC_EXTRACTOR)
        if (runCCExtractor) {
            def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
            def contentPath = baseDir + '/' + content.asset.stagedDirectoryName
            if (content.original) {
                contentPath += "/source"
            }

            def taskStartTime = System.currentTimeMillis()
            try {
                def cleanedContentName = fileService.cleanFilenameForDirectoryName(content.name)
                def ccCommand = ["ccextractor", content.uri, "-o", contentPath + "/" + cleanedContentName + ".srt"]
                log.info "Executing command: $ccCommand"
                def process = ccCommand.execute()
                process.waitFor()

                def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
                def metricInMinutes = metricInSecs / 60
                log.info("METRIC $metricInSecs seconds ($metricInMinutes minutes) to run CCEXTRACTOR on ${content.centralId}")

                def ccResult = process.exitValue()
                log.info "Content ${content.name} ran ccextractor with a result of ${ccResult}"
                return true
            } catch (Exception e) {
                log.error e, e
                mailService.sendMail("Exception running ccextractor on content ${content.centralId}",
                        "Exception running ccextractor on content ${content.centralId}: " + e)
                return false
            }
        } else {
            log.info "CCExtractor NOT configured to run on content."
        }

        return true
    }

    /**
     *
     * @param content
     * @return
     */
    def updateContentWithManzanitaResults(Content content) {
        File manzanitaXML = fileService.getFileForContent(content, "xml")
        if (manzanitaXML?.exists()) {
            content.manzanitaReport = manzanitaXML.text
            log.info "Parsing crosscheck output file ${manzanitaXML}"
            def parsedXML = new XmlSlurper().parse(manzanitaXML)
            parsedXML.'pid-properties'.each {
                //find cgms
                if (it.'xds-information'.cgms.@value.text().length() > 0) {
                    def cgms = it.'xds-information'.cgms.@value.text()
                    log.debug "Found cgms info: $cgms"
                    content.manzanitaCGMS = it.'xds-information'.cgms.@value.text()
                }
                //find program ratings
                if (it.'xds-information'.prograting.@value.text().length() > 0) {
                    def prograting = it.'xds-information'.prograting.@value.text()
                    log.debug "Found prograting info: $prograting"
                    def programRatings = prograting.replace(" ", ", ")
                    content.manzanitaVChip = programRatings
                }
                //find runtime
                if (it.'video-elementary-stream-information'.'final-timecode'.@value.text().length() > 0) {
                    def duration = it.'video-elementary-stream-information'.'final-timecode'.@value.text()
                    log.debug "Found final-timecode info: $duration"
                    content.manzanitaRuntime = computeManzanitaRuntime(duration)
                }
            }
            log.info "Crosscheck output file successfully parsed and content ${content.centralId} updated"
            return true
        } else {
            log.warn "File ${manzanitaXML} does not exist for parsing and updating content ${content.name}"
            return false
        }
    }

    /**
     *
     * @param asset
     * @return
     */
    def updatePreviewContentsManzanitaResult(Asset asset) {

        def CC_ERROR_CODES = Setting.getSetting(SettingNames.MANZANITA_CC_ERROR_CODES)
        def originalPreview = asset.contents.find { Content content -> content.original == true && content.contentType == ContentType.PREVIEW.toString()}
        def generatedPreviews = asset.contents.findAll { Content content -> content.original == false && content.contentType == ContentType.PREVIEW.toString()}

        def sourceContainsCCError = false
        if (originalPreview) {
            log.info "updatePreviewContentsManzanitaResult - Found original preview: ${originalPreview?.centralId}"

            def originalPreviewErrorCodes = parseContentManzanitaErrorCodes(originalPreview)
            if (originalPreviewErrorCodes?.contains("6-53e") == true) {
                log.info "Original preview content ${originalPreview.centralId} contains the CC error code 6-53e"
                sourceContainsCCError = true
                //if the pro7 preview has only 1 error: 6-53e, then PASS the source (manzanita result)
                if (originalPreviewErrorCodes.size() == 1) {
                    log.info "Original preview content ${originalPreview.centralId} contains ONLY the CC error code 6-53e; PASSING content"
                    Content.withTransaction {
                        originalPreview.manzanitaResult = ManzanitaResultStates.PASSED.toString()
                        originalPreview.save(flush: true)
                    }
                }
            }
        }

        if (generatedPreviews?.size() > 0 && sourceContainsCCError) {
            log.info "updatePreviewContentsManzanitaResult - Found ${generatedPreviews?.size()} generated previews"

            for (Content generatedContent in generatedPreviews) {
                //if generated preview has 6-53e and/or 6-54e and source has 6-53e as well -> pass the generated
                def contentErrorCodes = parseContentManzanitaErrorCodes(generatedContent)
                if (contentErrorCodes?.size() > 0) {

                    boolean ccErrors = true
                    for (errorCode in contentErrorCodes) {
                        if (CC_ERROR_CODES.contains(errorCode) == false) {
                            log.info "Generated preview content ${generatedContent.centralId} contains other non-cc error $errorCode."
                            ccErrors = false
                            break
                        }
                    }

                    if (ccErrors) {
                        log.info "Generated preview content ${generatedContent.centralId} contains only the CC error code(s) ${contentErrorCodes}; PASSING content"
                        Content.withTransaction {
                            generatedContent.manzanitaResult = ManzanitaResultStates.PASSED.toString()
                            generatedContent.save(flush: true)
                        }
                    }
                }// end if
            }// end for

        }
    }

    /**
     *
     * @param asset
     * @param xmlURI
     * @return
     */
    def updateAssetWithADI(Asset asset, URI xmlURI) {
        //getADIXML content and update asset.runtime, asset.advisory
        def xmlRuntimes
        def xmlAdvisories
        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        try {
            GPathResult adiXML = fileService.parseAdiXMLUri(xmlURI)
            //update runtime and advisory
            xmlRuntimes = AdiUtil.getFieldValues(adiXML, "title/App_Data/Run_Time")
            xmlAdvisories = AdiUtil.getFieldValues(adiXML, "title/App_Data/Advisories")
            log.info "Updating Asset with ADI fields ${xmlRuntimes} & ${xmlAdvisories}"
            asset.runtime = computeRuntime(xmlRuntimes)
            asset.advisory = ""
            xmlAdvisories.each {
                if (it != null) {
                    asset.advisory += "${it};"
                }
            }
            //check to see if the package included previews
            def previews = AdiUtil.getFieldValues(adiXML, "preview/App_Data/Type")
            asset.hasPreviews = (previews?.size() > 0) ? true : false
            //add the contents of adixml to the asset
            asset.adixml = fileService.getAdiXmlString(adiXML, "UTF-8", false)
        } catch (IOException e) {
            log.warn "Problem parsing ADIXML file for asset ${asset.centralId}"
            mailService.sendMail("IOException parsing ADIXML file for asset ${asset.centralId}",
                    "IOException ${e.getMessage()} parsing ADIXML file for asset ${asset.centralId}: " + e)
        }
    }

    /**
     *
     * @param asset
     * @return
     */
    def setAssetQCType(Asset asset) {
        def hboIngestSourceId = Setting.getSetting(SettingNames.HBO_INGEST_SOURCE)
        def hboHDIngestSourceId = Setting.getSetting(SettingNames.HBO_HD_INGEST_SOURCE)
        def hboSDIngestSourceId = Setting.getSetting(SettingNames.HBO_SD_INGEST_SOURCE)
        def qcType = "N/A"
        switch (asset.ingestSourceId) {
            case hboIngestSourceId:
                qcType = "BOTH"
                break;
            case hboHDIngestSourceId:
                qcType = "HD only"
                break;
            case hboSDIngestSourceId:
                qcType = "SD only"
                break;
            default:
                log.warn "Asset ingest_source ${asset.ingestSourceId} not configured for qc type"
        }

        asset.qcType = qcType
    }

    /**
     *
     * @param content
     * @return
     */
    def parseContentManzanitaErrorCodes(Content content) {
        if (content.manzanitaReport?.length() > 0) {
            GPathResult parsedXML = new XmlSlurper().parseText(content.manzanitaReport)
            def errors = parsedXML.'errors'.error
            log.info "Content ${content.centralId} has ${errors.size()} manzanita warnings/errors"
            def errorCodes = []
            def errorRegex = "\\d+\\-\\d+[e]"
            errors.each {
                def matcher = it.@code =~ errorRegex
                if (matcher.matches()) {
                    errorCodes << it.@code.text()
                }
            }
            log.info "Content ${content.centralId} has ${errorCodes.size()} manzanita errors"
            return errorCodes
        } else {
            log.info "Content ${content.centralId} has empty or null manzanita report ${content.manzanitaReport}"
        }

        log.warn "Couldn't get warning/error codes for content ${content.centralId}"
        return []
    }

    /**
     *
     * @param result
     * @return
     */
    def encodeManzanitaResult(String result) {
        def toRet = "N/A"
        switch (result) {
            case ManzanitaResultStates.FAILED.toString():
                toRet = "<font color='red'><b>$result</b></font>"
                break;
            default:
                toRet = result
        }

        return toRet
    }

    /**
     *
     * @param adiXML
     * @param adiField
     * @param adiValue
     * @param assetMetadata
     * @return
     */
    def validateMetadataValue(adiXML, adiField, adiValue, assetMetadata) {

        def sameValue = true
        switch(adiField) {

            case 'Licensing_Window_Start':
                adiValue = parseAndPadADIDate(adiValue, [0,0,0])
                sameValue = assetMetadata - adiValue == 0
                break;
            case 'Licensing_Window_End':
                adiValue = parseAndPadADIDate(adiValue, [23,59,59])
                sameValue = assetMetadata - adiValue == 0
                break;
            case 'Advisories':
                for (it in adiValue) {
                    if (it != null) {
                        sameValue = assetMetadata?.contains(it)
                        if (!sameValue) {
                            break
                        }
                    }
                }
                break;
            default:
                sameValue = assetMetadata == adiValue
                break;
        }

        if (sameValue) {
            return "${assetMetadata?.encodeAsHTML()}"
        } else {
            return "<font color='red'><b>${assetMetadata?.encodeAsHTML()}</b></font>"
        }

    }

    /**
     *
     * @param dateStr
     * @param mhs
     * @return
     */
    public Date parseAndPadADIDate(String dateStr, List mhs) {

        Date defaultDate = DateUtils.getDayStart(new Date())
        def validFormats = [DateFormatter.ADI, DateFormatter.ADI_TIME_ALTERNATIVE, DateFormatter.ADI_NO_TIME]
        for(format in validFormats) {
            try {
                def paddedDate = format.parse(dateStr)
                switch (format) {
                    case DateFormatter.ADI_TIME_ALTERNATIVE: //pad seconds to 00 or 59
                        Calendar c = Calendar.getInstance()
                        c.setTime(paddedDate)
                        c.set(Calendar.SECOND, mhs[2])
                        return c.time
                        break
                    case DateFormatter.ADI_NO_TIME: //pad hh:mm:ss to 00:00:00 or 23:59:59
                        Calendar c = Calendar.getInstance()
                        c.setTime(paddedDate)
                        c.set(Calendar.HOUR, mhs[0])
                        c.set(Calendar.MINUTE, mhs[1])
                        c.set(Calendar.SECOND, mhs[2])
                        return c.time
                        break
                    case DateFormatter.ADI: // no action
                        return paddedDate
                        break
                    default:
                        log.error "parseAndPadADIDate - Unexpected DateFormat $format encountered; returning new Date"
                        return defaultDate
                }

            } catch(Exception ex) {}  //ignore
        }
        log.error "parseAndPadADIDate - Date $dateStr doesn't fall into any of the ADI formats"
        return defaultDate
    }


    /**
     *
     * @return
     */
    def List exportStagedAssets() {
        Sql mxpDB
        def mxpSubmittedAssets = []
        // Find all STAGED qc assets.
        def hboAccountId = Setting.getSetting(SettingNames.HBO_ACCOUNT)

        def qcStagedAssets = Asset.createCriteria().list() {
            eq("assetState", AssetStates.STAGED)
            order("centralId", "asc")
        }
        log.info "Found ${qcStagedAssets.size()} qc assets that are still STAGED."

        // For each staged asset, see if it is in the submitted state still on mxp
        try {
            mxpDB = createDBSQLConnection()
            qcStagedAssets.each { Asset qcAsset ->
                def mxpAsset = mxpDB.firstRow("""SELECT id, mediaid, startdate, assetstate from asset where user_id in
                        (select id from cl_user where account_id = $hboAccountId)
                        and assetstate not in ('SUBMITTED')
                        and id = ${qcAsset.centralId}""")
                if (mxpAsset) {
                    log.info "$mxpAsset"
                    def reportEntryModel = [id:qcAsset.id, centralId: qcAsset.centralId, startDate: qcAsset.startDate, createDate: qcAsset.createDate,
                            title: qcAsset.title,assetState: qcAsset.assetState, mxpState: mxpAsset.assetstate]
                    log.info "MXP asset ${qcAsset.centralId} is still in the SUBMITTED state in MXP."
                    mxpSubmittedAssets << reportEntryModel
                }
            }
            
            log.info "Found ${mxpSubmittedAssets.size()} qc assets that are STAGED in qc and still SUBMITTED in MXP."
        } catch (Exception e) {
            log.info "Caught Exception $e.message generating asset reconcilation report", e
        } finally {
            mxpDB?.close()
        }

        return mxpSubmittedAssets
    }

    /**
     *
     * @return
     */
    def constructListOfProfilesToIgnore() {

        def allIgnoredProfiles = []
        String PROFILES_TO_IGNORE = Setting.getSetting(SettingNames.PROFILES_TO_IGNORE)
        String WEB_PREVIEW_PROFILES = Setting.getSetting(SettingNames.WEB_PREVIEW_PROFILES)

        def ignoredProfilesMap = PROFILES_TO_IGNORE.split(",")
        def webProfilesMap = WEB_PREVIEW_PROFILES.split(",")

        ignoredProfilesMap.each {
            allIgnoredProfiles << it?.trim()
        }

        webProfilesMap.each {
            allIgnoredProfiles << it?.trim()
        }

        log.info "List of profiles to ignore $allIgnoredProfiles"
        return allIgnoredProfiles
    }

    /**
     * Run updateClosure if object's state was safely updated.
     *
     * @param object The object to update.
     * @param oldState The object's current state.
     * @param newState The state to update the object to.
     * @param updateClosure The operation to perform if object's state was safely
     * updated.  Note that object is unlocked during this operation.
     * @return Whether object's state was updated to newState and updateClosure
     * ran.
     *
     * @see #safeUpdateState(Object, Object, Object)
     */
    public static boolean safeUpdateState(object, oldState, newState, Closure updateClosure) {
        if(safeUpdateState(object, oldState, newState)) {
            updateClosure(object)
            return true;
        } else {
            return false;
        }
    }

    public static boolean safeUpdateState(object, oldState, newState) {
        return UpdateUtil.safeUpdateObject(object) {
            if(object.assetState == oldState) {
                object.assetState = newState;
                return true;
            } else {
                return false;
            }
        }
    }

}