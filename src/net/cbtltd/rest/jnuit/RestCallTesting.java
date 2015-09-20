/**
 * @Author Devin Held
 */

package net.cbtltd.rest.jnuit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Error;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.test.mapper.LocationMapper;

public class RestCallTesting {
	private static HashMap<String, HashMap<String, String>> parameters;
	private static HashMap<String, String> environment;
	private static SqlSession sqlSession;
	private static HashMap<String, String> appPath;
	
	private static final String webappName = "razor"; // Used for localhost testing
	private static final String localServerPath = "/xml/json";
	private static final String remoteServerPath = "/xml/services/json";
	private static final String productionStr = "https://www.mybookingpal.com";
	private static final String uatStr = "https://uat.mybookingpal.com";
	
	private static HashMap<String, HashMap<String, String>> readCSVFile()
	{
		ICsvMapReader mapReader = null;
		try {
			final CsvPreference STANDARD_SURROUNDING_SPACES_NEED_QUOTES = new CsvPreference.Builder(
					CsvPreference.STANDARD_PREFERENCE).surroundingSpacesNeedQuotes(true).build();
			
			mapReader = new CsvMapReader(new FileReader
					(new File(RazorConfig.getJUnitRestCallTestingInputFile())),
					STANDARD_SURROUNDING_SPACES_NEED_QUOTES);
			
			final String[] header = mapReader.getHeader(true); // read header from file
			
			Map<String, String> oneRow;
			HashMap<String, HashMap<String, String>> parameterMap = new HashMap<String, HashMap<String, String>>();
			
			while( (oneRow = mapReader.read(header)) != null ) {
				parameterMap.put(oneRow.get("testCaseFunc"), new HashMap<String, String>(oneRow));
			}
			
			mapReader.close();
			
			return parameterMap; 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Input file not found at " + RazorConfig.getJUnitRestCallTestingInputFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("Problem reading input file");
		}
		
		return null;
	}
	
	@BeforeClass
	public static void readParameter()
	{
		parameters = readCSVFile();
		environment = new HashMap<String, String>();
		appPath = new HashMap<String, String>();
		environment.put("production", productionStr);
		environment.put("uat", uatStr);
		appPath.put("production", remoteServerPath);
		appPath.put("uat", remoteServerPath);
		appPath.put("local", webappName + localServerPath);
		sqlSession = RazorServer.openSession();
	}
	
	@AfterClass
	public static void shutdown()
	{
		sqlSession.close();
	}

	@Test
	public void testLocationSearchForAGivenLocation() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testLocationSearchForAGivenLocation");
		
		assertFalse("No data found for test case - testLocationSearchForAGivenLocation",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));
		

		String term = parameter.get("term");
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getlocations/?term=" 
						+ term + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONArray result = new JSONArray(body);
		
		ArrayList<String> locationSet = sqlSession.getMapper(LocationMapper.class).getLocations(term +"%");
		
		for(int i = 0; i < result.length(); i++)
		{
			JSONObject obj = (JSONObject) result.get(i);
			String label = obj.getString("label");
			String location = label.split(",")[0];
			
			assertTrue("Location: " + location + ", not present in database",locationSet.contains(location));
				
//				if(!label.toLowerCase().contains(term.toLowerCase()))
//				{
//					fail("Response: " + label + " does not start with term: " + term);
//				}
		}
	}
	
	@Test
	public void testLocationSearchForASingleLetter() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testLocationSearchForASingleLetter");
		
		assertFalse("No data found for test case - testLocationSearchForASingleLetter",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));
		
		String term = parameter.get("term");
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getlocations/?term=" 
						+ term + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONArray result = new JSONArray(body);
		
		ArrayList<String> locationSet = sqlSession.getMapper(LocationMapper.class).getLocations(term +"%");
		
		for(int i = 0; i < result.length(); i++)
		{
			JSONObject obj = (JSONObject) result.get(i);
			String label = obj.getString("label");
			String location = label.split(",")[0];
			
			assertTrue("Location: " + location + ", not present in database",locationSet.contains(location));
//				if(!label.toLowerCase().startsWith(term.toLowerCase()))
//				{
//					fail("Response: " + label + " does not start with term: " + term);
//				}
		}
		
	}
	
	@Test
	public void testLocationSearchInvalidLocation() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testLocationSearchInvalidLocation");
		
		assertFalse("No data found for test case - testLocationSearchInvalidLocation",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));
		
		String term = parameter.get("term");
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getlocations/?term=" 
						+ term + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONArray result = new JSONArray(body);
		
		assertEquals("Response not empty for search term: " + term, 0, result.length());
	}
	
	@Test
	public void testLocationSearchBlankLocation() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testLocationSearchBlankLocation");
		
		assertFalse("No data found for test case - testLocationSearchBlankLocation",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getlocations/?term=" + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONArray result = new JSONArray(body);
		
		assertTrue("Response empty ", result.length() > 0);
		
		ArrayList<String> locationSet = sqlSession.getMapper(LocationMapper.class).getLocations("%");
		
		for(int i = 0; i < result.length(); i++)
		{
			JSONObject obj = (JSONObject) result.get(i);
			String label = obj.getString("label");
			String location = label.split(",")[0];
			
			assertTrue("Location: " + location + ", not present in database",locationSet.contains(location));
		}
		
	}
	
	@Test
	public void testLocationCheckWithCompleteLocationName() throws IOException, JSONException {
		
		System.out.println("LLLLLLLLLLLLLLLLL");
		HashMap<String, String> parameter = parameters.get("testLocationCheckWithCompleteLocationName");
		
		assertFalse("No data found for test case - testLocationCheckWithCompleteLocationName",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));

		String location = parameter.get("location");
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getinfo?location="
						+ location + "&test=true");
		
		System.out.println("LLLLLLLLLLLLLLLLL " + environment.get(parameter.get("environment")) + "/api/location/getinfo?location="
				+ location + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		boolean error = result.getBoolean("error");
		
		assertFalse("Error response received", error);
		
		JSONObject data = (JSONObject)result.get("data");
		String id = data.getString("ID");
		
		String idFromDataBase = sqlSession.getMapper(LocationMapper.class).getLocationIdbyLocationName(location);
		
		assertEquals("ID: " + id + ", not equals IDfromDatabase: " + idFromDataBase, id, idFromDataBase);
		
	}
	

