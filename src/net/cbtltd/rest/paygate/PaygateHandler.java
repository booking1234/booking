/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.paygate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import net.cbtltd.shared.api.IsGateway;
import net.cbtltd.shared.finance.GatewayAction;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * The Class PaygateHandler is to make payments via the PayGate payment gateway.
 * 
 * @see https://plus.google.com/103695726651089961266/posts
 * 
 * It uses an XML message having the following information:
 * 
		Transaction Code (Number stat, Description sdesc)
		0 Not Done
		1 Approved
		2 Declined
		3 Paid
		4 Refunded
		5 Received by PayGate
		6 Replied to Client Returned by authtx if Unique Transaction is enabled
		and this transaction is a duplicate. In this case, check the
		result code to determine the original transaction result.
		
		Result Code (Number res Description rdesc)
		Credit Card Errors – These Result Codes (res attribute) are returned if the transaction cannot be completed successfully
		due to a problem with the card or data. Unless otherwise noted the stat attribute will be 2.
		900001 Call for Approval Card processing : Authorisation (authtx) Query (querytx)
		900002 Card Expired Card processing : Authorisation (authtx) Query (querytx)
		900003 Insufficient Funds Card processing : Authorisation (authtx) Query (querytx)
		900004 Invalid Card Number Card processing : Authorisation (authtx) Query (querytx)
		900005 Bank Interface Timeout Indicates a communications failure between the banks systems.
 */
public class PaygateHandler implements IsGateway {

	private static final Logger LOG = Logger.getLogger(PaygateHandler.class.getName());
	private static final DecimalFormat IF = new DecimalFormat("##");
	private final static String SERVER_URL = "https://www.paygate.co.za/payxml/process.trans?"; //The URL of the PayGate PayXML
	private final static String XML_VER = "4.0"; //The PayXML version number
	private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //The XML document declaration; precedes any XML document
	private final static String PAYGATE_DTD_DEF = "<!DOCTYPE protocol SYSTEM \"https://www.paygate.co.za/payxml/payxml_v4.dtd\">"; //The PayGate Document Type Definition
	private static final String PAYGATE_ID = "32741014884"; //"27171019168"; // 10011021600 test id unique to each PayGate client and ensures that all payments received are credited directly to the merchants bank account.
	private static final String PASSWORD = "n0xr3nt4l5 "; //"IrEtV85qUy"; // test for testing

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
	 * Creates the XML message to make payment.
	 *
	 * @param cref the transaction reference
	 * @param cname the name on the credit card.
	 * @param cc the credit card number.
	 * @param exp the credit card expiry date.
	 * @param value the payment value in the specified currency.
	 * @param cur the specified currency code.
	 * @param cvv the card CVV digits.
	 * @param email the email address of the payer.
	 * @param ip the IP address of the machine originating the message.
	 * @return the XML message.
	 * @throws RuntimeException the runtime exception.
	 */
	public static final Element getXML(
			String cref,
			String cname,
			String cc,
			String exp,	
			Double value,
			String cur,
			String cvv,
			String email,
			String ip
	) throws RuntimeException {
		try {
			if (cref == null || cref.isEmpty()) {throw new RuntimeException( "Invalid payment reference " + cur);}
			if (cname == null || cname.isEmpty()) {throw new RuntimeException( "Invalid card holder name " + cname);}
			if (cc == null || cc.isEmpty()) {throw new RuntimeException( "Invalid card number " + cc);}
			if (exp == null || exp.isEmpty()) {throw new RuntimeException( "Invalid expiry date " + exp);}
			if (value == null || value <= 0.0) {throw new RuntimeException( "Invalid payment value" + value);}
			if (cur == null || cur.isEmpty()) {throw new RuntimeException( "Invalid currency " + cur);}
			if (cvv == null || cvv.isEmpty()) {throw new RuntimeException( "Invalid CVV digits " + cvv);}
			if (email == null || email.isEmpty()) {throw new RuntimeException( "Invalid email address " + email);}
			if (ip == null || ip.isEmpty()) {throw new RuntimeException( "Invalid IP address " + ip);}

			StringBuilder sb = new StringBuilder();
			sb.append(XML_DECLARATION + PAYGATE_DTD_DEF); //Construct the XML document header
			sb.append("<protocol ver=\"" + XML_VER + "\" pgid=\"" + PAYGATE_ID + "\" pwd=\"" + PASSWORD + "\">"); //Build the transaction header
			sb.append("<authtx cref=\"" + cref + 
					"\" cname=\"" + cname + 
					"\" cc=\"" + cc +
					"\" exp=\"" + exp +
					"\" budp=\"" + "0" +
					"\" amt=\"" + IF.format(value * 100) +
					"\" cur=\"" + cur +
					"\" cvv=\"" + cvv +
					"\" email=\"" + email +
					"\" ip=\"" + ip + "\" />"); //Build the transaction body
			sb.append("</protocol>"); //Build the transaction trailer
			String request = sb.toString();
			LOG.debug( "Sending: " + request);

			URL url = new URL(SERVER_URL); //Open connection to server
			URLConnection conn = url.openConnection();
			conn.setDoOutput( true );

			OutputStreamWriter out = new OutputStreamWriter( conn.getOutputStream() ); //Post transaction request
			out.write(sb.toString());
			out.flush();
			out.close();

			//Get transaction response
			InputStream in = conn.getInputStream();
			SAXBuilder builder = new SAXBuilder();
			Document response = builder.build( in );
			in.close();

			//Parse XML response and log results.
			Element result = null;
			if (response.getRootElement().getName() != "protocol") {throw new RuntimeException( "Unexpected response string received: " + response.toString() );}
			result = response.getRootElement().getChild( "errorrx" );
			if (result != null ) {throw new RuntimeException("Payment Error: " + result.getAttributeValue( "edesc" ));} // + " ecode = " + result.getAttributeValue( "ecode" ));}
			result = response.getRootElement().getChild( "authrx" );					
			if (result == null ) {throw new RuntimeException("Unexpected response string received: " + response.toString());}
			if (result.getAttributeValue( "stat" ).equalsIgnoreCase("2")) {throw new RuntimeException("Payment Declined: " + result.getAttributeValue( "rdesc" ));}
			if (!result.getAttributeValue( "stat" ).equalsIgnoreCase("1")) {throw new RuntimeException("Invalid Payment Status: " + result.getAttributeValue( "sdesc" ));}

			LOG.debug( "--Auth Response Received--");
			LOG.debug( "Status (stat): " + result.getAttributeValue( "stat" ) );
			LOG.debug( "Status Description (sdesc): " + result.getAttributeValue( "sdesc" ) );
			LOG.debug( "Result (res): " + result.getAttributeValue( "res" ) );
			LOG.debug( "Result Description (rdesc): " + result.getAttributeValue( "rdesc" ) );
			LOG.debug( "Transaction ID (tid): " + result.getAttributeValue( "tid" ) );
			LOG.debug( "Reference (cref): " + result.getAttributeValue( "cref" ) );
			LOG.debug( "Auth Code (auth): " + result.getAttributeValue( "auth" ) );
			LOG.debug( "Card Type (ctype): " + result.getAttributeValue( "ctype" ) );
			return result;
		} 
		catch(MalformedURLException x) {throw new RuntimeException("Malformed URL: " + SERVER_URL);}
		catch(IOException x) {throw new RuntimeException("IO Exception");}
		catch(JDOMException x) {throw new RuntimeException("Malformed XML Exception");}
	}
}
