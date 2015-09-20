package net.cbtltd.rest.rent;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.rent.paymentrequest.CreateSimpleCreditCardToken;
import net.cbtltd.rest.rent.paymentrequest.CreditCardPayment;
import net.cbtltd.rest.rent.paymentrequest.RefundPayment;
import net.cbtltd.rest.rent.paymentresponse.CreditCardPaymentResponse;
import net.cbtltd.rest.rent.paymentresponse.RentPaymentError;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.CustomerPaymentProfileMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.server.util.Poster;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.CustomerPaymentProfile;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;

import org.apache.ibatis.session.SqlSession;

public class RentHandler implements GatewayHandler {

	private static final String PROD_API_URL = "https://rentpayment.com/api/2";
	private static final String DEMO_API_URL = "https://demo.rentpayment.com/api/2";
	private final String SUCCESS = "<CreditCardPaymentResponse>";
	private final String ERROR = "<Error>";
	private final String PAYMENT_CURRENCY = "USD";
	private GatewayInfo rent = null;
	private RentProperty property = null;

	public RentHandler(GatewayInfo rent) {
		this.setRent(rent);
		try {
			property = MarshallerHelper.fromXml(RentProperty.class, rent.getAdditional());
		} catch (JAXBException e) {
			property = null;
		}
	}

