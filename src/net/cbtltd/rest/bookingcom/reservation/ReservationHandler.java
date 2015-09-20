package net.cbtltd.rest.bookingcom.reservation;

import java.util.Arrays;
import java.util.List;

import net.cbtltd.rest.bookingcom.availability.AvailabilityUtils;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ChannelLastFetchMapper;
import net.cbtltd.server.api.ChannelProductMapMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelLastFetch;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class ReservationHandler {
	
	static List<Integer> noxPropertiesBookingIds = Arrays.asList(1123700, 1123699, 1123698,  1123697, 1123696, 
												1123695, 1123694, 1123693, 1123692, 1123691,
												1123690, 1123689, 1123688, 1123687,  1123686);
	
	static List<Integer> noxPropertiesIds = Arrays.asList(252276, 53929, 53868,  53448, 51947, 
			48692, 41736, 37786, 1490, 1287,
			703, 569, 151, 150,  141);
	
	static int BOOKING_CHANNEL_ID = 276;
	
	private static DateTimeFormatter dtf = DateTimeFormat
			.forPattern("yy-MM-dd-hh-mm");
	
	// version date format
	public static DateTimeFormatter vdtf = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// hotel id - 1123699
//		Integer hotelId = 1123699;
		
//		ReservationHandler.fetchAllReservationsForHotel(hotelId);
		
//		ReservationHandler.updateAvailabilityInBookingCom();
		
		ReservationUtils reservationUtils = new ReservationUtils();
//		SqlSession sqlSession = RazorServer.openSession();
//		
		reservationUtils.fetchAndUpdateAllReservationsFromFile(1123693, "14-10-01-12-00");
		
		// cancellation
//		reservationUtils.fetchAndUpdateAllReservationsFromFile(1123693, "14-10-09-10-00");
		
//		reservationUtils.fetchAndUpdateAllReservationsFromFile(1123688, "14-10-07-01-30");
		
		
	
	}
	
	public static void fetchAllReservations() {
		ReservationUtils reservationUtils = new ReservationUtils();
		SqlSession sqlSession = RazorServer.openSession();
		
		// find reservation happened after the last fetch
		DateTime currentDateTime = new DateTime();
		String currentTime = dtf.print(currentDateTime);
		ChannelLastFetch queryBookingComLastFetch = new ChannelLastFetch();
		queryBookingComLastFetch.setApiName("reservation");
		queryBookingComLastFetch.setChannelId(RazorConfig.getBookingChannelPartnerId());
		ChannelLastFetch bookingComLastFetch = sqlSession.getMapper(ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		
		for(int hotelId:noxPropertiesBookingIds) {
			reservationUtils.fetchReservationFromBookingCom(hotelId, currentTime);
			reservationUtils.fetchAndUpdateAllReservationsFromFile(hotelId, currentTime);
		}
		
		bookingComLastFetch.setLastfetch(vdtf.print(currentDateTime));
		sqlSession.commit();
		sqlSession.close();
	}
	
	public static void fetchAllReservationsForHotel(int hotelId) {
		ReservationUtils reservationUtils = new ReservationUtils();
	
		DateTime currentDateTime = new DateTime();
		String currentTime = dtf.print(currentDateTime);
		reservationUtils.fetchReservationFromBookingCom(hotelId, currentTime);
		reservationUtils.fetchAndUpdateAllReservationsFromFile(hotelId, currentTime);
		
		SqlSession sqlSession = RazorServer.openSession();
		ChannelLastFetch queryBookingComLastFetch = new ChannelLastFetch();
		queryBookingComLastFetch.setApiName("reservation");
		queryBookingComLastFetch.setChannelId(RazorConfig.getBookingChannelPartnerId());
		ChannelLastFetch bookingComLastFetch = sqlSession.getMapper(ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		bookingComLastFetch.setLastfetch(vdtf.print(currentDateTime));
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	public static void updateAvailabilityInBookingCom() {
		SqlSession sqlSession = RazorServer.openSession();
		
		// find reservation happened after the last fetch
		DateTime currentTime = new DateTime();
		ChannelLastFetch queryBookingComLastFetch = new ChannelLastFetch();
		queryBookingComLastFetch.setApiName("availability");
		queryBookingComLastFetch.setChannelId(RazorConfig.getBookingChannelPartnerId());
		ChannelLastFetch bookingComLastFetch = sqlSession.getMapper(ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		
		ChannelProductMap tempChannelProductMap = new ChannelProductMap();
		tempChannelProductMap.setChannelId(ReservationHandler.BOOKING_CHANNEL_ID);
		List<ChannelProductMap> channelProductMaps = sqlSession.getMapper(ChannelProductMapMapper.class).readAllBPProduct(tempChannelProductMap );
		
		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		
		for(ChannelProductMap channelProductMap:channelProductMaps) {
			
			Reservation reservation = new Reservation();
			reservation.setProductid(channelProductMap.getProductId());
			reservation.setVersion(vdtf.parseDateTime(bookingComLastFetch.getLastfetch()).toDate());
			List<Reservation> reservations = sqlSession.getMapper(ReservationMapper.class).readActiveBasedOnTime(reservation);
			
			for(Reservation activeReservation:reservations) {
				if(new DateTime(activeReservation.getTodate()).isAfter(currentTime)) {
					availabilityUtils.fetchAndUpdateAvailabilityToBookingCom(activeReservation, channelProductMap);
				}
			}
			
		}

		// save the last fetch
		queryBookingComLastFetch.setLastfetch(vdtf.print(currentTime));
		sqlSession.getMapper(ChannelLastFetchMapper.class).update(queryBookingComLastFetch);
	}
	
	

}