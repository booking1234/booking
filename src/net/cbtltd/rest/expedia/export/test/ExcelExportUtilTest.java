package net.cbtltd.rest.expedia.export.test;


import java.io.IOException;

import net.cbtltd.rest.expedia.export.ExcelExportUtil;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExcelExportUtilTest {
	private static final Logger LOG = Logger.getLogger(ExcelExportUtilTest.class
			.getName());
	ExcelExportUtil excelExportUtil=new ExcelExportUtil(); 
	@BeforeClass
	public static void setUp() throws Exception {
		
	}
	
	@Test
	public void testUploadProductData() throws Exception {
		excelExportUtil.generatePropertyExcelFromProduct(146,
				53868,
				145,
				339180,
				254490,
				143,
				141,
				48562,
				37854,
				569);
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}
}
