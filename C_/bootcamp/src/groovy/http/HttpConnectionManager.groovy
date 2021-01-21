package groovy.http

import org.apache.http.impl.conn.PoolingClientConnectionManager
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.CoreConnectionPNames

public enum HttpConnectionManager {
	INSTANCE

	private PoolingClientConnectionManager connectionManager = null
	DefaultHttpClient client = null

	private HttpConnectionManager() {

        Integer maxTotalConnections = 100
        // Sets the maximum number of connections allowed per host. Must be <= total maximum connections.
        Integer maxPerHostConnections = 100
		// Determines the timeout in milliseconds until a connection is established. A timeout value of zero is
		// interpreted as an infinite timeout. This parameter expects a value of type java.lang.Integer.
        Integer connectionTimeout = 30000
        // Defines the socket timeout (SO_TIMEOUT) in milliseconds, which is the timeout for waiting for data or,
        // put differently, a maximum period inactivity between two consecutive data packets). A timeout value of zero is interpreted as an infinite timeout. This parameter expects a value of type java.lang.Integer.
		Integer socketTimeout = 30000
        // Determines whether stale connection check is to be used. Disabling stale connection check may result in a
        // noticeable performance improvement (the check can cause up to 30 millisecond overhead per request) at the
        // risk of getting an I/O error when executing
		Boolean staleCheckingEnabled = true
		
		BasicHttpParams params = new BasicHttpParams()
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout)
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout)
		params.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, staleCheckingEnabled)
		
		connectionManager = new PoolingClientConnectionManager()
		connectionManager.setMaxTotal(maxTotalConnections)
		connectionManager.setDefaultMaxPerRoute(maxPerHostConnections)
		
		client = new DefaultHttpClient(connectionManager, params)
	}
}