package net.cbtltd.shared.finance.gateway.dibs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.AcceptedCaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.CaptureResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.capture.DeclinedCaptureResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;


public class CaptureCgiProcessor extends DibsServerProcessor {
	
	public static CaptureResponse process(String amount, String md5key, String orderId, String transact, String merchantId) throws Exception {
		return processPayments(amount, md5key, orderId, transact, merchantId, false);
	}
	
	public static CaptureResponse processSplitPayment(String amount, String md5key, String orderId, String transact, String merchantId) throws Exception {
		return processPayments(amount, md5key, orderId, transact, merchantId, true);
	}
	
	private static CaptureResponse processPayments(String amount, String md5key, String orderId, String transact, String merchantId, boolean splitPayment) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
        HttpPost post = new HttpPost(Constants.CAPTURE_CGI_URL);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
        if(!RazorServer.isLive()) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER, amount));
        if(md5key != null) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.MD5_KEY_PARAMETER, md5key));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TRANSACT_PARAMETER, transact));
        if(splitPayment) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.SPLIT_CAPTURE_PARAMETER, "true"));
        	nameValuePairs.add(new BasicNameValuePair("close", "true"));
        }

        responseMap = process(post, nameValuePairs);
        CaptureResponse dibsResponse = null;
        if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.ACCEPTED_STATUS)) {
            dibsResponse = acceptedCaptureResponse(responseMap);
        } else if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.DECLINED_STATUS)) {
        	dibsResponse = declinedCaptureResponse(responseMap);
        } else {
        	throw new Exception("Unknown status code");
        }
		return dibsResponse;
	}
	
	private static AcceptedCaptureResponse acceptedCaptureResponse(Map<String, String> responseMap) {
		AcceptedCaptureResponse dibsResponse = new AcceptedCaptureResponse();
		dibsResponse.setCardtype(responseMap.get(Constants.CARD_TYPE_PARAMETER));
		dibsResponse.setResult(Integer.valueOf(responseMap.get(Constants.RESULT_PARAMETER)));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
		dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
		dibsResponse.setTransact(responseMap.get(Constants.TRANSACT_PARAMETER));
    	return dibsResponse;
	}
	
	private static DeclinedCaptureResponse declinedCaptureResponse(Map<String, String> responseMap) {
		DeclinedCaptureResponse dibsResponse = new DeclinedCaptureResponse();
		dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
//		dibsResponse.setReason(Integer.valueOf(responseMap.get(Constants.REASON_PARAMETER)));
		dibsResponse.setResult(Integer.valueOf(responseMap.get(Constants.RESULT_PARAMETER)));
		dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
    	return dibsResponse;
	}
}
