package net.cbtltd.server.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.response.CancellationItem;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ConvertionInfoMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ConvertionInfo;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHandlerFactory;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public abstract class PaymentHelper {
	
	protected static final Logger LOG = Logger.getLogger(PaymentHelper.class.getName());
	
	public static final String DEPOSIT_PAYMENT_METHOD = "Deposit";
	public static final String SECOND_PAYMENT = "Second";
	public static final String FULL_PAYMENT_METHOD = "Full";
	public static final String API_FULL_PAYMENT_METHOD = "API_Full";
	public static final String API_DEPOSIT_PAYMENT_METHOD = "API_Deposit";
	public static final String MAIL_PAYMENT_METHOD = "mail";
	public static final String DEFAULT_BOOKINGPAL_CURRENCY = "USD";
	public static final String[] API_PAYMENT_METHODS = {API_FULL_PAYMENT_METHOD, API_DEPOSIT_PAYMENT_METHOD};
	public static final String[] DEPOSIT_PAYMENT_METHODS = {DEPOSIT_PAYMENT_METHOD, API_DEPOSIT_PAYMENT_METHOD};
	public static final String[] FULL_PAYMENT_METHODS = {API_FULL_PAYMENT_METHOD, FULL_PAYMENT_METHOD};
	
	public static Date getSecondChargeDate(Reservation reservation, PropertyManagerInfo propertyManagerInfo) throws ParseException {
		Date arrival = reservation.getFromdate();
		int beforeDays = propertyManagerInfo.getRemainderPaymentDate();
		return Time.addDuration(arrival, -beforeDays, Time.DAY);
	}
	
	public static Integer getLastDigits(String number, int places) {
		if(number == null || number.equals("") || number.equals("0")) {
			return null;
		} else {
			return Integer.valueOf(number.substring(number.length() - places, number.length()));
		}
	}

    public static PendingTransaction preparePendingTransaction(SqlSession sqlSession, String pos, String familyName, String firstName,
	    String cardNumber, String phoneNumber, Reservation reservation, Product product, int supplierId,
	    PropertyManagerInfo propertyManagerInfo, Double secondPayment, PaymentGatewayProvider paymentGatewayProvider,
	    Map<String, String> resultMap, Integer paymentGatewayId) throws ParseException {

		PendingTransaction pendingTransaction;
		String chargeType = propertyManagerInfo != null ? PaymentHelper.getChargeType(propertyManagerInfo, reservation) : null;
		Double additionalCommissionValue = propertyManagerInfo.getAdditionalCommission() == null ? 0.0 : propertyManagerInfo
			.getAdditionalCommission();
	
		Double nightlyRate = reservation.getNightlyrate();
		if (propertyManagerInfo.isNetRate()) {
		    if (nightlyRate == null) {
			throw new ServiceException(Error.parameter_null, "Nightly rate, pm id: " + propertyManagerInfo.getPropertyManagerId());
		    }
		    if (chargeType.equalsIgnoreCase(PaymentHelper.DEPOSIT_PAYMENT_METHOD)) {
			double depositPercentage = ReservationService.getDeposit(reservation, propertyManagerInfo);
			double firstNightlyRatePayment = nightlyRate * depositPercentage / 100;
			nightlyRate = nightlyRate - firstNightlyRatePayment;
			nightlyRate = nightlyRate > 0 ? nightlyRate : 0;
		    }
		}
	
		CommissionSeparatorUtil commissionSeparator = new CommissionSeparatorUtil(sqlSession, reservation, secondPayment, nightlyRate);
	
		double creditCardFee = 0.0;
		double pmsPaymentAmount = 0.0;
		double pmPaymentAmount = 0.0;
		double bookingpalPayment = 0.0;
		double partnerPayment = 0; // TODO
		double commission = 0.0;
		int partialIin = PaymentHelper.getLastDigits(cardNumber, 4);
		int partyid = Integer.valueOf(Model.decrypt(pos));
		//String pmsConfirmationId = reservation.getConfirmationId();
	
		if (commissionSeparator.isNetRate()) {
		    creditCardFee = commissionSeparator.getCreditCardFeeValue();
		    commission = commissionSeparator.getTotalCommissionValue();
		    bookingpalPayment = commissionSeparator.getBpCommissionValue();
		    pmsPaymentAmount = commissionSeparator.getPmsMarkupValue();
		    pmPaymentAmount = commissionSeparator.getPmCommissionValue();
		    partnerPayment = commissionSeparator.getChannelPartnerCommissionValue();
		} else {
		    bookingpalPayment = (Double.valueOf(propertyManagerInfo.getBpCommission()) / 100.) * secondPayment;
		    commission = secondPayment * product.getDiscount() / 100.;
		    creditCardFee = getCreditCardFee(propertyManagerInfo, secondPayment);
		    partnerPayment = commission - creditCardFee - bookingpalPayment;
		}

		boolean autopay = paymentGatewayProvider == null ? true : paymentGatewayProvider.isAutopay();
		pendingTransaction = new PendingTransaction(autopay, reservation, bookingpalPayment, secondPayment, propertyManagerInfo,
			commission, firstName, familyName, resultMap.get(GatewayHandler.TRANSACTION_ID), partialIin, partyid, partnerPayment,
			paymentGatewayId, phoneNumber, /*pmsConfirmationId,*/ PendingTransactionStatus.Active.status(), supplierId);
	
		pendingTransaction.setNetRate(commissionSeparator.isNetRate());
		pendingTransaction.setCreditCardFee(creditCardFee);
		pendingTransaction.setPmsPayment(pmsPaymentAmount);
		pendingTransaction.setPmCommissionValue(pmPaymentAmount);
		pendingTransaction.setPartnerPayment(partnerPayment);
		pendingTransaction.setAdditionalCommissionValue(additionalCommissionValue);
		pendingTransaction.setFundsHolder(propertyManagerInfo.getFundsHolder());
		return pendingTransaction;
    }

    /**
     * Create new PaymentTranasction using PendingTransaction.
     * 
     * @param sqlSession
     * @param reservation
     * @param pendingTransaction
     * @return
     */
    public static PaymentTransaction preparePaymentTransaction(SqlSession sqlSession, Reservation reservation, PendingTransaction pendingTransaction) {
     
    	Date currentDate = new Date();
    	Double amount = pendingTransaction.getChargeAmount();
    	Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
    	
    	PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setChargeDate(currentDate);
		paymentTransaction.setChargeType(PaymentHelper.SECOND_PAYMENT);
		paymentTransaction.setCreateDate(currentDate);
		paymentTransaction.setCurrency(pendingTransaction.getCurrency());
		paymentTransaction.setFinalAmount(reservation.getQuote()); // + creditCardFee
		paymentTransaction.setFundsHolder(pendingTransaction.getFundsHolder());
		paymentTransaction.setGatewayId(pendingTransaction.getPaymentGatewayId());
		paymentTransaction.setReservationId(Integer.valueOf(reservation.getId()));
		paymentTransaction.setServerId(1);
		paymentTransaction.setPartialIin(pendingTransaction.getPartialIin());
		paymentTransaction.setPartnerId(Integer.valueOf(agent.getId()));
		paymentTransaction.setPaymentMethod(PaymentProcessingTypeEnum.GATEWAY.type()); // 0 - credit card
		//paymentTransaction.setPmsConfirmationId(1);
		paymentTransaction.setSupplierId(Integer.valueOf(reservation.getOrganizationid()));				
		paymentTransaction.setAdditionalCommissionValue(pendingTransaction.getAdditionalCommissionValue());		
		paymentTransaction.setTotalAmount(amount);		
		
		paymentTransaction.setNetRate(pendingTransaction.isNetRate());
		
		paymentTransaction.setTotalBookingpalPayment(pendingTransaction.getBookingpalPayment());
		paymentTransaction.setPartnerPayment(pendingTransaction.getPartnerPayment());
		paymentTransaction.setPmsPayment(pendingTransaction.getPmsPayment());
		paymentTransaction.setPmCommissionValue(pendingTransaction.getPmCommissionValue());
		paymentTransaction.setTotalCommission(pendingTransaction.getCommission());
		paymentTransaction.setCreditCardFee(pendingTransaction.getCreditCardFee());	
		
		return paymentTransaction;
        
    }
        
        public static PaymentTransaction prepareInquiryTransaction(Reservation reservation, Product product, Integer agentId) {
        	double totalAmountValue = 2.75;
        	double totalCommissionValue = 2.75;
        	double bpCommissionValue = 1.25; 
        	double partnerPayment = 1.50;
        	double creditCardFee = 0.;
        	String status = GatewayHandler.INQUIRED;
        	Date currentDate = new Date();
        	String chargeType = null;
        	String currency = "USD";
        	Integer fundsHolder = FundsHolderEnum.BookingPal.value();
        	Integer reservationId = Integer.valueOf(reservation.getId());
        	Integer paymentMethod = PaymentProcessingTypeEnum.GATEWAY.type();
        	Integer supplierId = Integer.valueOf(product.getSupplierid());
        	Double additionalCommission = 0.;
        	boolean netRate = false;
        	
        	// PaymentTransaction initialization
        	/*
        	 * 	Current/Total Amount: $2.75
				Total Commission: $2.75
				BP Commission: $1.25
				Chanel commission: $1.50
        	 */
        	PaymentTransaction paymentTransaction = new PaymentTransaction();
        	paymentTransaction.setAdditionalCommissionValue(additionalCommission);
        	paymentTransaction.setChargeDate(currentDate);
        	paymentTransaction.setChargeType(chargeType);
        	paymentTransaction.setCreateDate(currentDate);
        	paymentTransaction.setCreditCardFee(creditCardFee);
        	paymentTransaction.setCurrency(currency);
        	paymentTransaction.setFinalAmount(totalAmountValue);
        	paymentTransaction.setFundsHolder(fundsHolder);
        	paymentTransaction.setGatewayId(null);
        	paymentTransaction.setGatewayTransactionId(null);
        	paymentTransaction.setMessage(null);
        	paymentTransaction.setNetRate(netRate);
        	paymentTransaction.setPartialIin(null);
        	paymentTransaction.setPartnerId(agentId);
        	paymentTransaction.setPartnerPayment(partnerPayment);
        	paymentTransaction.setPaymentMethod(paymentMethod);
        	paymentTransaction.setPmCommissionValue(0.);
        	paymentTransaction.setPmsConfirmationId(1);
        	paymentTransaction.setPmsPayment(0.);
        	paymentTransaction.setReservationId(reservationId);
        	paymentTransaction.setServerId(1);
        	paymentTransaction.setStatus(status);
        	paymentTransaction.setSupplierId(supplierId);
        	paymentTransaction.setTotalAmount(totalAmountValue);
        	paymentTransaction.setTotalBookingpalPayment(bpCommissionValue);
        	paymentTransaction.setTotalCommission(totalCommissionValue);
        	
        	return paymentTransaction;
        }
        
	public static PaymentTransaction preparePaymentTransaction(SqlSession sqlSession, Reservation reservation, PropertyManagerInfo propertyManagerInfo, Double amount, 
			ManagerToGateway managerToGateway, String cardNumber, Integer agentId, Product product, 
			Map<String, String> result, boolean secondPayment) throws ParseException {
		
		if(propertyManagerInfo == null) {
			LOG.warn("There is no property manager info found");
		}
		Integer paymentGatewayId = managerToGateway == null ? null : managerToGateway.getPaymentGatewayId();
		Integer fundsHolder = propertyManagerInfo != null ? propertyManagerInfo.getFundsHolder() : null;
		Date currentDate = new Date();
		Integer lastFour = PaymentHelper.getLastDigits(cardNumber, 4);
		String chargeType = propertyManagerInfo != null ? PaymentHelper.getChargeType(propertyManagerInfo, reservation) : null;
		Double bookingpalCommission = propertyManagerInfo.getBpCommission() / 100.;
		Double additionalCommissionValue = propertyManagerInfo.getAdditionalCommission() == null ? 0.0 : propertyManagerInfo.getAdditionalCommission();
		
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setChargeDate(currentDate);
		paymentTransaction.setChargeType(secondPayment ? PaymentHelper.SECOND_PAYMENT : chargeType);
		paymentTransaction.setCreateDate(currentDate);
		paymentTransaction.setCurrency(reservation.getCurrency());
		paymentTransaction.setFinalAmount(reservation.getQuote()); // + creditCardFee
		paymentTransaction.setFundsHolder(fundsHolder);
		paymentTransaction.setGatewayId(paymentGatewayId);
		paymentTransaction.setReservationId(Integer.valueOf(reservation.getId()));
		paymentTransaction.setServerId(1);
		paymentTransaction.setPartialIin(lastFour);
		paymentTransaction.setPartnerId(agentId);
		paymentTransaction.setPaymentMethod(PaymentProcessingTypeEnum.GATEWAY.type()); // 0 - credit card
		paymentTransaction.setPmsConfirmationId(1);
		paymentTransaction.setSupplierId(Integer.valueOf(product.getSupplierid()));		
		paymentTransaction.setAdditionalCommissionValue(additionalCommissionValue);
		paymentTransaction.setNetRate(propertyManagerInfo.isNetRate());
		
		if(propertyManagerInfo.isNetRate() && reservation.getPrice() != null) {
			
		    	Double nightlyRate = reservation.getNightlyrate();
		    	if (nightlyRate == null){
		    	    throw new ServiceException(Error.parameter_null, "Nightly rate, pm id: " + propertyManagerInfo.getPropertyManagerId());
		    	}		    	
			if (chargeType.equalsIgnoreCase(PaymentHelper.DEPOSIT_PAYMENT_METHOD)){
				double depositPercentage = ReservationService.getDeposit(reservation, propertyManagerInfo);
				nightlyRate = nightlyRate * depositPercentage / 100;				
			}
			
			CommissionSeparatorUtil commissionSeparator = new CommissionSeparatorUtil(sqlSession, reservation, amount, nightlyRate);

			Double crediCardFee = commissionSeparator.getCreditCardFeeValue();
			Double totalCommissionValue = commissionSeparator.getTotalCommissionValue();
			Double bpPaymentAmount = commissionSeparator.getBpCommissionValue();
			Double pmsPaymentAmount = commissionSeparator.getPmsMarkupValue();
			Double pmPaymentAmount = commissionSeparator.getPmCommissionValue(); 
			Double partnerPaymentAmount = commissionSeparator.getChannelPartnerCommissionValue();
			
			paymentTransaction.setTotalCommission(totalCommissionValue);
			paymentTransaction.setTotalBookingpalPayment(bpPaymentAmount);						
			paymentTransaction.setPmsPayment(pmsPaymentAmount);			
			paymentTransaction.setPmCommissionValue(pmPaymentAmount);
			paymentTransaction.setPartnerPayment(partnerPaymentAmount);
			paymentTransaction.setCreditCardFee(crediCardFee);			
			
		} else if(amount != null) {
			Double ccFee = getCreditCardFee(propertyManagerInfo, amount);
			paymentTransaction.setCreditCardFee(ccFee);
			
			if(product.getDiscount() != null){
				paymentTransaction.setTotalCommission(amount * (product.getDiscount() / 100.));	
			}
			paymentTransaction.setTotalBookingpalPayment(amount * bookingpalCommission);
			
			// TODO : if (getTotalCommission() > 0)
			paymentTransaction.setPartnerPayment(paymentTransaction.getTotalCommission() - ccFee - paymentTransaction.getTotalBookingpalPayment());
		}
		if(amount != null) {
			paymentTransaction.setTotalAmount(amount);
		}
		
		if(result != null) {
			paymentTransaction.setStatus(result.get(GatewayHandler.STATE));
			paymentTransaction.setGatewayTransactionId(result.get(GatewayHandler.TRANSACTION_ID));
			paymentTransaction.setMessage(result.get(GatewayHandler.ERROR_MSG));
		}
		
		// the amount that will be paid to the partner
		// when calculating this value find the partner in the partner table and use the percentage entered for this partner
		// paymentTransaction.setPartnerPayment(paymentTransaction.getTotalCommission() - creditCardFee - paymentTransaction.getTotalBookingpalPayment());
		
		return paymentTransaction;
	}
	
	public static Reservation prepareReservation(Party agent, Party customer, Product product, PropertyManagerInfo propertyManager, String fromdate, String todate, String notes, String productid,
			Integer adult, Integer child)
			throws ParseException {
		Reservation reservation = new Reservation();
		reservation.setActorid(Party.NO_ACTOR);
		reservation.setAgentid(agent.getId());
		reservation.setAltpartyid(product.getAltpartyid());
		reservation.setCustomerid(customer.getId());
		reservation.setEmailaddress(customer.getEmailaddress());
		reservation.setCustomername(customer.getName());
		reservation.setPhoneNumber(customer.getDayphone());
		reservation.setMessage(notes);
		reservation.setOrganizationid(product.getSupplierid());
		reservation.setFromdate(JSONService.DF.parse(fromdate));
		reservation.setTodate(JSONService.DF.parse(todate));
		if (reservation.getDuration(Time.DAY) < 1.0) {throw new ServiceException(Error.date_range, fromdate + " - " + todate);}
		reservation.setDate(new Date());
		reservation.setDuedate(reservation.getDate());
		reservation.setDonedate(null);
		reservation.setArrivaltime(propertyManager.getCheckInTime().toString());
		reservation.setDeparturetime(propertyManager.getCheckOutTime().toString());
		reservation.setUnit(product.getUnit());
		reservation.setCurrency(product.getCurrency());
		reservation.setNotes(notes);
//		reservation.setState(Reservation.State.Initial.name());
		reservation.setProductid(productid);
		reservation.setProduct(product);
		reservation.setAdult(adult);
		reservation.setChild(child);
		return reservation;
	}
	
	public static String getChargeType(PropertyManagerInfo propertyManagerInfo, Reservation reservation) throws ParseException {
		if (propertyManagerInfo.getPaymentProcessingType() == 1) { // 1 - Gateway
			if (propertyManagerInfo.getNumberOfPayments() == 2 && new Date().before(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo))) {
				return DEPOSIT_PAYMENT_METHOD;
			} else if (propertyManagerInfo.getNumberOfPayments() == 1 || (propertyManagerInfo.getNumberOfPayments() == 2 && !new Date().before(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo)))) {
				return FULL_PAYMENT_METHOD;
			} else {
				throw new ServiceException(Error.payment_type_unsupported);
			}
		} else if (propertyManagerInfo.getPaymentProcessingType() == 2) { // 2 - Email method
			return MAIL_PAYMENT_METHOD;
		} else if (propertyManagerInfo.getPaymentProcessingType() == 3) { // 3 - PMS API calling method 
			if (propertyManagerInfo.getNumberOfPayments() == 2 && new Date().before(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo))) {
				return API_DEPOSIT_PAYMENT_METHOD;
			} else if (propertyManagerInfo.getNumberOfPayments() == 1 || (propertyManagerInfo.getNumberOfPayments() == 2 && !new Date().before(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo)))) {
				return API_FULL_PAYMENT_METHOD;
			} else {
				throw new ServiceException(Error.payment_type_unsupported);
			}
		} else {
			throw new ServiceException(Error.payment_type_unsupported);
		}
	}
	
	public static GatewayHandler initializeHandler(PropertyManagerInfo propertyManagerInfo, ManagerToGateway managerToGateway, CreditCard creditCard) {
		if(PaymentHelper.isPaymentSupported(propertyManagerInfo) && managerToGateway == null) { 
			throw new ServiceException(Error.database_cannot_find_manager_to_gateway, String.valueOf(propertyManagerInfo.getPropertyManagerId()));
		}
		if(!PaymentHelper.isPaymentSupported(propertyManagerInfo)) {
			return null;
		}
		int paymentGatewayId = managerToGateway.getPaymentGatewayId();
		GatewayInfo gatewayInfo = new GatewayInfo(managerToGateway, creditCard);
		GatewayHandler handler = PaymentGatewayHandlerFactory.getHandler(paymentGatewayId, gatewayInfo);
		if(handler == null) { throw new ServiceException(Error.gateway_handler, String.valueOf(paymentGatewayId)); }
		return handler;
	}
	
	public static boolean isPaymentSupported(PropertyManagerInfo propertyManagerInfo) {
		return propertyManagerInfo.getPaymentProcessingType() == 1 || propertyManagerInfo.getPaymentProcessingType() == 3;
	}
	
	public static PaymentGatewayProvider getGatewayByName(SqlSession sqlSession, String name) {
		return sqlSession.getMapper(PaymentGatewayProviderMapper.class).readByName(name);
	}
	
	public static Double getFirstPayment(Reservation reservation, PropertyManagerInfo propertyManagerInfo) throws ParseException {
		double depositPercentage = ReservationService.getDeposit(reservation, propertyManagerInfo);
		double firstPayment = reservation.getQuote() * depositPercentage / 100.;
		Double amount = getAmountWithTwoDecimals(firstPayment);
		return amount;
	}
	
	public static Double getSecondPayment(Reservation reservation, PropertyManagerInfo propertyManagerInfo) throws ParseException {
		double firstPayment = getFirstPayment(reservation, propertyManagerInfo);
		double secondPayment = reservation.getQuote() - firstPayment;
		secondPayment = secondPayment > 0 ? secondPayment : 0;
		return getAmountWithTwoDecimals(secondPayment);
	}
	
	public static Double roundAmountTwoDecimals(String valueToFormat) throws ParseException {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		df.setParseBigDecimal(true);
		BigDecimal bd = (BigDecimal) df.parseObject(valueToFormat);
		return getAmountWithTwoDecimals(bd.doubleValue());
	}
	
	public static Double getAmountWithTwoDecimals(Double valueToFormat) throws ParseException {
		String amount = new DecimalFormat("0.00").format(valueToFormat);
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		Number amountNumber = format.parse(amount);
		return amountNumber.doubleValue();
	}
	
	public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		PaymentTransaction transaction = sqlSession.getMapper(PaymentTransactionMapper.class).read(1682);
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(String.valueOf(transaction.getReservationId()));
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		Double amount = reservation.getPrice();
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(transaction.getSupplierId());
		Integer fundsHolder = propertyManagerInfo != null ? propertyManagerInfo.getFundsHolder() : null;
		boolean pmHolder = fundsHolder == ManagerToGateway.PROPERTY_MANAGER_HOLDER;
		Double creditCardFee = getCreditCardFee(propertyManagerInfo, amount);
		
		Double totalCommission = amount * (product.getDiscount() / 100.);
		System.out.println("Amount: " + amount);
		System.out.println("Discount percentage: " + product.getDiscount() / 100.);
		CommissionCalculationUtil calculationUtil = new CommissionCalculationUtil(sqlSession, reservation, reservation.getPrice(), reservation.getExtra());
		Double totalBookingpalPayment = calculationUtil.getBpCommissionValue();
		System.out.println("Total commission: " + totalCommission + "\n" +
				"Credit card fee: " + creditCardFee + "\n" +
				"Total bookingpal payment: " + totalBookingpalPayment);
		System.out.println(totalCommission - creditCardFee - totalBookingpalPayment);
		// paymentTransaction.getTotalCommission() - creditCardFee - paymentTransaction.getTotalBookingpalPayment();
	}
	
	public static PropertyManagerCancellationRule getRuleForCurrentDate(SqlSession sqlSession, Reservation reservation, Integer propertyManagerId) {
		List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(propertyManagerId);
		NavigableSet<PropertyManagerCancellationRule> sortedRules = new TreeSet<PropertyManagerCancellationRule>(new RuleComparator());
		sortedRules.addAll(rules);
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance(); 
		
		Iterator<PropertyManagerCancellationRule> ruleIterator = sortedRules.descendingIterator();
		while(ruleIterator.hasNext()) {
			PropertyManagerCancellationRule rule = ruleIterator.next();
			calendar.setTime(reservation.getFromdate());
			calendar.add(Calendar.DAY_OF_MONTH, -rule.getCancellationDate());
			Date cancellationDate = calendar.getTime();
			if(currentDate.before(cancellationDate)) {
				return rule;
			}
		}
		return null;
	}
	
	public static List<Date> getCancellationDates(SqlSession sqlSession, Reservation reservation, PropertyManagerInfo propertyManagerInfo) {
		Date fromDate = reservation.getFromdate();
		Calendar calendar = Calendar.getInstance(); 
		List<Date> cancellationDates = new ArrayList<Date>();
		List<PropertyManagerCancellationRule> propertyManagerCancellationRules = 
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(propertyManagerInfo.getPropertyManagerId());
		
		if(propertyManagerCancellationRules != null && !propertyManagerCancellationRules.isEmpty()) {
			for(PropertyManagerCancellationRule rule : propertyManagerCancellationRules) {
				calendar.setTime(fromDate); 
				calendar.add(Calendar.DAY_OF_MONTH, -rule.getCancellationDate());
				cancellationDates.add(calendar.getTime());
			}
		} else {
			throw new ServiceException(Error.cancel_unavailable, ": there is no cancellation dates");
		}
		return cancellationDates;
	}
	
	public static List<Integer> getCancellationInDays(SqlSession sqlSession, Reservation reservation, PropertyManagerInfo propertyManagerInfo) {
		List<PropertyManagerCancellationRule> propertyManagerCancellationRules = 
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(propertyManagerInfo.getPropertyManagerId());
		List<Integer> days = new ArrayList<Integer>();
		if(propertyManagerCancellationRules != null && !propertyManagerCancellationRules.isEmpty()) {
			for(PropertyManagerCancellationRule rule : propertyManagerCancellationRules) {
				days.add(rule.getCancellationDate());
			}
		} else {
			throw new ServiceException(Error.cancel_unavailable, ": there is no cancellation dates");
		}
		return days;
	}
	
	public static Set<CancellationItem> getCancellationItems(SqlSession sqlSession, Reservation reservation) throws ParseException {
		Date fromDate = reservation.getFromdate();
		List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(Integer.valueOf(reservation.getSupplierid()));
		Set<CancellationItem> items = new TreeSet<CancellationItem>();
		Date currentDate = new Date();
		if(rules != null && !rules.isEmpty()) {
			for(PropertyManagerCancellationRule rule : rules) {
				Double cancellationAmount = getCancellationAmountWithFee(reservation, rule);
				Date cancellationDate = getCancellationDate(fromDate, rule);
				if(!currentDate.after(cancellationDate)) {
					CancellationItem item = new CancellationItem(AbstractReservation.dateToString(cancellationDate),
							Double.valueOf(rule.getCancellationRefund()),
							PaymentHelper.getAmountWithTwoDecimals(cancellationAmount),rule.getCancellationTransactionFee(), rule.getCancellationDate());
					items.add(item);
				}
			}
		}
		if(items.isEmpty()) {
			CancellationItem item = new CancellationItem("", 0., 0.,0.,0);
			items.add(item);
		}
		return items;
	}
	
	public static Date getCancellationDate(Date fromDate, PropertyManagerCancellationRule rule) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		calendar.add(Calendar.DAY_OF_MONTH, -rule.getCancellationDate());
		Date cancellationDate = calendar.getTime();
		return cancellationDate;
	}
	
	public static Double getCancellationAmountWithoutFee(Reservation reservation, PropertyManagerCancellationRule propertyManagerCancellationRule, String currency) {
		SqlSession sqlSession = RazorServer.openSession();
		Double reservationQuote = reservation.getQuote();
		try {
			reservationQuote = PaymentService.convertCurrency(sqlSession, reservation.getCurrency(), currency, reservation.getQuote());
		} finally {
			sqlSession.close();
		}
		Double cancellationAmount = reservationQuote * (propertyManagerCancellationRule.getCancellationRefund() / 100.); 
		return cancellationAmount;
	}
	
	public static Double getCancellationAmountWithFee(Reservation reservation, PropertyManagerCancellationRule rule) {
		Double cancellationAmount = 0.;
		if(rule.getCancellationRefund() > 0) { 
			cancellationAmount = (reservation.getQuote() * (rule.getCancellationRefund() / 100.)) - rule.getCancellationTransactionFee();
		}
		return cancellationAmount;
	}
	
	public static Double getCreditCardFee(PropertyManagerInfo pmi, Double amount ) {
		double creditCardFee = 0.0;
		if (pmi == null || amount == null) {
			return creditCardFee;
		}
		if (pmi.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER){
			creditCardFee = amount * CommissionCalculationUtil.CREDIT_CARD_FEE;
		}			 
		return creditCardFee;
	}
	
	public static Map<String, String> processPayment(SqlSession sqlSession, PaymentGatewayProvider paymentGatewayProvider, Double amountToCharge, Reservation reservation, GatewayHandler handler,
			String currency, CreditCard creditCard, Integer paymentGatewayId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(paymentGatewayProvider.getName().equals(PaymentGatewayHolder.PAYPAL) || paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)) {
			resultMap = handler.createPaymentByCreditCard(sqlSession, currency, amountToCharge, reservation.getId());
		} else {
			resultMap = handler.createCustomerPaymentProfile(sqlSession, Integer.valueOf(reservation.getId()), creditCard, paymentGatewayId);
			if(resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
				resultMap = handler.createPaymentByProfile(sqlSession, reservation.getCurrency(), amountToCharge, Integer.valueOf(reservation.getId()));
			}
		}
		return resultMap;
	}
	
	public static Double convertToDefaultMbpCurrency(SqlSession sqlSession, Reservation reservation, PropertyManagerInfo propertyManagerInfo, Double amount, String currency) throws ParseException {
		SqlSession convertionSqlSession = RazorServer.openSession();
		try {
			Double bookingpalDefaultCurrencyAmount = amount;
			if(propertyManagerInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER) {
				bookingpalDefaultCurrencyAmount = PaymentService.convertCurrency(sqlSession, currency, DEFAULT_BOOKINGPAL_CURRENCY, amount);
				bookingpalDefaultCurrencyAmount = getAmountWithTwoDecimals(bookingpalDefaultCurrencyAmount);
				ConvertionInfo convertion = new ConvertionInfo();
				convertion.setConvertionDate(new Date());
				convertion.setFromAmount(amount);
				convertion.setFromCurrency(currency);
				convertion.setRate(bookingpalDefaultCurrencyAmount / amount);
				convertionSqlSession.getMapper(ConvertionInfoMapper.class).create(convertion);
				convertionSqlSession.commit();
				reservation.setCurrency(DEFAULT_BOOKINGPAL_CURRENCY);
			}
			return bookingpalDefaultCurrencyAmount;
		} finally {
			convertionSqlSession.close();
		}
	}

	public static Double amountDifference(SqlSession sqlSession, Reservation reservation, Double amountToCheck, String currency) throws ParseException {
		String[] livePricingIds = PartnerService.getPartnerlivePricingIds();
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
		if(reservation.getAltpartyid() == null || !Arrays.asList(livePricingIds).contains(reservation.getAltpartyid())) {
			ReservationService.computePrice(sqlSession, reservation, currency);
		} else {
			PartnerService.readPrice(sqlSession, reservation, product.getAltid(), currency);
			
			ReservationService.computeLivePrice(sqlSession, reservation, null, currency);
		}
		
		String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
		Double firstPayment = PaymentHelper.getFirstPayment(reservation, propertyManagerInfo);
		Double secondPayment = PaymentHelper.getSecondPayment(reservation, propertyManagerInfo);

		Double amountToCharge = chargeType.equalsIgnoreCase(PaymentHelper.FULL_PAYMENT_METHOD) ? firstPayment + secondPayment : firstPayment;
		
		return Math.abs(amountToCharge - amountToCheck); // if difference between prices is less than 1, program will assume it as the same amount
	}
	
	public static boolean currencyExists(String currency) {
		return getAllCurrencies().contains(currency);
	}
	
	public static List<String> getAllCurrencies() {
		Locale[] locales = Locale.getAvailableLocales();
		List<String> currencies = new ArrayList<String>();
		for(Locale locale : locales) {
			try {
				java.util.Currency curr = Currency.getInstance(locale);
				currencies.add(curr.getCurrencyCode());
			} catch (Throwable e) {
				LOG.info("Missing currency for " + locale.getDisplayName());
			}
		}
		return currencies;
	}
	
	public static boolean isApiPaymentMethod(String paymentMethod) {
		return Arrays.asList(API_PAYMENT_METHODS).contains(paymentMethod);
	}
	
	public static boolean isDepositPaymentMethod(String paymentMethod) {
		return Arrays.asList(DEPOSIT_PAYMENT_METHODS).contains(paymentMethod);
	}
	
	public static boolean isFullPaymentMethod(String paymentMethod) {
		return Arrays.asList(FULL_PAYMENT_METHODS).contains(paymentMethod);
	}

	private static class RuleComparator implements Comparator<PropertyManagerCancellationRule> {

		@Override
		public int compare(PropertyManagerCancellationRule o1, PropertyManagerCancellationRule o2) {
			return o1.getCancellationDate().compareTo(o2.getCancellationDate());
		}
		
	}
}
