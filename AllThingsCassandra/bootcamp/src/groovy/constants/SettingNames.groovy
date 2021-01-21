package groovy.constants

class SettingNames {

	/*********************************************************/
	/*** UPDATE IF A SETTING NAME IS ADDED/CHANGED/REMOVED ***/
	/*********************************************************/

	// This array is used to maintain a full list of setting names. Update as necessary.
	// An enum would work for this class but it would still require us to maintain a list
	//  of setting names in a fromString() method so there's no real advantage to making
	//  the change. Function has been named 'values()' to mock enum behavior.
	public static final List values() {
		// Setting name lists are broken down into their categories for better maintainability
		//  and readability. They are combined into one list at the end.
		def systemSettings = [RECEIVER_DROP_FOLDER_BASE_DIR, FILE_MAX_SIZE_MB, FILE_ARCHIVE_EXTRACT_DIR]

		// Combine all the lists
		def returnList = systemSettings

		// Return the list of all settings in alphabetical order
		return returnList.sort {it}
	}
	/*****************************************/
	/*****************************************/

	/************/
	/** SYSTEM **/
	/************/
	public static final String RECEIVER_DROP_FOLDER_BASE_DIR = "RECEIVER_DROP_FOLDER_BASE_DIR"
    public static final String FILE_MAX_SIZE_MB = "FILE_MAX_SIZE_MB"
    public static final String FILE_ARCHIVE_EXTRACT_DIR = "FILE_ARCHIVE_EXTRACT_DIR"
    public static final String MAX_RETRIES_PER_POLL = "MAX_RETRIES_PER_POLL"
    public static final String MAX_FAILED_POLLS_BEFORE_ERROR_STATE = "MAX_FAILED_POLLS_BEFORE_ERROR_STATE"
    public static final String RETRY_SLEEP_INTERVAL_IN_MINUTES = "MAX_FAILED_POLLS_BEFORE_ERROR_STATE"
    public static final String FEED_CONNECT_TIME_OUT_SECS = "FEED_CONNECT_TIME_OUT_SECS"
    public static final String FEED_READ_TIME_OUT_SECS = "FEED_READ_TIME_OUT_SECS"
    public static final String FILE_INGEST_MAX_SIZE_MB = "FILE_INGEST_MAX_SIZE_MB"

    public static final String PUBLISH_TO_MQ = "PUBLISH_TO_MQ"
    public static final String INSERT_INTO_CASSANDRA = "PUBLISH_TO_CASSANDRA"

}
