package net.cbtltd.rest.bookingcom.upload.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.cbtltd.rest.bookingcom.upload.UploadUtils;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test case for testing upload utils
 * @author nibodha
 *
 */
public class UploadUtilsTest {
	private static final Logger LOG = Logger.getLogger(UploadUtilsTest.class
			.getName());
	
	@BeforeClass
	public static void setUp() throws Exception {
		
	}
	
	@Test
	public void testUploadProductData() throws Exception {
		UploadUtils uploadUtils=new UploadUtils();
		String[] supplierIds = {"15035", "48086", "11741", "8961", "12855", "5", "325315", "10791", "225945", "63151", "82018"};   //, Not registered "348024"

//		String[] supplierIds = {"15035"};
		
		String[] existingNoxProperties = {"252276", "53929", "53868", "53448", "51947", "48692", "41736",
				"1287", "37786", "1490", "703", "569", "151", "150", "141"};
		

		List<String> listId=new ArrayList<String>();
		
//		System.out.println(new File("").getAbsolutePath());
//		System.out.println(new File("/").getAbsolutePath());
//		System.out.println();
		//System.out.println(new File("/resources/template/bookingcom_export.xlsx").exists());
		
//		List<String> listSupplierId=new ArrayList<String>();
		//Supplier Id for V2.0 generation
		//listSupplierId.add("5");//Nox Tourism cc
		//listSupplierId.add("11741");//Villa and Apartment Management pty ltd
	//	listSupplierId.add("82018");//Vacationista
	//	listSupplierId.add("324431");//Java Jamica Limited
	//	listSupplierId.add("10791");//Red Cherry
	//	listSupplierId.add("8961");//SeaSky Villa Management
	//	listSupplierId.add("63151");//Anne Porter
	//	listSupplierId.add("9317");//Glen Beach Villas
	//	listSupplierId.add("15035");//Accommodation Hunter
	//	listSupplierId.add("165415");//Capeholidays.info
	//	listSupplierId.add("10387");//Classic Villas
	//	listSupplierId.add("48086");//A&E Services
	//	listSupplierId.add("12855");//SA Villas.com
	//	listSupplierId.add("55542");//Cape Dream Stay
//		listSupplierId.add("325315");//Clifton Bungalow
		
		
		// MBP Test Property
//		listSupplierId.add("8800");
		// Get Away - Streamline UAT
//		listSupplierId.add("325804");
		
		List<String> listSupplierId = new ArrayList<String>(Arrays.asList(supplierIds));
		
		List<String> listPropertyId=new ArrayList<String>();
//		listPropertyId.add("41736");
//		listPropertyId.add("37786");
//		listPropertyId.add("53868");
//		listPropertyId.add("51947");
//		listPropertyId.add("703");
//		listPropertyId.add("48692");
//		listPropertyId.add("53929");
//		listPropertyId.add("569");
//		listPropertyId.add("1490");
//		listPropertyId.add("1287");
//		listPropertyId.add("150");
//		listPropertyId.add("151");
//		listPropertyId.add("53448");
//		listPropertyId.add("141");
//		listPropertyId.add("252276");
		
		
		//  MBP Test Property
//		listPropertyId.add("878");
		try {
			uploadUtils.uploadPropertyDataAsExcel(listSupplierId,listPropertyId, Arrays.asList(existingNoxProperties));
		} catch(Exception e) {
			e.printStackTrace();
		}
		//uploadUtils.uploadPropertyData(listSupplierId,listPropertyId);
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}
}
