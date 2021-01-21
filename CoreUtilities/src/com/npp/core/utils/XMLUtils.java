package com.pabslabs.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


public class XMLUtils {

	/*private static final Log log = LogFactory.getLog("com.pabslabs.core.utils.XMLUtils");
	
	public static String formatXML(InputStream in)
	{
		String xmlString = convertInputStreamToString(in);
		
		return formatXML(xmlString);
	}


	
	public static String formatXML(String xmlString)
	{
		String retval = "";
		
		try {
			StringReader sr = new StringReader(xmlString.toString());

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(java.lang.String publicId, java.lang.String systemId) throws SAXException, java.io.IOException {
					return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
				}
			});

			Document doc = builder.parse(new InputSource(sr));

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			//t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			//t.setOutputProperty("indent", "yes");
			StringWriter s = new StringWriter();
			StreamResult result = new StreamResult(s);
			DOMSource source = new DOMSource(doc);
			t.transform(source, result);
			retval = s.toString();
		} catch (Exception e) {
			log.warn("formatXML caught unexpected exception:  ", e);
		}

		return retval;
	}
	
	public static String convertInputStreamToString(InputStream in) {
		StringBuilder xmlString = new StringBuilder("");
		
		try {
			log.info("InputStream has " + in.available() + " bytes available");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String inputLine = null;
	
			while ((inputLine = br.readLine()) != null) {
				xmlString.append(inputLine);
			}
	
			log.debug("convertInputStreamToString : String from read: " + xmlString.toString());
		} catch (Exception e) {
			log.warn("convertInputStreamToString caught unexpected exception:  ", e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				log.error("convertInputStreamToString Couldn't close inputstream");
			}
		}
		
		return xmlString.toString();
	}*/
}
