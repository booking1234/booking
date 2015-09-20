package net.cbtltd.rest.pathway;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
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
import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Downloaded;
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
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;


/**
 * The auth_token: a6f15f8a-d000-4b17-a46f-d21e8a8ed662
 * 
 * @author Zoran Kocevski
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");

	private static final String PATHWAY_API_URL = "https://api.pathwaygds.com/";	
	private static final String ACCOMMODATION_LIST_URL = PATHWAY_API_URL + "accommodation";	
	
	//API auth token:
	private static final String AUTH_TOKEN = "a6f15f8a-d000-4b17-a46f-d21e8a8ed662";
		
	private static final String PATHWAY_API_NAMESPACE_URL = "http://schemas.datacontract.org/2004/07/PathwayAPI.Models";
	private static final Namespace PATHWAY_API_NAMESPACE = Namespace.getNamespace(PATHWAY_API_NAMESPACE_URL);
	
	private static final String DEFAULT_LANGUAGE = "EN";

	
	/**
	 * Instantiates a new partner handler.
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param partner
	 *            the partner.
	 */
	public A_Handler(Partner partner) {
		super(partner);
	}
	
	private InputStream getInputStream(String urlname) throws Throwable {
		GetMethod get = new GetMethod(urlname);
		get.addRequestHeader("Accept" , "application/xml");
		get.addRequestHeader("auth_token" , AUTH_TOKEN);
		HttpClient httpclient = new HttpClient();
//		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 300000);
//	    httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 600000);
		int rs = httpclient.executeMethod(get);
		if(rs!=200){
			throw new ServiceException(Error.rest_invalid, "response code: "+rs);
		}
		return get.getResponseBodyAsStream();
	}

	private static final String getConnection(String rq, String urlString) throws Throwable {
		String xmlString = "";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlString); 
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("auth_token", AUTH_TOKEN);

			if (rq != null) {
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.connect();
				OutputStream os = connection.getOutputStream();
				os.write(rq.getBytes());
				os.flush();
			}else{
				connection.setRequestMethod("GET");
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {
				xmlString += line;
			}
		} catch (Throwable x) {
			throw new RuntimeException(x.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return xmlString;
	}
	
	
	private boolean checkIfValueNullOrEmpty(String value){
		boolean result = false;
		if(value==null || value.isEmpty()){
			result = true;
		}
			
		return result;
	}
	
	private String getAccommodationDetailsLink(String accomId){
		return PATHWAY_API_URL + "accommodation/details/" + accomId;
	}
	

	private String formatDateToRequest (Date date) {
		if (date == null) {return null;}
		return dateFormater.format(date);
	}
	

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			String altId = PartnerService.getProductaltid(sqlSession, reservation);
			
			String arrivalDate = this.formatDateToRequest(reservation.getFromdate());
			String departureDate = this.formatDateToRequest(reservation.getTodate());
			int partyNumber = reservation.getAdult() + reservation.getChild();
			
			SAXBuilder parser = new SAXBuilder();
			
			String ratesLink = PATHWAY_API_URL + "accommodation/price?id="+altId
					+"&arrival="+arrivalDate+"&departure="+departureDate+"&party="+partyNumber;
			Document document = parser.build(this.getInputStream(ratesLink));
			Element rootNode = document.getRootElement();
				
			if(rootNode.getChild("priceDetails",PATHWAY_API_NAMESPACE)!=null){
				isAvailableResult = this.isAttributeExist(rootNode.getChild("priceDetails",PATHWAY_API_NAMESPACE).getChildText("available",PATHWAY_API_NAMESPACE));
			}	
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		MonitorService.monitor(message, timestamp);
		
		return isAvailableResult;
	}


	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String message = "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readPrice productAltId:" + productAltId;
		LOG.debug(message);
		ReservationPrice result = new ReservationPrice();
		Date timestamp = new Date();
		try {
			result = this.computePrice(sqlSession, reservation, productAltId, currency);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}
		
		MonitorService.monitor(message, timestamp);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) throws Throwable {
		ReservationPrice result = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		Date version = new Date();
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		
		String altId = PartnerService.getProductaltid(sqlSession, reservation);
		String arrivalDate = this.formatDateToRequest(reservation.getFromdate());
		String departureDate = this.formatDateToRequest(reservation.getTodate());
		int partyNumber = reservation.getAdult() + reservation.getChild();
		
		SAXBuilder parser = new SAXBuilder();
		
		String ratesLink = PATHWAY_API_URL + "accommodation/price?id="+altId
				+"&arrival="+arrivalDate+"&departure="+departureDate+"&party="+partyNumber;
		Document document = parser.build(this.getInputStream(ratesLink));
		Element rootNode = document.getRootElement();
		
		Element priceDetailsElement = rootNode.getChild("priceDetails",PATHWAY_API_NAMESPACE);
		String currencyFromResponse = priceDetailsElement.getChildText("currencyCode",PATHWAY_API_NAMESPACE);  
		Double currencyRate = 1.0;
		if(!currency.equalsIgnoreCase(currencyFromResponse)){
			currencyRate = WebService.getRate(sqlSession, currencyFromResponse, currency, new Date());
		}
		
		reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
		reservation.setCurrency(currency);
		
		
		//property Price also total price for now
		Double propertyPriceNotConverted = Double.valueOf(priceDetailsElement.getChildText("totalCost",PATHWAY_API_NAMESPACE));
		Double propertyPriceValue = PaymentHelper.getAmountWithTwoDecimals(propertyPriceNotConverted * currencyRate);
		
		LOG.debug("Pathway property "+altId+", price(not converted):"+propertyPriceNotConverted.toString() + ", price(converted):" +propertyPriceValue.toString());
		
//		QuoteDetail propertyPriceQd = new QuoteDetail();
//		propertyPriceQd.setAmount(propertyPriceValue+"");
//		propertyPriceQd.setDescription("Property Price ");
//		propertyPriceQd.setIncluded(true);
//		quoteDetails.add(propertyPriceQd);
		
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
		propertyPrice.setValue(propertyPriceValue);
		propertyPrice.setCurrency(reservation.getCurrency());
		reservation.getQuotedetail().add(propertyPrice);
		
		Double extraPriceValue = 0.0;
		
		//fees
		if(rootNode.getChild("accommodationExtras",PATHWAY_API_NAMESPACE)!=null 
				&& rootNode.getChild("accommodationExtras",PATHWAY_API_NAMESPACE).getChildren("accommodationExtra",PATHWAY_API_NAMESPACE)!=null){
			List<Element> extraPricesList = rootNode.getChild("accommodationExtras",PATHWAY_API_NAMESPACE).getChildren("accommodationExtra",PATHWAY_API_NAMESPACE);
			for(Element extraPriceElement : extraPricesList){
				//we will just use mandatory elements
				if(!this.isAttributeExist(extraPriceElement.getChildText("isOptional",PATHWAY_API_NAMESPACE))){
					String paymentMethod = extraPriceElement.getChildText("paymentMethod",PATHWAY_API_NAMESPACE);
					String extraPriceDesc = extraPriceElement.getChildText("extraDesc",PATHWAY_API_NAMESPACE);
					String extraPriceCurrency = extraPriceElement.getChildText("currencyCode",PATHWAY_API_NAMESPACE);  
					Double feeAmountNotConverted = Double.valueOf(extraPriceElement.getChildText("extraCost",PATHWAY_API_NAMESPACE));
					Double feeAmount = feeAmountNotConverted;
					if(!currency.equalsIgnoreCase(extraPriceCurrency)){
						Double extraCurrencyRate = WebService.getRate(sqlSession, extraPriceCurrency, currency, new Date());
						feeAmount = PaymentHelper.getAmountWithTwoDecimals(feeAmountNotConverted * extraCurrencyRate);
					}
					
//					QuoteDetail chargeFeeQd = new QuoteDetail();
//					chargeFeeQd.setAmount(feeAmount+"");
//					chargeFeeQd.setDescription(extraPriceDesc);
	
					QuoteDetail chargeFeeQd;
					if(paymentMethod.equalsIgnoreCase("includedinprice")){
						chargeFeeQd = new QuoteDetail(String.valueOf(feeAmount), currency, extraPriceDesc, "Included in the price", "", true);
					}else{
						extraPriceValue += feeAmount;
						chargeFeeQd = new QuoteDetail(String.valueOf(feeAmountNotConverted), extraPriceCurrency, extraPriceDesc, "paid by the client directly to the property owner on arrival", "", false);
					}
										
					quoteDetails.add(chargeFeeQd);
										
					
					
					net.cbtltd.shared.Price extraPrice = new net.cbtltd.shared.Price();
					extraPrice = new net.cbtltd.shared.Price();
					extraPrice.setEntitytype(NameId.Type.Reservation.name());
					extraPrice.setEntityid(reservation.getId());
					String feeType = net.cbtltd.shared.Price.MANDATORY;

					extraPrice.setType(feeType);
					extraPrice.setName(extraPriceDesc);
					extraPrice.setState(net.cbtltd.shared.Price.CREATED);
					extraPrice.setDate(version);
					extraPrice.setQuantity(1.0);
					extraPrice.setUnit(Unit.EA);
					extraPrice.setValue(feeAmount);
					extraPrice.setCurrency(reservation.getCurrency());
					reservation.getQuotedetail().add(extraPrice);
				}
				
				
			}
		}
		
		Double depositNotConverted = Double.valueOf(priceDetailsElement.getChildText("depositAmount",PATHWAY_API_NAMESPACE));
		Double depositValue = PaymentHelper.getAmountWithTwoDecimals(depositNotConverted * currencyRate);
		
		
		result.setTotal(propertyPriceValue);
		result.setPrice(propertyPriceValue);
		result.setCurrency(reservation.getCurrency());
		result.setQuoteDetails(quoteDetails);
		
		reservation.setPrice(propertyPriceValue);
		reservation.setQuote(propertyPriceValue);
		reservation.setDeposit(depositValue);
		reservation.setExtra(extraPriceValue);
		Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
		reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
		
			
		
		return result;
	}

	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") createReservationAndPayment()");

	}
	
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, " reservation " + reservation.getName());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}

			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}

			
			String arrivalDate = this.formatDateToRequest(reservation.getFromdate());
			String departureDate = this.formatDateToRequest(reservation.getTodate());
			int adultNumber = 0;
			if(reservation.getAdult()!=null){adultNumber = reservation.getAdult();}
			int childNumber = 0;
			if(reservation.getChild()!=null){childNumber = reservation.getChild();}
			int infantNumber = 0;
			if(reservation.getInfant()!=null){infantNumber = reservation.getInfant();}
			
			// creating request
			String reservtionRequest = "arrival="+arrivalDate+"&departure="+departureDate
					+"&adult="+adultNumber+"&child="+childNumber+"&infant="+infantNumber;
			
			reservtionRequest += "&firstname="+customer.getFirstName();
			reservtionRequest += "&lastname="+customer.getFamilyName();
			if(!checkIfValueNullOrEmpty(customer.getLocalAddress())){reservtionRequest += "&line1="+customer.getLocalAddress();}
			if(!checkIfValueNullOrEmpty(customer.getCity())){reservtionRequest += "&town="+customer.getCity();}
			if(!checkIfValueNullOrEmpty(customer.getPostalcode())){reservtionRequest += "&postcode="+customer.getPostalcode();}
			if(!checkIfValueNullOrEmpty(customer.getCountry())){reservtionRequest += "&country="+customer.getCountry();}
			reservtionRequest += "&email="+customer.getEmailaddress();
			if(!checkIfValueNullOrEmpty(customer.getNightphone())){reservtionRequest += "&telephone="+customer.getNightphone();}
			if(!checkIfValueNullOrEmpty(customer.getMobilephone())){reservtionRequest += "&mobile="+customer.getMobilephone();}
			if(!checkIfValueNullOrEmpty(customer.getDayphone())){reservtionRequest += "&work="+customer.getDayphone();}
			
			if(!checkIfValueNullOrEmpty(reservation.getNotes())){reservtionRequest += "&comments="+reservation.getNotes();}
