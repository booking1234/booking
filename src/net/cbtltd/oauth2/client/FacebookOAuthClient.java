package net.cbtltd.oauth2.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
@Path("/facebookOAuthClient")
public class FacebookOAuthClient {
	public static final Logger LOG = Logger
			.getLogger(FacebookOAuthClient.class.getName());
	
public static void main(String[] args) {
	FacebookOAuthClient facebookOAuthClient=new FacebookOAuthClient();
	facebookOAuthClient.authorize();
}
@GET
@Path("/authorize")
	public void authorize() {
		BookingPalOAuthService bookingPalOAuthService = new BookingPalOAuthService();
		OAuthAccessTokenParams oauthAccessTokenParams = new OAuthAccessTokenParams();
		// http://localhost:8080/Razor/xml/rest/product/143/propertydetail?pos=5e7e3a77b3714ea2&test=true
		oauthAccessTokenParams.setRequestURL("https://graph.facebook.com/oauth/authorize");
		oauthAccessTokenParams.setClientId("211012835683755");
		oauthAccessTokenParams.setRedirectURL("http://localhost:8090/Razor/xml/rest/facebookOAuthClient/redirect");
		oauthAccessTokenParams.setScope("email");
		bookingPalOAuthService.authorize(oauthAccessTokenParams);
	}
	
	@GET
	@Path("/testMethod")
	public String testMethod(){
		System.out.println("Inside test method of  FacebookOAuthClient");
		LOG.info("Inside test method of  FacebookOAuthClient ");
		return "Inside test method of  FacebookOAuthClient";
	}

	@GET
	@Path("/redirect")
	public void redirect(@QueryParam("code") String code) {
		LOG.info("Code from OAuth Authorization server " + code);
		System.out.println("Code from OAuth Authorization server " + code);
		
		BookingPalOAuthService bookingPalOAuthService = new BookingPalOAuthService();
		OAuthAccessTokenParams oauthAccessTokenParams = new OAuthAccessTokenParams();
		oauthAccessTokenParams.setRequestURL("https://graph.facebook.com/oauth/access_token");
		oauthAccessTokenParams.setRedirectURL("http://localhost:8090/Razor/xml/rest/facebookOAuthClient/redirect");
		oauthAccessTokenParams.setCode(code);
		
		oauthAccessTokenParams.setClientId("211012835683755");
		oauthAccessTokenParams.setClientSecret("7201e8c79146e26635fec203b6f84763");
		String oAuthResponse = bookingPalOAuthService
				.fetchAccessToken(oauthAccessTokenParams);
		System.out.println("oAuthResponse "+oAuthResponse);
		LOG.info("oAuthResponse "+oAuthResponse);
		

	}
}
