package net.cbtltd.shared.finance.gateway;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.anet.ANetHandler;
import net.cbtltd.rest.dibs.DibsHandler;
import net.cbtltd.rest.paypal.PaypalHandlerNew;
import net.cbtltd.rest.rent.RentHandler;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.project.PaymentConfiguration;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.PaymentGatewayProvider;

public class PaymentGatewayHandlerFactory {
	private PaymentGatewayHandlerFactory() {super();}
	
	public static GatewayHandler getHandler(int paymentGatewayId, GatewayInfo gateway) {
		PaymentGatewayProvider paymentGatewayProvider = PaymentGatewayHolder.getPaymentGateway(paymentGatewayId);
		if(paymentGatewayProvider.getName().equalsIgnoreCase(PaymentGatewayHolder.ANET)) {
			return new ANetHandler(gateway);
		} else if (paymentGatewayProvider.getName().equalsIgnoreCase(PaymentGatewayHolder.PAYPAL)) {
			return new PaypalHandlerNew(gateway);
		} else if (paymentGatewayProvider.getName().equalsIgnoreCase(PaymentGatewayHolder.BOOKINGPAL)) {
			return getBookingpalGateway(gateway);
		} else if(paymentGatewayProvider.getName().equalsIgnoreCase(PaymentGatewayHolder.DIBS)) {
			return new DibsHandler(gateway);
		} else if (paymentGatewayProvider.getName().equalsIgnoreCase(PaymentGatewayHolder.RENT)) {
			return new RentHandler(gateway);
		}
		throw new ServiceException(Error.payment_gateway);
	}
	
	private static GatewayHandler getBookingpalGateway(GatewayInfo gateway) {
		String gatewayName = PaymentConfiguration.getInstance().getGateway();
		if(gatewayName.equals(PaymentGatewayHolder.ANET)) {
			return new ANetHandler(gateway);
		} else if(gatewayName.equals(PaymentGatewayHolder.PAYPAL)) {
			return new PaypalHandlerNew(gateway);
		} else if(gatewayName.equals(PaymentGatewayHolder.DIBS)) {
			return new DibsHandler(gateway);
		} else if(gatewayName.equals(PaymentGatewayHolder.RENT)) {
			return new RentHandler(gateway);
		} else {
			throw new ServiceException(Error.payment_gateway, "in BookingPal configuration");
		}
	}
}
