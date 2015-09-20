package net.cbtltd.oauth2.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;

@Path("/bookingSyncOAuthClient")
// This is a sample implementation which explains how BookingPalOAuthService can
// be used
public class BookingSyncOAuthClient {
	public static final Logger LOG = Logger
			.getLogger(BookingSyncOAuthClient.class.getName());
	
	public static void main(String[] args) {
		BookingSyncOAuthClient bookingSyncOAuthClient=new BookingSyncOAuthClient();
		//bookingSyncOAuthClient.authorize();
		bookingSyncOAuthClient.refreshToken("7f9083d5fed2d8c593450533214d6befba25918c569a0288efe62195ff31c041");
	}

	@GET
	@Path("/authorize")
	public void authorize() {
		BookingPalOAuthService bookingPalOAuthService = new BookingPalOAuthService();
		OAuthAccessTokenParams oauthAccessTokenParams = new OAuthAccessTokenParams();
		// http://localhost:8080/Razor/xml/rest/product/143/propertydetail?pos=5e7e3a77b3714ea2&test=true
		oauthAccessTokenParams.setRequestURL("https://www.bookingsync.com/oauth/authorize");
		oauthAccessTokenParams.setClientId("0d0203d7cb12eca9ccff48f99c71963e72e9ceaa239dace2d3bb14cdbd388685");
		oauthAccessTokenParams.setRedirectURL("http://localhost:8090/Razor/xml/rest/bookingSyncOAuthClient/redirect");
				
		bookingPalOAuthService.authorize(oauthAccessTokenParams);
	}

	@GET
	@Path("/redirect")
	public void redirect(@QueryParam("code") String code) {
		LOG.info("Code from OAuth Authorization server " + code);
		
		BookingPalOAuthService bookingPalOAuthService = new BookingPalOAuthService();
		OAuthAccessTokenParams oauthAccessTokenParams = new OAuthAccessTokenParams();
		oauthAccessTokenParams.setRequestURL("https://www.bookingsync.com/oauth/token");
		oauthAccessTokenParams.setRedirectURL("http://localhost:8090/Razor/xml/rest/bookingSyncOAuthClient/redirect");
		oauthAccessTokenParams.setCode(code);
		
		oauthAccessTokenParams.setClientId("0d0203d7cb12eca9ccff48f99c71963e72e9ceaa239dace2d3bb14cdbd388685");
		oauthAccessTokenParams.setClientSecret("224b3ecb6b4392ae1401e7625733d9784a2bbdaec6ad89501eafc657c06f8938");
		String oAuthResponse = bookingPalOAuthService
				.fetchAccessToken(oauthAccessTokenParams);
		System.out.println("oAuthResponse "+oAuthResponse);
		LOG.info("oAuthResponse "+oAuthResponse);

	}
	
	@GET
	@Path("/refreshToken")
	public void refreshToken(@QueryParam("refreshToken") String refreshToken) {
		LOG.info("refreshToken " + refreshToken);
		
		BookingPalOAuthService bookingPalOAuthService = new BookingPalOAuthService();
		OAuthAccessTokenParams oauthAccessTokenParams = new OAuthAccessTokenParams();
		oauthAccessTokenParams.setRequestURL("https://www.bookingsync.com/oauth/token");
		oauthAccessTokenParams.setRedirectURL("http://localhost:8090/Razor/xml/rest/bookingSyncOAuthClient/redirect");
		oauthAccessTokenParams.setRefreshToken(refreshToken);
		
		oauthAccessTokenParams.setClientId("0d0203d7cb12eca9ccff48f99c71963e72e9ceaa239dace2d3bb14cdbd388685");
		oauthAccessTokenParams.setClientSecret("224b3ecb6b4392ae1401e7625733d9784a2bbdaec6ad89501eafc657c06f8938");
		String oAuthResponse = bookingPalOAuthService
				.refreshToken(oauthAccessTokenParams);
		System.out.println("oAuthResponse "+oAuthResponse);
		LOG.info("oAuthResponse "+oAuthResponse);

	}
}
