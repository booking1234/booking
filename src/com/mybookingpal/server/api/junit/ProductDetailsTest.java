package com.mybookingpal.server.api.junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ProductDetailsTest {
	
	private String pos;
	private String productIDOneImg;
	private String productIDManyImg;
	private String productIDNoAddr;
	private String productIDOneAddr;
	private String productIDManyAddr;
	
	public ProductDetailsTest(String pos, String productIDOneImg, String productIDManyImg,
			String productIDNoAddr, String productIDOneAddr, String productIDManyAddr)
	{
		this.pos = pos;
		this.productIDOneImg = productIDOneImg;
		this.productIDManyImg = productIDManyImg;
		this.productIDNoAddr = productIDNoAddr;
		this.productIDOneAddr = productIDOneAddr;
		this.productIDManyAddr = productIDManyAddr;
	}
	
	@Parameters
	public static Collection<Object[]> configData(){
		/* Specify parameters pos, productIDOneImg, productIDManyImg, productIDNoAddr, productIDOneAddr, productIDManyAddr*/
		/* pos ex: 834a55a7680c79fe a502d2c65c2f75d3 02e4f51681056a38*/
		Object[][] data = new Object[][] {
				{"02e4f51681056a38", "1558", "122", "122", "53603", "54105"}
		};
		
		return Arrays.asList(data);
	}
	
	@Test
	public void parseOneImageResponseTest()
	{
		String productID = this.productIDOneImg;
		String pos = this.pos;
		URI uri;
		try {
			uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
					+ "/propertydetail?pos=" + pos + "&test=true");
			URL url = uri.toURL();
			
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			try
			{
				JSONObject result = new JSONObject(body);
				JSONObject propResponse = (JSONObject) result.get("property_response");
				boolean isError = propResponse.getBoolean("is_error");
				if(!isError)
				{
					JSONObject property = (JSONObject) propResponse.get("property");
					
					try{
						JSONObject images = (JSONObject) property.get("images");
						JSONArray imageArray = images.getJSONArray("image");
						assertEquals(1, imageArray.length());
					}
					catch(ClassCastException e)
					{
						fail("Unexpected type of images attribute");
					}
					catch(JSONException e)
					{
						fail("Inconsistent format of images in response");
					}
				}
				else
				{
					fail("Response returned error");
				}
			}
			catch (JSONException e) {
				fail("Unexpected response received");
			}
		} 
		catch (URISyntaxException e) {
			fail("Incorrect URI syntax");
		}
		catch (MalformedURLException e) {
			fail("Malformed URL");
		} catch (IOException e) {
			fail("IO exception");
		}
	}
	
	@Test
	public void parseMoreThanOneImageResponseTest()
	{
		String productID = this.productIDManyImg;
		String pos = this.pos;
		URI uri;
		try {
			uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
					+ "/propertydetail?pos=" + pos + "&test=true");
			URL url = uri.toURL();
			
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			try
			{
				JSONObject result = new JSONObject(body);
				JSONObject propResponse = (JSONObject) result.get("property_response");
				boolean isError = propResponse.getBoolean("is_error");
				if(!isError)
				{
					JSONObject property = (JSONObject) propResponse.get("property");
					
					try{
						JSONObject images = (JSONObject) property.get("images");
						JSONArray imageArray = images.getJSONArray("image");
						assertEquals(true, imageArray.length() > 1);
					}
					catch(ClassCastException e)
					{
						fail("Unexpected type of images attribute");
					}
					catch(JSONException e)
					{
						fail("Inconsistent format of images in response");
					}
				}
				else
				{
					fail("Response returned error");
				}
			}
			catch (JSONException e) {
				fail("Unexpected response received");
			}
		} 
		catch (URISyntaxException e) {
			fail("Incorrect URI syntax");
		}
		catch (MalformedURLException e) {
			fail("Malformed URL");
		} catch (IOException e) {
			fail("IO exception");
		}
	}
	
	@Test
	public void parseEmptyAddressResponseTest()
	{
		String productID = this.productIDNoAddr;
		String pos = this.pos;
		URI uri;
		try {
			uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
					+ "/propertydetail?pos=" + pos + "&test=true");
			URL url = uri.toURL();
			
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			try
			{
				JSONObject result = new JSONObject(body);
				JSONObject propResponse = (JSONObject) result.get("property_response");
				boolean isError = propResponse.getBoolean("is_error");
				if(!isError)
				{
					JSONObject property = (JSONObject) propResponse.get("property");
					
					try{
						JSONArray addressArray = property.getJSONArray("address");
						assertEquals(0, addressArray.length());
					}
					catch(JSONException e)
					{
						fail("Inconsistent format of address in response");
					}
				}
				else
				{
					fail("Response returned error");
				}
			}
			catch (JSONException e) {
				fail("Unexpected response received");
			}
		} 
		catch (URISyntaxException e) {
			fail("Incorrect URI syntax");
		}
		catch (MalformedURLException e) {
			fail("Malformed URL");
		} catch (IOException e) {
			fail("IO exception");
		}
	}
	
	@Test
	public void parseOneAddressResponseTest()
	{
		String productID = this.productIDOneAddr;
		String pos = this.pos;
		URI uri;
		try {
			uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
					+ "/propertydetail?pos=" + pos + "&test=true");
			URL url = uri.toURL();
			
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			try
			{
				JSONObject result = new JSONObject(body);
				JSONObject propResponse = (JSONObject) result.get("property_response");
				boolean isError = propResponse.getBoolean("is_error");
				if(!isError)
				{
					JSONObject property = (JSONObject) propResponse.get("property");
					
					try{
						JSONArray addressArray = property.getJSONArray("address");
						assertEquals(1, addressArray.length());
					}
					catch(JSONException e)
					{
						fail("Inconsistent format of address in response");
					}
				}
				else
				{
					fail("Response returned error");
				}
			}
			catch (JSONException e) {
				fail("Unexpected response received");
			}
		} 
		catch (URISyntaxException e) {
			fail("Incorrect URI syntax");
		}
		catch (MalformedURLException e) {
			fail("Malformed URL");
		} catch (IOException e) {
			fail("IO exception");
		}
	}
	
	@Test
	public void parseMoreThanOneAddressResponseTest()
	{
		String productID = this.productIDManyAddr;
		String pos = this.pos;
		URI uri;
		try {
			uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
					+ "/propertydetail?pos=" + pos + "&test=true");
			URL url = uri.toURL();
			
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			try
			{
				JSONObject result = new JSONObject(body);
				JSONObject propResponse = (JSONObject) result.get("property_response");
				boolean isError = propResponse.getBoolean("is_error");
				if(!isError)
				{
					JSONObject property = (JSONObject) propResponse.get("property");
					
					try{
						JSONArray addressArray = property.getJSONArray("address");
						assertEquals(true, addressArray.length() > 1);
					}
					catch(JSONException e)
					{
						fail("Inconsistent format of address in response");
					}
				}
				else
				{
					fail("Response returned error");
				}
			}
			catch (JSONException e) {
				fail("Unexpected response received");
			}
		} 
		catch (URISyntaxException e) {
			fail("Incorrect URI syntax");
		}
		catch (MalformedURLException e) {
			fail("Malformed URL");
		} catch (IOException e) {
			fail("IO exception");
		}
	}
}
