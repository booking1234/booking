package net.cbtltd.shared.finance.gateway.dibs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.cancel.AcceptedCancelResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.cancel.CancelResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.cancel.DeclinedCancelResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;


public class CancelCgiProcessor extends DibsServerProcessor {
	public static CancelResponse process(String username, String password, String md5key, String orderId, String transact, String merchantId) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
		String preparedUrl = Constants.CANCEL_CGI_URL.replaceAll("%username%", username).replaceAll("%password%", password);
        HttpPost post = new HttpPost(preparedUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
        if(!RazorServer.isLive()) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.MD5_KEY_PARAMETER, md5key));
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TEXT_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.TRANSACT_PARAMETER, transact));

        responseMap = process(post, nameValuePairs);
        CancelResponse dibsResponse = null;
        if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.ACCEPTED_STATUS)) {
            dibsResponse = acceptedCancelResponse(responseMap);
        } else if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.DECLINED_STATUS)) {
        	dibsResponse = declinedCancelResponse(responseMap);
        } else {
        	throw new Exception("Unknown status code");
        }
		return dibsResponse;
	}
	
	private static AcceptedCancelResponse acceptedCancelResponse(Map<String, String> responseMap) {
		AcceptedCancelResponse dibsResponse = new AcceptedCancelResponse();
		dibsResponse.setCardtype(responseMap.get(Constants.CARD_TYPE_PARAMETER));
		dibsResponse.setResult(responseMap.get(Constants.RESULT_PARAMETER));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
		dibsResponse.setTransact(responseMap.get(Constants.TRANSACT_PARAMETER));
    	return dibsResponse;
	}
	
	private static DeclinedCancelResponse declinedCancelResponse(Map<String, String> responseMap) {
		DeclinedCancelResponse dibsResponse = new DeclinedCancelResponse();
		dibsResponse.setReason(responseMap.get(Constants.REASON_PARAMETER));
		dibsResponse.setResult(responseMap.get(Constants.RESULT_PARAMETER));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
    	return dibsResponse;
	}
}
