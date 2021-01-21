package com.pabslabs.core.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.security.MessageDigest

class HashUtil {

	private static Log log = LogFactory.getLog("com.pabslabs.core.utils.HashUtil")

    // Calculates a hash for an object that is unique 99.9% of the time
	// PRE-CONDITIONS:
	static String calculateHash(objectId, objectName, userId) {
		String retval = null

        // Hash Algorithm
		//  - Concatenate user id, content id, content name, Date.getTime()
		//  - Take product and run MD5 on it
		//  - Run Base64 on that
		//  - Replace all instances of '+','/','=' with '-','_','' respectively
		def timeInMillis = (new Date()).getTime()
		
		//  - Concatenate user id, asset id, asset name, Date.getTime()
		def baseData = "${userId}${objectId}${objectName}${timeInMillis}"
		// Get the byte form of the data
		def data = baseData.getBytes()
			
		//  - Take product and run MD5 on it
		MessageDigest m = MessageDigest.getInstance("MD5")
		m.reset()
		m.update(data)
		def md5Sum = m.digest()
		
		//  - Run Base64 on that
		def base64 = Base64.encodeBase64(md5Sum)
		
		//  - Replace all instances of '+','/','=' with '-','_',''
		retval = new String(base64).replaceAll("[+]", "-").replaceAll("/", "_").replaceAll("=", "")

		log.info "Object ${objectId} has a hash of ${retval}"
		return retval
    }
	
	// Calculates a hash for an object that is unique 99.9% of the time
	// PRE-CONDITIONS:
	//  object has an id and name
	//  user has an id
	static String calculateHash(object, user) {
		if (!object) {
			log.info "Null object--not calculating hash"
			return null
		}
		else if (!object.id) {
			log.info "Object does not have an id--not calculating hash"
			return null
		}
		else if (!object.name) {
			log.info "Object does not have a name--not calculating hash"
			return null
		}
		
		if (!user) {
			log.info "Null user--not calculating hash"
			return null
		}
		else if (!user.id) {
			log.info "User does not have an id--not calculating hash"
			return null
		}
		
		return calculateHash(object.id, object.name, user.id)
	}
}