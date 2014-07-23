package clquality

import clsetting.Setting
import com.clearleap.core.MetadataConstants
import com.clearleap.core.util.AdiStreamingMarkupBuilder
import com.clearleap.core.util.ValidateUtil
import groovy.util.slurpersupport.GPathResult
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

import org.apache.hadoop.fs.FileUtil as HadoopFileUtils
import settings.SettingNames
import clquality.Content

class FileService {

    static transactional = true

    def grailsApplication

    //Taken from FileUtils; bug in FileUtils
    /**
     * Takes a String representing a filename and cleans it to be used as a directory name
     *
     * @param filename
     * @return
     */
    def cleanFilenameForDirectoryName(String filename) {
        String filenameFront = "";

        int indexOfDot = filename.lastIndexOf('.');
        if (indexOfDot < 0) {
            filenameFront = filename;
        } else {
            filenameFront = filename.substring(0, indexOfDot); //<-- (indexOfDot - 1) bug in FileUtils
        }

        // Strip invalid characters
        return cleanStringForDirectoryName(filenameFront)
    }

    /**
     * Takes in a string and returns a suitable directory name based on the value.
     *
     * @param filename the value to create the directory name from
     * @return a directory name from the string
     */
    def cleanStringForDirectoryName(String filename) {

        String cleanedFileName = "";
        // Strip invalid characters
        cleanedFileName = ValidateUtil.makeValidString(filename, MetadataConstants.VALID_CHAR_FILE_NAME);
        if (cleanedFileName.length() == 0) {
            cleanedFileName = "DefaultDirectoryName";
            log.warn "Directory name must exist (currently 0 characters). Changing to " + cleanedFileName;
        }

        log.info "Returning cleaned directory name " + cleanedFileName;
        return cleanedFileName;
    }

    /**
     * Copies a Hadoop URI file to a local File destination
     *
     * @param srcUri
     * @param destFile
     * @return
     */
    def copyPath(URI srcUri, File destFile) {
        FileSystem fs
        try {
            def fsDefaultName = Setting.getSetting(SettingNames.FS_DEFAULT_NAME)
            Configuration conf = new Configuration()
            conf.set("fs.default.name", fsDefaultName)
            fs = FileSystem.get(conf)

            Path h_srcPath = new Path(srcUri.path)
            Path h_dstPath = new Path(destFile.absolutePath)
            log.info "Creating dirs ${destFile.parent}"
            // Force the local directory to be created
            new File(destFile.parent).mkdirs()
            // Copy
            log.info "Copying file ${h_srcPath} to ${h_dstPath}"
            HadoopFileUtils.copy(fs, h_srcPath, destFile, false, conf)
            //fs.copyToLocalFile(h_srcPath, h_dstPath)

            def crcName = destFile.parent + File.separator + "." + destFile.name + ".crc"
            def crcFile = new File(crcName)
            if (crcFile.exists()) {
                crcFile.delete()
            }

        } catch (Exception e) {
            log.info (e)
            return false;
        } finally {
        }

        return true;
    }

    /**
     * Calculates the md5 checksum of a URI file.
     *
     * @param uri
     * @return
     */
    def md5(def uri) {
        InputStream dataStream

        try {
            dataStream = new FileInputStream(new File(uri.path))
            return getMD5ForInputStream(dataStream)
        } catch (Exception e) {
            log.info ("Error getting content ${e}")
            return "invalid"
        } finally {
            try { dataStream.close() } catch (Exception ignore) {}
        }
    }

    /**
     * Calculates the md5 checksum of a InputStream.
     *
     * @param is
     * @return
     */
    String getMD5ForInputStream(InputStream is) {

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("Error getting instance of MD5 message digest", e);
        }

