/*
 * Copyright (c) 2007 pabslabs, Inc. All Rights Reserved.
 *
 * This module contains unpublished, confidential, proprietary
 * material.  The use and dissemination of this material are
 * governed by a license.  The above copyright notice does not
 * evidence any actual or intended publication of this material.
 *
 * @(#)ApiAuth.java   08/11/08
 */

package com.pabslabs.core

/**
 * User: tomc
 * Date: Aug 11, 2008
 * Time: 11:10:21 AM
 */
class ApiAuth {
	String userName = "apiuser"
    String userPass = "c1AP1!"

    /**
	 * @param authString
	 *
	 * @return Whether the authString was verified successfully.
	 */
	boolean verifyAuthString(String authString) throws UnsupportedEncodingException {
		return verifyAuthString(authString, userName, userPass)
	}

    boolean verifyAuthString(String authString, String apiUsername, String apiKey) throws UnsupportedEncodingException {
		def aString = authString.replaceFirst("Basic ","")
		String str = decode(aString)

		int index = str.indexOf(":")
		if (index > 0) {
			String reqUser = str.substring(0, index)
			String reqKey = str.substring(index + 1)

			return ( apiUsername == reqUser && apiKey == reqKey )
		}
		
		return false
	}

    /**
     * Given the pabslabs header auth string, this method will verify the header for apiAsset authorization.
     *
     * @param authStr
     * @return
     */
    Boolean verifyApiAuthString(String authStr) {

        def aString = authStr.replaceFirst("Basic ","")
        String str = new ApiAuth().decode(aString)
        int index = str.indexOf(":")
        if (index > 0) {
            String reqUser = str.substring(0, index)
            String reqKey = str.substring(index + 1)

           /* def apiUser = User.findByUsernameAndApiKey(reqUser, reqKey)
            if (apiUser?.apiAccess == true) {
                return apiUser
            }*/
        }

        return false
    }

    String createAuthHeader() {
		return createAuthHeader(userName, userPass)
	}

    String createAuthHeader(String apiUsername, String apiKey) {
		def authString = apiUsername + ":" +  apiKey
		return "Basic " + encode(authString)
	}

    String encode(String source) {
		Base64 enc = new Base64()
		return ( new String( enc.encode( source.getBytes() ) ) )
	}

    String decode(String source) throws UnsupportedEncodingException {
		return ( new String(Base64.decodeBase64( source.getBytes("UTF-8") ) ) )
	}
}

