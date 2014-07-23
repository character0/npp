import clsetting.Setting
import settings.SettingNames
import clquality.AssetService

class DeleteJob {

    AssetService assetService

    static triggers = {
		simple (name: "deleteJob", startDelay: 60000*3, repeatInterval: 180000l)
	}

    private static Boolean currentlyProcessing = false

	def execute() {
        def runJob = Setting.getSetting(SettingNames.DO_JOBS)
        if (runJob) {
		    try {
			    runJobSafely {
				    assetService.cleanupQCedContent()
			    }
		    } catch(Throwable ex) {
                println "*** Caught exception in DeleteJob ***"
                ex.printStackTrace()
		    }
        } else {
            log.info "DeleteJob not configured to run"
        }
	}

	/**
	 * Use a local variable to prevent multiple quartz threads from trying to
	 * execute this job.Synchronize on the control variable and check the state.
	 * If no one else is running it, set the control variable and the local flag,
	 * exit the synchronized block and run the closure.When it's done running,
	 * reset the control variable.
	 */
	private void runJobSafely(Closure job) {
		private doJob = false

		synchronized(currentlyProcessing) {
			if (!currentlyProcessing) {
				currentlyProcessing = true
				doJob = true
			}
		}

		if (doJob) {
			try {
				job()
			}
			finally {
				synchronized(currentlyProcessing) {
					currentlyProcessing = false
				}
			}
		}
		else {
			log.debug "Another thread is currently running delete job--skipping"
		}
	}
}
