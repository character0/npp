package groovy.util

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.commons.validator.UrlValidator

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * This class exists so UI can allow a user to enter a url wo/ specifying the protocol.
 * The grails url validator requires a protocol.  This class will add one if the user
 * entry does not contain one.
 */
class UrlUtil { 

	private static Log log = LogFactory.getLog("java.npp.UrlUtil");
	
    private static urlValidator = new UrlValidator()
	
	// These are taken from UrlValidator
	// They could not be inherited since they are private
	private static final String URL_REGEX = '^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?';
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final int PARSE_URL_SCHEME = 2;
	private static final String LEGAL_ASCII_REGEX = '^\\p{ASCII}+$';
    private static final Pattern ASCII_PATTERN = Pattern.compile(LEGAL_ASCII_REGEX);

	/**
	 * Determines if a url string is a valid address
	 * @param url URL to check
	 * @return true if url is valid, false otherwise
	 */
    public static boolean isValidUrl(String url) {
    	return urlValidator.isValid(url)
    }
    
	public static String addProtocol(String url) {
		try {
			// Try to get the protocol.  If there's an issue trying to get it, just return the 
			// orig url and let the call to isValidUrl throw a flag.
			
			// UrlValidator.isValid checks this before pulling the protocol
			if (!ASCII_PATTERN.matcher(url).matches()) {
				return url;
			}
			
			// UrlValidator.isValid checks this before pulling the protocol
			Matcher urlMatcher = URL_PATTERN.matcher(url);
			if (!urlMatcher.matches()) {
				return url;
			}
			
			// This is how UrlValidator.isValid pull the protocol
			String protocol = urlMatcher.group(PARSE_URL_SCHEME);
			log.debug "Protocol '${protocol}'"

			// If blank, then add http
			if (!protocol || protocol == "")
				url = "http://" + url
			
			return url;
			
		} catch (Exception e) {
			log.warn "Error attempting to add protocol - ${e.getMessage()}"
			return url;
		}
	}
	
	public static String getUrlFilename(String urlStr) {
      def indexOfParams = urlStr.indexOf("?")
      if (indexOfParams > 0) {
        urlStr = urlStr[0..indexOfParams-1]
      }
      return urlStr[urlStr.lastIndexOf('/')+1..urlStr.length()-1]
	}
	
	public static String getUrlFileParent(String urlStr) {
      def indexOfParams = urlStr.indexOf("?")
      if (indexOfParams > 0) {
        urlStr = urlStr[0..indexOfParams-1]
      }
   	  return urlStr[0..urlStr.lastIndexOf('/')]
	}
	
}