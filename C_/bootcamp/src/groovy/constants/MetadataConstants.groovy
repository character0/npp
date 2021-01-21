package groovy.constants

public class MetadataConstants {

	/*********** START: ACCOUNT CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_ACCOUNT_NAME = 30;
	public static final String VALID_CHAR_ACCOUNT_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.SPACE + ValidateUtil.URL_PUNCTUATION;

	/*********** END: ACCOUNT CONSTANTS ***********/

	/*********** START: UPLOAD CONSTANTS ***********/

	// File Name
	public static final String VALID_CHAR_FILE_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.DASH + ValidateUtil.UNDERSCORE + ValidateUtil.DOT;

	/*********** END: UPLOAD CONSTANTS ***********/

	/*********** START: ASSET VALIDATION CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_ASSET_NAME = 128;
	public static final String VALID_CHAR_ASSET_NAME = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// ADI Asset ID
	public static final int MAX_LENGTH_ADI_ASSET_ID = 20;

	// Short Name
	public static final int MAX_LENGTH_ASSET_SHORT_NAME = 19;
    public static final int INGEST_MAX_LENGTH_ASSET_SHORT_NAME = 256;
	public static final String VALID_CHAR_ASSET_SHORT_NAME = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Description
	public static final int MAX_LENGTH_ASSET_DESCR = 256;
	public static final int INGEST_MAX_LENGTH_ASSET_DESCR = 2560;
	public static final String VALID_CHAR_ASSET_DESCR = ValidateUtil.LATIN + ValidateUtil.SPACE + ValidateUtil.NEW_LINE;

	// Episode
	public static final int MAX_LENGTH_ASSET_EPISODE = 50;
	public static final int INGEST_MAX_LENGTH_ASSET_EPISODE = 256;
	public static final String VALID_CHAR_ASSET_EPISODE = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Actors
	public static final int MAX_LENGTH_ASSET_ACTORS = 1024;
	public static final String VALID_CHAR_ASSET_ACTORS = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Ad-ID
	public static final int MAX_LENGTH_AD_ID = 12;
	public static final String VALID_CHAR_AD_ID = ValidateUtil.ALPHANUMERIC;

	// Comment
	public static final int MAX_LENGTH_ASSET_COMMENT = 1024;

	/*********** END: ASSET VALIDATION CONSTANTS ***********/

	/*********** START: CONTENT VALIDATION CONSTANTS ***********/

	// Filename
	public static final int MAX_LENGTH_CONTENT_FILENAME = 64;

	/*********** END: CONTENT VALIDATION CONSTANTS ***********/

	/*********** START: OVERLAY VALIDATION CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_OVERLAY_NAME = 50;
	public static final String VALID_CHAR_OVERLAY_NAME = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Text Line
	public static final int MAX_LENGTH_OVERLAY_TEXT = 25;
	public static final String VALID_CHAR_OVERLAY_TEXT = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Image size
	public static final int MAX_WIDTH_OVERLAY_IMAGE = 640;
	public static final int MAX_HEIGHT_OVERLAY_IMAGE = 480;

	/*********** END: OVERLAY VALIDATION CONSTANTS ***********/

	/*********** START: SETTING CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_SETTING_NAME = 255;

	// Value
	public static final int MAX_LENGTH_SETTING_VALUE = 255;

	/*********** END: SETTING CONSTANTS ***********/

	/*********** START: CHANNEL CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_CHANNEL_NAME = 255;
	public static final String VALID_CHAR_CHANNEL_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.SPACE + ValidateUtil.URL_PUNCTUATION;

	// Username
	public static final int MAX_LENGTH_CHANNEL_USERNAME = 255;

	// Password
	public static final int MAX_LENGTH_CHANNEL_PASSWORD = 255;

	// Target Rate
	public static final int MAX_LENGTH_CHANNEL_TARGET_RATE = 9;
	public static final String VALID_CHAR_TARGET_RATE = ValidateUtil.NUMERIC;

	/*********** END: CHANNEL CONSTANTS ***********/

