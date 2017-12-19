package QR;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class App {

	public static void main(String[] args) throws Exception {
		PrintWriter writerResult = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("E://QR//Doc//Log.csv", true), "UTF-8"));
		ArrayList<WarehouseModel> warehouse =  generateQR.getAllWarehouses();
		HashMap mails = ReadFileHeadOffice.readFileToListByLine();
//		ArrayList<EmailModel> heads = ReadFileHeadOffice.readFileToListByLine();
		
			
		    for (int j = 0; j < warehouse.size(); j++) {
		    	if(mails.get(warehouse.get(j).getRef()) == null){
		    		System.out.println(warehouse.get(j).getRef() + " - " + warehouse.get(j).getCity() + ", " + warehouse.get(j).getDescription());
		    	}
			
			
		  
		    
		    String shortUrl = getShortUrl.getShortURLFromGoogle("https://ap69r.app.goo.gl/?link=https://novaposhta/queue/?warehouse_ref=" + warehouse.get(j).getRef() + "&apn=ua.novaposhtaa&isi=545980774&ibi=com.novaposhta.novaposhta&ius=novaposhta");
			generateQR.generateQRcode(shortUrl, 793, warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big");
//			generateQR.generateQRcode(shortUrl, 559, warehouse.get(j).getCity()  + ", №" + warehouse.get(j).getNumber() + "- small");

			
//			new sendMail().test("E:\\QR\\" + warehouse.get(j).getRef() + ".png", warehouse.get(j).getRef() + ".png", "danylik.a@novaposhta.ua");
//			new sendMail().test("E:\\QR\\" + ref + ".png", ref + ".png", mail);
			new sendMail().test("E:\\QR\\" + warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png", warehouse.get(j).getCity() + ", №" + warehouse.get(j).getNumber() + " - big" + ".png", "litvinov.do@novaposhta.ua");
			break;
		   
		}

	

		
	}

}
