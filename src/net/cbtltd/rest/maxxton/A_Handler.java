/**
 * @author	Marko Ovuka
 * @version	1.00
 */

package net.cbtltd.rest.maxxton;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.maxxton.ReservationCriteria.SubjectQuantities;
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
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

/**
 * Class A_Handler manages the Maxxton API
 * 
 * @see http://rp.test.ws.newyse.com/nwsservice_roompot_acceptatie/
 * 
 * Test data: API Username = bookpal API Password = bookpal
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	//private static final QName SERVICE_NAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "NewyseWebService");
	private static final ObjectFactory OF = new ObjectFactory();
	
	//private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	// maxxton Test API access data (with credentials for testing)
	//old test credentials
	/*
	private static final String username = "bookpal";
	private static final String password = "bookpal";
	private static final String languageCode = "en";
	private static final String distributionchannelCode = "FSBUN";
	private static final String reservationCategoryCode = "touroperatorboeking";
	*/
	//new test credentials
	private static final String username = "bookingpal";
	private static final String password = "2014alpBP39";
	private static final String languageCode = "en";
	private static final String distributionchannelCode = "TOBP";
	private static final String reservationCategoryCode = "bookingpal";
	
//	private static final String RESERVATION_ACCOMMODATION_TYPE_CODE = "accommodationtype";
	
	private static final int SCHEDULES_FOR_NUMBER_DAYS_IN_FUTURE = 500;
	
	//this are maxxton types (codes) for some part of bill
	private static final Integer BILL_LINE_TYPE_LOCAL_TAXES = 10;
	private static final Integer BILL_LINE_TYPE_DEPOSIT = 50;
	
	//we will use 10 year age for child, and 40 for adults.
	//we need this to find age categories, and we do not know 
	//ages of our customer, so this are some temp value
	private static final long MAXXTON_CHILD_AGE = new Long(10);
	private static final long MAXXTON_ADULT_AGE = new Long(40);
	
	private static final String CURRENCY_IN_MAXXTON = Currency.Code.EUR.name();


	private static final String BEDROOM_CODE = "slk";
	private static final String BATHROOM_CODE = "bkr";

	NewyseWebService_Service ss = new NewyseWebService_Service();
	NewyseWebService port = ss.getNewyseWebServicePort();
	

	public A_Handler(Partner partner) {
		super(partner);
	}

	/**
	 * To access any of function in Maxxton API we need Session string.
	 * This will be create with this call.
	 * By documentation this session will not expire at least 12 hours but 
	 * it's better to create a new one for every call.  
	 * @return
	 */
	public String createSession() {
		String sessionValue = null;
		Date timestamp = new Date();
		String message = "createSession()";
//		LOG.debug(message);
		try {
			SessionCriteria sessionCriteria = new SessionCriteria();
			sessionCriteria.setUsername(username);
			sessionCriteria.setPassword(password);
			sessionCriteria.setLanguageCode(languageCode);
			sessionCriteria.setDistributionchannelCode(distributionchannelCode);

			sessionValue = port.createSession(sessionCriteria);
//			System.out.println("Session value: " + sessionValue);
		} catch (Throwable x) {
			LOG.error(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);

		return sessionValue;
	}
	

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "Maxxton isAvailable reservationId:" + reservation.getId();
		LOG.debug(message);
		try {
			String sessionKey = this.createSession();
	        Long altID = Long.valueOf(PartnerService.getProductaltid(sqlSession, reservation));

//	        LocalDate arrivalDateSearch = new DateTime(reservation.getFromdate()).toLocalDate();
//	        LocalDate departureDateSearch = new DateTime(reservation.getTodate()).toLocalDate();
//	        
	        DateTime arrivalDateSearch = new DateTime(reservation.getFromdate());
	        DateTime departureDateSearch = new DateTime(reservation.getTodate());
	        	        
	        AvailabilityCriteria inputData = OF.createAvailabilityCriteria();
	        inputData.setResourceId(OF.createAvailabilityCriteriaResourceId(altID));
	        inputData.setStartDate(OF.createAvailabilityCriteriaStartDate(getXGC(reservation.getFromdate())));
	        inputData.setEndDate(OF.createAvailabilityCriteriaEndDate(getXGC(reservation.getTodate())));
	        
	        AvailabilityContainer  availabilityContainer = port.getResourceAvailability(sessionKey, inputData);
	        List<Availability> availabilityList = availabilityContainer.getAvailabilities().availabilityItem;
	        if(availabilityList!=null && availabilityList.size()>0){
	        	/* we need to check results, because sometime in result there is no that exactly date range.
	        	 * sometimes arrivalDate is after, or departureDate is before searched date.
	        	 * this function in API return results if only one date is condition is satisfied
	        	 */
	        	 for(Availability availabilityCurrent : availabilityList){
//	        		LocalDate arrivalDateFromResult = new DateTime(getDate(availabilityCurrent.getArrivalDate())).toLocalDate();
//	    	        LocalDate departureDateFromResult = new DateTime(getDate(availabilityCurrent.getDepartureDate())).toLocalDate();
	    	        
	        		DateTime arrivalDateFromResult = new DateTime(getDate(availabilityCurrent.getArrivalDate()));
	        		DateTime departureDateFromResult = new DateTime(getDate(availabilityCurrent.getDepartureDate()));
	    	        
//	    	        LOG.debug("Is Available -- arrivalDateFromResult:"+arrivalDateFromResult.toString());
//	    	        LOG.debug("Is Available -- arrivalDateSearch:"+arrivalDateSearch.toString());
//	    	        LOG.debug("Is Available -- departureDateFromResult:"+departureDateFromResult.toString());
//	    	        LOG.debug("Is Available -- departureDateSearch:"+departureDateSearch.toString()); 
	        		//here is check is returned arrival date after searched arrival date,
	        		//or is returned departure date is before searched departure date 
	        		//in that case this condition is not good 
	        		if(DateTimeComparator.getDateOnlyInstance().compare(arrivalDateFromResult, arrivalDateSearch)==0 && 
	        				DateTimeComparator.getDateOnlyInstance().compare(departureDateFromResult, departureDateSearch)==0){
	        			isAvailableResult = true;
	        			break;
	        		}
	        		
	        		
//	        		if(arrivalDateFromResult.compareTo(arrivalDateSearch)==0 && departureDateFromResult.compareTo(departureDateSearch)==0){
//	        			isAvailableResult = true;
//	        			break;
//	        		}
	        	}
	        }
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		MonitorService.monitor(message, timestamp);
		
		return isAvailableResult;
	}
	
	private ReservationCriteria createMaxxtonReservationInputData(Reservation reservation, String productAltId, Customer maxxtonCustomer, 
			String productResortCode, String sessionKey){
		ReservationCriteria reservationInputData = OF.createReservationCriteria();
		
		Accommodation accommodationInput = OF.createAccommodation();
		accommodationInput.setArrivalDate(getXGC(reservation.getFromdate())); 			
		accommodationInput.setDuration(reservation.getDuration(Time.DAY).intValue());
		accommodationInput.setResourceId(Long.valueOf(productAltId));
		
		//we use code column for resort code
		List<Subject> agesCategoriesList = this.getAgeCategories(productResortCode, sessionKey);
		if(agesCategoriesList==null || agesCategoriesList.size()==0){
			throw new ServiceException(Error.reservation_api, reservation.getId());
		}
		Long resortAdultId = this.getMaxxtonAdultId(agesCategoriesList);
		Long resortChildId = this.getMaxxtonChildId(agesCategoriesList);
		
		if(resortAdultId==null || resortChildId==null){
			throw new ServiceException(Error.reservation_api, reservation.getId());
		}
		
		//TODO subjects are not only child and adults
		//they have many of others in every resort
		List<SubjectQuantity> subjectQuantityItemList = new ArrayList<SubjectQuantity>();
		SubjectQuantity subjectQuantityAdults = OF.createSubjectQuantity();
		subjectQuantityAdults.setSubjectId(OF.createSubjectQuantitySubjectId(resortAdultId));
		subjectQuantityAdults.setQuantity(reservation.getAdult());
		
		SubjectQuantity subjectQuantityChild = OF.createSubjectQuantity();
		subjectQuantityChild.setSubjectId(OF.createSubjectQuantitySubjectId(resortChildId));
		subjectQuantityChild.setQuantity(reservation.getChild());
		
		subjectQuantityItemList.add(subjectQuantityAdults);
		subjectQuantityItemList.add(subjectQuantityChild);
		
		SubjectQuantities subjectQuantitiesInput = new SubjectQuantities();
		subjectQuantitiesInput.subjectQuantityItem = subjectQuantityItemList;
		
		reservationInputData.setReservationCategoryCode(reservationCategoryCode);
		reservationInputData.setAccommodation(accommodationInput);
		reservationInputData.setSubjectQuantities(subjectQuantitiesInput);
		reservationInputData.setCustomerId(maxxtonCustomer.getCustomerId().getValue());
		reservationInputData.setLanguage(languageCode);
		reservationInputData.setReturnBill(true);
		
		
		return reservationInputData;
	}
	
	private ReservationPrice fillReservationPaymentDetails(SqlSession sqlSession, Reservation reservation, net.cbtltd.rest.maxxton.Reservation maxxtonReservation) throws Throwable{
		ReservationPrice reservationPrice = new ReservationPrice();		
		Date version = new Date();
		
		//setting currency Rate for convert currency if currency from reservation is not same as Maxxton default currency 
		Double currencyRate = 1.0;
		if(!reservation.getCurrency().equalsIgnoreCase(CURRENCY_IN_MAXXTON)){
			currencyRate = WebService.getRate(sqlSession, CURRENCY_IN_MAXXTON, reservation.getCurrency(), new Date());
		}
		
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		
		Double totalPriceValue = PaymentHelper.getAmountWithTwoDecimals(maxxtonReservation.getTotalPrice() * currencyRate);
		Double propertyPriceValue = PaymentHelper.getAmountWithTwoDecimals(maxxtonReservation.getAgentTotalPrice() * currencyRate);
//		Double extraPrice = PaymentHelper.getAmountWithTwoDecimals(maxxtonReservation.getCustomerTotalPrice() * currencyRate);
		
		reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
		
//		QuoteDetail propertyPriceQd = new QuoteDetail();
//		propertyPriceQd.setAmount(propertyPriceValue+"");
//		propertyPriceQd.setDescription("Property Price: "+reservation.getCurrency()+" "+propertyPriceValue);
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
		
		Double depositValue = null;
		Double localTaxesValue = null;
		
		List<ReservationBillLine> billLines = maxxtonReservation.getBillLines().getBillLineItem();
		for(ReservationBillLine billLine : billLines){
			if(billLine.getBillLineType()==BILL_LINE_TYPE_LOCAL_TAXES){
				localTaxesValue = PaymentHelper.getAmountWithTwoDecimals(billLine.getTotal() * currencyRate);
			}else if(billLine.getBillLineType()==BILL_LINE_TYPE_DEPOSIT){
				depositValue = PaymentHelper.getAmountWithTwoDecimals(billLine.getTotal() * currencyRate);
			}
		}
		
		if(localTaxesValue!=null){
			QuoteDetail localTaxesValueQd = new QuoteDetail(String.valueOf(localTaxesValue), reservation.getCurrency(), "Local taxes", "Included in the price", "", true);
			quoteDetails.add(localTaxesValueQd);
			
			net.cbtltd.shared.Price localTaxesPrice = new net.cbtltd.shared.Price();
			localTaxesPrice = new net.cbtltd.shared.Price();
			localTaxesPrice.setEntitytype(NameId.Type.Reservation.name());
			localTaxesPrice.setEntityid(reservation.getId());
			localTaxesPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
			localTaxesPrice.setName("Local Taxes");
			localTaxesPrice.setState(net.cbtltd.shared.Price.CREATED);
			localTaxesPrice.setDate(version);
			localTaxesPrice.setQuantity(1.0);
			localTaxesPrice.setUnit(Unit.EA);
			localTaxesPrice.setValue(localTaxesValue);
			localTaxesPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(localTaxesPrice);
		}
		
					
		if(depositValue!=null){
			QuoteDetail depositValueQd = new QuoteDetail(String.valueOf(depositValue), reservation.getCurrency(), "Damage Deposit Fee", "Included in the price", "", true);
			quoteDetails.add(depositValueQd);
			
			net.cbtltd.shared.Price depositPrice = new net.cbtltd.shared.Price();
			depositPrice = new net.cbtltd.shared.Price();
			depositPrice.setEntitytype(NameId.Type.Reservation.name());
			depositPrice.setEntityid(reservation.getId());
			depositPrice.setType(net.cbtltd.shared.Price.MANDATORY);
			depositPrice.setName("Damage Deposit Fee");
			depositPrice.setState(net.cbtltd.shared.Price.CREATED);
			depositPrice.setDate(version);
			depositPrice.setQuantity(1.0);
			depositPrice.setUnit(Unit.EA);
			depositPrice.setValue(depositValue);
			depositPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(depositPrice);
		}
		
		reservationPrice.setTotal(totalPriceValue);
		reservationPrice.setPrice(totalPriceValue);
		reservationPrice.setCurrency(reservation.getCurrency());
		reservationPrice.setQuoteDetails(quoteDetails);
		
		
//		reservation.setPrice(propertyPriceValue);
		reservation.setPrice(totalPriceValue);
		reservation.setQuote(totalPriceValue);
		reservation.setDeposit(0.0);
//		reservation.setExtra(extraPrice);
		reservation.setExtra(0.0);
		Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
		reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
		
		return reservationPrice;
	}
	
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productAltId, String sessionKey) throws Throwable{
		LOG.debug("Maxxton compute price");
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		Customer maxxtonCustomer = null;
		if(reservation.getCustomerid()==null){
			maxxtonCustomer = this.createTempMaxxtonCustomer(sessionKey);	
		}else{
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {
				throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());
			}
			maxxtonCustomer = this.createMaxxtonCustomer(sessionKey, customer);
		}
		
		ReservationCriteria reservationInputData = this.createMaxxtonReservationInputData(reservation, productAltId, maxxtonCustomer, product.getCode(), sessionKey);
		net.cbtltd.rest.maxxton.Reservation maxxtonReservation = port.createReservationProposal(sessionKey, reservationInputData);
		
		
		ReservationPrice result = this.fillReservationPaymentDetails(sqlSession, reservation, maxxtonReservation);
		return result;
	}
	
	
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String sessionKey = this.createSession();
		
		String message = "Maxxton readPrice productAltId:" + productAltId;
		LOG.debug(message);
		try {
			/*
			ReservationPrice result = new ReservationPrice();
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			Customer maxxtonCustomer = null;
			if(reservation.getCustomerid()==null){
				maxxtonCustomer = this.createTempMaxxtonCustomer(sessionKey);	
			}else{
				Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
				if (customer == null) {
					throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());
				}
				
				maxxtonCustomer = this.createMaxxtonCustomer(sessionKey, customer);
			}
			*/
			
			/*
			Accommodation accommodationInput = OF.createAccommodation();
			accommodationInput.setArrivalDate(getXGC(reservation.getFromdate())); 			
			accommodationInput.setDuration(reservation.getDuration(Time.DAY).intValue());
			accommodationInput.setResourceId(Long.valueOf(productAltId));
			
			//we use code column for resort code
			List<Subject> agesCategoriesList = this.getAgeCategories(product.getCode(), sessionKey);
			if(agesCategoriesList==null || agesCategoriesList.size()==0){
				throw new ServiceException(Error.reservation_api, reservation.getId());
			}
			Long resortAdultId = this.getMaxxtonAdultId(agesCategoriesList);
			Long resortChildId = this.getMaxxtonChildId(agesCategoriesList);
			
			if(resortAdultId==null || resortChildId==null){
				throw new ServiceException(Error.reservation_api, reservation.getId());
			}
			
			//subjects are not only child and adults
			//they have many for others in every resort
			List<SubjectQuantity> subjectQuantityItemList = new ArrayList<SubjectQuantity>();
			SubjectQuantity subjectQuantityAdults = OF.createSubjectQuantity();
			subjectQuantityAdults.setSubjectId(OF.createSubjectQuantitySubjectId(resortAdultId));
			subjectQuantityAdults.setQuantity(reservation.getAdult());
			
			SubjectQuantity subjectQuantityChild = OF.createSubjectQuantity();
			subjectQuantityChild.setSubjectId(OF.createSubjectQuantitySubjectId(resortChildId));
			subjectQuantityChild.setQuantity(reservation.getChild());
			
			subjectQuantityItemList.add(subjectQuantityAdults);
			subjectQuantityItemList.add(subjectQuantityChild);
			
			SubjectQuantities subjectQuantitiesInput = new SubjectQuantities();
			subjectQuantitiesInput.subjectQuantityItem = subjectQuantityItemList;
			
			ReservationCriteria reservationInputData = OF.createReservationCriteria();
			reservationInputData.setReservationCategoryCode(reservationCategoryCode);
			reservationInputData.setAccommodation(accommodationInput);
			reservationInputData.setSubjectQuantities(subjectQuantitiesInput);
			reservationInputData.setCustomerId(maxxtonCustomer.getCustomerId().getValue());
			reservationInputData.setLanguage(languageCode);
			reservationInputData.setReturnBill(true);
			*/
			
			
			/*
			Double totalPriceValue = maxxtonReservation.getTotalPrice();
			Double propertyPriceValue = maxxtonReservation.getAgentTotalPrice();
			Double extraPrice = maxxtonReservation.getCustomerTotalPrice();
			
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			
			net.cbtltd.shared.Price propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice.setEntitytype(NameId.Type.Reservation.name());
			propertyPrice.setEntityid(reservation.getId());
			propertyPrice.setType(net.cbtltd.shared.Price.RATE);
			propertyPrice.setName(net.cbtltd.shared.Price.RATE);
			propertyPrice.setState(net.cbtltd.shared.Price.CREATED);
			propertyPrice.setDate(timestamp);
			propertyPrice.setQuantity(1.0);
			propertyPrice.setUnit(Unit.EA);
			propertyPrice.setValue(totalPriceValue);
			propertyPrice.setCurrency(reservation.getCurrency());
			sqlSession.getMapper(PriceMapper.class).create(propertyPrice);
			reservation.getQuotedetail().add(propertyPrice);
			
			
			QuoteDetail propertyPriceQd = new QuoteDetail();
			propertyPriceQd.setAmount(propertyPrice+"");
			propertyPriceQd.setDescription("Property Price to rent property is:"+reservation.getCurrency()+" "+propertyPrice);
			propertyPriceQd.setIncluded(true);
			quoteDetails.add(propertyPriceQd);
			
			Double depositValue = null;
			Double localTaxesValue = null;
			
			List<ReservationBillLine> billLines = maxxtonReservation.getBillLines().getBillLineItem();
			for(ReservationBillLine billLine : billLines){
				if(billLine.getBillLineType()==BILL_LINE_TYPE_LOCAL_TAXES){
					localTaxesValue = billLine.getTotal();
				}else if(billLine.getBillLineType()==BILL_LINE_TYPE_DEPOSIT){
					depositValue = billLine.getTotal();
				}
			}
			
			if(localTaxesValue!=null){
				QuoteDetail localTaxesValueQd = new QuoteDetail();
				localTaxesValueQd.setAmount(localTaxesValue+"");
				localTaxesValueQd.setDescription("Local taxes to property is:"+reservation.getCurrency()+" "+localTaxesValue);
				localTaxesValueQd.setIncluded(false);
				quoteDetails.add(localTaxesValueQd);
				
				net.cbtltd.shared.Price localTaxesPrice = new net.cbtltd.shared.Price();
				localTaxesPrice = new net.cbtltd.shared.Price();
				localTaxesPrice.setEntitytype(NameId.Type.Reservation.name());
				localTaxesPrice.setEntityid(reservation.getId());
				localTaxesPrice.setType(net.cbtltd.shared.Price.TAX_EXCLUDED);
				localTaxesPrice.setName("Local Taxes");
				localTaxesPrice.setState(net.cbtltd.shared.Price.CREATED);
				localTaxesPrice.setDate(timestamp);
				localTaxesPrice.setQuantity(1.0);
				localTaxesPrice.setUnit(Unit.EA);
				localTaxesPrice.setValue(localTaxesValue);
				localTaxesPrice.setCurrency(reservation.getCurrency());
				sqlSession.getMapper(PriceMapper.class).create(localTaxesPrice);
				reservation.getQuotedetail().add(localTaxesPrice);
			}
			
						
			if(depositValue!=null){
				QuoteDetail depositValueQd = new QuoteDetail();
				depositValueQd.setAmount(depositValue+"");
				depositValueQd.setDescription("Deposit to property is:"+reservation.getCurrency()+" "+depositValue);
				depositValueQd.setIncluded(false);
				quoteDetails.add(depositValueQd);
				
				net.cbtltd.shared.Price depositPrice = new net.cbtltd.shared.Price();
				depositPrice = new net.cbtltd.shared.Price();
				depositPrice.setEntitytype(NameId.Type.Reservation.name());
				depositPrice.setEntityid(reservation.getId());
				depositPrice.setType(net.cbtltd.shared.Price.MANDATORY);
				depositPrice.setName("Damage Deposit Fee");
				depositPrice.setState(net.cbtltd.shared.Price.CREATED);
				depositPrice.setDate(timestamp);
				depositPrice.setQuantity(1.0);
				depositPrice.setUnit(Unit.EA);
				depositPrice.setValue(depositValue);
				depositPrice.setCurrency(reservation.getCurrency());
				sqlSession.getMapper(PriceMapper.class).create(depositPrice);
				reservation.getQuotedetail().add(depositPrice);
			}
			
			result.setTotal(totalPriceValue);
			result.setCurrency(reservation.getCurrency());
			result.setQuoteDetails(quoteDetails);
			
			reservation.setPrice(propertyPriceValue);
			reservation.setQuote(totalPriceValue);
			reservation.setDeposit(0.0);
			reservation.setExtra(extraPrice);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(reservation.getPrice() * discountfactor);

		*/
			reservation.setCurrency(currency);
			return this.computePrice(sqlSession, reservation, productAltId, sessionKey);
			
		}
		catch (Throwable x) {
			//x.printStackTrace();
			throw new ServiceException(Error.reservation_api, "Maxxton - probably readPrice request for invalid date range. Error: "+x.getMessage());
		} 

	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Maxxton createReservation " + reservation.getId();
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
//			Price price = sqlSession.getMapper(PriceMapper.class).altidfromdate(reservation); 
//			if( price == null) {throw new ServiceException(Error.price_data, reservation.getId());}

			String sessionKey = this.createSession();
			
			Customer maxxtonCustomer = this.createMaxxtonCustomer(sessionKey, customer);
			
			
			//we do not need checking one more time, because that will be done before this function call.
			//also all values necessary will be set in reservation objct already
			//this.computePrice(sqlSession, reservation, product.getAltid(), sessionKey);
			
			ReservationCriteria reservationInputData = this.createMaxxtonReservationInputData(reservation, product.getAltid(), maxxtonCustomer, product.getCode(), sessionKey);
			
			net.cbtltd.rest.maxxton.Reservation maxxtonReservation = port.confirmReservation(sessionKey, reservationInputData);
			
			Long maxxtonReservationId = maxxtonReservation.getReservationId();
			if(maxxtonReservationId==null){
				throw new ServiceException(Error.reservation_api, reservation.getId());
			}
			LOG.debug("Maxxton Reservation ID: "+maxxtonReservationId);
			
			reservation.setAltpartyid(getAltpartyid());
			reservation.setAltid(String.valueOf(maxxtonReservationId));
			reservation.setMessage(null);
			reservation.setState(Reservation.State.Confirmed.name());
			
			this.fillReservationPaymentDetails(sqlSession, reservation, maxxtonReservation);
			
			
			
