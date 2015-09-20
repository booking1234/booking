package net.cbtltd.rest.expedia.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ExpediaUtils {
	public static final String EXPEDIA_AR_URL = "bp.expedia.ar.url";
	public static final String EXPEDIA_BR_URL = "bp.expedia.br.url";
	public static final String EXPEDIA_BC_URL = "bp.expedia.bc.url";
	public static final String EXPEDIA_PARR_URL = "bp.expedia.parr.url";
	public static final String EXPEDIA_USERNAME = "bp.expedia.username";
	public static final String EXPEDIA_PWD = "bp.expedia.pwd";
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private static final Logger LOG = Logger
			.getLogger(ExpediaUtils.class.getName());
	
	
	
	public static String callExpediaAPI(String requestXML,String requestURL,Boolean needStatusCodeInResponse) throws Exception {
		requestXML=requestXML.replace(":ns2", "");
		DefaultHttpClient client = new DefaultHttpClient();
		client=(DefaultHttpClient) WebClientDevWrapper.wrapClient(client);
		LOG.info("requestURL "+requestURL);
		LOG.info("requestXML "+requestXML);
		HttpPost post = new HttpPost(requestURL);

		StringEntity entity = new StringEntity(requestXML, "UTF-8");
		
		entity.setContentType("text/xml;charset=UTF-8");
		post.setHeader("Content-Type", "text/xml;charset=UTF-8");
		post.setEntity(entity);
		
		
		
		int statusCode=0;
		HttpResponse response = client.execute(post);
		statusCode = response.getStatusLine().getStatusCode();

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOG.info("Expedia Response Status Code : " + statusCode);
		if(needStatusCodeInResponse){
			return statusCode + ":" + result.toString();	
		}else{
			return result.toString();
		}
		
		

	}


}
