package groovy.util

import groovy.constants.MetadataConstants
import groovy.constants.SettingNames
import org.htmlcleaner.CleanerProperties
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.htmlcleaner.TagNode

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class IngestUtil {
	
	private static Log log = LogFactory.getLog("java.npp.IngestUtil");

	/**
	 * Called by UploadController
	 */
	public static long getMaxSizeInBytes() {
		String maxFileSize = Setting.findByName(SettingNames.FILE_MAX_SIZE_MB).value

		long maxSize = Long.parseLong(maxFileSize)
		
		// MB * 1024 = KB; KB * 1024 = B
		return (maxSize * 1024 * 1024) 
	}

	/**
	 * Called by UploadController and OverlayController
	 */
	public static void verifyFileSize(long fileSize) throws Exception {
		long maxSize = IngestUtil.getMaxSizeInBytes()
		if (fileSize > maxSize) {
			String errorMsg = "File size is too big: MAX=${maxSize}, ACTUAL=${fileSize}."
    		log.warn errorMsg
    		throw new Exception(errorMsg)
    	}
	}


  /**
   * Scrubs a string of spaces and invalid characters, will also truncate for content names.
   * Content names should be no more than 64 chars.
   *
   * @param oldName
   * @return
   */
    public static String validateAndCleanContentname(String filename) {
      return validateAndCleanName(filename, MetadataConstants.MAX_LENGTH_CONTENT_FILENAME)
    }


    /**
	 * Called by IngestService, FeedService, OverlayService, and UploadController
	 * Method now cleans a file name and truncates the file name if too long. Truncating
	 * is the only validation step taken, callers are responsible for checking file
	 * invalid/missing extensions.
	 */
    public static String validateAndCleanFilename(String filename) {
      return validateAndCleanName(filename, MetadataConstants.MAX_LENGTH_ASSET_NAME)
    }


   /**
    * Helper method for validate and clean. Does the validating and cleaning.
    * @param filename
    * @param nameLength
    * @return
    * @throws Exception
    */
    public static String validateAndCleanName(String filename, int nameLength) throws Exception {
		 
		 log.info "Validating filename '${filename}'."
		 int indexOfDot = filename.lastIndexOf('.')
		 if (indexOfDot < 0) {
			 String errorMsg = "No extension on file '${filename}'."
			 log.info errorMsg
			 throw new Exception(errorMsg)
		 } else if(filename.endsWith(".")) {
			 String errorMsg = "No extension on file '${filename}'."
			 log.info errorMsg
			 throw new Exception(errorMsg)
		 }
		 
		 log.info "Cleaning filename '${filename}'."
		 String fName = cleanFilename(filename)
		 indexOfDot = fName.lastIndexOf('.')

		 String filenameFront = fName[0..indexOfDot-1]
		 String filenameExt = fName[indexOfDot+1..fName.length()-1].toLowerCase()

		 // Truncate if necessary
		 if (filenameFront.length() > nameLength) {
			 log.warn "Filename is too long - truncating..."
			 filenameFront = filenameFront[0..nameLength-1]
		 }

		 return "${filenameFront}.${filenameExt}"
	 }
	
	/**
	 * Gets the file name off a given path and returns it.
	 * The extension will be removed if it exists
	 */
	public static String getFileName(String filename) {
		int indexOfDot = filename.lastIndexOf('.')
		if (indexOfDot < 0 || filename.endsWith("."))
			return filename.trim()
			
		return filename[0..indexOfDot-1].trim()
	}
	
	/**
	 * Gets the file extension off a given path and returns it in lowercase form.  
	 * If there is not an extension, returns null
	 */
	public static String getFileExtension(String filename) {
		int indexOfDot = filename.lastIndexOf('.')
		if (indexOfDot < 0 || filename.endsWith("."))
			return null
			
		return filename[indexOfDot+1..filename.length()-1].toLowerCase()
	}
	
	/**
	 * Used as a hack for the moment, this function adds a file extension to a file if
	 *  it does not have one.
	 */
	public static String addFileExtensionIfNotExists(String filename) {
   		if (filename.lastIndexOf('.') < 0) { 
   			log.warn "File was missing an extension. Added it in"
   			filename += ".mod"
   		}
   		else if (filename.endsWith(".")) {
   			log.warn "File was missing an extension. Added it in"
   			filename += "mod"
		}
   		
   		return filename
	}
	
	/*
	 * Cleans the file name to an acceptable version to be saved on the system
	 */
	public static String cleanFilename(String filename) {
		String filenameFront = ""
		String filenameExt = ""
		
		int indexOfDot = filename.lastIndexOf('.')
		if (indexOfDot < 0) {
			filenameFront = filename
			filenameExt = ""
		} else {
			filenameFront = filename[0..indexOfDot-1]
			filenameExt = filename[indexOfDot+1..filename.length()-1].toLowerCase()
		}
		
		// Strip invalid characters
		filenameFront = ValidateUtil.makeValidString(filenameFront, MetadataConstants.VALID_CHAR_FILE_NAME)
		if (filenameFront.length() == 0) {
			filenameFront = 'DefaultFileName'
    		log.warn "Filename must exist (currently 0 characters). Changing to '${filenameFront}'."
    	}

        if (filenameExt.length() > 0) {
            filenameExt = ValidateUtil.makeValidString(filenameExt, MetadataConstants.VALID_CHAR_FILE_NAME)
        }
		
		log.info "Returning cleaned file name ${filenameFront}.${filenameExt}"
		return "${filenameFront}.${filenameExt}"
	}

	
	/**
	 * Called by FeedService
	 */
	public static String cleanAssetName(String name) {
		// Strip invalid characters and leading/trailing whitespace; keep this consistent w/ AssetConstraints.groovy
		String retName = ValidateUtil.makeValidString(name, MetadataConstants.VALID_CHAR_ASSET_NAME)?.trim()
				
		if (retName == null || retName.length() == 0) {
			retName = 'DefaultAssetName'
	    	log.warn "Asset name must exist (currently 0 characters). Changing to '${retName}'."
		}
		
		// Truncate if necessary
		if (retName.length() > MetadataConstants.MAX_LENGTH_ASSET_NAME) {
    		log.warn "Asset name is too long - truncating..."
    		retName = retName[0..MetadataConstants.MAX_LENGTH_ASSET_NAME-1]
		}
		
		return retName
	}
	
	/**
	 * Called by FeedService
	 */
	public static String cleanAssetTitle(String title) {
		if (!title) return ""
		
		// Strip invalid characters; keep this consistent w/ AssetConstraints.groovy
		title = ValidateUtil.makeValidString(title, MetadataConstants.VALID_CHAR_ASSET_NAME)
		// Trim leading and trailing whitespace
		title = title.trim()
		// Truncate if necessary
		if (title.length() > MetadataConstants.MAX_LENGTH_ASSET_NAME) {
    		log.warn "Asset name is too long - truncating..."
			title = title[0..MetadataConstants.MAX_LENGTH_ASSET_NAME-1]
		}
		
		return title
	}
	
	/**
	 * Called by FeedService
	 */
	public static String cleanAssetShortTitle(String shortTitle) {
		if (!shortTitle) return ""
		
		// Strip invalid characters; keep this consistent w/ AssetConstraints.groovy
		shortTitle = ValidateUtil.makeValidString(shortTitle, MetadataConstants.VALID_CHAR_ASSET_SHORT_NAME)
		// Trim leading and trailing whitespace
		shortTitle = shortTitle.trim()
		// Truncate if necessary
		if (shortTitle.length() > MetadataConstants.MAX_LENGTH_ASSET_SHORT_NAME) {
    		log.warn "Asset short title is too long - truncating..."
    		shortTitle = shortTitle[0..MetadataConstants.MAX_LENGTH_ASSET_SHORT_NAME-1]
		}
		
		return shortTitle
	}

	/**
	 * Called by FeedService
	 */
	public static String cleanAssetDescription(String description) {
		if (description == null) return null
		
		// Clean out HTML tags and CDATA section from the string
		description = stripCdataHtml(description)
		
		// Strip invalid characters
		description = ValidateUtil.makeValidString(description, MetadataConstants.VALID_CHAR_ASSET_DESCR)
        // Trim leading and trailing whitespace
		description = description.trim()

        // Truncate if necessary
		if (description.length() > MetadataConstants.MAX_LENGTH_ASSET_DESCR) {
    		log.warn "Asset description is too long - truncating..."
			description = description[0..MetadataConstants.MAX_LENGTH_ASSET_DESCR-1]
		}
		
		return description;
	}
	

    
    // Returns width and height of an MultipartFile as a map: [width: <w>, height: <h>]
    public static Map getImageDimensions(f) {
		def inputStream
		try {
			inputStream = f.getInputStream()
		}
		catch (Exception e) {
			log.error "getImageDimensions: Caught exception getting inputStream from file: ", e
			return null
		}
		
		return getInputStreamFileDimensions(inputStream)
    }
	

	
	public static Map getInputStreamFileDimensions(inputStream) {
    	BufferedImage image = null
    	
		try {
			image = ImageIO.read(inputStream)
			if (image) {
				def width = image.getWidth()
				def height = image.getHeight()
				return [width: width, height: height]
			}
		} 
    	catch (IOException e) {
			log.error "getInputStreamFileDimensions: Error getting dimensions: ", e
			return null
		}
		catch (Exception e) {
			log.error "getInputStreamFileDimensions: Caught unknown exception: ", e
			return null
		}
		
		return null
	}
	
	// Given a width and height, returns the smallest form of the aspect ratio
	//  using GCF
	public static String getAspectRatio(int width, int height) {
		int smaller = width > height ? height : width
		int i = smaller
		int gcf = -1		
			
		while (i > 0 && gcf == -1) {
			if ((width%i == 0) && (height%i == 0))
				gcf = i
			i--
		}
		
		return "${width/gcf}:${height/gcf}"
	}

	/**
	 * Determines the correct image MIME type given an extension
	 */
	public static String getImageMimeType(String imgExtension) {
		switch (imgExtension?.toUpperCase()) {
			case "BMP":
				return "image/bmp"
				break
			case "GIF":
			case "PNG":
				return "image/gif"
				break
			case "JPE":
			case "JPG":
			case "JPEG":
				return "image/jpeg"
				break
			case "TIF":
			case "TIFF":
				return "image/tiff"
				break
		}

		return ""
	}

    /**
     * This function takes in a string with html tags in it and returns a string without html tags
     * If a CDATA section is present, it will be stripped as well
     * Final return value will be plain text with decodeHTML() called on it so that any
     *   escaped values are set to their actual value
     */
    public static stripCdataHtml(String str) {
        // First unescape all HTML characters so that we don't have half escaped and half
        //  unescaped characters in the bunch
        String decodeStr = str.decodeHTML()

        // We use the HtmlCleaner class to format the HTML properly
        // It will escape all html characters such as '<' and '>' that are not
        //  associated with a known html tag
        HtmlCleaner cleaner = new HtmlCleaner()
        CleanerProperties props = cleaner.getProperties()
        // These 2 keep the cleaner from adding an xml declaration and doctype
        props.setOmitDoctypeDeclaration(true)
        props.setOmitXmlDeclaration(true)

        // This is the cleaned version of the string with all html tags
        //  recognized and closed properly so they can be removed.  At
        //  this point, node is only an html/xml structure
        TagNode node = cleaner.clean(decodeStr)

        // Serialize the html/xml to text and decode the html tags
        SimpleXmlSerializer serializer = new SimpleXmlSerializer(props)
        String htmlStr = serializer.getXmlAsString(node)?.decodeHTML()

        // Pull the serialized string into an XmlSlurper
        // CDATA section will be dropped after this
        XmlSlurper parser = new XmlSlurper()
        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        parser.setFeature("http://xml.org/sax/features/namespaces", false)

        def gPathResult
        try {
            gPathResult = parser.parseText(htmlStr)
        }
        catch (Exception e) {
            // This means there is an improperly formatted description
            return ""
        }

        // Get the text from the body node
        String bodyStr = gPathResult.text()

        // If a CDATA section was parsed, then it is now removed but the
        //  unescaped html inside needs to be removed next
        node = cleaner.clean(bodyStr)

        // Serialize the html/xml to text.  This will then be put back through the xml parser
        // This is the final decodeHTML and ensures that any html characters not part of a tag
        //  will be there actual value when returned (ie '&gt;' becomes '>')
        htmlStr = serializer.getXmlAsString(node)?.decodeHTML()
        try {
            gPathResult = parser.parseText(htmlStr)
        }
        catch (Exception e) {
            // This means there is an improperly formatted description
            return ""
        }

        // Get the return text from the fully cleaned description body
        String retStr = gPathResult.text()

        return retStr
    }
}