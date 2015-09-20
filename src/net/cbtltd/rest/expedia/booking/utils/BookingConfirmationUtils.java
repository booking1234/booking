package net.cbtltd.rest.expedia.booking.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.expedia.booking.domain.BookingConfirmRQ;
import net.cbtltd.rest.expedia.booking.domain.BookingConfirmRS;
import net.cbtltd.rest.expedia.utils.ExpediaUtils;
import net.cbtltd.server.api.ChannelProductMapMapper;
import net.cbtltd.server.api.ReservationExtMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.ReservationExt;
import net.cbtltd.shared.Reservation.State;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class BookingConfirmationUtils {
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
	private static final Logger LOG = Logger.getLogger(ReservationUtils.class
			.getName());

	public void updateBookingConfirmationToExpedia(SqlSession sqlSession,
			List<Reservation> listPersistedReservation) {
		try {
			// prepare a map where key is expedia HotelId and reservation as
			// value
			Map<String, List<Reservation>> mapReservation = prepareMapForExpedia(
					sqlSession, listPersistedReservation);

			// prepare a map where key is expedia HotelId and reservation as
			// value

			Map<BookingConfirmRQ, List<Reservation>> mapBookingConfirmation = this
					.createBookingConfirmationRequest(mapReservation);

			for (Entry<BookingConfirmRQ, List<Reservation>> entry : mapBookingConfirmation
					.entrySet()) {

				// call the Expedia BC API
				String expediaBookingConfirmationResponse = this
						.callExpediaBookingConfirmationAPI(entry.getKey());

				BookingConfirmRS bookingConfirmRS = this
						.prepareBookingConfirmationFromXMLResponse(expediaBookingConfirmationResponse);
				if (bookingConfirmRS.getSuccess() != null) {
					this.updateBPReservationAsConfirmed(sqlSession,entry.getValue());
				} else {
					for (BookingConfirmRS.Error error : bookingConfirmRS
							.getError()) {
						LOG.error("Error in Expedia Booking Confirmation response "
								+ error.getCode() + " " + error.getValue());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		}

	}

	/**
	 * To form Reservations object from XML response
	 * 
	 * @param reservationResponse
	 * @return Reservations
	 * @throws Exception
	 */
	private BookingConfirmRS prepareBookingConfirmationFromXMLResponse(
			String expediaBookingConfirmationResponse) throws Exception {
		expediaBookingConfirmationResponse=expediaBookingConfirmationResponse.replace("xmlns=\"http://www.expediaconnect.com/EQC/BC/2007/08\"", "");
		LOG.info("Expedia Response" + expediaBookingConfirmationResponse);
		StringReader xmlReader = new StringReader(
				expediaBookingConfirmationResponse.toString());
		JAXBContext jaxbContext = JAXBContext
				.newInstance(BookingConfirmRS.class);
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		// jaxbUnMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		return (BookingConfirmRS) jaxbUnMarshaller.unmarshal(xmlReader);
	}

	private String callExpediaBookingConfirmationAPI(
			BookingConfirmRQ bookingConfirmRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(BookingConfirmRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(bookingConfirmRQ, xmlWriter);
		LOG.info("Expedia Booking Confirmation API Request " + xmlWriter);
		return ExpediaUtils
				.callExpediaAPI(
						xmlWriter.toString(),
						RazorConfig
								.getValue(ExpediaUtils.EXPEDIA_BC_URL,
										"https://simulator.expediaquickconnect.com/connect/bc"),
						false);

	}

	private Map<String, List<Reservation>> prepareMapForExpedia(
			SqlSession sqlSession, List<Reservation> listPersistedReservation) {
		Map<String, List<Reservation>> mapReservation = new HashMap<String, List<Reservation>>();
		for (Reservation reservation : listPersistedReservation) {
			// Get the BP Hotel id from the mapping table and set the hotel id
			ChannelProductMap tempChannelProductMap = new ChannelProductMap();
			tempChannelProductMap.setChannelId(RazorConfig
					.getExpediaChannelPartnerId());
			tempChannelProductMap.setProductId(reservation.getProductid());
			ChannelProductMap channelProductMap = sqlSession.getMapper(
					ChannelProductMapMapper.class).findByBPProductAndChannelId(
					tempChannelProductMap);
			if (mapReservation.get(channelProductMap.getChannelProductId()) == null) {
				List<Reservation> tempReservation = new ArrayList<Reservation>();
				tempReservation.add(reservation);
				mapReservation.put(channelProductMap.getChannelProductId(),
						tempReservation);
			} else {
				List<Reservation> tempReservation = mapReservation
						.get(channelProductMap.getChannelProductId());
				tempReservation.add(reservation);
				mapReservation.put(channelProductMap.getChannelProductId(),
						tempReservation);
			}
		}
		return mapReservation;
	}

	private Map<BookingConfirmRQ, List<Reservation>> createBookingConfirmationRequest(
			Map<String, List<Reservation>> mapReservation) {
		Map<BookingConfirmRQ, List<Reservation>> mapBookingConfirmation = new HashMap<BookingConfirmRQ, List<Reservation>>();

		for (Entry<String, List<Reservation>> entry : mapReservation.entrySet()) {
			BookingConfirmRQ bookingConfirmRQ = new BookingConfirmRQ();
			BookingConfirmRQ.Authentication authentication = new BookingConfirmRQ.Authentication();
			// set authentication details
			authentication.setUsername(RazorConfig.getValue(
					ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
			authentication.setPassword(RazorConfig.getValue(
					ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
			bookingConfirmRQ.setAuthentication(authentication);
			BookingConfirmRQ.Hotel hotel = new BookingConfirmRQ.Hotel();
			hotel.setId(entry.getKey());
			bookingConfirmRQ.setHotel(hotel);

			for (Reservation reservation : entry.getValue()) {
				BookingConfirmRQ.BookingConfirmNumbers.BookingConfirmNumber bookingConfirmNumber = new BookingConfirmRQ.BookingConfirmNumbers.BookingConfirmNumber();
				bookingConfirmNumber.setBookingID(reservation.getAltid());
				if (State.Cancelled.name().equalsIgnoreCase(
						reservation.getState())) {
					bookingConfirmNumber.setBookingType("Cancel");
				} else {
					bookingConfirmNumber.setBookingType("Book");
				}

				bookingConfirmNumber.setConfirmNumber(reservation.getId());
				bookingConfirmNumber.setConfirmTime(dateTimeFormatter
						.print(new DateTime(reservation.getVersion())));
				BookingConfirmRQ.BookingConfirmNumbers bookingConfirmNumbers=new BookingConfirmRQ.BookingConfirmNumbers();
				bookingConfirmNumbers.getBookingConfirmNumber().add(bookingConfirmNumber);
				bookingConfirmRQ.setBookingConfirmNumbers(bookingConfirmNumbers);
				
			}

			mapBookingConfirmation.put(bookingConfirmRQ, entry.getValue());
		}

		return mapBookingConfirmation;
	}

	private void updateBPReservationAsConfirmed(SqlSession sqlSession,
			List<Reservation> listPersistedReservation) {

		for (Reservation reservation : listPersistedReservation) {
			reservation.setState(State.Confirmed.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			/*ReservationRest.postReservation(reservation,
					RazorConfig.getValue("pos.code"), Constants.NO_XSL);*/
		}

	}

}
