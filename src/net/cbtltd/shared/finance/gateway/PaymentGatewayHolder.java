package net.cbtltd.shared.finance.gateway;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.shared.PaymentGatewayProvider;

public class PaymentGatewayHolder {
	
	private static PaymentGatewayHolder paymentGatewayHolder = null;
	private static List<PaymentGatewayProvider> paymentGatewayProviders = null;
	public static final String PAYPAL = "PayPal";
	public static final String ANET = "Authorize.net";
	public static final String BOOKINGPAL = "BookingPal";
	public static final String DIBS = "DIBS";
	public static final String RENT = "RENT";
	
	
	private PaymentGatewayHolder() {
		SqlSession sqlSession = RazorServer.openSession();
		paymentGatewayProviders = sqlSession.getMapper(PaymentGatewayProviderMapper.class).list();
	}
	
	public static PaymentGatewayHolder getInstance() {
		if(paymentGatewayHolder == null) {
			paymentGatewayHolder = new PaymentGatewayHolder();
		}
		return paymentGatewayHolder;
	}
	
	public static PaymentGatewayProvider getPaymentGateway(int id) {
		getInstance();
		for(PaymentGatewayProvider paymentGatewayProvider : paymentGatewayProviders) {
			if(paymentGatewayProvider.getId() == id) {
				return paymentGatewayProvider;
			}
		}
		return null;
	}
	
	public static PaymentGatewayProvider getPaypal() {
		return getProviderByName(PAYPAL);
	}
	
	public static PaymentGatewayProvider getANet() {
		return getProviderByName(ANET);
	}
	
	public static PaymentGatewayProvider getBookingPal() {
		return getProviderByName(BOOKINGPAL);
	}
	
	public static PaymentGatewayProvider getDibs() {
		return getProviderByName(DIBS);
	}
	
	public static PaymentGatewayProvider getRent() {
		return getProviderByName(RENT);
	}
	
	private static PaymentGatewayProvider getProviderByName(String name) {
		getInstance();
		for(PaymentGatewayProvider paymentGatewayProvider : paymentGatewayProviders) {
			if(paymentGatewayProvider.getName().equalsIgnoreCase(name)) {
				return paymentGatewayProvider;
			}
		}
		return null;
	}
}
