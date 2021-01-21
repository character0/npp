package com.pabslabs.core

/**
 * Response codes used in methods for authentication and authorization (authnz)
 * This reflects the document Security_Error_codes.pdf : TODO: Update with url to wiki.
 * The listing order in the enum reflects the priority of the checks that must be made. 
 * Nothing in the enum itself enforces any priority, this is implicit in the code. 
 * However it is helpful for readability to always keep the listing order consistent with the actual priority. 
 * @author asrinivasan
 * 
 */

enum AuthorizationCode {
	// Values are ordered in precedence as shown in Security_Error_Codes.pdf
	// The lowest value is highest precedence
	SUCCESS(1), ACCOUNT_DISABLED(2), USER_DISABLED(3), DOS(4), MISSING_FEATURE(5), NO_SUCH_OBJECT(6), INCORRECT_ACCOUNT(7), 
	INCORRECT_USER(8), MISSING_ROLE(9), OBJECT_IS_DELETED(10), INCORRECT_STATE(11),ASSET_IS_DEFAULT(12), 
	ASSET_IN_AD(13), ASSET_IS_GLOBAL(14), ASSET_IS_GAP(15), ASSET_IN_SCHEDULE(16), NO_SCHEDULE(17), INVALID_SCHEDULE_TYPE(18), 
	MISSING_PERMISSION_SCHEDULE(19), DATE_IN_PAST(20), DATE_NOT_IN_PAST(21), ASSET_IN_ALL_VOD_SCHEDULES(22), 
	ASSET_IN_ALL_EXPORT_SCHEDULES(23),UPDATES_NOT_ALLOWED_AFTER_EXPORT(24), PUBLIC_DISABLED(25), PUBLIC_ACCOUNT_DISABLED(26), 
	PLACEMENT_CLOSED(27), PLACEMENT_DISTRIBUTED(28), ADI_METADATA_UPDATE_DISABLED(29), NO_METADATA_TEMPLATE(30), 
	METADATA_TEMPLATE_NEEDS_CATEGORIES(31), RULESET_ON_CHANNEL(32), CLOFFICE_PROVISION_DISABLED(33), 
	CLOFFICE_BILLING_TRANSACTIONS_DISABLED(34), CLOFFICE_INCORRECT_BILLING_PLUGIN(35), CLOFFICE_ASSET_HAS_SESSIONS(36),
	UNKNOWN_ERROR(37);
	
	private int value

    AuthorizationCode(int value) {
		this.value = value
	}

    int getValue() {
		return value
    }
}
