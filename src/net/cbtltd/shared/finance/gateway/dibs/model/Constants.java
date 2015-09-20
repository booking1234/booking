package net.cbtltd.shared.finance.gateway.dibs.model;

public class Constants {
	// statuses
	public static final String ACCEPTED_STATUS = "ACCEPTED";
	public static final String DECLINED_STATUS = "DECLINED";

	public static String TEST_PARAMETER = "test";
	
	// response parameters
	public static final String AMOUNT_PARAMETER = "amount";
	public static final String CARD_NUMBER_PARAMETER = "cardno";
	public static final String CURRENCY_PARAMETER = "currency";
	public static final String CVC_PARAMETER = "cvc";
	public static final String EXPIRATION_MONTH_PARAMETER = "expmon";
	public static final String EXPIRATION_YEAR_PARAMETER = "expyear";
	public static final String FULL_REPLY_PARAMETER = "fullreply";
	public static final String MD5_KEY_PARAMETER = "md5key";
	public static final String MERCHANT_PARAMETER = "merchant";
	public static final String ORDER_ID_PARAMETER = "orderId";
	public static final String TEXT_REPLY_PARAMETER = "textreply";
	public static final String APPROVAL_CODE_PARAMETER = "approvalcode";
	public static final String CARD_TYPE_PARAMETER = "cardtype";
	public static final String STATUS_PARAMETER = "status";
	public static final String TRANSACT_PARAMETER = "transact";
	public static final String REASON_PARAMETER = "reason";
	public static final String RESULT_PARAMETER = "result";
	public static final String MESSAGE_PARAMETER = "message";
	public static final String AUTH_KEY_PARAMETER = "authkey";
	public static final String FEE_PARAMETER = "fee";
	
	// capture.cgi request parameters
	public static final String ODER_ID_REQUEST = "orderid";
	public static final String TICKET_PARAMETER = "ticket";
	public static final String SPLIT_CAPTURE_PARAMETER = "splitpay";
	public static final String SPLIT_AUTH_PARAMETER = "split";
	
	public static final String AUTH_CGI_URL = "https://payment.architrade.com/cgi-ssl/auth.cgi";
	public static final String CAPTURE_CGI_URL = "https://payment.architrade.com/cgi-bin/capture.cgi";
	public static final String CANCEL_CGI_URL = "https://%username%:%password%@payment.architrade.com/cgi-adm/cancel.cgi"; // %username% and %password% are tokens that should be replaced
	public static final String REFUND_CGI_URL = "https://%username%:%password%@payment.architrade.com/cgi-adm/refund.cgi";
	public static final String TICKET_AUTH_CGI_URL = "https://payment.architrade.com/cgi-ssl/ticket_auth.cgi";
	public static final String SUPPLEMENTARY_AUTH_CGI_URL = "https://%username%:%password%payment.architrade.com/cgi-ssl/suppl_auth.cgi";
}
