package net.cbtltd.server.project;

import java.io.IOException;
import java.util.Properties;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

public class PaymentConfiguration {
	
	private static PaymentConfiguration instance = null;
	
	// Properties
	private String gateway = null;
	private Double commission = null;
	private String gatewayId = null;
	private String gatewayToken = null;
	
	// Properties names
	private static final String GATEWAY_NAME_PROPERTY = "gateway";
	private static final String GATEWAY_ACCOUNT_ID_PROPERTY = "gateway.id";
	private static final String GATEWAY_ACCOUNT_TOKEN_PROPERTY = "gateway.token";
	private static final String COMMISSION_PROPERTY = "commission";
	
	private PaymentConfiguration(Properties props) {
		super();
		try {
			gateway = props.get(GATEWAY_NAME_PROPERTY).toString();
			commission = Double.valueOf(props.get(COMMISSION_PROPERTY).toString());
			gatewayId = props.get(GATEWAY_ACCOUNT_ID_PROPERTY).toString();
			gatewayToken = props.get(GATEWAY_ACCOUNT_TOKEN_PROPERTY).toString();
		} catch (NumberFormatException exception) {
			throw new ServiceException(Error.number_format, "during BookingPal configuration file parsing");
		}
	}
	
	public static PaymentConfiguration getInstance() {
		try {
			Properties props = ConfigurationLoader.getInstance().loadPaymentProperties();
			instance = new PaymentConfiguration(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public Double getCommission() {
		return commission;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getGatewayToken() {
		return gatewayToken;
	}

	public void setGatewayToken(String gatewayToken) {
		this.gatewayToken = gatewayToken;
	}
	
	@Override
	public String toString() {
		if(instance == null) {
			return null;
		}
		String toString = "Gateway: " + instance.getGateway() + "\n" +
				"Gateway account ID: " + instance.getGatewayId() + "\n" +
				"Gateway account token: " + instance.getGatewayToken() + "\n" +
				"BookingPal Commission: " + instance.getCommission();
		return toString;
	}
}
