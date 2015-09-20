package net.cbtltd.rest.anet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.ResponseField;
import net.authorize.cim.Result;
import net.authorize.cim.Transaction;
import net.authorize.cim.TransactionType;
import net.authorize.data.Order;
import net.authorize.data.cim.CustomerProfile;
import net.authorize.data.cim.PaymentProfile;
import net.authorize.data.cim.PaymentTransaction;
import net.authorize.data.creditcard.CreditCard;
import net.authorize.data.echeck.BankAccountType;
import net.authorize.data.echeck.ECheckType;
import net.authorize.data.xml.Address;
import net.authorize.data.xml.BankAccount;
import net.authorize.data.xml.CustomerType;
import net.authorize.data.xml.Payment;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.CustomerPaymentProfileMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.BankAccountInfo;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.CustomerPaymentProfile;
import net.cbtltd.shared.finance.gateway.GatewayInfo;

import org.apache.ibatis.session.SqlSession;

public class ANetHandler implements GatewayHandler {

	private GatewayInfo anet = null;
	private static final String PRODUCTION_TRANSACT_DLL = "https://secure.authorize.net/gateway/transact.dll";
	private static final String SANDBOX_TRANSACT_DLL = "https://test.authorize.net/gateway/transact.dll";

	public ANetHandler(GatewayInfo anet) {
		this.setAnet(anet);
	}
	
	public static void main(String[] args) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		net.cbtltd.shared.finance.gateway.CreditCard cc = new net.cbtltd.shared.finance.gateway.CreditCard();
		cc.setNumber("5308170027097160");
		cc.setMonth("03");
		cc.setYear("2016");
		cc.setSecurityCode("927");
		cc.setType(CreditCardType.MASTER_CARD);
		GatewayInfo anet = new GatewayInfo();
		anet.setId("271386");
		anet.setToken("4vc2A6Q94S9qN7et");
		anet.setCreditCartd(cc);
		ANetHandler handler = new ANetHandler(anet);
		Map<String, String> result = handler.createCustomerPaymentProfile(sqlSession, 1, cc, 2);
		
		for(Map.Entry<String, String> entry : result.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> createCustomerPaymentProfile(SqlSession sqlSession, Integer reservationId, net.cbtltd.shared.finance.gateway.CreditCard creditCardInfo, Integer gatewayId) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId.toString());
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		// Create a merchant object:
		Merchant merchant = buildMerchant(getAnet().getId(), getAnet().getToken());

		// Construct a transaction to create a customer profile along with a customer payment profile. Also a shipping profile if you want
		Transaction transaction = merchant.createCIMTransaction(TransactionType.CREATE_CUSTOMER_PROFILE);

		// Add customer profile information
		CustomerProfile customerProfile = CustomerProfile.createCustomerProfile();
		customerProfile.setMerchantCustomerId(reservationId.toString());
		customerProfile.setEmail(customer.getEmailaddress());

		// Add customer payment profile information
		CreditCard creditCard = CreditCard.createCreditCard();
		creditCard.setCreditCardNumber(creditCardInfo.getNumber());
		creditCard.setExpirationMonth(creditCardInfo.getMonth());
		creditCard.setExpirationYear(creditCardInfo.getYear());
		
		Payment payment = Payment.createPayment(creditCard);
		PaymentProfile paymentProfile = PaymentProfile.createPaymentProfile();
		paymentProfile.setCustomerType(CustomerType.INDIVIDUAL);
		paymentProfile.addPayment(payment);

		// Add customer billing info
		Address address = new Address();
		address.setAddress(CommonUtils.encodeTo88591(PartyService.getSimpleAddress(customer)));
		address.setCity(CommonUtils.encodeTo88591(PartyService.getAddressValue(customer, net.cbtltd.shared.party.Address.Type.city)));
		address.setCountry(CommonUtils.encodeTo88591(customer.getCountry()));
		address.setFirstName(CommonUtils.encodeTo88591(customer.getFirstName()));
		address.setLastName(CommonUtils.encodeTo88591(customer.getFamilyName()));
		address.setPhoneNumber(CommonUtils.encodeTo88591(customer.getDayphone()));
		address.setState(CommonUtils.encodeTo88591(customer.getState()));
		address.setZipPostalCode(CommonUtils.encodeTo88591(customer.getPostalcode()));
		paymentProfile.setBillTo(address);

		transaction.setCustomerProfile(customerProfile);
		transaction.addPaymentProfile(paymentProfile);
		
		// Send request
		Result<Transaction> result = (Result<Transaction>) merchant.postTransaction(transaction);

