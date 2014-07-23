import clsetting.Setting
import settings.SettingNames
import clquality.AssetService

class StageJob {

    AssetService assetService

    def grailsApplication

    static triggers = {
		simple (name: "stageJob", startDelay: 60000, repeatInterval: 30000l)
	}

    public static int currentThreadCount = 0
    //private static Boolean currentlyProcessing = false

	def execute() {
        def runJob = Setting.getSetting(SettingNames.DO_JOBS)
        if (runJob) {
		    try {
			    runJobSafely {
				    assetService.processAssets()
			    }
		    } catch(Throwable ex) {
			    println "*** Caught exception in StageJob ***"
                ex.printStackTrace()
		    }
        } else {
            log.info "StageJob not configured to run"
        }
	}

	/**
	 */
	private void runJobSafely(Closure job) {

		private doJob = false
        // Ensures only X threads are running globally across shared apps.
        def maxThreadCountSetting = grailsApplication?.config?.manzanita?.licenses
        def maxThreadCount = maxThreadCountSetting ? maxThreadCountSetting.toInteger() : 1
        if (currentThreadCount < maxThreadCount) {
            log.debug "*** There are only $currentThreadCount current staging job threads running. Total allowed is $maxThreadCount ***"
            doJob = true
        } else {
            log.info "There are currently the maximum number of threads running ${currentThreadCount}; Skipping this run of stage job."
        }

		if (doJob) {
			try {
                currentThreadCount++
				job()
			} finally {
		        currentThreadCount--
			}
		} else {
			log.debug "Another thread is currently running stage job--skipping"
		}
	}
}
