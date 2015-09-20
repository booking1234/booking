package net.cbtltd.rest.flipkey.supplier.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;

import net.cbtltd.rest.flipkey.inquiry.Inquiry;
import net.cbtltd.rest.flipkey.inquiry.Inquiry.Data;
import net.cbtltd.rest.flipkey.inquiry.Inquiry.Header;
import net.cbtltd.rest.flipkey.supplier.InquiryRequest;
import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test cases for testing FlipkeySupplier API
 * @author nobodha
 *
 */
public class FlipkeySupplierAPITest {
	private static final Logger LOG = Logger
			.getLogger(FlipkeySupplierAPITest.class.getName());
	static String localRESTURL;
	static String fpInquiryURL;

	@BeforeClass
	public static void setUp() throws Exception {
		localRESTURL = "http://localhost:8080/Razor/xml/json/reservation/inquiry?pos=5e7e3a77b3714ea2&test=true";
	
		fpInquiryURL = "https://ws.demo.flipkey.net/pfe/inquiry/inquiry";	

	}

	@Test
	public void testAvailabilityInquiry() throws Exception {
		System.out.println("Inside testReadProducts");
		DefaultHttpClient client = new DefaultHttpClient();
		LOG.info(" localRESTURL " + localRESTURL);
		HttpPost postRequest = new HttpPost(localRESTURL);
		postRequest.addHeader("Content-Type", "application/json");
	//	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		// Build the InquiryRequest object
		InquiryRequest inquiryRequest = new InquiryRequest();
		inquiryRequest.setClient_property_id("320472");
		inquiryRequest.setFk_property_id(320472);
		inquiryRequest.setArrival("2014-06-25");
		inquiryRequest.setDeparture("2014-06-25");
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String inquiryJson="inquiryRequest=";
		inquiryJson = inquiryJson+ ow.writeValueAsString(inquiryRequest);
		
		System.out.println(" inquiryJson " + inquiryJson);

		/*urlParameters
				.add(new BasicNameValuePair("inquiryRequest", inquiryJson));*/

		postRequest.setEntity(new StringEntity (inquiryJson));

		HttpResponse response = client.execute(postRequest);
		int statusCode = response.getStatusLine().getStatusCode();

		LOG.info("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringWriter xmlWriter = new StringWriter();

		String line = "";
		while ((line = rd.readLine()) != null) {
			xmlWriter.write(line);
		}
		
		LOG.info("Response " + xmlWriter.toString());

	}
	
	@Test
	public void testInquiry() {
		int statusCode = 0;
		try {
			
			Inquiry inquiry = new Inquiry();
			Header header = new Header();
			header.setSource("FK");
			inquiry.setHeader(header);
			
			Data data = new Data();
			
			

			// Setting the test value for testing purpose
			data.setRequestDate(FlipKeyUtils.getXMLCurrentDate());
			data.setName("Test Sen");
			data.setTotalGuests(2);
			data.setCheckIn("2014-09-17");
			data.setCheckOut("2014-09-20");
			data.setComment("hi could u please confirm there is availability and location wise are we close to Edinburgh centre as we have 2 young children. look forward to hearing from you. Thanks,John ");
//			data.setPropertyId(417793);
//			data.setPropertyId(53993);
			data.setPropertyId(8495);
			data.setEmail("chirayu@mybookingpal1.com");
			data.setPhoneNumber("07747484202");
			data.setUserIp("90.221.169.227");
//			data.setPointOfSale("mybookingpal.com");
			data.setUtmMedium("csynd");
			data.setUtmSource("bookingpal");
			data.setUtmCampaign("Host&Post");

			
			inquiry.setData(data);
			LOG.error("Before callFlipKeyInquiryAPI");
			statusCode = FlipKeyUtils.callFlipKeyInquiryAPI(inquiry);
			LOG.error("After callFlipKeyInquiryAPI "+statusCode);
		}
		catch(Exception e) {
			e.printStackTrace();
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
