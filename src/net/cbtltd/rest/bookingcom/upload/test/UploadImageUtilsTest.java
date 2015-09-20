package net.cbtltd.rest.bookingcom.upload.test;

import java.io.IOException;

import net.cbtltd.rest.bookingcom.upload.UploadImageUtils;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test cases for testing the upload image utils
 * 
 * @author nibodha
 *
 */
public class UploadImageUtilsTest {
	private static final Logger LOG = Logger.getLogger(UploadImageUtilsTest.class
			.getName());
	
	@BeforeClass
	public static void setUp() throws Exception {
		System.out.println("@@@ setUp method called @@@@@");
	}
	
	@Test
	public void testUploadImageData() throws Exception {
		UploadImageUtils uploadImageUtils=new UploadImageUtils();
	//	uploadImageUtils.uploadImageData();
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}
}
