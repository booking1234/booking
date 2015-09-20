package net.cbtltd.trial.test.anet;

/*
 * Before working with this sample code, please be sure to read the accompanying Readme.txt file.
 * It contains important information regarding the appropriate use of and conditions for this sample
 * code. Also, please pay particular attention to the comments included in each individual code file,
 * as they will assist you in the unique and correct implementation of this code on your specific platform.
 *
 * Copyright 2009 Authorize.Net Corp.
 */

public class DeleteProfiles {
	public static void main(String args[]) {
		System.out.println();
		System.out.println("Delete Profile Test");
		
		String customerProfileId = "-1";
		if(args.length > 0) {
			customerProfileId = args[0];
		}
		
		String xml = "$xmldecl$\r\n<deleteCustomerProfileRequest $xmlns$>$merchauth$<customerProfileId>$customerProfileId$</customerProfileId></deleteCustomerProfileRequest>";
		xml = xml.replace("$customerProfileId$", APIUtilities.escapeXml(customerProfileId));
		xml = APIUtilities.prepareXml(xml);
		
		org.w3c.dom.Document doc = APIUtilities.sendRequest(xml);
		
		if (doc != null) {
			System.out.println("Successfully deleted customer profile id " + customerProfileId);
		}
	}
}