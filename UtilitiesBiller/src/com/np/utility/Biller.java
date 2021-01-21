package com.np.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;


/**
 * @author NPP
 *
 */
public class Biller {

	private Properties properties;

	private Bill iBill = null;

	private String to = "";
	
	private static final String CC = "nickpanahi@hotmail.com";

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";

	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	private static final String PASSWORD = "1automobills";
	private static final String LOGIN = "1362.brookhaven.village.circle";

	private static final String NEWLINE = "\n";
	
	private int offset = 0;

	private SimpleDateFormat sdf = new SimpleDateFormat("MMM ''yy");

	private static Logger log = Logger.getLogger(Biller.class.getPackage().getName());

	/*********************************CONSTRUCTOR*******************************/

	public Biller() {
		super();
	}

	/***********************************CORE************************************/

	private void sendMail(String subject, String body, String recipients) 
	throws MessagingException {

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		log.debug("Creating SMTP message");		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class",SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{ return new PasswordAuthentication(LOGIN,PASSWORD);	}
		});		

		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress("nickpanahi@gmail.com"));
		message.setFrom(new InternetAddress("nickpanahi@gmail.com"));
		message.setSubject(subject);
		message.setContent(body, "text/plain");

		if (recipients.indexOf(',') > 0) { 
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
		} else {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
		}

		message.setRecipient(Message.RecipientType.CC, new InternetAddress(CC));
		
		log.debug("Sending message");		
		Transport.send(message);

	}

	/*********************************HELPER METHODS*******************************/

	private float floatMe(String anyString) {
		return Float.valueOf(anyString).floatValue();
	}


	private String createSubject() {
		String toRet = null;

		Calendar lastMonth = Calendar.getInstance();
		lastMonth.set(Calendar.MONTH, lastMonth.get(Calendar.MONTH) - offset );

		toRet = sdf.format(lastMonth.getTime());

		return toRet + " Utility Bills";
	}


	private String formatBody(float powerTotal, float gasTotal, 
			float h2OTotal, float cableTotal) {
		
		if(h2OTotal<0){
			h2OTotal = 0;
		}
		
		String iTotal = this.iBill.computeITotal(powerTotal, gasTotal, h2OTotal, cableTotal);

		String formattedBody = "";

		formattedBody += NEWLINE;
		formattedBody += "Power: " + " $" + powerTotal + NEWLINE;
		formattedBody += "Gas: " + " $" + gasTotal + NEWLINE;
		formattedBody += "Water: " + "$" + h2OTotal + NEWLINE;
		formattedBody += "Cable & Internet: " + "$" + cableTotal + NEWLINE;
		formattedBody += NEWLINE;
		formattedBody += "Total: " + " $" + this.iBill.computeTotalBills(powerTotal, gasTotal, h2OTotal, cableTotal) + NEWLINE;
		formattedBody += "Divided by " + this.iBill.household + ":" + " $" + iTotal + NEWLINE;
		formattedBody += NEWLINE;
		formattedBody += "You owe $" + iTotal + " for " + createSubject();

		return formattedBody;
	}


	/**
	 * Initializes
	 * @param file
	 * @throws IOException
	 */
	private void initialize(FileInputStream file, Biller biller) throws IOException {

		biller.iBill = new Bill();
		
		log.debug("Initializing attributes from properties file");
		properties = System.getProperties();
		properties.load(file);
		file.close();

		to = properties.getProperty("to");
		
		log.info("Creating new Bill for " + this.createSubject());
		biller.iBill.powerTotal = Float.valueOf(properties.getProperty("power")).floatValue();
		biller.iBill.gasTotal = Float.valueOf(properties.getProperty("gas")).floatValue();
		biller.iBill.cableTotal = Float.valueOf(properties.getProperty("cable")).floatValue();
		biller.iBill.h2OTotal = properties.getProperty("water", "NA");
		offset = Integer.valueOf(properties.getProperty("offset")).intValue();

		biller.iBill.household = Integer.valueOf(properties.getProperty("household")).intValue();

	} // end initialize

	/***********************************MAIN METHOD*******************************/

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Biller b = new Biller();
		
		if (args.length < 1) {
			System.out.println("Usage: java Biller <file>");
			System.exit(0);
		} else {

			FileInputStream fis = null;

			try {

				fis = new FileInputStream(args[0]);
				log.info("Iniziatializing Biller");
				b.initialize(fis, b);
				
			} catch (IOException io) {
				log.fatal("Unable to load configuration file", io);
				System.exit(1);
			}
		}

		float h2O = -1;		

		if(!b.iBill.h2OTotal.equalsIgnoreCase("na")){
			h2O = b.floatMe(b.iBill.h2OTotal);
		} else {
			h2O = -1;
		}
		
		log.debug("Creating a message subject");
		String subject = b.createSubject();

		log.debug("Creating a message body");
		String body = b.formatBody(b.iBill.powerTotal, b.iBill.gasTotal, h2O, b.iBill.cableTotal);
		log.info("Message Body: " + body);

		try {
			log.info("Sending " + subject + " to " +  b.to);
			b.sendMail(subject, body, b.to);

		} catch (MessagingException e) {
			log.fatal("Exception sending " + subject + ", bill was not delivered");
			e.printStackTrace();
		}

	} //end MAIN

}