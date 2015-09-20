package net.cbtltd.rest.edomizil.xmlexport.test;

import java.io.IOException;

import net.cbtltd.rest.edomizil.xmlexport.PropertyExportUtils;
import net.cbtltd.rest.edomizil.xmlexport.XMLExportUtils;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price.Rule;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test case for testing e-domizil export
 * @author Suresh Kumar S
 * 
 */
public class XMLExportTest {

	Partner partner;
	SqlSession sqlSession;
	static String altpartyid;
	static XMLExportUtils xmlExportUtils;
	static String exportWithAccessPointAs= "mybookingpal.com";// "localhost:8080";

	@BeforeClass
	public static void setUp() throws Exception {
		 xmlExportUtils=new XMLExportUtils();
		 PropertyExportUtils.setExportWithAccessPointAs(exportWithAccessPointAs);
	}
//	public static void main(String[] args) {
//		DateTime dt= new DateTime();
//		System.out.println(dt.dayOfWeek().get());
//		System.out.println(Rule.SunCheckIn.ordinal());
//		System.out.println(Rule.MonCheckIn.ordinal());
//		System.out.println(Rule.TueCheckIn.ordinal());
//		System.out.println(Rule.SatCheckIn.ordinal());
//		
//	}

	@Test
	public void testExportProperty() throws Exception {
		
		xmlExportUtils.exportProductXML();
	}
	@Test
	public void testExportPrices() throws Exception {
		xmlExportUtils.exportPricesXML();
	}

	@Test
	public void testExportAvailability() throws Exception {
		
		xmlExportUtils.exportAvailabilityXML();
	}


	
	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}

}
