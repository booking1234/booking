package net.cbtltd.server.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.log4j.Logger;

public class RazorHostsConfig {
	
	static final String HOSTS_CONFIG_FILE = "Hosts.properties";
	
	private static CompositeConfiguration hostsConfig = new CompositeConfiguration();
	// hosts
	private static final String AFFILIATE_URL = "affiliateUrl";
	private static final String AFFILIATE_HELP_URL = "affiliateHelpUrl";
	private static final String APPLICATION_URL = "applicationUrl";
	private static final String CLOUD_URL = "cloudUrl";
	private static final String BROCHURE_URL= "brochureUrl";
	private static final String COPYRIGHT_NOTICE = "copyrightNotice";
	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String HOME_URL = "homeUrl";
	private static final String INFO_URL = "infoUrl";
	private static final String JSON_URL = "jsonUrl";
	private static final String KIGO_URL = "kigoUrl";
	private static final String MONITOR_EMAIL_ADDRESS = "monitorEmailAddress";
	private static final String OFFLINE_URL = "offlineUrl";
	private static final String PICTURES_URL = "picturesUrl";
	private static final String AMAZON_PICTURES_URL = "amazonPicturesUrl";
	private static final String PRODUCT_URL = "productUrl";
	private static final String RATE_URL = "rateUrl";
	private static final String ROOT_URL = "rootUrl";
	private static final String SENDER = "sender";
	private static final String CANCELLATION_LINK = "cancellationLink";
	private static final String REPORT_URL = "reportUrl";
	
	private static final Logger LOG = Logger.getLogger(RazorHostsConfig.class);
	
	
	
	static {
		hostsConfig.addConfiguration(new SystemConfiguration());
		try {
			hostsConfig.addConfiguration(new PropertiesConfiguration(HOSTS_CONFIG_FILE));
		} catch (ConfigurationException e) {
			LOG.error(e.getMessage());
		}
	}
	
	public static String getAffiliateUrl() {
		return hostsConfig.getString(AFFILIATE_URL);
	}
	
	public static String getAffiliateHelpUrl() {
		return hostsConfig.getString(AFFILIATE_HELP_URL);
	}
	
	public static String getApplicationUrl() {
		return hostsConfig.getString(APPLICATION_URL);
	}
	
	public static String getCloudUrl() {
		return hostsConfig.getString(CLOUD_URL);
	}
	
	public static String getBrochureUrl() {
		return hostsConfig.getString(BROCHURE_URL);
	}
	
	public static String getCopyrightNotice() {
		return hostsConfig.getString(COPYRIGHT_NOTICE);
	}
	
	public static String getEmailAddress() {
		return hostsConfig.getString(EMAIL_ADDRESS);
	}
	
	public static String getHomeUrl() {
		return hostsConfig.getString(HOME_URL);
	}
	
	public static String getInfoUrl() {
		return hostsConfig.getString(INFO_URL);
	}
	
	public static String getJsonUrl() {
		return hostsConfig.getString(JSON_URL);
	}
	
	public static String getKigoUrl() {
		return hostsConfig.getString(KIGO_URL);
	}
	
	public static String getMonitorEmailAddress() {
		return hostsConfig.getString(MONITOR_EMAIL_ADDRESS);
	}
	
	public static String getOfflineUrl() {
		return hostsConfig.getString(OFFLINE_URL);
	}
	
	public static String getPicturesUrl() {
		return hostsConfig.getString(PICTURES_URL);
	}
	
	public static String getAmazonPicturesUrl() {
		return hostsConfig.getString(AMAZON_PICTURES_URL);
	}
	
	public static String getProductUrl() {
		return hostsConfig.getString(PRODUCT_URL);
	}
	
	public static String getRateUrl() {
		return hostsConfig.getString(RATE_URL);
	}
	
	public static String getRootUrl() {
		return hostsConfig.getString(ROOT_URL);
	}
	
	public static String getSender() {
		return hostsConfig.getString(SENDER);
	}
	
	public static String getCancellationLink() {
		return hostsConfig.getString(CANCELLATION_LINK);
	}
	
	public static String getReportUrl() {
		return hostsConfig.getString(REPORT_URL);
	}
}
