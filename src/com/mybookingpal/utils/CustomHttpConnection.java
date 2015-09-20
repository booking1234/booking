package com.mybookingpal.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

public class CustomHttpConnection {
	
	private static final Logger LOG = Logger.getLogger(HttpsURLConnection.class);

	public final String PUT = "PUT";
	public final String GET = "GET";
	public final String POST = "POST";
	public final String HEAD = "HEAD";
	public final String TRACE = "TRACE";
	public final String DELETE = "DELETE";
	public final String OPTIONS = "OPTIONS";
	
	public String createGetRequest(String apiEndPoint, String basicEncodedAuthorization, String contentType, String requestPayload) throws Throwable{
		return createRequest(GET, apiEndPoint, basicEncodedAuthorization, contentType, requestPayload);
	}
	
	public String createPostRequest(String apiEndPoint, String basicEncodedAuthorization, String contentType, String requestPayload) throws Throwable{
		return createRequest(POST, apiEndPoint, basicEncodedAuthorization, contentType, requestPayload);
	}
	
	private String createRequest(String method, String apiEndPoint, String basicEncodedAuthorization, String contentType, String requestPayload) throws Throwable {
		String xmlString = "";
		HttpURLConnection connection = null;
		try {
			LOG.debug("Connecting to API... :" + apiEndPoint);
			URL url = new URL(apiEndPoint);
			if(apiEndPoint.contains("https")) {
				SSLSocketFactory preferredCipherSuiteSSLSocketFactory = PreferredCipherSuiteSSLSocketFactory.getInstance(HttpsURLConnection.getDefaultSSLSocketFactory());
				HttpsURLConnection.setDefaultSSLSocketFactory(preferredCipherSuiteSSLSocketFactory);
			}
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", contentType);
			connection.setRequestProperty("Accept", contentType); 
			connection.setRequestProperty("Authorization", "Basic " + basicEncodedAuthorization);

			connection.connect();
			byte[] outputBytes = requestPayload.getBytes("UTF-8");
				
			OutputStream os = connection.getOutputStream();
			os.write(outputBytes);
			os.flush();

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {
				xmlString += line;
			}
		} catch (Throwable x) {
			LOG.error((x.getMessage()));
			x.printStackTrace();
			throw new RuntimeException(x.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return xmlString;
	}
	

}
