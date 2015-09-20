package net.cbtltd.rest.ru.request;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;



public class Test_ListLanguages_RQ {
	
	protected static String username = "Cockabondy@hotmail.com";
	protected static String password = "Asd123456";

	public static void main(String[] args) {
		
		Authentication authentication = new Authentication();
		authentication.setPassword(password);
		authentication.setUsername(username);
		GetLocationDetails request = new GetLocationDetails();
		request.setAuthentication(authentication);
		request.setLocationid(419680);
		//System.out.println(request.toString());
		
		try {
			//File file = new File("D:\\Test_Request.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(GetLocationDetails.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			Writer stwr = new StringWriter();
			//jaxbMarshaller.marshal(request, file);
			jaxbMarshaller.marshal(request, System.out);
			jaxbMarshaller.marshal(request, stwr);
			
			String rs = stwr.toString();
			System.out.println(rs);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

}