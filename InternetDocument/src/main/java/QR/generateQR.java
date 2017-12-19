package QR;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class generateQR {

	public static void generateQRcode (String QR, int sizeQR, String nameQR){

		String myCodeText = QR;
		String filePath = "E:\\QR\\" + nameQR + ".png";
		int size = sizeQR;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 0); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, 
					BarcodeFormat.QR_CODE, 
					size,
					size, 
					hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nYou have successfully created QR Code for " + QR);
	}
	
		


	
	
	public static ArrayList getAllWarehouses() throws URISyntaxException, ClientProtocolException, IOException, ParseException{
		ArrayList<WarehouseModel> warehouses =  new ArrayList<WarehouseModel>();
//		HashMap <String, String> warehouses = new HashMap();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://api.novaposhta.ua/v2.0/json/");
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		StringEntity reqEntity = new StringEntity("{\"modelName\": \"AddressGeneral\","
												+ "\"calledMethod\": \"getWarehouses\"}", "UTF-8");

			
	
		request.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(request);
		String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);
		JSONArray data = (JSONArray) jsonObject.get("data");
		Iterator i = data.iterator();
		while(i.hasNext()){
			JSONObject obj = (JSONObject) i.next();
			String TypeOfWarehouse = (String)obj.get("TypeOfWarehouse");
			if(TypeOfWarehouse.equals("95dc212d-479c-4ffb-a8ab-8c1b9073d0bc") || TypeOfWarehouse.equals("f9316480-5f2d-425d-bc2c-ac7cd29decf0")){
				continue;
			}
			WarehouseModel model = new WarehouseModel((String) obj.get("Ref"), (String) obj.get("Description"), (String) obj.get("Number"), (String) obj.get("CityDescription"));
			warehouses.add(model);
		}
		return warehouses;
		
	}
	
}
