/**
 * @author	abookingnet And Isaac Mahgrefteh 
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */

package net.cbtltd.rest.bookt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.bookt.ArrayOfKeyValueOfdateTimedecimal.KeyValueOfdateTimedecimal;
import net.cbtltd.rest.bookt.ArrayOfKeyValueOfdateTimeint.KeyValueOfdateTimeint;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.AttributeService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RegionMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Region;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.journal.EventJournal;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * API handler for Bookt PMS
 * Uses SOAP and XML
 * 
 * @see http://isinghblog.blogspot.ca/2009/03/wcf-service-and-adobe-livecycle_10.html
 *
 * Bookt: 89eea03e-b659-474f-9fd3-8ab86240651b
 * Kissimmee: c6464e12-dced-4b6a-b28f-2db8d1c751b3
 *
 * I strongly advise that you log in to the API test account in our CP so that you can log in and see what rates to expect, add rates, etc.
 * Username: apitester@bookt.com
 * Password: apitester13
 * Login here: https://controlpanel.bookt.com
 * 
 * This data will correspond to the API key 89eea03e-b659-474f-9fd3-8ab86240651b
 * Please do not specify the currency in your SOAP requests - it will come back based on the currency defined for that property.
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	public static final String ENDPOINT_ADDRESS = "http://connect.bookt.com/svc/connect.svc";
	public static final String APIKEY = "89eea03e-b659-474f-9fd3-8ab86240651b";
//	public static final String KISSIMMEE = "c6464e12-dced-4b6a-b28f-2db8d1c751b3";
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final ObjectFactory OF = new ObjectFactory();
	private static HashMap<String,String> TYPES = null;

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {
		super(partner);
	}

	/**
	 * Gets the port to which to connect.
	 *
	 * @return the port
	 */
	public static final IConnect getPort() {
//		URL wsdlURL = Connect.WSDL_LOCATION;
//		ConnectREST ss = new ConnectREST(wsdlURL, SERVICE_NAME);
//		return ss.getBasicHttpBindingIConnectREST(); 
		//TODO: CJM
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IConnect.class);
		factory.setAddress(ENDPOINT_ADDRESS);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IConnect port = (IConnect) factory.create();
		return port;
	}

    private  int numDaysBetween(Date date1, Date date2) {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        int diffInDays = (int) ((date1.getTime() - date2.getTime())/ DAY_IN_MILLIS );
       
        return Math.abs(diffInDays);
    } 
	
    // Adds the number of days to the date given
    private Date addOneToDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    } 
	/**
	 * Returns false if there are collisions with the specified reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be tested for collisions.
	 * @param productaltid the partner's ID of the reserved property.
	 * @return true if there are no collisions, else false.
	 */
	//results for REST and SOAP do not always match. 
	//https://connect.bookt.com/ws/?method=search&entity=property&apikey=a330c168-6a07-4f04-a357-b72cee626b25&checkin=2013-07-09&checkout=2013-07-09
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		//String message = "isAvailable " + reservation.getId();
		//LOG.debug(message);
		try {
			
			if(reservation.getFromdate().after(reservation.getTodate()) && !reservation.getFromdate().equals(reservation.getTodate()))  
			{throw new ServiceException(Error.date_range, " ");}
				
			if(reservation == null || reservation.getFromdate() == null || reservation.getTodate() == null||reservation.getProductid() == null)
			{throw new ServiceException(Error.parameter_absent, " ");} 
			
	
			//if(reservation.getFromdate().)
			Integer altPropertyID = Integer.valueOf(PartnerService.getProductaltid(sqlSession, reservation));
			
			if(numDaysBetween(reservation.getFromdate(), reservation.getTodate())==0){
				reservation.setTodate(addOneToDate(reservation.getTodate()));
			}
			 
		
			ArrayOfKeyValueOfdateTimeint rs = getPort().getAvailability(getApikey(), altPropertyID, "", getXGC(reservation.getFromdate()), getXGC(reservation.getTodate()));
		
			
			

			List<KeyValueOfdateTimeint> availability = rs.getKeyValueOfdateTimeint();
			if (availability != null && !availability.isEmpty()) {
				for (KeyValueOfdateTimeint available : availability) {
			//	System.out.println("avialable = "+available.getValue());System.out.println("avialable = "+available.getKey());
					if (available.getValue() == 10 || available.getValue() == 0) {return false;}             //changed from 0; 
				}
			}
			return true;
		}
		catch (Throwable x) {LOG.error(x.getMessage());} 
		//MonitorService.monitor(message, timestamp);
		return false;
	}

	/**
	 * Create the specified reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created in the Bookt PMS.
	 * @param productaltid the Bookt ID of the property to be reserved.
	 */
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		createReservationAndPayment(sqlSession, reservation, null);
	}

	/**
	 * Read an existing reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation " + reservation.getAltid();
		LOG.debug(message);
		IConnect port = getPort();
		try {
			Boolean useInternalID = true;
			Booking booking = port.getBooking(getApikey(), reservation.getAltid(), useInternalID);
			LOG.debug("readReservation booking " + booking);
			if (booking == null) {throw new ServiceException(Error.reservation_id, reservation.getAltid() + " invalid Bookt ID.");}
			reservation = getReservation(sqlSession, booking);
			LOG.debug("readReservation " + reservation);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Update existing reservation.
	 *
	 * This is not yet implemented in the Bookt API.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date version = new Date();
		String message = "updateReservation " + reservation.getAltid();
		LOG.debug(message);
		throw new ServiceException(Error.service_absent, "Bookt updateReservation()");

		//		IConnect port = getPort();
		//		try {
		//			Booking rq = getBooking(sqlSession, reservation, null);
		//			Integer options = null;
		//			port.modifyBooking(getApikey(), rq, options);
		//			sqlSession.getMapper(ReservationMapper.class).update(reservation);
		//		}
		//		catch (Throwable x) {
		//			reservation.setMessage(x.getMessage());
		//			reservation.setState(Reservation.State.Cancelled.name());
		//			x.getMessage();
		//		}
		//		MonitorService.monitor(message, timestamp);

	}

	/**
	 * Confirm provisional reservation.
	 *
	 * This is not yet implemented in the Bookt API so does nothing.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be confirmed.
	 */
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		//DO NOTHING
	}

	/**
	 * Cancel existing reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation;
		LOG.debug(message);
		IConnect port = getPort();
		try {
			Boolean useInternalID = true;
			Double refundAmount = 0.0;
			Integer options = null;
			LOG.debug("cancelReservation useInternalID=" + useInternalID + " refundAmount=" + refundAmount + "options=" + options);
			port.cancelBooking(getApikey(), reservation.getAltid(), useInternalID, getBigDecimal(refundAmount), options);
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read accommodation alerts.
	 *
	 * Not yet implemented in the Bookt API.
	 * 
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Bookt readAlerts()");
	}

	/**
	 * Read accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		Date timestamp = new Date();
		String message = "readPrices " + getAltpartyid();
		LOG.debug(message);
		IConnect port = getPort();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			//String rq = RelationService.find(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE,getAltpartyid(), null);
			String rq = "2010-07-01T00:00:00"; //last download
			List<Integer> propertyids = port.getPropertyIDs(getApikey(), getXGC(rq)).getInt();
			Date version = new Date();
			int i = 0;
			for (Integer propertyid : propertyids) {
				String altid = String.valueOf(propertyid);
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (product == null) {LOG.error(Error.product_altid.getMessage() + " " + altid);}
				else {
					
					readPrices(port, sqlSession, version, propertyid, 1, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
					readPrices(port, sqlSession, version, propertyid, 7, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
//					readPrices(port, sqlSession, version, propertyid, 30, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
					readPrices(port, sqlSession, version, propertyid, 1, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
					readPrices(port, sqlSession, version, propertyid, 7, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
//					readPrices(port, sqlSession, version, propertyid, 30, product.getId(), product.getCurrency(), net.cbtltd.shared.Unit.DAY);
					sqlSession.commit();			
//					LOG.debug(i++ + " readPrices " + propertyid + " " + product.getId());
				}
				wait(getPricewait());
			}

		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Gets the accommodation rates.
	 *
	 * @param port the API port.
	 * @param sqlSession the current SQL session.
	 * @param propertyid the property ID.
	 * @param los the length of stay.
	 * @param productid the product ID.
	 * @param supplierid the supplier ID.
	 * @param currency the price currency code.
	 * @param unit the price unit of measure.
	 * @return the prices of the product.
	 */
	private final void readPrices(
			IConnect port,
			SqlSession sqlSession,
			Date version,
			Integer propertyid,
			Integer los,
			String productid,
			String currency,
			String unit
		) throws Throwable {

		Date startDate = Time.getDateStart();
		Date endDate = Time.addDuration(startDate, 500, Time.DAY);

		Price price = new Price();
		price.setEntityid(productid);
		price.setEntitytype(NameId.Type.Product.name());
		price.setPartyid(getAltpartyid());
		price.setName(Price.RACK_RATE);
		price.setType(NameId.Type.Reservation.name());
		price.setCurrency(currency);
		price.setQuantity(Double.valueOf(los));
		price.setUnit(unit);

		ArrayOfKeyValueOfdateTimedecimal rs = port.getRates(getApikey(), propertyid, "", los, getXGC(startDate), getXGC(endDate), currency);
		List<KeyValueOfdateTimedecimal> rates = rs.getKeyValueOfdateTimedecimal();
//		LOG.debug("rates " + rates);
		Date fromdate = null;
		Date todate = null;
		BigDecimal fromrate = null;
		if (rates != null && !rates.isEmpty()) {
			for (KeyValueOfdateTimedecimal rate : rates) {
//				LOG.debug("rate " + rate);
				if (fromdate == null) {
					fromdate = getDateTime(rate.key);
					fromrate = rate.value;
					price.setDate(fromdate);
					price.setValue(fromrate.doubleValue());
				}
				todate = getDateTime(rate.key);
				if (fromrate.compareTo(rate.value) != 0) {
					price.setTodate(Time.addDuration(todate, -1.0, Time.DAY));
					updatePrice(sqlSession, version, price);
					fromdate = getDateTime(rate.key);
					fromrate = rate.value;
					price.setDate(fromdate);
					price.setValue(fromrate.doubleValue());
				}
			}
			price.setTodate(todate);
			updatePrice(sqlSession, version, price);
		}
	}

	private void updatePrice(SqlSession sqlSession, Date version, Price price) {
		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}
		price.setState(Price.CREATED);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setMinimum(0.0);
		price.setFactor(1.0);
		Double value = price.getValue();
		Double quantity = price.getQuantity();
		price.setValue(NameId.round(value / quantity));
		price.setVersion(version);
		sqlSession.getMapper(PriceMapper.class).update(price);
		sqlSession.getMapper(PriceMapper.class).cancelversion(price);
