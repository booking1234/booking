package net.cbtltd.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import net.cbtltd.rest.jetpay.JetpayHandler;
import net.cbtltd.rest.paygate.PaygateHandler;
import net.cbtltd.rest.stripe.StripeHandler;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.api.IsGateway;
import net.cbtltd.shared.finance.GatewayAction;

import org.apache.log4j.Logger;

public class GatewayService {

	public static final Logger LOG = Logger.getLogger(GatewayService.class.getName());
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd"); //ISO date format
	public static Hashtable<String, IsGateway> requestHandlers = null;

	
	private static synchronized Hashtable<String, IsGateway> getRequestHandlers () {
		if (requestHandlers == null) {
			requestHandlers = new Hashtable<String, IsGateway>();
			register(new JetpayHandler());
			register(new PaygateHandler());
//			register(new PaypalHandler());
			register(new StripeHandler());
			LOG.debug(requestHandlers.toString());
		}
		return requestHandlers;
	}

	public static void register(final IsGateway handler) {
		 String service = handler.getAltpartyid();
		if (requestHandlers.containsKey(service)) {throw new ServiceException(Error.service_duplicate, service);}
		requestHandlers.put(service, handler);
	}

	public static final void createPayment(GatewayAction action) {
		if (action.noAltpartyid()) {return;}
		else getRequestHandlers().get(action.getAltpartyid()).createPayment(action);
	}

	public static final GatewayAction read(GatewayAction action) {
		return getRequestHandlers().get(action.getAltpartyid()).getPayment(action);
	}
	
	public static void createRefund(GatewayAction action) {
		getRequestHandlers().get(action.getAltpartyid()).createRefund(action);
	}
}
