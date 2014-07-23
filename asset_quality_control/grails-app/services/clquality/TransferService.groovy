package clquality

import clsetting.Setting
import settings.SettingNames
import states.AssetStates
import states.TransferManagerStates

import com.clearleap.core.util.UpdateUtil
import com.clearleap.core.util.DateUtils
import com.clearleap.core.distribution.SFTPDistributor

class TransferService {

    boolean transactional = false
	
	def grailsApplication
	
	MailService mailService
	
    /**
     * See the following wiki for how this works:
     * http://wiki.clearleap.com:8080/confluence/display/ENG/Manual+QC+server+sync+app
     */
	public void processAssets() {
		log.info "processAssets: Starting..."
		
		// Check basics on if we should even try to transfer
		if (!shouldAttemptTransfers(grailsApplication?.config?.qc?.id)) {
			log.info "processAssets: Stopping now."
			return
		}
		
		// Determine if there's an asset staged by this instance ready to be transferred next
		def transferAsset = getTransferAsset()
		if (!transferAsset) {
			log.info "processAssets: Stopping now."
			return
		}
		
		// Try to get an available thread on the cluster/instance to do the transfer. If successful, will
		//  increment thread counts appropriately and update asset state so we can move onto
		//  next step of actually doing the transfer.
		if (!acquireTransferThread(transferAsset)) {
			log.info "processAssets: Stopping now."
			return
		}
		
		// Actually do the SFTP transfer
		// Don't care about results since this method will change state to error if there are issues
		//  and we still need to do next step if this fails
        def taskStartTime = System.currentTimeMillis()
		doSftpTransfer(transferAsset)
        def metricInSecs = (System.currentTimeMillis() - taskStartTime) * 1000
        def metricInMinutes = metricInSecs/60
        log.info "METRIC $metricInSecs seconds ($metricInMinutes minutes) to SYNC asset ${transferAsset.centralId} "
        
		// Set asset state based on results of SFTP transfer and decrement thread counts
		completeTransfer(transferAsset)
		log.info "processAssets: Asset processing for asset ${transferAsset.id} finished for this run."
	}


    /**
     * Does initial checks to determine if we should consider doing any transfers
     */
	private boolean shouldAttemptTransfers(String processor) {
		log.debug "shouldAttemptTransfers: Checking if processor ${processor} should attempt transfers"
		
		def allTransferManager = TransferManager.ALL
		if (allTransferManager.state == TransferManagerStates.OFF) {
			log.info "shouldAttemptTransfers: ALL transfer manager is turned off. No attempts can be made."
			return false
		}
		if (allTransferManager.activeThreadCount >= allTransferManager.maxThreadCount) {
			log.info "shouldAttemptTransfers: ALL transfer manager active thread count of " +
				"${allTransferManager.activeThreadCount} is >= max thread count of " +
				"${allTransferManager.maxThreadCount}. No attempts can be made."
			return false			
		}
		
		def processorTransferManager = TransferManager.findByProcessor(processor)
		if (processorTransferManager.state == TransferManagerStates.OFF) {
			log.info "shouldAttemptTransfers: Processor transfer manager is turned off. No attempts can be made."
			return false
		}
		if (processorTransferManager.activeThreadCount >= processorTransferManager.maxThreadCount) {
			log.info "shouldAttemptTransfers: Processor transfer manager active thread count of " +
				"${processorTransferManager.activeThreadCount} is >= max thread count of " +
				"${processorTransferManager.maxThreadCount}. No attempts can be made."
			return false			
		}
		
		log.info "shouldAttemptTransfers: There's room for transfers for processor ${processor}."
		return true
	}


    /**
     * Determines if there is an asset that has been staged by this instance that is next in
     * line to be transferred

     * @return the next Asset that is available for a transfer
     */
	private Asset getTransferAsset() {
		def assetTransferList = Asset.findAllByAssetState(AssetStates.STAGED, [sort: "createDate", order: "asc"])
		log.debug "getTransferAsset: Checking ${assetTransferList.size()} staged assets for one to transfer."
		
		def processor = grailsApplication?.config?.qc?.id
		def assetToUse = null
		def blockedProcessors = []
		for (asset in assetTransferList) {		
			// if we own asset at the top, move on	
			if (asset.processor == processor) {
				assetToUse = asset
				break
			}
			
			// Determine if processor this asset belongs to is blocked and we can look at next asset in queue
			if (blockedProcessors.contains(asset.processor) || !shouldAttemptTransfers(asset.processor)) {
				log.info "getTransferAsset: Processor for asset ${asset.id} is blocked and there are more cluster threads available. " +
					"Going to look at next asset in transfer list."
				blockedProcessors << asset.processor // simplify things since it doesn't matter if same processor is in list twice
				continue
			}
					
			// See if we should skip this asset base on stage date skip threshold
			def skipThresholdMins = Setting.getSetting(SettingNames.STAGE_DATE_SKIP_THRESHOLD_MINS)
			if (!asset.stagedDate || DateUtils.addMinutes(asset.stagedDate, skipThresholdMins) >= new Date()) {
				log.info "getTransferAsset: Asset ${asset.id} has not be staged long enough to skip. " +
					"Will not transfer any assets."
				break
			}
			
			log.info "getTransferAsset: Asset ${asset.id} has been staged for too long and can be skipped. " +
				"Going to look at next asset in transfer list."
		}
		
		if (assetToUse) {
			log.info "getTransferAsset: Found ${assetToUse.id} that is next up in the transfer list for me to process."
		}
		else {		
			log.info "getTransferAsset: There are no assets in transfer list that I can transfer. Will not transfer any assets."
		}
		
		return assetToUse
	}
	

