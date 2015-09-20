/**
 * @author	Zoran Kocevski
 * @version	1.00
 */

package net.cbtltd.rest.webchalet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mybookingpal.server.ImageService;

import net.cbtltd.rest.webchalet.A_Handler;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;


public class A_Handler extends PartnerHandler implements IsPartner {
	
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final Gson GSON = new Gson();
	private static final String SERVICE_URL = "https://secure.webchalet.com/wcadmin/api/bookingpal/";
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar cal = Calendar.getInstance();

	public A_Handler(Partner partner) {
		super(partner);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the connection to the WebChalet server and executes the specified request.
	 *
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the JSON string returned by the message.
	 * @throws Throwable the exception thrown by the operation.
	 */
	private static final String getConnection(URL url, String rq) throws Throwable {
		String jsonString = "";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			if (rq != null) {
				connection.setRequestProperty("Accept", "application/json");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {jsonString += line;}
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return jsonString;
	}
	


	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		return false;
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
		Date today = new Date();
		String message = "readPrices() WebChalet STARTED.";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			String jsonString = getConnection(new URL(SERVICE_URL), "null");
			jsonString = "{\"properties\":"+jsonString+"}";
			ResponseWC response = GSON.fromJson(jsonString, ResponseWC.class);
			for (PropertyWC property : response.properties)
			{ 
				String altid = property.getPropertyID();
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
				if (product == null) {
					continue;
				}
				
				int regularminimumstay = property.getPrice().getMinimum();
				Double regularratevalue = property.getPrice().getNightly();
				String currency = property.getPrice().getCurrency();
				List<RateWC> rates = property.getRate();
				Date mindate = property.getMinimumDate();
				Date maxdate = property.getMaximumDate();
				
				if(mindate != null){
					if(today.compareTo(mindate)<0){
						Price price = new Price();
						price.setAltid(altid);
						price.setPartyid(getAltpartyid());
						price.setName(net.cbtltd.shared.Price.RACK_RATE);
						price.setType(NameId.Type.Reservation.name());
						price.setEntitytype(NameId.Type.Product.name());
						price.setEntityid(product.getId());
						price.setCurrency(currency);
					
						price.setQuantity(1.0);
						price.setUnit("DAY"); 
						price.setDate(today);
						cal.setTime(mindate);
						cal.add(Calendar.DAY_OF_YEAR, -1);
						price.setTodate(cal.getTime());
						price.setMinStay(regularminimumstay);
						price.setValue(Double.valueOf(regularratevalue));
						price.setMinimum(Double.valueOf(regularratevalue));
						price.setAvailable(1);

						
						Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
						if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
						else {price = exists;}

						
						price.setAltpartyid(getAltpartyid());
						price.setFactor(1.0);
						price.setOrganizationid(getAltpartyid());
						price.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
						price.setState(Price.CREATED);						
						price.setVersion(today);
						sqlSession.getMapper(PriceMapper.class).update(price);
						sqlSession.getMapper(PriceMapper.class).cancelversion(price);
					}
				}
				Date startDate = mindate;
				//Date endDate = maxdate;
				
				for(RateWC rate : rates){
					Date ratestartdate = rate.getStartDate();
					Date rateenddate =  rate.getEndDate();
					int rateminstay = rate.getMinimumStay();
					Double ratevalue = rate.getNightly();
					boolean insertedrate = false;
					
					Price price = new Price();
					price.setAltid(altid);
					price.setPartyid(getAltpartyid());
					price.setName(net.cbtltd.shared.Price.RACK_RATE);
					price.setType(NameId.Type.Reservation.name());
					price.setEntitytype(NameId.Type.Product.name());
					price.setEntityid(product.getId());
					price.setCurrency(currency);
					price.setQuantity(1.0);
					price.setUnit("DAY");
					price.setAvailable(1);
					
					if (startDate.compareTo(ratestartdate)==0){
						price.setDate(ratestartdate);
						price.setTodate(rateenddate);
						price.setMinStay(rateminstay);
						price.setValue(ratevalue);
						price.setMinimum(Double.valueOf(ratevalue));
						insertedrate = true;
					}else if(startDate.compareTo(ratestartdate)<0){
						price.setDate(startDate);
						cal.setTime(ratestartdate);
						cal.add(Calendar.DAY_OF_YEAR, -1);
						price.setTodate(cal.getTime());
						price.setMinStay(regularminimumstay);
						price.setValue(regularratevalue);
						price.setMinimum(Double.valueOf(regularratevalue));
					}
					
					Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
					if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
					else {price = exists;}

					
					price.setAltpartyid(getAltpartyid());
					price.setFactor(1.0);
					price.setOrganizationid(getAltpartyid());
					price.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
					price.setState(Price.CREATED);						
					price.setVersion(today);
					sqlSession.getMapper(PriceMapper.class).update(price);
					sqlSession.getMapper(PriceMapper.class).cancelversion(price);
					
					if(!insertedrate){
						price.setDate(ratestartdate);
						price.setTodate(rateenddate);
						price.setMinStay(rateminstay);
						price.setValue(ratevalue);
						price.setMinimum(Double.valueOf(ratevalue));
						insertedrate = true;
						
						exists = sqlSession.getMapper(PriceMapper.class).exists(price);
						if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
						else {price = exists;}

						
						price.setAltpartyid(getAltpartyid());
						price.setFactor(1.0);
						price.setOrganizationid(getAltpartyid());
						price.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
						price.setState(Price.CREATED);						
						price.setVersion(today);
						sqlSession.getMapper(PriceMapper.class).update(price);
						sqlSession.getMapper(PriceMapper.class).cancelversion(price);
					}
					
					cal.setTime(rateenddate);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					startDate=cal.getTime();
									
				}
					
			}
			sqlSession.commit();				
			LOG.debug("readPrices() WebChalet DONE.");
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, today);
	}

