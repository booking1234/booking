package net.cbtltd.rest.expedia.booking.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.expedia.booking.domain.BookingRetrievalRQ;
import net.cbtltd.rest.expedia.booking.domain.BookingRetrievalRS;
import net.cbtltd.rest.expedia.utils.ExpediaUtils;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.ReservationExt;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class ReservationUtils {

	private static final Logger LOG = Logger.getLogger(ReservationUtils.class
			.getName());
	private boolean processReservationWithPayment = false;
	private boolean processSpecificConfirmationId = false;
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
	private static DateTimeFormatter dateTimeFormatter1 = DateTimeFormat
			.forPattern("yyy-MM-dd");

	/**
	 * To fetch all Reservations information from Expedia and update it our DB
	 */
	public void fetchAndUpdateAllReservations() {

		// prepare the request
		// retrieve all the properties for which we want to retireve
		// reservation
		BookingRetrievalRQ bookingRetrievalRQ = this.createReservationRequest(null,null,null);
		this.processExpediaBookingRetrivalResponse(bookingRetrievalRQ);

	}

	public void fetchAndUpdateReservationsBasedOnHotelId(String hotelId) {

		// prepare the request
		// retrieve all the properties for which we want to retireve
		// reservation
		BookingRetrievalRQ bookingRetrievalRQ = this.createReservationRequest(hotelId,null,null);
		this.processExpediaBookingRetrivalResponse(bookingRetrievalRQ);

	}

	public void fetchAndUpdateReservationsBasedOnBookingId(String bookingId) {

		// prepare the request
		// retrieve all the properties for which we want to retrive
		// reservation
		BookingRetrievalRQ bookingRetrievalRQ = this.createReservationRequest(null,bookingId,null);
		this.processExpediaBookingRetrivalResponse(bookingRetrievalRQ);

	}

	public void fetchAndUpdateReservationsBasedOnDays(Integer no_of_days) {

		// prepare the request
		// retrieve all the properties for which we want to retireve
		// reservation
		BookingRetrievalRQ bookingRetrievalRQ = this.createReservationRequest(null,null,no_of_days);
		
		this.processExpediaBookingRetrivalResponse(bookingRetrievalRQ);

	}

	private void processExpediaBookingRetrivalResponse(
			BookingRetrievalRQ bookingRetrievalRQ) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(
					ChannelPartnerMapper.class).read(
					RazorConfig.getExpediaChannelPartnerId());
			// call the Expedia BR API
			String expediaBookingRetrivalResponse = this
					.callExpediaBookingRetrivalAPI(bookingRetrievalRQ);

			BookingRetrievalRS bookingRetrievalRS = this
					.prepareReservationsFromXMLResponse(expediaBookingRetrivalResponse);
			// read the BookingCom reservation response and prepare BP
			// reservation domain object.
			List<Reservation> listReservation = this.readBookingRetrievalRS(
					bookingRetrievalRS, channelPartner, sqlSession);

			// call webservice to persist the BP reservation domain objects
			List<Reservation> listPersistedReservation = this
					.callBPReservationService(listReservation);

			LOG.info("The following reservations are persisted");
			for (Reservation reservation : listPersistedReservation) {
				LOG.info(reservation.getId());
				
				
				
			}
			
			// only when the reservation is persited sucessfully we get the
			// reservation id.Else reservation id will be empty
			if (listPersistedReservation == null
					|| listPersistedReservation.isEmpty()) {
				net.cbtltd.server.EmailService
						.reservationNotPersisted(expediaBookingRetrivalResponse);
			}
			
		//Confirm booking here
			LOG.info("@@@@@Starting to Confirm bookings@@@@@");
			BookingConfirmationUtils bookingConfirmationUtils=new BookingConfirmationUtils();
			bookingConfirmationUtils.updateBookingConfirmationToExpedia(sqlSession, listPersistedReservation);
			LOG.info("@@@@@Ending to Confirm bookings@@@@@");

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
	}

	

	/*private BookingRetrievalRQ createReservationRequest() {
		BookingRetrievalRQ bookingRetrievalRQ = new BookingRetrievalRQ();
		BookingRetrievalRQ.Authentication authentication = new BookingRetrievalRQ.Authentication();
		authentication.setUsername(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
		authentication.setPassword(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
		bookingRetrievalRQ.setAuthentication(authentication);
		return bookingRetrievalRQ;

	}
	
	
	private BookingRetrievalRQ createReservationRequest(String hotelId) {
		BookingRetrievalRQ bookingRetrievalRQ = new BookingRetrievalRQ();
		BookingRetrievalRQ.Authentication authentication = new BookingRetrievalRQ.Authentication();
		authentication.setUsername(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
		authentication.setPassword(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
		bookingRetrievalRQ.setAuthentication(authentication);
		BookingRetrievalRQ.Hotel hotel = new BookingRetrievalRQ.Hotel();
		hotel.setId(hotelId);
		bookingRetrievalRQ.setHotel(hotel);
		return bookingRetrievalRQ;

	}*/

	private BookingRetrievalRQ createReservationRequest(String hotelId,
			String bookingId, Integer no_of_pastDays) {
		BookingRetrievalRQ bookingRetrievalRQ = new BookingRetrievalRQ();
		BookingRetrievalRQ.Authentication authentication = new BookingRetrievalRQ.Authentication();
		authentication.setUsername(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
		authentication.setPassword(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
		BookingRetrievalRQ.ParamSet paramSet = null;
		bookingRetrievalRQ.setAuthentication(authentication);
		if (StringUtils.isNotEmpty(hotelId)) {
			BookingRetrievalRQ.Hotel hotel = new BookingRetrievalRQ.Hotel();
			hotel.setId(hotelId);
			bookingRetrievalRQ.setHotel(hotel);
		}
		if (StringUtils.isNotEmpty(bookingId)) {

			paramSet = new BookingRetrievalRQ.ParamSet();

			BookingRetrievalRQ.ParamSet.Booking booking = new BookingRetrievalRQ.ParamSet.Booking();
			booking.setId(bookingId);
			paramSet.setBooking(booking);
		}
		if (no_of_pastDays != null) {
			if (paramSet == null) {
				paramSet = new BookingRetrievalRQ.ParamSet();
			}
			paramSet.setNbDaysInPast(no_of_pastDays);
		}
		if (paramSet != null) {
			bookingRetrievalRQ.setParamSet(paramSet);
		}

		return bookingRetrievalRQ;

	}

	private String callExpediaBookingRetrivalAPI(
			BookingRetrievalRQ bookingRetrievalRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(BookingRetrievalRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(bookingRetrievalRQ, xmlWriter);
		LOG.info("Expedia Booking Retrival API Request " + xmlWriter);
		return ExpediaUtils
				.callExpediaAPI(
						xmlWriter.toString(),
						RazorConfig
								.getValue(ExpediaUtils.EXPEDIA_BR_URL,
										"https://simulator.expediaquickconnect.com/connect/br"),
						false);

	}
	
	

	
	/**
	 * To form Reservations object from XML response
	 * 
	 * @param reservationResponse
	 * @return Reservations
	 * @throws Exception
	 */
	private BookingRetrievalRS prepareReservationsFromXMLResponse(
			String expediaBookingRetrivalResponse) throws Exception {
		LOG.info("Expedia Response" + expediaBookingRetrivalResponse);
		expediaBookingRetrivalResponse=expediaBookingRetrivalResponse.replace("xmlns=\"http://www.expediaconnect.com/EQC/BR/2014/01\"", "");
		
		StringReader xmlReader = new StringReader(
				expediaBookingRetrivalResponse.toString());
		JAXBContext jaxbContext = JAXBContext
				.newInstance(BookingRetrievalRS.class);
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		// jaxbUnMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		return (BookingRetrievalRS) jaxbUnMarshaller.unmarshal(xmlReader);
	}
	
	/**
	 * To form List of reservation object from the response of API
	 * 
	 * @param reservations
	 * @param channelPartner
	 * @param sqlSession
	 * @return List of reservation object
	 */
	private List<Reservation> readBookingRetrievalRS(
			BookingRetrievalRS bookingRetrievalRS,
			ChannelPartner channelPartner, SqlSession sqlSession) {
		LOG.info("Inside readBookingRetrievalRS "+channelPartner.getPartyId());
		List<Reservation> listBPReservation = new ArrayList<Reservation>();
		if(bookingRetrievalRS!=null && bookingRetrievalRS.getBookings()!=null && bookingRetrievalRS.getBookings().getBooking()!=null && bookingRetrievalRS.getBookings().getBooking().size()>0){
			for(BookingRetrievalRS.Bookings.Booking expediaBooking:bookingRetrievalRS.getBookings().getBooking()){
			//if(expediaBooking.getId().intValue()==1){
				Reservation bpReservation = new Reservation();
				List<ReservationExt> listReservationExt=new ArrayList<ReservationExt>();
				// set channel as travel agent and booking id as alt id
				bpReservation.setAltid(String.valueOf(expediaBooking.getId()));
				bpReservation
						.setAgentid(String.valueOf(channelPartner.getPartyId()));
				
				

				// check if this reservation exist in our system
				Reservation existing = sqlSession
						.getMapper(ReservationMapper.class).altreadforchannel(
								bpReservation);
				System.out.println("expediaBooking.getId() "+expediaBooking.getId());
				System.out.println("channelPartner.getPartyId() "+channelPartner.getPartyId());
			System.out.println("existing "+existing);
				if (existing != null) {
					bpReservation = existing;
				}
				
				if ("book".equalsIgnoreCase(expediaBooking.getType()) || "modify".equalsIgnoreCase(expediaBooking.getType())) {
					bpReservation.setState(State.Reserved.name());
				} else if ("cancel".equalsIgnoreCase(expediaBooking.getType())) {
					bpReservation.setState(State.Cancelled.name());
				} else {
					bpReservation.setState(State.Initial.name());
				}
				bpReservation.setAgentid(String.valueOf(channelPartner.getPartyId()));
				bpReservation.setAltid(String.valueOf(expediaBooking.getId()));
				
				String hotelId=expediaBooking.getHotel().getId()+"";
				//extract Details from RoomStay tag
				if(expediaBooking.getRoomStay()!=null){
					String roomTypeId=expediaBooking.getRoomStay().getRoomTypeID();
					if(expediaBooking.getRoomStay().getStayDate()!=null){
						System.out.println("expediaBooking.getRoomStay().getStayDate().getArrival() "+expediaBooking.getRoomStay().getStayDate().getArrival());
						System.out.println("expediaBooking.getRoomStay().getStayDate().getDeparture() "+expediaBooking.getRoomStay().getStayDate().getDeparture());
						bpReservation.setFromdate(dateTimeFormatter1.parseDateTime(expediaBooking.getRoomStay().getStayDate().getArrival()).toDate());
						bpReservation.setTodate(dateTimeFormatter1.parseDateTime(expediaBooking.getRoomStay().getStayDate().getDeparture()).toDate());
					
					
					}
					if(expediaBooking.getRoomStay().getGuestCount()!=null){
						bpReservation.setChild(expediaBooking.getRoomStay().getGuestCount().getChildCount());
						bpReservation.setAdult(expediaBooking.getRoomStay().getGuestCount().getAdult());
											
					}
					Product bpProduct = ChannelProductService.getInstance()
							.readProductFromChannelProductMapper(sqlSession,
									RazorConfig.getBookingChannelPartnerId(),
									hotelId + "",
									roomTypeId + "");
					if(bpProduct!=null){
						bpReservation.setProduct(bpProduct);
						bpReservation.setProductid(bpProduct.getId());
					}else{
						//SN:Hardcoded for testing purpose since the hotel id returned by them will not be available in our DB
						bpReservation.setProductid("121");
					}
					
					
					if(expediaBooking.getRoomStay().getTotal()!=null){
						BookingRetrievalRS.Bookings.Booking.RoomStay.Total total=expediaBooking.getRoomStay().getTotal();
						bpReservation.setPrice(total.getAmountAfterTaxes().doubleValue());
						bpReservation.setCost(total.getAmountAfterTaxes().doubleValue());
						bpReservation.setCurrency(total.getCurrency());
						
					}
					//Get Card information
					if(expediaBooking.getRoomStay().getPaymentCard()!=null){
						BookingRetrievalRS.Bookings.Booking.RoomStay.PaymentCard paymentCard=expediaBooking.getRoomStay().getPaymentCard();
						String mmyy=paymentCard.getExpireDate();
						bpReservation.setCardnumber(paymentCard.getCardNumber());
						bpReservation.setCardcode(paymentCard.getSeriesCode());
						
						bpReservation.setCardmonth(mmyy.substring(0, 2));
						bpReservation.setCardyear(mmyy.substring(2, 4));
						if("VI".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("VISA");
						}else if("MC".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("Master Card");
						}else if("AX".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("American Express");
						}else if("DS".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("Discover Card");
						}else if("CA".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("Master Card");
						}else if("DN".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("DINES CLUB");
						}else if("JC".equalsIgnoreCase(paymentCard.getCardCode())){
							bpReservation.setCardType("JCB");
						}
						
						if(paymentCard.getCardHolder()!=null){
							bpReservation.setCardholder(paymentCard.getCardHolder().getName());
							bpReservation.setCity(paymentCard.getCardHolder().getCity());
							bpReservation.setCountry(paymentCard.getCardHolder().getCountry());
							bpReservation.setAddrress(paymentCard.getCardHolder().getAddress());
													
						}
						
						
					}
					
				}
				
				//extract details from PrimaryGuest Tag
				if(expediaBooking.getPrimaryGuest()!=null){
					BookingRetrievalRS.Bookings.Booking.PrimaryGuest primaryGuest=expediaBooking.getPrimaryGuest();
					bpReservation.setCustomername(primaryGuest.getName().getGivenName(), primaryGuest.getName().getSurname());
					ReservationExt guestName=new ReservationExt();
					guestName.setName(primaryGuest.getName().getGivenName()+primaryGuest.getName().getSurname());
					guestName.setType("Guest");
					guestName.setAddonType("100");
					listReservationExt.add(guestName);
					
					bpReservation.setPhoneNumber(expediaBooking.getPrimaryGuest().getPhone().getCountryCode()+"-"+expediaBooking.getPrimaryGuest().getPhone().getCityAreaCode()+"-"+expediaBooking.getPrimaryGuest().getPhone().getNumber()+"-"+expediaBooking.getPrimaryGuest().getPhone().getExtension());
					bpReservation.setEmailaddress(expediaBooking.getPrimaryGuest().getEmail());
					
				}
				StringBuilder notesBuilder=new StringBuilder();
				
				if(expediaBooking.getRewardProgram()!=null){
				for(BookingRetrievalRS.Bookings.Booking.RewardProgram rewardProgram:expediaBooking.getRewardProgram()){
					notesBuilder.append(" Reward Code:"+rewardProgram.getCode());
					notesBuilder.append(" Reward Number: "+rewardProgram.getNumber());
					ReservationExt rewardPgm=new ReservationExt();
					rewardPgm.setName(rewardProgram.getNumber());
					rewardPgm.setType("Reward Program");
					rewardPgm.setAddonType(rewardProgram.getCode());
					listReservationExt.add(rewardPgm);
				}
				}
				if(expediaBooking.getSpecialRequest()!=null){
					for(BookingRetrievalRS.Bookings.Booking.SpecialRequest specialReq:expediaBooking.getSpecialRequest()){
						notesBuilder.append(" specialReq Code:"+specialReq.getCode());
						notesBuilder.append(" specialReq Number: "+specialReq.getValue());
						ReservationExt splRequest=new ReservationExt();
						splRequest.setName(specialReq.getValue());
						splRequest.setType("SpecialRequest");
						splRequest.setAddonType(specialReq.getCode());
						listReservationExt.add(splRequest);
					}
				}
				bpReservation.setNotes(notesBuilder.toString());
				
				//Build Reservation Extension here
				bpReservation.setListReservationExt(listReservationExt);
				listBPReservation.add(bpReservation);
			//}
			}
		}
		return listBPReservation;

	}

	/**
	 * To call Booking Pal service and load some more values for each of
	 * reservation objects
	 * 
	 * @param listReservation
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private List<Reservation> callBPReservationService(
			List<Reservation> listReservation) {

		LOG.info(" Inside callBPReservationService " + listReservation.size());
		Reservation bpreservation = null;
		SqlSession sqlSession = RazorServer.openSession();
		List<Reservation> listBPReservation = new ArrayList<Reservation>();
		for (Reservation reservation : listReservation) {
			LOG.info("Processing reservation id " + reservation.getAltid());
			if (processReservationWithPayment) {
				reservation.setCardholder("BookingPal");
				reservation.setCardType("MasterCard");
				reservation.setCardnumber("5346330641608164");
				reservation.setCardmonth("01");
				reservation.setCardyear("2017");
				reservation.setCardcode("123");

			}
			try {
				if (State.Confirmed.name().equalsIgnoreCase(
						reservation.getState())) {

					bpreservation = ReservationRest.postReservation(
							reservation, RazorConfig.getValue("pos.code","5"),
							Constants.NO_XSL);
				} else if (State.Reserved.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.hasCard()) {

					ReservationResponse reservationResponse = ReservationRest
							.createReservationAndPayment(reservation,
									RazorConfig.getValue("pos.code","5"));
					if (!reservationResponse.isError() && 
							reservationResponse.getReservationInfo() != null){
						String resId = reservationResponse.getReservationInfo().getId();
						bpreservation = sqlSession.getMapper(ReservationMapper.class).read(resId);
					}
					
				} else if (State.Reserved.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.noCard()) {

					bpreservation = ReservationRest.postReservation(
							reservation, RazorConfig.getValue("pos.code","5"),
							Constants.NO_XSL);
				} else if (State.Cancelled.name().equalsIgnoreCase(
						reservation.getState())) {
					System.out.println("Inisde Cancel Reservation");
					ReservationRest.cancelReservation(reservation.getId());

				} else {

					bpreservation = ReservationRest.postReservation(
							reservation, RazorConfig.getValue("pos.code","5"),
							Constants.NO_XSL);

				}
			} catch (Exception e) {
				LOG.error("Error while calling the Reservation service", e);
			}
			listBPReservation.add(bpreservation);

		}
		return listBPReservation;
	}

	public boolean isProcessReservationWithPayment() {
		return processReservationWithPayment;
	}

	public void setProcessReservationWithPayment(
			boolean processReservationWithPayment) {
		this.processReservationWithPayment = processReservationWithPayment;
	}

	public boolean isProcessSpecificConfirmationId() {
		return processSpecificConfirmationId;
	}

	public void setProcessSpecificConfirmationId(
			boolean processSpecificConfirmationId) {
		this.processSpecificConfirmationId = processSpecificConfirmationId;
	}

}
