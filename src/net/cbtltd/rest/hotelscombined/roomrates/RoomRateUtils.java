package net.cbtltd.rest.hotelscombined.roomrates;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.hotelscombined.HotelsCombinedUtils;
import net.cbtltd.rest.hotelscombined.roomrates.domain.OTAHotelAvailRQ;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class RoomRateUtils {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyy-MM-dd");
	
	private static final Logger LOG = Logger.getLogger(RoomRateUtils.class.getName());
	
	public void getRoomRates(List<String> listHotelCode) throws Exception{
		OTAHotelAvailRQ otaHotelAvailRQ=new OTAHotelAvailRQ();
		for(String hotelCode:listHotelCode){
		
		OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment availRequestSegment=new OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment();
		OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria hotelSearchCriteria=new OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria();
		OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria.Criterion criterion=new OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria.Criterion();
		OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria.Criterion.HotelRef hotelRef=new OTAHotelAvailRQ.AvailRequestSegments.AvailRequestSegment.HotelSearchCriteria.Criterion.HotelRef();
		hotelRef.setHotelCode(hotelCode);
		criterion.setHotelRef(hotelRef);
		hotelSearchCriteria.setCriterion(criterion);
		availRequestSegment.setHotelSearchCriteria(hotelSearchCriteria);
		otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment().add(availRequestSegment);
		}
		LOG.info("RoomRates API Response "+this.callRoomRatesFetchAPI(otaHotelAvailRQ));
		
	}
	
	private String callRoomRatesFetchAPI(OTAHotelAvailRQ otaHotelAvailRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelAvailRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Fetch API Request " + xmlWriter);
		
		
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}
}
