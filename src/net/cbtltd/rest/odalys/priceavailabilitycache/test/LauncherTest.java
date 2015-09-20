package net.cbtltd.rest.odalys.priceavailabilitycache.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Property;
import net.cbtltd.rest.odalys.entity.Segments;
import net.cbtltd.rest.odalys.entity.SegmentsRoot;
import net.cbtltd.rest.odalys.priceavailabilitycache.A_Handler;
import net.cbtltd.rest.odalys.priceavailabilitycache.Launcher;
import net.cbtltd.rest.odalys.priceavailabilitycache.OdalysUtils;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Partner;

import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Senthilnathan
 * 
 */
public class LauncherTest {

	Partner partner;
	private static final Logger LOG = Logger.getLogger(LauncherTest.class
			.getName());
	Launcher launcher=null;
	static String localRESTURL = "http://localhost:8080/Razor/xml/rest/product/#productID#/detail?pos=6f140574d2030101&test=true";
	@BeforeClass
	public static void setUp() throws Exception {
		
//		Model.encrypt("325490"); //6f140574d2030101
//		Model.decrypt("6f140574d2030101");
		
	}

	@Test
	public void testReadProducts() throws Exception {
		launcher=new Launcher("325490");
		//launcher.readProducts();
		validateXMLwithRestUrl();
	}

	
	
	SqlSession sqlSession;
	@Test
	public void validateXMLwithRestUrl() {
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Prices.Price> priceUnitMap=new HashMap<String, Segments.Prices.Price>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin> dateBeginMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration> durationMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At.Occupancies.Occupancy> occupanyMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At.Occupancies.Occupancy>();
	
		 A_Handler handler=launcher.getHandler("325490");
		  List<String> listFileNames = new ArrayList<String>();
		 String propertyFileName="PAC_SAMPLE";

		File file = new File(
				OdalysUtils.ODALYS_XML_LOCAL_FILE_PATH
						+ propertyFileName
						+ OdalysUtils.XML_EXTENSION);
		listFileNames.add(file.getPath());
		try {
			sqlSession = RazorServer.openSession();
			
			for (String fileNameStr : listFileNames) {
				List<net.cbtltd.shared.price.PriceCreate> priceList=null;
				JAXBContext jaxbContext = JAXBContext
						.newInstance( SegmentsRoot.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext
						.createUnmarshaller();
				SegmentsRoot propertyData = (SegmentsRoot) jaxbUnmarshaller
						.unmarshal(new File(fileNameStr));
				for(Segments seg :propertyData.getSegments()){
					if("Defaults".equalsIgnoreCase(seg.getWhat())){
						handler.loadPriceAndEntities(seg,priceUnitMap,dateBeginMap,durationMap,occupanyMap);
						}
				}
				for(Segments seg :propertyData.getSegments()){
					if(!"Defaults".equalsIgnoreCase(seg.getWhat())){
						priceList=handler.loadSegment(seg,priceUnitMap,dateBeginMap,durationMap,occupanyMap);
						
					}
				}
				if(priceList==null||priceList.isEmpty()) { Assert.fail("Not able to parse file or file is empty");}
				Map<String,Integer> freq=new HashMap<String, Integer>();
				
				for(net.cbtltd.shared.price.PriceCreate price : priceList){
					if(freq.containsKey(price.getEntityid()))
						{ freq.put( price.getEntityid(), freq.get(price.getEntityid() ).intValue()+1); }
					else
					{ freq.put( price.getEntityid(), 1); }
				}
				
				for(String prodId:occupanyMap.keySet()){
					Property  restProperty =null;
					String productID=prodId.replace("A", "");
				try{
					restProperty =determineRestProperty(productID);
					if(restProperty.getPricetable()==null){
						System.out.println(productID+" is not is db");
					}
				}catch(Exception e){
					System.out.println(productID+" is not is db");
				}
				
				
				if(priceList!=null && restProperty
						.getPricetable()!=null && freq.containsKey(productID)){
					System.out.println("Comparing price details of #"+productID);
					Assert.assertEquals(
							freq.get(productID).intValue(), restProperty
									.getPricetable().price.size() );
//					if (comparePrice(listPrice, restProperty)) {
//						fields.add(new ComparableField("Price values",
//								"All matches", "All matches"));
//					}else
//					{
//						fields.add(new ComparableField("Price values",
//								"some field mismatches", ""));
//					}
				}
				}
				
			}
				
			} catch(Exception e){}
	}
	private Property determineRestProperty(String productID)
			throws ClientProtocolException, IOException, JAXBException {

		Property property = null;
		HttpURLConnection connection = null;
		String localURL = localRESTURL.replace("#productID#", productID);
		LOG.info(" localRESTURL " + localURL);
	    try {
	        URL url = new URL(localURL);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			//StringBuffer result = new StringBuffer();
			StringWriter xmlWriter = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				xmlWriter.write(line);
			}
	              
			StringReader xmlreader = new StringReader(xmlWriter.toString());
			JAXBContext jaxbContext = JAXBContext.newInstance(Property.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			property = (Property) jaxbUnMarshaller.unmarshal(xmlreader);
			
	    } catch (MalformedURLException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    } finally {
	        if(null != connection) { connection.disconnect(); }
	    }
		
		
		return property;

	}	
	
	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}

}
