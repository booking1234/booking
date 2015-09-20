package net.cbtltd.rest.odalys.priceavailabilitycache;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.flipkey.inquiry.Inquiry;
import net.cbtltd.server.PartnerService;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import sun.misc.BASE64Encoder;

import com.mybookingpal.config.RazorConfig;

public class OdalysUtils extends CommonUtils{
	
	

	public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger LOG = Logger.getLogger(OdalysUtils.class.getName());

	// TO DO - put the file on the classpath
	public static String ODALYS_XML_LOCAL_FILE_PATH;
	public static final String XML_EXTENSION = ".xml";
	
	static {
		
		LOG.info("user.home " + System.getProperty("user.home"));
		ODALYS_XML_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "odalys"
				+ File.separator;
		

	}

	
}