		// Check if the response is positive and signal if it is not
		if (result.isOk()) {
			String customerProfileId = result.getCustomerProfileId();
			String customerPaymentProfileId = result.getCustomerPaymentProfileIdList().get(0);
			
			CustomerPaymentProfile customerPaymentProfile = new CustomerPaymentProfile(reservationId, customerProfileId, customerPaymentProfileId, gatewayId);
			sqlSession.getMapper(CustomerPaymentProfileMapper.class).create(customerPaymentProfile);
		}
		return getProfileResultMap(result);
	}
	
	@SuppressWarnings("unchecked")
	public Result<Transaction> createBankPaymentProfile(SqlSession sqlSession, Integer reservationId, BankAccountInfo bankAccountInfo,  
			String apiLoginId, String transactionKey) {
		// Create a merchant object:
		Environment environment = RazorServer.isLive() ? Environment.PRODUCTION : Environment.SANDBOX;
		Merchant merchant = Merchant.createMerchant(environment, apiLoginId, transactionKey);

		// Construct a transaction to create a customer profile along with a customer payment profile. Also a shipping profile if you want
		Transaction transaction = merchant.createCIMTransaction(TransactionType.CREATE_CUSTOMER_PROFILE);

		// Add customer profile information
		CustomerProfile customerProfile = CustomerProfile.createCustomerProfile();
		customerProfile.setMerchantCustomerId(reservationId.toString());
		transaction.setCustomerProfile(customerProfile);

		// Add customer payment profile information
		BankAccount bankAccount = BankAccount.createBankAccount();
		bankAccount.setBankAccountName(bankAccountInfo.getBankAccountName());
		bankAccount.setBankAccountNumber(bankAccountInfo.getBankAccountNumber());
		bankAccount.setBankAccountType(BankAccountType.BUSINESSCHECKING); // TODO : http://www.authorize.net/support/CNP/helpfiles/Tools/Virtual_Terminal/Submit_a_Bank_Account_Charge.htm
		bankAccount.setBankCheckNumber(bankAccountInfo.getBankCheckNumber());
		bankAccount.setBankName(bankAccountInfo.getBankName());
		bankAccount.setECheckType(ECheckType.UNKNOWN); // TODO : http://www.authorize.net/support/CNP/helpfiles/Miscellaneous/Pop-up_Terms/ALL/eCheck.Net_Type.htm
		bankAccount.setRoutingNumber(bankAccountInfo.getRoutingNumber());
		Payment payment = Payment.createPayment(bankAccount);
		PaymentProfile paymentProfile = PaymentProfile.createPaymentProfile();
		paymentProfile.setCustomerType(CustomerType.INDIVIDUAL);
		paymentProfile.addPayment(payment);
		transaction.addPaymentProfile(paymentProfile);

		// Send request
		return (Result<Transaction>) merchant.postTransaction(transaction);
		// Check if the response is positive and signal if it is not
//		if (result.isOk()) {
//			String customerProfileId = result.getCustomerProfileId();
//			String customerPaymentProfileId = result.getCustomerPaymentProfileIdList().get(0);
//			
//			CustomerPaymentProfile customerPaymentProfile = new CustomerPaymentProfile(reservationId, customerProfileId, customerPaymentProfileId, 0);
//			sqlSession.getMapper(CustomerPaymentProfileMapper.class).create(customerPaymentProfile);
//		}
//		return getProfileResultMap(result);
	}
	
	private static Map<String, String> getProfileResultMap(Result<Transaction> result) {
		Map<String, String> resultMap = initializeResultMap();
		if(result.isOk()) {
			String transactionId = Model.ZERO;
			resultMap.put(TRANSACTION_ID, transactionId);
			resultMap.put(STATE, ACCEPTED);
		} else if(result.isError()) {
			String errorMessage = "";
			if(result.getResultCode().equals("E00014")) {
				errorMessage = "The referenced transaction does not meet the criteria for issuing a credit.";
			} else {
				errorMessage = result.getMessages().get(0).getText();
			}
			resultMap.put(ERROR_MSG, errorMessage);
			resultMap.put(STATE, FAILED);
			throw new ServiceException(Error.widget_book, errorMessage);
		}
		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createPaymentByCreditCard(SqlSession sqlSession, String currency, Double amount, String reservationId) throws Exception {
		Merchant merchant = buildMerchant(getAnet().getId(), getAnet().getToken());

		// Create AUTH transaction
		net.authorize.aim.Transaction transaction = merchant.createAIMTransaction(net.authorize.TransactionType.AUTH_CAPTURE, new BigDecimal(amount));
		transaction.setCurrencyCode(currency);
		transaction.setCreditCard(getAnet().getCreditCard().getANetCard());
		Map<String, String> resultMap = postAIMTransaction(merchant, transaction);
		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createRefund(List<net.cbtltd.shared.PaymentTransaction> paymentTransactions, Double refundAmount) throws Exception {
		Merchant merchant = buildMerchant(getAnet().getId(), getAnet().getToken());
		Map<String, String> resultMap = null;
		for(net.cbtltd.shared.PaymentTransaction paymentTransaction : paymentTransactions) {
			double payedAmount = paymentTransaction.getTotalAmount();
			net.authorize.data.creditcard.CreditCard creditCard = net.authorize.data.creditcard.CreditCard.createCreditCard();
			creditCard.setCreditCardNumber(getAnet().getCreditCard().getNumber());
			if(payedAmount < refundAmount) {
				final net.authorize.aim.Transaction transaction = merchant.createAIMTransaction(net.authorize.TransactionType.CREDIT, new BigDecimal(payedAmount));
				transaction.setCreditCard(creditCard);
				transaction.setTransactionId(paymentTransaction.getGatewayTransactionId());
				resultMap = postAIMTransaction(merchant, transaction);
				refundAmount -= payedAmount;
			} else {
				final net.authorize.aim.Transaction transaction = merchant.createAIMTransaction(net.authorize.TransactionType.CREDIT, new BigDecimal(Math.abs(refundAmount)));
				transaction.setCreditCard(creditCard);
				transaction.setTransactionId(paymentTransaction.getGatewayTransactionId());
				resultMap = postAIMTransaction(merchant, transaction);
			}
		}
		
		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> createPaymentByProfile(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
		CustomerPaymentProfile customerPaymentProfile = sqlSession.getMapper(CustomerPaymentProfileMapper.class).readByReservation(reservationId);
		if(customerPaymentProfile == null) {
			throw new ServiceException(Error.database_cannot_find, "customer payment profile");
		}
		Merchant merchant = buildMerchant(getAnet().getId(), getAnet().getToken());
		// Create payment transaction
		Transaction transaction = merchant.createCIMTransaction(TransactionType.CREATE_CUSTOMER_PROFILE_TRANSACTION);

		// Set customer ID and customer's payment profile ID that you want to be billed
		transaction.setCustomerProfileId(customerPaymentProfile.getCustomerProfileId());
		transaction.setCustomerPaymentProfileId(customerPaymentProfile.getCustomerPaymentProfileId());

		// Add information about the transaction
		PaymentTransaction paymentTransaction = PaymentTransaction.createPaymentTransaction();
		Order order = Order.createOrder();
		order.setTotalAmount(new BigDecimal(amount));
//		order.setDescription("Test charge");
		paymentTransaction.setOrder(order);
		paymentTransaction.setTransactionType(net.authorize.TransactionType.AUTH_CAPTURE);
		transaction.setPaymentTransaction(paymentTransaction);

		// Send request and check the response
		Result<Transaction> result = null;
		Map<String, String> resultMap = initializeResultMap();
		try {
			result = (Result<Transaction>) merchant.postTransaction(transaction);
		} catch (Throwable ex) {
			resultMap.put(STATE, FAILED);
			throw new ServiceException(Error.widget_book);
		}
		
		if(result.isOk()) {
			String transactionId = result.getDirectResponseList().get(0).getDirectResponseMap().get(ResponseField.TRANSACTION_ID);
			resultMap.put(TRANSACTION_ID, transactionId);
			resultMap.put(STATE, ACCEPTED);
		} else if(result.isError()) {
			String errorMessage = result.getMessages().get(0).getText();
			resultMap.put(ERROR_MSG, errorMessage);
			resultMap.put(STATE, FAILED);
//			throw new ServiceException(Error.widget_book, errorMessage);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, String> authorizePayment(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
		throw new ServiceException(Error.not_implemented, "for Authorize.net");
	}
	
	@Override
	public Map<String, String> capturePayment(SqlSession sqlSession, String currency, Integer centAmount, Integer reservationId, String transactionId) {
		throw new ServiceException(Error.not_implemented, "for Authorize.net");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> postAIMTransaction(Merchant merchant, net.authorize.aim.Transaction transaction) {
		net.authorize.aim.Result<net.authorize.aim.Transaction> result = null;
		Map<String, String> resultMap = initializeResultMap();
		try {
			if(!RazorServer.isLive()) {
//				transaction.getRequestMap().put("x_test_request", net.authorize.aim.Transaction.TRUE);
			}
			result = (net.authorize.aim.Result<net.authorize.aim.Transaction>) merchant.postTransaction(transaction);
		} catch (Throwable ex) {
			resultMap.put(STATE, FAILED);
			throw new ServiceException(Error.widget_book);
		}
		resultMap.put(TRANSACTION_ID, result.getTarget().getTransactionId());
		if(result.isApproved()) {
			resultMap.put(STATE, ACCEPTED);
		} else if(result.isDeclined()) {
			resultMap.put(ERROR_MSG, result.getResponseText());
			resultMap.put(STATE, DECLINED);
		} else if(result.isError()) {
			String errorMessage = result.getResponseText();
			if(result.getReasonResponseCode().name().equals("RRC_3_33")) {
				errorMessage = "The referenced transaction does not meet the criteria for issuing a credit.";
			}
			resultMap.put(ERROR_MSG, errorMessage);
			resultMap.put(STATE, FAILED);
		}
		return resultMap;
	}
	
	private Merchant buildMerchant(String apiLoginId, String transactionKey) {
		Environment environment = RazorServer.isLive() ? Environment.PRODUCTION : Environment.SANDBOX;
		return Merchant.createMerchant(environment, apiLoginId, transactionKey);
	}
	
	private static Map<String, String> initializeResultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TRANSACTION_ID, null);
		map.put(ERROR_MSG, null);
		map.put(STATE, null);
		
		return map;
	}

	public String getPostUrl() {
		return RazorServer.isLive() ? PRODUCTION_TRANSACT_DLL : SANDBOX_TRANSACT_DLL;
	}

	public GatewayInfo getAnet() {
		return anet;
	}

	public void setAnet(GatewayInfo anet) {
		this.anet = anet;
	}

}