//		LOG.debug("updatePrice " + price);
	}

	/**
	 * Read property locations.
	 *
	 * Not yet implemented in the Bookt API.

	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Bookt readLocations()");
	}

	/**
	 * Read Bookt properties and map to accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	
	
	public synchronized void readProducts() {
		Date timestamp = new Date();
		String message = "readProducts BookT";
		LOG.debug(message);
		IConnect port = getPort();
		boolean useInternalID = true;
		StringBuilder description;
		String apikey = getApikey();
		ArrayList<String> attributes;
		Country country;
		Region region;
		StringBuilder address;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
//			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), getXGC(new Date()).toString());
			List<Integer> propertyids = port.getPropertyIDs(apikey, getXGC(new Date(0))).getInt();
			Date version = new Date();
			int i = 0;
			String bppropertyid;//bookingpal id. 
			for (Integer propertyid : propertyids) {         //property BookT //product razor. 
				
				Property property = port.getProperty(apikey, String.valueOf(propertyid), useInternalID);
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), String.valueOf(property.getID()));
				if (product == null) {continue;}
				LOG.debug("ProductID :"+product.getId());
			    bppropertyid = product.getId();
				description = new StringBuilder();
				

				product.setAltitude(0.0);
				product.setBathroom(property.getBathrooms().intValue());
				product.setChild(0);
				product.setCode(property.getAltID().getValue());
			    product.setAltSupplierId(apikey.subSequence(36-12, 36).toString());
				product.setCurrency(getCurrency());
				product.setDiscount(getDiscount());
				product.setInfant(0);
				product.setLatitude(property.getLatitude().doubleValue());
				product.setLongitude(property.getLongitude().doubleValue());
				
				product.setCommission(getCommission());
				product.setSecuritydeposit(Product.DEFAULT_SECUIRTY_DEPOSIT);
				product.setCleaningfee(Product.DEFAULT_CLEANING_FEE);
				product.setRank(getRank());
				
				//ask about/////////////////////////////
				product.setDynamicpricingenabled(false);
                product.setTax("VAT Normal");
                product.setUseonepricerow(false);
              ////////////////////////////////////////////
                
				       country = getCountry(sqlSession, property.getCountry().getValue());
				       region = getRegion(sqlSession, property.getState().getValue(), country.getId());
				if (region != null) {
					Location location = PartnerService.getLocation(
						sqlSession, 
						property.getCity().getValue(),
						region.getId(),
						country.getId());
					product.setLocationid(location.getId()); //fix get location
				}
				else {LOG.debug("Location " + property.getCity().getValue() + " " +  property.getState().getValue() + " " + country.getId());}
				
				product.setRank(0.0);

				     address = new StringBuilder();
				if (property.getAddress1() != null) {address.append(property.getAddress1().getValue());}
				if (property.getAddress2() != null) {address.append("\n").append(property.getAddress2().getValue());}
				if (property.getCity() != null) {address.append("\n").append(property.getCity().getValue());}
				if (property.getState() != null) {address.append("\n").append(property.getState().getValue());}
				product.setPhysicaladdress(address.toString());

				product.setPerson(property.getSleeps().intValue());
				product.setQuantity(1);
				product.setRating(5);
				product.setRoom(property.getBedrooms().intValue());
				product.setToilet(property.getBedrooms().intValue());
				product.setType(Product.Type.Accommodation.name());
				product.setUnit(net.cbtltd.shared.Unit.DAY);
				product.setWebaddress(getWebaddress());
				product.setVersion(version);

				
				product.setName(property.getHeadline().getValue());
				sqlSession.getMapper(ProductMapper.class).update(product);
			

				updateCreatePropertyFees(property,sqlSession, bppropertyid, version);
				updatePropertyTaxes(property,sqlSession, version, bppropertyid);
				

				product.setIntegerValue(Product.Value.YearBuilt.name(), property.getYearBuilt());
				

				if (property.getDescription() != null) {description.append(Text.stripHTML(property.getDescription().getValue())).append("\n");}

				updateOrCreateAmenities(sqlSession,property,bppropertyid);
								
				product.setPublicText(new Text(product.getPublicId(), product.getName(), Text.Type.HTML, version, description.toString(), Language.EN));
				product.setCheckinText(new Text(product.getCheckinId(), product.getName(), Text.Type.HTML, version, property.getCheckInInstructions().getValue(), Language.EN));

				TextService.update(sqlSession, product.getTexts());
				
			//	RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues()); //need
			
				
			//	readImage(sqlSession, String.valueOf(propertyid), version);  //need working. 
				
			
				LOG.debug(i++ + " readProducts " + property.getID() + " " + bppropertyid);
				wait(getProductwait());
				//if(i > 2 ) {sqlSession.commit(); break;}
				if(i % 5 == 0){sqlSession.commit();}
			}
			
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally { sqlSession.commit(); sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}
	
	
	
	private void updateOrCreateAmenities(SqlSession sqlSession,  Property property ,String bppropertyid){
		String otaattribute; 
		ArrayList<String> attributes = new ArrayList<String>();
	   
		addType(attributes, property.getType().getValue());       // addes atttibutes to type          
		
	//	RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, bppropertyid, attributes);       d
		
		
		for (String amenity : property.getAmenities().getValue().getString()) {
			otaattribute =AttributeService.getOTACodeForAttribute(amenity);
			if("".equals(otaattribute)){continue;}
			attributes.add(otaattribute);
		}

		RelationService.replace(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE, bppropertyid, attributes);
	//	RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE, bppropertyid, attributes);

		sqlSession.commit();
	}
	
	private static HashMap<String,String> AmenitiesMap = null;
	private static final String getAmenitiesMap(String booktamenity) {
		if (AmenitiesMap == null) {
			
			AmenitiesMap.put("Air Conditioning","FAC1");
			AmenitiesMap.put("Breakfast Bar","EVT4");
			AmenitiesMap.put("Cable/Satellite","RMA210");
			AmenitiesMap.put("Charcoal Grill","HAC118");
			AmenitiesMap.put("City View","RVT3");
			AmenitiesMap.put("Coffeemaker","RMA19");
			AmenitiesMap.put("Community Pool","FAC8");
			AmenitiesMap.put("DVD","RMA163");
			AmenitiesMap.put("Daily Housekeeping","CSC6");
			AmenitiesMap.put("Deck or Balcony","CBF1");
			AmenitiesMap.put("Dining and Livingroom","CBF71");
			AmenitiesMap.put("Double Bed","CBF29");
			AmenitiesMap.put("Eat-in Kitchen","RMA59");
			AmenitiesMap.put("Full Bed","CBF29");
			AmenitiesMap.put("Full Sized Refridgerator","RMA88");
			AmenitiesMap.put("Game Room","HAC44");
			AmenitiesMap.put("Garage","");//nothing.
			AmenitiesMap.put("Gas Grill","HAC118");
			AmenitiesMap.put("Gourmet Kitchen","RMA59");
			AmenitiesMap.put("Ground Floor","RLT17");
			AmenitiesMap.put("Hair Dryer","HAC240");
			AmenitiesMap.put("Heated Pool","REC30");
			AmenitiesMap.put("Hot Tub","RST104");
			AmenitiesMap.put("Internet Connection","RMA54");
			AmenitiesMap.put("Iron/Board","RMA56");
			AmenitiesMap.put("Jacuzzi Tub","RMA57");
			AmenitiesMap.put("Jacuzzi/HotTub","RMA57");
			AmenitiesMap.put("King Bed","CBF30");
			AmenitiesMap.put("Linens Provided","");//cannot find anyhting. 
			AmenitiesMap.put("Microwave","RMA68");
			AmenitiesMap.put("Multiple TV's","RMA90");
			AmenitiesMap.put("Nice Furniture","");//cannot find match. 
			AmenitiesMap.put("Night Life","RSN18");
			AmenitiesMap.put("No Pets Allowed","");
			AmenitiesMap.put("No Smoking","RMA74");
			AmenitiesMap.put("Non Smoking","RMP74");
			AmenitiesMap.put("Ocean View","LOC16");
			AmenitiesMap.put("Oceanfront","LOC16");
			AmenitiesMap.put("Outdoor Pool","RST123");
			AmenitiesMap.put("Patio Furniture","");
			AmenitiesMap.put("Queen Bed","CBF15");
			AmenitiesMap.put("Shuttle","TRP17");
			AmenitiesMap.put("Sofa Bed","BED6");
			AmenitiesMap.put("Sound/Cove View","");
			AmenitiesMap.put("Stove/Oven","RMA77");
			AmenitiesMap.put("Suitable for Children","HAC218");
			AmenitiesMap.put("TV","RMA90");
			AmenitiesMap.put("Telephone","RMA107");
			AmenitiesMap.put("Tennis Courts","RST94");
			AmenitiesMap.put("Walk to Beach","ENV4");
			AmenitiesMap.put("Washer/Dryer","RMA149");
		}
		String key = booktamenity.toLowerCase();
		if (AmenitiesMap.get(key) == null) {LOG.error("missing amenity: "+booktamenity ); return "";}
		return AmenitiesMap.get(key);
	}
	
	
	
	private void updateCreatePropertyFees(Property property, SqlSession sqlSession, String bpproductid, Date version) {
		ArrayOfFee fees = property.getFees().getValue();
		String pricename;
		for (Fee fee : fees.getFee()) {
			
			Price price = new Price();
			Double quantity = fee.getDefaultQty().doubleValue();
			price.setEntitytype(quantity == 0 ? NameId.Type.Feature.name() : NameId.Type.Mandatory.name());
			price.setEntityid(bpproductid);
			price.setPartyid(getAltpartyid());
			LOG.debug("Name:"+fee.getName().getValue()+"size :"+fee.getName().getValue().length()); 
			pricename= fee.getName().getValue();
			price.setName(pricename.substring(0, Math.min(49,pricename.length())));
			price.setAltid(String.valueOf(fee.id));
			price.setType(fee.getType().getValue());
			price.setQuantity(quantity == null ? 0.0 : quantity);
			price.setUnit(Unit.DAY);
			price.setCurrency(getCurrencyCode(fee.getCurrency().getValue())); //Bookt does not use ISO currency codes

			boolean nostart = fee.getStartDate().toXMLFormat().equalsIgnoreCase("0001-01-01T00:00:00");
			boolean noend = fee.getEndDate().toXMLFormat().equalsIgnoreCase("0001-01-01T00:00:00");
			price.setDate(nostart ? new Date(100,0,1) : fee.getStartDate().toGregorianCalendar().getTime());
			price.setTodate(noend ? new Date(150, 0, 1) : fee.getEndDate().toGregorianCalendar().getTime());

			
			Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
			if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
			else {price = exists;}

			price.setAltpartyid(getAltpartyid());
			price.setAltid(String.valueOf(fee.getID()));
			price.setValue(fee.getAmount().doubleValue());
			price.setMinimum(0.0);
			price.setFactor(1.0);
			price.setRule(getRule(fee));
			price.setState(Price.CREATED);
			price.setVersion(version);
			sqlSession.getMapper(PriceMapper.class).update(price);
			sqlSession.getMapper(PriceMapper.class).cancelversion(price);
//			LOG.debug("Price " + price);
		}	
	}

	private void updatePropertyTaxes(Property property, SqlSession sqlSession, Date version, String productid) {
		ArrayOfTax taxes = property.getTaxes().getValue();
		for (Tax alttax : taxes.getTax()) {
			net.cbtltd.shared.Tax tax = new net.cbtltd.shared.Tax();
			tax.setAccountid(Account.TAX_CONTROL);
			tax.setPartyid(getAltpartyid());
			tax.setName(alttax.getName().getValue());
			tax.setType(Price.TAX_EXCLUDED);
			tax.setDate(new Date(100,0,1));
			net.cbtltd.shared.Tax exists = sqlSession.getMapper(TaxMapper.class).exists(tax);
			if (exists == null) {sqlSession.getMapper(TaxMapper.class).create(tax);}
			else {tax = exists;}
			tax.setState(Price.CREATED);
			tax.setAmount(alttax.getPercentage().doubleValue()*100.0);
			tax.setThreshold(0);
			tax.setNotes(alttax.getNotes().getValue());
			tax.setVersion(version);
			sqlSession.getMapper(TaxMapper.class).update(tax);
			RelationService.replace(sqlSession, Relation.PRODUCT_TAX, productid, tax.getId());
			sqlSession.getMapper(TaxMapper.class).cancelversion(tax);
		}
		
	}

	/*
	 * @return pricing rule based on Bookt Fee parameter
	 * @param fee a Bookt Fee parameter
	 */
	private String getRule(Fee fee) {
		if (fee.isCalculatePerAdult()) {return Price.CalculatePerAdult;}
		else if (fee.isCalculatePerChild()) {return Price.CalculatePerChild;}
		else if (fee.isCalculatePerNight()) {return Price.CalculatePerNight;}
		else if (fee.isCalculatePerPerson()) {return Price.CalculatePerPerson;}
		else if (fee.isIsFlatRate()) {return Price.FlatRate;}
		return Price.FlatRate;
	}

	/**
	 * Read Bookt reservation schedule and update calendar.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule ";
		final String RATETYPE = "";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist(getAltpartyid());
			for (Product product : products) {
				Date startDate = Time.getDateStart();
				Date endDate = Time.addDuration(startDate, 500, Time.DAY);
				Integer propertyid = Integer.valueOf(product.getAltid());
				
				
				ArrayOfKeyValueOfdateTimeint rs = getPort().getAvailability(getApikey(), propertyid, RATETYPE, getXGC(startDate), getXGC(endDate));
				List<KeyValueOfdateTimeint> availability = rs.getKeyValueOfdateTimeint();
				Date fromdate = null;
				Date todate = null;
				
			     
				for (KeyValueOfdateTimeint available : availability) {
					//0=not available
					if (fromdate == null && available.value == 0) {fromdate = getDateTime(available.key);}
					todate = getDateTime(available.key);
					if (fromdate != null && available.value > 0) {
						PartnerService.createSchedule(sqlSession, product, fromdate, todate, timestamp);
						fromdate = null;
						todate = null;
					}
				}
				if (fromdate != null) {PartnerService.createSchedule(sqlSession, product, fromdate, todate, timestamp);}
				PartnerService.cancelSchedule(sqlSession, product, timestamp);
				sqlSession.commit();
				//wait(getSchedulewait()); change this to use mod function. 
			}
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read accommodation special offers.
	 *
	 * Not yet implemented in the Kigo API.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		String message = "readSpecials ";
		throw new ServiceException(Error.service_absent, message); //TODO:
	}

	/**
	 * Gets the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param booking the booking
	 * @return the reservation
	 */
	private Reservation getReservation(SqlSession sqlSession, Booking booking) {
		Reservation reservation = new Reservation();
		//	reservation.setId(String.valueOf(booking.getAltID()));
		reservation.setAltid(String.valueOf(booking.getID()));
		reservation.setAdult(booking.getNumAdults());
		Party agent = getParty(sqlSession, booking.getBookedBy().getValue(), Party.Type.Agent);
		reservation.setAgentid(agent == null ? null : agent.getId());
		reservation.setAltpartyid(getAltpartyid());
		reservation.setArrivaltime("14:00");
		reservation.setChild(booking.getNumChildren());
		Party customer = getParty(sqlSession, booking.getRenter().getValue(), Party.Type.Customer);
		reservation.setCustomerid(customer == null ? null : customer.getId());
		reservation.setDate(reservation.getDate() == null ? new Date() : reservation.getDate());
		reservation.setDeparturetime("10:00");
		Finance finance = getFinance(sqlSession, booking.getCreditCard().getValue());
		reservation.setFinanceid(finance == null ? null : finance.getId());
		reservation.setFromdate(getDate(booking.getCheckIn()));
		//	reservation.setInfant(infant);
		reservation.setMarket("Bookt");
		reservation.setName(String.valueOf(booking.getID())); //?
		reservation.setNotes(booking.getPrivateNotes().getValue());
		reservation.setOrganizationid(getAltpartyid());
		reservation.setProcess(NameId.Type.Reservation.name());
		Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), booking.getPropertyID().toString()));
		if (product == null) { throw new ServiceException(Error.product_id, booking.getPropertyID().toString() + " is not Bookt property");}
		reservation.setProductid(product.getId());
		Statement statement = booking.getStatement().getValue();
		reservation.setCurrency(statement.getCurrency().getValue());
		reservation.setPrice(statement.getTotal().doubleValue());
		reservation.setQuote(statement.getTotal().doubleValue());
		reservation.setState(Reservation.State.Confirmed.name());
		reservation.setTodate(getDate(booking.getCheckOut()));
		reservation.setUnit(net.cbtltd.shared.Unit.DAY);
		//	booking.getPublicNotes();
		//	booking.getStatus();
		//	booking.getTotalDueNow();
		//	booking.getType();
		//	booking.getUnitID();
		return reservation;
	}

	/**
	 * Gets the party.
	 *
	 * @param sqlSession the current SQL session.
	 * @param person the person
	 * @return the party
	 */
	private final Party getParty(SqlSession sqlSession, Person person, Party.Type type) {
		if (person == null) {return null;}
		Party party = new Party();

		party.setPostaladdress(person.getAddress1().getValue()
				+ "\n" + person.getAddress2().getValue()
				+ "\n" + person.getCity().getValue()
				+ "\n" + person.getState().getValue());
		party.setMobilephone(person.getCellPhone().getValue());

		Country country = getCountry(sqlSession, person.getCountry().getValue());
		party.setCountry(country.getId());
		person.getCompany().getValue();
		party.setFaxphone(person.getFaxNumber().getValue());
		party.setName(person.getLastName().getValue(), person.getFirstName().getValue());
		person.getGreeting().getValue();
		party.setNightphone(person.getHomePhone().getValue());
		//TODO: map person.getID().intValue();
		person.getInitial().getValue();
		party.setLatitude(person.getLatitude().doubleValue());
		party.setLongitude(person.getLongitude().doubleValue());
		//TODO: person.getLeadSource().getValue();
		party.setNotes(person.getNotes().getValue());
		party.setPostalcode(person.getPostalCode().getValue());
		//?person.getPrefix().getValue();
		party.setEmailaddress(person.getPrimaryEmail().getValue());
		//?person.getStatus().getValue();
		//?person.getSuffix().getValue();
		Collection<String> tags = person.getTags().getValue().getString();
		//?person.getTitle().getValue();
		//TODO: mapperson.getType().getValue();
		party.setWebaddress(person.getWebSite().getValue());
		party.setDayphone(person.getWorkPhone().getValue());
		//TODO Check if exists
		sqlSession.getMapper(PartyMapper.class).create(party);
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + type.name(), getAltpartyid(), party.getId());		
		return party;
	}

	/**
	 * Gets the person object for the specified party ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partyid the ID of the party.
	 * @return the person object.
	 */
	private static final Person getPerson(SqlSession sqlSession, String partyid) {
		if (partyid == null) {return null;}
		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null) {throw new ServiceException(Error.party_id, partyid);}
		Person person = OF.createPerson();
		person.setAddress1(OF.createPersonAddress1(party.getLocalAddress()));
		//person.setAddress2(OF.createPersonAddress2(party.getPostaladdress(1)));
		person.setAltID(OF.createPersonAltID(party.getId()));
		person.setCellPhone(OF.createPersonCellPhone(party.getMobilephone()));
		person.setCity(OF.createPersonCity(party.getCity()));
		person.setCompany(OF.createPersonCompany(party.getEmployerid()));
		person.setCountry(OF.createPersonCountry(party.getCountry()));
		person.setFaxNumber(OF.createPersonFaxNumber(party.getFaxphone()));
		person.setFirstName(OF.createPersonFirstName(party.noFirstName() ? party.getName() : party.getFirstName()));
		person.setGreeting(OF.createPersonGreeting("Mr"));
		person.setHomePhone(OF.createPersonHomePhone(party.getNightphone()));
		person.setAltID(OF.createString(party.getId()));
		//?person.setInitial(OF.createString(party.getInitial()));
		person.setLastName(OF.createPersonLastName(party.noFamilyName() ? party.getName() : party.getFamilyName()));
		person.setLatitude(getBigDecimal(party.getLatitude()));
		person.setLongitude(getBigDecimal(party.getLongitude()));
		person.setLeadSource(OF.createPersonLeadSource(party.getCreatorid()));
		person.setNotes(OF.createPersonNotes(party.getNotes())); //use text
		person.setPostalCode(OF.createPersonPostalCode(party.getPostalcode()));
		//		person.setPrefix(OF.createPersonPrefix(party.getPrefix()));
		person.setPrimaryEmail(OF.createPersonPrimaryEmail(party.getEmailaddress()));
		//		person.setState(OF.createPersonState(party.getRegion()));
		person.setStatus(OF.createPersonStatus(party.getState()));
		//		person.setSuffix(OF.createPersonSuffix(party.getSuffix()));
		ArrayOfstring tags = OF.createArrayOfstring();
		//TODO tags
		person.setTags(OF.createArrayOfstring(tags));
		person.setTitle(OF.createPersonTitle("Mr"));
		person.setType(OF.createPersonType(party.getType())); //TODO: map
		person.setWebSite(OF.createPersonWebSite(party.getWebaddress()));
		person.setWorkPhone(OF.createPersonWorkPhone(party.getDayphone()));
		return person;
	}

	/**
	 * Gets the credit card object from the specified financial account ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param financeid the ID of the financial account.
	 * @return the credit card object.
	 */
	//private static final CreditCard getCreditCard(SqlSession sqlSession, String financeid) {
	//	Finance finance = sqlSession.getMapper(FinanceMapper.class).read(financeid);
	//	if (finance == null) {return null;}
	//	CreditCard cc = OF.createCreditCard();
	//	cc.setCardNumber(OF.createCreditCardCardNumber(Model.decrypt(finance.getAccountnumber())));
	//	String month = Model.decrypt(finance.getMonth());
	//	String year = Model.decrypt(finance.getYear());
	//	Date expires = new Date(Integer.valueOf(year) + 100, Integer.valueOf(month), 0);
	//	cc.setExpiresOn(getXGC(expires));
	//	cc.setNameOnCard(OF.createCreditCardNameOnCard(finance.getName()));
	//	cc.setSecurityCode(OF.createCreditCardSecurityCode(Model.decrypt(finance.getCode())));
	//	cc.setType(OF.createCreditCardType(finance.getType()));
	//	return cc;
	//}

	/**
	 * Gets the country object from its name.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the country name.
	 * @return the country object.
	 */
	private static final Country getCountry(SqlSession sqlSession, String name) {
		Country country = sqlSession.getMapper(CountryMapper.class).readbyname(name);
		return country == null ? new Country(Country.US_NAME, Country.US) : country;
	}

	/**
	 * Gets the region object from its country code and name.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the country code.
	 * @param name the region name.
	 * @return the region object.
	 */
	private static final Region getRegion(SqlSession sqlSession, String name, String country) {
		Region action = new Region();
		action.setCountry(country);
		action.setName(name);
		Region region = sqlSession.getMapper(RegionMapper.class).readbyname(action);
		if (region == null) {
			action.setName(name.replace("-", " "));
			region = sqlSession.getMapper(RegionMapper.class).readbyname(action);
		}
		return region;
	}

	/**
	 * Gets the financial account for a credit card.
	 *
	 * @param sqlSession the current SQL session.
	 * @param cc the credit card object.
	 * @return the financial account.
	 */
	private static final Finance getFinance(SqlSession sqlSession, CreditCard cc) {
		if (cc == null) {return null;}
		Finance finance = new Finance();
		finance.setAccountnumber(cc.getCardNumber().getValue());
		finance.setMonth(String.valueOf(cc.getExpiresOn().getMonth()));
		finance.setYear(String.valueOf(cc.getExpiresOn().getYear()));
		finance.setName(cc.getNameOnCard().getValue());
		finance.setCode(cc.getSecurityCode().getValue());
		finance.setType(cc.getType().getValue());
		//TODO: check if exists
		sqlSession.getMapper(FinanceMapper.class).create(finance);
		return finance;
	}

	/**
	 * Gets the property rating.
	 *
	 * @param sqlSession the current SQL session.
	 * @param review the review object.
	 * @return the rating object.
	 */
	private final Rate getRate(SqlSession sqlSession, Review review) {
		Rate rate = new Rate();
		rate.setId(review.getAltID().getValue());
		Party customer = getParty(sqlSession, review.getReviewedBy().getValue(), Party.Type.Customer);
		rate.setCustomerid(customer.getId());
		rate.setProductid(review.getReviewedEntityID().getValue());
		rate.setQuantity(review.getRating());
		rate.setType(review.getType().getValue());
		return rate;
	}

	/**
	 * Gets the reservation statement.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation.
	 * @return the statement.
	 */
	private static final Statement getStatement (SqlSession sqlSession, Reservation reservation) {
		Statement statement = null;
		ArrayList<EventJournal> eventjournals = sqlSession.getMapper(EventMapper.class).listbyreservation(reservation.getId());
		if (eventjournals != null && !eventjournals.isEmpty()) {
			statement = OF.createStatement();
			statement.setCurrency(OF.createStatementCurrency(reservation.getCurrency()));
			statement.setDescription(OF.createStatementDescription("TODO"));
			ArrayOfStatementDetail details = OF.createArrayOfStatementDetail();
			List<StatementDetail> sds = details.getStatementDetail();
			StatementDetail sd = OF.createStatementDetail();
			sds.add(sd);
			for (EventJournal eventjournal : eventjournals) {
				sd.setAmount(getBigDecimal(eventjournal.getAmount()));
				sd.setCurrency(OF.createStatementDetailCurrency(eventjournal.getCurrency()));
				sd.setNotes(OF.createStatementDetailNotes(eventjournal.getDescription()));
				sd.setQuantity(getBigDecimal(eventjournal.getQuantity()));
				sd.setType(OF.createStatementDetailType(eventjournal.getProcess())); //TODO: map
			}
			statement.setDetails(OF.createStatementDetails(details));
		}
		return statement;
	}

	/**
	 * Gets the XML Gregorian Calendar date of a Java date.
	 *
	 * @param date the date
	 * @return the XGC
	 */
	protected static final XMLGregorianCalendar getXGC (Date date) {
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH)+1); // +1 because XMLGregorianCalendar is from 1 to 12 while Calendar.MONTH is from 0 to 11 !!!
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		} 
		catch (DatatypeConfigurationException e) {throw new ServiceException(Error.date_format, date.toString());}
		return xgc;
	}

	/**
	 * Gets the XML Gregorian Calendar date of a string date.
	 *
	 * @param xgcstring the string date
	 * @return the XGC
	 */
	protected static final XMLGregorianCalendar getXGC(String xgcstring) { //source in XGC format
		try {return DatatypeFactory.newInstance().newXMLGregorianCalendar(xgcstring);}
		catch (DatatypeConfigurationException e) {throw new ServiceException(Error.date_format, xgcstring);}
	}

	/**
	 * Gets the Java date of an XML Gregorian Calendar date.
	 *
	 * @param xgc the XML Gregorian Calendar date.
	 * @return the Java date.
	 */
	protected static final Date getDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		return xgc.toGregorianCalendar().getTime();
	}

	/**
	 * Gets the date.
	 *
	 * @param xgcstring the xgcstring
	 * @return the date
	 */
	protected static final Date getDate(String xgcstring) {
		return getDate(getXGC(xgcstring));
	}

	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	protected static final String getDate(Date date) {
		if (date == null) {return null;}
		else {return getXGC(date).toXMLFormat();}
	}

	/**
	 * Gets the big integer.
	 *
	 * @param value the value
	 * @return the big integer
	 */
	protected static BigInteger getBigInteger(String value) {
		if (value == null) {return null;}
		else {return new BigInteger(value);}
	}

	/**
	 * Gets the big integer.
	 *
	 * @param value the value
	 * @return the big integer
	 */
	protected static BigInteger getBigInteger(Integer value) {
		if (value == null) {return null;}
		else {return new BigInteger(String.valueOf(value));}
	}

	/**
	 * Gets the integer.
	 *
	 * @param value the value
	 * @return the integer
	 */
	protected static Integer getInteger(BigInteger value) {
		if (value == null) {return null;}
		else {return Integer.valueOf(value.toString());}
	}

	/**
	 * Gets the big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	protected static BigDecimal getBigDecimal(String value) {
		if (value == null) {return null;}
		else {return new BigDecimal(value);}
	}

	/**
	 * Gets the big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	protected static BigDecimal getBigDecimal(Double value) {
		if (value == null) {return null;}
		else {return new BigDecimal(String.valueOf(value));}
	}

	/**
	 * Gets the date time.
	 *
	 * @param xgc the xgc
	 * @return the date time
	 */
	protected static Date getDateTime(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		else {return xgc.toGregorianCalendar().getTime();}
	}

	/**
	 * Gets the time.
	 *
	 * @param xgc the xgc
	 * @return the time
	 */
	protected static String getTime(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		else {return xgc.toGregorianCalendar().getTime().toString();}
	}

	/**
	 * Maps the type to its OTA code and adds it to the property attributes.
	 *
	 *[House, Condo, Villa, Cottage, Detached House, Apartment, Undefined]
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("House","PCT16");
			TYPES.put("Condo","PCT8");
			TYPES.put("Villa","PCT35");
			TYPES.put("Cottage","PCT5");
			TYPES.put("Detached House","PCT16");
			TYPES.put("Apartment","PCT3");
			TYPES.put("Undefined","PCT16");
		}
		if (TYPES.get(type) != null) {attributes.add(TYPES.get(type));}
	}

	private static HashMap<String, Currency.Code> CURRENCY = null;
	/**
	 * Gets the currency code from the currency name.
	 *
	 * @param value the currency name.
	 * @return the currency code.
	 */
	private String getCurrencyCode (String value) {
		if (CURRENCY == null) {
			CURRENCY = new HashMap<String, Currency.Code>();

			CURRENCY.put("NotDefined", Currency.Code.USD);
			CURRENCY.put("UnitedStatesDollar", Currency.Code.USD);
			CURRENCY.put("EuropeanMonetaryUnionEuro", Currency.Code.EUR);
			CURRENCY.put("JapanYen", Currency.Code.JPY);
			CURRENCY.put("MalaysiaRinggit", Currency.Code.MYR);
			CURRENCY.put("MexicoPeso", Currency.Code.MXN);
			CURRENCY.put("NewZealandDollar", Currency.Code.NZD);
			CURRENCY.put("NorwayKrone", Currency.Code.NOK);
			CURRENCY.put("SingaporeDollar", Currency.Code.SGD);
			CURRENCY.put("SouthAfricaRand", Currency.Code.ZAR);
			CURRENCY.put("KoreaSouthWon", Currency.Code.KRW);
			//			CURRENCY.put("SriLankaRupee", Currency.Code.LKR);
			CURRENCY.put("SwedenKrona", Currency.Code.SEK);
			CURRENCY.put("AustraliaDollar", Currency.Code.AUD);
			CURRENCY.put("SwitzerlandFranc", Currency.Code.CHF);
			//			CURRENCY.put("TaiwanNTDollar", Currency.Code.TWD);
			CURRENCY.put("ThailandBaht", Currency.Code.THB);
			CURRENCY.put("UnitedKingdomPound", Currency.Code.GBP);
			//			CURRENCY.put("VenezuelaBolivar", Currency.Code.VEB);
			CURRENCY.put("BrazilReal", Currency.Code.BRL);
			CURRENCY.put("CanadaDollar", Currency.Code.CAD);
			CURRENCY.put("ChinaYuan", Currency.Code.CNY);
			CURRENCY.put("DenmarkKrone", Currency.Code.DKK);
			CURRENCY.put("HongKongDollar", Currency.Code.HKD);
			CURRENCY.put("IndiaRupee", Currency.Code.INR);
			//			CURRENCY.put("EgyptPound", Currency.Code.EGP);
		}
		if (value == null || CURRENCY.get(value) == null) {return Currency.Code.USD.name();}
		else {return CURRENCY.get(value).name();}
	}
	
	/**
	 * Create reservation and payment.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param creditCard credit card for payment processing.
	 * @param product the product to be reserved.
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, 
			net.cbtltd.shared.finance.gateway.CreditCard creditCard) {
				
		
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			Integer productaltid = Integer.valueOf(PartnerService.getProductaltid(sqlSession, reservation));
			Booking rq = OF.createBooking();
			rq.setID(reservation.noAltid() ? null : Integer.valueOf(reservation.getAltid()));
			rq.setAltID(OF.createBookingAltID(reservation.getId()));
			rq.setBookedBy(OF.createBookingBookedBy(getPerson(sqlSession, reservation.getAgentid())));
			rq.setCheckIn(getXGC(reservation.getFromdate()));
			rq.setCheckOut(getXGC(reservation.getTodate()));
		//	rq.setCreditCard(OF.createCreditCard(getCreditCard(sqlSession, reservation.getFinanceid())));
			rq.setNumAdults(reservation.getAdult());
			rq.setNumChildren(reservation.getChild());
			//? TODO booking.setPaymentTerms(value);
			rq.setPrivateNotes(OF.createBookingPrivateNotes(reservation.getNotes()));
			rq.setPropertyID(productaltid);
			rq.setRenter(OF.createBookingRenter(getPerson(sqlSession, reservation.hasCustomerid() ? reservation.getCustomerid() : reservation.getAgentid())));
			rq.setStatement(OF.createBookingStatement(getStatement(sqlSession, reservation)));
			rq.setStatus(OF.createBookingStatus(reservation.getState())); //TODO map
			rq.setTotalDueNow(getBigDecimal(0.0));
			rq.setType(OF.createBookingType(""));
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			context.createMarshaller().marshal(rq, System.out);
			LOG.debug("createReservation rq " + rq);

			Boolean infoOnly = false;
			Booking rs = getPort().makeBooking(getApikey(), rq, infoOnly);
			LOG.debug("createReservation rs " + rs.getID().intValue() + " " + rs);
			context.createMarshaller().marshal(rs, System.out);

			reservation.setAltpartyid(getAltpartyid());
			reservation.setAltid(String.valueOf(rs.getID().intValue()));

			Date version = new Date();
			Price price = new Price();
			price.setEntitytype(NameId.Type.Reservation.name());
			price.setEntityid(reservation.getId());
			sqlSession.getMapper(PriceMapper.class).deletebyexample(price);
			reservation.setQuotedetail(new ArrayList<Price>());

			double totValue = 0.0;
			double totExtra = 0.0;

			Statement statement = rs.getStatement().getValue();
			ArrayOfStatementDetail statementdetails = statement.getDetails().getValue();
			for (StatementDetail statementdetail : statementdetails.getStatementDetail()) {
				price = new Price();
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(reservation.getId());
				price.setCurrency(statementdetail.getCurrency().getValue());
				String name = NameId.trim(Text.stripHTML(statementdetail.getNotes().getValue()), 50);
				price.setName(name);
				price.setType(statementdetail.getType().getValue());
				double quantity = statementdetail.getQuantity().doubleValue();
				price.setQuantity(quantity);
				price.setUnit(Unit.EA);
				price.setState(Price.CREATED);
				price.setRule(Price.Rule.AnyCheckIn.name());
				double amount = statementdetail.getAmount().doubleValue();
				price.setValue(amount);
				price.setVersion(version);
				sqlSession.getMapper(PriceMapper.class).create(price);
				totValue += quantity * amount;
				if (!price.hasType("S")) {totExtra += quantity * amount;}
				reservation.getQuotedetail().add(price);
			}
			reservation.setPrice(totValue + totExtra);
			reservation.setQuote(totValue);
			reservation.setExtra(totExtra);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(reservation.getPrice() * discountfactor);
			Double dueNow = rs.getTotalDueNow().doubleValue();
			Double deposit = dueNow == null ? 0.0 : dueNow * 100 / (totValue + totExtra);
			reservation.setDeposit(deposit.doubleValue());
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		MonitorService.monitor(message, timestamp);
		sqlSession.commit();
		
		return null;
		
	}
	
//https://connect.bookt.com/ws/?method=get&entity=property&apikey=a330c168-6a07-4f04-a357-b72cee626b25&ids=107703&quoteonly=1&checkin=2014-07-15&checkout=2014-07-20	
//using JSON request. 
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL("https://connect.bookt.com/ws/?method=search&entity=property&apikey="+this.getApikey()+"&checkin="+reservation.getFromdate()+"&checkout="+reservation.getTodate());
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
		 
			
		} catch (Throwable e) {
			LOG.error(e.getMessage() + " \n " +e.getStackTrace().toString());
		} finally{
			conn.disconnect();
		}
		
		
		return null;
	}

	public void readDescriptions() {
		//this is done in read proudcts. 
		
	}

	//download single image
	public void readImage(SqlSession sqlSession, String booktpropertyid, Date version){
		try{
				boolean useInternalID = true;
				Property property = getPort().getProperty(getApikey(), booktpropertyid, useInternalID);
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), String.valueOf(property.getID()));
				if (product == null){return;}

				ArrayList<NameId> images = new ArrayList<NameId>();

				ArrayOfMedia arrayofmedia = property.getImages().getValue();
				List<Media> media = arrayofmedia.getMedia();
				for (Media medium : media) {
					images.add(new NameId(medium.getCaption().getValue(), medium.getMediumURL().getValue()));
				}

				LOG.debug("images " + images);
				UploadFileService.uploadImages(sqlSession,NameId.Type.Product, product.getId(), Language.EN,images);

				sqlSession.commit();
			}catch(Throwable e) {LOG.error(e.getMessage());}
		
		
	}
	@Override
	public void readImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		IConnect port = getPort();
		List<Integer> propertyids = port.getPropertyIDs(getApikey(), getXGC(new Date(1,1,1))).getInt();
		Date version = new Date();
		int i = 0;
		try{
			for (Integer propertyid : propertyids) {
				boolean useInternalID = true;
				Property property = port.getProperty(getApikey(), String.valueOf(propertyid), useInternalID);
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), String.valueOf(property.getID()));
				if (product == null) {continue;}

				ArrayList<NameId> images = new ArrayList<NameId>();

				ArrayOfMedia arrayofmedia = property.getImages().getValue();
				List<Media> media = arrayofmedia.getMedia();
				for (Media medium : media) {
					images.add(new NameId(medium.getCaption().getValue(), medium.getMediumURL().getValue()));
				}



				LOG.debug("images " + images);
				UploadFileService.uploadImages(sqlSession,NameId.Type.Product, product.getId(), Language.EN,images);

				sqlSession.commit();
			}
		}catch(Throwable e)
		{LOG.error(e.getMessage());}
	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Bookt inquireReservation()");
	}
}