    /**
     * Try to get an available thread on the cluster/instance to do the transfer. If successful, will
     * increment thread counts appropriately and update asset state.
     *
     * @param asset
     * @return
     */
	private boolean acquireTransferThread(Asset asset) {
		log.info "acquireTransferThread: Attempting to acquire transfer thread for asset ${asset.id}"
		
		def assetSetToSynching = UpdateUtil.safeUpdateObject(asset) {
			if (asset.assetState == AssetStates.STAGED) {
				asset.assetState = AssetStates.SYNCHING
				return true
			}
			
			return false
		}

		if (!assetSetToSynching) {
			log.info "acquireTransferThread: Failed to set state to SYNCHING. Will not transfer asset."
			return false
		}
		
		def currTransferManager = TransferManager.CURRENT
		def allTransferManager = TransferManager.ALL
		def threadCountsIncremented = TransferManager.withTransaction {
			currTransferManager.lock()
			currTransferManager.refresh()
			
			if (currTransferManager.activeThreadCount >= currTransferManager.maxThreadCount) {
				log.info "acquireTransferThread: Current transfer manager active thread count of " +
					"${currTransferManager.activeThreadCount} is >= max thread count of " +
					"${currTransferManager.maxThreadCount}. Will not transfer asset."
				return false
			}
			
			allTransferManager.lock()
			allTransferManager.refresh()
			
			if (allTransferManager.activeThreadCount >= allTransferManager.maxThreadCount) {
				log.info "acquireTransferThread: ALL transfer manager active thread count of " +
					"${allTransferManager.activeThreadCount} is >= max thread count of " +
					"${allTransferManager.maxThreadCount}. Will not transfer asset."
				return false
			}
			
			currTransferManager.activeThreadCount++
			allTransferManager.activeThreadCount++
			
			currTransferManager.save(flush: true)
			allTransferManager.save(flush: true)
			return true
		}

		if (!threadCountsIncremented) {
			log.info "acquireTransferThread: Failed to increment transfer threads. Will not transfer asset."
			// reset the asset to STAGED state
			UpdateUtil.safeUpdateObject(asset) {
				asset.assetState = AssetStates.STAGED
			}
			return false
		}
		
		log.info "acquireTransferThread: Successfully incremented threads and set asset to SYNCHING."
		return true
	}
	

    /**
     * Decrement thread counters
     *
     * @param asset
     */
	private void completeTransfer(Asset asset) {
		log.info "completeTransfer: Completing transfer for asset ${asset.id}"

		def currTransferManager = TransferManager.CURRENT
		def allTransferManager = TransferManager.ALL
		TransferManager.withTransaction {
			currTransferManager.lock()
			currTransferManager.refresh()
			
			allTransferManager.lock()
			allTransferManager.refresh()
			
			currTransferManager.activeThreadCount = currTransferManager.activeThreadCount > 0 ? currTransferManager.activeThreadCount - 1 : 0
			allTransferManager.activeThreadCount = allTransferManager.activeThreadCount > 0 ? allTransferManager.activeThreadCount - 1 : 0
			
			currTransferManager.save(flush: true)
			allTransferManager.save(flush: true)
		}
		
		log.info "completeTransfer: Successfully decremented threads and completed transfer."
	}
	

