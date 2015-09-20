package net.cbtltd.rest.paypal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.GatewayInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.Sale;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

public class PaypalHandlerNew implements GatewayHandler {
	private GatewayInfo paypal;
	private Map<String, String> sdkConfig = new HashMap<String, String>();

	public PaypalHandlerNew(GatewayInfo paypal) {
		this.setPaypal(paypal);
		sdkConfig.put("mode", getMode());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createPaymentByCreditCard(SqlSession sqlSession, String currency, Double amount, String reservationId) throws Exception {
		Map<String, String> resultMap = initializeResultMap();
		Payment createdPayment = null;
		try {
			String accessToken = getAccessToken();

			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);

			FundingInstrument fundingInstrument = new FundingInstrument();
			fundingInstrument.setCreditCard(paypal.getCreditCard().getPaypalCard());

			List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
			fundingInstrumentList.add(fundingInstrument);

			Payer payer = new Payer();
			payer.setFundingInstruments(fundingInstrumentList);
			payer.setPaymentMethod("credit_card");

			Amount amountValue = new Amount();
			amountValue.setCurrency(currency);
			
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
			decimalFormatSymbols.setDecimalSeparator('.');
			decimalFormatSymbols.setGroupingSeparator(',');
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
			amountValue.setTotal(decimalFormat.format(amount));

			Transaction transaction = new Transaction();
			transaction.setDescription("creating a direct payment with credit card for reservation " + reservationId);
			transaction.setAmount(amountValue);

			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			createdPayment = payment.create(apiContext);
			/*
			 * created; approved; failed; canceled; expired. 
			 */
		} catch (PayPalRESTException e) {
			String message = PaypalHelper.getParamValueFromJson(e.getMessage(), "message");
			message = message == null ? "" : message;
			if(PaypalHelper.getParamValueFromJson(e.getMessage(), "issue") != null) {
				message = message + " : " + PaypalHelper.getParamValueFromJson(e.getMessage(), "issue");
			}
			if(StringUtils.isEmpty(message)) {
				message = PaypalHelper.getParamValueFromJson(e.getMessage(), "error_description");
			}
			resultMap.put(STATE, FAILED);
			resultMap.put(ERROR_MSG, message);
		}
		
		if(createdPayment != null) {
			String saleId = createdPayment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
			String id = PaypalHelper.getParamValueFromJson(createdPayment.toJSON(), "id");
			resultMap.put(TRANSACTION_ID, id + ";" + saleId);
		}
		
		if(createdPayment != null && createdPayment.getState().equalsIgnoreCase("approved")) {
			resultMap.put(STATE, ACCEPTED);
		} else if(createdPayment != null && createdPayment.getState().equalsIgnoreCase("failed")) {
			resultMap.put(STATE, FAILED);
		} else {
			resultMap.put(STATE, DECLINED);
		}
		
		return resultMap;
	}
	
	public static String getMode() {
		return RazorServer.isLive() ? "live" : "sandbox";
	}
	
