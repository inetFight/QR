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
	
	public void test(String fileURL, String filename, String fileURL2, String filename2, ArrayList<String> recipient, WarehouseModel warehouse) throws Exception{
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.novaposhta.ua");
//        props.put("mail.smtp.auth", "true");


        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("QR-код для акції «Сфотографуйте чергу – отримайте промокод на знижку");


        
        message.setFrom(new InternetAddress("kucher.vg1@novaposhta.ua"));
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
        multipart.addBodyPart(messageBodyPart);
        
        
        String file2 = fileURL2;
        String fileName2 = filename2;
        MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
        DataSource source2 = new FileDataSource(file2); 
        messageBodyPart2.setDataHandler( new DataHandler(source2)); 
        messageBodyPart2.setFileName(MimeUtility.encodeText(fileName2)); 
        multipart.addBodyPart(messageBodyPart2); 

       
        
        
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("Доброго дня, відділення №" + warehouse.getNumber() + " м. " + warehouse.getCity()
        				    + "\n\nЦей лист ви отримали згідно з Розпорядженням про акцію «Сфотографуйте чергу – отримайте промокод на знижку». "
        				    + "Розпорядження та Додаток 1 до нього регламентують, що всі Відділення із переліку в Додатку 1 беруть участь в акції «Сфотографуйте чергу – отримайте промокод на знижку»."
        				    + "\n\nВпродовж п'ятниці 22.12.2017 на ваше Відділення мають надійти нові рекламні постери на заміну постерів \"SMS про чергу\". Постер може бути розміром А1 або А2."
        				    + "\n\nУ вкладенні до цього електронного листа ви знайдете два файли формату png із назвами \""+ warehouse.getCity() + ", №" + warehouse.getNumber() + " - big.png\" та \""+ warehouse.getCity() + ", №" + warehouse.getNumber() + " - small.png\" із зображеннями QR-кодів."
        				    + "\n\nВам необхідно роздрукувати той файл з QR-кодом, що відповідає розміру постеру, який отримало Відділення: великий QR-код \""+ warehouse.getCity() + ", №" + warehouse.getNumber() + " - big.png\" для постеру А1, малий QR-код \""+ warehouse.getCity() + ", №" + warehouse.getNumber() + " - small.png\" для постеру А2. На постері в полі для QR-коду додатково розміщено підказку. Далі вам потрібно вирізати та вклеїти QR-код у відповідне поле на постері."
        				    + "\n\nТакож в Додатку 1 ви знайдете ваш індивідуальний код для sms із чотирьох цифр. Його необхідно вписати у Додаток 6 (файл формату pdf) та роздрукувати на аркуші «папір А4 самоклейка 8 етикеток (105*74мм)». У разі його відсутності, роздрукувати на звичайному папері. Далі вирізати його та наклеїти на постер на спеціально відведене місце на постері."
        				    + "\n\nЯкщо ви отримали цього електронного листа, але не знайшли себе у переліку з Додатку 1 до Розпорядження - цей лист надіслано вам помилково. Ваше Відділення не бере участі в акції, вам не потрібно здійснювати жодних дій за згаданою акцією."
        				    + "\n\nЯкщо ви знайшли себе в переліку з Додатку 1 до Розпорядження, але з якихось причин не отримали постера - напишіть про це Валерії Кучер (менеджер з розробки цифрових продуктів) на kucher.vg1@novaposhta.ua та отримайте від неї подальші інструкції."
        				    + "\n\nБажаємо наснаги!"
        				    + "\n\n\nВалерія Кучер,\nClient Mobile Application Nova Poshta"

        );

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
           String username = "1111";
           String password = "1111";
           return new PasswordAuthentication(username, password);
        }
    }
}