	/*********** START: SCHEDULE CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_SCHEDULE_NAME = 255;
	public static final String VALID_CHAR_SCHEDULE_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.SPACE + ValidateUtil.URL_PUNCTUATION;

	/*********** END: SCHEDULE CONSTANTS ***********/

	/*********** START: EDGE EXCHANGE CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_EDGEEXCHANGE_NAME = 255;

	// Serial Number
	public static final int MAX_LENGTH_EDGEEXCHANGE_SN = 255;

	/*********** END: EDGE EXCHANGE CONSTANTS ***********/

	/*********** START: CONFIGURATION SETTINGS VALIDATION CONSTANTS ***********/

	// Image to Video Length
	public static final int MAX_LENGTH_CONF_IMGTOVIDLEN = 20;
	public static final String VALID_CHAR_CONF_IMGTOVIDLEN = ValidateUtil.NUMERIC;

	// Auto Delete Days
	public static final int MAX_LENGTH_CONF_AUTODELETEDAYS= 3;
	public static final String VALID_CHAR_CONF_AUTODELETEDAYS = ValidateUtil.NUMERIC;

	// Product
	public static final int MAX_LENGTH_CONF_PRODUCT = 20;
	public static final String VALID_CHAR_CONF_PRODUCT = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Provider
	public static final int MAX_LENGTH_CONF_PROVIDER = 20;
	public static final String VALID_CHAR_CONF_PROVIDER = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Product ID
	public static final int MAX_LENGTH_CONF_PROVIDERID = 20;
	public static final String VALID_CHAR_CONF_PROVIDERID = ValidateUtil.ALPHANUMERIC + ValidateUtil.DASH +
		ValidateUtil.DOT;

	// Provider Contact
	public static final int MAX_LENGTH_CONF_PROVIDERCONTACT = 50;
	public static final String VALID_CHAR_CONF_PROVIDERCONTACT = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Provider Contact ID
	public static final int MAX_LENGTH_CONF_PROVIDERCONTACTID = 50;
	public static final String VALID_CHAR_CONF_PROVIDERCONTACTID = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Billing ID
	public static final int MAX_LENGTH_CONF_BILLINGID = 36;
	public static final String VALID_CHAR_CONF_BILLINGID = ValidateUtil.ALPHANUMERIC;

	// House ID
	public static final int MAX_LENGTH_CONF_HOUSEID = 255;
	public static final String VALID_CHAR_CONF_HOUSEID = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Asset ID Prefix
	public static final int MAX_LENGTH_CONF_ASSETIDPREFIX = 3;
	public static final String VALID_CHAR_CONF_ASSETIDPREFIX = ValidateUtil.ALPHA;

	// Suggested Price
	public static final int MAX_LENGTH_CONF_SUGGESTEDPRICE = 8;

	// Studio
	public static final int MAX_LENGTH_CONF_STUDIO = 32;
	public static final String VALID_CHAR_CONF_STUDIO = ValidateUtil.ALPHANUMERIC + ValidateUtil.SPACE +
		ValidateUtil.PUNCTUATION;

	// Year
	public static final int MIN_LENGTH_CONF_YEAR = 4;
	public static final int MAX_LENGTH_CONF_YEAR = 4;
	public static final String VALID_CHAR_CONF_YEAR = ValidateUtil.NUMERIC;

