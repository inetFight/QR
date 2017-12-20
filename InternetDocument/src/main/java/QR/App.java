package QR;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.Synthesizer;

public class App {

	public static void main(String[] args) throws Exception {
		PrintWriter writerResult = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("D://QR//Doc//Log.csv", true), "UTF-8"));
		ArrayList<WarehouseModel> warehouse =  generateQR.getAllWarehouses();

		ArrayList<EmailModel> heads = ReadFileHeadOffice.readFileToListByLine();
		
			
		    for (int j = 0; j < warehouse.size(); j++) {
		    	ArrayList<String> recipientsMail = new ArrayList<String>();
		    	
		    	for (int i = 0; i < heads.size(); i++) {
		    		
		    		if(heads.get(i).getRef().equals(warehouse.get(j).getRef())){
//			    		System.out.println(warehouse.get(j).getRef() + " - " + warehouse.get(j).getCity() + ", " + warehouse.get(j).getDescription());
		    			
		    			recipientsMail.add(heads.get(i).getEmail());
			    	}
				}
		    	
			
			
		  
		    
		    String shortUrl = getShortUrl.getShortURLFromGoogle("https://ap69r.app.goo.gl/?link=https://novaposhta/queue/?warehouse_ref=" + warehouse.get(j).getRef() + "&apn=ua.novaposhtaa&isi=545980774&ibi=com.novaposhta.novaposhta&ius=novaposhta");
			generateQR.generateQRcode(shortUrl, 793, warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big");
			generateQR.generateQRcode(shortUrl, 559, warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small");

			

			String file1url = "D:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png";
			String file2url = "D:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small" + ".png";
			String file1name = warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png";
			String file2name = warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - small" + ".png";

			new sendMail().test(file1url,file1name,file2url,file2name,recipientsMail);
			break;
		   
		}

	

		
	}

}
