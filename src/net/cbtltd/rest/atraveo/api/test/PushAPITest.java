package net.cbtltd.rest.atraveo.api.test;
//
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.atraveo.api.PushAPI;
import net.cbtltd.rest.atraveo.datamodel.AtraveoResponse;
import net.cbtltd.rest.atraveo.datamodel.ResponseFactory;

import org.junit.Test;

public class PushAPITest  {
	ResponseFactory factory=new ResponseFactory();
	PushAPI api=new PushAPI();
	AtraveoResponse response;
	JAXBContext jaxbContext;
	Marshaller jaxb;
	
	@Test
	public  void bookingTest() throws JAXBException {
		response=api.booking(null,"382960","2014-12-10","2014-12-16","2","1",
				"mr","manikandan","mani","wewersdfasdf","382960","tamil","india","9876543210",null,"mani@mani.com",null,null,"nothing",
				null,null,null,null,null);
		 jaxbContext = JAXBContext.newInstance(AtraveoResponse.class);
		 jaxb = jaxbContext.createMarshaller();
		jaxb.marshal(response, System.out);
	}

	@Test
	public  void rateTest() throws JAXBException {
		response=api.rate(null,"382960","2014-12-10","2014-12-16","2");
		 jaxbContext = JAXBContext.newInstance(AtraveoResponse.class);
		 jaxb = jaxbContext.createMarshaller();
		jaxb.marshal(response, System.out);
	}
	@Test
	public  void availablilityTest() throws JAXBException {
		AtraveoResponse response=api.availability(null,"382960");
		JAXBContext jaxbContext = JAXBContext.newInstance(AtraveoResponse.class);
		Marshaller jaxb = jaxbContext.createMarshaller();
		jaxb.marshal(response, System.out);
	}
	
	public static void main(String[] args) throws Exception {

//		availablilityTest();
//
//		rateTest();
//		bookingTest();

		/*
		 * productid=382960&datefrom2014-12-10&toDateString=2014-12-16&persons=2pets=2&salutation=mr&name=manikandan&firstname=mani&
		 * street=wewersdfasdf&zip=456456&city=tamil&country=ind&phone=9876543210&email=mani@mani.com&remarks=nothing
		 */
		//		objid=471583&datefrom=2014-12-10&dateto=2014-12-17&persons=5&pets=1&salutation=Mr&name=Testmensch&firstname=Dieter&street=Teststrae&zip=12345&city=Testort&country=DE&phone=01234567890&email=dieter@testmensch.de&birthday=1970-01-01&remarks=Testbuchung
		//				  &payment=CreditCard&creditcardtype=VISA&creditcardnumber=1234567890123456&validto=2015-10&checkdigit=456

	}
}