        byte[] buffer = new byte[8192];
        int read = 0;

        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
        } catch (IOException e) {
            log.error("IO exception caught performing digest on inputSteam", e);
        } finally {
            try { is.close() } catch (Exception ignore) {}
        }

        byte[] md5sum = digest.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < md5sum.length; ++i) {
            hexString.append(Integer.toHexString(0x0100 + (md5sum[i] & 0x00FF)).substring(1));
        }

        log.info ("Calculated md5: ${hexString.toString()} ");
        return hexString.toString();
    }

    /**
     * Checks the available disk space for the base directory location from the Settings table
     *
     * @return
     */
    def haveSpace() {
        def minSpaceRequired = Setting.getSetting(SettingNames.MIN_REQUIRED)
        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def contentDir = new File(baseDir)
		try {
	        if ((contentDir.getUsableSpace() * 100 / contentDir.getTotalSpace()) < minSpaceRequired) {
	            log.info "Not enough space on $contentDir"
	            return false
	        }
		}
		catch (Exception e) {
			log.error "Could not calculate space requirements for ${contentDir}. Assuming no space to be safe. Exception was:\n", e
			return false
		}
        return true
    }

    /**
     * Takes a XMLSlurper parsed GPath XML and returns the String representaion
     *
     * @param gPathResult
     * @param encoding
     * @param includeDeclarations
     * @return
     */
    def getAdiXmlString(GPathResult gPathResult, String encoding, boolean includeDeclarations) {
		// Output the GPathResult string
        AdiStreamingMarkupBuilder outputBuilder = new AdiStreamingMarkupBuilder()
		outputBuilder.encoding = encoding
		String xmlString = outputBuilder.bind {
			if (includeDeclarations) {
				mkp.xmlDeclaration()
				mkp.yieldUnescaped '<!DOCTYPE ADI SYSTEM "ADI.DTD">'
			}
			mkp.yield gPathResult
		}

		return xmlString
    }

    /**
     * Parses a URI ADI.XML file into a GPath
     *
     * @param adiXmlUri
     * @return
     */
    def parseAdiXMLUri(URI adiXmlUri) {

        XmlSlurper parser = new XmlSlurper()
        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        parser.setFeature("http://xml.org/sax/features/namespaces", false)
        parser.setKeepWhitespace(true)

        def fsDefaultName = Setting.getSetting(SettingNames.FS_DEFAULT_NAME)
        Configuration conf = new Configuration()
        conf.set("fs.default.name", fsDefaultName)
        FileSystem fs = FileSystem.get(conf)

		Path h_srcPath = new Path(adiXmlUri.path)
		InputStream is = fs.open(h_srcPath)

	    def gPathResult = parser.parse(is)
        is.close()
        return gPathResult
    }

    /**
     * Recursively drills down to all folders from the base directory and deletes any emtpy folders.
     *
     * @param directory
     * @param receiver
     */
    def deleteEmptyDirs(File currentDirectory) {

        currentDirectory.eachDir {
            deleteEmptyDirs(it)
        }

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        if (currentDirectory.absolutePath == new File(baseDir).absolutePath) {
            return
        }

        log.debug "Processing directory ${currentDirectory.name} for delete"
        def dirFiles = currentDirectory?.listFiles()
        if (dirFiles?.size() < 1) {
            log.info "Directory ${currentDirectory.name} is an empty sub directory."
            Date time = new Date()
            if ((time.getTime() - currentDirectory.lastModified()) > 30000) {
                if (currentDirectory.delete()) {
                    log.info "Directory ${currentDirectory.name} successfully deleted"
                } else {
                    log.warn "Directory ${currentDirectory.name} was not deleted"
                    return;
                }
            } else {
                log.info "Directory ${currentDirectory.name} will not be deleted, directory is still being modified"
                return;
            }
        }
    }

    /**
     * Returns the type of File desired for a content object
     *
     * @param content the content to obtain the desired set of files
     * @param fileExt the type of file desired for the content (.xml, .srt, .mpg etc..)
     * @return File
     */
    def getFileForContent(Content content, String fileExt) {

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def assetBaseDir = baseDir + '/' + content.asset.stagedDirectoryName
        def source = content.original ? "/source/" : "/"
        switch (fileExt) {
            case "xml":
                def cleanedContentName = cleanFilenameForDirectoryName(content.name)
                return new File(assetBaseDir + source + "${cleanedContentName}.xml")
                break;
            case "srt":
                def cleanedContentName = cleanFilenameForDirectoryName(content.name)
                return new File(assetBaseDir + source + "${cleanedContentName}.srt")
                break;
            case "mpg":
                return new File(content.uri)
                break;
            case "html":
                return new File(assetBaseDir + "/metadata.html")
                break;
            default:
                log.info "getFileForContent - Wrong extension passed."
                return null
        }
    }

}