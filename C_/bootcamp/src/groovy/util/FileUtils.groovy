package groovy.util

import groovy.constants.MetadataConstants
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.compress.archivers.zip.ZipFile
import org.apache.commons.compress.utils.IOUtils
import org.apache.commons.io.FilenameUtils
import org.apache.commons.logging.LogFactory

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 *
 *
 * @author Nick
 *
 * TODO. Let this be a wrapper for the InputStream Version
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final log = LogFactory.getLog(this)

	public static String getMD5ForFile(File file)
	{
		try {
			return getMD5ForInputStream(new FileInputStream(file));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getMD5ForInputStream(InputStream is ) {

		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance( "MD5" );
		} catch (NoSuchAlgorithmException e) {
			log.error("Error getting instance of MD5 message digest", e);
		}

		byte[]      buffer = new byte[8192];
		int         read   = 0;

		try {
			while ( ( read = is.read( buffer ) ) > 0 ) {
				digest.update( buffer, 0, read );
			}
		} catch (IOException e) {
			log.error("IO exception caught performing digest on inputSteam", e);
		}

		byte[]       md5sum    = digest.digest();
		StringBuffer hexString = new StringBuffer();

		for ( int i = 0; i < md5sum.length; ++i ) {
			hexString.append( Integer.toHexString( 0x0100 + ( md5sum[i] & 0x00FF ) ).substring( 1 ) );
		}

		return hexString.toString();
	}

  /**
   * Create a symbolic link on the file system from target to linkName.
   * target and linkName are passed to "ln", so should be absolute paths,
   * even if you think you know what the CWD is.
   *
   * @param target  The absolute path of the file that the symlink should point at
   * @param linkName  The absolute path of the symlink
   * @throws IOException  If there is any issue execing the process or creating the symlink.
   *
   */
  public static void symlink(String target, String linkName) throws IOException
  {
    try {
	    String[] cmdArgs = ["/bin/ln", "-s" , target,  linkName] as String [];
      Process p = Runtime.getRuntime().exec( cmdArgs );
      int rVal = p.waitFor();

	    if (rVal != 0) {
		    InputStream stderr = p.getErrorStream();
		    BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
		    String errorMessage = stderrReader.readLine();
		    log.warn("Error creating symlink from " + target + " to " + linkName + ":  " + errorMessage);
		    throw new IOException(errorMessage);
	    }

    } catch(InterruptedException e){
      log.error("Interrupted while creating symlink from " + target + " to " + linkName, e);
    }
  }

  /**
   * i.e. zip -r zipFileNameFullPath /usr/local/dirToZip
   */
  public static void zip(String zipFilePath, String dirPath) throws IOException {
	  try {		  
		  ProcessBuilder processBuilder = new ProcessBuilder("zip", "-r", zipFilePath, ".")
		  processBuilder.directory(new File(dirPath))
		  Process p = processBuilder.start()
		  int rVal = p.waitFor();
		  
		  if (rVal != 0) {
			BufferedReader stderrReader = new BufferedReader(new InputStreamReader("// TODO"));
			String errorMessage = stderrReader.readLine();
			log.warn("zip() - Error creating zip $zipFilePath under directory $dirPath:  " + errorMessage);
			throw new IOException("Error creating zip $zipFilePath under directory $dirPath: " + errorMessage);
		  }
	
		} catch(InterruptedException e){
		  log.error("zip() - Interrupted while creating zip $zipFilePath under directory $dirPath", e);
		  throw new IOException("Interrupted while creating zip $zipFilePath under directory $dirPath: " + e);
		}
  }  // end zip()
  
  /**
   * Create a tar file.  All files should be absolute paths, even if
   * you think you know what the CWD is.  Tar is passed the "h" flag 
   * so that if any files are symlinks, the files linked to will be 
   * included, not the symlink itself.
   *
   * @param tarfile The absolue path of the tarfile you want to create.
   * @param subdir The (optional) subdirectory the files live in.
   * @param files A collection of files to include in the tarfile.
   * @throws IOException If there is any issue execing the process or creating the tar.
   * 
   */
  public static void tar(String tarfile, String subdir, files) throws IOException { 
    try { 
      def subdirArgs = subdir ? ["-C", subdir] : []
      def cmdArgs = ["tar", subdirArgs, "-hcf", tarfile, files].flatten();
      Process p = cmdArgs.execute()
      int rVal = p.waitFor();
      
      if (rVal != 0) {
        InputStream stderr = p.getErrorStream();
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
        String errorMessage = stderrReader.readLine();
        log.warn("Error creating tar $tarfile with contents $files:  " + errorMessage);
        throw new IOException(errorMessage);
      }

    } catch(InterruptedException e){
      log.error("Interrupted while creating tar $tarfile with contents $files", e);
    }
  }

    /**
   * untar a tar file.  All files should be absolute paths, even if
   * you think you know what the CWD is.  Tar is passed the "h" flag
   * so that if any files are symlinks, the files linked to will be
   * included, not the symlink itself.
   *
   * @param tarfile The absolue path of the tarfile you want to unpack.
   * @param subdir The (optional) subdirectory to unpack the files live in to.
   * @param files A collection of files to extract from the tarfile.
   * @throws IOException If there is any issue execing the process of extracting the tar.
   *
   */
  public static void untar(String tarfile, String subdir, files) throws IOException {
    try {
      def subdirArgs = subdir ? ["-C", subdir] : []
      def cmdArgs = ["tar", subdirArgs, "-xf", tarfile, files].flatten();
      Process p = cmdArgs.execute()
      int rVal = p.waitFor();

      if (rVal != 0) {
        InputStream stderr = p.getErrorStream();
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
        String errorMessage = stderrReader.readLine();
        log.warn("Error extracting tar $tarfile with contents $files:  " + errorMessage);
        throw new IOException(errorMessage);
      }

    } catch(InterruptedException e){
      log.error("Interrupted while extracting tar $tarfile with contents $files", e);
    }
  }
  
  // Tar method that works on windows and unix
  public static void safeTar(File tarFile, File srcDir, String include = "") {
	  new AntBuilder().tar(destfile: tarFile.absolutePath, basedir: srcDir.absolutePath, includes: include)
  }
  
  // Untar method that works on windows and unix
  public static void safeUntar(File tarFile, File destDir) {
	  new AntBuilder().untar(src: tarFile.absolutePath, dest: destDir)
  }

	public static Date calculateDistributionTime(String fileUri, int transferRateInKbps, Date scheduleItemStartDate) {

        log.debug("calculateDistributionTime - File: " + fileUri + " ; schedule item start date: "+ scheduleItemStartDate);

        if(fileUri == null)
            return scheduleItemStartDate;

        File contentFile = new File(fileUri);

        if (!contentFile.exists()) {
            log.info("calculateDistributionTime - File " + fileUri + " doesn't exist");
            return scheduleItemStartDate;
        }

        log.debug("Content size: " + contentFile.length());

        //Take content size in bytes and convert it into Kb
        long contentSizeInKb = contentFile.length()/128;

        //Assume average rate of transfer of 33% of target rate and add a buffer of 2 minutes
        long transferTimeInSec = (contentSizeInKb / (transferRateInKbps > 0 ? transferRateInKbps : 1000)) + 120;

        Date distributionTime = DateUtils.addSeconds(scheduleItemStartDate, -1 * (int)transferTimeInSec);

        log.info("calculateDistributionTime - File: " + fileUri + "; schedule item start date: " + scheduleItemStartDate + " - dist time: " + distributionTime);

        return distributionTime;
    }
	
    /**
     * Takes in a file name (*.* ) or string and returns a suitable directory name based on the value.
     *
     * @param filename the value to create the directory name from
     * @return a directory name from the string
     */
  public static String cleanFilenameForDirectoryName(String filename) {
		String filenameFront = "";

		int indexOfDot = filename.lastIndexOf('.');
		if (indexOfDot < 0) {
			filenameFront = filename;
		} else {
			filenameFront = filename.substring(0, indexOfDot-1);
		}

		// Strip invalid characters
		return cleanStringForDirectoryName(filenameFront)
	}



/**
     * Takes in a file name (*.* ) or string and returns a suitable directory name based on the value.
     *
     * @param filename the value to create the directory name from
     * @return a directory name from the string
     */
  public static String cleanStringForDirectoryName(String filename) {

		String cleanedFileName = "";
		// Strip invalid characters
		cleanedFileName = ValidateUtil.makeValidString(filename, MetadataConstants.VALID_CHAR_FILE_NAME);
		if (cleanedFileName.length() == 0) {
			cleanedFileName = "DefaultDirectoryName";
    		log.warn("Directory name must exist (currently 0 characters). Changing to " + cleanedFileName);
    	}

		log.info("Returning cleaned directory name " + cleanedFileName);
		return cleanedFileName;
	}


  //Returns the string after the first dot in the file name or empty string if there is ..
  public static String getExtension(String fileUri) {
     File file = new File(fileUri);
     return file.getName().indexOf('.') < 0 ? "" :  file.getName().substring(file.getName().indexOf('.'));
  }

  /**
   * Method to return just the filename without extension.
   * @param filename
   * @return
   */
  public static String getFileNameWithOutExtension(String filename) {
	  return FilenameUtils.removeExtension(filename);
  }


	/*
	*
	* ZIP/UNZIP 64 Bit Support Functions (Using Apache Common Compress Library)
	*    : Needed to zip and unzip files greater than ~4GB (32bit limit)
	* */

	/**
	 * Creates a zip file at the specified path with the contents of the specified directory.
	 * NB:
	 *
	 * @param directoryPath The path of the directory where the archive will be created. eg. c:/temp
	 * @param zipPath The full path of the archive to create. eg. c:/temp/archive.zip
	 * @throws IOException If anything goes wrong
	 */
	public static def zip64(String directoryPath, String zipPath){

		FileOutputStream fOut = null;
		BufferedOutputStream bOut = null;
		ZipArchiveOutputStream tOut = null;

		try {
			fOut = new FileOutputStream(new File(zipPath));
			bOut = new BufferedOutputStream(fOut);
			tOut = new ZipArchiveOutputStream(bOut);
			addFileToZip(tOut, directoryPath, "");

		} catch (Exception e){
			log.error("Unable to zip the directory [${directoryPath}] to archive [${zipPath}]. Exception=" + e
					.printStackTrace());
			return false;
		}
		finally {
			tOut.finish();
			tOut.close();
			bOut.close();
			fOut.close();
		}

		return true;
	}


	/**
	 * Creates a zip entry for the path specified with a name built from the base passed in and the file/directory
	 * name. If the path is a directory, a recursive call is made such that the full directory is added to the zip.
	 *
	 * @param zOut The zip file's output stream
	 * @param path The filesystem path of the file/directory being added
	 * @param base The base prefix to for the name of the zip file entry
	 *
	 * @throws IOException If anything goes wrong
	 */
	private static def addFileToZip(ZipArchiveOutputStream zOut, String path, String base) throws IOException {

		File f = new File(path);
		String entryName = base + f.getName();
		ZipArchiveEntry zipEntry = new ZipArchiveEntry(f, entryName);

		zOut.putArchiveEntry(zipEntry);

		if (!f.isDirectory()) {
			FileInputStream fInputStream = null;
			try {
				fInputStream = new FileInputStream(f);
				IOUtils.copy(fInputStream, zOut);
				zOut.closeArchiveEntry();
			} finally {
				//IOUtils.closeQuietly(fInputStream);
				fInputStream.close();
			}
		} else {
			zOut.closeArchiveEntry();
			File[] children = f.listFiles();

			if (children != null) {
				for (File child : children) {
					addFileToZip(zOut, child.getAbsolutePath(), entryName + "/");
				}
			}
		}
	}

	/**
	 * Extract zip file at the specified destination path.
	 * NB:archive must consist of a single root folder containing everything else
	 *
	 * @param archivePath 		path to zip file
	 * @param destinationPath 	path to extract zip file to (if the path/directory exists,
	 * it will be completely overwritten)
	 *
	 */
	public static def unzip64(String archivePath, String destinationPath) {
		File archiveFile = new File(archivePath);
		File unzipDestFolder = null;



		File dir = new File(destinationPath);
		log.info " Unzipping file ${archivePath} to ${dir.getAbsolutePath()}"


		try {

			if (dir.exists()) {
				if (dir.isDirectory()) {
					//Delete any old remnants...This should not happen.
					dir.deleteDir();
				} else {
					dir.delete();
				}
			}

			org.apache.commons.io.FileUtils.forceMkdir(dir);

			log.trace("Created directory ${dir.getAbsolutePath()}");
		} catch (IOException e) {
			log.error("Unable to create new directory ${dir.getAbsolutePath()} to store unzipped files.");
			throw e;
		}

		try {
			unzipDestFolder = new File(dir.getAbsolutePath());
			String[] zipRootFolder = new String[2];
			boolean retBool = unzipFolder(archiveFile, archiveFile.length(), unzipDestFolder, zipRootFolder);
			if(!retBool){
				log.error("Unable to unzip ${archivePath}");
				return retBool;
			}

			return retBool;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	/**
	 * Unzips a zip file into the given destination directory.
	 *
	 * The archive file MUST have a unique "root" folder. This root folder is
	 * skipped when unarchiving.
	 *
	 * @return true if folder is unzipped correctly.
	 */
	//@SuppressWarnings("unchecked")
	private static boolean unzipFolder(File archiveFile,
								long compressedSize,
								File zipDestinationFolder,
								String[] outputZipRootFolder) {

		if(!zipDestinationFolder.exists()){
			log.error("ERRROR: [${zipDestinationFolder}] does not exist...");
			return false;
		}
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(archiveFile);
			byte[] buf = new byte[65536];

			Enumeration entries = zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry zipEntry = entries.nextElement();
				String name = zipEntry.getName();
				name = name.replace('\\', '/');
				int i = name.indexOf('/');
				if (i > 0) {
					outputZipRootFolder[0] = name.substring(0, i);
				}
				name = name.substring(i + 1);

				if(name.isEmpty() || name ==""){
					continue;
				}

				File destinationFile = new File(zipDestinationFolder, name);
				if (name.endsWith("/")) {
					if (!destinationFile.isDirectory() && !destinationFile.mkdirs()) {
						log.error("Error creating temp directory:" + destinationFile.getPath());
						return false;
					}
					continue;
				} else if (name.indexOf('/') != -1) {
					// Create the the parent directory if it doesn't exist
					File parentFolder = destinationFile.getParentFile();
					if (!parentFolder.isDirectory()) {
						if (!parentFolder.mkdirs()) {
							log.error("Error creating temp directory:" + parentFolder.getPath());
							return false;
						}
					}
				}

				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(destinationFile);
					int n;
					InputStream entryContent = zipFile.getInputStream(zipEntry);
					while ((n = entryContent.read(buf)) != -1) {
						if (n > 0) {
							fos.write(buf, 0, n);
						}
					}
				} finally {
					if (fos != null) {
						fos.close();
					}
				}
			}
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					log.error("Error closing zip file.");
				}
			}
			return true;

		} catch (IOException e) {
			log.error("Unzip failed:" + e.getMessage());
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					log.error("Error closing zip file.");
				}
			}
		}

		return false;
	}

}
