/**
o * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.rest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.json.SharedService;
import net.cbtltd.json.minstay.WeeklyMinstay;
import net.cbtltd.json.price.WeeklyPrice;
import net.cbtltd.json.price.WeeklyPricesHolder;
import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.reservation.ReservationBuilder;
import net.cbtltd.rest.reservation.ReservationBuilderConfiguration;
import net.cbtltd.rest.response.AvailabilityRange;
import net.cbtltd.rest.response.AvailabilityResponse;
import net.cbtltd.rest.response.CalendarElement;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.rest.response.CancelReservationResponse;
import net.cbtltd.rest.response.CancellationItem;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.rest.response.QuoteResponse;
import net.cbtltd.rest.response.ReservationInfo;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.rest.response.SearchResponse;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.LicenseService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.api.PropertyMinStayMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.ReservationExtMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.SpecialMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.server.util.AvailabilityRangeUtil;
import net.cbtltd.server.util.CommissionCalculationUtil;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PendingTransactionStatus;
import net.cbtltd.server.util.PriceUtil;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.KeyValue;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.MinStay;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.ReservationExt;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasAlert;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.minstay.MinstayWeek;
import net.cbtltd.shared.price.PriceRange;
import net.cbtltd.shared.price.PriceWeek;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.ibm.icu.util.Calendar;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;
import com.mybookingpal.utils.BPThreadLocal;


/** 
 * The Class ReservationRest implements REST service for reservations. 
 * 
 * @see http://cxf.apache.org/docs/jax-rs-basics.html
 */

public abstract class AbstractReservation {

	protected static final Logger LOG = Logger.getLogger(AbstractReservation.class.getName());
	private static String[] livePricingIds = PartnerService.getPartnerlivePricingIds();
 	private static final double INQUIRY_PAYMENT_AMOUNT = 1.25;
    
	/**
	 * Check access and throw exception if not allowed.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the POS code for access.
	 * @param productid the product ID.
	 * @param wait the minimum wait in milliseconds between calls with the POS code
	 * @return the product if successful.
	 * @throws Throwable the exception
	 */
	protected static final Product getProduct (
			SqlSession sqlSession,
			String pos,
			String productid,
			Integer wait
		) throws Throwable {

		String partyid = Constants.decryptPos(pos);

		Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
		if (product == null) {throw new ServiceException(Error.product_id, productid);}
		if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid);}
		if (product.notState(Product.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}

		LicenseService.checkAccess(sqlSession, product.getSupplierid(), partyid, productid, License.Type.JSON_XML, wait);
		LicenseService.setAccess(partyid, System.currentTimeMillis());

		return product;
	}
	
	private static final Party getParty (
			SqlSession sqlSession,
			String emailaddress,
			String familyname,
			String firstname
			) throws Throwable {
		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.party_emailaddress, emailaddress);}
		Party customer =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
		if (customer == null) {
			if (familyname == null || familyname.isEmpty()) {throw new ServiceException(Error.family_name, familyname);}
			customer = new Party();
			customer.setEmailaddress(emailaddress);
			customer.setName(familyname, firstname);
			sqlSession.getMapper(PartyMapper.class).create(customer);
		}
		return customer;
	}

	/**
	 * Gets the quotes.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the pos
	 * @param items the items
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @return the quotes
	 * @throws Exception the exception
	 */
	private static Collection<Quote> getQuotes(SqlSession sqlSession, String pos, ArrayList<NameId> items, String fromdate, String todate, String currency, Boolean terms, Boolean check) throws Exception {
		Party agent = Constants.getParty(sqlSession, pos); // this method checks agent's status and throws an exception in case of agent is inactive
		if (fromdate == null || fromdate.isEmpty() 
				|| todate == null || todate.isEmpty() 
				|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}
		if (items == null || items.isEmpty()) {throw new ServiceException(Error.product_location, "");}

		Collection<Quote> quotes = new ArrayList<Quote>();
		for (NameId item : items) {
			quotes.add(getQuote(sqlSession, agent.getId(), item.getId(), fromdate, todate, currency, terms, check, null));
		}
		return quotes;
	}
	
	/**
	 * Gets the search quotes.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the pos
	 * @param items the items
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @return the quotes
	 * @throws Exception the exception
	 */
	private static List<Quote> getSearchQuotes(SqlSession sqlSession, String pos, ArrayList<NameId> items, String fromdate, String todate, Integer guestCount, String currency, Boolean terms) throws Exception {
		Party agent = JSONService.getParty(sqlSession, pos); // this method checks agent's status and throws an exception in case of agent is inactive
		if (fromdate == null || fromdate.isEmpty() 
				|| todate == null || todate.isEmpty() 
				|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}
		if (items == null || items.isEmpty()) {throw new ServiceException(Error.product_location, "");}

		Quote quote= null;
		
		List<Quote> quotes = new ArrayList<Quote>();
		for (NameId item : items) {
			quote = getSearchQuote(sqlSession, pos, agent.getId(), item.getId(), fromdate, todate, guestCount, currency, terms);
			if (quote != null && quote.getPrice() != null) {
				quotes.add(quote);
			}			
		}
		return quotes;
	}
	
	/**
	 * Gets the search quotes for products ids.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the pos
	 * @param items the items
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @return the quotes
	 * @throws Exception the exception
	 */
	private static List<Quote> getProductsQuotes(SqlSession sqlSession, String pos, ArrayList<String> items, String fromdate, String todate, Integer guestCount, Boolean exactmatch, String currency, Boolean terms) throws Exception {
		Party agent = Constants.getParty(sqlSession, pos); // this method checks agent's status and throws an exception in case of agent is inactive
		if (fromdate == null || fromdate.isEmpty() 
				|| todate == null || todate.isEmpty() 
				|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}
		if (items == null || items.isEmpty()) {throw new ServiceException(Error.product_location, "");}

		Quote quote= null;
		
		List<Quote> quotes = new ArrayList<Quote>();
		for (String item : items) {
			quote = getProductQuote(sqlSession, agent.getId(), item, fromdate, todate, guestCount, exactmatch, currency, terms);
			if (quote != null && quote.getPrice() != null) {
				quotes.add(quote);
			}			
		}
		return quotes;
	}

	/**
	 * Gets the search quote for product.
	 *
	 * @param sqlSession the current SQL session.
	 * @param agentid the agentid
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @return the quote
	 */
	private static Quote getProductQuote(SqlSession sqlSession, String agentid, String productid, String fromdate, String todate,  Integer guestCount, Boolean checkexactmatch, String currency, Boolean terms) {

		Quote result = null;
		Integer personCount = 0;
		try {
			
			/* Product check */
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null) {throw new ServiceException(Error.product_id, productid);}
			if (product.getAssignedtomanager() == null || !product.getAssignedtomanager()) { return null; }
			if (product.notState(Constants.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid + " not Accommodation");}
			/* Filter by guests count */
			if (product.getAdult() != null){
				personCount = product.getAdult();
				if(product.getChild() != null) {personCount += product.getChild();}
				if (guestCount != null){
					if (personCount < guestCount) { return null; }
				}
			}
			/* Filter by manager related to channel partner */
			List<String> listManagerIds = sqlSession.getMapper(ChannelPartnerMapper.class).readRelatedManagersByPartyId(Integer.valueOf(agentid));
			if (!listManagerIds.contains(product.getSupplierid())) { return null; }
			
			Party party = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
						
			Reservation reservation = new Reservation();
			reservation.setProductid(productid);
			reservation.setFromdate(Constants.parseDate(fromdate));
			reservation.setTodate(Constants.parseDate(todate));			

			reservation.setOrganizationid(product.getSupplierid());
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agentid);
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setDonedate(null);
			
			/* TODO: CHECK what to do whith this  */
			
			//reservation.setQuantity(ReservationService.getAvailable(sqlSession, reservation));
			reservation.setQuantity(1);
			//
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setNotes("XML Reservation Request");
			reservation.setState(Reservation.State.Provisional.name());
			reservation.setAltpartyid(product.getAltpartyid());
			
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(party.getId()));
			currency = PartyService.checkMybookingpalCurrency(currency, propertyManagerInfo);
			
			result = computeQuote(sqlSession, fromdate, todate, currency, terms, null, product, reservation, false);
			
			if (reservation.getPrice() == 0.0  && reservation.getCost() == 0.0 && reservation.getExtra() == 0.0){
				return null;
			}
			
			result.setBedroom(product.getRoom() == null ? "1" : product.getRoom().toString()); 			// default 1
			result.setBathroom(product.getBathroom() == null ? "1" : product.getBathroom().toString()); // default 1
			
//			result.setMainstay("");
			
			String address = product.getAddress() == null ? "" : Arrays.toString(product.getAddress());
			result.setAddress(address);
			result.setManagerName(party == null ? "" : party.getName());
			
//			String picture = getImageUrl(sqlSession, product);
//			Integer picturesSize = getImagesQuantity(sqlSession, product); 
			List<String> productImages = ImageService.getProductRegularImageURLs(sqlSession, product.getId());
			String picture = productImages.size() > 0 ? productImages.get(0) : "";
			Integer picturesSize = productImages.size();
			
			result.setGuests(personCount.toString());
			result.setPictureLocation(picture);
			result.setPicturesQuantity(picturesSize);
			
			Double lng = product.getLongitude() == null ? 0 : product.getLongitude(); // FIXME : workaround
			Double lat = product.getLatitude() == null ? 0 : product.getLatitude();// FIXME : workaround
			result.setLongitude(lng.toString());
			result.setLatitude(lat.toString());
			result.updatePriceCheckInDayRule();
			result.updateExactMatch();			
			PropertyManagerInfo pmInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(party.getId()));
			if(pmInfo != null) {
				result.setInquiryOnly(pmInfo.isInquireOnly());
			} else {
				result.setInquiryOnly(true);
			}
			
			if (checkexactmatch && !result.getExactmatch()){ return null; }
			
			LOG.debug(result);
		}
		catch(Throwable x) {
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.product_not_available.name())) {LOG.error("getQuote " + agentid + " " + x.getMessage());}
			result  = new Quote(productid, fromdate, todate, Currency.Code.USD.name(), x.getMessage());
		}
		return result;
	}


	/**
	 * Gets the quote.
	 *
	 * @param sqlSession the current SQL session.
	 * @param agentid the agentid
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quote
	 */
	private static Quote getQuote(SqlSession sqlSession, String agentid, String productid, String fromdate, String todate, String currency, Boolean terms, Boolean check, String xsl) {

		Quote result = null;
		try {
			
			/* Product check */
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);			
			if (product == null) {throw new ServiceException(Error.product_id, productid);}
			if (product.notState(Constants.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid + " not Accommodation");}
			/*End Product check */
						
			Reservation reservation = new Reservation();
			reservation.setProductid(productid);
			reservation.setFromdate(Constants.parseDate(fromdate));
			reservation.setTodate(Constants.parseDate(todate));
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			Boolean available = reservation.noCollisions();
			if (check && !available) {throw new ServiceException(Error.product_not_available);}

			reservation.setOrganizationid(product.getSupplierid());
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agentid);
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setDonedate(null);
//			reservation.setQuantity(1);
			reservation.setQuantity(ReservationService.getAvailable(sqlSession, reservation));
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setNotes("XML Reservation Request");
			reservation.setState(Reservation.State.Provisional.name());
			
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			
			currency = PartyService.checkMybookingpalCurrency(currency, propertyManagerInfo);
			result = computeQuote(sqlSession, fromdate, todate, currency, terms, xsl, product, reservation, true);

			LOG.debug(result);
		}
		catch(Throwable x) {
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.product_not_available.name())) {LOG.error("getQuote " + agentid + " " + x.getMessage());}
			result = new Quote(productid, fromdate, todate, Currency.Code.USD.name(), x.getMessage());
		}
		return result;
	}
	
	/**
	 * Gets the search quote.
	 *
	 * @param sqlSession the current SQL session.
	 * @param agentid the agentid
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param terms the terms
	 * @return the quote
	 */
	private static Quote getSearchQuote(SqlSession sqlSession, String pos, String agentid, String productid, String fromdate, String todate, Integer guestCount, String currency, Boolean terms) {

		Quote result = null;
		Integer personCount = 0;
		try {
			
			/* Product check */
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null) {throw new ServiceException(Error.product_id, productid);}
			if (product.notState(Constants.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid + " not Accommodation");}
			/* Filter by guests count */
			if (product.getAdult() != null){
				personCount = product.getAdult();
				if(product.getChild() != null) {personCount += product.getChild();}
				if (guestCount != null){
					if (personCount < guestCount) { return null; }
				}
			}
			/* Filter by manager related to channel partner */
//			List<String> listManagerIds = sqlSession.getMapper(ChannelPartnerMapper.class).readRelatedManagersByPartyId(Integer.valueOf(agentid));
//			if (!listManagerIds.contains(product.getSupplierid())) { return null; }
			JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid());
			
			Party party = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
						
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(party.getId()));
			currency = PartyService.checkMybookingpalCurrency(currency, propertyManagerInfo);
			
			Reservation reservation = new Reservation();
			reservation.setProductid(productid);
			reservation.setFromdate(Constants.parseDate(fromdate));
			reservation.setTodate(Constants.parseDate(todate));			
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));			
			Boolean available = reservation.noCollisions();
			
			if (!available) { return null; }

			reservation.setOrganizationid(product.getSupplierid());
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agentid);
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setDonedate(null);
			reservation.setQuantity(ReservationService.getAvailable(sqlSession, reservation));
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setNotes("XML Reservation Request");
			reservation.setState(Reservation.State.Provisional.name());
			
			result = computeQuote(sqlSession, fromdate, todate, currency, terms, null, product, reservation, false);
			
			if (product.getRoom() != null) { result.setBedroom(product.getRoom().toString());}
			if (product.getBathroom() != null) { result.setBathroom(product.getBathroom().toString());}
			
