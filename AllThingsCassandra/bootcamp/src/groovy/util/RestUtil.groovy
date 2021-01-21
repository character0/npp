package groovy.util

import groovy.http.HttpConnectionManager
import groovy.http.RestResponse

import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.FileEntity
import org.apache.http.entity.StringEntity
import org.apache.http.params.HttpConnectionParams
import org.apache.http.util.EntityUtils
import org.apache.log4j.Logger

class RestUtil {

    private static Logger log = Logger.getLogger(this)

    // ************************************** START GET ************************************** //

	public static RestResponse doGet(String url) {
        log.debug "doGet: Starting with url: ${url}"
        HttpGet httpget = new HttpGet(url)
        return doGetHelper(httpget, url)
	}

    private static RestResponse doGetHelper(HttpGet httpget, String url) {
        HttpResponse response
        RestResponse restResponse = new RestResponse()
        try {
            HttpClient client = HttpConnectionManager.INSTANCE.client
            response = client.execute(httpget)
            restResponse.responseCode = response.getStatusLine().getStatusCode()
            restResponse.responseText = EntityUtils.toString(response.getEntity())
        } catch (Exception e) {
            log.error "doGet: Ran into exception:\n", e
            restResponse.responseText = e.message
        } finally {
            try { httpget.reset() }
            catch(Exception ignore) { }
        }

        if (restResponse.responseCode != HttpStatus.SC_OK) {
			log.info "doGet: Response code was ${restResponse.responseCode} at url: ${url}\nresponseText: ${restResponse.responseText}"
        }

        log.debug "doGet: restResponse: ${restResponse}"
        return restResponse

    }

    // ************************************** END GET ************************************** //


    // ************************************** POST ************************************** //

    public static RestResponse doPost(String url) {

        HttpPost httppost = new HttpPost(url)
        return doPostHelper(httppost)
    }

    public static RestResponse doPost(String url, String mimeType, String charset, String data) {

        HttpPost httppost = new HttpPost(url)
        StringEntity entity = new StringEntity(data, mimeType, charset)
        httppost.setEntity(entity)

        return doPostHelper(httppost)
    }

    public static RestResponse doPost(String url, File file) {

        HttpPost httppost = new HttpPost(url)
        FileEntity entity = new FileEntity(file, "binary/octet-stream")
        entity.setChunked(true)
        httppost.setEntity(entity)

        return doPostHelper(httppost)
    }

	private static RestResponse doPostHelper(HttpPost httppost) {
		RestResponse restResponse = new RestResponse()
        HttpResponse response
        try {
            HttpClient client = HttpConnectionManager.INSTANCE.client
            HttpConnectionParams.setSoTimeout(client.getParams(), 0);
            response = client.execute(httppost)
            restResponse.responseCode = response.getStatusLine().getStatusCode()
            restResponse.responseText = EntityUtils.toString(response.getEntity())
        } catch (Exception e) {
			log.error "doPost(String): Ran into exception:\n", e
            restResponse.responseText = e.message
        } finally {
            try { httppost.reset() }
			catch(Exception ignore) { }
        }

        if (restResponse.responseCode != HttpStatus.SC_OK) {
			log.info "doPost: Response code was ${restResponse.responseCode}\nresponseText: ${restResponse.responseText}\ndata: ${httppost?.entity?.content}"
        }
		
		log.debug "doPost: restResponse: ${restResponse}"
        return restResponse
	}

    // ************************************** END POST ************************************** //


    // ************************************** PUT ************************************** //

    public static RestResponse doPut(String url, String mimeType, String charset, String data) {

        HttpPut httpput = new HttpPut(url)
        StringEntity entity = new StringEntity(data, mimeType, charset)
        httpput.setEntity(entity)

        return doPutHelper(httpput)
    }

    private static RestResponse doPutHelper(HttpPut httpput) {
		RestResponse restResponse = new RestResponse()
        HttpResponse response
        try {
            HttpClient client = HttpConnectionManager.INSTANCE.client
            response = client.execute(httpput)
            restResponse.responseCode = response.getStatusLine().getStatusCode()
            restResponse.responseText = EntityUtils.toString(response.getEntity())
        } catch (Exception e) {
            log.error "doPut: Ran into exception:\n", e
            restResponse.responseText = e.message
        } finally {
            try { httpput.reset() }
            catch(Exception ignore) { }
        }

        if (restResponse.responseCode != HttpStatus.SC_OK) {
			log.info "doPut: Response code was ${restResponse.responseCode}:\nresponseText: ${restResponse.responseText}"
        }

        log.debug "doPut: restResponse: ${restResponse}"
        return restResponse
    }

    // ************************************** END PUT ************************************** //


    // ************************************** DELETE ************************************** //

    public static RestResponse doDelete(String url) {
        HttpDelete httpdelete = new HttpDelete(url)
        return doDeleteHelper(httpdelete)
    }

	private static RestResponse doDeleteHelper(HttpDelete httpdelete) {
		RestResponse restResponse = new RestResponse()
        HttpResponse response
        try {
            HttpClient client = HttpConnectionManager.INSTANCE.client
            response = client.execute(httpdelete)
            restResponse.responseCode = response.getStatusLine().getStatusCode()
            restResponse.responseText = EntityUtils.toString(response.getEntity())
        } catch (Exception e) {
			log.error "doDelete: Ran into exception:\n", e
            restResponse.responseText = e.message
        } finally {
            try { httpdelete.reset() }
			catch(Exception ignore) { }
        }

        if (restResponse.responseCode != HttpStatus.SC_OK) {
			log.info "doDelete: Response code was ${restResponse.responseCode}\nresponseText: ${restResponse.responseText}"
        }
		
		log.debug "doDelete: restResponse: ${restResponse}"
        return restResponse
	}

    // ************************************** END DELETE ************************************** //
}