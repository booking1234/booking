package net.cbtltd.trial.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import net.cbtltd.server.web.Exchangerates;
import net.cbtltd.shared.Currency;

import com.google.gson.Gson;

/**
 * The Class OpenExchangeRates.
 */
public class OpenExchangeRates {

	private static final Gson GSON = new Gson();
	private static Exchangerates rs = null;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Double rate = getRates().get("EUR");
		System.out.println("Rate from USD to EUR " + rate);
		rate = getExchangeRate("EUR", "JPY");
		System.out.println("Rate from EUR to JPY " + rate);
		rate = getExchangeRate("JPY", "EUR");
		System.out.println("Rate from JPY to EUR " + rate);
	}

	/**
	 * Refresh the map of exchange rates if it is null or has expired.
	 *
	 * @return the JSON response transformed to a Java object by Gson.
	 */
	private static final Exchangerates refreshRates() {
		try {          // username isaac@mybookingpal.com psassword: bookingpal 
			if (rs == null || rs.isExpired()) {
				String jsonString = getJson(new URL("http://openexchangerates.org/api/latest.json?app_id=6ecab8a3764a4e32828974e4e0e436e7"));
				rs = GSON.fromJson(jsonString,Exchangerates.class);
			}
		}
		catch (Throwable x) {x.printStackTrace();}
		return rs;
	}

	/**
	 * Gets the map of currency codes to exchange rates.
	 *
	 * @return the map
	 */
	public static final HashMap<String, Double> getRates() {
		return refreshRates().getRates();
	}

	/**
	 * Gets the exchange rate.
	 *
	 * @param fromcurrency the from currency code
	 * @param tocurrency the to currency code
	 * @return the exchange rate
	 */
	public static final Double getExchangeRate(String fromcurrency, String tocurrency) {
		Double torate = refreshRates().getRate(tocurrency);
		if (Currency.Code.USD.name().equalsIgnoreCase(fromcurrency)) {return torate;}
		else {
			Double fromrate = refreshRates().getRate(fromcurrency);
			return (torate == null || fromrate == null || fromrate == 0.0) ? 0.0 : torate / fromrate;
		}
	}

	/**
	 * Makes the connection to the server and gets the JSON string.
	 *
	 * @param url the connection URL.
	 * @return the JSON string.
	 * @throws Throwable the exception thrown by the method.
	 */
	private static final String getJson(URL url) throws Throwable {
		StringBuilder sb = new StringBuilder();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + "URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {sb.append(line);}
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return sb.toString();
	}
}