    /**
     * Given an asset, recursively copies its staging directory to SFTP directory. On any exceptions set the state to
     * SYNCH_ERROR. On successful transfers mark the asset as SYNCED.
     *
     * @param asset
     * @return
     */
	private boolean doSftpTransfer(Asset asset) {
		log.info "doSftpTransfer: Transferring staging directory ${asset.stagedDirectoryName} via SFTP."
		
		log.info "doSftpTransfer: Setting asset transfer start date to now."
		UpdateUtil.safeUpdateObject(asset) {
			asset.transferStartDate = new Date()
		}
		
		def serverUrl = Setting.getSetting(SettingNames.SFTP_SERVER_URL)
		def serverPort = Setting.getSetting(SettingNames.SFTP_PORT)
		def serverUser = Setting.getSetting(SettingNames.SFTP_USER)
		def serverPass = Setting.getSetting(SettingNames.SFTP_PASSWORD)
		def serverDropDir = Setting.getSetting(SettingNames.SFTP_DROP_DIR_PATH)
			
		def success = true
        SFTPDistributor distributor
        def taskStartTime = System.currentTimeMillis()
		try {
            distributor = new SFTPDistributor()
			// Set up the the distributor
			distributor.initializeFTPConnection(serverUrl, serverPort,  serverUser,  serverPass)
            // Loop through all files in the directory recursively and send them
			recursiveSendStaging(serverUrl, serverPort, serverUser, serverPass, asset, serverDropDir, distributor)

            def metricInSecs = (System.currentTimeMillis() - taskStartTime) / 1000
            def metricInMinutes = metricInSecs / 60
            log.info "METRIC $metricInSecs seconds ($metricInMinutes minutes) to SFTP files for asset ${asset.centralId} to server."

		} catch (Throwable e) {
            log.error "doSftpTransfer: Exception '${e.message}' sending via SFTP. Setting asset to SYNCH_ERROR:\n", e
            success = false
		} finally {
			try {
				distributor.closeFTPConnection()
			}
			catch (Throwable ex) {
				log.error "doSftpTransfer: Tried to close SFTP connection but got exception:\n", ex
			}
		}
		
		if (success) {
			log.info "doSftpTransfer: Asset staging sent via SFTP."
            UpdateUtil.safeUpdateObject(asset) {
                asset.assetState = AssetStates.SYNCHED
            }
        } else {
			log.info "doSftpTransfer: Error sending via SFTP. Asset set to SYNCH_ERROR."
            UpdateUtil.safeUpdateObject(asset) {
                asset.assetState = AssetStates.SYNCH_ERROR
            }
        }
	}


    /**
     * Given an asset, create all necessary staging dir paths and recursively transfer the files in the asset's dir
     * preserving dir structure.
     *
     * @param serverUrl
     * @param serverPort
     * @param serverUser
     * @param serverPass
     * @param asset
     * @param serverDropDir
     * @param distributor
     * @throws Exception
     */
	private recursiveSendStaging(String serverUrl, Integer serverPort, String serverUser, String serverPass,
		Asset asset, String serverDropDir, SFTPDistributor distributor) throws Exception {

        def assetsDropDir = serverDropDir + File.separator + asset.stagedDirectoryName
        log.info "Initial creation of path $assetsDropDir"
        distributor.createDirectoryPath(assetsDropDir)

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName

        recursiveSendStagingHelper(serverUrl, serverPort, serverUser, serverPass,
                new File(assetBaseDir), assetsDropDir, distributor)
	}


    /**
     * Helper method that recursively iterates through directories and places files where they should be or creates
     * sub directories in the staged directory. Maintains dir structure.
     *
     * @param serverUrl
     * @param serverPort
     * @param serverUser
     * @param serverPass
     * @param currentDirectory
     * @param serverDropDir
     * @param distributor
     * @throws Exception
     */
    private recursiveSendStagingHelper(String serverUrl, Integer serverPort, String serverUser, String serverPass,
                                               File currentDirectory, String serverDropDir, SFTPDistributor distributor) throws Exception {

        currentDirectory.eachFile { currFile ->

            if (!currFile.isDirectory()) {
                log.info "Sending file $currFile to destination $serverDropDir"
                def distSuccess = distributor.send(serverUrl, serverPort, serverUser, serverPass, currFile.toURI().toString(), serverDropDir, currFile.name)
                if (distSuccess == false) {
                    def message = "Unsuccessful file transfer sending file $currFile to SFTP server $serverUrl"
                    log.error message
                    throw new Exception(message)
                }
            } else {
                def subDirectoryPath = serverDropDir + File.separator + currFile.name
                log.info "Creating directory ${subDirectoryPath}"
                distributor.createDirectoryPath(subDirectoryPath)
                recursiveSendStagingHelper(serverUrl, serverPort, serverUser, serverPass, currFile, subDirectoryPath, distributor)
            }
        }
    }


    /**
     * Looks for a TransferManager with name 'ALL' and creates one if none exist
     * Looks for a TransferManager with matching processor and creates one if none exist
     */
	public void handleTransferManagerSetUp() {
		// Create ALL Transfer Manager if not exists
		if (!TransferManager.ALL) {
			log.info "handleTransferManagerSetUp: ALL Transfer Manager does not exist. Creating now."
			TransferManager.withTransaction {
				def tmAll = new TransferManager(processor: "ALL", activeThreadCount: 0, maxThreadCount: 1, 
					state: TransferManagerStates.ON)
				tmAll.save(flush: true)
			}
		}
		
		// Create matching Transfer Manager if none exists
		if (!TransferManager.CURRENT) {
			def processor = grailsApplication?.config?.qc?.id
			if (processor) {
				log.info "handleTransferManagerSetUp: CURRENT Transfer Manager does not exist. Creating now for processor."
				TransferManager.withTransaction {
					def tmCurr = new TransferManager(processor: processor, activeThreadCount: 0, maxThreadCount: 1, 
						state: TransferManagerStates.ON)
					tmCurr.save(flush: true)
				}
			}
			else {
				log.info "handleTransferManagerSetUp: Processor from config is null. Will not create a transfer manager."
			}
		}
	}
}