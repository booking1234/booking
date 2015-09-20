/**
 * 
 */
package net.cbtltd.rest.homeaway;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.cbtltd.rest.common.utils.CommonUtils;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.xml.sax.InputSource;

import com.mybookingpal.config.RazorConfig;

/**
 * @author manikandan15785
 *
 */
public class HomeAwayUtils extends CommonUtils {
	public static String WEBLINK_WSDL="bp.homeaway.weblink.wsdl";
	public static String WEBLINK_USER="bp.homeaway.weblink.username";
	public static String WEBLINK_PWD="bp.homeaway.weblink.pwd";
	public static String WEBLINK_CLIENT_ID="bp.homeaway.weblink.clientid";
	public static String WEBLINK_API_KEY="bp.homeaway.weblink.apikey";
	public static String WEBLINK_HAPI_URL="bp.homeaway.weblink.hapi.url";
	
	public static String IMAGE_WIDTH_MIN="bp.homeaway.image.download.width.start";
	public static String IMAGE_WIDTH_MAX="bp.homeaway.image.download.width.end";
	public static String IMAGE_HEIGHT_MIN="bp.homeaway.image.download.height.start";
	public static String IMAGE_HEIGHT_MAX="bp.homeaway.image.download.height.end";
	
	public static boolean isDownloadRequired(int width,int height){
		int wMin=getValue(IMAGE_WIDTH_MIN),wMax=getValue(IMAGE_WIDTH_MAX);
		int hMin=getValue(IMAGE_HEIGHT_MIN),hMax=getValue(IMAGE_HEIGHT_MAX);
		return (width>=wMin&&width<=wMax)|| (height>=hMin&&height<=hMax);
		
	}
	
	private static int getValue(String var) {
		try{ return Integer.parseInt(RazorConfig.getValue(var)); } catch(NumberFormatException e){ return 0; }
	}
	
	private static Credentials credentials = new UsernamePasswordCredentials(
			RazorConfig.getValue(WEBLINK_USER),
			RazorConfig.getValue(WEBLINK_PWD));
	
	public static Credentials getCredentials() {
				return credentials;
	}
	
	private static MyDateComparator comparator=new MyDateComparator();

	/**
	 * @return the comparator
	 */
	public static MyDateComparator getComparator() {
		return comparator;
	}
	
	
	public static Map<String, String> getATTRIBUTES() {
		return attributes;
	}
	
	static {
		attributes.put("Air Conditioning","RMA2");
		attributes.put("Boat Dock","ACC6");
		attributes.put("Community Pool","RST75");
		attributes.put("Deck or Balcony","RMA7");
		attributes.put("Dishwasher","RMA32");
		attributes.put("Elevator","HAC33");
		attributes.put("Fireplace","RMA41");
		attributes.put("Fishing","RST20");
		attributes.put("Garage","TRP6");
		attributes.put("Gas Grill / BBQ","FAC3");
		attributes.put("Golf Nearby","RST27");
		attributes.put("Handicap Accessible","ACC94");
		attributes.put("Heated Pool","REC30");
		attributes.put("Hot Tub","CBF25");
		attributes.put("Laundry","RCC5");
		attributes.put("Microwave","RMA68");
		attributes.put("On Golf Course","REC2");
		attributes.put("Outside Shower","PHY48");
		attributes.put("Pets Permitted","PET10");
		attributes.put("Pool Table","FAC8");
		attributes.put("Private Fence","CBF88");
		attributes.put("Private Pool","RMA245");
		attributes.put("Screened Porch","RMA7");
		attributes.put("Single Story","CUI44");
		attributes.put("Ski In - Ski out","ACC45");
		attributes.put("Skiing","ACC45");
		attributes.put("Smoking Permitted","RMA101");
		attributes.put("Spa/Jacuzzi","RSN12");
		attributes.put("Stereo","RMA104");
		attributes.put("Suitable for Children","HAC193");
		attributes.put("Tennis","RSN17");
		attributes.put("TV","RMP90");
		attributes.put("VCR","CBF69");
		attributes.put("Watersports","RMA120");
		attributes.put("Golf Pkg Avail.","RST27");
		attributes.put("Jacuzzi/HotTub","CBF25");
		attributes.put("Multiple TV's","RMP90");
		attributes.put("Lazy River","ACC42");
		attributes.put("Full Kitchen","RMA59");
		attributes.put("Balcony","RMA7");
		attributes.put("Outdoor Pool","HAC54");
		attributes.put("Indoor Pool","HAC66");
		attributes.put("Bikers OK","RST6");
		attributes.put("Pet Friendly","PET10");
		attributes.put("Grill","FAC3");
		attributes.put("Internet Access","RMA54");
		attributes.put("Exercise Room","HAC35");
		attributes.put("Washer/Dryer","RMA149");
		attributes.put("Ocean Front","RST70");
	}

	public static String getToken(String creditCardNo) {
		String apikey =RazorConfig.getValue(WEBLINK_API_KEY);
		String clientId=RazorConfig.getValue(WEBLINK_CLIENT_ID);
		String baseurl=RazorConfig.getValue(WEBLINK_HAPI_URL);
		if(apikey==null||apikey.isEmpty()||clientId==null||clientId.isEmpty()||baseurl==null||baseurl.isEmpty()){
			return null;
		}
		long timestamp = new Date().getTime();
		String digest;
		try {
			digest = GenerateDigest(String.format("%s%s", timestamp, apikey));

			String url = String
					.format("%s?time=%s&digest=%s&clientId=%s",
							baseurl,timestamp, digest, clientId);
			String tokenRequestBody = String
					.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tokenForm><tokenType>CC</tokenType><value>%s</value></tokenForm>",
							creditCardNo);
//			System.out.println(url);
//			System.out.println(tokenRequestBody);
			return sendPost(url, tokenRequestBody);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String sendPost(String url, String urlParameters) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if (responseCode < 200 || responseCode >= 400)
			return null;

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		InputSource inputSource = new InputSource(in);
		return (String) xpath.evaluate("/token/@id", inputSource,
				XPathConstants.STRING);
	}
	
}

class MyDateComparator implements Comparator<Date> {
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public int compare(Date d1, Date d2) {
        return DATE_FORMAT.format(d1).compareTo(DATE_FORMAT.format(d2));
    }
}