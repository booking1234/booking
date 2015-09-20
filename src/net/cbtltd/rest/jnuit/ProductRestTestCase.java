package net.cbtltd.rest.jnuit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class ProductRestTestCase {

	@Test
	public void testProductCreation() {
		//import org.apache.commons.httpclient.*;
		//import javax.xml.bind.DatatypeConverter;

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
//		sb.append("<product>");
//		sb.append("<id>34993</id>"); // Razor ID of the property - include for updates to existing data but not for new properties
//		sb.append("<altid>ABC1234</id>"); // optional - include alternative ID if the data is uploaded from another system
//		sb.append("<altpartyid>4457</id>"); // optional - include the Razor ID of the party which has the other system
//		sb.append("<name>Camps Bay Terrace Apartment</name>");
//		sb.append("<state>Suspended</state>");
//		sb.append("<altitude>0.0</altitude>");
//		sb.append("<currency>EUR</currency>");
//		sb.append("<language>EN</language>");
//		sb.append("<latitude>-33.9571457582702</latitude>");
//		sb.append("<locationid>58777</locationid>");
//		sb.append("<longitude>18.3773481845856</longitude>");
//		sb.append("<type>Accommodation</type>");
//		sb.append("<unit>DAY</unit>");
//		sb.append("<bathroom>2</bathroom>");
//		sb.append("<child>0</child>");
//		sb.append("<code>Palm Suite</code>");
//		sb.append("<infant>0</infant>");
//		sb.append("<ownerid>890</ownerid>");
//		sb.append("<person>4</person>");
//		sb.append("<physicaladdress>107 Victoria Road\nCamps Bay 8005</physicaladdress>");
//		sb.append("<quantity>1</quantity>");
//		sb.append("<rating>8</rating>");
//		sb.append("<room>2</room>");
//		sb.append("<supplierid>5</supplierid>");
//		sb.append("<tax>VAT Normal</tax>");
//		sb.append("<toilet>2</toilet>");
//		sb.append("<webaddress>http://razor-cloud.com/razor/?product=144&amp;pos=8508a7e6ce43e091</webaddress>");
//		sb.append("<attributes>");
//		sb.append("<attribute>");
//		sb.append("<key>HAC</key>");
//		sb.append("<values>");
//		sb.append("<value>HAC5</value>");
//		sb.append("<value>HAC50</value>");
//		sb.append("<value>HAC71</value>");
//		sb.append("<value>HAC108</value>");
//		sb.append("<value>HAC109</value>");
//		sb.append("<value>HAC118</value>");
//		sb.append("<value>HAC137</value>");
//		sb.append("<value>HAC198</value>");
//		sb.append("<value>HAC221</value>");
//		sb.append("<value>HAC223</value>");
//		sb.append("<value>HAC253</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>AQC</key>");
//		sb.append("<values>");
//		sb.append("<value>AQC9</value>");
//		sb.append("<value>AQC10</value>");
//		sb.append("<value>AQC11</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>PCT</key>");
//		sb.append("<values>");
//		sb.append("<value>PCT34</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>BED</key>");
//		sb.append("<values>");
//		sb.append("<value>BED5</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>GRD</key>");
//		sb.append("<values>");
//		sb.append("<value>GRD4</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>LOC</key>");
//		sb.append("<values>");
//		sb.append("<value>LOC16</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("</attributes>");
//		sb.append("<contents>Cleaning Service : Monday - Saturday");
//		sb.append("Selected DSTV Bouquet");
//		sb.append("All taxes");
//		sb.append("All Linen and Towels");
//		sb.append("Pool Towels");
//		sb.append("Toiletries");
//		sb.append("Wireless internet access");
//		sb.append("</contents>");
//		sb.append("<description>An unpretentious and excellent value apartment in Camps");
//		sb.append("Bay's most prime position! The Palm Suite is perfect for groups of");
//		sb.append("friends or a family.");
//		sb.append("This charming apartment has two en-suite bedrooms adjoining onto an open");
//		sb.append("plan kitchen, dining area and lounge.");
//		sb.append("</description>");
//		sb.append("<images>");
//		sb.append("<image>http://mydomain.com/images/Lounge.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/DiningRoom.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/Kitchen.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/MasterBedroom.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/SecondBedroom.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/LawnView.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/South View.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/WestView.jpg</image>");
//		sb.append("<image>http://mydomain.com/images/FloorPlan.jpg</image>");
//		sb.append("</images>");
//		sb.append("<options>Transfers/Shuttles at additional expense");
//		sb.append("\nDinner Reservations");
//		sb.append("\nGrocery stocking at additional expense");
//		sb.append("\nDVD Collection on request");
//		sb.append("</options>");
//		sb.append("<pricetable>");
//		sb.append("<price>");
//		sb.append("<name>Rack Rate</name>");
//		sb.append("<date>2012-05-02</date>");
//		sb.append("<todate>2012-09-30</todate>");
//		sb.append("<unit>DAY</unit>");
//		sb.append("<value>1250.0</value>");
//		sb.append("<minimum>4850.0</minimum>");
//		sb.append("<currency>EUR</currency>");
//		sb.append("</price>");
//		sb.append("<price>");
//		sb.append("<name>Rack Rate</name>");
//		sb.append("<date>2012-10-01</date>");
//		sb.append("<todate>2012-12-31</todate>");
//		sb.append("<unit>DAY</unit>");
//		sb.append("<value>1500.0</value>");
//		sb.append("<minimum>6000.0</minimum>");
//		sb.append("<currency>EUR</currency>");
//		sb.append("</price>");
//		sb.append("</pricetable>");
//		sb.append("<terms>Please contact us if we can help in any way. We arrange airport ");
//		sb.append("transfers, car hire, tours, restaurant bookings or anything else to ");
//		sb.append("make your stay with us enjoyable. Also let us know of any particular ");
//		sb.append("requirements such as cots and high chairs for infants. We can also ");
//		sb.append("happily pre-stock your apartment with food and drinks on your behalf. ");
//		sb.append("If you will be celebrating a special occasion during your stay such as ");
//		sb.append("a birthday, anniversary or honeymoon, please let us know so that we ");
//		sb.append("can make that extra effort on your special day.");
//		sb.append("</terms>");
//		sb.append("<xsl>'no_xsl'</xsl>");
//		sb.append("</product>");
		
		sb.append("<product>");
		sb.append("<id>58716</id>");
		sb.append("<attributes>");
		sb.append("<attribute>");
		sb.append("<key>AQC</key>");
		sb.append("<values>");
//		sb.append("<value>AQC10</value>");
		sb.append("<value>AQC11</value>");
		sb.append("</values>");
		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>PCP</key>");
//		sb.append("<values>");
//		sb.append("<value>PCP34</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
//		sb.append("<attribute>");
//		sb.append("<key>BED</key>");
//		sb.append("<values>");
//		sb.append("<value>BED1</value>");
//		sb.append("<value>BED3</value>");
//		sb.append("<value>BED5</value>");
//		sb.append("<value>BED8</value>");
//		sb.append("</values>");
//		sb.append("</attribute>");
		sb.append("</attributes>");
		sb.append("<xsl>'no_xsl'</xsl>");
		sb.append("</product>");

		String rq = sb.toString();
		System.out.println("rq=" + rq);

//		PostMethod post = new PostMethod("https://razor-cloud/xml/rest/product?pos=8508a7e6ce43e091");
//		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
		PostMethod post = new PostMethod("http://54.83.202.109/xml/rest/product?pos=c709db2e4b15b7fc");
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
			System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
