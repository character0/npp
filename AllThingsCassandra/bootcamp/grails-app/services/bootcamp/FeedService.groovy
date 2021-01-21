package bootcamp

import com.sun.syndication.feed.module.mediarss.MediaModule
import com.sun.syndication.feed.module.mediarss.MediaEntryModuleImpl
import com.sun.syndication.feed.module.mediarss.types.Reference
import com.sun.syndication.feed.module.mediarss.types.UrlReference
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader
import groovy.constants.IngestSourceType
import groovy.constants.SettingNames
import groovy.state.IngestSourceState

import grails.transaction.Transactional

@Transactional
class FeedService {

	static transactional = false;

	def settingService
	def ingestService
	def grailsApplication

    /**
     * Method that is invoked when the feed job is invoked.
     */
    def checkFeeds() {
    	def feeds = Feed.findAllByStateAndNextPollLessThan(IngestSourceState.ON, new Date())

    	log.info "Found ${feeds.size()} feed(s) to process."
    	for (Feed feed in feeds) {
    		if(safeUpdateFeedState(feed, IngestSourceState.ON, IngestSourceState.PROCESSING)){
                processFeed(feed)
            }
        }
    }


    /**
     * Provides a safe method to update the state of a Feed object
     *
     * @param feed the feed to perform the state update on
     * @param oldState the old or current state of the feed
     * @param newState the new state to change the feed to
     */
    private boolean safeUpdateFeedState(Feed feed, IngestSourceState oldState, IngestSourceState newState) {
      boolean retval = false;

  		retval = safeUpdateFeed(feed) {
  			if(it.state == oldState){
  				log.info "Setting feed ${it.id} state to ${newState}."
  				it.state = newState;
				  it.currentProcessor = grailsApplication.config.central.id
  				retval = true;
  			} else {
  				log.info "Another thread updated the state of feed ${feed.id} to ${feed.state}; returning false."
  				retval = false;
  			}
  			return retval;
  		}

      return retval;
    }


    /**
     * Helper method that takes in a feed and a closure and performs the closure
     * on the feed in a safe, pessimistically locked transaction.
     *
     * @param feed the feed to perform the operation on
     * @param safeOperation the closure to perform onto the feed
     */
	private boolean safeUpdateFeed(Feed feed, Closure safeOperation) {
    	boolean retval = false

		IngestSource.withTransaction {
			feed.lock();
			feed.refresh();
			if(safeOperation(feed)){
				feed.save(flush:true);
				retval = true;
			} else {
				retval = false;
			}
		}

    	return retval
	}

