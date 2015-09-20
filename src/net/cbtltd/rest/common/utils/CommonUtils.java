package net.cbtltd.rest.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

import com.allen_sauer.gwt.log.client.Log;

public class CommonUtils {
	public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static Map<Integer, String> dayOftheWeekMap = new HashMap<Integer, String>();
	public static Map<Integer, String> monthOftheYearMap = new HashMap<Integer, String>();
	protected static Map<String, String> attributes = null;

	/**
	 * @return the attributes
	 */
	public static final Map<String, String> getAttributes() {
		return attributes;
	}

	static {
		dayOftheWeekMap.put(1, "Monday");
		dayOftheWeekMap.put(2, "Tuesday");
		dayOftheWeekMap.put(3, "Wednesday");
		dayOftheWeekMap.put(4, "Thursday");
		dayOftheWeekMap.put(5, "Friday");
		dayOftheWeekMap.put(6, "Saturday");
		dayOftheWeekMap.put(7, "Sunday");

		monthOftheYearMap.put(1, "January");
		monthOftheYearMap.put(2, "Febraury");
		monthOftheYearMap.put(3, "March");
		monthOftheYearMap.put(4, "April");
		monthOftheYearMap.put(5, "May");
		monthOftheYearMap.put(6, "June");
		monthOftheYearMap.put(7, "July");
		monthOftheYearMap.put(8, "August");
		monthOftheYearMap.put(9, "September");
		monthOftheYearMap.put(10, "October");
		monthOftheYearMap.put(11, "November");
		monthOftheYearMap.put(12, "December");

		attributes = getAttributesFromDB();
	}

	public static HttpURLConnection getConnection(String stringURL,
			String username, String pwd) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(stringURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			// connection.setRequestProperty("Content-Type",
			// "application/html");
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
			String userpassword = username + ":" + pwd;
			String encodedAuthorization = enc.encode(userpassword.getBytes());
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthorization);

			// connection.setRequestProperty("Accept", "application/html");

