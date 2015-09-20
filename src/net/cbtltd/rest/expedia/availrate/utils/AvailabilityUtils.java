package net.cbtltd.rest.expedia.availrate.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.expedia.availrate.domain.AvailRateUpdateRQ;
import net.cbtltd.rest.expedia.availrate.domain.DateRangeType;
import net.cbtltd.rest.expedia.availrate.domain.RoomTypeType;
import net.cbtltd.rest.expedia.utils.ExpediaUtils;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import net.cbtltd.shared.Reservation.State;

import com.mybookingpal.config.RazorConfig;

public class AvailabilityUtils {
	private static final Logger LOG = Logger.getLogger(AvailabilityUtils.class
			.getName());
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private boolean loadSelectedProducts = false;
	private List<ChannelProductMap> listToBeLoadedChannelProductMap = new ArrayList<ChannelProductMap>();
	private List<String> listInputProducts = new ArrayList<String>();
	
	
	/**
	 * This method is fo bulk updates
	 * 
	 */
	public void notifyAvailabiity() {

		SqlSession sqlSession = RazorServer.openSession();
		try {
			List<ChannelProductMap> listChannelProductMap = new ArrayList<ChannelProductMap>();

			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			listChannelProductMap = ChannelProductService.getInstance()
					.readAllProductFromChannelProductMapper(sqlSession,
							RazorConfig.getExpediaChannelPartnerId());
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
					LOG.info("Loading resevation for property id <"
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
	public Map<String, String> notifyAvailability(Map<ChannelProductMap, List<Reservation>> mapReservation){
		System.out.println("Inisde notify Availability "+mapReservation.size());
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
		

			// prepare the Rates request
			AvailRateUpdateRQ availRateUpdateRQ = this
					.createAvailabilityRequest(listReservation,
							channelProductMap);
			
			
			// call the Expedia Rates API
			String statusKey = channelProductMap.getProductId() + ","
					+ channelProductMap.getChannelProductId() + ","
					+ channelProductMap.getChannelRoomId();
			String statusCode="";
			statusCode = this.callAvailabilityUpdateAPI(availRateUpdateRQ);
			
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
	
	
	private AvailRateUpdateRQ createAvailabilityRequest(List<Reservation> listReservation,
			ChannelProductMap channelProductMap) {
		AvailRateUpdateRQ availRateUpdateRQ=new AvailRateUpdateRQ();
		AvailRateUpdateRQ.Authentication authentication=new AvailRateUpdateRQ.Authentication();
		//set authentication details
		authentication.setUsername(RazorConfig.getValue(ExpediaUtils.EXPEDIA_USERNAME,"testuser"));
		authentication.setPassword(RazorConfig.getValue(ExpediaUtils.EXPEDIA_PWD,"ECLPASS"));
		availRateUpdateRQ.setAuthentication(authentication);
		
		//set hotel details
		AvailRateUpdateRQ.Hotel hotel=new AvailRateUpdateRQ.Hotel();
		hotel.setId(Integer.parseInt(channelProductMap.getChannelProductId()));
		availRateUpdateRQ.setHotel(hotel);
		
		for(Reservation reservation:listReservation){
		//set AvailRateUpdate details
		AvailRateUpdateRQ.AvailRateUpdate availRateUpdate=new AvailRateUpdateRQ.AvailRateUpdate();
		
		//set dateRangeType
		DateRangeType dateRangeType=new DateRangeType();
		dateRangeType.setFrom(reservation.getCheckin());
		dateRangeType.setTo(reservation.getCheckout());
		availRateUpdate.getDateRange().add(dateRangeType);
		
		//set roomType details
		RoomTypeType roomTypeType=new RoomTypeType();
		roomTypeType.setId(channelProductMap.getChannelRoomId());
		if(State.Cancelled.name().equalsIgnoreCase(reservation.getState())){
			roomTypeType.setClosed(false);
		}else{
			roomTypeType.setClosed(true);
		}
		
		
		availRateUpdate.getRoomType().add(roomTypeType);
		
		
		
		
		availRateUpdateRQ.getAvailRateUpdate().add(availRateUpdate);
		}
		
		return availRateUpdateRQ;
	}
	
	
	

	private String callAvailabilityUpdateAPI(
			AvailRateUpdateRQ availRateUpdateRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(AvailRateUpdateRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(availRateUpdateRQ, xmlWriter);
		LOG.info("Expedia Rates Update API Request " + xmlWriter);
		return ExpediaUtils.callExpediaAPI(xmlWriter.toString(),RazorConfig.getValue(ExpediaUtils.EXPEDIA_AR_URL,"https://simulator.expediaquickconnect.com/connect/ar"),true);

	}
	
}
