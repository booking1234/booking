package net.cbtltd.shared.finance.gateway.dibs.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.dibs.DibsHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsAuthentication;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsResponseUtil;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AcceptedAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.DeclinedAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.CaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.DeclinedCaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.refund.RefundResponse;
import net.cbtltd.shared.finance.gateway.dibs.server.AuthCgiProcessor;
import net.cbtltd.shared.finance.gateway.dibs.server.CaptureCgiProcessor;
import net.cbtltd.shared.finance.gateway.dibs.server.RefundCgiProcessor;

public class Test {
	
	private static final String VISA_CARD_PREFIX = "4711";
	private static final String VISA_AUTH_POSTFIX_REASON_0 = "000000000000";
	private static final String VISA_AUTH_POSTFIX_REASON_1 = "000000000001";
	private static final String VISA_AUTH_POSTFIX_REASON_2 = "000000000002";
	private static final String VISA_AUTH_POSTFIX_REASON_3 = "000000000003";
	private static final String VISA_AUTH_POSTFIX_REASON_4 = "000000000004";
	private static final String VISA_AUTH_POSTFIX_ACCEPTED = "100000000000";
	
	private static final String VISA_CAPTURE_POSTFIX_APPROVED = "100000000000";
	private static final String VISA_CAPTURE_POSTFIX_REJECTED_BY_ACQUIRER_1 = "100000000001";
	private static final String VISA_CAPTURE_POSTFIX_REJECTED_BY_ACQUIRER_2 = "100000000002";
	private static final String VISA_CAPTURE_POSTFIX_CARD_EXPIRED = "100000000003";
	private static final String VISA_CAPTURE_POSTFIX_REJECTED_BY_ACQUIRER_3 = "100000000004";
	private static final String VISA_CAPTURE_POSTFIX_AUTHORIZATION_OUTDATED = "100000000005";
	private static final String VISA_CAPTURE_POSTFIX_INCORRECT_DIBS_STATUS = "100000000006";
	
	public static void main(String[] args) throws Exception {


		CreditCard creditCard = new CreditCard();
		String prefix = VISA_CARD_PREFIX;
		String postfix = VISA_CAPTURE_POSTFIX_APPROVED;
		creditCard.setNumber(prefix + postfix);
		
		GatewayInfo dibs = new GatewayInfo(CreditCardType.VISA, creditCard.getNumber(), "06", "24", "First", "Second", "684");
		DibsAuthentication auth = MarshallerHelper.fromXml(DibsAuthentication.class, dibs.getAdditional());
		String transact = null;
		Integer centAmount = (int) (5 * 100);
		AuthResponse authResponse = AuthCgiProcessor.createTicket(dibs.getCreditCard(), "USD", "12345", auth.getMerchantId());
		
	}
	
	public static void main_split_payments(String[] args) throws Exception { // split payments test

		CreditCard creditCard = new CreditCard();
		String prefix = VISA_CARD_PREFIX;
		String postfix = VISA_CAPTURE_POSTFIX_APPROVED;
		creditCard.setNumber(prefix + postfix);
		
		GatewayInfo dibs = new GatewayInfo(CreditCardType.VISA, creditCard.getNumber(), "06", "24", "First", "Second", "684");
		DibsAuthentication auth = MarshallerHelper.fromXml(DibsAuthentication.class, dibs.getAdditional());
		
		String transact = null;
		Integer centAmount = (int) (5 * 100);
		AuthResponse authResponse = AuthCgiProcessor.processSplitPayment(dibs.getCreditCard(), centAmount.toString(), "USD", "12345", auth.getMerchantId());
		CaptureResponse captureResponse;
		if(authResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
			AcceptedAuthResponse acceptedAuthResponse = (AcceptedAuthResponse) authResponse;
			transact = acceptedAuthResponse.getTransactionId();
			captureResponse = CaptureCgiProcessor.processSplitPayment(centAmount.toString(), null, "12345", transact, auth.getMerchantId());
			if(captureResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				System.out.println("Accepted");
			} else {
				DeclinedCaptureResponse declinedCaptureResponse = (DeclinedCaptureResponse) captureResponse;
				Method[] methods = declinedCaptureResponse.getClass().getMethods();
				for(Method method : methods) {
					if(method.getName().contains("get")) {
						if(method.invoke(declinedCaptureResponse) != null) {
							System.out.println(method.getName() + "() == " + method.invoke(declinedCaptureResponse).toString());
						}
					}
				}
			}
		} else {
			DeclinedAuthResponse declinedAuthResponse = (DeclinedAuthResponse) authResponse;
			Method[] methods = declinedAuthResponse.getClass().getMethods();
			for(Method method : methods) {
				if(method.getName().contains("get")) {
					if(method.invoke(declinedAuthResponse) != null) {
						System.out.println(method.getName());
						System.out.println(method.invoke(declinedAuthResponse).toString());
					}
				}
			}
		}
	}
	
	public static void refund_main(String[] args) throws Exception {
		CreditCard creditCard = new CreditCard();
		String prefix = VISA_CARD_PREFIX;
		String postfix = VISA_AUTH_POSTFIX_ACCEPTED;
		creditCard.setNumber(prefix + postfix);
		
		GatewayInfo dibs = new GatewayInfo(CreditCardType.VISA, creditCard.getNumber(), "06", "24", "First", "Second", "684");
		dibs.setId("90194061");
		dibs.setToken("Booking92618");
		DibsHandler dibsHandler = new DibsHandler(dibs);
		SqlSession sqlSession = RazorServer.openSession();
		Map<String, String> map = dibsHandler.createPaymentByCreditCard(sqlSession, "USD", 1., "12345");
		sqlSession.close();
		System.out.println(map);
		// ----------
		
		net.cbtltd.shared.PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setTotalAmount(1.);
		paymentTransaction.setReservationId(12345);
		paymentTransaction.setGatewayTransactionId(map.get(GatewayHandler.TRANSACTION_ID));
		paymentTransaction.setCurrency("USD");
		List<PaymentTransaction> transactions = new ArrayList<PaymentTransaction>();
		transactions.add(paymentTransaction);
		map = dibsHandler.createRefund(transactions, 1.);
		System.out.println(map);
	}
	
	public static void auth_capture_main(String[] args) throws Exception {
		CreditCard creditCard = new CreditCard();
		String prefix = VISA_CARD_PREFIX;
		String postfix = VISA_AUTH_POSTFIX_ACCEPTED;
		creditCard.setNumber(prefix + postfix);
		
		GatewayInfo dibs = new GatewayInfo(CreditCardType.VISA, creditCard.getNumber(), "06", "24", "First", "Second", "684");
		dibs.setId("90194061");
		dibs.setToken("Booking92618");
		DibsHandler dibsHandler = new DibsHandler(dibs);
		SqlSession sqlSession = RazorServer.openSession();
		Map<String, String> map = dibsHandler.createPaymentByCreditCard(sqlSession, "USD", 1., "12345");
		sqlSession.close();
		System.out.println(map);
	}
	
	public static void old_main(String[] args) throws Exception {
		CreditCard creditCard = new CreditCard();
		String prefix = "4711";
		String postfix = "000000000001";
		creditCard.setNumber(prefix + postfix);
//		RefundResponse response = RefundCgiProcessor.process("90194061", "Booking92618", "100", "USD", null, "123456", "1234567");
//		System.out.println(response.getResult() + ": " + DibsResponseUtil.getReasonText(Integer.valueOf(response.getResult())));
//		System.out.println(response.getMessage());
	}
}
