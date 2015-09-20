package net.cbtltd.shared.finance.gateway.dibs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.refund.RefundResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;


public class RefundCgiProcessor extends DibsServerProcessor {
	public static RefundResponse process(String username, String password, String amount, String currency, String md5key, String orderId, String transact, String merchantId)
			throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
		String preparedUrl = Constants.REFUND_CGI_URL.replaceAll("%username%", username).replaceAll("%password%", password);
        HttpPost post = new HttpPost(preparedUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER, amount));
        nameValuePairs.add(new BasicNameValuePair(Constants.CURRENCY_PARAMETER, currency));
        if(md5key != null) {
        	nameValuePairs.add(new BasicNameValuePair(Constants.MD5_KEY_PARAMETER, md5key));
        }
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TEXT_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.TRANSACT_PARAMETER, transact));

        responseMap = process(post, nameValuePairs);
        RefundResponse dibsResponse = refundResponse(responseMap);
		return dibsResponse;
	}
	
	private static RefundResponse refundResponse(Map<String, String> responseMap) {
		RefundResponse dibsResponse = new RefundResponse();
		dibsResponse.setResult(responseMap.get(Constants.RESULT_PARAMETER));
		dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
		dibsResponse.setCardType(responseMap.get(Constants.CARD_TYPE_PARAMETER));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
    	return dibsResponse;
	}
}