//			Double depositValue = null;
//			Double localTaxesValue = null;
//			
//			List<ReservationBillLine> billLines = maxxtonReservation.getBillLines().getBillLineItem();
//			for(ReservationBillLine billLine : billLines){
//				if(billLine.getBillLineType()==BILL_LINE_TYPE_LOCAL_TAXES){
//					localTaxesValue = billLine.getTotal();
//				}else if(billLine.getBillLineType()==BILL_LINE_TYPE_DEPOSIT){
//					depositValue = billLine.getTotal();
//				}
//			}
//			
//			
//			reservation.setPrice(maxxtonReservation.getAgentTotalPrice());
//			reservation.setQuote(maxxtonReservation.getTotalPrice());
//			reservation.setExtra(maxxtonReservation.getCustomerTotalPrice());
//			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
//			reservation.setCost(reservation.getPrice() * discountfactor);
//			reservation.setCurrency(CURRENCY_IN_MAXXTON);
//			reservation.setDeposit(depositValue);
			
			
			
		}catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
	}
	
	/**
	 * Return all possible age types for that resort.
	 * Maxxton have a lot of this types, and theirs ID can be different.
	 * 
	 * @param resortCode
	 * @param sessionKey
	 */
	private List<Subject> getAgeCategories(String resortCode, String sessionKey){
		List<Subject> resultList = new ArrayList<Subject>();
//		LOG.debug("Maxxton - getAgeCategories()");
		try{
			SubjectCriteria inputData = new SubjectCriteria();
			inputData.setResortCode(resortCode);
			//they have some other types, like pets. But we need this only for persons
			inputData.setType("person");
			
			SubjectContainer subjectContainer = port.getSubjects(sessionKey, inputData);
			resultList = subjectContainer.getSubjects().getSubjectItem();
		} catch (Throwable x) {
			LOG.error(x.getMessage());
		}
		
		return resultList;
		
	}
	
	/**
	 * Return ID for adult age category
	 * @param agesCategoriesList
	 * @return Long
	 */
	private Long getMaxxtonAdultId(List<Subject> agesCategoriesList){ 
		Long resultSubjectId = null;
		for(Subject subject : agesCategoriesList){
			if( MAXXTON_ADULT_AGE >= subject.getMinAge() && (subject.getMaxAge()==null || MAXXTON_ADULT_AGE <= subject.getMaxAge())){
				resultSubjectId = subject.getSubjectId();
				break;
			}
		}
		
		return resultSubjectId;
	}
	
	/**
	 * Return ID for children age category
	 * @param agesCategoriesList
	 * @return Long
	 */
	private Long getMaxxtonChildId(List<Subject> agesCategoriesList){ 
		Long resultSubjectId = null;
		for(Subject subject : agesCategoriesList){
			if(subject.getMaxAge()!=null && MAXXTON_CHILD_AGE <= subject.getMaxAge() && MAXXTON_CHILD_AGE >= subject.getMinAge()){
				resultSubjectId = subject.getSubjectId();
				break;
			}
		}
		
		return resultSubjectId;
	}
	
	
	
	/*
	public void CreateCustomerTest(SqlSession sqlSession, String customerId){
		try{
			Party ourCustomer = sqlSession.getMapper(PartyMapper.class).read(customerId);
			String sessionKey = this.createSession();
			Customer createdCustomer = this.createCustomer(sessionKey, ourCustomer);
			System.out.println("Create customer finish.");
			
		} catch (Throwable x) {
			//x.printStackTrace();
		}
		
	}
	*/
	
	
	private Customer createMaxxtonCustomer(String sessionKey, Party ourCustomer) throws NewyseWebserviceException{
						
		Customer customerInputData = OF.createCustomer();
		if(!ourCustomer.noFirstName()){customerInputData.setFirstname(OF.createCustomerFirstname(ourCustomer.getFirstName()));}
		if(!ourCustomer.noFamilyName()){customerInputData.setLastname(OF.createCustomerLastname(ourCustomer.getFamilyName()));}
		if(ourCustomer.getBirthdate()!=null){customerInputData.setBirthDate(OF.createCustomerBirthDate(getXGC(ourCustomer.getBirthdate())));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getDayphone())){customerInputData.setWorkPhone(OF.createCustomerWorkPhone(ourCustomer.getDayphone()));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getNightphone())){customerInputData.setPrivatePhone(OF.createCustomerPrivatePhone(ourCustomer.getNightphone()));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getMobilephone())){customerInputData.setMobilePhone(OF.createCustomerMobilePhone(ourCustomer.getMobilephone()));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getFaxphone())){customerInputData.setFax(OF.createCustomerFax(ourCustomer.getFaxphone()));}
		
		if(!checkIfValueNullOrEmpty(ourCustomer.getPostalcode())){customerInputData.setZipcode(OF.createCustomerZipcode(ourCustomer.getPostalcode()));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getCity())){customerInputData.setCity(OF.createCustomerCity(ourCustomer.getCity()));}
		if(!ourCustomer.noCountry()){customerInputData.setCountry(OF.createCustomerCountry(ourCustomer.getCountry()));}
		if(!checkIfValueNullOrEmpty(ourCustomer.getLocalAddress())){customerInputData.setAddress1(ourCustomer.getLocalAddress());}
		if(!ourCustomer.noEmailaddress()){customerInputData.setEmail(OF.createCustomerEmail(ourCustomer.getEmailaddress()));}
		
		Customer createdCustomer = port.createCustomer(sessionKey, customerInputData);
		
		
		return createdCustomer;
	}
	
	private Customer createTempMaxxtonCustomer(String sessionKey) throws NewyseWebserviceException{
		
		Customer customerInputData = OF.createCustomer();
		Customer createdCustomer = port.createCustomer(sessionKey, customerInputData);

		return createdCustomer;
	}
	
	private boolean checkIfValueNullOrEmpty(String value){
		boolean result = false;
		if(value==null || value.isEmpty()){
			result = true;
		}
			
		return result;
	}
	
	

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Maxxton confirmReservation()");


	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "Maxxton readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Maxxton readReservation()");
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
		
		//as we have readSchecule() method we do not need this
		/*
		Date timestamp = new Date();
		String message = "readReservation maxxton" + reservation.getAltid();
		LOG.debug(message);
		try {
			String sessionKey = this.createSession();
			
			
			GetReservation getReservationInput = OF.createGetReservation();
			getReservationInput.setReservationId(Long.parseLong(reservation.getAltid()));
			getReservationInput.setReturnResource(true);
			//set if you want bill, resource, customer and info values
			
			ReservationContainer maxxtonReservationContainer = port.getReservation(sessionKey, getReservationInput);
			if (maxxtonReservationContainer == null || maxxtonReservationContainer.getReservations().getReservationItem()==null || maxxtonReservationContainer.getReservations().getReservationItem().size()==0) {
				throw new ServiceException(Error.reservation_id, reservation.getAltid() + " invalid Reservation ID.");
			}
					
			net.cbtltd.rest.maxxton.Reservation maxxtonReservation = maxxtonReservationContainer.getReservations().getReservationItem().get(0);
			String altIdReservation = String.valueOf(maxxtonReservation.getReservationId());
			reservation = sqlSession.getMapper(ReservationMapper.class).altread(new NameId(getAltpartyid(), altIdReservation));
			if (reservation == null) {throw new ServiceException(Error.reservation_altid, altIdReservation);}
			
			if(maxxtonReservation.getReservedResources()==null || maxxtonReservation.getReservedResources().getReservedResourceItem()==null || maxxtonReservation.getReservedResources().getReservedResourceItem().size()==0){
				throw new ServiceException(Error.reservation_bad, altIdReservation);
			}
			
			//find product
			List<ReservedResource> reservationResourceList = maxxtonReservation.getReservedResources().getReservedResourceItem();
			for (ReservedResource reservationResource : reservationResourceList){
				if(reservationResource.getType().equalsIgnoreCase(RESERVATION_ACCOMMODATION_TYPE_CODE)){
					String altIdProduct = String.valueOf(reservationResource.getResourceId());
					Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altIdProduct));
					if (product == null) {throw new ServiceException(Error.product_altid, altIdProduct);}
					break;
				}
			}
			
			
			
			reservation.setDate(getDate(maxxtonReservation.getBookDate()));
			reservation.setFromdate(getDate(maxxtonReservation.getArrivalDate()));
			reservation.setTodate(getDate(maxxtonReservation.getDepartureDate()));
			
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			if (reservation.hasCollisions()) {throw new ServiceException(Error.reservation_altid, altIdReservation);}
			
			LOG.debug("readReservation " + reservation);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
		*/

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Maxxton updateReservation()");
	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Maxxton cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			String sessionKey = this.createSession();
			
			GetReservation getReservationInput = OF.createGetReservation();
			getReservationInput.setReservationId(Long.parseLong(reservation.getAltid()));
			
			//maxxton have two different method for canceling reservation: declineReservation and cancelReservation
			//cancelReservation is used only if payment confirm (via API). But we will not use this confirmation,
			//so for canceling we use declineReservation. This success depends on our agreement with that resort.
			
			//TODO if this do not success (if reservation is already declined this will go to catch block automatically. 
			//Check what should I do)
			ReservationContainer maxxtonReservationContainer = port.declineReservation(sessionKey, getReservationInput);
			if (maxxtonReservationContainer == null || maxxtonReservationContainer.getReservations().getReservationItem()==null 
					|| maxxtonReservationContainer.getReservations().getReservationItem().size()==0) {
				throw new ServiceException(Error.reservation_api, "Cancelling reservation - altId " + reservation.getAltid() + " - invalid Reservation ID or reservation was already cancelled.");
			}
					
			net.cbtltd.rest.maxxton.Reservation maxxtonReservation = maxxtonReservationContainer.getReservations().getReservationItem().get(0);
			
			if(!maxxtonReservation.getStatus().equalsIgnoreCase("DECLINED")){
				throw new ServiceException(Error.reservation_api, reservation.getAltid() + " reservation is not canceled.");
			}
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		
		MonitorService.monitor(message, timestamp);
		
	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.service_absent, "Maxxton readAlerts()");
	}

	
	@Override
	public void readPrices() {
		/**
		 * Main property prices will be entered in readSchedule function. 
		 * So we do not need to do this here. 
		 */

	}
	
	
	@Override
	public void readImages() {
		Date timestamp = new Date();
		String message = "readImages Maxxton";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			String sessionKey = this.createSession();			
			ResortCriteria resortCriteria = new ResortCriteria();
			resortCriteria.setIgnoreRentable(false);

			ResortContainer resorts = port.getResorts(sessionKey, resortCriteria);
			List<Resort> resortList = resorts.getResorts().getResortItem();
			
			for(Resort resortCurrent : resortList){
				AccommodationTypeCriteria accommodationTypeInputData = new AccommodationTypeCriteria();
				accommodationTypeInputData.setResortCode(OF.createAccommodationTypeCriteriaResortCode(resortCurrent.getCode()));
				accommodationTypeInputData.setOnlyBookable(OF.createAccommodationTypeCriteriaOnlyBookable(true)); 

				AccommodationTypeContainer accomodationTypes = port.getAccommodationTypes(sessionKey, accommodationTypeInputData);
				List<AccommodationType> accomodationTypeList = accomodationTypes.getAccommodationTypes().getAccommodationTypeItem();
				
				for(AccommodationType accommodationCurrent : accomodationTypeList){
				
					String altId = String.valueOf(accommodationCurrent.getResourceId().getValue());
					try {
						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
						if (product == null) {continue;}
						
						if(accommodationCurrent.getImageManagerId().getValue()!=null){
							ImageCriteria imageCriteria = new ImageCriteria();
							imageCriteria.setImageManagerId(accommodationCurrent.getImageManagerId().getValue());
							imageCriteria.setOnlyImageUrl(OF.createImageCriteriaOnlyImageUrl(true));
							ImageContainer imagesOfAccomm = port.getImages(sessionKey, imageCriteria);
							List<Image> imagesAccommList = imagesOfAccomm.getImages().getImageItem();
							
							
							if (HasUrls.LIVE && imagesAccommList != null && imagesAccommList.size()>0) {
								ArrayList<NameId> images = new ArrayList<NameId>();
								for (Image image : imagesAccommList) {
									images.add(new NameId(image.getFilename(), image.getImageUrl()));
								}
								LOG.debug("IMAGES " + images);
								UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
							}
						}
						
						sqlSession.commit();
					}
					catch (Throwable x) {
						LOG.error(Error.product_data.name() + altId); 
						x.printStackTrace();
					}
				}
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
		
	}
	

	@Override
	public synchronized void readProducts() {
		Date timestamp = new Date();
		StringBuilder sb = new StringBuilder();
		String message = "readProducts Maxxton ";
		LOG.debug(message);
		
		Date version = new Date();
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			
			String sessionKey = this.createSession();			
			ResortCriteria resortCriteria = new ResortCriteria();
			resortCriteria.setIgnoreRentable(false);

			ResortContainer resorts = port.getResorts(sessionKey, resortCriteria);
			List<Resort> resortList = resorts.getResorts().getResortItem();
			
			for(Resort resortCurrent : resortList){
				System.out.println("Resort code: " + resortCurrent.getCode());
				AddressCriteria addressInputData = new AddressCriteria();
				addressInputData.setAddressManagerId(resortCurrent.getVisitaddressManagerId());
				Address visitAddress = port.getAddress(sessionKey, addressInputData);
				
				Double latitude = visitAddress.getLatitude().getValue();
				Double longitude = visitAddress.getLongitude().getValue();
				String zipCode = (visitAddress.getZipcode() != null) ? visitAddress.getZipcode().getValue() : null;
				String country = (visitAddress.getCountry().getValue() != null) ? visitAddress.getCountry().getValue().getName() : null;
				String city = (visitAddress.getCity().getValue() != null) ? visitAddress.getCity().getValue() : null;
				String countryISO = (visitAddress.getCountry().getValue() != null) ? visitAddress.getCountry().getValue().getCode() : null;
				
			    // this is their second address
			//	addressInputData.setAddressManagerId(resortCurrent.getMailaddressManagerId());
			//	Address mailAddress = port.getAddress(sessionKey, addressInputData);
				
				StringBuilder physicalAddress = new StringBuilder();
				
				if (visitAddress.getAddress1() != null) {physicalAddress.append(visitAddress.getAddress1()).append("\n");}
				if (visitAddress.getHousenumber() != null) {physicalAddress.append(visitAddress.getHousenumber()).append("\n");}
				if (visitAddress.getZipcode().getValue() != null) {physicalAddress.append(visitAddress.getZipcode().getValue()).append("\n");}
				if (visitAddress.getCity().getValue() != null) {physicalAddress.append(visitAddress.getCity().getValue()).append("\n");}
				if (visitAddress.getCountry().getValue() != null) {physicalAddress.append(visitAddress.getCountry().getValue().getName()).append("\n");}
				
				//System.out.println("Address: " + physicalAddress.toString());
				
				
				/*
				System.out.println("VISIT ADDRESS");
				System.out.println("Address1: " + ((visitAddress.getAddress1() != null)?visitAddress.getAddress1():""));
				System.out.println("Housenumber: " + ((visitAddress.getHousenumber() != null)?visitAddress.getHousenumber():""));
				System.out.println("Address2: " + ((visitAddress.getAddress2() != null)?visitAddress.getAddress2():""));
				System.out.println("ZIP Code: " + ((visitAddress.getZipcode().getValue() != null)?visitAddress.getZipcode().getValue():""));
				System.out.println("City: " + ((visitAddress.getCity().getValue() != null)?visitAddress.getCity().getValue():""));
				System.out.println("Latitude: " + ((visitAddress.getLatitude().getValue() != null)?visitAddress.getLatitude().getValue():""));
				System.out.println("Longitude: " + ((visitAddress.getLongitude().getValue() != null)?visitAddress.getLongitude().getValue():""));
				System.out.println("Email : " + ((visitAddress.getEmail().getValue() != null)?visitAddress.getEmail().getValue():""));
				System.out.println("MobilePhone: " + ((visitAddress.getMobilePhone().getValue() != null)?visitAddress.getMobilePhone().getValue():""));
				System.out.println("PrivatePhone: " + ((visitAddress.getPrivatePhone().getValue() != null)?visitAddress.getPrivatePhone().getValue():""));
				System.out.println("WorkPhone: " + ((visitAddress.getWorkPhone().getValue() != null)?visitAddress.getWorkPhone().getValue():""));
				System.out.println("PoBox: " + ((visitAddress.getPoBox().getValue() != null)?visitAddress.getPoBox().getValue():""));
				System.out.println("PoBoxZipcode: " + ((visitAddress.getPoBoxZipcode().getValue() != null)?visitAddress.getPoBoxZipcode().getValue():""));
				System.out.println("District: " + ((visitAddress.getDistrict().getValue() != null)?visitAddress.getDistrict().getValue():""));
				
				System.out.println();
				
				System.out.println("MAIL ADDRESS");
				System.out.println("Address1: " + ((mailAddress.getAddress1() != null)?mailAddress.getAddress1():""));
				System.out.println("Housenumber: " + ((mailAddress.getHousenumber() != null)?mailAddress.getHousenumber():""));
				System.out.println("Address2: " + ((mailAddress.getAddress2() != null)?mailAddress.getAddress2():""));
				System.out.println("ZIP Code: " + ((mailAddress.getZipcode().getValue() != null)?mailAddress.getZipcode().getValue():""));
				System.out.println("City: " + ((mailAddress.getCity().getValue() != null)?mailAddress.getCity().getValue():""));
				System.out.println("Latitude: " + ((mailAddress.getLatitude().getValue() != null)?mailAddress.getLatitude().getValue():""));
				System.out.println("Longitude: " + ((mailAddress.getLongitude().getValue() != null)?mailAddress.getLongitude().getValue():""));
				System.out.println("Email : " + ((mailAddress.getEmail().getValue() != null)?mailAddress.getEmail().getValue():""));
				System.out.println("MobilePhone: " + ((mailAddress.getMobilePhone().getValue() != null)?mailAddress.getMobilePhone().getValue():""));
				System.out.println("PrivatePhone: " + ((mailAddress.getPrivatePhone().getValue() != null)?mailAddress.getPrivatePhone().getValue():""));
				System.out.println("WorkPhone: " + ((mailAddress.getWorkPhone().getValue() != null)?mailAddress.getWorkPhone().getValue():""));
				System.out.println("PoBox: " + ((mailAddress.getPoBox().getValue() != null)?mailAddress.getPoBox().getValue():""));
				System.out.println("PoBoxZipcode: " + ((mailAddress.getPoBoxZipcode().getValue() != null)?mailAddress.getPoBoxZipcode().getValue():""));
				System.out.println("District: " + ((mailAddress.getDistrict().getValue() != null)?mailAddress.getDistrict().getValue():""));
				*/
				
				//creating location
				Location location = null;
				String region = null;
				
				if(!(latitude == null || longitude == null)){
					location = PartnerService.getLocation(sqlSession, city, region, countryISO, latitude, longitude);
				}else{
					location = PartnerService.getLocation(sqlSession, city, region, countryISO);
				}
				if(location==null){
					sb.append("\n").append("Maxxton Resort: " + resortCurrent.getCode() + " country: " + country + " zip: " + zipCode + " city: " + visitAddress.getCity().getValue());
				}
				
				
				//for each resort get all resources (accommodation type) - this will be saved in our database.
				AccommodationTypeCriteria accommodationTypeInputData = new AccommodationTypeCriteria();
				accommodationTypeInputData.setResortCode(OF.createAccommodationTypeCriteriaResortCode(resortCurrent.getCode()));
				accommodationTypeInputData.setOnlyBookable(OF.createAccommodationTypeCriteriaOnlyBookable(true)); 

				AccommodationTypeContainer accomodationTypes = port.getAccommodationTypes(sessionKey, accommodationTypeInputData);
				List<AccommodationType> accomodationTypeList = accomodationTypes.getAccommodationTypes().getAccommodationTypeItem();
				
				for(AccommodationType accommodationCurrent : accomodationTypeList){
					//System.out.println("Accommodation name: " + accommodationCurrent.getName().getValue());
					String altId = String.valueOf(accommodationCurrent.getResourceId().getValue());
					
					try{
						//TODO check with Pavel
						//if product was inserted before with location == null product will have state suspended.
						//but than this method will return null and we will not be able to update that product
						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId);
						if (product == null) {
							continue;
						}
						
						//getting list of all real object with that type of accommodation for Quantity 
						ObjectCriteria objectCriteria = new ObjectCriteria();
						objectCriteria.setResourceId(accommodationCurrent.getResourceId());
						ObjectContainer objectsOfAccomm = port.getObjects(sessionKey, objectCriteria);
						List<Object> accommObjectList = objectsOfAccomm.getObjects().getObjectItem();
						
						product.setSupplierid(getAltpartyid());
						product.setAltpartyid(getAltpartyid());	
						product.setAltSupplierId(getAltpartyid());	
						//resort code is set in property code (as we do not have some field for this) 
						product.setCode(accommodationCurrent.getResortCode().getValue());
						product.setPerson(accommodationCurrent.getNumberOfPersons());
						product.setPhysicaladdress(physicalAddress.toString());
						
						//pretty name
						String propertyName = "";
						if(accommodationCurrent.getKindCode().getValue()!=null){
							propertyName += getMaaxtonPropertyTypeName(accommodationCurrent.getKindCode().getValue());
						}
						if(accommodationCurrent.getName().getValue()!=null){
							propertyName += " - Type " + accommodationCurrent.getName().getValue();
							
						}
						product.setName(propertyName);
						
						if(visitAddress.getLatitude().getValue()!=null){product.setLatitude(visitAddress.getLatitude().getValue());}
						if(visitAddress.getLongitude().getValue()!=null){product.setLongitude(visitAddress.getLongitude().getValue());}
						product.setType(Product.Type.Accommodation.name());
						product.setCurrency(CURRENCY_IN_MAXXTON);
						product.setUseonepricerow(true);
						product.setVersion(version);
						
						product.setUnit(Unit.DAY);
						product.setQuantity(accommObjectList.size());
						product.setRank(0.0);
						product.setRating(5);
						product.setAltitude(0.0);
						product.setWebaddress(getWebaddress());
						product.setCommission(getCommission());
						product.setDiscount(getDiscount());
						product.setCleaningfee(0.0);
						product.setSecuritydeposit(0.0);
						
						if(location!=null){
							product.setLocationid(location.getId());
						}else{
							product.setState(Product.SUSPENDED);
						}
						
						
						ArrayList<String> attributes = new ArrayList<String>();
						addType(attributes, accommodationCurrent.getKindCode().getValue());
						
						StringBuilder description = new StringBuilder();
						description.append(accommodationCurrent.getShortDescription().getValue()).append("\n");
						description.append(accommodationCurrent.getDescription().getValue()).append("\n");
						description.append(accommodationCurrent.getDescription2().getValue()).append("\n");
						
						
						//reading and setting product (resource) attributes.
						if(accommodationCurrent.getPropertyManagerId().getValue()!=null){
							PropertyCriteria propertyCriteria = OF.createPropertyCriteria();
							propertyCriteria.setPropertyManagerId(accommodationCurrent.getPropertyManagerId().getValue());
							propertyCriteria.setIncludePartial(OF.createPropertyCriteriaIncludePartial(false)); 
							
							PropertyContainer propertiesResponse = port.getProperties(sessionKey, propertyCriteria);
							List<Property> propertyAttributeList = propertiesResponse.getProperties().getPropertyItem();
							for (Property propertyAttribute : propertyAttributeList){
								//here will be also number of rooms ant bathrooms, so we need to extract that information
								if(propertyAttribute.getCode().equalsIgnoreCase(BEDROOM_CODE)){
									product.setRoom(Integer.parseInt(propertyAttribute.getValue().getValue()));
								}else if(propertyAttribute.getCode().endsWith(BATHROOM_CODE)){
									String tempBathroomNumber = propertyAttribute.getCode().substring(0, propertyAttribute.getCode().indexOf(BATHROOM_CODE));
									product.setBathroom(Integer.parseInt(tempBathroomNumber));
								}else{
									addAttribute(attributes, propertyAttribute.getCode());
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
						//wait(getProductwait());	
						
					}
					catch (Throwable x) {
						LOG.error(Error.product_data.name() + altId);
						x.printStackTrace();
					}
					
				}		
				
			}
			
			//canceling product which are not updated 
			Product action = new Product();
			action.setAltpartyid(getAltpartyid());
			action.setState(Product.CREATED);
			action.setVersion(version); 
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
			sqlSession.commit();
			
			LOG.debug("Maxxton - Missing Places: " + sb.toString());

		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}
	

	@Override
	public void readSchedule() {
		String sessionKey = this.createSession();
		long startTime = System.currentTimeMillis();
		Date version = new Date();
		
		Date startDate = Time.getDateStart();
		Date endDate = Time.addDuration(startDate, SCHEDULES_FOR_NUMBER_DAYS_IN_FUTURE, Time.DAY);
		String message = "Maxxton readSchedule (+ readPrice)";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			//first get all available resources (accommodation types)
			AccommodationTypeCriteria accommodationTypeInputData = new AccommodationTypeCriteria();
			AccommodationTypeContainer accomodationTypes = port.getAccommodationTypes(sessionKey, accommodationTypeInputData);
			List<AccommodationType> accomodationTypeList = accomodationTypes.getAccommodationTypes().getAccommodationTypeItem();
			
			if(accomodationTypeList!=null){
				for(AccommodationType accommodationCurrent : accomodationTypeList){
					String altId = String.valueOf(accommodationCurrent.getResourceId().getValue());
					//System.out.println("Product ID: " + altId + ", " + (new Date()).toString());
					SortedSet<DateTime> accommodationAvailableDates = new TreeSet<DateTime>();
					
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altId, false);
					//do not have the product in mybookingpal move on.
					if (product == null) {
						LOG.error(Error.product_altid.getMessage() + ", Product not exsit, altId:" + altId);
						continue;
					}
					
					
					/* now with same function we get availability and prices.
					 * Prices we entered for every period, 
					 * but from availability we need to find not available dates.
					 * also some product can have special prices (with discount), 
					 * usually for last minute booking. In that case we can use that
					 * price for our customer (and they will not need to have special code,
					 * because in createReservation customer will get that discount price anyway).  
					 */
					
					String inputDataForResourceAvailabilityString = "";
					Long startTimeGetResourceAvailability = new Long(0);
					//TODO this try is because of errors 
					//[java] Caused by: java.net.SocketException: SocketException invoking http://rp.test.ws.newyse.com/nwsservice_roompot_acceptatie/nwsws/newyseservice: Unexpected end of file from server
					try{
						AvailabilityCriteria inputData = OF.createAvailabilityCriteria();
				        inputData.setResourceId(accommodationCurrent.getResourceId());
				        inputData.setStartDate(OF.createAvailabilityCriteriaStartDate(getXGC(startDate)));
				        inputData.setEndDate(OF.createAvailabilityCriteriaEndDate(getXGC(endDate)));
				        inputDataForResourceAvailabilityString = "resourceId:"+accommodationCurrent.getResourceId().getValue() + ", startDate:"+getXGC(startDate) + ", endDate:"+getXGC(endDate);
				        
				        startTimeGetResourceAvailability = (new Date()).getTime();
				        
				        AvailabilityContainer  availabilityContainer = port.getResourceAvailability(sessionKey, inputData);
				        Long endTimeGetResourceAvailability = (new Date()).getTime();
				        Long durationOfGetResourceAvailability = endTimeGetResourceAvailability - startTimeGetResourceAvailability;
				        LOG.debug("Duration of getResourceAvailability for altID:" + altId + " is:"+durationOfGetResourceAvailability.toString());
				        List<Availability> availabilityList = availabilityContainer.getAvailabilities().availabilityItem;
				        
				        ArrayList<Price> pricesFromApi = new ArrayList<Price>();
				        
				        if(availabilityList!=null){
					        for(Availability availability: availabilityList){
					        	//here we adding current availability date to set of all accommodation availability dates
					        	//later from here we will find reserved dates for that accommodation.
					        	addToAvailableDates(accommodationAvailableDates, getDate(availability.getArrivalDate()), getDate(availability.getDepartureDate()));
					        	
					        	//now we enter prices
					        	if(availability.getPrices().getPriceItem()!=null && availability.getPrices().getPriceItem().size()>0 && 
					        			availability.getPrices().getPriceItem().get(0)!=null){
					        		net.cbtltd.rest.maxxton.Price priceMaxxtonItem = availability.getPrices().getPriceItem().get(0); 
						        	int daysBetween = Days.daysBetween(new DateTime(getDate(priceMaxxtonItem.getArrivalDate())), new DateTime(getDate(priceMaxxtonItem.getDepartureDate()))).getDays();
						        	
									Price price = new Price();
									price.setPartyid(getAltpartyid());
									price.setEntitytype(NameId.Type.Product.name());
									price.setEntityid(product.getId());
									price.setCurrency(CURRENCY_IN_MAXXTON);
									price.setQuantity(Double.valueOf(priceMaxxtonItem.getQuantity()));
									price.setMinStay(daysBetween);
									price.setMaxStay(daysBetween);
									price.setUnit(Unit.DAY);
									price.setName(Price.RACK_RATE);
									price.setType(NameId.Type.Reservation.name()); 
									price.setDate(getDate(priceMaxxtonItem.getArrivalDate()));
									price.setTodate(getDate(priceMaxxtonItem.getDepartureDate()));
									
//									net.cbtltd.shared.Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
//									if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
//									else {price = exists;}
									
									Double maxxtonPropertyPrice = priceMaxxtonItem.getPrice();
									//if special price exist we can use this price as regular
									if(priceMaxxtonItem.getSpecial()!=null){
										System.out.println("###############################Special prace reached, altId: " +altId);
										maxxtonPropertyPrice = priceMaxxtonItem.getSpecial().getSpecialPrice();
									}						
									
											
									price.setFactor(1.0);
									price.setOrganizationid(getAltpartyid());
									price.setRule(Price.Rule.FixedRate.name());
									price.setAvailable(priceMaxxtonItem.getQuantity());
									price.setState(Price.CREATED);
									double unitprice = maxxtonPropertyPrice/Double.valueOf(daysBetween);
									price.setValue(NameId.round(Double.valueOf(maxxtonPropertyPrice)/Double.valueOf(daysBetween)));
									//price.setValue(Double.valueOf(maxxtonPropertyPrice));
									price.setMinimum(Double.valueOf(maxxtonPropertyPrice));
									price.setCost(unitprice);
//									price.setVersion(version);
																						
									
//									sqlSession.getMapper(PriceMapper.class).update(price);
//									sqlSession.getMapper(PriceMapper.class).cancelversion(price);
//									sqlSession.commit();
									
									pricesFromApi.add(price);
					        	}
					        	
					        }

					        this.createSchedule(accommodationAvailableDates, product, version, sqlSession);
					        
					      //  wait(getSchedulewait());
					        
				        } else{
				        	//case when there is not availability list
				        	//create one big reservation
				        	PartnerService.createSchedule(sqlSession, product, startDate, endDate, version);
				        	PartnerService.cancelSchedule(sqlSession, product, version);
				    		sqlSession.commit();
				        }
				        
				        //update price in DB
				        PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
				        sqlSession.commit();
				        
					}catch (Throwable x) {
						Long endTimeGetResourceAvailability = (new Date()).getTime();
				        Long durationOfGetResourceAvailability = endTimeGetResourceAvailability - startTimeGetResourceAvailability;
				        LOG.debug("Duration when ERROR of getResourceAvailability for altID:" + altId + " is:"+durationOfGetResourceAvailability.toString());
				        
						sqlSession.rollback();
						LOG.debug("Maxxton getResourceAvailability Input data - "+inputDataForResourceAvailabilityString);
						LOG.error(x.getMessage());
						x.printStackTrace();
					}
			        
				}
			}
			
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		} finally {
			sqlSession.close();
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for readSchedule execution: " + (endTime - startTime));
		MonitorService.monitor(message, version );

	}
	
	/**
	 * Adding arrival - departure date range in dates list
	 * @param availableDates
	 * @param arrivalDate
	 * @param departureDate
	 */
	private void addToAvailableDates(SortedSet<DateTime> availableDates, Date arrivalDate, Date departureDate){
		int daysBetween = Days.daysBetween(new DateTime(arrivalDate), new DateTime(departureDate)).getDays();
		DateTime startDate = new DateTime(arrivalDate);
		for (int i=0; i <= daysBetween; i++) {
			availableDates.add(startDate.withFieldAdded(DurationFieldType.days(), i));
		}
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
			PartnerService.createSchedule(sqlSession, product, currentDate.toDate(), firstAvailableDate.withFieldAdded(DurationFieldType.days(),-1).toDate(), version);
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
	
	
	/**
	 * Maxxton have specials codes. They have 2 kinds of this codes. With regular request getResourceAvailability
	 * We get special codes which we can use. These are codes mainly for last minute booking, and there is not
	 * meeter is that code use in booking (createReservation) we will get that property with special price in any case.
	 * So we do not need to save that codes, and that price will be saved in function createSchedule. 
	 * Second kind of specials code which will be get with attribute IncludeHiddenSpecials=TRUE in getResourceAvailability requst
	 * we can not use, so we do not need to save this codes (customer will need to know that codes in booking process,
	 * adn we will need to change our booking frontend). So this function is not neccessary.    
	 */
	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Maxxton readSpecials()");

	}

	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Maxxton readDescriptions()");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Maxxton createReservationAndPayment()");
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
			//GregorianCalendar c = xgc.toGregorianCalendar();
			//Date fecha = c.getTime();
			//java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
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
		xgc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
//		Calendar c = xgc.toGregorianCalendar();
//	    Date date = c.getTime();
//	    return date;
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
			TYPES.put("sc","PCT25"); //Mobilhome
			TYPES.put("bg","PCT5"); //bungalow (on their system) - Cabin or Bungalow (in ours)
			TYPES.put("be","PCT5"); //bungalow eigenaar (on their system) - Cabin or Bungalow (in ours)
			TYPES.put("log","PCT22"); //loge (on their system) - Lodge (in ours)
			TYPES.put("beachhouse","PCT35"); //Beach Houses - Villa (in ours)
			TYPES.put("vt","PCT33"); //holidaytent (on their system) - Tent (in ours)
			TYPES.put("ho","PCT20"); //hotel
			TYPES.put("wcc","PCT34"); //white camp cottage (on their system) - Vacation Rental (in ours)
			TYPES.put("ch","PCT7"); //chalet
			TYPES.put("kp","PCT6"); //camp-site (on their system) - Campground (in ours)
			TYPES.put("ap","PCT3"); //appartement
			TYPES.put("as","PCT3"); //Appartementen en Suites
		}
		if (TYPES.get(type) == null) {
			attributes.add(getMaaxtonPropertyTypeName(type));
		} else {
			attributes.add(TYPES.get(type));
		}
	}
	
	private static HashMap<String, String> MAXXTON_TYPES_MAP = null;
	private static String getMaaxtonPropertyTypeName(String typeCode) {
		if (typeCode == null || typeCode.isEmpty()) {return "";}
		if (MAXXTON_TYPES_MAP == null) {
			MAXXTON_TYPES_MAP = new HashMap<String, String>();
			MAXXTON_TYPES_MAP.put("sc","Mobilhome"); 
			MAXXTON_TYPES_MAP.put("bg","Bungalow"); 
			MAXXTON_TYPES_MAP.put("be","Bungalow eigenaar"); 
			MAXXTON_TYPES_MAP.put("log","Loge"); 
			MAXXTON_TYPES_MAP.put("beachhouse","Beach house"); 
			MAXXTON_TYPES_MAP.put("vt","Holidaytent"); 
			MAXXTON_TYPES_MAP.put("ho","Hotel"); 
			MAXXTON_TYPES_MAP.put("wcc","White camp cottage"); 
			MAXXTON_TYPES_MAP.put("ch","Chalet");
			MAXXTON_TYPES_MAP.put("kp","Camp-site"); 
			MAXXTON_TYPES_MAP.put("ap","Appartement");
			MAXXTON_TYPES_MAP.put("as","Appartement");
		}
		if (MAXXTON_TYPES_MAP.get(typeCode) == null) {
			System.out.println("Property type code not find: " + typeCode);
			return "";
		}
		else {
			return MAXXTON_TYPES_MAP.get(typeCode);
		}
	}
	
	
	private static HashMap<String,String> ATTRIBUTES = null;
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	
	//TODO change this when start use real API 
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {

		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("bathandshower", "HAC263");
			ATTRIBUTES.put("bath", "HAC264");
			ATTRIBUTES.put("shower", "HAC265");
			ATTRIBUTES.put("nonsmoking", "HAC198");
			ATTRIBUTES.put("petsallowed", "PET7");
			ATTRIBUTES.put("hikingmountains", "RSN10");
			ATTRIBUTES.put("hikingplains", "RSN10");
			ATTRIBUTES.put("mountainbike", "RSP10");
			ATTRIBUTES.put("bikingplains", "RSN10");
			ATTRIBUTES.put("sailing", "RSN14");
			ATTRIBUTES.put("surfing", "RSN15");
			ATTRIBUTES.put("crosscountryski", "RSN6");
			ATTRIBUTES.put("icerink", "RST84");
			ATTRIBUTES.put("skiaera", "RSN6");
			ATTRIBUTES.put("snowboard", "RSN6");
			ATTRIBUTES.put("winterwalking", "RSN6");
			ATTRIBUTES.put("golfing", "RSN5");
			ATTRIBUTES.put("riding", "RSN16");
			ATTRIBUTES.put("tennis", "RSN17");
			ATTRIBUTES.put("themepark", "RSN2");
			ATTRIBUTES.put("breakfast", "HAC138");
			ATTRIBUTES.put("elevator", "HAC33");
			ATTRIBUTES.put("garden", "FAC4");
			ATTRIBUTES.put("solarium", "RSN12");
			ATTRIBUTES.put("fitness", "RSN12");
			ATTRIBUTES.put("massage", "RSN12");
			ATTRIBUTES.put("whirlpool", "FAC7");
			ATTRIBUTES.put("seaview", "LOC16");
			ATTRIBUTES.put("childrenplayground", "HAC193");
			ATTRIBUTES.put("airconditionning", "FAC1");
			ATTRIBUTES.put("balcony", "FAC2");
			ATTRIBUTES.put("terrace", "FAC2");
			ATTRIBUTES.put("bbq", "HAC118");
			ATTRIBUTES.put("babycot", "RMA26");
			ATTRIBUTES.put("dishwasher", "RMA32");
			ATTRIBUTES.put("fireplace", "RMA41");
			ATTRIBUTES.put("parking", "HAC63");
			ATTRIBUTES.put("parkingprivate", "HAC63");
			ATTRIBUTES.put("parkingcovered", "HAC63");
			ATTRIBUTES.put("sauna", "HAC79");
			ATTRIBUTES.put("pool", "HAC71");
			ATTRIBUTES.put("poolprivat", "HAC253");
			ATTRIBUTES.put("poolindor", "HAC54");
			ATTRIBUTES.put("poolheated", "HAC49");
			ATTRIBUTES.put("poolchildren", "HAC48");
			ATTRIBUTES.put("tv", "RMA90");
			ATTRIBUTES.put("washingmachine", "RMA31");
			ATTRIBUTES.put("internet", "HAC223");
			ATTRIBUTES.put("wlan", "HAC221");
			ATTRIBUTES.put("cleaning", "HAC51");
			ATTRIBUTES.put("dvd", "RMA163");
			ATTRIBUTES.put("tabletennis", "RMA140");
			ATTRIBUTES.put("microwave", "RMA68");
			ATTRIBUTES.put("oven", "RMA77");
			ATTRIBUTES.put("telephon", "RMA107");
			//reals
			ATTRIBUTES.put("sau", "HAC79");
			ATTRIBUTES.put("bru", "HAC55"); //jacuzzi (Maxxton - bubble bath)
			ATTRIBUTES.put("apd", "CBF22"); //Separate shower(s)
			ATTRIBUTES.put("mag", "RMA68");
			ATTRIBUTES.put("intacctb", "EQP55");
			ATTRIBUTES.put("intacctb", "EQP55");
			
		}
		
		if (ATTRIBUTES.get(attribute) != null) {attributes.add(ATTRIBUTES.get(attribute));}
	}

	

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Maxxton readAdditionCosts()");
		
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Maxxton inquireReservation()");
	}

}
