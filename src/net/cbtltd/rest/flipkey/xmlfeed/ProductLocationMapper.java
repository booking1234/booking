package net.cbtltd.rest.flipkey.xmlfeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductFilterSettingsMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.ProductFilterSettings;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.mybookingpal.config.RazorConfig;

public class ProductLocationMapper extends PartnerHandler {

	private static final Logger LOG = Logger.getLogger(ProductLocationMapper.class
			.getName());

	private boolean downloadFiles = true;
	private List<String> listFilesToBePersisted = new ArrayList<String>();
	private int numOfFilesToBeDownloaded = 100;
	private int numOfPropertiesToBePersisted = 5000;
	
	private Set<String> listofUpdatedData=new TreeSet<String>();
	private Set<String> listofInputData=new TreeSet<String>();
	private Set<String> listofInvalidData=new TreeSet<String>();
	private int skippedInFilter=0;
	Map<String,List<String>> filter=null;
	
	private HashMap<String, Location> locationMap;
	private String currentProcessedFile = "";
	/**
	 * Constructor 
	 * @param partner
	 */
	public ProductLocationMapper(Partner partner){
		super(partner);
	}
	public ProductLocationMapper(Partner partner, boolean downloadFile,
			List<String> listFilesToBePersisted, int numOfFilesToBeDownloaded,
			int numOfPropertiesToBePersisted) {
		super(partner);
		this.downloadFiles = downloadFile;
		this.listFilesToBePersisted = listFilesToBePersisted;
		this.numOfFilesToBeDownloaded = numOfFilesToBeDownloaded;
		this.numOfPropertiesToBePersisted = numOfPropertiesToBePersisted;
		locationMap = new HashMap<String, Location>();
	}
	/**
	 * @param partnerid
	 * @return
	 */
	public static Map<String,List<String>> getProductFilterSettings(String partnerid){
		SqlSession sqlSession = null;
		Map<String,List<String>> filters=null;
		try {
			sqlSession = RazorServer.openSession();
			ProductFilterSettingsMapper mapper = sqlSession
					.getMapper(ProductFilterSettingsMapper.class);
			filters=new HashMap<String,List<String>>();
			for (ProductFilterSettings filterSettings : mapper.getImportProductFilterSettings(partnerid)) {
				filters.put(filterSettings.getField().toLowerCase(), filterSettings.getFieldsValues());
			}
		} catch (Exception e) {
			Log.error("DB Exception fetching country codes ", e);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return filters;
	}
	
	public void readProducts() {
		String message = "Read Products started FlipKeyAPIKey: " + getApikey();
		LOG.info(message);
//		Date version = new Date();
		List<String> listPropertyFileNames = new ArrayList<String>();
		try {
			if (downloadFiles) {
				listPropertyFileNames = this.readFlipkeyData();
			} else {
				listPropertyFileNames = this.getListFilesToBePersisted();
			}

		} catch (Exception e1) {
			Log.error(
					"Fatal Exception during readProducts cannot proceed further ",
					e1);
		}

		long start = System.currentTimeMillis();
		filter= getProductFilterSettings(getAltpartyid());
	
		// only of the files are downloaded successfully proceed with the
		// parsing task
		if (listPropertyFileNames != null && listPropertyFileNames.size() > 0) {
			
			for (final String propertyFileName : listPropertyFileNames) {

				try {

							File file = new File(
									FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
											+ propertyFileName
											+ FlipKeyUtils.XML_EXTENSION);
							currentProcessedFile = FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
									+ propertyFileName
									+ FlipKeyUtils.XML_EXTENSION; 
							JAXBContext jaxbContext = JAXBContext
									.newInstance(PropertyData.class);
							Unmarshaller jaxbUnmarshaller = jaxbContext
									.createUnmarshaller();
							PropertyData propertyData = (PropertyData) jaxbUnmarshaller
									.unmarshal(file);
							LOG.info("Completed unmarshalling of file "
									+ propertyFileName + " "
									+ propertyData.getProperty().size());
							LOG.info("Before Calling Create Product");
							this.createProduct(propertyData);

						} catch (Exception e) {
							Log.error(
									"Fatal Exception during unmarshalling file "
											+ propertyFileName, e);

						}



			}

			long end = System.currentTimeMillis();
			
			LOG.info("Started persist @ "+start);
			LOG.info("Ended persist @ "+end);
			LOG.info("Completed the persisting of product in " + (end - start)
					/ (1000 * 60) + " min");
			LOG.info("--------------------------------");
			LOG.info( "Total no of properties to persist : "+numOfPropertiesToBePersisted);
			LOG.info( " + Total no of properties skipped on filter : "+skippedInFilter);
			LOG.info( " + Total no of properties records processed : "+listofInputData.size());
			LOG.info( " + Total no records  updated : "+listofUpdatedData.size());
			LOG.info( " + Total no of properties that are missing in the database: " + (listofInputData.size() - listofUpdatedData.size()));
			LOG.info("Below products(AltIDs) were updated: ");
			LOG.info(listofUpdatedData);
			LOG.info("Below are products(AltIDs) with invalid location attributes - city missing"
					+ " or locationName not eqauls city");
			LOG.info(listofInvalidData);
			
			

//			MonitorService.monitor(message, version);
		} else {
			LOG.info("Download of files failed so skipping the parsing step");
		}
	}

	private void createProduct(PropertyData propertyData) {
		
		int cnt =0;
		
		try{
				//for (final Property prop : propertyData.getProperty()) {
				for(int i=0;i<propertyData.getProperty().size();i++){
					
					final Property prop =propertyData.getProperty().get(i);
					if(FlipKeyUtils.isProductPersistRequired(prop,filter))
					{
						saveProduct(prop);
					}
					else
					{
						skippedInFilter++;
					}
					cnt++;
					if (cnt >= numOfPropertiesToBePersisted) {
						break;
					}
				}
		}finally {
			//sqlSession.close();

		}

		// TODO:Confim with chirayu whether we need to delete the file at the
		// end
		// delete(fileName);
	}

	void saveProduct(Property prop) {
		Product xmlProduct = new Product();
		Property property =prop;
		SqlSession sqlSession =null;
		try {
		sqlSession = RazorServer.openSession();
		xmlProduct.setAltpartyid(getAltpartyid());
		xmlProduct.setSupplierid(getAltpartyid());
		buildProductDetails(property, xmlProduct);
		listofInputData.add(xmlProduct.getAltid());
		LOG.info("-----------------------------------------------------------------------");
		LOG.info("Processing product with AltID:" + xmlProduct.getAltid() + " " + xmlProduct.getName()
				+ " found in file: " + currentProcessedFile);
		Product product = PartnerService.getProductWithoutInsert(sqlSession, xmlProduct);
		if(product != null && product.getLocationid() == null)
		{
			buildProductAttributes(property, xmlProduct, sqlSession);
			xmlProduct.setId(product.getId());
			
			// persist the properties of product
			// SN:persistProduct will not be updated on the daily run basis
			if(xmlProduct.getLocationid() != null)
				persistProduct(sqlSession, xmlProduct);
		}
		
		sqlSession.commit();
		} catch (Throwable x) {
			if(sqlSession !=null)
			sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
		} finally {
			if(sqlSession !=null)
				sqlSession.close();

		}
 	}
	
	public List<String> readFlipkeyData() throws Exception {

		LOG.info("Inside readFlipkeyData Starting to download the file ");
		long start = System.currentTimeMillis();
		List<String> listPropertyLink = this.readPropertyDataList();
		LOG.info("No of files to be downloaded " + listPropertyLink.size());
		List<String> listSuccessFullyDwnloadedPropertyLink = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(1);
		// ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<DownloadTaskStatus>> listFutureDownloadTaskStatus = new ArrayList<Future<DownloadTaskStatus>>();
		int cnt = 0;
		for (String propertyLink : listPropertyLink) {
			// download each file in it's own thread

			listFutureDownloadTaskStatus.add(executor
					.submit(new XMLDownloadTask(propertyLink)));
			cnt++;
			if (cnt >= this.numOfFilesToBeDownloaded) {
				break;
			}

		}

		executor.shutdown();
		for (Future<DownloadTaskStatus> futureDownloadTaskStatus : listFutureDownloadTaskStatus) {
			DownloadTaskStatus downloadTaskStatus = futureDownloadTaskStatus
					.get();
			if (downloadTaskStatus.isDownloadSucess()) {
				listSuccessFullyDwnloadedPropertyLink.add(downloadTaskStatus
						.getFileName());
			}
		}
		LOG.info("All the threads Completed the execution ");
		long end = System.currentTimeMillis();
		LOG.info("Completed the download in  " + (end - start) / (1000 * 60)
				+ " m");
		LOG.info("No of files successfully downloaded is  "
				+ listSuccessFullyDwnloadedPropertyLink.size());

		return listSuccessFullyDwnloadedPropertyLink;
		// return new ArrayList<String>();

	}
	
	public boolean isDownloadFiles() {
		return downloadFiles;
	}

	public void setDownloadFiles(boolean downloadFiles) {
		this.downloadFiles = downloadFiles;
	}

	public List<String> getListFilesToBePersisted() {
		return listFilesToBePersisted;
	}

	public void setListFilesToBePersisted(List<String> listFilesToBePersisted) {
		this.listFilesToBePersisted = listFilesToBePersisted;
	}
	
	public void buildProductDetails(Property property, Product xmlProduct) {
		Log.info("Inside buildProductDetails for Product ");
		for (Object obj : property.getPropertyDetails()
				.getCurrencyOrNameOrBathroomCount()) {
			if (obj instanceof JAXBElement) {
				JAXBElement jaxbElement = (JAXBElement) obj;
				if ("name".equalsIgnoreCase(jaxbElement.getName().toString())) {
					xmlProduct.setName((String) jaxbElement.getValue());
					Log.info("Processing product data for "
							+ xmlProduct.getName());
					
					xmlProduct.setName((xmlProduct.getName().length() > 100) ? xmlProduct
							.getName().substring(0, 99) : xmlProduct.getName());
				} 
			}
		}
		
		for (Object obj : property
				.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities()) {

			if (obj instanceof PropertyAttributes) {
				PropertyAttributes propertyAttributes = (PropertyAttributes) obj;
				xmlProduct.setAltid(propertyAttributes.getPropertyId()
						.intValue() + "");
				//FIX ME...owner id will be supplier of PM id..in reality this is id of property owner
				//Fixed....Commenting the line here and setting ouwner id as supplier id
				/*xmlProduct.setOwnerid(propertyAttributes.getUserId().intValue()
						+ "");*/

			}
		}
			
		
	}
	
	public void buildProductAttributes(Property property, Product xmlProduct, SqlSession sqlSession)
			throws Exception {
		for (Object obj : property
				.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities()) {

			if (obj instanceof PropertyAttributes) {
				PropertyAttributes propertyAttributes = (PropertyAttributes) obj;
				xmlProduct.setAltid(propertyAttributes.getPropertyId()
						.intValue() + "");
				/*xmlProduct.setOwnerid(propertyAttributes.getUserId().intValue()
						+ "");*/

			}
			if (obj instanceof PropertyAddresses) {
				StringBuilder builder = new StringBuilder();
				PropertyAddresses propertyAddr = (PropertyAddresses) obj;
				String countryCode = "";
				String stateCode="";
				String city = "";
				String zipCode = "";
				if(StringUtils.isNotEmpty(propertyAddr.country))
					countryCode = PartnerService.getCountryCode(propertyAddr.country.trim());
				
				if(StringUtils.isNotEmpty(propertyAddr.address1)) 
					builder.append(propertyAddr.address1).append(", ");
				if(StringUtils.isNotEmpty(propertyAddr.address2))  
					builder.append(propertyAddr.address2).append(", ");
				if(StringUtils.isNotEmpty(propertyAddr.city)) 
				{
					builder.append(propertyAddr.city).append(" ");
					city = propertyAddr.city;
				}
				if(StringUtils.isNotEmpty(propertyAddr.zip))
				{
					builder.append(propertyAddr.zip).append(", ");
					zipCode = propertyAddr.zip;
				}
				if(StringUtils.isNotEmpty(propertyAddr.state)) {
					stateCode=PartnerService.getRegionCode(propertyAddr.state);
					builder.append(propertyAddr.state).append(" ");
									
				}
					
				if(StringUtils.isNotEmpty(countryCode))  
					builder.append(countryCode.toUpperCase());
				
				xmlProduct.setPhysicaladdress(builder.toString());
				
				//Display address is set to true (default), if ShowExactAddress not exist
				if(StringUtils.isNotEmpty(propertyAddr.getShowExactAddress())){
					//Display address is set to true(if ShowExactAddress=1).  false otherwise
					xmlProduct.setDisplayaddress("1".equalsIgnoreCase(propertyAddr.getShowExactAddress()));
				}
				
				if(StringUtils.isEmpty(city) || StringUtils.isEmpty(propertyAddr.locationName) || !city.equalsIgnoreCase(propertyAddr.locationName))
				{
					LOG.error("Property: " + xmlProduct.getName() + " has city: " + propertyAddr.city + " location Name: " + propertyAddr.locationName);
					listofInvalidData.add(xmlProduct.getAltid());
				}
				
				LOG.info("Setting location from address: " + xmlProduct.getPhysicaladdress() + " country:|" + propertyAddr.country + "|");
				
				if(StringUtils.isEmpty(stateCode)){
					stateCode=propertyAddr.getState();
				}
				if(StringUtils.isEmpty(countryCode)){
					countryCode=propertyAddr.country;
				}
				
				String locationKey = city + stateCode + countryCode;
				locationKey = locationKey.toLowerCase();
								
				Location location = null;
				try {
					
					if(StringUtils.isNotEmpty(city)) {
					    location = locationMap.get(locationKey);
					    if(location == null) {
					    	LOG.info("Searching location with state code " + stateCode+" countryCode "+countryCode + " city " + city);
					    	location = PartnerService.getLocation(sqlSession, city, stateCode, countryCode, 
								doubleParse(propertyAddr.getLatitude(), 0),
								doubleParse(propertyAddr.getLongitude(), 0), 
								zipCode);
					    	
					    	locationMap.put(locationKey, location);
					    }
					} else {
						location = PartnerService.getLocation(sqlSession, zipCode, countryCode, 
								doubleParse(propertyAddr.getLatitude(), 0),
								doubleParse(propertyAddr.getLongitude(), 0));
					}
					
					
					if(location != null) {
						LOG.info("Setting location : " + location.getName());
						xmlProduct.setLocationid(location.getId());
					}
					else {
						LOG.error("Location is null");
					}

				} catch (Throwable t) {
					LOG.error("Error while doing location lookup ", t);
				}
			}
			

		}
		LOG.info("Property attributes are built...");
	}
	
	private void persistProduct(SqlSession sqlSession, Product xmlProduct) {
		LOG.info("Check whether Product data exists for data "
				+ xmlProduct.getName() + " AltID " + xmlProduct.getAltid());

			
		PartnerService.updateProduct(sqlSession, xmlProduct);
		
		listofUpdatedData.add(xmlProduct.getAltid());
		LOG.info("Product updated successfully AltID: "
				+ xmlProduct.getAltid());
		
	}
	
	private static double doubleParse(String value,double defaultvalue){
		try {
			if (StringUtils.isNotBlank(value) && StringUtils.isNotEmpty(value) && NumberUtils.isNumber(value)) {
				return Double.parseDouble(value);
			}
		} catch (Exception e) {
		}
		return defaultvalue;
	}
	
	private List<String> readPropertyDataList() throws Exception {

		List<String> listProperty = new ArrayList<String>();

		String htmlString = getHTMLData(
				RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL),
				RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME),
				RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
		LOG.info("Flipkey XML FEED URL"
				+ RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL));

