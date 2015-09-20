/**
* @author	Marko Ovuka
 * @see		License at http://razorpms.com/razor/License.html
 * @version	1.00
 */
package net.cbtltd.rest.rms;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import net.cbtltd.rest.GatewayHandler;
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
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
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
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;



import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * The Test API credentials are:
 * Username: BookingPal_Test
 * Password: zAqema743
 * 
 * @author Marko Ovuka
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat dateFormaterFromResponse = new SimpleDateFormat("MM-dd-yyyy");
	private static final DateFormat dateFormaterToCronRequest = new SimpleDateFormat("MMddyyyy");
	private static final DateFormat dateFormaterToReservationRequest = new SimpleDateFormat("MM/dd/yyyy");

	private static final String RMS_API_URL = "https://portal7.resortplanet.com/rms-live/xml/";	
	private static final String UNIT_LIST_URL = RMS_API_URL + "unit/list";
	
	// Test API credentials:
	// TODO: put production credentials 
	private static final String RMS_USERNAME = "BookingPal_Test";
	private static final String RMS_PASSWORD = "zAqema743";
	private static final String RMS_SERVER_AUTH = RMS_USERNAME+":"+RMS_PASSWORD;

	private static final int SCHEDULES_FOR_NUMBER_DAYS_IN_FUTURE = 500;
	
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

	
	private boolean checkIfValueNullOrEmpty(String value){
		boolean result = false;
		if(value==null || value.isEmpty()){
			result = true;
		}
			
		return result;
	}
	
	
	private String formatDateToCronRequest (Date date) {
		if (date == null) {return null;}
		return dateFormaterToCronRequest.format(date);
	}
	
	
	private String formatDateToReservationRequest (Date date) {
		if (date == null) {return null;}
		return dateFormaterToReservationRequest.format(date);
	}
	

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "RMS (Resort Management System) isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			String altId = PartnerService.getProductaltid(sqlSession, reservation);
			
			String arrivalDate = this.formatDateToCronRequest(reservation.getFromdate());
			String departureDate = this.formatDateToCronRequest(reservation.getTodate());
			
			String availabilityGetUrl = RMS_API_URL + "unit/availability?arrivalDate="+arrivalDate+"&departureDate="+departureDate+"&unitId="+altId;
			
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(PartnerService.getInputStream(availabilityGetUrl, RMS_SERVER_AUTH));
			Element rootNode = document.getRootElement();			
					
			if(rootNode.getChild("available")!=null && rootNode.getChildText("available").equalsIgnoreCase("true")){
				isAvailableResult = true;
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
		String message = "RMS (Resort Management System) readPrice productAltId:" + productAltId;
		LOG.debug(message);
		ReservationPrice result = new ReservationPrice();
		Date timestamp = new Date();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}

			Element rootNodeUnitPrice = this.createAndExecuteGetPriceRequest(sqlSession, reservation, product.getAltid());	
			result = this.fillResultsAfterGetPrice(sqlSession, reservation, currency, rootNodeUnitPrice, product);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}
		
		MonitorService.monitor(message, timestamp);
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	private ReservationPrice fillResultsAfterGetPrice(SqlSession sqlSession, Reservation reservation,  
			String currency, Element rootNodeUnitPrice, Product product) throws Throwable {
		
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		Date version = new Date();
		
		if(rootNodeUnitPrice.getChild("units")!=null && rootNodeUnitPrice.getChild("units").getChild("unit")!=null){
			Element unitElement = rootNodeUnitPrice.getChild("units").getChild("unit");
			
			Double currencyRate = 1.0;
			if(!currency.equalsIgnoreCase(product.getCurrency())){
				currencyRate = WebService.getRate(sqlSession, product.getCurrency(), currency, new Date());
			}
			
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			reservation.setCurrency(currency);
			
			//property Price
			Double propertyPriceNotConverted = Double.valueOf(unitElement.getChildText("rateTotal"));
			Double propertyPriceValue = PaymentHelper.getAmountWithTwoDecimals(propertyPriceNotConverted * currencyRate);
			
//			QuoteDetail propertyPriceQd = new QuoteDetail();
//			propertyPriceQd.setAmount(propertyPriceValue+"");
//			propertyPriceQd.setDescription("Property Rate Price: ");
//			propertyPriceQd.setIncluded(true);
//			quoteDetails.add(propertyPriceQd);
			
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
			 
			//fees
			if(unitElement.getChild("charges")!=null && unitElement.getChild("charges").getChildren("charge")!=null){
				List<Element> chargeList = unitElement.getChild("charges").getChildren("charge");
				for(Element chargeFeeElement : chargeList){
					//we will use only mandatory fees
					//in API response  <option>R</option> R mean required
					if(chargeFeeElement.getChildText("option").equalsIgnoreCase("R")){
						Double feeAmountNotConverted = Double.valueOf(chargeFeeElement.getChildText("amount"));
//						if(feeAmountNotConverted==null || feeAmountNotConverted==0.0){
//							continue;
//						}
						
						Double feeAmount = PaymentHelper.getAmountWithTwoDecimals(feeAmountNotConverted * currencyRate);
						
						QuoteDetail chargeFeeQd = new QuoteDetail(String.valueOf(feeAmount), currency, chargeFeeElement.getChildText("priceText"), "Included in the price", "", true);
//						QuoteDetail chargeFeeQd = new QuoteDetail();
//						chargeFeeQd.setAmount(feeAmount+"");
//						chargeFeeQd.setDescription(chargeFeeElement.getChildText("priceText"));
//						chargeFeeQd.setIncluded(true);
						quoteDetails.add(chargeFeeQd);
						
						net.cbtltd.shared.Price chargeFeePrice = new net.cbtltd.shared.Price();
						chargeFeePrice = new net.cbtltd.shared.Price();
						chargeFeePrice.setEntitytype(NameId.Type.Reservation.name());
						chargeFeePrice.setEntityid(reservation.getId());
						String feeType = net.cbtltd.shared.Price.MANDATORY;
						//in API response  <option>R</option> R mean required
//						if(!chargeFeeElement.getChildText("option").equalsIgnoreCase("R")){
//							feeType = net.cbtltd.shared.Price.OPTIONAL;
//						}
						chargeFeePrice.setType(feeType);
						chargeFeePrice.setName(chargeFeeElement.getChildText("priceText"));
						chargeFeePrice.setState(net.cbtltd.shared.Price.CREATED);
						chargeFeePrice.setDate(version);
						chargeFeePrice.setQuantity(1.0);
						chargeFeePrice.setUnit(Unit.EA);
						chargeFeePrice.setValue(feeAmount);
						chargeFeePrice.setCurrency(reservation.getCurrency());
						reservation.getQuotedetail().add(chargeFeePrice);
					}
					
				}
			}
			
			//security deposit
			Double securityDepositNotConverted = Double.valueOf(unitElement.getChildText("securityDeposit"));
			if(securityDepositNotConverted > 0.0){
				Double securityDepositValue = PaymentHelper.getAmountWithTwoDecimals(securityDepositNotConverted * currencyRate);		
				
				QuoteDetail securityDepositValueQd = new QuoteDetail(String.valueOf(securityDepositValue), currency, "Security deposit", "Included in the price", "", true);
//				QuoteDetail securityDepositValueQd = new QuoteDetail();
//				securityDepositValueQd.setAmount(securityDepositValue+"");
//				securityDepositValueQd.setDescription("Security deposit");
//				securityDepositValueQd.setIncluded(true);
				quoteDetails.add(securityDepositValueQd);
				
				net.cbtltd.shared.Price securityDepositPrice = new net.cbtltd.shared.Price();
				securityDepositPrice = new net.cbtltd.shared.Price();
				securityDepositPrice.setEntitytype(NameId.Type.Reservation.name());
				securityDepositPrice.setEntityid(reservation.getId());
				securityDepositPrice.setType(net.cbtltd.shared.Price.MANDATORY);
				securityDepositPrice.setName("Security deposit");
				securityDepositPrice.setState(net.cbtltd.shared.Price.CREATED);
				securityDepositPrice.setDate(version);
				securityDepositPrice.setQuantity(1.0);
				securityDepositPrice.setUnit(Unit.EA);
				securityDepositPrice.setValue(securityDepositValue);
				securityDepositPrice.setCurrency(reservation.getCurrency());
				reservation.getQuotedetail().add(securityDepositPrice);
			}
			
			//they have tax1, tax2 and tax3, but for now we using only taxTotal
			Double totalTaxValueNotConverted = Double.valueOf(unitElement.getChildText("taxTotal"));
			Double totalTaxValue = PaymentHelper.getAmountWithTwoDecimals(totalTaxValueNotConverted * currencyRate);		
			
			QuoteDetail totalTaxesValueQd = new QuoteDetail(String.valueOf(totalTaxValue), currency, "Total taxes", "Included in the price", "", true);
//			QuoteDetail totalTaxesValueQd = new QuoteDetail();
//			totalTaxesValueQd.setAmount(totalTaxValue+"");
//			totalTaxesValueQd.setDescription("Total taxes");
//			totalTaxesValueQd.setIncluded(true);
			quoteDetails.add(totalTaxesValueQd);
			
			net.cbtltd.shared.Price totalTaxesPrice = new net.cbtltd.shared.Price();
			totalTaxesPrice = new net.cbtltd.shared.Price();
			totalTaxesPrice.setEntitytype(NameId.Type.Reservation.name());
			totalTaxesPrice.setEntityid(reservation.getId());
			totalTaxesPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
			totalTaxesPrice.setName("Total taxes");
			totalTaxesPrice.setState(net.cbtltd.shared.Price.CREATED);
			totalTaxesPrice.setDate(version);
			totalTaxesPrice.setQuantity(1.0);
			totalTaxesPrice.setUnit(Unit.EA);
			totalTaxesPrice.setValue(totalTaxValue);
			totalTaxesPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(totalTaxesPrice);
				
			
			//setting other fields
			Double totalPriceNotConverted = Double.valueOf(unitElement.getChildText("grandTotal"));
			Double totalPriceValue = PaymentHelper.getAmountWithTwoDecimals(totalPriceNotConverted * currencyRate);
			
			LOG.debug("Total price (converted): " +currency + totalPriceValue);
			
			Double depositNotConverted = Double.valueOf(unitElement.getChildText("depositDue"));
			Double depositValue = PaymentHelper.getAmountWithTwoDecimals(depositNotConverted * currencyRate);
			
//			Double extraPriceValue = PaymentHelper.getAmountWithTwoDecimals(totalPriceValue - propertyPriceValue);
			
			reservationPrice.setTotal(totalPriceValue);
			reservationPrice.setPrice(totalPriceValue);
			reservationPrice.setCurrency(reservation.getCurrency());
			reservationPrice.setQuoteDetails(quoteDetails);
			
			
//			reservation.setPrice(propertyPriceValue);
			reservation.setPrice(totalPriceValue);
			reservation.setQuote(totalPriceValue);
			reservation.setDeposit(depositValue);
//			reservation.setExtra(extraPriceValue);
			reservation.setExtra(0.0);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
			
			
		}else{
			String errorMessage = "Property not available. ";
			if(rootNodeUnitPrice.getChild("error")!=null){
				errorMessage += rootNodeUnitPrice.getChildText("error");
			}
					
			throw new ServiceException(Error.reservation_api, errorMessage);
			
		}
		
		return reservationPrice;
	}
	
	
	private Element createAndExecuteGetPriceRequest(SqlSession sqlSession, Reservation reservation, String altId) throws Throwable{
		String arrivalDate = this.formatDateToReservationRequest(reservation.getFromdate());
		String departureDate = this.formatDateToReservationRequest(reservation.getTodate());
		
		String reservationPriceGetUrl = RMS_API_URL + "unit/search?arrivalDate="+arrivalDate+
				"&departureDate="+departureDate+"&unitId="+altId+
				"&numberOfAdults="+reservation.getAdult()+"&numberOfChildren="+reservation.getChild();
		
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build(PartnerService.getInputStream(reservationPriceGetUrl, RMS_SERVER_AUTH));
		Element rootNodeUnitPrice = document.getRootElement();
		
		return rootNodeUnitPrice;
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		Date timestamp = new Date();
		String message = "RMS (Resort Management System) createReservationAndPayment, resrvationId:" + reservation.getId();
		LOG.debug(message);
		
		Map<String, String> result = new HashMap<String, String>();
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

			//this is not needed any more
//			double oldQuote = reservation.getQuote();
			
			//first we need to run get unit price request one more time, 
			//because all prices need to be included in booking request
			Element rootNodeUnitPrice = this.createAndExecuteGetPriceRequest(sqlSession, reservation, product.getAltid());
			//we do this just to fill one more time reservation, to do price comparation.
			this.fillResultsAfterGetPrice(sqlSession, reservation, reservation.getCurrency(), rootNodeUnitPrice, product);
//			if(oldQuote != reservation.getQuote()) {
//				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
//			}
			
			Element unitElement = rootNodeUnitPrice.getChild("units").getChild("unit");
			
			
			String altId = product.getAltid();
			String arrivalDate = this.formatDateToReservationRequest(reservation.getFromdate());
			String departureDate = this.formatDateToReservationRequest(reservation.getTodate());
			
			//customer data
			String firstName = (!customer.noFirstName())? customer.getFirstName() : "";
			String lastName = (!customer.noFamilyName())? customer.getFamilyName() : "";
			String addressLine1 = (!this.checkIfValueNullOrEmpty(customer.getLocalAddress())) ? customer.getLocalAddress() : "";
			String city = (!this.checkIfValueNullOrEmpty(customer.getCity())) ? customer.getCity() : "City"; //city is mandatory 
			String state = (customer.getRegion()!=null) ? customer.getRegion() : ""; 
			String zipCode = (customer.getPostalcode()!=null)? customer.getPostalcode() : "";
			//TODO check this maybe this is only address, not country
			String foreignAddress = (!customer.noCountry())? customer.getCountry() : "";
			String cellPhone = (customer.getMobilephone()!=null)? customer.getMobilephone() : ""; 
			String homePhone = (customer.getNightphone()!=null)? customer.getNightphone() : ""; 
			String officePhone = (customer.getDayphone()!=null)? customer.getDayphone() : ""; 
			String faxPhone = (customer.getFaxphone()!=null)? customer.getFaxphone() : ""; 
			String email = (!customer.noEmailaddress())? customer.getEmailaddress() : ""; 
			
			//API require at least one phone. So if phones not exist we add phone from agent
			if(this.checkIfValueNullOrEmpty(cellPhone) && this.checkIfValueNullOrEmpty(homePhone)){
				cellPhone = (agent.getMobilephone()!=null)? agent.getMobilephone() : ""; 
				homePhone = (agent.getNightphone()!=null)? agent.getNightphone() : ""; 
			}
			
			String notePrefix = "Customer comment:";
			String note = (reservation.getNotes()!=null)? reservation.getNotes() : "";
			
			String resStatusCode = rootNodeUnitPrice.getChildText("reservationStatus");
			String guestRent = unitElement.getChildText("rateTotal");
			String taxTotal = unitElement.getChildText("taxTotal");
			String tax1 = unitElement.getChildText("tax1");
			String tax2 = unitElement.getChildText("tax2");
			String tax3 = unitElement.getChildText("tax3");
			String securityDeposit = unitElement.getChildText("securityDeposit");
			String grandTotal = unitElement.getChildText("grandTotal");
			String depositDue = unitElement.getChildText("depositDue");
			
			String resOperator = "BookingPal";
			
			String depositType = "CREDITCARD";
			String ccExpiration = creditCard.getMonth() + "-" + creditCard.getYear();
			String ccName = firstName + " " + lastName;
			String ccNumber = creditCard.getNumber();
			String ccSecurityCode = creditCard.getSecurityCode();
			
			
			//now create booking request
			String bookGetUrl = RMS_API_URL + "unit/book?" +
					"reservation.statusCode="+resStatusCode+
					"&reservation.arrival="+arrivalDate+
					"&reservation.departure="+departureDate+
					"&reservation.unitId="+altId+
					"&reservation.numberOfAdults="+reservation.getAdult()+
					"&reservation.numberOfChildren="+reservation.getChild()+
					"&reservation.firstName="+encodeParam(firstName)+
					"&reservation.lastName="+encodeParam(lastName)+
					"&reservation.addressLine1="+encodeParam(addressLine1)+
					"&reservation.city="+encodeParam(city)+
					"&reservation.state="+encodeParam(state)+
					"&reservation.zipCode="+encodeParam(zipCode)+
					"&reservation.foreignAddress="+encodeParam(foreignAddress)+
					"&reservation.cellPhone="+encodeParam(cellPhone)+
					"&reservation.homePhone="+encodeParam(homePhone)+
					"&reservation.officePhone="+encodeParam(officePhone)+
					"&reservation.faxPhone="+encodeParam(faxPhone)+
					"&reservation.email="+encodeParam(email)+
					"&reservation.notePrefix="+encodeParam(notePrefix)+
					"&reservation.note="+encodeParam(note)+
					"&reservation.guestRent="+guestRent+
					"&reservation.taxTotal="+taxTotal+
					"&reservation.tax1="+tax1+
					"&reservation.tax2="+tax2+
					"&reservation.tax3="+tax3+
					"&reservation.securityDeposit="+securityDeposit+
					"&reservation.grandTotal="+grandTotal+
					"&reservation.depositDue="+depositDue+
					"&reservation.depositType="+depositType+
					"&reservation.operator="+encodeParam(resOperator)+
					"&creditCard.expiration="+encodeParam(ccExpiration)+
					"&creditCard.name="+encodeParam(ccName)+
					"&creditCard.number="+ccNumber+
					"&creditCard.securityCode="+ccSecurityCode+
					"";
			
			/*
			 String bookGetUrl = RMS_API_URL + "unit/book?" +
					"reservation.statusCode="+resStatusCode+
					"&reservation.arrival="+arrivalDate+
					"&reservation.departure="+departureDate+
					"&reservation.unitId="+altId+
					"&reservation.numberOfAdults="+reservation.getAdult()+
					"&reservation.numberOfChildren="+reservation.getChild()+
					"&reservation.firstName="+firstName+
					"&reservation.lastName="+lastName+
					"&reservation.addressLine1="+addressLine1+
					"&reservation.city="+city+
					"&reservation.state="+state+
					"&reservation.zipCode="+zipCode+
					"&reservation.foreignAddress="+foreignAddress+
					"&reservation.cellPhone="+cellPhone+
					"&reservation.homePhone="+homePhone+
					"&reservation.officePhone="+officePhone+
					"&faxPhone="+faxPhone+
					"&reservation.email="+email+
					"&reservation.notePrefix="+notePrefix+
					"&reservation.note="+note+
					"&reservation.guestRent="+guestRent+
					"&reservation.taxTotal="+taxTotal+
					"&reservation.tax1="+tax1+
					"&reservation.tax2="+tax2+
					"&reservation.tax3="+tax3+
					"&reservation.securityDeposit="+securityDeposit+
					"&reservation.grandTotal="+grandTotal+
					"&reservation.depositDue="+depositDue+
					"&reservation.depositType="+depositType+
					"&reservation.operator="+resOperator+
					"&creditCard.expiration="+ccExpiration+
					"&creditCard.name="+ccName+
					"&creditCard.number="+ccNumber+
					"&creditCard.securityCode="+ccSecurityCode+
					"";
			 */
			
			//additional fees
			if(unitElement.getChild("charges")!=null && unitElement.getChild("charges").getChildren("charge")!=null){
				List<Element> chargesList = unitElement.getChild("charges").getChildren("charge");
				for(Element chargeElement : chargesList){
					String chargeId = chargeElement.getChildText("id");
					String chargeAmount = chargeElement.getChildText("amount");
					String chargeOption = chargeElement.getChildText("option");
					String chargeEnabled = chargeElement.getChildText("enabled");
					//if charge enabled and not required, than we set that option to false
					if(chargeEnabled.equalsIgnoreCase("true") && !chargeOption.equalsIgnoreCase("R")){
						chargeEnabled = "false";
					}
					
					/*
					//FIXME remove this later
					//this is some small error in API
					//in case of cleaning charge, charge is 120.0, and later charge is 120.07?!
					if(chargeId.equalsIgnoreCase("2")){
						chargeAmount = "120.07";
					}
					*/
					
					bookGetUrl += "&charge.id="+chargeId+
							"&charge.amount="+chargeAmount+
							"&charge.enabled="+chargeEnabled;
					
				}
			}
			
			LOG.debug("Book request:"+bookGetUrl);
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(PartnerService.getInputStream(bookGetUrl, RMS_SERVER_AUTH));
			Element rootNodeBook = document.getRootElement();
			
			if(rootNodeBook.getChildText("booked")!=null &&rootNodeBook.getChildText("booked").equalsIgnoreCase("true")){
				reservation.setAltid(rootNodeBook.getChildText("reservationId"));
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
			}else{
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				String errorDescription = "";
				if(rootNodeBook.getChildText("message")!=null){
					errorDescription = rootNodeBook.getChildText("message");
				}
				result.put(GatewayHandler.ERROR_MSG, errorDescription);
				return result;
			}
			
		}
		catch (ServiceException e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			throw new ServiceException(e.getError(), e.getMessage());	
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
		return result;
	}
	
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) createReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) confirmReservation(), reservationId="+reservation.getId());
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) updateReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) cancelReservation(), reservationId="+reservation.getId());

	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readAlerts()");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readPrices() {
		Date version = new Date();
		String message = "readPrices RMS (Resort Management System)";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
