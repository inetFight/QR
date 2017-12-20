package QR;
import java.util.ArrayList;
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
import javax.mail.internet.MimeUtility;

public class sendMail {
	
	public void test(String fileURL, String filename, String fileURL2, String filename2, ArrayList<String> recipient) throws Exception{
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


        
        message.setFrom(new InternetAddress("litvinov.do@novaposhta.ua"));
        for (int i = 0; i < recipient.size(); i++) {
        	message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient.get(i)));
		}
        
      //Первый файл
        MimeBodyPart messageBodyPart = new MimeBodyPart();        
        Multipart multipart = new MimeMultipart();        
        messageBodyPart = new MimeBodyPart();
        String file = fileURL;
        String fileName = filename;
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(MimeUtility.encodeText(fileName));
        //второй файл
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        Multipart multipart2 = new MimeMultipart();
       
        messageBodyPart2 = new MimeBodyPart();
        String file2 = fileURL2;
        String fileName2 = filename2;
        DataSource source2 = new FileDataSource(file2);
        messageBodyPart2.setDataHandler(new DataHandler(source2));
        messageBodyPart2.setFileName(MimeUtility.encodeText(fileName2));
        
        multipart2.addBodyPart(messageBodyPart2);
        
        
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("Распечатать и наклеить");
        multipart.addBodyPart(textBodyPart);

        
        message.setContent(multipart);
        message.setContent(multipart2);
        
        
        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        System.out.println("Mail send");
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "11111";
           String password = "11111";
           return new PasswordAuthentication(username, password);
        }
    }
}
