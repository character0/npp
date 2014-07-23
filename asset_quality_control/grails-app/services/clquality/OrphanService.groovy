package clquality

import com.clearleap.core.util.FileUtils
import clsetting.Setting
import com.clearleap.core.util.UpdateUtil
import states.AssetStates
import settings.SettingNames

class OrphanService {

    MailService mailService

    def grailsApplication

    static transactional = true

    def findAndFixOrphans() {

        //find all assets without a processor field.
        def assetsWithoutProcessors = Asset.createCriteria().list() {
            eq("processor", "")
            isNull("processor")
        }
        log.info "Fixing ${assetsWithoutProcessors.size()} assets that were have no processor assigned to them."
        for (Asset asset in assetsWithoutProcessors) {
            UpdateUtil.safeUpdateObject(asset) {
                asset.processor = grailsApplication?.config?.qc?.id
            }
        }

        def baseDir = Setting.getSetting(SettingNames.QC_BASE_DIR)
        try {
            def processingAssets = Asset.createCriteria().list() {
                eq('assetState', AssetStates.PROCESSING)
                eq("processor", grailsApplication?.config?.qc?.id)
            }
            log.info "Fixing ${processingAssets.size()} assets that were PROCESSING"
            for (Asset asset in processingAssets) {

                def assetBaseDir = baseDir + '/' + asset.stagedDirectoryName
                log.info "Cleaning up ${asset.assetState} asset ${asset.centralId} and contents in ${assetBaseDir}"
                FileUtils.deleteDirectory(new File(assetBaseDir))

                //If asset has any contents that have been qced
                Content qcContent = asset.contents.find { it.qcDate != null }
                if (qcContent) {
                    log.info "Asset ${asset.centralId}'s content ${qcContent.centralId} has been qced before, can't delete asset."
                    //set asset state to CREATED
                    UpdateUtil.safeUpdateObject(asset) {
                        asset.assetState = AssetStates.CREATED
                    }
                } else {
                    log.info "Asset ${asset.centralId} content's have not been qced; cleaning up."
                    asset.delete()
                }
            }
			
			// Fix assets stuck in SYNCHING state
			def synchingAssets = Asset.createCriteria().list() {
				eq('assetState', AssetStates.SYNCHING)
                eq("processor", grailsApplication?.config?.qc?.id)
			}
            log.info "Fixing ${synchingAssets.size()} assets that were SYNCHING"
			def currTransferManager = TransferManager.CURRENT
			def allTransferManager = TransferManager.ALL
			for (asset in synchingAssets) {
				UpdateUtil.safeUpdateObject(asset) {
					asset.assetState = AssetStates.STAGED
					asset.transferStartDate = null
				}
				// Need to decrement thread counts as well
				// From Rayan: Always decrement the counter if state of an asset is 'synching' on startup. 
				//              If the synch count is already 0, leave it as is. 
				//             With this approach, only danger is one more synch than maximum running. 
				//              It's an acceptable solution because this is a thin race
				UpdateUtil.safeUpdateObject(currTransferManager) {
					currTransferManager.activeThreadCount = currTransferManager.activeThreadCount > 0 ? currTransferManager.activeThreadCount - 1 : 0
				}
				UpdateUtil.safeUpdateObject(allTransferManager) {
					allTransferManager.activeThreadCount = allTransferManager.activeThreadCount > 0 ? allTransferManager.activeThreadCount - 1 : 0
				}
			}
        } catch (IOException ioe) {
            log.error ioe, ioe
            mailService.sendMail("Exception fixing Asset ${asset.centralId}",
                    "IO Exception deleting directory ${asset.stagedDirectoryName} for asset ${asset.centralId}")
        }

        log.info "Finished with assets."
    }

}
