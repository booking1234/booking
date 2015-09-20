package net.cbtltd.rest.jnuit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import net.cbtltd.rest.nextpax.A_Handler;
import net.cbtltd.server.RazorServer;
import com.mybookingpal.server.test.mapper.ProductMapper;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Product;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mortbay.log.Log;


public class FindImages {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	
	/* Tests all image links of all products*/
	public static void testAllImageLinks(SqlSession sqlSession, String pos) {
		
		try {
			
			
			ArrayList<String> productIDs = sqlSession.getMapper(ProductMapper.class).activeproductid();
			
			for(String productID : productIDs)
			{
				checkImagesForProduct(productID, pos);
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/* Tests all image links corresponding to a given productID*/
	public static void testImageLinksbyProductID(SqlSession sqlSession, String pos, String productID) {
		
		try {
	
			Product product = sqlSession.getMapper(ProductMapper.class).read(productID);
			
			if(product == null)
			{
				LOG.error("productID: " + productID + " not found");
			}
			else if(!product.getState().equals("Created"))
			{
				LOG.error("productID: " + productID + " not active");
			}
			else
			{
				checkImagesForProduct(productID, pos);
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/* Tests all images of all products belonging to a given partyID*/
	public static void testImageLinksbyPartyID(SqlSession sqlSession, String pos, String partyID)
	{
		try {

			NameIdAction idActionObj = new NameIdAction();
			idActionObj.setId(partyID);
			
			ArrayList<String> productList = sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(idActionObj);
			
			if(productList.isEmpty())
			{	
				LOG.error("No entries found for partyID: " + partyID);
			}
			else
			{
				for(String product : productList)
				{
					checkImagesForProduct(product, pos);
				}
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Tests all images of all products mapped to a given channel partner*/
	public static void testImageLinksbyChannelPartner(SqlSession sqlSession, String pos, String channelPartnerID)
	{
		try {

			NameIdAction idActionObj = new NameIdAction();
			idActionObj.setId(channelPartnerID);
			
			ArrayList<String> productList = sqlSession.getMapper(ProductMapper.class).activeproductidListbyChannelPartner(idActionObj);
			
			if(productList.isEmpty())
			{	
				LOG.error("No entries found for channelPartnerID: " + channelPartnerID);
			}
			else
			{
				for(String product : productList)
				{
					checkImagesForProduct(product, pos);
				}
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch(URISyntaxException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Checks all image links for a given productID*/
	private static void checkImagesForProduct(String productID, String pos) throws MalformedURLException, IOException, URISyntaxException
	{
		boolean failureFlag = false;
		
		URI uri = new URI("https://www.mybookingpal.com/xml/services/json/product/" + productID 
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
				JSONObject images = null;
				try{
					JSONObject property = (JSONObject) propResponse.get("property");
					images = (JSONObject) property.get("images");
					JSONArray imageArray = images.getJSONArray("image");
					
					for (int i = 0; i < imageArray.length(); i++) {
						try {
							URI imageUri = new URI(imageArray.get(i).toString());
							URL imageUrl = imageUri.toURL();
							URLConnection imageCon = imageUrl.openConnection();
							InputStream imageIn = imageCon.getInputStream(); // this line crashes if http fail is returned
							String imageEncoding = imageCon.getContentEncoding();
							imageEncoding = imageEncoding == null ? "UTF-8" : imageEncoding;
							
							//LOG.debug(imageArray.get(i).toString() + " success");
						}
						catch (IOException e) {
							LOG.error(imageArray.get(i).toString() + " " + "failed");
							failureFlag = true;
						}
						catch (URISyntaxException e) {
							LOG.error(imageArray.get(i).toString() + " " + "invalid URI syntax belonging to product id " + productID);
						}
					}
				}
				catch (ClassCastException e)
				{
					/* This line is reached when there are no images*/
					LOG.error(url.toString());
					LOG.error("No image found");
					System.out.println("");
				}
				catch (JSONException e) {
					/* This line is reached when there is only one image*/
					if(images != null)
					{
						String image = images.getString("image");
						
						try {
							URL imageUrl = new URL(image);
							URLConnection imageCon = imageUrl.openConnection();
							InputStream imageIn = imageCon.getInputStream(); // this line crashes if http fail is returned
							String imageEncoding = imageCon.getContentEncoding();
							imageEncoding = imageEncoding == null ? "UTF-8" : imageEncoding;
							//LOG.debug(imageArray.get(i).toString() + " success");
						}
						catch (IOException f) {
							LOG.error(url.toString());
							LOG.error(image + " " + "failed");
							failureFlag = true;
						}
					}
					else
					{
						LOG.error(url.toString());
					}
				}
			}
			else
			{
				LOG.error(url.toString());
				LOG.error(propResponse.getString("message"));
				System.out.println("");
			}
		}
		catch (JSONException e) {
			LOG.error(url.toString());
			failureFlag = true;
		}
		
		if(failureFlag)
		{
			System.out.println("");
		}
	}
	
	public static void findImagesForPartyId(SqlSession sqlSession, String pos, String partyID)
	{
		try {

			NameIdAction idActionObj = new NameIdAction();
			idActionObj.setId(partyID);
			
			ArrayList<String> productList = sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(idActionObj);
			
			if(productList.isEmpty())
			{	
				LOG.error("No entries found for partyID: " + partyID);
			}
			else
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/test/Desktop/imageList.csv")));
				
				for(String product : productList)
				{
					String uriStr = "https://www.mybookingpal.com/xml/services/json/product/" + product 
							+ "/propertydetail?pos=" + pos + "&test=true";
					try
					{
						URI uri = new URI(uriStr);
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
								JSONObject images = null;
								try{
									JSONObject property = (JSONObject) propResponse.get("property");
									images = (JSONObject) property.get("images");
									JSONArray imageArray = images.getJSONArray("image");
									
									for (int i = 0; i < imageArray.length(); i++) {
										bw.write(product + ", " + imageArray.getString(i));
										bw.write("\n");
									}
								}
								catch (ClassCastException e)
								{
									/* This line is reached when there are no images*/
									LOG.error(url.toString());
									LOG.error("No image found");
									//e.printStackTrace();
									System.out.println("");
								}
								catch (JSONException e) {
									/* This line is reached when there is only one image*/
									if(images != null)
									{
										String image = images.getString("image");
										
										bw.write(product + ", " + image);
										bw.write("\n");
									}
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
						catch (JSONException e) {
							LOG.error(url.toString());
							//e.printStackTrace();
						}
					}
					catch (URISyntaxException e)
					{
						LOG.error("Problem with syntax " + uriStr);
					}
				}
				
				bw.close();
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void findImagesForListOfPartyIds(SqlSession sqlSession, String pos, String filePath)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			
			br.readLine(); // skip first line;
			
			String line;
			
			while((line = br.readLine()) != null)
			{
				line = line.trim().replaceAll("\"", "");
				findImagesForPartyId(sqlSession, pos, line);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		SqlSession sqlSession = RazorServer.openSession();
		String pos = "a502d2c65c2f75d3";
//		String pos = Model.encrypt("320362");
		//testImageLinksbyProductID(sqlSession, pos, "318");
		//testImageLinksbyChannelPartner(sqlSession, pos, "388"); // all rooms
		
		testImageLinksbyPartyID(sqlSession, pos, "90640");
		//findImagesForListOfPartyIds(sqlSession, pos, "/home/test/Desktop/MyVHO_ProductionSuppliers.csv");
		System.out.println("Testing done");
	}
}