//			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder parser = new SAXBuilder();
			Document documentUnitList = parser.build(PartnerService.getInputStream(UNIT_LIST_URL, RMS_SERVER_AUTH));
			Element rootNodeUnitList = documentUnitList.getRootElement();
			
			List<Element> units = rootNodeUnitList.getChildren("unit");
			for(Element unit : units){
				String altId = unit.getChildText("unitId");
				LOG.debug("ReadPrice unitID="+altId);
				
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
				if (product == null) {
					LOG.error(Error.product_altid.getMessage() + " " + altId);
//					System.out.println("NOT EXIST property ID: " + altId);
					continue;
				}
				
				Document documentUnitRates = parser.build(PartnerService.getInputStream(this.getUnitDetailsLink(altId), RMS_SERVER_AUTH));
				Element rootNodeUnitRates = documentUnitRates.getRootElement();
				List<Element> unitRatesList = rootNodeUnitRates.getChild("unit").getChild("rates").getChildren("rate");
				for(Element unitRate : unitRatesList){
					Price price = new Price();
					price.setEntityid(product.getId());
					price.setEntitytype(NameId.Type.Product.name());
					price.setPartyid(getAltpartyid());
					price.setName(Price.RACK_RATE);
					price.setType(NameId.Type.Reservation.name());
					String startDateString = unitRate.getChildText("startDate");
					Date fromdate =  dateFormaterFromResponse.parse(startDateString);
					price.setDate(fromdate);
					String endDateString = unitRate.getChildText("endDate");
					Date todate =  dateFormaterFromResponse.parse(endDateString);
					price.setTodate(todate);
					price.setQuantity(1.0);
					price.setCurrency(product.getCurrency());
					price.setUnit(Unit.DAY);

					Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
					if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
					else {price = exists;}
										
					
					price.setState(Price.CREATED);
					//FIXME check how to set few days in week
					price.setRule(Price.Rule.AnyCheckIn.name());
					
					//TODO they have weekly rate. What we should do??
					Integer minStay = 0;
					Double rateValue = null;
					try{minStay = Integer.parseInt(unitRate.getChildText("minimumNightStay"));}catch(Exception parseExc){}
					try{rateValue = Double.valueOf(unitRate.getChildText("dailyRate"));}catch(Exception parseExc){}
					
					Double minimumValue = minStay * rateValue;
					
					price.setValue(rateValue);
					price.setCost(rateValue);
					price.setMinStay(minStay);
					price.setMinimum(minimumValue);
					price.setAvailable(1);
					price.setVersion(version);
					sqlSession.getMapper(PriceMapper.class).update(price);
					sqlSession.getMapper(PriceMapper.class).cancelversion(price);
					
					sqlSession.commit();
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
	
	
	private String getUnitDetailsLink(String unitId){
		return RMS_API_URL + "unit/show/" + unitId;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void readProducts() {
		String message = "readProducts RMS (Resort Management System) ";
		LOG.debug(message);
		Date version = new Date();
		
		StringBuilder sbNotKnowLocation = new StringBuilder();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder parser = new SAXBuilder();
			Document documentUnitList = parser.build(PartnerService.getInputStream(UNIT_LIST_URL, RMS_SERVER_AUTH));
			Element rootNodeUnitList = documentUnitList.getRootElement();
			
			List<Element> units = rootNodeUnitList.getChildren("unit");
			for(Element unit : units){
				//now we need to load unit (property) details for every unit
				String altId = unit.getChildText("unitId");
				LOG.debug("Read unitID="+altId);
				
				Document documentUnitDetails = parser.build(PartnerService.getInputStream(this.getUnitDetailsLink(altId), RMS_SERVER_AUTH));
				Element rootNodeUnitDetails = documentUnitDetails.getRootElement();
				Element unitDetails = rootNodeUnitDetails.getChild("unit");
				
				//this we read one more time, just to be sure. But that should be same.
				altId = unitDetails.getChildText("unitId");
				
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId);
				if (product == null) {
					continue;
				}
				
				Integer roomNumber = 0;
				Integer bathroomNumber = 0;
				Integer personNumber = 0;
				Double latitude = null;
				Double longitude = null;
				Double cleaningFee = 0.0;
				try{roomNumber = Integer.parseInt(unitDetails.getChildText("numberOfBedrooms"));}catch(Exception parseExc){}
				try{bathroomNumber = Integer.parseInt(unitDetails.getChildText("numberOfBathrooms"));}catch(Exception parseExc){}
				try{personNumber = Integer.parseInt(unitDetails.getChildText("maximumNumberOfPeople"));}catch(Exception parseExc){}
				try{latitude = Double.valueOf(unitDetails.getChildText("latitude"));}catch(Exception parseExc){}
				try{longitude = Double.valueOf(unitDetails.getChildText("longitude"));}catch(Exception parseExc){}
				try{cleaningFee = Double.valueOf(unitDetails.getChildText("cleaningCharge"));}catch(Exception parseExc){}
				
				Element unitAddress = unitDetails.getChild("address");
				StringBuilder physicalAddress = new StringBuilder();
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("line1"))) {physicalAddress.append(unitAddress.getChildText("line1")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("line2"))) {physicalAddress.append(unitAddress.getChildText("line2")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("state"))) {physicalAddress.append(unitAddress.getChildText("state")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("zipCode"))) {physicalAddress.append(unitAddress.getChildText("zipCode")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("region"))) {physicalAddress.append(unitAddress.getChildText("region")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("subRegion"))) {physicalAddress.append(unitAddress.getChildText("subRegion")).append("\n");}
				if (!checkIfValueNullOrEmpty(unitAddress.getChildText("country"))) {physicalAddress.append(unitAddress.getChildText("country")).append("\n");}
				
				product.setCurrency(unitDetails.getChild("details").getChildText("currency"));
				product.setName(unitDetails.getChildText("type") +", "+ unitDetails.getChildText("level") +", "+ unitDetails.getChildText("localAddress"));
				product.setUnit(Unit.DAY);
				product.setRoom(roomNumber);
				product.setBathroom(bathroomNumber);
				product.setQuantity(1);
				product.setPerson(personNumber);
				product.setRank(getRank());
				product.setLatitude(latitude);
				product.setLongitude(longitude);
				product.setPhysicaladdress(physicalAddress.toString());
				product.setCleaningfee(cleaningFee);
				
				product.setWebaddress(getWebaddress());
				product.setCommission(getCommission());
				product.setDiscount(getDiscount());
				product.setRating(5);
				product.setAltitude(0.0);
				product.setChild(0);
				product.setInfant(0);
				product.setToilet(0);
				
				product.setVersion(version);
				
				String countryISO = unitAddress.getChildText("country");
				String state = unitAddress.getChildText("state");
				String city = unitAddress.getChildText("city");
				String latitudeStr = unitDetails.getChildText("latitude");
				String longitudeStr = unitDetails.getChildText("longitude");
				
				Location location = null;
				if(!(checkIfValueNullOrEmpty(latitudeStr) || checkIfValueNullOrEmpty(longitudeStr))){
					location = PartnerService.getLocation(sqlSession, 
							city,
							state,
							countryISO, 
							checkIfValueNullOrEmpty(latitudeStr) ? null : Double.valueOf(latitudeStr), 
							checkIfValueNullOrEmpty(longitudeStr) ? null : Double.valueOf(longitudeStr));
				}else{
					location = PartnerService.getLocation(sqlSession, 
							city,
							state,
							countryISO);
				}
				
				
				
				if(location!=null && location.getId()!=null){
					product.setLocationid(location.getId());
				}else{
					sbNotKnowLocation.append("\n").append("RMS (Resort Management System) property: " + altId + " country: " + countryISO + " city: " + city);
					product.setState(Product.SUSPENDED);
				}
				
				//description build
				//TODO check maybe with real data to add fields locationDescription and sectionDescription
				StringBuilder description = new StringBuilder();
				description.append(unitDetails.getChildText("level")).append("\n\n");
				description.append(unitDetails.getChildText("description")).append("\n\n");
				
				
				ArrayList<String> attributes = new ArrayList<String>();
				System.out.println("Type: " + unitDetails.getChildText("type"));
				addType(attributes, unitDetails.getChildText("type"));
				
				List<Element> attributesList = unitDetails.getChild("miscellaneousFields").getChildren(); 
				for (Element attribute : attributesList){
					String exist = attribute.getAttribute("field").getValue();
					addPropertyAttribute(attributes, attribute.getValue(), exist);
				}
				
				//in search fields there is also some attributes. Generally this attributes
				//are present in regular attributes (miscellaneousFields) but some are not
				List<Element> searchAattributesList = unitDetails.getChild("searchFields").getChildren(); 
				for (Element attribute : searchAattributesList){
					String exist = attribute.getAttribute("description").getValue();
					if(attribute.getValue().equalsIgnoreCase("Internet") && isAttributeExist(exist)){
						attributes.add("RMA54"); //Internet access
					}else if(attribute.getValue().equalsIgnoreCase("Smoking")){
						if(isAttributeExist(exist)){
							attributes.add("RMA101");//smoking YES
						}else{
							attributes.add("RMA74");//smoking NO
						}
					}else if(attribute.getValue().equalsIgnoreCase("Pets Allowed")){
						if(isAttributeExist(exist)){
							attributes.add("PET10"); //PET10 = Domestic pets
							description.append("Pets are allowed.").append("\n\n");
						}else{
							description.append("Pets are not allowed.").append("\n\n");
						}
					}
				}
			
				sqlSession.getMapper(ProductMapper.class).update(product);
				
				product.setPublicText(new Text(product.getPublicId(), product.getName(), Text.Type.HTML, new Date(), description.toString(), Language.EN));
				TextService.update(sqlSession, product.getTexts());
				
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
				RelationService.removeDeprecatedData(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
				
				sqlSession.commit();
				
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
			x.printStackTrace();
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
		String message = "readImages RMS (Resort Management System)";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder parser = new SAXBuilder();
			Document documentUnitList = parser.build(PartnerService.getInputStream(UNIT_LIST_URL, RMS_SERVER_AUTH));
			Element rootNodeUnitList = documentUnitList.getRootElement();
						
			List<Element> units = rootNodeUnitList.getChildren("unit");
			for(Element unit : units){
				String altId = unit.getChildText("unitId");				
				Document documentUnitDetails = parser.build(PartnerService.getInputStream(this.getUnitDetailsLink(altId), RMS_SERVER_AUTH));
				Element rootNodeUnitDetails = documentUnitDetails.getRootElement();
				Element unitDetails = rootNodeUnitDetails.getChild("unit");
			
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
					if (product == null) {continue;}
					List<Element> imageList = unitDetails.getChild("photos").getChildren("photo"); 
					if (HasUrls.LIVE && imageList != null && imageList.size()>0) {
						ArrayList<NameId> images = new ArrayList<NameId>();
						for (Element imgElement : imageList) {
							images.add(new NameId("", imgElement.getChildText("url")));
						}
						UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
					}
					sqlSession.commit();
				}
				catch (Throwable x) {
					LOG.error(x.getMessage()); 
					x.printStackTrace();
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
		String message = "readSchedule RMS (Resort Management System)";
		
		Date startDate = Time.getDateStart();
		Date endDate = Time.addDuration(startDate, SCHEDULES_FOR_NUMBER_DAYS_IN_FUTURE, Time.DAY);

		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			String depStart = this.formatDateToCronRequest(startDate);
			String depEnd = this.formatDateToCronRequest(endDate);
			
			String reservationGetUrl = RMS_API_URL + "reservation/list?departureStart="+depStart+"&departureEnd="+depEnd;
			
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(PartnerService.getInputStream(reservationGetUrl, RMS_SERVER_AUTH));
			Element rootNode = document.getRootElement();
			List<Element> reservationList = rootNode.getChildren("reservation");
			for(Element reservationElement : reservationList){
				String altId = reservationElement.getChildText("unitId");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
				if (product == null) {continue;}
				
				try {
					String arrivalDateString = reservationElement.getChildText("arrival");
					Date fromDate = dateFormaterFromResponse.parse(arrivalDateString);
					String departureDateString = reservationElement.getChildText("departure");
					Date toDate = dateFormaterFromResponse.parse(departureDateString);
					PartnerService.createSchedule(sqlSession, product, fromDate, toDate, version);
					PartnerService.cancelSchedule(sqlSession, product, version);
					
				}catch (Throwable x) {
					LOG.error(x.getMessage()); 
					x.printStackTrace();
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

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readSpecials()");

	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readDescriptions()");

	}

	

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readAdditionCosts()");

	}
	
	private static HashMap<String, String> TYPES = null;
	/**
	 * Adds the type. We use Maxxton codes here for mapping
	 * sc=Mobilhome, bg=bungalow, be=bungalow eigenaar, log=Apartment, beachhouse=Beach Houses, 
	 * co=contractanten(this is not real type. They use this only for test. Not need to us), 
	 * vt=holidaytent, ho=hotel, wc=white camp cottage,ch=chalet, kp=camp-site, ap=appartement
	 *
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("Condo","PCT8"); 
			TYPES.put("Home","PCT35"); //PCT35 = Vila??? 
		}
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
	}
	
	
	private static HashMap<String,String> PROPERTY_ATTRIBUTES = null;
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	
	//TODO change this when start use real API 
	private static final void addPropertyAttribute(ArrayList<String> attributes, String attribute, String exist) {

		if (PROPERTY_ATTRIBUTES == null) {
			PROPERTY_ATTRIBUTES = new HashMap<String, String>();
			PROPERTY_ATTRIBUTES.put("Washer", "RMA31"); //Washing Machine
			PROPERTY_ATTRIBUTES.put("Dryer", "RMA149"); //TODO actually thay have separate washer(up) and dryer and this attribute is Washer and Dryer
			PROPERTY_ATTRIBUTES.put("Dishwasher", "RMA32"); 
			PROPERTY_ATTRIBUTES.put("Game Table", "HAC44"); //in our system this is attitbure Games room?? 
			PROPERTY_ATTRIBUTES.put("Pool", "HAC71"); 
			PROPERTY_ATTRIBUTES.put("Heated Pool", "HAC49"); 
			PROPERTY_ATTRIBUTES.put("Fireplace", "RMA41"); 
			PROPERTY_ATTRIBUTES.put("Hot Tub", "RST104"); 
			PROPERTY_ATTRIBUTES.put("Wood Burning Stove", "RMA105"); 
			PROPERTY_ATTRIBUTES.put("Deck", "RMA7"); //balcony/terrace
			PROPERTY_ATTRIBUTES.put("Screened Porch", "RMA7"); //balcony/terrace
			PROPERTY_ATTRIBUTES.put("Flat Screen TV", "RMA246"); 
			PROPERTY_ATTRIBUTES.put("Hunting Equipment", "RST105"); //Hunting
			PROPERTY_ATTRIBUTES.put("Air Conditioning", "RMA2"); 
			PROPERTY_ATTRIBUTES.put("Stocked Bar", "RMA69"); 
			PROPERTY_ATTRIBUTES.put("Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Coffee Maker", "CBF85"); 
			PROPERTY_ATTRIBUTES.put("Fenced in Yard", "HAC25"); //Courtyard in our system?? 
			PROPERTY_ATTRIBUTES.put("Security System", "EQP60"); //Security devices in our system?? 
			
			
		}
		
		if (isAttributeExist(exist) && PROPERTY_ATTRIBUTES.get(attribute) != null) {
			attributes.add(PROPERTY_ATTRIBUTES.get(attribute));
		}
	}
	
	private static boolean isAttributeExist(String exist){
		boolean result = false;
		if(exist.equalsIgnoreCase("Yes")){
			result = true;
		}
		
		return result;
	}
	
	
	private String encodeParam(String inputParameter) throws Throwable{
		return URLEncoder.encode(inputParameter, "UTF-8");
	}
	
	
	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) readLocations()");
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "RMS (Resort Management System) inquireReservation()");
	}

}