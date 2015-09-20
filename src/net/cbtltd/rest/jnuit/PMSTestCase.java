package net.cbtltd.rest.jnuit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class PMSTestCase {
  
	@Test
	public void testReservationCall() {
		// now works with valid dates.  previous dates invalid.  
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");	
		sb.append("<reservation>");
		sb.append("<state>Confirmed</state>");
		sb.append("<productid>251669</productid>");
		sb.append("<fromdate>2014-08-19</fromdate>"); // changed start date
		sb.append("<todate>2014-08-21</todate>"); // changed end date
		sb.append("<emailaddress>rose@rentalscapetown.com</emailaddress>");
		sb.append("<familyname>dennis</familyname>");
		sb.append("<firstname>dennis</firstname>");
		sb.append("<adult>2</adult>");
		sb.append("<child></child>");
		sb.append("<notes>notes</notes>");
		sb.append("<quote>1.025,00 EUR</quote>");
		sb.append("<tax>0,00 EUR</tax>");
		sb.append("<currency>EUR</currency>");
		sb.append("</reservation>");

		
		String rq = sb.toString();
		System.out.println("rq=" + rq);
		System.out.println("\n\n");
		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/reservation?pos=c709db2e4b15b7fc");
//		String encoding = DatatypeConverter.printBase64Binary("primary_email_address:password".getBytes());
		String encoding = DatatypeConverter.printBase64Binary("rose@rentalscapetown.com:rose".getBytes());
		post.addRequestHeader("Authorization", "Basic " + encoding);
		post.addRequestHeader("Accept" , "application/xml");

		RequestEntity entity;
		try {
			entity = new StringRequestEntity(rq, "application/xml", null);
			post.setRequestEntity(entity);
			HttpClient httpclient = new HttpClient();
			int rs = httpclient.executeMethod(post);
			System.out.println("rs=" + rs + "\n" + post.getResponseBodyAsString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testNewProductUpload() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		sb.append("<product><name>Test house</name>");
		sb.append("<state>Created</state>");
		sb.append("<currency>EUR</currency>");
		sb.append("<language>EN</language>");
		sb.append("<type></type>");
		sb.append("<unit>DAY</unit>");
		sb.append("<physicaladdress>1 Commonwealth Avenue");
		sb.append("USA</physicaladdress>");
		sb.append("<adult>3</adult>");
		sb.append("<bathroom>3</bathroom>");
		sb.append("<dynamicpricingenabled>false</dynamicpricingenabled>");
		sb.append("<infant>0</infant>");
		sb.append("<ownerid>10947</ownerid>");
		sb.append("<person>3</person>");
		sb.append("<quantity>1</quantity>");
		sb.append("<room>3</room>");
		sb.append("<supplierid>10947</supplierid>");
		sb.append("<toilet>3</toilet>");
		
		sb.append("<attributes><attribute><key>AQC1</key>");
		sb.append("<values>");
		sb.append("<value>AQC10</value><value>AQC9</value>");
		sb.append("</values></attribute><attribute><key>BED</key>");
		sb.append("<values>");
		sb.append("<value>BED3</value>");
		sb.append("</values></attribute><attribute><key>BEP</key>");
		sb.append("</attribute><attribute><key>COM</key>");
		sb.append("</attribute><attribute><key>FAC</key>");
		sb.append("<values>");
		sb.append("<value>FAC1</value>");
		sb.append("</values></attribute><attribute><key>GRD</key>");
		sb.append("<values>");
		sb.append("<value>GRD4</value>");
		sb.append("</values></attribute><attribute><key>GRP</key>");
		sb.append("</attribute><attribute><key>HAC</key>");
		sb.append("<values>");
		sb.append("<value>HAC221</value><value>HAC253</value>");
		sb.append("</values></attribute><attribute><key>LOC</key>");
		sb.append("<values>");
		sb.append("<value>LOC26</value>");
		sb.append("</values></attribute><attribute><key>PCP</key>");
		sb.append("</attribute><attribute><key>PCT</key>");
		sb.append("<values>");
		sb.append("<value>PCT3</value>");
		sb.append("</values></attribute><attribute><key>PEP</key>");
		sb.append("</attribute><attribute><key>PET</key>");
		sb.append("<values>");
		sb.append("<value>PET1</value>");
		sb.append("</values></attribute><attribute><key>PHP</key>");
		sb.append("</attribute><attribute><key>PHY</key>");
		sb.append("</attribute><attribute><key>RMA</key>");
		sb.append("</attribute><attribute><key>RMP</key>");
		sb.append("<values>");
		sb.append("<value>RMP2</value><value>RMP245</value>");
		sb.append("</values></attribute><attribute><key>RSN</key>");
		sb.append("</attribute><attribute><key>RSP</key>");
		sb.append("</attribute></attributes>");
		
		sb.append("<bedroom>3</bedroom>");
		sb.append("<city>Boston</city>");
		sb.append("<conditions>Terms and Condidtions text for ADRC 3 upload new property function</conditions>");
		sb.append("<contents>Text in the description field for ADRC 3 upload new property function</contents>");
		sb.append("<country>ZA</country>");
		sb.append("<description>Text in the description field for ADRC 3 upload new property function</description>");
		//sb.append("<images></images>");
		sb.append("<region>MA</region>");
		sb.append("<terms>Terms and Condidtions text for ADRC 3 upload new property function</terms>");
		
		sb.append("<xsl>no_xsl</xsl></product>");
			
		String rq = sb.toString();
		System.out.println("rq=" + rq);
		System.out.println("\n\n");
		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
//		String encoding = DatatypeConverter.printBase64Binary("primary_email_address:password".getBytes());
		String encoding = DatatypeConverter.printBase64Binary("rose@rentalscapetown.com:rose".getBytes());
		post.addRequestHeader("Authorization", "Basic " + encoding);
		post.addRequestHeader("Accept" , "application/xml");

		RequestEntity entity;
		try {
			entity = new StringRequestEntity(rq, "application/xml", null);
			post.setRequestEntity(entity);
			HttpClient httpclient = new HttpClient();
			int rs = httpclient.executeMethod(post);
			System.out.println("rs=" + rs + "\n" + post.getResponseBodyAsString());

		} catch (UnsupportedEncodingException e) {
			System.out.println("encoding");
		
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
			System.out.println("http");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("io");
		}
	
	}
	
	@Test
	public void testPriceUpload() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");		
		sb.append("<product>");
		sb.append("<id>251669</id>");
		sb.append("<pricetable>");
		sb.append("<price>");
		sb.append("<id>26449494</id>");
		sb.append("<name>Rack Rate</name>");
		sb.append("<state>Created</state>");
		sb.append("<available>1</available>");
		sb.append("<currency>EUR</currency>");
		sb.append("<date>2014-06-01</date>");
		sb.append("<factor>1.0</factor>");
		sb.append("<minimum>250</minimum>");
		sb.append("<quantity>0.0</quantity>");
		sb.append("<rule>AnyCheckIn</rule>");
		sb.append("<todate>2014-06-07</todate>");
		sb.append("<type>Reservation</type>");
		sb.append("<unit>DAY</unit>");
		sb.append("<value>50</value>");
		sb.append("</price>");
		sb.append("</pricetable>");
		sb.append("</product>");
		
		String rq = sb.toString();
		System.out.println("rq=" + rq);
		System.out.println("\n\n");
		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
//		String encoding = DatatypeConverter.printBase64Binary("primary_email_address:password".getBytes());
		String encoding = DatatypeConverter.printBase64Binary("rose@rentalscapetown.com:rose".getBytes());
		post.addRequestHeader("Authorization", "Basic " + encoding);
		post.addRequestHeader("Accept" , "application/xml");

		RequestEntity entity;
		try {
			entity = new StringRequestEntity(rq, "application/xml", null);
			post.setRequestEntity(entity);
			HttpClient httpclient = new HttpClient();
			int rs = httpclient.executeMethod(post);
			System.out.println("rs=" + rs + "\n" + post.getResponseBodyAsString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	@Test
	public void testCsUpdateString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<product><id>252867</id>");
		sb.append("<description>Updating Description text for ADRC testing 1</description>");
		sb.append("<attributes><attribute><key>AQC</key>");
		sb.append("<values>");
		sb.append("<value>AQC4</value><value>AQC9</value>");
		sb.append("</values></attribute><attribute><key>BED</key>");
		sb.append("<values>");
		
		sb.append("<value>BED1</value><value>BED4</value>");
		sb.append("<value>BED9</value>");
		sb.append("</values></attribute>");
		sb.append("<attribute><key>HAC</key>");
		sb.append("<values>");
		sb.append("<value>HAC118</value><value>HAC55</value>");
		sb.append("</values></attribute>");
		sb.append("<attribute><key>PET</key>");
		sb.append("<values>");
		sb.append("<value>PET1</value>");
		sb.append("</values></attribute>");
		sb.append("<attribute><key>RMA</key>");
		sb.append("<values>");
		sb.append("<value>RMA57</value>");
		sb.append("</values></attribute><attribute><key>RMP</key>");
		sb.append("<values>");
		sb.append("<value>RMP57</value>");
		sb.append("</values></attribute>");
		sb.append("</attributes>");
		sb.append("<images><image>http://accommodationdaddy.com/wp-content/uploads/2014/05/test-d.jpeg</image>");
		sb.append("<image>http://accommodationdaddy.com/wp-content/uploads/2014/05/test-c.jpeg</image>");
		sb.append("<image>http://accommodationdaddy.com/wp-content/uploads/2014/05/test-b.jpeg</image>");
		sb.append("<image>http://accommodationdaddy.com/wp-content/uploads/2014/05/test-a.jpeg</image>");
		sb.append("</images>");
		sb.append("<xsl>no_xsl</xsl></product>");
		
		
		
		String rq = sb.toString();
		System.out.println("rq=" + rq);
		System.out.println("\n\n");
		System.out.println("----------------------------");
		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
//		String encoding = DatatypeConverter.printBase64Binary("primary_email_address:password".getBytes());
		String encoding = DatatypeConverter.printBase64Binary("rose@rentalscapetown.com:rose".getBytes());
		post.addRequestHeader("Authorization", "Basic " + encoding);
		post.addRequestHeader("Accept" , "application/xml");

		RequestEntity entity;
		try {
			entity = new StringRequestEntity(rq, "application/xml", null);
			post.setRequestEntity(entity);
			HttpClient httpclient = new HttpClient();
			int rs = httpclient.executeMethod(post);
			System.out.println("rs=" + rs + "\n" + post.getResponseBodyAsString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