    /**
     * Tries to get a SyndFeed object from an RSS feed.
     * If there are any problems getting to the feed or parsing it, we will:
     *  - Retry if we have not hit the max number of retries
     *  - Return null if we have hit the max number of retries
     *
     *  @param feed
     */
    private SyndFeed parseRss(Feed feed) {
    	// There are two limits while retrying polling of feeds that fail:
    	//  retryLimitPerPoll and maxAllowableFailedPolls
    	// Try a couple of times each time FeedJob runs (retryLimitPerPoll) ... and try for a couple of
    	//  runs of FeedJob before sending the feed into ERROR state (maxAllowableFailedPolls)
    	// So if FeedJob runs every hour,  retryLimitPerPoll is 5 and maxAllowableFailedPolls is 4: then
    	//  when FeedJob runs it will try 5 times before giving up.
    	// It will try every hour for the next 4 hours before sending the feed into the ERROR state.
    	def retryLimitPerPoll = settingService.getIntegerSystemSetting(SettingNames.MAX_RETRIES_PER_POLL, 5)
    	def maxAllowableFailedPolls = settingService.getIntegerSystemSetting(SettingNames.MAX_FAILED_POLLS_BEFORE_ERROR_STATE, 4)
    	def retrySleepIntervalInMinutes = settingService.getIntegerSystemSetting(SettingNames.RETRY_SLEEP_INTERVAL_IN_MINUTES, 1)
    	def retriesThisPoll = 0
    	def successfullyParsed = false
    	def cumulativeErrorMessage = ""
    	// def ipAddress = java.net.InetAddress.localHost.hostAddress

  	    SyndFeedInput sfi
    	SyndFeed sf

    	// If max retries is set to 0 and retry limit is 0, need to run through because this is the first run
    	// All other checks must pass the test of retries < max retries
    	while (!successfullyParsed &&
    			((retriesThisPoll == 0 && retryLimitPerPoll == 0) || retriesThisPoll < retryLimitPerPoll)) {
	    	log.info "Processing feed '${feed.name}' url '${feed.location}': Attempt ${retriesThisPoll + 1}. " +
	    		"Feed retry count: ${feed.retries }"

	    	try {
	    		InputStream feedStream = getTimeoutUrlInputStream(feed.location)

		  	    // https://rome.dev.java.net/apidocs/1_0/overview-summary.html
			    sfi = new SyndFeedInput();
				sf = sfi.build(new XmlReader(feedStream));

		  	    // This dumps the parsed structure to standard out. Very helpful in debugging.
		  	    log.debug(sf);

				successfullyParsed = true
	    	}
    		catch (Throwable e) {
	    		log.error("Caught exception while processing feed.", e)
	    		cumulativeErrorMessage += e.message + "\n"
	    		retriesThisPoll++
	    		//Send a transient error alert
	    		def errorMsg = "Feed '${feed.name}' had a transient error. We've tried ${retriesThisPoll} " +
	    			"time(s) this poll. The error message was: \n${e.message}"

	    		if (retriesThisPoll < retryLimitPerPoll)  {
	    			log.warn "Going to sleep for ${retrySleepIntervalInMinutes} minute/s. Hopefully " +
	    				"the error in feed '${feed.name}' will have been fixed when we attempt a retry."
	    			sleep(retrySleepIntervalInMinutes * 60000)
	    		}
	    	}
    	}//End of while

    	if (!successfullyParsed) {
    		//Update failure count and save the feed
    		log.info "Updating retry count to ${feed.retries + 1} for feed ${feed.name}"
    		safeUpdateFeed(feed) {it.retries++}

    		if(feed.retries < maxAllowableFailedPolls) {
        		//Set it back to ON state so that it can run again the next time the Job runs.
        		safeUpdateFeedState(feed, IngestSourceState.PROCESSING, IngestSourceState.ON);
        		log.warn "Feed '${feed.name}' has tried ${retryLimitPerPoll} times before giving up. " +
        				"The error messages were: \n${cumulativeErrorMessage}"
    		}
    		else {
    			//We've hit the limit. Shut down the feed by setting it to error state and send an alert
    			log.error "Inactivating feed '${feed.name}' after having reached the max error limit " +
    					"${maxAllowableFailedPolls}. Failures were due to errors: \n${cumulativeErrorMessage}"
    			deactivateFeed(feed, cumulativeErrorMessage)
    		}

    		return null
    	}

		//Successful. So reset retry count to 0
		if (feed.retries != 0){
			log.info "Feed '${feed.name}' was successfully processed. Resetting retry count"
			safeUpdateFeed(feed) {it.retries = 0; return true}
		}

		return sf
    }


