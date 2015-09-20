package net.cbtltd.rest.hotelscombined.availability;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.hotelscombined.HotelsCombinedUtils;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailGetRQ;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ.AvailStatusMessages;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.integration.channel.ChannelIntegrationUtils;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Reservation.State;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class AvailabilityUtils {
	private static final Logger LOG = Logger.getLogger(AvailabilityUtils.class
			.getName());
	private boolean loadSelectedProducts = false;
	private List<ChannelProductMap> listToBeLoadedChannelProductMap = new ArrayList<ChannelProductMap>();
	private List<String> listInputProducts = new ArrayList<String>();
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");


	/**
	 * This method is fo bulk updates
	 * 
	 */
	public void notifyAvailability() {

		SqlSession sqlSession = RazorServer.openSession();
		try {
			List<ChannelProductMap> listChannelProductMap = new ArrayList<ChannelProductMap>();

			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			listChannelProductMap = ChannelProductService.getInstance()
					.readAllProductFromChannelProductMapper(sqlSession,
							RazorConfig.getHotelsCombinedChannelPartnerId());
			if (loadSelectedProducts) {
				for (ChannelProductMap channelProductMap : listChannelProductMap) {
					if (listInputProducts.contains(channelProductMap
							.getProductId())) {
						listToBeLoadedChannelProductMap.add(channelProductMap);
					}
				}
			} else {
				listToBeLoadedChannelProductMap = listChannelProductMap;
			}
			Map<ChannelProductMap, List<Reservation>> mapReservation = new HashMap<ChannelProductMap, List<Reservation>>();
			Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
			for (ChannelProductMap channelProductMap : listToBeLoadedChannelProductMap) {
				LOG.info("Loading reservation for property id <"
						+ channelProductMap.getChannelProductId()
						+ ">.................");

			List<Reservation> listReservation = sqlSession.getMapper(ReservationMapper.class).getschedule(channelProductMap.getProductId());

				if (listReservation != null) {
					LOG.info("Size of listReservation is " + listReservation.size());
				}
				if (listReservation != null && listReservation.size() > 0) {
					LOG.info("listReservation.size() " + listReservation.size());
					
					mapReservation.put(channelProductMap,listReservation);
				}

				

			}
			if(mapReservation!=null && !mapReservation.isEmpty()){
				for (Entry<ChannelProductMap, List<Reservation>> entry : mapReservation.entrySet()) {
					LOG.info("Processing Reservation for Product id "+entry.getKey().getProductId());
					mapStatusCodeMap.putAll(this.notifyAvailability(entry.getKey(),entry.getValue()));
				}
			
		
			}
			LOG.info("Bulk opertaion result is ");
			for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
				LOG.info("Upload status for <" + entry.getKey() + ">: "
						+ entry.getValue());
			}

		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}

	}
	
	/**
	 * This method will handle the publish/subscribe model.Bulk updates will use
	 * this method
	 * @param mapPrice
	 * @return
	 */
	public Map<String, String> notifyAvialability(Map<ChannelProductMap, List<Reservation>> mapReservation){
		System.out.println("Inisde notifyAvialability "+mapReservation.size());
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		for (Entry<ChannelProductMap, List<Reservation>> entry : mapReservation.entrySet()) {
			LOG.info("Processing Price for Product id "+entry.getKey().getProductId());
			mapStatusCodeMap.putAll(this.notifyAvailability(entry.getKey(),entry.getValue()));
		}
		for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
			LOG.info("Upload status for <" + entry.getKey() + ">: "
					+ entry.getValue());
		}
		return mapStatusCodeMap;
	}

	/**
	 *
	 * 
	 * @param price
	 * @param channelProductMap
	 * @return
	 */
	public Map<String, String> notifyAvailability(ChannelProductMap channelProductMap,List<Reservation> listReservation) {
		SqlSession sqlSession = RazorServer.openSession();
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		try {
			// get the calendar response for the product
			CalendarResponse calendarResponse = ChannelIntegrationUtils
					.getAvailabilityCalendar(channelProductMap.getProductId());

			// prepare the Rates request
			OTAHotelAvailNotifRQ otaHotelAvailNotifRQ = this
					.createAvailabilityRequest(calendarResponse, listReservation,
							channelProductMap);

			// call the HotelsCombined Rates API
			String statusKey = channelProductMap.getProductId() + ","
					+ channelProductMap.getChannelProductId() + ","
					+ channelProductMap.getChannelRoomId();
			String statusCode = this.callAvailabilityNotificationAPI(otaHotelAvailNotifRQ);
			mapStatusCodeMap.put(statusKey, statusCode);
			LOG.info("statusKey " + statusKey + " statusCode " + statusCode);

		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
		return mapStatusCodeMap;
	}
	

	private OTAHotelAvailNotifRQ createAvailabilityRequest(
			CalendarResponse calendarResponse, List<Reservation> listReservation,
			ChannelProductMap channelProductMap) {
		OTAHotelAvailNotifRQ otaHotelAvailNotifRQ=new OTAHotelAvailNotifRQ();
		otaHotelAvailNotifRQ.setEchoToken(HotelsCombinedUtils.getGUID());
		otaHotelAvailNotifRQ.setTimeStamp(HotelsCombinedUtils.getCurrentDateTimeInISOFormat());
		AvailStatusMessages availStatusMessages=new AvailStatusMessages();
		availStatusMessages.setHotelCode(channelProductMap.getChannelProductId());
	
		for(Reservation reservation:listReservation){
			AvailStatusMessage availStatusMessage=new AvailStatusMessage();
			//availStatusMessage.setBookingLimit("0");
			
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.RestrictionStatus restrictionStatus=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.RestrictionStatus();
			if(State.Cancelled.name().equalsIgnoreCase(reservation.getState())){
				restrictionStatus.setStatus("Open");
			}else{
				restrictionStatus.setStatus("Close");
			}
			
			restrictionStatus.setRestriction("Master");
			availStatusMessage.setRestrictionStatus(restrictionStatus);
			
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl statusApplicationControl=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl();
			statusApplicationControl.setStart(dateTimeFormatter.print(new DateTime(reservation.getFromdate())));
			statusApplicationControl.setEnd(dateTimeFormatter.print(new DateTime(reservation.getTodate())));
			statusApplicationControl.setRatePlanCode(channelProductMap.getChannelRateId());
			statusApplicationControl.setInvTypeCode(channelProductMap.getChannelRoomId());
			availStatusMessage.setStatusApplicationControl(statusApplicationControl);
			
	
			
			availStatusMessages.getAvailStatusMessage().add(availStatusMessage);
		}
		
		otaHotelAvailNotifRQ.getAvailStatusMessages().add(availStatusMessages);
		
		return otaHotelAvailNotifRQ;
	}

	private String callAvailabilityNotificationAPI(
			OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailNotifRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelAvailNotifRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Notification API Request " + xmlWriter);
	
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}
	
	public void getAvailability(String startDate,String endDate,String hotelCode,String ratePlanCode) throws Exception{
		OTAHotelAvailGetRQ otaHotelAvailGetRQ=new OTAHotelAvailGetRQ();
		otaHotelAvailGetRQ.setEchoToken(HotelsCombinedUtils.getGUID());
		otaHotelAvailGetRQ.setTimeStamp(HotelsCombinedUtils.getCurrentDateTimeInISOFormat());
		OTAHotelAvailGetRQ.HotelAvailRequests hotelAvailRequests =new OTAHotelAvailGetRQ.HotelAvailRequests();
		
		OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest hotelAvailRequest=new OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest();
		
		OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HotelRef hotelRef=new OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HotelRef();
		hotelRef.setHotelCode(hotelCode);
		hotelAvailRequest.setHotelRef(hotelRef);
		
		OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DateRange dateRange=new OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DateRange();
		dateRange.setStart(startDate);
		dateRange.setEnd(endDate);
		hotelAvailRequest.setDateRange(dateRange);
		
		OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates roomTypeCandidates=new OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates();
		OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates.RoomTypeCandidate roomTypeCandidate=new OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates.RoomTypeCandidate();
		roomTypeCandidate.setRoomTypeCode(ratePlanCode);
		roomTypeCandidates.setRoomTypeCandidate(roomTypeCandidate);
		hotelAvailRequest.setRoomTypeCandidates(roomTypeCandidates);
		
		hotelAvailRequests.setHotelAvailRequest(hotelAvailRequest);
		
		otaHotelAvailGetRQ.setHotelAvailRequests(hotelAvailRequests);
		
		String response=this.callAvailabilityFetchAPI(otaHotelAvailGetRQ);
		LOG.info("HotelsCombined Rates Fetch API Response "+response);
		
	}

	
	private String callAvailabilityFetchAPI(
			OTAHotelAvailGetRQ otaHotelAvailGetRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailGetRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelAvailGetRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Fetch API Request " + xmlWriter);
		
		
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}

	public boolean isLoadSelectedProducts() {
		return loadSelectedProducts;
	}

	public void setLoadSelectedProducts(boolean loadSelectedProducts) {
		this.loadSelectedProducts = loadSelectedProducts;
	}

	public List<ChannelProductMap> getListToBeLoadedChannelProductMap() {
		return listToBeLoadedChannelProductMap;
	}

	public void setListToBeLoadedChannelProductMap(
			List<ChannelProductMap> listToBeLoadedChannelProductMap) {
		this.listToBeLoadedChannelProductMap = listToBeLoadedChannelProductMap;
	}

	public List<String> getListInputProducts() {
		return listInputProducts;
	}

	public void setListInputProducts(List<String> listInputProducts) {
		this.listInputProducts = listInputProducts;
	}

	

}
