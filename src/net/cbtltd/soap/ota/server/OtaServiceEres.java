/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.product.ProductValue;
import net.cbtltd.soap.ota.OtaService;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType.AvailRequestSegment;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate;
import net.cbtltd.soap.ota.server.BookingRulesType.BookingRule;
import net.cbtltd.soap.ota.server.GuestCountType.GuestCount;
import net.cbtltd.soap.ota.server.HotelReservationIDsType.HotelReservationID;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.HotelRef;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS.RoomStays;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS.RoomStays.RoomStay;
import net.cbtltd.soap.ota.server.ProfilesType.ProfileInfo;
import net.cbtltd.soap.ota.server.RatePlanCandidatesType.RatePlanCandidate;
import net.cbtltd.soap.ota.server.RateType.Rate;
import net.cbtltd.soap.ota.server.RequiredPaymentsType.GuaranteePayment;
import net.cbtltd.soap.ota.server.RequiredPaymentsType.GuaranteePayment.Deadline;
import net.cbtltd.soap.ota.server.ResGuestsType.ResGuest;
import net.cbtltd.soap.ota.server.RoomStayType.RoomRates;
import net.cbtltd.soap.ota.server.RoomStayType.RoomRates.RoomRate;

import org.apache.ibatis.session.SqlSession;

/**
 * The Class OtaServiceEres services OTA requests for the eRes system.
 * @see http://www.eres-dev.com
 */