	@Override
	public void readProducts() {
		String message = "WebChalet: readProducts started.";
		LOG.debug(message);
		Date version = new Date();
		
		try{
			//update/create products. 
			createProduct();
			LOG.debug("WebChalet: createProduct done.");
			//update/create images. 
			createImages();
			LOG.debug("WebChalet: createImages done.");
			LOG.debug("WebChalet: readProducts done."  );
			MonitorService.monitor(message, version);
		}
		catch (Throwable x) {
			LOG.error(x.getStackTrace());
		}
		
	}
	
	public void createProduct() {
		String altid = null; 
		Product product;
		Date timestamp = new Date();
		String message = "createProduct WebChalet";		
		
		final SqlSession sqlSession = RazorServer.openSession();

		try {			
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			String jsonString = getConnection(new URL(SERVICE_URL), "null");
			jsonString = "{\"properties\":"+jsonString+"}";
			ResponseWC response = GSON.fromJson(jsonString, ResponseWC.class);
			//System.out.println(response);
			for (PropertyWC property : response.properties)
			{ 
				//System.out.println(property.getRate());
				
				altid = property.getPropertyID();
				
				
				product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {continue;}
				
				//String altsupplierid = property.getPropertyManager(); 
				String altsupplierid = getAltpartyid(); 
				String name = property.getName();
				int bathroom = property.getBathrooms();
				int rooms = property.getBedrooms();
				int maxpersons = property.getSleeps();
				String webaddress = property.getURL();
				String propertytype = "House"; //TODO HARD CODED! THEY CAN MODIFY THIS DATA IN THE WEBSERVICE
				
				String address = property.getLocation().getAddressDetails();
				String place = property.getLocation().getCity();
				String zipCode = property.getLocation().getZip();
				String region = property.getLocation().getState();
				String country = Country.US; //TODO HARD CODED, but it should change with String country = property.getLocation.getCountry(); (add getCountry to class LocationWC)
				Double latitude = property.getLocation().getLatitude();
				Double longitude = property.getLocation().getLongitude();
				Location location = PartnerService.getLocation(sqlSession, place, region, country, latitude, longitude);
				
				String currency = property.getPrice().getCurrency();
				Double deposit = 0.0; //TODO CHECK IT!
				Double cleaningfee = 0.0;// TODO CHECK IT!
				
				ArrayList<String> attributes = new ArrayList<String>();
				addType(attributes, propertytype);
				List<String> amenities = property.getAmenities();
				for (String amenity : amenities) {
					if(amenity.toString().compareTo("Bumper Pool / Poker Table")!=0)
						addAttribute(attributes, amenity.toString());
				}
				
				String language = Language.EN;
				String type;
				String text;
				text = property.getDesription();
				type = "Description";
				if (text != null && !text.isEmpty()) {
					product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), NameId.trim(text, 20000), language));
					TextService.update(sqlSession, product.getTexts());
				}
				