	public static void main(String[] args) throws Exception {
		
		
		GatewayInfo gatewayInfo = new GatewayInfo(CreditCardType.VISA, "4111111111111111", "01", "2016", "John", "Doe", ""); 
		RentHandler handler = new RentHandler(gatewayInfo);
		SqlSession sqlSession = RazorServer.openSession();
		handler.createPaymentByCreditCard(sqlSession, "EUR", 1500.00, "21199272");
		
		/*CreateSimpleCreditCardToken requestCardToken =  handler.createCreditCardToken();		
		String response = Poster.postXmlRequest("https://demo.rentpayment.com/api/2", requestCardToken);
		System.out.println(response);
		
		if (!StringUtils.isEmpty(response) && !response.contains("<Error>")){
			JAXBContext jaxbContext = JAXBContext.newInstance(CreateSimpleCreditCardTokenResponse.class);		 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(response);
			CreateSimpleCreditCardTokenResponse cardTokenResponse = (CreateSimpleCreditCardTokenResponse) jaxbUnmarshaller.unmarshal(reader);
			
			if (cardTokenResponse != null){
				CreditCardPayment creditCardPayment =  handler.generateSecondPayment(cardTokenResponse.getToken());
				//creditCardPayment.setAmount(new Amount("USD", 15000));
				creditCardPayment.setAmount(15000);
				
				String paymentResponse = Poster.postXmlRequest("https://demo.rentpayment.com/api/2", creditCardPayment);
				System.out.println(paymentResponse);
			}
			
		}*/
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createPaymentByCreditCard(SqlSession sqlSession, String currency, Double amount, String reservationId) throws Exception {
		Map<String, String> resultMap = initializeResultMap();		
		if (amount == null) throw new ServiceException(Error.card_amount);
		SqlSession session = RazorServer.openSession();
		
		Reservation reservation = session.getMapper(ReservationMapper.class).read(reservationId);
		if (reservation == null){
			throw new ServiceException(Error.database_cannot_find, "reservation");
		}
		Party customer = session.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (customer == null){
			throw new ServiceException(Error.database_cannot_find, "party");
		}
				
		
		CreditCardPayment request = generateCreditCardPayment(customer);
		//CreditCardPayment request  = generateTestCCPayment("");
		
		if(!PAYMENT_CURRENCY.equals(currency)) {
			Double exchangeRate = WebService.getRate(session, currency, PAYMENT_CURRENCY, new Date());
			amount *= exchangeRate;
		}
		int centsAmount = (int)(amount * 100);
		//Amount amountObject = new Amount(currency, centsAmount.intValue());
		//request.setAmount(amountObject);
		request.setAmount(centsAmount);
		
		String response = Poster.postXmlRequest(RazorServer.isLive() ? PROD_API_URL : DEMO_API_URL, request);
		if (response != null ){
			if (response.contains(SUCCESS)){
				
				CreditCardPaymentResponse paymentResponse  = MarshallerHelper.fromXml(CreditCardPaymentResponse.class, response);
				if (paymentResponse != null){
					resultMap.put(STATE, ACCEPTED);
					resultMap.put(TRANSACTION_ID, paymentResponse.getReferenceNumber());
					
					Integer rentPaymentGatewayID = PaymentGatewayHolder.getRent().getId();								
					CustomerPaymentProfile customerPaymentProfile = new CustomerPaymentProfile(Integer.valueOf(reservationId), paymentResponse.getToken(), null, rentPaymentGatewayID);
					sqlSession.getMapper(CustomerPaymentProfileMapper.class).create(customerPaymentProfile);
					
				}else{
					resultMap.put(STATE, FAILED);
					resultMap.put(ERROR_MSG, "Invalid payment response format.");
				}
			}else if (response.contains(ERROR)){
				RentPaymentError paymentError  = MarshallerHelper.fromXml(RentPaymentError.class, response);
				resultMap.put(STATE, FAILED);
				resultMap.put(ERROR_MSG, "Code: " + paymentError.getCode() + ", Description: " + paymentError.getDescription());
			}else	{
				throw new ServiceException(Error.payment_gateway_info, "Response: " + response);
			}
		}
			
		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createRefund(List<PaymentTransaction> paymentTransactions, Double refundAmount) throws Exception {
		Map<String, String> resultMap = initializeResultMap();
		for(net.cbtltd.shared.PaymentTransaction paymentTransaction : paymentTransactions) {
			
			RefundPayment refundPayment = new RefundPayment();
			refundPayment.setUsername(rent.getId());
			refundPayment.setPassword(rent.getToken());
			refundPayment.setPropertyCode(property.getPropertyCode());
			refundPayment.setReferenceNumber(paymentTransaction.getGatewayTransactionId());
			
			double payedAmount = paymentTransaction.getTotalAmount();
			String response = "";
			try {
				if(payedAmount < refundAmount) {
					Integer amount = (int)(payedAmount * 100);
					refundPayment.setAmount(amount);
					response = Poster.postXmlRequest(RazorServer.isLive() ? PROD_API_URL : DEMO_API_URL, refundPayment);									
					refundAmount -= payedAmount;
				} else {
					Integer amount = (int)(refundAmount * 100);
					refundPayment.setAmount(amount.intValue());
					response = Poster.postXmlRequest(RazorServer.isLive() ? PROD_API_URL : DEMO_API_URL, refundPayment);
				}
				if (response != null ){
					if (response.contains(SUCCESS)){
						CreditCardPaymentResponse paymentResponse  = MarshallerHelper.fromXml(CreditCardPaymentResponse.class, response);
						if (paymentResponse != null){
							resultMap.put(STATE, ACCEPTED);
							resultMap.put(TRANSACTION_ID, paymentResponse.getReferenceNumber());
						}else{
							resultMap.put(STATE, FAILED);
							resultMap.put(ERROR_MSG, "Invalid payment response format.");
						}
					}else if (response.contains(ERROR)){
						RentPaymentError paymentError  = MarshallerHelper.fromXml(RentPaymentError.class, response);
						resultMap.put(STATE, FAILED);
						resultMap.put(ERROR_MSG, "Code: " + paymentError.getCode() + ", Description: " + paymentError.getDescription());
					}else	{
						throw new ServiceException(Error.payment_gateway_info, "Response: " + response);
					}
				}
			}catch(Throwable throwable){
				throw new ServiceException(Error.payment_gateway_info);
			}
		}
		
		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createPaymentByProfile(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
				
		if (amount == null) throw new ServiceException(Error.card_amount);
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId.toString());
		if (reservation == null){
			throw new ServiceException(Error.database_cannot_find, "reservation");
		}
		CustomerPaymentProfile customerPaymentProfile = sqlSession.getMapper(CustomerPaymentProfileMapper.class).readByReservation(reservationId);
		
		if(customerPaymentProfile == null) { throw new ServiceException(Error.database_cannot_find, "customer payment profile"); }
		
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (customer == null){ throw new ServiceException(Error.database_cannot_find, "party"); }
		if (property == null ){ throw new ServiceException(Error.payment_gateway_info, "property code");}
		Map<String, String> resultMap = initializeResultMap();

		String firstName = "";
		String familyName = "";
		String[] name = customer.getName().split(",");
		if (name.length < 2) {
			firstName = customer.getName();
		}else {
			familyName = name[0];
			firstName = name[1];
		}
		
		CreditCardPayment payment = new CreditCardPayment();		 
		payment.setUsername(rent.getId());
		payment.setPassword(rent.getToken());
		payment.setPropertyCode(property.getPropertyCode());
		payment.setNumber(customerPaymentProfile.getCustomerPaymentProfileId());
		payment.setExpiration("");
		payment.setCardholder(firstName + " " + familyName);
		payment.setType("");
		
		payment.setStreet("");
		String postalAddress = customer.getPostaladdress();
		if (postalAddress.contains("address")){
			String[] strings = postalAddress.split(";");
			payment.setStreet(strings[0].replace("address:", ""));						
		}
		// Need to get from postal address
		payment.setCity(customer.getCity());
		
		payment.setState(customer.getState()); 		// make format
		payment.setZip(customer.getPostalcode());		
		/*
		 * Second payment not required fields ( value can be blank).
		 * payment.setExpiration("");
		 * payment.setType("");
		 * payment.setCountry(""); 
		 * payment.setPhone(""); 
		 * payment.setEmail(""); 
		 */		
		payment.setCountry(""); 
		payment.setPhone(""); 
		payment.setEmail(""); 
		Double centAmount = amount * 100;
		//Amount amountObject = new Amount(currency, centAmount.intValue());
		//payment.setAmount(amountObject);
		payment.setAmount(centAmount.intValue());
		payment.setId(customer.getId());
		payment.setPersonFirstname(firstName);
		payment.setPersonLastname(familyName);
		payment.setAccountNumber("NV56Y3Z458");
		payment.setReturnToken(true);
		
		try {
			String response = Poster.postXmlRequest(RazorServer.isLive() ? PROD_API_URL : DEMO_API_URL, payment);
			if (response != null ){
				if (response.contains(SUCCESS)){
					CreditCardPaymentResponse paymentResponse  = MarshallerHelper.fromXml(CreditCardPaymentResponse.class, response);
					if (paymentResponse != null){
						resultMap.put(STATE, ACCEPTED);
						resultMap.put(TRANSACTION_ID, paymentResponse.getReferenceNumber());
					}else{
						resultMap.put(STATE, FAILED);
						resultMap.put(ERROR_MSG, "Invalid payment response format.");
					}
				}else if (response.contains(ERROR)){
					RentPaymentError paymentError  = MarshallerHelper.fromXml(RentPaymentError.class, response);
					resultMap.put(STATE, FAILED);
					resultMap.put(ERROR_MSG, "Code: " + paymentError.getCode() + ", Description: " + paymentError.getDescription());
				}else {
					throw new ServiceException(Error.payment_gateway_info, "Response: " + response);
				}
			}
		}catch (Throwable ex){
			resultMap.put(STATE, FAILED);
			throw new ServiceException(Error.widget_book);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, String> createCustomerPaymentProfile(SqlSession sqlSession, Integer reservationId, CreditCard creditCard, Integer gatewayId) {
		//this.gatewayID = gatewayId;
		Map<String, String> resultMap = new HashMap<String, String>();		
		resultMap.put(STATE, ACCEPTED);
		return resultMap;
	}

	
	
	@Override
	public Map<String, String> authorizePayment(SqlSession sqlSession,
			String currency, Double amount, Integer reservationId) {
		return null;
	}

	@Override
	public Map<String, String> capturePayment(SqlSession sqlSession,
			String currency, Integer centAmount, Integer reservationId,
			String transactionId) {
		return null;
	}

	private CreditCardPayment generateCreditCardPayment(Party customer) throws java.lang.Exception {
		if (customer == null){	throw new ServiceException(Error.database_cannot_find, "party"); }
		if (property == null ){ throw new ServiceException(Error.payment_gateway_info, "property code");}
		
		CreditCard card = this.rent.getCreditCard();		
		String phone = RentPaymentUtils.validatePhoneNumber(customer.getDayphone());
		
		CreditCardPayment payment = new CreditCardPayment();
		payment.setUsername(rent.getId());
		payment.setPassword(rent.getToken());
		payment.setPropertyCode(property.getPropertyCode());
		payment.setNumber(card.getNumber());
		payment.setExpiration(card.getMonth() + "-" + card.getYear()); 
		payment.setCardholder(card.getFirstName() + " " + card.getLastName()); 
		payment.setType(card.getRentCardType()); 
		payment.setStreet(Constants.BLANK);
		
		String postalAddress = customer.getPostaladdress();
		if (postalAddress.contains("address")){
			String[] strings = postalAddress.split(";");
			payment.setStreet(strings[0].replace("address:", ""));						
		}
		
		payment.setCity(customer.getCity());
		payment.setState(customer.getState());		// TODO: format state codes		
		payment.setZip(customer.getPostalcode());		
		payment.setCountry(customer.getCountry());	// TODO: format country Codes
		// Must be 10 digit. No dashes, parentheses, dots, etc		
		payment.setPhone(phone);
		payment.setEmail(customer.getEmailaddress());
		payment.setId(customer.getId());
		payment.setPersonFirstname(card.getFirstName());
		payment.setPersonLastname(card.getLastName());
//		/payment.setAccountNumber("NV56Y3Z458");
		payment.setReturnToken(true);
		
		return payment;
	}
	
	private CreditCardPayment generateSecondPayment(String token){
	
		CreditCardPayment payment = new CreditCardPayment();		 
		payment.setUsername("9w8bit5");
		payment.setPassword("9w8bit5");
		payment.setPropertyCode("PPXHG46V88");
		payment.setNumber(token);
		payment.setExpiration("01-2016");
		payment.setCardholder("John Doe");
		payment.setType("VISA");
		payment.setStreet("505 Sansome");
		payment.setCity("San Francisco");
		payment.setState("CA");
		payment.setZip("94111");
		
		/*
		 * Second payment not required fields ( value can be blank).
		 * payment.setExpiration("");
		 * payment.setType("");
		 * payment.setCountry(""); 
		 * payment.setPhone(""); 
		 * payment.setEmail(""); 
		 */
		
		payment.setCountry(Constants.BLANK); 
		payment.setPhone(Constants.BLANK); 
		payment.setEmail(Constants.BLANK); 		
		//payment.setAmount(new Amount("USD", 15000));
		payment.setAmount(15000);
		payment.setId("12345");
		payment.setPersonFirstname("Jonh");
		payment.setPersonLastname("Doe");
		//payment.setAccountNumber("NV56Y3Z458");
		payment.setReturnToken(true);
		return payment;
	}
	
	/**
	 * Test method
	 * @param token
	 * @return test CreditCardPayment object.
	 */
	private CreditCardPayment generateTestCCPayment(String token){
		
		CreditCardPayment payment = new CreditCardPayment();		 
		payment.setUsername("9w8bit5");
		payment.setPassword("9w8bit5");
		payment.setPropertyCode("PPXHG46V88");
		payment.setNumber("4111111111111111");
		payment.setExpiration("01-2016");
		payment.setCardholder("John Doe");
		payment.setType("VISA");
		payment.setStreet("505 Sansome");
		payment.setCity("San Francisco");
		payment.setState("CA");
		payment.setZip("94111");
		payment.setCountry("US"); 
		payment.setPhone("1234567890"); 
		payment.setEmail("twst@mail.com"); 		
		//payment.setAmount(new Amount("USD", 15000));
		payment.setAmount(15000);
		payment.setId("12345");
		payment.setPersonFirstname("Jonh");
		payment.setPersonLastname("Doe");
		payment.setAccountNumber("NV56Y3Z458");
		payment.setReturnToken(true);
		return payment;
	}
	
	private CreateSimpleCreditCardToken createCreditCardToken(){
		
		CreateSimpleCreditCardToken creditCardToken = new CreateSimpleCreditCardToken();
		creditCardToken.setUsername("9w8bit5");
		creditCardToken.setPassword("9w8bit5");
		creditCardToken.setNumber("4111111111111111");		
		
		return creditCardToken;
		
	}
	
	public GatewayInfo getRent() {
		return rent;
	}

	public void setRent(GatewayInfo rent) {
		this.rent = rent;
	}
	
	private Map<String, String> initializeResultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TRANSACTION_ID, null);
		map.put(ERROR_MSG, null);
		map.put(STATE, null);
		
		return map;
	}

}

