package net.cbtltd.rest.hotelscombined.reservation;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.hotelscombined.HotelsCombinedUtils;
import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRQ;
import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRS;
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

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyy-MM-dd");

	private static final Logger LOG = Logger.getLogger(ReservationUtils.class
			.getName());

	private boolean processReservationWithPayment = false;

	public OTAHotelResNotifRS persistReservations(String posCode,
			OTAHotelResNotifRQ otaHotelResNotifRQ) {
		LOG.info("Inside persistReservations ");
		SqlSession sqlSession = RazorServer.openSession();
		OTAHotelResNotifRS otaHotelResNotifRS = new OTAHotelResNotifRS();
		try {
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(
					ChannelPartnerMapper.class).read(
					RazorConfig.getHotelsCombinedChannelPartnerId());

			// reservation domain object.
			List<Reservation> listReservation = this
					.readHotelsCombinedReservation(otaHotelResNotifRQ,
							channelPartner, sqlSession);

			// Persist the reservations in BP system
			Map<String, Reservation> mapBPReservationStatus = this
					.callBPReservationService(listReservation, posCode);
			Boolean isPersistReservationSuccess = true;

			LOG.info("The following reservations are persisted");
			for (Entry status : mapBPReservationStatus.entrySet()) {

				LOG.info(status.getKey() + " " + status.getValue());
				if (status.getValue() == null) {
					isPersistReservationSuccess = false;
					JAXBContext jaxbContext = JAXBContext
							.newInstance(OTAHotelResNotifRQ.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					StringWriter xmlWriter = new StringWriter();
					jaxbMarshaller.setProperty(
							Marshaller.JAXB_FORMATTED_OUTPUT, true);
					jaxbMarshaller.marshal(otaHotelResNotifRQ, xmlWriter);
					LOG.info("HotelsCombined  Response " + xmlWriter);
					// send mail when Reservations are not successfully
					// persisted
					net.cbtltd.server.EmailService
							.reservationNotPersisted(xmlWriter.toString());
				}
			}

			otaHotelResNotifRS.setEchoToken(HotelsCombinedUtils.getGUID());
			otaHotelResNotifRS.setTimeStamp(HotelsCombinedUtils
					.getCurrentDateTimeInISOFormat());
			if (isPersistReservationSuccess) {
				otaHotelResNotifRS.setSuccess(isPersistReservationSuccess + "");
			} else {
				OTAHotelResNotifRS.Errors.Error error = new OTAHotelResNotifRS.Errors.Error();
				error.setCode("450 Unable to Process");
				error.setType("12 Processing Exception");
				error.setTag("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
		return otaHotelResNotifRS;

	}

	/**
	 * To form List of reservation object from the response of API
	 * 
	 * @param reservations
	 * @param channelPartner
	 * @param sqlSession
	 * @return List of reservation object
	 */
	private List<Reservation> readHotelsCombinedReservation(
			OTAHotelResNotifRQ reservations, ChannelPartner channelPartner,
			SqlSession sqlSession) {
		LOG.info("Inside readBookingComReservationResponse ");
		List<Reservation> listBPReservation = new ArrayList<Reservation>();
		List<ReservationExt> listReservationExt=new ArrayList<ReservationExt>();
		
		for (OTAHotelResNotifRQ.HotelReservations.HotelReservation reservation : reservations
				.getHotelReservations().getHotelReservation()) {
			
		
			Reservation bpReservation = new Reservation();

			// set channel as travel agent and booking id as alt id
			bpReservation.setAltid(String.valueOf(reservation.getUniqueID().getID()));
			bpReservation
					.setAgentid(String.valueOf(channelPartner.getPartyId()));

			// check if this reservation exist in our system
			Reservation existing = sqlSession
					.getMapper(ReservationMapper.class).altreadforchannel(
							bpReservation);
		
			if (existing != null) {
				bpReservation = existing;
			}
			if(reservation.getResGlobalInfo()!=null ){
				
				if(reservation.getResGlobalInfo().getGuarantee()!=null && reservation.getResGlobalInfo().getGuarantee().getGuaranteesAccepted()!=null && reservation.getResGlobalInfo().getGuarantee().getGuaranteesAccepted().getGuaranteeAccepted()!=null && reservation.getResGlobalInfo().getGuarantee().getGuaranteesAccepted().getGuaranteeAccepted().getPaymentCard()!=null){
					OTAHotelResNotifRQ.HotelReservations.HotelReservation.ResGlobalInfo.Guarantee.GuaranteesAccepted.GuaranteeAccepted.PaymentCard paymentCard=	reservation.getResGlobalInfo().getGuarantee().getGuaranteesAccepted().getGuaranteeAccepted().getPaymentCard();
					//set credit card details here
					bpReservation.setCardcode(paymentCard.getSeriesCode());
					bpReservation.setCardholder(paymentCard.getCardHolderName());
					bpReservation.setCardmonth(paymentCard.getExpireDate().substring(0, 2));
					bpReservation.setCardyear(paymentCard.getExpireDate().substring(2, 4));
					bpReservation.setCardType(paymentCard.getCardType());
					bpReservation.setCardnumber(paymentCard.getCardNumber());
				}
				
				
				if(reservation.getResGlobalInfo().getTotal()!=null){
					if (StringUtils.isNotEmpty(reservation.getResGlobalInfo().getTotal().getCurrencyCode())) {
						bpReservation.setCurrency(reservation.getResGlobalInfo().getTotal().getCurrencyCode());
					}
					
					bpReservation.setPrice((double)reservation.getResGlobalInfo().getTotal().getAmountAfterTax());
					bpReservation.setCost((double)reservation.getResGlobalInfo().getTotal().getAmountAfterTax());
				
				}
		}
							
			if (reservation.getResGuests()!= null) {
			for(OTAHotelResNotifRQ.HotelReservations.HotelReservation.ResGuests.ResGuest resGuest:reservation.getResGuests().getResGuest()){
				OTAHotelResNotifRQ.HotelReservations.HotelReservation.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer customer=null;
				if(resGuest.getProfiles()!=null && resGuest.getProfiles().getProfileInfo()!=null && resGuest.getProfiles().getProfileInfo().getProfile()!=null && resGuest.getProfiles().getProfileInfo().getProfile().getCustomer()!=null){
					  customer=resGuest.getProfiles().getProfileInfo().getProfile().getCustomer();
				}
				if("1".equalsIgnoreCase(resGuest.getPrimaryIndicator()) && customer!=null){
					// Set other details of cutomer here
					
					 if (StringUtils.isNotEmpty( customer.getTelephone().getPhoneNumber())) {
						bpReservation.setPhoneNumber( customer.getTelephone().getPhoneNumber());
					}
					 if (StringUtils
								.isNotEmpty(customer.getEmail())) {
							bpReservation.setEmailaddress(customer.getEmail());

						}
					
					 if (StringUtils.isNotEmpty( customer.getAddress().getAddressLine())) {
							bpReservation.setAddrress( customer.getAddress().getAddressLine());

						}
					 
					 if (StringUtils.isNotEmpty(customer.getAddress().getCityName())) {
							bpReservation.setCity(customer.getAddress().getCityName());

						}
					 

						if (StringUtils.isNotEmpty(customer.getAddress().getCountryName().getCode())) {
							bpReservation.setCountry(customer.getAddress().getCountryName().getCode());

						}
						if (customer.getPersonName()!=null) {
							bpReservation.setCustomername(customer.getPersonName().getSurname(), customer.getPersonName().getGivenName());	
						}
						
					
					
				}
				ReservationExt guestName=new ReservationExt();
				guestName.setName(customer.getPersonName().getSurname()+","+ customer.getPersonName().getGivenName());
				guestName.setType("Guest");
				guestName.setAddonType("100");
				listReservationExt.add(guestName);
			}
			
				
			}
			if(reservation.getResGlobalInfo()!=null && reservation.getResGlobalInfo().getSpecialRequests()!=null){
				//capture the special request here
				bpReservation.setNotes(reservation.getResGlobalInfo().getSpecialRequests().getSpecialRequest().getText());
			}
			

			// set arrival date and departure date
			
			if ( reservation.getRoomStays() != null) {
				for(OTAHotelResNotifRQ.HotelReservations.HotelReservation.RoomStays.RoomStay roomStay:reservation.getRoomStays().getRoomStay()){
					if(roomStay.getTimeSpan()!=null && roomStay.getTimeSpan().getStart()!=null &&  roomStay.getTimeSpan().getEnd()!=null){
						bpReservation.setFromdate(dateTimeFormatter.parseDateTime(roomStay.getTimeSpan().getStart()).toDate());
						bpReservation.setTodate(dateTimeFormatter.parseDateTime(roomStay.getTimeSpan().getEnd()).toDate());
					}
					if(roomStay.getGuestCounts()!=null){
						for(OTAHotelResNotifRQ.HotelReservations.HotelReservation.RoomStays.RoomStay.GuestCounts.GuestCount guestCount:roomStay.getGuestCounts().getGuestCount()){
							if("10".equalsIgnoreCase(guestCount.getAgeQualifyingCode())){
								bpReservation.setAdult(Integer.parseInt(guestCount.getCount()));
							}else{
								bpReservation.setChild(Integer.parseInt(guestCount.getCount()));
							}
						}
					}
					
					// Get the BP Hotel id from the mapping table and set the hotel id
					// here
					Product bpProduct = null;
					 bpProduct = ChannelProductService.getInstance()
									.readProductFromChannelProductMapper(sqlSession,
											RazorConfig.getHotelsCombinedChannelPartnerId(),
											roomStay.getBasicPropertyInfo().getHotelCode(),
											roomStay.getRoomRates().getRoomRate().getRoomTypeCode());
					 bpReservation.setProductid(bpProduct.getId());
				}
									
				}
				bpReservation.setListReservationExt(listReservationExt);
				
				if ("Commit".equalsIgnoreCase(reservation.getResStatus())) {
					bpReservation.setState(State.Confirmed.name());
				} else if ("Cancel".equalsIgnoreCase(reservation.getResStatus())) {
					bpReservation.setState(State.Cancelled.name());
				} else {
					bpReservation.setState(State.Confirmed.name());
				}

				listBPReservation.add(bpReservation);
			
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
	private Map<String, Reservation> callBPReservationService(
			List<Reservation> listReservation, String posCode) {

		LOG.info(" Inside callBPReservationService " + listReservation.size());
		Reservation bpreservation = null;
		SqlSession sqlSession = RazorServer.openSession();
		Map<String, Reservation> mapBPReservationStatus = new HashMap<String, Reservation>();

		for (Reservation reservation : listReservation) {
			LOG.info("Processing reservation id " + reservation.getAltid());
			LOG.info("Processing reservation status " + reservation.getState());
			if (processReservationWithPayment) {
				reservation.setCardholder("BookingPal");
				reservation.setCardType("MasterCard");
				reservation.setCardnumber("5346330641608164");
				reservation.setCardmonth("01");
				reservation.setCardyear("2017");
				reservation.setCardcode("123");

			}
			try {
				if (StringUtils.isEmpty(posCode)) {
					posCode = RazorConfig.getValue("pos.code");
				}
			 if (State.Confirmed.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.hasCard()) {
					System.out.println("Inside createReservationAndPayment");
					ReservationResponse reservationResponse = ReservationRest
							.createReservationAndPayment(reservation, posCode);
					if (!reservationResponse.isError() && 
							reservationResponse.getReservationInfo() != null){
						String resId = reservationResponse.getReservationInfo().getId();
						bpreservation = sqlSession.getMapper(ReservationMapper.class).read(resId);
					}
					
				} else if (State.Confirmed.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.noCard()) {
					System.out.println("Inside postReservation");
					bpreservation = ReservationRest.postReservation(
							reservation, posCode, Constants.NO_XSL);
				} else if (State.Cancelled.name().equalsIgnoreCase(
						reservation.getState())) {
					System.out.println("Inisde Cancel Reservation");
					ReservationRest.cancelReservation(reservation.getId());

				} else {

					bpreservation = ReservationRest.postReservation(
							reservation, posCode, Constants.NO_XSL);

				}
			} catch (Exception e) {
				LOG.error("Error while calling the Reservation service", e);
			}
			mapBPReservationStatus.put(reservation.getAltid(), bpreservation);

		}
		return mapBPReservationStatus;
	}

}
