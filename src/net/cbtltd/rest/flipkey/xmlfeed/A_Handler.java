package net.cbtltd.rest.flipkey.xmlfeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.flipkey.inquiry.Inquiry;
import net.cbtltd.rest.flipkey.inquiry.Inquiry.Data;
import net.cbtltd.rest.flipkey.inquiry.Inquiry.Header;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.api.ProductFilterSettingsMapper;
import net.cbtltd.server.api.ProductUpdateIntervalMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.ProductFilterSettings;
import net.cbtltd.shared.ProductUpdateInterval;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;

/**
 * Handler to do the load operation of xml from flipkey
 * @author nibodha
 *
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private boolean downloadFiles = true;
	private List<String> listFilesToBePersisted = new ArrayList<String>();
	private int numOfFilesToBeDownloaded = 100;
	private int numOfPropertiesToBePersisted = 5000;
	
	private Set<String> listofInsertedData=new TreeSet<String>();
	private Set<String> listofUpdatedData=new TreeSet<String>();
	private Set<String> listofInputData=new TreeSet<String>();
	private int skippedInFilter=0;
	Map<String,List<String>> filter=null;
	
	private final static String US_COUNTRY_NAME 	= "United States";
	private final static String US_ISO_COUNTRY_NAME = "US";
	/**
	 * Constructor 
	 * @param partner
	 */
	public A_Handler(Partner partner){
		super(partner);
	}
	public A_Handler(Partner partner, boolean downloadFile,
			List<String> listFilesToBePersisted, int numOfFilesToBeDownloaded,
			int numOfPropertiesToBePersisted) {
		super(partner);
		this.downloadFiles = downloadFile;
		this.listFilesToBePersisted = listFilesToBePersisted;
		this.numOfFilesToBeDownloaded = numOfFilesToBeDownloaded;
		this.numOfPropertiesToBePersisted = numOfPropertiesToBePersisted;
	}
	/**
	 * @param partnerid
	 */
	public static void loadUpdateInterval(String partnerid){
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				ProductUpdateIntervalMapper mapper = sqlSession
						.getMapper(ProductUpdateIntervalMapper.class);
				for (ProductUpdateInterval productUpdInt : mapper.getProductUpdateInterval(partnerid)) {
					ProductUpdateInterval.setInstance(productUpdInt);
				}
			} catch (Exception e) {
				Log.error("DB Exception fetching country codes ", e);
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
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
	
	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		return true;

	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readPrices() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSchedule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readProducts()
	 */
	@Override
	public void readProducts() {
		String message = "Read Products started FlipKeyAPIKey: " + getApikey();
		LOG.info(message);
		Date version = new Date();
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
		loadUpdateInterval(getAltpartyid());
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
			LOG.info( "    + Total no records inserted : "+listofInsertedData.size());
			LOG.info( "    + Total no records  updated : "+listofUpdatedData.size());
			LOG.info( (listofInputData.size()==(listofInsertedData.size()+listofUpdatedData.size()))?"All records processed":"below records are missed" );
			LOG.info(SetOperationUtils.difference(listofInputData, SetOperationUtils.union(listofInsertedData, listofUpdatedData)));
			
			

			MonitorService.monitor(message, version);
		} else {
			LOG.info("Download of files failed so skipping the parsing step");
		}

	}

	private void createProduct(PropertyData propertyData) {
		
		int cnt =0;
		ExecutorService executor = Executors
				.newFixedThreadPool(1);
		
		try{
				//for (final Property prop : propertyData.getProperty()) {
				for(int i=0;i<propertyData.getProperty().size();i++){
					
					final Property prop =propertyData.getProperty().get(i);
					if(FlipKeyUtils.isProductPersistRequired(prop,filter))
					{
						executor.execute(new Runnable() {
	
						@Override
						public void run() {
	
							long start = System.currentTimeMillis();
							Product product = saveProduct(prop);
							long end = System.currentTimeMillis();
							if (product != null) {
								if (product.getRefresh() == 0) {
									listofInsertedData.add(product.getAltid());
									LOG.info("Product add successfully : "
											+ product.getAltid() + "{" + ","
											+ start + "," + end + ", "
											+ (end - start) + " }");
								} else {
									listofUpdatedData.add(product.getAltid());
									LOG.info("Product updated successfully : "
											+ product.getAltid() + "{" + ","
											+ start + "," + end + ", "
											+ (end - start) + " }");
									}
								} 
							}
						});
					}else{
						skippedInFilter++;
					}
					cnt++;
					if (cnt >= numOfPropertiesToBePersisted) {
						break;
					}
					
		
				}
				executor.shutdown();
				// Wait until all threads are finish
				while (!executor.isTerminated()) {
				}
		}finally {
			//sqlSession.close();

		}

		// TODO:Confim with chirayu whether we need to delete the file at the
		// end
		// delete(fileName);
	}

	Product saveProduct(Property prop) {
		Product product = null;
		Product xmlProduct = new Product();
		Property property =prop;
		ArrayList<String> attributes = new ArrayList<String>();
		List<Date> listBookedDate = new ArrayList<Date>();
		Map<String, String> mapProductDesc = new HashMap<String, String>();
		Map<String, String> mapImages = new HashMap<String, String>();
		List<Price> listPrice = new ArrayList<Price>();
		SqlSession sqlSession =null;
		try {
		sqlSession = RazorServer.openSession();
		Integer minStay = 0;
		xmlProduct.setAltpartyid(getAltpartyid());
		xmlProduct.setSupplierid(getAltpartyid());
		buildProductDetails(property, xmlProduct, attributes, minStay);
		LOG.info("-----------------------------------------------------------------------");
		LOG.info("Processing product :" + xmlProduct.getAltid() + " " + xmlProduct.getName());
		buildProductAttributes(property, xmlProduct, attributes,
				sqlSession, mapProductDesc, listBookedDate, listPrice,
				mapImages);

		listofInputData.add(xmlProduct.getAltid());
		// persist the properties of product
		// SN:persistProduct will not be updated on the daily run basis
		product = persistProduct(sqlSession, xmlProduct);
	
		
		// persist the properties of Attributes
		// SN:persistAttributes will not be updated on the daily run
		// basis
		if (product.getRefresh()==0||ProductUpdateInterval.attributeUpdateInterval.isRequiredUpdate()) {
			persistAttributes(sqlSession, product, attributes);
		}

		// persist the description of the Property
		// SN:persistDescription will not be updated on the daily run
		// basis
//		if (product.getRefresh()==0||ProductUpdateInterval.descriptionUpdateInterval.isRequiredUpdate()){
			persistDescription(sqlSession, product, mapProductDesc);
//		}

		// persist the minStay of product
		// SN:Min Stay will be updated on the daily run basis
		if (product.getRefresh()==0||ProductUpdateInterval.minStayUpdateInterval.isRequiredUpdate()){
			persistMinStay(sqlSession, product, minStay);
		}

		

		// persistRate data
		// SN:Price Data will be updated on the daily run basis
		if (product.getRefresh()==0||ProductUpdateInterval.priceDataUpdateInterval.isRequiredUpdate()) {
			PriceService.getInstance().persistPriceData(sqlSession, listPrice);
		}

		// persist booked Date in reservation table
		// SN:Booked Date will be updated on the daily run basis
		if (product.getRefresh()==0||ProductUpdateInterval.bookedDateUpdateInterval.isRequiredUpdate()){
			persistBookedDate(sqlSession, product, listBookedDate, listPrice);
		}
		
		// persist Image data
		// SN:persistImageData will not be updated on the daily run
		// basis
		if (product.getRefresh()==0||ProductUpdateInterval.imageDataUpdateInterval.isRequiredUpdate()){
			persistImageData(sqlSession, product, mapImages);
		}
		
		sqlSession.commit();
		return product;
		} catch (Throwable x) {
			if(sqlSession !=null)
			sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
		} finally {
			if(sqlSession !=null)
				sqlSession.close();

		}
		return null;
 	}
	
	private void buildBookedDates(PropertyCalendar propertyCalendar,
			List<Date> listBookedDate) throws ParseException {

		Log.info("Inside buildBookedDates ");

		for (Serializable serializable : propertyCalendar.getContent()) {
			JAXBElement element = (JAXBElement) serializable;
			try {
				listBookedDate.add(sdf.parse(element.getValue().toString()));
			} catch (Exception e) {
				Log.info("Error while parsing date in listBookedDate.add so date defaulted to today");
				//listBookedDate.add(new Date());
			}
		}
		Log.info("Size of booked date is " + listBookedDate.size());

	}

	private void buildImageData(PropertyPhotos propertyPhotos,
			Map<String, String> mapImages) throws ParseException {

		Log.info("Inside buildImageData ");

		for (PropertyPhoto propertyPhoto : propertyPhotos.getPropertyPhoto()) {
			List<String> imageVariations = new ArrayList<String>();
			String baseURL = propertyPhoto.getBaseUrl();
			String photoFileName = propertyPhoto.getPhotoFileName();
			String fullPhotoURL = "";
			for (Object obj : propertyPhoto.getOrderOrTypeOrHeight()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("largest_image_prefix".equalsIgnoreCase(element
							.getName().toString())) {
						if ("thumb".equalsIgnoreCase((String) element
								.getValue())) {
							imageVariations.add("thumb");
						} else if ("large".equalsIgnoreCase((String) element
								.getValue())) {
							imageVariations.add("thumb");
							imageVariations.add("large");
						} else if ("640x480".equalsIgnoreCase((String) element
								.getValue())) {
							imageVariations.add("thumb");
							imageVariations.add("large");
							imageVariations.add("640x480");
						}
					}
				}
			}
			// build different variations of photo here
			
			for (String variation : imageVariations) {

				fullPhotoURL = RazorConfig
						.getValue(FlipKeyUtils.FLIPKEY_IMAGE_URI)
						.replace("#baseURL", baseURL)
						.replace("#prefix", variation)
						.replace("#photoFileName", photoFileName);
				mapImages.put(photoFileName + "_" + variation, fullPhotoURL);
			}

		}

		Log.info("Size of Image is " + mapImages.size());

	}

	private void buildMonthlyPriceData(Product product,
			PropertyRates propertyRates, List<Price> listPrice)
			throws ParseException {

		Log.info("Inside buildMonthlyPriceData ");

		for (PropertyRate propertyRate : propertyRates.getPropertyRate()) {
			Price price = new Price();
			// FIX...this should be price/rate id we receive from flipkey
			/**
			 *  There is no price or rate id for the property_rate which we receive from flipkey,, please find the sample below
			 * 
			 * 	<property_rate>
				<weekend_min_rate></weekend_min_rate>
				<weeknight_max_rate>109.0</weeknight_max_rate>
				<end_date>2014-06-17</end_date>
				<weekend_nights></weekend_nights>
				<weekend_max_rate></weekend_max_rate>
				<start_date>2014-05-23</start_date>
				<label></label>
				<week_max_rate></week_max_rate>
				<per_person_per_stay></per_person_per_stay>
				<per_person_per_night></per_person_per_night>
				<minimum_length>1</minimum_length>
				<turn_day></turn_day>
				<is_changeover_day_defined></is_changeover_day_defined>
				<per_person_over_amount></per_person_over_amount>
				<month_max_rate></month_max_rate>
				<weeknight_min_rate>109.0</weeknight_min_rate>
				<week_min_rate></week_min_rate>
				<min_stay_unit>night</min_stay_unit>
				<month_min_rate></month_min_rate>
				<min_stay_count>1</min_stay_count>
				<changeover_day></changeover_day>
			</property_rate>
			 */
			price.setAltid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setName("Monthly Rate");
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			for (Object obj : propertyRate
					.getChangeoverDayOrIsChangeoverDayDefinedOrMinStayCount()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("start_date".equalsIgnoreCase(element.getName()
							.toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
							try {
								price.setDate(sdf.parse(value));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in Monthly Rate so date defaulted to today");
								price.setDate(new Date());
							}
							try {
								price.setRule(FlipKeyUtils.getDayOfWeek(sdf
										.parse(value)));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in Monthly Rate so date defaulted to today");
								price.setRule(FlipKeyUtils
										.getDayOfWeek(new Date()));
							}
						}
						//
					} else if ("minimum_length".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
								price.setMinStay(integerParse(value,0));
						}
					} else if ("month_max_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.MON);
							price.setValue(doubleParse(value,0.0));
					} else if ("month_min_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.MON);
							price.setMinimum(doubleParse(value,0.0));
					}
				}
			}
			try {
				price.setTodate(sdf.parse(propertyRate.getEndDate()));
			} catch (Exception e) {
				Log.info("Error while parsing date in price.setTodate so date defaulted to today");
				price.setTodate(new Date());
			}
			if (price.getRule()!=null && price.getMinimum() != null
					&& (price.getMinimum() > 0 || price.getValue() > 0)) {
				listPrice.add(price);
			}

		}
		Log.info("Size of monthly price data is " + listPrice.size());

	}

	private void buildWeekNightPriceData(Product product,
			PropertyRates propertyRates, List<Price> listPrice)
			throws ParseException {

		Log.info("Inside buildWeekNightPriceData ");

		for (PropertyRate propertyRate : propertyRates.getPropertyRate()) {
			Price price = new Price();
			//FIX ME
			//Fixed--Modified Price to priceCreate and using PriceService to create Price instance
			price.setAltid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setName("Weeknight Rate");
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			for (Object obj : propertyRate
					.getChangeoverDayOrIsChangeoverDayDefinedOrMinStayCount()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("start_date".equalsIgnoreCase(element.getName()
							.toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
							try {
								price.setDate(sdf.parse(value));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in weeknight Rate so date defaulted to today");
								price.setDate(new Date());
							}
							try {
								price.setRule(FlipKeyUtils.getDayOfWeek(sdf
										.parse(value)));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in weeknight Rate so date defaulted to today");
								price.setRule(FlipKeyUtils
										.getDayOfWeek(new Date()));
							}
						}
						//
					} else if ("minimum_length".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
								price.setMinStay(integerParse(value,0));
						}
					} else if ("weeknight_min_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.DAY);
							price.setMinimum(doubleParse(value,0.0));
					}
				}
			}
			price.setValue(doubleParse(propertyRate
						.getWeeknightMaxRate(),0.0));
			try {
				price.setTodate(sdf.parse(propertyRate.getEndDate()));
			} catch (Exception e) {
				Log.info("Error while parsing to date default todays date");
				price.setTodate(new Date());
			}
			if (price.getRule()!=null && price.getMinimum() != 0 || price.getValue() != 0) {
				listPrice.add(price);
			}

		}
		Log.info("Size of weeknight price  data is " + listPrice.size());

	}

	private void buildWeekEndPriceData(Product product,
			PropertyRates propertyRates, List<Price> listPrice)
			throws ParseException {

		Log.info("Inside buildWeekEndPriceData ");

		for (PropertyRate propertyRate : propertyRates.getPropertyRate()) {
			Price price = new Price();
			// FIX ME
			//Fixed--Modified Price to priceCreate and using PriceService to create Price instance
			price.setAltid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setName("Weekend Rate");
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			for (Object obj : propertyRate
					.getChangeoverDayOrIsChangeoverDayDefinedOrMinStayCount()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("start_date".equalsIgnoreCase(element.getName()
							.toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
							try {
								price.setDate(sdf.parse(value));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in Monthly Rate so date defaulted to today");
								price.setDate(new Date());
							}
							try {
								price.setRule(FlipKeyUtils.getDayOfWeek(sdf
										.parse(value)));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in Monthly Rate so date defaulted to today");
								price.setRule(FlipKeyUtils
										.getDayOfWeek(new Date()));
							}
						}
						//
					} else if ("minimum_length".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
								price.setMinStay(integerParse(value,0));
						}
					} else if ("weekend_max_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.DAY);
							price.setValue(doubleParse(value,0.0));
					}
				}
			}
			price.setMinimum(doubleParse(propertyRate
						.getWeekendMinRate(),0.0));

			try {
				price.setTodate(sdf.parse(propertyRate.getEndDate()));
			} catch (Exception e) {
				Log.info("Error while parsing to date default todays date");
				price.setTodate(new Date());
			}

			if (price.getRule()!=null && price.getMinimum() != null
					&& (price.getMinimum() != 0 || price.getValue() != 0)) {
				listPrice.add(price);
			}

		}
		Log.info("Size of weekend price  data is " + listPrice.size());

	}

	private void buildWeekPriceData(Product product,
			PropertyRates propertyRates, List<Price> listPrice)
			throws ParseException {

		Log.info("Inside buildWeekPriceData ");

		for (PropertyRate propertyRate : propertyRates.getPropertyRate()) {
			Price price = new Price();
			// FIX ME
			//Fixed--Modified Price to priceCreate and using PriceService to create Price instance
			price.setAltid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setName("Week Rate");
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			for (Object obj : propertyRate
					.getChangeoverDayOrIsChangeoverDayDefinedOrMinStayCount()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("start_date".equalsIgnoreCase(element.getName()
							.toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
							try {
								price.setDate(sdf.parse(value));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in week Rate so date defaulted to today");
								price.setDate(new Date());
							}
							try {
								price.setRule(FlipKeyUtils.getDayOfWeek(sdf
										.parse(value)));
							} catch (Exception e) {
								Log.info("Error while parsing date in price.setDate in week Rate so date defaulted to today");
								price.setRule(FlipKeyUtils
										.getDayOfWeek(new Date()));
							}
						}
						//
					} else if ("minimum_length".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
						if (StringUtils.isNotEmpty(value)) {
								price.setMinStay(integerParse(value,0));
						}
					} else if ("week_max_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.WEE);
							price.setValue(doubleParse(value,0));
					} else if ("week_min_rate".equalsIgnoreCase(element
							.getName().toString())) {
						String value = (String) element.getValue();
							price.setUnit(Unit.WEE);
							price.setMinimum((doubleParse(value,0)));
					}
				}
			}

			try {
				price.setTodate(sdf.parse(propertyRate.getEndDate()));
			} catch (Exception e) {
				Log.info("Error while parsing to date default todays date");
				price.setTodate(new Date());
			}
			if (price.getRule()!=null && price.getMinimum() != 0 || price.getValue() != 0) {
				listPrice.add(price);
			}

		}
		Log.info("Size of week price  data is " + listPrice.size());

	}

	private void buildPropertyFeeData(Product product,
			PropertyFees propertyFees, List<Price> listPrice)
			throws ParseException {
		Log.info("Inside buildPropertyFeeData ");
		for (PropertyFee fee : propertyFees.getPropertyFee()) {
			Price price = new Price();
			// FIX ME
			//Fixed--Modified Price to priceCreate and using PriceService to create Price instance
			price.setAltid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType("FEE");
			price.setOrganizationid(product.getAltpartyid());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());

			for (Object obj : fee.getDescriptionOrNameOrType()) {
				if (obj instanceof JAXBElement) {
					JAXBElement element = (JAXBElement) obj;
					if ("name".equalsIgnoreCase(element.getName().toString())) {
						String value = (String) element.getValue();
						if (value != null) {
							price.setName((value.length() > 45) ? value
									.substring(0, 45) : value);
						}

					} else if ("cost".equalsIgnoreCase(element.getName()
							.toString())) {
						String value = (String) element.getValue();
							price.setCost((doubleParse(value,0)));
					}
				}
			}
			if (price.getCost() != null && price.getCost() > 0) {
				listPrice.add(price);
			}

		}
		Log.info("Size of Fee  data is " + listPrice.size());

	}

	public void buildProductAttributes(Property property, Product xmlProduct,
			ArrayList<String> attributes, SqlSession sqlSession,
			Map<String, String> mapProductDesc, List<Date> listBookedDate,
			List<Price> listPrice, Map<String, String> mapImages)
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
				if(StringUtils.isNotEmpty(propertyAddr.country))
					countryCode = PartnerService.getCountryCode(propertyAddr.country.trim());
				
				if(StringUtils.isNotEmpty(propertyAddr.address1)) 
					builder.append(propertyAddr.address1).append(", ");
				if(StringUtils.isNotEmpty(propertyAddr.address2))  
					builder.append(propertyAddr.address2).append(", ");
				if(StringUtils.isNotEmpty(propertyAddr.city))  
					builder.append(propertyAddr.city).append(" ");
				if(StringUtils.isNotEmpty(propertyAddr.zip))
						builder.append(propertyAddr.zip).append(", ");
				if(StringUtils.isNotEmpty(propertyAddr.state)) {
					stateCode=PartnerService.getRegionCode(propertyAddr.state);
					builder.append(propertyAddr.state).append(" ");
									
				}
					
				if(StringUtils.isNotEmpty(countryCode))  
					builder.append(countryCode.toUpperCase());
				
				xmlProduct.setPhysicaladdress(builder.toString());
				xmlProduct.setLatitude(doubleParse(propertyAddr
						.getLatitude(),0));
				xmlProduct.setLongitude(doubleParse(propertyAddr
						.getLongitude(),0));
				
				//Display address is set to true (default), if ShowExactAddress not exist
				if(StringUtils.isNotEmpty(propertyAddr.getShowExactAddress())){
					//Display address is set to true(if ShowExactAddress=1).  false otherwise
					xmlProduct.setDisplayaddress("1".equalsIgnoreCase(propertyAddr.getShowExactAddress()));
				}
				
				LOG.info("Setting location from address: " + xmlProduct.getPhysicaladdress() + " country:|" + propertyAddr.country + "|");
				
				Location location = null;
				try {
					
					if(StringUtils.isEmpty(stateCode)){
						stateCode=propertyAddr.getState();
					}
					if(StringUtils.isEmpty(countryCode)){
						countryCode=propertyAddr.country;
					}
					
						LOG.info("Searching location with state code " + stateCode+" countryCode "+countryCode);
						location = PartnerService.getLocation(sqlSession, propertyAddr.getLocationName(), stateCode, countryCode, 
								doubleParse(propertyAddr.getLatitude(), 0),
								doubleParse(propertyAddr.getLongitude(), 0), 
								propertyAddr.getZip());
					
					
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
			if (obj instanceof PropertyBathrooms) {
				attributes.add(FlipKeyUtils.getATTRIBUTES().get("Bathroom"));
			}
			if (obj instanceof PropertyBedrooms) {
				attributes.add(FlipKeyUtils.getATTRIBUTES().get("Bedroom"));
				PropertyBedrooms propertyBedrooms = (PropertyBedrooms) obj;
				if (propertyBedrooms != null
						&& propertyBedrooms.getPropertyBedroom() != null) {
					for (PropertyBedroom bedroom : propertyBedrooms
							.getPropertyBedroom()) {
						if (bedroom != null && bedroom.getKingCount() != null) {
							attributes.add("BED3");

						}
						if (bedroom != null && bedroom.getQueenCount() != null) {
							attributes.add("BED5");
						}
						if (bedroom != null && bedroom.getTwinCount() != null) {
							attributes.add("BED8");
						}
						if (bedroom != null
								&& bedroom.getStandardCount() != null) {
							attributes.add("BED10");
						}
					}
				}
			}
			if (obj instanceof PropertyAmenities) {
				PropertyAmenities propertyAmenities = (PropertyAmenities) obj;
				for (PropertyAmenity amenity : propertyAmenities
						.getPropertyAmenity()) {
					for (JAXBElement element : amenity
							.getDescriptionOrOrderOrParentAmenity()) {
						if ("site_amenity".equalsIgnoreCase(element.getName()
								.toString())) {
							if (FlipKeyUtils.getATTRIBUTES().get(
									(String) element.getValue()) != null
									&& !"Other"
											.equalsIgnoreCase((String) element
													.getValue())) {
								// if the site amenity value contains a "/" then
								// atleast one word will match our attributes so
								// adding the attributes
								List<String> listTokenizedStr = FlipKeyUtils
										.getTokenizedString((String) element
												.getValue());
								for (String value : listTokenizedStr) {
									attributes.add(FlipKeyUtils.getATTRIBUTES()
											.get(value));
								}
							}

						}
						if ("description".equalsIgnoreCase(element.getName()
								.toString())) {
							// if other then check with tag description
							// if the site amenity value contains a "/" then
							// atleast one word will match our attributes so
							// adding the attributes
							List<String> listTokenizedStr = new ArrayList<String>();
							if (FlipKeyUtils.getATTRIBUTES().get(
									(String) element.getValue()) != null) {
								listTokenizedStr = FlipKeyUtils
										.getTokenizedString((String) element
												.getValue());
							}

							for (String value : listTokenizedStr) {
								attributes.add(FlipKeyUtils.getATTRIBUTES()
										.get(value));
							}

						}
					}
				}
			}
			if (obj instanceof PropertyDescriptions) {
				PropertyDescriptions propertyDescriptions = (PropertyDescriptions) obj;
				for (PropertyDescription description : propertyDescriptions
						.getPropertyDescription()) {
					mapProductDesc.put(description.getType(),
							description.getDescription());
				}
			}
			if (obj instanceof PropertyCalendar) {
				PropertyCalendar propertyCalendar = (PropertyCalendar) obj;
				buildBookedDates(propertyCalendar, listBookedDate);

			}
			if (obj instanceof PropertyRates) {
				PropertyRates propertyRates = (PropertyRates) obj;
				buildMonthlyPriceData(xmlProduct, propertyRates, listPrice);
				buildWeekEndPriceData(xmlProduct, propertyRates, listPrice);
				buildWeekNightPriceData(xmlProduct, propertyRates, listPrice);
				buildWeekPriceData(xmlProduct, propertyRates, listPrice);
			}

			if (obj instanceof PropertyFees) {
				PropertyFees propertyFees = (PropertyFees) obj;
				buildPropertyFeeData(xmlProduct, propertyFees, listPrice);
			}

			if (obj instanceof PropertyPhotos) {
				PropertyPhotos propertyPhotos = (PropertyPhotos) obj;
				buildImageData(propertyPhotos, mapImages);

			}

		}
		xmlProduct.setRank(0.0);
		LOG.info("Property attributes are built...");
	}

	public void buildProductDetails(Property property, Product xmlProduct,
			ArrayList<String> attributes, Integer minStay) {
		Log.info("Inside buildProductDetails for Product ");
		for (Object obj : property.getPropertyDetails()
				.getCurrencyOrNameOrBathroomCount()) {
			if (obj instanceof JAXBElement) {
				JAXBElement jaxbElement = (JAXBElement) obj;
				if ("name".equalsIgnoreCase(jaxbElement.getName().toString())) {
					xmlProduct.setName((String) jaxbElement.getValue());
					Log.info("Processing product data for "
							+ xmlProduct.getName());
				} else if ("bathroom_count".equalsIgnoreCase(jaxbElement
						.getName().toString())) {
						xmlProduct.setBathroom(integerParse((String) jaxbElement.getValue(),0));
						xmlProduct.setToilet(integerParse((String) jaxbElement.getValue(),0));
					
				} else if ("bedroom_count".equalsIgnoreCase(jaxbElement
						.getName().toString())) {
						xmlProduct.setRoom(integerParse((String) jaxbElement.getValue(),0));
				} else if ("currency".equalsIgnoreCase(jaxbElement.getName()
						.toString())) {
					xmlProduct.setCurrency((String) jaxbElement.getValue());
				} else if ("occupancy".equalsIgnoreCase(jaxbElement.getName()
						.toString())) {
						xmlProduct.setPerson(integerParse((String) jaxbElement.getValue(),0));
				} else if ("url".equalsIgnoreCase(jaxbElement.getName()
						.toString())) {
					xmlProduct.setWebaddress((String) jaxbElement.getValue());
				} else if ("property_type".equalsIgnoreCase(jaxbElement
						.getName().toString())) {
					if(FlipKeyUtils.getATTRIBUTES().containsKey(jaxbElement
							.getValue().toString()))
						attributes.add(FlipKeyUtils.getATTRIBUTES().get(jaxbElement
								.getValue().toString()));
					//xmlProduct.setType((String) jaxbElement.getValue());
					// Hard coding the attribute type for Accommodation
					xmlProduct.setType("Accommodation");
				} else if ("pet".equalsIgnoreCase(jaxbElement.getName()
						.toString())
						&& !"no".equalsIgnoreCase((String) jaxbElement
								.getValue())) {
					attributes.add(FlipKeyUtils.getATTRIBUTES().get("Pet"));
				} else if ("smoking".equalsIgnoreCase(jaxbElement.getName()
						.toString())
						&& !"no".equalsIgnoreCase((String) jaxbElement
								.getValue())) {
					attributes.add(FlipKeyUtils.getATTRIBUTES().get("Smoking"));
				} else if ("minimun_stay".equalsIgnoreCase(jaxbElement
						.getName().toString())) {
						minStay = integerParse((String) jaxbElement
								.getValue(),0);
				}

			} else if (obj instanceof ChildrenOverFive) {
				ChildrenOverFive childrenOverFive = (ChildrenOverFive) obj;
				/*LOG.info("Children Over Five value "
						+ childrenOverFive.toString());*/
						int children_count = integerParse(childrenOverFive
								.toString(),0);
						xmlProduct.setChild(children_count);
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

	private static int integerParse(String value,int defaultvalue){
		try {
			if (StringUtils.isNotBlank(value)&& StringUtils.isNotEmpty(value)&&StringUtils.isNumeric(value)) {
				return Integer.parseInt(value);
			}
		} catch (Exception e) {
		}
		return defaultvalue;
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
	
	private Product persistProduct(SqlSession sqlSession, Product xmlProduct) {
		LOG.info("Check whether Product data exists for data "
				+ xmlProduct.getName() + " ID " + xmlProduct.getAltid());
		// the name can be common with other existing products....also
		// pass in supplier id - flipkey's partner id
		// PartnerService.getProduct(sqlSession, getAltpartyid(),
		// altid);
		xmlProduct.setName((xmlProduct.getName().length() > 100) ? xmlProduct
				.getName().substring(0, 99) : xmlProduct.getName());
		
		//FIX ME - if alt id and altpartyid available, lookup product using that
		// the index key is on those columns
		//Fixed modified lookup as alt id and altpartyid
//		Product product = PartnerService.getProductWithoutInsert(sqlSession,
//				new NameId(xmlProduct.getAltpartyid(),
//						xmlProduct.getAltid()));
		Product product = PartnerService.getProductWithoutInsert(sqlSession, xmlProduct);
		


		if (product == null) {
			LOG.info("Product with " + xmlProduct.getName()
					+ " does not exists so creating the product entry");
			// if a particular product doesnt not exists create the
			// product
			String altPartyId = getAltpartyid();

			xmlProduct.setAltpartyid(altPartyId);
			xmlProduct.setState(Product.CREATED);
			xmlProduct.setType(Product.Type.Accommodation.name());
			xmlProduct.setUnit(Unit.DAY);
			xmlProduct.setOrganizationid(altPartyId);
			xmlProduct.setAltSupplierId(altPartyId);
			xmlProduct.setSupplierid(altPartyId);
			xmlProduct.setOwnerid(altPartyId);
			xmlProduct.setQuantity(1);
			xmlProduct.setInquireState(Product.USE_API);
//			sqlSession.getMapper(ProductMapper.class).create(xmlProduct);
			
			// after create retrive the product once again so that we
			// can insert its attributes in relation table.
			product = PartnerService.createProduct(sqlSession, xmlProduct);
			product.setRefresh(0);
			return product;
		} else {
			xmlProduct.setId(product.getId());
			xmlProduct.setRefresh(new Long(FlipKeyUtils.daysBetween(product.getVersion(),new Date())).intValue());
			PartnerService.updateProduct(sqlSession, xmlProduct);
			return xmlProduct;
		}
	}

	private void persistAttributes(SqlSession sqlSession, Product product,
			ArrayList<String> attributes) {

		// update attribute of product
		// Hard Coding property for vacation rental
		attributes.add("PCT53");
		// remove null value if exists
		attributes.remove(null);
		ListIterator<String> listIterator = attributes.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next() == null) {
				listIterator.remove();
			}
		}

		LOG.info("Updating the attributes of Product " + product.getName());

		RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE,
				product.getId(), attributes);

	}

	private void persistDescription(SqlSession sqlSession, Product product,
			Map<String, String> mapProductDesc) {
		// update description of product
		LOG.error("Updating the description of Product " + product.getName());
		
		Map<String, String> texts = new HashMap<String, String>();
		for (Entry<String, String> entryProductDesc : mapProductDesc.entrySet()) {
			// the key is the type of description
			// E.g:property,rates,location..etc
			// the value is the description of each type.We are storing type in
			// name column and value in notes column of the text.

			// This service will create a new entry and will not update if entry
			// already exists.
			if(entryProductDesc.getKey().equalsIgnoreCase("property")) {
//				TextService.createDescription(sqlSession,
//						new Text(product.getId() + "_" + entryProductDesc.getKey(),
//								entryProductDesc.getKey(), Text.Type.Text,
//								new Date(), entryProductDesc.getValue(), "EN"));
				texts.put("EN", entryProductDesc.getValue());
				
			}
		}
		TextService.createProductDescription(sqlSession, product, texts);
	}

	private void persistImageData(SqlSession sqlSession, Product product,
			Map<String, String> mapImages) {
		// update image data of product
		LOG.info("Updating the Image Data of Product " + product.getName());

		for (Entry<String, String> entryProductDesc : mapImages.entrySet()) {
			try{
			if (entryProductDesc.getKey().contains("small")) {
				ImageService.persistImage(entryProductDesc.getKey(),
						Integer.parseInt(product.getId()), "EN", "",
						Image.Type.Linked, entryProductDesc.getValue(), false,
						true, sqlSession);
			} else {
				ImageService.persistImage(entryProductDesc.getKey(),
						Integer.parseInt(product.getId()), "EN", "",
						Image.Type.Linked, entryProductDesc.getValue(), true,
						false, sqlSession);
			}
			}catch(Exception e){
				LOG.error("Error while persisting image since image url is long "+ entryProductDesc.getValue());
			}
		}
	}

	private void persistMinStay(SqlSession sqlSession, Product product,
			Integer minStay) {
		LOG.info("Updating the minstay of Product " + product.getName());
		// update the minStay of product
		// SN:I am updating this property since they might change this
		// frequently or on the daily basis
		PartnerService.createMinStay(sqlSession, minStay, product, new Date(),
				new Date(), new Date());
	}

	private void persistBookedDate(SqlSession sqlSession, Product product,
			List<Date> listBookedDate,List<Price> priceList) {
		List<Date> reservationList = sqlSession.getMapper(
				ReservationMapper.class).reservedDateForProperty(
				product.getId());

		for (Date bkDate : listBookedDate) {
			if (!reservationList.contains(bkDate)) {
				//LOG.info("Blocking dates date - " + bkDate);
				PartnerService.createSchedule(sqlSession, product, bkDate, bkDate, new Date());
//				Reservation reservation = new Reservation();
//				reservation.setProduct(product);
//				reservation.setProductid(product.getId());
//				reservation.setAltpartyid(product.getAltpartyid());
//				reservation.setAltid(product.getAltid());
//				reservation.setFromdate(bkDate);
//				reservation.setTodate(bkDate);
//				reservation.setPrice(getPriceValue(priceList,bkDate));
//				reservation.setState(State.Reserved.name());
//				sqlSession.getMapper(ReservationMapper.class).create(
//						reservation);
			}
		}

		for (Date cdDate : reservationList) {
			if (!listBookedDate.contains(cdDate)) {
				sqlSession.getMapper(ReservationMapper.class).deleteDate(
						product.getId(), cdDate);
			}
		}

		// LOG.info("Updating the booked date of Product " + product.getName());
		// // delete the existing booking date and update the new booking date
		// sqlSession.getMapper(ReservationMapper.class).delete(product.getId());
		// // update the booked date of product
		// for (Date dt : listBookedDate) {
		// Reservation reservation = new Reservation();
		// reservation.setProduct(product);
		// reservation.setProductid(product.getId());
		// reservation.setAltpartyid(product.getAltpartyid());
		// reservation.setAltid(product.getAltid());
		// reservation.setFromdate(dt);
		// reservation.setTodate(dt);
		//
		// sqlSession.getMapper(ReservationMapper.class).create(reservation);
		// }

	}

	private double getPriceValue(List<Price> priceList,Date dt){
		if(priceList==null||priceList.isEmpty()) return 0.0;
		for(Price price : priceList){
			if(price.getDate()!=null && dt.compareTo(price.getDate()) >= 0 && dt.compareTo(price.getTodate()) <= 0){
				return price.getValue();
			}
		}
		return 0;
	}
	

	/**
	 * @throws Exception
	 * @throws Throwable
	 */
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

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		int statusCode = 0;
		try {
			
			Inquiry inquiry = new Inquiry();
			Header header = new Header();
			header.setSource("FK");
			inquiry.setHeader(header);
			
			Data data = new Data();
			if(reservation!=null){
			data.setRequestDate(FlipKeyUtils.getXMLCurrentDate());
			 data.setName(reservation.getCustomername());
			 data.setTotalGuests(reservation
			 .getAdult()+reservation.getChild());
			 data.setCheckIn(reservation.getCheckin());
			 data.setCheckOut(reservation.getCheckout());
			 data.setComment(reservation.getMessage());
			 data.setPropertyId(Integer
			 .parseInt(reservation.getProduct().getAltid()));
			data.setEmail(reservation.getEmailaddress()); 
			
			 data.setPhoneNumber(reservation.getPhoneNumber()); 
			//TODO SN:To check with chirayu on ipaddress. 
			 data.setUserIp("");
			  data.setPointOfSale("mybookingpal.com");
			  data.setUtmMedium("csynd"); 
			  data.setUtmSource("bookingpal");
			  data.setUtmCampaign("Host&Post");
			  
			}

			// Setting the test value for testing purpose
			  /*data.setRequestDate(FlipKeyUtils.getXMLCurrentDate());
			data.setName("Test Sen");
			data.setTotalGuests(4);
			data.setCheckIn("2014-07-17");
			data.setCheckOut("2014-07-20");
			data.setComment("hi could u please confirm there is availability and location wise are we close to Edinburgh centre as we have 2 young children. look forward to hearing from you. Thanks,John ");
			data.setPropertyId(417793);
			data.setEmail("sen@mybookingpal1.com");
			data.setPhoneNumber("07747484202");
			data.setUserIp("90.221.169.227");
			data.setPointOfSale("mybookingpal.com");
			data.setUtmMedium("csynd");
			data.setUtmSource("bookingpal");
			data.setUtmCampaign("Host&Post");*/

			
			inquiry.setData(data);
			LOG.info("Before callFlipKeyInquiryAPI");
			statusCode = FlipKeyUtils.callFlipKeyInquiryAPI(inquiry);
			LOG.info("After callFlipKeyInquiryAPI "+statusCode);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
		

	}

}
