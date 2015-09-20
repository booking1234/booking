package net.cbtltd.rest.atraveo.xmlexport.test;

import java.io.IOException;

import net.cbtltd.rest.atraveo.xmlexport.ExportUtils;
import net.cbtltd.rest.atraveo.xmlexport.ExportUtils.EXPORT_USING;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test case for testing e-domizil export
 * @author Suresh Kumar S
 * 
 */
public class ExportTest {

	SqlSession sqlSession;
	static String channelid;
	static ExportUtils exportUtils=new ExportUtils();
	static String exportWithAccessPointAs= "uat.mybookingpal.com";// "localhost:8080";

	@BeforeClass
	public static void setUp() throws Exception {
		channelid="8800";
		ExportUtils.setExportWithAccessPointAs(exportWithAccessPointAs);
	}

	@Test
	public void testExport() throws Exception {
		exportUtils.generatePropertyXML(channelid,EXPORT_USING.SUPPLIER_ID);
		exportUtils.generatePricingXML(channelid,EXPORT_USING.SUPPLIER_ID);
	}

	
	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}

}
