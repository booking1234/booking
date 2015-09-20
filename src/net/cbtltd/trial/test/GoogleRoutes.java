package net.cbtltd.trial.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.web.Exchangerates;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.api.HasUrls;

import com.google.gson.Gson;

/**
 * The Class OpenExchangeRates.
 */
public class GoogleRoutes {

	private static final Gson GSON = new Gson();
	private static Exchangerates rs = null;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String origin = "Cape Town Waterfront";
		String destination = "Camps Bay";
		System.out.println("Route from " + origin + " to " + destination);
	}

	public static final void getRoute(String origin, String destination) {
		try {
			if (rs == null || rs.isExpired()) {
				
				String url = HasUrls.GOOGLE_DIRECTIONS_SERVICE + "&origin=" + origin + "&destination=" + destination + "&sensor=false";
				String jsonString = getConnection(new URL(url), null);
				System.out.println("getRoute jsonString " + jsonString);
//				rs = GSON.fromJson(jsonString,Exchangerates.class);
//				Log.debug("refreshRates rs " + rs);
//				if (rs == null || rs.isExpired()) {throw new ServiceException(Error.currency_exchange_rate);}
			}
		}
		catch (Throwable x) {MonitorService.log(x);}
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
}
