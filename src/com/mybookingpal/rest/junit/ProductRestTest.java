package com.mybookingpal.rest.junit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

/**
 * Collection of Unit tests for various interfaces for Product API
 * 
 * @author cshah
 * @version @version@
 * May 21, 2014
 */
public class ProductRestTest {

	@Test
	public void testUpdateProduct() {

		StringBuilder sb = new StringBuilder();
//		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		sb.append("<product>");
		sb.append("<id>58716</id>");
		sb.append("<attributes>");
		sb.append("<attribute>");
		sb.append("<key>AQC</key>");
		sb.append("<values>");
		sb.append("<value>AQC10</value>");
		sb.append("<value>AQC11</value>");
		sb.append("</values>");
		sb.append("</attribute>");
		sb.append("<attribute>");
		sb.append("<key>PCP</key>");
		sb.append("<values>");
		sb.append("<value>PCP34</value>");
		sb.append("</values>");
		sb.append("</attribute>");
		sb.append("<attribute>");
		sb.append("<key>BED</key>");
		sb.append("<values>");
		sb.append("<value>BED1</value>");
		sb.append("<value>BED3</value>");
		sb.append("<value>BED5</value>");
		sb.append("<value>BED8</value>");
		sb.append("<value>BED8</value>");
		sb.append("</values>");
		sb.append("</attribute>");
		sb.append("</attributes>");
		sb.append("<images>");
		sb.append("<image>https://s3.amazonaws.com/mybookingpal/templates/butt2.jpg</image>");
		sb.append("</images>");
		sb.append("</product>");

		String rq = sb.toString();
		System.out.println("rq=" + rq);

		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
		// import the certificate to your keystore
		// download it from the website
		// keytool -import -trustcacerts -file /home/cshah/certificates/\*.mybookingpal.com -alias mybookingpal.com -keystore /usr/lib/jvm/jdk1.7.0_51/jre/lib/security/cacerts 
//		PostMethod post = new PostMethod("https://www.mybookingpal.com/xml/rest/product?pos=c709db2e4b15b7fc");
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
	@Test
	public void testCreateProduct() {
		
		StringBuilder input = new StringBuilder();
//		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		input.append("<product>");
		input.append("<state>Created</state>");
		input.append("<name>ADRC testing CS4</name>");
		input.append("<currency>ZAR</currency>");
		input.append("<language>EN</language>");
		input.append("<type>Accommodation</type>");
		input.append("<unit>DAY</unit>");
		input.append("<physicaladdress>2265 Sedgwick Avenue");
		input.append("New York");
		input.append("USA</physicaladdress>");
		input.append("<adult>2</adult>");
		input.append("<bathroom>2</bathroom>");
		input.append("<child>0</child>");
		input.append("<code></code>");
		input.append("<dynamicpricingenabled>false</dynamicpricingenabled>");
		input.append("<infant>0</infant>");
		input.append("<ownerid>10947</ownerid>");
		input.append("<person>2</person>");
		input.append("<quantity>1</quantity>");
		input.append("<room>2</room>");
		input.append("<supplierid>10947</supplierid>");
		input.append("<toilet>2</toilet>");
		input.append("<attributes>");
		input.append("<attribute><key>AQC</key>");
		input.append("<values>");
		input.append("<value>AQC10</value>");
		input.append("<value>AQC4</value>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute><key>BED</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>BEP</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>COM</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>FAC</key>");
		input.append("<values>");
		input.append("<value>FAC1</value>");
		input.append("</values></attribute>");
		input.append("<attribute><key>GRD</key>");
		input.append("<values>");
		input.append("<value>GRD5</value>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute><key>GRP</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>HAC</key>");
		input.append("<values>");
		input.append("<value>HAC165</value>");
		input.append("<value>HAC223</value>");
		input.append("</values>");
		input.append("</attribute><attribute><key>LOC</key>");
		input.append("<values>");
		input.append("<value>LOC3</value>");
		input.append("</values></attribute><attribute><key>PCP</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>PCT</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>PEP</key>");
		input.append("<values>");
		input.append("</values></attribute><attribute><key>PET</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>PHP</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>PHY</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>RMA</key>");
		input.append("<values>");
		input.append("<value>RMA32</value>");
		input.append("<value>RMA59</value>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>RMP</key>");
		input.append("<values>");
		input.append("<value>RMP2</value>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>RSN</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("<attribute>");
		input.append("<key>RSP</key>");
		input.append("<values>");
		input.append("</values>");
		input.append("</attribute>");
		input.append("</attributes>");
		input.append("<bedroom>2</bedroom>");
		input.append("<city>New York</city>");
		input.append("<conditions>ADRC testing 3 Terms and Conditions / Cancellation policy Text</conditions>");
		input.append("<contents>ADRC testing 3 Description Text</contents>");
		input.append("<country>ZA</country>");
		input.append("<description>ADRC testing 3 Description Text</description>");
		input.append("<images></images>");
		input.append("<region>New York</region>");
		input.append("<terms>ADRC testing 3 Terms and Conditions / Cancellation policy Text</terms>");
		input.append("<xsl>no_xsl</xsl></product>");
		
		String rq = input.toString();
		System.out.println("rq=" + rq);
		
//		PostMethod post = new PostMethod("https://www.razor-cloud.com/xml/rest/product?pos=c709db2e4b15b7fc");
		// import the certificate to your keystore
		// download it from the website
		// keytool -import -trustcacerts -file /home/cshah/certificates/\*.mybookingpal.com -alias mybookingpal.com -keystore /usr/lib/jvm/jdk1.7.0_51/jre/lib/security/cacerts 
//		PostMethod post = new PostMethod("https://www.mybookingpal.com/xml/rest/product?pos=c709db2e4b15b7fc");
		PostMethod post = new PostMethod("https://mybookingpal.com/xml/services/rest/product?pos=c709db2e4b15b7fc");
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
	
	@Test
	public void testGetProduct() {
		
		String url = "https://www.mybookingpal.com/xml/rest/product/${productId}/attribute?pos=c709db2e4b15b7fc&test=true&xsl=http://razor-cloud.com/xsl/pretty.xsl";
		url = url.replace("${productId}", "58716");
		
		GetMethod get = new GetMethod(url);
		String encoding = DatatypeConverter.printBase64Binary("rose@rentalscapetown.com:rose".getBytes());
		get.addRequestHeader("Authorization", "Basic " + encoding);
		get.addRequestHeader("Accept" , "application/xml");
		
		RequestEntity entity;
		try {
//			entity = new StringRequestEntity(rq, "application/xml", null);
//			get.setRequestEntity(entity);
			HttpClient httpclient = new HttpClient();
			int rs = httpclient.executeMethod(get);
			System.out.println("rs=" + rs + " " + get.getResponseBodyAsString());
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