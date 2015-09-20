package net.cbtltd.rest.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Paypal;
import net.cbtltd.shared.Process;
import net.cbtltd.shared.api.IsGateway;
import net.cbtltd.shared.finance.GatewayAction;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class PaypalHandler is to make payments via the PayPal payment gateway.
 * 
 * The sender can log on and approve a pre-approval paypal using Adaptive Payments when
 * you redirect the sender’s browser to PayPal using the _ap-preapproval command.
 * PayPal redirects to the return URL you set in the Preapproval paypal.
 * NOTE: Any additional parameters you specify in the URL are not returned.
 * Redirect to https://www.paypal.com/webscr?cmd=_ap-payment&paykey=value
 * Response https://www.sandbox.paypal.com/webscr?cmd=_ap-payment&paykey=value
 * The sender can log on and approve a payment using Adaptive Payments when
 * you redirect the sender’s browser to PayPal using the _ap-payment command.
 * PayPal redirects to the return URL you set in the Pay paypal.
 * NOTE: Any additional parameters you specify in the URL are not returned.
 */
public class PaypalHandler implements IsGateway {

	private static final Logger LOG = Logger.getLogger(PaypalHandler.class.getName());
	
	/* Settings of the live PayPal gateway. */
	private static final String SUPPLIER_EMAIL_ADDRESS = "admin@campsbayterrace.com";
	private static final String URL_PAY_RESULT = "http://razor-cloud.com/razor/paypal.html";
	private static final String URL_PAY = "https://svcs.paypal.com/AdaptivePayments/Pay/";
	private static final String URL_PAYMENT_DETAILS = "https://svcs.paypal.com/AdaptivePayments/PaymentDetails/";
	private static final String URL_PREAPPROVAL = "https://svcs.paypal.com/AdaptivePayments/Preapproval/";
	private static final String URL_PREAPPROVAL_DETAILS = "https://svcs.paypal.com/AdaptivePayments/PreapprovalDetails/";
	private static final String URL_CANCEL_PREAPPROVAL = "https://svcs.paypal.com/AdaptivePayments/CancelPreapproval/";
	private static final String URL_CONVERT_CURRENCY = "https://svcs.paypal.com/AdaptivePayments/Preapproval/";
	private static final String URL_REFUND = "https://svcs.paypal.com/AdaptivePayments/Refund/";
	private static final String URL_PAYMENT_REDIRECT = "https://www.paypal.com/webscr?cmd=_ap-payment&paykey=";
	private static final String SECURITY_USERID = "admin_api1.campsbayterrace.com";
	private static final String SECURITY_PASSWORD = "BYGBBHDDSR9GBNVK";
	private static final String SECURITY_SIGNATURE = "AY736001DMP72i81dQfSjsxDrN.QAJldNa5r6CPlE.-HLbROMVV8G2Wd";
	private static final String APPLICATION_ID = "APP-8RH04204FU174111A"; //Razor Property Management System
	//private static final String APPLICATION_ID = "APP-9NA837436X7544013"; //Razor Widget

	/* Settings of the test PayPal gateway.
	private static final String SUPPLIER_EMAIL_ADDRESS = "chris_1260002082_biz@campsbayterrace.com";
	private static final String URL_PAY_RESULT = "http://razor-cloud.com/razor/paypal.html";
	private static final String URL_PAY = "https://svcs.sandbox.paypal.com/AdaptivePayments/Pay/";
	private static final String URL_PAYMENT_DETAILS = "https://svcs.sandbox.paypal.com/AdaptivePayments/PaymentDetails/";
	private static final String URL_PREAPPROVAL = "https://svcs.sandbox.paypal.com/AdaptivePayments/Preapproval/";
	private static final String URL_PREAPPROVAL_DETAILS = "https://svcs.sandbox.paypal.com/AdaptivePayments/PreapprovalDetails/";
	private static final String URL_CANCEL_PREAPPROVAL = "https://svcs.sandbox.paypal.com/AdaptivePayments/CancelPreapproval/";
	private static final String URL_CONVERT_CURRENCY = "https://svcs.sandbox.paypal.com/AdaptivePayments/Preapproval/";
	private static final String URL_REFUND = "https://svcs.sandbox.paypal.com/AdaptivePayments/Refund/";
	private static final String URL_PAYMENT_REDIRECT = "https://www.sandbox.paypal.com/webscr?cmd=_ap-payment&paykey=";
	private static final String SECURITY_USERID = "chris_1259669582_biz_api1.campsbayterrace.com";
	private static final String SECURITY_PASSWORD = "1259669670";
	private static final String SECURITY_SIGNATURE = "An5ns1Kso7MWUdW4ErQKJJJ4qi4-AhNcSE-2VMWxPWa8gTHkdU.VuN35";
	private static final String APPLICATION_ID = "APP-80W284485P519543T"; 
	 */