    /**
     * Takes in a feed object
     *
     * @param feed the Feed to process
     */
    private void processFeed(feed) {
    	SyndFeed sf = parseRss(feed)
    	if (!sf) {
    		log.warn "We were not able to parse the feed. No entries will be checked for assets."
    		return
    	}

    	try {
	        // Channel level info
	        log.info "Retrieved feed - title '${sf.title}' author '${sf.author}' type '${sf.feedType}'."
			// In the event there are no entries, set last polled to now
			if (feed.state == IngestSourceState.PROCESSING) {
				log.info "Feed is PROCESSING. Setting last polled to now."
				safeUpdateFeed(feed) {it.lastPolled = new Date()}
			}
	        // Loop through channel items looking for any not already harvested
	        for (int i = 0; i < sf?.entries?.size(); i++) {
	        	// Need to refresh the feed in order to get its current state
	        	def feedState = IngestSourceState.PROCESSING
	        	try {
		        	feed.withTransaction {
		        		feed.lock()
		        		feed.refresh()
		        		feedState = feed.state
		        	}
	        	}
	        	catch (Exception e) {
	        		// Log and set state to processing to continue
	        		log.warn("Tried to lock and refresh feed to get its current state, but failed:", e)
	        		feedState = IngestSourceState.PROCESSING
	        	}

	        	// If feed's state is not PROCESSING, stop processing it
	        	if (feedState != IngestSourceState.PROCESSING) {
	        		log.info("Feed's state was changed to ${feedState} before processing.  Stopping processing.")
	        		return
	        	}

	        	// TODO - Negative itemsToIngest value means ingest all items.
	        	if (feed.itemsToIngest > -1 && i >= feed.itemsToIngest) {
	        		log.info("Configured to ingest only first ${feed.itemsToIngest} feed items.")
	        		break
	        	}

	        	def entry = sf?.entries[i]

	        	// Does this entry have a URL
	       		String fileUrl = getFileUrl(entry)
	        	if (!fileUrl) {
	        		log.warn "No URL in feed entry '${entry.title}';"
	        		//continue
	        	}

		        String location = feed.location
		        // If the entry has a guid (entry.uri), use it as the unique identifier of a
	        	// feed entry.  If it doesn't, use the enclosure url.
	        	String uri = entry.uri ? entry.uri : fileUrl

	        	// Try to find an already created asset matching this entry
	    		String filename = UrlUtil.getUrlFilename(fileUrl)
	    		log.info "Found file URL '${fileUrl}' and file name '${filename}' within the enclosure."


                Date publishDate = entry.publishedDate
                String mappedName = getMappedName(entry)
                String title = mappedName ?: filename
                // String assetTitle = getMappedTitle(entry)
                // String assetShortTitle = getMappedShortTitle(entry)
                String description = getMappedDescription(entry)

                log.info "Parsed out values publish date: ${publishDate}" +
                        " name: ${mappedName}" +
                        " title: ${title}" +
                        " description: ${description}"

	    		/*def pullContent = settingService.getBooleanSetting(feed.user.account,
	    				SettingNames.DEV_FEED_PULL_CONTENT, true)
	    		if (!pullContent) {
	    			log.warn "DEV_FEED_PULL_CONTENT is false; not pulling content for RSS item; " +
	    				"not creating content objects for asset. Moving on to next entry."
	    			continue
	    		}*/

	       		// Attempt to get the file for the item. Will get null if there's a problem
	       		// log.info "Attempting to get the file for the item."
	       		// File enclosureFile = getFile(filename, fileUrl, feed)
	        }

	        log.info "Finished parsing entries."

	        safeUpdateFeed(feed) {
	            long nextPollTime = new Date().getTime() + feed.pollFrequencyInMinutes * 1000
	            feed.nextPoll = new Date(nextPollTime)
	            return true
	        }
			log.info("Next poll of feed '${feed.name}' is '${feed.nextPoll}'.")

	        safeUpdateFeedState(feed, IngestSourceState.PROCESSING, IngestSourceState.ON)
    	}
        catch (Exception e) {
        	def errorMsg = "Unknown exception occurred while processing feed. Exceptions should not " +
        		"occur here so deactivating feed:\n${e.message}"
        	log.error(errorMsg, e)
        	deactivateFeed(feed, errorMsg)
        }
    }


    /**
     * Sets a feed's state to error and sends an Ingest Source Down event
     *
     * @param feed
     * @param errorMsg
     */
    private void deactivateFeed(Feed feed, String errorMsg) {
    	def ipAddress = java.net.InetAddress.localHost.hostAddress

      safeUpdateFeedState(feed, feed.state, IngestSourceState.ERROR);

    }


    /**
     * Given an entry, finds the URL of the file it references
     */
    public String getFileUrl(entry) {
    	if (!entry) return null

    	// First try to find the file url from <media:content>
		MediaModule mediaModule
		MediaEntryModuleImpl media
	   	try {
			mediaModule = entry.getModule(MediaModule.URI)
	    	media = (MediaEntryModuleImpl) mediaModule

		 	// If there is at least 1 media content objects
	    	if (media?.mediaContents) {
	    		// Get the reference object from the first media content
	    		Reference ref = media.mediaContents[0].reference
	    		// If the reference is of type UrlReference, then get URL from it
	    		// URL is instance of java.net.URI so need to get string from it
	    		if (ref instanceof UrlReference && ref?.url?.toString()) {
	    			return ref?.url?.toString()
	    		}
	    	}

            if (media?.mediaGroups) {
                // Get the reference object from the first media group and first content in the media group
                Reference ref = media.mediaGroups[0].contents[0].reference
                // If the reference is of type UrlReference, then get URL from it
                // URL is instance of java.net.URI so need to get string from it
                if (ref instanceof UrlReference && ref?.url?.toString()) {
                    return ref?.url?.toString()
                }

            }
	   	}
	 	catch (Exception e) {
	 		// Fall through to next file url check
	 	}

    	// If there is no <media:content>, fall back to enclosure
    	// Check that there is at least 1 enclosure
    	if (entry.enclosures) {
    		// Get the first enclosure
    		def enclosure = entry.enclosures[0]
    		if (enclosure?.url) {
    			return enclosure.url
    		}
    	}

		return null
    }

