package QR;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getShortUrl {
	public static String getShortURLFromGoogle(String longUrl) throws URISyntaxException, ClientProtocolException, IOException, ParseException{
		
		ArrayList<String> warehouses =  new ArrayList<String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://www.googleapis.com/urlshortener/v1/url");
		builder.addParameter("key", "AIzaSyCV-TQ12DyCmUk3rCsclLQsKA0D4tMhTo0");
		builder.addParameter("quotaUser", "me");
		builder.addParameter("userIp", "10.233.102.64");
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);

		
		request.addHeader("Content-Type", "application/json");
		StringEntity reqEntity = new StringEntity("{\"longUrl\": \"" + longUrl+ "\"}");
		reqEntity.setContentType("application/json");
		request.setEntity(reqEntity);
		
		HttpResponse response = httpclient.execute(request);
		
		String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
//		System.out.println(otvet);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);
		String shortUrl = (String) jsonObject.get("id");
//		System.out.println(shortUrl);
		return shortUrl;
		
	}

}
