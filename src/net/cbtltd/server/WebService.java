/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.api.CurrencyrateMapper;
import net.cbtltd.server.web.Exchangerates;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasUrls;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gson.Gson;

/**
 * The Class WebService is to access RESTful web services. Specialised operations may cache web service
 * results in the database (eg: currency exchange rates) for efficiency and fault tolerance.
 * @see <pre>http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml</pre>
 * @see <pre>"http://webservices.bermilabs.com/exchange/" + Currency.EUR + "/" + tocurrency</pre>
 * @see Google url = string.Format("http://www.google.com/ig/calculator?hl=en&q={2}{0}%3D%3F{1}", fromCurrency.ToUpper(), toCurrency.ToUpper(), amount);
 * @see http://openexchangerates.org/documentation/
 */
public class WebService {

	private static final Logger LOG = Logger.getLogger(WebService.class.getName());
	private static final Gson GSON = new Gson();
//	private static final String URL = HasUrls.BERMI_EXCHANGE_RATE_SERVICE  + Currency.EUR + "/";
	private static HashMap<String, Double> exchangerates = new HashMap<String, Double>();
	private static Exchangerates rs = null; //JSON response

	public static final void getRoute(String origin, String destination) {
		try {
			if (rs == null || rs.isExpired()) {
				
				String url = HasUrls.GOOGLE_DIRECTIONS_SERVICE + "&origin=" + origin + "&destination=" + destination + "&sensor=false";
				String jsonString = getConnection(new URL(url), null);
				LOG.debug("getRoute jsonString " + jsonString);
//				rs = GSON.fromJson(jsonString,Exchangerates.class);
//				Log.debug("refreshRates rs " + rs);
//				if (rs == null || rs.isExpired()) {throw new ServiceException(Error.currency_exchange_rate);}
			}
		}
		catch (Throwable x) {MonitorService.log(x);}
	}
	

	private static final void refreshRates() {
		try {
			if (rs == null || rs.isExpired()) {
				String jsonString = getConnection(new URL(HasUrls.OPEN_EXCHANGE_RATE_SERVICE), null);
				LOG.debug("refreshRates jsonString " + jsonString);
				rs = GSON.fromJson(jsonString,Exchangerates.class);
				Log.debug("refreshRates rs " + rs);
				if (rs == null || rs.isExpired()) {throw new ServiceException(Error.currency_exchange_rate);}
			}
		}
		catch (Throwable x) {MonitorService.log(x);}
	}
	
	public static final  HashMap<String, Double> getRates() {
		refreshRates();
		return rs.getRates();
	}
	
	/*
	 * Gets the current exchange rate given the currency conversion pair and date.
	 * This service has EUR base rates, so may need to combine two separate exchange rates.
	 * @see service at http://webservices.bermilabs.com/exchange/
	 * 
	 * @param fromcurrency the code of the currency from which to convert.
	 * @param tocurrency the code of the currency to which to convert.
	 * @return the current exchange rate.
	 */
	private static final Double getWebRate(String fromcurrency, String tocurrency) {
		refreshRates();
		Double torate = rs.getRate(tocurrency);
		if (Currency.Code.USD.name().equalsIgnoreCase(fromcurrency)) {return torate;}
		else {
			Double fromrate = rs.getRate(fromcurrency);
			return (torate == null || fromrate == null || fromrate == 0.0) ? 0.0 : torate / fromrate;
		}
	}

//	private static final Double getWebRate(String fromcurrency, String tocurrency) {
//		try {
//			RestClientWrapper restClientWrapper;
//			restClientWrapper = new RestClientWrapper(URL + tocurrency, "", "", "localhost", 9000, "xml");
//			double torate = Double.valueOf(restClientWrapper.get());
//			if (Currency.EUR.equalsIgnoreCase(fromcurrency)) {return torate;}
//			else {
//				restClientWrapper = new RestClientWrapper(URL + fromcurrency, "", "", "localhost", 9000, "xml");
//				double fromrate = Double.valueOf(restClientWrapper.get());
//				return torate/fromrate;
//			}
//		} 
//		catch (RestClientException x) {MonitorService.log(x);}
//		return null;
//	}

	/**
	 * Gets the exchange rate given the currency conversion pair and date.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param fromcurrency the code of the currency from which to convert.
	 * @param tocurrency the code of the currency to which to convert.
	 * @param date the date of the exchange rate.
	 * @return the exchange rate.
	 */
	public static synchronized final Double getRate(SqlSession sqlSession, String fromcurrency, String tocurrency) {
		return getRate(sqlSession, new Currencyrate(fromcurrency, tocurrency, new Date()));
	}

	public static synchronized final Double getRate(SqlSession sqlSession, String fromcurrency, String tocurrency, Date date) {
		return getRate(sqlSession, new Currencyrate(fromcurrency.toUpperCase(), tocurrency.toUpperCase(), date));
	}

	/**
	 * Gets the exchange rate for the specified currency rate instance.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the specified currency rate instance.
	 * @return the exchange rate.
	 */
	public static synchronized final Double getRate(SqlSession sqlSession, Currencyrate action) {
		LOG.debug("Currencyrate in " + action);
		if (action.hasId(action.getToid())) {return 1.0;}
		String key = action.key();
		if (!exchangerates.containsKey(key)) {
			Currencyrate currencyrate = sqlSession.getMapper(CurrencyrateMapper.class).readbyexample(action);
			if (currencyrate == null) {
				try {
					if(action.getId() == null || action.getToid() == null) {
						return null;
					}
					Double rate = getWebRate(action.getId(), action.getToid());
					action.setRate(rate == null ? 0.0 : rate);
					sqlSession.getMapper(CurrencyrateMapper.class).create(action);
					sqlSession.commit();
					currencyrate = action;
				}
				catch (Exception x) {
					sqlSession.rollback(); 
					MonitorService.log(x);
				}
			}
			exchangerates.put(key, currencyrate.getRate());
			LOG.debug("New Rate " + key + " = " + exchangerates.get(key));
		}
		LOG.debug("Currencyrate out " + exchangerates.get(key));
		return exchangerates.get(key);
	}
	
	/**
	 * Gets the connection to the JSON server.
	 *
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the JSON string returned by the message.
	 * @throws Throwable the exception thrown by the method.
	 */
	private static final String getConnection(URL url, String rq) throws Throwable {
		String jsonString = "";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			//connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			if (rq != null) {
				connection.setRequestProperty("Accept", "application/json");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + "URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {jsonString += line;}
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return jsonString;
	}

	/**
	 * Gets the HTTP response.
	 *
	 * @param url the url of the service.
	 * @param nameids the parameter key value pairs.
	 * @return the HTTP response string.
	 * @throws Throwable the exception that can be thrown.
	 */
	public static final String getHttpResponse(String url, NameId[] nameids) throws Throwable {
	    GetMethod get = new GetMethod(url);
	    get.addRequestHeader("Accept" , "text/plain");
	    HttpMethodParams params = new HttpMethodParams();
	    get.setParams(params);
	    for (NameId nameid : nameids) {params.setParameter(nameid.getId(), nameid.getName());}
	    HttpClient httpclient = new HttpClient();		   
	    int rs = httpclient.executeMethod(get);
		LOG.debug("rs=" + rs + " " + get.getResponseBodyAsString());
		return get.getResponseBodyAsString();
	}
}

