/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import javax.xml.bind.JAXBContext;

import net.cbtltd.shared.Language;
import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTACancelRS;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRS;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRQ;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRS;
import net.cbtltd.soap.ota.server.OTAHotelResRQ;
import net.cbtltd.soap.ota.server.OTAHotelResRS;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRS;
import net.cbtltd.soap.ota.server.OTAPingRQ;
import net.cbtltd.soap.ota.server.OTAPingRS;
import net.cbtltd.soap.ota.server.OTAProfileCreateRQ;
import net.cbtltd.soap.ota.server.OTAProfileCreateRS;
import net.cbtltd.soap.ota.server.ObjectFactory;
import net.cbtltd.soap.ota.server.OtaServiceEres;
import net.cbtltd.soap.ota.server.OtaServiceRazor;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

/**
 * The Class SOAPClient is the test application for Razor Cloud SOAP web services.
 * @see <pre>http://cxf.apache.org/docs/</pre>
 * @see	<pre>http://mail-archives.apache.org/mod_mbox/cxf-issues/201105.mbox/%3C122353421.26874.1304646183148.JavaMail.tomcat@hel.zones.apache.org%3E</pre>
 */
public final class SOAPClient {

	private static final Logger LOG = Logger.getLogger(SOAPClient.class.getName());
//	public static final String ENDPOINT_ADDRESS = "http://localhost:8080/xml/soap";
	public static final String ENDPOINT_ADDRESS = "https://razor-cloud.com/xml/soap";

	private SOAPClient() {}

	public static void main(String args[]) throws Exception {
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(RazorPortType.class);
			factory.setAddress(ENDPOINT_ADDRESS);
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			RazorPortType port = (RazorPortType) factory.create();

			if (false) {
				LOG.debug("OTACancelRQ");
				OTACancelRQ rq = OtaServiceRazor.cancelRQ("110305", "Too expensive", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTACancelRS rs = port.cancelReservation(rq);
				LOG.debug("\nOTACancelRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAHotelAvailRQ");
				OTAHotelAvailRQ rq = OtaServiceRazor.hotelAvailRQ("144", "2013-06-01", "2013-06-11", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelAvailRS rs = port.getAvailability(rq);
				LOG.debug("\nOTAHotelAvailRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAHotelDescriptiveInfoRQ");
				OTAHotelDescriptiveInfoRQ rq = OtaServiceRazor.hotelDescriptiveInfoRQ("145", "your_pos_code");
				rq.setPrimaryLangID(Language.EN);
				HotelDescriptiveInfos hotelDescriptiveInfos = new HotelDescriptiveInfos();
				rq.setHotelDescriptiveInfos(hotelDescriptiveInfos);
				hotelDescriptiveInfos.setLangRequested(Language.EN);
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelDescriptiveInfoRS rs = port.getDescription(rq);
				LOG.debug("\nOTAHotelDescriptiveInfoRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAPingRQ");
				OTAPingRQ rq = OtaServiceRazor.pingRQ("your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAPingRS rs = port.ping(rq);
				LOG.debug("\nOTAPingRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAHotelRatePlanRQ");
				OTAHotelRatePlanRQ rq = OtaServiceRazor.hotelRatePlanRQ("145", "2013-06-01", "2013-06-11", "ZAR", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelRatePlanRS rs = port.getRate(rq);
				LOG.debug("\nOTAHotelRatePlanRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAProfileCreateRQ");
				OTAProfileCreateRQ rq = OtaServiceRazor.profileCreateRQ("ADDRESS=107 Victoria Road, BIRTHDATE=1945-10-28, CITYNAME= CapeTown, COMPANYNAME=Tourinfo CC, COUNTRYCODE=ZA, COUNTRYNAME=South Africa, CURRENCYCODE=ZAR, DAYPHONE=021 438 5048, EMAILADDRESS=gila@camps.com, FAXPHONE=021 438 5693, GIVENNAME=Gila, IDENTITY=CK12222, LANGUAGE=EN, MARITALSTATUS=M, MOBILEPHONE=082 600 5212, POBOXNUMBER=PO Box 128, POSTALCODE=8040, REGIONCODE=WC, REGIONNAME=Western Cape, STREETNUMBER=107, SURNAME=Marshall, TEXT=My notes, TYPE=1, URL=www.campabayterrace.com, VIP=true", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAProfileCreateRS rs = port.createParty(rq);
				LOG.debug("\nOTAProfileCreateRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAHotelResNotifRQ");
				OTAHotelResNotifRQ rq = OtaServiceRazor.hotelResNotifRQ("145", "2013-06-01", "2013-06-11", "chris@zzz.com", "Vvvvv", "Chris", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelResNotifRS rs = port.createReservation(rq);
				LOG.debug("\nOTAHotelResNotifRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\nOTAHotelSearchRQ");
				OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("HotelCode=143,HotelCode=144,HotelCode=146,HotelCode=155,ArrivalDate=2013-12-19,DepartureDate=2013-12-25", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelSearchRS rs = port.findProperty(rq);
				LOG.debug("\nOTAHotelSearchRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\neRes OTAHotelAvailRQ");
				OTAHotelAvailRQ rq = OtaServiceRazor.hotelAvailRQ("144", "2013-06-01", "2013-06-11", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelAvailRS rs = port.eresAvailability(rq);
				LOG.debug("\neRes OTAHotelAvailRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (false) {
				LOG.debug("\neRes OTAHotelResNotifRQ");
				OTAHotelResRQ rq = OtaServiceEres.hotelResRQ("145", "2013-06-01", "2013-06-11", "chris@zzz.com", "Vvvvv", "Chris", "your_pos_code");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
				OTAHotelResRS rs = port.eresReservation(rq);
				LOG.debug("\neRes OTAHotelResNotifRS");
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
		}
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			x.printStackTrace();
		}
		finally{System.exit(0);}
	}
}
