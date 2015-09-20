package net.cbtltd.shared.finance.gateway.dibs.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DibsServerProcessor {
	
//	protected static final String MERCHANT_ID = "4221772"; //TEST_MERCHANT_ID = "90194061"
	
	protected static Map<String, String> process(HttpPost post, List<NameValuePair> nameValuePairs) {
    	Map<String, String> responseMap = new HashMap<String, String>();
        try {
    		HttpClient client = new DefaultHttpClient();
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            
            String responseText = EntityUtils.toString(response.getEntity());
            StringTokenizer tokenizer = new StringTokenizer(responseText, "&");
            while(tokenizer.hasMoreTokens()) {
            	String token = tokenizer.nextToken();
            	String[] keyValue = token.split("=");
            	responseMap.put(keyValue[0], keyValue[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return responseMap;
	}
}