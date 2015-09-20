/**
 * 
 */
package net.cbtltd.rest.ru;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.CancellationItem;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.rest.ru.product.*;
import net.cbtltd.rest.ru.request.Authentication;
import net.cbtltd.rest.ru.request.CancelReservation;
import net.cbtltd.rest.ru.request.GetLocationDetails;
import net.cbtltd.rest.ru.request.GetPropertyAvbPrice;
import net.cbtltd.rest.ru.request.ListOwnerProp;
import net.cbtltd.rest.ru.request.ListPropByCreationDate;
import net.cbtltd.rest.ru.request.ListPropertyChangeLog;
import net.cbtltd.rest.ru.request.ListSpecProp;
import net.cbtltd.rest.ru.request.PutConfirmedReservations;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentMethodMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentMethod;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.registration.RegistrationConstants;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * API handler for RU (Rentals United) PMS
 * Uses XML
 * 
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat FF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat TF = new SimpleDateFormat("hh:mm:ss");
	private static final String HANDLER_URL = "http://rm.rentalsunited.com/api/Handler.ashx";
	private static final String USERNAME = "alex@mybookingpal.com";
	private static final String PASSWORD = "jksqfiul";
	private static HashMap<String, Double> RU_PRICE = new HashMap<String, Double>();

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler(Partner partner) {	super(partner);	}

	/**
	 * Sends XML POST request
	 * 
	 * @param rq - string request
	 */
	private static String getConnection(String rq) throws Throwable {
		String xmlString = "";
		HttpURLConnection connection = null;
		long start = System.currentTimeMillis();
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
			long result = System.currentTimeMillis() - start;
			LOG.debug("Connection to RU was closed, time spent: " + result + " milliseconds, response: HTTP " + connection.getResponseCode());
		}
		return xmlString;
		}

	/**
	 * Returns if the property is available for the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		String message = "RentalsUnited isAvailable " + reservation.getId();
		LOG.debug(message);
		boolean isAvailable = false;
		try {
			SAXBuilder builder = new SAXBuilder();
			GetPropertyAvbPrice request = new GetPropertyAvbPrice();
			request.setPropertyID(PartnerService.getProductaltid(sqlSession, reservation));
			request.setDateFrom(format(reservation.getFromdate()));
			request.setDateTo(format(reservation.getTodate()));
			request.setNOP(reservation.getAdult());
			request.setAuthentication(setAuth());
			
			String rs = getConnection(toXML(request));
			Document resp = builder.build(new StringReader(rs));
			
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {isAvailable = true;}
			else {isAvailable = false;}
			message = resp.getRootElement().getChild("Status").getText();
		}
		catch (Throwable x) {
			isAvailable = false;
			LOG.error(x.getMessage());
			}
		LOG.debug("\nisAvailable=" + isAvailable + "Status: " + message);		
		return isAvailable;
	}
	
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "createReservation " + reservation.getId() ;
		LOG.debug(message);
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}
			
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) {reservation.setCustomerid(Party.NO_ACTOR);}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}
			
			SAXBuilder builder = new SAXBuilder();

/*			//compare prices, if doesn't match throw exception
			//get old price
			double reservationQuote = reservation.getQuote();
			computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency());
			if(reservationQuote != reservation.getQuote()) {
				throw new ServiceException(Error.price_not_match, "old: " + reservationQuote + ", new: " + reservation.getQuote());
			}

			//send request for live price without any currency specifications. Price will be returned in property currency.
			net.cbtltd.rest.ru.response.GetPropertyAvbPrice prices = getAvbPrice(sqlSession, reservation, product.getAltid());
			//store price info
			PropertyPrice finalprice = prices.getPropertyPrices().getPropertyPrice().get(0);
			if (prices.getPropertyPrices().getPropertyPrice().size() > 1) {
				for (PropertyPrice price : prices.getPropertyPrices().getPropertyPrice()) {
				if (price.getNOP() == reservation.getAdult()) {finalprice = price;}//check number of guests and receive price for this number
				}
			}*/

			Costs cost = new Costs();
			StayInfo stayInfo = new StayInfo();
			StayInfos stayInfos = new StayInfos();
			CustomerInfo customerInfo = new CustomerInfo();
			PutConfirmedReservations rq = new PutConfirmedReservations();
			net.cbtltd.rest.ru.Reservation reserv = new net.cbtltd.rest.ru.Reservation();
			rq.setAuthentication(setAuth());
			rq.setReservation(reserv);
			
			stayInfos.setStayInfo(stayInfo);
			
			Double totalPrice = reservation.getQuote();
			// double ruPrice = RU_PRICE.get(reservation.getId());
			double ruPrice = 0.0;

			net.cbtltd.rest.ru.response.GetPropertyAvbPrice prices = getAvbPrice(sqlSession, reservation, product.getAltid());

			PropertyPrice finalprice = prices.getPropertyPrices().getPropertyPrice().get(0);
			if (prices.getPropertyPrices().getPropertyPrice().size() > 1) {
				for (PropertyPrice price : prices.getPropertyPrices().getPropertyPrice()) {
				if (price.getNOP() == reservation.getAdult()) {finalprice = price;}//check number of guests and receive price for this number
				}
			}

			ruPrice = finalprice.price;
			cost.setRUPrice(ruPrice);
			cost.setClientPrice(totalPrice);
			cost.setAlreadyPaid(totalPrice);
			stayInfo.setCosts(cost);
			stayInfo.setPropertyID(Integer.parseInt(product.getAltid()));
			stayInfo.setNumberOfGuests(reservation.getAdult());
			stayInfo.setDateTo(DF.format(reservation.getTodate()));
			stayInfo.setDateFrom(DF.format(reservation.getFromdate()));
			String[] naming = customer.getName().split(", ");
			customerInfo.setSurName(naming[0]);
			customerInfo.setName(naming[1]);
//			customerInfo.setSurName(customer.getExtraname());
			customerInfo.setEmail(customer.getEmailaddress());
			customerInfo.setZipCode(customer.getPostalcode());
			customerInfo.setAddress(customer.getPostaladdress());
			customerInfo.setPhone(customer.getDayphone());
//			customerInfo.setSkypeID(skypeID);
//			customerInfo.setCountryID(customer.getCountry());
			reserv.setComments(reservation.getNotes());
			reserv.setStayInfos(stayInfos);
			reserv.setCustomerInfo(customerInfo);