			connection.connect();

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:"
						+ connection.getResponseCode() + " URL " + url);
			}

		} catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(x.getMessage());

		}
		return connection;
	}

	public static String getDayOfWeek(Date startdate) {
		String checkinday = Price.Rule.AnyCheckIn.name();
		switch (new DateTime(startdate).getDayOfWeek()) {
		case 1:
			checkinday = Price.Rule.MonCheckIn.name();
			break;
		case 2:
			checkinday = Price.Rule.TueCheckIn.name();
			break;
		case 3:
			checkinday = Price.Rule.WedCheckIn.name();
			break;
		case 4:
			checkinday = Price.Rule.ThuCheckIn.name();
			break;
		case 5:
			checkinday = Price.Rule.FriCheckIn.name();
			break;
		case 6:
			checkinday = Price.Rule.SatCheckIn.name();
			break;
		case 7:
			checkinday = Price.Rule.SunCheckIn.name();
			break;
		default:
			// LOG.error("error in parsing DayOfWeek: " + startdate);
		}

		return checkinday;
	}

	public static long daysBetween(Date startDate, Date endDate) {
		Calendar day1 = Calendar.getInstance();
		day1.setTime(startDate);

		Calendar day2 = Calendar.getInstance();
		day2.setTime(endDate);
		return daysBetween(day1, day2);
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		// assert: startDate must be before endDate
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static List<String> getTokenizedString(String str) {
		List<String> listTokenizedStr = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(str, "/");
		while (tokenizer.hasMoreElements()) {
			listTokenizedStr.add((String) tokenizer.nextElement());
		}
		return listTokenizedStr;
	}

	public static XMLGregorianCalendar getXMLDateFormat(String date)
			throws Exception {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(SDF_YYYY_MM_DD.parse(date));
		XMLGregorianCalendar xGCal = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gcal);
		return xGCal;

	}

	public static XMLGregorianCalendar getXMLCurrentDate() throws Exception {
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xGCal = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gcal);
		return xGCal;
	}
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(InputStream stream, Class<T> clazz)
			 {
		T obj;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			obj = (T) jaxbUnmarshaller.unmarshal(stream);
		} catch (JAXBException e) {
			Log.error("Fatal Exception during unmarshalling file ", e);
			return null;
		} finally{}
		return obj;
	}
	public static <T> T unmarshall(File file,Class<T> clazz) {
		T obj;
		try {
			obj = unmarshall( new FileInputStream(file), clazz);
		} catch (FileNotFoundException e) {
			Log.error("Fatal Exception during unmarshalling file "
							+ file.getName(), e);
			return null;
		} finally{} 
		return obj;
	}
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(URL url,Class<T> clazz) {
		T obj;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			obj = (T) jaxbUnmarshaller.unmarshal(url);
		} catch (JAXBException e) {
			Log.error("Fatal Exception during unmarshalling file ", e);
			return null;
		} finally{}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T> T unmarshallFile(T obj, Class<T> clazz, InputStream stream)
			throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			obj = (T) jaxbUnmarshaller.unmarshal(stream);
		} catch (Exception e) {
			throw e;
		}
		return obj;
	}
	@Deprecated
	public static <T> T unmarshallFile(T obj, Class<T> clazz, File file) {
		try {
			obj = unmarshallFile(obj, clazz, new FileInputStream(file));
		} catch (Exception e) {
			Log.error(
					"Fatal Exception during unmarshalling file "
							+ file.getName(), e);
		}
		return obj;
	}

	public static String removeSpecialCharacters(String input) {
		// this will remove space, -, +, ., ^, :, and , from the given string
		return input.replaceAll("[ \\-\\+\\.\\^:,]", "");
	}

	private static Map<String, String> getAttributesFromDB() {
		Map<String, String> attributes = new HashMap<String, String>();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			List<NameId> attributeNames = sqlSession.getMapper(
					AttributeMapper.class).list();
			for (NameId nameId : attributeNames) {
				attributes.put(nameId.getName().toLowerCase(), nameId.getId());
			}
			sqlSession.commit();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return attributes;
	}

	public static String getAttributeID(String attribute) {
		if (attributes.containsKey(attribute))
			return attributes.get(attribute);
		return null;
	}
	
	public static String GenerateDigest(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes("UTF-8")); // can use "UTF-16" if needed
		byte[] hashValue = md.digest();
		BigInteger bigInt = new BigInteger(1, hashValue);
        return bigInt.toString(16);
	}
	
	public static StringReader readDataFromUrlGet(String localURL,
			String credentials) {
		HttpURLConnection connection = null;
		StringReader reader = null;
		try {
			URL url = new URL(localURL);
			connection = (HttpURLConnection) url.openConnection();
			// add reuqest header
			connection.setRequestMethod("GET");

			String encodedCredentials = new sun.misc.BASE64Encoder().encode (credentials.getBytes("UTF-8"));
			connection.setRequestProperty("Authorization", encodedCredentials);
			connection.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			//connection.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// StringBuffer result = new StringBuffer();
			StringWriter writer = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				writer.write(line);
			}

			reader = new StringReader(writer.toString());

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
		return reader;
	}

	public static StringReader readDataFromUrlPost(String localURL,
			String urlParameters, String encodedCredentials) {
		HttpURLConnection connection = null;
		StringReader reader = null;
		try {
			URL url = new URL(localURL);
			connection = (HttpURLConnection) url.openConnection();

			// add reuqest header
			connection.setRequestMethod("POST");

			// Send post request
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", encodedCredentials);
			connection.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			int responseCode = connection.getResponseCode();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// StringBuffer result = new StringBuffer();
			StringWriter writer = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				writer.write(line);
			}
			reader = new StringReader(writer.toString());

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
		return reader;
	}

	// convert java object to JSON format,
	// and returned as JSON formatted string
	public static String formJsonString(Set<KeyValuePair> input)
			throws JSONException {
		JSONObject jo = new JSONObject();
		for (KeyValuePair keyValue : input) {
			jo.put(keyValue.getKey(), keyValue.getValueAsString());
		}
		return jo.toString();
	}
	
	public static String encodeTo88591(String value) {
		final CharsetEncoder encoder = Charset.forName("ISO-8859-1").newEncoder();
		if (iso88591Encodable(new String[]{value})) {
			ByteBuffer buffer = null;
			try {
				buffer = encoder.encode(CharBuffer.wrap(value));
			} catch (CharacterCodingException e) {
				e.printStackTrace();
			}
			return new String(buffer.array());
		} else {
			return value;
		}
	}
	
	public static boolean iso88591Encodable(String[] values) {
		final CharsetEncoder encoder = Charset.forName("ISO-8859-1").newEncoder();
		for(String value : values) {
			if(!encoder.canEncode(value)) {
				return false;
			}
		}
		return true;
	}
}
