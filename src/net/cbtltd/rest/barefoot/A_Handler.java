package net.cbtltd.rest.barefoot;

//import java.io.IOException;
import java.io.StringReader;
//import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





//import javax.xml.bind.JAXBException;
//import javax.xml.datatype.DatatypeConfigurationException;
//import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
//import javax.xml.namespace.QName;





import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.barefoot.services.Amenities;
import net.cbtltd.rest.barefoot.services.ArrayOfAmenities;
import net.cbtltd.rest.barefoot.services.ArrayOfMinimumDayRule;
import net.cbtltd.rest.barefoot.services.ArrayOfProperty;
import net.cbtltd.rest.barefoot.services.ArrayOfString;
import net.cbtltd.rest.barefoot.services.BookingAccess3;
import net.cbtltd.rest.barefoot.services.BookingAccess3Soap;
//import net.cbtltd.rest.barefoot.services.GetAvailableBookingPeriodsResponse.GetAvailableBookingPeriodsResult;
//import net.cbtltd.rest.barefoot.services.GetPropertyAllImgsResponse;
import net.cbtltd.rest.barefoot.services.MinimumDayRule;
import net.cbtltd.rest.barefoot.services.Property;
import net.cbtltd.rest.barefoot.services.PropertyMinimumDay;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.rest.rtr.RTRWSAPISoap;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.YieldService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Username: BFRZ
 * Password:  testpwd0827
 * BarefootAccount :  v3cdemo
 * Url : https://agent.barefoot.com/barefootwebservice/bookingaccess3.asmx
 * 
 * Jennifer jennifer.li@barefoot.com
 *
 */
public class A_Handler extends PartnerHandler implements IsPartner {
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat SF = new SimpleDateFormat("yyyy-MM-dd");
	private static final String username = "pal1024001";
	private static final String password = "*24palwer";
	//private static final String barefootAccount = "v3cscv0710_dailytest";
	//private static final String barefootAccount = "v3cdemo";
	private static final int partneridx = 1038;
			

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {super(partner);}

	/**
	 * Gets the port to which to connect.
	 *
	 * @return the port.
	 */
	public static final RTRWSAPISoap getPort() {
		throw new ServiceException(Error.service_absent, "Barefoot getPort()");
	}