    //Product Content Tier
    public static final int MAX_LENGTH_CONF_PROVIDER_CONTENT_TIER = 256;
	public static final String VALID_CHAR_CONF_PROVIDER_CONTENT_TIER = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Maximum Viewing Length
	//  - Days
	public static final int MAX_LENGTH_CONF_MAXVIEWLENDAYS = 2;
	public static final String VALID_CHAR_CONF_MAXVIEWLENDAYS = ValidateUtil.NUMERIC;
	//  - Hours
	public static final int MAX_LENGTH_CONF_MAXVIEWLENHOURS = 2;
	public static final String VALID_CHAR_CONF_MAXVIEWLENHOURS = ValidateUtil.NUMERIC;
	public static final int MAX_VALUE_CONF_MAXVIEWHOURS = 23;
	//  - Minutes
	public static final int MAX_LENGTH_CONF_MAXVIEWLENMINS = 2;
	public static final String VALID_CHAR_CONF_MAXVIEWLENMINS = ValidateUtil.NUMERIC;
	public static final int MAX_VALUE_CONF_MAXVIEWLENMINS = 59;
	
	//Default window length in days
	public static final int MAX_DEFAULT_WINDOW_LENGTH_IN_DAYS = 36500;
	public static final String VALID_CHAR_CONF_MAXDEFAULTWINDOWLENGTHINDAYS = ValidateUtil.NUMERIC;

    //Publish delay in days
    public static final int MAX_PUBLISH_DELAY_IN_DAYS = 365;
    public static final String VALID_CHAR_CONF_MAXPUBLISHDELAYINNDAYS = ValidateUtil.NUMERIC;

	// Display as new days
	public static final int MAX_LENGTH_DISPLAY_AS_NEW_DAYS = 3;
	public static final String VALID_CHAR_DISPLAY_AS_NEW_DAYS = ValidateUtil.NUMERIC;

	// Display as last chance days
	public static final int MAX_LENGTH_DISPLAY_AS_LAST_CHANCE_DAYS = 3;
	public static final String VALID_CHAR_DISPLAY_AS_LAST_CHANCE_DAYS = ValidateUtil.NUMERIC;

	// Propagation priority
	public static final int MIN_VALUE_PROPAGATION_PRIORITY = 1;
	public static final int MAX_VALUE_PROPAGATION_PRIORITY = 10000;
	public static final int MAX_LENGTH_PROPAGATION_PRIORITY = 5;
	public static final String VALID_CHAR_PROPAGATION_PRIORITY = ValidateUtil.NUMERIC;

	// Title sort name
	public static final int MAX_LENGTH_TITLE_SORT_NAME = 22;
	public static final int INGEST_MAX_LENGTH_TITLE_SORT_NAME = 255;
	public static final String VALID_CHAR_TITLE_SORT_NAME = ValidateUtil.LATIN + ValidateUtil.SPACE;

    /*********** END: CONFIGURATION SETTINGS VALIDATION CONSTANTS ***********/

	/*********** START: PUBLISH TO VOD VALIDATION CONSTANTS ***********/

	// Title
	public static final int MAX_LENGTH_ADI_TITLE = 128;
	public static final String VALID_CHAR_ADI_TITLE = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Title Brief
	public static final int MAX_LENGTH_ADI_TITLEBRIEF = 19;
	public static final String VALID_CHAR_ADI_TITLEBRIEF = ValidateUtil.LATIN + ValidateUtil.SPACE;

	/*********** END: PUBLISH TO VOD VALIDATION CONSTANTS ***********/

	/*********** START: EXPORT VALIDATION CONSTANTS ***********/

	// Remote File Name
	public static final int MAX_LENGTH_REMOTE_FILE_NAME = 128;
	public static final String VALID_CHAR_REMOTE_FILE_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.DASH + ValidateUtil.UNDERSCORE + ValidateUtil.DOT;

	/*********** END: EXPORT VALIDATION CONSTANTS ***********/

	/*********** START: CATEGORY VALIDATION CONSTANTS ***********/

	// Name
	public static final int MAX_LENGTH_CATEGORY_NAME = 20;
	public static final String VALID_CHAR_CATEGORY_NAME = ValidateUtil.LATIN + ValidateUtil.SPACE;

	/*********** END: CATEGORY VALIDATION CONSTANTS ***********/

	/*********** START: USER VALIDATION CONSTANTS ***********/

