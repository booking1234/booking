/**
 * @author	Marko Ovuka
 * @see		License at http://razorpms.com/razor/License.html
 * @version	1.00
 */
package net.cbtltd.rest.streamline;

import java.io.BufferedReader;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.FeeService;
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
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.FeeMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * Class A_Handler manages the Streamline API Company code for testing:
 * 6f902e1f18fe45c2a8c5b831e81121c
 * 
 * @author Marko Ovuka
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("MM/dd/yyyy");
	
	private static final DateFormat responseDF = new SimpleDateFormat("yyyy-MM-dd");
	
	//this is 100 years value, to avoid often updating
	private static final int FEES_PRICES_FOR_NUMBER_DAYS_IN_FUTURE = 365000;
	private static final int FEES_PRICES_UPDATE_DAYS= 365;

	private static final String STREAMLINE_API_URL = "https://www.resortpro.net/api/1.1";
	private static final String STREAMLINE_PRICES_AND_RESERVATIONS_XML_FEED_URL = "http://www.resortpro.net/xml/pricesAndReservations.php";
	
	private static final String CURRENCY_IN_STREAMLINE = Currency.Code.USD.name();
	
	private static final String PRICING_MODEL = "0"; //0 for auto detect
	
	private static final String OPTIONAL_DEFAULT_ENABLED_FEE_TAX = "optional_default_enabled";
	
	private static final String DEFAULT_CHANNEL_PARTNER_NAME = "BookingPal";
	//sometimes during live pricing or when we creating booking Streamline auto apply some discount
	//this will be avoided when we always send some false discount code.
	private static final String DO_NOT_USE_DISCOUNT_CODE="<coupon_code>mybookingpal_needs_pricing_without_a_discount</coupon_code>";
	
	private static Map<String, Location> locationMap = new HashMap<String, Location>();
	
	
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

	/**
	 * Gets the connection to the Streamline server and executes the specified
	 * request.
	 * 
	 * @param url
	 *            the connection URL.
	 * @param rq
	 *            the request object.
	 * @return the XML string returned by the message.
	 * @throws Throwable
	 *             the exception thrown by the operation.
	 */
	private static final String getApiConnection(String rq) throws Throwable {
		StringBuilder stringBuilder = new StringBuilder();
		HttpURLConnection connection = null;
		try {
			URL url = new URL(STREAMLINE_API_URL); 
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
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Throwable x) {
			throw new RuntimeException(x.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return stringBuilder.toString();
	}
	
	private static final String getXmlFeedConnection(SqlSession sqlSession, String altPartyId) throws Throwable {
		StringBuilder stringBuilder = new StringBuilder();
		HttpURLConnection connection = null;
		try {
			long functionStart= System.currentTimeMillis();
			Party party = sqlSession.getMapper(PartyMapper.class).read(altPartyId);
			URL url = new URL(STREAMLINE_PRICES_AND_RESERVATIONS_XML_FEED_URL+"?companycode="+party.getAltid()); 
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/xml");


			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			long bufferingEnd= System.currentTimeMillis();
			LOG.debug("Accessing API data takes: "+ (bufferingEnd-functionStart) + "ms"); 
			
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
			LOG.debug("Reading Lines from response takes: "+ (System.currentTimeMillis()-bufferingEnd) + "ms"); 
		} catch (Throwable x) {
			throw new RuntimeException(x.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Assistant method for creating XML request
	 * Because some parameters are same in every request
	 * @param apiFunction
	 * @param otherParams
	 * @return
	 * @throws Throwable
	 */
	private String createXMLRequestToStreamline(SqlSession sqlSession, String apiFunction, String otherParams) throws Throwable{
		//finding PM code (this is on field party.altId)
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version='1.0' ?>");
		sb.append("<methodCall>");
		sb.append("  <methodName>" + apiFunction + "</methodName>");
		sb.append("  <params>");
		sb.append("    <company_code>" + party.getAltid() + "</company_code>");
		sb.append( otherParams);
		sb.append("  </params>");
		sb.append("</methodCall>");
		
		String request = sb.toString();
//		LOG.debug(apiFunction+" req=" + request + "\n");
		
		String response = getApiConnection(request);
//		LOG.debug(apiFunction+" res=" + response + "\n");
		
		return response;
	}
	
	 
	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "isAvailable Streamline (Altpartyid:"+this.getAltpartyid()+"), reservationId=" + reservation.getId();
		LOG.debug(message);
		try {
			String altId = PartnerService.getProductaltid(sqlSession, reservation);
		
			String requestAvailabitlity = "<unit_id>"+altId+"</unit_id>" +
											"<startdate>"+DF.format(reservation.getFromdate())+"</startdate>" +
											"<enddate>"+DF.format(reservation.getTodate())+"</enddate>"+
											"<occupants>"+reservation.getAdult()+"</occupants>";
			String responseAvailability = createXMLRequestToStreamline(sqlSession,"VerifyPropertyAvailability",requestAvailabitlity); 
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseAvailability));
			Element rootNode = document.getRootElement();				
			
			if(rootNode.getChild("data")!=null && rootNode.getChild("status")==null){
				isAvailableResult = true;
			}else if(rootNode.getChild("status")!=null && rootNode.getChild("status").getChildText("description")!=null){
				throw new ServiceException(Error.reservation_api, rootNode.getChild("status").getChildText("description"));
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
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Streamline createReservation()");
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String message = "readPrice Streamline (Altpartyid:"+this.getAltpartyid()+") productAltId:" + productAltId;
		LOG.debug(message);
		ReservationPrice result = new ReservationPrice();
		Date timestamp = new Date();
		try {
			result = computePrice(sqlSession, reservation, productAltId, currency, timestamp);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}
		
		MonitorService.monitor(message, timestamp);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency, Date version) throws Throwable{
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		
		reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
		reservation.setCurrency(currency);
		reservationPrice.setCurrency(currency);
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			
		String reqPreReservationPrice = "<unit_id>"+product.getAltid()+"</unit_id>" +
											"<startdate>"+DF.format(reservation.getFromdate())+"</startdate>" +
											"<enddate>"+DF.format(reservation.getTodate())+"</enddate>"+
											"<occupants>"+reservation.getAdult()+"</occupants>"+
											"<occupants_small>"+reservation.getChild()+"</occupants_small>"+
											"<show_breakdowns>yes</show_breakdowns>"+
											DO_NOT_USE_DISCOUNT_CODE;
		
		//now we need to find all optional tax and fees which are included by default to turn off them.
		Price actionPrice = new Price();
		actionPrice.setType(Tax.Type.SalesTaxExcluded.name());
		actionPrice.setEntityid(product.getId());
		actionPrice.setDate(new Date(0));
		ArrayList <Tax> productTaxesFromDB = sqlSession.getMapper(TaxMapper.class).taxdetail(actionPrice);
		for(Tax currProductTax : productTaxesFromDB){
			if(currProductTax.getState().equalsIgnoreCase(Tax.MandatoryType.OptionalTax.name()) && currProductTax.getOptionValue()!=null
					&& currProductTax.getOptionValue().equalsIgnoreCase(OPTIONAL_DEFAULT_ENABLED_FEE_TAX)){
				reqPreReservationPrice += "<optional_fee_"+currProductTax.getAltId()+">no</optional_fee_"+currProductTax.getAltId()+">";
			}
		}
		
		Fee actionFee = new Fee();
		actionFee.setProductId(product.getId());
		actionFee.setPartyId(product.getAltpartyid());
		actionFee.setState(Fee.CREATED);
		ArrayList <Fee> productFeesFromDB = sqlSession.getMapper(FeeMapper.class).readbyproductandstate(actionFee);
		for(Fee currProductFee : productFeesFromDB){
			if(currProductFee.isTypeOptional() && currProductFee.getOptionValue()!=null && 
					currProductFee.getOptionValue().equalsIgnoreCase(OPTIONAL_DEFAULT_ENABLED_FEE_TAX)){
				reqPreReservationPrice += "<optional_fee_"+currProductFee.getAltId()+">no</optional_fee_"+currProductFee.getAltId()+">";
			}
		}
		
		String responsePreReservationPrice = createXMLRequestToStreamline(sqlSession,"GetPreReservationPrice",reqPreReservationPrice); 
		
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document) builder.build(new StringReader(responsePreReservationPrice));
		Element rootNode = document.getRootElement();	
		
		if(rootNode.getChild("data")==null){throw new ServiceException(Error.reservation_api);}
		
		Element priceDetails = rootNode.getChild("data");
		Double propertyPriceValue = 0.0;
		try{propertyPriceValue = Double.valueOf(priceDetails.getChildText("price"));}catch(Exception parseExc){}
		
		Double totalPriceValue = 0.0;
		try{totalPriceValue = Double.valueOf(priceDetails.getChildText("total"));}catch(Exception parseExc){}
		
		Double depositValue = 0.0;
		try{depositValue = Double.valueOf(priceDetails.getChildText("due_today"));}catch(Exception parseExc){}
		
		Double taxesAndFeesValue = 0.0;
		try{taxesAndFeesValue = Double.valueOf(priceDetails.getChildText("taxes"));}catch(Exception parseExc){}
		
		String propertyCurrency = product.getCurrency();
		Double currencyRate = WebService.getRate(sqlSession, propertyCurrency, currency, new Date());

		if(!propertyCurrency.equalsIgnoreCase(currency)){ 
			
			totalPriceValue = PaymentHelper.getAmountWithTwoDecimals(totalPriceValue * currencyRate);
			propertyPriceValue = PaymentHelper.getAmountWithTwoDecimals(propertyPriceValue * currencyRate);
			depositValue = PaymentHelper.getAmountWithTwoDecimals(depositValue * currencyRate);
			taxesAndFeesValue = PaymentHelper.getAmountWithTwoDecimals(taxesAndFeesValue * currencyRate);
		}
		
		//property rate
		//for this we do not need qoute detail
//		QuoteDetail propertyPriceQd = new QuoteDetail();
//		propertyPriceQd.setAmount(propertyPriceValue+"");
//		propertyPriceQd.setDescription("Property Price: ");
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
		
		//fees
		List<Element> feeList = priceDetails.getChildren("required_fees");
		for(Element feeElement : feeList){
			Double feeAmountValue = 0.0;
			try{feeAmountValue = Double.valueOf(feeElement.getChildText("value"));}catch(Exception parseExc){}
			
			feeAmountValue = PaymentHelper.getAmountWithTwoDecimals(feeAmountValue * currencyRate);
			
			QuoteDetail chargeFeeQd = new QuoteDetail(String.valueOf(feeAmountValue), currency, feeElement.getChildText("name"), "Included in the price", "", true);
			quoteDetails.add(chargeFeeQd);
			
			net.cbtltd.shared.Price chargeFeePrice = new net.cbtltd.shared.Price();
			chargeFeePrice = new net.cbtltd.shared.Price();
			chargeFeePrice.setEntitytype(NameId.Type.Reservation.name());
			chargeFeePrice.setEntityid(reservation.getId());			
			chargeFeePrice.setType(net.cbtltd.shared.Price.MANDATORY);
			chargeFeePrice.setName(feeElement.getChildText("name"));
			chargeFeePrice.setState(net.cbtltd.shared.Price.CREATED);
			chargeFeePrice.setDate(version);
			chargeFeePrice.setQuantity(1.0);
			chargeFeePrice.setUnit(Unit.EA);
			chargeFeePrice.setValue(feeAmountValue);
			chargeFeePrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(chargeFeePrice);
		}
		
		
		//taxes
		List<Element> taxList = priceDetails.getChildren("taxes_details");
		for(Element taxElement : taxList){
			Double taxAmountValue = 0.0;
			try{taxAmountValue = Double.valueOf(taxElement.getChildText("value"));}catch(Exception parseExc){}
			
			taxAmountValue = PaymentHelper.getAmountWithTwoDecimals(taxAmountValue * currencyRate);
			
			QuoteDetail chargeTaxQd = new QuoteDetail(String.valueOf(taxAmountValue), currency, taxElement.getChildText("name"), "Included in the price", "", true);
			quoteDetails.add(chargeTaxQd);
			
			net.cbtltd.shared.Price chargeTaxPrice = new net.cbtltd.shared.Price();
			chargeTaxPrice = new net.cbtltd.shared.Price();
			chargeTaxPrice.setEntitytype(NameId.Type.Reservation.name());
			chargeTaxPrice.setEntityid(reservation.getId());			
			chargeTaxPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
			chargeTaxPrice.setName(taxElement.getChildText("name"));
			chargeTaxPrice.setState(net.cbtltd.shared.Price.CREATED);
			chargeTaxPrice.setDate(version);
			chargeTaxPrice.setQuantity(1.0);
			chargeTaxPrice.setUnit(Unit.EA);
			chargeTaxPrice.setValue(taxAmountValue);
			chargeTaxPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(chargeTaxPrice);
		}
		
		reservationPrice.setPrice(propertyPriceValue);
		reservationPrice.setTotal(totalPriceValue);
		reservationPrice.setQuoteDetails(quoteDetails);
					
		reservation.setPrice(propertyPriceValue);
//		reservation.setPrice(totalPriceValue);
		reservation.setQuote(totalPriceValue);
		reservation.setDeposit(depositValue);
//		reservation.setExtra(taxesAndFeesValue);
		reservation.setExtra(0.0);
		Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
		reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));		
		 
		return reservationPrice;
	}

	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		Date timestamp = new Date();
		String message = "createReservationAndPayment Streamline (Altpartyid:"+this.getAltpartyid()+"), resrvationId:" + reservation.getId();
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

			//this is not necessary any more because this is done in AbstractReservation already 
//			double oldQuote = reservation.getQuote();
//			this.computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency(),timestamp);
//			
//			if(oldQuote != reservation.getQuote()) {
//				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
//			}
			
			String otherReqParamsAttributes = 
					"<pricing_model>"+PRICING_MODEL+"</pricing_model>" +
					"<unit_id>"+product.getAltid()+"</unit_id>" +
					"<startdate>"+DF.format(reservation.getFromdate())+"</startdate>" +
					"<enddate>"+DF.format(reservation.getTodate())+"</enddate>"+
					"<occupants>"+reservation.getAdult()+"</occupants>"+
					"<occupants_small>"+reservation.getChild()+"</occupants_small>+"+
					DO_NOT_USE_DISCOUNT_CODE;
			
			//now we need to find all optional tax and fees which are included by default to turn off them.
			Price actionPrice = new Price();
			actionPrice.setType(Tax.Type.SalesTaxExcluded.name());
			actionPrice.setEntityid(product.getId());
			actionPrice.setDate(new Date(0));
			ArrayList <Tax> productTaxesFromDB = sqlSession.getMapper(TaxMapper.class).taxdetail(actionPrice);
			for(Tax currProductTax : productTaxesFromDB){
				if(currProductTax.getState().equalsIgnoreCase(Tax.MandatoryType.OptionalTax.name()) && currProductTax.getOptionValue()!=null
						&& currProductTax.getOptionValue().equalsIgnoreCase(OPTIONAL_DEFAULT_ENABLED_FEE_TAX)){
					otherReqParamsAttributes += "<optional_fee_"+currProductTax.getAltId()+">no</optional_fee_"+currProductTax.getAltId()+">";
				}
			}
			
			Fee actionFee = new Fee();
			actionFee.setProductId(product.getId());
			actionFee.setPartyId(product.getAltpartyid());
			actionFee.setState(Fee.CREATED);
			ArrayList <Fee> productFeesFromDB = sqlSession.getMapper(FeeMapper.class).readbyproductandstate(actionFee);
			for(Fee currProductFee : productFeesFromDB){
				if(currProductFee.isTypeOptional() && currProductFee.getOptionValue()!=null && 
						currProductFee.getOptionValue().equalsIgnoreCase(OPTIONAL_DEFAULT_ENABLED_FEE_TAX)){
					otherReqParamsAttributes += "<optional_fee_"+currProductFee.getAltId()+">no</optional_fee_"+currProductFee.getAltId()+">";
				}
			}
			
			//now we adding customer data which exist
			if(!customer.noFirstName()){otherReqParamsAttributes +="<first_name>"+customer.getFirstName()+"</first_name>";}
			if(!customer.noFamilyName()){otherReqParamsAttributes +="<last_name>"+customer.getFamilyName()+"</last_name>";}
			if(StringUtils.isNotEmpty(customer.getLocalAddress())){otherReqParamsAttributes +="<address>"+customer.getLocalAddress()+"</address>";}
			if(StringUtils.isNotEmpty(customer.getCity())){otherReqParamsAttributes +="<city>"+customer.getCity()+"</city>";}
			if(StringUtils.isNotEmpty(customer.getPostalcode())){otherReqParamsAttributes +="<zip>"+customer.getPostalcode()+"</zip>";}
			if(!customer.noEmailaddress()){otherReqParamsAttributes +="<email>"+customer.getEmailaddress()+"</email>";}
			if(!customer.noCountry()){otherReqParamsAttributes +="<country_name>"+customer.getCountry()+"</country_name>";}
			if(StringUtils.isNotEmpty(customer.getRegion())){otherReqParamsAttributes +="<state_name>"+customer.getRegion()+"</state_name>";}
			if(StringUtils.isNotEmpty(customer.getDayphone())){otherReqParamsAttributes +="<work_phone>"+customer.getDayphone()+"</work_phone>";}
			if(StringUtils.isNotEmpty(customer.getNightphone())){otherReqParamsAttributes +="<phone>"+customer.getNightphone()+"</phone>";}
			if(StringUtils.isNotEmpty(customer.getMobilephone())){otherReqParamsAttributes +="<mobile_phone>"+customer.getMobilephone()+"</mobile_phone>";}
			if(StringUtils.isNotEmpty(customer.getFaxphone())){otherReqParamsAttributes +="<fax>"+customer.getFaxphone()+"</fax>";}
			
			//adding credit card data
			otherReqParamsAttributes +=
					"<credit_card_type_id>"+this.getCreditCardStreamlineTypeId(creditCard.getType())+"</credit_card_type_id>" +
					"<credit_card_number>"+creditCard.getNumber()+"</credit_card_number>" +
					"<credit_card_expiration_month>"+creditCard.getMonth()+"</credit_card_expiration_month>" +
					"<credit_card_expiration_year>"+creditCard.getYear()+"</credit_card_expiration_year>" +
					"<credit_card_cid>"+creditCard.getSecurityCode()+"</credit_card_cid>";
	
			String channelPartnerName = DEFAULT_CHANNEL_PARTNER_NAME;
			ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
			if(channelPartner!=null && channelPartner.getChannelName()!=null){
				channelPartnerName = channelPartner.getChannelName();
				LOG.debug("Streamline - channel partner name found: "+channelPartnerName);
			}
			
			
			//additional field for new API created for us
			otherReqParamsAttributes += "<price_total>"+reservation.getQuote()+"</price_total>" +
					"<hear_about_new>"+channelPartnerName+"</hear_about_new>";
			
			String responseMakeReservation = createXMLRequestToStreamline(sqlSession,"MakeReservationBookingPal",otherReqParamsAttributes); 
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseMakeReservation));
			Element rootNode = document.getRootElement();	

			if(rootNode.getChild("data")!=null && rootNode.getChild("data").getChild("reservation")!=null){
				Element reservationStreamline = rootNode.getChild("data").getChild("reservation");
				LOG.debug("Streamline reservation success, altId: " + reservationStreamline.getChildText("confirmation_id")); 
				reservation.setAltid(reservationStreamline.getChildText("confirmation_id"));
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
			}else{
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				String errorDescription = "";
				if(rootNode.getChild("status")!=null && rootNode.getChild("status").getChild("description")!=null){
					errorDescription = rootNode.getChild("status").getChildText("description");
				}
				LOG.debug("Streamline reservation not success - error: "+errorDescription);
				result.put(GatewayHandler.ERROR_MSG, errorDescription);
				return result;
			}
			
		}
		catch (Throwable e) {
			e.printStackTrace();
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			LOG.error(e.getMessage());
			throw new ServiceException(Error.reservation_api, e.getMessage());	
		}
		
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
		return result;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Streamline confirmReservation()");

	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Streamline readReservation()");

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Streamline updateReservation()");

	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation Streamline (Altpartyid:"+this.getAltpartyid()+"), reservationId=" + reservation.getAltid();
		LOG.debug(message);
		try {			
			String requestCancelRes = "<confirmation_id>"+reservation.getAltid()+"</confirmation_id>";
			String response = createXMLRequestToStreamline(sqlSession,"CancelReservation",requestCancelRes); 
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(response));
			Element rootNode = document.getRootElement();				
			
			if(rootNode.getChild("data")==null || rootNode.getChild("status")!=null){
				String cancelerrormessage = "Error in cancelling reservations";
				if(rootNode.getChild("status").getChildText("description")!=null){
					cancelerrormessage += ": " + rootNode.getChild("status").getChildText("description");
				}
				throw new ServiceException(Error.reservation_api, cancelerrormessage);
			}
		}
		catch (Throwable x) {
			LOG.error("Streamline cancel reservation - " + x.getMessage());
			reservation.setMessage(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
		
	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.service_absent, "Streamline readAlerts()");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void readPrices() {
		Date version = new Date();
		Date toDateForExtraPrices = Time.addDuration(version, FEES_PRICES_FOR_NUMBER_DAYS_IN_FUTURE, Time.DAY);
		Date minimumToDateForExtraPricesUpdate = Time.addDuration(version, FEES_PRICES_UPDATE_DAYS, Time.DAY);
		String message = "readPrices Streamline (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message + version.toString());
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			long functionStart = System.currentTimeMillis();
			String responseReservationsAndPrices = getXmlFeedConnection(sqlSession, this.getAltpartyid()); 			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseReservationsAndPrices));
			Element rootNode = document.getRootElement();	
			List<Element> properties = rootNode.getChildren("property");
			long gettingApiDataEnd = System.currentTimeMillis();
			LOG.debug("Reading API data takes: "+ (gettingApiDataEnd-functionStart) + "ms"); 
			for(Element property : properties){
				long onePropertyStart = System.currentTimeMillis();
				String altId = property.getChildText("propertyId");
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
					if (product == null) {
//						LOG.error("ERROR product not exsit:" + altId);
						continue;
					}
					
					String productId = product.getId();
					
					//Rates
					ArrayList<Price> pricesFromApi = new ArrayList<Price>();
					ArrayList<Yield> discountYieldFromApi = new ArrayList<Yield>();
//					ArrayList<Adjustment> adjustmentsFromApi = new ArrayList<Adjustment>();
					List<Element> ratePeriodsList = property.getChild("ratePeriods").getChildren("ratePeriod");
					for(Element ratePeriodEl : ratePeriodsList){
						String fromDateStr = ratePeriodEl.getChild("dateRange").getChildText("beginDate");
						String toDateStr = ratePeriodEl.getChild("dateRange").getChildText("endDate");
						Date fromDate =  responseDF.parse(fromDateStr);
						Date toDate =  responseDF.parse(toDateStr);
						
						int minimumStay = Integer.parseInt(ratePeriodEl.getChildText("minimumStay"));
						List<Element> ratesList = ratePeriodEl.getChild("rates").getChildren("rate");
						String dailyCurrency = product.getCurrency();
						String weekendCurrency = product.getCurrency();
						String weeklyCurrency = product.getCurrency();
						String monthlyCurrency = product.getCurrency();
						Double dailyRateValue = null;
						Double weekendRateValue = null;
						Double weeklyRateValue = null;
						Double monthlyRateValue = null;
						for(Element rateEl : ratesList){
							String rateAmountStr = rateEl.getChildText("amount");
							String currency = rateEl.getChild("amount").getAttributeValue("currency");
							Double rateValue = null;
							try{rateValue = Double.valueOf(rateAmountStr);}catch(Exception parseExc){}
							
							if(rateEl.getAttributeValue("rateType").equals("NIGHTLY_WEEKDAY")){
								dailyCurrency = currency;
								dailyRateValue = rateValue;
								Double minumumValue = rateValue * minimumStay;
								this.addPrice(pricesFromApi, product, dailyCurrency, Unit.DAY, fromDate, toDate, rateValue, minumumValue, minimumStay);
							} else if(rateEl.getAttributeValue("rateType").equals("NIGHTLY_WEEKEND")){
								weekendRateValue = rateValue;
								weekendCurrency = currency;
							} else if(rateEl.getAttributeValue("rateType").equals("WEEKLY")){
								weeklyRateValue = rateValue;
								weeklyCurrency = currency;
							} else if(rateEl.getAttributeValue("rateType").equals("MONTHLY")){
								monthlyRateValue = rateValue;
								monthlyCurrency = currency;
							}
						}
						
						if(weekendRateValue!=null && dailyRateValue!=null && (weekendRateValue-dailyRateValue != 0.0)){
							if(!weekendCurrency.equalsIgnoreCase(dailyCurrency)){
								Double currencyRate = WebService.getRate(sqlSession, weekendCurrency, dailyCurrency, new Date());
								weekendRateValue = PaymentHelper.getAmountWithTwoDecimals(weekendRateValue * currencyRate);
							}
							Yield weekendRateYield = YieldService.createWeekendRate(productId, dailyRateValue, weekendRateValue, fromDate, toDate);
							if(weekendRateYield!=null){
								weekendRateYield.setParam(Yield.DAYS_OF_WEEKEND_FRI_SAT);
								discountYieldFromApi.add(weekendRateYield);
							}
							
//							Double extraAdjustmentPrice = weekendRateValue - dailyRateValue;
//							Adjustment adjustmentPrice = new Adjustment();
//							adjustmentPrice.setCurrency(dailyCurrency);
//							adjustmentPrice.setPartyID(getAltpartyid());
//							adjustmentPrice.setProductID(product.getId());
//							adjustmentPrice.setFromDate(fromDate);
//							adjustmentPrice.setToDate(toDate);
//							adjustmentPrice.setMaxStay(999);
//							adjustmentPrice.setMinStay(minimumStay);
//							adjustmentPrice.setExtra(extraAdjustmentPrice);
//							adjustmentPrice.setServicedays(Adjustment.WEEKEND);
//							
//							adjustmentsFromApi.add(adjustmentPrice);
						}
						

						if(weeklyRateValue!=null && dailyRateValue!=null){
							if(!weeklyCurrency.equalsIgnoreCase(dailyCurrency)){
								Double currencyRate = WebService.getRate(sqlSession, weeklyCurrency, dailyCurrency, new Date());
								weeklyRateValue = PaymentHelper.getAmountWithTwoDecimals(weeklyRateValue * currencyRate);
							}
							Yield weeklyDiscount = YieldService.createLengthOfStayDiscount(productId, dailyRateValue, weeklyRateValue, 7, fromDate, toDate);
							if(weeklyDiscount!=null){
								discountYieldFromApi.add(weeklyDiscount);
							}
						}
						
						if(monthlyRateValue!=null && dailyRateValue!=null){
							if(!monthlyCurrency.equalsIgnoreCase(dailyCurrency)){
								Double currencyRate = WebService.getRate(sqlSession, monthlyCurrency, dailyCurrency, new Date());
								monthlyRateValue = PaymentHelper.getAmountWithTwoDecimals(monthlyRateValue * currencyRate);
							}
							//TODO set number of days in month
							//This is now set on 30, but in case of Streamline
							//min days for month price can be 27,28 
							Yield monthlyDiscount = YieldService.createLengthOfStayDiscount(productId, dailyRateValue, monthlyRateValue, 30, fromDate, toDate);
							if(monthlyDiscount!=null){
								discountYieldFromApi.add(monthlyDiscount);
							}
						}
						
						
					}
					
//					long handlingPropertyRatesDataEnd = System.currentTimeMillis();
//					LOG.debug("Handling property rates data takes: "+ (handlingPropertyRatesDataEnd-onePropertyStart) + "ms");
					
					PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
//					long updateProductPricesEnd = System.currentTimeMillis();
//					LOG.debug("PartnerService.updateProductPrices takes: "+ (updateProductPricesEnd-handlingPropertyRatesDataEnd) + "ms");
					
//					PartnerService.updateProductAdjustments(sqlSession, product, adjustmentsFromApi, version);
					PartnerService.updateProductRateYields(sqlSession, product, discountYieldFromApi, version);
//					long updateProductRateYieldsEnd = System.currentTimeMillis();
//					LOG.debug("PartnerService.updateProductRateYields takes: "+ (updateProductRateYieldsEnd-updateProductPricesEnd) + "ms");
					
					
					
					ArrayList<Fee> feeFromApi = new ArrayList<Fee>();
					ArrayList<Tax> taxListFromApi = new ArrayList<Tax>();
					ArrayList<Tax> taxSpecialListForSomeFee = new ArrayList<Tax>();
					
					
					//it is important to first process fees and than taxes
					//because in there 'fees' we will have some tax which are only for some fees..
					//Fees
					List<Element> feesList = property.getChild("fees").getChildren("fee");
					for(Element feeElement : feesList){
						this.processFeeFromApi(feeFromApi, taxListFromApi, taxSpecialListForSomeFee, feeElement, product, toDateForExtraPrices); 
					}
					
					
					//taxes
					List<Element> taxesElementList = property.getChild("taxes").getChildren("tax");
					for(Element taxElement : taxesElementList){
						this.processFeeFromApi(feeFromApi, taxListFromApi, taxSpecialListForSomeFee, taxElement, product, toDateForExtraPrices); 
					}
					
//					long handlingPropertyFeesAndTaxesDataEnd = System.currentTimeMillis();
//					LOG.debug("Handling property fees and taxes data takes: "+ (handlingPropertyFeesAndTaxesDataEnd-updateProductRateYieldsEnd) + "ms");
					
					
					PartnerService.updateProductTaxes(sqlSession, product, taxListFromApi, version);
					
//					long updateProductTaxesEnd = System.currentTimeMillis();
//					LOG.debug("PartnerService.updateProductTaxes takes: "+ (updateProductTaxesEnd-handlingPropertyFeesAndTaxesDataEnd) + "ms");
					
				
					FeeService.updateProductFees(sqlSession, product, feeFromApi, version, true, minimumToDateForExtraPricesUpdate);
					
//					long updateProductFeesEnd = System.currentTimeMillis();
//					LOG.debug("FeeService.updateProductFees takes: "+ (updateProductFeesEnd-updateProductTaxesEnd) + "ms");
					
					
					sqlSession.commit();
					
					LOG.debug("TOTAL one property (altID="+altId+") takes: "+ (System.currentTimeMillis()-onePropertyStart) + "ms");
				}
				catch (Throwable x) {
					LOG.error(Error.product_data.name() + altId); 
					x.printStackTrace();
				}
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, version);

	}
	
	private void addPrice(ArrayList<Price> pricesFromApi, Product product, String currency, String unit, 
			Date fromDate, Date toDate, double rateValue, double minimumValue, Integer minimumStay){
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
		price.setMinimum(minimumValue);
		price.setAvailable(1);
		
		pricesFromApi.add(price);
	}
	
	
	private void processFeeFromApi(ArrayList<Fee> feeFromApi, ArrayList<Tax> taxListFromApi, ArrayList<Tax> taxSpecialListForSomeFee, Element feeElement, Product product,  Date toDateForExtraPrices){
		String costValueStr = feeElement.getChildText("cost_value");
		Double costValue = null;
		try{costValue = Double.valueOf(costValueStr);}catch(Exception parseExc){}
		
		String percentValueStr = feeElement.getChildText("percent_value");
		Double percentValue = null;
		try{percentValue = Double.valueOf(percentValueStr);}catch(Exception parseExc){}
		
		String elementAltId = feeElement.getChildText("id");
		String nameOfElement = feeElement.getChildText("name");
		boolean isMandatory = !this.isFeeParamterOn(feeElement.getChildText("optional"));
		
		boolean isOptionalDefaultEnabled = this.isFeeParamterOn(feeElement.getChildText("optional_default_enabled"));
		boolean isTax = this.isFeeParamterOn(feeElement.getChildText("include_into_tax"));
		boolean isTaxable = this.isFeeParamterOn(feeElement.getChildText("taxable"));
		int feeWeight = 0;
		//only one of this special parameters can be turn on, so we can use if/else if
		//and we map this special parameters in weight 
		if(this.isFeeParamterOn(feeElement.getChildText("based_on_total_cost"))){
			feeWeight=1;
		} else if(this.isFeeParamterOn(feeElement.getChildText("based_on_grandtotal_cost"))){
			feeWeight=2;
		} else if(this.isFeeParamterOn(feeElement.getChildText("based_on_grandtotal_cost_with_guest_deposits"))){
			feeWeight=3;
		}
		
		int feeUnit = Fee.NOT_APPLICABLE;
		//this parameter is per day
		//if impact=1 - Apply as nightly impact fee. This fee is calculated on a per night basis
		if(this.isFeeParamterOn(feeElement.getChildText("impact"))){
			feeUnit = Fee.PER_DAY;
		}
		
		//this parameter is per person
		//if impact_guests=1 - Apply as guest impact fee.  This fee is calculated on a per guest basis (multiplies fee by the total number of guests)
		if(this.isFeeParamterOn(feeElement.getChildText("impact_guests"))){
			if(feeUnit==Fee.PER_DAY){
				feeUnit = Fee.PER_DAY_PER_PERSON;
			}else{
				feeUnit = Fee.PER_PERSON;
			}	
		}
		
		// here we have also daily_cleaning
		//daily cleaning meaning same as impact - so this should be added PER_DAY.
		//but we need to check if maybe unit is already changed
		//(this should not be happen!)
		if(feeElement.getChild("daily_cleaning")!=null && this.isFeeParamterOn(feeElement.getChildText("daily_cleaning"))){
			if(feeUnit==Fee.NOT_APPLICABLE){
				feeUnit = Fee.PER_DAY;
			}else if(feeUnit==Fee.PER_PERSON){
				feeUnit = Fee.PER_DAY_PER_PERSON;
			}
		}
		
		//also there is option extra_charge - this fee is only for reservation which have more person than allowed
		//so in our case this will be optional fee.
		if(this.isFeeParamterOn(feeElement.getChildText("extra_charge"))){
			if(isMandatory){
				isMandatory = false;
			}
		}
		
		boolean isPetFee = this.isFeeParamterOn(feeElement.getChildText("use_pets_logic"));
		
		
		//if we have some of special parameter turned on, than this is a tax, 
		//(but we get include_into_tax=0 so this need to be changed.
		//NEW: this special fees will be saved in Fee table
//		if(feeWeight>0 && !isTax){
//			isTax = true;
//		}

		//there are some special tax which sould be used only for fees.
		//this tax will be in <fees> response, and will be processed only before <taxes> where we will have this kind of fee 
		//this tax we will not save in DB. Instead we will add this tax to fee immediately
		boolean isFeeTax = this.isFeeParamterOn(feeElement.getChildText("fee_tax"));
		if(isFeeTax){
			if(isMandatory){
				this.insertTax(taxSpecialListForSomeFee, product.getId(),nameOfElement, percentValue, isMandatory, elementAltId, isOptionalDefaultEnabled);
			}
		} else{
		
			if(isTax && percentValue>0){
				this.insertTax(taxListFromApi, product.getId(), nameOfElement, percentValue, isMandatory, elementAltId, isOptionalDefaultEnabled);
				
			}else if(costValue>0 || percentValue>0){ //we will not insert fee with value=0
				//first we will add fee tax to fee if that is necessary
				String feeApplyThisTaxes = null;
				if(feeElement.getChild("fee_taxes")!=null && feeElement.getChildText("fee_taxes")!=null && feeElement.getChildText("fee_taxes").length()>0){
					feeApplyThisTaxes = feeElement.getChildText("fee_taxes");
					//this mean we have fee and we need to apply only some tax on this fee 
					//this will be always for flat value
					double tempCostValue = costValue;
					if(percentValue>0){
						LOG.error("Streamline fee with special fee tax is in percent value, PartyID=" + this.getAltpartyid() + ", productID="+product.getId()+", product altID="+product.getAltid());
					}
					//we can have array of tax Id, separate by comma
					String taxAltIdArray[] = feeApplyThisTaxes.split(",");
					
					for(String taxForFeeAltId : taxAltIdArray){
						taxForFeeAltId = taxForFeeAltId.trim();
						if(taxForFeeAltId.length()>0){
							for(Tax specialFeeTax : taxSpecialListForSomeFee){
								if(specialFeeTax.getAltId().equalsIgnoreCase(taxForFeeAltId)){
									tempCostValue += costValue * specialFeeTax.getAmount() / (double)100;
								}
							}
						}
					}
					
					costValue = tempCostValue;
				}
				
				
				this.insertFee(feeFromApi, product, elementAltId, nameOfElement, costValue, percentValue, isTaxable, isMandatory, isOptionalDefaultEnabled, isPetFee, feeUnit, feeWeight, toDateForExtraPrices);	
			}
		}
	}
	
	private void insertFee(ArrayList<Fee> feeFromApi, Product product, String elementAltId, String nameOfElement, 
			Double costValue, Double percentValue, boolean isTaxable, boolean isMandatory, boolean isOptionalDefaultEnabled, 
			boolean isPetFee, int feeUnit, int feeWeight, Date toDateForExtraPrices){
		Fee feeObject = new Fee();
		
		feeObject.setAltId(elementAltId);
		if(isMandatory){
			if(!isPetFee){
				feeObject.setEntityType(Fee.MANDATORY);
			}else{
				feeObject.setEntityType(Fee.PET_FEE_MANDATORY);
			}
		}else{
			if(!isPetFee){
				feeObject.setEntityType(Fee.OPTIONAL);
			}else{
				feeObject.setEntityType(Fee.PET_FEE_OPTIONAL);
			}
		}
		feeObject.setProductId(product.getId());
		feeObject.setPartyId(getAltpartyid());
		feeObject.setName(nameOfElement);
		if(isOptionalDefaultEnabled){
			feeObject.setOptionValue(OPTIONAL_DEFAULT_ENABLED_FEE_TAX);
		}
		if(isTaxable){
			feeObject.setTaxType(Fee.TAXABLE);
		}else{
			feeObject.setTaxType(Fee.NOT_TAXABLE);
		}
		feeObject.setFromDate(new Date(0));
		feeObject.setToDate(toDateForExtraPrices);
		feeObject.setUnit(feeUnit);
		if(costValue>0){
			feeObject.setValue(costValue);
			feeObject.setValueType(Fee.FLAT);
			feeObject.setCurrency(product.getCurrency());
		}else{
			feeObject.setValue(percentValue);
			feeObject.setValueType(Fee.PERCENT);
		}
		feeObject.setWeight(feeWeight);
		feeFromApi.add(feeObject);

	}
	
	
	private void insertTax(ArrayList<Tax> taxListFromApi, String productId, String nameOfElement, Double percentValue, boolean isMandatory, 
			String elementAltId, boolean isOptionalDefaultEnabled){
		Tax taxObject = new Tax();
		taxObject.setAccountid(Account.VAT_OUTPUT);
		taxObject.setPartyid(getAltpartyid());
		taxObject.setProductId(productId);
		taxObject.setType(Tax.Type.SalesTaxExcluded.name());
		taxObject.setDate(new Date(0));
		taxObject.setThreshold(0);
		taxObject.setName(nameOfElement);
		taxObject.setAmount(percentValue);
		if(isMandatory){
			taxObject.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
		}else{
			taxObject.setMandatoryType(Tax.MandatoryType.OptionalTax.name());
		}
		taxObject.setAltId(elementAltId);
		if(isOptionalDefaultEnabled){
			taxObject.setOptionValue(OPTIONAL_DEFAULT_ENABLED_FEE_TAX);
		}
		
		taxListFromApi.add(taxObject);
	}
	
	
	
	
	
	private boolean isFeeParamterOn(String pefFeeValue){
		boolean result = false;
		if(pefFeeValue.equals("1")){
			result = true;
		}
		
		return result;
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public void readProducts() {
		
		String message = "readProducts Streamline (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);

		Date version = new Date();
		String responseAllProperties;
		
		StringBuilder sbNotKnowLocation = new StringBuilder();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			
			responseAllProperties = createXMLRequestToStreamline(sqlSession,"GetPropertyList",""); 			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseAllProperties));
			Element rootNode = document.getRootElement();	
			List<Element> properties = rootNode.getChild("data").getChildren("property");
			for(Element property : properties){
				try{
					String altId = property.getChildText("id");
	//				LOG.debug("Current AltId="+altId);
					String countryISO = property.getChildText("country_name");
					
					//data for finding location
					String state = property.getChildText("state_name");
					String city = property.getChildText("city");
					String latitudeLocationStr = property.getChildText("location_latitude");
					String longitudeLocationStr = property.getChildText("location_longitude");
					
					//Streamline provide us 2 set of coordinates. One for location (city) and one for property. 
					//This coordinates will be used for finding locations, so first we use location coordinates,
					//and if not exist we use property coordinates
					if(StringUtils.isEmpty(latitudeLocationStr) && StringUtils.isEmpty(longitudeLocationStr)){
						latitudeLocationStr = property.getChildText("latitude");
						longitudeLocationStr = property.getChildText("longitude");
					}
					
					
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId);
					if (product == null) {
						continue;
					}
					
					if(LOG.isDebugEnabled()) 
						LOG.debug("Processing property : " + product.getName());
					
					Integer roomNumber = 0;
					Integer bathroomNumber = 0;
					Integer maxPersonTotalNumber = 0;
					Integer childNumber = 0;
					Integer adultsNumber = 0;
					Double latitude = null;
					Double longitude = null;
					try{roomNumber = Integer.parseInt(property.getChildText("bedrooms_number"));}catch(Exception parseExc){LOG.error("Parse exception: "+parseExc.getMessage());}
					try{bathroomNumber = Integer.parseInt(property.getChildText("bathrooms_number"));}catch(Exception parseExc){LOG.error("Parse exception: "+parseExc.getMessage());}
					try{maxPersonTotalNumber = Integer.parseInt(property.getChildText("max_occupants"));}catch(Exception parseExc){LOG.error("Parse exception: "+parseExc.getMessage());}
					try{adultsNumber = Integer.parseInt(property.getChildText("max_adults"));}catch(Exception parseExc){LOG.error("Parse exception: "+parseExc.getMessage());}
					childNumber = maxPersonTotalNumber - adultsNumber;
					if(childNumber < 0){childNumber=0;}
					try{latitude = Double.valueOf(property.getChildText("latitude"));}catch(Exception parseExc){}
					try{longitude = Double.valueOf(property.getChildText("longitude"));}catch(Exception parseExc){}
					
					StringBuilder physicalAddress = new StringBuilder();
					StringBuilder physicalAddressForLocation = new StringBuilder();
					if (StringUtils.isNotEmpty(property.getChildText("address"))) {
						physicalAddress.append(property.getChildText("address")).append("\n");
						physicalAddressForLocation.append(property.getChildText("address")).append(", ");
					}
					if (StringUtils.isNotEmpty(property.getChildText("city"))) {
						physicalAddress.append(property.getChildText("city")).append("\n");
						physicalAddressForLocation.append(property.getChildText("city")).append(", ");
					}
					if (StringUtils.isNotEmpty(property.getChildText("state_description"))) {
						physicalAddress.append(property.getChildText("state_description")).append("\n");
						physicalAddressForLocation.append(property.getChildText("state_description")).append(", ");
					}
					if (StringUtils.isNotEmpty(property.getChildText("country_name"))) {
						physicalAddress.append(property.getChildText("country_name")).append("\n");
						physicalAddressForLocation.append(property.getChildText("country_name"));
					}
					
					
					//finding long and lat using physical address and Google Location service 
					//if they do not exist in API response (if exist we do not need to find) 
					//and if we have reason to find - if address is changed or lat or long do not exist in product  
					if((latitude==null || longitude==null) && 
							(!physicalAddress.toString().equalsIgnoreCase(product.getPhysicaladdress())
									|| product.getLatitude()==null
									|| product.getLongitude()==null ) && StringUtils.isNotEmpty(physicalAddress.toString())){
						Location propertyLocation = GoogleLocationProcessor.getGoogleLocation(physicalAddressForLocation.toString());
						if(propertyLocation!=null){
							if(latitude==null){
								latitude = propertyLocation.getLatitude();
							}
							if(longitude==null){
								longitude = propertyLocation.getLongitude();
							}
						}
					}
					
					
					String propertyName = property.getChildText("name");
//					if(property.getChildText("seo_title")!=null && !property.getChildText("seo_title").equalsIgnoreCase("")){
//						propertyName = property.getChildText("seo_title");
//					} else{
						if(property.getChildText("home_type")!=null && !property.getChildText("home_type").equalsIgnoreCase("")){
							propertyName += ", "+ property.getChildText("home_type");
						}
						
						if(city!=null && !city.equalsIgnoreCase("")){
							propertyName += " at "+ city;
						}
						
						if(property.getChildText("view_name")!=null && !property.getChildText("view_name").equalsIgnoreCase("")){
							propertyName += ", with "+ property.getChildText("view_name");
						}
//					}
					
					if(propertyName.length()>99){
						propertyName = propertyName.substring(0, 99);
					}
					
					product.setCurrency(CURRENCY_IN_STREAMLINE);
					
	//				product.setName(property.getChildText("name") +", "+ property.getChildText("seo_title") );
					product.setName(propertyName);
					product.setUnit(Unit.DAY);
					product.setRoom(roomNumber);
					product.setBathroom(bathroomNumber);
					product.setQuantity(1);
					product.setPerson(adultsNumber);
					product.setChild(0);
//					product.setChild(childNumber);
					product.setRank(0.0);
					
					product.setPhysicaladdress(physicalAddress.toString());
					

					if(product.getLocationid()==null){
						Location location = null;
						if(locationMap.get(city+state+countryISO) != null){
							location = locationMap.get(city+state+countryISO);
						}else if(StringUtils.isNotEmpty(latitudeLocationStr) && StringUtils.isNotEmpty(longitudeLocationStr)){
							location = PartnerService.getLocation(sqlSession, city, state, countryISO, Double.valueOf(latitudeLocationStr), Double.valueOf(longitudeLocationStr));
							locationMap.put(city+state+countryISO, location);
						}else{
							location = PartnerService.getLocation(sqlSession, city,	state, countryISO);
							locationMap.put(city+state+countryISO, location);
						}
						
						if(location!=null){
							product.setLocationid(location.getId());
						}else{
							product.setState(Product.SUSPENDED);
							sbNotKnowLocation.append("\n").append("Streamline property: " + altId + " country: " + countryISO + " city: " + city);
						}
					}	
					
					product.setLatitude(latitude);
					product.setLongitude(longitude);
					product.setWebaddress(getWebaddress());
					product.setCommission(getCommission());
					product.setDiscount(getDiscount());
					product.setRating(5);
					product.setAltitude(0.0);
					product.setVersion(version);
					
					/*
					 //not used
					`Options` 
					`Tax` 
					`Code` 
					`Unspsc` 
					`Servicedays` 
					`Toilet` 
					`Infant` 
					`Baby` 
					`Linenchange` 
					`Refresh` 
					`OwnerDiscount` 
					`DynamicPricingEnabled` 
					`AssignedtoManager` 
					`CleaningFee` 
					`SecurityDeposit` 
					 */
					
					//description build
					StringBuilder description = new StringBuilder();
					description.append(property.getChildText("seo_description")).append("\n");
					description.append(property.getChildText("short_description")).append("\n");
					description.append(property.getChildText("description")).append("\n");
					
					
					ArrayList<String> attributes = new ArrayList<String>();
					addType(attributes, property.getChildText("lodging_type_id"));
					
					//attributes
					String otherReqParamsAttributes = "<unit_id>"+altId+"</unit_id>";
					String responseAttr = createXMLRequestToStreamline(sqlSession,"GetPropertyAmenities",otherReqParamsAttributes); 
					builder = new SAXBuilder();
					Document documentAttr = (Document) builder.build(new StringReader(responseAttr));
					Element rootNodeAttributes = documentAttr.getRootElement();	
					List<Element> propertyAttributes = rootNodeAttributes.getChild("data").getChildren("amenity");
					for (Element amenity : propertyAttributes){
						addPropertyAttribute(attributes, amenity.getChildText("amenity_name"));			
					}
					
					//removing duplicate values from attributes
					HashSet<String> attributeHashSet = new HashSet<String>();
					attributeHashSet.addAll(attributes);
					attributes.clear();
					attributes.addAll(attributeHashSet); 
					
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
					x.printStackTrace();
				}
			}
			
			//print not find attributes
			
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(PROPERTY_ATTRIBUTES_NOT_FOUND);
			PROPERTY_ATTRIBUTES_NOT_FOUND.clear();
			PROPERTY_ATTRIBUTES_NOT_FOUND.addAll(hs);