    /**
     * Helper method that pulls a file from a URL and adds it to a scratch directory.
     * It outputs the stream to the file in the scratch directory.
     *
     * If URL can be reached, returns file obtained; else returns null
     *
     * @param filename
     * @param fileUrl
     * @param asset
     */
    private File getFile(String filename, String fileUrl, Feed feed) {
    	File tempDir = ingestService.safeCreateTempDir()
       	File contentAtTempLocation = new File(tempDir.absolutePath + "/" + filename)

			String urlFileParent = UrlUtil.getUrlFileParent(fileUrl)
			String urlFilename = UrlUtil.getUrlFilename(fileUrl)?.decodeURL()
			// When encoding the filename, encodeAsURL() will replace spaces with '+' which is not desired behavior
			// After encoding, replace all '+' signs with '%20' which is readable by our getter
			String encodedUrlFilename = urlFilename.encodeAsURL().replaceAll('[+]', '%20')
			String urlStr = "${urlFileParent}${encodedUrlFilename}"

			log.info "Pulling content file from '${fileUrl}' [${urlStr}] and pumping to " +
				"${contentAtTempLocation.absolutePath}"

			BufferedOutputStream bos
			try {
	    		InputStream feedStream = getTimeoutUrlInputStream(urlStr)

				bos = new BufferedOutputStream(new FileOutputStream(contentAtTempLocation))
				bos << feedStream
			}
			catch (Exception e) {
				bos?.close()
				deleteFileAndParentDirectory(contentAtTempLocation)
	    		log.warn("Error getting file '${fileUrl}' [${urlStr}].", e)
				return null
			}

		return contentAtTempLocation
    }

    /**
     * Attempts to delete the file and its parent directory from the system
     *
     * @param file
     */
    private void deleteFileAndParentDirectory(File file) {
    	try {
    		log.info "Attempting to delete file ${file}."
			FileUtils.forceDelete(file)

			File parentDir = file.getParentFile()
			log.info "Attempting to delete file's parent directory ${parentDir}."
			FileUtils.forceDelete(parentDir)

			log.info "Successfully deleted file and its parent directory."
    	}
    	catch (Exception e) {
    		log.warn("Error deleting file '${file}' and its directory.", e)
    	}
    }

    /** Given an RSS entry, returns name mapped by item from hierarchy below
     *   if name can be found, else returns ""
     * ---Name mapping hierarchy---
	 *  1) <media:title> (within <media:content>)
	 *  2) <media:title> (within <item>)
	 *  3) <title>
	 */
    public String getMappedName(entry) {
    	if (!entry) return ""

    	// 1-2) <media:title>
		MediaModule mediaModule
		MediaEntryModuleImpl media
	   	try {
			mediaModule = entry.getModule(MediaModule.URI)
	    	media = (MediaEntryModuleImpl) mediaModule

	    	// 1) <media:title> (within <media:content>)
		 	// If there is at least 1 media content objects
	    	if (media?.mediaContents) {
	    		// Get the title from the first media content
	    		def title = media?.mediaContents[0]?.metadata?.title
	    		if (title) {
    				return IngestUtil.cleanAssetName(title)
	    		}
	    	}

	    	// 2) <media:title> (within <item>)
			// If there is a title in the <item>
	    	if (media?.metadata?.title) {
	    		return IngestUtil.cleanAssetName(media.metadata.title)
	    	}
	   	}
	 	catch (Exception e) {
	 		// Fall through to next name check
	 	}

	 	// 3) <title>
	 	if (entry.title) {
	 		return IngestUtil.cleanAssetName(entry.title)
	 	}

	 	return ""
    }