	// Username
	public static final int MAX_LENGTH_USER_USERNAME = 80;

	// First name
	public static final int MAX_LENGTH_USER_FIRSTNAME = 30;
	public static final String VALID_CHAR_USER_FIRSTNAME = ValidateUtil.ALPHA + ValidateUtil.SPACE;

	// Last name
	public static final int MAX_LENGTH_USER_LASTNAME = 30;
	public static final String VALID_CHAR_USER_LASTNAME = ValidateUtil.ALPHA + ValidateUtil.SPACE;

	// Password
	public static final int MAX_LENGTH_USER_PASSWORD = 255;
	public static final int MIN_LENGTH_USER_PASSWORD = 6;

	/*********** END: USER VALIDATION CONSTANTS ***********/

	/*********** START: PROMOTION VALIDATION CONSTANTS ***********/

	// E-Mail Title
	public static final int MAX_LENGTH_NOTIFICATION_TITLE = 50;
	public static final String VALID_CHAR_NOTIFICATION_TITLE = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// E-Mail Body
	public static final int MAX_LENGTH_NOTIFICATION_BODY = 255;
	public static final String VALID_CHAR_NOTIFICATION_BODY = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// E-Mail Recipient(s)
	public static final int MAX_LENGTH_NOTIFICATION_RECIPIENT = 255;

	// Display Message
	public static final int MAX_LENGTH_NOTIFICATION_MESSAGE = 255;
	public static final String VALID_CHAR_NOTIFICATION_MESSAGE = ValidateUtil.LATIN + ValidateUtil.SPACE;

	// Click through link
	public static final int MAX_LENGTH_NOTIFICATION_LINK = 255;

	// Twitter Postfix
	public static final int MAX_LENGTH_TWITTER_POSTFIX = 140;

	// RSS Max Items
	public static final int MIN_VALUE_RSS_ITEMS = 0;
	public static final int MAX_VALUE_RSS_ITEMS = 100;
	public static final int MAX_LENGTH_RSS_ITEMS = 3;
	public static final String VALID_CHAR_RSS_ITEMS = ValidateUtil.NUMERIC;

	// RSS VOD Start Date Offset
	public static final int MAX_LENGTH_RSS_VOD_START_OFFSET = 3;
	public static final String VALID_CHAR_VOD_START_OFFSET = ValidateUtil.NUMERIC;

	// Header Image size
	public static final int MAX_WIDTH_NOTIFICATION_HEADER_IMAGE = 800;
	public static final int MAX_HEIGHT_NOTIFICATION_HEADER_IMAGE = 400;

	/*********** END: PROMOTION VALIDATION CONSTANTS ***********/

	/*********** START: AD PLACEMENT VALIDATION CONSTANTS ***********/

	// Ad Placement Comment
	public static final int MAX_LENGTH_ADPLACEMENT_COMMENT = 255;

	/*********** END: AD PLACEMENT VALIDATION CONSTANTS ***********/

	/*********** START: EXCHANGE VALIDATION CONSTANTS ***********/

	// Bundle Icon reference width and height
	//  Used to determine the required aspect ratio for bundle icons
	public static final int REFERENCE_WIDTH_EXCHANGE_BUNDLE_ICON = 120;
	public static final int REFERENCE_HEIGHT_EXCHANGE_BUNDLE_ICON = 120;

	public static final int MAX_LENGTH_INGEST_SOURCE_NAME = 30;
	public static final String VALID_CHAR_INGEST_SOURCE_NAME = ValidateUtil.ALPHANUMERIC + ValidateUtil.SPACE + ValidateUtil.PUNCTUATION;

	/*********** END: EXCHANGE VALIDATION CONSTANTS ***********/

	/*********** START: RULE VALIDATION CONSTANTS ***********/

	// Ruleset Name
	public static final int MAX_LENGTH_RULESET_NAME = 255;

	// Rule Name
	public static final int MAX_LENGTH_RULE_NAME = 255;