	private Map<String, String> initializeResultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TRANSACTION_ID, null);
		map.put(ERROR_MSG, null);
		map.put(STATE, null);
		
		return map;
	}
	
	private String getAccessToken() throws PayPalRESTException {
		String accessToken = new OAuthTokenCredential(paypal.getId(), paypal.getToken(), sdkConfig).getAccessToken();
		return accessToken;
	}

	public GatewayInfo getPaypal() {
		return paypal;
	}

	public void setPaypal(GatewayInfo paypal) {
		this.paypal = paypal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createRefund(List<PaymentTransaction> paymentTransactions, Double amount) throws Exception {
		Map<String, String> resultMap = initializeResultMap();
		Refund responseRefund = null;
		try {
			String accessToken = getAccessToken();
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);

			for (PaymentTransaction paymentTransaction : paymentTransactions) {
				Sale sale = new Sale();
				String transactionId = paymentTransaction.getGatewayTransactionId();
				String saleId = transactionId.substring(transactionId.indexOf(";") + 1);
				sale.setId(saleId);
				Refund refund = new Refund();

				Amount amountValue = new Amount();
				amountValue.setCurrency(paymentTransaction.getCurrency());
				
				DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
				decimalFormatSymbols.setDecimalSeparator('.');
				decimalFormatSymbols.setGroupingSeparator(',');
				DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
				amountValue.setTotal(decimalFormat.format(amount));
				
				refund.setAmount(amountValue);
				responseRefund = sale.refund(apiContext, refund);
			}

		} catch (Exception e) {
			throw new ServiceException(Error.refund_unsupported, "for PayPal");
		}
		
		if(responseRefund != null) {
			String id = responseRefund.getId();
			resultMap.put(TRANSACTION_ID, id);
		}
		
		if(responseRefund.getState().equalsIgnoreCase("approved")) {
			resultMap.put(STATE, ACCEPTED);
		} else if(responseRefund.getState().equalsIgnoreCase("failed")) {
			resultMap.put(STATE, FAILED);
		} else {
			resultMap.put(STATE, DECLINED);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, String> authorizePayment(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
		throw new ServiceException(Error.not_implemented, "PayPal");
	}
	
	@Override
	public Map<String, String> capturePayment(SqlSession sqlSession, String currency, Integer centAmount, Integer reservationId, String transactionId) {
		throw new ServiceException(Error.not_implemented, "PayPal");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createPaymentByProfile(SqlSession sqlSession, String currency, Double amount, Integer reservationId) {
		throw new ServiceException(Error.payment_profile_unsupported, "for PayPal");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createCustomerPaymentProfile(SqlSession sqlSession, Integer reservationId, CreditCard creditCard, Integer gatewayId) {
		throw new ServiceException(Error.payment_profile_unsupported, "for PayPal");		
	}
	
//	public static void main(String[] args) throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException {
//		String receiverEmail = "platfo_1255170694_biz@gmail.com";
//		String senderEmail = "platfo_1255077030_biz@gmail.com";
//		String currency = "USD";
//		Double amount = 2.5;
//		
//		PaypalAccount account = new PaypalAccount();
//		account.setAppId("APP-80W284485P519543T");
//		account.setPassword("WX4WTU3S8MY44S7F");
//		account.setSignature("AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
//		account.setUserName("jb-us-seller_api1.paypal.com");
//		
//		PayResponse response = accountToAccountPayment(receiverEmail, senderEmail, account, amount, currency);
//		System.out.println("PayKey: " + response.getPayKey());
//		System.out.println("Payment exec status: " + response.getPaymentExecStatus());
//	}
	
	public static PayResponse accountToAccountPayment(String receiverEmail, String senderEmail, PaypalAccount account, Double amount, String currency) 
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException {
		PayRequest payRequest = new PayRequest();
			
		List<Receiver> receivers = new ArrayList<Receiver>();
		Receiver receiver = new Receiver();
		receiver.setAmount(amount);
		receiver.setEmail(receiverEmail);
		receivers.add(receiver);
		ReceiverList receiverList = new ReceiverList(receivers);
		payRequest.setSenderEmail(senderEmail);

		payRequest.setReceiverList(receiverList);

		RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
		payRequest.setRequestEnvelope(requestEnvelope); 
		payRequest.setActionType("PAY");
		payRequest.setCancelUrl("https://devtools-paypal.com/guide/ap_implicit_payment?cancel=true");
		payRequest.setReturnUrl("https://devtools-paypal.com/guide/ap_implicit_payment?success=true");
		payRequest.setCurrencyCode(currency);
		payRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");

		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", getMode());
		sdkConfig.put("acct1.UserName", account.getUserName());
		sdkConfig.put("acct1.Password", account.getPassword());
		sdkConfig.put("acct1.Signature", account.getSignature());
		sdkConfig.put("acct1.AppId", account.getAppId());

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
		PayResponse payResponse = adaptivePaymentsService.pay(payRequest);
		return payResponse;
	}

}
