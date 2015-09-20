package net.cbtltd.rest.flipkey.xmlexport.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Property;
import net.cbtltd.rest.common.utils.report.Cell;
import net.cbtltd.rest.common.utils.report.ReportUtil;
import net.cbtltd.rest.common.utils.report.Row;
import net.cbtltd.rest.common.utils.report.Table;
import net.cbtltd.rest.common.utils.report.Worksheet;
import net.cbtltd.rest.flipkey.xmlexport.Launcher;
import net.cbtltd.rest.flipkey.xmlfeed.A_Handler;
import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyData;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.price.PriceCreate;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test case for testing flipkey export
 * @author nibodha
 * 
 */
public class LauncherTest {

	Partner partner;
	SqlSession sqlSession;
	static String altpartyid;
	static Launcher launcher;

	@BeforeClass
	public static void setUp() throws Exception {
		altpartyid = "100";
		System.out.println("altpartyid " + altpartyid);
		launcher = new Launcher(altpartyid);
		

	}

	@Test
	public void testExport() throws Exception {
		launcher.generatePropertyData();
	}

	
	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}

}
