/**
 * @author	Marko Ovuka
 * @see		License at http://razorpms.com/razor/License.html
 * @version	1.00
 */
package net.cbtltd.rest.lodgix;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.homeaway.feedparser.domain.Address;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.Bathroom;
import net.cbtltd.rest.homeaway.feedparser.domain.Bedroom;
import net.cbtltd.rest.homeaway.feedparser.domain.Image;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndexEntry;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingFeatureValue;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingReservations;
import net.cbtltd.rest.homeaway.feedparser.domain.Rate;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriod;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods;
import net.cbtltd.rest.homeaway.feedparser.domain.RateType;
import net.cbtltd.rest.homeaway.feedparser.domain.UnitFeatureValue;
import net.cbtltd.rest.homeaway.feedparser.domain.UnitWSD;
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
import net.cbtltd.server.YieldService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
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
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.google.gwt.dev.util.collect.HashSet;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.utils.CustomHttpConnection;

/**
 * Class A_Handler manages the Lodgix API
 * 
 * @author Marko Ovuka
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());

	private static final DateFormat apiRequestDF = new SimpleDateFormat("yyyy-MM-dd");
	private static final String LOCALE_LANGUAGE = "en";

	private static final String PRODUCTS_URL = "http://www.lodgix.com/partners/feed/bookingpal/en/contentindex/listings";
	private static final String RESERVATIONS_URL = "http://www.lodgix.com/partners/feed/bookingpal/en/contentindex/reservations";
	private static final String RATES_URL = "http://www.lodgix.com/partners/feed/bookingpal/en/contentindex/rates";

//	private static final String CHANNEL_LIST_VALUE = "HOMEAWAY_US";
	private static final String CHANNEL_LIST_VALUE = "BOOKINGPAL";

	private static final String LODGIX_API_URL = "https://www.lodgix.com/partners/olb/bookingpal/";
	private static final String LODGIX_API_QUOTE_URL = LODGIX_API_URL + "quote";
	private static final String LODGIX_API_BOOK_URL = LODGIX_API_URL + "book";
	private static final String LODGIX_API_CANCEL_BOOK_URL = LODGIX_API_URL + "booking-reservation-operation";

	// API credentials for POST requests
	private static final String OLB_API_USERNAME = "bookingpal";
	private static final String OLB_API_PASSWORD = "Px9287nWg889QZi";
	private static final String LODGIX_OLB_SERVER_AUTH = OLB_API_USERNAME + ":" + OLB_API_PASSWORD;
	
	private static final String HOMEAWAY_TOKEN_CARD_NUMBER = "20c98eb5-b596-4e1a-b74d-a391e3fd2a9";

	private static final int NUMBER_OF_PETS_DEFAULT = 0;
	
	static {
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
	}

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
	 * Gets the connection to the Lodgix server and executes the specified
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
	private static final String getApiConnection(String rq, String apiUrl) throws Throwable {
		String xmlString = "";
//		HttpURLConnection connection = null;
		CustomHttpConnection connection = new CustomHttpConnection();
//		try {
//			URL url = new URL(apiUrl);
//			connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setDoOutput(true);
//			connection.setRequestProperty("Content-Type", "application/xml");
			String encodedBasicAuthCredentials = DatatypeConverter.printBase64Binary(LODGIX_OLB_SERVER_AUTH.getBytes());
			xmlString = connection.createPostRequest(apiUrl, encodedBasicAuthCredentials, "application/xml", rq);
//			connection.setRequestProperty("Authorization", "Basic " + encoding);
//
//			if (rq != null) {
//				connection.setRequestProperty("Accept", "application/xml");
//				connection.connect();
//				byte[] outputBytes = rq.getBytes("UTF-8");
//				OutputStream os = connection.getOutputStream();
//				os.write(outputBytes);
//			}
//
//			if (connection.getResponseCode() != 200) {
//				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
//			}
//			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//			String line;
//			while ((line = br.readLine()) != null) {
//				xmlString += line;
//			}
//		} catch (Throwable x) {
//			throw new RuntimeException(x.getMessage());
//		} finally {
//			if (connection != null) {
//				connection.disconnect();
//			}
//		}
		return xmlString;
	}


	
	private boolean checkIfValueNullOrEmpty(String value) {
		boolean result = false;
		if (value == null || value.isEmpty()) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "Lodgix isAvailable (PartyId: "+this.getAltpartyid()+"), reservation ID:" + reservation.getId();
		LOG.debug(message);
		try {
			Element rootNode = this.getQuoteRootElement(sqlSession, reservation);
			if (rootNode.getChild("quoteResponseDetails") != null && rootNode.getChild("quoteResponseDetails").getChild("orderList") != null
					&& rootNode.getChild("quoteResponseDetails").getChild("orderList").getChild("order") != null) {
				isAvailableResult = true;
			} else{
				String description = "Property not available.";
				if(rootNode.getChild("errorList") != null && rootNode.getChild("errorList").getChild("error") != null && rootNode.getChild("errorList").getChild("error").getChildText("message")!=null){
					description = rootNode.getChild("errorList").getChild("error").getChildText("message");
					LOG.debug("Lodgix property not available: "+description);
				}
//				throw new ServiceException(Error.reservation_api, description);
			}
		} catch (Throwable x) {
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		MonitorService.monitor(message, timestamp);

		return isAvailableResult;
	}
	
	private Element getQuoteRootElement(SqlSession sqlSession, Reservation reservation) throws Throwable{
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {
			throw new ServiceException(Error.product_id, reservation.getProductid());
		}

		StringBuilder requestQuote = new StringBuilder();
		requestQuote.append("<?xml version='1.0' encoding='UTF-8'?>");
		requestQuote.append("<quoteRequest>");
		requestQuote.append("  <quoteRequestDetails>");
		requestQuote.append("    <advertiserAssignedId>" + product.getCode() + "</advertiserAssignedId>");
		requestQuote.append("    <listingExternalId>" + product.getAltid() + "</listingExternalId>");
		requestQuote.append("    <propertyUrl>" + product.getWebaddress() + "</propertyUrl>");
		requestQuote.append("    <listingChannel>" + CHANNEL_LIST_VALUE + "</listingChannel>");
		requestQuote.append("    <reservation>");
		requestQuote.append("      <numberOfAdults>" + reservation.getAdult() + "</numberOfAdults>");
		requestQuote.append("      <numberOfChildren>" + reservation.getChild() + "</numberOfChildren>");
		requestQuote.append("      <numberOfPets>" + NUMBER_OF_PETS_DEFAULT + "</numberOfPets>");
		requestQuote.append("      <reservationDates>");
		requestQuote.append("        <beginDate>" + apiRequestDF.format(reservation.getFromdate()) + "</beginDate>");
		requestQuote.append("        <endDate>" + apiRequestDF.format(reservation.getTodate()) + "</endDate>");
		requestQuote.append("      </reservationDates>");
		requestQuote.append("    </reservation>");
		requestQuote.append("  </quoteRequestDetails>");
		requestQuote.append("</quoteRequest>");

		String responseQuote = getApiConnection(requestQuote.toString(), LODGIX_API_QUOTE_URL);
		LOG.debug("Lodgix qoute response: " + responseQuote); 

		SAXBuilder builder = new SAXBuilder();
		Document document = (Document) builder.build(new StringReader(responseQuote));
//		Document document = builder.build(PartnerService.getInputStreamNoSSL(LODGEX_API_QUOTE_URL, LODGEX_OLB_SERVER_AUTH, requestQuote.toString()));
		Element rootNode = document.getRootElement();
		
		return rootNode;
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Lodgix createReservation()");
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String message = "Lodgix readPrice (PartyId: "+this.getAltpartyid()+") productAltId:" + productAltId;
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
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency, Date version)
			throws Throwable {
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();

		reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
		reservation.setCurrency(currency);
		reservationPrice.setCurrency(currency);

		Element rootNode = this.getQuoteRootElement(sqlSession, reservation);

		if (rootNode.getChild("quoteResponseDetails") == null || rootNode.getChild("quoteResponseDetails").getChild("orderList") == null
				|| rootNode.getChild("quoteResponseDetails").getChild("orderList").getChild("order") == null) {
			String description = "";
			if(rootNode.getChild("errorList") != null && rootNode.getChild("errorList").getChild("error") != null && rootNode.getChild("errorList").getChild("error").getChildText("message")!=null){
				description = rootNode.getChild("errorList").getChild("error").getChildText("message");
			}
			LOG.debug("Lodgix property not available, "+description);
			throw new ServiceException(Error.reservation_api, description);
		}

		Element orderElement = rootNode.getChild("quoteResponseDetails").getChild("orderList").getChild("order");
		String responseCurrency = orderElement.getChildText("currency");
		Double currencyRate = 1.0;
		if (!responseCurrency.equalsIgnoreCase(currency)) {
			currencyRate = WebService.getRate(sqlSession, responseCurrency, currency, new Date());
		}

		Double totalTaxValue = 0.0;
		Double totalPriceValue = 0.0;
		Double depositValue = 0.0;
		
		List<Element> paymentScheduleList = orderElement.getChild("paymentSchedule").getChild("paymentScheduleItemList").getChildren("paymentScheduleItem");
		for (Element paymentScheduleItemElement : paymentScheduleList) {
			Double paymentScheduleItemValue = 0.0;
			try {paymentScheduleItemValue = Double.valueOf(paymentScheduleItemElement.getChildText("amount"));} catch (Exception parseExc) {}
			paymentScheduleItemValue = PaymentHelper.getAmountWithTwoDecimals(paymentScheduleItemValue * currencyRate);
			totalPriceValue += paymentScheduleItemValue;
			//first item is deposit, so on this way we adding deposit
			if(depositValue==0.0){
				depositValue = paymentScheduleItemValue;
			}
			
		}

		List<Element> orderItemList = orderElement.getChild("orderItemList").getChildren("orderItem");
		for (Element orderItemElement : orderItemList) {
			String itemName = orderItemElement.getChildText("name");
			String itemType = orderItemElement.getChildText("feeType");

			Double preTaxAmountValue = 0.0;
			try {preTaxAmountValue = Double.valueOf(orderItemElement.getChildText("preTaxAmount"));} catch (Exception parseExc) {}
			preTaxAmountValue = PaymentHelper.getAmountWithTwoDecimals(preTaxAmountValue * currencyRate);

			Double itemTotalAmount = 0.0;
			try {itemTotalAmount = Double.valueOf(orderItemElement.getChildText("totalAmount"));} catch (Exception parseExc) {}
			itemTotalAmount = PaymentHelper.getAmountWithTwoDecimals(itemTotalAmount * currencyRate);

			//on of items can be discount, but we do not need discount information in quote details
			if (!itemType.equalsIgnoreCase("DISCOUNT")) {
				totalTaxValue += (itemTotalAmount - preTaxAmountValue);

				net.cbtltd.shared.Price itemPriceObject = new net.cbtltd.shared.Price();
				itemPriceObject = new net.cbtltd.shared.Price();
				itemPriceObject.setEntitytype(NameId.Type.Reservation.name());
				itemPriceObject.setEntityid(reservation.getId());

				itemPriceObject.setState(net.cbtltd.shared.Price.CREATED);
				itemPriceObject.setDate(version);
				itemPriceObject.setQuantity(1.0);
				itemPriceObject.setUnit(Unit.EA);
				itemPriceObject.setValue(itemTotalAmount);
				itemPriceObject.setCurrency(reservation.getCurrency());

				if (itemType.equalsIgnoreCase("RENTAL")) {
					itemPriceObject.setType(net.cbtltd.shared.Price.RATE);
					itemPriceObject.setName(net.cbtltd.shared.Price.RATE);
				} else {
					itemPriceObject.setType(net.cbtltd.shared.Price.MANDATORY);
					itemPriceObject.setName(itemName);

					// in this case we create quote element
					QuoteDetail quoteDetail = new QuoteDetail(itemTotalAmount.toString(), reservation.getCurrency(), itemName, "Included in the price", itemName, true);
					quoteDetails.add(quoteDetail);
				}

				reservation.getQuotedetail().add(itemPriceObject);
			}
		}

		if (totalTaxValue > 0) {
			net.cbtltd.shared.Price calculatedTaxPrice = new net.cbtltd.shared.Price();
			calculatedTaxPrice = new net.cbtltd.shared.Price();
			calculatedTaxPrice.setEntitytype(NameId.Type.Reservation.name());
			calculatedTaxPrice.setEntityid(reservation.getId());
			calculatedTaxPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
			calculatedTaxPrice.setName("Taxes");
			calculatedTaxPrice.setState(net.cbtltd.shared.Price.CREATED);
			calculatedTaxPrice.setDate(version);
			calculatedTaxPrice.setQuantity(1.0);
			calculatedTaxPrice.setUnit(Unit.EA);
			calculatedTaxPrice.setValue(totalTaxValue);
			calculatedTaxPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(calculatedTaxPrice);

			QuoteDetail quoteDetail = new QuoteDetail(totalTaxValue.toString(), reservation.getCurrency(), "Taxes", "Included in the price", "Taxes", true);
			quoteDetails.add(quoteDetail);
		}

		reservationPrice.setTotal(totalPriceValue);
		reservationPrice.setPrice(totalPriceValue);
		reservationPrice.setQuoteDetails(quoteDetails);

		reservation.setPrice(totalPriceValue);
		reservation.setQuote(totalPriceValue);
		reservation.setDeposit(depositValue);
		reservation.setExtra(0.0);
		Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
		reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));

		
		LOG.debug("Lodgix compute price finished."); 
		return reservationPrice;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Element> getOrderItemsAndComputePrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency, Date version) throws Throwable {
		reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
		reservation.setCurrency(currency);

		Element rootNode = this.getQuoteRootElement(sqlSession, reservation);

		if (rootNode.getChild("quoteResponseDetails") == null || rootNode.getChild("quoteResponseDetails").getChild("orderList") == null
				|| rootNode.getChild("quoteResponseDetails").getChild("orderList").getChild("order") == null) {
			String description = "";
			if(rootNode.getChild("errorList") != null && rootNode.getChild("errorList").getChild("error") != null && rootNode.getChild("errorList").getChild("error").getChildText("message")!=null){
				description = rootNode.getChild("errorList").getChild("error").getChildText("message");
			}
			throw new ServiceException(Error.reservation_api, description);
		}

		Element orderElement = rootNode.getChild("quoteResponseDetails").getChild("orderList").getChild("order");
		String responseCurrency = orderElement.getChildText("currency");
		Double currencyRate = 1.0;
		if (!responseCurrency.equalsIgnoreCase(currency)) {
			currencyRate = WebService.getRate(sqlSession, responseCurrency, currency, new Date());
		}

//		Double totalValueWithoutTaxes = 0.0;
//		Double totalValueIncludingTaxes = 0.0;
		
		Double totalPrice = 0.0;

		List<Element> orderItemList = orderElement.getChild("orderItemList").getChildren("orderItem");
		List<Element> paymentScheduleList = orderElement.getChild("paymentSchedule").getChild("paymentScheduleItemList").getChildren("paymentScheduleItem");
		for (Element paymentScheduleItemElement : paymentScheduleList) {
			Double paymentScheduleItemValue = 0.0;
			try {paymentScheduleItemValue = Double.valueOf(paymentScheduleItemElement.getChildText("amount"));} catch (Exception parseExc) {}
			paymentScheduleItemValue = PaymentHelper.getAmountWithTwoDecimals(paymentScheduleItemValue * currencyRate);
			totalPrice += paymentScheduleItemValue;
		}
//		for (Element orderItemElement : orderItemList) {
//			String itemName = orderItemElement.getChildText("name");
//			String itemType = orderItemElement.getChildText("feeType");
//
//			Double preTaxAmountValue = 0.0;
//			try {preTaxAmountValue = Double.valueOf(orderItemElement.getChildText("preTaxAmount"));} catch (Exception parseExc) {}
//			
//			preTaxAmountValue = PaymentHelper.getAmountWithTwoDecimals(preTaxAmountValue * currencyRate);
//
//			Double itemTotalAmount = 0.0;
//			try {itemTotalAmount = Double.valueOf(orderItemElement.getChildText("totalAmount"));} catch (Exception parseExc) {}
//			itemTotalAmount = PaymentHelper.getAmountWithTwoDecimals(itemTotalAmount * currencyRate);
//
//			if (itemType.equalsIgnoreCase("DISCOUNT")) {
//				LOG.error("########################### Lodgex property have Discount!!!!");
//			} else {
//				totalValueWithoutTaxes += preTaxAmountValue;
//				totalValueIncludingTaxes += itemTotalAmount;
//
//				net.cbtltd.shared.Price itemPriceObject = new net.cbtltd.shared.Price();
//				itemPriceObject = new net.cbtltd.shared.Price();
//				itemPriceObject.setEntitytype(NameId.Type.Reservation.name());
//				itemPriceObject.setEntityid(reservation.getId());
//
//				itemPriceObject.setState(net.cbtltd.shared.Price.CREATED);
//				itemPriceObject.setDate(version);
//				itemPriceObject.setQuantity(1.0);
//				itemPriceObject.setUnit(Unit.EA);
//				itemPriceObject.setValue(itemTotalAmount);
//				itemPriceObject.setCurrency(reservation.getCurrency());
//
//				if (itemType.equalsIgnoreCase("RENTAL")) {
//					itemPriceObject.setType(net.cbtltd.shared.Price.RATE);
//					itemPriceObject.setName(net.cbtltd.shared.Price.RATE);
//				} else {
//					itemPriceObject.setType(net.cbtltd.shared.Price.MANDATORY);
//					itemPriceObject.setName(itemName);
//				}
//
//				reservation.getQuotedetail().add(itemPriceObject);
//			}
//		}
//
//		if (totalValueIncludingTaxes > totalValueWithoutTaxes) {
//			Double taxValue = totalValueIncludingTaxes - totalValueWithoutTaxes;
//			net.cbtltd.shared.Price calculatedTaxPrice = new net.cbtltd.shared.Price();
//			calculatedTaxPrice = new net.cbtltd.shared.Price();
//			calculatedTaxPrice.setEntitytype(NameId.Type.Reservation.name());
//			calculatedTaxPrice.setEntityid(reservation.getId());
//			calculatedTaxPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
//			calculatedTaxPrice.setName("Taxes");
//			calculatedTaxPrice.setState(net.cbtltd.shared.Price.CREATED);
//			calculatedTaxPrice.setDate(version);
//			calculatedTaxPrice.setQuantity(1.0);
//			calculatedTaxPrice.setUnit(Unit.EA);
//			calculatedTaxPrice.setValue(taxValue);
//			calculatedTaxPrice.setCurrency(reservation.getCurrency());
//			reservation.getQuotedetail().add(calculatedTaxPrice);
//		}

		reservation.setPrice(totalPrice);
		reservation.setQuote(totalPrice);

		return orderItemList;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		Date timestamp = new Date();
		String message = "Lodgix createReservationAndPayment (PartyId: "+this.getAltpartyid()+"), resrvationId:" + reservation.getId();
		LOG.debug(message);

		Map<String, String> result = new HashMap<String, String>();
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) { throw new ServiceException(Error.reservation_agentid); }
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) {reservation.setCustomerid(Party.NO_ACTOR);}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}
	
			//not neccessary any more to check price. This is done in AbstractReservation
//			double oldQuote = reservation.getQuote();
			List<Element> orderItemList = this.getOrderItemsAndComputePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency(), timestamp);

//			if (oldQuote != reservation.getQuote()) {
//				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
//			}
			
			String userFirstLastName = "";
			if (!customer.noFirstName()) {
				userFirstLastName += customer.getFirstName();
			}
			if (!customer.noFamilyName()) {
				if(!this.checkIfValueNullOrEmpty(userFirstLastName)){userFirstLastName+=" ";}
				userFirstLastName += customer.getFamilyName();
			}
					
			
			StringBuilder requestQuote = new StringBuilder();
			requestQuote.append("<?xml version='1.0' encoding='UTF-8'?>");
			requestQuote.append("<bookingRequest>");
			requestQuote.append("  <bookingRequestDetails>");
			requestQuote.append("    <advertiserAssignedId>" + product.getCode() + "</advertiserAssignedId>");
			requestQuote.append("    <listingExternalId>" + product.getAltid() + "</listingExternalId>");
			requestQuote.append("    <propertyUrl>" + product.getWebaddress() + "</propertyUrl>");
			requestQuote.append("    <listingChannel>" + CHANNEL_LIST_VALUE + "</listingChannel>");
			if (!checkIfValueNullOrEmpty(reservation.getNotes())) {
				requestQuote.append("<message>" + reservation.getNotes() + "</message>");
			}
			requestQuote.append("    <inquirer>");
			requestQuote.append("        <name>" + userFirstLastName + "</name>");
			
			if (!customer.noEmailaddress()) {
				requestQuote.append("    <emailAddress>" + customer.getEmailaddress() + "</emailAddress>");
			}
			if (!checkIfValueNullOrEmpty(customer.getDayphone())) {
				requestQuote.append("    <phoneNumber>" + customer.getDayphone() + "</phoneNumber>");
			}
			requestQuote.append("        <address rel='BILLING'>");
			requestQuote.append("          <addressLine1>" + customer.getLocalAddress() + "</addressLine1>");
			requestQuote.append("          <addressLine3>" + customer.getCity() + "</addressLine3>");

			//TODO check state for Canada
			if (!checkIfValueNullOrEmpty(customer.getRegion())) {
				requestQuote.append("      <addressLine4>" + customer.getRegion() + "</addressLine4>");
			}

			if (!customer.noCountry()) {
				requestQuote.append("        <country>" + customer.getCountry() + "</country>");
			}
			if (!checkIfValueNullOrEmpty(customer.getPostalcode())) {
				requestQuote.append("        <postalCode>" + customer.getPostalcode() + "</postalCode>");
			}
			
			requestQuote.append("        </address>");
			requestQuote.append("    </inquirer>");
			requestQuote.append("    <commission/>");
			requestQuote.append("    <reservation>");
			requestQuote.append("      <numberOfAdults>" + reservation.getAdult() + "</numberOfAdults>");
			requestQuote.append("      <numberOfChildren>" + reservation.getChild() + "</numberOfChildren>");
			requestQuote.append("      <numberOfPets>" + NUMBER_OF_PETS_DEFAULT + "</numberOfPets>");
			requestQuote.append("      <reservationDates>");
			requestQuote.append("        <beginDate>" + apiRequestDF.format(reservation.getFromdate()) + "</beginDate>");
			requestQuote.append("        <endDate>" + apiRequestDF.format(reservation.getTodate()) + "</endDate>");
			requestQuote.append("      </reservationDates>");
			requestQuote.append("    </reservation>");
			if(orderItemList!=null && orderItemList.size()>0){
				requestQuote.append("<orderItemList>");
				for(Element orderItemElement : orderItemList){
					requestQuote.append("<orderItem>");
					requestQuote.append("  <feeType>" + orderItemElement.getChildText("feeType") + "</feeType>");
					requestQuote.append("  <name>" + orderItemElement.getChildText("name") + "</name>");
					requestQuote.append("  <preTaxAmount currency='" +orderItemElement.getChild("preTaxAmount").getAttributeValue("currency")+ "'>" + orderItemElement.getChildText("preTaxAmount") + "</preTaxAmount>");
					requestQuote.append("  <totalAmount currency='" +orderItemElement.getChild("totalAmount").getAttributeValue("currency")+ "'>" + orderItemElement.getChildText("totalAmount") + "</totalAmount>");
					requestQuote.append("</orderItem>");
				}
				requestQuote.append("</orderItemList>");
			}
			
			requestQuote.append("    <paymentCard>");
			requestQuote.append("      <paymentFormType>CARD</paymentFormType>");
			requestQuote.append("      <billingAddress rel='BILLING'>");
			requestQuote.append("        <addressLine1>" + customer.getLocalAddress() + "</addressLine1>");
			requestQuote.append("        <addressLine3>" + customer.getCity()+ "</addressLine3>");
			
			//TODO check state for Canada
			if (!checkIfValueNullOrEmpty(customer.getRegion())) {
				requestQuote.append("      <addressLine4>" + customer.getRegion() + "</addressLine4>");
			}
			if (!customer.noCountry()) {
				requestQuote.append("      <country>" + customer.getCountry() + "</country>");
			}
			if (!checkIfValueNullOrEmpty(customer.getPostalcode())) {
				requestQuote.append("      <postalCode>" + customer.getPostalcode() + "</postalCode>");
			}
			
			//card month need to be in MM format
			String cardMonth = creditCard.getMonth();
			if(cardMonth.length()==1){
				cardMonth = "0"+cardMonth;
			}
			requestQuote.append("      </billingAddress>");
			requestQuote.append("      <cvv>" + creditCard.getSecurityCode() + "</cvv>");
			requestQuote.append("      <expiration>" + cardMonth + "/" + creditCard.getYear() + "</expiration>");
			requestQuote.append("      <number>" + creditCard.getNumber() + "</number>");
			requestQuote.append("      <numberToken>" + HOMEAWAY_TOKEN_CARD_NUMBER + "</numberToken>");
			requestQuote.append("      <nameOnCard>" + userFirstLastName + "</nameOnCard>");
			requestQuote.append("      <paymentCardDescriptor>"); 
			requestQuote.append("        <paymentFormType>CARD</paymentFormType>");
			requestQuote.append("        <cardCode>" + this.getCreditCardLodgixTypeId(creditCard.getType()) + "</cardCode>");
			//requestQuote.append("        <cardType>CREDIT</cardType>");
			requestQuote.append("      </paymentCardDescriptor>"); 
			requestQuote.append("    </paymentCard>");
			requestQuote.append("    <travelerSource>" + CHANNEL_LIST_VALUE + "</travelerSource>");
			requestQuote.append("  </bookingRequestDetails>");
			requestQuote.append("</bookingRequest>");


			String request = requestQuote.toString();
			LOG.debug("Book Request: " + request);
			String responseQuote = getApiConnection(request, LODGIX_API_BOOK_URL);

			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseQuote));
			Element rootNode = document.getRootElement();

			if (rootNode.getChild("bookingResponseDetails") != null) {
				Element reservationLodgix = rootNode.getChild("bookingResponseDetails");
				reservation.setAltid(reservationLodgix.getChildText("externalId"));
//				reservation.setConfirmationId(reservationLodgix.getChildText("operationKey"));
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
				LOG.debug("Lodgix reservation success. Reservation AltID: "+reservationLodgix.getChildText("externalId")); 
			} else {
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				String errorDescription = "";
				if (rootNode.getChild("errorList") != null && rootNode.getChild("errorList").getChildren("error") != null) {
					List<Element> errorList= rootNode.getChild("errorList").getChildren("error");
					for(Element errorElement : errorList){
						errorDescription += errorElement.getChildText("message") + "; ";
					}
				}
				LOG.debug("Lodgix reservation not success. Error: " + errorDescription); 
				result.put(GatewayHandler.ERROR_MSG, errorDescription);
				return result;
			}

		} catch (ServiceException e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			throw new ServiceException(e.getError(), e.getMessage());
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);

		return result;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Lodgix confirmReservation()");
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Lodgix readReservation()");
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Lodgix updateReservation()");
	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation Lodgix (PartyId: "+this.getAltpartyid()+"), reservationId=" + reservation.getAltid();
		LOG.debug(message);
		try {		
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			
			StringBuilder requestQuote = new StringBuilder();
			requestQuote.append("<?xml version='1.0' encoding='UTF-8'?>");
			requestQuote.append("<bookingReservationOperationRequest>");
			requestQuote.append("  <reservationOperation>CANCEL</reservationOperation>");
//			requestQuote.append("  <operationKey>" + reservation.getConfirmationId() + "</operationKey>");
			requestQuote.append("  <advertiserAssignedId>" + product.getCode() + "</advertiserAssignedId>");
			requestQuote.append("  <externalId>" + reservation.getAltid() + "</externalId>");
			requestQuote.append("</bookingReservationOperationRequest>");
			
			
			String request = requestQuote.toString();
			String responseCancelBooking = getApiConnection(request, LODGIX_API_CANCEL_BOOK_URL);

			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(responseCancelBooking));
			Element rootNode = document.getRootElement();
			
			if(rootNode.getChild("status")==null || !rootNode.getChildText("status").equalsIgnoreCase("SUCCESS")){
				String cancelerrormessage = "Error in cancelling reservations";
				if(rootNode.getChild("errorList") != null && rootNode.getChild("errorList").getChild("error") != null
						&& rootNode.getChild("errorList").getChild("error").getChild("message") != null){
					cancelerrormessage += ": " + rootNode.getChild("errorList").getChild("error").getChildText("message");
				}
				throw new ServiceException(Error.reservation_api, cancelerrormessage);
			}
		}
		catch (Throwable x) {
			LOG.error("Lodgix cancel reservation - " + x.getMessage());
			reservation.setMessage(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.service_absent, "Lodgix readAlerts()");
	}

	@Override
	public void readPrices() {
		Date version = new Date();
		String message = "readPrices Lodgix (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);

		//TODO temp finding last cronjob run
		//temp will be 10 days.
		Calendar cal = Calendar.getInstance();
		cal.setTime(version);
		cal.add(Calendar.DATE, -500);
		Date lastCronJobRun = cal.getTime();
				
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
			String advertiserIdForThisPM = party.getAltid();
			ListingContentIndex content = CommonUtils.unmarshall(new URL(RATES_URL), ListingContentIndex.class);
				
			for (AdvertiserListingIndex.Advertiser advertiser : content.getAdvertisers().getAdvertiser()) {
				String advertiserIdCurrent = advertiser.getAssignedId();
				if(!advertiserIdForThisPM.equalsIgnoreCase(advertiserIdCurrent)){
					continue;
				}
				for (ListingContentIndexEntry contentIndex : advertiser.getListingContentIndexEntry()) {
					//checking last update
					Date apiPropertyLastUpdate = getDateTime(contentIndex.getLastUpdatedDate());
					if(apiPropertyLastUpdate.before(lastCronJobRun)){
						continue;
					}
					
					if (!checkIfValueNullOrEmpty(contentIndex.getUnitRatesUrl())) {
						RatePeriods ratePeriodsList = CommonUtils.unmarshall(new URL(contentIndex.getUnitRatesUrl()), RatePeriods.class);
						String altId = contentIndex.getListingExternalId();
//						LOG.debug("Current property altId: " + altId);
						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
						if (product == null) {
							continue;
						}

						ArrayList<Price> pricesFromApi = new ArrayList<Price>();
						ArrayList<Yield> discountYieldFromApi = new ArrayList<Yield>();
						
						for (RatePeriod ratePeriodCurrent : ratePeriodsList.getRatePeriod()) {
							Date fromDate = getDateTime(ratePeriodCurrent.getDateRange().getBeginDate());
							Date toDate = getDateTime(ratePeriodCurrent.getDateRange().getEndDate());
							Integer minimumStay = ratePeriodCurrent.getMinimumStay();
							if(minimumStay==null){
								//if minimum stay missing than = 1
								minimumStay = 1;
							}
							
							List<Rate> ratesList = ratePeriodCurrent.getRates().getRate();
							String currencyDaily = product.getCurrency();
							String currencyWeekend = product.getCurrency();
							String currencyWeekly = product.getCurrency();
							String currencyMonthly = product.getCurrency();
							Double dailyRateValue = null;
							Double weekendRateValue = null;
							Double weeklyRateValue = null;
							Double monthlyRateValue = null;

							//TODO This need to be tested on UAT
							for (Rate rateCurrent : ratesList) {
								// in response some prices have value=0 so that rates we will not use
								// we using nightly rate, weekend rate (adjusted price) 
								// and also remember weekly rate (if nightly do not exist - this is noticed for some properties)
								if (rateCurrent.getAmount().getValue() != null && rateCurrent.getAmount().getValue().doubleValue() > 0) {
									if (rateCurrent.getRateType().equals(RateType.NIGHTLY_WEEKDAY)) {
										dailyRateValue = rateCurrent.getAmount().getValue().doubleValue();
										currencyDaily = rateCurrent.getAmount().getCurrency();
									} else if (rateCurrent.getRateType().equals(RateType.NIGHTLY_WEEKEND)) {
										weekendRateValue = rateCurrent.getAmount().getValue().doubleValue();
										currencyWeekend = rateCurrent.getAmount().getCurrency();
									} else if (rateCurrent.getRateType().equals(RateType.WEEKLY)) {
										weeklyRateValue = rateCurrent.getAmount().getValue().doubleValue();
										currencyWeekly = rateCurrent.getAmount().getCurrency();
									} else if (rateCurrent.getRateType().equals(RateType.MONTHLY)) {
										monthlyRateValue = rateCurrent.getAmount().getValue().doubleValue();
										currencyMonthly = rateCurrent.getAmount().getCurrency();
									} else {
										System.out.println("######################################### Rate type: " + rateCurrent.getRateType().toString());
									}
								}
							}
							
							if (dailyRateValue != null && minimumStay != null) {
								Price price = new Price();
								price.setEntityid(product.getId());
								price.setEntitytype(NameId.Type.Product.name());
								price.setPartyid(getAltpartyid());
								price.setName(Price.RACK_RATE);
								price.setType(NameId.Type.Reservation.name());
								price.setQuantity(1.0);
								price.setCurrency(currencyDaily);
								price.setUnit(Unit.DAY);
								price.setDate(fromDate);
								price.setTodate(toDate);

//								Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
//								if (exists == null) {
//									sqlSession.getMapper(PriceMapper.class).create(price);
//								} else {
//									price = exists;
//								}

//								price.setState(Price.CREATED);
								// TODO check this with them, they have this but we do not get this information
								price.setRule(Price.Rule.AnyCheckIn.name());
								price.setValue(dailyRateValue);
								price.setCost(dailyRateValue);
								price.setMinStay(minimumStay);
								price.setMinimum(dailyRateValue * minimumStay);
								price.setAvailable(1);
//								price.setVersion(version);
//								sqlSession.getMapper(PriceMapper.class).update(price);
//								sqlSession.getMapper(PriceMapper.class).cancelversion(price);
								
								pricesFromApi.add(price);
								
								if(weekendRateValue!=null && dailyRateValue!=null && (weekendRateValue-dailyRateValue != 0.0)){
									if(!currencyWeekend.equalsIgnoreCase(currencyDaily)){
										Double currencyRate = WebService.getRate(sqlSession, currencyWeekend, currencyDaily, new Date());
										weekendRateValue = PaymentHelper.getAmountWithTwoDecimals(weekendRateValue * currencyRate);
									}
									Yield weekendRateYield = YieldService.createWeekendRate(product.getId(), dailyRateValue, weekendRateValue, fromDate, toDate);
									if(weekendRateYield!=null){
										weekendRateYield.setParam(Yield.DAYS_OF_WEEKEND_FRI_SAT);
										discountYieldFromApi.add(weekendRateYield);
									}
								}
								
								if(weeklyRateValue!=null){
									if(!currencyWeekly.equalsIgnoreCase(currencyDaily)){
										Double currencyRate = WebService.getRate(sqlSession, currencyWeekly, currencyDaily, new Date());
										weeklyRateValue = PaymentHelper.getAmountWithTwoDecimals(weeklyRateValue * currencyRate);
									}
									Yield weeklyDiscount = YieldService.createLengthOfStayDiscount(product.getId(), dailyRateValue, weeklyRateValue, 7, fromDate, toDate);
									if(weeklyDiscount!=null){
										discountYieldFromApi.add(weeklyDiscount);
									}
								}
								
								if(monthlyRateValue!=null && dailyRateValue!=null){
									if(!currencyMonthly.equalsIgnoreCase(currencyDaily)){
										Double currencyRate = WebService.getRate(sqlSession, currencyMonthly, currencyDaily, new Date());
										monthlyRateValue = PaymentHelper.getAmountWithTwoDecimals(monthlyRateValue * currencyRate);
									}
									Yield monthlyDiscount = YieldService.createLengthOfStayDiscount(product.getId(), dailyRateValue, monthlyRateValue, 30, fromDate, toDate);
									if(monthlyDiscount!=null){
										discountYieldFromApi.add(monthlyDiscount);
									}
								}
								
								//this is case when we have adjustment value also
								//we adding this rate price only if regular weekly price exist
//								if (rateAmountWeekendValue != null) {
//									Double extraAdjustmentPrice = rateAmountWeekendValue - dailyRateValue;
//									Adjustment adjustmentPrice = new Adjustment();
//									adjustmentPrice.setCurrency(currencyWeekend);
//									adjustmentPrice.setPartyID(getAltpartyid());
//									adjustmentPrice.setProductID(product.getId());
//									adjustmentPrice.setFromDate(fromDate);
//									adjustmentPrice.setToDate(toDate);
//									adjustmentPrice.setMaxStay(999);
//									adjustmentPrice.setMinStay(minimumStay);
//									adjustmentPrice.setExtra(extraAdjustmentPrice);
//									adjustmentPrice.setServicedays(Adjustment.WEEKEND);
////									adjustmentPrice.setState(Adjustment.Created);
//									
//									Adjustment existsAdjustment = sqlSession.getMapper(AdjustmentMapper.class).exists(adjustmentPrice);
//									if (existsAdjustment == null) {
//										sqlSession.getMapper(AdjustmentMapper.class).create(adjustmentPrice);
//									}
//									else {
//										adjustmentPrice = existsAdjustment;
//									}
//									
//									adjustmentPrice.setState(Adjustment.Created);
//									adjustmentPrice.setVersion(version);
//									
//									sqlSession.getMapper(AdjustmentMapper.class).update(adjustmentPrice);
//									sqlSession.getMapper(AdjustmentMapper.class).cancelversion(adjustmentPrice);
//								}
							}
						}

						PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
						PartnerService.updateProductRateYields(sqlSession, product, discountYieldFromApi, version);
						
						sqlSession.commit();
					}
				}
			}

		} catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);

	}

	@Override
	public void readProducts() {
		String message = "readProducts Lodgix (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);
		Date version = new Date();

		StringBuilder sbNotKnowLocation = new StringBuilder();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			// FeedParser<AdvertiserListingIndex.Advertiser, Listing> parser = FeedParserFactory.getFactoryInstance().createContentIndexedFeedParser(ALL_PRODUCTS_URL);

			Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
			String advertiserIdForThisPM = party.getAltid();
			ListingContentIndex content = CommonUtils.unmarshall(new URL(PRODUCTS_URL), ListingContentIndex.class);
			for (AdvertiserListingIndex.Advertiser advertiser : content.getAdvertisers().getAdvertiser()) {
				String advertiserIdCurrent = advertiser.getAssignedId();
				if(!advertiserIdForThisPM.equalsIgnoreCase(advertiserIdCurrent)){
					continue;
				}
				for (ListingContentIndexEntry contentIndex : advertiser.getListingContentIndexEntry()) {

					Listing propertyListing = CommonUtils.unmarshall(new URL(contentIndex.getListingUrl()), Listing.class);

					String altId = propertyListing.getExternalId();
					LOG.debug("Current AltId=" + altId);

					Address propertyAddress = propertyListing.getLocation().getAddress();
					String countryISO = propertyAddress.getCountry().value();
					String state = propertyAddress.getStateOrProvince();
					String city = propertyAddress.getCity();
					Double latitude = propertyListing.getLocation().getGeoCode().getLatLng().getLatitude().doubleValue();
					Double longitude = propertyListing.getLocation().getGeoCode().getLatLng().getLongitude().doubleValue();

					Location location = null;
					if (latitude != null && longitude != null) {
						location = PartnerService.getLocation(sqlSession, city, state, countryISO, latitude, longitude);
					} else {
						location = PartnerService.getLocation(sqlSession, city, state, countryISO);
					}

					if (location == null) {
						sbNotKnowLocation.append("\n").append("Lodgix property: " + altId + " country: " + countryISO + " city: " + city);
					}
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, true);
					if (product == null) {
						continue;
					}

					ArrayList<String> attributes = new ArrayList<String>();

					if (propertyListing.getUnits().getUnit().size() > 0) {
						UnitWSD propertyUnit = propertyListing.getUnits().getUnit().get(0);
						
						product.setPerson(propertyUnit.getMaxSleep());
						product.setCurrency(propertyUnit.getUnitMonetaryInformation().getCurrency());

						attributes.add(propertyUnit.getPropertyType().getCode());
//						addType(attributes, propertyUnit.getPropertyType().name());
						
						int numberOfBathrooms = 0;
						if(propertyUnit.getBathrooms().getBathroom()!=null){
							numberOfBathrooms = propertyUnit.getBathrooms().getBathroom().size();
							for(Bathroom currentBathroom : propertyUnit.getBathrooms().getBathroom()){
								attributes.add(currentBathroom.getRoomSubType().getCode());
							}
						}
						product.setBathroom(numberOfBathrooms);
						
						int numberOfBedrooms = 0;
						if(propertyUnit.getBedrooms().getBedroom()!=null){
							numberOfBedrooms = propertyUnit.getBedrooms().getBedroom().size();
							for(Bedroom currentBedroom : propertyUnit.getBedrooms().getBedroom()){
								attributes.add(currentBedroom.getRoomSubType().getCode());
							}
						}
						product.setRoom(numberOfBedrooms);
						
						
						//this are unit features
						if (propertyUnit.getFeatureValues() != null && propertyUnit.getFeatureValues().getFeatureValue() != null) {
							for (UnitFeatureValue feature : propertyUnit.getFeatureValues().getFeatureValue()) {
								attributes.add(feature.getUnitFeatureName().getCode());
							}
						}
					}

					String propertyName = "";
					for (net.cbtltd.rest.homeaway.feedparser.domain.Text propertyNameText : propertyListing.getAdContent().getPropertyName().getTexts().getText()) {
						if (propertyNameText.getLocale().equalsIgnoreCase(LOCALE_LANGUAGE)) {
							propertyName = propertyNameText.getTextValue();
							break;
						}
					}

					if (propertyName.equalsIgnoreCase("")) {
						System.out.println("WARNING: There is no property name on english.");
						for (net.cbtltd.rest.homeaway.feedparser.domain.Text propertyHedlineText : propertyListing.getAdContent().getHeadline().getTexts().getText()) {
							if (propertyHedlineText.getLocale().equalsIgnoreCase(LOCALE_LANGUAGE)) {
								propertyName = propertyHedlineText.getTextValue();
								break;
							}
						}
					}
					product.setName(propertyName);

					product.setAltSupplierId(getAltpartyid());
					product.setSupplierid(getAltpartyid());

					product.setCommission(getCommission());
					product.setDiscount(getDiscount());
					product.setRank(0.0);
					product.setAltitude(0.0);
					product.setLatitude(latitude);
					product.setLongitude(longitude);

					product.setWebaddress(getWebaddress());
					product.setCommission(getCommission());
					product.setDiscount(getDiscount());
					product.setRating(5);

					product.setChild(0);
					product.setInfant(0);
					product.setToilet(0);
					product.setQuantity(1);
					product.setUnit(Unit.DAY);
					product.setSecuritydeposit(Product.DEFAULT_SECUIRTY_DEPOSIT);
					product.setCleaningfee(Product.DEFAULT_CLEANING_FEE);

					// this 2 fields we need for creating getting quotes
					//web address field is not used, so we can use this field too
					product.setCode(advertiserIdCurrent);
					product.setWebaddress(contentIndex.getListingUrl());

					product.setVersion(version);

					StringBuilder address = new StringBuilder();
					if (!checkIfValueNullOrEmpty(propertyAddress.getAddressLine1())) {address.append(propertyAddress.getAddressLine1()).append("\n");}
					if (!checkIfValueNullOrEmpty(propertyAddress.getAddressLine2())) {address.append(propertyAddress.getAddressLine2()).append("\n");}
					if (!checkIfValueNullOrEmpty(city)) {address.append(city).append("\n");}
					if (!checkIfValueNullOrEmpty(state)) {address.append(state).append("\n");}
					if (!checkIfValueNullOrEmpty(countryISO)) {address.append(countryISO).append("\n");}

					product.setPhysicaladdress(address.toString());

					// TODO check this setting state
					if (location != null && propertyListing.isActive()) {
						product.setLocationid(location.getId());
						product.setState(Product.CREATED);
					} else {
						product.setState(Product.SUSPENDED);
					}

					sqlSession.getMapper(ProductMapper.class).update(product);

					//this are property features
					if (propertyListing.getFeatureValues() != null && propertyListing.getFeatureValues().getFeatureValue() != null) {
						for (ListingFeatureValue feature : propertyListing.getFeatureValues().getFeatureValue()) {
							attributes.add(feature.getListingFeatureName().getCode());
						}
					}
					

					String descriptionProperty = "";
					for (net.cbtltd.rest.homeaway.feedparser.domain.Text descriptionText : propertyListing.getAdContent().getDescription().getTexts().getText()) {
						if (descriptionText.getLocale().equalsIgnoreCase(LOCALE_LANGUAGE)) {
							descriptionProperty = descriptionText.getTextValue();
							break;
						}
					}
					StringBuilder description = new StringBuilder();
					description.append(descriptionProperty);
					product.setPublicText(new Text(product.getPublicId(), product.getName(), Text.Type.HTML, new Date(), description.toString(),Language.EN));

					//removing duplicate values from attributes
					HashSet<String> attributeHashSet = new HashSet<String>();
					attributeHashSet.addAll(attributes);
					attributes.clear();
					attributes.addAll(attributeHashSet); 
					
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
			}

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);

	}

	@Override
	public void readImages() {
		Date timestamp = new Date();
		String message = "readImages Lodgix (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);

		final SqlSession sqlSession = RazorServer.openSession();
		try {
			Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
			String advertiserIdForThisPM = party.getAltid();
								
			ListingContentIndex content = CommonUtils.unmarshall(new URL(PRODUCTS_URL), ListingContentIndex.class);
			for (AdvertiserListingIndex.Advertiser advertiser : content.getAdvertisers().getAdvertiser()) {
				String advertiserIdCurrent = advertiser.getAssignedId();
				if(!advertiserIdForThisPM.equalsIgnoreCase(advertiserIdCurrent)){
					continue;
				}
				for (ListingContentIndexEntry contentIndex : advertiser.getListingContentIndexEntry()) {
					if (!checkIfValueNullOrEmpty(contentIndex.getListingUrl())) {
						Listing propertyListing = CommonUtils.unmarshall(new URL(contentIndex.getListingUrl()), Listing.class);
						String altId = propertyListing.getExternalId();

						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
						if (product == null) {
							continue;
						}

						if (propertyListing.getImages() != null && propertyListing.getImages().getImage() != null) {
							ArrayList<NameId> images = new ArrayList<NameId>();
							List<Image> imagesList = propertyListing.getImages().getImage();
							for (Image currentImage : imagesList) {
								images.add(new NameId(currentImage.getExternalId(), currentImage.getUri()));
							}
							UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
						}

						sqlSession.commit();
					}
				}
			}

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, timestamp);

	}

	@Override
	public void readSchedule() {
		Date version = new Date();
		String message = "readSchedule Lodgix (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);

		//TODO temp finding last cronjob run
		//temp will be 5 days.
		Calendar cal = Calendar.getInstance();
		cal.setTime(version);
		cal.add(Calendar.DATE, -555);
		Date lastCronJobRun = cal.getTime();
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			
			Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
			String advertiserIdForThisPM = party.getAltid();
				
			ListingContentIndex content = CommonUtils.unmarshall(new URL(RESERVATIONS_URL), ListingContentIndex.class);

			for (AdvertiserListingIndex.Advertiser advertiser : content.getAdvertisers().getAdvertiser()) {
				String advertiserIdCurrent = advertiser.getAssignedId();
				if(!advertiserIdForThisPM.equalsIgnoreCase(advertiserIdCurrent)){
					continue;
				}
			
				for (ListingContentIndexEntry contentIndex : advertiser.getListingContentIndexEntry()) {
					//checking last update
					Date apiPropertyLastUpdate = getDateTime(contentIndex.getLastUpdatedDate());
					if(apiPropertyLastUpdate.before(lastCronJobRun)){
						continue;
					}
					
					if (!checkIfValueNullOrEmpty(contentIndex.getUnitReservationsUrl())) {
						ListingReservations reservationList = CommonUtils.unmarshall(new URL(contentIndex.getUnitReservationsUrl()), ListingReservations.class);
						String altId = reservationList.getListingExternalId();

						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
						if (product == null) {
							continue;
						}

						ArrayList<Reservation> reservationsFromApi = new ArrayList<Reservation>();
						for (net.cbtltd.rest.homeaway.feedparser.domain.Reservation reservationCurrent : reservationList.getReservations().getReservation()) {
							XMLGregorianCalendar beginDate = reservationCurrent.getReservationDates().getBeginDate();
							XMLGregorianCalendar endDate = reservationCurrent.getReservationDates().getEndDate();

							Date fromDate = getDateTime(beginDate);
							Date toDate = getDateTime(endDate);
							
							Reservation reservation = new Reservation();
							reservation.setProductid(product.getId());
							reservation.setFromdate(fromDate);
							reservation.setTodate(toDate);
							
							reservationsFromApi.add(reservation);

//							PartnerService.createSchedule(sqlSession, product, fromDate, toDate, version);
						}

//						PartnerService.cancelSchedule(sqlSession, product, version);
						
						PartnerService.updateProductSchedules(sqlSession, product, reservationsFromApi, version);
						sqlSession.commit();
					}
				}
			}
		} catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
	}

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Lodgix readSpecials()");
	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Lodgix readDescriptions()");
	}

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Lodgix readAdditionCosts()");
	}

	
	private String getCreditCardLodgixTypeId(CreditCardType systemCCtype) {
		String ccTypeId = "";
		if (systemCCtype == CreditCardType.VISA) {
			ccTypeId = "VISA";
		} else if (systemCCtype == CreditCardType.MASTER_CARD) {
			ccTypeId = "MASTERCARD";
		} else if (systemCCtype == CreditCardType.AMERICAN_EXPRESS) {
			ccTypeId = "AMEX";
		} else if (systemCCtype == CreditCardType.DINES_CLUB) {
			ccTypeId = "DINERS";
		} else if (systemCCtype == CreditCardType.DISCOVER) {
			ccTypeId = "DISCOVER";
		} else if (systemCCtype == CreditCardType.JCB) {
			ccTypeId = "JCB";
		}
		return ccTypeId;
	}

	/**
	 * Gets the date time.
	 *
	 * @param xgc
	 *            the xgc
	 * @return the date time
	 */
	protected static Date getDateTime(XMLGregorianCalendar xgc) {
		if (xgc == null) {
			return null;
		} else {
			return xgc.toGregorianCalendar().getTime();
		}
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Lodgix inquireReservation()");
	}

}