/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

/**
 * The Class RestClient is for quick RESTful client calls by services.
 * <pre>RestClient.doDelete("/registration/" + userToken);</pre>
 * <pre>RestClient.doGet("/me/profile?ct=" + userToken);</pre>
 */
public class RestClient {
	
	private static final int HTTP_OK = 200;

	/**
	 * Do get.
	 *
	 * @param url the url
	 * @return the string
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the uRI syntax exception
	 */
	public static String doGet(final String url) throws RuntimeException {
		try {
			final HttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams
					.setConnectionTimeout(httpClient.getParams(), 10000);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			return read(instream);
		}
//		catch(HttpException x) {throw new RuntimeException("HttpException: " + x.getMessage());}
		catch(IOException x) {throw new RuntimeException("IOException: " + x.getMessage());}
//		catch(URISyntaxException x) {throw new RuntimeException("URISyntaxException: " + x.getMessage());}
	}

	/**
	 * Do post.
	 *
	 * @param url the url
	 * @param POSTText the pOST text
	 * @return the string
	 * @throws URISyntaxException the uRI syntax exception
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String doPost(final String url, final String POSTText)
			throws URISyntaxException, HttpException, IOException {

		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(POSTText, "UTF-8");
		BasicHeader basicHeader = new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json");
		httpPost.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		entity.setContentType(basicHeader);
		httpPost.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPost);
		InputStream instream = response.getEntity().getContent();
		return read(instream);
	}

	/**
	 * Do put.
	 *
	 * @param url the url
	 * @param PUTText the pUT text
	 * @return true, if successful
	 * @throws URISyntaxException the uRI syntax exception
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean doPut(final String url, final String PUTText)
			throws URISyntaxException, HttpException, IOException {
		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader("Accept", "application/json");
		httpPut.addHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(PUTText, "UTF-8");
		entity.setContentType("application/json");
		httpPut.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPut);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == HTTP_OK ? true : false;
	}

	/**
	 * Do delete.
	 *
	 * @param url the url
	 * @return true, if successful
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the uRI syntax exception
	 */
	public static boolean doDelete(final String url) throws HttpException,
			IOException, URISyntaxException {
		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.addHeader("Accept",
				"text/html, image/jpeg, *; q=.2, */*; q=.2");
		HttpResponse response = httpClient.execute(httpDelete);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == HTTP_OK ? true : false;
	}

	private static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}
}