//			LOG.debug(message + " rq=" + reservtionRequest);

			SAXBuilder parser = new SAXBuilder();
			String bookingRequestUrl = PATHWAY_API_URL + "booking/create/"+PartnerService.getProductaltid(sqlSession, reservation);
			Document document = parser.build(new StringReader(getConnection(reservtionRequest,bookingRequestUrl)));			
			Element rootNode = document.getRootElement();
			
			if (rootNode.getChild("bookingDetails",PATHWAY_API_NAMESPACE)!=null && rootNode.getChild("bookingDetails",PATHWAY_API_NAMESPACE).getChildText("bookingID",PATHWAY_API_NAMESPACE)!=null) {
				String bookingID = rootNode.getChild("bookingDetails",PATHWAY_API_NAMESPACE).getChildText("bookingID",PATHWAY_API_NAMESPACE);
			    reservation.setAltid(bookingID);
			    reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				LOG.debug("createReservation reservationAltId=" + bookingID);
			}
			else {
				throw new ServiceException(Error.reservation_api, "Reservation is not created");
			}
					
		}
		catch (Throwable x) {
			x.printStackTrace();
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(reservation.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") confirmReservation(), reservationId="+reservation.getId());
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") updateReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			String cancelBookingLink = PATHWAY_API_URL + "booking/cancel/" + reservation.getAltid();
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(this.getInputStream(cancelBookingLink));
			Element rootNode = document.getRootElement();
			
			if(rootNode.getChild("cancelStatus",PATHWAY_API_NAMESPACE)==null 
					|| rootNode.getChildText("cancelStatus",PATHWAY_API_NAMESPACE)==null
					|| !rootNode.getChildText("cancelStatus",PATHWAY_API_NAMESPACE).equalsIgnoreCase("ok")){
				throw new ServiceException(Error.reservation_api, "Error in cancelling reservations");
			}
		}
		catch (Throwable x) {
			LOG.error("Pathway GDS (Altpartyid:"+this.getAltpartyid()+") cancel reservation - " + x.getMessage());
			reservation.setMessage(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
	}

	@Override
	public void readAlerts() {
		//this is done in readProducts
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readAlerts()");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readPrices() {
		Date version = new Date();
		String message = "readPrices Pathway GDS (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayList<Product> productList = sqlSession.getMapper(ProductMapper.class).altlist(this.getAltpartyid());
			SAXBuilder parser = new SAXBuilder();
						
			for(Product product : productList){
				String altId = product.getAltid();
				LOG.debug("ReadPrice unitID="+altId);
				
				try {
					Document documentAccommodationDetails = parser.build(this.getInputStream(this.getAccommodationDetailsLink(altId)));
					Element rootNodeAccomDetails = documentAccommodationDetails.getRootElement();
					List<Element> minStayPeriods = rootNodeAccomDetails.getChild("minStayPeriods",PATHWAY_API_NAMESPACE).getChildren("minStayPeriod",PATHWAY_API_NAMESPACE);
	
					
					String ratesLink = PATHWAY_API_URL + "accommodation/rates/" + altId;
					Document documentAccommodationRates = parser.build(this.getInputStream(ratesLink));
					Element rootNodeAccomRates = documentAccommodationRates.getRootElement();
					List<Element> accommodationRates = rootNodeAccomRates.getChild("rates",PATHWAY_API_NAMESPACE).getChildren("rateDetails",PATHWAY_API_NAMESPACE);
	
					for(Element rateElement : accommodationRates){
						Boolean fixedPeriod = Boolean.valueOf(rateElement.getChildText("fixedPeriod",PATHWAY_API_NAMESPACE));
						Double rateValue = null;
						try{rateValue = Double.valueOf(rateElement.getChildText("rate",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
						String periodFormStr = rateElement.getChildText("periodFrom",PATHWAY_API_NAMESPACE);
						Date fromDate =  dateFormater.parse(periodFormStr);
						String periodToStr = rateElement.getChildText("periodTo",PATHWAY_API_NAMESPACE);
						Date toDate =  dateFormater.parse(periodToStr);
						
						
						if(fixedPeriod){
							//TODO now they return lot of fixed periods, and it seems that every is for 1 week (7 days)
							//price is a little lower than 7 x dailyRate. But we do not support this two situation (fixed and not fixed prices)
							//(we have field useOnePriceRow in product but than for all prices range must be fixed)
							
//							Double minimumValue = rateValue;
//							int minStay = Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays();
//				        	rateValue = NameId.round(rateValue/Double.valueOf(minStay));
//				        	
//				        	this.createOrFindPrice(sqlSession, product, fromDate, toDate, rateValue, minStay, minimumValue, version);
//				        	
						}else{
							//for every date range we need to have minStay.
							//otherwise rates will not be entered.
							if(minStayPeriods!=null && minStayPeriods.size()>0){
								//TODO check toDate with PathWay
								//because end of one range and start of next range are same dates.
								
								//parsing and format to remove timepart (dateFormater = yyyy-MM-dd)
								Date todayDate = dateFormater.parse(dateFormater.format(new Date()));
								
								//we do not need dates from past
								if(fromDate.before(todayDate)){
									fromDate = todayDate;
								}
								
								//also we check if fromDate before start date of first minStay interval
								String minStayFirstperiodFormStr = minStayPeriods.get(0).getChildText("periodFrom",PATHWAY_API_NAMESPACE);
								Date minStayFirstFromDate =  dateFormater.parse(minStayFirstperiodFormStr);
								if(fromDate.before(minStayFirstFromDate)){
									fromDate = minStayFirstFromDate;
								}
								
								//now we know that fromDate can not be before startDate in first minStay period,
								//and we go to each minStay period and if that is need, create more price intervals
								for(Element minStayElement : minStayPeriods){
//									String minStayperiodFormStr = minStayElement.getChildText("periodFrom",PATHWAY_API_NAMESPACE);
//									Date minStayFromDate =  dateFormater.parse(minStayperiodFormStr);
									
									String minStayperiodToStr = minStayElement.getChildText("periodTo",PATHWAY_API_NAMESPACE);
									Date minStayToDate =  dateFormater.parse(minStayperiodToStr);
									
									//in minStay Periods end of one, and start of next one are same dates.
									//so we check if fromDate not before minStay To Date, than look in next minStay period
									if(!fromDate.before(minStayToDate)){
										continue;
									}
									
									int minStay = Integer.parseInt(minStayElement.getChildText("minNights",PATHWAY_API_NAMESPACE));
									Double minimumValue = minStay * rateValue;
									
									if(!toDate.after(minStayToDate)){
										this.createOrFindPrice(sqlSession, product, fromDate, toDate, rateValue, minStay, minimumValue, version);
										break;
									} else{
										//now we need to split date range
										//this will also provide to have only prices for ranges for which we have minStay and price value
										this.createOrFindPrice(sqlSession, product, fromDate, minStayToDate, rateValue, minStay, minimumValue, version);
										
										//and we set startDate of next price range to be startDate from next minStay period
										//one more time: end date of one minStay period is same as startDdate of next one
										fromDate = minStayToDate;
									}
									
								}								
								
							}
						}

					}
					
					sqlSession.commit();
				}
				catch (Throwable x) {
					x.printStackTrace();
					sqlSession.rollback();
					LOG.error(x.getStackTrace());
				}	
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getStackTrace());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, version);
	}
	

	private void createOrFindPrice(final SqlSession sqlSession, Product product, Date fromDate, Date toDate, 
			Double rateValue, int minStay, Double minimumValue, Date version){
		Price price = new Price();
		price.setEntityid(product.getId());
		price.setEntitytype(NameId.Type.Product.name());
		price.setPartyid(getAltpartyid());
		price.setName(Price.RACK_RATE);
		price.setType(NameId.Type.Reservation.name());
		price.setDate(fromDate);						
		price.setTodate(toDate);
		price.setQuantity(1.0);
		price.setCurrency(product.getCurrency());
		price.setUnit(Unit.DAY);
		price.setState(Price.CREATED);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setAvailable(1);

		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}
		
		price.setValue(rateValue);
		price.setCost(rateValue);
		price.setMinStay(minStay);
		price.setMinimum(minimumValue);
		
		price.setVersion(version);
		sqlSession.getMapper(PriceMapper.class).update(price);
		sqlSession.getMapper(PriceMapper.class).cancelversion(price);
		
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public void readProducts() {
		String message = "readProducts Pathway GDS (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);
		Date version = new Date();
		
		StringBuilder sbNotKnowLocation = new StringBuilder();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder parser = new SAXBuilder();
			boolean accommodExist = true;
			int pageSize = 10;
			int currentPage = 1;
			int numberOfResult = 0;
			while(accommodExist){
				try{
					Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
					String accommodationRequestUrl = ACCOMMODATION_LIST_URL + "?language="+DEFAULT_LANGUAGE+"&suppliers="+party.getAltid()+"&pageSize="+pageSize+"&page="+currentPage;
//					String responseTemp = getConnection(null,accommodationRequestUrl);
					Document documentAccommodationList = parser.build(new StringReader(getConnection(null,accommodationRequestUrl)));
					//Document documentAccommodationList = parser.build(this.getInputStream(accommodationRequestUrl));
					Element rootNodeAccommodationList = documentAccommodationList.getRootElement();
					
					if(rootNodeAccommodationList.getChild("totalItems",PATHWAY_API_NAMESPACE)!=null){
						try{numberOfResult = Integer.parseInt(rootNodeAccommodationList.getChildText("totalItems",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
						if((pageSize * currentPage) >= numberOfResult){
							accommodExist = false;
						}
					}
						
					currentPage++;
					
					
					if(rootNodeAccommodationList.getChild("accommodation",PATHWAY_API_NAMESPACE)!=null 
							&& rootNodeAccommodationList.getChild("accommodation",PATHWAY_API_NAMESPACE).getChildren("accommodationDetails")!=null){
		
						List<Element> accommodationList = rootNodeAccommodationList.getChild("accommodation",PATHWAY_API_NAMESPACE).getChildren("accommodationDetails",PATHWAY_API_NAMESPACE);
						for(Element accommElement : accommodationList){
							try{
								//now we need to load unit (property) details for every unit
								String altId = accommElement.getChildText("accomID",PATHWAY_API_NAMESPACE);
								LOG.debug("Read accomID="+altId);
								
								Document documentAccommodationDetails = parser.build(new StringReader(getConnection(null,this.getAccommodationDetailsLink(altId))));
//								Document documentAccommodationDetails = parser.build(this.getInputStream(this.getAccommodationDetailsLink(altId)));
								Element rootNodeAccomDetails = documentAccommodationDetails.getRootElement();
								Element accomDataElement = rootNodeAccomDetails.getChild("accommodationData",PATHWAY_API_NAMESPACE);
								
								
								//this we read one more time, just to be sure. But that should be same.
								altId = accomDataElement.getChildText("accomID",PATHWAY_API_NAMESPACE);
								
								Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId);
								if (product == null) {
									continue;
								}
								
								Integer roomNumber = 0;
								Integer bathroomNumber = 0;
								Integer personNumber = 0;
								Integer rating = 5;
								Double latitude = null;
								Double longitude = null;
								try{roomNumber = Integer.parseInt(accomDataElement.getChildText("totalRooms",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								try{bathroomNumber = Integer.parseInt(accomDataElement.getChildText("totalBathrooms",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								try{personNumber = Integer.parseInt(accomDataElement.getChildText("maxOccupancy",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								try{rating = Integer.parseInt(accomDataElement.getChildText("starRating",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								try{latitude = Double.valueOf(accomDataElement.getChildText("lat",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								try{longitude = Double.valueOf(accomDataElement.getChildText("lon",PATHWAY_API_NAMESPACE));}catch(Exception parseExc){}
								
					
								//this name is to big
//								String physicalAddress = accomDataElement.getChildText("locationName",PATHWAY_API_NAMESPACE);
								
								product.setCurrency(accommElement.getChildText("currencyCode",PATHWAY_API_NAMESPACE));
								product.setName(accomDataElement.getChildText("propTitle",PATHWAY_API_NAMESPACE) +", "+ accomDataElement.getChildText("accomType",PATHWAY_API_NAMESPACE));
								product.setUnit(Unit.DAY);
								product.setRoom(roomNumber);
								product.setBathroom(bathroomNumber);
								product.setQuantity(1);
								product.setPerson(personNumber);
								product.setRank(getRank());
								product.setLatitude(latitude);
								product.setLongitude(longitude);
//								product.setPhysicaladdress(physicalAddress);
								product.setRating(rating);
								
								//this is because property usually have some period
								//but in this case we can not use this because we have here combination
								//of fixed and not fixed prices, and setUseonepricerow working only with 1 or more fixed prices
//								product.setUseonepricerow(true);
								
								product.setCleaningfee(0.0);
								product.setWebaddress(getWebaddress());
								product.setCommission(getCommission());
								product.setDiscount(getDiscount());
								
								product.setAltitude(0.0);
								product.setChild(0);
								product.setInfant(0);
								product.setToilet(0);
								
								product.setVersion(version);
								
								List<Element> accommodationLocations = rootNodeAccomDetails.getChild("accommodationLocations",PATHWAY_API_NAMESPACE).getChildren("accommodationLocation",PATHWAY_API_NAMESPACE);
								String countryISO = null;
								String countryName = null;
								String state = null;
								String stateName = null;
								String city = null;
								
								
								for(Element accommodationLocation : accommodationLocations){
									if(accommodationLocation.getChildText("IATACode",PATHWAY_API_NAMESPACE)!=null && !accommodationLocation.getChildText("IATACode",PATHWAY_API_NAMESPACE).equalsIgnoreCase("")){
										countryISO = accommodationLocation.getChildText("IATACode",PATHWAY_API_NAMESPACE);
										countryName = accommodationLocation.getChildText("locationName",PATHWAY_API_NAMESPACE);
										continue;
									}else if(accommodationLocation.getChildText("stateCode",PATHWAY_API_NAMESPACE)!=null && !accommodationLocation.getChildText("stateCode",PATHWAY_API_NAMESPACE).equals("")){
										state = accommodationLocation.getChildText("stateCode",PATHWAY_API_NAMESPACE);
										stateName = accommodationLocation.getChildText("locationName",PATHWAY_API_NAMESPACE);
										continue;
									}
									
									//city will be last element
									city = accommodationLocation.getChildText("locationName",PATHWAY_API_NAMESPACE);
								}
								
//								StringBuilder physicalAddress = new StringBuilder();
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("line1"))) {physicalAddress.append(unitAddress.getChildText("line1")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("line2"))) {physicalAddress.append(unitAddress.getChildText("line2")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("state"))) {physicalAddress.append(unitAddress.getChildText("state")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("zipCode"))) {physicalAddress.append(unitAddress.getChildText("zipCode")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("region"))) {physicalAddress.append(unitAddress.getChildText("region")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("subRegion"))) {physicalAddress.append(unitAddress.getChildText("subRegion")).append("\n");}
//								if (!checkIfValueNullOrEmpty(unitAddress.getChildText("country"))) {physicalAddress.append(unitAddress.getChildText("country")).append("\n");}
//								
								
								StringBuilder physicalAddress = new StringBuilder();
								if (!checkIfValueNullOrEmpty(city)) {physicalAddress.append(city).append("\n");}
								if (!checkIfValueNullOrEmpty(stateName)) {physicalAddress.append(stateName).append("\n");}
								if (!checkIfValueNullOrEmpty(countryName)) {physicalAddress.append(countryName).append("\n");}
								
								product.setPhysicaladdress(physicalAddress.toString());
								
								//location finding								
								Location location = null;
								if(latitude!=null && longitude!=null){
									location = PartnerService.getLocation(sqlSession, 
											city,
											state,
											countryISO, 
											latitude, 
											longitude);
								}else{
									location = PartnerService.getLocation(sqlSession, 
											city,
											state,
											countryISO);
								}
								
								
								if(location!=null && location.getId()!=null){
									product.setLocationid(location.getId());
								}else{
									String locationNotFoundMessage = "Pathway property: " + altId + " country: " + countryISO + " city: " + city;
									sbNotKnowLocation.append("\n").append(locationNotFoundMessage);
									product.setState(Product.SUSPENDED);
									LOG.debug("Not Found: " +locationNotFoundMessage);
								}
								
								
								
								//description build
								StringBuilder description = new StringBuilder();
								description.append(accomDataElement.getChildText("detailed",PATHWAY_API_NAMESPACE)).append("\n\n");
								
								
								ArrayList<String> attributes = new ArrayList<String>();
								addType(attributes, accomDataElement.getChildText("accomType",PATHWAY_API_NAMESPACE));
								
								List<Element> attributesList = rootNodeAccomDetails.getChild("accommodationFeatures",PATHWAY_API_NAMESPACE).getChildren("accommodationFeature",PATHWAY_API_NAMESPACE); 
								for (Element attribute : attributesList){
									addPropertyAttribute(attributes, attribute.getChildText("featureName",PATHWAY_API_NAMESPACE));
								}
								
								//now some attributes which are not in list
								if(isAttributeExist(accomDataElement.getChildText("hasAirCon",PATHWAY_API_NAMESPACE))){
									attributes.add("RMA2"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasBeach",PATHWAY_API_NAMESPACE))){
									attributes.add("RST5"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasGolf",PATHWAY_API_NAMESPACE))){
									attributes.add("RST27"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasHeatedPool",PATHWAY_API_NAMESPACE))){
									attributes.add("HAC49"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasPrivatePool",PATHWAY_API_NAMESPACE))){
									attributes.add("HAC253"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasSharedPool",PATHWAY_API_NAMESPACE))){
									attributes.add("HAC71"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("hasInternet",PATHWAY_API_NAMESPACE))){
									attributes.add("RMA54"); 
								}
								if(isAttributeExist(accomDataElement.getChildText("petsAllowed",PATHWAY_API_NAMESPACE))){
									attributes.add("PET10"); //PET10 = Domestic pets
									description.append("Pets are allowed.").append("\n\n");
								}else{
									description.append("Pets are not allowed.").append("\n\n");
								}
								
								
								
								//in search fields there is also some attributes. Generally this attributes
								//are present in regular attributes (miscellaneousFields) but some are not
			//					List<Element> searchAattributesList = accomDataElement.getChild("searchFields").getChildren(); 
			//					for (Element attribute : searchAattributesList){
			//						String exist = attribute.getAttribute("description").getValue();
			//						if(attribute.getValue().equalsIgnoreCase("Internet") && isAttributeExist(exist)){
			//							attributes.add("RMA54"); //Internet access
			//						}else if(attribute.getValue().equalsIgnoreCase("Smoking")){
			//							if(isAttributeExist(exist)){
			//								attributes.add("RMA101");//smoking YES
			//							}else{
			//								attributes.add("RMA74");//smoking NO
			//							}
			//						}
			//					}
								
								
								//alerts
								List<Element> erataList = rootNodeAccomDetails.getChild("errataItems",PATHWAY_API_NAMESPACE).getChildren("errataItem",PATHWAY_API_NAMESPACE);
								if(erataList!=null && erataList.size()>0){
									System.out.println("Alerts exist");
									for(Element alertElement : erataList){
										Alert alert = new Alert();
										alert.setEntitytype(NameId.Type.Product.name());
										alert.setEntityid(product.getId());

										String periodFormStr = alertElement.getChildText("periodFrom",PATHWAY_API_NAMESPACE);
										Date fromDate =  dateFormater.parse(periodFormStr);
										String periodToStr = alertElement.getChildText("periodTo",PATHWAY_API_NAMESPACE);
										Date toDate =  dateFormater.parse(periodToStr);
										
										alert.setFromdate(fromDate);  
										alert.setTodate(toDate);  
										alert.setLanguage(DEFAULT_LANGUAGE);
										alert.setName(alertElement.getChildText("errataInfo",PATHWAY_API_NAMESPACE));
										
										net.cbtltd.shared.Alert duplicate = sqlSession.getMapper(AlertMapper.class).duplicate(alert);
										if (duplicate == null) {sqlSession.getMapper(AlertMapper.class).create(alert);}
										else {sqlSession.getMapper(AlertMapper.class).update(alert);}
										sqlSession.commit();
										LOG.debug("Alert: " + alert.toString());
									}
								}
							
								sqlSession.getMapper(ProductMapper.class).update(product);
								
								product.setPublicText(new Text(product.getPublicId(), product.getName(), Text.Type.HTML, new Date(), description.toString(), Language.EN));
								TextService.update(sqlSession, product.getTexts());
								
								RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
								RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
								RelationService.removeDeprecatedData(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
								
								sqlSession.commit();
								
							
							} catch (Throwable x) {
								sqlSession.rollback();
								LOG.error(x.getMessage());
//								x.printStackTrace();
							}
						}
					}
					
					//canceling not updated products
					Product action = new Product();
					action.setAltpartyid(getAltpartyid());
					action.setState(Product.CREATED);
					action.setVersion(version); 
					
					sqlSession.getMapper(ProductMapper.class).cancelversion(action);
					sqlSession.commit();
					
				} catch (Throwable x) {
					sqlSession.rollback();
					LOG.error(x.getMessage());
//					x.printStackTrace();
				}
			}	

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
//			x.printStackTrace();
		} finally {
			sqlSession.close();
		}
		LOG.debug("Missing Places" + sbNotKnowLocation.toString());
		MonitorService.monitor(message, version);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void readImages() {
		Date timestamp = new Date();
		String message = "readImages Pathway GDS (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			ArrayList<Product> productList = sqlSession.getMapper(ProductMapper.class).altlist(this.getAltpartyid());
			SAXBuilder parser = new SAXBuilder();
						
			for(Product product : productList){
				String altId = product.getAltid();	
				
				try {
					
					Document documentAccommodationDetails = parser.build(this.getInputStream(this.getAccommodationDetailsLink(altId)));
					Element rootNodeAccomDetails = documentAccommodationDetails.getRootElement();
					List<Element> accommodationImages = rootNodeAccomDetails.getChild("accommodationImages",PATHWAY_API_NAMESPACE).getChildren("accommodationImage",PATHWAY_API_NAMESPACE);
	
					if (HasUrls.LIVE && accommodationImages != null && accommodationImages.size()>0) {
						ArrayList<NameId> images = new ArrayList<NameId>();
						for (Element imgElement : accommodationImages) {
							images.add(new NameId("", imgElement.getChildText("imageFile",PATHWAY_API_NAMESPACE)));
						}
						UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
					}
					sqlSession.commit();
				}
				catch (Throwable x) {
					LOG.error(x.getMessage()); 
					x.printStackTrace();
					sqlSession.rollback();
				}
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void readSchedule() {
		Date version = new Date();
		String message = "readSchedule Pathway GDS (Altpartyid:"+this.getAltpartyid()+")";
		
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayList<Product> productList = sqlSession.getMapper(ProductMapper.class).altlist(this.getAltpartyid());
			SAXBuilder parser = new SAXBuilder();
						
			for(Product product : productList){
				
				String altId = product.getAltid();
				LOG.debug("read schedule for AltId="+altId);
				SortedSet<DateTime> accommodationAvailableDates = new TreeSet<DateTime>();
				try {
					String accommodationAvailabilityLink = PATHWAY_API_URL + "accommodation/availability/" + altId; 
					Document document = parser.build(this.getInputStream(accommodationAvailabilityLink));
					Element rootNode = document.getRootElement();
					List<Element> accomAvailabilityList = rootNode.getChild("availability",PATHWAY_API_NAMESPACE).getChildren("accommodationAvailability",PATHWAY_API_NAMESPACE);
					for(Element accommAvailabilityElement : accomAvailabilityList){
						String availabilityDateString = accommAvailabilityElement.getChildText("availabilityDate",PATHWAY_API_NAMESPACE);
						Date currentDate = dateFormater.parse(availabilityDateString);
						String isDateBookedString = accommAvailabilityElement.getChildText("changeover",PATHWAY_API_NAMESPACE);
						String unavailableString = accommAvailabilityElement.getChildText("unavailable",PATHWAY_API_NAMESPACE);
						//we adding all available dates to list (N mean not available)
						//also if <unavailable>true</unavailable> they wrote that mean day is not available
						//for other side I was able to book that kind of day - answer from them - this property should not be available
						if(!isDateBookedString.equalsIgnoreCase("N") && !isAttributeExist(unavailableString)){
							accommodationAvailableDates.add(new DateTime(currentDate));
						}
					}
					
					if(accommodationAvailableDates.size()>0){
						this.createSchedule(accommodationAvailableDates, product, version, sqlSession);
					}
					
				}catch (Throwable x) {
					LOG.error(x.getMessage()); 
					x.printStackTrace();
					sqlSession.rollback();
				}	
			}
			
			sqlSession.commit();

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
	}
	
	
	/**
	 * Creating and entering not available date ranges from available dates
	 * if between two available dates is 1 or more days, than that is non available range
	 * 
	 * @param availableDates
	 * @param product
	 * @param version
	 * @param sqlSession
	 */
	private void createSchedule(SortedSet<DateTime> availableDates, Product product , Date version, SqlSession sqlSession ) {
		
		DateTime currentDate = new DateTime(version).withTime(0, 0, 0, 0);
		
		// create reservation if current date is less than the first date in the available dates set
		DateTime firstAvailableDate = availableDates.first();
		int daysBetween = Days.daysBetween(currentDate, availableDates.first()).getDays();
		if(daysBetween > 1) {
			PartnerService.createSchedule(sqlSession, product, currentDate.toDate(), firstAvailableDate.withFieldAdded(DurationFieldType.days(),0).toDate(), version);
		}
		
		DateTime fromDate = firstAvailableDate;
		
		boolean first = true;
		for(DateTime  toDate : availableDates) {
			if(first) {
				first = false;
				continue;
			}
			daysBetween = Days.daysBetween(fromDate, toDate).getDays();
			if(daysBetween > 1) {
				//System.out.println("Create Schedule " + fromDate.toString() + " - " + toDate.toString());
				//toDate is changed 0 (was -1)
				PartnerService.createSchedule(sqlSession, product, fromDate.withFieldAdded(DurationFieldType.days(), 1).toDate(), toDate.withFieldAdded(DurationFieldType.days(),0).toDate(), version);
			}
			fromDate = toDate;
			
		}
		
		PartnerService.cancelSchedule(sqlSession, product, version);
		sqlSession.commit();
		
	}
	
	

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readSpecials()");

	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readDescriptions()");

	}

	

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readAdditionCosts()");

	}
	
	private static HashMap<String, String> TYPES = null;
	/**
	 * villa, apartment, bungalow, farmhouse, holiday village residence,
	 * castle / mansion, yacht, holiday home, hotel 
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("apartment","PCT3"); 
			TYPES.put("bungalow","PCT5"); 
			TYPES.put("holiday home","PCT35"); //PCT35 = Vila??? 
			TYPES.put("villa","PCT35");
			TYPES.put("castle / mansion","PCT37");
			TYPES.put("yacht","PCT44");
			TYPES.put("hotel","PCT20");
			TYPES.put("farmhouse","PCT15"); //PCT15 = Guest Farm??? 
			TYPES.put("holiday village residence","PCT18"); //PCT18 = Holiday Resort??? 
		}
		if (TYPES.get(type) == null) {
			attributes.add(type);
			System.out.println("Type not exist: "+type);
		}
		else  {
			attributes.add(TYPES.get(type));
		}
	}
	
	
	private static HashMap<String,String> PROPERTY_ATTRIBUTES = null;
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	
	private static final void addPropertyAttribute(ArrayList<String> attributes, String attribute) {
		if (PROPERTY_ATTRIBUTES == null) {
			PROPERTY_ATTRIBUTES = new HashMap<String, String>();
			
			PROPERTY_ATTRIBUTES.put("seaview", "RVT25"); 
			PROPERTY_ATTRIBUTES.put("snowboarding", "REC13"); 
			PROPERTY_ATTRIBUTES.put("skiarea", "ACC45"); 
			PROPERTY_ATTRIBUTES.put("tennis", "RST94"); 
			PROPERTY_ATTRIBUTES.put("tabletennis", "RST117"); 
			PROPERTY_ATTRIBUTES.put("gamesconsole", "HAC44"); //HAC44-Games room 
			PROPERTY_ATTRIBUTES.put("hairdryer", "RMA50"); 
			PROPERTY_ATTRIBUTES.put("maidservice", "RMA208"); 
			PROPERTY_ATTRIBUTES.put("wheelchairaccess", "PHP24"); 
			PROPERTY_ATTRIBUTES.put("waterfilter", "RMA121"); 
			PROPERTY_ATTRIBUTES.put("satellitetv", "RMA210"); 
		
			//these are already in readProducts as attirbute
			//	PROPERTY_ATTRIBUTES.put("internet", "RMA54"); //
			//	PROPERTY_ATTRIBUTES.put("petsallowed", "PET10"); //PET10 = Domestic pets
			// 
			// airconditioning,
			
			//missing in mapping
			//gatedpool, stairgates, babytravelcot, villagelocation
		}
		
		
		if (PROPERTY_ATTRIBUTES.get(attribute) != null) {
			attributes.add(PROPERTY_ATTRIBUTES.get(attribute));
		}else{
			if(!attribute.equalsIgnoreCase("internet") && !attribute.equalsIgnoreCase("petsallowed")){
				System.out.println("Missing attribute: " + attribute);
			}
		}
	}
	
	
	private boolean isAttributeExist(String exist){
		boolean result = Boolean.valueOf(exist);
		
		return result;
	}
	
	
	
	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Pathway GDS (Altpartyid:"+this.getAltpartyid()+") readLocations()");
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Pathway inquireReservation()");
	}
}