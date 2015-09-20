package net.cbtltd.soap.ota.server;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;

import net.cbtltd.server.PositionService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.ContractMapper;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.shared.product.ProductValue;
import net.cbtltd.soap.ota.OtaService;
import net.cbtltd.soap.ota.server.AddressType.StreetNmbr;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType.AvailRequestSegment;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType.ContactNumbers;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType.Position;
import net.cbtltd.soap.ota.server.BookingRulesType.BookingRule;
import net.cbtltd.soap.ota.server.CategoryCodesType.ArchitecturalStyle;
import net.cbtltd.soap.ota.server.CategoryCodesType.GuestRoomInfo;
import net.cbtltd.soap.ota.server.CategoryCodesType.HotelCategory;
import net.cbtltd.soap.ota.server.CategoryCodesType.LocationCategory;
import net.cbtltd.soap.ota.server.CategoryCodesType.SegmentCategory;
import net.cbtltd.soap.ota.server.CompanyInfoType.TelephoneInfo;
import net.cbtltd.soap.ota.server.CustomerType.Telephone;
import net.cbtltd.soap.ota.server.FacilityInfoType.GuestRooms;
import net.cbtltd.soap.ota.server.FacilityInfoType.GuestRooms.GuestRoom;
import net.cbtltd.soap.ota.server.FacilityInfoType.GuestRooms.GuestRoom.Amenities;
import net.cbtltd.soap.ota.server.FacilityInfoType.GuestRooms.GuestRoom.Amenities.Amenity;
import net.cbtltd.soap.ota.server.FeaturesType.Feature;
import net.cbtltd.soap.ota.server.GuestCountType.GuestCount;
import net.cbtltd.soap.ota.server.HotelDescriptiveContentType.Policies;
import net.cbtltd.soap.ota.server.HotelInfoType.HotelInfoCodes;
import net.cbtltd.soap.ota.server.HotelInfoType.HotelInfoCodes.HotelInfoCode;
import net.cbtltd.soap.ota.server.HotelInfoType.HotelName;
import net.cbtltd.soap.ota.server.HotelInfoType.Services;
import net.cbtltd.soap.ota.server.HotelReservationIDsType.HotelReservationID;
import net.cbtltd.soap.ota.server.HotelSearchCriteriaType.Criterion;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelAmenity;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelFeature;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.Service;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.HotelRef;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.Radius;
import net.cbtltd.soap.ota.server.OTACancelRQ.Reasons;
import net.cbtltd.soap.ota.server.OTACancelRQ.UniqueID;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ.AvailRequestSegments;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS.RoomStays;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS.RoomStays.RoomStay;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans.RatePlan;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange;
import net.cbtltd.soap.ota.server.OTAReadRQ.ReadRequests;
import net.cbtltd.soap.ota.server.OTAReadRQ.ReadRequests.ReadRequest;
import net.cbtltd.soap.ota.server.OTAResRetrieveRS.ReservationsList;
import net.cbtltd.soap.ota.server.PoliciesType.Policy;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.CommissionPolicy;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.GuaranteePaymentPolicy;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.PetsPolicies;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.PolicyInfo;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.StayRequirements;
import net.cbtltd.soap.ota.server.PoliciesType.Policy.StayRequirements.StayRequirement;
import net.cbtltd.soap.ota.server.ProfilesType.ProfileInfo;
import net.cbtltd.soap.ota.server.PropertiesType.Property;
import net.cbtltd.soap.ota.server.PropertyValueMatchType.RateRange;
import net.cbtltd.soap.ota.server.RateType.Rate;
import net.cbtltd.soap.ota.server.RequiredPaymentsType.GuaranteePayment;
import net.cbtltd.soap.ota.server.RequiredPaymentsType.GuaranteePayment.Deadline;
import net.cbtltd.soap.ota.server.ResCommonDetailType.TimeSpan;
import net.cbtltd.soap.ota.server.ResGuestsType.ResGuest;
import net.cbtltd.soap.ota.server.RoomStayType.RoomRates;
import net.cbtltd.soap.ota.server.RoomStayType.RoomRates.RoomRate;
import net.cbtltd.soap.ota.server.SpecialRequestType.SpecialRequest;

import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.config.RazorConfig;

