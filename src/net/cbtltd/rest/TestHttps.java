package net.cbtltd.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import sun.misc.BASE64Encoder;

/**
 * @see http://vmustafayev4en.blogspot.com/2012/05/avoiding-javaxnetsslsslhandshakeexcepti.html
 * 
 * Usage urlConn.setHostnameVerifier(hv); 
 */
public class TestHttps {  
    public static void main(String args[]) throws Exception {  
  
  
        // Create a trust manager that does not validate certificate chains  
        TrustManager[] trustAllCerts = new TrustManager[] {  
                new X509TrustManager() {  
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                        return null;  
                    }  
  
                    public void checkClientTrusted(  
                            java.security.cert.X509Certificate[] certs, String authType) {  
                    }  
  
                    public void checkServerTrusted(  
                            java.security.cert.X509Certificate[] certs, String authType) {  
                    }  
                }  
        };  
  // Install the all-trusting trust manager  
        try {  
            SSLContext sc = SSLContext.getInstance("SSL");  
            sc.init(null, trustAllCerts, new java.security.SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
        } catch (Exception e) {  
            System.out.println("Error" + e);  
        }  
  // Now you can access an https URL without having the certificate in the truststore  
        try {  
              HostnameVerifier hv = new HostnameVerifier() {  
                public boolean verify(String urlHostName, SSLSession session) {  
                    System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());  
                    return true;  
                }
            };  
  
//            String datam = "param=myparam";  
            URL url = new URL("https://www.summitcove.com/xml/service/properties/"); //https://myurlgoeshere/blabla.aspx"); 
            
            URLConnection connection = url.openConnection();  
            HttpsURLConnection urlConn = (HttpsURLConnection) connection;  
            urlConn.setHostnameVerifier(hv);  
            connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
			String userpassword = "razorcloud:-5z%8Apu6fA8gM5s";
			String encodedAuthorization = enc.encode(userpassword.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
//            wr.write(datam);  
//            wr.flush();  
  
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
            StringBuilder sb = new StringBuilder();  
            String inputLine;  
            while ((inputLine = in.readLine()) != null) {  
                sb.append(inputLine);  
            }  
            in.close();  
            String res = sb.toString();  
            System.out.println(res);  
  
        } catch (MalformedURLException e) {  
            System.out.println("Error in SLL Connetion" + e);  
        }  
    }  
}  