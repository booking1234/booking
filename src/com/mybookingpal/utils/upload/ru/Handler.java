package com.mybookingpal.utils.upload.ru;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.ru.Currencies;
import net.cbtltd.rest.ru.Currency;
import net.cbtltd.rest.ru.LastMinute;
import net.cbtltd.rest.ru.LongStay;
import net.cbtltd.rest.ru.MinStay;
import net.cbtltd.rest.ru.Prices;
import net.cbtltd.rest.ru.PropertyMinStay;
import net.cbtltd.rest.ru.Season;
import net.cbtltd.rest.ru.product.*;
import net.cbtltd.rest.ru.request.Authentication;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ChannelProductMapMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Yield;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.mybookingpal.server.ImageService;
import com.mybookingpal.server.Uploadable;
import com.mybookingpal.utils.upload.ru.request.*;

public class Handler extends PartnerHandler implements Uploadable {
	
	private static final Logger LOG = Logger.getLogger(Handler.class.getName());
	private static final DateFormat TF = new SimpleDateFormat("hh:mm");
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat FF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final String HANDLER_URL = "http://rm.rentalsunited.com/api/Handler.ashx";
	private static final String USERNAME = "Cockabondy@hotmail.com";
	private static final String PASSWORD = "Asd123456";
	private static final Integer CHANNEL = 429; //tet channel
	private static final Integer OWNER = 421616; //Test owner in RU system
	private static final Integer PROPERTY = 270; //Test property

	/**
	 * Instantiates a new partner handler
	 *
	 * @param sqlSession the current SQL session
	 * @param partner the partner.
	 */
	public Handler (Partner partner) {super(partner);}
	
	/**
	 * Sends XML POST request
	 * 
	 * @param rq - string request
	 */
	private static final String getConnection(String rq) throws Throwable {
		String xmlString = "";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(HANDLER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/xml");
			
			if (rq != null) {
				connection.setRequestProperty("Accept", "application/xml");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}
			
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {xmlString += line;}
			
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return xmlString;
	}
	
	/**
	 * Upload properties into Rentals United system
	 * 
	 */
	@Override
	public void uploadProperties() {
		String message = "Uploading products to Rentals United";
		LOG.debug(message);
		Date date = new Date();

		final SqlSession sqlSession = RazorServer.openSession();
		try{
//			List<Integer> managers = sqlSession.getMapper(ChannelPartnerMapper.class).ManagerByChannelList(CHANNEL);
			//get list of products to upload using manager_to_channel table for each channel
//			for (int manager : managers) {
				//check if owner already pushed. if not - create
				
//				ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).getProductsForChannel;
				//select properties which are not listed in channel_product_map and upload them into
//				for (Product product : products) {
					//retrieve each product and create property object according to RU specifications
//					Property property = getProperty(sqlSession, product);
					//upload each property
//					putProperty(property);
//				}
				//create new list of properties which were already uploaded.
//				ArrayList<Product> products = ;
				//check each property version date with date of last upload for this property. If version > date of last upload then update static property info.
//				for (Product product : products) {
//				}
//			}
			Property property;
			ArrayList<Integer> lostProductIDs = new ArrayList<Integer>();
			ArrayList<Integer> importedProducts = sqlSession.getMapper(ChannelProductMapMapper.class).readProductIDs(CHANNEL);
			// TODO: maybe change this, using only product IDs to reduce memory usage
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).getProductsForChannel(CHANNEL.toString());
			for (Product product : products){
				property = null;
				Integer productid = Integer.valueOf(product.getId());
				//check if product already imported
				if (importedProducts.contains(productid)){
					//Check versions with product import
					String s = RelationService.find(sqlSession, Downloaded.PRODUCT_IMPORT_DATE, productid.toString(), null); 
					if (s == null || (product.getVersion()).after(FF.parse(s))) {
						// using ID from RU system (stored in ChannelProductMap)
						ChannelProductMap tempChannelProductMap = new ChannelProductMap();
						tempChannelProductMap.setChannelId(CHANNEL);
						tempChannelProductMap.setProductId(product.getId());
						Integer ruID = sqlSession.getMapper(ChannelProductMapMapper.class).readChannelProductID(tempChannelProductMap);
						property = getProperty(sqlSession, product, ruID);
					}
				} else {
					property = getProperty(sqlSession, product, null);
				}
				//update property or log problem product
				if (property != null){
					putProperty(sqlSession, property, productid, lostProductIDs, date);
				} else {
					lostProductIDs.add(productid);
					continue;
				}
			}
			if (lostProductIDs.size() > 1) {LOG.error("Couldn't update following products: " + lostProductIDs);}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}

	}
	