public class OtaServiceRazor
extends OtaService {

	/*
	 * Gets the party having the specified pos code.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the the specified pos code.
	 * @return the party.
	 */
	private static Party getParty(SqlSession sqlSession, POSType pos) {
		return getParty(sqlSession, Model.decrypt(getRequestorID(pos, 0)));
	}

	/**
	 * Creates an OTACancelRQ message with the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.2
	 *
	 * @param name the reference code of reservation to be cancelled.
	 * @param reason the reason for the cancellation.
	 * @param pos the point of sale code of the organization making the request
	 * @return OTACancelRS cancel reservation request
	 */
	public static OTACancelRQ cancelRQ(
			String name,
			String reason,
			String pos
			) {
		OTACancelRQ rq = OF.createOTACancelRQ();
		rq.setEchoToken("OTACancelRQ " + name + ", " + reason + ", " + pos);
		LOG.debug(rq.getEchoToken() + "\n");
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_1010);
		
		Reasons reasons = OF.createOTACancelRQReasons();
		rq.setReasons(reasons);
		reasons.getReason().add(getFreeText(reason, DEFAULT_LANGUAGE_CODE));

		UniqueID uid = OF.createOTACancelRQUniqueID();
		rq.getUniqueID().add(uid);
		uid.setID(name);
		uid.setIDContext(CONTEXT);
		uid.setType(Attribute.UIT_RESERVATION);
		LOG.debug("\ncancelRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTACancelRS message with the specified OTACancelRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.2
	 *
	 * @param rq the specified request.
	 * @return OTACancelRS cancel reservation response.
	 */
	public static OTACancelRS cancelRS(OTACancelRQ rq) {
		OTACancelRS rs = OF.createOTACancelRS();
		LOG.debug(rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setEchoToken(rq.getEchoToken());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_2006);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party party = getParty(sqlSession, rq.getPOS());

			if (rq.getUniqueID() == null
					|| rq.getUniqueID().isEmpty()
					|| rq.getUniqueID().get(0).getType() == null
					|| !Attribute.UIT_RESERVATION.equalsIgnoreCase(rq.getUniqueID().get(0).getType())
			) {throw new OtaException("UniqueIDType", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			if (!CONTEXT.equalsIgnoreCase(rq.getUniqueID().get(0).getIDContext())
					) {throw new OtaException("UniqueID_Context", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			if (rq.getUniqueID().get(0).getID() == null
					|| rq.getUniqueID().get(0).getID().isEmpty()
					) {throw new OtaException("UniqueID", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			String name = rq.getUniqueID().get(0).getID();
			Reservation action = new Reservation();
			action.setName(name);
			action.setOrganizationid(party.getId());
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).readbyname(action);

			if (reservation == null) {throw new OtaException(name, Attribute.ERR_INVALID_RESERVATION_ID, Attribute.EWT_INVALID_RESERVATION_ID);}
			if (!party.hasId(reservation.getAgentid())) {throw new OtaException("POS_Reservation_Mismatch", Attribute.ERR_INVALID_AGENT_ID, Attribute.EWT_INVALID_AGENT_ID);}
			rs.setCancelInfoRS(OF.createCancelInfoRSType());
			rs.getCancelInfoRS().setCancelRules(getCancelRules(new Date(), reservation.getFromdate()));
			reservation.setState(Reservation.State.Cancelled.name());
			reservation.setDonedate(new Date());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			rs.setErrors(null);
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch (Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("\ncancelRS.result " + rs);
		return rs;
	}

	/**
	 * Creates an OTAHotelAvailRQ message with the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.1
	 *
	 * @param productids the list of comma delimited properties for which availability is requested.
	 * @param fromdate the date from which the availability is requested.
	 * @param todate the date to which the availability is requested.
	 * @param pos the point of sale code of the organization making the request
	 * @return OTAHotelAvailRQ hotel availability request
	 */
	public static OTAHotelAvailRQ hotelAvailRQ(
			String productids,
			String fromdate,
			String todate,
			String pos
			) {
		return hotelAvailRQ(
				productids,
				fromdate,
				todate,
				false,
				pos
				);
	}

	/**
	 * Creates an OTAHotelAvailRQ message with the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.1
	 *
	 * @param productids the list of comma delimited properties for which availability is requested.
	 * @param fromdate the date from which the availability is requested.
	 * @param todate the date to which the availability is requested.
	 * @param summary is true if the request if for separate availability for each day.
	 * @param pos the point of sale code of the organization making the request
	 * @return OTAHotelAvailRQ hotel availability request
	 */
	public static OTAHotelAvailRQ hotelAvailRQ(
			String productids,
			String fromdate,
			String todate,
			boolean summary,
			String pos
			) {
		OTAHotelAvailRQ rq = OF.createOTAHotelAvailRQ();
		rq.setEchoToken("OTAHotelAvailRQ " + productids + ", " + fromdate + ", " + todate + ", " + summary + ", " + pos);
		LOG.debug(rq.getEchoToken());
		rq.setSummaryOnly(summary);
		rq.setPOS(getPOS(pos, CONTEXT));

		AvailRequestSegments availRequestSegments = OF.createOTAHotelAvailRQAvailRequestSegments();
		rq.setAvailRequestSegments(availRequestSegments);
		AvailRequestSegment availRequestSegment =  OF.createAvailRequestSegmentsTypeAvailRequestSegment();
		availRequestSegments.getAvailRequestSegment().add(availRequestSegment);
		HotelSearchCriteriaType hotelSearchCriteria = OF.createHotelSearchCriteriaType();
		availRequestSegment.setHotelSearchCriteria(hotelSearchCriteria);
		Criterion criterion = OF.createHotelSearchCriteriaTypeCriterion();
		hotelSearchCriteria.getCriterion().add(criterion);
		String[] idArray = productids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			HotelRef hotelRef = OF.createItemSearchCriterionTypeHotelRef();
			criterion.getHotelRef().add(hotelRef);
			hotelRef.setHotelCode(idArray[i]);
			hotelRef.setHotelCodeContext(CONTEXT);
			//hotelRef.setChainCode(getChainCode(idArray[i]));
			DateTimeSpanType stayDateRange = OF.createDateTimeSpanType();
			criterion.setStayDateRange(stayDateRange);
			criterion.getStayDateRange().setStart(fromdate);
			criterion.getStayDateRange().setEnd(todate);
		}
		LOG.debug("\nhotelAvailRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTAHotelAvailRS response to the specified OTAHotelAvailRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.1
	 *
	 * @param rq the OTAHotelAvailRQ query for availability of specified properties and dates.
	 * @return OTAHotelAvailRS hotel availability response.
	 */
	public static OTAHotelAvailRS hotelAvailRS (OTAHotelAvailRQ rq) {
		OTAHotelAvailRS rs = OF.createOTAHotelAvailRS();
		rs.setEchoToken(rq.getEchoToken());
		LOG.debug(rq + "\n");
		//LOG.debug(rq.getEchoToken() + "\n");
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_5001);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			getParty(sqlSession, rq.getPOS());

			if (rq.getAvailRequestSegments() == null
					|| rq.getAvailRequestSegments().getAvailRequestSegment() == null
					|| rq.getAvailRequestSegments().getAvailRequestSegment().isEmpty()
					|| rq.getAvailRequestSegments().getAvailRequestSegment().get(0).getHotelSearchCriteria() == null
					|| rq.getAvailRequestSegments().getAvailRequestSegment().get(0).getHotelSearchCriteria().getCriterion().isEmpty()
					) {throw new OtaException("SearchCriteria", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			HotelSearchCriteriaType.Criterion criterion = rq.getAvailRequestSegments().getAvailRequestSegment().get(0).getHotelSearchCriteria().getCriterion().get(0);
			if (criterion == null
					|| criterion.getHotelRef() == null
					|| criterion.getHotelRef().isEmpty()
					){throw new OtaException("HotelRefs", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			if (criterion.getStayDateRange() == null
					|| criterion.getStayDateRange().getStart() == null
					|| criterion.getStayDateRange().getStart().isEmpty()
					) {throw new OtaException("StartDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			if (criterion.getStayDateRange().getEnd() == null
					|| criterion.getStayDateRange().getEnd().isEmpty()
					) {throw new OtaException("EndDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

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
			reservation.setFromdate(getDate(criterion.getStayDateRange().getStart()));
			reservation.setTodate(getDate(criterion.getStayDateRange().getEnd()));

			RoomStays roomStays = OF.createOTAHotelAvailRSRoomStays();
			rs.setRoomStays(roomStays);
			ArrayList<OtaRoomStay> stays;

			boolean isSummary = rq.isSummaryOnly() == null || rq.isSummaryOnly();
			LOG.debug("action " + isSummary + " " + reservation);

			if (isSummary) {stays = sqlSession.getMapper(ReservationMapper.class).hotelavailabilities(reservation);} // summary availability
			else {stays = sqlSession.getMapper(ReservationMapper.class).hotelavailability(reservation);}

			LOG.debug("\nstays " + stays);

			if (stays == null || stays.isEmpty()) {throw new OtaException("Not Available", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			for (OtaRoomStay stay : stays) {
				RoomStay roomStay = OF.createOTAHotelAvailRSRoomStaysRoomStay();
				roomStays.getRoomStay().add(roomStay);
				BasicPropertyInfoType basicPropertyInfo = OF.createBasicPropertyInfoType();
				roomStay.setBasicPropertyInfo(basicPropertyInfo);
				basicPropertyInfo.setHotelCodeContext(CONTEXT);
				basicPropertyInfo.setHotelCode(stay.getProductid());
				basicPropertyInfo.setHotelName(stay.getHotelname());
				basicPropertyInfo.setAreaID(stay.getLocationid());
				basicPropertyInfo.setChainCode(stay.getHotelgroup());
				basicPropertyInfo.setPosition(getPosition(stay.getLatitude(), stay.getLongitude(), stay.getAltitude()));

				DateTimeSpanType timeSpan = OF.createDateTimeSpanType();
				roomStay.setTimeSpan(timeSpan);
				timeSpan.setStart(getXGC(stay.getDate()).toXMLFormat());
				timeSpan.setEnd(getXGC(stay.getTodate()).toXMLFormat());

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

				TotalType value = OF.createTotalType();
				rate.setBase(value);
				value.setCurrencyCode(stay.getCurrency());

				TotalType total = OF.createTotalType(); // minimum booking value
				rate.setTotal(total);
				total.setCurrencyCode(stay.getCurrency());

				if (isSummary) {
					value.setAmountAfterTax(getBigDecimal(stay.getValue()));
					total.setAmountAfterTax(getBigDecimal(stay.getTotal()));
				}
				else {
					reservation.setOrganizationid(stay.getHotelgroup());
					reservation.setProductid(stay.getProductid());
					reservation.setUnit(Unit.DAY);
					ReservationService.computePrice(sqlSession, reservation, null);
					Double quote = reservation.getQuote();
					LOG.debug("quote " + quote + " " + reservation);
					total.setAmountAfterTax(getBigDecimal(quote));
					value.setAmountAfterTax(getBigDecimal(quote/stay.getDuration()));
				}
				if (i++ > maxResponses) {break;}
			}

			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		} 
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("\nHotelAvailRS.result " + rs);
		return rs;
	}

	/**
	 * Creates an OTAHotelDescriptiveInfoRQ request for the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.7
	 *
	 * @param productids list of properties for which descriptive information is requested.
	 * @param pos the point of sale code of the organization making the request.
	 * @return OTAHotelDescriptiveInfoRQ hotel descriptive info request.
	 */
	public static OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ(
			String productids,
			String pos
			) {
		OTAHotelDescriptiveInfoRQ rq = OF.createOTAHotelDescriptiveInfoRQ();
		rq.setEchoToken("OTAHotelDescriptiveInfoRQ " + productids + ", " + pos);
		LOG.debug("hotelDescriptiveInfoRQ " + rq.getEchoToken() + "\n");
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_1009);

		HotelDescriptiveInfos hotelDescriptiveInfos = OF.createOTAHotelDescriptiveInfoRQHotelDescriptiveInfos();
		rq.setHotelDescriptiveInfos(hotelDescriptiveInfos);
		String[] idArray = productids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			HotelDescriptiveInfo hotelDescriptiveInfo = OF.createOTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo();
			hotelDescriptiveInfo.setHotelCode(idArray[i]);
			hotelDescriptiveInfo.setHotelCodeContext(CONTEXT);
			//TODO:			hotelDescriptiveInfo.setChainCode(id);
			//hotelDescriptiveInfo.setChainCode(getChainCode(idArray[i]));
			rq.getHotelDescriptiveInfos().getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
		}
		LOG.debug("\nhotelDescriptiveInfoRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTAHotelDescriptiveInfoRS response to the specified OTAHotelDescriptiveInfoRQ request .
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.7.1
	 * @see <pre>http://www.seekom.com/xml/documentation/OtaHotelDescriptiveInfoRS.htm</pre>
	 *
	 * @param rq the OTAHotelDescriptiveInfoRQ request for property descriptive information.
	 * @return OTAHotelDescriptiveInfoRS descriptive information for the specified properties.
	 */
	public static OTAHotelDescriptiveInfoRS hotelDescriptiveInfoRS(OTAHotelDescriptiveInfoRQ rq) {

		OTAHotelDescriptiveInfoRS rs = OF.createOTAHotelDescriptiveInfoRS();
		rs.setEchoToken(rq.getEchoToken());
		LOG.debug("hotelDescriptiveInfoRS " + rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_6004);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = getParty(sqlSession, rq.getPOS());

			List<HotelDescriptiveInfo> hotelDescriptiveInfos = rq.getHotelDescriptiveInfos().getHotelDescriptiveInfo();
			for (HotelDescriptiveInfo hotelDescriptiveInfo : hotelDescriptiveInfos) {
				if (hotelDescriptiveInfo.getHotelCode() == null
						|| hotelDescriptiveInfo.getHotelCode().isEmpty()
						) {throw new OtaException("HotelCode", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				if (hotelDescriptiveInfo.getHotelCodeContext() == null
						|| !hotelDescriptiveInfo.getHotelCodeContext().equalsIgnoreCase(CONTEXT)
						) {throw new OtaException("HotelCode_Context", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				Product product = sqlSession.getMapper(ProductMapper.class).read(hotelDescriptiveInfo.getHotelCode());

				if (product == null || !product.hasState(Product.CREATED)
						) {throw new OtaException("HotelCode_Invalid", Attribute.ERR_INVALID_HOTEL_ID, Attribute.EWT_INVALID_HOTEL_ID);}

				Party manager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
				if (manager == null) {throw new OtaException("ChainCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				Party owner = sqlSession.getMapper(PartyMapper.class).read(product.getOwnerid());
				if (owner == null) {throw new OtaException("BranchCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				String pos = getRequestorID(rq.getPOS(), 0); //.getSource().get(0).getRequestorID().getID();
				Contract contract = sqlSession.getMapper(ContractMapper.class).readbyexample(new Contract(NameId.Type.Reservation.name(), manager.getId(), pos));

				Location location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
				Country country = sqlSession.getMapper(CountryMapper.class).read(location.getCountry());
				//Region region = sqlSession.getMapper(RegionMapper.class).readbylocation(location);

				if (agent == null) {throw new OtaException("RequestorID_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
				if (location == null || country == null
						) {throw new OtaException("LocationCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}


				HotelDescriptiveContents hotelDescriptiveContents = OF.createOTAHotelDescriptiveInfoRSHotelDescriptiveContents();
				rs.setHotelDescriptiveContents(hotelDescriptiveContents);

				hotelDescriptiveContents.setAreaID(product.getLocationid());
				hotelDescriptiveContents.setBrandCode(owner.getId());
				hotelDescriptiveContents.setBrandName(owner.getName());
				hotelDescriptiveContents.setChainCode(manager.getId());
				hotelDescriptiveContents.setChainName(manager.getName());
				hotelDescriptiveContents.setHotelCityCode(location.getCode());
				hotelDescriptiveContents.setHotelCode(product.getId());
				hotelDescriptiveContents.setHotelCodeContext(CONTEXT);
				hotelDescriptiveContents.setHotelName(product.getName());
				hotelDescriptiveContents.setOverwrite(true);

				HotelDescriptiveContent hotelDescriptiveContent = OF.createOTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent();
				hotelDescriptiveContents.getHotelDescriptiveContent().add(hotelDescriptiveContent);
				hotelDescriptiveContent.setCurrencyCode(manager.getCurrency());
				hotelDescriptiveContent.setHotelCityCode(location.getCode());
				hotelDescriptiveContent.setLanguageCode(DEFAULT_LANGUAGE_CODE);
				hotelDescriptiveContent.setOverwrite(true);

				ContactInfosType contactInfosType = OF.createContactInfosType();
				hotelDescriptiveContent.setContactInfos(contactInfosType);
				ContactInfoRootType contactInfoRootType = OF.createContactInfoRootType();
				contactInfosType.getContactInfo().add(contactInfoRootType);
				contactInfoRootType.setAddresses(getAddresses(manager, Attribute.AUT_CONTACT));
				//						contactInfoRootType.setCompanyName(getCompanyName(manager, TVS_HOTEL));
				contactInfoRootType.setContactProfileID(manager.getId());
				contactInfoRootType.setContactProfileType(Attribute.PRT_HOTEL);
				contactInfoRootType.setEmails(getEmails(manager, Attribute.EAT_MANAGING_COMPANY));
				contactInfoRootType.setPhones(getPhones(manager));
				contactInfoRootType.setRemoval(false);
				contactInfoRootType.setURLs(getURLs(manager, Attribute.URL_HOME));

				FacilityInfoType facilityInfo = OF.createFacilityInfoType();
				hotelDescriptiveContent.setFacilityInfo(facilityInfo);
				GuestRooms guestRooms = OF.createFacilityInfoTypeGuestRooms();
				facilityInfo.setGuestRooms(guestRooms);
				{
					//Guest rooms
					BigInteger maxOccupancy = getBigInteger(
							product.getIntegerValue(ProductValue.ADULT_COUNT)
							+ product.getIntegerValue(ProductValue.CHILD_COUNT)
							+ product.getIntegerValue(ProductValue.INFANT_COUNT)
							);
					guestRooms.setMaxOccupancy(maxOccupancy);
					GuestRoom guestRoom = OF.createFacilityInfoTypeGuestRoomsGuestRoom();
					guestRooms.getGuestRoom().add(guestRoom);
					guestRoom.setMaxOccupancy(maxOccupancy);
					guestRoom.setMaxAdultOccupancy(getBigInteger(product.getIntegerValue(ProductValue.ADULT_COUNT)));
					guestRoom.setMaxChildOccupancy(getBigInteger(product.getIntegerValue(ProductValue.CHILD_COUNT)));
					guestRoom.setRoomTypeName(product.getValue(ProductValue.ROOM_TYPE));
					if (product.hasValue(Attribute.RMA)) {
						//Room amenities
						Amenities amenities = OF.createFacilityInfoTypeGuestRoomsGuestRoomAmenities();
						guestRoom.setAmenities(amenities);
						List<String> rmas = product.getAttributeValues(Attribute.RMA);
						for (String rma : rmas){
							Amenity amenity = OF.createFacilityInfoTypeGuestRoomsGuestRoomAmenitiesAmenity();
							amenities.getAmenity().add(amenity);
							amenity.setRoomAmenityCode(rma);
						}
					}

					FeaturesType features = OF.createFeaturesType();
					guestRoom.setFeatures(features);
					//Physically disabled feature
					if (product.hasValue(Attribute.PHY)) {
						List<String> phys = product.getAttributeValues(Attribute.PHY);
						for (String phy : phys){
							Feature feature = OF.createFeaturesTypeFeature();
							features.getFeature().add(feature);
							feature.setAccessibleCode(phy);
						}
					}
					//Room location feature
					if (product.hasValue(Attribute.RLT)) {
						List<String> rlts = product.getAttributeValues(Attribute.RLT);
						for (String rlt : rlts){
							Feature feature = OF.createFeaturesTypeFeature();
							features.getFeature().add(feature);
							feature.setProximityCode(rlt);
						}
					}
					//Security feature
					if (product.hasValue(Attribute.SEC)) {
						List<String> secs = product.getAttributeValues(Attribute.SEC);
						for (String sec : secs){
							Feature feature = OF.createFeaturesTypeFeature();
							features.getFeature().add(feature);
							feature.setAccessibleCode(sec);
						}
					}
				}

				MultimediaDescriptionsType multimediaDescriptions = OF.createMultimediaDescriptionsType();
				hotelDescriptiveContent.setMultimediaDescriptions(multimediaDescriptions);
				{
					{
						MultimediaDescriptionType multimediaDescription = OF.createMultimediaDescriptionType();
						multimediaDescriptions.getMultimediaDescription().add(multimediaDescription);
						//Text information
						TextItemsType textItems = OF.createTextItemsType();
						multimediaDescription.setTextItems(textItems);

						//Product description in plain text
						Text text = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(product.getPublicId(), DEFAULT_LANGUAGE_CODE));
						textItems.getTextItem().add(getTextItem(
								RazorHostsConfig.getCopyrightNotice(),
								owner.getName(),
								"Property Notes",
								text.getLanguage(),
								text.getNotes())

								);

						//Product contents
						text = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(product.getContentsId(), DEFAULT_LANGUAGE_CODE));
						textItems.getTextItem().add(getTextItem(
								RazorHostsConfig.getCopyrightNotice(),
								owner.getName(),
								"Included Items",
								text.getLanguage(),
								text.getNotes())
								);

						//Product options
						text = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(product.getOptionsId(), DEFAULT_LANGUAGE_CODE));
						textItems.getTextItem().add(getTextItem(
								RazorHostsConfig.getCopyrightNotice(),
								owner.getName(),
								"Optional Items",
								text.getLanguage(),
								text.getNotes())
								);

						//Product manager
						text = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(Party.getPublicId(product.getSupplierid()), DEFAULT_LANGUAGE_CODE));
						textItems.getTextItem().add(getTextItem(
								RazorHostsConfig.getCopyrightNotice(),
								owner.getName(),
								"Manager Notes",
								text.getLanguage(),
								text.getNotes())
								);
					}

					///Image information
					ArrayList<String> imageids = sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Product.name(), product.getId()));
//					ArrayList<String> imageIds = RelationService.read(sqlSession, Relation.PRODUCT_IMAGE, product.getId(), null);
					if (imageids!= null && !imageids.isEmpty()) {
						MultimediaDescriptionType multimediaDescription = OF.createMultimediaDescriptionType();
						multimediaDescriptions.getMultimediaDescription().add(multimediaDescription);
						ImageItemsType imageItems = OF.createImageItemsType();
						multimediaDescription.setImageItems(imageItems);
						for (String imageId : imageids) {
							imageItems.getImageItem().add(getImageItem(
									sqlSession,
									Attribute.PIC_BASICS,
									RazorHostsConfig.getCopyrightNotice(),
									owner.getName(),
									imageId)
									);
						}
					}
				}

				HotelInfoType hotelInfo = OF.createHotelInfoType();
				hotelDescriptiveContent.setHotelInfo(hotelInfo);
				{
					CategoryCodesType categoryCodes = OF.createCategoryCodesType();
					hotelInfo.setCategoryCodes(categoryCodes);
					{
						//Hotel architectural style
						if (product.hasValue(Attribute.ARC)) {
							List<String> arcs = product.getAttributeValues(Attribute.ARC);
							for (String arc : arcs){
								ArchitecturalStyle architecturalStyle = OF.createCategoryCodesTypeArchitecturalStyle();
								categoryCodes.getArchitecturalStyle().add(architecturalStyle);
								architecturalStyle.setCode(arc);
							}
						}

						//Guest room info
						if (product.hasValue(Attribute.GRI)) {
							List<String> gris = product.getAttributeValues(Attribute.GRI);
							for (String gri : gris){
								GuestRoomInfo guestRoomInfo = OF.createCategoryCodesTypeGuestRoomInfo();
								categoryCodes.getGuestRoomInfo().add(guestRoomInfo);
								guestRoomInfo.setCode(gri);
								guestRoomInfo.setQuantity(SEQUENCE);
							}
						}

						//Hotel category code
						if (product.hasValue(Attribute.ACC)) {
							List<String> accs = product.getAttributeValues(Attribute.ACC);
							for (String acc : accs){
								HotelCategory hc = OF.createCategoryCodesTypeHotelCategory();
								categoryCodes.getHotelCategory().add(hc);
								hc.setCode(acc);
							}
						}

						//Location category code
						if (product.hasValue(Attribute.LOC)) {
							List<String> locs = product.getAttributeValues(Attribute.LOC);
							for (String loc : locs){
								LocationCategory lc = OF.createCategoryCodesTypeLocationCategory();
								categoryCodes.getLocationCategory().add(lc);
								lc.setCode(loc);
							}
						}

						//Segment category code
						if (product.hasValue(Attribute.SEG)) {
							List<String> segs = product.getAttributeValues(Attribute.SEG);
							for (String seg : segs){
								SegmentCategory sc = OF.createCategoryCodesTypeSegmentCategory();
								categoryCodes.getSegmentCategory().add(sc);
								sc.setCode(seg);
							}
						}

						//Hotel Info code
						if (product.hasValue(Attribute.HIC)) {
							HotelInfoCodes hotelInfoCodes = OF.createHotelInfoTypeHotelInfoCodes();
							hotelInfo.setHotelInfoCodes(hotelInfoCodes);
							List<String> hics = product.getAttributeValues(Attribute.HIC);
							for (String hic : hics){
								HotelInfoCode hotelInfoCode = OF.createHotelInfoTypeHotelInfoCodesHotelInfoCode();
								hotelInfoCodes.getHotelInfoCode().add(hotelInfoCode);
								hotelInfoCode.setCode(hic);
							}
						}
					}

					HotelName hotelName = OF.createHotelInfoTypeHotelName();
					hotelInfo.setHotelName(hotelName);
					hotelName.setHotelShortName(product.getName());
					hotelName.setValue(product.getName());

					//							OwnershipManagementInfos omis = of.createHotelInfoTypeOwnershipManagementInfos();
					//							hi.setOwnershipManagementInfos(omis);
					//							OwnershipManagementInfo omi = of.createHotelInfoTypeOwnershipManagementInfosOwnershipManagementInfo();
					//							omis.getOwnershipManagementInfo().add(omi);

					hotelInfo.setPMSSystem(CONTEXT);

					HotelInfoType.Position position = OF.createHotelInfoTypePosition();
					hotelInfo.setPosition(position);
					position.setAltitude(String.valueOf(product.getAltitude()));
					position.setAltitudeUnitOfMeasureCode(Attribute.UOM_METERS);
					position.setLatitude(String.valueOf(product.getLatitude()));
					position.setLongitude(String.valueOf(product.getLongitude()));

					Services hotelInfoServices = OF.createHotelInfoTypeServices();
					hotelInfo.setServices(hotelInfoServices);
					{
						//Business service code
						if (product.hasValue(Attribute.BUS)) {
							List<String> codes = product.getAttributeValues(Attribute.BUS);
							for (String code : codes){
								HotelInfoType.Services.Service service = OF.createHotelInfoTypeServicesService();
								hotelInfoServices.getService().add(service);
								service.setBusinessServiceCode(code);
							}
						}

						//Meal plan code
						if (product.hasValue(Attribute.MPT)) {
							List<String> codes = product.getAttributeValues(Attribute.MPT);
							for (String code : codes){
								HotelInfoType.Services.Service service = OF.createHotelInfoTypeServicesService();
								hotelInfoServices.getService().add(service);
								service.setMealPlanCode(code);
							}
						}

						//Meeting room code
						if (product.hasValue(Attribute.MRC)) {
							List<String> codes = product.getAttributeValues(Attribute.MRC);
							for (String code : codes){
								HotelInfoType.Services.Service service = OF.createHotelInfoTypeServicesService();
								hotelInfoServices.getService().add(service);
								service.setMeetingRoomCode(code);
							}
						}

						//Proximity code
						if (product.hasValue(Attribute.LOC)) {
							List<String> codes = product.getAttributeValues(Attribute.LOC);
							for (String code : codes){
								HotelInfoType.Services.Service service = OF.createHotelInfoTypeServicesService();
								hotelInfoServices.getService().add(service);
								service.setProximityCode(code);
							}
						}
					}
					hotelInfo.setWhenBuilt(product.getValue(ProductValue.WHEN_BUILT));
				}

				Policies policies = OF.createHotelDescriptiveContentTypePolicies();
				hotelDescriptiveContent.setPolicies(policies);

				{
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setCancelPolicy(getCancelPenalties());
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
				}

				if (contract != null) {
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
					CommissionPolicy commissionPolicy = OF.createPoliciesTypePolicyCommissionPolicy();
					policy.setCommissionPolicy(commissionPolicy);
					commissionPolicy.setPercent(new BigDecimal(contract.getDiscount()));
					commissionPolicy.setCurrencyCode(contract.getCurrency());
					commissionPolicy.setTaxInclusive(true);
					commissionPolicy.setEffectiveDate(getXGC(contract.getDate()));
				}

				{
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
					GuaranteePaymentPolicy guaranteePaymentPolicy = OF.createPoliciesTypePolicyGuaranteePaymentPolicy();
					policy.setGuaranteePaymentPolicy(guaranteePaymentPolicy);
					GuaranteePayment guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
					guaranteePaymentPolicy.getGuaranteePayment().add(guaranteePayment);
					guaranteePayment.setNoCardHolderInfoReqInd(false);
					guaranteePayment.setType(Attribute.PMT_GUARANTEE);
					guaranteePayment.setAcceptedPayments(getAcceptedPayments(product, agent));
				}

				if (product.hasValue(Attribute.PET)) {
					//Pet policy
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
					List<String> pets = product.getAttributeValues(Attribute.PET);
					for (String pet : pets) {
						PetsPolicies petsPolicies = OF.createPoliciesTypePolicyPetsPolicies();
						policy.setPetsPolicies(petsPolicies);
						petsPolicies.setPetsAllowedCode(pet);
					}
				}

				{
					//Policy info
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
					PolicyInfo policyInfo = OF.createPoliciesTypePolicyPolicyInfo();
					policy.setPolicyInfo(policyInfo);
					policyInfo.setCheckInTime(product.getValue(ProductValue.CHECK_IN_TIME));
					policyInfo.setCheckOutTime(product.getValue(ProductValue.CHECK_OUT_TIME));
					policyInfo.setInternetGuaranteeRequiredInd(product.getBooleanValue(ProductValue.INTERNET_GUARANTEE_REQUIRED_FLAG));
					policyInfo.setKidsStayFree(product.getBooleanValue(ProductValue.KIDS_STAY_FREE_FLAG));
					policyInfo.setMaxChildAge(product.getIntegerValue(ProductValue.MAX_CHILD_AGE));
					policyInfo.setMinGuestAge(getBigInteger(product.getIntegerValue(ProductValue.MIN_GUEST_AGE)));
					policyInfo.setMinRecommendedGuestAge(getBigInteger(product.getIntegerValue(ProductValue.MIN_RECOMMENDED_GUEST_AGE)));
					policyInfo.setTotalGuestCount(getBigInteger(product.getIntegerValue(ProductValue.ADULT_COUNT)));
					policyInfo.setUsualStayFreeChildPerAdult(getBigInteger(product.getIntegerValue(ProductValue.STAY_FREE_CHILD_PER_ADULT)));
					policyInfo.setUsualStayFreeCutoffAge(getBigInteger(product.getIntegerValue(ProductValue.STAY_FREE_CHILD_CUTOFF_AGE)));
					policy.setRemoval(false);
				}

				{
					//Stay requirements
					Policy policy = OF.createPoliciesTypePolicy();
					policies.getPolicy().add(policy);
					policy.setRemoval(false);
					policy.setLastUpdated(getXGC());
					StayRequirements stayRequirements = OF.createPoliciesTypePolicyStayRequirements();
					policy.setStayRequirements(stayRequirements);
					StayRequirement stayRequirement = OF.createPoliciesTypePolicyStayRequirementsStayRequirement();
					stayRequirements.getStayRequirement().add(stayRequirement);
					stayRequirement.setStayContext(STAY_CONTEXT);
					stayRequirement.setMaxLOS(getBigInteger(product.getIntegerValue(ProductValue.MAX_LENGTH_OF_STAY)));//max length of stay
					stayRequirement.setMinLOS(getBigInteger(product.getIntegerValue(ProductValue.MIN_LENGTH_OF_STAY)));
				}
			}
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("\nhotelDescriptiveInfoRS.result " + rs);
		return rs;
	}

	/**
	 * Creates an OTAHotelRatePlanRQ message with the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.18
	 *
	 * @param productid identity of property for which rate plans are required.
	 * @param start the start date of the rate plans.
	 * @param end the end date of the rate plans.
	 * @param currency the currency code for the rates.
	 * @param pos the point of sale code of the organization making the request.
	 * @return OTAHotelRatePlanRQ requests for the rate plans.
	 */
	public static OTAHotelRatePlanRQ hotelRatePlanRQ(
			String productid,
			String start,
			String end,
			String currency,
			String pos
			) {
		OTAHotelRatePlanRQ rq = OF.createOTAHotelRatePlanRQ();
		rq.setEchoToken("OTAHotelRatePlanRQ " + productid + ", " + start + ", " + end + ", " + currency + ", " + pos);
		LOG.debug("hotelRatePlanRQ " + rq.getEchoToken() + "\n");
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_1000);
		rq.setRequestedCurrency(currency);

		RatePlans ratePlans = OF.createOTAHotelRatePlanRQRatePlans();
		rq.setRatePlans(ratePlans);

		String[] idArray = productid.split(",");
		for (int i = 0; i < idArray.length; i++) {
			RatePlan ratePlan = OF.createOTAHotelRatePlanRQRatePlansRatePlan();
			ratePlans.getRatePlan().add(ratePlan);
			RatePlan.HotelRef hotelRef = OF.createOTAHotelRatePlanRQRatePlansRatePlanHotelRef();
			ratePlan.setHotelRef(hotelRef);
			hotelRef.setHotelCode(idArray[i]);
			hotelRef.setHotelCodeContext(CONTEXT);
			DateRange dateRange = OF.createOTAHotelRatePlanRQRatePlansRatePlanDateRange();
			ratePlan.setDateRange(dateRange);
			dateRange.setStart(start);
			dateRange.setEnd(end);
		}

		LOG.debug("\nhotelRatePlanRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTAHotelRatePlanRS response to the specified OTAHotelRatePlanRQ request .
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.18
	 *
	 * @param rq the OTAHotelRatePlanRQ request.
	 * @return OTAHotelRatePlanRS response.
	 */
	public static OTAHotelRatePlanRS hotelRatePlanRS(OTAHotelRatePlanRQ rq) {
		OTAHotelRatePlanRS rs = OF.createOTAHotelRatePlanRS();
		rs.setEchoToken(rq.getEchoToken());
		LOG.debug(rs.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_2000);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			getParty(sqlSession, rq.getPOS());

			if (rq == null
					|| rq.getRatePlans() == null
					|| rq.getRatePlans().getRatePlan() == null
					|| rq.getRatePlans().getRatePlan().isEmpty()
					) {throw new OtaException("HotelCode", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			RatePlan ratePlanRQ = rq.getRatePlans().getRatePlan().get(0);
			String start = ratePlanRQ.getDateRange().getStart();
			String end = ratePlanRQ.getDateRange().getEnd();
			String currency = rq.getRequestedCurrency();
			Date fromdate = getDate(start);
			Date todate = getDate(end);

			if (todate.before(new Date())) {throw new OtaException("EndDate", Attribute.ERR_END_DATE_IS_IN_THE_PAST, Attribute.EWT_END_DATE_IS_IN_THE_PAST);}
			if (todate.before(fromdate)) {throw new OtaException("StartEndDate", Attribute.ERR_END_DATE_IS_BEFORE_START_DATE, Attribute.EWT_END_DATE_IS_BEFORE_START_DATE);}

			OTAHotelRatePlanRS.RatePlans ratePlans = OF.createOTAHotelRatePlanRSRatePlans();
			rs.setRatePlans(ratePlans);

			for (RatePlan rateplan : rq.getRatePlans().getRatePlan()) {
				HotelRatePlanType ratePlan = OF.createHotelRatePlanType();
				ratePlans.getRatePlan().add(ratePlan);
				HotelRatePlanType.HotelRef hotelRef = OF.createHotelRatePlanTypeHotelRef();
				ratePlan.setHotelRef(hotelRef);
				String productid = rateplan.getHotelRef().getHotelCode();
				hotelRef.setHotelCode(productid);
				hotelRef.setHotelCodeContext(CONTEXT);
				HotelRatePlanType.Rates rates = OF.createHotelRatePlanTypeRates();
				ratePlan.setRates(rates);

				OtaRate action = new OtaRate();
				action.setProductid(productid);
				action.setFromdate(fromdate);
				action.setTodate(todate);
				action.setCurrency(currency);

				ArrayList<OtaRate> otarates = sqlSession.getMapper(PriceMapper.class).otarates(action);
				for (OtaRate otarate : otarates) {
					HotelRatePlanType.Rates.Rate rate = OF.createHotelRatePlanTypeRatesRate();
					rates.getRate().add(rate);

					UniqueIDType uniqueID = OF.createUniqueIDType();
					rate.setUniqueID(uniqueID);
					uniqueID.setID(otarate.getId());
					uniqueID.setIDContext(CONTEXT);
					Double exchangerate = WebService.getRate(sqlSession, otarate.getCurrency(), currency, new Date());
					rate.setCeilingAmount(new BigDecimal(AF().format(exchangerate * otarate.getValue())));
					rate.setFloorAmount(new BigDecimal(AF().format(exchangerate * Math.max(otarate.getValue(), otarate.getMinimum() == null ? 0.0 : otarate.getMinimum()))));
					rate.setCurrencyCode(currency);
					rate.setEnd(getXGC(otarate.getFromdate()).toXMLFormat());
					rate.setStart(getXGC(otarate.getTodate()).toXMLFormat());
					rate.setRateTimeUnit(TimeUnitType.DAY);
					rate.setIsRoom(true);
				}
				SuccessType success = OF.createSuccessType();
				rs.setSuccess(success);
			}
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("\nhotelRatePlanRS.result " + rs);
		return rs;
	}

	/**
	 * Creates an OTAHotelResNotifRQ request  to notify Razor of a reservation using the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.22
	 *
	 * @param productid the ID of the property to be reserved.
	 * @param start the arrival date of the reservation in yyyy-MM-dd format.
	 * @param end the departure date of the reservation in yyyy-MM-dd format.
	 * @param emailaddress the email address of the primary guest for which the reservation is made.
	 * @param familyname the family name of the primary guest.
	 * @param firstname the first name of the primary guest.
	 * @param pos the point of sale code of the organization making the request
	 * @return OTAHotelResNotifRQ request to make a reservation.
	 */
	public static OTAHotelResNotifRQ hotelResNotifRQ(
			String productid,
			String start,
			String end,
			String emailaddress,
			String familyname,
			String firstname,
			String pos
			) {
		OTAHotelResNotifRQ rq = OF.createOTAHotelResNotifRQ();
		rq.setEchoToken("OTAHotelResNotifRQ " +  productid + ", " + start + ", " + end + ", " + emailaddress + ", " + familyname  + ", " + firstname + ", " + pos);
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
		LOG.debug("\nhotelResNotifRQ.result " + rq);
		return rq;
	}

	/**
	 * Creates an OTAHotelResNotifRS response to the specified OTAHotelResNotifRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.22
	 *
	 * @param rq the specified OTAHotelResNotifRQ request.
	 * @return OTAHotelResNotifRS to update the system with reservation confirmation data.
	 */
	public static OTAHotelResNotifRS hotelResNotifRS(OTAHotelResNotifRQ rq) {
		OTAHotelResNotifRS rs = OF.createOTAHotelResNotifRS();
		rs.setEchoToken(rq.getEchoToken());
		LOG.debug("hotelResNotifRS " + rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setWarnings(OF.createWarningsType());
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_4001);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = getParty(sqlSession, rq.getPOS());

			for (HotelReservationType reservationRQ : rq.getHotelReservations().getHotelReservation()) {
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

				if (!Party.isEmailAddress(resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue())
						) {throw new OtaException("EmailAddress_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				RoomStaysType roomStaysRQ = reservationRQ.getRoomStays();
				if (roomStaysRQ == null
						|| roomStaysRQ.getRoomStay() == null
						|| roomStaysRQ.getRoomStay().isEmpty()
						) {throw new OtaException("RoomStay", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				RoomStayType roomStayRQ = roomStaysRQ.getRoomStay().get(0);

				if (roomStayRQ.getBasicPropertyInfo() == null
						|| roomStayRQ.getBasicPropertyInfo().getHotelCode() == null
						|| roomStayRQ.getBasicPropertyInfo().getHotelCode().isEmpty()
						) {throw new OtaException("HotelCode", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				if (!roomStayRQ.getBasicPropertyInfo().getHotelCodeContext().equalsIgnoreCase(CONTEXT)
						) {throw new OtaException("HotelCode_Context", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

				if (roomStayRQ.getBasicPropertyInfo().getChainCode() == null
						|| roomStayRQ.getBasicPropertyInfo().getChainCode().isEmpty()
						) {throw new OtaException("ChainCode", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

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

				Product product = sqlSession.getMapper(ProductMapper.class).read(roomStayRQ.getBasicPropertyInfo().getHotelCode());
				if (product == null) {throw new OtaException("HotelCode", Attribute.ERR_INVALID_HOTEL_ID, Attribute.EWT_INVALID_HOTEL_ID);}

				Party manager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
				if (manager == null) {throw new OtaException("ChainCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				// Create customer party ---------------------------------------------------------------------------
				CustomerType customer = resGuests.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer();
				String emailaddress = customer.getEmail().get(0).getValue();
				Party party = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);

				if (party != null) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), manager.getId(), party.getId());}
				else {
					party = new Party();
					party.setEmailaddress(emailaddress);
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
					if (agent == null) {party.setCreatorid(manager.getId());}
					else {party.setCreatorid(agent.getId());}
					party.setCurrency(DEFAULT_CURRENCY_CODE);
					if (customer.getTelephone() != null && !customer.getTelephone().isEmpty()){
						CustomerType.Telephone phone = customer.getTelephone().get(0);
						party.setDayphone(phone.getAreaCityCode() + phone.getPhoneNumber());
					}
					party.setState(Party.CREATED);
					ArrayList<String>partyTypes = new ArrayList<String>();
					partyTypes.add(Party.Type.Customer.name());
					if (customer.getURL() != null && !customer.getURL().isEmpty()) {party.setWebaddress(customer.getURL().get(0).getValue());}

					sqlSession.getMapper(PartyMapper.class).create(party);
					RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), manager.getId(), party.getId());
//					RelationService.create(sqlSession, Relation.PARTY_TYPE, party.getId(), Party.Type.Customer.name());
//					RelationService.create(sqlSession, Relation.ORGANIZATION_PARTY, manager.getId(), party.getId());
				}

				// Create reservation ---------------------------------------------------------------------------
				Reservation reservation = new Reservation();
				reservation.setOrganizationid(product.getSupplierid());
				reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
				sqlSession.getMapper(ReservationMapper.class).create(reservation);

				if (agent != null){reservation.setAgentid(agent.getId());}

				reservation.setFromdate(arrivaldate);

				if (resGuests.getResGuest().get(0).getArrivalTime() == null){reservation.setArrivaltime(product.getValue(ProductValue.CHECK_IN_TIME));}
				else {reservation.setArrivaltime(getTime(resGuests.getResGuest().get(0).getArrivalTime()));}

				reservation.setActorid(Party.NO_ACTOR);
				reservation.setCustomerid(party.getId());
				reservation.setTodate(departuredate);
				reservation.setDuedate(Time.addDuration(now, 2.0, Time.DAY));

				if (resGuests.getResGuest().get(0).getDepartureTime() == null){reservation.setDeparturetime(product.getValue(ProductValue.CHECK_OUT_TIME));}
				else {reservation.setDeparturetime(getTime(resGuests.getResGuest().get(0).getDepartureTime()));}

				reservation.setDate(now);

				reservation.setDeposit(ReservationService.getDeposit(sqlSession, reservation)); //.getSupplierid(), reservation.getFromdate()));

				reservation.setMarket(MARKET);
				reservation.setOrganizationid(manager.getId());
				reservation.setState(Reservation.State.Provisional.name());

				reservation.setUnit(DEFAULT_UNIT);

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

				CommentType resGuestComments = resGuests.getResGuest().get(0).getComments();
				if (resGuestComments != null && !resGuestComments.getComment().isEmpty()){
					sb.append("\nGuest Comments: ");
					for (CommentType.Comment resGuestComment : resGuestComments.getComment()){
						sb.append(resGuestComment);
					}
				}

				SpecialRequestType specialRequests = resGuests.getResGuest().get(0).getSpecialRequests();
				if (specialRequests != null && !specialRequests.getSpecialRequest().isEmpty()) {
					sb.append("\nSpecial Requests: ");
					for (SpecialRequestType.SpecialRequest specialRequest : specialRequests.getSpecialRequest()){
						sb.append(specialRequest);
					}
				}

				reservation.setNotes(sb.toString());
				reservation.setProductid(product.getId());

				String currency = rq.getPOS().getSource().get(0).getISOCurrency();
				if (currency!= null) {reservation.setCurrency(currency);}
				else if (agent.hasCurrency()) {reservation.setCurrency(agent.getCurrency());}
				else {reservation.setCurrency(DEFAULT_CURRENCY_CODE);}
				ReservationService.computePrice(sqlSession, reservation, null);
//				reservation.setPrice(ReservationService.getPrice(sqlSession, reservation, false)); 
//				reservation.setQuote(ReservationService.getPrice(sqlSession, reservation, true));//yielded
				reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));

				//				TotalType requestTotal = roomStayRQ.getTotal();
				//				BigDecimal requestAmount = requestTotal.getAmountAfterTax();
				//				String requestCurrency = requestTotal.getCurrencyCode();
				//				Double exchangerate = WebService.getRate(sqlSession, requestCurrency, product.getCurrency(), new Date());
				//				Double requestValue = requestAmount.doubleValue() * exchangerate;
				//				if (Math.abs(requestValue - reservation.getQuote()) > 10) {rs.getWarnings().getWarning().add(addWarning("Quote_Changed", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE));}

				ReservationService.reservationUpdate(sqlSession, reservation);
				if (reservation.hasCollisions()) {throw new OtaException("Reservation_Collides", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

				//Acknowledge response
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

				//RACK price
				Rate price = OF.createRateTypeRate();
				rates.getRate().add(price);
				price.setRateDescription(getParagraph("Total RACK per stay"));
				price.setEffectiveDate(getXGC(reservation.getFromdate()));
				price.setExpireDate(getXGC(reservation.getTodate()));
				TotalType rackTotal = OF.createTotalType();
				price.setBase(rackTotal);
				rackTotal.setAmountAfterTax(getBigDecimal(reservation.getPrice()));
				rackTotal.setCurrencyCode(reservation.getCurrency());

				//QUOTE rate
				Rate quote = OF.createRateTypeRate();
				rates.getRate().add(quote);
				quote.setRateDescription(getParagraph("Total QUOTE per stay"));
				quote.setEffectiveDate(getXGC(reservation.getFromdate()));
				quote.setExpireDate(getXGC(reservation.getTodate()));
				TotalType quoteTotal = OF.createTotalType();
				quote.setBase(quoteTotal);
				quoteTotal.setAmountAfterTax(getBigDecimal(reservation.getQuote()));
				quoteTotal.setCurrencyCode(reservation.getCurrency());

				//STO rate
				Rate sto = OF.createRateTypeRate();
				rates.getRate().add(sto);
				sto.setRateDescription(getParagraph("Total STO per stay"));
				sto.setEffectiveDate(getXGC(reservation.getFromdate()));
				sto.setExpireDate(getXGC(reservation.getTodate()));
				TotalType stoTotal = OF.createTotalType();
				sto.setBase(stoTotal);
				stoTotal.setAmountAfterTax(getBigDecimal(reservation.getCost()));
				stoTotal.setCurrencyCode(reservation.getCurrency());

				//Cancellation penalty
				roomStayRS.setCancelPenalties(getCancelPenalties());

				//Reservation rules
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

				//Deposit payment
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
			}
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("hotelResNotifRS.result " + rs + "\n");
		return rs;
	}

	/**
	 * Creates an OTAHotelSearchRQ request using the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.29
	 *
	 * @param criteria the comma delimited list of key=value pairs for each criterion.
	 * @param pos the point of sale code of the organization making the request.
	 * @return OTAHotelSearchRQ request to search for properties that meet the criteria.
	 */
	public static OTAHotelSearchRQ hotelSearchRQ(
			String criteria,
			String pos
			) {

		OTAHotelSearchRQ rq = OF.createOTAHotelSearchRQ();
		rq.setEchoToken("OTAHotelSearchRQ " +  criteria + ", " + pos);
		LOG.debug(rq.getEchoToken());
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_2004);

		HotelSearchCriteriaType hotelSearchCriteria = OF.createHotelSearchCriteriaType();
		rq.setCriteria(hotelSearchCriteria);
		String[] criterionArray = criteria.split(",");

		Criterion criterion = OF.createHotelSearchCriteriaTypeCriterion();
		hotelSearchCriteria.getCriterion().add(criterion);
		String key = null;
		String lastkey = null;
		String value = null;
		for (int i = 0; i < criterionArray.length; i++) {
			String[] nameValue = criterionArray[i].split("=");

			if (nameValue.length == 1 && lastkey != null){
				value = nameValue[0];
				key = lastkey;
			}
			else if (nameValue.length == 2){
				key = nameValue[0];
				value = nameValue[1];
			}
			else if (nameValue.length > 2){continue;}
			lastkey = key;

			if (CRITERION_IMPORTANCE.equalsIgnoreCase(key)) {criterion.setImportanceType(value);}
			else if (CRITERION_EXACT_MATCH.equalsIgnoreCase(key)){criterion.setExactMatch(new Boolean(value));}
			else if (CRITERION_LATITUDE.equalsIgnoreCase(key)){
				if (criterion.getPosition() == null){criterion.setPosition(OF.createItemSearchCriterionTypePosition());}
				criterion.getPosition().setLatitude(value);
			}
			else if (CRITERION_LONGITUDE.equalsIgnoreCase(key)){
				if (criterion.getPosition() == null){criterion.setPosition(OF.createItemSearchCriterionTypePosition());}
				criterion.getPosition().setLongitude(value);
			}
			else if (CRITERION_RADIUS.equalsIgnoreCase(key)){
				Radius radius = OF.createItemSearchCriterionTypeRadius();
				radius.setDistance(value);
				radius.setUnitOfMeasureCode(Attribute.UOM_KILOMETERS);
				criterion.setRadius(radius);
			}
			else if (CRITERION_HOTEL_AREA_ID.equalsIgnoreCase(key)){addHotelRef(criterion).setAreaID(value);} //locationid
			else if (CRITERION_HOTEL_BRAND_CODE.equalsIgnoreCase(key)){addHotelRef(criterion).setBrandCode(value);}//ownerid
			else if (CRITERION_HOTEL_BRAND_NAME.equalsIgnoreCase(key)){addHotelRef(criterion).setBrandName(value);}//owner name
			else if (CRITERION_HOTEL_CHAIN_CODE.equalsIgnoreCase(key)){addHotelRef(criterion).setChainCode(value);}//manager id
			else if (CRITERION_HOTEL_CHAIN_NAME.equalsIgnoreCase(key)){addHotelRef(criterion).setChainName(value);}//manager name
			else if (CRITERION_HOTEL_CITY_CODE.equalsIgnoreCase(key)){addHotelRef(criterion).setHotelCityCode(value);}//location code
			else if (CRITERION_HOTEL_CODE.equalsIgnoreCase(key)){ //productid
				Criterion.HotelRef hotelRef = addHotelRef(criterion);
				hotelRef.setHotelCode(value);
				hotelRef.setHotelCodeContext(CONTEXT);
			}
			else if (CRITERION_HOTEL_NAME.equalsIgnoreCase(key)){addHotelRef(criterion).setHotelName(value);}//product name
			else if (CRITERION_ARRIVAL_DATE.equalsIgnoreCase(key)){
				if (criterion.getStayDateRange() == null){criterion.setStayDateRange(OF.createDateTimeSpanType());}
				criterion.getStayDateRange().setStart(value);
			}
			else if (CRITERION_DEPARTURE_DATE.equalsIgnoreCase(key)){
				if (criterion.getStayDateRange() == null){criterion.setStayDateRange(OF.createDateTimeSpanType());}
				criterion.getStayDateRange().setEnd(value);
			}
			else if (CRITERION_DURATION.equalsIgnoreCase(key)){
				if (criterion.getStayDateRange() == null){criterion.setStayDateRange(OF.createDateTimeSpanType());}
				criterion.getStayDateRange().setDuration(value);
			}
			else if (CRITERION_ADULT_COUNT.equalsIgnoreCase(key) || ProductValue.ADULT_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_ADULT);}
			else if (CRITERION_CHILD_COUNT.equalsIgnoreCase(key) || ProductValue.CHILD_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_CHILD);}
			else if (CRITERION_INFANT_COUNT.equalsIgnoreCase(key) || ProductValue.INFANT_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_INFANT);}
			else if (CRITERION_ROOM_SIZE.equalsIgnoreCase(key)){getRoomStayCandidate(criterion).setRoomType(value);}
			else if (CRITERION_RATE_CURRENCY.equalsIgnoreCase(key)){getRateRange(criterion).setCurrencyCode(value);}
			else if (CRITERION_RATE_MAXIMUM.equalsIgnoreCase(key)){getRateRange(criterion).setMaxRate(getBigDecimal(value));}
			else if (CRITERION_RATE_MINIMUM.equalsIgnoreCase(key)){getRateRange(criterion).setMinRate(getBigDecimal(value));}
			else if (CRITERION_MAX_RESPONSES.equalsIgnoreCase(key)){rq.setMaxResponses(getBigInteger(value));}
			else if (CRITERION_BED.equalsIgnoreCase(key) || Attribute.BED.equalsIgnoreCase(key)){getRoomStayCandidate(criterion).getBedTypeCode().add(value);}
			else if (CRITERION_HAC.equalsIgnoreCase(key) || Attribute.HAC.equalsIgnoreCase(key)){addHotelAmenity(criterion, value);}
			else if (CRITERION_LOC.equalsIgnoreCase(key) || Attribute.LOC.equalsIgnoreCase(key)){addHotelRef(criterion).setHotelName(value);}
			else if (CRITERION_PCT.equalsIgnoreCase(key) || Attribute.PCT.equalsIgnoreCase(key)){addHotelRef(criterion).setPropertyClassCode(value);}
			//NO OTA SUPPORT			else if (CRITERION_PET.equalsIgnoreCase(name) || Attribute.PET.equalsIgnoreCase(name)) {addPetPolicy(criterion, value);}
			else if (CRITERION_PHY.equalsIgnoreCase(key) || Attribute.PHY.equalsIgnoreCase(key)){addAccessibilityCode(criterion, value);} //PHY physically challenged feature code
		}
		LOG.debug("hotelSearchRQ.result " + rq + "\n");
		return rq;
	}

	/**
	 * Creates an OTAHotelSearchRS response to the specified OTAHotelSearchRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.29
	 *
	 * @param rq OTAHotelSearchRQ request to search for properties that meet certain criteria.
	 * @return OTAHotelSearchRS response of properties that meet the criteria.
	 * @throws Throwable a JAXB exception.
	 */
	public static OTAHotelSearchRS hotelSearchRS(OTAHotelSearchRQ rq) throws Throwable {

		JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.err);

		OTAHotelSearchRS rs = OF.createOTAHotelSearchRS();
		LOG.debug(rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setEchoToken(rq.getEchoToken());
		rs.setPOS(rq.getPOS());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_2004);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = getParty(sqlSession, rq.getPOS());

			if (rq.getCriteria() == null
					|| rq.getCriteria().getCriterion() == null
					|| rq.getCriteria().getCriterion().isEmpty()
					) {throw new OtaException("Criteria", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			OtaSearchParameter param = new OtaSearchParameter();
			for (Criterion criterion : rq.getCriteria().getCriterion()) {

				//Accepted payments
				if (criterion.getAcceptedPayments() != null
						&& criterion.getAcceptedPayments().getAcceptedPayment() != null
						&& !criterion.getAcceptedPayments().getAcceptedPayment().isEmpty()){
					List<PaymentFormType> acceptedPayments = criterion.getAcceptedPayments().getAcceptedPayment();
					for (PaymentFormType acceptedPayment : acceptedPayments) {
						param.addOrota(Attribute.PMT, acceptedPayment.getRemark());
						param.addOrota(ProductValue.CARD_CODE, acceptedPayment.getPaymentCard().getCardCode());
					}
				}

				//Hotel amenities
				List<HotelAmenity> hotelAmenities = criterion.getHotelAmenity();
				if (hotelAmenities != null){for (HotelAmenity amenity : hotelAmenities){param.addOta(Attribute.HAC, amenity.getCode());}}

				//Hotel features
				List<HotelFeature> hotelFeatures = criterion.getHotelFeature();
				if (hotelFeatures != null){
					for (HotelFeature hotelFeature : hotelFeatures){
						param.addOta(Attribute.PHY, hotelFeature.getAccessibilityCode());
						param.addOta(Attribute.SEC, hotelFeature.getSecurityFeatureCode());
					}
				}

				//Hotel references
				List<HotelRef> hotelRefs = criterion.getHotelRef();
				if (hotelRefs != null && !hotelRefs.isEmpty()){
					HotelRef hotelRef = hotelRefs.get(0);
					param.setOwnerid(hotelRef.getBrandCode());
					param.setSupplierid(hotelRef.getChainCode());
					param.setProductid(hotelRef.getHotelCode());
					if (hotelRef.getHotelCodeContext() != null
							&& !CONTEXT.equalsIgnoreCase(hotelRef.getHotelCodeContext())
							) {throw new OtaException("Invalid Context", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
					param.setProductname(hotelRef.getHotelName());
					param.addOta(Attribute.ARC, hotelRef.getArchitecturalStyleCode());
					param.addOta(Attribute.LOC, hotelRef.getLocationCategoryCode());
					param.addOta(Attribute.PCT, hotelRef.getPropertyClassCode());
					param.addOta(Attribute.SEG, hotelRef.getSegmentCategoryCode());
				}

				//Rate range
				List<Criterion.RateRange> rateRanges = criterion.getRateRange();
				if (rateRanges != null) {
					for (Criterion.RateRange rateRange : rateRanges) {
						param.setMaxrate(getDouble(rateRange.getMaxRate()));
						param.setCurrency(rateRange.getCurrencyCode());
					}
				}

				//Recreation services
				List<Criterion.Recreation> recreations = criterion.getRecreation();
				if (recreations != null && !recreations.isEmpty()){
					for (Criterion.Recreation recreation : recreations){
						param.addOta(Attribute.REC, recreation.getCode());
					}
				}

				//Availability date range
				if (criterion.getStayDateRange() != null) {
					if (criterion.getStayDateRange().getStart() != null) {param.setArrivaldate(getDate(criterion.getStayDateRange().getStart()));}
					if (criterion.getStayDateRange().getEnd() != null) {param.setDeparturedate(getDate(criterion.getStayDateRange().getEnd()));}
					if (criterion.getStayDateRange().getDuration() != null) {param.setDuration(Double.valueOf(criterion.getStayDateRange().getDuration()));}
				}

				//Physical position
				if (criterion.getPosition() != null
						&& criterion.getPosition().getLatitude() != null
						&& criterion.getPosition().getLongitude() != null) {
					param.setLatitude(Double.valueOf(criterion.getPosition().getLatitude()));
					param.setLongitude(Double.valueOf(criterion.getPosition().getLongitude()));
					if (criterion.getRadius() == null
							|| criterion.getRadius().getDistance() == null
							){
						param.setDistance(10.0);
						param.setDistanceunit(Attribute.UOM_KILOMETERS);
					}
					else {
						param.setDistance(Double.valueOf(criterion.getRadius().getDistance()));
						if (criterion.getRadius().getUnitOfMeasureCode() == null){param.setDistanceunit(Attribute.UOM_KILOMETERS);}
						else {param.setDistanceunit(PositionService.isoUnit(criterion.getRadius().getUnitOfMeasureCode()));}
					}
				}

				//Room stay candidates
				if (criterion.getRoomStayCandidates() != null
						&& criterion.getRoomStayCandidates().getRoomStayCandidate() != null
						&& !criterion.getRoomStayCandidates().getRoomStayCandidate().isEmpty()) {
					List<RoomStayCandidateType> roomStayCandidates = criterion.getRoomStayCandidates().getRoomStayCandidate();
					for (RoomStayCandidateType roomStayCandidate : roomStayCandidates) {
						//Beds
						List<String> beds = roomStayCandidate.getBedTypeCode();
						if (beds != null) {for (String bed : beds){param.addOrota(Attribute.BED, bed);}}

						//Guest counts
						if (roomStayCandidate.getGuestCounts() == null
								|| roomStayCandidate.getGuestCounts().getGuestCount().isEmpty()
								) {param.setGuestcount(2);}
						else {
							for (GuestCount guestCount : roomStayCandidate.getGuestCounts().getGuestCount()) {
								if (Attribute.AQC_ADULT.equalsIgnoreCase(guestCount.getAgeQualifyingCode())) {param.setGuestcount(guestCount.getCount());}
							}
						}
						param.setRoomtype(roomStayCandidate.getRoomType());//Accommodation size
						param.addOta(Attribute.PCT, roomStayCandidate.getRoomClassificationCode());//Property classification
						param.addOta(Attribute.RLT, roomStayCandidate.getRoomLocationCode());//Room location
						param.addOta(Attribute.RVT, roomStayCandidate.getRoomViewCode());//Room view
						//Rooom amenities
						List<RoomAmenityPrefType> roomAmenities = criterion.getRoomAmenity();
						if (roomAmenities != null){for (RoomAmenityPrefType amenity : roomAmenities){param.addOta(Attribute.RMA, amenity.getRoomAmenity());}}
					}
				}

				//Business services
				List<Service> services = criterion.getService();
				if (services != null) {for (Service service : services) {param.addOta(Attribute.BUS, service.getBusinessServiceCode());}}
			}
			if (rq.getMaxResponses() == null) {param.setMaxresponses(Integer.MAX_VALUE);}
			else {param.setMaxresponses(getInteger(rq.getMaxResponses()));}

			//Parameter param is now set ------------------------------------------------------------------------------------------
			//TODO:			if (param.hasPosition()) {
			//				LocationService locationService = LocationService.getInstance();
			//				param.setLocationids(locationService.nearbyids(sqlSession, param.getLatitude(), param.getLongitude(), param.getDistance(), param.getDistanceunit()));
			//			}

			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).hotelsearch(param);
			LOG.debug("\nSEARCH RESULT\n" + products.toString());

			if (products == null || products.isEmpty()) {rs.getWarnings().getWarning().add(addWarning("No_Results", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE));}
			else {
				rs.setCriteria(rq.getCriteria());
				PropertiesType properties = OF.createPropertiesType();
				rs.setProperties(properties);

				for (Product product : products) {

					//product = sqlSession.getMapper(ProductMapper.class).read(product.getId());
					LOG.debug("\n\nPRODUCT 1" + product.toString());

					//TODO:
					//			if (param.hasTimespan() && !net.cbtltd.rest.ReservationService.isAvailable(product.getId(), param.getArrivaldate(), param.getDeparturedate())) {
					//				rs.getWarnings().getWarning().add(addWarning(product.getName() + " Not_Available", Value.ERR_BIZ_RULE, Value.EWT_BIZ_RULE));
					//			}
					Party manager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());

					Property property = OF.createPropertiesTypeProperty();
					properties.getProperty().add(property);
					LOG.debug("\n\nPRODUCT 2");

					LOG.debug("\n\nPRODUCT ADDRESS " + getAddressInfo(manager, Attribute.AUT_BILLING));
					AddressInfoType addressInfo = getAddressInfo(manager, Attribute.AUT_BILLING);
					if (addressInfo != null) {property.setAddress(addressInfo);}

					if (product.hasValue(Attribute.HAC)) {
						//Hotel amenities
						Property.Amenities amenitie = OF.createPropertyValueMatchTypeAmenities();
						property.setAmenities(amenitie);
						Property.Amenities.Amenity amenity = OF.createPropertyValueMatchTypeAmenitiesAmenity();
						amenitie.getAmenity().add(amenity);
						List<String>otas = product.getAttributeValues(Attribute.HAC);
						if (otas != null){for (String ota : otas) {amenity.setPropertyAmenityType(ota);}}
					}

					if (product.hasValue(Attribute.RMA)) {
						//Room amenities
						Property.Amenities amenities = OF.createPropertyValueMatchTypeAmenities();
						property.setAmenities(amenities);
						Property.Amenities.Amenity amenity = OF.createPropertyValueMatchTypeAmenitiesAmenity();
						amenities.getAmenity().add(amenity);
						List<String>otas = product.getAttributeValues(Attribute.RMA);
						if (otas != null){for (String ota : otas) {amenity.setRoomAmenity(ota);}}
					}

					property.setBrandCode(product.getOwnerid());
					//Party owner = sqlSession.getMapper(PartyMapper.class).read(product.getOwnerid());
					//if (owner != null) {property.setBrandName(owner.getName());}
					property.setChainCode(product.getSupplierid());

					ContactNumbers contactNumbers = getContactNumbers(manager);
					if (contactNumbers != null) {property.setContactNumbers(contactNumbers);}
					//					Location location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
					//					if (location != null) {property.setHotelCityCode(location.getCode());}

					property.setHotelCode(product.getId());
					property.setHotelCodeContext(CONTEXT);
					property.setHotelName(product.getName());

					property.setMaxGroupRoomQuantity(getBigInteger(product.getIntegerValue(ProductValue.ADULT_COUNT)));

					//Checking policy
					BasicPropertyInfoType.Policy policy = OF.createBasicPropertyInfoTypePolicy();
					if (product.hasValue(ProductValue.CHECK_IN_TIME)) {
						policy.setCheckInTime(getXGC(TF.parse(product.getValue(ProductValue.CHECK_IN_TIME))));
						property.setPolicy(policy);
					}
					if (product.hasValue(ProductValue.CHECK_OUT_TIME)) {
						policy.setCheckOutTime(getXGC(TF.parse(product.getValue(ProductValue.CHECK_OUT_TIME))));
						property.setPolicy(policy);
					}

					//Position
					Position position = getPosition(product);
					if (position != null) {property.setPosition(position);}

					//Rate range
					RateRange rateRange = OF.createPropertyValueMatchTypeRateRange();
					property.setRateRange(rateRange);

					Reservation reservation = new Reservation();
					reservation.setOrganizationid(product.getSupplierid());
					reservation.setActorid(Party.NO_ACTOR);
					reservation.setAgentid(agent.getId());
					reservation.setFromdate(param.getArrivaldate());
					reservation.setTodate(param.getDeparturedate());
					reservation.setDate(new Date());
					reservation.setDuedate(reservation.getDate());
					reservation.setDonedate(null);
					reservation.setUnit(product.getUnit());
					reservation.setCurrency(product.getCurrency());
					reservation.setState(Reservation.State.Provisional.name());
					reservation.setProductid(product.getId());
					ReservationService.computePrice(sqlSession, reservation, null);
					//					Double rack = ReservationService.getPrice(sqlSession, reservation, false); //not yielded
//					Double quote = ReservationService.getPrice(sqlSession, reservation, true); // yielded
					LOG.debug("\n\nPRODUCT 7");

					Contract contract = sqlSession.getMapper(ContractMapper.class).readbyexample(new Contract(NameId.Type.Reservation.name(), product.getSupplierid(), agent.getId()));
					reservation.setCost(contract == null ? reservation.getQuote() : reservation.getQuote() * (100.0 - contract.getDiscount().doubleValue()) / 100.0);
					LOG.debug("\n\nPRODUCT 8");

					rateRange.setMaxRate(getBigDecimal(reservation.getPrice()));
					rateRange.setFixedRate(getBigDecimal(reservation.getQuote()));
					if (contract == null) {rateRange.setMinRate(getBigDecimal(reservation.getQuote()));}
					else {rateRange.setMinRate(getBigDecimal(contract.getDiscounted(reservation.getQuote())));}
					rateRange.setCurrencyCode(product.getCurrency());
					rateRange.setRateTimeUnit(TimeUnitType.DAY);
					//TODO: all currencies
					//Segment category
					property.setHotelSegmentCategoryCode(product.getValue(Attribute.SEG));
				}
			}
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.err);
		LOG.debug("hotelSearchRS.result " + rs + "\n");
		return rs;
	}

	/**
	 * Creates an OTAPingRQ request using the specified parameter.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.6
	 *
	 * @param pos the point of sale code of the organization making the request
	 * @return OTAPingRQ request to test application connectivity
	 */
	public static OTAPingRQ pingRQ(
			String pos
			) {
		OTAPingRQ rq = OF.createOTAPingRQ();
		rq.setEchoToken("OTAPingRQ " + pos);
		LOG.debug(rq.getEchoToken());
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_1005);
		LOG.debug("pingRQ.result " + rq + "\n");
		return rq;
	}

	/**
	 * Creates an OTAPingRS response to the specified OTAPingRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.6
	 *
	 * @param rq the specified OTAPingRQ request.
	 * @return OTAPingRS response to test message connectivity.
	 */
	public static OTAPingRS pingRS(OTAPingRQ rq) {
		OTAPingRS rs = OF.createOTAPingRS();
		LOG.debug(rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setEchoToken(rq.getEchoToken());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_1007);

		if (rs.getErrors() == null
				|| rs.getErrors().getError() == null
				|| rs.getErrors().getError().isEmpty()) {
			//TODO: check pos
			rs.setErrors(null);
		}
		LOG.debug("\npingRS.result " + rs);
		return rs;
	}

	enum Key {
		/** The address line of the party. */
		ADDRESS, 
		/** The birth date of the party. */
		BIRTHDATE, 
		/** The city where the party is located. */
		CITYNAME, 
		/** The company that employs the party. */
		COMPANYNAME, 
		/** The code of the country where the party is located. */
		COUNTRYCODE, 
		/** The name of the country where the party is located. */
		COUNTRYNAME, 
		/** The code of the currency used where the party is located. */
		CURRENCYCODE, 
		/** The day phone number of the party. */
		DAYPHONE, 
		/** The email address of the party. */
		EMAILADDRESS, 
		/** The fax number of the party. */
		FAXPHONE, 
		/** The given or first name of the party. */
		GIVENNAME, 
		/** The identity number of the party. */
		IDENTITY, 
		/** The code of the language preferred by the party. */
		LANGUAGE, 
		/** The marital status of the party. */
		MARITALSTATUS, 
		/** The mobile phone number of the party. */
		MOBILEPHONE, 
		/** The post office box number of the party. */
		POBOXNUMBER, 
		/** The postal code of the party. */
		POSTALCODE, 
		/** The code of the region (state, province, county) where the party is located. */
		REGIONCODE, 
		/** The name of the region (state, province, county) where the party is located. */
		REGIONNAME, 
		/** The street number where the party is located. */
		STREETNUMBER, 
		/** The surname or family name of the party. */
		SURNAME, 
		/** The descriptive text for the party. */
		TEXT, 
		/** The party type (customer, employee, agent etc). */
		TYPE, 
		/** The URL of the party. */
		URL, 
		/** The VIP status of the party. */
		VIP};

	protected static final HashMap<Key, String> keyvalueMap = new HashMap<Key, String>();
	protected static final String getValue(Key key) {return keyvalueMap.get(key);}

	/**
	 * Creates an OTAProfileCreateRQ request using the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 17.1
	 *
	 * @param keyvalues the list of key-value pairs of the party attributes
	 * @param pos the point of sale code of the organization making the request.
	 * @return OTAProfileCreateRQ request to transfer information about a customer.
	 */
	public static OTAProfileCreateRQ profileCreateRQ(
			String keyvalues,
			String pos
			) {
		OTAProfileCreateRQ rq = OF.createOTAProfileCreateRQ();
		try {
			rq.setEchoToken("OTAProfileCreateRQ " + keyvalues + ", " + pos);
			LOG.debug(rq.getEchoToken());
			rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
			rq.setRetransmissionIndicator(false);
			rq.setSequenceNmbr(SEQUENCE);
			rq.setTarget(TARGET);
			rq.setTimeStamp(getXGC());
			rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
			rq.setVersion(VER_4001);
			rq.setTarget(TARGET);
			rq.setTimeStamp(getXGC());
			rq.setVersion(VER_2004);

			String[] keyvalueArray = keyvalues.split(",");
			for (int i = 0; i < keyvalueArray.length; i++) {
				String[] keyvalue = keyvalueArray[i].split("=");
				if (keyvalue.length == 2){
					String key = keyvalue[0].toUpperCase().trim().replaceAll("^\\s+", "");
					String value = keyvalue[1];
					keyvalueMap.put(Key.valueOf(key), value);
				}
			}
			LOG.debug("keyvalueMap " + keyvalueMap);

			//Unique ID - owner of the profile - equivalent of pos
			UniqueIDType uniqueID = OF.createUniqueIDType();
			rq.getUniqueID().add(uniqueID);
			uniqueID.setID(pos);
			uniqueID.setIDContext(CONTEXT);

			//Profile
			ProfileType profile = OF.createProfileType();
			rq.setProfile(profile);
			profile.setCreateDateTime(getXGC());
			profile.setProfileType(getValue(Key.TYPE));

			if (Attribute.UIT_CUSTOMER.equalsIgnoreCase(getValue(Key.TYPE))) {
				CustomerType customer = OF.createCustomerType();
				profile.setCustomer(customer);

				CustomerType.Address address = OF.createCustomerTypeAddress();
				customer.getAddress().add(address);
				address.getAddressLine().add(getValue(Key.ADDRESS));
				address.setCityName(getValue(Key.CITYNAME));

				StreetNmbr streetNmbr = OF.createAddressTypeStreetNmbr();
				address.setStreetNmbr(streetNmbr);
				streetNmbr.setPOBox(getValue(Key.POBOXNUMBER));
				streetNmbr.setValue(getValue(Key.STREETNUMBER));

				StateProvType region = OF.createStateProvType();
				address.setStateProv(region);
				region.setStateCode(getValue(Key.REGIONCODE));
				region.setValue(getValue(Key.REGIONNAME));

				CountryNameType countryName = OF.createCountryNameType();
				address.setCountryName(countryName);
				countryName.setCode(getValue(Key.COUNTRYCODE));
				countryName.setValue(getValue(Key.COUNTRYNAME));

				customer.setBirthDate(getXGC(getValue(Key.BIRTHDATE)));
				customer.setCurrencyCode(getValue(Key.CURRENCYCODE));
				customer.getPersonName().add(getPersonName(getValue(Key.SURNAME), getValue(Key.GIVENNAME), Attribute.REL_NONE));
				customer.getEmail().add(getCustomerEmail(getValue(Key.EMAILADDRESS), Attribute.EAT_PERSONAL));
				customer.setEmployerInfo(getCompanyName(getValue(Key.IDENTITY), getValue(Key.COMPANYNAME), Attribute.TVS_HOTEL));
				customer.setLanguage(getValue(Key.LANGUAGE));
				customer.setMaritalStatus(getValue(Key.MARITALSTATUS));
				customer.getTelephone().add(getTelephone(getValue(Key.DAYPHONE), Attribute.PTT_VOICE, Attribute.PUT_DAYTIME_CONTACT));
				customer.getTelephone().add(getTelephone(getValue(Key.FAXPHONE), Attribute.PTT_FAX, Attribute.PUT_ELECTRONIC_DOCUMENT_REFERENCE));
				customer.getTelephone().add(getTelephone(getValue(Key.MOBILEPHONE), Attribute.PTT_MOBILE, Attribute.PUT_CONTACT));

				customer.setText(getValue(Key.TEXT));
				customer.setVIPIndicator(Boolean.valueOf(getValue(Key.VIP)));
			}
			else {
				CompanyInfoType company = OF.createCompanyInfoType();
				profile.setCompanyInfo(company); //getCompanyInfo(party, Attribute.TVS_HOTEL));
				company.setCurrencyCode(getValue(Key.CURRENCYCODE));
				company.getCompanyName().add(getCompanyName(getValue(Key.IDENTITY), getValue(Key.COMPANYNAME), Attribute.TVS_HOTEL));
				company.getAddressInfo().add(getAddressInfo(getValue(Key.STREETNUMBER), getValue(Key.CITYNAME), getValue(Key.REGIONCODE), getValue(Key.COUNTRYCODE), getValue(Key.POSTALCODE))); 
				company.getContactPerson().add(getContactPerson(getValue(Key.SURNAME), getValue(Key.GIVENNAME)));
				company.getTelephoneInfo().add(getTelephoneInfo(getValue(Key.DAYPHONE), Attribute.PTT_VOICE, Attribute.PUT_DAYTIME_CONTACT));
				company.getTelephoneInfo().add(getTelephoneInfo(getValue(Key.FAXPHONE), Attribute.PTT_FAX, Attribute.PUT_ELECTRONIC_DOCUMENT_REFERENCE));
				company.getTelephoneInfo().add(getTelephoneInfo(getValue(Key.MOBILEPHONE), Attribute.PTT_MOBILE, Attribute.PUT_CONTACT));
				URLType url = OF.createURLType();
				company.getURL().add(url);
				url.setValue(getValue(Key.URL));
			}
			profile.setShareAllMarketInd(null);
			profile.setShareAllSynchInd(null);
		}
		catch(Throwable x) {rq.setTransactionStatusCode(x.getMessage());}
		return rq;
	}

	/**
	 * Creates an OTAProfileCreateRS response to the specified OTAProfileCreateRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 17.1
	 *
	 * @param rq OTAProfileCreateRQ request to upload detailed business content of a customer
	 * @return OTAProfileCreateRS response to confirm upload
	 */
	public static OTAProfileCreateRS profileCreateRS(OTAProfileCreateRQ rq) {
		OTAProfileCreateRS rs = OF.createOTAProfileCreateRS();
		LOG.debug(rq.getEchoToken() + "\n");
		rs.setEchoToken(rq.getEchoToken());
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_1008);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party manager = getParty(sqlSession, rq.getUniqueID());
			Party party = new Party();
			ProfileType profile = rq.getProfile();
			if (profile == null) {throw new OtaException("Profile", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
			String type = profile.getProfileType();
			if (type == null || type.isEmpty()) {throw new OtaException("Profile Type", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
			if (Attribute.UIT_CUSTOMER.equalsIgnoreCase(type)) {
				CustomerType customer = profile.getCustomer();
				if (customer == null) {throw new OtaException("Customer Profile", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				if (customer.getPersonName() == null || customer.getPersonName().isEmpty()) {throw new OtaException("Customer Name Absent", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				if (customer.getEmail() == null || customer.getEmail().isEmpty()) {throw new OtaException("Customer Email Absent", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				party.setEmailaddress(customer.getEmail().get(0).getValue().trim());
				Party exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(party.getEmailaddress());
				//					LOG.debug("exists " + exists);
				if (exists != null) {throw new OtaException("Customer Email Exists", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
				if (customer.getAddress() != null && !customer.getAddress().isEmpty()) {
					CustomerType.Address address = customer.getAddress().get(0);
					StringBuilder sb = new StringBuilder();
					if (address.getStreetNmbr() != null && address.getStreetNmbr().getPOBox() != null) {sb.append(address.getStreetNmbr().getPOBox()).append("\n");}
					if (address.getStreetNmbr() != null && address.getStreetNmbr().getValue() != null) {sb.append(address.getStreetNmbr().getValue()).append("\n");}
					if (address.getAddressLine() != null && !address.getAddressLine().isEmpty()) {sb.append(address.getAddressLine().get(0)).append("\n");}
					if (address.getCityName() != null && !address.getCityName().isEmpty()) {sb.append(address.getCityName()).append("\n");}
					if (address.getStateProv() != null && address.getStateProv().getValue() != null) {sb.append(address.getStateProv().getValue()).append("\n");}
					if (address.getCountryName() != null && address.getCountryName().getValue() != null) {sb.append(address.getCountryName().getValue()).append("\n");}
					party.setPostaladdress(sb.toString());
					party.setPostalcode(address.getPostalCode());
				}
				party.setBirthdate(getDate(customer.getBirthDate()));
				party.setCurrency(customer.getCurrencyCode());
				party.setLanguage(customer.getLanguage());
				//party.setIdentitynumber(customer.get());

				PersonNameType personName = customer.getPersonName().get(0);
				String givenname = (personName.getGivenName() == null || personName.getGivenName().isEmpty()) ? null : personName.getGivenName().get(0);
				party.setName(personName.getSurname(), givenname);
				List<Telephone> phones = customer.getTelephone();
				if (phones != null && phones.size() >= 1) {party.setDayphone(phones.get(0).getPhoneNumber());}
				if (phones != null && phones.size() >= 2) {party.setFaxphone(phones.get(1).getPhoneNumber());}
				if (phones != null && phones.size() >= 3) {party.setMobilephone(phones.get(2).getPhoneNumber());}
				party.setNotes(customer.getText());
			}
			else {
				CompanyInfoType company = profile.getCompanyInfo();
				if (company == null) {throw new OtaException("Company Profile", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				if (company.getCompanyName() == null || company.getCompanyName().isEmpty()) {throw new OtaException("Company Name Absent", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				if (company.getEmail() == null || company.getEmail().isEmpty()) {throw new OtaException("Company Email Absent", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
				party.setEmailaddress(company.getEmail().get(0).getValue().trim());
				if (sqlSession.getMapper(PartyMapper.class).readbyemailaddress(party.getEmailaddress()) != null) {throw new OtaException("Company Email Exists", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
				if (company.getAddressInfo() != null && !company.getAddressInfo().isEmpty()) {
					AddressInfoType address = company.getAddressInfo().get(0);
					StringBuilder sb = new StringBuilder();
					if (address.getStreetNmbr() != null && address.getStreetNmbr().getPOBox() != null) {sb.append(address.getStreetNmbr().getPOBox()).append("\n");}
					if (address.getStreetNmbr() != null && address.getStreetNmbr().getValue() != null) {sb.append(address.getStreetNmbr().getValue()).append("\n");}
					if (address.getAddressLine() != null && !address.getAddressLine().isEmpty()) {sb.append(address.getAddressLine().get(0)).append("\n");}
					if (address.getCityName() != null && !address.getCityName().isEmpty()) {sb.append(address.getCityName()).append("\n");}
					if (address.getStateProv() != null && address.getStateProv().getValue() != null) {sb.append(address.getStateProv().getValue()).append("\n");}
					if (address.getCountryName() != null && address.getCountryName().getValue() != null) {sb.append(address.getCountryName().getValue()).append("\n");}
					party.setPostaladdress(sb.toString());
					party.setPostalcode(address.getPostalCode());
				}
				CompanyNameType companyName = company.getCompanyName().get(0);
				party.setName(companyName.getValue());
				party.setIdentitynumber(companyName.getCode());
				if (company.getContactPerson() != null && !company.getContactPerson().isEmpty() && company.getContactPerson().get(0) != null) {
					PersonNameType personName = company.getContactPerson().get(0).getPersonName();
					String givenname = (personName.getGivenName() == null || personName.getGivenName().isEmpty()) ? null : personName.getGivenName().get(0);
					party.setExtraname(givenname == null ? personName.getSurname() : personName.getSurname() + ", " + givenname);
				}
				party.setCurrency(company.getCurrencyCode());
				//party.setLanguage(company.getLanguage());
				List<TelephoneInfo> phones = company.getTelephoneInfo();
				if (phones != null && phones.size() >= 1) {party.setDayphone(phones.get(0).getPhoneNumber());}
				if (phones != null && phones.size() >= 2) {party.setFaxphone(phones.get(1).getPhoneNumber());}
				if (phones != null && phones.size() >= 3) {party.setMobilephone(phones.get(2).getPhoneNumber());}
				if (company.getURL() != null && !company.getURL().isEmpty()) {party.setWebaddress(company.getURL().get(0).getValue());}
			}
			party.setCreatorid(manager.getId());
			party.setOrganizationid(manager.getId());
			party.setState(Party.CREATED);
			LOG.debug("Create Party " + party);
			sqlSession.getMapper(PartyMapper.class).create(party);
//			RelationService.create(sqlSession, Relation.ORGANIZATION_PARTY, manager.getId(), party.getId());
			if (Attribute.UIT_CUSTOMER.equalsIgnoreCase(type)) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), manager.getId(), party.getId());}
			else if (Attribute.UIT_COMPANY.equalsIgnoreCase(type)) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), manager.getId(), party.getId());}
			else if (Attribute.UIT_TRAVEL_AGENCY.equalsIgnoreCase(type)) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Agent.name(), manager.getId(), party.getId());}
			else if (Attribute.UIT_TOUR_OPERATOR.equalsIgnoreCase(type)) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Agent.name(), manager.getId(), party.getId());}
			else if (Attribute.UIT_HOTEL.equalsIgnoreCase(type)) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Owner.name(), manager.getId(), party.getId());}
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
			sqlSession.commit();
		}
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch(Throwable x) {rs.setErrors(addMessage(x));}
		finally {sqlSession.close();}
		LOG.debug("profileCreateRS.result " + rs + "\n");
		return rs;
	}


	/**
	 * Creates an OTAReadRQ request using the specified parameters.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.8
	 *
	 * @param id the reference code of the reservation.
	 * @param pos the point of sale code of the organization making the request.
	 * @return OTAReadRQ request to retrieve an existing reservation.
	 */
	public static OTAReadRQ readRQ(
			String id,
			String pos
			) {
		OTAReadRQ rq = OF.createOTAReadRQ();
		rq.setEchoToken("OTAReadRQ " + id + ", " + pos);
		LOG.debug(rq.getEchoToken());
		rq.setPOS(getPOS(pos, CONTEXT));
		rq.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rq.setRetransmissionIndicator(false);
		rq.setSequenceNmbr(SEQUENCE);
		rq.setTarget(TARGET);
		rq.setTimeStamp(getXGC());
		rq.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rq.setVersion(VER_1011);

		ReadRequests readRequests = OF.createOTAReadRQReadRequests();
		rq.setReadRequests(readRequests);

		ReadRequest readRequest = OF.createOTAReadRQReadRequestsReadRequest();
		readRequests.getReadRequest().add(readRequest);
		UniqueIDType uniqueID = OF.createUniqueIDType();
		readRequest.setUniqueID(uniqueID);
		uniqueID.setID(id);
		uniqueID.setIDContext(CONTEXT);
		uniqueID.setType(Attribute.UIT_RESERVATION);
		uniqueID.setURL(null);

		LOG.debug("otaReadRQ.result " + rq + "\n");
		return rq;
	}

	/**
	 * Creates an OTAResRetrieveRS response to the specified OTAReadRQ request.
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.8
	 *
	 * @param rq the specified OTAReadRQ request.
	 * @return OTAResRetrieveRS request to retrieve an existing record
	 */
	public static OTAResRetrieveRS resRetrieveRS(OTAReadRQ rq) {
		OTAResRetrieveRS rs = OF.createOTAResRetrieveRS();
		LOG.debug(rq.getEchoToken() + "\n");
		//rs.setErrors(OF.createErrorsType());
		rs.setEchoToken(rq.getEchoToken());
		rs.setPrimaryLangID(DEFAULT_LANGUAGE_CODE);
		rs.setRetransmissionIndicator(false);
		rs.setSequenceNmbr(SEQUENCE);
		rs.setTarget(TARGET);
		rs.setTimeStamp(getXGC());
		rs.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
		rs.setVersion(VER_5001);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = getParty(sqlSession, rq.getPOS());
			if (agent == null) {}
			else if (rq.getReadRequests() == null
					|| rq.getReadRequests().getReadRequest() == null
					|| rq.getReadRequests().getReadRequest().isEmpty()
					|| rq.getReadRequests().getReadRequest().get(0).getUniqueID() == null
					|| rq.getReadRequests().getReadRequest().get(0).getUniqueID().getID() == null
					|| rq.getReadRequests().getReadRequest().get(0).getUniqueID().getID().isEmpty()
					) {throw new OtaException("UniqueID", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

			if (!rq.getReadRequests().getReadRequest().get(0).getUniqueID().getIDContext().equalsIgnoreCase(CONTEXT)
					) {throw new OtaException("UniqueID_Context", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			if (!rq.getReadRequests().getReadRequest().get(0).getUniqueID().getType().equalsIgnoreCase(Attribute.UIT_RESERVATION)
					) {throw new OtaException("UniqueIDType", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			String name = rq.getReadRequests().getReadRequest().get(0).getUniqueID().getID();
			Reservation reservation = new Reservation();
			reservation.setName(name);
			reservation.setOrganizationid(agent.getId());
			reservation = sqlSession.getMapper(ReservationMapper.class).readbyname(reservation);
			if (reservation == null) {throw new OtaException("UniqueID_Invalid", Attribute.ERR_INVALID_RESERVATION_ID, Attribute.EWT_INVALID_RESERVATION_ID);}

			String productid = reservation.getProductid();
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null) {throw new OtaException("HotelCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			Party guest = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (guest == null) {throw new OtaException("Customer_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			Party manager = sqlSession.getMapper(PartyMapper.class).read(reservation.getOrganizationid());
			if (manager == null) {throw new OtaException("ChainCode_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			//String pos = rq.getPOS().getSource().get(0).getRequestorID().getID();
			//Party agent = sqlSession.getMapper(PartyMapper.class).read(pos);

			if (!agent.hasId(reservation.getAgentid())
					) {throw new OtaException("RequestorID_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}

			//						Contract contract = new Contract(Contract.RESERVATION, manager.getId(), pos);
			//						sqlSession.getMapper(ContractMapper.class).create(contract);
			//						if (contract == null
			//								|| !agent.getId().equalsIgnoreCase(reservation.getAgentid())
			//						) {throw new OtaException("RequestorID_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE));}


			ReservationsList reservationsList = OF.createOTAResRetrieveRSReservationsList();
			rs.setReservationsList(reservationsList);
			HotelReservationType hotelReservation = OF.createHotelReservationType();
			rs.getReservationsList().getHotelReservation().add(hotelReservation);
			hotelReservation.setCreateDateTime(getXGC(reservation.getDate()));
			hotelReservation.setCreatorID(reservation.getAgentid());
			hotelReservation.setResStatus(reservation.getState());

			ResGlobalInfoType resGlobalInfo = OF.createResGlobalInfoType();
			hotelReservation.setResGlobalInfo(resGlobalInfo);
			//rgi.setBookingRules(getBookingRules());
			resGlobalInfo.setCancelPenalties(getCancelPenalties());
			RequiredPaymentsType rps = OF.createRequiredPaymentsType();
			resGlobalInfo.setDepositPayments(rps);

			GuaranteePayment guaranteePayment = OF.createRequiredPaymentsTypeGuaranteePayment();
			rps.getGuaranteePayment().add(guaranteePayment);
			guaranteePayment.setNoCardHolderInfoReqInd(false);
			guaranteePayment.setType(Attribute.PMT_GUARANTEE);
			guaranteePayment.setAcceptedPayments(getAcceptedPayments(product, agent));

			HotelReservationIDsType hotelReservationIDs = OF.createHotelReservationIDsType();
			resGlobalInfo.setHotelReservationIDs(hotelReservationIDs);
			HotelReservationID hotelReservationID = OF.createHotelReservationIDsTypeHotelReservationID();
			hotelReservationIDs.getHotelReservationID().add(hotelReservationID);
			hotelReservationID.setResIDDate(getXGC(reservation.getDate()));
			hotelReservationID.setResIDType(Attribute.UIT_RESERVATION);
			hotelReservationID.setResIDValue(reservation.getName());

			SpecialRequestType specialRequests = OF.createSpecialRequestType();
			resGlobalInfo.setSpecialRequests(specialRequests);
			SpecialRequest specialRequest = OF.createSpecialRequestTypeSpecialRequest();
			specialRequests.getSpecialRequest().add(specialRequest);
			specialRequest.setLanguage(DEFAULT_LANGUAGE_CODE);
			FormattedTextTextType formattedTextText = OF.createFormattedTextTextType();
			specialRequest.getTextOrImageOrURL().add(OF.createParagraphTypeText(formattedTextText));
			formattedTextText.setFormatted(false);
			formattedTextText.setValue(reservation.getNotes());

			TimeSpan timeSpan = OF.createResCommonDetailTypeTimeSpan();
			resGlobalInfo.setTimeSpan(timeSpan);
			//			timeSpan.setStart(DF.format(reservation.getFromdate()));
			//			timeSpan.setEnd(DF.format(reservation.getTodate()));
			timeSpan.setStart(getXGC(reservation.getFromdate()).toXMLFormat());
			timeSpan.setEnd(getXGC(reservation.getTodate()).toXMLFormat());
			timeSpan.setDuration(String.valueOf(reservation.getDuration(Time.DAY)));

			//RACK rate
			TotalType rackTotal = OF.createTotalType();
			rackTotal.setAmountAfterTax(getBigDecimal(reservation.getQuote()));
			rackTotal.setCurrencyCode(reservation.getCurrency());

			//STO rate
			TotalType stoTotal = OF.createTotalType();
			stoTotal.setAmountAfterTax(getBigDecimal(reservation.getCost()));
			stoTotal.setCurrencyCode(reservation.getCurrency());

			resGlobalInfo.setTotal(rackTotal);

			ResGuestsType resGuests = OF.createResGuestsType();
			hotelReservation.setResGuests(resGuests);
			ResGuest resGuest = OF.createResGuestsTypeResGuest();
			resGuests.getResGuest().add(resGuest);
			resGuest.setArrivalTime(getXGC(reservation.getArrivaltime()));
			resGuest.setCodeContext(CONTEXT);
			resGuest.setDepartureTime(getXGC(reservation.getDeparturetime()));
			ProfilesType profiles = OF.createProfilesType();
			resGuest.setProfiles(profiles);
			ProfileInfo ps = OF.createProfilesTypeProfileInfo();
			resGuest.getProfiles().getProfileInfo().add(ps);
			ProfileInfo profileInfo = OF.createProfilesTypeProfileInfo();
			profiles.getProfileInfo().add(profileInfo);
			ProfileType profile = OF.createProfileType();
			profileInfo.setProfile(profile);
			profile.setProfileType(Attribute.PRT_CUSTOMER);
			CustomerType customer = OF.createCustomerType();
			profile.setCustomer(customer);
			PersonNameType personName = OF.createPersonNameType();
			customer.getPersonName().add(personName);
			personName.setSurname(guest.getFamilyName());
			personName.getGivenName().add(guest.getFirstName());
			customer.getEmail().add(getCustomerEmail(guest.getEmailaddress(),Attribute.EAT_PERSONAL));
			SuccessType success = OF.createSuccessType();
			rs.setSuccess(success);
		} 
		catch (OtaException x) {rs.setErrors(addError(x));}
		catch (Throwable x) {LOG.error(x.getMessage());}
		finally {sqlSession.close();}
		LOG.debug("otaReadRS.result " + rs + "\n");
		return rs;
	}

}