	// Rule If Input String (field or category)
	public static final int MAX_LENGTH_RULE_IF_INPUT = 1024;

	// Rule Then Input String
	public static final int MAX_LENGTH_RULE_THEN_INPUT = 1024;

	/*********** END: RULE VALIDATION CONSTANTS ***********/

	/*********** START: CLOFFICE VALIDATION CONSTANTS ***********/

	// Cloffice Instance Default Subscriber Credit Limit
	public static final int MAX_LENGTH_CONF_CLOFFICE_DEFAULT_SUBSCRIBER_CREDIT_LIMIT = 8;
	public static final String VALID_CHAR_CLOFFICE_DEFAULT_SUBSCRIBER_CREDIT_LIMIT = ValidateUtil.NUMERIC;

	// Account ID
	public static final int MAX_LENGTH_CLOFFICE_ACCOUNT_ID = 255;

	// Cloffice Account First Name
	public static final int MAX_LENGTH_CLOFFICE_ACCOUNT_FIRST_NAME = 255;

	// Cloffice Account Last Name
	public static final int MAX_LENGTH_CLOFFICE_ACCOUNT_LAST_NAME = 255;

	// Cloffice Account Monthly Credit Limit
	public static final int MAX_LENGTH_CLOFFICE_ACCOUNT_MONTHLY_LIMIT = 8;
	public static final String VALID_CHAR_CLOFFICE_ACCOUNT_MONTHLY_LIMIT = ValidateUtil.NUMERIC;
    public static final double MAX_VALUE_CLOFFICE_ACCOUNT_MONTHLY_LIMIT = 9999.99;

	// Rendezvous Code
	public static final int MAX_LENGTH_CLOFFICE_RENDEZVOUS_CODE = 255;

	// Cloffice Asset Name
	public static final int MAX_LENGTH_CLOFFICE_ASSET_NAME = 255;

	// Cloffice Asset Short Title
	public static final int MAX_LENGTH_CLOFFICE_ASSET_SHORT_TITLE = 255;

	// Cloffice Asset Episode Name
	public static final int MAX_LENGTH_CLOFFICE_ASSET_EPISODE_NAME = 255;

	// Cloffice Asset Description
	public static final int MAX_LENGTH_CLOFFICE_ASSET_DESCRIPTION = 256;

	// Cloffice Asset Rental Length
	//  - Days
	public static final int MAX_LENGTH_CLOFFICE_ASSET_RENTAL_DAYS = 2;
	public static final String VALID_CHAR_CLOFFICE_ASSET_RENTAL_DAYS = ValidateUtil.NUMERIC;
	//  - Hours
	public static final int MAX_LENGTH_CLOFFICE_ASSET_RENTAL_HOURS = 2;
	public static final String VALID_CHAR_CLOFFICE_ASSET_RENTAL_HOURS = ValidateUtil.NUMERIC;
	//  - Minutes
	public static final int MAX_LENGTH_CLOFFICE_ASSET_RENTAL_MINS = 2;
	public static final String VALID_CHAR_CLOFFICE_ASSET_RENTAL_MINS = ValidateUtil.NUMERIC;

	// Cloffice Asset New Days
	public static final int MAX_LENGTH_CLOFFICE_ASSET_NEW_DAYS = 3;
	public static final String VALID_CHAR_CLOFFICE_ASSET_NEW_DAYS = ValidateUtil.NUMERIC;

	// Cloffice Asset Last Chance Days
	public static final int MAX_LENGTH_CLOFFICE_ASSET_LAST_CHANCE_DAYS = 3;
	public static final String VALID_CHAR_CLOFFICE_ASSET_LAST_CHANCE_DAYS = ValidateUtil.NUMERIC;

	// Cloffice Asset Price
	public static final int MAX_LENGTH_CONF_CLOFFICE_ASSET_PRICE = 6;

