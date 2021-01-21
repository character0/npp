package com.pabslabs.core.utils

import com.pabslabs.core.ApiAuth
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class RESTUtil {

	private static final Log log = LogFactory.getLog("com.pabslabs.core.utils.RESTUtil")
    private static ApiAuth apiAuth = new ApiAuth()

    private static int TIME_OUT = 300 * 1000

    /***********************************/
	/************* DELETE **************/
	/***********************************/

	/**
	 * Calls the provided URL with DELETE request.
	 * @param url The URL to call.
	 * @return Response text from DELETE request.
	 * @throws Exception If there are any problems.
	 */
	static String doDELETE(String url) throws Exception {
		return doDELETEHelper(url, null, null, false)?.responseText
	}
	
	/**
	 * Calls the provided URL with DELETE request.
	 * @param url The URL to call.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doDELETEResponse(String url) throws Exception {
		return doDELETEHelper(url, null, null, true)
	}
	// With apiUsername/apiKey
	static Map doDELETEResponse(String url, String apiUsername, String apiKey) throws Exception {
		return doDELETEHelper(url, apiUsername, apiKey, true)
	}

	/**
	 * Calls the provided URL with DELETE request.
	 * @param url The URL to call.
	 * @param handleInputStreamException True if input stream exception should be caught and logged. False if it should be thrown.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	private static Map doDELETEHelper(String url, String apiUsername, String apiKey, boolean handleInputStreamException) throws Exception {
		log.debug("Request URL: " + url)

        URL u = new URL(url)
        HttpURLConnection c = (HttpURLConnection) u.openConnection()
        c.setConnectTimeout(TIME_OUT)
        c.setReadTimeout(TIME_OUT)
        c.setDoInput(true)
        c.setRequestMethod("DELETE")

        String authHeader = apiUsername && apiKey ? apiAuth.createAuthHeader(apiUsername, apiKey) : apiAuth.createAuthHeader()
		c.setRequestProperty("Authorization", authHeader)

        int response = c.getResponseCode()
        log.debug("Response HTTP code: " + response)

        String responseXML = null
		// Some response codes throw an exception when trying to get input stream.
		// To handle, catch exceptions around getting input stream so that a valid
		//  map with a response code can be returned.
		try {
			responseXML = convertInputStreamToString(c.getInputStream())
			log.debug("Response XML: " + responseXML)
        }
		catch (Exception e) {
			log.error "doDELETEHelper: InputStream exception: ", e
			if (!handleInputStreamException) {
				// Throw the exception after logging if it should be handled elsewhere
				throw e
			}
		}		

		return [responseText: responseXML, responseCode: response]
	}

	/***********************************/
	/*************** GET ***************/
	/***********************************/
	
	/**
	 * Calls the provided URL with GET request.
	 * @param url The URL to call.
	 * @return Response text from GET request as formatted XML.
	 * @throws Exception If there are any problems.
	 */
	static String doGET(String url) throws Exception {
		return doGETHelper(url, null, null, true, false, false)?.responseText
	}

	/**
	 * Calls the provided URL with GET request.
	 * @param url The URL to call.
	 * @return If 200 response, response text from GET request as formatted XML. Otherwise, null.
	 * @throws Exception If there are any problems.
	 */
	static String doGETRestrictive(String url) throws Exception {
		return doGETHelper(url, null, null, false, true, false)?.responseText
	}
	
	/**
	 * Calls the provided URL with GET request.
	 * @param url The URL to call.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doGETResponse(String url) throws Exception {
		return doGETHelper(url, null, null, false, false, true)
	}
	// With apiUsername/apiKey	
	static Map doGETResponse(String url, String apiUsername, String apiKey) throws Exception {
		return doGETHelper(url, apiUsername, apiKey, false, false, true)
	}
	
	/**
	 * Calls the provided URL with GET request.
	 * @param url The URL to call.
	 * @param parseResponseAsXML True if response text should be formatted as XML. False otherwise.
	 * @param followRedirect True if url call should follow redirects. False otherwise.
	 * @param failOnNon200Response True if method should return null for responseText in the event of a non-200 response. False otherwise.
	 * @param handleInputStreamException True if input stream exception should be caught and logged. False if it should be thrown.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	private static Map doGETHelper(String url, String apiUsername, String apiKey, boolean parseResponseAsXML, boolean failOnNon200Response, 
			boolean handleInputStreamException) throws Exception 
	{
		log.debug("Request URL: " + url)

        URL u = new URL(url)
        HttpURLConnection c = (HttpURLConnection) u.openConnection()
        c.setConnectTimeout(TIME_OUT)
        c.setReadTimeout(TIME_OUT)
        c.setDoInput(true)
        c.setRequestMethod("GET")

        String authHeader = apiUsername && apiKey ? apiAuth.createAuthHeader(apiUsername, apiKey) : apiAuth.createAuthHeader()
		c.setRequestProperty("Authorization", authHeader)

        int response = c.getResponseCode()
        log.debug("Response HTTP code: " + response)

        String responseString = null
    	if (failOnNon200Response && (response != HttpURLConnection.HTTP_OK)) {
    		log.error ("Failing on a non 200 response code")
            return [responseText: null, responseCode: response]
    	}
    	else {
    		// Some response codes throw an exception when trying to get input stream.
    		// To handle, catch exceptions around getting input stream so that a valid
    		//  map with a response code can be returned.
    		try {
				if (parseResponseAsXML) {
					responseString = XMLUtils.formatXML(c.getInputStream())
				} else {
					responseString = convertInputStreamToString(c.getInputStream())
				}
    		}
    		catch (Exception e) {
    			log.error "doGETHelper: InputStream exception: ", e
    			if (!handleInputStreamException) {
    				// Throw the exception after logging if it should be handled elsewhere
    				throw e
    			}
			}
    	}
		log.debug("Response String: " + responseString)

        return [responseText: responseString, responseCode: response]
	}

	/***********************************/
	/************** POST ***************/
	/***********************************/

	/**
	 * Calls the provided URL with POST request.
	 * @param url The URL to call.
	 * @param params A list of url parameters to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return Response text from POST request.
	 * @throws Exception If there are any problems.
	 */
	static String doPOST(String url, Map params, int readTimeout = TIME_OUT) throws Exception {
		String contentType = "application/x-www-form-urlencoded;charset=utf-8"
		return doPOSTHelper(url, null, null, convertParamMapToString(params), contentType, readTimeout, false)?.responseText
	}

	/**
	 * Calls the provided URL with POST request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return Response text from POST request.
	 * @throws Exception If there are any problems.
	 */
	static String doPOST(String url, String xml, int readTimeout = TIME_OUT) throws Exception {
		String contentType = "text/xml;charset=utf-8"
		return doPOSTHelper(url, null, null, xml, contentType, readTimeout, false)?.responseText
	}
	
	/**
	 * Calls the provided URL with POST request.
	 * @param url The URL to call.
	 * @param params A list of url parameters to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doPOSTResponse(String url, Map params, int readTimeout = TIME_OUT) throws Exception {
		String contentType = "application/x-www-form-urlencoded;charset=utf-8"
		return doPOSTHelper(url, null, null, convertParamMapToString(params), contentType, readTimeout, true)
	}

    /**
     * Calls the provided URL with POST request.
     * @param url The URL to call.
     * @param params A list of url parameters to be included in call.
     * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
     * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
     * @throws Exception If there are any problems.
     */
    static Map doPOSTResponse(String url, String apiUsername, String apiKey, Map params, int readTimeout = TIME_OUT) throws Exception {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8"
        return doPOSTHelper(url, apiUsername, apiKey, convertParamMapToString(params), contentType, readTimeout, true)
    }

	/**
	 * Calls the provided URL with POST request for a file.
	 * @param url The URL to call.
	 * @param file A file to post.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doPOSTResponse(String url, File file, int readTimeout = TIME_OUT) throws Exception {
		return doPOSTHelper(url, null, null, file, null, readTimeout, true)
	}
	// With apiUsername/apiKey
	static Map doPOSTResponse(String url, String apiUsername, String apiKey, File file, int readTimeout = TIME_OUT) throws Exception {
		return doPOSTHelper(url, apiUsername, apiKey, file, null, readTimeout, true)
	}
	
	/**
	 * Calls the provided URL with POST request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doPOSTResponse(String url, String xml, int readTimeout = TIME_OUT) throws Exception {
		String contentType = "text/xml;charset=utf-8"
        return doPOSTHelper(url, null, null, xml, contentType, readTimeout, true)
	}
	// With apiUsername/apiKey
	static Map doPOSTResponse(String url, String apiUsername, String apiKey, String xml, int readTimeout = TIME_OUT) throws Exception {
		String contentType = "text/xml;charset=utf-8"
        return doPOSTHelper(url, apiUsername, apiKey, xml, contentType, readTimeout, true)
	}

	/**
	 * Calls the provided URL with POST request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @param handleInputStreamException True if input stream exception should be caught and logged. False if it should be thrown.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	private static Map doPOSTHelper(String url, String apiUsername, String apiKey, Object obj, String contentType, int readTimeout, 
			boolean handleInputStreamException) throws Exception 
	{
		log.debug("Request URL: " + url)
        log.debug("Request obj: " + obj)
        log.debug "Read timeout $readTimeout"

		URL u = new URL(url)
        HttpURLConnection c = (HttpURLConnection) u.openConnection()
        c.setConnectTimeout(TIME_OUT)
        c.setReadTimeout(readTimeout)
        c.setDoInput(true)
        c.setDoOutput(true)
        c.setRequestMethod("POST")

        String authHeader = apiUsername && apiKey ? apiAuth.createAuthHeader(apiUsername, apiKey) : apiAuth.createAuthHeader()
		c.setRequestProperty("Authorization", authHeader)

        if ( !(obj instanceof File) ) {
			def byteArray = obj.getBytes("UTF8")
			
			if (contentType) {
				c.setRequestProperty("Content-Type", contentType)
			}
			c.setRequestProperty("Accept-Charset", "UTF-8")
			c.setRequestProperty("Content-Length", "" + byteArray.length)
		
			OutputStream out = c.getOutputStream()
            out.write(byteArray)
            out.flush()
            out.close()
        }
		else {
			String param = "value"
			String boundary = Long.toHexString(System.currentTimeMillis()) // Just generate some unique random value.
			String CRLF = "\r\n" // Line separator required by multipart/form-data.
			String charset = "UTF-8"
			
			c.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary)
			PrintWriter writer = null
			try {
				def blocksize = 4096 * 4
				c.setChunkedStreamingMode(blocksize)
				OutputStream output = c.getOutputStream()
				writer = new PrintWriter(new OutputStreamWriter(output, charset), true) // true = autoFlush, important!
			
				// Send binary file.
				writer.append("--" + boundary).append(CRLF)
				writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + obj.getName() + "\"").append(CRLF)
				writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(obj.getName())).append(CRLF)
				writer.append("Content-Transfer-Encoding: binary").append(CRLF)
				writer.append(CRLF).flush()
				
				InputStream input = null
				try {
					input = new FileInputStream(obj)
					byte[] buffer = new byte[blocksize]
					for (int length = 0; (length = input.read(buffer)) > 0;) {
						output.write(buffer, 0, length)
                    }
					output.flush()
				} 
				finally {
					try { input?.close() } catch (IOException logOrIgnore) {}
				}
				
				writer.append(CRLF).flush() // CRLF is important! It indicates end of binary boundary.
			
				// End of multipart/form-data.
				writer.append("--" + boundary + "--").append(CRLF)
			} 
			finally {
				writer?.close()
			}
		}

		int response = c.getResponseCode()
        log.debug("Response HTTP code: " + response)

        String responseXML = null
		// Some response codes throw an exception when trying to get input stream.
		// To handle, catch exceptions around getting input stream so that a valid
		//  map with a response code can be returned.
		try {
			responseXML = convertInputStreamToString(c.getInputStream())
		}
		catch (Exception e) {
			log.error "doPOSTHelper: InputStream exception: ", e
			if (!handleInputStreamException) {
				// Throw the exception after logging if it should be handled elsewhere
				throw e
			}
		}
		log.debug("Response XML: " + responseXML)

        return [responseText: responseXML, responseCode: response]
	}

	/***********************************/
	/*************** PUT ***************/
	/***********************************/

	/**
	 * Calls the provided URL with PUT request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return Response text from PUT request.
	 * @throws Exception If there are any problems.
	 */
	static String doPUT(String url, String xml, int readTimeout = TIME_OUT) throws Exception {
		return doPUTHelper(url, null, null, xml, readTimeout, false)?.responseText
	}
	
	/**
	 * Calls the provided URL with PUT request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	static Map doPUTResponse(String url, String xml, int readTimeout = TIME_OUT) throws Exception {
		return doPUTHelper(url, null, null, xml, readTimeout, true)
	}
	// With apiUsername/apiKey
	static Map doPUTResponse(String url, String apiUsername, String apiKey, String xml, int readTimeout = TIME_OUT) throws Exception {
		return doPUTHelper(url, apiUsername, apiKey, xml, readTimeout, true)
	}
	
	/**
	 * Calls the provided URL with PUT request.
	 * @param url The URL to call.
	 * @param xml XML text to be included in call.
	 * @param readTimeout Optional. Amount of time before throwing read timeout in ms.
	 * @param handleInputStreamException True if input stream exception should be caught and logged. False if it should be thrown.
	 * @return A map in the form: [responseText: String, responseCode: Integer (HttpURLConnection constant)]
	 * @throws Exception If there are any problems.
	 */
	private static Map doPUTHelper(String url, String apiUsername, String apiKey, String xml, int readTimeout, 
			boolean handleInputStreamException) throws Exception 
	{
		log.debug("Request URL: " + url)
        log.debug("Request XML: " + xml)
        log.debug("Timeout: "+ readTimeout)

		URL u = new URL(url)
        HttpURLConnection c = (HttpURLConnection) u.openConnection()
        c.setConnectTimeout(TIME_OUT)
        c.setReadTimeout(readTimeout)
        c.setDoInput(true)
        c.setDoOutput(true)
        c.setRequestMethod("PUT")
        c.setRequestProperty("Accept-Charset", "UTF-8")
		c.setRequestProperty("Content-Type", "text/xml;charset=utf-8")
		c.setRequestProperty("Content-Length", "" + xml.getBytes().length)

        String authHeader = apiUsername && apiKey ? apiAuth.createAuthHeader(apiUsername, apiKey) : apiAuth.createAuthHeader()
		c.setRequestProperty("Authorization", authHeader)

        OutputStream out = c.getOutputStream()
        out.write(xml.getBytes("UTF8"))
        out.flush()
        out.close()

        int response = c.getResponseCode()
        log.debug("Response HTTP code: " + response)

        String responseXML = null
		// Some response codes throw an exception when trying to get input stream.
		// To handle, catch exceptions around getting input stream so that a valid
		//  map with a response code can be returned.
		try {
			responseXML = convertInputStreamToString(c.getInputStream())
		}
		catch (Exception e) {
			log.error "doPUTHelper: InputStream exception: ", e
			if (!handleInputStreamException) {
				// Throw the exception after logging if it should be handled elsewhere
				throw e
			}
		}
		log.debug("Response XML: " + responseXML)

        return [responseText: responseXML, responseCode: response]
	}

	/***********************************/
	/************* HELPERS *************/
	/***********************************/
	
	private static String convertParamMapToString(Map params) {
        String paramString = ""
		params.each { param ->
			if (param.value) {
				paramString += (paramString ? "&" : "")
				paramString += param.key + "=" + param.value?.encodeAsURL()
			}
		}
        
        return paramString
	}
	
	private static String convertInputStreamToString(InputStream input) {
        StringBuffer out = new StringBuffer()
        byte[] b = new byte[4096]
		
        for (int n; (n = input.read(b)) != -1;) {
            out.append(new String(b, 0, n))
        }
		
        return out.toString()
    }
}
