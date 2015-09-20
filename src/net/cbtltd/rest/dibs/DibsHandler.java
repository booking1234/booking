package net.cbtltd.rest.dibs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsAuthentication;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsResponseUtil;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AcceptedAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.DeclinedAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.AcceptedTicketAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.DeclinedTicketAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.TicketAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.AcceptedCaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.CaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.DeclinedCaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.refund.RefundResponse;
import net.cbtltd.shared.finance.gateway.dibs.server.AuthCgiProcessor;
import net.cbtltd.shared.finance.gateway.dibs.server.CaptureCgiProcessor;
import net.cbtltd.shared.finance.gateway.dibs.server.RefundCgiProcessor;
import net.cbtltd.shared.finance.gateway.dibs.server.TicketAuthCgiProcessor;

import org.apache.ibatis.session.SqlSession;

public class DibsHandler implements GatewayHandler {

	private GatewayInfo dibs = null;
	private DibsAuthentication auth = null;
	
	public DibsHandler(GatewayInfo dibs) {
		this.setDibs(dibs);
		try {
			auth = MarshallerHelper.fromXml(DibsAuthentication.class, dibs.getAdditional());
		} catch (JAXBException e) {
			auth = null;
		}
	}
	
	public Map<String, String> createPaymentByCreditCard_old(String currency, Double amount, String reservationId) throws Exception {
		Map<String, String> resultMap = initializeResultMap();
		String transact = null;
		Integer centAmount = (int) (amount * 100);
		AuthResponse authResponse = AuthCgiProcessor.process(dibs.getCreditCard(), centAmount.toString(), currency, reservationId, auth.getMerchantId());
		CaptureResponse captureResponse;
		if(authResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
			AcceptedAuthResponse acceptedAuthResponse = (AcceptedAuthResponse) authResponse;
			transact = acceptedAuthResponse.getTransactionId();
			captureResponse = CaptureCgiProcessor.process(centAmount.toString(), null, reservationId, transact, auth.getMerchantId());
			if(captureResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				resultMap.put(STATE, ACCEPTED);
			} else {
				DeclinedCaptureResponse declinedCaptureResponse = (DeclinedCaptureResponse) captureResponse;
				resultMap.put(STATE, FAILED);
				resultMap.put(ERROR_MSG, declinedCaptureResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedCaptureResponse.getReason()) +
						", detailed message: "+ declinedCaptureResponse.getMessage());
			}
		} else {
			DeclinedAuthResponse declinedAuthResponse = (DeclinedAuthResponse) authResponse;
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, declinedAuthResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedAuthResponse.getReason()) +
					", detailed message: "+ declinedAuthResponse.getMessage());
		}
		resultMap.put(TRANSACTION_ID, transact);
		return resultMap;
	}
	
	@Override
	public Map<String, String> createPaymentByCreditCard(SqlSession sqlSession, String currency, Double amount, String reservationId) throws Exception {
		Integer reservationIdInt = Integer.valueOf(reservationId);
		Map<String, String> result = authorizePayment(sqlSession, currency, amount, reservationIdInt);
		if(result.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
			result = capturePayment(sqlSession, currency, (int) (amount * 100), reservationIdInt, result.get(GatewayHandler.TRANSACTION_ID));
		}
		return result;
	}
	
	@Override
	public Map<String, String> createRefund(List<PaymentTransaction> paymentTransactions, Double refundAmount) throws Exception {
		Map<String, String> resultMap = initializeResultMap();
		Integer centRefundAmount = (int) (refundAmount * 100);
		for(net.cbtltd.shared.PaymentTransaction paymentTransaction : paymentTransactions) {
			Integer centPayedAmount = (int) (paymentTransaction.getTotalAmount() * 100);
			String reservationIdString = String.valueOf(paymentTransaction.getReservationId());
			String transact = paymentTransaction.getGatewayTransactionId();
			RefundResponse response = null;
			if(centPayedAmount < centRefundAmount) {
				response = RefundCgiProcessor.process(dibs.getId(), dibs.getToken(), centPayedAmount.toString(), paymentTransaction.getCurrency(), null, 
						reservationIdString, transact, auth.getMerchantId());
				refundAmount -= centPayedAmount;
			} else {
				response = RefundCgiProcessor.process(dibs.getId(), dibs.getToken(), centRefundAmount.toString(), paymentTransaction.getCurrency(), null, 
						reservationIdString, transact, auth.getMerchantId());
			}
			resultMap = fillRefundResultMap(response);
			resultMap.put(TRANSACTION_ID, transact);
		}
		return resultMap;
	}

	@Override
	public Map<String, String> createPaymentByProfile(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
		Map<String, String> resultMap = initializeResultMap();
		try {
			String transact = null;
			Integer centAmount = (int) (amount * 100);
			String ticketId = null; // TODO : get ID from cutomer_payment_profile table
			String orderId = null; // TODO : get ID from cutomer_payment_profile table
			TicketAuthResponse authResponse = TicketAuthCgiProcessor.process(amount.toString(), currency, orderId, ticketId, auth.getMerchantId());
			CaptureResponse captureResponse;
			if(authResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				AcceptedTicketAuthResponse acceptedAuthResponse = (AcceptedTicketAuthResponse) authResponse;
				transact = acceptedAuthResponse.getTransact();
				captureResponse = CaptureCgiProcessor.process(centAmount.toString(), null, reservationId.toString(), transact, auth.getMerchantId());
				if(captureResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
					resultMap.put(STATE, ACCEPTED);
				} else {
					DeclinedCaptureResponse declinedCaptureResponse = (DeclinedCaptureResponse) captureResponse;
					resultMap.put(STATE, FAILED);
					resultMap.put(ERROR_MSG, declinedCaptureResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedCaptureResponse.getReason()) +
							", detailed message: "+ declinedCaptureResponse.getMessage());
				}
			} else {
				DeclinedTicketAuthResponse declinedAuthResponse = (DeclinedTicketAuthResponse) authResponse;
				resultMap.put(STATE, FAILED);
				resultMap.put(ERROR_MSG, declinedAuthResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedAuthResponse.getReason()) +
						", detailed message: "+ declinedAuthResponse.getMessage());
			}
			resultMap.put(TRANSACTION_ID, transact);
		} catch (Exception e) {
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, e.getMessage());
		}
		return resultMap;
	}

	public static void main(String[] args) throws Exception {
		CreditCard cc = new CreditCard();
//		cc.setFirstName("PAVLO");
//		cc.setLastName("BOIKO");
//		cc.setMonth("03");
//		cc.setNumber("5308170027097160");
//		cc.setSecurityCode("927");
//		cc.setType(CreditCardType.MASTER_CARD);
//		cc.setYear("2016");
		
		cc.setMonth("06");
		cc.setYear("2024");
		cc.setNumber("4711100000000000");
		cc.setSecurityCode("684");
		cc.setType(CreditCardType.VISA);
		
		GatewayInfo dibs = new GatewayInfo();
		dibs.setCreditCartd(cc);
		dibs.setId("novasolweb");
		dibs.setToken("nova909web");
		dibs.setAdditional("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><dibs_auth><merchant_id>4221772</merchant_id></dibs_auth>");
		DibsHandler handler = new DibsHandler(dibs);
		SqlSession sqlSession = RazorServer.openSession();
		Integer reservationId = 123;
		Map<String, String> result = handler.authorizePayment(sqlSession, "USD", 1., 29514502);
		for(String string : result.keySet()) {
			System.out.println(string + " : " + result.get(string));
		}
	}
	
	@Override // TODO
	public Map<String, String> createCustomerPaymentProfile(SqlSession sqlSession, Integer reservationId, CreditCard creditCard, Integer gatewayId) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId.toString());
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			AuthResponse response = AuthCgiProcessor.createTicket(creditCard, reservation.getCurrency(), reservationId.toString(), auth.getMerchantId());
			if(response.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				AcceptedAuthResponse acceptedAuthResponse = (AcceptedAuthResponse) response;
				System.out.println("Approval code: " + acceptedAuthResponse.getApprovalCode());
				System.out.println("Card type: " + acceptedAuthResponse.getCardType());
				System.out.println("Transaction ID: " + acceptedAuthResponse.getTransactionId());
			} else {
				DeclinedAuthResponse declinedAuthResponse = (DeclinedAuthResponse) response;
				System.out.println("Message: " + declinedAuthResponse.getMessage());
				System.out.println("Reason: " + declinedAuthResponse.getReason());
			}
			System.out.println(response.getStatus());
		} catch (Exception e) {
			resultMap.put(ERROR_MSG, e.getMessage());
			resultMap.put(STATE, FAILED);
		}
		return resultMap;
	}

	public GatewayInfo getDibs() {
		return dibs;
	}

	public void setDibs(GatewayInfo dibs) {
		this.dibs = dibs;
	}
	
	private static Map<String, String> initializeResultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TRANSACTION_ID, null);
		map.put(ERROR_MSG, null);
		map.put(STATE, null);
		
		return map;
	}
	
	private static Map<String, String> fillRefundResultMap(RefundResponse response) {
		Map<String, String> resultMap = initializeResultMap();
		if(response.getStatus().equals(Constants.ACCEPTED_STATUS)) {
			resultMap.put(STATE, ACCEPTED);
		} else {
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, response.getMessage());
		}
		return resultMap;
	}
	
	@Override
	public Map<String, String> authorizePayment(SqlSession sqlSession, String currency, Double amount,	Integer reservationId) {
		Map<String, String> resultMap = initializeResultMap();
		try {
			String transact = null;
			Integer centAmount = (int) (amount * 100);
			String orderId = formatOrderId(sqlSession, reservationId);
			AuthResponse authResponse = AuthCgiProcessor.process(dibs.getCreditCard(), centAmount.toString(), currency,
					orderId, auth.getMerchantId());
			if (authResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				AcceptedAuthResponse acceptedAuthResponse = (AcceptedAuthResponse) authResponse;
				transact = acceptedAuthResponse.getTransactionId();
				resultMap.put(STATE, ACCEPTED);
			} else {
				DeclinedAuthResponse declinedAuthResponse = (DeclinedAuthResponse) authResponse;
				resultMap.put(STATE, FAILED);
				resultMap.put(ERROR_MSG, declinedAuthResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedAuthResponse.getReason())
								+ ", detailed message: " + declinedAuthResponse.getMessage());
			}
			resultMap.put(TRANSACTION_ID, transact);
		} catch (Exception e) {
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, e.getMessage());
		}
		return resultMap;
	}

	@Override
	public Map<String, String> capturePayment(SqlSession sqlSession, String currency, Integer centAmount, Integer reservationId, String transactionId) {
		Map<String, String> resultMap = initializeResultMap();
		CaptureResponse captureResponse;
		try {
			String orderId = formatOrderId(sqlSession, reservationId);
			captureResponse = CaptureCgiProcessor.process(centAmount.toString(), null, orderId, transactionId, auth.getMerchantId());
			if (captureResponse.getStatus().equals(Constants.ACCEPTED_STATUS)) {
				AcceptedCaptureResponse acceptedCaptureResponse = (AcceptedCaptureResponse) captureResponse;
				resultMap.put(TRANSACTION_ID, acceptedCaptureResponse.getTransact());
				resultMap.put(STATE, ACCEPTED);
			} else {
				DeclinedCaptureResponse declinedCaptureResponse = (DeclinedCaptureResponse) captureResponse;
				resultMap.put(STATE, FAILED);
				resultMap.put(ERROR_MSG, declinedCaptureResponse.getReason() + ": " + DibsResponseUtil.getReasonText(declinedCaptureResponse.getReason())
								+ ", detailed message: " + declinedCaptureResponse.getMessage());
			}
		} catch (Exception e) {
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, e.getMessage());
		}
		return resultMap;
	}
	
	private String formatOrderId(SqlSession sqlSession, Integer reservationId) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId.toString());
//		String fromDateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getFromdate());
//		String toDateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getTodate());
		String orderId = reservation.getAltid();
		return orderId;
	}
}