	// Cloffice Subscriber Pin
	public static final int MIN_LENGTH_CLOFFICE_ACCOUNT_PIN = 4;
	public static final int MAX_LENGTH_CLOFFICE_ACCOUNT_PIN = 4;
	public static final String VALID_CHAR_CLOFFICE_ACCOUNT_PIN = ValidateUtil.NUMERIC;

	// Cloffice Category Name
	public static final int MAX_LENGTH_CLOFFICE_CATEGORY_NAME = 255;

	// Cloffice Category Description
	public static final int MAX_LENGTH_CLOFFICE_CATEGORY_DESCRIPTION = 255;

	// Entitlement Name
	public static final int MAX_LENGTH_ENTITLEMENT_NAME = 255;

	// Entitlement Ruleset: Name
	public static final int MAX_LENGTH_ENTITLEMENT_RULESET_NAME = 255;

	// Entitlement Ruleset: Rule Name
	public static final int MAX_LENGTH_ENTITLEMENT_RULE_NAME = 255;

	// Entitlement Ruleset: Rule If Field
	public static final int MAX_LENGTH_ENTITLEMENT_RULE_IF_INPUT = 1024;

	// Entitlement Ruleset: Rule Order
	public static final int MAX_LENGTH_ENTITLEMENT_RULE_ORDER = 3;
	public static final String VALID_CHAR_ENTITLEMENT_RULE_ORDER = ValidateUtil.NUMERIC;

	/*********** END: CLOFFICE VALIDATION CONSTANTS ***********/

	/*********** START: CLOFFICE LINEAR VALIDATION CONSTANTS ***********/
	// Linear Content Provider: Name
	public static final int MAX_LENGTH_LINEAR_CONTENT_PROVIDER_NAME = 255;
	
	// Linear Content Family: Name
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_NAME = 255;
	
	// Linear Content Family: Callsign
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_CALLSIGN = 255;
	
	// Linear Content Family: TMS ID
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_TMS_ID = 255;
	
	// Linear Content Family: TV Guide ID
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_TV_GUIDE_ID = 255;
	
	// Linear Content Family: Alternate Service ID
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_ALT_SERVICE_ID = 255;
	
	// Linear Content Family: Comment
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_COMMENT = 2048;
	
	// Linear Content Family: Thumbnail URI
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_THUMB_URI = 1024;
	
	// Linear Content Family: Genre
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_GENRE = 255;
	
	// Linear Content Family: Sub Genre
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_SUB_GENRE = 255;
	
	// Linear Content Family: Entitlements
	public static final int MAX_LENGTH_LINEAR_CONTENT_FAMILY_ENTITLEMENTS = 1024;
	
	// Source Feed: Name
	public static final int MAX_LENGTH_SOURCE_FEED_NAME = 255;
	
	// Source Feed: URI
	public static final int MAX_LENGTH_SOURCE_FEED_URI = 1024;
	
	// Encode: Name
	public static final int MAX_LENGTH_ENCODE_NAME = 255;
	
	// Encode: Comment
	public static final int MAX_LENGTH_ENCODE_COMMENT = 2048;
	
	// Encode: URI
	public static final int MAX_LENGTH_ENCODE_URI = 1024;
	
	// Channel Map: Name
	public static final int MAX_LENGTH_CHANNEL_MAP_NAME = 255;
	
	// Encoder: Name
	public static final int MAX_LENGTH_ENCODER_NAME = 255;
	
	// Encoder: Management Interface URI
	public static final int MAX_LENGTH_ENCODER_MGMT_INTERFACE_URI = 1024;

	/*********** END: CLOFFICE LINEAR VALIDATION CONSTANTS ***********/
	
	// Custom Metadatas display name max length
	public static final int MAX_LENGTH_CUSTOM_METADATA_DISPLAYNAME = 23;
	// Custom Metadata value length
	public static final int MAX_LENGTH_CUSTOM_METADATA_VALUE = 255;
	
}
