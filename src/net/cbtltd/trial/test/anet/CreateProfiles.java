package net.cbtltd.trial.test.anet;

/*
 * Before working with this sample code, please be sure to read the accompanying Readme.txt file.
 * It contains important information regarding the appropriate use of and conditions for this sample
 * code. Also, please pay particular attention to the comments included in each individual code file,
 * as they will assist you in the unique and correct implementation of this code on your specific platform.
 *
 * Copyright 2009 Authorize.Net Corp.
 */

public class CreateProfiles {

	public static void main(String[] args) {
		System.out.println();
		System.out.println("Create Profile Test");
		String xml = "$xmldecl$\r\n<createCustomerProfileRequest $xmlns$>$merchauth$<profile><merchantCustomerId>$custid$</merchantCustomerId><description>$descr$</description><email>$email$</email></profile></createCustomerProfileRequest>";
		xml = xml.replace("$custid$", APIUtilities.escapeXml("custId" + APIUtilities.rand.nextInt()));
		xml = xml.replace("$descr$", APIUtilities.escapeXml("some description"));
		xml = xml.replace("$email$", APIUtilities.escapeXml("mark@example.com"));
		xml = APIUtilities.prepareXml(xml);
		
		org.w3c.dom.Document doc = APIUtilities.sendRequest(xml);
		
		if (doc != null) {
			try {
				javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
				String customerProfileId = xpath.evaluate("/*/customerProfileId/text()",  doc);
				System.out.println("Successfully created customer profile id " + customerProfileId);
				org.w3c.dom.NodeList customerPaymentProfileIdList = (org.w3c.dom.NodeList)xpath.evaluate("/*/customerPaymentProfileIdList/numericString/text()", doc, javax.xml.xpath.XPathConstants.NODESET);
				org.w3c.dom.NodeList validationDirectResponseList = (org.w3c.dom.NodeList)xpath.evaluate("/*/validationDirectResponseList/string/text()", doc, javax.xml.xpath.XPathConstants.NODESET);
				org.w3c.dom.NodeList customerShippingAddressIdList = (org.w3c.dom.NodeList)xpath.evaluate("/*/customerShippingAddressIdList/numericString/text()", doc, javax.xml.xpath.XPathConstants.NODESET);
				if (customerPaymentProfileIdList != null) {
					for (int i = 0; i < customerPaymentProfileIdList.getLength(); i++) {
						System.out.print(" - customer payment profile id " + customerPaymentProfileIdList.item(i).getNodeValue());
						if (validationDirectResponseList != null && i < validationDirectResponseList.getLength()) {
							String s = validationDirectResponseList.item(i).getNodeValue();
							if (s.length() > 40) {
								s = s.substring(0, 39) + "...";
							}
							System.out.print(" ; validation raw response: " + s);
						}
						System.out.println();
					}
					for (int i = 0; i < customerShippingAddressIdList.getLength(); i++) {
						System.out.println(" - customer shipping address id " + customerShippingAddressIdList.item(i).getNodeValue());
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
}
