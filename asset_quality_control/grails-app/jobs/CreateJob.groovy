import clsetting.Setting
import settings.SettingNames
import clquality.AssetService

class CreateJob {

    AssetService assetService

    static triggers = {
		simple (name: "syncJob", startDelay: 30000, repeatInterval: 60000l)  //TODO configurable time?
	}

    public static int currentThreadCount = 0
    private static Boolean currentlyProcessing = false

	def execute() {
        def runJob = Setting.getSetting(SettingNames.DO_JOBS)
        if (runJob) {
            try {
			    runJobSafely {
				    assetService.findNewAsset()
			    }
		    } catch(Throwable ex) {
                println "*** Caught exception in CreateJob ***"
                ex.printStackTrace()
		    }
        } else {
            log.info "CreateJob not configured to run"
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
        // Ensures only 1 thread is running globally across shared apps.
        int maxThreadCount = Setting.getSetting(SettingNames.CREATE_JOB_GLOBAL_MAX_THREADS, 1)
        int currentGlobalThreads = Setting.setValue(SettingNames.CURRENT_GLOBAL_CREATE_JOB_THREADS, 1)
        if (currentThreadCount < maxThreadCount && currentGlobalThreads < maxThreadCount) {
            doJob = true
        } else {
            log.info "There are currently the maximum number of threads running ${maxThreadCount}). Skipping this run of create job."
        }
        // Ensures only 1 thread is running on same JVM.
		synchronized(currentlyProcessing) {
			if (!currentlyProcessing) {
				currentlyProcessing = true
				doJob = true
			}
		}

		if (doJob) {
			try {
                currentThreadCount++
                Setting.setValue(SettingNames.CURRENT_GLOBAL_CREATE_JOB_THREADS, currentThreadCount)
				job()
			}
			finally {

				synchronized(currentlyProcessing) {
					currentlyProcessing = false
                }

                currentThreadCount--
                Setting.setValue(SettingNames.CURRENT_GLOBAL_CREATE_JOB_THREADS, currentThreadCount)
			}
		} else {
			log.debug "Another thread is currently running create job--skipping"
		}
	}
}