//			result.setMainstay("");
			
			String address = ReservationService.getPropertyLocation(sqlSession, product);
			result.setAddress(address);
			result.setManagerName(party == null ? "" : party.getName());
			
			String picture = getImageUrl(sqlSession, product);
			Integer picturesSize = getImagesQuantity(sqlSession, product); 
			
			result.setGuests(personCount.toString());
			result.setPictureLocation(picture);
			result.setPicturesQuantity(picturesSize);
			result.setLongitude(product.getLongitude().toString());
			result.setLatitude(product.getLatitude().toString());
			
			LOG.debug(result);
		}
		catch(Throwable x) {
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.product_not_available.name())) {LOG.error("getQuote " + agentid + " " + x.getMessage());}
			result = new Quote(productid, fromdate, todate, Currency.Code.USD.name(), x.getMessage());
		}
		return result;
	}

	private static Quote computeQuote(SqlSession sqlSession, String fromdate, String todate, String currency, Boolean terms, String xsl, Product product, Reservation reservation, Boolean alert) throws Exception {
		
		Quote result;
		Boolean useOnePriceRow = false;
		/* TODO: commented for TEST   Integer quantity = ReservationService.getAvailable(sqlSession, reservation); */
		
		if (product.getUseonepricerow() != null && product.getUseonepricerow()){
		    
			ReservationService.computeOneRowPrice(sqlSession, reservation, product, currency);
			useOnePriceRow = true;
		}else {
		    
		    ReservationService.computePrice(sqlSession, reservation, currency);
		}

		Double agentDiscount = ReservationService.getDiscountfactor(sqlSession, reservation);
		Double price = reservation.getPrice(); 
		Double quote = reservation.getQuote();
		Double extra = reservation.getExtra();
		Double cost = quote * agentDiscount;
		Double deposit = reservation.getDeposit(quote);
		/*reservation.setQuote(quote);*/
		ArrayList<net.cbtltd.shared.Price> pricedetails = reservation.getQuotedetail(); // ReservationService.getPricedetails(sqlSession, reservation);

		if (!Constants.NO_CURRENCY.equalsIgnoreCase(currency) && reservation.notCurrency(currency)) {
			if (!Currency.isConvertible(currency)) {throw new ServiceException(Error.currency_exchange_rate, reservation.getCurrency() + " to " + currency);}
			Double exchangerate = WebService.getRate(sqlSession, reservation.getCurrency(), currency, new Date());			
			price = price * exchangerate;
			quote = quote * exchangerate;
			cost = cost * exchangerate;
			if (pricedetails != null) {
				for (net.cbtltd.shared.Price pricedetail : pricedetails) {
					Double value = pricedetail.getValue();
					pricedetail.setValue(value == null ? 0.0 : Double.valueOf(value) * exchangerate);
					pricedetail.setCurrency(currency);
				}
			}
			reservation.setCurrency(currency);
		}

		Double taxrate = 0.0;
		List<String> accomPriceIds = new ArrayList<String>();

		if (pricedetails != null) {
			Double tax = 0.0;
			for (net.cbtltd.shared.Price pricedetail : pricedetails) {
				pricedetail.setValue(NameId.round(pricedetail.getValue()));
				if (
						pricedetail.getValue() > 0.0 &&
						(pricedetail.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_INCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_ON_TAX))
						) {tax += pricedetail.getValue();}
				
				if (pricedetail.getType() != null && pricedetail.getType().equals(Price.RATE)){
					accomPriceIds.add(pricedetail.getId());
				}
			}
//TODO: CJM				taxrate = (quote == null || quote <= 0.0) ? 0.0 : tax * 100 / quote;
			taxrate = (quote == null || quote <= 0.0 || tax == null) ? 0.0 : tax * 100 / (quote - tax);
		}
		
		String checkInDayRule = Price.Rule.AnyCheckIn.toString();
		Integer minStay = 1;
		
		if (accomPriceIds.size() > 0){
			Price checkinprice = sqlSession.getMapper(PriceMapper.class).getcheckinprice(accomPriceIds);
			if (checkinprice != null){
				if (checkinprice.getRule() != null && !useOnePriceRow){
					checkInDayRule = checkinprice.getRule();
				}
				if (PartyIds.PARTY_INTERHOME_ID.equals(product.getSupplierid()) || PartyIds.PARTY_RENTALS_UNITED_ID.equals(product.getSupplierid())){
					MinStay action = new MinStay(product.getSupplierid(), product.getId(), Constants.parseDate(fromdate), null, 0);					
					action = sqlSession.getMapper(PropertyMinStayMapper.class).readbyexample(action);
					if (action != null && action.getValue() != null && action.getValue() > 0){
						minStay = action.getValue();
					}
				}else if (checkinprice.getMinStay() != null && checkinprice.getMinStay() > 0){
						minStay = checkinprice.getMinStay();
				}				
			}
		}

		String terms_text = null;
		if (terms) {
			String textid = NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Contract.name();
			terms_text = TextService.notes(sqlSession, textid, Language.EN);
		}
		String alertValue = "";
		if (alert){
			alertValue = getAlert(sqlSession, reservation);			
		}
		
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
		Double agentCommission = 0.;
		Double agentCommissionValue = 0.;
		PropertyManagerInfo pmInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
		CommissionCalculationUtil calculationUtil = new CommissionCalculationUtil(sqlSession, reservation, reservation.getPrice(), reservation.getExtra());
		if(channelPartner != null && pmInfo.isNetRate()) {
			agentCommission = calculationUtil.getAgentCommission() * 100.;
			agentCommissionValue = calculationUtil.getAgentCommissionValue();
		} else {
		if (agentDiscount < 1.0){
			agentCommission = NameId.round(1 - agentDiscount) * 100;
			if (quote != null){
				agentCommissionValue = quote * NameId.round(1 - agentDiscount);
			}
		}
		}
		
		result = new Quote(
				product.getAltpartyid(), 				// altpartyid 
				product.getLocationid(), 				// locationid
				product.getId(), 						// productid
				product.getName(),						// productname					
				fromdate, 								// arrive
				todate, 								// depart
				checkInDayRule,							// checkindayrule
				minStay,								// minstay
				String.valueOf(NameId.round(price)), 	// rack 
				String.valueOf(NameId.round(quote)), 	// quote
				String.valueOf(NameId.round(cost)),  	// sto
				String.valueOf(agentCommission),        // agentcommission
				String.valueOf(agentCommissionValue),	// agentcommissionvalue
				String.valueOf(NameId.round(extra)),	// extra
				String.valueOf(NameId.round(deposit)),	// deposit
				reservation.getQuantity(),				// quantity
				reservation.getCurrency(),				// currency
				alertValue,								// alert
				terms_text,								// terms	
				String.valueOf(NameId.round(taxrate)),	// taxrate
				pricedetails, 							// quotedetail
				null, xsl);								// message, xsl.
		return result;
	}

	/*
	 * Sets the alert string if one or more applies to the date range.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param hasalert the entity and date range for the alert.
	 */
	protected static String getAlert(SqlSession sqlSession, HasAlert hasalert) {
		if (hasalert == null) {return null;}
		Alert action = new Alert();
		action.setEntitytype(hasalert.getEntitytype());
		action.setEntityid(hasalert.getEntityid());
		action.setFromdate(hasalert.getFromdate());
		action.setTodate(hasalert.getTodate());
		ArrayList<Alert> alerts = sqlSession.getMapper(AlertMapper.class).exists(action);
		if (alerts == null || alerts.isEmpty()) {return null;}
		else {
			StringBuilder sb =  new StringBuilder();
			for (Alert alert : alerts) {sb.append(Constants.formatDate(alert.getFromdate())).append(" - ").append(Constants.formatDate(alert.getTodate())).append(" ").append(alert.getName()).append("\n");}
			return sb.toString();
		}
	}

	private static Double exchangerate = null;
	private static String currentCurrency = "";
	/*
	 * Convert currency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param quote the quote
	 * @param currency the currency
	 */
	private static void convertCurrency(SqlSession sqlSession, Quote quote, String currency) {
		if (!Constants.NO_CURRENCY.equalsIgnoreCase(currency) && quote.notCurrency(currency)) {
			
			if (!Currency.isConvertible(currency)) {throw new ServiceException(Error.currency_exchange_rate, quote.getCurrency() + " to " + currency);}
			if(exchangerate == null || !currentCurrency.equals(quote.getCurrency())) {
				exchangerate = WebService.getRate(sqlSession, quote.getCurrency(), currency, new Date());
				currentCurrency = quote.getCurrency();
			}
			
			Double price = Double.valueOf(quote.getRack()) * exchangerate;
			
			quote.setRack(String.valueOf(NameId.round(price)));
			
			price = Double.valueOf(quote.getQuote()) * exchangerate;
			
			quote.setQuote(String.valueOf(NameId.round(price)));
			
			price = Double.valueOf(quote.getSto()) * exchangerate;
			
			quote.setSto(String.valueOf(NameId.round(price)));
			quote.setCurrency(currency);
			
			if (quote.getQuotedetail() != null)
				for (net.cbtltd.shared.Price pricedetail : quote.getQuotedetail()) {
					Double value = pricedetail.getValue();
					pricedetail.setValue(NameId.round(value == null ? 0.0 : value * exchangerate));
					pricedetail.setCurrency(currency);
				}
			
		}
	}

	/**
	 * Gets the terms.
	 *
	 * @param sqlSession the current SQL session.
	 * @param special the special
	 * @param terms the terms
	 * @return the terms
	 */
	private static String getTerms(SqlSession sqlSession, Quote special, Boolean terms ) {
		if (terms) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(special.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, special.getProductid());}
			String textid = NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Contract.name();
			return TextService.notes(sqlSession, textid, Language.EN);
		}
		else {return null;}
	}

	/**
	 * Gets if the product is available for the date range.
	 *
	 * @param productid the product ID
	 * @param fromdate the arrival date
	 * @param todate the departure date
	 * @param pos the point of sale code
	 * @param xsl the optional style sheet
	 * @return true if the product is available, else false
	 */
	protected static synchronized Available getAvailable(
			String productid,
			String fromdate,
			String todate,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/available/" + productid + "/" + fromdate + "/" + todate + "?xsl=" + xsl + "&pos=" + pos;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Available result = null;
		try {
			getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT);
			Reservation reservation = new Reservation();
			reservation.setProductid(productid);
			reservation.setFromdate(Constants.parseDate(fromdate));
			reservation.setTodate(Constants.parseDate(todate));
			reservation.setQuantity(1);
//			Integer quantity = ReservationService.getAvailable(sqlSession, reservation);
			ReservationService.getCollisions(sqlSession, reservation);
			Boolean available = reservation.noCollisions();
//			Boolean available = quantity > 0;
			result = new Available(productid, getAlert(sqlSession, reservation), null, fromdate, todate, xsl, available, reservation.getQuantity());
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result =  new Available(productid, null, message + " " + x.getMessage(), fromdate, todate, xsl, false, 0);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the schedule at the location for the date range.
	 *
	 * @param locationid the location ID
	 * @param fromdate the start date
	 * @param todate the end date
	 * @param pos the point of sale code
	 * @param xsl the optional style sheet
	 * @return the schedule
	 */
	protected static synchronized Schedule getSchedule(
			String locationid,
			String fromdate,
			String todate,
			String pos,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/location/" + locationid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Schedule result = null;
		try {
			Reservation action = new Reservation();
			action.setId(locationid);
			action.setFromdate(Constants.parseDate(fromdate));
			action.setTodate(Constants.parseDate(todate));
			result = new Schedule(locationid, null, getRows(sqlSession.getMapper(ReservationMapper.class).locationschedule(action)), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Schedule(locationid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the product.
	 *
	 * @param productid the productid
	 * @param fromdate the from date
	 * @param todate the to date
	 * @param pos the pos code
	 * @param xsl the xsl
	 * @return the product
	 */
	protected static synchronized Schedule getProduct(
			String productid,
			String fromdate,
			String todate,
			String pos,
			String xsl) {

		return getProduct(productid,fromdate,todate,pos,xsl, false);
	}

	private static final Product getProductForXMLFeed(SqlSession sqlSession, String pos, String productid) throws Throwable {
		Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
		if (product == null) {
			throw new ServiceException(Error.product_id, productid);
		}
		if (product.notState(Product.CREATED)) {
			throw new ServiceException(Error.product_inactive, productid);
		}
		return product;
	}
	
	protected static synchronized Schedule getProduct(
			String productid,
			String fromdate,
			String todate,
			String pos,
			String xsl,
			boolean fromxml) {

		Date timestamp = new Date();
		String message = "/reservation/product/" + productid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Schedule result = null;
		
		@SuppressWarnings("unused")
		Product product;
		try {
			
			product = (fromxml) ? getProductForXMLFeed(sqlSession, pos, productid) :getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT);;
//			JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid());
			Reservation action = new Reservation();
			action.setId(productid);
			action.setFromdate(Constants.parseDate(fromdate));
			action.setTodate(Constants.parseDate(todate));
			result = new Schedule(null, null, getRows(sqlSession.getMapper(ReservationMapper.class).productschedule(action)), xsl);
		} 

		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Schedule(null, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Create schedule table from schedule item list.
	 *
	 * @param items the items
	 * @return the rows
	 */
	private static List<ScheduleRow> getRows(List<ScheduleItem> items) {
		List<ScheduleRow> rows = new ArrayList<ScheduleRow>();
		List<ScheduleCol> cols = null;
		ScheduleItem lastItem = null;
		int i = 0;
		for (ScheduleItem item : items) {
			if (lastItem == null || item.notId(lastItem.getId())) {
				cols = new ArrayList<ScheduleCol>();
				rows.add(new ScheduleRow(item.getId(), item.getName(), cols));
			}
			if (item.notSameCell(lastItem)) {cols.add(new ScheduleCol(item.getState(), item.getReservation(), item.getDate()));}
			lastItem = item;
			if (i++ > 10000){break;}
		}
		return rows;
	}

	/**
	 * Gets the quote.
	 *
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quote
	 */
	protected static synchronized Quote getQuote(
			String productid,
			String fromdate,
			String todate,
			String pos,
			String currency,
			Boolean terms,
			Boolean check,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/quote/" + productid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Quote result = null;
		try {
			getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT);
			if (fromdate == null || fromdate.isEmpty() 
					|| todate == null || todate.isEmpty() 
					|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}

			result = getQuote(sqlSession, Constants.decryptPos(pos), productid, fromdate, todate, currency, terms, check, xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Quote(productid, fromdate, todate, Currency.Code.USD.name(), x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the quotes.
	 *
	 * @param locationid the locationid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quotes
	 */
	protected static synchronized Quotes getQuotes(
			String locationid,
			String fromdate,
			String todate,
			String pos,
			String currency,
			Boolean terms,
			Boolean check,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/quotes/" + locationid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Quotes result = null;
		try {
			String[] locationids = locationid.split(",");
			ArrayList<NameId> items = sqlSession.getMapper(ProductMapper.class).nameidbylocationid(locationids);
			result = new Quotes(null,locationid, null, getQuotes(sqlSession, pos, items, fromdate, todate, currency, terms, check), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Quotes(null, locationid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the search quotes.
	 *
	 * @param locationid the locationid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quotes
	 */
	protected static synchronized SearchQuotes getSearchQuotes(
			String locationid,
			String fromdate,
			String todate,
			String guests,
			String pos,
			String currency,
			Boolean terms,
			Boolean amenities,
			String page,
			String perpage,
			String xsl) {
		
		Date timestamp = new Date();
		
		String message = "/search/quotes/" + locationid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&amenity"+ amenities + "&page"+ page + "&perpage" + perpage +"&xsl=" + xsl;		
		LOG.debug(message);
		
		SqlSession sqlSession = RazorServer.openSession();		
		SearchQuotes result = null;
		List<Quote> filteredQuotes = null;
		Integer guestCount = 0;
		
		try {
			String[] locationids = locationid.split(",");
			if (!guests.isEmpty()) { guestCount = Integer.parseInt(guests);}
			
//			if(locationIdsWithSublocations == null || locationIdsWithSublocations.length == 0) {
//				throw new ServiceException(Error.database_cannot_find, "Locations for passed parent location ID");
//			}
			ArrayList<NameId> items = sqlSession.getMapper(ProductMapper.class).nameidbyparentlocationid(locationids);
			List<Quote> searchQuotes = getSearchQuotes(sqlSession, pos, items, fromdate, todate, guestCount, currency, terms);
			
			if (searchQuotes != null){
				if (!page.isEmpty() && !perpage.isEmpty()){
					
					Integer pageInt = Integer.parseInt(page);
					Integer perPageInt = Integer.parseInt(perpage);
					Integer offset = (pageInt - 1) * perPageInt;
					
					if (offset > searchQuotes.size()) { throw new Exception("Invalid page number."); }
					
					Integer endOffset = ((searchQuotes.size() - offset) > perPageInt) ? offset + perPageInt : searchQuotes.size();
					filteredQuotes = searchQuotes.subList(offset, endOffset);									
				}else {
					filteredQuotes = searchQuotes;
				}
			}
			
			Map<String, Quote> quotesMap = new HashMap<String, Quote>();
						
			for (Quote item : filteredQuotes) {				
				quotesMap.put(item.getProductid(), item);
				item.getAttributes().add("");
			}

			if (!quotesMap.keySet().isEmpty()) {
				if (amenities) {
					List<Relation> attributes = sqlSession.getMapper(RelationMapper.class).headidsattributes(new ArrayList<String>(quotesMap.keySet()));
					if (attributes != null && !attributes.isEmpty()) {
						for (Relation relation : attributes) {

							String attribute = relation.getLineid();
							Quote quote = quotesMap.get(relation.getHeadid());

							if (attribute.startsWith("PCT")) {
								quote.setProductClassType(attribute);
							} else {
								quote.getAttributes().add(attribute);
							}
						}
					}

				} else {

					List<Relation> productTypes = sqlSession.getMapper(RelationMapper.class).productsclasstype(new ArrayList<String>(quotesMap.keySet()));

					if (productTypes != null && !productTypes.isEmpty()) {
						for (Relation productType : productTypes) {

							String attribute = productType.getLineid();
							Quote quote = quotesMap.get(productType.getHeadid());
							if (quote != null) {
								quote.setProductClassType(attribute);
							}
						}
					}
				}
			}
									
			result = new SearchQuotes(null, locationid, null, filteredQuotes, xsl);
			result.setQuotesCount(String.valueOf(searchQuotes.size()));
			result.setQuotesPerPage(perpage);
			result.setPageNumber(page);
		}
		
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new SearchQuotes(null, locationid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the search products.
	 *
	 * @param locationid the locationid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quotes
	 */
	public static synchronized SearchResponse getSearchProducts(
			String locationid,
			String fromdate,
			String todate,
			String productid,
			String guests,
			Boolean exactmatch,
			Boolean inquireonly,
			Boolean commission,
			String pos,
			String currency,
			Boolean terms,
			Boolean amenities,
			String page,
			String perpage,
			String xsl) {
		
		Date timestamp = new Date();
		LocalDate currentDate = new LocalDate();
		String message = "/search/quotes/" + locationid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&amenity"+ amenities + "&page"+ page + "&perpage" + perpage +"&xsl=" + xsl;		
		//LOG.debug(message);
		
		SqlSession sqlSession = RazorServer.openSession();	
		SearchResponse response = new SearchResponse();
		SearchQuotes result = null;
		List<Quote> filteredQuotes = null;
		List<Quote> listQuotes = null;
		Integer guestCount = 0;
		
		try {
			String[] locationids = locationid.split(",");
			if (!guests.isEmpty()) { guestCount = Integer.parseInt(guests);}
			
			LocalDate parsedFromDate = new LocalDate(Constants.parseDate(fromdate));
			if (parsedFromDate.isBefore(currentDate)){throw new Exception("Invalid from date parameter.");}
			
			if (locationids != null && locationids.length > 0){
				
				ArrayList<String> itemsWithPrice= null;
				if (inquireonly){
					itemsWithPrice = sqlSession.getMapper(ProductMapper.class).inquireonlyidsbyparentlocationandprice(locationids[0], fromdate, todate);
				}else{
					itemsWithPrice = sqlSession.getMapper(ProductMapper.class).idsbyparentlocationandprice(locationids[0], fromdate, todate);
				}
				
				ArrayList<String> itemsWithCollision = sqlSession.getMapper(ProductMapper.class).idswithreservationcollision(locationids[0], fromdate, todate);

				if (itemsWithPrice != null){
					itemsWithPrice.removeAll(itemsWithCollision);
					if (itemsWithPrice.size() > 0){
						if (!productid.isEmpty()){
							listQuotes = new ArrayList<Quote>();
							if (itemsWithPrice.contains(productid)){
								ArrayList<String> product = new ArrayList<String>();
								product.add(productid);
								listQuotes = getProductsQuotes(sqlSession, pos, product, fromdate, todate, guestCount, exactmatch, currency, terms);
							}
						}else {
							listQuotes = getProductsQuotes(sqlSession, pos, itemsWithPrice, fromdate, todate, guestCount, exactmatch, currency, terms);
						}
					}
					
					if (listQuotes != null ){
						if (!page.isEmpty() && !perpage.isEmpty()) {
							Integer pageInt = Integer.parseInt(page);
							Integer perPageInt = Integer.parseInt(perpage);
							Integer offset = (pageInt - 1) * perPageInt;
							
							if (offset > listQuotes.size()) { throw new Exception("Invalid page number."); }
							
							Integer endOffset = ((listQuotes.size() - offset) > perPageInt) ? offset + perPageInt : listQuotes.size();
							filteredQuotes = listQuotes.subList(offset, endOffset);									
						} else {
							filteredQuotes = listQuotes;
						}
						
						Map<String, Quote> quotesMap = new HashMap<String, Quote>();
						
						for (Quote item : filteredQuotes) {			
							if (!commission){
								item.setAgentCommission(null);
								item.setAgentCommissionValue(null);
							}
							quotesMap.put(item.getProductid(), item);
							item.getAttributes().add("");
						}
	
						if (!quotesMap.keySet().isEmpty()) {
							if (amenities) {
								List<Relation> attributes = sqlSession.getMapper(RelationMapper.class).headidsattributes(new ArrayList<String>(quotesMap.keySet()));
								if (attributes != null && !attributes.isEmpty()) {
									for (Relation relation : attributes) {
	
										String attribute = relation.getLineid();
										Quote quote = quotesMap.get(relation.getHeadid());
	
										if (attribute.startsWith("PCT") && StringUtils.isEmpty(quote.getProductClassType())) {
											quote.setProductClassType(attribute);
										} else {
											quote.getAttributes().add(attribute);
										}
									}
								}
	
							} else {
	
								List<Relation> productTypes = sqlSession.getMapper(RelationMapper.class).productsclasstype(new ArrayList<String>(quotesMap.keySet()));
	
								if (productTypes != null && !productTypes.isEmpty()) {
									String attribute = productTypes.get(0).getLineid();
									Quote quote = quotesMap.get(productTypes.get(0).getHeadid());
									if (quote != null) {	quote.setProductClassType(attribute); }
								}
							}
						}
					}
					
					result = new SearchQuotes(null, locationid, null, filteredQuotes, xsl);
					
					if (listQuotes != null ){
						result.setQuotesCount(String.valueOf(listQuotes.size()));
					}else{
						result.setQuotesCount("0");
					}
					
					result.setQuotesPerPage(perpage);
					result.setPageNumber(page);
				}
			}
		}
		
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new SearchQuotes(null, locationid, message + " " + x.getMessage(), null, xsl);
			response.setErrorMessage(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		response.setSearchQuotes(result);
		return response;
	}

	/**
	 * Gets the quotes nearby.
	 *
	 * @param locationid the locationid
	 * @param distance the distance
	 * @param unit the unit
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quotes nearby
	 */
	protected static synchronized Quotes getQuotesNearby(
			String locationid,
			Double distance,
			String unit,
			String fromdate,
			String todate,
			String pos,
			String currency,
			Boolean terms,
			Boolean check,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/quotes/" + locationid + "/" + distance + "/" + unit + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Quotes result = null;
		try {
			Location location = sqlSession.getMapper(LocationMapper.class).restread(locationid);
			Area area = new Area(location.getLatitude(), location.getLongitude(), distance, unit);
			String title = "Within " + distance + " " + unit + " of " + location.getName();
			ArrayList<NameId> items = sqlSession.getMapper(ProductMapper.class).nameidbyarea(area);
			result = new Quotes(title, locationid, null, getQuotes(sqlSession, pos, items, fromdate, todate, currency, terms, check), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Quotes(null, locationid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the quotes by position.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @param unit the unit
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the quotes by position
	 */
	protected static synchronized Quotes getQuotesByPosition(
			Double latitude,
			Double longitude,
			Double distance,
			String unit,
			String fromdate,
			String todate,
			String pos,
			String currency,
			Boolean terms,
			Boolean check,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/quotes/" + latitude + "/" + longitude + "/" + distance + "/" + unit + "/" + fromdate + "/" + todate + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Quotes result = null;
		try {
			Area area = new Area(latitude, longitude, distance, unit);
			String title = "Within " + distance + " " + unit + " of Latitude = " + latitude + " and Longitude = " + longitude;
			ArrayList<NameId> items = sqlSession.getMapper(ProductMapper.class).nameidbyarea(area);
			result = new Quotes(title, null, null, getQuotes(sqlSession, pos, items, fromdate, todate, currency, terms, check), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Quotes(null, null, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the special.
	 *
	 * @param locationid the locationid
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the special
	 */
	protected static synchronized Specials getSpecial(
			String locationid,
			String pos,
			String currency,
			Boolean terms,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/special/" + locationid + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Specials result = null;
		try {
			Constants.decryptPos(pos);
			String[] locationids = locationid.split(",");
			ArrayList<Quote> quotes = sqlSession.getMapper(SpecialMapper.class).quotesbylocationid(locationids);
			if (quotes == null || quotes.isEmpty()) {throw new ServiceException(Error.special_absent, locationid);}

			Collection<Quote> quote = new ArrayList<Quote>();
			
			for (Quote special : quotes) {
				//					special.setQuotedetail(ReservationService.getPricedetails(sqlSession, special));
				convertCurrency(sqlSession, special, currency);
				special.setTerms(getTerms(sqlSession, special, terms));
				quote.add(special);					
			}
			exchangerate = null;
			result = new Specials(quote, message, xsl);
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage());
			result = new Specials(null, message + " " + x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the specials by location.
	 *
	 * @param locationid the locationid
	 * @param distance the distance
	 * @param unit the unit
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the specials by location
	 */
	protected static synchronized Specials getSpecialsByLocation(
			String locationid,
			Double distance,
			String unit,
			String pos,
			String currency,
			Boolean terms,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/special/" + locationid + "/" + distance + "/" + unit + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Specials result = null;
		try {
			Constants.decryptPos(pos);
			Location location = sqlSession.getMapper(LocationMapper.class).restread(locationid);
			Area area = new Area(location.getLatitude(), location.getLongitude(), distance, unit);
			ArrayList<Quote> specials = sqlSession.getMapper(SpecialMapper.class).quotesbyarea(area);
			if (specials == null || specials.isEmpty()) {throw new ServiceException(Error.special_absent, area.toString());}

			Collection<Quote> quote = new ArrayList<Quote>();
			for (Quote special : specials) {
				//				special.setQuotedetail(ReservationService.getPricedetails(sqlSession, special));//.getProductid(), Constants.parseDate(special.getArrive())));
				convertCurrency(sqlSession, special, currency);
				special.setTerms(getTerms(sqlSession, special, terms));
				quote.add(special);
			}
			exchangerate = null;
			result = new Specials(quote, message, xsl);
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage());
			result = new Specials(null, message + " " + x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the specials by position.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @param unit the unit
	 * @param pos the pos
	 * @param currency the currency
	 * @param terms the terms
	 * @param xsl the xsl
	 * @return the specials by position
	 */
	protected static synchronized Specials getSpecialsByPosition(
			Double latitude,
			Double longitude,
			Double distance,
			String unit,
			String pos,
			String currency,
			Boolean terms,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/special/" + latitude + "/" + longitude + "/" + distance + "/" + unit + "?pos=" + pos + "&currency=" + currency + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Specials result = null;
		try {
			Constants.decryptPos(pos);
			Area area = new Area(latitude, longitude, distance, unit);
			ArrayList<Quote> specials = sqlSession.getMapper(SpecialMapper.class).quotesbyarea(area);
			if (specials == null || specials.isEmpty()) {throw new ServiceException(Error.special_absent, area.toString());}
			Collection<Quote> quote = new ArrayList<Quote>();
			for (Quote special : specials) {
				//				special.setQuotedetail(ReservationService.getPricedetails(sqlSession, special));
				convertCurrency(sqlSession, special, currency);
				special.setTerms(getTerms(sqlSession, special, terms));
				quote.add(special);
			}
			exchangerate = null;
			result = new Specials(quote, message, xsl);
		}
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result =  new Specials(null, message + " " + x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	protected static ReservationResponse getCancellationInfo(String pos) {
		SqlSession sqlSession = null;
		ReservationResponse reservationResponse = new ReservationResponse();
		try {
			sqlSession = RazorServer.openSession();
			String reservationId = Model.decrypt(pos);
			
			// init values
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
			String propertyManagerName = propertyManager.getName();
			String propertyManagerPhone = propertyManager.getDayphone();
			String propertyManagerEmail = propertyManager.getEmailaddress();
			String formattedFromDate = JSONService.DF.format(reservation.getFromdate());
			String formattedToDate = JSONService.DF.format(reservation.getTodate());
			List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
			String currency = paymentTransactions.get(0).getCurrency();
			PriceUtil.convertCurrency(sqlSession, reservation, currency, reservation.getDate());
			roundReservationDoubleValues(reservation);
			Double totalCharge = reservation.getQuote();
			Double downPayment = PaymentHelper.getFirstPayment(reservation, propertyManagerInfo);
			Double secondPayment = null;
			if(propertyManagerInfo.getNumberOfPayments() > 1) {
				secondPayment = PaymentHelper.getSecondPayment(reservation, propertyManagerInfo);
			}
			
			PropertyManagerCancellationRule rule = PaymentHelper.getRuleForCurrentDate(sqlSession, reservation, Integer.valueOf(product.getSupplierid()));
			CancellationItem cancellationItem = null;
			if(rule != null) {
				Date cancellationDate = PaymentHelper.getCancellationDate(reservation.getFromdate(), rule);
				Double cancellationAmount = ReservationService.calculateCancellationAmount(sqlSession, reservation, propertyManagerInfo);
				String formattedCacnellationDate = JSONService.DF.format(cancellationDate);
				cancellationItem = new CancellationItem(formattedCacnellationDate, Double.valueOf(rule.getCancellationRefund()), cancellationAmount, rule.getCancellationTransactionFee(), null);
			} else {
				cancellationItem = new CancellationItem("", 0., 0., 0., 0);
			}
			
			// set values to response
			reservationResponse.setReservationInfo(new ReservationInfo(reservation));
			reservationResponse.setPropertyAddress(ReservationService.getPropertyLocation(sqlSession, product));
			reservationResponse.setCancellationItem(cancellationItem);
			reservationResponse.setDownPayment(downPayment);
			reservationResponse.setSecondPayment(secondPayment);
			reservationResponse.setPropertyName(product.getName());
			reservationResponse.setPropertyAddress(propertyAddress);
			reservationResponse.setPropertyManagerEmail(propertyManagerEmail);
			reservationResponse.setPropertyManagerName(propertyManagerName);
			reservationResponse.setPropertyManagerPhone(propertyManagerPhone);
			reservationResponse.setTotalCharge(totalCharge);
			reservationResponse.setFromDate(formattedFromDate);
			reservationResponse.setToDate(formattedToDate);
		} catch (Exception e) {
			reservationResponse.setErrorMessage(e.getMessage());
			LOG.error(e.getMessage()); 
		} finally {
			if(sqlSession != null) {sqlSession.close();}
		}
		return reservationResponse;
	}
	
	private static void roundReservationDoubleValues(Reservation reservation) {
		if(reservation == null) {
			throw new ServiceException(Error.parameter_null, "reservation");
		}
		Double price = reservation.getPrice();
		Double cost = reservation.getCost();
		Double quote = reservation.getQuote();
		Double deposit = reservation.getDeposit();
		Double extra = reservation.getExtra();
		
		reservation.setPrice(price == null ? 0. : round(price, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setCost(cost == null ? 0. : round(cost, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setQuote(quote == null ? 0. : round(quote, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setDeposit(deposit == null ? 0. : round(deposit, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setExtra(extra == null ? 0. : round(extra, 2, BigDecimal.ROUND_HALF_UP));
		if(reservation.getQuotedetail() != null) {
			for(Price priceItem : reservation.getQuotedetail()) {
				priceItem.setValue(priceItem.getValue() == null ? 0. : round(priceItem.getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}

	/**
	 * Get a reservation by its global ID or reservation number specific to a manager.
	 *
	 * @param isName is true if the globally unique ID, else false.
	 * @param id the reservation ID or number.
	 * @param pos the point of sale code.
	 * @param terms is true id the terms and conditions are to be included.
	 * @param xsl the style sheet.
	 * @return the reservation.
	 */
	protected static synchronized Reservation getReservation (
			boolean byID,
			boolean detail,
			String id,
			String pos,
			Boolean terms,
			String xsl) {

		Date timestamp = new Date();
		String message = byID ? "/id/" : "/name/" + id + "?pos=" + pos + "&terms=" + terms + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Reservation result = new Reservation();
		try {
			if (byID) {result = sqlSession.getMapper(ReservationMapper.class).read(id);}
			else {
//				Party organization = Constants.getParty(sqlSession, pos);
				Reservation action = new Reservation();
				action.setName(id);
				action.setOrganizationid(Constants.decryptPos(pos));
				result = sqlSession.getMapper(ReservationMapper.class).readbyorganization(action);
//				if (result == null || !organization.hasId(result.getOrganizationid())) {throw new ServiceException(Error.reservation_bad, id);}
			}

			if (result == null) {throw new ServiceException(Error.reservation_id, id);}

			Product product = getProduct(sqlSession, pos, result.getProductid(), License.DEFAULT_WAIT);
			Party actor = result.noActorid() ? null : sqlSession.getMapper(PartyMapper.class).read(result.getActorid());
			Party agent = result.noAgentid() ? null : sqlSession.getMapper(PartyMapper.class).read(result.getAgentid());
			Party customer = result.noCustomerid() ? null : sqlSession.getMapper(PartyMapper.class).read(result.getCustomerid());
			Party supplier = result.noSupplierid() ? null : sqlSession.getMapper(PartyMapper.class).read(result.getSupplierid());
			
			if (detail) {
				result.setProduct(product);
				result.setActor(actor);
				result.setAgent(agent);
				result.setCustomer(customer);
				result.setSupplier(supplier);
			}
			else {
				result.setProductname(product == null ? null : product.getName());
				result.setActorname(actor == null ? null : actor.getName());
				result.setAgentname(agent == null ? null : agent.getName());
				result.setCustomername(customer == null ? null : customer.getName());
				result.setSuppliername(supplier == null ? null : supplier.getName());
			}

			if (result.hasCard()) {
				result.setCardholder(Model.decrypt(result.getCardholder()));
				result.setCardnumber(Model.decrypt(result.getCardnumber()));
				result.setCardmonth(Model.decrypt(result.getCardmonth()));
				result.setCardyear(Model.decrypt(result.getCardyear()));
				result.setCardcode(Model.decrypt(result.getCardcode()));				
			}

			Double quote = result.getQuote();

			ArrayList<net.cbtltd.shared.Price> quotedetail = sqlSession.getMapper(PriceMapper.class).quotedetail(result.getId());
			result.setQuotedetail(quotedetail);

			if (quotedetail != null) {
				Double extra = 0.0;
				Double tax = 0.0;
				for (net.cbtltd.shared.Price pricedetail : quotedetail) {
					pricedetail.setValue(NameId.round(pricedetail.getValue()));
					if (!pricedetail.hasType(Price.RATE) && !pricedetail.hasType(Price.YIELD)) {extra += pricedetail.getValue();}
					if (
						pricedetail.getValue() > 0.0 &&
						(pricedetail.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_INCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_ON_TAX))
						) {tax += pricedetail.getValue();}
				}
				result.setExtra(extra);
				result.setTaxrate((quote == null || quote <= 0.0 || tax == null) ? 0.0 : tax * 100 / (quote - tax));
			}
			
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.RESERVATION_VALUE, result.getId(), null);
			if (keyvalues != null) {
				ArrayList<KeyValue> item = new ArrayList<KeyValue>();
				StringBuilder sb = new StringBuilder();
				for (String keyvalue : keyvalues) {
					String[] args = keyvalue.split(Model.DELIMITER);
					if (args.length == 2) {
						item.add(new KeyValue(args[0], args[1]));
						sb.append(keyvalue).append("\n");
					}
				}
				result.setKeyvalues(item);
				if (result.hasNotes()) {sb.append(result.getNotes());}
				result.setNotes(sb.toString());
			}

			result.setAlert(getAlert(sqlSession, result));
			if (terms) {
				String textid = NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Contract.name();
				result.setTerms(TextService.notes(sqlSession, textid, Language.EN));
			}
			
			ReservationExt temp = new ReservationExt();
			temp.setReservationId(result.getAltid());
			temp.setType(ReservationExt.GUEST_TYPE);
			System.out.println("Reservation Ext Reservation id: " + result.getAltid());
			List<ReservationExt> reservationExts = sqlSession.getMapper(ReservationExtMapper.class).readReservationExt(temp);
			for(ReservationExt reservationExt:reservationExts) {
				if(reservationExt != null) {
					System.out.println("Guest name: " + reservationExt.getName());
					result.setGuestName(reservationExt.getName());
					break;
				}
			}
			//Correct firtname and last name here
			if(customer.getName()!=null){
				System.out.println("Customer name with id: " + customer.getName() + "id: " + customer.getId());
				//result.setFamilyname(customer.getName());
				//result.setFirstname(customer.getName());
				/*StringTokenizer token=new StringTokenizer(customer.getName(),",");
				int cnt=1;
				while(token.hasMoreTokens()){
					String str=(String) token.nextElement();
					if(cnt==1){
						result.setFamilyname(str);
					}else{
						result.setFirstname(str);
					}
					cnt++;
				}*/
			}
			
			// add the addons
			temp = new ReservationExt();
			temp.setReservationId(result.getAltid());
			temp.setType(ReservationExt.ADDON_TYPE);
			System.out.println("Reservation Ext Reservation id: " + result.getAltid());
			reservationExts = sqlSession.getMapper(ReservationExtMapper.class).readReservationExt(temp);
			result.setListReservationExt(reservationExts);
		
		}
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Reservation();
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Creates, updates or cancels a reservation.
	 *
	 * @param action the reservation parameter.
	 * @param pos the POS code.
	 * @param xsl the style sheet.
	 * @return the provisional reservation.
	 */
	protected static synchronized Reservation setReservation (
			Reservation action,
			String pos,
			String xsl
		) {
		Date timestamp = new Date();
		String message = "POST reservation?pos=" + pos + "&xsl=" + xsl + "\nparams " + action.paramString();
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Reservation result = null;
		try {
			
			String partyid = Constants.decryptPos(pos);
	
			Product product = getProduct(sqlSession, pos, action.getProductid(), License.DEFAULT_WAIT);
			if (product == null) {throw new ServiceException(Error.product_id, action.getProductid());}

			if (action.notValidState()) {throw new ServiceException(Error.reservation_state, action.getId() + " " + action.getState());}

			if (action.noId()) { //new reservation
				result = action;
				result.setName(SessionService.pop(sqlSession, product.getSupplierid(), Serial.RESERVATION));
				//set booking id in the name field
				
				result.setAltpartyid(product.getAltpartyid());
				result.setAltid(action.getAltid());
//				result.setName("Booking Id "+action.getAltid());
				result.setOrganizationid(product.getSupplierid());
				result.setActorid(Party.NO_ACTOR);
				
				//TODO:COnfirm with Chirayu
				if(action.getAgentid()!=null){
					result.setAgentid(action.getAgentid());	
				}else{
					result.setAgentid(partyid);
				}
				
				result.setDate(new Date());
				result.setDuedate(result.getDate());
				result.setDonedate(null);
				result.setUnit(product.getUnit());
				result.setCurrency(product.getCurrency());
				result.setState(action.getState());
				sqlSession.getMapper(ReservationMapper.class).create(result);
				if (State.Cancelled.name().equalsIgnoreCase(action.getState())) {
					// incase a reservation is created and cancelled
					// immediately.
					// TODO:validate with chirayu.

					MonitorService.update(sqlSession, Data.Origin.XML_JSON,
							NameId.Type.Reservation, result);
					sqlSession.commit();
					return result;
				}
			}
			else {
	 
				result = sqlSession.getMapper(ReservationMapper.class).read(
						action.getId());
				
				
			}
			

			if (result == null) {
				throw new ServiceException(Error.reservation_id, action.getId());
			}

			if (action.getEmailaddress() != null) {
				Party customer = getParty(sqlSession, action.getEmailaddress(), action.getFamilyname(), action.getFirstname());
				RelationService.replace(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), partyid, customer.getId());
				RelationService.replace(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), product.getSupplierid(), customer.getId());
				result.setCustomerid(customer.getId());
				result.setEmailaddress(action.getEmailaddress());
			}

			if (action.getFromdate() != null || action.getTodate() != null) {
				if (action.noDuration(0.9, Time.DAY)) {throw new ServiceException(Error.date_invalid, action.getProductFromToDate());}
				result.setFromdate(action.getFromdate());
				result.setTodate(action.getTodate());
			}

			if (action.getArrivaltime() != null) {result.setArrivaltime(action.getArrivaltime());}
			else {result.setArrivaltime(Reservation.ARRIVALTIME);}
			
			if (action.getDeparturetime() != null) {result.setDeparturetime(action.getDeparturetime());}
			else {result.setDeparturetime(Reservation.DEPARTURETIME);}
			
			Double quote = action.getQuote();
			Double taxrate = action.getTaxrate();
			String currency = action.getCurrency();
			
			//if (quote != null) {result.setQuote(quote);}
			if (taxrate != null) {result.setTaxrate(taxrate);}
			if (currency != null) {result.setCurrency(currency);}

			if (action.hasNotes()) {result.setNotes(action.getNotes());}

			result.setState(action.getState());
	
			ReservationService.computePrice(sqlSession, result, null);
			if (quote != 0){
				Double oldquote = action.getQuote();
				action.setQuote(quote);
				if (action.getQuotedetail() != null) {
					for (net.cbtltd.shared.Price pricedetail : action.getQuotedetail()) {
						if (
								pricedetail.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_INCLUDED)
								|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_ON_TAX)
								) {
							pricedetail.setValue(pricedetail.getValue() * quote / oldquote);
						}
					}
				}
			}
			

			//TODO: save tax allocations
			if (result.notCurrency(product.getCurrency())) {result.setQuote(WebService.getRate(sqlSession, result.getCurrency(), product.getCurrency()) * result.getQuote());}
			result.setCost(result.getQuote() * ReservationService.getDiscountfactor(sqlSession, result));
			result.setDeposit(ReservationService.getDeposit(sqlSession, result)); //.getOrganizationid(), action.getFromdate()));
			if(result.getDeposit()!=null && result.getDeposit().isNaN()){
				result.setDeposit(0.0);
			}
		
			sqlSession.getMapper(ReservationMapper.class).update(result);
			
			if (action.getKeyvalues() != null) {
				ArrayList<String> kvs = new ArrayList<String>();
				Collection<KeyValue> keyvalues = action.getKeyvalues();
				for (KeyValue keyvalue : keyvalues) {kvs.add(keyvalue.getKey() + Model.DELIMITER + keyvalue.getValue());}
				RelationService.replace(sqlSession, Relation.RESERVATION_VALUE, action.getId(), kvs);
			}

			result.setAlert(getAlert(sqlSession, result));
			String textid = NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Contract.name();
			String terms = TextService.notes(sqlSession, textid, Language.EN);
			result.setTerms(terms);

			result.setCollisions(ReservationService.getCollisions(sqlSession, result));			
			if (result.hasCollisions()) {throw new ServiceException(Error.product_not_available, result.getProductFromToDate());}

			//System.out.println("\nALTID " + product.hasAltpartyid() + " " + product.getAltpartyid());
			
			if (product.hasAltpartyid()) {
				try{
				PartnerService.createReservation(sqlSession, result);
				}catch(ServiceException e){
				//	LOG.error(e.getMessage(),e);
					LOG.error(e.getMessage());
				}
			}

			MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Reservation, result);
			sqlSession.commit();
			
			if (result.isActive() && product.noAltpartyid()) {EmailService.provisionalReservation(sqlSession, result);}
		
		}
		catch (Throwable x){
			x.printStackTrace();
			sqlSession.rollback();
			result = new Reservation();
			result.setMessage(message + "\nsetReservation failed " + x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	public static Date stringToDate(Integer year, Integer month, Integer day) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}
	
	/*public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		String productId = "95420";
		String fromDate = "2014-07-26";
		String toDate = "2014-07-30";
		String notes = "notes";
		Integer adult = 2;
		Integer child = 0;
		String emailAddress = "test@test.com";
		String familyName = "TestFamily";
		String firstName = "TestFirst";
		String phoneNumber = "1234567";
		String address = "address";
		String country = "USA";
		String city = "New-York";
		String zip = "11111";
		String state = "New-York";
		Integer birthDay = 1;
		Integer birthMonth = 6;
		Integer birthYear = 1988;
		String pos = "834a55a7680c79fe";
		createInquiredReservation(pos, productId, fromDate, toDate, notes, adult, child, emailAddress, familyName, firstName, phoneNumber, address, country, city, zip, state, birthDay, birthMonth, birthYear);
	}*/
	
	protected static synchronized ReservationResponse createInquiredReservation(String pos, String productId, String fromDate, String toDate, String notes, Integer adult, Integer child, // reservation fields
			String emailAddress, String familyName, String firstName, String phoneNumber) { // customer fields
		SqlSession sqlSession = RazorServer.openSession();
		ReservationResponse response = new ReservationResponse();
		Party channelPartnerParty = null;
		Party propertyManager = null;
		Product product = null;
		Reservation reservation = null;
		PropertyManagerInfo propertyManagerInfo = null;
		PaymentTransaction paymentTransaction = null;
		boolean successful = false;
		try {
			channelPartnerParty = JSONService.getParty(sqlSession, pos);
			if (channelPartnerParty == null) {throw new ServiceException(Error.reservation_agentid);}
			product = sqlSession.getMapper(ProductMapper.class).read(productId);
			propertyManager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
			propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			Party customer = processCustomer(sqlSession, emailAddress, familyName, firstName, product, phoneNumber, channelPartnerParty, product.getSupplierid(), 
					channelPartnerParty, null, null, null, null, null, null);
			reservation = PaymentHelper.prepareReservation(channelPartnerParty, customer, product, propertyManagerInfo, fromDate, toDate, notes, productId, adult, child);
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).create(reservation);
			if (product.getInquireState().equals(Product.SEND_EMAIL)) {
				EmailService.inquiredReservationForManager(sqlSession, reservation);
				EmailService.inquiredReservationForRenter(sqlSession, reservation);
				successful = true;
			} else if (product.getInquireState().equals(Product.USE_API)) {
				PartnerService.inquireReservation(sqlSession, reservation);
				successful = true;
			} else {
				throw new ServiceException(Error.inquiry_state);
			}
	
			Map<String, String> result = new HashMap<String, String>();
			result.put(GatewayHandler.STATE, GatewayHandler.INQUIRED);
			Integer agentId = channelPartnerParty == null ? null : Integer.valueOf(channelPartnerParty.getId());
			paymentTransaction = PaymentHelper.prepareInquiryTransaction(reservation, product, agentId);
			reservation.setState(Reservation.State.Inquiry.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			response.setPropertyAddress(ReservationService.getPropertyLocation(sqlSession, product));
			response.setPropertyName(product.getName());
			response.setPropertyManagerEmail(propertyManager.getEmailaddress());
			response.setReservationInfo(new ReservationInfo(reservation));			
		} catch (RuntimeException x) {
			x.printStackTrace();
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage());
		} catch (Exception x) {
			if(!successful) {
				Integer id = channelPartnerParty == null ? null : Integer.valueOf(channelPartnerParty.getId());
				Map<String, String> result = new HashMap<String, String>();
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				paymentTransaction = PaymentHelper.prepareInquiryTransaction(reservation, product, id);
			}
		} finally {
			if(paymentTransaction != null) {
				sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
				sqlSession.commit();
			}
			sqlSession.close();
		}
		return response;
	}
	
	protected static synchronized ReservationResponse createReservationPayment_new(String pos, String productId, String fromDate, String toDate, String emailAddress, 
			String familyName, String firstName, String notes, String cardNumber, String cardExpiryMonth, String cardExpiryYear, String amount, String currency, String phoneNumber,
			Integer cardType, Integer cvc, Integer adult, Integer child, String address, String country, String state, String city, String zip, Integer birthDay,
			Integer birthMonth, Integer birthYear, String altId) {
		ReservationRequest request = new ReservationRequest();
		ReservationResponse response = new ReservationResponse();
		request.setPos(pos);
		request.setProductId(productId);
		request.setFromDate(fromDate);
		request.setToDate(toDate); 
		request.setEmailAddress(emailAddress); 
		request.setFamilyName(familyName); 
		request.setFirstName(firstName); 
		request.setNotes(notes); 
		request.setCardNumber(cardNumber); 
		request.setCardMonth(cardExpiryMonth); 
		request.setCardYear(cardExpiryYear); 
		request.setAmount(amount); 
		request.setCurrency(currency); 
		request.setPhoneNumber(phoneNumber);
		request.setCardType(cardType); 
		request.setCvc(cvc); 
		request.setAdult(adult); 
		request.setChild(child); 
		request.setAddress(address); 
		request.setCountry(country); 
		request.setState(state); 
		request.setCity(city); 
		request.setZip(zip); 
		request.setBirthDay(birthDay);
		request.setBirthMonth(birthMonth);
		request.setBirthYear(birthYear);
		request.setAltId(altId);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			
			ReservationBuilderConfiguration configuration = ReservationBuilderConfiguration.getConfigurationForPm(Integer.valueOf(product.getSupplierid()));
			ReservationBuilder builder = ReservationBuilder.getInstance(configuration);
			response = builder.buildResponse(request);
		} catch (ServiceException e) {
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			response.setErrorMessage("Something went wrong during reservation");
		} finally {
			sqlSession.close();
		}
		return response;
	}
	
	// it need should and must be refactored
	protected static synchronized ReservationResponse createReservationPayment(String pos, String productId, String fromDate, String toDate, String emailAddress, 
			String familyName, String firstName, String notes, String cardNumber, String cardExpiryMonth, String cardExpiryYear, String amount, String currency, String phoneNumber,
			Integer cardType, Integer cvc, Integer adult, Integer child, String address, String country, String state, String city, String zip, Integer birthDay,
			Integer birthMonth, Integer birthYear, String altId) {
		resetValue(address);
		resetValue(country);
		resetValue(city);
		resetValue(state);
		SqlSession sqlSession = RazorServer.openSession();
		ReservationResponse response = new ReservationResponse();
		PaymentTransaction paymentTransaction = null;
		PendingTransaction pendingTransaction = null;
		Reservation reservation = new Reservation();
		boolean reservationFinished = false;
		GatewayHandler handler = null;
		try {
			if(StringUtils.isEmpty(pos) || StringUtils.isEmpty(productId) || StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate) || StringUtils.isEmpty( emailAddress) || StringUtils.isEmpty(familyName) | StringUtils.isEmpty(firstName) ||
					StringUtils.isEmpty(phoneNumber) || cardType == null ||  child == null || StringUtils.isEmpty(currency)  || birthDay == null || birthMonth == null || 
					birthYear == null || amount == null || zip == null ) {
				throw new ServiceException(Error.parameter_absent);
			}
			
			if(adult == null || adult < 1 ){
				throw new ServiceException(Error.missing_adult_parameter);
			}
			if( StringUtils.isEmpty(cardNumber) || StringUtils.isEmpty( cardExpiryMonth ) || StringUtils.isEmpty( cardExpiryYear ) || cvc == null) {
//				throw new ServiceException(Error.credit_card_error);
			}
			//yyyy-mm-dd
			if(fromDate.compareToIgnoreCase(toDate) > 0){  throw new ServiceException(Error.date_range);  }
			
			Date birthDate = null;
			if(birthYear != null && birthMonth != null && birthDay != null) {
				birthDate = stringToDate(birthYear, birthMonth - 1, birthDay);
			}	
			
			boolean encodable = false;
			if(state != null) {
				encodable = CommonUtils.iso88591Encodable(new String[]{firstName, familyName, address, country, city, state});	
			} else {
				encodable = CommonUtils.iso88591Encodable(new String[]{firstName, familyName, address, country, city});
			}
			
			if(!encodable) {
				throw new ServiceException(Error.parameter_invalid);
			}
			
		//	String[] ignoreIds = {"179805", "90640", "179769","179791","179792","179793","179795","179801","179802", "179896", "179797",
			//		"179799", "179800", "179803", "179804"};  
			
			// Credit card initialization
			CreditCard creditCard = new CreditCard();
			creditCard.setFirstName(firstName);
			creditCard.setLastName(familyName);
			creditCard.setMonth(cardExpiryMonth);
			cardNumber = cardNumber.trim();
			creditCard.setNumber(cardNumber);
			creditCard.setType(CreditCardType.get(cardType));
			creditCard.setYear(cardExpiryYear);
			creditCard.setSecurityCode(cvc.toString());
			
			// Initialization of necessary instances
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			if(!product.getState().equals(Product.CREATED)) {
				throw new ServiceException(Error.product_inactive);
			}
			int supplierId = Integer.valueOf(product.getSupplierid());
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(supplierId);
			if(propertyManagerInfo == null) { throw new ServiceException(Error.database_cannot_find_property_manager, String.valueOf(supplierId)); }
			
			response.setPropertyName(product.getName());
			response.setPropertyAddress(ReservationService.getPropertyLocation(sqlSession, product));
			
			// Initialization end
			Party channelPartnerParty = JSONService.getParty(sqlSession, pos);
	//		Party channelPartnerParty = JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid()); // this method checks agent's status and throws an exception in case of agent is inactive
			if (channelPartnerParty == null) {throw new ServiceException(Error.reservation_agentid);}
//			Party customer = JSONService.getCustomer(sqlSession, emailAddress, familyName, firstName, product.getSupplierid(), phoneNumber, channelPartnerParty);
			Party customer = processCustomer(sqlSession, emailAddress, familyName, firstName, product, phoneNumber, channelPartnerParty, product.getSupplierid(), 
					channelPartnerParty, address, country, city, zip, state, birthDate);
			Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(propertyManagerInfo.getPropertyManagerId().toString());
			response.setPropertyManagerName(propertyManager.getName());
			response.setPropertyManagerEmail(propertyManager.getEmailaddress());
			response.setPropertyManagerPhone(propertyManager.getDayphone());
			
			// Reservation processing
			reservation = PaymentHelper.prepareReservation(channelPartnerParty, customer, product, propertyManagerInfo, fromDate, toDate, notes, productId, adult, child);

			// END INITIALIZATION BLOCK
			
			Double amountToCheck = PaymentHelper.roundAmountTwoDecimals(amount);
			Double amountDifference = PaymentHelper.amountDifference(sqlSession, reservation, amountToCheck, currency);
			if(amountDifference > 1 && (BPThreadLocal.get() == null || !BPThreadLocal.get())) {
				throw new ServiceException(Error.price_not_match, "passed: " + amountToCheck + currency + ", difference: " + amountDifference);
			}
			
			if(propertyManagerInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER) {
//				amount = PaymentHelper.convertToDefaultMbpCurrency(sqlSession, reservation, propertyManagerInfo, PaymentHelper.roundAmountTwoDecimals(amount), currency).toString();
				currency = PaymentHelper.DEFAULT_BOOKINGPAL_CURRENCY;
			} else {
//				amount = PaymentService.convertCurrency(sqlSession, currency, reservation.getCurrency(), PaymentHelper.roundAmountTwoDecimals(amount)).toString();
				currency = product.getCurrency();
			}
			if(reservation.getAltpartyid() == null || !Arrays.asList(livePricingIds).contains(reservation.getAltpartyid())) {
				ReservationService.computePrice(sqlSession, reservation, currency);
				Double deposit = ReservationService.getDeposit(reservation, propertyManagerInfo);
				reservation.setDeposit(deposit);
				reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
			} else {
				LOG.error("PartnerService.readPrice started");
				PartnerService.readPrice(sqlSession, reservation, product.getAltid(), currency);
				
				if((reservation.getPrice() == null || reservation.getPrice() <= 0) || (reservation.getQuote() == null || reservation.getQuote() <= 0)) {
					throw new ServiceException(Error.price_missing, "Price was resturned null or 0");
				}				
				
				ReservationService.computeLivePrice(sqlSession, reservation, null, currency);
				
				LOG.error("PartnerService.readPrice finished");
				
			}
			
			if(reservation.getPrice() == 0.0) {
				throw new ServiceException(Error.price_data); 
			}
			
			// END PRICE BLOCK


			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new ServiceException(Error.product_not_available);}
			reservation.setState(Reservation.State.Provisional.name());
			
			reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
						
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}
			
			if(StringUtils.isNotEmpty(altId)){
				reservation.setAltid(altId);	
			}
			sqlSession.getMapper(ReservationMapper.class).create(reservation);
			
			ReservationService.createEvent(sqlSession, paymentTransaction, reservation, creditCard);

			// END AVAILABILITY BLOCK
			
			ManagerToGateway managerToGateway = null;
			PaymentGatewayProvider paymentGatewayProvider = null;
			Map<String, String> resultMap = null;
			int paymentGatewayId = -1;
			// Processing charge type
			String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
			Double firstPayment = PaymentHelper.getFirstPayment(reservation, propertyManagerInfo);
			Double secondPayment = PaymentHelper.getSecondPayment(reservation, propertyManagerInfo);

			response.setDownPayment(firstPayment);
			
			Double amountToCharge = PaymentHelper.isFullPaymentMethod(chargeType) ? firstPayment + secondPayment : firstPayment;
			if(PaymentHelper.isApiPaymentMethod(chargeType)) {
				if(StringUtils.isEmpty(reservation.getState())) { 
					reservation.setState(Reservation.State.Confirmed.name());
				}
				resultMap = PartnerService.createReservationAndPayment(sqlSession, reservation, creditCard);
				if (resultMap != null && PaymentHelper.isDepositPaymentMethod(chargeType) && resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
					pendingTransaction = PaymentHelper.preparePendingTransaction(sqlSession, pos, familyName, 
							firstName, cardNumber, phoneNumber, reservation, product,
							propertyManagerInfo.getPropertyManagerId(), propertyManagerInfo, secondPayment, null, resultMap, null);
					sqlSession.getMapper(PendingTransactionMapper.class).create(pendingTransaction);
					sqlSession.commit();
				}
			} else if (chargeType.equalsIgnoreCase(PaymentHelper.FULL_PAYMENT_METHOD) || chargeType.equalsIgnoreCase(PaymentHelper.DEPOSIT_PAYMENT_METHOD)) {
				/* 
				 * The following line is bad because this is the final payment. Once it was charged, we have no information about renter's CC
				 * and in case of cancellation (deposit and full payment) or second payment (deposit payment) we have no CC information about renter.
				 * Thus, there is always needed to create the payment profile in payment gateway and store returned information to database.
				 * 
				 * resultMap = handler.createPaymentByCreditCard(product.getCurrency(), firstPayment);
				 */
				
				// Payment gateway initialization
				managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(supplierId);
				paymentGatewayId = managerToGateway.getPaymentGatewayId();
				paymentGatewayProvider = PaymentGatewayHolder.getPaymentGateway(paymentGatewayId);
				handler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);

				if(!paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)) {
					resultMap = PaymentHelper.processPayment(sqlSession, paymentGatewayProvider, amountToCharge, reservation, handler, currency, creditCard, paymentGatewayId);
				}

				// Pending transaction processing
				if (resultMap != null && chargeType.equalsIgnoreCase(PaymentHelper.DEPOSIT_PAYMENT_METHOD) && resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
					pendingTransaction = PaymentHelper.preparePendingTransaction(sqlSession, pos, familyName, firstName, cardNumber, phoneNumber, reservation, product, supplierId,
							propertyManagerInfo, secondPayment, paymentGatewayProvider, resultMap, paymentGatewayId);
					
					sqlSession.getMapper(PendingTransactionMapper.class).create(pendingTransaction);
				}
			} else if(chargeType.equalsIgnoreCase(PaymentHelper.MAIL_PAYMENT_METHOD)) {
				setReservationState(reservation, chargeType);
				response.setReservationInfo(new ReservationInfo(reservation));
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				sqlSession.commit();
			} else {
				throw new ServiceException(Error.payment_method_unsupported);
			}
			
			if(propertyManagerInfo.getPropertyManagerId() != Integer.valueOf(RazorConfig.getNextpaxNovasolId())) {
				paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, firstPayment, managerToGateway, cardNumber,
							Integer.valueOf(channelPartnerParty.getId()), product, resultMap, false);
			}
			
			if(chargeType.equalsIgnoreCase(PaymentHelper.MAIL_PAYMENT_METHOD)) {
				sendEmails(sqlSession, paymentTransaction, chargeType);
				return response;
			}
			
			// END PAYMENT BLOCK
			
			if(resultMap != null && resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED) || (paymentGatewayProvider != null && paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS))) {
				if(product.hasAltpartyid() && !PaymentHelper.isApiPaymentMethod(chargeType)) {
					if(reservation.getState() == null) {
						reservation.setState(Reservation.State.Confirmed.name());
					}
					try {
						PartnerService.createReservation(sqlSession, reservation);
					} catch (ServiceException e){
						LOG.error(e.getMessage());
					}
				}
				
				MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);

				if(paymentGatewayProvider != null && paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS) ) {
					if(paymentGatewayId != -1 && paymentGatewayProvider != null && (!reservation.getState().equals(Reservation.State.Cancelled.name()))) {
						resultMap = PaymentHelper.processPayment(sqlSession, paymentGatewayProvider, amountToCharge, reservation, handler, currency, creditCard, paymentGatewayId);
						paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, firstPayment, managerToGateway, cardNumber,
								Integer.valueOf(channelPartnerParty.getId()), product, resultMap, false);
						if(resultMap == null || !resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
							LOG.error("An error occurred while processing your payment");
							PartnerService.cancelReservation(sqlSession, reservation);
							reservation.setState(Reservation.State.Cancelled.name());
							if(resultMap != null) {
								response.setErrorMessage(resultMap.get(GatewayHandler.ERROR_MSG));
							} else {
								response.setErrorMessage("Something went wrong with payment processing");
							}
						} else {
							PartnerService.confirmReservation(sqlSession, reservation);
						}
					}
				}
				
				// END PARTNER RESERVATION BLOCK
				
				// refund transaction made in case of PMS rejected the reservation
				if(paymentTransaction != null && reservation.getState() != null && reservation.getState().equals(Reservation.State.Cancelled.name())) {
					List<PaymentTransaction> paymentTransactions = new ArrayList<PaymentTransaction>();
					paymentTransactions.add(paymentTransaction);
					refundAmount(sqlSession, supplierId, paymentTransactions, firstPayment);
					cancelPendingTransactions(sqlSession, reservation);
					reservationFinished = true;
					throw new ServiceException(Error.pms_reservation_reject);
				} else if(paymentTransaction == null && paymentGatewayProvider != null && 
						paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)) {
					cancelPendingTransactions(sqlSession, reservation);
					reservationFinished = true;
					throw new ServiceException(Error.pms_reservation_reject);
				} else {
					setReservationState(reservation, chargeType);
					reservationFinished = true;
				}
				
				ReservationService.createEvent(sqlSession, paymentTransaction, reservation, creditCard);
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				// Pending transaction end
				sendEmails(sqlSession, paymentTransaction, chargeType);
				
				// END VERIFICATION BLOCK
			} else {
				reservation.setState(Reservation.State.Cancelled.name());
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				if(resultMap != null && resultMap.get(GatewayHandler.ERROR_MSG) != null) {
					LOG.error("An error occurred while processing your payment");
					response.setErrorMessage(resultMap.get(GatewayHandler.ERROR_MSG));
				} else {
					LOG.error("An error occurred while processing your payment");
					response.setErrorMessage("Something went wrong with payment processing");
				}
			}
			sqlSession.commit();
		}
		catch(ServiceException ex) {
			response.setErrorMessage(ex.getDetailedMessage());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			LOG.error(ex.getDetailedMessage());
			
			if(reservationFinished) {
				sqlSession.commit();
			} else {
				sqlSession.rollback();
			}
		}
		catch(Throwable x) {
			x.printStackTrace();
			response.setErrorMessage(x.getMessage());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			LOG.error(x.getMessage());
			
			if(reservationFinished) {
				sqlSession.commit();
			} else {
				sqlSession.rollback();
			}
		}
		finally {sqlSession.close();}

		try{
			sqlSession = RazorServer.openSession();
			if(paymentTransaction != null) {
				sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
				sqlSession.commit();
			}
		}
		catch(Throwable x) {
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage());
			sqlSession.rollback();
		}
		finally {sqlSession.close();}
		
		try {
			sqlSession = RazorServer.openSession();
			roundReservationDoubleValues(reservation);
			sendAdminEmail(sqlSession, paymentTransaction);
		} catch(Exception x) {
			if(!response.isError()) {
			response.setErrorMessage("Unable to round the values for reservation. :"+x.getMessage());
			}
			LOG.error("Unable to round the values for reservation.");
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		
		response.setReservationInfo(new ReservationInfo(reservation));
		return response;
	}

	private static void sendAdminEmail(SqlSession sqlSession, PaymentTransaction paymentTransaction) {
		if(paymentTransaction != null && (paymentTransaction.getStatus() == null || !paymentTransaction.getStatus().equals(GatewayHandler.ACCEPTED))) {
			EmailService.failedPaymentTransactionToAdmin(sqlSession, paymentTransaction);
		}
	}
	
	private static String resetValue(String value) {
		if(value != null && value.equals("0")) {
			return null;
		} else {
			return value;
		}
	}
	
	private static void cancelPendingTransactions(SqlSession sqlSession, Reservation reservation) {
		PendingTransactionMapper mapper = sqlSession.getMapper(PendingTransactionMapper.class);
		PendingTransaction pendingTransaction = mapper.readByReservation(reservation);
		if(pendingTransaction == null) {
			return;
		}
		pendingTransaction.setStatus(PendingTransactionStatus.Cancelled.status());
		mapper.update(pendingTransaction);
	}
	
	private static void sendEmails(SqlSession sqlSession, PaymentTransaction paymentTransaction, String chargeType) throws ParseException, InterruptedException {
		EmailService.reservationPaymentToRenter(sqlSession, paymentTransaction, chargeType);
		Thread.sleep(5000); // test if second email is not sending because of delay absence
		EmailService.reservationPaymentToPropertyManager(sqlSession, paymentTransaction, chargeType);
	}
	
	private static Party processCustomer(SqlSession sqlSession, String emailAddress, String familyName, String firstName, Product product, String phoneNumber, 
			Party channelPartnerParty, String organizationId, Party agent, String address, String country, String city, String zip, String state, Date birthDate) {
		  Party customer = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailAddress);
		  if(customer == null) {
		   customer = JSONService.getDetailedCustomer(sqlSession, emailAddress, familyName, firstName, 
		     product.getSupplierid(), phoneNumber, channelPartnerParty, address, country, city, zip, state, birthDate);
		  } else {
			   customer.setDayphone(phoneNumber);
			   customer.setEmailaddress(emailAddress);
			   customer.setName(familyName, firstName);
			   customer.setState(Party.CREATED);
			   customer.setBirthdate(birthDate);
			   customer.setCreatorid(agent == null ? organizationId : agent.getId());
			   customer.setCountry(country);
			   customer.setCity(city);
			   customer.setRegion(state == null || state.equals("0") ? "" : state);
			   customer.setCurrency(agent == null ? Currency.Code.USD.name() : agent.getCurrency());
			   customer.setLanguage(agent == null ? Language.EN : agent.getLanguage());
			   customer.setLocalAddress(address);
			   customer.setPostalcode(zip);
			   customer.setUsertype(Constants.RENTER_USER_TYPE);
			   sqlSession.getMapper(PartyMapper.class).update(customer);
		}
		sqlSession.commit();
		return customer;
	}
	
	private static void setReservationState(Reservation reservation, String chargeType) {
		if(PaymentHelper.isFullPaymentMethod(chargeType)){
			reservation.setState(Reservation.State.FullyPaid.name());
		} else if(PaymentHelper.isDepositPaymentMethod(chargeType)) {
			reservation.setState(Reservation.State.Confirmed.name());
		} else if(PaymentHelper.MAIL_PAYMENT_METHOD.equals(chargeType)) {
			reservation.setState(Reservation.State.Reserved.name());
		} else {
			throw new ServiceException(Error.payment_method_unsupported);
		}
	}
	
//	private static void refundAmount(SqlSession sqlSession, Integer propertyManagerId, String reservationId, Double amount) throws Exception {
//		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservationId));
//		refundAmount(sqlSession, propertyManagerId, paymentTransactions, amount);
//	}
	
	private static void refundAmount(SqlSession sqlSession, Integer propertyManagerId, List<PaymentTransaction> paymentTransactions, Double amount) throws Exception {
		if(paymentTransactions == null || paymentTransactions.isEmpty()) {
			throw new ServiceException(Error.database_cannot_find, "payment transaction for refund");
		}
		
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber(String.valueOf(paymentTransactions.get(0).getPartialIin()));
		
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(propertyManagerId);
		GatewayHandler gatewayHandler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
		
		gatewayHandler.createRefund(paymentTransactions, amount);
	}
	
	protected static synchronized CancelReservationResponse cancelBooking(String reservationPos) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		CancelReservationResponse cancelReservationResponse = new CancelReservationResponse();
		try {
			if(reservationPos == null) {
				throw new ServiceException(Error.parameter_absent, "reservationId");
			}
			
			String reservationId = Model.decrypt(reservationPos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId);
			if(reservation == null) {
				throw new ServiceException(Error.database_cannot_find, "reservation");
			}
			if(reservation.getState().equals(Reservation.State.Cancelled.name())) {
				throw new ServiceException(Error.reservation_cancelled);
			}
			
			int propertyManagerId = Integer.valueOf(reservation.getOrganizationid());
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
			if(propertyManagerInfo == null) {
				throw new ServiceException(Error.database_cannot_find, "property manager info");
			}
			
			double cancellationAmount = ReservationService.calculateCancellationAmount(sqlSession, reservation, propertyManagerInfo);
			boolean refund = cancellationAmount < 0;
			
			ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(propertyManagerId);
			Map<String, String> transactionResult = null;
			
			// initialize property manager and agent
			Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(propertyManagerInfo.getPropertyManagerId().toString());
			Party renter = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
			
			double absAmount = Math.abs(cancellationAmount);
			reservation.setState(Reservation.State.Cancelled.name());
			if(propertyManagerInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER) {
				cancelPendingTransaction(sqlSession, reservation);
				if(managerToGateway == null) {
					if(refund) {
						EmailService.cancelWithoutGatewayToPMRefund(propertyManager, renter, reservation, absAmount, propertyAddress);
					} else {
						EmailService.cancelWithoutGatewayToPMCharge(propertyManager, renter, reservation, absAmount, propertyAddress);
					}
					insertCancellationPaymentTransaction(sqlSession, cancellationAmount, managerToGateway, reservation, propertyManagerInfo, null, null, null, null, null, "");
					SharedService.onReservationStateChange(sqlSession, reservation, product);
				} else {
					List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservationId));
					CreditCard creditCard = new CreditCard();
					creditCard.setNumber(String.valueOf(paymentTransactions.get(0).getPartialIin()));
					GatewayHandler gatewayHandler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
					if(absAmount >= 1) {
						if(refund) {
							transactionResult = gatewayHandler.createRefund(paymentTransactions, absAmount);
							EmailService.cancelWithGatewayToPMRefund(propertyManager, renter, reservation, absAmount, propertyAddress);
							EmailService.cancelWithGatewayToRenterRefund(propertyManager, renter, reservation, absAmount, propertyAddress);
						} else {
							transactionResult = gatewayHandler.createPaymentByProfile(sqlSession, reservation.getCurrency(), absAmount, Integer.valueOf(reservationId));
							EmailService.cancelWithGatewayToPMCharge(propertyManager, renter, reservation, absAmount, propertyAddress);
							EmailService.cancelWithGatewayToRenterCharge(propertyManager, renter, reservation, absAmount, propertyAddress);
						}
						if(transactionResult != null) {
							insertCancellationPaymentTransaction(sqlSession, cancellationAmount, managerToGateway, reservation, propertyManagerInfo, transactionResult.get(GatewayHandler.TRANSACTION_ID),
									transactionResult.get(GatewayHandler.ERROR_MSG), Integer.valueOf(creditCard.getNumber()), null, transactionResult.get(GatewayHandler.STATE), paymentTransactions.get(0).getCurrency());
						}
					}
					// set cancelled flag to true
					for(PaymentTransaction paymentTransaction : paymentTransactions) {
						if(paymentTransaction.getStatus().equals(GatewayHandler.ACCEPTED)) {
							updateTransactionToCancelled(sqlSession, paymentTransaction);
						}
					}
					if(paymentTransactions.get(0) != null) {
						ReservationService.createEvent(sqlSession, paymentTransactions.get(0), reservation, creditCard);
					}
				}
			} else {
				EmailService.cancelPMWithoutFundsHolder(sqlSession, propertyManager, renter, reservation, propertyAddress);
				EmailService.cancelRenterWithoutFundsHolder(propertyManager, renter, reservation, propertyAddress);
				SharedService.onReservationStateChange(sqlSession, reservation, product);
			}
			
			if(transactionResult != null && transactionResult.get(GatewayHandler.ERROR_MSG) != null) {
				cancelReservationResponse.setErrorMessage(transactionResult.get(GatewayHandler.ERROR_MSG));
			}
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			if (product.hasAltpartyid()) {PartnerService.cancelReservation(sqlSession, reservation);}
		} catch(Exception e) {
			cancelReservationResponse.setErrorMessage(e.getMessage());
			LOG.error(e.getMessage());
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		return cancelReservationResponse;
	}
	
	/**
	 * Creates, updates or cancels a reservation.
	 *
	 * @param action the reservation parameter.
	 * @param pos the POS code.
	 * @param xsl the style sheet.
	 * @return the provisional reservation.
	 */
	protected static synchronized Reservation cancelUnPaidBookings (
			Reservation action,
			String pos,
			String xsl
		) {
		Date timestamp = new Date();
		String message = "POST reservation?pos=" + pos + "&xsl=" + xsl + "\nparams " + action.paramString();
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Reservation result = null;
		try {
			
			@SuppressWarnings("unused")
			String partyid = Constants.decryptPos(pos);
	
			Product product = getProduct(sqlSession, pos, action.getProductid(), License.DEFAULT_WAIT);
			if (product == null) {throw new ServiceException(Error.product_id, action.getProductid());}

			if (action.notValidState()) {throw new ServiceException(Error.reservation_state, action.getId() + " " + action.getState());}

			if (!action.noId()) { //existing reservation
				result = sqlSession.getMapper(ReservationMapper.class).read(action.getId());
			}
			

			if (result == null) {
				throw new ServiceException(Error.reservation_id, action.getId());
			}

			int propertyManagerId = Integer.valueOf(action.getOrganizationid());
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
			
			PropertyManagerCancellationRule cancellationRule = PaymentHelper.getRuleForCurrentDate(sqlSession, action, propertyManagerInfo.getPropertyManagerId());
			double cancellationTransactionFee = 0;
			if(cancellationRule != null) {
				cancellationTransactionFee = cancellationRule.getCancellationTransactionFee() == null ? 0 : cancellationRule.getCancellationTransactionFee();
				@SuppressWarnings("unused")
				double amountToRenter = PaymentHelper.getCancellationAmountWithoutFee(action, cancellationRule, action.getCurrency());
			}
			
			if(cancellationTransactionFee == 0) {
				result.setState(Reservation.State.Cancelled.name());
				sqlSession.getMapper(ReservationMapper.class).update(result);
				MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Reservation, result);
			}
			sqlSession.commit();
		}
		catch (Throwable x){
			x.printStackTrace();
			sqlSession.rollback();
			result = new Reservation();
			result.setMessage(message + "\nsetReservation failed " + x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	private static void updateTransactionToCancelled(SqlSession sqlSession, PaymentTransaction paymentTransaction) {
//		paymentTransaction.setStatus(PaymentTransaction.CANCELLED_CHARGE_TYPE);
		sqlSession.getMapper(PaymentTransactionMapper.class).update(paymentTransaction);
	}
	
	/**
	 * Availability calendar.
	 * 
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @param date the date from which the calendar is to be shown.
	 * @return Availability calendar
	 */
	protected static synchronized CalendarResponse getAvailabilityCalendar(String pos, String productid, String date) {
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (date == null || date.isEmpty() || date.length() != 10) {throw new ServiceException(Error.date_invalid, date);}

		SqlSession sqlSession = RazorServer.openSession();
		CalendarResponse response = new CalendarResponse();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid()); // this method checks agent's status and throws an exception in case of agent is inactive
//			JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setId(productid);
			action.setFromdate(date);
			JSONService.DF.parse(date);
			ArrayList<CalendarElement> items = sqlSession.getMapper(ReservationMapper.class).calendarelement(action);
			if (items != null && !items.isEmpty()) {
				CalendarElement[] array = new CalendarElement[items.size()];
				items.toArray(array);
				response.setItems(array);
			}
		}
		catch (ParseException x) {
			
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage()); 
//			throw new ServiceException(Error.date_format);
		}
		catch (Throwable x) {
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage()); 
		}
		finally {sqlSession.close();}
		return response;
	}
	
	protected static synchronized AvailabilityResponse getAvailabilityCalendar(String pos, String productid, String fromDate, String toDate) {
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (fromDate != null && fromDate.length() != 10 && !fromDate.isEmpty()) {throw new ServiceException(Error.date_invalid, fromDate);}
		if (toDate != null &&     toDate.length() != 10 && !toDate.isEmpty()  ) {throw new ServiceException(Error.date_invalid, toDate);}
		SqlSession sqlSession = RazorServer.openSession();
		AvailabilityResponse response = new AvailabilityResponse();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if(product == null ) throw new ServiceException(Error.product_id, productid);
			JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid()); // this method checks agent's status and throws an exception in case of agent is inactive
			Parameter action = new Parameter();
			action.setId(productid);
			action.setFromdate(fromDate);
			Date fromDateDate = null;
			Date toDateDate = null;
			if (fromDate == null || fromDate.isEmpty()) {
				fromDateDate = new Date();
				fromDate = JSONService.DF.format(fromDateDate);
				action.setFromdate(fromDate);
			} else {
				JSONService.DF.setLenient(false);
				fromDateDate = JSONService.DF.parse(fromDate);
			}
			if(toDate == null || toDate.isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fromDateDate);
				calendar.add(Calendar.DAY_OF_YEAR, 365);
				toDateDate = calendar.getTime();
			} else {
				toDateDate = JSONService.DF.parse(toDate);
			}
			if(fromDateDate.after(toDateDate)) {
				throw new ServiceException(Error.date_range, fromDate + " - " + toDate);
			}
			ArrayList<CalendarElement> items = sqlSession.getMapper(ReservationMapper.class).calendarelement(action);
			List<AvailabilityRange> ranges = AvailabilityRangeUtil.getAvailabilityList(items, fromDateDate, toDateDate);
			if (ranges != null && !ranges.isEmpty()) {
				AvailabilityRange[] array = new AvailabilityRange[ranges.size()];
				ranges.toArray(array);
				response.setItems(array);
				response.setProductid(productid);
			}
		}
		catch (ParseException x) {
			response.setErrorMessage(Error.date_format.getMessage());
			LOG.error(x.getMessage()); 
		}
		catch (Throwable x) {
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage()); 
		}
		finally {sqlSession.close();}
		return response;
	}
	
	/**
	 * Get quotes for a setted date
	 * 
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @param fromDateString the date from which calculate a quote
	 * @param todate the date to which calculate a quote
	 * @param currency currency to calculate
	 * @return quotes
	 */
	protected static synchronized QuoteResponse getQuotes(String pos, String productid, String fromDateString, String todate, String currency, Integer adults, Integer children) {

       if (productid == null || productid.isEmpty() ) {throw new ServiceException(Error.product_id, productid);}
		if (!Time.isDateCorrectFormat(fromDateString)) {throw new ServiceException(Error.date_from, fromDateString);}
		if (!Time.isDateCorrectFormat(todate)) {throw new ServiceException(Error.date_to, todate);}

		SqlSession sqlSession = RazorServer.openSession();
		QuoteResponse response = new QuoteResponse();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if(product == null) {
				throw new ServiceException(Error.database_cannot_find, "product with [" + productid + "] id");
			}
			if (!Product.CREATED.equals(product.getState())) {
				throw new ServiceException(Error.product_inactive, "product with [" + productid + "] id");
			}
			if(StringUtils.isEmpty(currency) || !PaymentHelper.currencyExists(currency)) {
				currency = product.getCurrency();
			}
			Party channelPartnerParty = JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid()); // this method checks agent's status and throws an exception in case of agent is inactive
			if(channelPartnerParty == null) {
				throw new ServiceException(Error.database_cannot_find, "channel partner with pos: [" + pos + "]");
			}
			product.setPhysicaladdress(ReservationService.getPropertyLocation(sqlSession, product));
			Integer propertyManagerId = Integer.valueOf(product.getSupplierid());
			if(propertyManagerId == null) {
				throw new ServiceException(Error.database_cannot_find, "property manager ID for product id [" + product.getId() + "]");
			}
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);

			if(propertyManagerInfo == null) {
				throw new ServiceException(Error.database_cannot_find, "property manager info by PM ID [" + propertyManagerId + "]");
			}

			response.setFromTime(convertTime(propertyManagerInfo.getCheckInTime()));
			response.setToTime(convertTime(propertyManagerInfo.getCheckOutTime()));

			Date fromDate = JSONService.DF.parse(fromDateString);
			Date toDate = JSONService.DF.parse(todate);
			
			if(!toDate.after(fromDate)) {
				throw new ServiceException(Error.date_range);
			}
			
			Reservation reservation = new Reservation();
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setProductid(productid);
			reservation.setFromdate(fromDate);
			reservation.setUnit(Unit.DAY);
			reservation.setTodate(toDate);
			reservation.setAltpartyid(product.getAltpartyid());
			reservation.setCurrency(currency);
			reservation.setAdult(adults);
			reservation.setChild(children);
			reservation.setAgentid(channelPartnerParty.getId());
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			
			currency = PartyService.checkMybookingpalCurrency(currency, propertyManagerInfo);
			
			ReservationPrice reservationPrice = new ReservationPrice();
			if(reservation.getAltpartyid() == null ||  !Arrays.asList(livePricingIds).contains(reservation.getAltpartyid())) {
			    
				reservationPrice = ReservationService.computePrice(sqlSession, reservation, currency);
				
				response.setDamageInsurance(round(PaymentService.convertCurrency(sqlSession, product.getCurrency(), reservation.getCurrency(), product.getSecuritydeposit() != null ? product.getSecuritydeposit() : 0), 2, BigDecimal.ROUND_HALF_UP));
				response.setCleaningFee(round(PaymentService.convertCurrency(sqlSession, product.getCurrency(), reservation.getCurrency(), product.getCleaningfee() != null ? product.getCleaningfee() : 0), 2, BigDecimal.ROUND_HALF_UP));
				
				LOG.debug("Reservation [" + reservation.getId() + "] has not altparty ID.");
			} else {
				reservationPrice = PartnerService.readPrice(sqlSession, reservation, product.getAltid(), currency);
				
				if(reservationPrice.getTotal() == null || reservationPrice.getTotal() <= 0 
						|| reservationPrice.getPrice() == null || reservationPrice.getPrice() <= 0) {
					throw new ServiceException(Error.product_not_available, "Total or Price is null or less/equals 0 returned from handler");
				}
				
				reservationPrice = ReservationService.computeLivePrice(sqlSession, reservation, reservationPrice, currency);
				
				response.setDamageInsurance(0.0);
				response.setCleaningFee(0.0);
			}			
			

			if(reservationPrice.getTotal() == null  || reservationPrice.getTotal() <= 0) {
				throw new ServiceException(Error.product_not_available, "From:"+reservation.getFromdate() + "-" + reservation.getTodate());
			}
			
			if (reservationPrice.getQuoteDetails() == null){
				reservationPrice.setQuoteDetails(new ArrayList<QuoteDetail>());
			}
			
			// add yield rules to quotes detail response
			if (reservation.getQuotedetail() != null && reservation.getQuotedetail().size() > 0){
				for(Price price : reservation.getQuotedetail()) {
					if (price.getType() != null && price.getType().equals(Price.YIELD)){
						reservationPrice.getQuoteDetails().add(new QuoteDetail(String.valueOf(price.getValue()), reservation.getCurrency(), price.getName(), Price.YIELD, "", true));
					}
				}
			}
			
			// Round response quoteDetail values to tenths
				for(QuoteDetail quoteDetail : reservationPrice.getQuoteDetails()) {
					Double amount = Double.valueOf(quoteDetail.getAmount());
					quoteDetail.setAmount(String.valueOf(round(amount, 2, BigDecimal.ROUND_HALF_UP)));
				}
			
			
			// Cancellation start

			Set<CancellationItem> cancellationItems = PaymentHelper.getCancellationItems(sqlSession, reservation);
			response.setCancellationItems(cancellationItems);
			
			// Cancellation end
			
			response.setReservationPrice(reservationPrice);
			
			String terms = propertyManagerInfo.getTermsLink() == null ? "" : propertyManagerInfo.getTermsLink();
			response.setTermsLink(terms);
			
			// Taxes calculation
			ArrayList<net.cbtltd.shared.Price> pricedetails = reservation.getQuotedetail();
			
			Double taxrate = 0.0;

			if (pricedetails != null) {
				Double tax = 0.0;
				for (net.cbtltd.shared.Price pricedetail : pricedetails) {
					pricedetail.setValue(NameId.round(pricedetail.getValue()));
					if (
							pricedetail.getValue() > 0.0 &&
							(pricedetail.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)
									|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_INCLUDED)
									|| pricedetail.hasType(net.cbtltd.shared.Price.TAX_ON_TAX))
							) {tax += pricedetail.getValue();}
				}
				taxrate = (reservation.getQuote() == null || reservation.getQuote() <= 0.0 || tax == null) ? 0.0 : tax * 100 / (reservation.getQuote() - tax);
			}
			// End taxes
			
			response.setPrice(round(reservation.getPrice(), 2, BigDecimal.ROUND_HALF_UP));
			response.setQuote(round(reservation.getQuote(), 2, BigDecimal.ROUND_HALF_UP));
			response.setCurrency(reservation.getCurrency());
			response.setTax(round(taxrate, 2, BigDecimal.ROUND_HALF_UP));
			JSONService.LOG.debug("getDeposit " + product.getSupplierid() + ", " + fromDateString);
			response.setDeposit(round(ReservationService.getDeposit(sqlSession, reservation), 2, BigDecimal.ROUND_HALF_UP)); 
			
			PropertyManagerSupportCC propertyManagerSupportCC =  sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(propertyManagerInfo.getPropertyManagerId());
			
			if(propertyManagerSupportCC == null) { 			// set all credit card types to false in case of inability to find the PropertyManagerSupportCC in DB 
				propertyManagerSupportCC = new PropertyManagerSupportCC();
				propertyManagerSupportCC.setNone(false);
				propertyManagerSupportCC.setPartyId(Integer.valueOf(product.getSupplierid()));
				propertyManagerSupportCC.setSupportAE(false);
				propertyManagerSupportCC.setSupportDINERSCLUB(false);
				propertyManagerSupportCC.setSupportDISCOVER(false);
				propertyManagerSupportCC.setSupportJCB(false);
				propertyManagerSupportCC.setSupportMC(false);
				propertyManagerSupportCC.setSupportVISA(false);
				LOG.error("cannot find propertyManagerSupportCC for PM [" + propertyManagerId + "] in database");
			}
			response.setPropertyManagerSupportCC(propertyManagerSupportCC);

			String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
			
			// Setting amounts to both of payments. If deposit is 100%, than second payment is 0.
			Double firstPayment = round(PaymentHelper.getFirstPayment(reservation, propertyManagerInfo), 2, BigDecimal.ROUND_HALF_UP);
			Double secondPayment = round(PaymentHelper.getSecondPayment(reservation, propertyManagerInfo), 2, BigDecimal.ROUND_HALF_UP);
			if(PaymentHelper.isDepositPaymentMethod(chargeType)) {
				response.setFirstPayment(firstPayment);
				DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
				response.setSecondPayment(round(PaymentHelper.getSecondPayment(reservation, propertyManagerInfo), 2, BigDecimal.ROUND_HALF_UP));
				response.setSecondPaymentDate(DF.format(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo)));
			} else {
				response.setFirstPayment(firstPayment + secondPayment);
				response.setSecondPayment(0.0);
				response.setSecondPaymentDate("");
			}
			// end setting
			
						
			// Minstay calculation block
			if (PartyIds.PARTY_INTERHOME_ID.equals(product.getSupplierid()) || PartyIds.PARTY_RENTALS_UNITED_ID.equals(product.getSupplierid())){
				MinStay minstayAction = new MinStay(product.getSupplierid(), product.getId(), fromDate, toDate, 0);					
				minstayAction = sqlSession.getMapper(PropertyMinStayMapper.class).readbyexample(minstayAction);
				if (minstayAction != null && minstayAction.getValue() != null && minstayAction.getValue() > 0){
					response.setMinstay(minstayAction.getValue());
				}
			}else {
				Price checkInPrice = null;
				Price action = new Price();
				action.setPartyid(product.getSupplierid());
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(product.getId());
				action.setDate(fromDate);
				action.setTodate(toDate);
				action.setCurrency(currency);
				if (product.getUseonepricerow() != null && product.getUseonepricerow()){
					checkInPrice = sqlSession.getMapper(PriceMapper.class).readexactmatch(action);
				}else{
					checkInPrice = sqlSession.getMapper(PriceMapper.class).getpropertydetailcheckinprice(action);
				}
				if (checkInPrice != null && checkInPrice.getMinStay() != null && checkInPrice.getMinStay() > 0){
					response.setMinstay(checkInPrice.getMinStay());
				}
			}
			// End minstay			

			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			response.setPaymentSupported(PaymentHelper.isPaymentSupported(propertyManagerInfo));
			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			response.setAvailable(available && reservation.noCollisions());
			response.setImageUrl(ImageService.getProductDefaultImageURL(sqlSession, product.getId()));
			response.setPropertyName(product.getName());
			
			if(!available) {
				throw new ServiceException(Error.product_not_available);
			}
		}
		catch (ParseException x) {
			response.setErrorMessage(x.getMessage() == null ? "Null message" : x.getMessage());
			LOG.error(x.getMessage()); 
		}
		catch (Throwable x) {
			response.setPrice(0.0);
			response.setQuote(0.0);
			response.setErrorMessage(x.getMessage() == null ? "Null message" : x.getMessage());
			LOG.error(x.getMessage()); 
		}
		finally {sqlSession.close();}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	protected static synchronized WeeklyPriceResponse getWeeklyPrices(String pos, String productid, String fromDate, String toDate, String currency) {
		SqlSession sqlSession = RazorServer.openSession();
		WeeklyPriceResponse result = new WeeklyPriceResponse();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			JSONService.getPartyWithPMCheck(sqlSession, pos, product.getSupplierid()); // this method checks agent's status and throws an exception in case of agent is inactive
			
			//Date fromDateParsed = JSONService.DF.parse(fromdate);
			//Date toDateParsed = JSONService.DF.parse(todate);
			
			Date fromDateParsed;
			if (fromDate == null || fromDate.isEmpty()) {
				fromDateParsed = new Date();
				fromDate = JSONService.DF.format(fromDateParsed);
				//action.setFromdate(fromDate);
			} else {
				JSONService.DF.setLenient(false);
				fromDateParsed = JSONService.DF.parse(fromDate);
			}
			Date toDateParsed;
			if(toDate == null || toDate.isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fromDateParsed);
				calendar.add(Calendar.DAY_OF_YEAR, 365);
				toDateParsed = calendar.getTime();
			} else {
				toDateParsed = JSONService.DF.parse(toDate);
			}
			if(fromDateParsed.after(toDateParsed)) {
				throw new ServiceException(Error.date_range, fromDate + " - " + toDate);
			}
			
			PriceWeek priceAction = new PriceWeek();
			priceAction.setFromDate(fromDateParsed);
			priceAction.setToDate(toDateParsed);
			priceAction.setId(productid);
			MinstayWeek minstayAction = new MinstayWeek();
			minstayAction.setFromDate(fromDateParsed);
			minstayAction.setToDate(toDateParsed);
			minstayAction.setEntityId(productid);
			List<WeeklyPrice> prices = sqlSession.getMapper(PriceMapper.class).getPricesByWeeks(priceAction);
			List<WeeklyMinstay> minstays = sqlSession.getMapper(PropertyMinStayMapper.class).getMinstayByWeeks(minstayAction);
			
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			currency = PartyService.checkMybookingpalCurrency(currency, propertyManagerInfo);
			if(StringUtils.isEmpty(currency) || Constants.NO_CURRENCY.equalsIgnoreCase(currency)) { currency = product.getCurrency(); }

			prices = (List<WeeklyPrice>) convertWeeklyCurrency(sqlSession, prices, currency);
			WeeklyPricesHolder holder = new WeeklyPricesHolder(prices, minstays);
			List<PriceRange> ranges = holder.getAvailalbePriceRanges();
			
			// use one price row prices
			List<Price> priceList = new ArrayList<Price>();
			if(product.IsUseonepricerow()) {
				Price priceActionForList = new Price();
				priceActionForList.setPartyid(product.getSupplierid());
				priceActionForList.setEntitytype(NameId.Type.Product.name());
				priceActionForList.setEntityid(product.getId());
				priceActionForList.setDate(fromDateParsed);
				priceActionForList.setTodate(toDateParsed);
				priceActionForList.setCurrency(currency);
				priceList = sqlSession.getMapper(PriceMapper.class).restreadLimitedRows(priceActionForList);
				priceList = (List<Price>) convertWeeklyCurrency(sqlSession, priceList, currency);
			}
			result.setPrices(priceList);
			result.setPriceRanges(ranges);
			result.setPropertyId(Integer.valueOf(product.getId()));
			result.setCurrency(currency);
		} catch (ParseException e) {
			result.setErrorMessage(Error.date_format.getMessage());
			LOG.error(Error.date_format.getMessage(), e);
		} finally {
			sqlSession.close();
		}
		return result;
	}
	
	private static List<? extends Price> convertWeeklyCurrency(SqlSession sqlSession, List<? extends Price> prices, String toCurrency) throws ParseException {
		for(Price price : prices) {
			Double amount = price.getValue();
			String fromCurrency = price.getCurrency();
			Double convertedAmount = PaymentService.convertCurrency(sqlSession, fromCurrency, toCurrency, amount);
			convertedAmount = PaymentHelper.getAmountWithTwoDecimals(convertedAmount);
			price.setValue(convertedAmount);
			price.setCurrency(toCurrency);
		}
		return prices;
	}
	
	private static Double round(double unrounded, int precision, int roundingMode) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}
	
	private static String getImageUrl(SqlSession sqlSession, Product product) {
		List<String> pictureLocations = new ArrayList<String>();
		String picture = "";
/*		if (product.hasSupplierid(HasUrls.PARTY_INTERHOME_ID)) {
			pictureLocations = sqlSession.getMapper(TextMapper.class).imageidsbyurl(new NameId(NameId.Type.Product.name(), product.getId()));
			if (pictureLocations != null && pictureLocations.size() > 0){
				picture = pictureLocations.get(0) == null ? "" : pictureLocations.get(0);
			}
		} else {*/
		pictureLocations = sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Product.name(), product.getId()));
		if (pictureLocations != null && pictureLocations.size() > 0){
//				picture = pictureLocations.get(0) == null ? "" : HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + pictureLocations.get(0);
			picture = UploadFileService.getImageUrl(sqlSession, product.getId()) + pictureLocations.get(0);
		}
		
		return picture;
	}
	
	private static String convertTime(Date time) throws ParseException {
		if(time == null || time.equals("")) {
			return "";
		}
		SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm aa");
		String parsedDate = displayFormat.format(time);
		return parsedDate;
	}
	
	private static Integer getImagesQuantity(SqlSession sqlSession, Product product) {
		List<String> pictureLocations = sqlSession.getMapper(TextMapper.class).imageidsbyurl(new NameId(NameId.Type.Product.name(), product.getId()));
		return pictureLocations == null ? 0 : pictureLocations.size();
	}
	
	public static String dateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = dateFormat.format(date);
		return stringDate;
	}
	
	private static void cancelPendingTransaction(SqlSession sqlSession, Reservation reservation) {
		PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
		if(pendingTransaction == null) {
			LOG.info("There is no pending transactions for reservation [" + reservation.getId() + "]");
			return;
		}
		pendingTransaction.setStatus(PendingTransactionStatus.Cancelled.status());
		sqlSession.getMapper(PendingTransactionMapper.class).update(pendingTransaction);
	}
	
	private static void insertCancellationPaymentTransaction(SqlSession sqlSession, double cancellationAmount, ManagerToGateway managerToGateway,
			Reservation reservation, PropertyManagerInfo propertyManagerInfo, String gatewayTransactionId, String errorMessage, Integer iin,
			Integer pmsConfirmationId, String transactionStatus, String currency) {
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		Date currentDate = new Date();
		int paymentGatewayId = managerToGateway.getPaymentGatewayId();
//		PaymentGatewayProvider paymentGatewayProvider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(paymentGatewayId);
//		double creditCardFee = cancellationAmount * (paymentGatewayProvider.getFee() / 100.);
		double creditCardFee = cancellationAmount * CommissionCalculationUtil.CREDIT_CARD_FEE; // TODO: check
		
		paymentTransaction.setChargeDate(currentDate);
		paymentTransaction.setChargeType(cancellationAmount > 0 ? PaymentTransaction.CANCELLED_CHARGE_TYPE : PaymentTransaction.REFUND_CHARGE_TYPE);
		paymentTransaction.setCreateDate(currentDate);
		paymentTransaction.setCreditCardFee(cancellationAmount > 0 ? creditCardFee : 0); // if refund than no credit card fee should be applied
		paymentTransaction.setCurrency(currency);
		paymentTransaction.setFinalAmount(cancellationAmount);
		paymentTransaction.setFundsHolder(ManagerToGateway.BOOKINGPAL_HOLDER);
		paymentTransaction.setGatewayId(paymentGatewayId);
		paymentTransaction.setGatewayTransactionId(gatewayTransactionId);
		paymentTransaction.setMessage(errorMessage);
		paymentTransaction.setPartialIin(iin);
		paymentTransaction.setPartnerId(Integer.valueOf(reservation.getAgentid()));
		paymentTransaction.setPartnerPayment(0.);
		paymentTransaction.setPaymentMethod(0);
		paymentTransaction.setPmsConfirmationId(pmsConfirmationId);
		paymentTransaction.setReservationId(Integer.valueOf(reservation.getId()));
		paymentTransaction.setServerId(1); // FIXME Hardcode
		paymentTransaction.setStatus(transactionStatus);
		paymentTransaction.setSupplierId(propertyManagerInfo.getPropertyManagerId());
		paymentTransaction.setTotalAmount(cancellationAmount);
		paymentTransaction.setTotalBookingpalPayment(0);
		paymentTransaction.setTotalCommission(0);
		paymentTransaction.setCancelled(false);
		
		sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
		
		if(transactionStatus != null && (!transactionStatus.equals(GatewayHandler.ACCEPTED))) {
			EmailService.failedCancellationToAdmin(sqlSession, paymentTransaction);
		}
	}	
	
	/**
	 * Gets the reservation key values.
	 *
	 * @param reservationid the reservation ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected Items getValues(
			String reservationid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/" + reservationid + "/values?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.RESERVATION_VALUE, reservationid, null);
			if (keyvalues == null) {throw new ServiceException(Error.reservation_value);}
			Collection<NameId> item = new ArrayList<NameId>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new NameId(args[0], args[1]));}
			}
			result = new Items(NameId.Type.Reservation.name(), reservationid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Reservation.name(), reservationid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	/**
	 * Gets the reservation key values.
	 *
	 * @param reservationid the reservation ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected KeyValues getKeyvalues(
			String reservationid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/" + reservationid + "/keyvalues?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		KeyValues result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.RESERVATION_VALUE, reservationid, null);
			if (keyvalues == null) {throw new ServiceException(Error.reservation_value);}
			Collection<KeyValue> item = new ArrayList<KeyValue>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new KeyValue(args[0], args[1]));}
			}
			result = new KeyValues(NameId.Type.Reservation.name(), reservationid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new KeyValues(NameId.Type.Reservation.name(), reservationid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
}