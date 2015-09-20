package net.cbtltd.shared.finance.gateway.dibs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.shared.finance.gateway.dibs.model.Constants;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.AcceptedTicketAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.DeclinedTicketAuthResponse;
import net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket.TicketAuthResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;


public class TicketAuthCgiProcessor extends DibsServerProcessor {
	public static TicketAuthResponse process(String amount, String currency, String orderId, String merchantId, String ticketId) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
        HttpPost post = new HttpPost(Constants.TICKET_AUTH_CGI_URL);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair(Constants.TEST_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.AMOUNT_PARAMETER, amount));
        nameValuePairs.add(new BasicNameValuePair(Constants.CURRENCY_PARAMETER, currency));
        nameValuePairs.add(new BasicNameValuePair(Constants.MERCHANT_PARAMETER, merchantId));
        nameValuePairs.add(new BasicNameValuePair(Constants.ORDER_ID_PARAMETER, orderId));
        nameValuePairs.add(new BasicNameValuePair(Constants.TEXT_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.FULL_REPLY_PARAMETER, "1"));
        nameValuePairs.add(new BasicNameValuePair(Constants.TICKET_PARAMETER, ticketId));

        responseMap = process(post, nameValuePairs);
        TicketAuthResponse dibsResponse = null;
        if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.ACCEPTED_STATUS)) {
            dibsResponse = acceptedTicketAuthResponse(responseMap);
        } else if(responseMap.get(Constants.STATUS_PARAMETER).equals(Constants.DECLINED_STATUS)) {
        	dibsResponse = declinedTicketAuthResponse(responseMap);
        } else {
        	throw new Exception("Unknown status code");
        }
		return dibsResponse;
	}
	
	private static AcceptedTicketAuthResponse acceptedTicketAuthResponse(Map<String, String> responseMap) {
		AcceptedTicketAuthResponse dibsResponse = new AcceptedTicketAuthResponse();
		dibsResponse.setApprovalcode(responseMap.get(Constants.APPROVAL_CODE_PARAMETER));
		dibsResponse.setAuthkey(responseMap.get(Constants.AUTH_KEY_PARAMETER));
		dibsResponse.setCardtype(responseMap.get(Constants.CARD_TYPE_PARAMETER));
		dibsResponse.setFee(responseMap.get(Constants.FEE_PARAMETER));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
		dibsResponse.setTransact(responseMap.get(Constants.TRANSACT_PARAMETER));
    	return dibsResponse;
	}
	
	private static DeclinedTicketAuthResponse declinedTicketAuthResponse(Map<String, String> responseMap) {
		DeclinedTicketAuthResponse dibsResponse = new DeclinedTicketAuthResponse();
		dibsResponse.setReason(Integer.valueOf(responseMap.get(Constants.REASON_PARAMETER)));
		dibsResponse.setStatus(responseMap.get(Constants.STATUS_PARAMETER));
		dibsResponse.setMessage(responseMap.get(Constants.MESSAGE_PARAMETER));
    	return dibsResponse;
	}
}
