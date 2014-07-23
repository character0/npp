import clsetting.Setting
import settings.SettingNames
import clquality.TransferManager
import clquality.TransferService

class TransferJob {
	public static int currentThreadCount = 0

    TransferService transferService

    static triggers = {
		//simple (name: "transferJob", startDelay: 60000, repeatInterval: 30000l)
		simple (name: "transferJob", startDelay: 10000, repeatInterval: 20000l)
	}

	def execute() {
        def runJob = Setting.getSetting(SettingNames.DO_JOBS)
        if (runJob) {
            int maxThreadCount = TransferManager.CURRENT.maxThreadCount
            if (!maxThreadCount) {
                log.info "Max ingest threads is set to 0. Skipping this run of transfer job."
                return
            }

            if (currentThreadCount >= maxThreadCount) {
                log.info "There are currently the maximum number of threads running " +
                        "(${maxThreadCount}). Skipping this run of transfer job."
                return
            }

            try {
                currentThreadCount++
                log.info "Executing transfer job on thread number ${currentThreadCount}"

                transferService.processAssets()

                log.info "Find transfer job execution complete on thread number ${currentThreadCount}"
            } catch(Throwable ex){
                println "*** Caught exception in TransferJob ***"
                ex.printStackTrace()
            } finally {
                currentThreadCount--
            }
        } else {
            log.info "TransferJob not configured to run"
        }
	}
}