				createOrUpdateProductModel(location, place, region, zipCode, country, maxpersons, 
						bathroom, rooms, propertytype, currency, deposit,  cleaningfee, webaddress,
						address, name,altid,altsupplierid, product);
				sqlSession.getMapper(ProductMapper.class).update(product);
		
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
			}
			sqlSession.commit();
		}catch (Throwable x) {
			sqlSession.rollback();
			//x.getMessage();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} 		
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}
	
	private Product createOrUpdateProductModel(Location location, String place, String region, String zipCode, 
			String country, int maxpersons, int bathroom, int rooms, String propertytype,
			String currency, Double deposit,  Double cleaningfee,
			String webaddress, String address, String name, String altid,
			String altsupplierid, Product product) {
		
		if(product == null) {
		product = new Product();
		}
		//product.setState(Product.CREATED);
		if(location!=null){
			product.setLocationid(location.getId());
			product.setLatitude(location.getLatitude());
			product.setLongitude(location.getLongitude());
		}else{
			product.setState(Product.SUSPENDED);
		}
		
		product.setAltitude(0.0);
		product.setPhysicaladdress(address);
		
		product.setAltSupplierId(altsupplierid);
		product.setAltpartyid(getAltpartyid()); 
		product.setSupplierid(getAltpartyid());  
		product.setAltid(altid);
		
		product.setPerson(maxpersons);
		product.setChild(0); //TODO IF WE DON'T HAVE THIS VALUE WHAT TO DO, SET DEFAULT VALUE?
		product.setInfant(0);
		product.setQuantity(1); //TODO CHECK!
		
		product.setRating(5);
		product.setCommission(getCommission());
		product.setDiscount(getDiscount());
		product.setRank(getRank());
		
		product.setType(Product.Type.Accommodation.name());
		product.setCurrency(currency);
		product.setSecuritydeposit(deposit);
		product.setCleaningfee(cleaningfee);
		product.setUnit(Unit.DAY);
		product.setWebaddress(webaddress);
		product.setServicedays("0000000");
		product.setCode("");
		product.setBathroom(bathroom);
		product.setRoom(rooms);
		product.setDynamicpricingenabled(false);
		
		product.setName(name.toString());
		
		return product;
	}
	
	
	public void createImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		long startTime = System.currentTimeMillis();
		String altid = null; 
		String message = "Create Images started WebChalet";
		LOG.debug(message);
		Date version = new Date();
		
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			String jsonString = getConnection(new URL(SERVICE_URL), "null");
			jsonString = "{\"properties\":"+jsonString+"}";
			ResponseWC response = GSON.fromJson(jsonString, ResponseWC.class);
			ArrayList<String> names = new ArrayList<String>();
			int i = 0;
			//System.out.println(response);
			for (PropertyWC property : response.properties)
			{ 
				names.add(property.getPropertyID());
				i++;
			}
			
			i=0;
			
			for (PropertyWC property : response.properties) {
				
				altid = property.getPropertyID();
				i++;
				Product product = PartnerService.getProduct(sqlSession,
						getAltpartyid(), altid,false);
				if (product == null) {
					continue;
				}
				
				List<String> photos = property.getPhotos();
				ArrayList<NameId> images = new ArrayList<NameId>();
				for (String photo : photos) {
					images.add(new NameId(photo.toString().substring(photo.toString().lastIndexOf("/")+1),  photo.toString()));
				}
				LOG.debug("Total images uploading for the property " + product.getId() + ": " + images.size());
				ImageService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images,"C:/htdocs");
				
				sqlSession.commit();
				MonitorService.monitor(message, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} catch (Throwable x) {
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for createImage execution " + getApikey() + " : " + (endTime - startTime)/1000 + " seconds.");
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
	
	private static HashMap<String,String> ATTRIBUTES = null;
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {

		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			
			ATTRIBUTES.put("TV", "RMA90");
			ATTRIBUTES.put("Cable TV", "RMA210");
			ATTRIBUTES.put("Internet", "HAC223");
			ATTRIBUTES.put("Wireless Internet", "RMA123");
			ATTRIBUTES.put("Heating", "HAC242");
			ATTRIBUTES.put("Parking Included", "HAC63");
			ATTRIBUTES.put("Washer/Dryer", "RMA32");
			ATTRIBUTES.put("Fireplace", "RMA41");
			ATTRIBUTES.put("No Smoking", "HAC198");
			ATTRIBUTES.put("Kid Friendly", "SUI1");
			ATTRIBUTES.put("Pets Allowed", "PET7");
			ATTRIBUTES.put("BBQ", "FAC3");
			ATTRIBUTES.put("Air Conditioning", "FAC1");
			
		}
		//TODO remove this
		if(ATTRIBUTES.get(attribute) == null){attributes.add(attribute);}
		
		if (ATTRIBUTES.get(attribute) != null) {attributes.add(ATTRIBUTES.get(attribute));}
	}
	
	private static HashMap<String, String> TYPES = null;
	/**
	 * Adds the type.
	 *
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("House","PCT16"); //Guesthouse
			TYPES.put("Cabin","PCT5"); //Cabin or Bungalow
			TYPES.put("Chalet","PCT7"); //Chalet
			TYPES.put("Lodge","PCT22"); //Lodge
		}
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
	}

	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	
}