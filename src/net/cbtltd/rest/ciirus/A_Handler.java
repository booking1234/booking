package net.cbtltd.rest.ciirus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfPropertyDetails;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfRate;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfSGuests;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfString;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.PropertyDetails;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.Rate;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.SBookingDetails;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.SBookingResult;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.SGuests;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.StrucSearchFilterOptions;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.StrucSearchOptions;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.ArrayOfReservation;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.GetExtrasResult;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.PropertyExtras;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.TaxRates;
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
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Account;
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
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * API Username = EricMason
 * API Password = YiPL46Rfi
 * Roger Parry roger@ciirus.com
 * 
 * CiiRUS App:
 *   Username: 	    MyBookingPal
 *   PSWD:      	YiPL46Rfi
 */
public class A_Handler extends PartnerHandler implements IsPartner {
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());

	private static String username = "1q3w2e4r6t5y7u9i8opzmc8";
	private static String password = "m12n4b3vc568x744e";

	private static final int CUSTOMER_DEFAULT_AGE = 30;
	private static final int CIIRUS_PRICES_FOR_NUMBER_DAYS_IN_FUTURE = 365000;
	private static final int FEES_PRICES_UPDATE_DAYS= 365;
	
	private static final String STATE_NOT_EXIST_CIIRUS_PAYMENT = "%none%";
	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {
		super(partner);
	}

	// please note this line. This object calls below instead of getPort() and getPortNew() methods
	private static CiirusServiceHolder ciirusServiceHolder = new CiirusServiceHolder(); 

	/**
	 * Returns collisions with the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			int propertyID = Integer.valueOf(PartnerService.getProductaltid(sqlSession, reservation)); 
			String arrivalDate = format(reservation.getFromdate());
			String departureDate = format(reservation.getTodate());			
			isAvailableResult = ciirusServiceHolder.isPropertyAvailable(username, password, propertyID, arrivalDate, departureDate);
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
		return isAvailableResult;
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
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) {reservation.setCustomerid(Party.NO_ACTOR);}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.hasCustomerid() ? reservation.getCustomerid() : reservation.getAgentid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}

			SBookingDetails bookingRequest = new SBookingDetails();
			Integer propertyID = Integer.valueOf(PartnerService.getProductaltid(sqlSession, reservation));
			bookingRequest.setPropertyID(propertyID);
			bookingRequest.setArrivalDate(format(reservation.getFromdate()));
			bookingRequest.setDepartureDate(format(reservation.getTodate()));
			bookingRequest.setGuestAddress(customer.getLocalAddress());
			bookingRequest.setGuestEmailAddress(customer.getEmailaddress() == null ? "" : customer.getEmailaddress());
			bookingRequest.setGuestTelephone(customer.getDayphone() == null ? "" : customer.getDayphone());
			bookingRequest.setGuestName(customer.getName() == null ? "" : customer.getName());
			ArrayOfSGuests guests = new ArrayOfSGuests();
			bookingRequest.setGuestList(guests);
			SGuests guest = new SGuests();
			guests.getSGuests().add(guest);
			guest.setName(customer.getName() == null ? "Unknown" : customer.getName());
			guest.setAge(CUSTOMER_DEFAULT_AGE);
			bookingRequest.setPoolHeatRequired(false);
			
//			LOG.debug("createReservation rq " + bookingRequest);
			SBookingResult bookingResponse = ciirusServiceHolder.makeBooking(username, password, bookingRequest);
//			LOG.debug("createReservation rs " + bookingResponse);
			
			if(!bookingResponse.isBookingPlaced()){
				LOG.error("Ciirus reservation not created, productId:"+reservation.getProductid()+", error message from Ciirus: "+ bookingResponse.getErrorMessage());
				throw new ServiceException(Error.reservation_api, bookingResponse.getErrorMessage());
			}
			reservation.setAltpartyid(getAltpartyid());
			int ciirusBookingID = bookingResponse.getBookingID();
			reservation.setAltid(ciirusBookingID+"");
			reservation.setMessage(null);
			reservation.setState(Reservation.State.Confirmed.name());
			
			LOG.debug("Booking ID from Ciirus (PartyId: "+this.getAltpartyid()+"): "+reservation.getAltid());

		}
		catch (Throwable x) {
			x.printStackTrace();
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {

		Date timestamp = new Date();
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") createReservationAndPayment " + reservation.getId();
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
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.hasCustomerid() ? reservation.getCustomerid() : reservation.getAgentid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}

			
			//first we need to create booking.
			SBookingDetails bookingRequest = new SBookingDetails();
			String guestName = (customer.getFirstName()!=null) ? customer.getFirstName() + " " : "";
			guestName += (customer.getFamilyName()!=null) ? customer.getFamilyName() : "";
			String customerEmailAddress = customer.noEmailaddress() ? "" : customer.getEmailaddress();
			String localAddress = checkIfValueNullOrEmpty(customer.getLocalAddress()) ? "" : customer.getLocalAddress(); 
			Integer propertyID = Integer.valueOf(PartnerService.getProductaltid(sqlSession, reservation));
			bookingRequest.setPropertyID(propertyID);
			bookingRequest.setArrivalDate(format(reservation.getFromdate()));
			bookingRequest.setDepartureDate(format(reservation.getTodate()));
			bookingRequest.setGuestAddress(localAddress);
			bookingRequest.setGuestEmailAddress(customerEmailAddress);
			bookingRequest.setGuestTelephone(customer.getDayphone() == null ? "" : customer.getDayphone());
			
			bookingRequest.setGuestName(guestName);
			ArrayOfSGuests guests = new ArrayOfSGuests();
			bookingRequest.setGuestList(guests);
			SGuests guest = new SGuests();
			guests.getSGuests().add(guest);
			guest.setName(guestName);
			guest.setAge(CUSTOMER_DEFAULT_AGE);
			bookingRequest.setPoolHeatRequired(false);
			
//			LOG.debug("createReservation rq " + bookingRequest);
			SBookingResult bookingResponse = ciirusServiceHolder.makeBooking(username, password, bookingRequest);
//			LOG.debug("createReservation rs " + bookingResponse);
			
			if(!bookingResponse.isBookingPlaced()){
				LOG.error("Ciirus reservation not created, productId:"+reservation.getProductid()+", error message from Ciirus: "+ bookingResponse.getErrorMessage());
				throw new ServiceException(Error.reservation_api, bookingResponse.getErrorMessage());
			}
			reservation.setAltpartyid(getAltpartyid());
			int ciirusBookingID = bookingResponse.getBookingID();
			reservation.setAltid(ciirusBookingID+"");
			//reservation.setMessage(null);
			//reservation.setState(Reservation.State.Confirmed.name());
			
			LOG.debug("Booking ID from Ciirus (PartyId: "+this.getAltpartyid()+"): "+reservation.getAltid());
			
		

			//NOW create payment (second API call)
			String cardType = this.getCreditCardCiirusTypeId(creditCard.getType());
			//card month need to be in MM format
			String expiryMonth = creditCard.getMonth();
			if(expiryMonth.length()==1){
				expiryMonth = "0"+expiryMonth;
			}
			
			String expiryYear = creditCard.getYear();
			if(expiryYear.length()>2){
				expiryYear = expiryYear.substring(expiryYear.length() - 2);
			}
			
			String zipCode = checkIfValueNullOrEmpty(customer.getPostalcode()) ? "" : customer.getPostalcode();
			String city = checkIfValueNullOrEmpty(customer.getCity()) ? "" : customer.getCity();
			String state = checkIfValueNullOrEmpty(customer.getRegion()) ? "" : customer.getRegion();
			if(state==null || state.isEmpty()){
				state = STATE_NOT_EXIST_CIIRUS_PAYMENT;
			}
			
			String country = customer.noCountry() ? "" : customer.getCountry();
			
			String paymentStatus = ciirusServiceHolder.addCCPayment(username, password, getXGC(reservation.getFromdate()), customerEmailAddress, 
					cardType, creditCard.getNumber(), creditCard.getSecurityCode(), expiryMonth, expiryYear, guestName, 
					localAddress, zipCode, country, city, state, reservation.getQuote(), propertyID, bookingResponse.getBookingID());
			
			
			if(paymentStatus.equalsIgnoreCase("OK")){
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
				reservation.setMessage(null);
				LOG.debug("Ciirus reservation payment success!");
			}else{
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				String errorDescription = paymentStatus;
				
				//canceling previous reservation
				//because that was different API call
				this.cancelReservation(sqlSession, reservation);
				
				LOG.error("Ciirus reservation payment not success - error: "+errorDescription);
				reservation.setMessage(errorDescription);
				reservation.setState(Reservation.State.Cancelled.name());
				result.put(GatewayHandler.ERROR_MSG, errorDescription);
				return result;
			}
			
		}
		catch (Throwable x) {
			x.printStackTrace();
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			if(reservation.getAltid()!=null){
				//canceling previous reservation if we got error later
				this.cancelReservation(sqlSession, reservation);
			}
			LOG.error(x.getMessage());
		} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
	
		return result;
	}
	
	
	
	
	
	
	
	

	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			LOG.debug("readReservation " + reservation);
			throw new ServiceException(Error.service_absent, "Ciirus readReservation()");
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
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Ciirus updateReservation()");
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
		String message = "Ciirus (PartyId: "+this.getAltpartyid()+") cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			String cancelerrormessage =	ciirusServiceHolder.cancelBooking(username, password, Integer.parseInt(reservation.getAltid()));
			if(!cancelerrormessage.isEmpty()){
				throw new ServiceException(Error.reservation_api, "Error in cancelling reservations: "+cancelerrormessage);
			}
		}
		catch (Throwable x) {
			LOG.error("Ciirus (PartyId: "+this.getAltpartyid()+") cancel reservation - " + x.getMessage());
			reservation.setMessage(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		try {
			throw new ServiceException(Error.service_absent, "Ciirus readAlerts()");
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		try {
			throw new ServiceException(Error.service_absent, "Ciirus readLocations()");
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		Date version = new Date();
		Date toDateForExtraPrices = Time.addDuration(version, CIIRUS_PRICES_FOR_NUMBER_DAYS_IN_FUTURE, Time.DAY);		
		Date minimumToDateForExtraPricesUpdate = Time.addDuration(version, FEES_PRICES_UPDATE_DAYS, Time.DAY);
		
		String message = "readPrices Ciirus (Altpartyid:"+this.getAltpartyid()+")";
		LOG.debug(message + version.toString());
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayOfPropertyDetails rs = ciirusServiceHolder.getProperties(username, password, null, null, this.getStrucSearchFilterOptions(sqlSession), this.getStrucSearchOptions());
			for (PropertyDetails property : rs.getPropertyDetails()) {
				String altId = String.valueOf(property.getPropertyID());
//				System.out.println("Prices read property ID: " + altId);
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altId));
				if (product == null) {
					LOG.error(Error.product_altid.getMessage() + " " + altId);
				}
				else {
					ArrayList<Price> pricesFromApi = new ArrayList<Price>();
					ArrayList<Fee> feeFromApi = new ArrayList<Fee>();
					ArrayList<Tax> taxListFromApi = new ArrayList<Tax>();
					
					ArrayOfRate rates = ciirusServiceHolder.getPropertyRates(username, password, property.getPropertyID());
					for (Rate rate : rates.getRate()) {						
						Price price = new Price();
						price.setEntityid(product.getId());
						price.setEntitytype(NameId.Type.Product.name());
						price.setPartyid(getAltpartyid());
						price.setName(Price.RACK_RATE);
						price.setType(NameId.Type.Reservation.name());
						price.setQuantity(1.0);
						price.setCurrency(product.getCurrency());
						price.setUnit(Unit.DAY);
						Date fromdate =  rate.getFromDate().toGregorianCalendar().getTime();
						price.setDate(fromdate);
						Date todate =  rate.getToDate().toGregorianCalendar().getTime();
						price.setTodate(todate);

//						Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
//						if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
//						else {price = exists;}
//
//						price.setState(Price.CREATED);
						price.setRule(Price.Rule.AnyCheckIn.name());
						price.setValue(rate.getDailyRate().doubleValue());
						price.setCost(rate.getDailyRate().doubleValue());
						price.setMinStay(rate.getMinNightsStay());
						price.setMinimum(price.getValue() * rate.getMinNightsStay());
						price.setAvailable(1);
//						price.setVersion(version);
//						sqlSession.getMapper(PriceMapper.class).update(price);
//						sqlSession.getMapper(PriceMapper.class).cancelversion(price);
//						LOG.debug(i++ + " " + altid + " = " + product.getId() + " " + price.getDate() + " " + price.getTodate());
						
						pricesFromApi.add(price);
					}
					
										
					//here we will read taxes
					TaxRates propertyTaxRates = ciirusServiceHolder.getTaxRates(username, password, property.getPropertyID());
					
					if(propertyTaxRates.getTax1Percent().compareTo(BigDecimal.ZERO)>0 && propertyTaxRates.getTax1Name()!=null){
						this.insertTax(taxListFromApi, product.getId(), propertyTaxRates.getTax1Name(), propertyTaxRates.getTax1Percent().doubleValue());
					}
					
					if(propertyTaxRates.getTax2Percent().compareTo(BigDecimal.ZERO)>0 && propertyTaxRates.getTax2Name()!=null){
						this.insertTax(taxListFromApi, product.getId(), propertyTaxRates.getTax2Name(), propertyTaxRates.getTax2Percent().doubleValue());
					}
					
					if(propertyTaxRates.getTax3Percent().compareTo(BigDecimal.ZERO)>0 && propertyTaxRates.getTax3Name()!=null){
						this.insertTax(taxListFromApi, product.getId(), propertyTaxRates.getTax3Name(), propertyTaxRates.getTax3Percent().doubleValue());
					}

					
					//And here read extras - some of them are mandatory, some not.
					//Also 3 kind of pricing - FlatFee, DailyFee and PercentageFee
					//there are also taxes here (3 different)
					GetExtrasResult propertyExtras = ciirusServiceHolder.getExtras(username, password, property.getPropertyID());
					if(propertyExtras!=null && propertyExtras.getExtras()!=null && propertyExtras.getExtras().getPropertyExtras()!=null){
						List<PropertyExtras> propertyExtrasList = propertyExtras.getExtras().getPropertyExtras();
						for(PropertyExtras currentCiirusExtra : propertyExtrasList){
							
							//TODO they have MinimumCharge - minimum fee value which will be billed
							//we do not handle MinimumCharge - but we use live pricing
							
							int feeUnit = Fee.NOT_APPLICABLE;
							boolean isFlatType = true;
							double feeValue = 0.0;
							if(currentCiirusExtra.isFlatFee()){
								isFlatType = true;
								feeValue = currentCiirusExtra.getFlatFeeAmount().doubleValue();
							}else if(currentCiirusExtra.isPercentageFee()){
								isFlatType = false;
								feeValue = currentCiirusExtra.getPercentage().doubleValue();
							}else if(currentCiirusExtra.isDailyFee()){
								isFlatType = true;
								feeUnit = Fee.PER_DAY;
								feeValue = currentCiirusExtra.getDailyFeeAmount().doubleValue();
							}
							
							boolean isMandatory = false;
							if(currentCiirusExtra.isMandatory()){
								isMandatory = true;
							}
							
							boolean isTaxable = false;
							//first check should we add all 3 taxes - if yes than fee is taxable
							if(currentCiirusExtra.isChargeTax1() && currentCiirusExtra.isChargeTax2() && currentCiirusExtra.isChargeTax3()){
								isTaxable = true;
							}else if(currentCiirusExtra.isChargeTax1() || currentCiirusExtra.isChargeTax2() || currentCiirusExtra.isChargeTax3()){
								//but in case if we have one or more tax (but not all) than we need to add this taxes manually
								double noTaxFeeValue = feeValue;
								if(currentCiirusExtra.isChargeTax1() && propertyTaxRates.getTax1Percent().compareTo(BigDecimal.ZERO)>0){
									feeValue += noTaxFeeValue * (propertyTaxRates.getTax1Percent().doubleValue()/100.0);
								}
								if(currentCiirusExtra.isChargeTax2() && propertyTaxRates.getTax2Percent().compareTo(BigDecimal.ZERO)>0){
									feeValue += noTaxFeeValue * (propertyTaxRates.getTax2Percent().doubleValue()/100.0);
								}
								if(currentCiirusExtra.isChargeTax3() && propertyTaxRates.getTax3Percent().compareTo(BigDecimal.ZERO)>0){
									feeValue += noTaxFeeValue * (propertyTaxRates.getTax3Percent().doubleValue()/100.0);
								}
							}
							
							this.insertFee(feeFromApi, product, currentCiirusExtra.getItemCode(), currentCiirusExtra.getItemDescription(), 
									feeValue, isTaxable, isMandatory, isFlatType, feeUnit, toDateForExtraPrices);
							
//							Price priceExtra = new Price();
//							if(currentCiirusExtra.isMandatory()){
//								priceExtra.setEntitytype(NameId.Type.Mandatory.name());
//							}else{
//								priceExtra.setEntitytype(NameId.Type.Feature.name());
//							}
//							priceExtra.setEntityid(product.getId());
//							priceExtra.setPartyid(getAltpartyid());
//							priceExtra.setType("Fees");
//							priceExtra.setDate(version);
//							priceExtra.setTodate(toDateForExtraPrices);
//							priceExtra.setCurrency(product.getCurrency());
//							priceExtra.setQuantity(1.0);
//							priceExtra.setAvailable(1);
//							priceExtra.setName(currentCiirusExtra.getItemDescription());
//							priceExtra.setMinimum(currentCiirusExtra.getMinimumCharge().doubleValue());
//							if(currentCiirusExtra.isDailyFee()){
//								priceExtra.setUnit(Unit.DAY);
//								priceExtra.setValue(currentCiirusExtra.getDailyFeeAmount().doubleValue());
//							}else if(currentCiirusExtra.isFlatFee()){
//								priceExtra.setUnit(Unit.EA);
//								priceExtra.setValue(currentCiirusExtra.getFlatFeeAmount().doubleValue());
//							}else if(currentCiirusExtra.isPercentageFee()){
//								priceExtra.setValue(currentCiirusExtra.getPercentage().doubleValue());
//							}
//							Price exists = sqlSession.getMapper(PriceMapper.class).exists(priceExtra);
//							if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(priceExtra);}
//							else {priceExtra = exists;}
//	
//							priceExtra.setState(net.cbtltd.shared.Price.CREATED);
//							
//							
//	//						priceExtra.setValue(service.getServiceprice() == null ? 0.0 : service.getServiceprice().doubleValue());
//							priceExtra.setRule("Manual");
//							priceExtra.setVersion(version);
//							sqlSession.getMapper(PriceMapper.class).update(priceExtra);
//							sqlSession.getMapper(PriceMapper.class).cancelversion(priceExtra);
						}
					}
					
					PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
					PartnerService.updateProductTaxes(sqlSession, product, taxListFromApi, version);
					FeeService.updateProductFees(sqlSession, product, feeFromApi, version, true, minimumToDateForExtraPricesUpdate);
					
					sqlSession.commit();
				}
			}
		}
		catch (Throwable x) {
//			x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getStackTrace());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, version);
	}
	
	
	private void insertFee(ArrayList<Fee> feeFromApi, Product product, String elementAltId, String nameOfElement, 
			Double feeValue, boolean isTaxable, boolean isMandatory, boolean isFlatType, int feeUnit, Date toDateForExtraPrices){
		Fee feeObject = new Fee();
		
		feeObject.setAltId(elementAltId);
		if(isMandatory){
			feeObject.setEntityType(Fee.MANDATORY);
		}else{
			feeObject.setEntityType(Fee.OPTIONAL);
		}
		feeObject.setProductId(product.getId());
		feeObject.setPartyId(getAltpartyid());
		feeObject.setName(nameOfElement);

		if(isTaxable){
			feeObject.setTaxType(Fee.TAXABLE);
		}else{
			feeObject.setTaxType(Fee.NOT_TAXABLE);
		}
		feeObject.setFromDate(new Date(0));
		feeObject.setToDate(toDateForExtraPrices);
		feeObject.setUnit(feeUnit);
		feeObject.setValue(feeValue);
		if(isFlatType){
			feeObject.setValueType(Fee.FLAT);
			feeObject.setCurrency(product.getCurrency());
		}else{
			feeObject.setValueType(Fee.PERCENT);
		}
		feeObject.setWeight(0);
		
		feeFromApi.add(feeObject);
	}
	
	
	
	private void insertTax(ArrayList<Tax> taxListFromApi, String productId, String nameOfElement, Double percentValue){
		Tax taxObject = new Tax();
		taxObject.setAccountid(Account.VAT_OUTPUT);
		taxObject.setPartyid(getAltpartyid());
		taxObject.setProductId(productId);
		taxObject.setType(Tax.Type.SalesTaxExcluded.name());
		taxObject.setDate(new Date(0));
		taxObject.setThreshold(0);
		taxObject.setName(nameOfElement);
		taxObject.setAmount(percentValue);
		taxObject.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
		
		taxListFromApi.add(taxObject);
	}
	
//	private void createOrFindTax(SqlSession sqlSession, String taxName, Double taxValue, String ProductID, ArrayList <Tax> taxExistlist, Date version) {
//		if(taxExistlist.size()>0){
//			this.addTax(sqlSession, taxExistlist.get(0).getId(), taxName, taxValue, ProductID, version);
//			taxExistlist.remove(0);
//		}else{
//			this.addTax(sqlSession, null, taxName, taxValue, ProductID, version);
//		}
//	}
//
//	
//	private void addTax(SqlSession sqlSession, String taxID, String taxName, Double taxValue, String ProductID, Date version) {
//		Tax tax = new Tax();
//		if (taxID == null) {
//			tax.setAccountid(Account.VAT_OUTPUT);
//			tax.setPartyid(getAltpartyid());
//			tax.setType(Tax.Type.SalesTaxExcluded.name());
//			tax.setDate(new Date(0));
//			tax.setThreshold(0);
//			sqlSession.getMapper(TaxMapper.class).create(tax);
//		}else {
//			tax = sqlSession.getMapper(TaxMapper.class).read(taxID);
//		}
//		tax.setName(taxName);
//		tax.setAmount(taxValue);
//		tax.setState(Tax.CREATED);
//		tax.setVersion(version);
//		sqlSession.getMapper(TaxMapper.class).update(tax);
//		RelationService.replace(sqlSession, Relation.PRODUCT_TAX, ProductID, tax.getId());
//		//LOG.debug("Tax created: " + tax);
//	}

	private StrucSearchFilterOptions getStrucSearchFilterOptions(SqlSession sqlSession) {
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		StrucSearchFilterOptions filterOptions = new StrucSearchFilterOptions();
		filterOptions.setBedrooms(0);
		filterOptions.setCommunalGym(2);
		filterOptions.setCommunityID(0);
		filterOptions.setHasGamesRoom(2);
		filterOptions.setConservationView(2);
		filterOptions.setHasPool(2);
		filterOptions.setHasSpa(2);
		filterOptions.setIsGasFree(false);
		filterOptions.setLakeView(2);
		filterOptions.setManagementCompanyID(0);
		filterOptions.setOnGolfCourse(2);
		filterOptions.setPetsAllowed(2);
		filterOptions.setPrivacyFence(2);
		filterOptions.setPropertyClass(0);
		filterOptions.setPropertyID(0);		
		filterOptions.setPropertyType(0);
		filterOptions.setSleeps(0);
		filterOptions.setSouthFacingPool(2);
		filterOptions.setWaterView(2);
		filterOptions.setWiFi(2);
		filterOptions.setManagementCompanyID(Integer.parseInt(party.getAltid()));
		
		return filterOptions;
	}

	private StrucSearchOptions getStrucSearchOptions() {
		StrucSearchOptions searchOptions = new StrucSearchOptions();
		searchOptions.setReturnFullDetails(true);
		searchOptions.setReturnQuote(false);
		searchOptions.setIncludePoolHeatInQuote(false);
		searchOptions.setReturnTopX(0);
		return searchOptions;
	}

	/**
	 * Transfer accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date version = new Date();
		String message = "readProducts Ciirus (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);
		
		StringBuilder locationNotFoundString = new StringBuilder();
		final SqlSession sqlSession = RazorServer.openSession();
		try {

			/*
			ArrayOfManagementCompanies managers = ciirusServiceHolder.getManagementCompanyList(username, password);
			for (ManagementCompanies manager : managers.getManagementCompanies()) {
				String altid = String.valueOf(manager.getMCUserID());
				Party party = PartnerService.getParty(sqlSession, getAltpartyid(), altid, Party.Type.Organization);
				party.setName(manager.getMCCompanyName());
				sqlSession.getMapper(PartyMapper.class).update(party);
			}
			*/
		

			ArrayOfPropertyDetails rs = ciirusServiceHolder.getProperties(username, password, null, null, getStrucSearchFilterOptions(sqlSession), getStrucSearchOptions());
			for (PropertyDetails property : rs.getPropertyDetails()) {
				String altid = String.valueOf(property.getPropertyID());
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
					if (product == null) {continue;}
					ArrayList<String> attributes = new ArrayList<String>();
					
					product.setBathroom(property.getBathrooms().intValue());
					product.setRoom(property.getBedrooms());

					product.setCurrency(property.getCurrencyCode());
					product.setAltSupplierId(getAltpartyid());
					//product.setAltSupplierId(Integer.toString(property.getHostMCUserID())); //previous way
					product.setSupplierid(getAltpartyid());
					product.setRating(getRating(property.getPropertyClass()));
					addType(attributes, property.getPropertyType());

					property.getQuoteExcludingTax();
					property.getQuoteIncludingTax();
					product.setPerson(property.getSleeps());

					product.setCommission(getCommission());
					product.setDiscount(getDiscount());
					product.setRank(0.0);
					product.setAltitude(0.0);
					product.setLatitude(property.getXCO().doubleValue());
					product.setLongitude(property.getYCO().doubleValue());
					
					product.setChild(0);
					product.setInfant(0);
					product.setToilet(0);
					product.setQuantity(1);
					product.setUnit(Unit.DAY);
					
					product.setName(property.getWebsitePropertyName() == null ? property.getMCPropertyName() : property.getWebsitePropertyName());
					
					
					product.setVersion(version);
					
					String country = property.getCountry();
					if(!country.isEmpty() && country.length()==3){
						country = COUNTRY_LOCALE_MAP.get(country.toUpperCase()).getCountry();
					}
					
					Location location = null;
					
					StringBuilder address = new StringBuilder();
					if (property.getAddress1() != null) {address.append(property.getAddress1()).append("\n");}
					if (property.getAddress2() != null) {address.append(property.getAddress2()).append("\n");}
					if (property.getCommunity() != null) {address.append(property.getCommunity()).append("\n");}
					if (property.getCity() != null) {address.append(property.getCity()).append("\n");}
					if (property.getCounty() != null) {address.append(property.getCounty()).append("\n");}
					if (country != null) {address.append(country).append("\n");}
					product.setPhysicaladdress(address.toString());
					
					location = PartnerService.getLocation(sqlSession, 
							property.getCity(),
							property.getState(),
							country, 
							property.getXCO().doubleValue(), 
							property.getYCO().doubleValue(),
							property.getZip());
					
					/*
					location = PartnerService.getLocation(sqlSession, 
							property.getCity(),
							property.getState(),
							property.getCountry());
							*/
					if(location!=null){
						product.setLocationid(location.getId());
					}else{
						product.setState(Product.SUSPENDED);
						locationNotFoundString.append("\n").append("Ciirus Property: " + altid + " country: " + country +  " city: " + property.getCity() + "state: "+property.getState());
					}
					
					//reading cleaning fee
					//TODO check how to use option OnlyChargeCleaningFeeWhenLessThanDays
					//->maybe later add this. As we using live pricing, cleaning fee
					//info is not necessary, because problem with value OnlyChargeCleaningFeeWhenLessThanDays
					//is not resolved in our system. So for now we will set 0 for cleaning fee
					//and if cleaning fee exist, value will be calculated in total price from live pricing 
					/*
					CleaningFeeSettings cleaningFee = ciirusServiceHolder.getCleaningFee(username, password, property.getPropertyID());
					if(cleaningFee.isChargeCleaningFee()){
						product.setCleaningfee(cleaningFee.getCleaningFeeAmount().doubleValue());
					}else{
						product.setCleaningfee(0.0);
					}
					*/
					
					product.setCleaningfee(0.0);
					
					String descriptionPlainText = ciirusServiceHolder.getDescriptions(username, password, property.getPropertyID(), true);
					
					sqlSession.getMapper(ProductMapper.class).update(product);
					
					StringBuilder description = new StringBuilder();
					description.append(descriptionPlainText).append("\n\n");
					
					this.setDesciptionAndAttributes(property, description, attributes);

					product.setPublicText(new Text(product.getPublicId(), product.getName(), Text.Type.HTML, new Date(), description.toString(), Language.EN));

					TextService.update(sqlSession, product.getTexts());
					RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
					RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
					RelationService.removeDeprecatedData(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);

					sqlSession.commit();
//					LOG.debug(i++ + " readProducts - Ciirus property ID: " + altid + ", our system product ID: " + product.getId());
//					wait(getProductwait());
				}
				catch (Throwable x) {LOG.error(Error.product_data.name() + altid); x.printStackTrace();}
			}
			
			//canceling product which are not updated 
			Product action = new Product();
			action.setAltpartyid(getAltpartyid());
			action.setState(Product.CREATED);
			action.setVersion(version); 
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
			sqlSession.commit();
		}
		catch (Throwable x) {
			//x.printStackTrace();
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug("Missing Places" + locationNotFoundString.toString());
		MonitorService.monitor(message, version);
	}
	
	
	@Override
	public void readImages() {
		Date timestamp = new Date();
		String message = "readImages Ciirus (PartyId: "+this.getAltpartyid()+")";
		LOG.debug(message);
		
		final SqlSession sqlSession = RazorServer.openSession();
		try { 
			ArrayOfPropertyDetails rs = ciirusServiceHolder.getProperties(username, password, null, null, getStrucSearchFilterOptions(sqlSession), getStrucSearchOptions());
			for (PropertyDetails property : rs.getPropertyDetails()) {
				String altid = String.valueOf(property.getPropertyID());
				try {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
					if (product == null) {continue;}
					ArrayOfString urls = ciirusServiceHolder.getImageList(username, password, property.getPropertyID());
					if (HasUrls.LIVE && urls != null && urls.getString() != null && !urls.getString().isEmpty()) {
						String main = property.getMainImageURL();
						ArrayList<NameId> images = new ArrayList<NameId>();
						for (String url : urls.getString()) {
							images.add(new NameId(url.equalsIgnoreCase(main) ? "Main Picture" : "", url));
						}
						UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
					}
					sqlSession.commit();
				}
				catch (Throwable x) {
					LOG.error(Error.product_data.name() + altid); 
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
	
	private void setDesciptionAndAttributes(PropertyDetails property, StringBuilder description, ArrayList<String> attributes){
		if (property.getBullet1() != null) {description.append(property.getBullet1()).append("\n\n");}
		if (property.getBullet2() != null) {description.append(property.getBullet2()).append("\n\n");}
		if (property.getBullet3() != null) {description.append(property.getBullet3()).append("\n\n");}
		if (property.getBullet4() != null) {description.append(property.getBullet4()).append("\n\n");}
		if (property.getBullet5() != null) {description.append(property.getBullet5()).append("\n\n");}
		if (property.isAirCon()) {addAttribute(attributes, "AirCon");}
		if (property.isAirHockeyTable()) {description.append("Air Hockey Table").append("\n\n"); addAttribute(attributes, "AirHockeyTable");}
		if (property.isBBQ()) {addAttribute(attributes, "BBQ");}
		if (property.isBigScreenTV()) {addAttribute(attributes, "BigScreenTV");}
		if (property.isCDPlayer()) {addAttribute(attributes, "CDPlayer");}
		if (property.isCommunalGym()) {addAttribute(attributes, "CommunalGym");}
		if (property.isCommunalPool()) {addAttribute(attributes, "CommunalPool");}
		if (property.isConservationView()) {addAttribute(attributes, "ConservationView");}
		if (property.isCrib()) {addAttribute(attributes, "Crib");}
		if (property.isDVDPlayer()) {addAttribute(attributes, "DVDPlayer");}
		if (property.isElectricFireplace()) {addAttribute(attributes, "ElectricFireplace");}
		if (property.isFishing()) {addAttribute(attributes, "Fishing");}
		if (property.isFoosballTable()) {description.append("Foosball Table").append("\n\n"); addAttribute(attributes, "FoosballTable");}
		if (property.isFreeCalls()) {addAttribute(attributes, "FreeCalls");}
		if (property.isFreeSolarHeatedPool()) {addAttribute(attributes, "FreeSolarHeatedPool");}
		if (property.isGamesRoom()) {addAttribute(attributes, "GamesRoom");}
		if (property.isGasFireplace()) {addAttribute(attributes, "GasFireplace");}
		if (property.isGolfIncluded()) {addAttribute(attributes, "GolfIncluded");}
		if (property.isGolfView()) {addAttribute(attributes, "GolfView");}
		if (property.isGrill()) {addAttribute(attributes, "Grill");}
		if (property.isHairDryer()) {addAttribute(attributes, "HairDryer");}
		if (property.isHasPool()) {addAttribute(attributes, "HasPool");}
		if (property.isHasSpa()) {addAttribute(attributes, "HasSpa");}
		if (property.isHighChair()) {addAttribute(attributes, "HighChair");}
		if (property.isIndoorHotTub()) {addAttribute(attributes, "IndoorHotTub");}
		if (property.isIndoorJacuzzi()) {addAttribute(attributes, "IndoorJacuzzi");}
		if (property.isInternet()) {addAttribute(attributes, "Internet");}
		if (property.isOutdoorHotTub()) {addAttribute(attributes, "OutdoorHotTub");}
		if (property.isPacknPlay()) {addAttribute(attributes, "PacknPlay");}
		if (property.isPavedParking()) {addAttribute(attributes, "PavedParking");}
		if (property.isPetsAllowed()) {addAttribute(attributes, "PetsAllowed");}
		if (property.isPoolAccess()) {addAttribute(attributes, "PoolAccess");}
		if (property.isPrivacyFence()) {addAttribute(attributes, "PrivacyFence");}
		if (property.isRockingChairs()) {addAttribute(attributes, "RockingChairs");}
		if (property.isSouthFacingPool()) {addAttribute(attributes, "SouthFacingPool");}
		if (property.isStroller()) {addAttribute(attributes, "Stroller");}
		if (property.isTVInEveryBedroom()) {addAttribute(attributes, "TVInEveryBedroom");}
		if (property.isVCR()) { addAttribute(attributes, "VCR");}
		if (property.isVideoGames()) {addAttribute(attributes, "VideoGames");}
		if (property.isWaterView()) {addAttribute(attributes, "WaterView");}
		if (property.isWiFi()) {addAttribute(attributes, "sWiFi");}
		if (property.isWiredInternetAccess()) {addAttribute(attributes, "WiredInternetAccess");}
		if (property.isWoodBurningFireplace()) {addAttribute(attributes, "WoodBurningFireplace");}
		if (property.isExtraBed()) {addAttribute(attributes, "ExtraBed");}
		if (property.isSofaBed()) {addAttribute(attributes, "SofaBed");}
		
		if (property.getTwinSingleBeds()>0) {description.append(property.getTwinSingleBeds() + " Twin Single Beds").append(";"); addAttribute(attributes, "TwinSingleBeds");}
		if (property.getFullBeds()>0) {description.append(property.getFullBeds() + " Full Beds").append("\n\n"); addAttribute(attributes, "FullBeds");}
		if (property.getQueenBeds()>0) {description.append(property.getQueenBeds() + " Queen Beds").append("\n\n"); addAttribute(attributes, "QueenBeds");}
		if (property.getKingBeds()>0) {description.append(property.getKingBeds() + " King Beds").append("\n\n"); addAttribute(attributes, "KingBeds");}
		if (property.getTelephone() != null)  {description.append("Telephone ").append(property.getTelephone()).append("\n\n");}
	}
	
	

	/**
	 * Transfer reservation schedule.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule Ciirus (PartyId: "+this.getAltpartyid()+")";
		String altid;//pms propertyID.      

		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayOfReservation bookings = ciirusServiceHolder.getAllReservations(username, password);
			//here we will have all products (for every property manager),
			//but we handle only for one property manager at time.
			for (net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.Reservation reservation : bookings.getReservation()) {
				altid = String.valueOf(reservation.getPropertyID());
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
				if (product == null) {
					//LOG.error(Error.product_altid.getMessage() + " " + altid);
					continue;
				} 

				PartnerService.createSchedule(sqlSession, product, reservation.getArrivalDate().toGregorianCalendar().getTime(), 
						reservation.getDepartureDate().toGregorianCalendar().getTime(), timestamp);
				PartnerService.cancelSchedule(sqlSession, product, timestamp);
			}
			sqlSession.commit();

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation special offers.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Ciirus readSpecials()");
	}





	private static HashMap<String, Integer> RATINGS = null;
	/**
	 * Gets the rating from the property class.
	 * 1=Standard, 2=Superior, 3=Deluxe,5=Superior Deluxe with Games Room / Spa, 4=Ultimate
	 *
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final Integer getRating(String value) {
		if (value == null || value.isEmpty()) {return 5;}
		if (RATINGS == null) {
			RATINGS = new HashMap<String, Integer>();
			RATINGS.put("1",6);
			RATINGS.put("2",7);
			RATINGS.put("3",8);
			RATINGS.put("4",10);
			RATINGS.put("5",9);
		}
		if (RATINGS.get(value) == null) {return 5;}
		else  {return RATINGS.get(value);}
	}

	private static HashMap<String, String> TYPES = null;
	/**
	 * Adds the type.
	 * 0=Unspecified, 1=Condo, 2=Townhouse,3=Apartment, 4=Villa, 5=House, 6=Cottage, 7=B+B, 8=Cabin,9=Hotel, 10=Motel, 11=Office, 
	 * 12=Studio, 13=Barn, 14=Signature Villa, 15=Resort, 16=Resort Home, 17=Private Room
	 *
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("0","PCT34"); //0=Unspecified; Our=Vacation home
			TYPES.put("1","PCT8"); //1=Condo; Our=Condominium
			TYPES.put("2","PCT3"); //2=Townhouse; Our=Apartment
			TYPES.put("3","PCT3"); //3=Apartment; Our=Apartment
			TYPES.put("4","PCT35"); //4=Villa; Our=Villa
			TYPES.put("5","PCT34"); //5=House; Our=Vacation home
			TYPES.put("6","PCT5"); //6=Cottage; Our=Cabin or Bungalow
			TYPES.put("7","PCT4"); //7=B+B; Our=Bed & Breakfast
			TYPES.put("8","PCT5"); //8=Cabin; Our=Cabin or Bungalow
			TYPES.put("9","PCT20"); //9=Hotel; Our=Hotel
			TYPES.put("10","PCT27"); //10=Motel; Our=Motel
			TYPES.put("11","PCT53"); //11=Office; Our=Vacation Rental
			TYPES.put("12","PCT3"); //12=Studio; Our=Apartment
			TYPES.put("13","PCT15"); //13=Barn; Our=Guest Farm
			TYPES.put("14","PCT35"); //14=Signature Villa; Our=Villa
			TYPES.put("15","PCT30"); //15=Resort; Our=Resort
			TYPES.put("16","PCT34"); //16=Resort Home; Our=Vacation home
			TYPES.put("17","PCT9"); //17=Private Room; Our=Hosted Unit
		}
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
	}
	
	
	
	
	private static HashMap<String,String> ATTRIBUTES = null;
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {

		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("HasPool", "RMA245");
			ATTRIBUTES.put("HasSpa", "RST91");
			ATTRIBUTES.put("GamesRoom", "HAC44");
			ATTRIBUTES.put("CommunalPool", "HAC71");
			ATTRIBUTES.put("CommunalGym", "RST35");
			ATTRIBUTES.put("GolfView", "RVT5");
			ATTRIBUTES.put("GolfIncluded", "RST27");
			ATTRIBUTES.put("WiFi", "HAC221");
			ATTRIBUTES.put("Internet", "RMA54"); //Internet access
			ATTRIBUTES.put("WiredInternetAccess", "RMA54"); //Internet access
			ATTRIBUTES.put("PetsAllowed", "PET7");
			ATTRIBUTES.put("Stroller", "EQP15");
			ATTRIBUTES.put("Crib", "CBF7");
			ATTRIBUTES.put("PacknPlay", "CBF7"); //Crib
			ATTRIBUTES.put("AirCon", "FAC1");
			ATTRIBUTES.put("WoodBurningFireplace", "RMA41");
			ATTRIBUTES.put("ElectricFireplace", "RMA41"); //Fireplace
			ATTRIBUTES.put("GasFireplace", "RMA41"); //Fireplace
			ATTRIBUTES.put("FreeCalls", "RMC6"); //Toll free telephone
			ATTRIBUTES.put("FreeSolarHeatedPool", "FAC5"); //Heated Pool
			ATTRIBUTES.put("SouthFacingPool", "FAC8"); //Pool
			ATTRIBUTES.put("PoolAccess", "FAC8"); //Pool
			ATTRIBUTES.put("BBQ", "HAC118"); //Barbeque Grill
			ATTRIBUTES.put("Grill", "HAC118"); //Barbeque Grill
			ATTRIBUTES.put("BigScreenTV", "RMA246");//HDTV
			ATTRIBUTES.put("TVInEveryBedroom", "RMA90");//TV
			ATTRIBUTES.put("CDPlayer", "RMA129");
			ATTRIBUTES.put("DVDPlayer", "RMA163");
			ATTRIBUTES.put("HairDryer", "HAC240");
			ATTRIBUTES.put("VCR", "CBF69");
			ATTRIBUTES.put("IndoorJacuzzi", "RMA57");
			ATTRIBUTES.put("IndoorHotTub", "CBF25");
			ATTRIBUTES.put("OutdoorHotTub", "CBF89");
			ATTRIBUTES.put("PavedParking", "RCC7");
			ATTRIBUTES.put("VideoGames", "HAC123");
			ATTRIBUTES.put("Fishing", "RSP20");
			ATTRIBUTES.put("ConservationView", "RVT20"); //Various views
			ATTRIBUTES.put("WaterView", "RVT15"); //Beach view
			ATTRIBUTES.put("HighChair", "EQP8"); //Child toddler seat
			ATTRIBUTES.put("PrivacyFence", "HAC25"); //Courtyard
			
			ATTRIBUTES.put("SofaBed", "RMA102");
			ATTRIBUTES.put("TwinSingleBeds", "RMA113"); //RMA113=Twin bed
			ATTRIBUTES.put("ExtraBed", "RMA113"); //Twin bed
			ATTRIBUTES.put("QueenBeds", "RMA86");
			ATTRIBUTES.put("KingBeds", "RMA58");
			ATTRIBUTES.put("FullBeds", "CBF29"); //Double bed
		}
		
/*//missing
       
        <AirHockeyTable>false</AirHockeyTable>
        <FoosballTable>false</FoosballTable>
        <RockingChairs>false</RockingChairs>

*/
		
		if (ATTRIBUTES.get(attribute) != null) {attributes.add(ATTRIBUTES.get(attribute));}
	}
	

	

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		ReservationPrice reservationPrice = new ReservationPrice();
		Date version = new Date();
		try {
			String message = "Ciirus (PartyId: "+this.getAltpartyid()+") readPrice productAltId:" + productAltId;
			LOG.debug(message);
			
			List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
			
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			reservationPrice.setCurrency(currency);

			StrucSearchFilterOptions strucsearchfilterpptions = this.getStrucSearchFilterOptions(sqlSession);
			strucsearchfilterpptions.setPropertyID(Integer.parseInt(productAltId));
	
			StrucSearchOptions strucsearchoptions = this.getStrucSearchOptions();
			strucsearchoptions.setIncludePoolHeatInQuote(false);
			strucsearchoptions.setReturnFullDetails(true);
			strucsearchoptions.setReturnQuote(true);
			                                                                                      //DD MM YYYY
			ArrayOfPropertyDetails rs = ciirusServiceHolder.getProperties(username, password,format(reservation.getFromdate()), format(reservation.getTodate()),
					strucsearchfilterpptions, strucsearchoptions);
	
			PropertyDetails property = rs.getPropertyDetails().get(0) ;
			
			String propertyCurrency = property.getCurrencyCode();
			Double totalPrice = property.getQuoteIncludingTax().doubleValue();
			Double priceExcludingTax = property.getQuoteExcludingTax().doubleValue();
			Double taxValue = totalPrice - priceExcludingTax;
	
			if(!propertyCurrency.equalsIgnoreCase(currency)){ 
				Double currencyRate = WebService.getRate(sqlSession, propertyCurrency, currency, new Date());
				totalPrice = PaymentHelper.getAmountWithTwoDecimals(totalPrice * currencyRate);
				priceExcludingTax = PaymentHelper.getAmountWithTwoDecimals(priceExcludingTax * currencyRate);
				taxValue = PaymentHelper.getAmountWithTwoDecimals(taxValue * currencyRate);
			}
			
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
			propertyPrice.setValue(priceExcludingTax);
			propertyPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(propertyPrice);
			
			QuoteDetail taxPriceQd = new QuoteDetail(String.valueOf(taxValue), currency, "All Taxes", "Included in the price", "", true);
			quoteDetails.add(taxPriceQd);
			
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
			totalTaxesPrice.setValue(taxValue);
			totalTaxesPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(totalTaxesPrice);
						
			reservationPrice.setPrice(priceExcludingTax.doubleValue());
			reservationPrice.setTotal(totalPrice.doubleValue());
			reservationPrice.setQuoteDetails(quoteDetails);
						
			reservation.setCurrency(currency);
			reservation.setPrice(priceExcludingTax);
//			reservation.setPrice(totalPrice);
			reservation.setQuote(totalPrice);
			reservation.setDeposit(0.0);
//			reservation.setExtra(taxValue);
			reservation.setExtra(0.0);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
			
		} catch (Throwable x) {
			x.printStackTrace();
			throw new ServiceException(Error.reservation_api,x.getMessage() +"; Probably property is not available any more.");
		}
		return reservationPrice;
	}
	
	
	
	public void readDescriptions() {
		//this is done in read products. 
		throw new ServiceException(Error.service_absent, "Ciirus readAlerts()");
	}
	
	
	/*
	 * This is not used yet and this implementation is not 100% correct
	 * for now this is done in readPrices
	 */
	@Override
	public void readAdditionCosts() { 
		throw new ServiceException(Error.service_absent, "Ciirus readAlerts()");
		/*
		Date timestamp = new Date();
		String message = "readAdditionCosts ";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayOfPropertyDetails rs = ciirusServiceHolder.getProperties(username, password, null, null, getStrucSearchFilterOptions(), getStrucSearchOptions());
			for (PropertyDetails property : rs.getPropertyDetails()) {
				String altid = String.valueOf(property.getPropertyID());
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (product == null) {LOG.error(Error.product_altid.getMessage() + " " + altid);}
				else {  
					
					GetExtrasResult extraResult = ciirusServiceHolder.getExtras(username, password, property.getPropertyID());
					List<PropertyExtras> propertyExtrasList = extraResult.getExtras().getPropertyExtras();
					for (PropertyExtras propertyExtra : propertyExtrasList) {
						//check all fields when this will start to be used
						Price price = new Price();
						price.setEntityid(altid);
						price.setEntitytype(NameId.Type.Product.name());
						price.setPartyid(getAltpartyid());
						price.setName(propertyExtra.getItemDescription());
						price.setType(Price.OPTIONAL);
						price.setAltid(propertyExtra.getItemCode());

						Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
						if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
						else {price = exists;}
						
						double priceValue = 0.0;
						if(propertyExtra.isDailyFee()){
							priceValue = propertyExtra.getDailyFeeAmount().doubleValue();
						}else if(propertyExtra.isFlatFee()){
							priceValue = propertyExtra.getFlatFeeAmount().doubleValue();
						}else if(propertyExtra.isPercentageFee()){
							priceValue = propertyExtra.getPercentage().doubleValue();
						}
						
						price.setQuantity(1.0);
						price.setCurrency(product.getCurrency());
						price.setUnit(Unit.EA);
						price.setState(Price.CREATED);
						//maybe here all options
						//price.setRule();
						price.setValue(priceValue);
						price.setMinimum(propertyExtra.getMinimumCharge().doubleValue());
						sqlSession.getMapper(PriceMapper.class).update(price);
					}
					sqlSession.commit();
					
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getStackTrace());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
		*/
	}
	
	//mapping of 3-letter country ISO codes 
	private static String[] COUNTRIES_LIST = Locale.getISOCountries();
	
	private static final Map<String, Locale> COUNTRY_LOCALE_MAP = new HashMap<String, Locale>();
	static{
		for (String country : COUNTRIES_LIST) {
	        Locale locale = new Locale("", country);
	        COUNTRY_LOCALE_MAP.put(locale.getISO3Country().toUpperCase(), locale);
	    }
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
	
	
	private boolean checkIfValueNullOrEmpty(String value){
		boolean result = false;
		if(value==null || value.isEmpty()){
			result = true;
		}
			
		return result;
	}
	
	
	private String getCreditCardCiirusTypeId(CreditCardType systemCCtype) {
		String ccTypeId = "";
		if(systemCCtype == CreditCardType.VISA){
			ccTypeId = "Visa";
		}else if(systemCCtype == CreditCardType.MASTER_CARD){
			ccTypeId = "Mastercard";
		}else if(systemCCtype == CreditCardType.AMERICAN_EXPRESS){
			ccTypeId = "American Express";
		}
		return ccTypeId;
	}
	

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Ciirus inquireReservation()");
	}

}
