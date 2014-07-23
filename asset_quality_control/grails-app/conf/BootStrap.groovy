import grails.util.GrailsUtil
import clsetting.Setting
import settings.SettingNames
import clquality.TransferService
import clquality.OrphanService

class BootStrap {

    OrphanService orphanService
	TransferService transferService

    def init = { servletContext ->
		// Make sure there is a clquality.properties file
		File p = new File("/usr/local/clearleap/conf/clquality.properties");
		if (!p.exists()) {
			File d = new File("/usr/local/clearleap/conf")
			d.mkdirs()
			File f = new File(d, "clquality.properties")
			FileWriter w = new FileWriter(f)
			w.write('# clquality property file')
			w.close()
		}
		
        initialize()
        orphanService.findAndFixOrphans()
    }

    def destroy = {
    }

    private initialize() {

        log.info "Initializing Settings"
        initSetting(SettingNames.CLCORE_DB_DRIVER, "org.postgresql.Driver")
        initSetting(SettingNames.CLCORE_DB_USERNAME, "clcore")
        initSetting(SettingNames.CLCORE_DB_PASSWORD, "clcore")
        initSetting(SettingNames.AFFILIATE_LEAD_TIME, 14)
        initSetting(SettingNames.DISTRIBUTION_TIME_IN_DAYS, 1)
        initSetting(SettingNames.MIN_REQUIRED, 10)
        initSetting(SettingNames.WEB_PREVIEW_PROFILES, "WEB-PREVIEW.SMALLTHUMB, WEB-PREVIEW.THUMB, WEB-PREVIEW.VIDEO, WEB-PREVIEW.VIDEO.H264")
        initSetting(SettingNames.PROFILES_TO_IGNORE, "")
        initSetting(SettingNames.RECIPIENTS, "nobody@clearleap.com")
        initSetting(SettingNames.SMTP_HOST, "mail-atl.prod.clearleap.com")
        initSetting(SettingNames.MANZANITA_CC_ERROR_CODES, "6-53e, 6-54e")
        initSetting(SettingNames.STAGE_DATE_SKIP_THRESHOLD_MINS, 30)
        initSetting(SettingNames.CURRENT_GLOBAL_CREATE_JOB_THREADS, 0)
        initSetting(SettingNames.CREATE_JOB_GLOBAL_MAX_THREADS, 1)

        if (GrailsUtil.environment == "production") {

            initSetting(SettingNames.DO_JOBS, true)

            initSetting(SettingNames.HBO_ACCOUNT, 1764)
            initSetting(SettingNames.HBO_INGEST_SOURCE, 2449700)
            initSetting(SettingNames.HBO_HD_INGEST_SOURCE, 2397997)
            initSetting(SettingNames.HBO_SD_INGEST_SOURCE, 2398020)
            initSetting(SettingNames.HBO_3D_INGEST_SOURCE, 3496311)
            initSetting(SettingNames.QC_BASE_DIR, "/mnt/content/hbo_sync")
            initSetting(SettingNames.FS_DEFAULT_NAME, "hdfs://hdfs-nn2-atl.prod.clearleap.com:9000")
            initSetting(SettingNames.CLCORE_DB_URL, "jdbc:postgresql://dbmaster-atl.prod.clearleap.com/clcore")
            initSetting(SettingNames.RUN_MANZANITA, true)
            initSetting(SettingNames.RUN_CC_EXTRACTOR, true)
			//SFTP settings
	        initSetting(SettingNames.SFTP_SERVER_URL, ".prod.clearleap.com")
	        initSetting(SettingNames.SFTP_PORT, 22)
	        initSetting(SettingNames.SFTP_USER, "user@clearleap.com")
	        initSetting(SettingNames.SFTP_PASSWORD, "pwd123")
	        initSetting(SettingNames.SFTP_DROP_DIR_PATH, "/mnt/content")

            initSetting(SettingNames.SEND_EMAIL, true)

        } else {

            initSetting(SettingNames.DO_JOBS, false)

            initSetting(SettingNames.HBO_ACCOUNT, 2572)
            initSetting(SettingNames.HBO_INGEST_SOURCE, 4486256)
            initSetting(SettingNames.HBO_HD_INGEST_SOURCE, 2397997)
            initSetting(SettingNames.HBO_SD_INGEST_SOURCE, 2398020)
            initSetting(SettingNames.HBO_3D_INGEST_SOURCE, 3496311)
            initSetting(SettingNames.QC_BASE_DIR, "/usr/local/clearleap/edge/test")
            initSetting(SettingNames.FS_DEFAULT_NAME, "hdfs://hadoop-namenode.clearleap.com:9000")
            initSetting(SettingNames.CLCORE_DB_URL, "jdbc:postgresql://dev-central2.clearleap.com/clcore")
            initSetting(SettingNames.RUN_MANZANITA, false)
            initSetting(SettingNames.RUN_CC_EXTRACTOR, false)
			//SFTP settings
	        initSetting(SettingNames.SFTP_SERVER_URL, "dev-central2.clearleap.com")
	        initSetting(SettingNames.SFTP_PORT, 22)
	        initSetting(SettingNames.SFTP_USER, "cledge")
	        initSetting(SettingNames.SFTP_PASSWORD, "Gulf0!l.")
	        initSetting(SettingNames.SFTP_DROP_DIR_PATH, "qc_staging")

            initSetting(SettingNames.SEND_EMAIL, false)
        }

		// Handle Transfer Manager set up if necessary
		transferService.handleTransferManagerSetUp()
    }

    private void initSetting(String name, value) {
        def currentValue = Setting.getSetting(name)
        if (currentValue == null) {
            Setting.setValue(name, value)
        }
    }
}
