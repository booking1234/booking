package net.cbtltd.json.booknew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import net.cbtltd.json.JSONService;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;

import com.google.gson.Gson;

public class ReservationPaymentHandlerTest {
	ReservationPaymentHandler handler = new ReservationPaymentHandler();
	static JSONService jsonService = new JSONService();
	private static final Gson GSON = new Gson();	
//	public static void main(String[] args) throws ServletException {
//		jsonService.init();
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//
//		parameters.put("service", JSONRequest.RESERVATION_PAYMENT.name());
//		parameters.put("pos", Model.encrypt("34575")); 					// the point of sale code.
//		parameters.put("productid", "270"); 		// the ID of the property
//		parameters.put("fromdate", "2014-01-05");			// the date from which availability is to be shown.
//		parameters.put("todate", "2014-01-06");				// the date to which availability is to be shown.
//		parameters.put("emailaddress", "melnikov.roman@gmail.com"); 	// the email address of the guest.
//		parameters.put("familyname", "Melnykov");		// the family name of the guest.
//		parameters.put("firstname", "Roman");			// the first name of the guest.
//		parameters.put("notes", "Hello!");					// the guest notes or requests.
//		parameters.put("cardnumber", "4446283280247005");		// the card number.
//		parameters.put("cardmonth", "11");	// the card expiry month.
//		parameters.put("cardyear", "2018");		// the card expiry year.
//		parameters.put("amount", "12");				// the amount to be charged to the card.
//		parameters.put("cardtype", "visa");
//		
//		String jsonString = GSON.toJson(jsonService.dispatch(parameters));
//		System.out.println(jsonString);
//	}
	
	public static void main(String[] args) throws Throwable {
		HashMap<String, String> parameters = new HashMap<String, String>();
		// http://localhost:8080/service/JSONService?callback=jQuery1710022335249445987637_1386869538700&service=reservation_payment&pos=d9b0dc2061815e25&
		// productid=270&fromdate=2013-12-12&todate=2013-12-12&emailaddress=melnikov.roman%40gmail.com&familyname=Melnykov&firstname=Roman&notes=Hi!&
		// cardnumber=4446283280247004&cardmonth=11&cardyear=2018&cardtype=visa&_=1386939698839
		parameters.put("callback","jqury");
		parameters.put("service", JSONRequest.RESERVATION_PAYMENT.name());
		parameters.put("pos", Model.encrypt("34575")); 					// the point of sale code.
		parameters.put("productid", "270"); 		// the ID of the property
		parameters.put("fromdate", "2014-01-09");			// the date from which availability is to be shown.
		parameters.put("todate", "2014-01-10");				// the date to which availability is to be shown.
		parameters.put("emailaddress", "melnikov.roman@gmail.com"); 	// the email address of the guest.
		parameters.put("familyname", "Melnykov");		// the family name of the guest.
		parameters.put("firstname", "Roman");			// the first name of the guest.
		parameters.put("notes", "Hello!");					// the guest notes or requests.
		parameters.put("cardnumber", "4446283280247004");		// the card number.
		parameters.put("cardmonth", "11");	// the card expiry month.
		parameters.put("cardyear", "2018");		// the card expiry year.
		parameters.put("amount", "12");				// the amount to be charged to the card.
		parameters.put("cardtype", "visa");
		
		String rq = GSON.toJson(parameters);
		System.out.println("Encrypted: " + Model.encrypt("34575"));
		// ===================================== REQUEST END ===========================================
		
//	    PostMethod post = new PostMethod("http://localhost:8080/service/JSONService");
//	    post.addRequestHeader("Accept" , "*/*");
//	    post.addRequestHeader("Content-Type", "application/json");
//	    RequestEntity entity = new StringRequestEntity(rq, "*/*", null);
//	    post.setRequestEntity(entity);
//	    HttpClient httpclient = new HttpClient();		   
//	    int rs = httpclient.executeMethod(post);
// 		https://razor-cloud.com/razor/JSONService?callback=jQuery17102773492578417063_1385565630723&
//		service=calendar&pos=c73f8de0ec583527&productid=48562&date=2013-11-01&_=1385565991075
		String response = getConnection(makeRequestUrl(parameters), null);

        System.out.println(response);
	}
	
	private static URL makeRequestUrl(Map<String, String> parameters) throws MalformedURLException {
		String urlString = "http://localhost:8080/service/JSONService?callback=jQuery";
		for(String key : parameters.keySet()) {
			urlString += "&" + key + "=" + parameters.get(key); 
		}
		System.out.println(urlString);
		return new URL(urlString);
	}
	
	private static final String getConnection(URL url, String rq) throws Throwable {
		String jsonString = "";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("GET");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
//			BASE64Encoder enc = new sun.misc.BASE64Encoder();
//			String userpassword = "campsbayterrace" + ":" + "C8MYS2zUhJq1RO";
//			String encodedAuthorization = enc.encode( userpassword.getBytes() );
//			connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

			if (rq != null) {
				connection.setRequestProperty("Accept", "*/*");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + "URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {jsonString += line;}
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return jsonString;
	}
}
