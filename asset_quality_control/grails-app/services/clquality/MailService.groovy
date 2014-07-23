package clquality

import javax.mail.*;
import javax.mail.internet.*
import clsetting.Setting
import settings.SettingNames

class MailService {

    static transactional = true

    def sendMail(String subject, String body) {

        def recipients = Setting.getSetting(SettingNames.RECIPIENTS)
        def smtpHost = Setting.getSetting(SettingNames.SMTP_HOST)
        def doEmail = Setting.getSetting(SettingNames.SEND_EMAIL)

        if (doEmail) {
            // Javamail overhead
            Properties sysprops = System.getProperties();
            sysprops.put("mail.smtp.host", smtpHost);
            Session session = Session.getDefaultInstance(sysprops, null);

            // Build and send the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply-dataops@clearleap.com"))

            recipients.split(",").each {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(it));
            }

            message.setSubject(subject);
            message.setText(body)

            log.info "sendMail - Sending email '$body' to '$subject'"

            Transport.send(message)
        }
    }

}