//	@Test
//	public void testLocationCheckCompleteLocationNameWithVienna() {
//		String expected = "{\"error\":false,\"error_message\":\"\",\"data\":{\"ID\":\"996\"}}";
//		
//		HashMap<String, String> parameter = parameters.get("testLocationSearchCompleteLocationNameEqualsRome");
//		
//		try {
//			URL url = new URL(this.environment + "/api/location/getinfo?location=Vienna");
//			URLConnection con = url.openConnection();
//			InputStream in = con.getInputStream();
//			String encoding = con.getContentEncoding();
//			encoding = encoding == null ? "UTF-8" : encoding;
//			String body = IOUtils.toString(in, encoding);
//			//System.out.println(body);
//			assertEquals(body, expected);
//		}
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@Test
	public void testLocationCheckIncompleteLocation() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testLocationCheckIncompleteLocation");
		
		assertFalse("No data found for test case - testLocationCheckIncompleteLocation",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));

		String location = parameter.get("location");
		
		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getinfo?location="
						+ location + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		boolean error = result.getBoolean("error");
		
		assertTrue("No Error response received", error);
	}
	
	@Test
	public void testLocationCheckBlankLocation() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testLocationCheckBlankLocation");
		
		assertFalse("No data found for test case - testLocationCheckBlankLocation",
				parameter == null);
		assertFalse("Not supported for local", parameter.get("environment").equals("local"));

		URL url = new URL(environment.get(parameter.get("environment")) + "/api/location/getinfo?location=" + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);

		JSONObject result = new JSONObject(body);
		boolean error = result.getBoolean("error");
		
		assertTrue("No Error response received", error);	
	}
	
	@Test
	public void testBuildSearchPageValidParameters() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageValidParameters");
		
		assertFalse("No data found for test case - testBuildSearchPageValidParameters",
				parameter == null);

		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject searchQuotes = (JSONObject) searchResponse.get("search_quotes");
		int quoteCount = searchQuotes.getInt("quotes_count");
		
		assertTrue("Search location does not have any quotes", quoteCount > 0);
		
		JSONArray quotes = searchQuotes.getJSONArray("quote");
		
		for(int i = 0; i < quotes.length(); i++)
		{
			JSONObject quote = (JSONObject)quotes.get(i);
			String quoteCurrency = quote.getString("currency");
			int quoteGuests = quote.getInt("guests");
			JSONArray attributes = quote.getJSONArray("attributes");
			
			assertTrue("Quote currency is " + quoteCurrency + ", expected " + currency,
					quoteCurrency.equals(currency));
			assertTrue("Quote with number of guests lower than requested", quoteGuests >= Integer.parseInt(guests));
			assertTrue("No attributes found for quote", attributes.length() > 0);
		}
	}
	
	@Test
	public void testBuildSearchPageNegativeDates() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageNegativeDates");
		
		assertFalse("No data found for test case - testBuildSearchPageNegativeDates",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.date_range.getMessage());
		
	}
	
	@Test
	public void testBuildSearchPagePastDates() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPagePastDates");
		
		assertFalse("No data found for test case - testBuildSearchPagePastDates",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.date_from.getMessage());
	}
	
	@Test
	public void testBuildSearchPagePastFromDate() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPagePastFromDate");
		
		assertFalse("No data found for test case - testBuildSearchPagePastFromDate",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.date_from.getMessage());
	}
	
	@Test
	public void testBuildSearchPagePastToDate() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPagePastToDate");
		
		assertFalse("No data found for test case - testBuildSearchPagePastToDate",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.date_to.getMessage());
	}
	
	@Test
	public void testBuildSearchPageInvalidPOS() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageInvalidPOS");
		
		assertFalse("No data found for test case - testBuildSearchPageInvalidPOS",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.pos_invalid.getMessage());		
	}
	
	@Test
	public void testBuildSearchPageInvalidLocationID() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageInvalidLocationID");
		
		assertFalse("No data found for test case - testBuildSearchPageInvalidLocationID",
				parameter == null);

		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject searchQuotes = (JSONObject)searchResponse.get("search_quotes");
		int quoteCount = searchQuotes.getInt("quotes_count");
		
		assertEquals("Qoutes found for an invalid location id", 0, quoteCount);
		
	}
	
	@Test
	public void testBuildSearchPageTooManyGuests() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageTooManyGuests");
		
		assertFalse("No data found for test case - testBuildSearchPageTooManyGuests",
				parameter == null);

		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage  + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject searchQuotes = (JSONObject)searchResponse.get("search_quotes");
		int quoteCount = searchQuotes.getInt("quotes_count");
		
		assertEquals("Qoutes found for too many guests", 0, quoteCount);
	}
	
	@Test
	public void testBuildSearchPageNegativeGuests() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageNegativeGuests");
		
		assertFalse("No data found for test case - testBuildSearchPageNegativeGuests",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, "negative guests");
	}
	
	@Test
	public void testBuildSearchPageZeroGuests() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageZeroGuests");
		
		assertFalse("No data found for test case - testBuildSearchPageZeroGuests",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, "zero guests");
	}
	
	@Test
	public void testBuildSearchPageInvalidDateFormat() throws JSONException, IOException {

		HashMap<String, String> parameter = parameters.get("testBuildSearchPageInvalidDateFormat");
		
		assertFalse("No data found for test case - testBuildSearchPageInvalidDateFormat",
				parameter == null);
		
		checkPositiveErrorResponseBuildSearchPage(parameter, Error.date_format.getMessage());
	}
	
	@Test
	public void testBuildSearchPageInvalidCurrency() throws IOException, JSONException {
	
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageInvalidCurrency");
		
		assertFalse("No data found for test case - testBuildSearchPageInvalidCurrency",
				parameter == null);

		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject searchQuotes = (JSONObject) searchResponse.get("search_quotes");
		int quoteCount = searchQuotes.getInt("quotes_count");
		
		assertTrue("Location id does not have any quotes", quoteCount > 0);
		
		JSONArray quotes = searchQuotes.getJSONArray("quote");
		
		for(int i = 0; i < quotes.length(); i++)
		{
			JSONObject quote = (JSONObject)quotes.get(i);
			String quoteCurrency = quote.getString("currency");
			
			assertTrue("Quote currency is " + quoteCurrency + ", expected USD", quoteCurrency.equals("USD"));
		}
	}
	
	@Test
	public void testBuildSearchPageEURCurrency() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildSearchPageEURCurrency");
		
		assertFalse("No data found for test case - testBuildSearchPageEURCurrency",
				parameter == null);

		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage + "&test=true");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject searchQuotes = (JSONObject) searchResponse.get("search_quotes");
		int quoteCount = searchQuotes.getInt("quotes_count");
		
		assertTrue("Location id does not have any quotes", quoteCount > 0);
		
		JSONArray quotes = searchQuotes.getJSONArray("quote");
		
		for(int i = 0; i < quotes.length(); i++)
		{
			JSONObject quote = (JSONObject)quotes.get(i);
			String quoteCurrency = quote.getString("currency");
			
			assertTrue("Quote currency is " + quoteCurrency + ", expected " + currency,
					quoteCurrency.equals(currency));
		}
	}
	
	private void checkPositiveErrorResponseBuildSearchPage(HashMap<String, String> parameter, String message) throws JSONException, IOException
	{
		String locationId = parameter.get("locationId");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String pos = parameter.get("pos");
		String guests = parameter.get("guests");
		String amenity = parameter.get("amenity");
		String currency = parameter.get("currency");
		String page = parameter.get("page");
		String perPage = parameter.get("perPage");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/products/" + locationId + "/" + fromDate + "/" + toDate
				+ "?pos=" + pos + "&guests=" + guests + "&amenity=" + amenity + "&currency=" + currency 
				+ "&page=" + page + "&perpage=" + perPage + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject searchResponse = (JSONObject)result.get("search_response");
		boolean isError = searchResponse.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String resMessage = searchResponse.getString("message");
			assertTrue("Unexpected error message: " + resMessage, resMessage.contains(message));
		}
	}
	
	@Test
	public void testBuildPropertyDetailPageInvalidProductID() throws JSONException, IOException {

		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPageInvalidProductID");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPageInvalidProductID",
				parameter == null);
		
		checkBuildPropertyDetailsPositiveErrorResponse(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testBuildPropertyDetailPagePastDates() throws JSONException, IOException {

		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPagePastDates");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPagePastDates",
				parameter == null);
		
		checkBuildPropertyDetailsPositiveErrorResponse(parameter, Error.date_from.getMessage());
		
	}
	
	@Test
	public void testBuildPropertyDetailPageWithNegativeDateRange() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPageWithNegativeDateRange");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPageWithNegativeDateRange",
				parameter == null);
		
		checkBuildPropertyDetailsPositiveErrorResponse(parameter, Error.date_range.getMessage());
	}
	
	@Test
	public void testBuildPropertyDetailPageWithPOSandUnrelatedPM() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPageWithPOSandUnrelatedPM");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPageWithPOSandUnrelatedPM",
				parameter == null);
		
		checkBuildPropertyDetailsPositiveErrorResponse(parameter, Error.agent_to_pm.getMessage());
	}
	
	@Test
	public void testBuildPropertyDetailPageValidInformation() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPageValidInformation");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPageValidInformation",
				parameter == null);

		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String currency = parameter.get("currency");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/product/" + productId + "/propertydetail?pos=" 
				+ pos + "&currency=" + currency + "&date=" + fromDate + "&toDate=" + toDate + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject propertyResponse = (JSONObject)result.get("property_response");
		boolean isError = propertyResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject property = propertyResponse.getJSONObject("property");
		String responseProductId = property.getString("productid");
		String responseCurrency = property.getString("currency");
		
		assertTrue("Product id in response different form requested", responseProductId.equals(productId));
		assertTrue("Currency in response different from requested", responseCurrency.equals(currency));
	}
	
	@Test
	public void testBuildPropertyDetailInvalidDateFormat() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailInvalidDateFormat");
		
		assertFalse("No data found for test case - testBuildPropertyDetailInvalidDateFormat",
				parameter == null);
		
		checkBuildPropertyDetailsPositiveErrorResponse(parameter, Error.date_format.getMessage());
		
	}
	@Test
	public void testBuildPropertyDetailPageInvalidCurrency() throws IOException, JSONException {
	
		HashMap<String, String> parameter = parameters.get("testBuildPropertyDetailPageInvalidCurrency");
		
		assertFalse("No data found for test case - testBuildPropertyDetailPageInvalidCurrency",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String currency = parameter.get("currency");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/product/" + productId + "/propertydetail?pos=" 
				+ pos + "&currency=" + currency + "&date=" + fromDate + "&toDate=" + toDate + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject propertyResponse = (JSONObject)result.get("property_response");
		boolean isError = propertyResponse.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONObject property = propertyResponse.getJSONObject("property");
		String responseCurrency = property.getString("currency");
		
		assertTrue("Currency has not defaulted to USD", responseCurrency.equals("USD"));
	}
	
	private void checkBuildPropertyDetailsPositiveErrorResponse(HashMap<String, String> parameter, String message) 
			throws JSONException, IOException
	{
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String currency = parameter.get("currency");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/product/" + productId + "/propertydetail?pos=" 
				+ pos + "&currency=" + currency + "&date=" + fromDate + "&toDate=" + toDate +"&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		
		JSONObject result = new JSONObject(body);
		JSONObject propertyResponse = (JSONObject)result.get("property_response");
		boolean isError = propertyResponse.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String resMessage = propertyResponse.getString("message");
			assertTrue("Unexpeted error message: " + resMessage, resMessage.contains(message));
		}
	}
	
	@Test
	public void testPriceInfoPastDates() throws JSONException, IOException {
		HashMap<String, String> parameter = parameters.get("testPriceInfoPastDates");
		
		assertFalse("No data found for test case - testPriceInfoPastDates",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "No message");
		
	}

	@Test
	public void testPriceFutureFromDatePastToDate() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceFutureFromDatePastToDate");
		
		assertFalse("No data found for test case - testPriceFutureFromDatePastToDate",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, Error.date_range.getMessage());
		
	}
	
	@Test
	public void testPriceUnrelatedPOSID() throws JSONException, IOException {
	
		HashMap<String, String> parameter = parameters.get("testPriceUnrelatedPOSID");
		
		assertFalse("No data found for test case - testPriceUnrelatedPOSID",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, Error.agent_to_pm.getMessage());
		
	}
	
	@Test
	public void testPriceInfoValidInformation() throws IOException, JSONException {

		HashMap<String, String> parameter = parameters.get("testPriceInfoValidInformation");
		
		assertFalse("No data found for test case - testPriceInfoValidInformation",
				parameter == null);

		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String adults = parameter.get("adults");
		String child = parameter.get("child");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/quotes?pos=" + pos + "&productid=" + productId
				+ "&fromdate=" + fromDate + "&todate=" + toDate + "&currency=" + currency
				+ "&adults=" + adults + "&chld=" + child + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject quotes = (JSONObject)result.get("quotes");
		boolean isError = quotes.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
	}
	
	@Test
	public void testPriceInfoInvalidCurrency() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoInvalidCurrency");
		
		assertFalse("No data found for test case - testPriceInfoInvalidCurrency",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String adults = parameter.get("adults");
		String child = parameter.get("child");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/quotes?pos=" + pos + "&productid=" + productId
				+ "&fromdate=" + fromDate + "&todate=" + toDate + "&currency=" + currency
				+ "&adults=" + adults + "&chld=" + child + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject quotes = (JSONObject)result.get("quotes");
		boolean isError = quotes.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		String responseCurrency = quotes.getString("currency");
		
		assertTrue("Currency not USD", responseCurrency.equals("USD"));	
	}
	
	@Test
	public void testPriceInfoInvalidProductID() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoInvalidProductID");

		assertFalse("No data found for test case - testPriceInfoInvalidProductID",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testPriceInfoZeroAdults() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoZeroAdults");

		assertFalse("No data found for test case - testPriceInfoZeroAdults",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "no message");
	}
	
	@Test
	public void testPriceInfoNegativeAdults() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoNegativeAdults");

		assertFalse("No data found for test case - testPriceInfoNegativeAdults",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "no message");
	}
	
	@Test(expected=org.junit.Test.None.class)
	public void testPriceInfoTooManyAdults() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoTooManyAdults");

		assertFalse("No data found for test case - testPriceInfoTooManyAdults",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "no message");
	}
	
	@Test
	public void testPriceInfoNegativeChildren() throws JSONException, IOException {

		HashMap<String, String> parameter = parameters.get("testPriceInfoNegativeChildren");
		
		assertFalse("No data found for test case - testPriceInfoNegativeChildren",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "no message");
	}
	
	@Test
	public void testPriceInfoTooManyChildren() throws JSONException, IOException {
		
		HashMap<String, String> parameter = parameters.get("testPriceInfoTooManyChildren");
		
		assertFalse("No data found for test case - testPriceInfoTooManyChildren",
				parameter == null);
		
		checkPositiveErrorResponsePriceInfo(parameter, "no message");
	}
	
	private void checkPositiveErrorResponsePriceInfo(HashMap<String, String> parameter, String message) 
			throws JSONException, IOException
	{
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String adults = parameter.get("adults");
		String child = parameter.get("child");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/quotes?pos=" + pos + "&productid=" + productId
				+ "&fromdate=" + fromDate + "&todate=" + toDate + "&currency=" + currency
				+ "&adults=" + adults + "&chld=" + child + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject priceResponse = (JSONObject)result.get("quotes");
		boolean isError = priceResponse.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String respMessage = priceResponse.getString("message");
			assertTrue("Unexpected error message: " + respMessage, respMessage.contains(message));
		}
	}
	
	@Test
	public void testAvailabilityCalendarValidInformation() 
			throws IOException, JSONException, NumberFormatException 
	{
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarValidInformation");
		
		assertFalse("No data found for test case - testAvailabilityCalendarValidInformation",
				parameter == null);

		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String date = parameter.get("date");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/calendar?pos=" + pos
				+ "&productid=" + productId + "&date=" + date + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject calendar = (JSONObject)result.get("calendar");
		boolean isError = calendar.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		JSONArray items = calendar.getJSONArray("items");
		String[] date1 = date.split("[\\-]");
	    
		JSONObject item = items.getJSONObject(0);
		String[] date2 = item.getString("date").split("[\\-]");
		
		/* check if both dates are equal*/
		assertTrue("Availability calendar doesn't start from " + date,
				compareDates(date1, date2) == 0);
		
		for(int i = 1; i < items.length(); i++)
		{
			item = items.getJSONObject(i);
			date1 = item.getString("date").split("[\\-]");
			
			/* check if the dates are in increasing order*/
			assertTrue("Availability calendar dates are not in increasing order " + item.getString("date"), 
					compareDates(date1, date2) > 0);
			
			date2 = date1;
		}
		
	}
	
	private int compareDates(String[] date1, String[] date2) throws NumberFormatException
	{
		if(Integer.parseInt(date1[0]) != Integer.parseInt(date2[0])) //year
			return Integer.parseInt(date1[0]) - Integer.parseInt(date2[0]);
		else if (Integer.parseInt(date1[1]) != Integer.parseInt(date2[1])) //month
			return Integer.parseInt(date1[1]) - Integer.parseInt(date2[1]);
		
		return Integer.parseInt(date1[2]) - Integer.parseInt(date2[2]); //day
	}
	
	@Test
	public void testAvailabilityCalendarPastDate() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarPastDate");

		assertFalse("No data found for test case - testAvailabilityCalendarPastDate",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityCalendar(parameter, Error.date_from.getMessage());
	}
	
	@Test
	public void testAvailabilityCalendarUnrelatedPOSID() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarUnrelatedPOSID");

		assertFalse("No data found for test case - testAvailabilityCalendarUnrelatedPOSID",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityCalendar(parameter, Error.agent_to_pm.getMessage());
	}
	
	@Test
	public void testAvailabilityCalendarVeryFarAwayDate() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarVeryFarAwayDate");

		assertFalse("No data found for test case - testAvailabilityCalendarVeryFarAwayDate",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String date = parameter.get("date");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/calendar?pos=" + pos
				+ "&productid=" + productId + "&date=" + date + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject calendar = (JSONObject)result.get("calendar");
		boolean isError = calendar.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
	}
	
	@Test
	public void testAvailabilityCalendarInvalidProductID() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarInvalidProductID");

		assertFalse("No data found for test case - testAvailabilityCalendarInvalidProductID",
				parameter == null);
		/*The url DOES return an error, but it does not return an error message, 
		 * therefore the test fails.
		 * format is not the same as all the other error messages.
		 */
		checkPositiveErrorResponseAvailabilityCalendar(parameter, Error.product_id.getMessage());
		
	}
	
	@Test
	public void testAvailabilityCalendarInvalidDateFormat() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarInvalidDateFormat");

		assertFalse("No data found for test case - testAvailabilityCalendarInvalidDateFormat",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityCalendar(parameter, Error.date_format.getMessage());
	}
	
	@Test
	public void testAvailabilityCalendarInvalidPOS() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testAvailabilityCalendarInvalidPOS");

		assertFalse("No data found for test case - testAvailabilityCalendarInvalidPOS",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityCalendar(parameter, Error.pos_invalid.getMessage());
		
	}
	
	private void checkPositiveErrorResponseAvailabilityCalendar(HashMap<String, String> parameter, String message) 
			throws IOException, JSONException
	{
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String date = parameter.get("date");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env) 
				+ "/reservation/calendar?pos=" + pos
				+ "&productid=" + productId + "&date=" + date + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject calendar = (JSONObject)result.get("calendar");
		boolean isError = calendar.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String respMessage = calendar.getString("message");
			assertTrue("Unexpected error message: " + respMessage, respMessage.contains(message));
		}
	}
	
	@Test
	public void testAvailabilityPeriodVailidInputs() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testAvailabilityPeriodVailidInputs");

		assertFalse("No data found for test case - testAvailabilityPeriodVailidInputs",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/available_calendar?pos=" + pos
				+ "&productid=" + productId + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject availCalendar = (JSONObject)result.get("availability_calendar");
		boolean isError = availCalendar.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		String responseProductId = availCalendar.getString("productid");
		
		assertTrue("Product ID: " + responseProductId + ", different from requested",
				responseProductId.equals(productId));
	}
	
	@Test
	public void testAvailabilityPeriodInvalidProductId() throws IOException, JSONException {
		
		HashMap<String, String> parameter = parameters.get("testAvailabilityPeriodInvalidProductId");

		assertFalse("No data found for test case - testAvailabilityPeriodInvalidProductId",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityPeriod(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testAvailabilityPeriodInvalidPOS() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testAvailabilityPeriodInvalidPOS");

		assertFalse("No data found for test case - testAvailabilityPeriodInvalidPOS",
				parameter == null);
		
		checkPositiveErrorResponseAvailabilityPeriod(parameter, Error.pos_invalid.getMessage());
	}
	
	private void checkPositiveErrorResponseAvailabilityPeriod(HashMap<String, String> parameter, String message) 
			throws IOException, JSONException
	{
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/available_calendar?pos=" + pos
				+ "&productid=" + productId + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject availCalendar = (JSONObject)result.get("availability_calendar");
		boolean isError = availCalendar.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String respMessage = availCalendar.getString("message");
			assertTrue("Unexpected error message: " + respMessage, respMessage.contains(message));
		}
	}
	
	@Test
	public void testWeeklyPricesVailidInputs() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesVailidInputs");

		assertFalse("No data found for test case - testWeeklyPricesVailidInputs",
				parameter == null);

		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/prices?pos=" + pos
				+ "&productid=" + productId + "&fromdate=" + fromDate + "&todate="
				+ toDate + "&currency=" + currency + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject ranges = (JSONObject)result.get("ranges");
		boolean isError = ranges.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		String respCurrency = ranges.getString("currency");
		String respProductId = ranges.getString("propertyId");
		
		assertTrue("Currency: " + respCurrency + ", different from requested",
				respCurrency.equals(currency));
		assertTrue("PropertyId: " + respProductId + ", different from requested",
				respProductId.equals(productId));
	}
	
	@Test
	public void testWeeklyPricesInvalidPOS() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesInvalidPOS");

		assertFalse("No data found for test case - testWeeklyPricesInvalidPOS",
				parameter == null);
		
		checkPositiveErrorResponseWeeklyPrices(parameter, Error.pos_invalid.getMessage());
	}
	
	@Test
	public void testWeeklyPricesInvalidProductId() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesInvalidProductId");

		assertFalse("No data found for test case - testWeeklyPricesInvalidProductId",
				parameter == null);
		
		checkPositiveErrorResponseWeeklyPrices(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testWeeklyPricesInvalidDateFormat() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesInvalidDateFormat");

		assertFalse("No data found for test case - testWeeklyPricesInvalidDateFormat",
				parameter == null);
		
		checkPositiveErrorResponseWeeklyPrices(parameter, Error.date_format.getMessage());
	}
	
	@Test
	public void testWeeklyPricesInvalidDateRange() throws IOException, JSONException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesInvalidDateRange");

		assertFalse("No data found for test case - testWeeklyPricesInvalidDateRange",
				parameter == null);
		
		checkPositiveErrorResponseWeeklyPrices(parameter, Error.date_range.getMessage());
	}
	
	@Test
	public void testWeeklyPricesInvalidCurrency() throws JSONException, IOException {
		HashMap<String, String> parameter = parameters.get("testWeeklyPricesInvalidCurrency");

		assertFalse("No data found for test case - testWeeklyPricesInvalidCurrency",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/prices?pos=" + pos
				+ "&productid=" + productId + "&fromdate=" + fromDate + "&todate="
				+ toDate + "&currency=" + currency + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject ranges = (JSONObject)result.get("ranges");
		boolean isError = ranges.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		String respCurrency = ranges.getString("currency");
		
		assertTrue("Currency: " + respCurrency + ", expected USD", respCurrency.equals("USD"));
	}
	
	private void checkPositiveErrorResponseWeeklyPrices(HashMap<String, String> parameter, String message)
			throws IOException, JSONException
	{
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String currency = parameter.get("currency");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/prices?pos=" + pos
				+ "&productid=" + productId + "&fromdate=" + fromDate + "&todate="
				+ toDate + "&currency=" + currency + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject ranges = (JSONObject)result.get("ranges");
		boolean isError = ranges.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String respMessage = ranges.getString("message");
			assertTrue("Unexpected error message: " + respMessage, respMessage.contains(message));
		}
	}
	
	@Test
	public void testPropertyInquiryVaildInfo() throws JSONException, IOException {
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryVaildInfo");

		assertFalse("No data found for test case - testPropertyInquiryVaildInfo",
				parameter == null);
		
		String productId = parameter.get("productId");
		String pos = parameter.get("pos");
		String fromDate = parameter.get("fromDate");
		String toDate = parameter.get("toDate");
		String notes = parameter.get("notes");
		String adult = parameter.get("adults");
		String child = parameter.get("child");
		String email = parameter.get("email");
		String familyName = parameter.get("familyName");
		String firstName = parameter.get("firstName");
		String telNumber = parameter.get("telnumber");
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/inquiry?pos=" + pos
				+ "&productid=" + productId + "&fromdate=" + fromDate + "&todate="
				+ toDate + "&notes=" + notes + "&adult=" + adult + "&child="
				+ child + "&emailaddress=" + email + "&familyname=" + familyName
				+ "&firstname=" + firstName + "&telnumber=" + telNumber + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject resvResp = (JSONObject)result.get("reservation_response");
		boolean isError = resvResp.getBoolean("is_error");
		
		assertFalse("Error response received", isError);
		
		
	//	assertTrue();
	}
	
	@Test
	public void testPropertyInquiryWithInvalidPOS() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidPOS");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidPOS",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.pos_invalid.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentPOS() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentPOS");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentPOS",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.pos_absent.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithInvalidProductId() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidProductId");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidProductId",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentProductId() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentProductId");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentProductId",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.product_id.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithInvalidFromDateFormat() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidFromDateFormat");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidFromDateFormat",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_format.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithInvalidToDateFormat() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidToDateFormat");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidToDateFormat",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_format.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentFromDate() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentFromDate");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentFromDate",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_from.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentToDate() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentToDate");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentToDate",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_to.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithPastDates() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentToDate");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentToDate",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_range.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithNegativeDateRange() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithNegativeDateRange");

		assertFalse("No data found for test case - testPropertyInquiryWithNegativeDateRange",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.date_range.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentNotes() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentNotes");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentNotes",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithZeroPersons() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithZeroPersons");

		assertFalse("No data found for test case - testPropertyInquiryWithZeroPersons",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithNegativeAdults() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithNegativeAdults");

		assertFalse("No data found for test case - testPropertyInquiryWithNegativeAdults",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithNegativeChildren() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithNegativeChildren");

		assertFalse("No data found for test case - testPropertyInquiryWithNegativeChildren",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithTooManyAdults() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithTooManyAdults");

		assertFalse("No data found for test case - testPropertyInquiryWithTooManyAdults",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithTooManyChildren() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithTooManyChildren");

		assertFalse("No data found for test case - testPropertyInquiryWithTooManyChildren",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithAbsentAdult() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentAdult");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentAdult",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithAbsentChild() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentChild");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentChild",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithInvalidEmail() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidEmail");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidEmail",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, Error.party_emailaddress.getMessage());
	}
	
	@Test
	public void testPropertyInquiryWithAbsentEmail() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentEmail");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentEmail",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithAbsentFamilyName() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentFamilyName");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentFamilyName",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithAbsentFirstName() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentFirstName");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentFirstName",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithAbsentTelNumber() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithAbsentTelNumber");

		assertFalse("No data found for test case - testPropertyInquiryWithAbsentTelNumber",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	@Test
	public void testPropertyInquiryWithInvalidTelNumber() throws JSONException, IOException
	{
		HashMap<String, String> parameter = parameters.get("testPropertyInquiryWithInvalidTelNumber");

		assertFalse("No data found for test case - testPropertyInquiryWithInvalidTelNumber",
				parameter == null);
		
		checkPositiveErrorResponsePropertyInquiry(parameter, "No message");
	}
	
	private void checkPositiveErrorResponsePropertyInquiry(HashMap<String, String> parameter, String message)
			throws IOException, JSONException
	{
		String productId = parameter.get("productId") != null
				? ("&productid=" + parameter.get("productId")) : "";
		String pos = parameter.get("pos") != null 
				? ("pos=" + parameter.get("pos")) : "";
		String fromDate = parameter.get("fromDate") != null
				? ("&fromdate=" + parameter.get("fromDate")) : "";
		String toDate = parameter.get("toDate") != null
				? ("&todate=" + parameter.get("toDate")) : "";
		String notes = (parameter.get("notes") != null) 
				? ("&notes=" + parameter.get("notes")) : "";
		String adult = parameter.get("adult") != null
				? ("&adult=" + parameter.get("adult")) : "";
		String child = parameter.get("child") != null 
				? ("&child=" + parameter.get("child")) : "";
		String email = parameter.get("email") != null
				? ("&emailaddress=" + parameter.get("email")) : "";
		String familyName = parameter.get("familyName") != null
				? ("&familyname=" + parameter.get("familyName")) : "";
		String firstName = parameter.get("firstName") != null
				? ("&firstname=" + parameter.get("firstName")) : "";
		String telNumber = parameter.get("telnumber") != null 
				? ("&telnumber=" + parameter.get("telnumber")) : "";
		String env = parameter.get("environment");
		
		URL url = new URL(environment.get(env) + appPath.get(env)
				+ "/reservation/inquiry?" + pos + productId + fromDate + toDate + notes
				+ adult + child + email + familyName + firstName + telNumber + "&test=true");
		
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
	
		JSONObject result = new JSONObject(body);
		JSONObject resvResp = (JSONObject)result.get("reservation_response");
		boolean isError = resvResp.getBoolean("is_error");
		
		assertTrue("No Error response received", isError);
		
		if(message != null)
		{
			String respMessage = resvResp.getString("message");
			assertTrue("Unexpected error message: " + respMessage, respMessage.contains(message));
		}
	}
}
