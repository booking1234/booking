package net.cbtltd.shared.finance.gateway.dibs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AcceptedAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.AuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.DeclinedAuthResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;


public class AuthCgiProcessor extends DibsServerProcessor {
	
	public static AuthResponse process(CreditCard creditCard, String amount, String currency, String orderId, String merchantId) throws Exception {
		return processPayment(creditCard, amount, currency, orderId, merchantId, false);
	}
	
	public static AuthResponse processSplitPayment(CreditCard creditCard, String amount, String currency, String orderId, String merchantId) throws Exception {
		return processPayment(creditCard, amount, currency, orderId, merchantId, true);
	}
	
	public static AuthResponse processPayment(CreditCard creditCard, String amount, String currency, String orderId, String merchantId, boolean splitPayment) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
		Integer amountInt = Integer.valueOf(amount);
        HttpPost post = new HttpPost(Constants.AUTH_CGI_URL);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
        if(!RazorServer.isLive()) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
        if(splitPayment) {
        	nameValuePairs.add(new BasicNameValuePair("split", "2"));
        	nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER + "1", String.valueOf(amountInt / 2)));
        	nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER + "2", String.valueOf(amountInt / 2)));
        } else {
        	nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER, amount));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.CURRENCY_PARAMETER, currency));
        nameValuePairs.add(new BasicNameValuePair(Constants.CARD_NUMBER_PARAMETER, creditCard.getNumber()));
        nameValuePairs.add(new BasicNameValuePair(Constants.CVC_PARAMETER, creditCard.getSecurityCode()));
        nameValuePairs.add(new BasicNameValuePair(Constants.EXPIRATION_MONTH_PARAMETER, creditCard.getMonth()));
        nameValuePairs.add(new BasicNameValuePair(Constants.EXPIRATION_YEAR_PARAMETER, creditCard.getYear()));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TEXT_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.FULL_REPLY_PARAMETER, "1"));

        responseMap = process(post, nameValuePairs);
        AuthResponse dibsResponse = null;
        if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.ACCEPTED_STATUS)) {
            dibsResponse = acceptedAuthResponse(responseMap);
        } else if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.DECLINED_STATUS)) {
        	dibsResponse = declinedAuthResponse(responseMap);
        } else {
        	throw new Exception("Unknown status code");
        }
		return dibsResponse;
	}
	
	public static AuthResponse createTicket(CreditCard creditCard, String currency, String orderId, String merchantId) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
//		Integer amountInt = Integer.valueOf(amount);
        HttpPost post = new HttpPost(Constants.AUTH_CGI_URL);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
        if(!RazorServer.isLive()) {
//        	nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
       	nameValuePairs.add(new BasicNameValuePair("preauth", "true"));
        nameValuePairs.add(new BasicNameValuePair(Constants.CURRENCY_PARAMETER, currency));
        nameValuePairs.add(new BasicNameValuePair(Constants.CARD_NUMBER_PARAMETER, creditCard.getNumber()));
        nameValuePairs.add(new BasicNameValuePair(Constants.CVC_PARAMETER, creditCard.getSecurityCode()));
        nameValuePairs.add(new BasicNameValuePair(Constants.EXPIRATION_MONTH_PARAMETER, creditCard.getMonth()));
        nameValuePairs.add(new BasicNameValuePair(Constants.EXPIRATION_YEAR_PARAMETER, creditCard.getYear()));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TEXT_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.FULL_REPLY_PARAMETER, "1"));

        responseMap = process(post, nameValuePairs);
        AuthResponse dibsResponse = null;
        if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.ACCEPTED_STATUS)) {
            dibsResponse = acceptedAuthResponse(responseMap);
        } else if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.DECLINED_STATUS)) {
        	dibsResponse = declinedAuthResponse(responseMap);
        } else {
        	throw new Exception("Unknown status code");
        }
		return dibsResponse;
	}
	
	private static AcceptedAuthResponse acceptedAuthResponse(Map<String, String> responseMap) {
		AcceptedAuthResponse dibsResponse = new AcceptedAuthResponse();
    	dibsResponse.setApprovalCode(responseMap.get(Constants.APPROVAL_CODE_PARAMETER));
    	dibsResponse.setCardType(responseMap.get(Constants.CARD_TYPE_PARAMETER));
    	dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
    	dibsResponse.setTransactionId(responseMap.get(Constants.TRANSACT_PARAMETER));
    	return dibsResponse;
	}
	
	private static DeclinedAuthResponse declinedAuthResponse(Map<String, String> responseMap) {
		DeclinedAuthResponse dibsResponse = new DeclinedAuthResponse();
    	dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
    	dibsResponse.setReason(Integer.valueOf(responseMap.get(Constants.REASON_PARAMETER)));
    	dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
    	return dibsResponse;
	}
}