		HTMLLinkExtractor htmlLinkExtractor = new HTMLLinkExtractor();

		List<HTMLLink> linksList = htmlLinkExtractor.grabHTMLLinks(htmlString);
		for (HTMLLink link : linksList) {
			if (link.getLink() != null
					&& link.getLink().contains("property_data")) {
				Log.info("Adding link " + link.getLink());
				listProperty.add(link.getLink());
			} else {
				Log.info("Skipping link " + link.getLink());
			}

		}
		return listProperty;
	}
	
	private String getHTMLData(String flipKeyURL, String username, String pwd)
			throws Exception {

		String htmlString = "";
		HttpURLConnection connection = FlipKeyUtils.getConnection(flipKeyURL,
				username, pwd);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {
				htmlString += line;

			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return htmlString;

	}
	
	public static void main(String args[])
	{
		if(args.length<4) { System.out.println("Some of the arguements are missing.."); return; }
		String altpartyid=args[0];
		boolean downloadFile="true".equalsIgnoreCase(args[1])?true:false;
		int numOfFilesToBeDownloaded=Integer.parseInt(args[2].trim());
		int numOfPropertiesToBePersisted=Integer.parseInt(args[3].trim());
		
		System.out.println("Altpartyid "+altpartyid);
		System.out.println("downloadFile ? "+downloadFile);
		System.out.println("numOfFiles to process = "+numOfFilesToBeDownloaded);
		System.out.println("numOfPropertiesToBePersisted="+numOfPropertiesToBePersisted);
		
		List<String> listFilesToBePersisted=null;
		if(args.length>4&& args[4]!=null&&!"".equalsIgnoreCase(args[4]))
		{
			String[] files=args[4].split(",");
			listFilesToBePersisted=java.util.Arrays.asList(files);
		}
		System.out.println("Starting location handler");
		
		SqlSession sqlSession = RazorServer.openSession();
		// String altpartyid = "179769";
		//Flip key uat supplierid = 325490
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		
		new ProductLocationMapper(partner, downloadFile, listFilesToBePersisted, numOfFilesToBeDownloaded, numOfPropertiesToBePersisted)
		.readProducts();
	}
	
}
