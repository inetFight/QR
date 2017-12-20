package QR;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadFileHeadOffice {
	
	public static ArrayList readFileToListByLine() {
		String csvFile = "E:\\QR\\Doc\\Mails.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
//		HashMap<String, String> map = new HashMap();
		ArrayList<EmailModel> tmp = new ArrayList<EmailModel>();
		int cnt = 0;
		int cntFail = 0;
		 System.out.println("Загружаю базовую информацию из файла");
		try {
			 br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF-8"));
//			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] EH = line.split(cvsSplitBy);
				String Ref = EH[0];
				String Mail = EH[3];
				EmailModel obj = new EmailModel(Ref, Mail);
//				map.put(Ref, Mail);

				tmp.add(obj);
				
					cnt += 1;

		
				
					

					
					
				

				
			}
//			System.out.println("В файле найдено " + cnt + " строчек, загрузка успешна. В коллекции " + heads.size() );

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 System.out.println("В файле найдено " + cnt + " строчек, загрузка успешна. В коллекции " + tmp.size() +  " fails " + cntFail);
		return tmp;
	}

}