//			reserv.setCreditCard();
//			reserv.setReservationID();
			
			String rs = getConnection(toXML(rq));
			Document resp = builder.build(new StringReader(rs));
			message = resp.getRootElement().getChild("Status").getText();
			String reservationAltID = resp.getRootElement().getChild("ReservationID").getText();
			if (resp.getRootElement().getChild("Status").getAttributeValue("ID").equals("0")) {
				reservation.setAltid(reservationAltID);
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(message);
				}
			else {throw new ServiceException(Error.reservation_api, message);}
			LOG.debug("Reservation created, ID: " + reservationAltID + ", stsus: " + message);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
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
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			SAXBuilder builder = new SAXBuilder();
			CancelReservation rq = new CancelReservation();
			rq.setAuthentication(setAuth());
			rq.setReservationID(reservation.getAltid());
			
			String rs = getConnection(toXML(rq));
			
			Document response = builder.build(new StringReader(rs));
			if (!response.getRootElement().getChild("Status").getAttributeValue("ID").equalsIgnoreCase("0")) {
				throw new ServiceException(Error.reservation_api, response.getRootElement().getChildText("Status"));
			}
		}
		catch (Throwable x) {reservation.setMessage(x.getMessage());}
	}
	
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency)  {
		try {
			return computePrice(sqlSession, reservation, productAltId, currency);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, "was occured during RU computePrice method. Detailed message: " + e.getMessage());
		}
	}
	
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productaltid, String currency) throws Throwable {
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		
		net.cbtltd.rest.ru.response.GetPropertyAvbPrice prices = getAvbPrice(sqlSession, reservation, productaltid);
		
		PropertyPrice finalprice = prices.getPropertyPrices().getPropertyPrice().get(0);
		if (prices.getPropertyPrices().getPropertyPrice().size() > 1) {
			for (PropertyPrice price : prices.getPropertyPrices().getPropertyPrice()) {
			if (price.getNOP() == reservation.getAdult()) {finalprice = price;}//check number of guests and receive price for this number
			}
		}

		QuoteDetail quoteDetail;
		reservation.setExtra(0.0);
		reservation.setCurrency(currency);//check
		reservationPrice.setCurrency(currency);//ok
		double total = finalprice.getPrice();
		double deposit = finalprice.getDeposit();
		double price = total - finalprice.getCleaning() - finalprice.getExtraPersonPrice();
		RU_PRICE.put(reservation.getId(), total);
		
		if (currency.equalsIgnoreCase(product.getCurrency())) {
			if (finalprice.getCleaning() != 0) {
				quoteDetail = new QuoteDetail(String.valueOf(finalprice.getCleaning()), currency, "Cleaning fee", "Included in the price", "", false);
				quoteDetails.add(quoteDetail);
			}
			if (finalprice.getExtraPersonPrice() != 0) {
				double extra = finalprice.getExtraPersonPrice();
				quoteDetail = new QuoteDetail(String.valueOf(extra), currency, "Extra person fee", "Included in the price", "", false);
				reservation.setExtra(extra);//ok
				quoteDetails.add(quoteDetail);
			}
			if (finalprice.getSecurityDeposit() != 0) {
				quoteDetail = new QuoteDetail(String.valueOf(finalprice.getSecurityDeposit()), currency, "Refundable security deposit", "paid by the client directly to the property owner on arrival", "", false);
				quoteDetails.add(quoteDetail);
			}
			reservation.setPrice(price);//ok
			reservation.setQuote(total);//ok
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(reservation.getPrice() * discountfactor);//ok
			reservation.setDeposit(deposit);
	
			reservationPrice.setTotal(reservation.getQuote());
			reservationPrice.setQuoteDetails(quoteDetails);
		} else {
			String prodCurr = product.getCurrency();
			if (finalprice.getCleaning() != 0) {
				double cleaning = PaymentService.convertCurrency(sqlSession, prodCurr, currency, finalprice.getCleaning());
				double cleaninground = PaymentHelper.getAmountWithTwoDecimals(cleaning);					
				quoteDetail = new QuoteDetail(String.valueOf(cleaninground), currency, "Cleaning fee", "Included in the price", "", false);
				quoteDetails.add(quoteDetail);
			}
			if (finalprice.getExtraPersonPrice() != 0) {					
				double extra = PaymentService.convertCurrency(sqlSession, prodCurr, currency, finalprice.getExtraPersonPrice());
				double extraround = PaymentHelper.getAmountWithTwoDecimals(extra);
				quoteDetail = new QuoteDetail(String.valueOf(extraround), currency, "Extra person fee", "Included in the price", "", false);
				reservation.setExtra(extraround);//ok
				quoteDetails.add(quoteDetail);
			}
			if (finalprice.getSecurityDeposit() != 0) {
				quoteDetail = new QuoteDetail(String.valueOf(finalprice.getSecurityDeposit()), prodCurr, "Refundable security deposit", "paid by the client directly to the property owner on arrival", "", false);
				quoteDetails.add(quoteDetail);
			}
/*			if (finalprice.getSecurityDeposit() != 0) {
				double securityDeposit = PaymentService.convertCurrency(sqlSession, prodCurr, currency, finalprice.getSecurityDeposit());
				double securityDepositround = PaymentHelper.getAmountWithTwoDecimals(securityDeposit);					
				quoteDetail = new QuoteDetail(String.valueOf(securityDepositround), null, "Refundable security deposit", "paid by the client directly to the property owner on arrival", "", false);
				quoteDetails.add(quoteDetail);
			}*/
			price = PaymentService.convertCurrency(sqlSession, prodCurr, currency, price);
			double priceround = PaymentHelper.getAmountWithTwoDecimals(price);
			total = PaymentService.convertCurrency(sqlSession, prodCurr, currency, total);
			double totalround = PaymentHelper.getAmountWithTwoDecimals(total);
			deposit = PaymentService.convertCurrency(sqlSession, prodCurr, currency, deposit);
			double depositround = PaymentHelper.getAmountWithTwoDecimals(deposit);
			reservation.setPrice(priceround);//ok
			reservation.setQuote(totalround);//ok
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(reservation.getPrice() * discountfactor);//ok
			reservation.setDeposit(depositround);

			reservationPrice.setTotal(reservation.getQuote());
			reservationPrice.setQuoteDetails(quoteDetails);
		}
		reservationPrice.setPrice(price);
		
//		LOG.debug("computePrice " + reservation.getQuotedetail());
		return reservationPrice;
	}
	
	/**
	 * Return price info in property currency.
	 * 
	 * @param sqlSession
	 * @param reservation
	 * @param productaltid
	 * @return
	 */
	private net.cbtltd.rest.ru.response.GetPropertyAvbPrice getAvbPrice (SqlSession sqlSession, Reservation reservation, String productaltid){
		
		try{
			GetPropertyAvbPrice request = new GetPropertyAvbPrice();
			request.setAuthentication(setAuth());
			request.setDateFrom(DF.format(reservation.getFromdate()));
			request.setDateTo(DF.format(reservation.getTodate()));
			request.setNOP(reservation.getAdult());
			request.setPropertyID(productaltid);
			
			String rs = getConnection(toXML(request));
			
			JAXBContext jc = JAXBContext.newInstance(net.cbtltd.rest.ru.response.GetPropertyAvbPrice.class);
			Unmarshaller ju = jc.createUnmarshaller();
			net.cbtltd.rest.ru.response.GetPropertyAvbPrice prices;
			try {
				prices = (net.cbtltd.rest.ru.response.GetPropertyAvbPrice) ju.unmarshal(new StringReader(rs));
			} catch (UnmarshalException e) {
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(new ByteArrayInputStream(rs.getBytes("utf-8")));
				String text = document.getRootElement().getText();
				throw new ServiceException(Error.price_missing, text);
			}
			
			if (prices.getStatus().getID() != 0) {
				throw new ServiceException(Error.reservation_api, "was occured during RU computePrice method. Detailed message: " + prices.getStatus().getValue());
			}
			
			return prices;
			
		}catch (Throwable e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Read locations from RU system.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public synchronized void readLocations() {
		String message = "Rental United readLocations " + getAltpartyid();
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			File file = new File("src/resources/ru/DuplicateLocations.txt");
			FileOutputStream fop = new FileOutputStream(file);
//			if (!file.exists()) file.createNewFile();
			StringBuilder sb = new StringBuilder();
			SAXBuilder builder = new SAXBuilder();
			//get xml document and parse it
			File xmlFile = new File("war/WEB-INF/classes/net/cbtltd/rest/ru/xml/Locations.xml");
			Document document = builder.build(xmlFile);
			List<Element> locations = document.getRootElement().getChildren();
			for (Element location : locations) {
				try{
					//Check if it is a city
					if (!location.getAttributeValue("LocationTypeID").equals("4")) continue;
					String coderentalsunited = location.getAttributeValue("LocationID");
					if (getLocation(sqlSession, coderentalsunited) != null) {continue;}
					String locName = location.getValue();

					//create XML RQ for detailed location info
					GetLocationDetails request = new GetLocationDetails();
					request.setLocationid(Integer.valueOf(coderentalsunited));
					request.setAuthentication(setAuth());

					String rs = getConnection(toXML(request));
					
					//get country
					String countryname = null;
					String countrycode = null;
					Document doc = builder.build(new StringReader(rs));
					List<Element> locs = doc.getRootElement().getChild("Locations").getChildren();
					for (Element loc : locs) {
						//get countrycode
						if (loc.getAttributeValue("LocationTypeID").equals("2")) {
							countryname = loc.getValue();
							countrycode = getCountrycode(sqlSession, countryname);
							break;
						}
					}
					//set coderentalsunited and check if location already exists
					if (countrycode != null){
						setLocation(sqlSession, sb, locName, null, countrycode, coderentalsunited);
					} else {LOG.error("Sorry, no countrycode was found for country: " + countryname);}
					//session commits in setLocation method
				}
				catch (Throwable x) {
					x.printStackTrace();
					LOG.error("Location " + location.getValue() + " error: " + x.getMessage());
					}
			}
			byte[] contentInBytes = sb.toString().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		}
		catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}		
	}
	
	/**
	 * Checks and returns country code for give country name
	 * 
	 * @param sqlSession
	 * @param countryname
	 * @return country code
	 */
	private final String getCountrycode(SqlSession sqlSession, String countryname){
		if (countryname.equals("Bosnia")) {return "BA";}
		if (countryname.equals("Macedonia")) {return "MK";}
		if (countryname.equals("Russia")) {return "RU";}
		if (countryname.equals("Serbia")) {return "RS";}
		if (countryname.equals("Montenegro")) {return "ME";}
		if (countryname.equals("South Korea")) {return "KR";}
		else {
			return sqlSession.getMapper(CountryMapper.class).idbyname(countryname);
		}
	}
	
	/**
	 * Checks and creates the location from its name, region and country if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of current location
	 * @param region the name of current location region (currently only for US)
	 * @param countrycode the current country
	 * @param coderentalsunited the unique Rentals United code for current location
	 * @return the location object
	 */
	private final void setLocation(SqlSession sqlSession, StringBuilder sb, String name, String region, String countrycode, String coderentalsunited) {
		Location location = PartnerService.getLocation(sqlSession, name, region, countrycode);
		if (org.apache.commons.lang.StringUtils.isEmpty(location.getCoderentalsunited())) {
			sb.append(coderentalsunited + ":" + location.getCoderentalsunited() + "\n");
		}
		location.setCoderentalsunited(coderentalsunited);
		sqlSession.getMapper(LocationMapper.class).update(location);
		sqlSession.commit();
		LOG.debug("Location updated: " + location);
	}
	
	/**
	 * Gets the location by coderentalsunited.
	 *
	 * @param sqlSession the current SQL session.
	 * @param coderentalsunited the place code.
	 * @return the location object.
	 */
	private static Location getLocation(SqlSession sqlSession, String coderentalsunited) {
		Location action = new Location();
		action.setCoderentalsunited(coderentalsunited);
//		action.setState(Location.CREATED);
		action = sqlSession.getMapper(LocationMapper.class).rentalsunitedSearch(action);
		return action;
	}

	/**
	 * Read accommodation alerts.
	 *
	 * Not available in the RU API.
	 * 
	 * @param sqlSession the current SQL session.
	 */
	@Override
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "RentalsUnited readAlerts()");	
	}

	/**
	 * Read accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	@Override
	public synchronized void readPrices() {
		String altPartyID = getAltpartyid();
		String message = "Rentals United readPrices " + altPartyID;
		Calendar nowDate = Calendar.getInstance();
		Date version = new Date();
		LOG.debug(message);
		String rs;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			nowDate.add(Calendar.YEAR, +1);
			String fromDate = DF.format(version);
			String toDate = DF.format(nowDate.getTime());
			ChangeLog changeLog;

			int i = 0;
			int j = 0;
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist(altPartyID);
			for (Product product : products) {
				String productid = product.getId();
				String altid = product.getAltid();
				//Checking actual date of last made change
				changeLog = getChangeLog(product.getAltid());
				if (changeLog == null) {continue;}
				String s = RelationService.find(sqlSession, Downloaded.PRODUCT_PRICE_VERSION, productid, null); 
				if (s == null || FF.parse(changeLog.getPricing()).after(FF.parse(s))) {
					try {
						String supplierID = product.getSupplierid();
						String currency = product.getCurrency();
						Price action;
	/*					if (product.getCleaningfee() instanceof Double) {
							net.cbtltd.shared.Price mandatory = new net.cbtltd.shared.Price();
							
							mandatory.setEntitytype(NameId.Type.Mandatory.name());
							mandatory.setEntityid(productid);
							mandatory.setPartyid(altPartyID);
							mandatory.setName("Cleaning Price");
							mandatory.setType(Price.MANDATORY);
							mandatory.setDate(version);
							mandatory.setTodate(nowDate.getTime());
								mandatory.setCurrency(currency);
							mandatory.setQuantity(0.0);
							mandatory.setUnit(Unit.EA);
							
							exists = sqlSession.getMapper(PriceMapper.class).exists(mandatory);
							if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(mandatory);}
							else {mandatory = exists;}
							
							Double mandatoryPrice = product.getCleaningfee();
							mandatory.setValue(mandatoryPrice);
							mandatory.setCost(mandatoryPrice * (1.0 - getDiscount() / 100.0));
							mandatory.setMinimum(0.0);
							mandatory.setAvailable(1);
							mandatory.setState(net.cbtltd.shared.Price.CREATED);
							mandatory.setRule(Price.Rule.FixedRate.name());
							mandatory.setVersion(version);
							sqlSession.getMapper(PriceMapper.class).update(mandatory);
						}*/
						
						net.cbtltd.rest.ru.request.ListPropertyPrices request = new net.cbtltd.rest.ru.request.ListPropertyPrices();
						request.setPropertyID(Integer.valueOf(altid));
						request.setAuthentication(setAuth());
						request.setDateFrom(fromDate);
						request.setDateTo(toDate);
						
						rs = getConnection(toXML(request));
		
						JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyPrices.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						net.cbtltd.rest.ru.response.ListPropertyPrices prices = (net.cbtltd.rest.ru.response.ListPropertyPrices) jaxbUnmarshaller.unmarshal(new StringReader(rs));
						for (Season seasonprice : prices.getPrices().getSeason()){
							action = new Price();
							
							action.setPartyid(supplierID);
							action.setEntitytype(NameId.Type.Product.name());
							action.setEntityid(productid);
							action.setCurrency(currency);
							action.setQuantity(1.0);
							action.setUnit(Unit.DAY);
							action.setName(Price.RACK_RATE);
							action.setType(NameId.Type.Reservation.name());
							action.setDate(DF.parse(seasonprice.getDateFrom()));
							action.setTodate(DF.parse(seasonprice.getDateTo()));
							
							Price exists = sqlSession.getMapper(PriceMapper.class).exists(action);
							if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(action);}
							else {action = exists;}
							
							action.setState(Price.CREATED);
							action.setRule(Price.Rule.AnyCheckIn.name());
							action.setFactor(1.0);
							Double rentalPrice = seasonprice.getPrice();
							action.setValue(rentalPrice);
							action.setMinimum(rentalPrice);
							action.setCost(rentalPrice * (1.0 - getDiscount() / 100.0));
							action.setVersion(version);
							action.setAvailable(1);
							sqlSession.getMapper(PriceMapper.class).update(action);
							
							//TODO: add YIELD for extra persons
							
							LOG.debug(i++ + " Price for product: " + productid + ", altid: " + altid + " with value: " + rentalPrice + " and currency: " + currency + " from date: " + DF.format(action.getDate()) + " to date: " + DF.format(action.getTodate()));
						}
						
						//upload discounts
						net.cbtltd.rest.ru.request.ListPropertyDiscounts rq = new net.cbtltd.rest.ru.request.ListPropertyDiscounts();
						rq.setPropertyID(Integer.valueOf(altid));
						rq.setAuthentication(setAuth());
						
						rs = getConnection(toXML(rq));
		
						jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyDiscounts.class);
						jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						net.cbtltd.rest.ru.response.ListPropertyDiscounts discounts = (net.cbtltd.rest.ru.response.ListPropertyDiscounts) jaxbUnmarshaller.unmarshal(new StringReader(rs));
						Yield yield;
						for (LongStay longStay : discounts.getDiscounts().getLongStays()){
							double amount = longStay.getValue();
							yield = new Yield(NameId.Type.Product.name(), productid, DF.parse(longStay.getDateFrom()), DF.parse(longStay.getDateTo()));
							yield.setName(Yield.LENGTH_OF_STAY);
							yield.setState(Yield.CREATED);
							yield.setModifier(Yield.DECREASE_PERCENT);
							//cycle for each parameter
							for (int param = longStay.getBigger(); param < longStay.getSmaller(); param++){
								yield.setParam(param);
								Yield exists = sqlSession.getMapper(YieldMapper.class).exists(yield);
								if (exists == null) {sqlSession.getMapper(YieldMapper.class).create(yield);}
								else {yield = exists;}
								yield.setAmount(amount);
								sqlSession.getMapper(YieldMapper.class).update(yield);
								LOG.debug(j++ + " Long stay discount for product: " + productid + ", altid: " + altid + " with value: " + amount + " from date: " + DF.format(yield.getFromdate()) + " to date: " + DF.format(yield.getTodate()));
							}
						}
						for (LastMinute lastMinute : discounts.getDiscounts().getLastMinutes()){
							double amount = lastMinute.getValue();
							yield = new Yield(NameId.Type.Product.name(), productid, DF.parse(lastMinute.getDateFrom()), DF.parse(lastMinute.getDateTo()));
							yield.setName(Yield.LAST_MINUTE);
							yield.setState(Yield.CREATED);
							yield.setModifier(Yield.DECREASE_PERCENT);
							//cycle for each parameter
							for (int param = lastMinute.getDaysToArrivalFrom(); param < lastMinute.getDaysToArrivalTo(); param++){
								yield.setParam(param);
								Yield exists = sqlSession.getMapper(YieldMapper.class).exists(yield);
								if (exists == null) {sqlSession.getMapper(YieldMapper.class).create(yield);}
								else {yield = exists;}
								yield.setAmount(amount);
								sqlSession.getMapper(YieldMapper.class).update(yield);
								LOG.debug(j++ + " Last minute discount for product: " + productid + ", altid: " + altid + " with value: " + amount + " from date: " + DF.format(yield.getFromdate()) + " to date: " + DF.format(yield.getTodate()));
							}
						}
						action = new Price();
						action.setCurrency(currency);
						action.setPartyid(supplierID);
						action.setEntitytype(NameId.Type.Product.name());
						sqlSession.getMapper(PriceMapper.class).cancelversion(action);
						
						RelationService.load(sqlSession, Downloaded.PRODUCT_PRICE_VERSION, productid, FF.format(version));
						sqlSession.commit();
					}
					catch (Throwable x) {
						LOG.error("Error: " + x.getMessage());
					}
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
		}
	}
	
	private ChangeLog getChangeLog(String altid){
		ChangeLog changeLog = null;
		try {
			ListPropertyChangeLog propertyChangeLog = new ListPropertyChangeLog();
			propertyChangeLog.setAuthentication(setAuth());
			propertyChangeLog.setPropertyID(Integer.valueOf(altid));
	
			//send request for log changes
			String rs = getConnection(toXML(propertyChangeLog));
			JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyChangeLog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			net.cbtltd.rest.ru.response.ListPropertyChangeLog res = (net.cbtltd.rest.ru.response.ListPropertyChangeLog) jaxbUnmarshaller.unmarshal(new StringReader(rs));
	
			changeLog = res.getChangeLog();
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error(x.getMessage());
		}
		return changeLog;
	}

	/**
	 * Update for uploaded products.
	 * Works only for uploaded properties.
	 */
	public synchronized void updateProducts() {
		String altPartyID = getAltpartyid();
		String message = "Rentals United updateProducts " + altPartyID;
		LOG.debug(message);
		Date version = new Date();

		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, altPartyID, new Date().toString());
			
			//Retrieve list of all product IDs in our database.
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist(altPartyID);
			
			//Run loop for each product from list
			Property property = null;
			//boolean check;
			StringBuilder sb = new StringBuilder();
			ArrayList<String> languages = new ArrayList<String>();
			ArrayList<String> lostamenities = new ArrayList<String>();
			for (Product product : products) {
				try {
					String altid = product.getAltid();
					String productid = product.getId();
					
					ListPropertyChangeLog propertyChangeLog = new ListPropertyChangeLog();
					propertyChangeLog.setAuthentication(setAuth());
					propertyChangeLog.setPropertyID(Integer.valueOf(altid));
		
					//send request for log changes
					String rs = getConnection(toXML(propertyChangeLog));
					JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyChangeLog.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					net.cbtltd.rest.ru.response.ListPropertyChangeLog res = (net.cbtltd.rest.ru.response.ListPropertyChangeLog) jaxbUnmarshaller.unmarshal(new StringReader(rs));
	
					ChangeLog changeLog = res.getChangeLog();
					Date propVersion = product.getVersion();
	
					//check dates and update if versions different
					if (FF.parse(changeLog.getStaticData()).after(propVersion)) {
						if (property == null){property = getProperty(altid);}
						if (property != null){
							updateProductStaticData(sqlSession, product, property, sb, lostamenities, version);
						}
					}
					String s = RelationService.find(sqlSession, Downloaded.PRODUCT_IMAGE_VERSION, productid, null); 
					if (s == null || FF.parse(changeLog.getImage()).after(FF.parse(s))) {
						if (property == null){property = getProperty(altid);}
						if (property != null){
							updateProductImage(sqlSession, property, productid, version);
						}
					}
					s = RelationService.find(sqlSession, Downloaded.PRODUCT_DESCRIPTION_VERSION, productid, null);
					if (s == null || FF.parse(changeLog.getDescription()).after(FF.parse(s))){
						if (property == null){property = getProperty(altid);}
						if (property != null){
							updateProductDescription(sqlSession, product, property, languages, version);
						}
					}
					sqlSession.commit();
					property = null;
				}
				catch (Throwable x) {
					LOG.error("Error: " + x.getMessage());
				}
			}

			if (sb.length() > 1) LOG.debug("Lost location ids: " + sb.toString());
			if (languages.size() > 1) LOG.debug("Lost language codes: " + languages.toString());
			if (lostamenities.size() > 1) LOG.debug("Lost amenities: " + lostamenities.toString());
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getStackTrace());
		}
		finally {sqlSession.close();}
		
	}
	
	/**
	 * Uploads product static data from xml feed into database
	 */
	private void updateProductStaticData(SqlSession sqlSession, Product product, Property property, StringBuilder sb, ArrayList<String> lostamenities, Date version){
		String altPartyID = getAltpartyid();
		String altid = String.valueOf(property.getID().getValue());
		String message = "Updating property static data for altID: " + altid;
		LOG.debug(message);

		ArrayList<String> amenities = new ArrayList<String>();
		try{
			Integer coderentalsunited = property.getDetailedLocationID();
			Location location = getLocation(sqlSession, String.valueOf(coderentalsunited));
			String lng = property.getCoordinates().getLongitude();
			String lat = property.getCoordinates().getLatitude();
			if (location != null) {
				product.setLocationid(location.getId());
			}
			else if (location == null && isDouble(lat) && isDouble(lng)) {
				String zipcode = property.getZipCode();
				String countryname = null;
				String name = null;
				try {
					GetLocationDetails request = new GetLocationDetails();
					request.setLocationid(coderentalsunited);
					request.setAuthentication(setAuth());

					String rs = getConnection(toXML(request));

					JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.GetLocationDetails.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					net.cbtltd.rest.ru.response.GetLocationDetails details = (net.cbtltd.rest.ru.response.GetLocationDetails) jaxbUnmarshaller.unmarshal(new StringReader(rs));
					
					for (net.cbtltd.rest.ru.Location detailedloc : details.getLocation()){
						if (detailedloc.getLocationtype() == 2) {countryname = detailedloc.getName();}
						if (detailedloc.getLocationtype() == 4) {name = detailedloc.getName();}
					}
				}
				catch (Throwable x) {
					x.printStackTrace();
					LOG.error("Error: " + x.getMessage());
				}
					
				try {
					//need to get location name and country
					location = PartnerService.getLocation(sqlSession, name, null, getCountrycode(sqlSession, countryname), Double.valueOf(lat), Double.valueOf(lng), zipcode);
				} catch (GoogleLocationLimitException g) {LOG.error(g.getStackTrace());}
				
				if (location != null) {
					product.setLocationid(location.getId());
					location.setCoderentalsunited(String.valueOf(coderentalsunited));
					sqlSession.getMapper(LocationMapper.class).update(location);
				}
			}
			if (location == null) {
				LOG.error("No location for Property: " + altid + " with codeRentalsUnited: " + property.getDetailedLocationID());
				sb.append("\nProperty: " + property.getName() + "/" + altid + " location code: " + property.getDetailedLocationID());
				product.setState(Product.SUSPENDED);
			}
			
			if (isDouble(lat)) {product.setLatitude(Double.valueOf(lat));}
			else {product.setState(Product.SUSPENDED);}
			if (isDouble(lng)) {product.setLongitude(Double.valueOf(lng));}
			else {product.setState(Product.SUSPENDED);}
			
			if (!property.isIsActive() || property.getLastMod().isNLA() || property.isIsArchived()) {product.setState(Product.SUSPENDED);}

			CompositionRoomsAmenities roomsAmenities = property.getCompositionRoomsAmenities();
			
			product.setSupplierid(setPmRules(sqlSession, property));
			
			product.setName(property.getName());
//			product.setWebaddress("");
			product.setCurrency(getCurrency(coderentalsunited));
			product.setUnit(Unit.DAY);
			product.setRoom(getNrOfRooms(property.getPropertyTypeID()));
			product.setToilet(0);
			product.setBathroom(0);
			for (CompositionRoomAmenities roomamenities : roomsAmenities.getCompositionRoomAmenities()) {
				if (roomamenities.getCompositionRoomID() == 53){product.setToilet(1);}
				if (roomamenities.getCompositionRoomID() == 81){product.setBathroom(1);}
			}
			product.setQuantity(1);
			product.setPerson(property.getCanSleepMax());
			product.setChild(0);
			product.setInfant(0);
			product.setRating(5);
			product.setCommission(getCommission());
			product.setDiscount(getDiscount());
//			product.setCommission(0.0);
//			product.setDiscount(0.0);					
			product.setRank(getRank());
			product.setDynamicpricingenabled(false);
			
			product.setAltitude(0.0);					
			product.setVersion(version);
			product.setPhysicaladdress(property.getStreet().length() < 100 ? property.getStreet().toString() : null);
//			product.setAssignedtomanager(false);
//			product.setCleaningfee(property.getCleaningPrice());
			product.setCleaningfee(0.0);
			//moved back security deposit. Looks like it is not counts for some properties...
			product.setSecuritydeposit(property.getSecurityDeposit().getDepositValue());
//			product.setSecuritydeposit(0.0);
			
			sqlSession.getMapper(ProductMapper.class).update(product);
			
			
			//adding cleaning fee
			if (property.getCleaningPrice() > 0.0) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MONTH, 0);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				
				net.cbtltd.shared.Price mandatory = new net.cbtltd.shared.Price();
				mandatory.setEntitytype(NameId.Type.Mandatory.name());
				mandatory.setEntityid(product.getId());
				mandatory.setPartyid(product.getSupplierid());
				mandatory.setType("Fees");
				mandatory.setDate(cal.getTime());
				cal.add(Calendar.YEAR, 2);
				mandatory.setTodate(cal.getTime());
				mandatory.setCurrency(product.getCurrency());
				mandatory.setQuantity(0.0);
				mandatory.setUnit(Unit.EA);
				mandatory.setAvailable(1);
				net.cbtltd.shared.Price exists = sqlSession.getMapper(PriceMapper.class).exists(mandatory);
				if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(mandatory);}
				else {mandatory = exists;}

				mandatory.setState(net.cbtltd.shared.Price.CREATED);
				mandatory.setName("Final cleaning");
				mandatory.setMinimum(0.0);
				mandatory.setValue(property.getCleaningPrice());
				mandatory.setRule("Manual");
				mandatory.setVersion(version);
				sqlSession.getMapper(PriceMapper.class).update(mandatory);
				//cannot use cancelversionbypartyid after all iterations because currency may be changed.
				sqlSession.getMapper(PriceMapper.class).cancelversion(mandatory);
			}

			//add property type
			amenities.add("PCT3");
			//adding amenities(attributes)
			if (property.getAmenities() != null && property.getAmenities().getAmenity() != null) {
				for (net.cbtltd.rest.ru.product.Amenity amenity : property.getAmenities().getAmenity()) {
					Integer value = amenity.getValue();
					addAmenity(amenities, lostamenities, value);
				}
			}
			RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), amenities);
			RelationService.removeDeprecatedData(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), amenities);
			
			//add floor and space values into description
			StringBuilder description = new StringBuilder();

			Text action = new Text(product.getPublicId(), Language.Code.en.name());
			Text text = sqlSession.getMapper(TextMapper.class).readbyexample(action);
			if (text == null) {text = new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), "", Language.Code.en.name());}
			else {description.append(text.getNotes());}
			
			Integer floor = property.getFloor();
			if (floor != null && floor > 0 && !text.getNotes().contains("floor")) {
				description.append("\nProperty is located on the " + floor);
				switch (floor) {
				case 1:
				case 21:
				case 31:
				case 41:
				case 51:
				case 61:
				case 71:
				case 81:
				case 91: description.append("st");
					break;
				case 2:
				case 22:
				case 32:
				case 42:
				case 52:
				case 62:
				case 72:
				case 82:
				case 92: description.append("nd");
					break;
				case 3:
				case 23:
				case 33:
				case 43:
				case 53:
				case 63:
				case 73:
				case 83:
				case 93: description.append("rd");
					break;
				default:  description.append("th");
					break;
				}
				description.append(" floor.");
			}
			
			if (!text.getNotes().contains("Property consists of")) {
				int i = 1;
				description.append("\nProperty consists of: ");
				if (property.getCompositionRooms() != null && property.getCompositionRooms().getCompositionRoomID().size() > 0) {
					List<CompositionRoomID> compositionRooms = property.getCompositionRooms().getCompositionRoomID();
					for (CompositionRoomID room : compositionRooms){
						String roomType = getRoomType(room.getValue());
						int count = room.getCount();
						description.append(count + "x " + roomType + (count > 1 ? "'s" : "") + (compositionRooms.size() > i++ ? "," : "."));
					}
				} else if (roomsAmenities != null && roomsAmenities.getCompositionRoomAmenities() != null) {
					HashMap<Integer, Integer> roomsMap = new HashMap<Integer, Integer>();
					Set<Integer> idList = new HashSet<Integer>();
					for (CompositionRoomAmenities roomAmenity : roomsAmenities.getCompositionRoomAmenities()) {
						int roomId = roomAmenity.getCompositionRoomID();
						if (!idList.add(roomId)) {
							roomsMap.put(roomId, (roomsMap.get(roomId) + 1));
						} else {
							roomsMap.put(roomId, 1);
						}
					}
					for (Map.Entry<Integer, Integer> entry : roomsMap.entrySet()){
						int key = entry.getKey();
						int count = entry.getValue();
						description.append(count + "x " + getRoomType(key) + (count > 1 ? "'s" : "") + (roomsMap.entrySet().size() > i++ ? ", " : "."));
					}
				}
			}
			
			if (!text.getNotes().contains("Bedding") 
				&& roomsAmenities != null
				&& roomsAmenities.getCompositionRoomAmenities() != null
			) {
				int i = 0;
				description.append("\nBedding info: ");
				HashMap<String, Integer> beds = new HashMap<String, Integer>();
				for (CompositionRoomAmenities roomAmenities : roomsAmenities.getCompositionRoomAmenities()) {
					for (net.cbtltd.rest.ru.product.Amenity amenity : roomAmenities.getAmenities().getAmenity()) {
						String bedType = getBedType(amenity.getValue());
						if (bedType != null) {
							if (beds.get(bedType) != null) {
								beds.put(bedType, beds.get(bedType) + 1);
							} else {
								beds.put(bedType, amenity.getCount());
							}
						}
					}
				}
				for (Map.Entry<String, Integer> bedInfo : beds.entrySet()){
					String bedType = bedInfo.getKey();
					int count = bedInfo.getValue();
					description.append("\n" + count + "x " + bedType + ";");
					i++;
				}
			}
			
			Integer space = property.getSpace();
			if (space != null && space > 0 && !text.getNotes().contains("square m")){
				description.append("\nProperty space: " + space + " square meters.");
			}
			
			text.setState(Text.State.Created.name());
			text.setNotes(description.toString());
			product.setPublicText(text);
			TextService.update(sqlSession, product.getTexts());
			
			//adding distances to description text