//			LOG.debug("Streamline attributes not find (Altpartyid:"+this.getAltpartyid()+"): ");
//			for(String tempAttr : PROPERTY_ATTRIBUTES_NOT_FOUND){
//				System.out.println(":::"+tempAttr +":::");
//			}
			
			//canceling product which are not updated 
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
		MonitorService.monitor(message, version);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void readImages() {
		Date timestamp = new Date();
		String message = "readImages Streamline (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			String responseAllProperties = createXMLRequestToStreamline(sqlSession,"GetPropertyList",""); 			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseAllProperties));
			Element rootNode = document.getRootElement();	
			List<Element> properties = rootNode.getChild("data").getChildren("property");
			for(Element property : properties){
				String altId = property.getChildText("id");
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
					if (product == null) {continue;}
					
					String otherReqParamsImages = "<unit_id>"+altId+"</unit_id>";
					String responseImages = createXMLRequestToStreamline(sqlSession,"GetPropertyGalleryImages",otherReqParamsImages); 
					builder = new SAXBuilder();
					Document documentImg = (Document) builder.build(new StringReader(responseImages));
					Element rootNodeImages = documentImg.getRootElement();	
					List<Element> propertyImages = rootNodeImages.getChild("data").getChildren("image");
					if(propertyImages!=null && propertyImages.size()>0){
						ArrayList<NameId> images = new ArrayList<NameId>();
						for (Element image : propertyImages) {
							images.add(new NameId(image.getChildText("id"),image.getChildText("image_path")));
						}
						UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
					}
					
					sqlSession.commit();
				}
				catch (Throwable x) {
					LOG.error(Error.product_data.name() + altId); 
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
		String message = "readSchedule Streamline (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			long functionStart = System.currentTimeMillis();
			String responseReservationsAndPrices = getXmlFeedConnection(sqlSession, this.getAltpartyid());
			long gettingApiDataEnd = System.currentTimeMillis();
			LOG.debug("Reading API data takes: "+ (gettingApiDataEnd-functionStart) + "ms"); 
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseReservationsAndPrices));
			Element rootNode = document.getRootElement();	
			List<Element> properties = rootNode.getChildren("property");
			long parsingApiDataEnd = System.currentTimeMillis();
			LOG.debug("Parsing API data takes: "+ (parsingApiDataEnd-gettingApiDataEnd) + "ms"); 
			
			for(Element property : properties){
				long onePropertyStart = System.currentTimeMillis();
				String altId = property.getChildText("propertyId");
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
					if (product == null) {
//						LOG.error(Error.product_id.getMessage() + " " + altId);
						continue;
					}
					
					
					List<Element> reservationsListFeed = property.getChild("reservations").getChildren("reservation");
					ArrayList<Reservation> reservationsFromApi = new ArrayList<Reservation>();
					for(Element reservationEl : reservationsListFeed){
						String fromDateStr = reservationEl.getChild("reservationDates").getChildText("beginDate");
						String toDateStr = reservationEl.getChild("reservationDates").getChildText("endDate");
						Date fromDate =  responseDF.parse(fromDateStr);
						Date toDate =  responseDF.parse(toDateStr);
						
						Reservation reservation = new Reservation();
						reservation.setProductid(product.getId());
						reservation.setFromdate(fromDate);
						reservation.setTodate(toDate);
						
						reservationsFromApi.add(reservation);
					}
					
					PartnerService.updateProductSchedules(sqlSession, product, reservationsFromApi, version);
					sqlSession.commit();
				
					LOG.debug("TOTAL one property (altID="+altId+") takes: "+ (System.currentTimeMillis()-onePropertyStart) + "ms");
				}
				catch (Throwable x) {
					LOG.error(Error.product_data.name() + altId); 
					x.printStackTrace();
				}
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, version);
	}

