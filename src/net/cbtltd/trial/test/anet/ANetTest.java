package net.cbtltd.trial.test.anet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;
import net.authorize.sim.Fingerprint;
import net.cbtltd.rest.anet.ANetHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.finance.gateway.ANet;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.GatewayInfo;

import org.apache.commons.httpclient.HttpException;
import org.apache.ibatis.session.SqlSession;

/*

These numbers seem to work. The expiration date must be the current date or later. I do not have a MasterCard test number.

- Visa: 4222222222222222

- American Express Test Card: 370000000000002

- Discover Test Card: 6011000000000012

- Visa Test Card: 4007000000027

- Second Visa Test Card: 4012888818888

- JCB: 3088000000000017

- Diners Club/ Carte Blanche: 38000000000006

- MC: 5555555555554444 or 5105105105105100

 */
public class ANetTest {
	public static void main(String[] args) throws Exception {
		GatewayInfo anet = new GatewayInfo();
		ANetHandler anetHandler = new ANetHandler(anet);
		anet.setId("47R5Rjv2PP");
		anet.setToken("544j66HY9seWAv4k");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("4111 1111 1111 1111");
		creditCard.setMonth("12");
		creditCard.setYear("2015");
		
		SqlSession sqlSession = RazorServer.openSession();
		System.out.println(anetHandler.createPaymentByCreditCard(sqlSession, "USD", 2.0, "12345"));
		sqlSession.close();
//		System.out.println(result.getTarget().getTransactionId());
//		System.out.println("responseCode = " + result.getResponseCode());
//		System.out.println("responseText = " + result.getResponseText());
//		System.out.println("reasonResponseCode = " + result.getReasonResponseCode());
//		postRequest(anet);
//		officialPayment(anet);
	}

	// Working method
	private static void postRequest(ANet anet) throws HttpException, IOException {
		Merchant merchant = Merchant.createMerchant(Environment.SANDBOX, anet.getId(), anet.getToken());

//		CreditCard creditCard = CreditCard.createCreditCard();
//		creditCard.setCreditCardNumber("4111 1111 1111 1111");
//		creditCard.setExpirationMonth("12");
//		creditCard.setExpirationYear("2015");

		// Create AUTH transaction
		Transaction authCaptureTransaction = merchant.createAIMTransaction(TransactionType.AUTH_CAPTURE, new BigDecimal(1.99));
//		authCaptureTransaction.setCreditCard(creditCard);

		Result<Transaction> result = (Result<Transaction>) merchant.postTransaction(authCaptureTransaction);
		System.out.println(result.getTarget().getTransactionId());
		System.out.println("responseCode = " + result.getResponseCode());
		System.out.println("responseText = " + result.getResponseText());
		System.out.println("reasonResponseCode = " + result.getReasonResponseCode());
	}

	private static void officialPayment(ANet anet) throws IOException {
		URL post_url = new URL("https://test.authorize.net/gateway/transact.dll");

		Hashtable<String, String> postValues = new Hashtable<String, String>();

		// the API Login ID and Transaction Key must be replaced with valid values
		postValues.put("x_login", anet.getId());
		postValues.put("x_tran_key", anet.getToken());

		postValues.put("x_version", "3.1");
		postValues.put("x_delim_data", "TRUE");
		postValues.put("x_delim_char", "|");
		postValues.put("x_relay_response", "FALSE");

		postValues.put("x_type", "AUTH_CAPTURE");
		postValues.put("x_method", "CC");
		postValues.put("x_card_num", "4111111111111111");
		postValues.put("x_exp_date", "0115");

		postValues.put("x_amount", "19.99");
		postValues.put("x_description", "Sample Transaction");

		postValues.put("x_first_name", "John");
		postValues.put("x_last_name", "Doe");
		postValues.put("x_address", "1234 Street");
		postValues.put("x_state", "WA");
		postValues.put("x_zip", "98004");
		// Additional fields can be added here as outlined in the AIM
		// integration
		// guide at: http://developer.authorize.net

		// This section takes the input fields and converts them to the proper
		// format
		// for an http post. For example: "x_login=username&x_tran_key=a1B2c3D4"
		StringBuffer post_string = new StringBuffer();
		Enumeration<String> keys = postValues.keys();
		while (keys.hasMoreElements()) {
			String key = URLEncoder.encode(keys.nextElement().toString(), "UTF-8");
			String value = URLEncoder.encode(postValues.get(key).toString(), "UTF-8");
			post_string.append(key + "=" + value + "&");
		}

		// The following section provides an example of how to add line item
		// details to
		// the post string. Because line items may consist of multiple values
		// with the
		// same key/name, they cannot be simply added into the above array.
		//
		// This section is commented out by default.
		/*
		 * String[] line_items = { "item1<|>golf balls<|><|>2<|>18.95<|>Y",
		 * "item2<|>golf bag<|>Wilson golf carry bag, red<|>1<|>39.99<|>Y",
		 * "item3<|>book<|>Golf for Dummies<|>1<|>21.99<|>Y"};
		 * 
		 * for (int i = 0; i < line_items.length; i++) { String value =
		 * line_items[i]; post_string.append("&x_line_item=" +
		 * URLEncoder.encode(value)); }
		 */

		// Open a URLConnection to the specified post url
		URLConnection connection = post_url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		// this line is not necessarily required but fixes a bug with some
		// servers
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// submit the post_string and close the connection
		DataOutputStream requestObject = new DataOutputStream(connection.getOutputStream());
		requestObject.write(post_string.toString().getBytes());
		requestObject.flush();
		requestObject.close();

		// process and read the gateway response
		BufferedReader rawResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		String responseData = rawResponse.readLine();
		rawResponse.close(); // no more data

		// split the response into an array
		String[] responses = responseData.split("\\|");

		// The results are output to the screen in the form of an html numbered
		// list.
		for (Iterator<String> iter = Arrays.asList(responses).iterator(); iter.hasNext();) {
//			System.out.println(iter.next().trim());
		}
		// individual elements of the array could be accessed to read certain
		// response
		// fields. For example, response_array[0] would return the Response
		// Code,
		// response_array[2] would return the Response Reason Code.
		// for a list of response fields, please review the AIM Implementation
		// Guide
	}

	// private ANetRequest prepareRequest(String sequence, String timestamp,
	// String hash) {
	// ANetRequest request = new ANetRequest();
	// request.setAmount("1.99");
	// request.setHash(hash);
	// request.setInvoiceNum("1");
	// request.setLogin(login);
	// request.setMethod(method);
	// request.setNotes(notes);
	// request.setRelayUrl(relayUrl);
	// request.setSequence(sequence);
	// request.setTestRequest(testRequest);
	// request.setTimestamp(timestamp);
	// request.setType(type);
	// request.setVersion(version);
	// }
}
