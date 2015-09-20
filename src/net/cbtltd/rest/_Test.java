/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.mortbay.util.ajax.JSON;

/**
 * The Class _Test calls REST services for test purposes.
 * 
 * Basics
 * @see http://cxf.apache.org/docs/jax-rs-basics.html
 * Configure services
 * @see http://cxf.apache.org/docs/jaxrs-services-configuration.html
 * Configure programmatically
 * @see http://cxf.apache.org/docs/jaxrs-services-configuration.html#JAXRSServicesConfiguration-ConfiguringJAXRSservicesprogrammatically
 * Also preparing fo REST JSON services
 * @see JSON http://cxf.apache.org/docs/json-support.html
 * NEEDS jettison.jar PLEASE!
 * In Eclipse right click on this class and select 'Run as Java Application'.
 * Then test the URLs at "http://localhost:9000/" or whatever address is set below.
 * For example, http://localhost:9000/product/list?pos=a6abb91b2d3c9a6d&xsl=%27no_xsl%27
 */
public class _Test {

	protected _Test() {}

	public static void main(String args[]) throws Exception {
        System.out.println("Starting REST Client");
		try {
			
			if (false) {
				// Create skyware property request XML 
				//StringBuilder sb = new StringBuilder();
				//String rq = sb.toString();
			String rq = "<product><id>157215</id><name>Test Name</name><pricetable><price><name>Rack Rate</name><id>157215</id><quantity>6</quantity><date>2013-02-15</date><todate>2013-02-16</todate><minimum>51.00</minimum><value>51.00</value><unit>DAY</unit><type>Reservation</type><currency>USD</currency><rule>AnyCheckIn</rule></price></pricetable></product>";
			System.out.println("rq=" + rq);

//		    PostMethod post = new PostMethod("http://localhost/xml/rest/product?pos=a3a2e74b809e0e87");
		    PostMethod post = new PostMethod("http://www.razor-cloud.com/service/xml/rest/product?pos=33b447148baaefb1");
		    	String encoding = DatatypeConverter.printBase64Binary("info@noxrentals.co.za:19pimp77".getBytes());
		    post.addRequestHeader("Authorization", "Basic " + encoding);
		    post.addRequestHeader("Accept" , "application/xml");
		    
		    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
		    post.setRequestEntity(entity);
		    HttpClient httpclient = new HttpClient();
		    int rs = httpclient.executeMethod(post);
			System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());
			}
			if (false) {
				// Create property request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
				sb.append("<product>");
//				sb.append("<id>34993</id>"); // include for updates	to existing data
				sb.append("<name>Camps Bay Terrace Apartment</name>");
				sb.append("<state>Suspended</state>");
				sb.append("<altitude>0.0</altitude>");
				sb.append("<currency>ZAR</currency>");
				sb.append("<language>EN</language>");
				sb.append("<latitude>-33.9571457582702</latitude>");
				sb.append("<locationid>58777</locationid>");
				sb.append("<longitude>18.3773481845856</longitude>");
				sb.append("<type>Accommodation</type>");
				sb.append("<unit>DAY</unit>");
				sb.append("<bathroom>2</bathroom>");
				sb.append("<child>0</child>");
				sb.append("<code>Palm Suite</code>");
				sb.append("<infant>0</infant>");
				sb.append("<ownerid>890</ownerid>");
				sb.append("<person>4</person>");
				sb.append("<physicaladdress>107 Victoria Road\nCamps Bay 8005</physicaladdress>");
				sb.append("<quantity>1</quantity>");
				sb.append("<rating>8</rating>");
				sb.append("<room>2</room>");
				sb.append("<supplierid>5</supplierid>");
				sb.append("<tax>VAT Normal</tax>");
				sb.append("<toilet>2</toilet>");
				sb.append("<webaddress>http://razor-cloud.com/razor/?product=144&amp;pos=a3a2e74b809e0e87</webaddress>");
				sb.append("<attributes>");
				sb.append("<attribute>");
				sb.append("<key>Amenities</key>");
				sb.append("<values>");
				sb.append("<value>Air conditioning</value>");
				sb.append("<value>Serviced daily</value>");
				sb.append("<value>Pool</value>");
				sb.append("<value>Laundry Self Service</value>");
				sb.append("<value>Direct Dial Phone</value>");
				sb.append("<value>Barbeque Grill</value>");
				sb.append("<value>Coffee/Tea in Room</value>");
				sb.append("<value>Non-smoking Rooms</value>");
				sb.append("<value>WiFi Hotspot</value>");
				sb.append("<value>Internet Free</value>");
				sb.append("<value>Private Pool</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("<attribute>");
				sb.append("<key>Ages Allowed</key>");
				sb.append("<values>");
				sb.append("<value>Teenager (13 - 17 years)</value>");
				sb.append("<value>Adult (18 - 64 years)</value>");
				sb.append("<value>Senior (65+ years)</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("<attribute>");
				sb.append("<key>Property Type</key>");
				sb.append("<values>");
				sb.append("<value>Vacation Rental</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("<attribute>");
				sb.append("<key>Bed Type</key>");
				sb.append("<values>");
				sb.append("<value>Queen</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("<attribute>");
				sb.append("<key>Grading</key>");
				sb.append("<values>");
				sb.append("<value>4 Star</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("<attribute>");
				sb.append("<key>Location Category</key>");
				sb.append("<values>");
				sb.append("<value>Oceanfront</value>");
				sb.append("</values>");
				sb.append("</attribute>");
				sb.append("</attributes>");
				sb.append("<contents>Cleaning Service : Monday - Saturday");
				sb.append("Selected DSTV Bouquet");
				sb.append("All taxes");
				sb.append("All Linen and Towels");
				sb.append("Pool Towels");
				sb.append("Toiletries");
				sb.append("Wireless internet access");
				sb.append("</contents>");
				sb.append("<description>An unpretentious and excellent value apartment in Camps");
				sb.append("Bay's most prime position! The Palm Suite is perfect for groups of");
				sb.append("friends or a family.");
				sb.append("This charming apartment has two en-suite bedrooms adjoining onto an open");
				sb.append("plan kitchen, dining area and lounge.");
				sb.append("</description>");
				sb.append("<images>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-000.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-001.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-002.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-003.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-004.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-005.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-006.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-007.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-008.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-009.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-010.jpg</image>");
				sb.append("<image>http://razor-cloud.com/pictures/Product144-011.jpg</image>");
				sb.append("</images>");
				sb.append("<options>Transfers/Shuttles at additional expense");
				sb.append("\nDinner Reservations");
				sb.append("\nGrocery stocking at additional expense");
				sb.append("\nDVD Collection on request");
				sb.append("</options>");
				sb.append("<pricetable>");
				sb.append("<price>");
				sb.append("<name>Rack Rate</name>");
				sb.append("<date>2012-05-02</date>");
				sb.append("<todate>2012-09-30</todate>");
				sb.append("<unit>DAY</unit>");
				sb.append("<value>1250.0</value>");
				sb.append("<minimum>4850.0</minimum>");
				sb.append("<currency>ZAR</currency>");
				sb.append("</price>");
				sb.append("<price>");
				sb.append("<name>Rack Rate</name>");
				sb.append("<date>2012-10-01</date>");
				sb.append("<todate>2012-12-31</todate>");
				sb.append("<unit>DAY</unit>");
				sb.append("<value>1500.0</value>");
				sb.append("<minimum>6000.0</minimum>");
				sb.append("<currency>ZAR</currency>");
				sb.append("</price>");
				sb.append("</pricetable>");
				sb.append("<terms>Please contact us if we can help in any way. We arrange airport ");
				sb.append("transfers, car hire, tours, restaurant bookings or anything else to ");
				sb.append("make your stay with us enjoyable. Also let us know of any particular ");
				sb.append("requirements such as cots and high chairs for infants. We can also ");
				sb.append("happily pre-stock your apartment with food and drinks on your behalf. ");
				sb.append("If you will be celebrating a special occasion during your stay such as ");
				sb.append("a birthday, anniversary or honeymoon, please let us know so that we ");
				sb.append("can make that extra effort on your special day.");
				sb.append("</terms>");
				sb.append("<xsl>'no_xsl'</xsl>");
				sb.append("</product>");
				
				String rq = sb.toString();
				System.out.println("rq=" + rq);

			    PostMethod post = new PostMethod("http://localhost/xml/rest/product?pos=a3a2e74b809e0e87");
			    String encoding = DatatypeConverter.printBase64Binary("info@noxrentals.co.za:19pimp77".getBytes());
			    post.addRequestHeader("Authorization", "Basic " + encoding);
			    post.addRequestHeader("Accept" , "application/xml");
			    
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();
			    int rs = httpclient.executeMethod(post);
				System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());	
			}

			if (false) {
				// Cancel reservation request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<reservation>");
				sb.append("<state>Cancelled</state>"); //Cancelled, Blocked etc
				sb.append("<id>765108</id>");
//				sb.append("<name>14500</name>");
				sb.append("</reservation>");

				String rq = sb.toString();
				System.out.println("rq=" + rq);

			    PostMethod post = new PostMethod("http://localhost/xml/rest/reservation?pos=a3a2e74b809e0e87");
			    String encoding = DatatypeConverter.printBase64Binary("info@noxrentals.co.za:19pimp77".getBytes());
			    post.addRequestHeader("Authorization", "Basic " + encoding);
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
				System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());
			}
			if (false) {
				// Create summitcove reservation request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<reservation>");
				sb.append("<state>Provisional</state>"); //Cancelled, Blocked etc
//				sb.append("<id>XYZ123</id>");
//				sb.append("<name>14500</name>");
				sb.append("<productid>34536</productid>");
				sb.append("<fromdate>2013-06-15</fromdate>");
				sb.append("<todate>2013-06-30</todate>");
				sb.append("<arrivaltime>14:00:00</arrivaltime>");
				sb.append("<emailaddress>timmy@pinawayst.com</emailaddress>");
				sb.append("<customername>Martin, Tim</customername>");
				sb.append("<familyname>Martinus</familyname>");
				sb.append("<firstname>Timothy Jamie</firstname>");
				sb.append("<adult>2</adult>");
				sb.append("<child>3</child>");
				sb.append("<quote>1500.00</quote>");
				sb.append("<currency>USD</currency>");
				sb.append("<notes>This is a beautiful honeymoon couple!\nPlease look after them.</notes>");
				sb.append("</reservation>");

				String rq = sb.toString();
				System.out.println("rq=" + rq);

			    PostMethod post = new PostMethod("http://localhost/xml/rest/reservation?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
				System.out.println("\nrs=" + rs + " " + post.getResponseBodyAsString());
			}
			if (false) {
				// Create reservation request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<reservation>");
				sb.append("<state>Provisional</state>"); //Cancelled, Blocked etc
//				sb.append("<id>XYZ123</id>");
//				sb.append("<name>14500</name>");
				sb.append("<productid>146</productid>");
				sb.append("<fromdate>2015-06-15</fromdate>");
				sb.append("<todate>2015-06-30</todate>");
				sb.append("<arrivaltime>14:00:00</arrivaltime>");
				sb.append("<emailaddress>timmy@pinawayst.com</emailaddress>");
				sb.append("<customername>Martin, Tim</customername>");
				sb.append("<familyname>Martinus</familyname>");
				sb.append("<firstname>Timothy Jamie</firstname>");
				sb.append("<adult>2</adult>");
				sb.append("<child>3</child>");
				sb.append("<quote>1500.00</quote>");
				sb.append("<currency>USD</currency>");
				sb.append("<notes>This is a beautiful honeymoon couple!\nPlease look after them.</notes>");
				sb.append("</reservation>");

				String rq = sb.toString();
				System.out.println("rq=" + rq);

			    PostMethod post = new PostMethod("http://localhost/xml/rest/reservation?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
				System.out.println("\nrs=" + rs + " " + post.getResponseBodyAsString());
			}
			if (false) {
				String url = "http://maps.googleapis.com/maps/api/geocode/json";
//				String rq = "address=58 Camps Bay Drive, Camps Bay, ZA&sensor=false";
			    GetMethod get = new GetMethod(url);
//			    get.addRequestHeader("Accept" , "text/plain");
			    HttpMethodParams params = new HttpMethodParams();
//			    params.setParameter("key", HasUrls.GOOGLE_SERVER_KEY);
			    params.setParameter("address", "58 Camps Bay Drive");
			    params.setParameter("sensor", false);
			    get.setParams(params);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(get);
				System.out.println("rs=" + rs + " " + get.getResponseBodyAsString());
			}
			if (true) {
				// Create provisional reservation request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<reservation>");
				sb.append("<state>Confirmed</state>"); //Cancelled, Blocked etc
				sb.append("<id>6283</id>");
				sb.append("<productid>157238</productid>");
				sb.append("<fromdate>2013-04-01</fromdate>");
				sb.append("<todate>2013-04-03</todate>");
				sb.append("<emailaddress>lars@silverstar.com</emailaddress>");
				sb.append("<familyname>Larson</familyname>");
				sb.append("<firstname>Chris</firstname>");
				sb.append("<quote>1.50</quote>");
				sb.append("<currency>USD</currency>");
				sb.append("<notes>Some new notes...</notes>");
				sb.append("</reservation>");

				String rq = sb.toString();
				System.out.println("rq=" + rq);

				String URL = "https://razor-cloud.com/service/xml/rest/reservation?pos=583749428dc38ac7";
				//String URL = "http://localhost/xml/rest/reservation?pos=a3a2e74b809e0e87";
				PostMethod post = new PostMethod(URL);
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
				System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());
			}
			if (false) {
				// Create product request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<product>");
				sb.append("<name>XML Test Product</name>");
				sb.append("<ownerid>734118</ownerid>");
				sb.append("<locationid>51018</locationid>");
				sb.append("<room>3</room>");
				sb.append("<person>7</person>");
				sb.append("<commission>10.0</commission>");
				sb.append("<discount>15.0</discount>");
				sb.append("<latitude>39.6079282365642</latitude>");
				sb.append("<longitude>-105.952706336975</longitude>");
				sb.append("<physicaladdress>23058 US Hwy 6\nKeystone\nCO 80435\nUS</physicaladdress>");
				sb.append("</product>");
	
				String rq = sb.toString();
				System.out.println("rq=" + rq);
				
			    PostMethod post = new PostMethod("http://localhost/xml/rest/product/upload?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
			    System.out.println("rs=" + rs);
			}
			
			if (false) {
				// Create price request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<price>");
				sb.append("<entity>1400</entity>");
				sb.append("<fromdate>2012-10-01</fromdate>");
				sb.append("<todate>2012-12-31</todate>");
				sb.append("<value>300.0</value>");
				sb.append("<minimum>1200.00</minimum>");
				sb.append("<currency>USD</currency>");
				sb.append("</price>");
	
				String rq = sb.toString();
				System.out.println("rq=" + rq);
				
			    PostMethod post = new PostMethod("http://localhost/xml/rest/product/upload/price?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
			    System.out.println("rs=" + rs);
			}

			if (false) {
				// Create text request XML
				StringBuilder sb = new StringBuilder();
				sb.append("<text>");
				sb.append("<id>2710</id>");
				sb.append("<name>Description</name>");
				sb.append("<type>Public</type>");
				sb.append("<language>en</language>");
				sb.append("<notes>This is a description of the property.</notes>");
				sb.append("</text>");
	
				String rq = sb.toString();
				System.out.println("rq=" + rq);
			    PostMethod post = new PostMethod("http://localhost/xml/rest/product/upload/text?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
			    System.out.println("rs=" + rs);
			}
	        
			if (false) {
				// Create images request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<product>");
				sb.append("<productid>2710</productid>");
				sb.append("<images>");
				sb.append("<image>http://testimage1.jpg</image>");
				sb.append("<image>http://testimage2.jpg</image>");
				sb.append("<image>http://testimage3.jpg</image>");
				sb.append("</images>");				
				sb.append("</product>");
	
				String rq = sb.toString();
				System.out.println("rq=" + rq);
				
			    PostMethod post = new PostMethod("http://localhost/xml/rest/product/upload/image?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
			    System.out.println("rs=" + rs);
			}

			if (false) {
				// Create attributes request XML 
				StringBuilder sb = new StringBuilder();
				sb.append("<product>");
				sb.append("<productid>2710</productid>");
				sb.append("<attributelist>ARC1,ARC4,ARC7,DEF8,FGH900</attributelist>");				
				sb.append("</product>");
	
				String rq = sb.toString();
				System.out.println("rq=" + rq);
				
			    PostMethod post = new PostMethod("http://localhost/xml/rest/product/upload/attributelist?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/xml");
			    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
			    System.out.println("rs=" + rs);
			}

			if (false) {
				// Create request JSON string
				StringBuilder sb = new StringBuilder();
				sb.append("{\"product\":{");
				sb.append("\"name\":\"JSON Test Product\",");
				sb.append("\"ownerid\":\"734118\",");
				sb.append("\"locationid\":\"51018\",");
				sb.append("\"room\":3,");
				sb.append("\"person\":7,");
				sb.append("\"commission\":10.0,");
				sb.append("\"discount\":15.0,");
				sb.append("\"latitude\":39.6079282365642,");
				sb.append("\"longitude\":-105.952706336975,");
				sb.append("\"physicaladdress\":\"23058 US Hwy 6\nKeystone\nCO 80435\nUS\"");
				sb.append("}}");
//				{"product":{"bedroom":2,"latitude":-33.9571457582702,"locationid":58777,"longitude":18.3773481845856,"name":"Camps Bay Terrace Palm Suite","room":2,"supplierid":5,"unit":"DAY"}}
				String rq = sb.toString();
		        System.out.println("rq=" + rq);

			    PostMethod post = new PostMethod("http://localhost/xml/json/product/upload?pos=a3a2e74b809e0e87");
			    post.addRequestHeader("Accept" , "application/json");
//			    post.addRequestHeader("Content-Type", "application/json");
			    RequestEntity entity = new StringRequestEntity(rq, "application/json", null);
			    post.setRequestEntity(entity);
			    HttpClient httpclient = new HttpClient();		   
			    int rs = httpclient.executeMethod(post);
		        System.out.println("rs=" + rs);

		        //				URL url = new URL("http://localhost/xml/json/product/upload?pos=a3a2e74b809e0e87");
//				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//				connection.setRequestMethod("POST");
//				connection.setDoOutput(true);
////				connection.setRequestProperty("Content-Type", "application/json");
//				connection.setRequestProperty("Accept", "application/json");
//				connection.connect();
//				OutputStream os = connection.getOutputStream();
//				os.write(rq.getBytes("UTF-8"));
//				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//				String line;
//				while ((line = br.readLine()) != null) {System.out.println(line);}
//				connection.disconnect();
			}
		} catch(Throwable x) {x.printStackTrace();}
        System.out.println("Stopping REST Client");
	}
	
	/**
	 * Gets the connection to the JSON server.
	 *
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the JSON string returned by the message.
	 * @throws Throwable the exception thrown by the method.
	 */
	private static final String getConnection(URL url, String rq) throws Throwable {
		String jsonString = "";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
//			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
//			BASE64Encoder enc = new sun.misc.BASE64Encoder();
//			String userpassword = "campsbayterrace" + ":" + "C8MYS2zUhJq1RO";
//			String encodedAuthorization = enc.encode( userpassword.getBytes() );
//			connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

			if (rq != null) {
				connection.setRequestProperty("Accept", "application/json");
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
