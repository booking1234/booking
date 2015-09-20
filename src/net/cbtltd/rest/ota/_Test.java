/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.ota;

import java.io.FileWriter;

import javax.xml.bind.JAXBContext;

import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTACancelRS;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRS;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRQ;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRS;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRS;
import net.cbtltd.soap.ota.server.OTAPingRQ;
import net.cbtltd.soap.ota.server.OTAPingRS;
import net.cbtltd.soap.ota.server.OTAReadRQ;
import net.cbtltd.soap.ota.server.OTAResRetrieveRS;
import net.cbtltd.soap.ota.server.ObjectFactory;
import net.cbtltd.soap.ota.server.OtaServiceRazor;

public class _Test {

	public static void main(String args[]) throws Exception {
		//Service service = new Service();
		System.out.println("Test start.");
		try {
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			if (false) {			System.out.println("\n\n");
			OTACancelRQ rq = OtaServiceRazor.cancelRQ("100602", "This is a test cancellation.", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTACancelRS rs = OtaServiceRazor.cancelRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
			if (false) {			System.out.println("\n\n");
			OTAHotelAvailRQ rq = OtaServiceRazor.hotelAvailRQ("143, 144, 146, 155", "2009-12-19", "2009-12-25", "5");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelAvailRS rs = OtaServiceRazor.hotelAvailRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
			if (false) {			System.out.println("\n\n");
			OTAHotelDescriptiveInfoRQ rq = OtaServiceRazor.hotelDescriptiveInfoRQ("143", "5");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelDescriptiveInfoRS rs = OtaServiceRazor.hotelDescriptiveInfoRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
//			if (false) {			System.out.println("\n\n");
//			OTAHotelInvCountNotifRQ rq = OtaServiceRazor.hotelInvCountNotifRQ("155", "2009-07-19", "2009-07-25", "5");
//			context.createMarshaller().marshal(rq, System.out);
//			}
//			if (false) {			System.out.println("\n\n");
//			OTAHotelInvNotifRQ rq = OtaServiceRazor.hotelInvNotifRQ("5", "155", "5");
//			context.createMarshaller().marshal(rq, System.out);
//			OTAHotelInvNotifRS rs = OtaServiceRazor.hotelInvNotifRS(rq);
//			context.createMarshaller().marshal(rs, System.out);
//			}
//			if (false) {			System.out.println("\n\n");
//			OTAHotelRatePlanNotifRQ rq = OtaServiceRazor.hotelRatePlanNotifRQ("155", "5");
//			context.createMarshaller().marshal(rq, System.out);
//			OTAHotelRatePlanNotifRS rs = OtaServiceRazor.hotelRatePlanNotifRS(rq);
//			context.createMarshaller().marshal(rs, System.out);
//			}
			if (false) {			System.out.println("\n\n");
			OTAHotelRatePlanRQ rq = OtaServiceRazor.hotelRatePlanRQ("155", "2011-05-01", "2012-06-30", "ZAR", "5");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelRatePlanRS rs = OtaServiceRazor.hotelRatePlanRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
			if (false) {			System.out.println("\n\n");
			OTAHotelResNotifRQ rq = OtaServiceRazor.hotelResNotifRQ("155", "2009-07-19", "2009-07-25", "chris@razor.com", "Mars", "Christopher", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelResNotifRS rs = OtaServiceRazor.hotelResNotifRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("HotelCode=143,HotelCode=144", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("HAC=88,PCT=8", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			//		context.createMarshaller().marshal(rs, System.out);
			FileWriter outputStream = new FileWriter("C:/HotelSearchOtaRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("CardCode=MC,CardCode=VI", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			FileWriter outputStream = new FileWriter("C:/HotelSearchCardRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("Adults=2,Children=2", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			FileWriter outputStream = new FileWriter("C:/HotelSearchCountRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("MaxRate=1000,Currency=ZAR,ArrivalDate=2009-07-20", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			FileWriter outputStream = new FileWriter("C:/HotelSearchRateRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("AreaID=58777,58878", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			FileWriter outputStream = new FileWriter("C:/HotelSearchRateRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");	
			OTAHotelSearchRQ rq = OtaServiceRazor.hotelSearchRQ("Latitude=-33.95,Longitude=18.37,Radius=10,MaxResponses=5", "2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
			//context.createMarshaller().marshal(rs, System.out);
			FileWriter outputStream = new FileWriter("C:/HotelSearchPositionRS.xml");
			context.createMarshaller().marshal(rs, outputStream);
			outputStream.close();
			}
			if (false) {			System.out.println("\n\n");
			OTAPingRQ rq = OtaServiceRazor.pingRQ("2376");
			context.createMarshaller().marshal(rq, System.out);
			OTAPingRS rs = OtaServiceRazor.pingRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
//			if (false) {			System.out.println("\n\n");
//			OTAProfileCreateRQ rq = OtaServiceRazor.profileCreateRQ("5", "2376");
//			context.createMarshaller().marshal(rq, System.out);
//			OTAProfileCreateRS rs = OtaServiceRazor.profileCreateRS(rq);
//			context.createMarshaller().marshal(rs, System.out);
//			}
			if (false) {			System.out.println("\n\n");
			OTAReadRQ rq = OtaServiceRazor.readRQ("5", "102503");
			context.createMarshaller().marshal(rq, System.out);
			OTAResRetrieveRS rs = OtaServiceRazor.resRetrieveRS(rq);
			context.createMarshaller().marshal(rs, System.out);
			}
		} catch (Throwable x) {x.printStackTrace();}
		System.out.println("Test end.");
	}
}