    /** Given an RSS entry, returns description mapped by item from hierarchy below
     *   if description can be found, else returns ""
     * ---Description mapping hierarchy---
	 *  3) <media:description> (within <media:content>)
	 *  4) <media:description> (within <item>)
	 *  5) <description>
	 */
    public String getMappedDescription(entry) {
    	if (!entry) return ""

    	// 3-4) <media:description>
		MediaModule mediaModule
		MediaEntryModuleImpl media
	   	try {
			mediaModule = entry.getModule(MediaModule.URI)
	    	media = (MediaEntryModuleImpl) mediaModule

	    	// 3) <media:description> (within <media:content>)
		 	// If there is at least 1 media content objects
	    	if (media?.mediaContents) {
	    		// Get the description from the first media content
	    		def description = media?.mediaContents[0]?.metadata?.description
	    		if (description) {
    				//return IngestUtil.cleanAssetDescription(description)
                    return description
	    		}
	    	}

	    	// 4) <media:description> (within <item>)
			// If there is a description in the <item>
	    	if (media?.metadata?.description) {
	    		//return IngestUtil.cleanAssetDescription(media.metadata.description)
                return media.metadata.description
	    	}
	   	}
	 	catch (Exception e) {
	 		// Fall through to next name check
	 	}

	 	// 5) <description>
	 	if (entry?.description?.value) {
	 		//return IngestUtil.cleanAssetDescription(entry.description.value)
            return entry.description.value
	 	}

	 	return ""
    }



    /**
     * Helper method that converts a file size in Bytes to MegaBytes.
     *
     * @param fileSize the value to convert
     */
	 private double convertToMB(long fileSize) {
    	double mb = (double)(fileSize*1.0/1024.0/1024.0)
    	// Round to 2 decimal places
    	mb = Math.round(mb * 100.0) / 100.0

		return mb
	 }



	public boolean resetFeed(Feed feed) {
		if (!feed) return false

		def retval = UpdateUtil.safeUpdateObject(feed) {
    		feed.lastPolled = null
			return true
		}

		return retval
	}

	public InputStream getTimeoutUrlInputStream(String urlStr) {
		URL url = new URL(urlStr)
		URLConnection conn = url.openConnection()

		// Setting these timeouts ensures the client does not deadlock indefinitely
		//   when the server has problems.
		int connectTimeoutSecs = settingService.getIntegerSystemSetting(SettingNames.FEED_CONNECT_TIME_OUT_SECS, 30)
		int readTimeoutSecs = settingService.getIntegerSystemSetting(SettingNames.FEED_READ_TIME_OUT_SECS, 120)

		conn.connectTimeout = connectTimeoutSecs * DateUtils.MILLIS_IN_SECOND
		conn.readTimeout = readTimeoutSecs * DateUtils.MILLIS_IN_SECOND

		return conn.inputStream
	}


    public void deleteFeed(Feed f) {
        // TODO
        /*log.info "deleteFeed(): ${f.id}"
        def syndications = Syndication.createCriteria().list {
            ingestSources {
                eq('id', f.id)
            }
        }
        log.debug "Found $syndications.size ingest sources"
        Syndication.withTransaction {
            for (Syndication syndication: syndications) {
                log.info "Removing feed from syndication $syndication.id"
                syndication.removeFromIngestSources(f)
                syndication.save()
            }
        }

        def assetsUsingFeed = Asset.findByIngestSource(f)
        if (assetsUsingFeed) {
        	log.info "deleteFeed(): There are assets using feed so just setting state to DELETED."
        	safeUpdateFeedState(f, f.state, IngestSourceState.DELETED)
        }
        else {
        	log.info "deleteFeed(): No assets using feed so removing feed from system."
            Feed.withTransaction {
                f.delete()
            }
        }*/
    }

    public IngestSource createIngestSource(String name, String location, String description,
                                           String prefix, String suffix, int polling) {

        Feed feed = new Feed(name: name, location: location, state: IngestSourceState.ON, description: description,
                pollFrequencyInMinutes: 3, prefix: prefix, suffix: suffix, polling: polling,
                ingestSourceType: IngestSourceType.FEED, currentProcessor: grailsApplication.config.server.id,
                itemsToIngest: 1)

        IngestSource.withTransaction {
            log.info "Saving ingestSource ${name}"
            if (!feed.save(flush: true)) {
                for (e in feed.errors) {
                    log.warn "Unable to save ingestSource ${name}: ${e}";
                }
                feed = null;
                return;
            }
        }

        return feed
    }


}