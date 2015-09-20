package net.cbtltd.server.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {

	private static ConfigurationLoader instance = null;
	private static final String RESOURCES_FOLDER = "resources" + File.separator;
	private static final String BOOKINGPAL_PAYMENT = "bookingpal-payment.properties";
	
	private ConfigurationLoader() {
		super();
	}
	
	public static ConfigurationLoader getInstance() {
		if(instance == null) {
			synchronized (ConfigurationLoader.class) {
				instance = new ConfigurationLoader();
			}
		}
		return instance;
	}
	
	public synchronized Properties loadPaymentProperties() throws IOException {
		return load(RESOURCES_FOLDER + BOOKINGPAL_PAYMENT);
	}
	
	private synchronized static Properties load(String fileName) throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		properties.load(inputStream);
		return properties;
	}
}