//			StringBuilder description = new StringBuilder();
//			Distances distances = property.getDistances();
//			if (distances != null && distances.getDistance() != null) {
//				description.append("Distances to:");
//				for (Distance distance : distances.getDistance()) {
//					description.append(" ")
//					.append(getDestinationType(distance.getDestinationID()))
//					.append(" ")
//					.append(distance.getDistanceValue())
//					.append(" ")
//					.append(getDistance(distance.getDistanceUnitID()))
//					.append(" ");
//				}
//				description.append("\n");
//			}
//			Text action = new Text(product.getPublicId(), Language.Code.en.name());
//			Text text = sqlSession.getMapper(TextMapper.class).readbyexample(action);
//			if (text == null) {text = new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), "", Language.Code.en.name());}
//			text.setState(Text.State.Created.name());
//			text.setNotes(description.toString());
//			product.setPublicText(text);
//			TextService.update(sqlSession, product.getTexts());
			
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error("Error: " + x.getMessage() + " for property: " + altid);
		}
			
	}
	
	/**
	 * Uploads product image data from xml feed into database
	 */
	private void updateProductImage(SqlSession sqlSession, Property property, String productid, Date version){
		String altid = String.valueOf(property.getID().getValue());
		String message = "Updating property image for altID: " + altid;
		LOG.debug(message);
		try{
			if (property.getImages() != null && property.getImages().getImage() != null) {
				ArrayList<NameId> images = new ArrayList<NameId>();
				List<Image> imagesList = property.getImages().getImage();
				
				int i = 0;
				for (Image image : imagesList) {
					if (image.getImageTypeID() == 1){
						String imageid = getImageType(image.getImageTypeID());
						images.add(new NameId(imageid, image.getUrl()));
						imagesList.remove(i);
						break;
					}
					i++;
				}
				i = 0;
				while (i < imagesList.size()) {
					Image image = imagesList.get(i);
					if (image.getImageTypeID() == 3){
						String imageid = getImageType(image.getImageTypeID());
						images.add(new NameId(imageid, image.getUrl()));
						imagesList.remove(i);
					} else i++;
				}
				for (Image image : property.getImages().getImage()) {
					try {
						String imageid = getImageType(image.getImageTypeID());
						images.add(new NameId(imageid, image.getUrl()));
					}
					catch (Throwable x) {LOG.error("\nURL error " + image.getUrl());}
				}
				UploadFileService.uploadImages(sqlSession, NameId.Type.Product, productid, Language.EN, images);
				RelationService.load(sqlSession, Downloaded.PRODUCT_IMAGE_VERSION, productid, FF.format(version));
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error("Error: " + x.getMessage() + " for property: " + altid);
		}
	}
	
	/**
	 * Uploads product Description from xml feed into database
	 */
	private void updateProductDescription(SqlSession sqlSession, Product product, Property property, ArrayList<String> languages, Date version){
		String altid = String.valueOf(property.getID().getValue());
		String message = "Updating property description for altID: " + altid;
		LOG.debug(message);
		try{
			if (property.getDescriptions() != null && property.getDescriptions().getDescription() != null) {
				for (Description description : property.getDescriptions().getDescription()){
					//catch exception for missing languages
					Language.Code language = null;
					String langcode = getLanguageType(description.getLanguageID()).toLowerCase();
					try {
						language = Language.Code.valueOf(langcode);
					} catch (IllegalArgumentException e) {
						if (!languages.contains(langcode)) {
							languages.add(langcode);
						}
					}
					if (language != null) {addDescription(sqlSession, description.getText(), product, language);}
				}
				RelationService.load(sqlSession, Downloaded.PRODUCT_DESCRIPTION_VERSION, product.getId(), FF.format(version));
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error("Error: " + x.getMessage() + " for property: " + altid);
		}
	}
	
	/**
	 * List specific property for given AltID
	 * 
	 * @return 
	 */
	private static Property getProperty(String altid){
		net.cbtltd.rest.ru.response.ListSpecProp res = null;
		try{
			ListSpecProp request1 = new ListSpecProp();
			request1.setAuthentication(setAuth());
			request1.setPropertyID(altid);
			
			String rs = getConnection(toXML(request1));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListSpecProp.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			res = (net.cbtltd.rest.ru.response.ListSpecProp) jaxbUnmarshaller.unmarshal(new StringReader(rs));
		}
		catch (Throwable x) {
			x.printStackTrace();
			LOG.error("Error: " + x.getMessage() + " for property: " + altid);
		}
		return res.getProperty();
	}
	
	private String setPmRules(SqlSession sqlSession, Property property) {
		//retrieve information about cancellation policies and "check-in-out" from property object
		ArrivalInstructions info = property.getArrivalInstructions();
		//Email
		String email = info.getEmail();
		if (email == null || email.isEmpty()) {return getAltpartyid();}
		Party exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(email);
		
		if (exists != null) {
			//need to check cancellations and change them accordingly before returning PM ID
			String pmID = exists.getId();
			
			//cancellations from RU for property
			CancellationPolicies policies = property.getCancellationPolicies();
			//cancellations for PM from our DB
			List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class)
					.readbypmid(Integer.valueOf(pmID));
			if ((policies.getCancellationPolicy() != null && policies.getCancellationPolicy().get(0) != null) 
					&& (rules.isEmpty() || rules == null)) {
				setCancellationPolicies(sqlSession, policies, Integer.valueOf(pmID));
			} else {
				try {
					ListPropertyChangeLog propertyChangeLog = new ListPropertyChangeLog();
					propertyChangeLog.setAuthentication(setAuth());
					propertyChangeLog.setPropertyID(property.getID().getValue());

					//send request for log changes
					String rs = getConnection(toXML(propertyChangeLog));
					JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyChangeLog.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					net.cbtltd.rest.ru.response.ListPropertyChangeLog res = (net.cbtltd.rest.ru.response.ListPropertyChangeLog) 
							jaxbUnmarshaller.unmarshal(new StringReader(rs));
					ChangeLog changeLog = res.getChangeLog();

					Party party = sqlSession.getMapper(PartyMapper.class).read(pmID);
					Date versionDate = party.getVersion();
					if (FF.parse(changeLog.getStaticData()).after(versionDate)) {
						//add new policies
						int i = 0;
						for (CancellationPolicy policy : policies.getCancellationPolicy()){
							if (policy != null && policy.getValidTo() != 0 && policy.getValue() > 0.0){
								//check if same entry already exists in DB
								PropertyManagerCancellationRule cancellationRule = new PropertyManagerCancellationRule();
								cancellationRule.setCancellationRefund((int)Math.round(policy.getValue()));
								cancellationRule.setCancellationDate(policy.getValidTo());
								cancellationRule.setCancellationTransactionFee(0.0);
								cancellationRule.setPropertyManagerId(Integer.valueOf(pmID));
								
								if (!rules.contains(cancellationRule)) {
									sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).create(cancellationRule);
								}
								rules.remove(cancellationRule);
							}
						}
						//clean unused rules
						if (!rules.isEmpty()) {
							for (PropertyManagerCancellationRule rule : rules){
								sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).delete(rule.getId());
							}
						}
					}
				}
				catch (Throwable x) {
					LOG.error("Error: " + x.getMessage());
				}
			}
			
			return pmID;
		}
		else {
			//LandLord name
			String name = info.getLandlord();
			//Phone number
			String phone = info.getPhone();
			Integer pmsID = Integer.valueOf(getAltpartyid());
			
			//take RU party entry as example
			Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(pmsID.toString());
			//create party using same info as RU party entity does.
			//actionCreate.setEmployerID();
			String[] fullName = name.split(" ");
			String firstName = fullName[0];
			String lastName = fullName.length > 1 ? fullName[1] : null;
			propertyManager.setName(lastName, firstName);
			propertyManager.setState(Party.CREATED);
			propertyManager.setDayphone(phone);
			propertyManager.setExtraname("RU PM");
			propertyManager.setEmailaddress(email);
			propertyManager.setEmployerid(pmsID.toString());
			propertyManager.setAltid(property.getOwnerID().toString());
			propertyManager.setAltpartyid(pmsID.toString());
			//actionCreate.setCurrency();
			//actionCreate.setLanguage(Language.EN);
			propertyManager.setUsertype(Constants.PM_USER_TYPE);
			
			sqlSession.getMapper(PartyMapper.class).create(propertyManager);
			//action = partyService.execute(sqlSession, actionCreate);
			Integer pmID = Integer.valueOf(propertyManager.getId());
			
			PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(pmsID);
			//create property manager info object
			managerInfo.setPmsId(pmsID);
			managerInfo.setPropertyManagerId(pmID);
			managerInfo.setNewRegistration(true);
			managerInfo.setCancellationType(RegistrationConstants.CANCEL_CUSTOM);
			//create check-in-out info
			CheckInOut checkInOut = property.getCheckInOut();
			managerInfo.setCheckInTime(java.sql.Time.valueOf(checkInOut.getCheckInFrom() + ":00"));
			managerInfo.setCheckOutTime(java.sql.Time.valueOf(checkInOut.getCheckOutUntil() + ":00"));
			managerInfo.setCreatedDate(new Date());
			sqlSession.getMapper(PropertyManagerInfoMapper.class).create(managerInfo);
			
			//create cancellationPolicies
			CancellationPolicies policies = property.getCancellationPolicies();
			setCancellationPolicies(sqlSession, policies, pmID);
			//manager_to_channel
			List<Integer> channelIdList = sqlSession.getMapper(PartyMapper.class).listChannelID(pmsID);
			if (channelIdList != null && !channelIdList.isEmpty()) {
				sqlSession.getMapper(PartyMapper.class).deleteChannelPartners(pmID);
				for (Integer channelId : channelIdList) {
					sqlSession.getMapper(PartyMapper.class).insertChannelPatner(pmID, channelId);
				}
			} else {
				throw new ServiceException(Error.channel_partner_data, "Listof channel partners ids is empty.");
			}
			//parter
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(pmsID.toString());
			partner.setPartyid(pmID.toString());
			partner.setAbbrevation(null);
			partner.setSeparatepmaccounts(false);
			partner.setPartyname("RU - " + email);
			sqlSession.getMapper(PartnerMapper.class).create(partner);
			//manager_to_gateway
			ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(pmsID);
			managerToGateway.setSupplierId(pmID);
			sqlSession.getMapper(ManagerToGatewayMapper.class).create(managerToGateway);
			//payment_method
			PaymentMethod paymentMethod = sqlSession.getMapper(PaymentMethodMapper.class).read_by_pm(pmsID);
			paymentMethod.setPmID(pmID);
			sqlSession.getMapper(PaymentMethodMapper.class).create(paymentMethod);
			//property_manager_support_cc
			PropertyManagerSupportCC propertyManagerSupportCC = sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(pmsID);
			propertyManagerSupportCC.setPartyId(pmID);
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).create(propertyManagerSupportCC);
			
			sqlSession.commit();
			return pmID.toString();
		}
	}
	
	private void setCancellationPolicies(SqlSession sqlSession, CancellationPolicies policies, Integer pmID) {
		for (CancellationPolicy policy : policies.getCancellationPolicy()){
			if (policy != null && policy.getValidTo() != 0 && policy.getValue() > 0.0){
				PropertyManagerCancellationRule cancellationRule = new PropertyManagerCancellationRule();
				cancellationRule.setCancellationRefund((int)Math.round(policy.getValue()));
				cancellationRule.setCancellationDate(policy.getValidTo());
				cancellationRule.setCancellationTransactionFee(0.0);
				cancellationRule.setPropertyManagerId(pmID);
				
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).create(cancellationRule);
			}
		}
	}
	
	/**
	 * Transfer accommodation products.
	 */
	@Override
	public synchronized void readProducts() {
		String altPartyID = getAltpartyid();
		String message = "Rentals United readProducts " + altPartyID;
		LOG.debug(message);
		int nowDate = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		Date version = new Date();
		String rs;

		final SqlSession sqlSession = RazorServer.openSession();
		try {
			if ((nowDate != Calendar.SATURDAY) && (nowDate != Calendar.SUNDAY)) {
				updateProducts();
			} else {
				// XML Handler url - http://rm.rentalsunited.com/api/Handler.ashx
				// XML Data path - war/WEB-INF/classes/net/cbtltd/rest/ru/xml/*
				RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, altPartyID, new Date().toString());
				
				SAXBuilder builder = new SAXBuilder();
				String fromDate = DF.format(0);
				String toDate = DF.format(version);
				
				String ownerID = partner.getAltpartyid();
				
				// THIS IS OLD REQUEST. WE WILL NOW RETRIEVE PROPERTIES BY OWNER ID.
				// ----------
				//create XML RQ for property list sorted by creation date
				/**
				 * <Pull_ListPropByCreationDate_RQ>
				 * 	<Authentication>
				 * 		<UserName>Cockabondy@hotmail.com</UserName>
				 * 		<Password>Asd123456</Password>
				 * 	</Authentication>
				 * 	<CreationFrom> </CreationFrom>
				 * 	<CreationTo> </CreationTo>
				 * 	<IncludeNLA>true/false</IncludeNLA>
				 * </Pull_ListPropByCreationDate_RQ>
				 */
//				StringBuilder sb = new StringBuilder();			
//				sb.append("<Pull_ListPropByCreationDate_RQ>");
//				sb.append("	<Authentication>");
//				sb.append("		<UserName>" + username + "</UserName>");
//				sb.append("		<Password>" + password + "</Password>");
//				sb.append("	</Authentication>");
//				sb.append("	<CreationFrom>" + fromDate + "</CreationFrom>");
//				sb.append(" <CreationTo>" + toDate + "</CreationTo>");
//				sb.append(" <IncludeNLA>true</IncludeNLA>"); 	//false is set by default, this field is optional
//				sb.append("</Pull_ListPropByCreationDate_RQ>");
//				sb.append("");
//				rq = sb.toString();
//				LOG.error("\nXML request, rq=" + rq + "\n");
				
//				ListPropByCreationDate request = new ListPropByCreationDate();
//				request.setAuthentication(setAuth());
//				request.setCreationFrom(fromDate);
//				request.setCreationTo(toDate);
//				request.setIncludeNLA(false);
				// ----------
				
				/* try { */
					ListOwnerProp request = new ListOwnerProp();
					request.setAuthentication(setAuth());
					request.setOwnerID(Integer.valueOf(ownerID));
					request.setIncludeNLA(false);
					
					rs = getConnection(toXML(request));
/*				} catch (NullPointerException npe) {
					sqlSession.rollback();
					npe.printStackTrace();
					LOG.error("Wrong or absent OwnerID");
				} // cannot catch any exceptions here, because program will continue to run, and we don't need it
				*/
				
				Document propList = builder.build(new StringReader(rs));
				List<Element> properties = propList.getRootElement().getChild("Properties").getChildren();
				ArrayList<String> lostamenities = new ArrayList<String>();
				ArrayList<String> languages = new ArrayList<String>();
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (Element property : properties) {
					String altid = property.getChild("ID").getValue();
					try {
						
						//check if property exists in db
						Product product = PartnerService.getProduct(sqlSession, altPartyID, altid);
						if (product == null) {continue;}
						
						//Create XML request for specific property to list it's detailed info
						Property prop = getProperty(altid);
						if (prop == null){continue;}
						
						//update property static info
						updateProductStaticData(sqlSession, product, prop, sb, lostamenities, version);
						
						//adding descriptions
						updateProductDescription(sqlSession, product, prop, languages, version);
						
						//adding images
						updateProductImage(sqlSession, prop, product.getId(), version);
						
						sqlSession.commit();
						LOG.debug(i++ + " Property " + altid + " " + prop.getName() + " = " + product.getId());
//						wait(getProductwait());
					}
					catch (Throwable x) {
						x.printStackTrace();
						LOG.error("Error: " + x.getMessage() + " for property: " + property.getChildText("ID"));
					}
					
					sqlSession.commit();
				}
				Product action = new Product();
				action.setAltpartyid(altPartyID);
				action.setState(Product.CREATED);
				action.setVersion(version);
				sqlSession.getMapper(ProductMapper.class).cancelversion(action);
				
				LOG.debug("Lost language codes: " + languages.toString());
				LOG.debug("Lost amenities: " + lostamenities.toString());
				LOG.debug("Lost location ids: " + sb.toString());
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getStackTrace());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Transfer reservation schedule.
	 *
	 * @param sqlSession the current SQL session.
	 */
	@Override
	public synchronized void readSchedule() {
		String message = "readSchedule " + getAltpartyid();
		Calendar nowDate = Calendar.getInstance();
		String altPartyID = getAltpartyid();
		Date version = new Date();
		LOG.debug(message);
		String rs;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.SCHEDULE_DOWNLOAD_DATE, altPartyID, new Date().toString());
			nowDate.add(Calendar.YEAR, +1);
			String fromDate = DF.format(version);
			String toDate = DF.format(nowDate.getTime());
			ChangeLog changeLog;

			int i = 0;
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist(altPartyID);
			for (Product product : products) {
				String productid = product.getId();
				String altid = product.getAltid();
				//Checking actual date of last made change
				changeLog = getChangeLog(altid);
				if (changeLog == null) {continue;}
				String s = RelationService.find(sqlSession, Downloaded.PRODUCT_SCHEDULE_VERSION, productid, null); 
				if (s == null || FF.parse(changeLog.getPricing()).after(FF.parse(s))) {
					try {
						net.cbtltd.rest.ru.request.ListPropertyBlocks request = new net.cbtltd.rest.ru.request.ListPropertyBlocks();
						request.setPropertyID(Integer.valueOf(altid));
						request.setAuthentication(setAuth());
						request.setDateFrom(fromDate);
						request.setDateTo(toDate);
						
						rs = getConnection(toXML(request));
						
						JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyBlocks.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						net.cbtltd.rest.ru.response.ListPropertyBlocks blocks = (net.cbtltd.rest.ru.response.ListPropertyBlocks) jaxbUnmarshaller.unmarshal(new StringReader(rs));
						Date fromdate = null;
						Date todate = null;
						for (Block block : blocks.getPropertyBlock().getBlock()) {
							fromdate = block.getDateFrom().toGregorianCalendar().getTime();
							block.getDateTo().setTime(+24, 0, 0);
							todate = block.getDateTo().toGregorianCalendar().getTime();
							PartnerService.createSchedule(sqlSession, product, fromdate, todate, version);
						}
						PartnerService.cancelSchedule(sqlSession, product, version);
						LOG.debug(i++ + " Schedule for product: " + altid + ", " + productid + " loaded");
	
						net.cbtltd.rest.ru.request.ListPropertyMinStay rq = new net.cbtltd.rest.ru.request.ListPropertyMinStay();
						rq.setPropertyID(Integer.valueOf(product.getAltid()));
						rq.setAuthentication(setAuth());
						rq.setDateFrom(fromDate);
						rq.setDateTo(toDate);
						
						rs = getConnection(toXML(rq));
						
						jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyMinStay.class);
						jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						net.cbtltd.rest.ru.response.ListPropertyMinStay minstays = (net.cbtltd.rest.ru.response.ListPropertyMinStay) jaxbUnmarshaller.unmarshal(new StringReader(rs));
						fromdate = null;
						todate = null;
						for (net.cbtltd.rest.ru.MinStay minstay : minstays.getPropertyMinStay().getMinStay()) {
							fromdate = DF.parse(minstay.getDateFrom());
							todate = DF.parse(minstay.getDateTo());
							PartnerService.createMinStay(sqlSession, minstay.getValue(), product, fromdate, todate, version);
						}
						PartnerService.deleteMinStay(sqlSession, product, version);
						RelationService.load(sqlSession, Downloaded.PRODUCT_SCHEDULE_VERSION, productid, FF.format(version));
						LOG.debug(i++ + " MinStay for product: " + altid + " " + productid + " loaded");
	
						sqlSession.commit();
					}
					catch (Throwable x) {
						LOG.error("Error: " + x.getMessage());
					}
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}
	
	/**
	 * Transfer minstay values.
	 *
	 * @param sqlSession the current SQL session.
	 */
	@Deprecated
	public synchronized void readMinStay() {
		String message = "readMinstay " + getAltpartyid();
		Calendar nowDate = Calendar.getInstance();
		Date version = new Date();
		LOG.debug(message);
		String rs;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			nowDate.add(Calendar.YEAR, +1);
			String fromDate = DF.format(version);
			String toDate = DF.format(nowDate.getTime());

			ArrayList<Product> products= sqlSession.getMapper(ProductMapper.class).altlist(getAltpartyid());
			int i = 0;
			for (Product product : products) {
				net.cbtltd.rest.ru.request.ListPropertyMinStay request = new net.cbtltd.rest.ru.request.ListPropertyMinStay();
				request.setPropertyID(Integer.valueOf(product.getAltid()));
				request.setAuthentication(setAuth());
				request.setDateFrom(fromDate);
				request.setDateTo(toDate);
				
				rs = getConnection(toXML(request));
				
				JAXBContext jaxbContext = JAXBContext.newInstance(net.cbtltd.rest.ru.response.ListPropertyMinStay.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				net.cbtltd.rest.ru.response.ListPropertyMinStay minstays = (net.cbtltd.rest.ru.response.ListPropertyMinStay) jaxbUnmarshaller.unmarshal(new StringReader(rs));
				Date fromdate = null;
				Date todate = null;
				for (net.cbtltd.rest.ru.MinStay minstay : minstays.getPropertyMinStay().getMinStay()) {
					fromdate = DF.parse(minstay.getDateFrom());
					todate = DF.parse(minstay.getDateTo());
					PartnerService.createMinStay(sqlSession, minstay.getValue(), product, fromdate, todate, version);
				}
				PartnerService.deleteMinStay(sqlSession, product, version);
				sqlSession.commit();
				LOG.debug(i++ + " MinStay for product: " + product.getAltid() + " " + product.getId() + " loaded");
			}			
		}
		catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
			
		}
		finally {sqlSession.close();}
	}
	
	private static HashMap<Integer, Integer> PROPROOMS = null;
	
	/**
	 * Creates the map of property types to number of rooms.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static Integer getNrOfRooms(Integer type) {
		if (PROPROOMS == null) {
			PROPROOMS = new HashMap<Integer, Integer>();
			PROPROOMS.put(1,1);
			PROPROOMS.put(2,1);
			PROPROOMS.put(3,2);
			PROPROOMS.put(4,3);
			PROPROOMS.put(11,5);
			PROPROOMS.put(12,4);
			PROPROOMS.put(26,6);
			PROPROOMS.put(27,7);
			PROPROOMS.put(28,8);
		}
		if (PROPROOMS.get(type) == null) {return type;}
		else {return PROPROOMS.get(type);}
	}
	
	private static HashMap<Integer, String> PROPTYPE = null;
	
	private static HashMap<Integer, String> BEDTYPE = null;
	
	/**
	 * Creates the map of bed types.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static String getBedType(Integer type) {
		if (BEDTYPE == null) {
			BEDTYPE = new HashMap<Integer, String>();
			BEDTYPE.put(61,"double bed");
			BEDTYPE.put(200,"double sofa bed");
			BEDTYPE.put(209,"Extra Bed");
			BEDTYPE.put(237,"sofabed");
			BEDTYPE.put(323,"single bed");
			BEDTYPE.put(324,"king size bed");
			BEDTYPE.put(440,"Pair of twin beds");
			BEDTYPE.put(444,"Bunk Bed");
			BEDTYPE.put(485,"Queen size bed");
			BEDTYPE.put(624,"Pull-Out Bed");
			BEDTYPE.put(655,"Canopy / Poster Bed");
			BEDTYPE.put(778,"Murphy Bed");
			BEDTYPE.put(779,"Rollaway Beds");
		}
		if (BEDTYPE.get(type) == null) {return null;}
		else {return BEDTYPE.get(type);}
	}
	
	/**
	 * Creates the map of property types to number of rooms.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static String getPropertyType(Integer type) {
		if (PROPTYPE == null) {
			PROPTYPE = new HashMap<Integer, String>();
			PROPTYPE.put(1,"Studio");
			PROPTYPE.put(2,"One Bedroom");
			PROPTYPE.put(3,"Two Bedroom");
			PROPTYPE.put(4,"Three Bedroom");
			PROPTYPE.put(11,"Five Bedroom");
			PROPTYPE.put(12,"Four Bedroom");
			PROPTYPE.put(26,"Six Bedroom");
			PROPTYPE.put(27,"Seven Bedroom");
			PROPTYPE.put(28,"Eight Bedroom");
		}
		if (PROPTYPE.get(type) == null) {return "Accommodation";}
		else {return PROPTYPE.get(type);}
	}
	
	private static HashMap<Integer, String> ROOMTYPE = null;
	
	/**
	 * Creates the map of composition room types.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static String getRoomType(Integer type) {
		if (PROPTYPE == null) {
			PROPTYPE = new HashMap<Integer, String>();
			PROPTYPE.put(53,"WC");
			PROPTYPE.put(81,"Bathroom");
			PROPTYPE.put(94,"Kitchen in the living / dining room");
			PROPTYPE.put(101,"Kitchen");
			PROPTYPE.put(249,"Living room");
			PROPTYPE.put(257,"Bedroom");
			PROPTYPE.put(372,"Livingroom / Bedroom");
			PROPTYPE.put(517,"Bedroom/Living room with kitchen corner");
		}
		if (PROPTYPE.get(type) == null) {return "additional room";}
		else {return PROPTYPE.get(type);}
	}

	private static HashMap<Integer, String> IMAGES = null;
	
	/**
	 * Creates the map of image types.
	 *
	 * @param image type code
	 * @return the description of the image
	 */
	private static String getImageType(Integer type) {
		if (IMAGES == null) {
			IMAGES = new HashMap<Integer, String>();
			IMAGES.put(1,"Main image");
			IMAGES.put(2,"Property plan");
			IMAGES.put(3,"Interior");
			IMAGES.put(4,"Exterior");
		}
		if (IMAGES.get(type) == null) {return type.toString();}
		else {return IMAGES.get(type);}
	}
	
	private static HashMap<Integer,String> AMENITITES = null;

	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	private static void addAmenity(ArrayList<String> amenities, ArrayList<String> lostamenities, Integer amenity) {

		if (AMENITITES == null) {
			AMENITITES = new HashMap<Integer, String>();
			AMENITITES.put(2, "RMA60");												//		Cookware & Kitchen Utensils
			AMENITITES.put(3, "RMA60");												//		Crockery & Cutlery --
			AMENITITES.put(590, "RMA55");											//		Iron
			AMENITITES.put(591, "RMA56");											//		Ironing Board
			AMENITITES.put(4, "RMA55;RMA56");										//		Iron & Ironing Board -- *need two matches
			AMENITITES.put(491, "RMA55;RMA56");										//		Iron/Ironing board on request -- *need two matches
			AMENITITES.put(5, "RMA149");											//		Drying Rack
			AMENITITES.put(6, "RMA50");												//		Hair Dryer
			AMENITITES.put(7, "REC43");												//		Bed Linen & Towels
			AMENITITES.put(11, "RMA31");											//		Washing Machine
			AMENITITES.put(21, "RMA3");												//		Alarm Clock
			AMENITITES.put(187, "HAC242");											//		heating
			AMENITITES.put(201, "CBF58");											//		wardrobe
			AMENITITES.put(368, "HAC223");											//		Free Wireless Internet
			AMENITITES.put(24, "RMA129");											//		CD player
			AMENITITES.put(660, "RMA129");											//		CD Player --
			AMENITITES.put(70, "MRC47");											//		night table
			AMENITITES.put(71, "MRC47");											//		night tables --
			AMENITITES.put(189, "RMA146");											//		table and chairs
			AMENITITES.put(13, "RMA32");											//		Dishwasher
			AMENITITES.put(23, "RMA163");											//		DVD
			AMENITITES.put(325, "RMA163");											//		DVD player --
			AMENITITES.put(174, "RMA54");											//		internet connection
			AMENITITES.put(17, "RMA19");											//		kettle
			AMENITITES.put(140, "RMA19");											//		coffee maker --
			AMENITITES.put(667, "RMA19");											//		Coffee Maker in Room --
			AMENITITES.put(123, "RMA19");											//		electric kettle --
			AMENITITES.put(74, "RMA90");											//		TV
			AMENITITES.put(19, "RMA90");											//		Cable TV --
			AMENITITES.put(166, "RMA90");											//		TV (local channels only) --
			AMENITITES.put(167, "RMA210");											//		satellite TV
			AMENITITES.put(180, "RMA2");											//		air conditioning
			AMENITITES.put(804, "HAC186");											//		Paid parking on the street
			AMENITITES.put(295, "HAC186");											//		On street parking --
			AMENITITES.put(89, "RMA7");												//		Balcony
			AMENITITES.put(689, "HAC33");											//		Elevator
			AMENITITES.put(652, "HAC33");											//		Brailled Elevator --
			AMENITITES.put(374, "BUS40");											//		Cell Phone Rentals
			AMENITITES.put(808, "RMA54");											//		Paid cable internet
			AMENITITES.put(281, "PHY24");											//		Wheelchair access possible
			AMENITITES.put(601, "RMA92");											//		Safe
			AMENITITES.put(315, "RMA13");											//		bathtub
			AMENITITES.put(114, "RMA105");											//		cooking hob
			AMENITITES.put(250, "MRC47");											//		dining table
			AMENITITES.put(792, "RMA123");											//		Wireless Internet
			AMENITITES.put(134, "RMA149");											//		washing machine with drier
			AMENITITES.put(22, "RMA104");											//		Stereo
			AMENITITES.put(390, "RMA19");											//		Espresso-Machine
			AMENITITES.put(339, "HAC223");											//		FREE internet access
			AMENITITES.put(330, "CTT6");											//		city maps
			AMENITITES.put(124, "RMA68");											//		microwave
			AMENITITES.put(128, "RMA81");											//		plates
			AMENITITES.put(130, "RMA88");											//		fridge / freezer
			AMENITITES.put(131, "RMA88");											//		fridge --
			AMENITITES.put(152, "RMA88");											//		freezer --
			AMENITITES.put(395, "REC43");											//		Towels
			AMENITITES.put(596, "RMA26");											//		Free cot in the apartment
			AMENITITES.put(597, "RMA26");											//		Free cot on request --
			AMENITITES.put(702, "HAC42");											//		Free Parking
			AMENITITES.put(805, "HAC42");											//		Free parking with garage --
			AMENITITES.put(806, "HAC53");											//		Paid parking with garage
			AMENITITES.put(799, "RMA101");											//		Smoking allowed
			AMENITITES.put(497, "RMA145");											//		Luggage Storage Facilities
			AMENITITES.put(429, "BUS41");											//		Computer rental
			AMENITITES.put(234, "RCC5");											//		Laundry
			AMENITITES.put(803, "HAC42");											//		Free parking on the street
			AMENITITES.put(793, "RCC7");											//		Parking
			AMENITITES.put(448, "USC3");											//		Free weekly cleaning
			AMENITITES.put(504, "RCC7");											//		Private parking
			AMENITITES.put(235, "EVT4");											//		Breakfast
		}
		if (AMENITITES.get(amenity) != null) {
			String am = AMENITITES.get(amenity);
			if (am == null) {lostamenities.add(amenity + ", " + getAmenity(amenity));}
			else if (am.contains(";")) {
				StringTokenizer st = new StringTokenizer(am, ";");
				while (st.hasMoreElements()){
					amenities.add(st.nextElement().toString());					
				}
			}
			else amenities.add(am);
		}
	}

	private static HashMap<Integer, String> DESTINATIONS = null;
	
	/**
	 * Creates the map of property types to number of rooms.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static String getDestinationType(Integer type) {
		if (DESTINATIONS == null) {
			DESTINATIONS = new HashMap<Integer, String>();
			DESTINATIONS.put(26,"Airport");
			DESTINATIONS.put(97,"Main Railway Station");
			DESTINATIONS.put(129,"Grocery Store");
			DESTINATIONS.put(302,"Bus stop");
			DESTINATIONS.put(316,"Historical Centre");
			DESTINATIONS.put(386,"Centrum (Central) district");
		}
		if (DESTINATIONS.get(type) == null) {return type.toString();}
		else {return DESTINATIONS.get(type);}
	}
	
	private static HashMap<Integer, String> DISTANCES = null;
	
	/**
	 * Creates the map of property types to number of rooms.
	 *
	 * @param type the type of property
	 * @return the number of rooms
	 */
	private static String getDistance(Integer type) {
		if (DISTANCES == null) {
			DISTANCES = new HashMap<Integer, String>();
			DISTANCES.put(1,"meters");
			DISTANCES.put(2,"minutes");
			DISTANCES.put(3,"km");
			DISTANCES.put(6,"minutes walk");
			DISTANCES.put(8,"miles");
			DISTANCES.put(16,"minutes by tube");
			DISTANCES.put(18,"minutes by train");
			DISTANCES.put(19,"minutes by bus");
			DISTANCES.put(20,"On location");
			DISTANCES.put(21,"minutes drive");
			DISTANCES.put(22,"yards");
		}
		if (DISTANCES.get(type) == null) {return type.toString();}
		else {return DISTANCES.get(type);}
	}
	

	/**
	 * Gets amenity type code and returns text value.
	 *
	 * @param amenity type code
	 * @return the amenity string name
	 */
	private static String getAmenity(Integer type) {
		String amenityCode = null;
		try {
		JAXBContext jc = JAXBContext.newInstance(net.cbtltd.rest.ru.Amenities.class);
		Unmarshaller ju = jc.createUnmarshaller();
		Amenities amenities = (Amenities) ju.unmarshal(new File("war/WEB-INF/classes/net/cbtltd/rest/ru/xml/Amenities.xml"));
		
			for (net.cbtltd.rest.ru.Amenity amenity : amenities.getAmenity()) {
				if (amenity.getAmenityID() == type) {
					amenityCode = amenity.getAmenity();
					break;
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return amenityCode;
		
	}
	
	
	/**
	 * Adds description for property with specified language
	 * @param sqlSession
	 * @param txt - description text
	 * @param product entity
	 * @param lang - language code
	 */
	private static void addDescription(SqlSession sqlSession, String txt, Product product, Language.Code lang) {
		Text action = new Text(product.getPublicId(), lang.name());
		Text text = sqlSession.getMapper(TextMapper.class).readbyexample(action);
		if (text == null) {text = new Text(product.getPublicId(), "Description", Text.Type.HTML, new Date(), "", lang.name());}
		text.setState(Text.State.Created.name());
//		text.setNotes(txt);
		if (org.apache.commons.lang.StringUtils.isEmpty(text.getNotes())) {text.setNotes(txt);}
		else if (!text.getNotes().contains(txt)) {text.setNotes(txt + "\n\n" + text.getNotes());}
		product.setPublicText(text);
		TextService.update(sqlSession, product.getTexts());		
	}
	
	private static HashMap<Integer, String> LANGUAGES = null;
	
	/**
	 * Creates the map of language types.
	 *
	 * @param language type code
	 * @return the language
	 */
	private static String getLanguageType(Integer type) {
		if (LANGUAGES == null) {
			LANGUAGES = new HashMap<Integer, String>();
			LANGUAGES.put(1,"EN");
			LANGUAGES.put(2,"DE");
			LANGUAGES.put(3,"PL");
			LANGUAGES.put(4,"FR");
			LANGUAGES.put(5,"ES");
			LANGUAGES.put(6,"IT");
			LANGUAGES.put(7,"SV");
			LANGUAGES.put(8,"DK");
			LANGUAGES.put(9,"PT");
			LANGUAGES.put(10,"CZ");
			LANGUAGES.put(11,"NL");
			LANGUAGES.put(12,"FI");
			LANGUAGES.put(13,"RU");
			LANGUAGES.put(14,"LV");
			LANGUAGES.put(15,"LT");
			LANGUAGES.put(16,"EE");
			LANGUAGES.put(17,"SK");
			LANGUAGES.put(18,"HU");
			LANGUAGES.put(19,"JP");
			LANGUAGES.put(22,"BG");
			LANGUAGES.put(23,"TK");
			LANGUAGES.put(24,"GR");
			LANGUAGES.put(25,"RO");
			LANGUAGES.put(26,"ZH");
		}
		if (LANGUAGES.get(type) == null) {return type.toString();}
		else {return LANGUAGES.get(type);}
	}
	
	
	/**
	 * Gets the currency for specified location for product
	 * @param coderentalsunited
	 * @return currency code by location (City)
	 */
	private static String getCurrency(Integer coderentalsunited) {
		String currencyCode = null;
		try {		
		JAXBContext jc = JAXBContext.newInstance(net.cbtltd.rest.ru.Currencies.class);
		Unmarshaller ju = jc.createUnmarshaller();
		Currencies currencies = (Currencies) ju.unmarshal(new File("war/WEB-INF/classes/net/cbtltd/rest/ru/xml/Currencies.xml"));
		
		for (net.cbtltd.rest.ru.Currency currency : currencies.getCurrency()) {
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
	 * Checks if current string can be transfered to Double value
	 * @param str - String 
	 * @return true if this String can be parsed to Double
	 */
	private static boolean isDouble(String str){
		try{
			Double.parseDouble(str);
			return true;
		}
		catch(Exception e){
			return false;
		}		
	}
	
	/**
	 * Read special accommodation prices.
	 */
	@Override
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "RentalsUnited readSpecials()");
	}
	
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RentalsUnited inquireReservation()");
	}

}