	/**
	 * Returns collisions with the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Barefoot isAvailable " + reservation.getId();
		LOG.debug(message);
		boolean isAvailable = false;
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			String propertyId =  product.getAltid();
			String dateFrom = DF.format(reservation.getFromdate());
			String dateTo = DF.format(reservation.getTodate());
			
			isAvailable = port.isPropertyAvailability(username, password, barefootAccount, dateFrom, dateTo, propertyId, partneridx);
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
			//x.printStackTrace();
		} 
		MonitorService.monitor(message, timestamp);
		//System.out.println(isAvailable);
		return isAvailable;
	}
	
	
	/**
	 * Create reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Barefoot createReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}
	
	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			LOG.debug("readReservation " + reservation);
			throw new ServiceException(Error.service_absent, "Barefoot readReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}
	
	/**
	 * Update reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Barefoot updateReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}
	
	/**
	 * Confirm reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be confirmed.
	 */
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		//DO NOTHING
	}

	/**
	 * Cancel reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try {
			
			if(!port.cancelReservation(username, password, barefootAccount, Integer.parseInt(reservation.getAltid()), partneridx)){
				throw new ServiceException(Error.reservation_api, "Error in cancelling reservations");
			}else{
				LOG.debug("cancelReservation SUCCESS, reservationAltId=" + reservation.getAltid());
			}
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Barefoot readAlerts()");
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Barefoot readLocations()");
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		final SqlSession sqlSession = RazorServer.openSession();
		Date version = new Date();
		String message = "Read Prices started Barefoot." ;
		LOG.debug(message);

		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try{
			
			String date1 = "";
			String date2 = "";
			int weekly = 0;
			int sleeps = 0;
			float baths = 0;
			int rooms = 0;
			String strwhere = "";
			ArrayOfProperty arrayOfProperty = port.getAllPropertiesDetail(username, password, barefootAccount, date1, date2, weekly, sleeps, baths, rooms, strwhere, partneridx);			
			List<Property> listProperty = arrayOfProperty.getProperties();
			for (Property property : listProperty){
				String propertyID = property.getPropertyID();
				
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), propertyID, false);
				if (product == null) {
					continue;
				}
				
				String productId = product.getId();
				
				LOG.debug("PropertyID:"+propertyID);
				
				ArrayList<Price> pricesFromApi = new ArrayList<Price>();
				ArrayList<Yield> discountYieldFromApi = new ArrayList<Yield>();
				
				PropertyMinimumDay propertyMinimumDay = port.getMinimumDays(username, password, barefootAccount, Integer.parseInt(propertyID), partneridx);
				ArrayOfMinimumDayRule arrayOfMinimumRule = propertyMinimumDay.getMininumDayRules();
				List<MinimumDayRule> listMinimumDayRule = arrayOfMinimumRule.getMinimumDayRules();
				List<String> arrayMinimumPeriodFrom = new ArrayList<String>(); 
				List<String> arrayMinimumPeriodTo = new ArrayList<String>(); 
				List<Integer> arrayMinimumNumberDays = new ArrayList<Integer>(); 
				for(MinimumDayRule minimumDayRule : listMinimumDayRule){
					int minDays = minimumDayRule.getNumOfMinDay();
					XMLGregorianCalendar dateFrom = minimumDayRule.getPeriodFrom();
					XMLGregorianCalendar dateTo = minimumDayRule.getPeriodTo();
					
					//long difference = dateTo.toGregorianCalendar().getTimeInMillis() - dateFrom.toGregorianCalendar().getTimeInMillis();
					
					//int differenceInDays = (int) (difference / 86400000 );
					
					String dateStart = DF.format(dateFrom.toGregorianCalendar().getTime());
					String dateEnd = DF.format(dateTo.toGregorianCalendar().getTime());
					arrayMinimumPeriodFrom.add(dateStart);
					arrayMinimumPeriodTo.add(dateEnd);
					arrayMinimumNumberDays.add(minDays);
					
				}	
				String response = port.getPropertyPartnerRatesXML(username, password, barefootAccount, propertyID, "", "", partneridx);
				//System.out.println(response);
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(new StringReader(response));
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("PropertyRates");
				Double dailyRate = null;
				Double weeklyRate = null;
				Double monthlyRate = null;
				Double moreNightsRate = null;
				int numberNights = 0;
				Date previousDateFrom = null;
				Date previousDateTo = null;
				for (int i = 0; i < list.size(); i++) {
					Element node = (Element) list.get(i);
					String  rateFrom = node.getChildText("date1");
				 	String rateTo = node.getChildText("date2");
				 	Date rateDateFrom = SF.parse(SF.format(DF.parse(rateFrom)));
				 	Date rateDateTo = SF.parse(SF.format(DF.parse(rateTo)));
				 	if(i==0){
				 		previousDateFrom = rateDateFrom;
				 		previousDateTo = rateDateTo;
				 	}
				 	if(previousDateFrom.compareTo(rateDateFrom)!=0 || previousDateTo.compareTo(rateDateTo)!=0){
				 		dailyRate = null;
				 		weeklyRate = null;
				 		monthlyRate = null;
				 		moreNightsRate = null;
				 	}
				 	String rateRent = node.getChildText("rent");
				 	String ratePriceType = node.getChildText("pricetype");
				 	
				 	for(int m=0; m< arrayMinimumPeriodFrom.size(); m++){
				 		Date minDateFrom = SF.parse(SF.format(DF.parse(arrayMinimumPeriodFrom.get(m).toString())));
				 		Date minDateTo = SF.parse(SF.format(DF.parse(arrayMinimumPeriodTo.get(m).toString())));
				 		if(
				 				(rateDateFrom.compareTo(minDateFrom)>=0 && rateDateTo.compareTo(minDateTo)<=0) || 
				 				(rateDateFrom.compareTo(version)<=0 && rateDateTo.compareTo(minDateTo)<=0)
				 		){
				 			if(ratePriceType.compareTo("daily")==0){
						 		dailyRate = Double.parseDouble(rateRent.replaceAll(",", ""));
						 		int minimumStay = arrayMinimumNumberDays.get(m).intValue();
						 		Double minumumValue = dailyRate * minimumStay;
								this.addPrice(pricesFromApi, product, "USD", Unit.DAY, rateDateFrom, rateDateTo, dailyRate, minumumValue, minimumStay);
						 		previousDateFrom = rateDateFrom;
						 		previousDateTo = rateDateTo;
						 	}else if(ratePriceType.compareTo("weekly")==0 && previousDateFrom.compareTo(rateDateFrom)==0 && previousDateTo.compareTo(rateDateTo)==0){
								weeklyRate = Double.parseDouble(rateRent.replaceAll(",", ""));
							} else if(ratePriceType.compareTo("monthly")==0 && previousDateFrom.compareTo(rateDateFrom)==0 && previousDateTo.compareTo(rateDateTo)==0){
								monthlyRate = Double.parseDouble(rateRent.replaceAll(",", ""));
							}else if(ratePriceType.indexOf("nights")>0 && previousDateFrom.compareTo(rateDateFrom)==0 && previousDateTo.compareTo(rateDateTo)==0){
								moreNightsRate = Double.parseDouble(rateRent.replaceAll(",", ""));
								numberNights = Integer.parseInt(ratePriceType.substring(0, ratePriceType.indexOf("nights")-1));
							}
				 		}else{
				 			continue;
				 		}
				 	}
				 	
				 	if(weeklyRate!=null && dailyRate!=null){
						LOG.debug("Weekly Rate:"+propertyID +" ProductID:" +productId);
						Yield weeklyDiscount = YieldService.createLengthOfStayDiscount(productId, dailyRate, weeklyRate, 7, rateDateFrom, rateDateTo);
						if(weeklyDiscount!=null){
							discountYieldFromApi.add(weeklyDiscount);
						}
					}
					
					if(monthlyRate!=null && dailyRate!=null){
						//TODO set number of days in month
						LOG.debug("Monthly Rate:"+propertyID +" ProductID:" +productId);
						Yield monthlyDiscount = YieldService.createLengthOfStayDiscount(productId, dailyRate, monthlyRate, 30, rateDateFrom, rateDateTo);
						if(monthlyDiscount!=null){
							discountYieldFromApi.add(monthlyDiscount);
						}
					}
					
					if(moreNightsRate!=null && dailyRate!=null){
						LOG.debug("More Nights Rate:"+propertyID +" ProductID:" +productId);
						Yield moreNightsDiscount = YieldService.createLengthOfStayDiscount(productId, dailyRate, moreNightsRate, numberNights, rateDateFrom, rateDateTo);
						if(moreNightsDiscount!=null){
							discountYieldFromApi.add(moreNightsDiscount);
						}
					}
					 	
				 	
				}
				
				PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
//				PartnerService.updateProductAdjustments(sqlSession, product, adjustmentsFromApi, version);
				PartnerService.updateProductRateYields(sqlSession, product, discountYieldFromApi, version);
				
			}
			sqlSession.commit();
		}catch (Throwable x) {
			LOG.error(x.getStackTrace());
			x.printStackTrace();
		}
		
		LOG.debug("readPrices() BareFoot DONE");
	    
	}
	
	private void addPrice(ArrayList<Price> pricesFromApi, Product product, String currency, String unit, 
			Date fromDate, Date toDate, double rateValue, double minimumValue, Integer minimumStay){
		try{
			Price price = new Price();
			price.setEntityid(product.getId());
			price.setEntitytype(NameId.Type.Product.name());
			price.setPartyid(getAltpartyid());
			price.setName(Price.RACK_RATE);
			price.setType(NameId.Type.Reservation.name());
			price.setQuantity(1.0);
			price.setCurrency(currency);
			price.setUnit(unit);
			price.setDate(fromDate);
			price.setTodate(toDate);

			price.setRule(Price.Rule.AnyCheckIn.name());
			price.setValue(rateValue);
			price.setCost(rateValue);
			price.setMinStay(minimumStay);
			price.setMinimum(PaymentHelper.getAmountWithTwoDecimals(minimumValue));
			price.setAvailable(1);
			
			pricesFromApi.add(price);
		}catch (Throwable x) {
			LOG.error(x.getStackTrace());
			x.printStackTrace();
		}
		
	}

	/**
	 * Transfer accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		String message = "readProducts Barefoot started!";
		LOG.debug(message);
		Date version = new Date();		
		try{
			//update/create products. 
			createProduct();
			LOG.debug("Barefoot:  readProducts done"  );
			MonitorService.monitor(message, version);
		}
		catch (Throwable x) {
			LOG.error(x.getStackTrace());
		}
	}
	
	public void createProduct(){
		String altid = null; 
		Product product;
		Date timestamp = new Date();
		String message = "createProduct started Barefoot";		
		Date version = new Date();
		final SqlSession sqlSession = RazorServer.openSession();
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try {			
			String date1 = "";
			String date2 = "";
			int weekly = 0;
			int sleeps = 0;
			float baths = 0;
			int rooms = 0;
			String strwhere = "";
			ArrayOfProperty arrayOfProperty = port.getAllPropertiesDetail(username, password, barefootAccount, date1, date2, weekly, sleeps, baths, rooms, strwhere, partneridx);			
			List<Property> listProperty = arrayOfProperty.getProperties();
			for (Property property : listProperty){
				
				altid = property.getPropertyID();
				System.out.println(altid);
				if(altid.compareTo("10436")==0) continue; //TODO check it with the client
				
				String propertyLocation = property.getPropAddressNew();
				String addressProperty = propertyLocation;
				String address = propertyLocation.substring(0,propertyLocation.indexOf(","));
				propertyLocation = propertyLocation.substring(propertyLocation.indexOf(",")+2);
				String cityName = propertyLocation.substring(0,propertyLocation.indexOf(","));
				propertyLocation = propertyLocation.substring(propertyLocation.indexOf(",")+2);
				String stateCode = propertyLocation.substring(0,propertyLocation.indexOf(","));
				propertyLocation = propertyLocation.substring(propertyLocation.indexOf(",")+2);
				String postalCode = propertyLocation.substring(0,propertyLocation.indexOf(","));
				propertyLocation = propertyLocation.substring(propertyLocation.indexOf(","));
				String countryName = "";
				if(propertyLocation.substring(1).length()>1) 
					countryName = propertyLocation.substring(2);
				//if(countryCode.isEmpty()){countryCode = Country.US;}
				String countryCode = Country.US;
				if(countryName.compareTo("Mexico")==0) countryCode="MX"; //TODO CHECK IT WITH MARKO!
				else continue;
				
				Location location = PartnerService.getLocation(sqlSession, 
						cityName,
						stateCode,
						countryCode);
						
				String maxPersons = property.getOccupancy();
				int bathrooms = Integer.parseInt(property.getBathrooms());
				int bedrooms = Integer.parseInt(property.getBedrooms());
				String type = Product.Type.Accommodation.name();
				String currency = "USD";
				StringBuilder sb = new StringBuilder();
				String language = Language.EN;
				if (property.getDescription() != null && !property.getDescription().isEmpty()) {
					sb.append("Description ").append(":").append(property.getDescription()).append("\n");
				}
				if (property.getExtdescription() != null && !property.getExtdescription().isEmpty()) {
					sb.append("ExtDescription ").append(":").append(property.getExtdescription()).append("\n");
				}
				
				String name = property.getName();
				
				ArrayList<String> attributes = new ArrayList<String>();
				ArrayOfAmenities arrayOfAmenities = property.getAmenity();
				List<Amenities> amenities = arrayOfAmenities.getAmenities();
				for(Amenities amenity:amenities){
					if(amenity.getAmenityValue().equals("Yes")){
						addAttribute(attributes, amenity.getAmenityName());
					}
				}
				
				String propertyType = "Apartment"; //HARDCODED, THERE ISN'T INFO ABOUT PROPERTY TYPE FROM THEIR WEB SERVICE
				addType(attributes,propertyType);
				
				product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {continue;}
				String altSupplierID = product.getAltSupplierId();
				
				product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), NameId.trim(sb.toString(), 20000), language));
				TextService.update(sqlSession, product.getTexts());
				
				product.setVersion(version);
				
				createOrUpdateProductModel(location, cityName, stateCode, postalCode, countryCode, maxPersons, 
						bathrooms, 0, bedrooms, type, currency, 0.0, "", 0.0, "",
						addressProperty, name,altid,altSupplierID, product);
				
				sqlSession.getMapper(ProductMapper.class).update(product);
		
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
			}
			
			sqlSession.commit();
			//canceling not updated products
			Product action = new Product();
			action.setAltpartyid(getAltpartyid());
			action.setState(Product.CREATED);
			action.setVersion(version); 
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
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
			String country, String maxpersons, int bathroom, int toilet, int rooms, String propertytype,
			String currency, double deposit, String terms, double cleaningfee,
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
		//product.setLocationid(location.getId());
		
		product.setAltitude(0.0);
		product.setPhysicaladdress(address.toString());
		
		product.setAltSupplierId(altsupplierid);
		product.setAltpartyid(getAltpartyid()); 
		product.setSupplierid(getAltpartyid());  
		product.setAltid(altid);
		
		product.setPerson(maxpersons == null || maxpersons.isEmpty() ? 1 : Integer.valueOf(maxpersons));
		product.setChild(0); //TODO IF WE DON'T HAVE THIS VALUE WHAT TO DO, SET DEFAULT VALUE?
		product.setInfant(0);
		product.setQuantity(1); //TODO CHECK!
		
		product.setRating(5);
		product.setCommission(getCommission());
		product.setDiscount(getDiscount());
		product.setRank(getRank());
		
		product.setType(propertytype);
		product.setCurrency(currency);
		product.setSecuritydeposit(deposit);
		product.setCleaningfee(cleaningfee);
		product.setUnit(Unit.DAY);
		product.setWebaddress(webaddress);
		product.setServicedays("0000000");
		product.setCode("");
		product.setToilet(toilet);
		product.setBathroom(bathroom);
		product.setRoom(rooms);
		product.setDynamicpricingenabled(false);
		product.setUseonepricerow(false); //TODO CHECK IT!
		
		product.setName(name.toString());
		
		return product;
	}
	
	/**
	 * Transfer reservation schedule.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		final SqlSession sqlSession = RazorServer.openSession();
		Date timestamp = new Date();
		String message = "Read Schedule started Barefoot";
		LOG.debug(message);
		
		Calendar date = Calendar.getInstance();  
	    date.setTime(new Date());
	    date.add(Calendar.DATE, 1);
		Date fromDate = date.getTime();
		date.add(Calendar.YEAR,2);
		Date toDate = date.getTime();
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try {
			String date1 = "";
			String date2 = "";
			int weekly = 0;
			int sleeps = 0;
			float baths = 0;
			int rooms = 0;
			String strwhere = "";
			ArrayOfProperty arrayOfProperty = port.getAllPropertiesDetail(username, password, barefootAccount, date1, date2, weekly, sleeps, baths, rooms, strwhere, partneridx);			
			List<Property> listProperty = arrayOfProperty.getProperties();
			for (Property property : listProperty){
				String propertyID = property.getPropertyID();
				
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), propertyID,false);
				if (product == null) {
					continue;
				}
				
				String response = port.getAvailableBookingPeriodsXML(username, password, barefootAccount, DF.format(fromDate), DF.format(toDate), propertyID, partneridx);
				//System.out.println(response);
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(new StringReader(response));
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("AvailableBookingPeriods");
				System.out.println(propertyID);
				System.out.println("---------------------------------------------");
				Date startReservedDate = fromDate;
				for (int i = 0; i < list.size(); i++) {
					Element node = (Element) list.get(i);
				 	String availableFrom = node.getChildText("Date1");
				 	String availableTo = node.getChildText("Date2");
				 	System.out.println("Available: "+ availableFrom + "-----" + availableTo);
				 	
				 	if(startReservedDate.compareTo(SF.parse(SF.format(DF.parse(availableFrom))))<0){
				 		Date reservedFrom = startReservedDate;
				 		date.setTime(SF.parse(SF.format(DF.parse(availableFrom))));    
				 		date.add( Calendar.DATE, -1 );    
				 		Date reservedTo = date.getTime();
				 		
				 		System.out.println("Reserved: "+ reservedFrom + "---------" + reservedTo);
				 		
				 		PartnerService.createSchedule(sqlSession, product, 
				 				reservedFrom, 
				 				reservedTo, 
								timestamp);
						PartnerService.cancelSchedule(sqlSession, product, timestamp);
				 		
				 		date.setTime(SF.parse(SF.format(DF.parse(availableTo))));    
				 		date.add( Calendar.DATE, 1 );    
				 		startReservedDate = date.getTime(); 
				 	}else{
				 		date.setTime(SF.parse(SF.format(DF.parse(availableTo))));    
				 		date.add( Calendar.DATE, 1 );    
				 		startReservedDate = date.getTime(); 
				 	}
				 	
				}
				System.out.println("---------------------------------------------");
				sqlSession.commit();
			}
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
			//x.printStackTrace();
		} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation special offers.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Barefoot readSpecials()");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession,
			Reservation reservation, CreditCard creditCard) {
		Date timestamp = new Date();
		String message = "createReservationAndPayment Barefoot " + reservation.getId();
		LOG.debug(message);
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		Map<String, String> result = new HashMap<String, String>();
		try{
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}
			
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}
			
			String fromDate = DF.format(reservation.getFromdate()).toString();
			String toDate = DF.format(reservation.getTodate()).toString();
			int propertyId = Integer.parseInt(product.getAltid());
			int numAdult = reservation.getAdult();
			int numPet = 0;
			int numBaby = 0;
			int numChild = reservation.getChild();
			
			double reservationPrice = computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency()).getTotal();
			
			String leaseId = port.getLeaseidByReztypeid(username, password, barefootAccount, fromDate, toDate,propertyId,numAdult,numPet,numBaby,numChild,partneridx,partneridx);
			
			ArrayOfString consumerInfo = new ArrayOfString();
			if(customer.getLocalAddress()!=null)consumerInfo.getStrings().add(customer.getLocalAddress());else consumerInfo.getStrings().add("");
			if(customer.getLocalAddress()!=null)consumerInfo.getStrings().add(customer.getLocalAddress());else consumerInfo.getStrings().add("");
			if(customer.getCity()!=null)consumerInfo.getStrings().add(customer.getCity());else consumerInfo.getStrings().add("");
			if(customer.getRegion()!=null)consumerInfo.getStrings().add(customer.getRegion());else consumerInfo.getStrings().add("");
			//consumerInfo.getStrings().add("");
			if(customer.getPostalcode()!=null)consumerInfo.getStrings().add(customer.getPostalcode());else consumerInfo.getStrings().add("");
			if(customer.getCountry()!=null)consumerInfo.getStrings().add(customer.getCountry());else consumerInfo.getStrings().add("");
			//consumerInfo.getStrings().add("");
			if(customer.getFamilyName()!=null)consumerInfo.getStrings().add(customer.getFamilyName());else consumerInfo.getStrings().add("");
			if(customer.getFirstName()!=null)consumerInfo.getStrings().add(customer.getFirstName());else consumerInfo.getStrings().add("");
			if(customer.getDayphone()!=null)consumerInfo.getStrings().add(customer.getDayphone());else consumerInfo.getStrings().add("");
			if(customer.getMobilephone()!=null)consumerInfo.getStrings().add(customer.getMobilephone());else consumerInfo.getStrings().add("555-555-5555");
			if(customer.getFaxphone()!=null)consumerInfo.getStrings().add(customer.getFaxphone());else consumerInfo.getStrings().add("555-555-5555");
			if(customer.getEmailaddress()!=null)consumerInfo.getStrings().add(customer.getEmailaddress());else consumerInfo.getStrings().add("");
			consumerInfo.getStrings().add(product.getAltid());
			consumerInfo.getStrings().add("Internet");
			/*string street1,
			string street2,
			string city,
			string state,
			string zip,
			string country,
			string lastname,
			string firstname,
			string homephone,
			string bizphone,
			string fax,
			string mobile,
			string email,
			string PropertyID,
			string SourceOfBusiness */
			System.out.print("ConsumerInfo:");
			for (int i = 0; i < consumerInfo.getStrings().size(); i++) {
				   System.out.print(consumerInfo.getStrings().get(i) + ", ");
			}
			
			int customerId = port.setConsumerInfo(username, password, barefootAccount, consumerInfo);
			
			if(customerId>0){
				ArrayOfString bookingInfo = new ArrayOfString();
				bookingInfo.getStrings().add(0, "TRUE"); //isTestMode
				bookingInfo.getStrings().add(1, String.valueOf(reservationPrice)); //Initial amount
				bookingInfo.getStrings().add(2, ""); //EzicAccount TODO CHECK IT!
				bookingInfo.getStrings().add(3, product.getAltid()); //PropertyID 
				bookingInfo.getStrings().add(4, DF.format(reservation.getFromdate()).toString()); //  Arrival date
				bookingInfo.getStrings().add(5, DF.format(reservation.getTodate()).toString()); //  Departure date
				bookingInfo.getStrings().add(6, String.valueOf(customerId)); // Tenant contact ID
				bookingInfo.getStrings().add(7, leaseId); // The unique ID of a quote
				bookingInfo.getStrings().add(8, "");// EZIC3
				bookingInfo.getStrings().add(9, creditCard.getFirstName());// First Name of tenants credit card info, which can be an empty string if isTestMode=ON.
				bookingInfo.getStrings().add(10, creditCard.getLastName());//Last Name of tenants credit card info, which can be an empty string if isTestMode=ON
				bookingInfo.getStrings().add(11, "");//EzicTag 
				bookingInfo.getStrings().add(12, "S");// EzicTranstype  In general, use value S, which means sale. Another choice is A, which means authorization.
				bookingInfo.getStrings().add(13, "C");//EzicPaytype  In general, use value C, which means Credit card. For non-EZIC clients, another choice can be A , which means ACH and is not available at this moment.
				bookingInfo.getStrings().add(14, creditCard.getNumber());//Credit Card number or tenants' bank account number if the value of info[13] above is set as ACH. Leave this string empty if isTestMode=ON.
				bookingInfo.getStrings().add(15, creditCard.getMonth());//CC expiration month, which can be empty string if isTestMode=ON. 
				bookingInfo.getStrings().add(16, creditCard.getYear());//CC expiration year, which can be empty string if isTestMode=ON.
				bookingInfo.getStrings().add(17, creditCard.getSecurityCode());//CC security number, which can be empty string if isTestMode=ON.
				bookingInfo.getStrings().add(18, "HOTEL"); //ccratetype  In general, use value HOTEL. It can be empty string if isTestMode=ON.
				String typeOfCreditCard = "";
				if(creditCard.getType().toString().compareTo("Master")==0) typeOfCreditCard = "1";
				else if(creditCard.getType().toString().compareTo("Visa")==0) typeOfCreditCard = "2";
				else if(creditCard.getType().toString().compareTo("Discover")==0) typeOfCreditCard = "3";
				else if(creditCard.getType().toString().compareTo("Amex")==0) typeOfCreditCard = "4";
				bookingInfo.getStrings().add(19, typeOfCreditCard); //Use 1 for Master, 2 for Visa, 3 for Discover, 4 for Amex.  It can be empty string if isTestMode=ON.
				bookingInfo.getStrings().add(20, ""); //street 
				bookingInfo.getStrings().add(21, ""); //city
				bookingInfo.getStrings().add(22, ""); //state
				bookingInfo.getStrings().add(23, ""); //zip
				bookingInfo.getStrings().add(24, ""); //country
				
				//If above five parameters have been provided, system will use them as billing info for credit card processing. Otherwise, set them as empty string and system will use tenant’s contact info as credit card billing info. 
				
				String resultOfBooking = port.propertyBooking(username, password, barefootAccount, bookingInfo, partneridx);
				
				if(resultOfBooking.compareTo("cc failed")==0){
					message = "cc failed: Error when processing the credit card.";
					LOG.error(message);
					reservation.setState(Reservation.State.Cancelled.name());
					result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
					result.put(GatewayHandler.ERROR_MSG, message);
					return result;
				}else if(resultOfBooking.compareTo("cc Duplicate")==0){
					message = "cc Duplicate: Your transaction is duplicated";
					LOG.error(message);
					reservation.setState(Reservation.State.Cancelled.name());
					result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
					result.put(GatewayHandler.ERROR_MSG, message);
					return result;
				}else if(resultOfBooking.compareTo("Property invalidation")==0){
					message = "Property invalidation:  when property is un-available(our system will double check availability before finish the booking)";
					LOG.error(message);
					reservation.setState(Reservation.State.Cancelled.name());
					result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
					result.put(GatewayHandler.ERROR_MSG, message);
					return result;
				}else{
					String folioID = resultOfBooking.substring(resultOfBooking.indexOf("<FolioID>")+9, resultOfBooking.indexOf("</FolioID>"));
					if(!folioID.isEmpty()){
						LOG.debug("createReservation confirmationID=" + folioID);
						reservation.setAltid(folioID);
					    reservation.setAltpartyid(getAltpartyid());
						reservation.setMessage(reservation.getMessage());
						reservation.setVersion(timestamp);
						reservation.setState(Reservation.State.Confirmed.name());
						result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);						
					}
				}
			}else{
				message = "There is the problem during saving consumer information!";
				LOG.error(message);
				reservation.setState(Reservation.State.Cancelled.name());
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				result.put(GatewayHandler.ERROR_MSG, message);
				return result;
			}

			
		}catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		MonitorService.monitor(message, timestamp);
		
		return result;
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String message = "Barefoot readPrice productAltId:" + productAltId;
		LOG.debug(message);
		ReservationPrice result = new ReservationPrice();
		Date timestamp = new Date();
		try {
			result = this.computePrice(sqlSession, reservation, productAltId, currency);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}
		
		MonitorService.monitor(message, timestamp);
		message = "Barefoot End readPrice productAltId:" + productAltId + " Total Amount:" + result.getTotal();
		LOG.debug(message);
		return result;
	}
	
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productaltid, String currency) throws Throwable {
		Date version = new Date();
		ReservationPrice result = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try{
			String response = port.getQuoteRatesDetail(username, password, barefootAccount, DF.format(reservation.getFromdate()), DF.format(reservation.getTodate()), 
					Integer.parseInt(productaltid), 
					reservation.getAdult(), 0, 0, reservation.getChild(), partneridx, partneridx);
			//System.out.println(response);
			response = "<details>"+response+"</details>";
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(response));
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("propertyratesdetails");
			
			String currencyResponse = "USD"; //CHECK IT!
			Double currencyRate = 1.0;
			if(!currency.equalsIgnoreCase(currencyResponse)){
				currencyRate = WebService.getRate(sqlSession, currencyResponse, currency, new Date());
			}
						
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			reservation.setCurrency(currency);
				
			Double total = 0.0;
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				String rateName = node.getChildText("ratesname");
				Double rateValue = 0.0;
				try{
					rateValue = Double.parseDouble(node.getChildText("ratesvalue"));
					rateValue = PaymentHelper.getAmountWithTwoDecimals(rateValue * currencyRate);
				}catch(Exception parseExc){
					LOG.warn(parseExc.getMessage());
				}
				
				
				if(rateName.compareTo("Rent")==0){
					/*QuoteDetail propertyPriceQd = new QuoteDetail();
					propertyPriceQd.setAmount(rateValue+"");
					propertyPriceQd.setDescription("Property Price ");
					propertyPriceQd.setIncluded(true);
					quoteDetails.add(propertyPriceQd);*/
					
					total = total + rateValue;
					
					net.cbtltd.shared.Price propertyPrice = new net.cbtltd.shared.Price();
					propertyPrice = new net.cbtltd.shared.Price();
					propertyPrice.setEntitytype(NameId.Type.Reservation.name());
					propertyPrice.setEntityid(reservation.getId());
					propertyPrice.setType(net.cbtltd.shared.Price.RATE);
					propertyPrice.setName(net.cbtltd.shared.Price.RATE);
					propertyPrice.setState(net.cbtltd.shared.Price.CREATED);
					propertyPrice.setDate(version);
					propertyPrice.setQuantity(1.0);
					propertyPrice.setUnit(Unit.EA);
					propertyPrice.setValue(rateValue);
					propertyPrice.setCurrency(reservation.getCurrency());
					reservation.getQuotedetail().add(propertyPrice);
					
				}else if(rateName.compareTo("Lodging Tax")==0){
					Double taxValue = PaymentHelper.getAmountWithTwoDecimals(rateValue * currencyRate);
					String taxName =  node.getChildText("ratesname");
					total = total + taxValue;
					QuoteDetail chargeTaxQd = new QuoteDetail(String.valueOf(taxValue), currency, taxName, "Included in the price", "", true);
					quoteDetails.add(chargeTaxQd);
				}else{
					Double feeValue = PaymentHelper.getAmountWithTwoDecimals(rateValue * currencyRate);
					String feeName =  node.getChildText("ratesname");
					total = total + feeValue;
					QuoteDetail chargeFeeQd = new QuoteDetail(String.valueOf(feeValue), currency, feeName, "Included in the price", "", true);
					quoteDetails.add(chargeFeeQd);
				}
				
			}
			
			reservation.setPrice(total);
			reservation.setQuote(total);
			
			
			//result.setTotal(total);
			result.setTotal(reservation.getQuote());
			result.setPrice(total);
			result.setCurrency(reservation.getCurrency());
			result.setQuoteDetails(quoteDetails);
			
			reservation.setExtra(0.0);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
			reservation.setDeposit(0.0);
				
			
		}catch (Throwable x) {
			//reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		LOG.debug("Barefoot compute price: " + reservation.getQuotedetail());
		return result;
	}
	
	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		long startTime = System.currentTimeMillis();
		String altid = null; 
		String message = "Create Images started Barefoot." ;
		LOG.debug(message);
		Date version = new Date();
		
		BookingAccess3 ss = new BookingAccess3();
		BookingAccess3Soap port = ss.getBookingAccess3Soap();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String barefootAccount =  party.getAltid();
		//barefootAccount = "v3cscv0710_dailytest";
		
		try{
			String date1 = "";
			String date2 = "";
			int weekly = 0;
			int sleeps = 0;
			float baths = 0;
			int rooms = 0;
			String strwhere = "";
			
			
			
			ArrayOfProperty arrayOfProperty = port.getAllPropertiesDetail(username, password, barefootAccount, date1, date2, weekly, sleeps, baths, rooms, strwhere, partneridx);			
			List<Property> listProperty = arrayOfProperty.getProperties();
			for(Property property:listProperty){
				System.out.println("------------------------");
				String propertyID = property.getPropertyID();
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), propertyID,false);
				if (product == null) {
					continue;
				}
				//GetPropertyAllImgsResponse.GetPropertyAllImgsResult response = port.getPropertyAllImgs(username, password, barefootAccount, propertyID);
				String response = port.getPropertyAllImgsXML(username, password, barefootAccount, propertyID);
				System.out.println(propertyID);
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(new StringReader(response));
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("PropertyImg");
				ArrayList<NameId> images = new ArrayList<NameId>();
				for (int i = 0; i < list.size(); i++) {
				 	Element node = (Element) list.get(i);
				 	String imageURL = node.getChildText("imagepath");
				 	String imageNo = node.getChildText("imageNo");
				 	String imageName = imageURL.substring(imageURL.indexOf(propertyID+"/") + (propertyID+"/").length());
				 	if(!imageNo.equals("100")){
				 		images.add(new NameId(imageName, imageURL));
				 	}
				}
				LOG.debug("images " + images);
				UploadFileService.uploadImages(sqlSession,NameId.Type.Product, product.getId(), Language.EN,images);
				
				sqlSession.commit();
			}
			
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			//x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for createImage execution  : " + (endTime - startTime)/1000 + " seconds.");
		
		
	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Barefoot inquireReservation()");
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
			TYPES.put("Guesthouse","PCT16"); //Guesthouse
			TYPES.put("Condo","PCT8"); //Condominium
			TYPES.put("Hotel","PCT20");
			TYPES.put("Hostel","PCT19");
			TYPES.put("Apartment","PCT3"); //Appartement
			TYPES.put("Villas","PCT35");
			TYPES.put("Townhouse","PCT16");
			TYPES.put("Inn","PCT21");//Inn
			TYPES.put("Cabin","PCT5");//Cabin or Bungalow
			TYPES.put("Motel","PCT27");//Motel
			TYPES.put("Lodge","PCT22");//Lodge
			TYPES.put("Bed & Breakfast","PCT4");//Bed & Breakfast
		}
		
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
	}
	
	private static HashMap<String,String> PROPERTY_ATTRIBUTES = null;
	/**
	 * PROPERTY_ATTRIBUTES map.
	 *
	 * @param PROPERTY_ATTRIBUTES the PROPERTY_ATTRIBUTES
	 * @param attribute the attribute
	 */
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {

		if (PROPERTY_ATTRIBUTES == null) {
			PROPERTY_ATTRIBUTES = new HashMap<String, String>();
			
			PROPERTY_ATTRIBUTES.put("CHILDREN_WELCOME", "HAC218");
			PROPERTY_ATTRIBUTES.put("CHILDREN_NOT_ALLOWED", "HIC3");
			PROPERTY_ATTRIBUTES.put("SMOKING_ALLOWED", "RMP101");
			PROPERTY_ATTRIBUTES.put("SMOKING_NOT_ALLOWED", "RMP74");
			PROPERTY_ATTRIBUTES.put("PETS_CONSIDERED", "SUI4"); //TODO CHECK
			PROPERTY_ATTRIBUTES.put("ACCESSIBILITY_WHEELCHAIR_ACCESSIBLE", "PHP24");
			
			PROPERTY_ATTRIBUTES.put("KITCHEN", "RMA59");
			PROPERTY_ATTRIBUTES.put("DINING_KITCHEN", "INF10");
			PROPERTY_ATTRIBUTES.put("DINING_AREA", "INF10");
			PROPERTY_ATTRIBUTES.put("DINING_REFRIGERATOR", "RMA88");
			PROPERTY_ATTRIBUTES.put("DINING_MICROWAVE", "RMA68");
			PROPERTY_ATTRIBUTES.put("DINING_MICROWAVE", "RMA68");
			PROPERTY_ATTRIBUTES.put("DINING_COFFEE_MAKER", "RMA19");
			PROPERTY_ATTRIBUTES.put("DINING_DISHWASHER", "RMA32");
			PROPERTY_ATTRIBUTES.put("DINING_DISHES_UTENSILS", "RMA98");
			PROPERTY_ATTRIBUTES.put("DINING_STOVE", "RMA105");
			PROPERTY_ATTRIBUTES.put("DINING_ICE_MAKER", "RMA89");
			PROPERTY_ATTRIBUTES.put("DINING_TOASTER", "RMA167");
			PROPERTY_ATTRIBUTES.put("DINING_ROOM", "CBF71");
			
			PROPERTY_ATTRIBUTES.put("TELEVISION", "CBF68");
			PROPERTY_ATTRIBUTES.put("STEREO", "RMA104");
			PROPERTY_ATTRIBUTES.put("VIDEO_LIBRARY", "CBF62");
			PROPERTY_ATTRIBUTES.put("VIDEO_GAMES", "HAC123");
			PROPERTY_ATTRIBUTES.put("PING_PONG_TABLE", "RMA140");
			PROPERTY_ATTRIBUTES.put("DVD", "RMA163");
			PROPERTY_ATTRIBUTES.put("SATELLITE_OR_CABLE", "FAC10");
			
			PROPERTY_ATTRIBUTES.put("GRILL", "HAC118");
			PROPERTY_ATTRIBUTES.put("BALCONY", "FAC2");
			PROPERTY_ATTRIBUTES.put("GARDEN", "FAC4");
			PROPERTY_ATTRIBUTES.put("BICYCLE", "TRP1");
			PROPERTY_ATTRIBUTES.put("TENNIS", "CSC25");
			PROPERTY_ATTRIBUTES.put("GOLF", "RCC4");
			PROPERTY_ATTRIBUTES.put("BOAT", "TRP2");
			PROPERTY_ATTRIBUTES.put("BOAT_DOCK", "ACC6");
			PROPERTY_ATTRIBUTES.put("KAYAK_CANOE", "RST65");
			PROPERTY_ATTRIBUTES.put("WATER_SPORTS_GEAR", "RSP99");
			PROPERTY_ATTRIBUTES.put("SNOW_SPORTS_GEAR", "RSP88");
			
			PROPERTY_ATTRIBUTES.put("COMMUNAL_POOL", "FAC8");
			PROPERTY_ATTRIBUTES.put("PRIVATE_POOL", "FAC9");
			PROPERTY_ATTRIBUTES.put("HOT_TUB", "RST104");
			PROPERTY_ATTRIBUTES.put("SAUNA", "FAC11");
			PROPERTY_ATTRIBUTES.put("INDOOR_POOL", "HAC54");
			PROPERTY_ATTRIBUTES.put("HEATED_POOL", "HAC49");
			
			PROPERTY_ATTRIBUTES.put("TYPE_BED_AND_BREAKFAST", "PCP4");
			PROPERTY_ATTRIBUTES.put("TYPE_GUEST_HOUSE", "PCT16");
			PROPERTY_ATTRIBUTES.put("TYPE_HOTEL", "PCT20");
			PROPERTY_ATTRIBUTES.put("TYPE_MOTEL", "PCT27");
			PROPERTY_ATTRIBUTES.put("HOUSE_CLEANING_INCLUDED", "USC3");
			PROPERTY_ATTRIBUTES.put("OTHER_SERVICES_CHAUFFEUR", "RCC20");
			PROPERTY_ATTRIBUTES.put("OTHER_SERVICES_CONCIERGE", "CBF45");
			PROPERTY_ATTRIBUTES.put("OTHER_SERVICES_MASSAGE", "HAC61");
			PROPERTY_ATTRIBUTES.put("OTHER_SERVICES_CAR_AVAILABLE", "TRP5");
			
			PROPERTY_ATTRIBUTES.put("FAMILY", "RES7");
			PROPERTY_ATTRIBUTES.put("LUXURY", "BCC8");
			PROPERTY_ATTRIBUTES.put("TOURIST_ATTRACTIONS", "INF7");
			PROPERTY_ATTRIBUTES.put("BUDGET", "BCC2");
			PROPERTY_ATTRIBUTES.put("HISTORIC", "ARC5");
			PROPERTY_ATTRIBUTES.put("ADVENTURE", "RSN10");
			PROPERTY_ATTRIBUTES.put("SPA", "CSC3");
			PROPERTY_ATTRIBUTES.put("FARM_HOLIDAYS", "ACC21");
			
			PROPERTY_ATTRIBUTES.put("INTERNET", "BCT7");
			PROPERTY_ATTRIBUTES.put("FIREPLACE", "RMA41");
			PROPERTY_ATTRIBUTES.put("WOOD_STOVE", "RMA105");
			PROPERTY_ATTRIBUTES.put("AIR_CONDITIONING", "RMA2");
			PROPERTY_ATTRIBUTES.put("TOWELS", "REC43");
			PROPERTY_ATTRIBUTES.put("WASHER", "RMA149");
			//PROPERTY_ATTRIBUTES.put("DRYER", "RMA50");
			PROPERTY_ATTRIBUTES.put("HAIR_DRYER", "RMA50"); 
			PROPERTY_ATTRIBUTES.put("PARKING", "HAC63");
			PROPERTY_ATTRIBUTES.put("TELEPHONE", "RMA107");
			PROPERTY_ATTRIBUTES.put("LIVING_ROOM", "CBF72");
			PROPERTY_ATTRIBUTES.put("FITNESS_ROOM", "RST21");
			PROPERTY_ATTRIBUTES.put("GAME_ROOM", "HAC44");
			PROPERTY_ATTRIBUTES.put("IRON_BOARD", "RMA56");
			PROPERTY_ATTRIBUTES.put("ELEVATOR", "HAC33");
		}
		
		if (PROPERTY_ATTRIBUTES.get(attribute) != null) {
			attributes.add(PROPERTY_ATTRIBUTES.get(attribute));
		}else{
			System.out.println("Missing attribute: " + attribute);
		}
	}
}
