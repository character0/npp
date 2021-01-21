package com.pabslabs.core.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 * The purpose of this class it to help with storing and displaying XML input/output that contains new lines
 */
class NewLineUtil {

	private static Log log = LogFactory.getLog("com.pabslabs.core.utils.NewLineUtil")
	
	/*
	 * Encodes a string with new lines for HTML
	 */
	static String encodeAsHtml(String text, boolean preEncodeAsHtml = true) {
		String retStr = text
		 
		if (text) {
			// First encode all characters as HTML before trying to add <br> tags
			if (preEncodeAsHtml) {
				retStr = text.encodeAsHTML()
			}
			
			// A text area will save new lines as '\r\n' rather than simply '\n'
			
			// Need to cover the '\r\n' case first
			retStr = retStr.replaceAll("[\r][\n]", "<br/>")
			
			// Next, cover the '\n' case
			retStr = retStr.replaceAll("[\n]", "<br/>")
		}
		
		return retStr
	}
	
	/*
	 * Encodes a string with new lines for XML
	 */
	static String encodeAsXml(String text) {
		String retStr = ""
		 
		if (text) {
			// Convert all '\r' characters
			retStr = text.replaceAll("[\r]", "&#x000D;")
			
			// Convert all '\n' characters
			retStr = retStr.replaceAll("[\n]", "&#x000A;")
		}
		
		return retStr
	}
	
	/*
	 * Encodes a string with new lines taken from XML into a string with escape characters
	 */
	static String encodeAsString(String xmlText) {
		String retStr = ""
		 
		if (xmlText) {
			// Convert all '\r' characters
			retStr = xmlText.replaceAll("&#x000D;", "\r")
			retStr = retStr.replaceAll("&#xD;", "\r")
			
			// Convert all '\n' characters
			retStr = retStr.replaceAll("&#x000A;", "\n")
			retStr = retStr.replaceAll("&#xA;", "\n")
		}
		
		return retStr
	}
	
	/*
	 * Cleans up double encoded new lines in XML
	 */
	static String cleanDoubleEncodes(String xmlText) {
		String retStr = ""
		 
		if (xmlText) {
			// Clean all double encoded '\r' characters
			retStr = xmlText.replaceAll("&amp;#x000D;", "&#x000D;")
			retStr = retStr.replaceAll("&amp;#xD;", "&#x000D;")
			
			// Clean all double encoded  '\n' characters
			retStr = retStr.replaceAll("&amp;#x000A;", "&#x000A;")
			retStr = retStr.replaceAll("&amp;#xA;", "&#x000A;")
		}
		
		return retStr
	}
}