	/**
	 * Set all possible fields using info from product entity in our database according to RU specifications along with static product info
	 * 
	 * @param sqlSession
	 * @param string
	 * @return property object
	 */
	private Property getProperty(SqlSession sqlSession, Product product, Integer ruID){
		Property property = new Property();
		
		try{
//			Product product = PartnerService.getProduct(sqlSession, altpartyid, propertyid);
			if (!Product.CREATED.equals(product.getState())) {
				throw new ServiceException(Error.product_inactive, "product with [" + product.getId() + "] id");
			}
			Integer propertyManagerId = Integer.valueOf(product.getSupplierid());
			if(propertyManagerId == null) {
				throw new ServiceException(Error.database_cannot_find, "property manager ID for product id [" + product.getId() + "]");
			}
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
			if(propertyManagerInfo == null) {
				throw new ServiceException(Error.database_cannot_find, "property manager info by PM ID [" + propertyManagerId + "]");
			}
			Party propertyManagerParty = sqlSession.getMapper(PartyMapper.class).read(propertyManagerInfo.getPropertyManagerId().toString());
			
			if (ruID != null){
				property.setID(new ID(ruID));
			}
			property.setPUID(new PUID());
			property.setName(product.getName());
			property.setOwnerID(OWNER);
			//set location with CodeRentalsUnited id.
			Integer rentalsunitedLocationID = 0;
			try {
				rentalsunitedLocationID = getRuLocationID(sqlSession, Integer.valueOf(product.getLocationid()));
			} catch (Throwable e) {
				LOG.error(e.getMessage());
				return null;
			}
			property.setDetailedLocationID(rentalsunitedLocationID);
			//property.setDetailedLocationID(321);//test location
			//TODO: maybe set to active when ruID != null
//			property.setIsActive(false);
//			property.setIsArchived(false);
			Double cleaningPrice = product.getCleaningfee();
			//TODO: Change this, not finished
			if (cleaningPrice == null || cleaningPrice == 0) {
				//try to find cleaning price
				net.cbtltd.shared.Price mandatory = new net.cbtltd.shared.Price();
				//mandatory.setEntitytype(NameId.Type.Mandatory.name());
				mandatory.setEntityid(product.getId());
				mandatory.setPartyid(getAltpartyid());
				mandatory.setName("cleaning");
				mandatory.setCurrency(product.getCurrency());
				//mandatory.setQuantity(0.0);
				mandatory.setAvailable(1);
				mandatory.setState(net.cbtltd.shared.Price.CREATED);
				//set new value if found price, if not set to 0
				Price exists = sqlSession.getMapper(PriceMapper.class).exists(mandatory);
				if (exists == null) {cleaningPrice = 0.0;}
				else {
					mandatory = exists;
					//TODO: Change this to actual value when done
					cleaningPrice = mandatory.getValue();

					//convert currency
					Integer rentalsunitedCode = getRuLocationID(sqlSession, Integer.valueOf(product.getLocationid()));
					String ruCurrency = getCurrency(rentalsunitedCode);
					String priceCurrency = mandatory.getCurrency();
					if (!priceCurrency.equals(ruCurrency)) {
						//convert
						cleaningPrice = PaymentService.convertCurrency(sqlSession, priceCurrency, ruCurrency, cleaningPrice);
					}
				}
			}
			property.setCleaningPrice(cleaningPrice);
			//TODO: check this value. Should be only numbers.
			String[] sqM = product.getSpace().split(" ");
			try {
				property.setSpace(Integer.valueOf(sqM[0]));
			} catch (NullPointerException npe) {
				property.setSpace(0);
			}
			property.setFloor(product.getFloor());
			property.setStandardGuests(product.getPerson());
			property.setCanSleepMax(product.getPerson());
			/*Available property types are:
			 * 1 - Studio,
			 * 2 - One Bedroom,
			 * 3 - Two Bedroom,
			 * 4 - Three Bedroom,
			 * 11 - Five Bedroom,
			 * 12 - Four Bedroom,
			 * 26 - Six Bedroom,
			 * 27 - Seven Bedroom,
			 * 28 - Eight Bedroom,
			*/
			int propType = 2;
			switch (product.getBathroom()) {
			case 2: propType = 3;
				break;
			case 3: propType = 4;
				break;
			case 4: propType = 12;
				break;
			case 5: propType = 11;
				break;
			case 6: propType = 26;
				break;
			case 7: propType = 27;
				break;
			case 8: propType = 28;
				break;
			}
			property.setPropertyTypeID(propType);
			String address = Arrays.toString(product.getAddress());
			property.setStreet(address);
			//TODO: mandatory field, check value
			Location loc = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
			if (loc != null && !loc.getZipCode().isEmpty()) {
				property.setZipCode(loc.getZipCode());
			} else property.setZipCode("0");
			property.setCoordinates(new Coordinates(product.getLatitude().toString(), product.getLongitude().toString()));
			//?Distances - optional
			//?CompositionRooms - optional (use room types?)
			//?CompositionRoomsAmenities - optional (e.g. bathroom, toilet, etc)
			//Amenities
			Amenities amenities = new Amenities();
			Relation action = new Relation();
			action.setHeadid(product.getId());
			action.setLink(Relation.PRODUCT_ATTRIBUTE);
			List<Relation> attributes = sqlSession.getMapper(RelationMapper.class).list(action);
			for (Relation relation : attributes) {
				String attr = relation.getLineid();
				Amenity amenity = new Amenity();
				amenity.setCount(1);
				Integer value = addAmenity(attr);
				if (value != null) {
					amenity.setValue(value);
				} else {
					LOG.error("No match was found for attribute \"" + attr + "\"");
				}
				amenities.getAmenity().add(amenity);
			}
			property.setAmenities(amenities);
			//set Images
			Images images = new Images();
			//check this list, it may consist wrong urls
			List <String> mbpImageUrls = ImageService.getProductRegularImageURLs(sqlSession, product.getId());
			/*Image types:
			 * 1 - Main image
			 * 2 - Property plan
			 * 3 - Interior
			 * 4 - Exterior
			 */
			int i = 1;
			for (String url : mbpImageUrls){
				Image image = new Image();
				image.setUrl(url);
				if (i == 1) {image.setImageTypeID(1);}
				else {image.setImageTypeID(3);}
				images.getImage().add(image);
				i++;
			}
			property.setImages(images);
			//TODO: mandatory field, check value
			ArrivalInstructions instructions = new ArrivalInstructions();
			instructions.setLandlord(propertyManagerParty.getFirstName() + " " + propertyManagerParty.getFamilyName());
			instructions.setEmail(propertyManagerParty.getEmailaddress());
			instructions.setPhone(propertyManagerParty.getDayphone());
			instructions.setDaysBeforeArrival(0);
			HowToArrive howToArrive = new HowToArrive();
			howToArrive.getText().add(new net.cbtltd.rest.ru.product.Text("", 1));
			instructions.setHowToArrive(howToArrive);
			PickupService pickupService = new PickupService();
			pickupService.getText().add(new net.cbtltd.rest.ru.product.Text("", 1));
			instructions.setPickupService(pickupService);
			property.setArrivalInstructions(instructions);
			CheckInOut checkInOut = new CheckInOut();
			checkInOut.setCheckInFrom(TF.format(propertyManagerInfo.getCheckInTime()));
			checkInOut.setCheckInTo(TF.format(propertyManagerInfo.getCheckInTime()));
			checkInOut.setCheckOutUntil(TF.format(propertyManagerInfo.getCheckOutTime()));
			checkInOut.setPlace("apartment");
			property.setCheckInOut(checkInOut);
			/*Available payment methods are:
			 * 1 - Cash
			 * 2 - Bank transfer
			 * 3 - Credit card
			 * 4 - PayPal
			 * 5 - Other method
			 */
			PaymentMethods paymentMethods = new PaymentMethods();
			PaymentMethod payment = new PaymentMethod();
			payment.setPaymentMethodID(3);
			payment.setValue("Creadit Card");
			paymentMethods.getPaymentMethod().add(payment);
			property.setPaymentMethods(paymentMethods);
			Deposit deposit = new Deposit();
			Double securityDeposit = product.getSecuritydeposit();
			/*Available deposit values are:
			 * 1 - No deposit
			 * 2 - Percentage of total price(without cleaning)
			 * 3 - Percentage of total price
			 * 4 - Fixed amount per day
			 * 5 - Flat amount per stay
			 */
			//TODO: mandatory field, check value
			if (securityDeposit == null || securityDeposit == 0){
				deposit.setDepositTypeID(1);
				deposit.setDepositValue(0.0);
				property.setDeposit(deposit);
			} else {
				deposit.setDepositTypeID(5);
				deposit.setDepositValue(securityDeposit);
				property.setSecurityDeposit(deposit);
				property.setDeposit(deposit);
			}
			//TODO: mandatory field, check value
			CancellationPolicies cancellationPolicies = new CancellationPolicies();
			//value in percentage
			List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(propertyManagerId);
			if(rules != null && !rules.isEmpty()) {
				//need to find out how to write ValidFrom value
				for(PropertyManagerCancellationRule rule : rules) {
					CancellationPolicy cancellationPolicy = new CancellationPolicy();
					cancellationPolicy.setValue(rule.getCancellationRefund());
					//TODO: check this value
					cancellationPolicy.setValidFrom(0);
					cancellationPolicy.setValidTo(rule.getCancellationDate());
					cancellationPolicies.getCancellationPolicy().add(cancellationPolicy);
				}
			}
			else {
				CancellationPolicy cancellationPolicy = new CancellationPolicy();
				cancellationPolicy.setValue(0.0);
				cancellationPolicy.setValidFrom(0);
				cancellationPolicy.setValidTo(0);
			}
			property.setCancellationPolicies(cancellationPolicies);
			Descriptions descriptions = new Descriptions();
			Description description = new Description();
			Text textAction = new Text(product.getPublicId(), Language.Code.en.name());
			Text text = sqlSession.getMapper(TextMapper.class).readbyexample(textAction);
			description.setLanguageID(1);
			//description.setImage(image);
			description.setText(text.getNotes());
			descriptions.getDescription().add(description);
			property.setDescriptions(descriptions);
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		
		return property;
	}
	
	/**
	 * Upload single property into RU system, and creates ChannelProductMap entry if successful (with internal property ID).
	 * 
	 * @param sqlSession
	 * @param property - property object which we send to RU
	 * @param productid - id of actual product from our system
	 * @param version - current date
	 * @return Status message
	 */
	private String putProperty(SqlSession sqlSession, Property property, Integer productid, List<Integer> lostProductIDs, Date version){
		String message = "Uploading property " + productid + " into RU system";
		LOG.debug(message);
		//send property into RU system
		PutProperty rq = new PutProperty();
		rq.setAuthentication(setAuth());
		rq.setProperty(property);
		
		try {
			//send request with property info
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			//receive id for this prop from ru and write it
			message = resp.getRootElement().getChild("Status").getText();
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*vse ok*/
				String channelProductId = resp.getRootElement().getChild("ID").getValue();
				
				RelationService.load(sqlSession, Downloaded.PRODUCT_IMPORT_DATE, productid.toString(), FF.format(version));
				//Create entry for channel product ID
				ChannelProductMap tempChannelProductMap = new ChannelProductMap();
				tempChannelProductMap.setChannelId(CHANNEL);
				tempChannelProductMap.setProductId(productid.toString());
				tempChannelProductMap.setChannelProductId(channelProductId);
				sqlSession.getMapper(ChannelProductMapMapper.class).createProductMap(tempChannelProductMap);
				LOG.debug("Property " + productid + " has been uploaded/updated to RU system with id: " + channelProductId);
			} else {/*not good*/
				lostProductIDs.add(productid);
				LOG.error("Property " + productid + " could not be uploaded/updated: " + message);
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		
		return message;
	}

	/**
	 * Upload prices for all properties uploaded to RU
	 * 
	 */
	public void uploadPrices() {
		String message = "Uploading prices to Rentals United";
		LOG.debug(message);
		Date date = new Date();
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.YEAR, 2);

		final SqlSession sqlSession = RazorServer.openSession();
		try{
			//retrieve list of uploaded properties from ChannelProductMap
			ArrayList<Integer> importedProducts = sqlSession.getMapper(ChannelProductMapMapper.class).readProductIDs(CHANNEL);
			//int i = 0;
			for (Integer id : importedProducts) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(id.toString());
				// using ID from RU system (stored in ChannelProductMap)
				ChannelProductMap tempChannelProductMap = new ChannelProductMap();
				tempChannelProductMap.setChannelId(CHANNEL);
				tempChannelProductMap.setProductId(product.getId());
				Integer ruID = sqlSession.getMapper(ChannelProductMapMapper.class).readChannelProductID(tempChannelProductMap);
				//launch single property prices update (maybe create separate method)
				Price priceAction = new Price();
				priceAction.setEntityid(id.toString());
				priceAction.setDate(date);
				priceAction.setTodate(toDate.getTime());

				//select all prices for this productid
				ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).readByEntityId(priceAction);
				
				//create an array of seasonPrices for RU
				List<Season> seasonPriceArray = new ArrayList<Season>();
				List<MinStay> minStayArray = new ArrayList<MinStay>();
				for (Price price : prices) {
					String dateFrom = DF.format(price.getDate());
					String dateTo = DF.format(price.getTodate());
					double value = price.getValue();
					
					//check currency, if they differ from RU currency, convert them.
					Integer rentalsunitedCode = getRuLocationID(sqlSession, Integer.valueOf(product.getLocationid()));
					String ruCurrency = getCurrency(rentalsunitedCode);
					String priceCurrency = price.getCurrency();
					if (!priceCurrency.equals(ruCurrency)) {
						//convert
						value = PaymentService.convertCurrency(sqlSession, priceCurrency, ruCurrency, value);
					}
					
					Season seasonPrice = new Season();
					seasonPrice.setDateFrom(dateFrom);
					seasonPrice.setDateTo(dateTo);
					seasonPrice.setPrice(value);
					
					seasonPriceArray.add(seasonPrice);
					//check if price has minstay value and put it into MinStayArray
					if (price.getMinStay() != null && price.getMinStay() > 0) {
						//create minstay object
						MinStay minStay = new MinStay();
						minStay.setDateFrom(dateFrom);
						minStay.setDateTo(dateTo);
						minStay.setValue(price.getMinStay());
						
						minStayArray.add(minStay);
					}
				}
				//send prices
				if (seasonPriceArray != null && !seasonPriceArray.isEmpty()) {
					Prices pricesObj = new Prices();
					pricesObj.setPropertyID(ruID);
					pricesObj.setSeason(seasonPriceArray);
					putPrices(pricesObj);
				}
				if (minStayArray != null && !minStayArray.isEmpty()) {
					PropertyMinStay propertyMinStay = new PropertyMinStay();
					propertyMinStay.setPropertyID(ruID);
					propertyMinStay.setMinStay(minStayArray);
					putPropertyMinStay(propertyMinStay);
				}
				//create an array of discounts for RU (from YIELDS)
				Yield yieldAction = new Yield(NameId.Type.Product.name(), id.toString());
				ArrayList<Yield> yields = sqlSession.getMapper(YieldMapper.class).listbyentity(yieldAction);
				ArrayList<LastMinute> lastMinuteArray = new ArrayList<LastMinute>();
				ArrayList<LongStay> longStayArray = new ArrayList<LongStay>();
				for (Yield yield : yields) {
					if (!yield.getModifier().equals(Yield.DECREASE_PERCENT) && !yield.getState().equals(Yield.CREATED)) {
						continue;
					}
					//here goes lastMinute discount
					if (yield.getName().equals(Yield.LAST_MINUTE)) {
						LastMinute lastMinute = new LastMinute();
						lastMinute.setDateFrom(DF.format(yield.getFromdate()));
						lastMinute.setDateTo(DF.format(yield.getTodate()));
						lastMinute.setValue(yield.getAmount().intValue());
						lastMinute.setDaysToArrivalTo(yield.getParam());
						lastMinute.setDaysToArrivalFrom(0);
						
						lastMinuteArray.add(lastMinute);
						continue;
					}
					//here goes longStay discount
					if (yield.getName().equals(Yield.LENGTH_OF_STAY)) {
						LongStay longStay = new LongStay();
						longStay.setDateFrom(DF.format(yield.getFromdate()));
						longStay.setDateTo(DF.format(yield.getTodate()));
						longStay.setValue(yield.getAmount().intValue());
						longStay.setBigger(yield.getParam());
						longStay.setSmaller(365);
						
						longStayArray.add(longStay);
						continue;
					}
				}
				if (lastMinuteArray != null && !lastMinuteArray.isEmpty()) {
					LongStays longStays = new LongStays();
					longStays.setPropertyID(ruID);
					longStays.setLongStay(longStayArray);
					putLongStays(longStays);
				}
				if (longStayArray != null && !longStayArray.isEmpty()) {
					LastMinutes lastMinutes = new LastMinutes();
					lastMinutes.setPropertyID(ruID);
					lastMinutes.setLastMinute(lastMinuteArray);
					putLastMinutes(lastMinutes);
				}
				//LOG.debug(i++ + "Prices for property " + id + " have been uploaded");
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Upload prices array into RU system, and return status message.
	 * If some objects from array was not uploaded, message will be shown.
	 * 
	 * @param prices - array of seasonprices for RU created from our system
	 */
	private void putPrices(Prices prices){
		Integer productAltID = prices.getPropertyID();
		String message = "Uploading prices for property altID " + productAltID + " into RU system";
		//form a request and put array inside
		PutPrices rq = new PutPrices();
		rq.setAuthentication(setAuth());
		rq.setPrices(prices);
		
		try {
			//send prices into RU system
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			//receive status id for this request from RU and show it
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*OK*/
				LOG.debug("Prices for property altID " + productAltID + " has been uploaded/updated to RU system");
			} else {/*not OK*/
				message = resp.getRootElement().getChild("Status").getText();
				LOG.error("Prices for property altID " + productAltID + " could not be uploaded/updated: " + message);
				LOG.error("Detailed log message: ");
				List<Element> notifs = resp.getRootElement().getChild("Notifs").getChildren();
				for (Element notif : notifs) {
					message = notif.getText();
					LOG.error(message + " for dates: " + notif.getAttributeValue("DateFrom") + " - " + notif.getAttributeValue("DateTo"));
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
	}

	/**
	 * Upload PropertyMinStay array into RU system, and return status message if one or few elements from array wasn't uploaded.
	 * 
	 * @param propertyMinStay - array of MinStay values for RU created from our system
	 */
	private void putPropertyMinStay(PropertyMinStay propertyMinStay){
		Integer productAltID = propertyMinStay.getPropertyID();
		String message = "Uploading MinStay discounts for property altID " + productAltID + " into RU system";
		LOG.debug(message);
		PutMinstay rq = new PutMinstay();
		rq.setAuthentication(setAuth());
		rq.setPropertyMinStay(propertyMinStay);
		
		try {
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*OK*/
				LOG.debug("MinStays for property altID " + productAltID + " has been uploaded/updated to RU system");
			} else {/*not OK*/
				message = resp.getRootElement().getChild("Status").getText();
				LOG.error("MinStays for property altID " + productAltID + " could not be uploaded/updated: " + message);
				LOG.error("Detailed log message: ");
				List<Element> notifs = resp.getRootElement().getChild("Notifs").getChildren();
				for (Element notif : notifs) {
					message = notif.getText();
					LOG.error(message + " for dates: " + notif.getAttributeValue("DateFrom") + " - " + notif.getAttributeValue("DateTo"));
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
	}
	
	/**
	 * Upload LastMinutes array into RU system, and return status message if one or few elements from array wasn't uploaded.
	 * 
	 * @param lastMinutes - array of LongStays for RU created from our system
	 */
	private void putLastMinutes(LastMinutes lastMinutes){
		Integer productAltID = lastMinutes.getPropertyID();
		String message = "Uploading LastMinute discounts for property altID " + productAltID + " into RU system";
		LOG.debug(message);
		PutLastMinuteDiscounts rq = new PutLastMinuteDiscounts();
		rq.setAuthentication(setAuth());
		rq.setLastMinutes(lastMinutes);
		
		try {
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*OK*/
				LOG.debug("LastMinutes for property altID " + productAltID + " has been uploaded/updated to RU system");
			} else {/*not OK*/
				message = resp.getRootElement().getChild("Status").getText();
				LOG.error("LastMinutes for property altID " + productAltID + " could not be uploaded/updated: " + message);
				LOG.error("Detailed log message: ");
				List<Element> notifs = resp.getRootElement().getChild("Notifs").getChildren();
				for (Element notif : notifs) {
					message = notif.getText();
					LOG.error(message + " for dates: " + notif.getAttributeValue("DateFrom") + " - " + notif.getAttributeValue("DateTo"));
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
	}
	
	/**
	 * Upload LongStays array into RU system, and return status message if one or few elements from array wasn't uploaded.
	 * 
	 * @param longStays - array of LongStays for RU created from our system
	 */
	private void putLongStays(LongStays longStays){
		Integer productAltID = longStays.getPropertyID();
		String message = "Uploading LongStay discounts for property altID " + productAltID + " into RU system";
		LOG.debug(message);
		PutLongStayDiscounts rq = new PutLongStayDiscounts();
		rq.setAuthentication(setAuth());
		rq.setLongStays(longStays);
		
		try {
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*OK*/
				LOG.debug("LongStays for property altID " + productAltID + " has been uploaded/updated to RU system");
			} else {/*not OK*/
				message = resp.getRootElement().getChild("Status").getText();
				LOG.error("LongStays for property altID " + productAltID + " could not be uploaded/updated: " + message);
				LOG.error("Detailed log message: ");
				List<Element> notifs = resp.getRootElement().getChild("Notifs").getChildren();
				for (Element notif : notifs) {
					message = notif.getText();
					LOG.error(message + " for dates: " + notif.getAttributeValue("DateFrom") + " - " + notif.getAttributeValue("DateTo"));
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
	}
	
	/**
	 * Upload reservations into RU system.
	 * 
	 */
	public void uploadReservations() {
		String message = "Uploading reservations to Rentals United";
		LOG.debug(message);
		Date date = new Date();

		final SqlSession sqlSession = RazorServer.openSession();
		try{
			//retrieve list of uploaded properties from ChannelProductMap
			ArrayList<Integer> importedProducts = sqlSession.getMapper(ChannelProductMapMapper.class).readProductIDs(CHANNEL);
			for (int id : importedProducts) {
				//check import dates, update if needed
				
			}
			
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Upload available (and unavailable?) dates into RU system. 
	 */
	public void uploadAvailability() {
		String message = "Uploading availability calendar to Rentals United";
		LOG.debug(message);
		Date date = new Date();
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.YEAR, 2);

		final SqlSession sqlSession = RazorServer.openSession();
		try{
			//retrieve list of uploaded properties from ChannelProductMap
			ArrayList<Integer> importedProducts = sqlSession.getMapper(ChannelProductMapMapper.class).readProductIDs(CHANNEL);
			for (Integer id : importedProducts) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(id.toString());
				ChannelProductMap tempChannelProductMap = new ChannelProductMap();
				tempChannelProductMap.setChannelId(CHANNEL);
				tempChannelProductMap.setProductId(product.getId());
				Integer ruID = sqlSession.getMapper(ChannelProductMapMapper.class).readChannelProductID(tempChannelProductMap);

				//get list of all reservation for single property. Create day gaps between actual reservations.
				Reservation reservationAction = new Reservation();
				reservationAction.setProductid(id.toString());
				reservationAction.setFromdate(date);
				reservationAction.setTodate(toDate.getTime());
				
				List<Availability> availabilitiesArray = new ArrayList<Availability>();
				String lastDate = DF.format(date);
				ArrayList<Reservation> reservedDates = sqlSession.getMapper(ReservationMapper.class).reserveredDatesForPropertyid(reservationAction);
				for (Reservation reserved : reservedDates) {
					Availability availability = new Availability();
					//check for startDate
					String startDate = DF.format(reserved.getFromdate());
					if (!reserved.getFromdate().after(date)) {
						startDate = DF.format(date);
					}
					toDate.setTime(reserved.getTodate());
					toDate.add(Calendar.HOUR, -24);
					String endDate = DF.format(toDate.getTime());
					
					availability.setDateFrom(startDate);
					availability.setDateTo(endDate);
					availability.setValue(false);
					availabilitiesArray.add(availability);
					//fill gap between booked dates
					if (!lastDate.equals(DF.format(date))) {
						Availability gap = new Availability();
						gap.setDateFrom(lastDate);
						toDate.setTime(DF.parse(startDate));
						toDate.add(Calendar.HOUR, -24);
						gap.setDateTo(DF.format(toDate.getTime()));
						gap.setValue(true);
						availabilitiesArray.add(gap);
					}
					lastDate = DF.format(reserved.getTodate());
				}
				Availability gapFiller = new Availability();
				gapFiller.setDateFrom(lastDate);
				toDate.setTime(DF.parse(lastDate));
				toDate.add(Calendar.YEAR, +1);
				gapFiller.setDateTo(DF.format(toDate.getTime()));
				gapFiller.setValue(true);
				availabilitiesArray.add(gapFiller);
				if (availabilitiesArray != null && !availabilitiesArray.isEmpty()) {
					com.mybookingpal.utils.upload.ru.Calendar calendar = new com.mybookingpal.utils.upload.ru.Calendar();
					calendar.setPropertyID(ruID);
					calendar.setAvailability(availabilitiesArray);
					putAvailability(calendar);
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}
	
	/**
	 * Upload Availabilities into RU system, and return status message if one or few elements from array wasn't uploaded.
	 * 
	 * @param calendar - range of dates with availability for RU created from our system
	 */
	private void putAvailability(com.mybookingpal.utils.upload.ru.Calendar calendar){
		Integer productAltID = calendar.getPropertyID();
		String message = "Uploading Availability for property altID " + productAltID + " into RU system";
		LOG.debug(message);
		PutAvailability rq = new PutAvailability();
		rq.setAuthentication(setAuth());
		rq.setCalendar(calendar);
		
		try {
			String rs = getConnection(toXML(rq));
			SAXBuilder builder = new SAXBuilder();
			Document resp = builder.build(new StringReader(rs));
			
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {/*OK*/
				LOG.debug("Availability for property altID " + productAltID + " has been uploaded/updated to RU system");
			} else {/*not OK*/
				message = resp.getRootElement().getChild("Status").getText();
				LOG.error("Availability for property altID " + productAltID + " could not be uploaded/updated: " + message);
				LOG.error("Detailed log message: ");
				List<Element> notifs = resp.getRootElement().getChild("Notifs").getChildren();
				for (Element notif : notifs) {
					message = notif.getText();
					LOG.error(message + " for dates: " + notif.getAttributeValue("DateFrom") + " - " + notif.getAttributeValue("DateTo"));
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
	}
	
	/**
	 * Get Rentals United location code from mybookingpal location.
	 * 
	 * @param sqlSession
	 * @param locID - mybookingpal location id
	 * @return Rentals United location id (code)
	 * @throws ServiceException if there is no code (no location ID was given, or no rentals United code was found)
	 */
	private static Integer getRuLocationID(SqlSession sqlSession, Integer locID) throws Throwable {
		if (locID != null) {
			Location location = sqlSession.getMapper(LocationMapper.class).read(locID.toString());
			String locRU = location.getCoderentalsunited();
			if (locRU != null) {
				return Integer.valueOf(locRU);
			} else {
				throw new ServiceException(Error.location_id, "No RentalsUnited code id was found for location " + locID);
			}
		} else {
			throw new ServiceException(Error.location_id, "Location id null");
		}
	}
	
	/**
	 * Get the currency for specified location for product
	 * 
	 * @param coderentalsunited
	 * @return currency code by location (City)
	 */
	private static String getCurrency(Integer coderentalsunited) {
		String currencyCode = null;
		try {		
		JAXBContext jc = JAXBContext.newInstance(net.cbtltd.rest.ru.Currencies.class);
		Unmarshaller ju = jc.createUnmarshaller();
		Currencies currencies = (Currencies) ju.unmarshal(new File("war/WEB-INF/classes/net/cbtltd/rest/ru/xml/Currencies.xml"));
		
		for (Currency currency : currencies.getCurrency()) {
			for (Integer locID : currency.getLocationID()) {
				if (locID.equals(coderentalsunited)) {
					currencyCode = currency.getCurrencyCode();
					break;
				}
			}
			if (currencyCode != null) {break;}
		}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return currencyCode;
	}
	
	/**
	 * Set basic XML authentication
	 * @return authentication object
	 */
	private static Authentication setAuth() {
		Authentication authentication = new Authentication();
		authentication.setPassword(PASSWORD);
		authentication.setUsername(USERNAME);
		return authentication;
	}
	
	/**
	 * Converts selected XML object class to string
	 * @param t - Class object
	 * @return converted string
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static <T> String toXML(T t) throws IOException, JAXBException {
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty("com.sun.xml.bind.xmlDeclaration", false);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(t, outputStream);
			String xml = outputStream.toString();
			return xml;
		} finally {
			if(outputStream != null) {
				outputStream.close();
			}
		}
	}

	/**
	 * Transfer XML text to specified object Class (<T>)
	 * @param clazz - selected class
	 * @param xml - XML text
	 * @return object
	 * @throws JAXBException
	 */
	public static <T> T fromXML(Class<T> clazz, String xml) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		Object obj = um.unmarshal(new StringReader(xml));
		return clazz.cast(obj);
	}

	private static HashMap<String,Integer> AMENITITES = null;

	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	private static final Integer addAmenity(String amenity) {

		if (AMENITITES == null) {
			AMENITITES = new HashMap<String, Integer>();
			AMENITITES.put("RMA60",2);												//		Cookware & Kitchen Utensils
			AMENITITES.put("RMA55",4);												//		Iron & Ironing Board
			AMENITITES.put("RMA56",4);												//		Iron & Ironing Board -- *need two matches
			AMENITITES.put("RMA149",5);												//		Drying Rack
			AMENITITES.put("RMA50",6);												//		Hair Dryer
			AMENITITES.put("REC43",7);												//		Bed Linen & Towels
			AMENITITES.put("RMA32",13);												//		Dishwasher
			AMENITITES.put("RMA31",11);												//		Washing Machine
			AMENITITES.put("RMA19",17);												//		kettle
			AMENITITES.put("RMA90",19);												//		Cable TV --
			AMENITITES.put("RMA3",21);												//		Alarm Clock
			AMENITITES.put("RMA104",22);											//		Stereo
			AMENITITES.put("RMA163",23);											//		DVD
			AMENITITES.put("RMA129",24);											//		CD player
			AMENITITES.put("MRC47",70);												//		night table
			AMENITITES.put("MRC47",71);												//		night tables --
			AMENITITES.put("RMA90",74);												//		TV
			AMENITITES.put("RMA7",89);												//		Balcony
			AMENITITES.put("RMA105",114);											//		cooking hob
			AMENITITES.put("RMA19",123);											//		electric kettle --
			AMENITITES.put("RMA68",124);											//		microwave
			AMENITITES.put("RMA81",128);											//		plates
			AMENITITES.put("RMA88",130);											//		fridge / freezer
			AMENITITES.put("RMA88",131);											//		fridge --
			AMENITITES.put("RMA149",134);											//		washing machine with drier
			AMENITITES.put("RMA19",140);											//		coffee maker --
			AMENITITES.put("RMA88",152);											//		freezer --
			AMENITITES.put("RMA90",166);											//		TV (local channels only) --
			AMENITITES.put("RMA210",167);											//		satellite TV
			AMENITITES.put("RMA54",174);											//		internet connection
			AMENITITES.put("RMA2",180);												//		air conditioning
			AMENITITES.put("HAC242",187);											//		heating
			AMENITITES.put("RMA146",189);											//		table and chairs
			AMENITITES.put("CBF58",201);											//		wardrobe
			AMENITITES.put("RCC5",234);												//		Laundry
			AMENITITES.put("EVT4",235);												//		Breakfast
			AMENITITES.put("MRC47",250);											//		dining table
			AMENITITES.put("PHY24",281);											//		Wheelchair access possible
			AMENITITES.put("HAC186",295);											//		On street parking --
			AMENITITES.put("RMA13",315);											//		bathtub
			AMENITITES.put("RMA163",325);											//		DVD player --
			AMENITITES.put("CTT6",330);												//		city maps
			AMENITITES.put("HAC223",339);											//		FREE internet access
			AMENITITES.put("HAC223",368);											//		Free Wireless Internet
			AMENITITES.put("BUS40",374);											//		Cell Phone Rentals
			AMENITITES.put("RMA19",390);											//		Espresso-Machine
			AMENITITES.put("REC43",395);											//		Towels
			AMENITITES.put("BUS41",429);											//		Computer rental
			AMENITITES.put("USC3",448);												//		Free weekly cleaning
			AMENITITES.put("RMA145",497);											//		Luggage Storage Facilities
			AMENITITES.put("RCC7",504);												//		Private parking
			AMENITITES.put("RMA55",590);											//		Iron
			AMENITITES.put("RMA56",591);											//		Ironing Board
			AMENITITES.put("RMA26",596);											//		Free cot in the apartment
			AMENITITES.put("RMA26",597);											//		Free cot on request --
			AMENITITES.put("RMA92",601);											//		Safe
			AMENITITES.put("HAC33",652);											//		Brailled Elevator --
			AMENITITES.put("RMA129",660);											//		CD Player --
			AMENITITES.put("RMA19",667);											//		Coffee Maker in Room --
			AMENITITES.put("HAC33",689);											//		Elevator
			AMENITITES.put("HAC42",702);											//		Free Parking
			AMENITITES.put("RMA123",792);											//		Wireless Internet
			AMENITITES.put("RCC7",793);												//		Parking
			AMENITITES.put("RMA101",799);											//		Smoking allowed
			AMENITITES.put("HAC42",803);											//		Free parking on the street
			AMENITITES.put("HAC186",804);											//		Paid parking on the street
			AMENITITES.put("HAC42",805);											//		Free parking with garage --
			AMENITITES.put("HAC53",806);											//		Paid parking with garage
			AMENITITES.put("RMA54",808);											//		Paid cable internet
		}
		if (AMENITITES.get(amenity) != null) {
			return AMENITITES.get(amenity);
		} else return null;//lostamenities.add(amenity + ", " + getAmenity(amenity));}
	}
}