package QR;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class sendMail {
	
	public void test(String fileURL, String filename, String recipient) throws Exception{
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.novaposhta.ua");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Testing Subject");
//        message.setText("ууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууууу");

        
        message.setFrom(new InternetAddress("litvinov.do@novaposhta.ua"));
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(recipient));
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();
        
        messageBodyPart = new MimeBodyPart();
        String file = fileURL;
        String fileName = filename;
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
        
        
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("Распечатать и наклеить");
        multipart.addBodyPart(textBodyPart);
        
        
        message.setContent(multipart);
        
        
        
        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        System.out.println("Mail send");
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "litvinov.do@novaposhta.ua";
           String password = "QW0500481222";
           return new PasswordAuthentication(username, password);
        }
    }
}