//	private boolean isReservationsEqual(Reservation res1, Reservation res2){
//		boolean result = false;
//		if(res1.getProductid().equals(res2.getProductid()) 
//				&& res1.getFromdate().equals(res2.getFromdate())
//				&& res1.getTodate().equals(res2.getTodate())){
//			result = true;
//		}
//		return result;
//	}
	
	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Streamline readSpecials()");

	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Streamline readDescriptions()");

	}

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Streamline readAdditionCosts()");

	}
	
	private static HashMap<String, String> TYPES = null;
	/**
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("1","PCT30"); //1 = resort 
			TYPES.put("2","PCT20"); //2 = hotels
			TYPES.put("3","PCT34"); //3 = homes in our system - "Vacation Rental"?? //TODO
			TYPES.put("4","PCT44"); //4 = yacht
		}
		//System.out.println("property type: " + type);
		if (TYPES.get(type) == null) {
			LOG.error("############################### Property Type not find: " + type);
			attributes.add(type);}
		else  {
			attributes.add(TYPES.get(type));
		}
	}
	
	
	private static HashMap<String,String> PROPERTY_ATTRIBUTES = null;
	private static ArrayList<String> PROPERTY_ATTRIBUTES_NOT_FOUND = new ArrayList<String>();
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	
	/* TODO all attributes need to be updated with new PM 
	 * In comment there are names in our system
	 */
	private static final void addPropertyAttribute(ArrayList<String> attributes, String attribute) {

		if (PROPERTY_ATTRIBUTES == null) {
			PROPERTY_ATTRIBUTES = new HashMap<String, String>();

			
			
			//Bedroom Details
			PROPERTY_ATTRIBUTES.put("Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen Bed", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("1 Queen Bed", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen Size Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queens", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 1: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 1: Queen bed with an adjoining bathroom.", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 2: 2 Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 2: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 3: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 3: 2 Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 3: Queen + Twin", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: 2 Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Queen + Bunks", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 5: 2 Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 6: Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Aerobed - Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Aerobeds - Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen Bunk", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Two Queen Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen/ Twin Bunk Bed", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen/Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Cal King + Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("King/Queen", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Twin Day Bed Wi/Trundle", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen/Twin", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Futon", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Twin Bed", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Twin", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Queen + Twin", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Bunk", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Bunk + Other", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen &amp; Full Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen & Full Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen &amp; Twin Beds", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen/King", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen/Full", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen/Twin Bunk", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen Air Bed", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen Air Mattress", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("-Queen Air Mattress", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("1 Queen Air Mattress", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen Over Queen Bunk", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Sets of Queen Over Queen Bunks", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Queen + Other", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("2 Queen + Other", "CBF15"); //Queen bed
			PROPERTY_ATTRIBUTES.put("Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Full Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Queen Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Queen Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("1 Queen Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Wall / Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Bedroom 2: Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Twin Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("2 Twin Murphy Beds", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper &amp; Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper & Murphy Bed", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper?: Murphy", "BED4"); //Murphy bed
			PROPERTY_ATTRIBUTES.put("King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("1 King Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 1: King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 1: King Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 2: King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 2: King Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 3: King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Kind Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King &amp; Twin", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("California King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King + Twin", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King/Twin", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King/Full", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("2 King Size Beds", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("2 King Beds", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King/King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King/Double", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("11 King", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King/Twin Bunk", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King Air Mattress", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King or 2 Twins (please specify)", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("King + Other", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("2 Full", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("2 Full Beds", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Two Fulls", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full &amp; Twin Beds", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("1 Full Bed", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("2 Full Beds", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full/King", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full/Queen", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full/Twin", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full/Full", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full/Double", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full Over Queen Bunk", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Full Over  Full Bunk", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("2 Sets of Full Over Full Bunks", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("2 Doubles", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Two Doubles", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Two Double Beds", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/Twin Bunk Bed", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/King", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/Queen", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/Twin", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/Full", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double/Double", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Double", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("Double Air Mattress", "CBF29"); //Double bed
			PROPERTY_ATTRIBUTES.put("2 Twins", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 2: 2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 4: 2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 3: 2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 3: 2 Twin Beds", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 5: 2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Bedroom 6: 2 Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin + Other", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("1 Full + 2 Twins", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("1 Full + 2 Twin Bunks", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Two Twin Rollaways", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twins + Trundle", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin + Bunk", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Two Twins", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin Beds", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Two Twin Beds", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 twin ben bag beds", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Twin/Twin", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("Two Twin Air Mattress", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin/convertable chairs", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("2 Twin + 2 Other", "CBF96"); //2 lower twin beds
			PROPERTY_ATTRIBUTES.put("3 Twins", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("Three Twin Beds", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("3 Bb Twin/Dbl", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("Three Twin Bed Mats", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("Triple Twin Bunks", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("King &amp; Twin or 3 Twins (please specify)", "CBF97"); //3 lower twin beds
			PROPERTY_ATTRIBUTES.put("4 Twin", "CBF98"); //4 lower twins convert
			PROPERTY_ATTRIBUTES.put("4 Twin Beds", "CBF98"); //4 lower twins convert
			PROPERTY_ATTRIBUTES.put("5 Twin Beds", "CBF98"); //4 lower twins convert
			PROPERTY_ATTRIBUTES.put("Twin Bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Twin", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Twin", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin Air Bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("1 Twin", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Extra Twin Mattress under bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin/King", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin/Queen", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin/Full", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin/Double", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin + Other", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("1 Full, 1 Twin, 1 T/Full Bunk", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin Bunk Bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin Beds or Bunks", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twins", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Full Bed + Twin Bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Single", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin Daybed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Twin Air Mattress", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("1 Twin Bed", "BED8"); //Twin
			PROPERTY_ATTRIBUTES.put("Bunk Beds Full", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Queen/Twin/Twin", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Twins (can be converted to Queen)", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Queen + 2 Twin", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin over Queen bunkbed", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("1 Twin over Queen Bunkbed", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Twin over Queen Bunkbeds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("1 Full over Queen Bunkbeds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Full over Queen Bunkbeds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("1 Queen over Queen Bunkbed", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Queen over Queen Bunkbeds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin over queen bunk with tuck-under twin", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin Over Queen Bunk", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Queen over Twin Bunk Beds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin Over Full Bunk", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin Over Double Bunk", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 twins/convertible king", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Twin + 2 Bunks", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Bunk Beds Single", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Pyramid Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bunk Bed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("3 Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bunk Beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bunk + Other", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 3: Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 6: Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 5: Bunks + Twin Bed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Bunks + Other", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin Over Twin Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Sets of Twin Over Twin Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 sets of Twin over Full Bunkbed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("3 twin over full bunk beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 3: Bunks + Trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin over Full Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Full over Full bunkbed w/ twin trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("1 Full over Full Bunkbed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Full over Full Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 2: Bunks &amp;#038; Twin Day Bed with Trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("twin over full bunk w/ twin trundle, plus twin bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Full/Twin Bunk Bed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Full over full bunk bed plus a twin bed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Full + Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("T/Full Bunk + Futon", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunk + Full Futon", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunk + 2 Full Futons", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunks + Twin", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bedroom 3: 2 Twin Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunks + 2 Twins", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("3 Twin + 2 Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("3 Twin Bunks", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Triple twin bunkbed + Twin bunkbed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin + Bunk + Other", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin + Bunk", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Two Twin Bunk Beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Bunk Beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Full/Twin Bunk Beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin/Twin Bunk Beds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin over Twin bunk bed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Twin over full bunk bed plus twin trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("1 Twin over Twin Bunkbed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin over Twin Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("3 Twin over Twin Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("4 Twin over Twin Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("1 Twin over Full Bunkbed", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin over Full Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("4 Twin over Full Bunkbeds", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("1 Twin Bed over 1 Twin Trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 Twin Beds over 2 Twin Trundles", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("4 Twin Beds over 4 Twin Trundles", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("1 Twin over Full Bunkbed with Twin Trundle", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("2 sets of triple XL twin bunk beds (6 XL twin beds)", "CBF94"); //1 lower + 1 pullman upper
			PROPERTY_ATTRIBUTES.put("Bath Towels and Linens Provided", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Bed Linens &amp; Bath Towels", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Bed Linens & Bath Towels", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Towels Provided", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Towels Provided: Yes", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Linens/Towels", "REC43"); //Towels available
			PROPERTY_ATTRIBUTES.put("Trundle", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Trundles", "CBF36"); //Pullout bed	
			PROPERTY_ATTRIBUTES.put("Day Bed with Trundle", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Twin Trundle Bed", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Twin/Twin Trundle", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Twin Trundle", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("2 Twin Trundle", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Twin Sofa Pullout", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Full Sofa Pullout", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Queen Sofa Pullout", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("King Sofa Pullout", "CBF36"); //Pullout bed
			PROPERTY_ATTRIBUTES.put("Cama extra", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Rollaway Bed", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Twin Rollaway", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Three Rollaway Beds", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("One Rollaway Bed", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Two Rollaway Beds", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Roll Away", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Twin Fold-Out", "CBF17"); //Rollaway
			PROPERTY_ATTRIBUTES.put("Fold Out Full Sofa Bed", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Full Sofa", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa Bed", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("King Sofa", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Bedroom 4: Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("1 Sleeper Sofa", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper?: Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper?: Sofa Sleeper In Living Room", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper?: Sofa Sleeper in living room.", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Sofa Sleepers", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Sleeper Sofas", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Queen Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("1 Queen Sofa Sleeper Couch", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Queen Sofa Sleeper Couches", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("3 Queen Sofa Sleeper Couches", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("1 Twin Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Twin Sofa Sleeper Couches", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("1 Full Sofa Sleeper Couch", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Full Sofa Sleeper Couches", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("3 Full Sofa Sleeper Couches", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Full Sofa Sleeper", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Den with Sleeper Sofa", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sleeper Sofa", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Single Sofabed", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Day Bed", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("2 Daybeds-Twin", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofabed", "BED6"); //Sofa bed
			PROPERTY_ATTRIBUTES.put("Sofa", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Twin Sofa Sleeper", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Sofa Cama", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Sofa Cama 2 P", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Regular Sofa", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Queen Sofa", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Leather Fold-down Queen Sofa", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Std. Couch", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Convertible Sofa", "CBF19"); //Sofa
			PROPERTY_ATTRIBUTES.put("Double Sofabed", "CBF27"); //Convertible double sofa bed
			PROPERTY_ATTRIBUTES.put("Double Sofa Sleeper", "CBF27"); //Convertible double sofa bed
			PROPERTY_ATTRIBUTES.put("Two Double Rollaways", "CBF27"); //Convertible double sofa bed
			PROPERTY_ATTRIBUTES.put("2 Full Bunk Beds", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("2 Full Bunk", "CBF95"); //2 lower/convert to queen
			PROPERTY_ATTRIBUTES.put("Adjustable Firmness Bed", "CBF30"); //King size bed
			PROPERTY_ATTRIBUTES.put("Studio", "CBF79"); //Studio bedroom
			PROPERTY_ATTRIBUTES.put("1 Bedroom", "ASZ1"); //1 Bedroom
			PROPERTY_ATTRIBUTES.put("1 bedroom", "ASZ1"); //1 Bedroom
			PROPERTY_ATTRIBUTES.put("2 Bedrooms", "ASZ2"); //2 Bedrooms
			PROPERTY_ATTRIBUTES.put("2 bedroom", "ASZ2"); //2 Bedrooms
			PROPERTY_ATTRIBUTES.put("3 Bedrooms", "ASZ3"); //3 Bedrooms
			PROPERTY_ATTRIBUTES.put("3 bedroom", "ASZ3"); //3 Bedrooms
			PROPERTY_ATTRIBUTES.put("4 Bedrooms", "ASZ4"); //4 Bedrooms
			PROPERTY_ATTRIBUTES.put("4 bedroom", "ASZ4"); //4 Bedrooms
			PROPERTY_ATTRIBUTES.put("5 bedroom", "ASZ5"); //5 Bedrooms
			PROPERTY_ATTRIBUTES.put("5+ Bedrooms", "ASZ5"); //5 Bedrooms
			PROPERTY_ATTRIBUTES.put("6 bedroom", "ASZ6"); //6 Bedrooms
			PROPERTY_ATTRIBUTES.put("7 bedroom", "ASZ7"); //7 Bedrooms
			PROPERTY_ATTRIBUTES.put("8 bedroom", "ASZ8"); //8 Bedrooms
			PROPERTY_ATTRIBUTES.put("9+ bedrooms", "ASZ9"); //9+ Bedrooms
			PROPERTY_ATTRIBUTES.put("closets", "RMA94"); //Separate closet
			PROPERTY_ATTRIBUTES.put("Futon", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Queen Futon Sleeper", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("2 Queen Size Futon Beds", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Double Futon Sleeper", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Futon Couch", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Full Size Futon", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Twin Size Futon", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Full Futon Couch", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Full Futon", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("1 Full Futon Couch", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("2 Full Futon Couches", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("Sofa Sleeper?: Futon", "BED2"); //Futon
			PROPERTY_ATTRIBUTES.put("No Sleeper", "DEC5"); //Sleeping rooms not available
			PROPERTY_ATTRIBUTES.put("Master Bedroom with Balcony Access", "CBF73"); //Master bedroom
			PROPERTY_ATTRIBUTES.put("Toddler Bed", "RMA26"); //Crib/Cot
			PROPERTY_ATTRIBUTES.put("2 Twin Cots", "RMA26"); //Crib/Cot
//			PROPERTY_ATTRIBUTES.put("Air Mattress", "///"); 
//			PROPERTY_ATTRIBUTES.put("Two Beds in Master Bedroom", "///"); 
//			PROPERTY_ATTRIBUTES.put("Triple twin bunk + 2 twins", "///"); 
//			PROPERTY_ATTRIBUTES.put("3 Bb Twin/Dbl + Twin", "///"); 
//			PROPERTY_ATTRIBUTES.put("Other", "///"); 
//			PROPERTY_ATTRIBUTES.put("2 Other", "///"); 

			
			
			
			//Bathroom details
			PROPERTY_ATTRIBUTES.put("Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Shower over Tub", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Tub/Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Access Tub/Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Tub/Shower Combo", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Access Tub", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Shower Only", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Ducha / Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Access Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Hand Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Separate Shower", "CBF22"); //Separate shower
			PROPERTY_ATTRIBUTES.put("Steam Shower", "HAC86"); //Steam Bath
			PROPERTY_ATTRIBUTES.put("Steam Shower for 2", "HAC86"); //Steam Bath
			PROPERTY_ATTRIBUTES.put("Walk-In Shower", "PHY48"); //Walk-in shower
			PROPERTY_ATTRIBUTES.put("Shower - Walk-in", "PHY48"); //Walk-in shower
			PROPERTY_ATTRIBUTES.put("Tub", "RMA13"); //Bathtub
			PROPERTY_ATTRIBUTES.put("Tub + Shower Combo", "RMA13"); //Bathtub
			PROPERTY_ATTRIBUTES.put("Tub Only", "RMA13"); //Bathtub
			PROPERTY_ATTRIBUTES.put("Bathtub", "RMA13"); //Bathtub
			PROPERTY_ATTRIBUTES.put("Banera / Bath Tube", "RMA13"); //Bathtub
			PROPERTY_ATTRIBUTES.put("Full Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Full  Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Full Bath Access", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Full Luxury Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Ensuite Full Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("3 master bedroom suites with private full bathrooms", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Half Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Double", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Two Full Bathrooms", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Three-Quarter Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Quarter Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("3/4 Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Three Fourths Bath", "RMA85"); //Private bathroom
			PROPERTY_ATTRIBUTES.put("Half Bath Access", "PHY21"); //Bath Accessible
			PROPERTY_ATTRIBUTES.put("Shared Full Bath", "RMA193"); //Shared bathroom
			PROPERTY_ATTRIBUTES.put("Hair Dryer", "RMA50"); //Hairdryer
			PROPERTY_ATTRIBUTES.put("Hair Dryer: Yes", "RMA50"); //Hairdryer
			PROPERTY_ATTRIBUTES.put("Hair dryer", "RMA50"); //Hairdryer
			PROPERTY_ATTRIBUTES.put("Jacuzzi Tub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi Tub(s)", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi: Yes", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi: Yes. High Speed WiFi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Private Jacuzzi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Spa Tub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Tub in Bath", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi Tub in Master Bath", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Bathtub in Master", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Tub(s)", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Bathtub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi hot tub on private deck", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("7 adult, multiple jet jacuzzi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Whirlpool Tub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jetted Tub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Hot Tub/Jacuzzi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Jacuzzi Bathtub", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Community Jacuzzi", "RMA57"); //Jacuzzi
			PROPERTY_ATTRIBUTES.put("Wall mirror", "RMA239"); //Makeup mirror
			PROPERTY_ATTRIBUTES.put("En Suite", "PHY54"); //Bath in bedroom
			PROPERTY_ATTRIBUTES.put("en Suite", "PHY54"); //Bath in bedroom
			PROPERTY_ATTRIBUTES.put("double sinks", "CBF2"); //Bath
			PROPERTY_ATTRIBUTES.put("Dual Sinks", "CBF2"); //Bath
			PROPERTY_ATTRIBUTES.put("Sink Only", "CBF2"); //Bath
			PROPERTY_ATTRIBUTES.put("Powder Room Bath", "HAC264"); //Bath
			PROPERTY_ATTRIBUTES.put("Toilet/Sink/Shower Stall", "HAC263"); //Bath/Shower
			PROPERTY_ATTRIBUTES.put("Toilet/Sink/Shower/Bath Tub", "HAC263"); //Bath/Shower
			PROPERTY_ATTRIBUTES.put("Toilet/Sink", "HAC243"); //Toilet
			PROPERTY_ATTRIBUTES.put("Washlet Toilet", "HAC243"); //Toilet
			PROPERTY_ATTRIBUTES.put("Double Vanity", "RMA238"); //Double vanity
			PROPERTY_ATTRIBUTES.put("Full bathroom shared by two bedrooms", "RMA193"); //Shared bathroom
//			PROPERTY_ATTRIBUTES.put("Soap/Shampoo Provided", "///"); 
//			PROPERTY_ATTRIBUTES.put("Shampoo/ Basic Bathroom Supplies", "///"); 
//			PROPERTY_ATTRIBUTES.put("Shampoo / Conditioner", "///"); 
//			PROPERTY_ATTRIBUTES.put("Body Soap", "///"); 
//			PROPERTY_ATTRIBUTES.put("Jack and Jill Bathroom", "///"); 
//			PROPERTY_ATTRIBUTES.put("Separate Bathroom Door", "///"); 
//			PROPERTY_ATTRIBUTES.put("2nd Entrance to Bathroom", "///"); 
	
			
			
			//Property Amenities
			PROPERTY_ATTRIBUTES.put("Washer and Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer / Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Common Area Washer / Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("2 Washer / Dryers", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer and Dryer (Private)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer &amp; Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer & Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer &amp; Dryer (Shared Access)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer & Dryer (Shared Access)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Laundry room w/ large capacity washer/dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/dryer in bathroom", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/Dryer In Unit", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Coin-Op Washer and Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/Dryer (In Room)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/Dryer coin operated (shared)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Shared Washer/ Dryer (coin operated)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washer/Dryer (Coin Operated)", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Private Washer/ Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Clothes Dryer: Yes", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Clothes Dryer", "RMA149"); //Washer/dryer
			PROPERTY_ATTRIBUTES.put("Washing Machine", "RMA31"); //Washing Machine
			PROPERTY_ATTRIBUTES.put("Washing Machine: Yes", "RMA31"); //Washing Machine
			PROPERTY_ATTRIBUTES.put("Washer", "RMA31"); //Washing Machine
			PROPERTY_ATTRIBUTES.put("Washing Machine / No Dryer", "RMA31"); //Washing Machine
			PROPERTY_ATTRIBUTES.put("Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace: Wood Burning", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Wood-Burning Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace: Wood Burning, firewood included with rental.", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace: Yes", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Electric Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace: Gas Burning", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Gas Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Gas Fireplace in Master Bedroom", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Multiple Fireplaces", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Multiple Gas Fireplaces", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Multiple Wood Burning Fireplaces", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace (Gas)", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Gas Log Fireplace Master Bedroom", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Gas Log Fireplace Living Room", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Wood Burning Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace (Wood Burning)", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace(s) / Wood", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Gas and Wood Burning Fireplaces", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Large comfortable great room with gas fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Outdoor Fireplace", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fireplace: Yes. High Speed WiFi", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fire Place (Gas Log)", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Fire Wood and Fire Starters", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Wood Burning Fire Place", "RMA41"); //Fireplace
			PROPERTY_ATTRIBUTES.put("Game Room", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Game room", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Game Room: Yes. High Speed WiFi", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Game Room: Yes", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Common Area Game Room", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Multi Sport Game Room", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Board Games", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Arcade Room", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Games: Yes", "HAC44"); //Games room
			PROPERTY_ATTRIBUTES.put("Children's Toys", "HAC193"); //Children's Area
			PROPERTY_ATTRIBUTES.put("Toys: Yes", "HAC193"); //Children's Area
			PROPERTY_ATTRIBUTES.put("Pool Table", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put("Pool Table In Property", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put(": Pool Table", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put("Pool Table: Yes. High Speed WiFi", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put("Pool Table: Yes", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put("Billiards Room", "RST111"); //Billiards
			PROPERTY_ATTRIBUTES.put("Dining Table", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Dinning Table for 4", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Dinning Table for 6", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Dinning Table for 8", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Kitchen Dining Table", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Expandable Dining Table", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("12 Chair Dining Table Looking Over the Ocean", "RMA146"); //Tables and chairs
			PROPERTY_ATTRIBUTES.put("Filtered Water", "RMA121"); 
			PROPERTY_ATTRIBUTES.put("Sega Genesis", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Xbox Game System", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("X-Box", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("XBox", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("XBOX 360", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("X Box 360 w/ Kinect in bunkroom", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Playstation", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Games", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Video games", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Video Games", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Wii Game Console", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Wii in TV Room", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Video Game Console", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Video Game Library", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Nintendo 64", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("TV/DVD/Wii/Xbox", "HAC123"); //Video games
			PROPERTY_ATTRIBUTES.put("Private Hot Tub", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Private Hot Tub - Indoor", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Pool: Year round outdoor pool and hot tub.", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub &amp;#038; Pool: Year round outdoor pool and hot tub.", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub &#038; Pool: Year round outdoor pool and hot tub.", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub: Private", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub: Private Hot Tub with Amazing Views", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub (Private)", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub/Spa", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub: Yes", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Shared Hot Tub", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Pool/Hot Tub", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Indoor/Outdoor Hot Tub", "RST104"); //Hot tub 
			PROPERTY_ATTRIBUTES.put("Hot Tub", "RST104"); //Hot tub
			PROPERTY_ATTRIBUTES.put("8 person Hot Tub", "RST104"); //Hot tub
			PROPERTY_ATTRIBUTES.put("2 Over-sized Hot Tubs", "RST104"); //Hot tub
			PROPERTY_ATTRIBUTES.put("Common Area Hot Tub", "RST104"); //Hot tub
			PROPERTY_ATTRIBUTES.put("Common Area Hot Tub - Indoor", "RST104"); //Hot tub
			PROPERTY_ATTRIBUTES.put("Private Outdoor Hot Tub", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Private Hot Tub - Outdoor", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Two Private Hot Tubs - Outdoor", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("3 Private Outdoor Hot Tubs", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Private Outdoor Hot Tub- Winter Only", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Outdoor Hot Tub", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Common Area Hot Tub - Outdoor", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Community Hot Tub", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Hot Tub: Community", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Hot Tub (Summer Only)", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Outdoor Pool/Hot tub", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Hot Tub Access", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Deck/Hot Tub Access", "CBF89"); //Courtyard hot tub
			PROPERTY_ATTRIBUTES.put("Deck or Balcony", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Deck", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Deck: 1", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Deck: 2", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Deck: There is a small deck with mountain views.", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Deck/Balcony", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Balcony: Yes", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Screened Deck", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Uncovered Deck", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Partially Covered Deck", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Covered Deck", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Balcony", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Lanai (Balcony)", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Lanai", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Terrace", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Patio", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Deck/Patio", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Deck / Patio: Yes", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Patio Terrace", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("*Patio Terrace", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Covered Patio", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Roof Top Patio", "RMA7"); //Balcony/Terrace
			PROPERTY_ATTRIBUTES.put("Porch", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Screened Porch", "FAC2"); //Balcony
			PROPERTY_ATTRIBUTES.put("Private Patio with View", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Patio", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("*Private Patio", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Balcony/Deck with View", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Balcony/Deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Balcony", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("*Private Balcony", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Balcony/deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Balcony/deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Private Deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("*Private Deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Multiple Private Decks", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("*Multiple Private Decks", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Walk out Deck", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Multiple Private Balconies", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("*Multiple Private Balconies", "CBF52"); //Private balcony
			PROPERTY_ATTRIBUTES.put("Elevator", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Elevators", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Elevator: Yes", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Elevator: Yes. High Speed WiFi", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Building Elevator", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Private Elevator", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("*Private Elevator", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Lift Tickets", "HAC33"); //Elevators
			PROPERTY_ATTRIBUTES.put("Full Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Stove: Yes", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Stove/Oven", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Electric Range", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Gas Range", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Stove Top Burner", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Wood Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Wood Burning Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Gas Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Electric Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Stove Oven", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Microwave, Oven, Stove", "RMA105"); //Stove
			PROPERTY_ATTRIBUTES.put("Oven", "RMA77"); //Oven
			PROPERTY_ATTRIBUTES.put("Oven: Yes", "RMA77"); //Oven
			PROPERTY_ATTRIBUTES.put("Horno / Oven", "RMA77"); //Oven
			PROPERTY_ATTRIBUTES.put("Wood Burning Pizza Oven", "RMA77"); //Oven
			PROPERTY_ATTRIBUTES.put("Ground Floor", "RLT17"); //Ground floor
			PROPERTY_ATTRIBUTES.put("Wireless Internet", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wireless Internet Access", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wi/Fi Internet", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wifi Internet", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("High Speed Wireless Internet", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("*High Speed Wireless Internet", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wi-Fi Internet Access", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wifi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("WIFI", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("WiFi - Main House", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Paid WiFi Available", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Wireless Card Available Upon Request", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Ski-in/Ski-out?: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Den/Loft?: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Children: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("TV: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("Internet: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("View: Yes. High Speed WiFi", "RMA123"); //Wireless Internet
			PROPERTY_ATTRIBUTES.put("WiFi Hotspot Optional", "HAC221"); //WiFi Hotspot
			PROPERTY_ATTRIBUTES.put("FREE Secured Wi-Fi", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Wi-Fi: Yes, Free High Speed Wireless Internet", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Free Wireless Internet", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Free WiFi", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Free WiFi (internet)", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Free WI-FI", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Free Wi-Fi", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("WIFI: Free High Speed Internet", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("Internet?: Yes, free high speed wi- fi.", "HAC223"); //Internet Free
			PROPERTY_ATTRIBUTES.put("High-Speed Internet", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("High Speed Internet", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("*High Speed Internet", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("High Speed Internet Access", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Hi-Speed Internet", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Internet Access", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("*Internet Access", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Internet", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Internet: Yes", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Internet/Computer", "RMA54"); //Internet access
			PROPERTY_ATTRIBUTES.put("Guest Computer Available", "RMA21"); //Computer
			PROPERTY_ATTRIBUTES.put("Computer/Printer", "CBF84"); //Computer and printer
			PROPERTY_ATTRIBUTES.put("Telephone", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Telephone: Yes", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Landline Phone", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Local Calls", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Local Calls Included", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Free Calls within the United States", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Free Calls within the United States and Canada", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Free Long Distance Calling", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("*Free Long Distance Calling", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Llamadas locales gratis / Free local calls", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Long Distance Calls", "RMA107"); //Telephone
			PROPERTY_ATTRIBUTES.put("Sauna", "HAC79"); //Sauna
			PROPERTY_ATTRIBUTES.put("Common Area Sauna", "HAC79"); //Sauna
			PROPERTY_ATTRIBUTES.put("Sauna: Private Sauna in unit", "HAC79"); //Sauna
			PROPERTY_ATTRIBUTES.put("Private Sauna", "HAC79"); //Sauna
			PROPERTY_ATTRIBUTES.put("Steam Room", "CBF78"); //Steam room
			PROPERTY_ATTRIBUTES.put("Steam Room (Private)", "CBF78"); //Steam room
			PROPERTY_ATTRIBUTES.put("Private Steam Room", "CBF78"); //Steam room
			PROPERTY_ATTRIBUTES.put("Steamroom/Steam Shower", "CBF78"); //Steam room
			PROPERTY_ATTRIBUTES.put("Common Area Steam Room", "CBF78"); //Steam room
			PROPERTY_ATTRIBUTES.put("Spa", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Spa: Yes", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Golden Door Spa Treatments", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Whirlpool &amp; Sauna", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Whirlpool & Sauna", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Spa/Whirlpool", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Pool/Spa View/Access", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Pool/Spa", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("Spa Services", "RST91"); //Spa
			PROPERTY_ATTRIBUTES.put("health/beauty spa: Yes", "RSN12"); //Spa / Health
			PROPERTY_ATTRIBUTES.put("Air Conditioning - Bedroom", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Air Conditioning - Living Room", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Air conditioning", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Air Conditioning", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("*Air Conditioning", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Air Conditioning: Yes", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Air Conditioning: Yes. High Speed WiFi", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("AC/Heat", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Window A/C", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Central Air Conditioning", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Central Air/Heat", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Central A/C and Heat", "RMA2"); //Air conditioning
			PROPERTY_ATTRIBUTES.put("Central Heating", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("Central Heat", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("Radiant Heat", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("In-floor Heating", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("Baseboard Heat", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("Heating: Yes", "HAC242"); //Heated rooms
			PROPERTY_ATTRIBUTES.put("Ceiling Fans", "RMA157"); //Ceiling fan
			PROPERTY_ATTRIBUTES.put("Cooler", "RMA157"); //Ceiling fan
			PROPERTY_ATTRIBUTES.put("Pack-n-Play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("-Pack-n-play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("Pack N Play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("1 Pack n Play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("2 Pack n Play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("Port-A-Crib/Pack N Play", "CBF7"); //Crib
			PROPERTY_ATTRIBUTES.put("Crib", "CBF7"); //Crib
//			PROPERTY_ATTRIBUTES.put("Portable Fans", "???");
			PROPERTY_ATTRIBUTES.put("Water Front", "ACC55"); //Waterfront
//			PROPERTY_ATTRIBUTES.put("Firewood", "???"); 
//			PROPERTY_ATTRIBUTES.put("Complimentary Firewood", "???"); 
//			PROPERTY_ATTRIBUTES.put("Firewood Provided", "???"); 
//			PROPERTY_ATTRIBUTES.put("Outdoor Firepit", "???");
//			PROPERTY_ATTRIBUTES.put("Firepit", "???");
			PROPERTY_ATTRIBUTES.put("Best Views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("Exquisite Views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("*Exquisite Views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("Amazing Views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("Spectacular views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("View: Yes", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("View", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("Mountain Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Mountain View", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Exquisite Mountain Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("*Exquisite Mountain Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Ski Run Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("View - Mountains", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Exquisite Ski Resort Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("*Exquisite Ski Resort Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Ski Resort Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("*Ski Resort Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Slope View", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Continental Divide View", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("View: Yes, Mountain Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("View: Yes, Amazing Mountain Views", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("View: Ski run views of Park City Mountain Resort.", "RVT10"); //Mountain View
			PROPERTY_ATTRIBUTES.put("Base Village View", "RVT24"); //Countryside view
			PROPERTY_ATTRIBUTES.put("Hideaway Park View", "RVT17"); //Park view
			PROPERTY_ATTRIBUTES.put("Golf Course Views", "RVT5"); //Golf view
			PROPERTY_ATTRIBUTES.put("*Golf Course Views", "RVT5"); //Golf view
			PROPERTY_ATTRIBUTES.put("Views of Park City Main Street", "RVT3"); //City view
			PROPERTY_ATTRIBUTES.put("*Views of Park City Main Street", "RVT3"); //City view
			PROPERTY_ATTRIBUTES.put("Forest View", "RVT18"); //Forest view
			PROPERTY_ATTRIBUTES.put("forests: Yes", "RVT18"); //Forest view
			PROPERTY_ATTRIBUTES.put("River Front", "RVT13"); //River view
			PROPERTY_ATTRIBUTES.put("Riverfront", "RVT13"); //River view
			PROPERTY_ATTRIBUTES.put("River Views", "RVT13"); //River view
			PROPERTY_ATTRIBUTES.put("View - River", "RVT13"); //River view
			PROPERTY_ATTRIBUTES.put("Lake View", "RVT8"); //Lake view
			PROPERTY_ATTRIBUTES.put("Lakefront", "RVT8"); //Lake view
			PROPERTY_ATTRIBUTES.put("Lake Front", "RVT8"); //Lake view
			PROPERTY_ATTRIBUTES.put("Specatcular Lake Views", "RVT8"); //Lake view
			PROPERTY_ATTRIBUTES.put("View - Lake", "RVT8"); //Lake view
			PROPERTY_ATTRIBUTES.put("Ocean Front Property", "RVT25"); //Sea view
			PROPERTY_ATTRIBUTES.put("Ocean View", "RVT25"); //Sea view
			PROPERTY_ATTRIBUTES.put("Near Ocean", "RST70"); //Ocean
			PROPERTY_ATTRIBUTES.put("Lake Nearby", "LOC7"); //Lake
			PROPERTY_ATTRIBUTES.put("Lake Access", "LOC7"); //Lake
			PROPERTY_ATTRIBUTES.put("Waterfront", "ACC55"); //Waterfront
			PROPERTY_ATTRIBUTES.put("Walkway to Water", "ACC55"); //Waterfront
			PROPERTY_ATTRIBUTES.put("Near Downtown", "VWF7"); //City center/downtown
			PROPERTY_ATTRIBUTES.put("Walk to Park City Main Street", "VWF7"); //City center/downtown
			PROPERTY_ATTRIBUTES.put("Located on Park City Main Street", "VWF7"); //City center/downtown
			PROPERTY_ATTRIBUTES.put("Downtown", "VWF7"); //City center/downtown
			PROPERTY_ATTRIBUTES.put("Privacy - Seclusion", "RSN19"); //Quiet Retreat
			PROPERTY_ATTRIBUTES.put("Alarm Clock", "RMA3"); //Alarm clock
			PROPERTY_ATTRIBUTES.put("Wall Clock", "RMA3"); //Alarm clock
			PROPERTY_ATTRIBUTES.put("Safe Deposit Box", "HAC78"); //Safe deposit box
			PROPERTY_ATTRIBUTES.put("Caja Fuerte / Safe Deposit Box", "HAC78"); //Safe deposit box
			PROPERTY_ATTRIBUTES.put("Room Safe", "RMA92"); //Safe
			PROPERTY_ATTRIBUTES.put("Safe", "RMA92"); //Safe
			PROPERTY_ATTRIBUTES.put("Iron &amp; Ironing Board", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron & Ironing Board", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron & ironing board", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron/Ironing Board", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron/Board", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron &amp;#038; Board: Yes", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron &#038; Board: Yes", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Iron", "RMA55"); //Iron
			PROPERTY_ATTRIBUTES.put("Ironing Board", "RMA56"); //Ironing board
			PROPERTY_ATTRIBUTES.put("Beach Towels", "REC43"); //Towels available
//			PROPERTY_ATTRIBUTES.put("Ski In Ski Out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski In/Ski Out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski-In / Ski-Out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski In-Ski Out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski-in/Ski-out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Skiing: Ski in/out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski-in/Ski-out?: Yes", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski On / Ski Off", "???"); 
//			PROPERTY_ATTRIBUTES.put("Walk-In / Ski-Out", "???"); 
//			PROPERTY_ATTRIBUTES.put("Ski Storage", "???"); 
//			PROPERTY_ATTRIBUTES.put("*Ski Storage", "???"); 
			PROPERTY_ATTRIBUTES.put("live theater: Yes", "ACC71"); //Live theatre
			PROPERTY_ATTRIBUTES.put("Theater Room", "RST146"); //Theatre
			PROPERTY_ATTRIBUTES.put("Theater", "RST146"); //Theatre
			PROPERTY_ATTRIBUTES.put("Home Theater", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("Home Theatre", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("Home Theater Room", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("In-Home Theater", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("cinemas: Yes", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("120&quot; screen in Media room", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("120\"; screen in Media room", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("120\"; screen in Media room with THX Surround Sound", "ACC50"); //Theater/cinema
			PROPERTY_ATTRIBUTES.put("Wet Bar", "CBF46"); //Bar
			PROPERTY_ATTRIBUTES.put("*Wet Bar", "CBF46"); //Bar
//			PROPERTY_ATTRIBUTES.put("5 Acres", "???"); 
//			PROPERTY_ATTRIBUTES.put("3 Acres", "???"); 
			PROPERTY_ATTRIBUTES.put("Privacy", "RSN19"); //Quiet Retreat
			PROPERTY_ATTRIBUTES.put("Outdoor Living Space", "RMA243"); //Outdoor Space 
			PROPERTY_ATTRIBUTES.put("Outdoor Seating Area", "RMA243"); //Outdoor Space 
			PROPERTY_ATTRIBUTES.put("Awesome Outdoor Living", "RMA243"); //Outdoor Space 
			PROPERTY_ATTRIBUTES.put("Indoor/Outdoor Living Space", "RMA243"); //Outdoor Space 
			PROPERTY_ATTRIBUTES.put("Stunning Views", "RVT20"); //Various views
			PROPERTY_ATTRIBUTES.put("Dock/Boat Dock", "ACC6"); //Boat dock
			PROPERTY_ATTRIBUTES.put("Boat Dock", "ACC6"); //Boat dock
			PROPERTY_ATTRIBUTES.put("Dock/Boat-Slip", "ACC6"); //Boat dock
			PROPERTY_ATTRIBUTES.put("Designated parking for boats (limited)", "ACC6"); //Boat dock
			PROPERTY_ATTRIBUTES.put("Motor Boat", "TRP2"); //Boat
			PROPERTY_ATTRIBUTES.put("Row Boat", "TRP2"); //Boat
			PROPERTY_ATTRIBUTES.put("Sail Boat", "TRP2"); //Boat
			PROPERTY_ATTRIBUTES.put("-Boat", "TRP2"); //Boat
			PROPERTY_ATTRIBUTES.put("Yard", "HAC25"); //Courtyard
			PROPERTY_ATTRIBUTES.put("Private Fenced Yard", "HAC25"); //Courtyard
			PROPERTY_ATTRIBUTES.put("Fenced Yard", "HAC25"); //Courtyard
			PROPERTY_ATTRIBUTES.put("Private Shaded Yard", "HAC25"); //Courtyard
			PROPERTY_ATTRIBUTES.put("Keyless Entry", "SEC13"); //Electronic key
			PROPERTY_ATTRIBUTES.put("24/7 Guest Help Line", "HAC1"); //Front desk 24h
			PROPERTY_ATTRIBUTES.put("24-Hour Front Desk", "HAC1"); //Front desk 24h
			PROPERTY_ATTRIBUTES.put("24 Hour On Call Maintenance", "HAC2"); //Room service 24h
			PROPERTY_ATTRIBUTES.put("Master Suite", "PIC7"); //Suite
			PROPERTY_ATTRIBUTES.put("Two Luxurious Master Suites", "PIC7"); //Suite
			PROPERTY_ATTRIBUTES.put("Two Master Suites", "PIC7"); //Suite
			PROPERTY_ATTRIBUTES.put("Luxury Home", "PCT57"); //Luxury
			PROPERTY_ATTRIBUTES.put("Luxury", "PCT57"); //Luxury
			PROPERTY_ATTRIBUTES.put("Boot Warmers", "REC11"); //Boot warmers
			PROPERTY_ATTRIBUTES.put("*Boot Warmers", "REC11"); //Boot warmers
			PROPERTY_ATTRIBUTES.put("12 ski hangers, boot warmers and glove warmers", "REC11"); //Boot warmers
			PROPERTY_ATTRIBUTES.put("Boot Dryer in mud room", "REC11"); //Boot warmers
			PROPERTY_ATTRIBUTES.put("Boot dryer for 4 boots in laundry room", "REC11"); //Boot warmers
			
//			PROPERTY_ATTRIBUTES.put("Beach Chairs", "???"); 
//			PROPERTY_ATTRIBUTES.put("Beach Umbrellas", "///"); 
//			PROPERTY_ATTRIBUTES.put("Beach Service Included", "///");
//			PROPERTY_ATTRIBUTES.put("Beach Chairs and Umbrella Included", "///");
//			PROPERTY_ATTRIBUTES.put("Beach Chairs and Umbrella For a Fee", "///");
//			PROPERTY_ATTRIBUTES.put("Basic Beach Toys", "///"); 
//			PROPERTY_ATTRIBUTES.put("Gated Community", "///"); 
//			PROPERTY_ATTRIBUTES.put("Gated Complex", "///"); 
//			PROPERTY_ATTRIBUTES.put("Outdoor Furniture", "///"); 
//			PROPERTY_ATTRIBUTES.put("Deck Furniture", "///"); 
//			PROPERTY_ATTRIBUTES.put("*Hand Crafted Furniture", "///"); 
//			PROPERTY_ATTRIBUTES.put("Hand Crafted Furniture", "///"); 
//			PROPERTY_ATTRIBUTES.put("Porch Furniture", "///"); 
//			PROPERTY_ATTRIBUTES.put("Hammock", "///"); 
//			PROPERTY_ATTRIBUTES.put("Fish Cleaning Station", "///"); 
//			PROPERTY_ATTRIBUTES.put("Snowbirds Welcome!! (winter monthly rentals)", "///"); 
//			PROPERTY_ATTRIBUTES.put("Black Out Curtains (Bedroom)", "///"); 
//			PROPERTY_ATTRIBUTES.put("Piano", "///"); 
//			PROPERTY_ATTRIBUTES.put("Private/Secluded", "///"); 
//			PROPERTY_ATTRIBUTES.put("Heated Tile Floor", "///"); 
//			PROPERTY_ATTRIBUTES.put("Paper Towels Provided", "///"); 
//			PROPERTY_ATTRIBUTES.put("Barter Properties", "///"); 
//			PROPERTY_ATTRIBUTES.put("Daily Curbside Trash Pickup", "///"); 
//			PROPERTY_ATTRIBUTES.put("We are MOBILE. Download our APP on ITunes and Google Play", "///"); 
//			PROPERTY_ATTRIBUTES.put("Flexible Check in/Check Out Days - Call 855-550-8005 for options", "///"); 
//			PROPERTY_ATTRIBUTES.put("Under 25 Booking Options", "///"); 
//			PROPERTY_ATTRIBUTES.put("Chateau La Mer", "///"); 
//			PROPERTY_ATTRIBUTES.put("Sandestin Beach and Golf Resort", "///"); 
//			PROPERTY_ATTRIBUTES.put("Okaloosa Island", "///"); 
//			PROPERTY_ATTRIBUTES.put("30-A", "///"); 
//			PROPERTY_ATTRIBUTES.put("St. Augustine Beach", "///"); 
//			PROPERTY_ATTRIBUTES.put("Panama City Beach", "///"); 
//			PROPERTY_ATTRIBUTES.put("Lake Sinclair", "///"); 
//			PROPERTY_ATTRIBUTES.put("Holiday Isle - Destin", "///"); 
//			PROPERTY_ATTRIBUTES.put("Scenic Highway 98 - Destin", "///"); 
//			PROPERTY_ATTRIBUTES.put("Atlanta", "///"); 
//			PROPERTY_ATTRIBUTES.put("Atlanta - Long Term", "///"); 
//			PROPERTY_ATTRIBUTES.put("Lake Sinclair - Long Term", "///"); 
//			PROPERTY_ATTRIBUTES.put("Gulf Sands Units", "///"); 
//			PROPERTY_ATTRIBUTES.put("Silver Shells", "///"); 
//			PROPERTY_ATTRIBUTES.put("Peaden Service Contracts", "///"); 
//			PROPERTY_ATTRIBUTES.put("Need Service Contracts", "///"); 
//			PROPERTY_ATTRIBUTES.put("Rocking Chair", "///"); 
//			PROPERTY_ATTRIBUTES.put("Swamp Cooler", "///"); 
//			PROPERTY_ATTRIBUTES.put("Fire Pit", "///"); 
//			PROPERTY_ATTRIBUTES.put("Group Site Area with Fire Pit", "///"); 
//			PROPERTY_ATTRIBUTES.put("Loft", "///"); 
//			PROPERTY_ATTRIBUTES.put("Den/Loft?: Yes", "///"); 
//			PROPERTY_ATTRIBUTES.put("Recliner", "///"); 
//			PROPERTY_ATTRIBUTES.put("Unfurnished", "///"); 
//			PROPERTY_ATTRIBUTES.put("0-199/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("200-299/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("300-399/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("400-599/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("600-799/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("$800-999/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("$1000-1499/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("1500+/nt", "///"); 
//			PROPERTY_ATTRIBUTES.put("TA 20%", "///"); 
//			PROPERTY_ATTRIBUTES.put("TA 10%", "///"); 
//			PROPERTY_ATTRIBUTES.put("On Special!", "///"); 
//			PROPERTY_ATTRIBUTES.put("TA 5%", "///"); 
//			PROPERTY_ATTRIBUTES.put("Steps to Ski Lift", "///"); 
//			PROPERTY_ATTRIBUTES.put("$1000 - $2000", "///"); 
		

			//Room Features
			PROPERTY_ATTRIBUTES.put("Samsung 50&quot;", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("LCD TV 32&quot;", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("LCD TV 40&quot;", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("LCD TV 50&quot;", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("LCD TV", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("TV LCD", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("42&quot; TV w/Xbox", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Large Screen TV", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("FLAT SCREEN", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Flat Screen TV", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("TV: Yes, Flat Screen TVs", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Flat Screen TV(s)", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("5 Flat Screen TVs", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("6 LCD HD TVs", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Smart TV's in each bedroom", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Flat Screen TV(s) - Bedroom(s)", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("50 Inch Flatscreen TV", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Flatscreen TV", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Multiple Flat Screen TVs", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("50-60 Inch Flat Screen", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("40-50 Inch Flat Screen", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("30-40 Inch Flat Screen", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("20-30 Inch Flat Screen", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Cool bunk bed room with 42&quot; TV with cable &amp; XBox", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Cool bunk bed room with 42\"; TV with cable & XBox", "MRC178"); //LCD TV
			PROPERTY_ATTRIBUTES.put("Fine Linens", "RMA234"); //Luxury linen type
			PROPERTY_ATTRIBUTES.put("Linens", "RMA234"); //Luxury linen type
			PROPERTY_ATTRIBUTES.put("Linens Provided", "RMA234"); //Luxury linen type
			PROPERTY_ATTRIBUTES.put("Linens Provided: Yes", "RMA234"); //Luxury linen type
			PROPERTY_ATTRIBUTES.put("Linens Included", "RMA234"); //Luxury linen type
			PROPERTY_ATTRIBUTES.put("Stereo System", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Stereo", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Stereo: Yes", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Stereo/Surround Sound", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Surround Sound System", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Surround Sound", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Music System", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Stereo with MP3 Plugin", "RMA104"); //Stereo
			PROPERTY_ATTRIBUTES.put("Microcomponente / CD player", "RMA129"); //CD  player
			PROPERTY_ATTRIBUTES.put("CD Player", "RMA129"); //CD  player
			PROPERTY_ATTRIBUTES.put("CD Players", "RMA129"); //CD  player
			PROPERTY_ATTRIBUTES.put("Stereo/CD", "RMA129"); //CD  player
			PROPERTY_ATTRIBUTES.put("Stereo-CD", "RMA129"); //CD  player
			PROPERTY_ATTRIBUTES.put("iPod Connectivity", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("Ipod Docking Station", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("iPod Docking Station", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("Ipod Dock Station", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("iPod Dock Stations", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("iPOD Dock", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("iPod Dock", "RMA214"); //iPod docking station
			PROPERTY_ATTRIBUTES.put("TV", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Tv", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("TV(s)", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Multiple TVs", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Television", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Television: Yes", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Standard TV", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("TV: Yes", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Antenna TV", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("TV in Bedroom", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("All Bedrooms Have TVs", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("TV/BD", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Standard Definition TV", "RMA90"); //TV
			PROPERTY_ATTRIBUTES.put("Cable TV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Satellite / Cable: Yes", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV - Satellite", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Satellite TV - Main House", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("CATV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/CATV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/CATV/XBOX/Blue Ray DVD", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/SATV/WII/XBOX/Blue Ray DVD", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/SAT in bathroom", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/SATV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/SATV/PS2", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV/CATV/DVD", "RMA210"); //Satellite TV			
			PROPERTY_ATTRIBUTES.put("TV/DVD/CATV", "RMA210"); //Satellite TV			
			PROPERTY_ATTRIBUTES.put("TV/DVD/SATV", "RMA210"); //Satellite TV			
			PROPERTY_ATTRIBUTES.put("Satellite/Cable TV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Cable/Satellite TV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Cable or SATV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Satellite TV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Premiuim Cable", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Standard Cable", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Satellite/Cable Television", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Cable Television", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Satellite or Cable TV", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Cable TV - free", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Free HBO", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV with Digital Satellite", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("Comcast cable TV throughout", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV with Satellite &amp; DVD", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("TV with Satellite & DVD", "RMA210"); //Satellite TV
			PROPERTY_ATTRIBUTES.put("DVD Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD PLAYER", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD's", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD Player: Yes", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV/DVD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV - DVD Only", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV/CATV/DVD/BD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blue Ray Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Bluray Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blu-ray Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blu-Ray DVD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blue Ray DVD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blu-ray/ DVD players", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blu-Ray", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Blu-Ray Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV w/DVD", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV with DVD Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV w/DVD &amp; VCR", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV w/DVD & VCR", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVDs/videos", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("TV/DVD/VCR", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("Reproductor de DVD / DVD Player", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("DVD/CD Player(s)", "RMA163"); //DVD Player
			PROPERTY_ATTRIBUTES.put("VCR Player", "CBF69"); //VCR
			PROPERTY_ATTRIBUTES.put("TV w/VCR", "CBF69"); //VCR
			PROPERTY_ATTRIBUTES.put("VCR", "CBF69"); //VCR
			PROPERTY_ATTRIBUTES.put("HDTV Flat Screen", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("Hi Def Television", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("High Definition TV", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("Roku Streaming Player", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("HDTV", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("HD LCD TV", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("TV: Yes, HDTV", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("HDTV w/ Netflix, Hulu+", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("Apple TV", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("HD LCD TV/DVD in living room &amp; bedrooms", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("HD LCD TV/DVD in living room & bedrooms", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("TV: Yes, HDTV mounted on the wall above fireplace.", "RMA246"); //HDTV
			PROPERTY_ATTRIBUTES.put("Radio", "RMA5"); //AM/FM radio
			PROPERTY_ATTRIBUTES.put("Library", "HAC14"); //Library
			PROPERTY_ATTRIBUTES.put("library: Yes", "HAC14"); //Library
			PROPERTY_ATTRIBUTES.put("Books: Yes", "HAC14"); //Library
			PROPERTY_ATTRIBUTES.put("Books", "HAC14"); //Library
			PROPERTY_ATTRIBUTES.put("Music Library", "CBF61"); //CD library
			PROPERTY_ATTRIBUTES.put("Music Library: Yes", "CBF61"); //CD library
			PROPERTY_ATTRIBUTES.put("Movie Library", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("DVD Movie Library", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Media Library", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Movie/Video Library", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Free Netflix Movies", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Netflix/Hulu", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Netflix", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("DVD rentals", "CBF62"); //Digital video disc library
			PROPERTY_ATTRIBUTES.put("Fax Machine", "BUS9"); //Fax machine
			PROPERTY_ATTRIBUTES.put("Fax", "BUS9"); //Fax machine
//			PROPERTY_ATTRIBUTES.put("Single Level", "???"); 


	
			//Location Amenities
			PROPERTY_ATTRIBUTES.put("Swimming Pool", "RST106"); //Swimming pool
			PROPERTY_ATTRIBUTES.put("Private Swimming Pool", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Private Pool", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Pool (Private)", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Pool: Private", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Pool: Community", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Shared Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Pool: Community (summer only)", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Pool - Community", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Community pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Community Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Beach Front Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Moab Public Pool Reimbursement for one visit", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Pools", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("2 Pools", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Indoor/Outdoor Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Pool Access (Year Round Access)", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Common Area Pool", "HAC71"); //Pool
			PROPERTY_ATTRIBUTES.put("Outdoor Pool", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Pool Access (Summer Only)", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Beach Front Outdoor Pool", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Outdoor Swimming Pool", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Common Area Pool - Outdoor", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("2 Large Outdoor Pools", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Gulf-Front Pool", "RST123"); //Outdoor pool
			PROPERTY_ATTRIBUTES.put("Indoor Pool", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Indoor pool", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Year Round Indoor Swimming Pool", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Common Area Pool - Indoor", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Common Area Pool - Indoor / Outdoor", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Common Area Pool - Year Round", "RST122"); //Indoor pool
			PROPERTY_ATTRIBUTES.put("Pool - Private", "HAC253"); //Private Pool
			PROPERTY_ATTRIBUTES.put("Pool (Summer Only)", "HAC66"); //Pool outside
			PROPERTY_ATTRIBUTES.put("Heated Pool", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Heated pool", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Heated Pool (Seasonal)", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Shared Pool (Heated in Winter)", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Pool - Seasonally Heated", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Year Round Outdoor Heated Pool", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Heated Common Area Pool", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Community Pool - Heated", "HAC49"); //Heated Pool
			PROPERTY_ATTRIBUTES.put("Private Pond", "RST66"); //Lake
			PROPERTY_ATTRIBUTES.put("pond: Yes", "RST66"); //Lake
			PROPERTY_ATTRIBUTES.put("Restaurant", "ACC41"); //Restaurant
			PROPERTY_ATTRIBUTES.put("Restaurant on Site", "ACC41"); //Restaurant
			PROPERTY_ATTRIBUTES.put("Restaurant On-Site", "ACC41"); //Restaurant
			PROPERTY_ATTRIBUTES.put("restaurants: Yes", "ACC41"); //Restaurant
			PROPERTY_ATTRIBUTES.put("Parking Spaces", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking Spaces: 1", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking Spaces: 2", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking Spaces: 3", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking Spaces: 4", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Designated Parking For One Vehicle", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("*Guest Parking", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking: Yes", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Designated Parking", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Free Parking", "HAC42"); //Parking free
			PROPERTY_ATTRIBUTES.put("*Free Parking", "HAC42"); //Parking free
			PROPERTY_ATTRIBUTES.put("Free Undergournd Parking Available", "HAC42"); //Parking free
			PROPERTY_ATTRIBUTES.put("Free Offsite Parking", "HAC42"); //Parking free
			PROPERTY_ATTRIBUTES.put("Covered Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Covered Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Driveway Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Heated Driveway", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Heated Driveway", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Oversized Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Garage: Yes", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Parking: Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Parking: Covered", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private 1-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Private 1-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private 3-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Private 3-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private 4 Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Heated 5 Car Private Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Paid Underground Parking Available", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Underground Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Underground Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private Garage (one car)", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private Garage (two car)", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("One Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Two Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Garage - Heated", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Heated Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Garage - Large SUV", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Garage Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Garage Parking", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Private 2-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Private 2-Car Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Common Area Parking Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("*Common Area Parking Garage", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Standard 2 car garage, with ski storage rack (8+ skis)", "HAC53"); //Parking indoors
			PROPERTY_ATTRIBUTES.put("Estacionamiento / Parking", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking", "HAC63"); //Parking
			PROPERTY_ATTRIBUTES.put("Parking Access", "PHY8"); //Parking Accessible
			PROPERTY_ATTRIBUTES.put("Assigned Parking Space", "HAC64"); //Parking onsite
			PROPERTY_ATTRIBUTES.put("On-Site Parking", "HAC64"); //Parking onsite
			PROPERTY_ATTRIBUTES.put("Shared Parking lot", "HAC65"); //Parking outside
			PROPERTY_ATTRIBUTES.put("Outdoor Parking", "HAC65"); //Parking outside
			PROPERTY_ATTRIBUTES.put("*Outdoor Parking", "HAC65"); //Parking outside
			PROPERTY_ATTRIBUTES.put("Off Street Parking", "HAC186"); //Parking on street
			PROPERTY_ATTRIBUTES.put("Off Street Parking for 2", "HAC186"); //Parking on street
			PROPERTY_ATTRIBUTES.put("Unassigned Off Street Parking", "HAC186"); //Parking on street
			PROPERTY_ATTRIBUTES.put("Seguridad 24hs / Security 24hs", "SEC47"); //24h security
			PROPERTY_ATTRIBUTES.put("24-Hour Security", "SEC47"); //24h security
			PROPERTY_ATTRIBUTES.put("24 hr camera surveillance", "SEC86"); //24h CCTV surveillance
			PROPERTY_ATTRIBUTES.put("Security", "SCY0"); //Security
			PROPERTY_ATTRIBUTES.put("Security System", "SCY0"); //Security
			PROPERTY_ATTRIBUTES.put("Lavaredo / Laundry", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Laundry room", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Laundry: Yes", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Laundry Facilities", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Common Area Laundry Facility", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Common Laundry", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Laundry: Yes. High Speed WiFi", "RCC5"); //Laundry
			PROPERTY_ATTRIBUTES.put("Laundry Service/Dry Cleaning", "HAC58"); //Laundry service
			PROPERTY_ATTRIBUTES.put("laundromat: Yes", "HAC58"); //Laundry service
			PROPERTY_ATTRIBUTES.put("On-Site Coin Laundry", "HAC108"); //Laundry Self Service
			PROPERTY_ATTRIBUTES.put("Coin laundry on each floor", "HAC108"); //Laundry Self Service
			PROPERTY_ATTRIBUTES.put("Laundry: Yes, Private Laundry Room in Unit.", "HAC108"); //Laundry Self Service
			PROPERTY_ATTRIBUTES.put("Shuffleboard Court (Shared)", "RST156"); //Shuffleboard
			PROPERTY_ATTRIBUTES.put("Solaruim / Sun Deck", "RST90"); //Solarium
			PROPERTY_ATTRIBUTES.put("Jardin / Garden", "RST134"); //Garden
			PROPERTY_ATTRIBUTES.put("Garden", "RST134"); //Garden
			PROPERTY_ATTRIBUTES.put("Lawn", "RST134"); //Garden
			PROPERTY_ATTRIBUTES.put("Sunset Cruise", "PCT12"); //Cruise
			PROPERTY_ATTRIBUTES.put("Sightseeing", "RST142"); //Sightseeing tours			
			PROPERTY_ATTRIBUTES.put("sight seeing: Yes", "RST142"); //Sightseeing tours			
			PROPERTY_ATTRIBUTES.put("Sight-Seeing", "RST142"); //Sightseeing tours			
			PROPERTY_ATTRIBUTES.put("Helicopter Tours", "RST142"); //Sightseeing tours			
			PROPERTY_ATTRIBUTES.put("Personalized Excursions", "FAQ1"); //Excursion		
//			PROPERTY_ATTRIBUTES.put("Putting Green Onsite", "REC5"); //Putting green
//			PROPERTY_ATTRIBUTES.put("Horseshoe Pits (Shared)", "???");
//			PROPERTY_ATTRIBUTES.put("Outdoor Dining", "???");
//			PROPERTY_ATTRIBUTES.put("Outdoor Dining Area", "???");
//			PROPERTY_ATTRIBUTES.put("Oceanfront Lounge Chairs (Shared)", "???");
//			PROPERTY_ATTRIBUTES.put("Patio Furniture", "???");
			PROPERTY_ATTRIBUTES.put("Outdoor Shower (Private)", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Outdoor Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Outside Shower", "RMA142"); //Shower
			PROPERTY_ATTRIBUTES.put("Orange Beach Sports Complex", "REF31"); //Sports facility
			PROPERTY_ATTRIBUTES.put("Sports &amp;#038; Activities: Yes", "REF31"); //Sports facility
			PROPERTY_ATTRIBUTES.put("Sports &#038; Activities: Yes", "REF31"); //Sports facility
			PROPERTY_ATTRIBUTES.put("Miniature Golf Course", "RST67"); //Miniature golf
			PROPERTY_ATTRIBUTES.put("Miniature Golf", "RST67"); //Miniature golf
			PROPERTY_ATTRIBUTES.put("Exercise Trail", "RST64"); //Jogging trail
			PROPERTY_ATTRIBUTES.put("Beach", "RST5"); //Beach
			PROPERTY_ATTRIBUTES.put("Public Beach", "RST5"); //Beach
			PROPERTY_ATTRIBUTES.put("Private Beach", "RST5"); //Beach
			PROPERTY_ATTRIBUTES.put("Private Resort Beach Access", "RST155"); //Direct access to beach
			PROPERTY_ATTRIBUTES.put("Beach Access", "RST155"); //Direct access to beach
			PROPERTY_ATTRIBUTES.put("Private Beach Access", "RST155"); //Direct access to beach
			PROPERTY_ATTRIBUTES.put("On the Beach/Water", "RST155"); //Direct access to beach
			PROPERTY_ATTRIBUTES.put("Near Beach (Short Walk)", "ENV4"); //Within 1km of Beach
			PROPERTY_ATTRIBUTES.put("Palapas with Restrooms", "CBF82"); //Guest restroom
			PROPERTY_ATTRIBUTES.put("Tennis Court", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis Court (Shared)", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis Court Access", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis Courts", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis Courts - Free", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis Courts - For a Fee", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Community Tennis", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Common Area Tennis Courts", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Private Tennis Court", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Racquet club (Clay tennis courts)", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Premium Tennis Court", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis: Walk to lifts", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis/Basketball Court", "RSP94"); //Tennis Court
			PROPERTY_ATTRIBUTES.put("Tennis", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("Tennis: Nearby", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("Tennis: Available", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("tennis: Yes", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("Tennis: On Property", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("Tennis: On Property (summer only)", "RSN17"); //Tennis
			PROPERTY_ATTRIBUTES.put("Ping Pong Table", "RST117"); //Table tennis
			PROPERTY_ATTRIBUTES.put("Ping Pong Table.", "RST117"); //Table tennis
			PROPERTY_ATTRIBUTES.put("Golf", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf: Nearby", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("golf: Yes", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf Nearby", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("*Golf Nearby", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf - Free", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golfing", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf Clubs", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf: Park City Golf Club is 1 mile away.", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf: Walk to lifts", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf Cart Included", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf - For a Fee", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf Cart", "RSP27"); //Golf
			PROPERTY_ATTRIBUTES.put("Golf Course Location", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Located on Golf Course", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Golf Courses", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Golf: On Golf Course", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Walk to Park City Golf Course", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Championship Golf Course", "REC2"); //Golf course
			PROPERTY_ATTRIBUTES.put("Swimming", "RST106"); //Swimming pool
			PROPERTY_ATTRIBUTES.put("swimming: Yes", "RST106"); //Swimming pool
			PROPERTY_ATTRIBUTES.put("Ocean Swimming", "RST70"); //Ocean
			PROPERTY_ATTRIBUTES.put("Fishing", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("fishing: Yes", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("fly fishing: Yes", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("freshwater fishing: Yes", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Sound/Bay Fishing", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Surf Fishing", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Fishing Equipment", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Deep Sea Fishing", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Inshore and Bay Fishing", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Trout Fishing Pond ( Summer Only)", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("-Fly Fishing Adult Private", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("-Fly Fishing Child W/ Private", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("-Intro to Fly Fishing Adult min 2 ppl/2hrs", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("-Intro to Fly Fishing Adult Private/2hrs", "RST20"); //Fishing
			PROPERTY_ATTRIBUTES.put("Parasailing", "RST73"); //Parasailing
			PROPERTY_ATTRIBUTES.put("Sailing", "RST80"); //Sailing
			PROPERTY_ATTRIBUTES.put("sailing: Yes", "RST80"); //Sailing
			PROPERTY_ATTRIBUTES.put("Diving", "RST82"); //Scuba diving
			PROPERTY_ATTRIBUTES.put("Water Skiing", "RST99"); //Water-skiing
			PROPERTY_ATTRIBUTES.put("Water Skis", "RST99"); //Water-skiing
			PROPERTY_ATTRIBUTES.put("Waterskiing", "RST99"); //Water-skiing
			PROPERTY_ATTRIBUTES.put("water skiing: Yes", "RST99"); //Water-skiing
			PROPERTY_ATTRIBUTES.put("JetSki", "RSP63"); //Jet-ski
			PROPERTY_ATTRIBUTES.put("jet skiing: Yes", "RSP63"); //Jet-ski
			PROPERTY_ATTRIBUTES.put("Wind-Surfing", "RST101"); //Windsurfing
			PROPERTY_ATTRIBUTES.put("Wind Surfing", "RST101"); //Windsurfing
			PROPERTY_ATTRIBUTES.put("wind-surfing: Yes", "RST101"); //Windsurfing
			PROPERTY_ATTRIBUTES.put("Surfing", "RST116"); //Surfing
			PROPERTY_ATTRIBUTES.put("Surf Board", "RST116"); //Surfing
			PROPERTY_ATTRIBUTES.put("Surf Boards", "RST116"); //Surfing
			PROPERTY_ATTRIBUTES.put("Boogie Boards", "RST116"); //Surfing
			PROPERTY_ATTRIBUTES.put("Boating", "RSP7"); //Boating
			PROPERTY_ATTRIBUTES.put("boating: Yes", "RSP7"); //Boating
			PROPERTY_ATTRIBUTES.put("paddle boating: Yes", "RSP7"); //Boating
			PROPERTY_ATTRIBUTES.put("Mountain Biking", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("-Mountain Biking", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("mountain biking: Yes", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("Cycling", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("cycling: Yes", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("Biking", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("Bikes", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("Bike Rentals", "RST68"); //Mountain biking
			PROPERTY_ATTRIBUTES.put("Hiking Trails on Site", "RST60"); //Hiking trail
			PROPERTY_ATTRIBUTES.put("Hiking Trails", "RST60"); //Hiking trail
			PROPERTY_ATTRIBUTES.put("Hiking", "RST60"); //Hiking trail
			PROPERTY_ATTRIBUTES.put("hiking: Yes", "RST60"); //Hiking trail
			PROPERTY_ATTRIBUTES.put("Foosball", "RST26"); //Football 
			PROPERTY_ATTRIBUTES.put("Activity court (Basketball)", "RST4"); //Basketball  
			PROPERTY_ATTRIBUTES.put("Basketball Hoops", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Basketball Courts", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Basketball Court", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Common Area Basketball Court", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Volleyball &amp; Basketball Court Access", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Volleyball & Basketball Court Access", "RSP4"); //Basketball 
			PROPERTY_ATTRIBUTES.put("Sand Volleyball", "RST98"); //Volleyball  
			PROPERTY_ATTRIBUTES.put("Volleyball Court", "RST98"); //Volleyball
			PROPERTY_ATTRIBUTES.put("Volleyball Set", "RST98"); //Volleyball
			PROPERTY_ATTRIBUTES.put("Horseback Riding", "RST61"); //Horse riding
			PROPERTY_ATTRIBUTES.put("-Horseback Riding", "RST61"); //Horse riding
			PROPERTY_ATTRIBUTES.put("horseback riding: Yes", "RST61"); //Horse riding
			PROPERTY_ATTRIBUTES.put("Horse Riding", "RST61"); //Horse riding
			PROPERTY_ATTRIBUTES.put("Barn and Horse Arena", "RST61"); //Horse Riding
			PROPERTY_ATTRIBUTES.put("Ocean Kayaking", "RST65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("Kayak", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("kayaking: Yes", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("-ATV &amp; Guided Kayak/Rafting 5hr", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("-ATV & Guided Kayak/Rafting 5hr", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("-Kayak Rental Adult 2-man", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("-Kayak Rental Adult 1-man", "RSP65"); //Kayaking
			PROPERTY_ATTRIBUTES.put("Canoe", "RST149"); //Canoeing
			PROPERTY_ATTRIBUTES.put("rafting: Yes", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("whitewater rafting: Yes", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Provo River Adult", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Provo River Child", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Provo w BBQ dinner River Adult", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Provo w BBQ dinner River Child", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Weber Sunser Rafting Tour Adult", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Weber Sunser Rafting Tour Child", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Weber Raft &amp; Rails Adult", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Weber Raft &amp; Rails Child", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("- Provo Canyon Rafting &amp; Zipline", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Adult 1-man", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Rafting Adult 2-man", "RST79"); //River rafting
			PROPERTY_ATTRIBUTES.put("-Tubing Single", "RST9g"); //Tubing
			PROPERTY_ATTRIBUTES.put("-Tubing Double", "RST9g"); //Tubing
			PROPERTY_ATTRIBUTES.put("water tubing: Yes", "RST9g"); //Tubing
			PROPERTY_ATTRIBUTES.put("Walking trail", "RST161"); //Walking track
			PROPERTY_ATTRIBUTES.put("Walk to Trail", "RST161"); //Walking track
			PROPERTY_ATTRIBUTES.put("Walking", "RST161"); //Walking track
			PROPERTY_ATTRIBUTES.put("walking: Yes", "RST161"); //Walking track
			PROPERTY_ATTRIBUTES.put("Beach Walking", "RST161"); //Walking track
			PROPERTY_ATTRIBUTES.put("Fitness Center", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Fitness center", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("fitness center: Yes", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("recreation center: Yes", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Area Fitness Center", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Fitness Room", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Fitness Studio", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Common Area Fitness Room", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Upscale Fitness Center", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Shared Fitness Area", "RST21"); //Fitness center
			PROPERTY_ATTRIBUTES.put("Recreation room", "PIC14"); //Recreational facility
			PROPERTY_ATTRIBUTES.put("Rec Room", "PIC14"); //Recreational facility
			PROPERTY_ATTRIBUTES.put("Common Area Rec Room", "PIC14"); //Recreational facility
			PROPERTY_ATTRIBUTES.put("mountain climbing: Yes", "RSP108"); //Mountain climbing
			PROPERTY_ATTRIBUTES.put("mountaineering: Yes", "RSP108"); //Mountain climbing
			PROPERTY_ATTRIBUTES.put("rock climbing: Yes", "RSP112"); //Rock Climbing
			PROPERTY_ATTRIBUTES.put("Gimnasio / GYM", "RSP35"); //Gym
			PROPERTY_ATTRIBUTES.put("Gym/Workout Facilities", "RSP35"); //Gym
			PROPERTY_ATTRIBUTES.put("Exercise Gear", "HAC35"); //Exercise gym
			PROPERTY_ATTRIBUTES.put("Exercise Room", "HAC35"); //Exercise gym
			PROPERTY_ATTRIBUTES.put("Exercise Room: On Property", "HAC35"); //Exercise gym
			PROPERTY_ATTRIBUTES.put("Exercise Room: In Unit", "HAC35"); //Exercise gym
			PROPERTY_ATTRIBUTES.put("Workout Equipment", "REC60"); //Core training equipment
			PROPERTY_ATTRIBUTES.put("Workout Equipment Access", "REC60"); //Core training equipment
			PROPERTY_ATTRIBUTES.put("Hunting", "RST105"); //Hunting
			PROPERTY_ATTRIBUTES.put("hunting: Yes", "RST105"); //Hunting
			PROPERTY_ATTRIBUTES.put("hunting big game: Yes", "RST105"); //Hunting
			PROPERTY_ATTRIBUTES.put("hunting small game: Yes", "RST105"); //Hunting
			PROPERTY_ATTRIBUTES.put("Ski Access", "ACC45"); //Ski area
			PROPERTY_ATTRIBUTES.put("Ski Access: Walking distance to lifts at PCMR.", "ACC45"); //Ski area
			PROPERTY_ATTRIBUTES.put("Ski", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Skiing", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("skiing: Yes", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Skiing: Drive to lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Walk to Ski Slopes", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Walk To Ski", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Walk to Ski Run", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Walk to Lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Walk to Ski Lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Shuttle to Lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Skiing: Shuttle to lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Skiing: Walk to lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Short Drive to Lifts", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("On Nordic Ski Area", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("cross country skiing: Yes", "RSP88"); //Snow Skiing
			PROPERTY_ATTRIBUTES.put("Downhill Skiing", "RST18"); //Downhill skiing
			PROPERTY_ATTRIBUTES.put("snowboarding: Yes", "RST87"); //Snow boarding
			PROPERTY_ATTRIBUTES.put("snowmobiling: Yes", "RST120"); //Snow mobiling
			PROPERTY_ATTRIBUTES.put("Snowmobile/ATV From The Property", "RST120"); //Snow mobiling		
			PROPERTY_ATTRIBUTES.put("-SnowMobile", "RST120"); //Snow mobiling		
			PROPERTY_ATTRIBUTES.put("Snowmobile Rentals", "RST120"); //Snow mobiling
			PROPERTY_ATTRIBUTES.put("On Nordic Ski Trails", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Ski &amp;#038; Snowboard: Yes", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Ski &#038; Snowboard: Yes", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Deer Valley Resort (walk)", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Deer Valley Ski Slopes (walk to)", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Deer Valley Ski Slopes (easy walk for ski-in/ski-out)", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Deer Valley Lodge (walk to)", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Famous Deer Valley Ski School services (group and private lesson", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("2 blocks to free Deer Valley/ Canyons bus stop", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Walk to Park City Resort &amp; Chair Lifts", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Walk to Park City Resort & Chair Lifts", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Walking To Park City Town Chair Lift", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Walk to Park City Mountain Resort", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Silver Star/Park City Mountain Resort", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Steps to Park City Mountain Resort", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("Walking Distance to the Sundance Resort", "RSN6"); //Skiing / Winter Sports
			PROPERTY_ATTRIBUTES.put("ice skating: Yes", "RST137"); //Ice skating
			PROPERTY_ATTRIBUTES.put("Ice Skating Pond On Property", "RST137"); //Ice skating
			PROPERTY_ATTRIBUTES.put("Private Ski Locker", "REC12"); //Ski lockers
			PROPERTY_ATTRIBUTES.put("Ski Rentals (walk to)", "EQP35"); //Ski rental
			PROPERTY_ATTRIBUTES.put("Ski/Bike rentals &amp; pro shops (walk to)", "EQP35"); //Ski rental
			PROPERTY_ATTRIBUTES.put("Ski/Bike rentals & pro shops (walk to)", "EQP35"); //Ski rental
			PROPERTY_ATTRIBUTES.put("Winter Sledding Next To Property", "RST119"); //Winter sports
			PROPERTY_ATTRIBUTES.put("sledding: Yes", "RST119"); //Winter sports
			PROPERTY_ATTRIBUTES.put("-Inner Tube Sledding", "RST119"); //Winter sports
			PROPERTY_ATTRIBUTES.put("Adventure", "RSN10"); //Outdoor / Adventure
			PROPERTY_ATTRIBUTES.put("Adventure: Yes", "RSN10"); //Outdoor / Adventure
			PROPERTY_ATTRIBUTES.put("racquetball: Yes", "RST77"); //Racquetball
			PROPERTY_ATTRIBUTES.put("Development: Racquet Club", "RST77"); //Racquetball
			PROPERTY_ATTRIBUTES.put("Playground Nearby", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Playground", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("playground: Yes", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Play Ground", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Huge Playground", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Community Playground", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Children's Playground", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Play area on Lagoon", "RST74"); //Playground
			PROPERTY_ATTRIBUTES.put("Olympic Sport Court", "REF31"); //Sports facility
			PROPERTY_ATTRIBUTES.put("-Skeet Shooting", "RST85"); //Skeet shooting
			PROPERTY_ATTRIBUTES.put("Shopping", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("shopping: Yes", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("Outlet Mall Shopping", "RST151"); //Outlet shopping
			PROPERTY_ATTRIBUTES.put("Tanger Outlet Mall Shopping", "RST151"); //Outlet shopping
			PROPERTY_ATTRIBUTES.put("Retail Outlets (walk to)", "RST151"); //Outlet shopping
			PROPERTY_ATTRIBUTES.put("outlet shopping: Yes", "RST151"); //Outlet shopping
			PROPERTY_ATTRIBUTES.put("Craft Supplies Shopping", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("Silver Lake Village (walk to)", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("Silverlake retail stores (walk to)", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("The Wharf - Dining, Shopping, Amphitheater, Ferris Wheel, etc.", "RST83"); //Shopping
			PROPERTY_ATTRIBUTES.put("Nearby Grocery Stores", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("Grocery Stores", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("Grocery Services", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("Grocery Service", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("-Grocery Service", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("Groceries/Drug Store (walk to)", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("groceries: Yes", "HAC149"); //Grocery Shopping Service
			PROPERTY_ATTRIBUTES.put("Shuttle Service", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("In-House Shuttle Service", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Shuttle to slopes", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Shuttle Stop Access", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Free Resort Shuttle", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Free On-Demand Shuttle", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("On Free Winter Shuttle Route", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Complimentary Shuttle", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Short Walk to Free Shuttle", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("*Short Walk to Free Shuttle", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Free Shuttle Route", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("Located on Free Shuttle Route", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("*Located on Free Shuttle Route", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("FREE DOOR TO DOOR SKI SHUTTLE", "TRP17"); //Shuttle
			PROPERTY_ATTRIBUTES.put("-Airport Trans Shuttle", "HAC41"); //Airport shuttle
			PROPERTY_ATTRIBUTES.put("Airport Transportion", "HAC41"); //Airport shuttle
			PROPERTY_ATTRIBUTES.put("Historic Downtown", "ACC23"); //Historic building
			PROPERTY_ATTRIBUTES.put("Historic: Yes", "ACC23"); //Historic building
			PROPERTY_ATTRIBUTES.put("Fort Morgan Historical Site", "RSN8"); //Museum / Cultural / Historical
			PROPERTY_ATTRIBUTES.put("Cultural/Historic", "RSN8"); //Museum / Cultural / Historical
			PROPERTY_ATTRIBUTES.put("museums: Yes", "ACC32"); //Museum
			PROPERTY_ATTRIBUTES.put("River Nearby", "LOC27"); //River
			PROPERTY_ATTRIBUTES.put("-Weber River 4hr Child", "LOC27"); //River
			PROPERTY_ATTRIBUTES.put("-Weber River 4hr Adult", "LOC27"); //River
			PROPERTY_ATTRIBUTES.put("River", "RST78"); //River
			PROPERTY_ATTRIBUTES.put("River Access", "RST78"); //River
			PROPERTY_ATTRIBUTES.put("Bicycle", "TRP1"); //Bicycle
			PROPERTY_ATTRIBUTES.put("Bicycles", "TRP1"); //Bicycle
			PROPERTY_ATTRIBUTES.put("Ramp Access", "PHY49"); //Ramp Access
			PROPERTY_ATTRIBUTES.put("Wider Halls", "PHY108"); //Wide corridors
			PROPERTY_ATTRIBUTES.put("Wider Doorways", "PHY107"); //Wide entrance
			PROPERTY_ATTRIBUTES.put("Resort", "ACC40"); //Resort
			PROPERTY_ATTRIBUTES.put("hot air ballooning: Yes", "RST153"); //Hot Air Ballooning
			PROPERTY_ATTRIBUTES.put("Massage services", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("In Room Massage", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("-Massage", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("Guest Massage", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("massage therapist: Yes", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("Massage In House (avail for fee)", "HAC61"); //Massage services
			PROPERTY_ATTRIBUTES.put("Housekeeping", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("Additional Housekeeping Optional", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("Housekeeping Included", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("Daily Housekeeping", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("*Daily Housekeeping", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("Housekeeper Included: Yes", "CSC6"); //Housekeeping
			PROPERTY_ATTRIBUTES.put("Medical Services", "REF21"); //Medical facility
			PROPERTY_ATTRIBUTES.put("medical services: Yes", "REF21"); //Medical facility
			PROPERTY_ATTRIBUTES.put("Staff", "HAC106"); //Bell staff/porter
			PROPERTY_ATTRIBUTES.put("Child Friendly", "SUI1"); //Child Friendly
			PROPERTY_ATTRIBUTES.put("children welcome: Yes", "HAC128"); //Children welcome
			PROPERTY_ATTRIBUTES.put("Children: Yes", "HAC128"); //Children welcome
			PROPERTY_ATTRIBUTES.put("Childcare", "HAC194"); //Children's nursery
			PROPERTY_ATTRIBUTES.put("Babysitter: Yes", "HAC194"); //Children's nursery
			PROPERTY_ATTRIBUTES.put("Elderly Friendly", "SUI2"); //Elderly Accessible
			PROPERTY_ATTRIBUTES.put("Handicapped Access", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Handicapped Accessible", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Handicap Accessible", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Handicap: Yes", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Handicap: Yes. High Speed WiFi", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Handicap Friendly", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Disability Friendly", "SUI3"); //Handicap Accessible
			PROPERTY_ATTRIBUTES.put("Wheel chair access", "PHY24"); //Wheelchair Accessible
			PROPERTY_ATTRIBUTES.put("wheelchair accessible: Yes", "PHY24"); //Wheelchair Accessible
			PROPERTY_ATTRIBUTES.put("ADA Compliant/ Wheelchair Accessible", "PHY24"); //Wheelchair Accessible
			PROPERTY_ATTRIBUTES.put("Pet Friendly", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("Pet friendly", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("Pets Considered", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("Pet Home", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("Pets Allowed: Yes", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("Pets Allowed with Additional Fee", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("*Pets Allowed with Additional Fee", "SUI4"); //Pet Friendly
			PROPERTY_ATTRIBUTES.put("*Small Pets Allowed", "PET8"); //Small Animals
			PROPERTY_ATTRIBUTES.put("Groups", "INV18"); //Group available
			PROPERTY_ATTRIBUTES.put("No Smoking", "RMP74"); //Non-smoking
			PROPERTY_ATTRIBUTES.put("Non-smoking", "RMP74"); //Non-smoking
			PROPERTY_ATTRIBUTES.put("non smoking only: Yes", "RMP74"); //Non-smoking
			PROPERTY_ATTRIBUTES.put("Smoking Permitted", "RMA101"); //Smoking
			PROPERTY_ATTRIBUTES.put("Singles", "GRP1"); //Individual Person
			PROPERTY_ATTRIBUTES.put("Wedding", "HAC104"); //Wedding services
			PROPERTY_ATTRIBUTES.put("Weddings Allowed", "HAC104"); //Wedding services
			PROPERTY_ATTRIBUTES.put("Rural", "LOC11"); //Rural
			PROPERTY_ATTRIBUTES.put("Family", "RES7"); //Family
			PROPERTY_ATTRIBUTES.put("Family: Yes", "RES7"); //Family
			PROPERTY_ATTRIBUTES.put("Daytona Airport (DAB)", "LOC1"); //Airport
			PROPERTY_ATTRIBUTES.put("Jacksonville Airport (JAX)", "LOC1"); //Airport
			PROPERTY_ATTRIBUTES.put("Saint Augustine Airport (UST)", "LOC1"); //Airport
			PROPERTY_ATTRIBUTES.put("Wildlife Viewing", "PCT36"); //Wildlife reserve
			PROPERTY_ATTRIBUTES.put("wildlife viewing: Yes", "PCT36"); //Wildlife reserve
			PROPERTY_ATTRIBUTES.put("Onsite Office", "EVT35"); //Office
			PROPERTY_ATTRIBUTES.put("Concierge", "CBF45"); //Concierge
			PROPERTY_ATTRIBUTES.put("Concierge Service", "CBF45"); //Concierge
			PROPERTY_ATTRIBUTES.put("Concierge Services Available", "CBF45"); //Concierge
			PROPERTY_ATTRIBUTES.put("Personal Concierge", "CBF45"); //Concierge
			PROPERTY_ATTRIBUTES.put("Concierge On Site", "CBF45"); //Concierge
			PROPERTY_ATTRIBUTES.put("Local Guides/Maps", "CTT6"); //Map
			PROPERTY_ATTRIBUTES.put("All Terrain Vehicle", "VCP1"); //Vehicle rental
			PROPERTY_ATTRIBUTES.put("Automobile", "VCP1"); //Vehicle rental
			PROPERTY_ATTRIBUTES.put("Car Service", "ACC11"); //Car rental
			PROPERTY_ATTRIBUTES.put("Walk to Restaurants/Bars", "ENV5"); //Within 1km of Restaurants
			PROPERTY_ATTRIBUTES.put("Restaurants (walk to)", "ENV5"); //Within 1km of Restaurants
			PROPERTY_ATTRIBUTES.put("Silverlake restaurants &amp; Bars (walk to)", "ENV5"); //Within 1km of Restaurants
			PROPERTY_ATTRIBUTES.put("Silverlake restaurants & Bars (walk to)", "ENV5"); //Within 1km of Restaurants
			PROPERTY_ATTRIBUTES.put("Zoo", "ACC57"); //Zoo
			PROPERTY_ATTRIBUTES.put("zoo: Yes", "ACC57"); //Zoo
			PROPERTY_ATTRIBUTES.put("On Site Grill/Bar", "CBF46"); //Bar
			PROPERTY_ATTRIBUTES.put("Covered Pavillion", "PCT33"); //Tent
			PROPERTY_ATTRIBUTES.put("Gazebo", "PCT33"); //Tent
			PROPERTY_ATTRIBUTES.put("Farm Holidays", "ACC21"); //Farm
			PROPERTY_ATTRIBUTES.put("Food/Wine", "RSN4"); //Great Food / Wine
			PROPERTY_ATTRIBUTES.put("Festivals", "RSN7"); //Concert / Music Festivals
			PROPERTY_ATTRIBUTES.put("Conference Rooms - extra fee", "ACC16"); //Conference center
			PROPERTY_ATTRIBUTES.put("Mansion", "ACC35"); //Palace
			PROPERTY_ATTRIBUTES.put("Adjoining Townhouse(s)", "REF33"); //Town center
			PROPERTY_ATTRIBUTES.put("On (free) Bus Route", "REF8"); //Bus station
			PROPERTY_ATTRIBUTES.put("Close Shuttle Stop Access", "REF8"); //Bus station
			PROPERTY_ATTRIBUTES.put("On free shuttle bus route to Old Town/Main St in Park City", "REF8"); //Bus station
			PROPERTY_ATTRIBUTES.put("Wine Room", "ACC56"); //Winery
			PROPERTY_ATTRIBUTES.put("Wine Cellar", "ACC56"); //Winery
			PROPERTY_ATTRIBUTES.put("Stein Erickson Lodge", "PCT22"); //Lodge
			PROPERTY_ATTRIBUTES.put("Snowpark Lodge (walking)", "RST119"); //Winter sports
			PROPERTY_ATTRIBUTES.put("Office Suite", "EVT35"); //Office
			PROPERTY_ATTRIBUTES.put("Private 12-Passenger Van (available free)", "SIZ12"); //12 passenger van
			PROPERTY_ATTRIBUTES.put("Private Heated Walkway", "SEC31"); //Lighted walkways
			PROPERTY_ATTRIBUTES.put("Service Staff/Guest Quarters", "HAC103"); //Multilingual staff
			PROPERTY_ATTRIBUTES.put("Meeting Facilities", "BUS46"); //Meeting room
			PROPERTY_ATTRIBUTES.put("2-Living Rooms", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("*2-Living Rooms", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("Two Living Rooms with Sleeper Sofas", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("Two Living Areas", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("3 Living Areas", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("Living Room: Yes", "CBF72"); //Living room
			PROPERTY_ATTRIBUTES.put("Dance Floor", "MRC157"); //Dance floor
			PROPERTY_ATTRIBUTES.put("Corner Unit", "RLT4"); //Corner
			PROPERTY_ATTRIBUTES.put("Dinner at your service", "RSI10"); //Special meal services
			PROPERTY_ATTRIBUTES.put("Dining Reservations", "RSI10"); //Special meal services
			PROPERTY_ATTRIBUTES.put("ATM/bank: Yes", "HAC7"); //ATM nearby
			PROPERTY_ATTRIBUTES.put("hospital: Yes", "ACC24"); //Hospital
			PROPERTY_ATTRIBUTES.put("theme parks: Yes", "RSN2"); //Theme / Amusement Park
			PROPERTY_ATTRIBUTES.put("Budget: Yes", "BCC2"); //Budget
			PROPERTY_ATTRIBUTES.put("Tourist Attractions: Yes", "INF7"); //Attractions
			PROPERTY_ATTRIBUTES.put("festivals: Yes", "RSN7"); //Concert / Music Festivals
			PROPERTY_ATTRIBUTES.put("synagogues: Yes", "ACC82"); //Synagogue
			PROPERTY_ATTRIBUTES.put("bird watching: Yes", "RST127"); //Bird watching
			
//			PROPERTY_ATTRIBUTES.put("Horse Shoes", "///");
//			PROPERTY_ATTRIBUTES.put("", "///");
//			PROPERTY_ATTRIBUTES.put("Personal Water Craft Allowed", "///");
//			PROPERTY_ATTRIBUTES.put("Personal Water Craft", "///");
//			PROPERTY_ATTRIBUTES.put("Car Necessary", "///");
//			PROPERTY_ATTRIBUTES.put("Car Recommended", "///");
//			PROPERTY_ATTRIBUTES.put("*Car Recommended", "///");
//			PROPERTY_ATTRIBUTES.put("No Pets", "///");
//			PROPERTY_ATTRIBUTES.put("pets not allowed: Yes", "///");
//			PROPERTY_ATTRIBUTES.put("Minimum Age for Renters", "///");
//			PROPERTY_ATTRIBUTES.put("Rustic", "///");
//			PROPERTY_ATTRIBUTES.put("Romantic", "///");
//			PROPERTY_ATTRIBUTES.put("Romantic: Yes", "///");
//			PROPERTY_ATTRIBUTES.put("Disney World", "///");
//			PROPERTY_ATTRIBUTES.put("Universal Studios", "///");
//			PROPERTY_ATTRIBUTES.put("Universal Studios Island Of Adventures", "///");
//			PROPERTY_ATTRIBUTES.put("Swingset", "///");
//			PROPERTY_ATTRIBUTES.put("No Onsite Office", "///");
//			PROPERTY_ATTRIBUTES.put("Complimentary Guest Experience Coordinator", "///");
//			PROPERTY_ATTRIBUTES.put("Private Chef Service", "///");
//			PROPERTY_ATTRIBUTES.put("Private Chef", "///");
//			PROPERTY_ATTRIBUTES.put("-Chef Services", "///");
//			PROPERTY_ATTRIBUTES.put("Chef In House (avail for fee)", "///");
//			PROPERTY_ATTRIBUTES.put("Personal Chef", "///");
//			PROPERTY_ATTRIBUTES.put("Private Setting", "///");
//			PROPERTY_ATTRIBUTES.put("Best Location", "///");
//			PROPERTY_ATTRIBUTES.put("Main Street Access", "///");
//			PROPERTY_ATTRIBUTES.put("Luggage Shipping", "///");
//			PROPERTY_ATTRIBUTES.put("Ski Valet", "///");
//			PROPERTY_ATTRIBUTES.put("Apres Ski/Happy Hour Bartender", "///");
//			PROPERTY_ATTRIBUTES.put("Vaulted Ceilings", "///");
//			PROPERTY_ATTRIBUTES.put("*Vaulted Ceilings", "///");
//			PROPERTY_ATTRIBUTES.put("Picnic Area", "///");
//			PROPERTY_ATTRIBUTES.put("Boat Launch", "///");
//			PROPERTY_ATTRIBUTES.put("Pier", "///");
//			PROPERTY_ATTRIBUTES.put("Onsite dining", "///");
//			PROPERTY_ATTRIBUTES.put("Gulf State Park", "///");
//			PROPERTY_ATTRIBUTES.put("Foosball Table", "///");
//			PROPERTY_ATTRIBUTES.put("Discounts on Fun Moab Activities", "///");
//			PROPERTY_ATTRIBUTES.put("One night rentals available - Call or email for details", "///");
//			PROPERTY_ATTRIBUTES.put("Private Island", "///"); 
//			PROPERTY_ATTRIBUTES.put("Photography", "///"); 			
//			PROPERTY_ATTRIBUTES.put("photography: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Lockable Bike Storage", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Bike Bench", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Air Hockey Table", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Air Hockey", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Dog Kennel", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Swim Pond", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Roller Blading", "///"); 			
//			PROPERTY_ATTRIBUTES.put("roller blading: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Beach Shower", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Boardwalk to the beach", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Within 150 yards Of Slopes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Accepts Partial Stay (3 night min.)", "///"); 					
//			PROPERTY_ATTRIBUTES.put("Sandbox", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Beaver Mountain Ski Passes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("OCD Properties", "///"); 			
//			PROPERTY_ATTRIBUTES.put("BLCC Properties", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Steps to Ski Slopes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Lobby", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Professionally decorated", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Newly Remodeled", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Newly Remodeled", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Newly Constructed", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Newly Constructed", "///"); 			
//			PROPERTY_ATTRIBUTES.put("New Appliances", "///"); 			
//			PROPERTY_ATTRIBUTES.put("'Home of the Stars' Designation", "///"); 			
//			PROPERTY_ATTRIBUTES.put("'Dream Home of Park City' Designation", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Adjoining Studio", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Waterfall", "///"); 			
//			PROPERTY_ATTRIBUTES.put("waterfalls: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Mud Room", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Mud Room", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Mud Room w/ ski and board storage", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Slopeside", "///"); 			
//			PROPERTY_ATTRIBUTES.put("I", "///"); 			
//			PROPERTY_ATTRIBUTES.put("C", "///"); 			
//			PROPERTY_ATTRIBUTES.put("[", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Viking Appliances", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Located at The Jordanelle", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Slate Floors", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Slate Floors", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Penthouse", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Penthouse", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Walk to Redstone Village", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Hardwood Floors", "///"); 			
//			PROPERTY_ATTRIBUTES.put("*Hardwood Floors", "///"); 			
//			PROPERTY_ATTRIBUTES.put("New mattresses throughout (summer 2013)", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Mountain Guide", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Airport Summer Trans Private Sedan Each", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Park City Lift Tickets", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Canyons Lift Tickets", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Deer Valley Lift Tickets", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Ski Valet Per Day, Per Person", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Airport Private SUV Winter", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-4x4 Jeep Wrangler Nov25-Apr15", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-4x4 Jeep Wrangler Apr16-Nov24", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-4x4 Jeep Wrangler Weekly", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-General Transportation", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Private Sundance Drivers", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Dog Sled", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Heli-Ski Tours", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Weber Full Moon Tour Adult", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Weber Full Moon Tour Child", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Park City Food Tours", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Ski Valet Helmet", "///"); 			
//			PROPERTY_ATTRIBUTES.put("-Ski Valet Junior", "///"); 			
//			PROPERTY_ATTRIBUTES.put("wheelchair inaccessible: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("paragliding: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Away From It All: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Glenfiddich", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: La Maconnerie", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Double Eagle", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Snowcrest", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Comstock Lodge", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Courchevel", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Boulder Creek", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Ridgepoint", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Private Homes - DV", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: The Cove", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Development: Pinnacle", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Pantry Items: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Summer: Y", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Summer: N", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Winter: Y", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Winter: N", "///"); 			
//			PROPERTY_ATTRIBUTES.put("autumn foliage: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Stories: 1", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Stories: 3", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Stories: 2", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Stories: 4", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Use Code: Rental", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Complex: Rental", "///"); 			
//			PROPERTY_ATTRIBUTES.put("not necessary: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("recommended: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Guests provide their own meals: Yes", "///"); 			 			
//			PROPERTY_ATTRIBUTES.put("scenic drives: Yes", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Laundry: No laundry facilities.", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Floor: 2nd floor.", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Patio Heaters", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Barn", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Backs to National Forest", "///"); 			
//			PROPERTY_ATTRIBUTES.put("3,400 Square Feet", "///"); 			
//			PROPERTY_ATTRIBUTES.put("Spices", "///"); 			
//			PROPERTY_ATTRIBUTES.put("View - Territorial", "///"); 			



			
			//Kitchen Amenities
			PROPERTY_ATTRIBUTES.put("Coffee Maker", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Coffee Maker: Yes", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Coffee Pot", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Cafetera / Coffee Maker", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Keurig Coffee Maker", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Gourmet Coffee Maker", "RMA19"); //Coffee/Tea maker
			PROPERTY_ATTRIBUTES.put("Coffee and Tea", "HAC137"); //Coffee/Tea in Room
			PROPERTY_ATTRIBUTES.put("Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Refrigerator: Yes", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Full Size Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Small Size Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Small Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Extra Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Mini-Fridge", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Mini Fridge", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("SubZero Refrigerator", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Wine Cooler", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("*Wine Cooler", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Freezer", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Refrigerator without Ice Maker", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Refrigerator with Ice  Maker", "RMA88"); //Refrigerator
			PROPERTY_ATTRIBUTES.put("Microwave", "RMA68"); //Microwave
			PROPERTY_ATTRIBUTES.put("Microondas / Microwave", "RMA68"); //Microwave
			PROPERTY_ATTRIBUTES.put("Microwave: Yes", "RMA68"); //Microwave
			PROPERTY_ATTRIBUTES.put("Dishwasher", "RMA32"); //Dishwasher
			PROPERTY_ATTRIBUTES.put("Dishwasher: Yes", "RMA32"); //Dishwasher
			PROPERTY_ATTRIBUTES.put("Dishwasher: Yes. High Speed WiFi", "RMA32"); //Dishwasher
			PROPERTY_ATTRIBUTES.put("Dish Towels", "RMA237"); //Dish-cleaning supplies
			PROPERTY_ATTRIBUTES.put("Dish/Dishwasher Soap", "RMA237"); //Dish-cleaning supplies
			PROPERTY_ATTRIBUTES.put("Toaster", "RMA167"); //Toaster
			PROPERTY_ATTRIBUTES.put("Toaster Oven", "RMA167"); //Toaster
			PROPERTY_ATTRIBUTES.put("Toaster: Yes", "RMA167"); //Toaster
			PROPERTY_ATTRIBUTES.put("Tostadora / Toaster", "RMA167"); //Toaster
//			PROPERTY_ATTRIBUTES.put("Coffee Grinder", "???"); 
			PROPERTY_ATTRIBUTES.put("Dining Room", "CBF71"); //Dining Room
			PROPERTY_ATTRIBUTES.put("Eat-in Kitchen", "CBF71"); //Dining Room
			PROPERTY_ATTRIBUTES.put("Dining Room: Yes", "CBF71"); //Dining Room
			PROPERTY_ATTRIBUTES.put("Dining Area: Yes", "CBF71"); //Dining Room
			PROPERTY_ATTRIBUTES.put("Indoor BBQ", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("BBQ Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("*BBQ Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grill/BBQ: Yes", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grill/BBQ: Yes. High Speed WiFi", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Parrilla / BBQ", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("BBQ", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("BBQ Grills", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("BBQ Pit", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Barbecue", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Charcoal Grill (BYOC)", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Community BBQ Grills - Charcoal", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Charcoal Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Charcoal BBQ", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Charcoal Barbecue", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Gas Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Gas Barbecue", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grill - Gas", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Gas BBQ Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Electric Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grill - Charcoal", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Grilling Area", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Designated Grilling Area", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Private Propane Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Shared Outdoor Grill in Common Area", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Outdoor Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Outdoor Grill: Yes", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Natural Gas Grill", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Propane Grill (Propane provided)", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Outdoor gas grill on private deck", "HAC118"); //Barbeque Grill
			PROPERTY_ATTRIBUTES.put("Kitchen", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Outdoor Kitchen", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Kitchen: Yes", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("2 Kitchens", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Fully Stocked Kitchen", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Gourmet Kitchen", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Three Gourmet Kitchens", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Gourmet kitchen w/ granite counters &amp; new appliances", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Gourmet kitchen w/ granite counters & new appliances", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Full Kitchen", "RMA59"); //Kitchen 
			PROPERTY_ATTRIBUTES.put("Ice Maker", "HAC52"); //Ice machine 
			PROPERTY_ATTRIBUTES.put("Kitchenette", "RMA61"); //Kitchenette
			PROPERTY_ATTRIBUTES.put("Cooking Utensils Provided", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Cooking Utinsils", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Dishes and Utensils", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Dishes &amp;#038; Utensils: Yes", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Dishes &#038; Utensils: Yes", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Cooking Utensils", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Dinnerware", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Dinnerware / Cookware Furnished", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Cookware", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Fully Equipped (dishes, cookware, silverware)", "RMA98"); //Silverware/utensils
			PROPERTY_ATTRIBUTES.put("Fully Equipped Kitchen", "RMA98"); //Silverware/utensils 
			PROPERTY_ATTRIBUTES.put("Fully Equipped Kitchens", "RMA98"); //Silverware/utensils 
			PROPERTY_ATTRIBUTES.put("Pots and Pans", "RMA82"); //Pots and pans
			PROPERTY_ATTRIBUTES.put("Crock Pot", "RMA82"); //Pots and pans
			PROPERTY_ATTRIBUTES.put("Breakfast Bar", "EVT4"); //Breakfast
			PROPERTY_ATTRIBUTES.put("Kitchen Bar/Island", "CBF46"); //Bar
			PROPERTY_ATTRIBUTES.put("Mid-Stay Cleaning", "USC3"); //Cleaning services
			PROPERTY_ATTRIBUTES.put("Catering Available", "HAC226"); //Catering services
			PROPERTY_ATTRIBUTES.put("Catering available: Yes", "HAC226"); //Catering services
			PROPERTY_ATTRIBUTES.put("-Catering", "HAC226"); //Catering services
			PROPERTY_ATTRIBUTES.put("Toddler High Chair", "EQP8"); //Child toddler seat
			PROPERTY_ATTRIBUTES.put("High Chair", "EQP8"); //Child toddler seat
			PROPERTY_ATTRIBUTES.put("-High Chair", "EQP8"); //Child toddler seat
//			PROPERTY_ATTRIBUTES.put("Blender", "///");  
//			PROPERTY_ATTRIBUTES.put("Mixer", "///");  
//			PROPERTY_ATTRIBUTES.put("Citrus Juicer", "///");  
//			PROPERTY_ATTRIBUTES.put("Garbage Disposal", "///"); 
//			PROPERTY_ATTRIBUTES.put("Rice Cooker", "///"); 
//			PROPERTY_ATTRIBUTES.put("Stainless Steel Appliances", "///"); 
//			PROPERTY_ATTRIBUTES.put("Wolf Appliances", "///"); 
//			PROPERTY_ATTRIBUTES.put("SubZero Appliances", "///"); 
//			PROPERTY_ATTRIBUTES.put("Granite Countertops", "///"); 
//			PROPERTY_ATTRIBUTES.put("Granite Counter Tops", "///"); 
//			PROPERTY_ATTRIBUTES.put("Tile Flooring", "///"); 
//			PROPERTY_ATTRIBUTES.put("Salt, Pepper, Basic Kitchen Goods", "///"); 


			
			//Type of Property
			PROPERTY_ATTRIBUTES.put("Vacation Home", "PCT34"); //Vacation Rental
			PROPERTY_ATTRIBUTES.put("Condo", "PCT8"); //Condominium
			PROPERTY_ATTRIBUTES.put("Chalet", "PCT7"); //Chalet
			PROPERTY_ATTRIBUTES.put("Townhome", "PCT3"); //Apartment
			PROPERTY_ATTRIBUTES.put("Cabin", "PCT5"); //Cabin or Bungalow
//			PROPERTY_ATTRIBUTES.put("Duplex", "???"); 
			PROPERTY_ATTRIBUTES.put("Hotel", "PCT20"); //Hotel
			PROPERTY_ATTRIBUTES.put("Inn", "PCT21"); //Inn

			
		}
		
		if (PROPERTY_ATTRIBUTES.get(attribute) != null) {
			attributes.add(PROPERTY_ATTRIBUTES.get(attribute));
		}
		else{
			PROPERTY_ATTRIBUTES_NOT_FOUND.add(attribute);
			//System.out.println("Atribute not find: " + attribute);
		}
	}
	
	
	
	
	private String getCreditCardStreamlineTypeId(CreditCardType systemCCtype) {
		String ccTypeId = "";
		if(systemCCtype == CreditCardType.VISA){
			ccTypeId = "1";
		}else if(systemCCtype == CreditCardType.MASTER_CARD){
			ccTypeId = "2";
		}else if(systemCCtype == CreditCardType.AMERICAN_EXPRESS){
			ccTypeId = "3";
		}else if(systemCCtype == CreditCardType.DISCOVER){
			ccTypeId = "4";
		}
		return ccTypeId;
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Streamline inquireReservation()");
	}

}