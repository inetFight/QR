package QR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.internet.InternetAddress;
import javax.sound.midi.Synthesizer;

import org.apache.commons.validator.routines.EmailValidator;

public class App {

	public static void main(String[] args) throws Exception {
		PrintWriter writerResult = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("E://QR//Doc//LogNoHead.csv", true), "UTF-8"));
		PrintWriter writerResultMails = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("E://QR//Doc//LogSendMails.csv", true), "UTF-8"));
		PrintWriter writerResultNoSendMails = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("E://QR//Doc//LogSendMailsError.csv", true), "UTF-8"));
		ArrayList<WarehouseModel> warehouse =  generateQR.getAllWarehouses();
//		HashMap<String, String> check = new HashMap<String, String>();
		ArrayList<EmailModel> heads = ReadFileHeadOffice.readFileToListByLine();
		System.out.println(warehouse.size());

		    for (int j = 1600; j < 1800; j++) {
		    	System.out.println(j);
		    	if(j % 5 == 0) {
		    		Thread.sleep(4000);
		    	}
		    	ArrayList<String> recipientsMail = new ArrayList<String>();
//		    	recipientsMail.add("litvinov.do@novaposhta.ua");
//		    	recipientsMail.add("kucher.vg1@novaposhta.ua");
//		    	recipientsMail.add("danylik.a@novaposhta.ua");
		    	boolean check = false;
		    	for (int i = 0; i < heads.size(); i++) {
		    		
		    		if(heads.get(i).getRef().equals(warehouse.get(j).getRef()) && EmailValidator.getInstance().isValid(heads.get(i).getEmail())){
			    		
		    			recipientsMail.add(heads.get(i).getEmail());
		    			check = true;
			    	}

				}
		    	if(!check){
		    	writerResult.write(warehouse.get(j).getRef() + ";" + warehouse.get(j).getCity() + ";" + warehouse.get(j).getNumber() + ";" + warehouse.get(j).getDescription());
		    	writerResult.write(System.lineSeparator());
		    	writerResult.flush();
		    	}
			
			
		  
		    if(new File("E:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big.png").exists() && 
		       new File("E:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small.png").exists()){
		    	
		    
//		    String shortUrl = getShortUrl.getShortURLFromGoogle("https://ap69r.app.goo.gl/?link=https://novaposhta/queue/?warehouse_ref=" + warehouse.get(j).getRef() + "&apn=ua.novaposhtaa&isi=545980774&ibi=com.novaposhta.novaposhta&ius=novaposhta");
//			generateQR.generateQRcode(shortUrl, 793, warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big");
//			generateQR.generateQRcode(shortUrl, 559, warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small");
//
//			
//
			String file1url = "E:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png";
			String file1name = warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png";
			
			String file2url = "E:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small" + ".png";			
			String file2name = warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small" + ".png";
//
			try {
				new sendMail().test(file1url,file1name,file2url,file2name,recipientsMail, warehouse.get(j));
					writerResultMails.write(file1url + ";" + file2url);
				for (int i = 0; i < recipientsMail.size(); i++) {
					writerResultMails.write(";" + recipientsMail.get(i));
				}
				writerResultMails.write(";" + warehouse.get(j).getRef() + ";" + warehouse.get(j).getCity() + ";" + warehouse.get(j).getNumber() + ";" + warehouse.get(j).getDescription());
				writerResultMails.write(System.lineSeparator());
				writerResultMails.flush();

			} catch (Exception e) {
				e.printStackTrace();
				writerResultNoSendMails.write(e.getMessage() + ";" + warehouse.get(j).getRef() + ";" + warehouse.get(j).getCity() + ";" + warehouse.get(j).getNumber() + ";" + warehouse.get(j).getDescription());
				for (int i = 0; i < recipientsMail.size(); i++) {
					writerResultNoSendMails.write(";" + recipientsMail.get(i));
				}
				writerResultNoSendMails.write(System.lineSeparator());
				writerResultNoSendMails.flush();

			}
			
			
		    }
		    
		}


		
	}

}
