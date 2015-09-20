package net.cbtltd.rest.hotelscombined;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.mybookingpal.config.RazorConfig;

public class HotelsCombinedUtils {
	public static final String HOTELS_COMBINED_URL = "bp.hotelscombined.url";
	public static final String HOTELS_COMBINED_USERNAME = "bp.hotelscombined.username";
	public static final String HOTELS_COMBINED_PWD = "bp.hotelscombined.pwd";
	public static final String HOTELS_COMBINED_ADULTS_CODE = "10";
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private static final Logger LOG = Logger
			.getLogger(HotelsCombinedUtils.class.getName());

	public static String getCurrentDateTimeInISOFormat() {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
		return fmt.print(dt);

	}

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String callHotelsCombinedAPI(String requestXML) throws Exception {
		DefaultHttpClient client = new DefaultHttpClient();
	
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(RazorConfig.getValue(HOTELS_COMBINED_USERNAME,"bookingpal"), RazorConfig.getValue(HOTELS_COMBINED_PWD,"C9DVLb+5zbwdMCYvWVjg"));
		provider.setCredentials(AuthScope.ANY, credentials);
		client.setCredentialsProvider(provider);
		
		HttpPost post = new HttpPost(
				RazorConfig.getValue(HOTELS_COMBINED_URL,"https://cmi.hotelscombined.com/v1.0/ota"));
		LOG.info("HOTELS_COMBINED_URL : "
				+ RazorConfig.getValue(HOTELS_COMBINED_URL,"https://cmi.hotelscombined.com/v1.0/ota"));
		
		LOG.info("HOTELS_COMBINED_USERNAME : "
				+ RazorConfig.getValue(HOTELS_COMBINED_USERNAME,"bookingpal"));
		
		LOG.info("HOTELS_COMBINED_PWD : "
				+ RazorConfig.getValue(HOTELS_COMBINED_PWD,"C9DVLb+5zbwdMCYvWVjg"));
		
		
		StringEntity entity = new StringEntity(requestXML, "UTF-8");
		
		entity.setContentType("text/xml;charset=UTF-8");
		post.setHeader("Content-Type", "text/xml;charset=UTF-8");
		post.setEntity(entity);
		
		
		
		int statusCode=0;
		HttpResponse response = client.execute(post);
		statusCode = response.getStatusLine().getStatusCode();

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOG.info("Hotels Combined Response Status Code : " + statusCode);
		
		return statusCode + ":" + result.toString();

	}
}
