package com.pabslabs.core.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class LogUtil {
	private static Log log = LogFactory.getLog("com.pabslabs.core.util")

    static HashMap getLoggingParams(params, String replaceText = "HIDDEN_FOR_LOGS") {
		def loggingParams = new HashMap(params)
		def removeParamKeys = ["password", "deviceToken"]
		removeParamKeys.each { paramKey ->
			if (loggingParams[paramKey]) {
				loggingParams[paramKey] = replaceText
			}
		}
		
		// Handle device token in a url
		loggingParams["originalURL"] = loggingParams["originalURL"]?.replaceAll("deviceToken=.*&", 
			"deviceToken=${replaceText}&")
		
		return loggingParams
	}
}