	/* Settings shared by live and test modes. */
	private static final String REQUEST_DATA_FORMAT = "NV";
	private static final String RESPONSE_DATA_FORMAT = "NV";
	private static final String REQUEST_SOURCE = "PLATFORM_JAVA_SDK_V1.0.0";
	private static final String SERVICE_VERSION = "1.1.0";

	public String getAltpartyid() {
		return "Altpartyid"; //TODO:
	}
	
	public void createPayment(GatewayAction action) {
		
	}

	public GatewayAction getPayment(GatewayAction action) {
		return null;
	}

	public void createRefund(GatewayAction action) {
		
	}

	/**
	 * Gets the journal event that records the payment in the financial ledger.
	 *
	 * @param event the journal event.
	 * @return the response.
	 */
	public final Event<Journal> paid(Event<Journal> event) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			event = sqlSession.getMapper(EventMapper.class).read(event.getId());
			LOG.debug("Paypal Paid event " + event.toString());
			if (event == null){LOG.error("Paypal Paid error " + event);}
			else if (event.hasProcess(Event.Type.Payment.name())) {
				//PaymentService paymentService = PaymentService.getInstance();
				//TODO: paymentService.pay(paymentService.read(event), false);
			}
			else if (event.hasProcess(Event.Type.Receipt.name())) {
				Event<Journal> receipt = sqlSession.getMapper(EventMapper.class).read(event.getId());
				if (receipt.hasActivity(NameId.Type.Reservation.name())) {

					//TODO:				Reservation reservation = ReservationService.read(receipt.getParentid());
					//				if (reservation != null) {
					//					reservation.setState(Reservation.RESERVED);
					//					ReservationService.write(reservation);
					//				}
				}
				//				ReceiptService receiptService = ReceiptService.getInstance();
				//				receiptService.receive(receiptService.read(event), false);
			}
		}
		catch (Throwable x) {
//			event.setStatus(ServiceException.NAME);
			MonitorService.log(x);
		}
		finally {sqlSession.close();}
		return event;
	}

	/**
	 * Pay via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to submit for payment.
	 * @return the response.
	 */
	public static final Paypal pay(Paypal paypal) {
		LOG.debug("pay " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID",	SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);

			StringBuilder body = new StringBuilder();
			body.append("actionType=PAY");
			//Party supplier = PartyService.read(paypal.getSupplierid());
			body.append("&");
			body.append("receiverList.receiver(0).email=" + SUPPLIER_EMAIL_ADDRESS);
			//body.append("receiverList.receiver(0).email=" + supplier.getEmailaddress());
			//(Required) Receiver’s email address. This address can be unregistered with PayPal.com. If so, the receiver 				
			//does not receive the payment until they have created a Paypal account associated with the email address.
//			if (Currency.isPaypal(paypal.getCurrency())) {
//				body.append("&");
//				body.append("receiverList.receiver(0).amount=" + paypal.getAmount());
//				body.append("&");
//				body.append("receiverList.receiver(0).invoiceId=" + paypal.getId());
//				body.append("&");
//				body.append("currencyCode=" + paypal.getCurrency());
//			}
//			else {
//				body.append("&");
//				//TODO:				body.append("receiverList.receiver(0).amount=" + Event.round(WebService.getRate(paypal.getCurrency(), Currency.Code.USD.name()) * paypal.getAmount(), 2));
//				body.append("&");
//				body.append("receiverList.receiver(0).invoiceId=" + paypal.getId());
//				body.append("&");
//				body.append("currencyCode=" + Currency.Code.USD.name());
//			}
			//(Required) Amount in Currency to be credited to the receiver’s account.
			//body.append("&");
			//body.append("receiverList.receiver(0).invoiceId=" + paypal.getId());
			//(Optional) The invoice number for the payment. This field is only used in the
			//Pay API operation—it is ignored in other Adaptive Payment API operations.

			body.append("&");
			body.append("returnUrl=" + URL_PAY_RESULT + "?value=" + paypal.getId());
			//			body.append("returnUrl=http://localhost:8888/net.cbtltd.Lucid/index.html#Payment");
			//TODO: autologin widget to thank for payment and update pay state
			//URL to redirect the sender’s browser to after the sender has
			//logged into PayPal and approved a payment; it is always
			//required but only used if a payment requires explicit approval

			body.append("&");
			body.append("cancelUrl=" + URL_PAY_RESULT + "?value=0");
			//TODO: autologin widget to advise that payment has been unsuccessful
			//URL to redirect the sender’s browser to after canceling the
			//approval for a payment; it is always required but only used
			//for payments that require approval (explicit payments)

			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");
			//			body.append("&");
			//			body.append("clientDetails.ipAddress=127.0.0.1");
			//			body.append("&");
			//			body.append("clientDetails.deviceId=mydevice");
			//			body.append("&");
			//			body.append("clientDetails.applicationId=PayNvpDemo");

			LOG.debug("Request:\n" + body.toString());
			String response = sendHttpPost(URL_PAY, body.toString(), headers);
			printResponse(response);
			setPaypal(paypal, response);
		} 
		catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Make multiple payments via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to submit for payment.
	 * @return the response.
	 */
	public final Paypal multipay(Paypal paypal) {
		LOG.debug("multipay " + paypal);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID",	SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);

			//			Party customer = PartyService.read(paypal.getCustomerid());
			StringBuilder body = new StringBuilder();
			//			body.append("senderEmail=''");// + customer.getEmailaddress());
			//			body.append("&");
			body.append("actionType=PAY");
			body.append("&");
			body.append("currencyCode=" + paypal.getCurrency());
			body.append("&");
			body.append("feesPayer=EACHRECEIVER");

			Party supplier = sqlSession.getMapper(PartyMapper.class).read(paypal.getSupplierid());
			body.append("&");
			body.append("receiverList.receiver(0).email=" + supplier.getEmailaddress());		
			//(Required) Receiver’s email address. This address can be unregistered with PayPal.com. If so, the receiver 				
			//does not receive the payment until they have created a Paypal account associated with the email address.

			//			body.append("&");
			//			body.append("receiverList.receiver(0).primary=true");
			//(Optional) Whether this receiver is the primary receiver, which makes the payment a chained payment. 
			//You can specify at most one primary receiver. Omit this field for simple and parallel payments.
			//Allowable values are: true – Primary receiver false – Secondary receiver (default)

			body.append("&");
			body.append("receiverList.receiver(0).amount=" + paypal.getAmount());
			//(Required) Amount to be credited to the receiver’s account.

			body.append("&");
			body.append("receiverList.receiver(1).email=chris_1260002082_biz@campsbayterrace.com"); //TODO:
			body.append("&");
			body.append("receiverList.receiver(1).primary=false");
			body.append("&");
			body.append("receiverList.receiver(1).amount=" + paypal.getAmount());

			body.append("&");
			body.append("receiverList.receiver(0).invoiceId=" + paypal.getId());	
			body.append("&");
			body.append("returnUrl=" + URL_PAY_RESULT + "?value=" + paypal.getId());
			body.append("&");
			body.append("cancelUrl=" + URL_PAY_RESULT + "?value=0");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");
			//			body.append("&");
			//			body.append("clientDetails.ipAddress=127.0.0.1");
			//			body.append("&");
			//			body.append("clientDetails.deviceId=mydevice");
			//			body.append("&");
			//			body.append("clientDetails.applicationId=PayNvpDemo");

			LOG.debug("Request:\n" + body.toString());
			String response = sendHttpPost(URL_PAY, body.toString(), headers);
			printResponse(response);
			setPaypal(paypal, response);
		}
		catch (Throwable x) {MonitorService.log(x);}
		finally {sqlSession.close();}
		return paypal;
	}

	/**
	 * Get payment details the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to pay.
	 * @return the response.
	 */
	public static final Paypal paymentDetails(Paypal paypal) {
		LOG.debug("paymentDetails " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID",	SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);

			StringBuilder body = new StringBuilder();
			body.append("payKey=AP-6L043935WS864332G");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");

			String response = sendHttpPost(URL_PAYMENT_DETAILS, body.toString(), headers);
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));

		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Get payment pre-approval via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to pay.
	 * @return the response.
	 */
	public static final Paypal preapproval(Paypal paypal) {
		LOG.debug("preapproval " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID", SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);

			StringBuilder body = new StringBuilder();
			body.append("payKey=AP-6L043935WS864332G");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");
			body.append("&");
			body.append("clientDetails.ipAddress=127.0.0.1");
			body.append("&");
			body.append("clientDetails.deviceId=mydevice");
			body.append("&");
			body.append("clientDetails.applicationId=PreApprovalNvpDemo");
			body.append("&");
			body.append("cancelUrl=http://localhost/cancel.html");
			body.append("&");
			body.append("currencyCode=USD");
			body.append("&");
			body.append("dayOfWeek=NO_DAY_SPECIFIED");
			body.append("&");
			body.append("endingDate=2009-08-27T00:00:00");
			body.append("&");
			body.append("maxAmountPerPayment=300");
			body.append("&");
			body.append("maxNumberOfPayments=3");
			body.append("&");
			body.append("maxNumberOfPaymentsPerPeriod=3");
			body.append("&");
			body.append("maxTotalAmountOfAllPayments=300");
			body.append("&");
			body.append("paymentPeriod=NO_PERIOD_SPECIFIED");
			body.append("&");
			body.append("returnUrl=http://localhost/success.html");
			body.append("&");
			body.append("senderEmail=buyer_1244668829_per@someone.com");
			body.append("&");
			body.append("startingDate=2009-07-27T00:00:00");
			body.append("&");
			body.append("pinType=NOT_REQUIRED");

			String response = sendHttpPost(URL_PREAPPROVAL, body.toString(), headers);
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));
		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Get payment pre-approval details via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to pay.
	 * @return the response.
	 */
	public static final Paypal preapprovalDetails(Paypal paypal) {
		LOG.debug("preapprovalDetails " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID", SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);


			StringBuffer body = new StringBuffer();
			body.append("preapprovalKey=PA-7YA703198X295882L");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");

			String response = sendHttpPost(URL_PREAPPROVAL_DETAILS, body.toString(), headers);
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));

		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Cancel payment pre-approval via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to submit.
	 * @return the response.
	 */
	public static final Paypal cancelPreapproval(Paypal paypal) {
		LOG.debug("cancelPreapproval " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID", SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);


			StringBuffer body = new StringBuffer();
			body.append("preapprovalKey=PA-7YA703198X295882L");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");

			String response = sendHttpPost(URL_CANCEL_PREAPPROVAL, body.toString(), headers);
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));

		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Convert currency via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to submit.
	 * @return the response.
	 */
	public static final Paypal convertCurrency(Paypal paypal) {
		LOG.debug("convertCurrency " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();
			headers.put("X-PAYPAL-SECURITY-USERID", SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);


			StringBuffer body = new StringBuffer();
			body.append("preapprovalKey=PA-7YA703198X295882L");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");

			String response = sendHttpPost(URL_CONVERT_CURRENCY, body.toString(), headers);
			LOG.debug("Response:\n" + response + "\n\n");
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));

		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/**
	 * Refund payment via the PayPal payment gateway.
	 *
	 * @param paypal the Paypal instance to submit.
	 * @return the response.
	 */
	public static final Paypal refund(Paypal paypal) {
		LOG.debug("refund " + paypal);
		try {
			//RequestHelper ppRequest = new RequestHelper();
			Properties headers = new Properties();

			/*TODO
			 *  
			 * The paypal and the headers contain sample test data 
			 * Change the data with valid data applicable to your application
			 */
			headers.put("X-PAYPAL-SECURITY-USERID", SECURITY_USERID);
			headers.put("X-PAYPAL-SECURITY-PASSWORD", SECURITY_PASSWORD);
			headers.put("X-PAYPAL-SECURITY-SIGNATURE", SECURITY_SIGNATURE);
			headers.put("X-PAYPAL-APPLICATION-ID", APPLICATION_ID);
			headers.put("X-PAYPAL-SERVICE-VERSION", SERVICE_VERSION);
			headers.put("X-PAYPAL-REQUEST-DATA-FORMAT", REQUEST_DATA_FORMAT);
			headers.put("X-PAYPAL-RESPONSE-DATA-FORMAT", RESPONSE_DATA_FORMAT);
			headers.put("X-PAYPAL-REQUEST-SOURCE", REQUEST_SOURCE);

			StringBuilder body = new StringBuilder();
			body.append("senderEmail=buyer_1244668829_per@someone.com");
			body.append("&");
			body.append("currencyCode=USD");
			body.append("&");
			body.append("payKey=AP-95D34342NM062242V");
			body.append("&");
			body.append("receiverList.receiver(0).email=tok_1244668961_biz@somecompany.com");
			body.append("&");
			body.append("receiverList.receiver(0).amount=0.01");
			body.append("&");
			body.append("receiverList.receiver(1).email=cert_biz@somecompany.com");
			body.append("&");
			body.append("receiverList.receiver(1).amount=0.05");
			body.append("&");
			body.append("requestEnvelope.errorLanguage=en_US");
			body.append("&");
			body.append("clientDetails.ipAddress=127.0.0.1");
			body.append("&");
			body.append("clientDetails.deviceId=mydevice");
			body.append("&");
			body.append("clientDetails.applicationId=APP-1JE4291016473214C");

			String response = sendHttpPost(URL_REFUND, body.toString(), headers);
			printResponse(response);
			paypal.setApprovalUrl(getApprovalUrl(response));

		} catch (Throwable x) {MonitorService.log(x);}
		return paypal;
	}

	/* 
	 * HTTP POST.
	 * 
	 * @param url the URL to which to POST.
	 * @param payload the data to POST.
	 * @param headers the header properties.
	 * @return the response.
	 */
	private static String sendHttpPost(String url, String payload, Properties headers) {
		SSLContext sc = null;
		sc = getDefaultSSLContext();
		HttpURLConnection connection = setupConnection(url, sc, headers, null);
		return sendHttpPost(payload, connection);
	}

	/* 
	 * HTTP POST.
	 * 
	 * @param payload the data to POST.
	 * @param connection the HTTP connection.
	 * @return the response.
	 */
	private static String sendHttpPost(String payload, HttpURLConnection connection) {
		String line, returnedResponse = "";
		BufferedReader reader = null;
		LOG.debug("Request: " + payload);

		try {
			OutputStream os = connection.getOutputStream();
			os.write(payload.toString().getBytes("UTF-8"));
			os.close();
			int status = connection.getResponseCode();
			if (status != 200) {
				LOG.debug("HTTP Error code " + status + " received, transaction not submitted");
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			} 
			else {reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));}

			while ((line = reader.readLine()) != null) {returnedResponse += line;}

		} 
		catch (Throwable x) {MonitorService.log(x);} 
		finally {
			try {
				if (reader != null) {reader.close();}
				if (connection != null){connection.disconnect();}
			}
			catch (Throwable x) {MonitorService.log(x);}
		}
		return returnedResponse;
	}

	/* 
	 * Gets HTTP URL Connection.
	 * 
	 * @param endpoint the end point to which to POST.
	 * @param sslContext the SSL context.
	 * @param headers the header properties.
	 * @param connectionProps the connection properties.
	 * @return the response.
	 */
	private static HttpURLConnection setupConnection(
			String endpoint,
			SSLContext sslContext, 
			Properties headers, 
			Properties connectionProps) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(endpoint);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			// TODO make configurable

			// connection.setConnectTimeout(Integer
			// .parseInt((String) connectionProps
			// .getProperty(CONNECTION_TIMEOUT)));
			// connection.setReadTimeout(Integer.parseInt((String)
			// connectionProps
			// .getProperty(READ_TIMEOUT)));

			Object[] keys = headers.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				connection.setRequestProperty((String) keys[i],
						(String) headers.get(keys[i]));
			}
		} 
		catch (Throwable x) {LOG.debug("Failed setting up HTTP Connection");}
		return connection;
	}

	/* Gets the default SSL context. */
	private static SSLContext getDefaultSSLContext() {
		try {
			SSLContext ctx = SSLContext.getInstance("SSL"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(System.currentTimeMillis());
			ctx.init(null, null, null);
			return ctx;
		} catch (Throwable x) {MonitorService.log(x);}
		return null;
	} 

	/*
	 * Sets the PayPal message and response.
	 * 
	 * @param paypal the Paypal instance.
	 * @param the response string.
	 */
	private static void setPaypal(Paypal paypal, String response) {
		LOG.debug("setPaypal: " + noError(response) + "\n" + response + "\n\n");		
		if (noError(response)){
			paypal.setApprovalUrl(getApprovalUrl(response));
			paypal.setState(Paypal.SUCCESS);
		}
		else {
			paypal.setApprovalUrl(getError(response)); //.replace("{0}", paypal.getName()).replace("{1}", ""));
			paypal.setState(Paypal.FAILURE);
		}
	}

	/*
	 * Prints the response.
	 * 
	 * @param response the response string.
	 */
	private static void printResponse(String response) {
		LOG.debug("printResponse:\n" + response + "\n\n");
		if (response != null) {
			StringTokenizer st = new StringTokenizer(response, "&");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				LOG.debug(token);
			}
		}
		LOG.debug("\n\n" + getApprovalUrl(response));
	}

	/*
	 * Checks if there is no error in the specified response.
	 *
	 * @param response the specified response.
	 * @return true, if there is no error in the specified response.
	 */
	private static final boolean noError(String response) {
		if (response == null) {return false;}
		boolean responseEnvelopeAck = false;
		boolean paymentExecStatus = false;
		StringTokenizer st = new StringTokenizer(response, "&");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.startsWith("responseEnvelope.ack")) {responseEnvelopeAck = token.endsWith("Success");}
			else if (token.startsWith("paymentExecStatus")) {paymentExecStatus = token.endsWith("CREATED") || token.endsWith("COMPLETED");}
		}
		return responseEnvelopeAck && paymentExecStatus;
	}

	/*
	 * Gets the error if there is an error in the specified response.
	 *
	 * @param response the specified response.
	 * @return the error string.
	 */
	private static final String getError(String response) {
		if (response == null) {return null;}
		try  {  
			StringTokenizer st = new StringTokenizer(response, "&");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				//LOG.debug("getError token " + token);
				if (token.startsWith("error(0).message")) {return URLDecoder.decode(token.substring(17), "UTF-8");}
				else if (token.startsWith("payErrorList.payError(0).error.message=")){return URLDecoder.decode(token.substring(39), "UTF-8");}
			}
		} catch(UnsupportedEncodingException x ) {LOG.debug("Unsupported Encoding Exception");}
		return null;
	}

	/*
	 * Gets the approval URL from the specified response.
	 *
	 * @param response the specified response.
	 * @return the approval URL.
	 */
	private static String getApprovalUrl(String response) {
		if (response != null) {
			StringTokenizer st = new StringTokenizer(response, "&");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				LOG.debug("getApprovalUrl token " + token);
				if (token.startsWith("payKey")) {return URL_PAYMENT_REDIRECT + token.substring(7);}
			}
		}
		return Process.BLANK;
	}
}