public class OtaServiceEres
extends OtaService {

	private final static BigDecimal VER_111 = new BigDecimal("1.11");
	private final static BigDecimal VER_115 = new BigDecimal("1.15");
	private final static String ERES_BOOK = "Book";
	private final static String ERES_ERSP = "22";
	private final static String ERES_UID_TYPE = "10";

	/*
	 * Gets the party having the specified requestor instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the the specified requestor instance.
	 * @return the party.
	 */
	private static Party getParty(SqlSession sqlSession, POSType pos) {
		SourceType source = getSource(pos, 0);
		if (source.getRequestorID() == null
				|| source.getRequestorID().getInstance() == null
				|| source.getRequestorID().getInstance().isEmpty()
				) {throw new OtaException("RequestorInstance", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		String partyid =  source.getRequestorID().getInstance(); //TODO: transfrom requestor instance to partyid
		//eRes: You can supply us with the ID and we will then map it on our system to ensure you are receiving the correct agent mapping code 
		return getParty(sqlSession, partyid);
	}

	/*
	 * Gets the available request segments.
	 *
	 * @param rq the hotel availability request.
	 * @return the available request segments
	 */
	private static final List<AvailRequestSegment> getAvailRequestSegments(OTAHotelAvailRQ rq) {
		if (rq.getAvailRequestSegments() == null
				|| rq.getAvailRequestSegments().getAvailRequestSegment() == null
				|| rq.getAvailRequestSegments().getAvailRequestSegment().isEmpty()
				) {throw new OtaException("AvailRequestSegment", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		else {return rq.getAvailRequestSegments().getAvailRequestSegment();}
	}

	/*
	 * Gets the reservation ID value.
	 *
	 * @param rq the hotel availability request.
	 * @param index the index of the reservation ID value.
	 * @return the reservation ID value.
	 */
	private static final String getResIDValue(OTAHotelAvailRQ rq, int index) {
		if (rq.getHotelReservationIDs() == null
				|| rq.getHotelReservationIDs().getHotelReservationID() == null
				|| rq.getHotelReservationIDs().getHotelReservationID().isEmpty()
				|| rq.getHotelReservationIDs().getHotelReservationID().get(index).getResIDValue() == null
				|| rq.getHotelReservationIDs().getHotelReservationID().get(index).getResIDValue().isEmpty()
				) {throw new OtaException("ResIDValue", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		else {return rq.getHotelReservationIDs().getHotelReservationID().get(index).getResIDValue();}
	}

	/**
	 * Creates an OTAHotelAvailRS response to the specified eRes OTAHotelAvailRQ request.
	 *
	 * @param rq OTAHotelAvailRQ request for availability of specified properties and dates.
	 * @return OTAHotelAvailRS hotel availability response.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.1
	 */
	public static OTAHotelAvailRS hotelAvailRS (OTAHotelAvailRQ rq) {
		LOG.debug(rq + "\n");
		OTAHotelAvailRS rs = OF.createOTAHotelAvailRS();
		rs.setEchoToken(getRequestorID(rq.getPOS(), 0));
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_111);
		RoomStays roomStays = OF.createOTAHotelAvailRSRoomStays();
		rs.setRoomStays(roomStays);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			if (!getRequestorType(rq.getPOS(), 0).equalsIgnoreCase(ERES_ERSP)) {throw new OtaException("RequestorType", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			getParty(sqlSession, rq.getPOS());

			if (rq.isIsModify()) {
				//-----------------------------------------------
				//Get rates for existing reservation
				//-----------------------------------------------
				String reservationid = getResIDValue(rq, 0);
				//TODO: eRes If true, it will indicate that this request is for an existing reservation so the response will return rates for the reserved roomtype even if no rooms are available anymore.
				Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
				if (reservation == null) {throw new OtaException("ReservationID", Attribute.ERR_INVALID_RESERVATION_ID, Attribute.EWT_INVALID_RESERVATION_ID);}

				RoomStay roomStay = OF.createOTAHotelAvailRSRoomStaysRoomStay();
				roomStays.getRoomStay().add(roomStay);
				RoomTypeType roomTypeType = OF.createRoomTypeType();
				roomStay.getRoomTypes().getRoomType().add(roomTypeType);
				roomTypeType.setRoomTypeCode(reservation.getProductid());
				RoomRates roomRates = OF.createRoomStayTypeRoomRates();
				roomStay.setRoomRates(roomRates);
				RoomRate roomRate = OF.createRoomStayTypeRoomRatesRoomRate();
				roomRates.getRoomRate().add(roomRate);
				roomRate.setEffectiveDate(getXGC(reservation.getFromdate()));
				roomRate.setRoomTypeCode(reservation.getProductid());
				roomRate.setNumberOfUnits(1);
				RateType rates = OF.createRateType();
				roomRate.setRates(rates);
				Rate rate = OF.createRateTypeRate();
				rates.getRate().add(rate);

				rate.setUnitMultiplier(getBigInteger(1));
				rate.setRateTimeUnit(TimeUnitType.fromValue("Day"));

				TotalType base = OF.createTotalType();
				rate.setBase(base);
				base.setCurrencyCode(reservation.getCurrency());

				TotalType total = OF.createTotalType(); // minimum booking value
				rate.setTotal(total);
				total.setCurrencyCode(reservation.getCurrency());
				ReservationService.computePrice(sqlSession, reservation, null);
//				Double price = ReservationService.getPrice(sqlSession, reservation, false);
//				Double quote = ReservationService.getPrice(sqlSession, reservation, true);
//				LOG.debug("quote " + quote + " " + reservation);
				total.setAmountAfterTax(getBigDecimal(reservation.getQuote()));
				base.setAmountAfterTax(getBigDecimal(reservation.getQuote()/reservation.getDuration(Time.DAY)));

				roomStay.setDepositPayments(getRequiredPayments(Double.valueOf(reservation.getDeposit()), reservation.getCurrency()));
			}
			else {
				//-----------------------------------------------
				//Get rates and availability for date range
				//-----------------------------------------------
				String bookingChannelType = getBookingChannelType(rq.getPOS(), 0);

				for (AvailRequestSegment availRequestSegment : getAvailRequestSegments(rq)) {

					DateTimeSpanType stayDateRange = getDateTimeSpanType(availRequestSegment.getStayDateRange());

					for (RatePlanCandidate ratePlanCandidate : availRequestSegment.getRatePlanCandidates().getRatePlanCandidate()) {
						String ratePlanCode = ratePlanCandidate.getRatePlanCode();
					} //for (RatePlanCandidate ratePlanCandidate : availRequestSegment.getRatePlanCandidates().getRatePlanCandidate()) {


					for (RoomStayCandidate roomStayCandidate : availRequestSegment.getRoomStayCandidates().getRoomStayCandidate()) {
						String roomType = roomStayCandidate.getRoomType();
						Integer roomQuantity = roomStayCandidate.getQuantity();

						if (roomStayCandidate.getGuestCounts() == null
								|| roomStayCandidate.getGuestCounts().getGuestCount() == null
								|| roomStayCandidate.getGuestCounts().getGuestCount().isEmpty()
								) {throw new OtaException("GuestCount", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

						boolean isPerRoom = roomStayCandidate.getGuestCounts().isIsPerRoom();
						List<GuestCount> guestCounts = roomStayCandidate.getGuestCounts().getGuestCount();
						int[] counts = new int[guestCounts.size()];
						int[] ages = new int[guestCounts.size()];
						String[] ageQualifyingCodes = new String[guestCounts.size()];
						int index = 0;
						for (GuestCount guestCount : guestCounts) {
							counts[index] = guestCount.getCount();
							ages[index] = guestCount.getAge();
							ageQualifyingCodes[index] = guestCount.getAgeQualifyingCode();
							index++;
						}

						if (availRequestSegment.getHotelSearchCriteria() == null
								|| availRequestSegment.getHotelSearchCriteria().getCriterion() == null
								|| availRequestSegment.getHotelSearchCriteria().getCriterion().isEmpty()
								) {throw new OtaException("HotelSearchCriteria", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
						//TODO: If this attribute does not exist, then all available properties will be returned

						for (HotelSearchCriteriaType.Criterion criterion : availRequestSegment.getHotelSearchCriteria().getCriterion()) {
							if (criterion == null
									|| criterion.getHotelRef() == null
									|| criterion.getHotelRef().isEmpty()
									) {throw new OtaException("HotelRefs", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

							List<HotelRef> hotelRefs = criterion.getHotelRef();
							StringBuilder sb = new StringBuilder();
							for (HotelRef hotelRef : hotelRefs) {
								sb.append(hotelRef.getHotelCode()).append(",");
							}
							if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
							else {sb.append("'-1'");}
							String productid = sb.toString();

							int maxResponses = (rq.getMaxResponses() == null)? Integer.MAX_VALUE : getInteger(rq.getMaxResponses());
							int i = 0;

							Reservation reservation = new Reservation();
							reservation.setProductid(productid);
							reservation.setFromdate(getDate(stayDateRange.getStart()));
							reservation.setTodate(getDate(stayDateRange.getEnd()));

							ArrayList<OtaRoomStay> stays;

							//-----------------------------------------------
							//If summary return rates for each day, else for stay
							//-----------------------------------------------
							boolean isSummary = rq.isSummaryOnly() == null || rq.isSummaryOnly();
							if (isSummary) {stays = sqlSession.getMapper(ReservationMapper.class).hotelavailabilities(reservation);} // summary availability
							else {stays = sqlSession.getMapper(ReservationMapper.class).hotelavailability(reservation);}

							LOG.debug("\nstays " + stays);

							if (stays == null || stays.isEmpty()) {throw new OtaException("NoneAvailable", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

							for (OtaRoomStay stay : stays) {

								RoomStay roomStay = OF.createOTAHotelAvailRSRoomStaysRoomStay();
								roomStays.getRoomStay().add(roomStay);
								RoomTypeType roomTypeType = OF.createRoomTypeType();
								roomStay.getRoomTypes().getRoomType().add(roomTypeType);
								roomTypeType.setRoomTypeCode(stay.getProductid());

								//TODO:								ExtraRoomTypeInfo extraRoomTypeInfo = OF.createExtraRoomTypeInfo();
								//								roomTypeType.getTPAExtensions().getAny().add(extraRoomTypeInfo);
								//								extraRoomTypeInfo.setMaxAdults(stay.getMaxAdults());
								//								extraRoomTypeInfo.setMaxKids(stay.getMaxKids());
								//								extraRoomTypeInfo.setMaxPax(stay.getMaxPax());

								//								BasicPropertyInfoType basicPropertyInfo = OF.createBasicPropertyInfoType();
								//								roomStay.setBasicPropertyInfo(basicPropertyInfo);
								//								basicPropertyInfo.setHotelCodeContext(CONTEXT);
								//								basicPropertyInfo.setHotelCode(stay.getProductid());
								//								basicPropertyInfo.setHotelName(stay.getHotelname());
								//								basicPropertyInfo.setAreaID(stay.getLocationid());
								//								basicPropertyInfo.setChainCode(stay.getHotelgroup());
								//								basicPropertyInfo.setPosition(getPosition(stay.getLatitude(), stay.getLongitude(), stay.getAltitude()));

								//								DateTimeSpanType timeSpan = OF.createDateTimeSpanType();
								//								roomStay.setTimeSpan(timeSpan);
								//								timeSpan.setStart(getXGC(stay.getDate()).toXMLFormat());
								//								timeSpan.setEnd(getXGC(stay.getTodate()).toXMLFormat());

								RoomRates roomRates = OF.createRoomStayTypeRoomRates();
								roomStay.setRoomRates(roomRates);
								RoomRate roomRate = OF.createRoomStayTypeRoomRatesRoomRate();
								roomRates.getRoomRate().add(roomRate);
								roomRate.setEffectiveDate(getXGC(stay.getDate()));
								roomRate.setRoomTypeCode(stay.getRoomtype());
								roomRate.setNumberOfUnits(1);
								RateType rates = OF.createRateType();
								roomRate.setRates(rates);
								Rate rate = OF.createRateTypeRate();
								rates.getRate().add(rate);

								rate.setUnitMultiplier(getBigInteger(1));
								rate.setRateTimeUnit(TimeUnitType.fromValue("Day"));

								TotalType base = OF.createTotalType();
								rate.setBase(base);
								base.setCurrencyCode(stay.getCurrency());

								TotalType total = OF.createTotalType(); // minimum booking value
								rate.setTotal(total);
								total.setCurrencyCode(stay.getCurrency());

								if (isSummary) {
									base.setAmountAfterTax(getBigDecimal(stay.getValue()));
									total.setAmountAfterTax(getBigDecimal(stay.getTotal()));
								}
								else {
									reservation.setOrganizationid(stay.getHotelgroup());
									reservation.setProductid(stay.getProductid());
									reservation.setUnit(Unit.DAY);
									ReservationService.computePrice(sqlSession, reservation, null);
									total.setAmountAfterTax(getBigDecimal(reservation.getQuote()));
									base.setAmountAfterTax(getBigDecimal(reservation.getQuote()/stay.getDuration()));
								}

								roomStay.setDepositPayments(getRequiredPayments(Double.valueOf(reservation.getDeposit()), reservation.getCurrency()));

								if (i++ > maxResponses) {break;}
							}
						} //for (HotelSearchCriteriaType.Criterion criterion : availRequestSegment.getHotelSearchCriteria().getCriterion()) {

					} //for (RoomStayCandidate roomStayCandidate : availRequestSegment.getRoomStayCandidates().getRoomStayCandidate()) {

				} //for (AvailRequestSegment availRequestSegment : getAvailRequestSegments(rq)) {

			} //if (isIsModify) {
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		} 
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {MonitorService.log(x);}
		finally {sqlSession.close();}
		LOG.debug("\nHotelAvailRS.result " + rs);
		return rs;
	}

	/*
	 * Gets the required payments terms.
	 *
	 * @param deposit the deposit required.
	 * @param currency the currency of the deposit.
	 * @return the required payments terms.
	 */
	private static RequiredPaymentsType getRequiredPayments(Double deposit, String currency) {
		RequiredPaymentsType requiredPayments = OF.createRequiredPaymentsType();
		GuaranteePayment guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
		requiredPayments.getGuaranteePayment().add(guaranteePayment);
		//TODO: determine which
		guaranteePayment.setPaymentCode(Attribute.PMT_PRE_PAY);
		guaranteePayment.setPaymentCode(Attribute.PMT_DEPOSIT);
	
		//								guaranteePayment.setType(Attribute.PMT_GUARANTEE);
		//								guaranteePayment.setNoCardHolderInfoReqInd(false);
		//								guaranteePayment.setAcceptedPayments(getAcceptedPayments(product, agent));
		//								guaranteePayment.setNonRefundableIndicator(true);
		//
		//								guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
		//								requiredPayments.getGuaranteePayment().add(guaranteePayment);
		//								guaranteePayment.setType(Attribute.PMT_DEPOSIT);
		//								guaranteePayment.setNonRefundableIndicator(true);
	
		AmountPercentType amountPercent = OF.createAmountPercentType();
		guaranteePayment.setAmountPercent(amountPercent);
		amountPercent.setPercent(getBigDecimal(Double.valueOf(deposit)));
		amountPercent.setCurrencyCode(currency);
	
		//								amountPercent.setFeesInclusive(true);
		//								amountPercent.setNmbrOfNights(getBigInteger(1));
		//								amountPercent.setTaxInclusive(true);
	
		Deadline deadline = OF.createRequiredPaymentsTypeGuaranteePaymentDeadline();
		guaranteePayment.getDeadline().add(deadline);
		Date due = Time.addDuration(new Date(), 7.0, Time.DAY); //TODO:
		deadline.setAbsoluteDeadline(getDate(due));
		return requiredPayments;
	}

	/**
	 * Creates an OTAHotelResRQ request to notify Razor of a reservation using the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.22
	 *
	 * @param productid the ID of the property to be reserved.
	 * @param start the arrival date of the reservation in yyyy-MM-dd format.
	 * @param end the departure date of the reservation in yyyy-MM-dd format.
	 * @param emailaddress the email address of the primary guest for which the reservation is made.
	 * @param familyname the family name of the primary guest.
	 * @param firstname the first name of the primary guest.
	 * @param pos the point of sale code of the organization making the request
	 * @return OTAHotelResRQ request to make a reservation.
	 */
	public static OTAHotelResRQ hotelResRQ(
			String productid,
			String start,
			String end,
			String emailaddress,
			String familyname,
			String firstname,
			String pos
			) {
		OTAHotelResRQ rq = OF.createOTAHotelResRQ();
		rq.setEchoToken("OTAHotelResRQ " +  productid + ", " + start + ", " + end + ", " + emailaddress + ", " + familyname  + ", " + firstname + ", " + pos);
		LOG.debug(rq.getEchoToken());
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_4001);

		HotelReservationsType hotelReservations = OF.createHotelReservationsType();
		rq.setHotelReservations(hotelReservations);
		HotelReservationType hotelReservation = OF.createHotelReservationType();
		hotelReservations.getHotelReservation().add(hotelReservation);
		RoomStaysType roomStays = OF.createRoomStaysType();
		hotelReservation.setRoomStays(roomStays);

		RoomStaysType.RoomStay roomStay = OF.createRoomStaysTypeRoomStay();
		roomStays.getRoomStay().add(roomStay);
		//roomStay.getPromotionVendorCode().add(PROMOTION_VENDOR_CODE);
		roomStay.getWarningRPH().add("1");

		BasicPropertyInfoType basicPropertyInfo = OF.createBasicPropertyInfoType();
		roomStay.setBasicPropertyInfo(basicPropertyInfo);
		basicPropertyInfo.setHotelCode(productid);
		//basicPropertyInfo.setChainCode(getChainCode(productid));
		basicPropertyInfo.setHotelCodeContext(CONTEXT);

		DateTimeSpanType dateTimeSpan = OF.createDateTimeSpanType();
		roomStay.setTimeSpan(dateTimeSpan);
		dateTimeSpan.setStart(start);
		dateTimeSpan.setEnd(end);

		TotalType quoteTotal = OF.createTotalType();
		roomStay.setTotal(quoteTotal);
		quoteTotal.getAmountAfterTax();
		quoteTotal.getCurrencyCode();
		//TODO: check and issue warning if not in RS

		ResGuestsType resGuests = OF.createResGuestsType();
		hotelReservation.setResGuests(resGuests);
		ResGuest resGuest = OF.createResGuestsTypeResGuest();
		resGuests.getResGuest().add(resGuest);
		ProfilesType profiles = OF.createProfilesType();
		resGuest.setProfiles(profiles);
		ProfileInfo profileInfo = OF.createProfilesTypeProfileInfo();
		profiles.getProfileInfo().add(profileInfo);
		ProfileType profile = OF.createProfileType();
		profileInfo.setProfile(profile);
		profile.setProfileType(Attribute.PRT_CUSTOMER);
		CustomerType customer = OF.createCustomerType();
		profile.setCustomer(customer);
		PersonNameType personName = OF.createPersonNameType();
		customer.getPersonName().add(personName);
		personName.setSurname(familyname);
		personName.getGivenName().add(firstname);
		customer.getEmail().add(getCustomerEmail(emailaddress, Attribute.EAT_PERSONAL));
		LOG.debug("\nhotelResRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTAHotelResRS response to the specified eRes OTAHotelResRQ request.
	 *
	 * @param rq the OTAHotelResRQ (HotelResRequestType) request.
	 * @return OTAHotelResRS (HotelResResponseType) to update the system with reservation data.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.23
	 */
	public static OTAHotelResRS hotelResNotifRS(OTAHotelResRQ rq) {
		OTAHotelResRS rs = OF.createOTAHotelResRS();
		LOG.debug("eresHotelResRS " + rq.getEchoToken() + "\n");
		rs.setEchoToken(getRequestorID(rq.getPOS(), 0));
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_115);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = getParty(sqlSession, rq.getPOS());

			TransactionActionType resStatus = rq.getResStatus();
			if (resStatus == null
					|| resStatus.value() == null
					|| !resStatus.value().equalsIgnoreCase(ERES_BOOK)
					) {throw new OtaException("ResStatus", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			String bookingChannelType = getBookingChannelType(rq.getPOS(), 0);	//TODO: INTERNET=7 GDS=1 PMS=4

			//Iterate through hotel reservation requests
			int indexHotelReservation = 0;
			for (HotelReservationType reservationRQ : rq.getHotelReservations().getHotelReservation()) {

				//reservationRQ.getResGuests().getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().get
				if (reservationRQ == null
						|| reservationRQ.getResGuests() == null
						) {throw new OtaException("Customers", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				ResGuestsType resGuests = reservationRQ.getResGuests();

				if (resGuests == null
						|| resGuests.getResGuest() == null
						|| resGuests.getResGuest().isEmpty()
						|| resGuests.getResGuest().get(0).getProfiles() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().isEmpty()
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer() == null
				) {throw new OtaException("Customer", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				if (resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName().isEmpty()
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName().get(0).getSurname() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName().get(0).getSurname().isEmpty()
				) {throw new OtaException("Customer_Surname", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				if (resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().isEmpty()
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue() == null
						|| resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue().isEmpty()
				) {throw new OtaException("EmailAddress", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}


				if (!Party.isEmailAddress(resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue())) 
				{throw new OtaException("EmailAddress_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				if (reservationRQ.getUniqueID() == null
						|| reservationRQ.getUniqueID().isEmpty()
						|| reservationRQ.getUniqueID().get(indexHotelReservation) == null
						|| reservationRQ.getUniqueID().get(indexHotelReservation).getType() == null
						|| reservationRQ.getUniqueID().get(indexHotelReservation).getType().equalsIgnoreCase(ERES_UID_TYPE)
						|| reservationRQ.getUniqueID().get(indexHotelReservation).getID() == null
						|| reservationRQ.getUniqueID().get(indexHotelReservation).getID().isEmpty()
				) {throw new OtaException("UniqueID", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				String uid = reservationRQ.getUniqueID().get(indexHotelReservation).getID();
				//				The Unique ID should always start at 1 for each reservation. This will indicate the itinerary. If you make a multi itinerary reservation you will increase your unique ID by 1 ex:

				RoomStaysType roomStaysRQ = reservationRQ.getRoomStays();
				if (roomStaysRQ == null
						|| roomStaysRQ.getRoomStay() == null
						|| roomStaysRQ.getRoomStay().isEmpty()
				) {throw new OtaException("RoomStay", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				for (RoomStayType roomStayRQ : roomStaysRQ.getRoomStay()) {

					if (roomStayRQ.getRoomRates() == null
							|| roomStayRQ.getRoomRates().getRoomRate() == null
							|| roomStayRQ.getRoomRates().getRoomRate().isEmpty()
					) {throw new OtaException("RoomRate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

					if (roomStayRQ.getTimeSpan() == null
							|| roomStayRQ.getTimeSpan().getStart() == null
							|| roomStayRQ.getTimeSpan().getStart().isEmpty()
							|| getDate(roomStayRQ.getTimeSpan().getStart()) == null
					) {throw new OtaException("StartDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

					if (roomStayRQ.getTimeSpan().getEnd() == null
							|| roomStayRQ.getTimeSpan().getEnd().isEmpty()
							|| getDate(roomStayRQ.getTimeSpan().getEnd()) == null
					) {throw new OtaException("EndDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

					Date arrivaldate = getDate(roomStayRQ.getTimeSpan().getStart());
					Date departuredate = getDate(roomStayRQ.getTimeSpan().getEnd());
					Date now = new Date();
					
					if (arrivaldate.before(now)) {throw new OtaException("StartDate", Attribute.ERR_START_DATE_IS_IN_THE_PAST, Attribute.EWT_START_DATE_IS_IN_THE_PAST);}
					if (departuredate.before(now)) {throw new OtaException("EndDate", Attribute.ERR_END_DATE_IS_IN_THE_PAST, Attribute.EWT_END_DATE_IS_IN_THE_PAST);}
					if (departuredate.before(arrivaldate)) {throw new OtaException("StartEndDate", Attribute.ERR_END_DATE_IS_BEFORE_START_DATE, Attribute.EWT_END_DATE_IS_BEFORE_START_DATE);}

					boolean isPerRoom = roomStayRQ.getGuestCounts().isIsPerRoom();
					String ageQualifyingCode = roomStayRQ.getGuestCounts().getGuestCount().get(0).getAgeQualifyingCode(); //ADULT=10 CHILD=8
					Integer age = roomStayRQ.getGuestCounts().getGuestCount().get(0).getAge();
					Integer count = roomStayRQ.getGuestCounts().getGuestCount().get(0).getCount();

					RoomRate roomRateRQ = roomStayRQ.getRoomRates().getRoomRate().get(0);
					String roomTypeCode = roomRateRQ.getRoomTypeCode(); 	//Razor productid
					String ratePlanCode = roomRateRQ.getRatePlanCode(); 	//Not used
					Integer numberOfUnits = roomRateRQ.getNumberOfUnits(); //Must be one
					String invBlockCode = roomRateRQ.getInvBlockCode();	//Allocation ignored
					Product product = sqlSession.getMapper(ProductMapper.class).read(roomTypeCode);
					if (product == null) {throw new OtaException("HotelCode", Attribute.ERR_INVALID_HOTEL_ID, Attribute.EWT_INVALID_HOTEL_ID);}
					Party manager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
					if (manager == null) {throw new OtaException("ChainCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

					// Create customer party ---------------------------------------------------------------------------
					CustomerType customer = resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer();
					String emailaddress = customer.getEmail().get(0).getValue();
					Party party = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
					if (party == null) {party = getCustomer(sqlSession, customer, (agent == null) ? manager.getId() : agent.getId());}
					RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), manager.getId(), party.getId());

					// Create reservation ---------------------------------------------------------------------------
					String arrivaltime = (resGuests.getResGuest().get(0).getArrivalTime() == null) ? product.getValue(ProductValue.CHECK_IN_TIME) : getTime(resGuests.getResGuest().get(0).getArrivalTime());
					String departuretime = (resGuests.getResGuest().get(0).getDepartureTime() == null) ? product.getValue(ProductValue.CHECK_OUT_TIME) : getTime(resGuests.getResGuest().get(0).getDepartureTime());
					String currency = rq.getPOS().getSource().get(0).getISOCurrency();
					if (currency == null) {currency = (agent.hasCurrency()) ? agent.getCurrency() : DEFAULT_CURRENCY_CODE;}
					
					StringBuilder sb = new StringBuilder();
					GuestCountType guestCount = roomStayRQ.getGuestCounts();
					if (guestCount != null && !guestCount.getGuestCount().isEmpty()) {
						sb.append("\nGuest Count/Age: ");
						for (GuestCount gc : guestCount.getGuestCount()){
							if (Attribute.AQC_ADULT.equalsIgnoreCase(gc.getAgeQualifyingCode())) {sb.append("\nNumber of Adults:" + gc.getCount());}
							if (Attribute.AQC_CHILD.equalsIgnoreCase(gc.getAgeQualifyingCode())) {sb.append("\nNumber of Children:" + gc.getCount());}
							if (Attribute.AQC_INFANT.equalsIgnoreCase(gc.getAgeQualifyingCode())) {sb.append("\nNumber of Infants:" + gc.getCount());}
						}
					}
					String notes = sb.toString();
					
					Reservation reservation = getReservation(sqlSession, 
						(agent == null) ? manager.getId() : agent.getId(),
						party.getId(),
						product.getSupplierid(),
						product.getId(),
						arrivaldate, 
						departuredate, 
						arrivaltime, 
						departuretime,
						currency,
						notes
					);

					//-----------------------------------------------
					//Acknowledge response
					//-----------------------------------------------
					HotelReservationsType hotelReservations = OF.createHotelReservationsType();
					rs.setHotelReservations(hotelReservations);
					HotelReservationType reservationRS = OF.createHotelReservationType();
					hotelReservations.getHotelReservation().add(reservationRS);
					ResGlobalInfoType resGlobalInfo = OF.createResGlobalInfoType();
					reservationRS.setResGlobalInfo(resGlobalInfo);
					HotelReservationIDsType hotelReservationIDs = OF.createHotelReservationIDsType();
					resGlobalInfo.setHotelReservationIDs(hotelReservationIDs);
					HotelReservationID hotelReservationID = OF.createHotelReservationIDsTypeHotelReservationID();
					hotelReservationIDs.getHotelReservationID().add(hotelReservationID);
					hotelReservationID.setResIDType(Attribute.UIT_RESERVATION);
					hotelReservationID.setResIDValue(reservation.getName());
					hotelReservationID.setResIDSource(reservation.getAgentid());
					hotelReservationID.setResIDSourceContext(CONTEXT);

					RoomStaysType roomStaysRS = OF.createRoomStaysType();
					reservationRS.setRoomStays(roomStaysRS);
					RoomStaysType.RoomStay roomStayRS = OF.createRoomStaysTypeRoomStay();
					roomStaysRS.getRoomStay().add(roomStayRS);

					BasicPropertyInfoType basicPropertyInfo = OF.createBasicPropertyInfoType();
					roomStayRS.setBasicPropertyInfo(basicPropertyInfo);
					basicPropertyInfo.setAddress(getAddressInfo(manager, Attribute.AUT_BILLING));
					basicPropertyInfo.setBrandCode(product.getOwnerid());
					basicPropertyInfo.setChainCode(manager.getId());
					basicPropertyInfo.setChainName(manager.getName());
					basicPropertyInfo.setHotelCode(product.getId());
					basicPropertyInfo.setHotelCodeContext(CONTEXT);
					basicPropertyInfo.setHotelName(product.getName());

					RoomRates roomRates = OF.createRoomStayTypeRoomRates();
					roomStayRS.setRoomRates(roomRates);
					RoomRate roomRate = OF.createRoomStayTypeRoomRatesRoomRate();
					roomRates.getRoomRate().add(roomRate);
					RateType rates =OF.createRateType();
					roomRate.setRates(rates);

					//-----------------------------------------------
					//Rack price for stay
					//-----------------------------------------------
					Rate price = OF.createRateTypeRate();
					rates.getRate().add(price);
					price.setRateDescription(getParagraph("Total RACK per stay"));
					price.setEffectiveDate(getXGC(reservation.getFromdate()));
					price.setExpireDate(getXGC(reservation.getTodate()));
					TotalType rackTotal = OF.createTotalType();
					price.setBase(rackTotal);
					rackTotal.setAmountAfterTax(getBigDecimal(reservation.getPrice()));
					rackTotal.setCurrencyCode(reservation.getCurrency());

					//-----------------------------------------------
					//Quoted price for stay
					//-----------------------------------------------
					Rate quote = OF.createRateTypeRate();
					rates.getRate().add(quote);
					quote.setRateDescription(getParagraph("Total QUOTE per stay"));
					quote.setEffectiveDate(getXGC(reservation.getFromdate()));
					quote.setExpireDate(getXGC(reservation.getTodate()));
					TotalType quoteTotal = OF.createTotalType();
					quote.setBase(quoteTotal);
					quoteTotal.setAmountAfterTax(getBigDecimal(reservation.getQuote()));
					quoteTotal.setCurrencyCode(reservation.getCurrency());

					//-----------------------------------------------
					//STO price for stay
					//-----------------------------------------------
					Rate sto = OF.createRateTypeRate();
					rates.getRate().add(sto);
					sto.setRateDescription(getParagraph("Total STO per stay"));
					sto.setEffectiveDate(getXGC(reservation.getFromdate()));
					sto.setExpireDate(getXGC(reservation.getTodate()));
					TotalType stoTotal = OF.createTotalType();
					sto.setBase(stoTotal);
					stoTotal.setAmountAfterTax(getBigDecimal(reservation.getCost()));
					stoTotal.setCurrencyCode(reservation.getCurrency());

					//-----------------------------------------------
					//Cancellation penalty
					//-----------------------------------------------
					roomStayRS.setCancelPenalties(getCancelPenalties());

					//-----------------------------------------------
					//Reservation booking rules
					//-----------------------------------------------
					BookingRulesType bookingRules = OF.createBookingRulesType();
					roomStayRS.setBookingRules(bookingRules);
					BookingRule bookingRule = OF.createBookingRulesTypeBookingRule();
					bookingRules.getBookingRule().add(bookingRule);
					bookingRule.setCancelPenalties(getCancelPenalties());

					RequiredPaymentsType requiredPayments = OF.createRequiredPaymentsType();
					bookingRule.setRequiredPaymts(requiredPayments);
					GuaranteePayment guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
					requiredPayments.getGuaranteePayment().add(guaranteePayment);
					guaranteePayment.setType(Attribute.PMT_GUARANTEE);
					guaranteePayment.setNoCardHolderInfoReqInd(false);
					guaranteePayment.setAcceptedPayments(getAcceptedPayments(product, agent));
					guaranteePayment.setNonRefundableIndicator(true);

					//-----------------------------------------------
					//Deposit payment
					//-----------------------------------------------
					guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
					requiredPayments.getGuaranteePayment().add(guaranteePayment);
					guaranteePayment.setType(Attribute.PMT_DEPOSIT);
					guaranteePayment.setNonRefundableIndicator(true);

					AmountPercentType amountPercent = OF.createAmountPercentType();
					guaranteePayment.setAmountPercent(amountPercent);
					amountPercent.setCurrencyCode(reservation.getCurrency());
					amountPercent.setFeesInclusive(true);
					amountPercent.setNmbrOfNights(getBigInteger(reservation.getDuration(Time.DAY).intValue()));
					amountPercent.setTaxInclusive(true);

					Deadline deadline = OF.createRequiredPaymentsTypeGuaranteePaymentDeadline();
					guaranteePayment.getDeadline().add(deadline);
					Date due = Time.addDuration(now, 7.0);
					deadline.setAbsoluteDeadline(getDate(due));
					amountPercent.setPercent(getBigDecimal(Double.valueOf(reservation.getDeposit())));
				} //for (RoomStayType roomStayRQ : roomStaysRQ.getRoomStay()) {
			} //for (HotelReservationType reservationRQ : rq.getHotelReservations().getHotelReservation()) {
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch (Throwable x) {LOG.error(x.getMessage());}
		finally {sqlSession.close();}
		LOG.debug("hotelResNotifRS.result " + rs + "\n");
		return rs;
	}

	/*
	 * Gets the customer for the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param customer the OTA customer.
	 * @param creatorid the ID of the creator.
	 * @return the customer party.
	 */
	public static Party getCustomer(SqlSession sqlSession, CustomerType customer, String creatorid) {
		Party party = new Party();
		party.setEmailaddress(customer.getEmail().get(0).getValue());
		party.setLocationid(Model.ZERO);
		String surname = customer.getPersonName().get(0).getSurname();
		party.setName(surname);
		if (customer.getPersonName().get(0).getGivenName() != null
				&& !customer.getPersonName().get(0).getGivenName().isEmpty()) {
			party.setName(surname, customer.getPersonName().get(0).getGivenName().get(0));
			if (customer.getPersonName().get(0).getGivenName().size() > 1) {
				party.setExtraname(customer.getPersonName().get(0).getGivenName().get(1));
			}
		}
		if (customer.getAddress() != null && !customer.getAddress().isEmpty()){
			StringBuilder postaladdress = new StringBuilder();
			CustomerType.Address address = customer.getAddress().get(0);
			for (String line : address.getAddressLine()) {postaladdress.append(line + "\n");}
			postaladdress.append(address.getCityName() + ", " + address.getCounty());
			party.setPostaladdress(postaladdress.toString());
			party.setPostalcode(address.getPostalCode());
			//party.setCountry(address.getCounty());
		}
		party.setBirthdate(getDateTime(customer.getBirthDate()));
		party.setCreatorid(creatorid);
		party.setCurrency(DEFAULT_CURRENCY_CODE);
		if (customer.getTelephone() != null && !customer.getTelephone().isEmpty()){
			CustomerType.Telephone phone = customer.getTelephone().get(0);
			party.setDayphone(phone.getAreaCityCode() + phone.getPhoneNumber());
		}
		party.setState(Party.CREATED);
		ArrayList<String>partyTypes = new ArrayList<String>();
		if (customer.getURL() != null && !customer.getURL().isEmpty()) {party.setWebaddress(customer.getURL().get(0).getValue());}

		sqlSession.getMapper(PartyMapper.class).create(party);
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), creatorid, party.getId());
		return party;
	}

	/*
	 * Gets the reservation for the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param agentid the agent ID.
	 * @param customerid the customer or guest ID.
	 * @param organizationid the manager's ID.
	 * @param productid the property ID.
	 * @param arrivaldate the arrival date.
	 * @param departuredate the departure date.
	 * @param arrivaltime the arrival time.
	 * @param departuretime the departure time.
	 * @param currency the currency of payment.
	 * @param notes the reservation comments.
	 * @return the reservation.
	 */
	private static Reservation getReservation(SqlSession sqlSession, 
			String agentid,
			String customerid,
			String organizationid,
			String productid,
			Date arrivaldate, 
			Date departuredate, 
			String arrivaltime, 
			String departuretime,
			String currency,
			String notes) {
		
		Reservation reservation = new Reservation();
		reservation.setName(SessionService.pop(sqlSession, organizationid, Serial.RESERVATION));
		sqlSession.getMapper(ReservationMapper.class).create(reservation);
	
		reservation.setAgentid(agentid);
		reservation.setFromdate(arrivaldate);
	
		reservation.setArrivaltime(arrivaltime);
		reservation.setDeparturetime(departuretime);
	
		reservation.setActorid(Party.NO_ACTOR);
		reservation.setCustomerid(customerid);
		reservation.setTodate(departuredate);
		Date now = new Date();
		reservation.setDuedate(Time.addDuration(now, 2.0, Time.DAY));
		reservation.setDate(now);
	
		reservation.setDeposit(ReservationService.getDeposit(sqlSession, reservation)); //.getSupplierid(), reservation.getFromdate()));
	
		reservation.setMarket(MARKET);
		reservation.setOrganizationid(organizationid);
		reservation.setState(Reservation.State.Provisional.name());
	
		reservation.setUnit(DEFAULT_UNIT);
	
		reservation.setNotes(notes);
		reservation.setProductid(productid);
	
		reservation.setCurrency(currency);
		ReservationService.computePrice(sqlSession, reservation, null);	
		reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
	
		ReservationService.reservationUpdate(sqlSession, reservation);
		if (reservation.hasCollisions()) {throw new OtaException("Reservation_Collides", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
		return reservation;
	}

}
