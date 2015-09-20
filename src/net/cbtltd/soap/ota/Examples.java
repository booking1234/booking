/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.shared.Attribute;
import net.cbtltd.soap.ota.server.AddressType.StreetNmbr;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType.AvailRequestSegment;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType;
import net.cbtltd.soap.ota.server.CompanyInfoType;
import net.cbtltd.soap.ota.server.CompanyInfoType.TelephoneInfo;
import net.cbtltd.soap.ota.server.CompanyNameType;
import net.cbtltd.soap.ota.server.ContactPersonType;
import net.cbtltd.soap.ota.server.CountryNameType;
import net.cbtltd.soap.ota.server.CustomerType;
import net.cbtltd.soap.ota.server.CustomerType.Email;
import net.cbtltd.soap.ota.server.CustomerType.Telephone;
import net.cbtltd.soap.ota.server.DateTimeSpanType;
import net.cbtltd.soap.ota.server.FreeTextType;
import net.cbtltd.soap.ota.server.GuestCountType;
import net.cbtltd.soap.ota.server.GuestCountType.GuestCount;
import net.cbtltd.soap.ota.server.HotelReservationType;
import net.cbtltd.soap.ota.server.HotelReservationsType;
import net.cbtltd.soap.ota.server.HotelSearchCriteriaType;
import net.cbtltd.soap.ota.server.HotelSearchCriteriaType.Criterion;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelAmenity;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelFeature;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.RoomStayCandidates;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.HotelRef;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.Radius;
import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTACancelRQ.Reasons;
import net.cbtltd.soap.ota.server.OTACancelRQ.UniqueID;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ.AvailRequestSegments;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans.RatePlan;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.OTAPingRQ;
import net.cbtltd.soap.ota.server.OTAProfileCreateRQ;
import net.cbtltd.soap.ota.server.OTAReadRQ;
import net.cbtltd.soap.ota.server.OTAReadRQ.ReadRequests;
import net.cbtltd.soap.ota.server.OTAReadRQ.ReadRequests.ReadRequest;
import net.cbtltd.soap.ota.server.ObjectFactory;
import net.cbtltd.soap.ota.server.OtaException;
import net.cbtltd.soap.ota.server.POSType;
import net.cbtltd.soap.ota.server.PersonNameType;
import net.cbtltd.soap.ota.server.ProfileType;
import net.cbtltd.soap.ota.server.ProfilesType;
import net.cbtltd.soap.ota.server.ProfilesType.ProfileInfo;
import net.cbtltd.soap.ota.server.ResGuestsType;
import net.cbtltd.soap.ota.server.ResGuestsType.ResGuest;
import net.cbtltd.soap.ota.server.RoomStayCandidateType;
import net.cbtltd.soap.ota.server.RoomStaysType;
import net.cbtltd.soap.ota.server.SourceType;
import net.cbtltd.soap.ota.server.SourceType.RequestorID;
import net.cbtltd.soap.ota.server.StateProvType;
import net.cbtltd.soap.ota.server.URLType;
import net.cbtltd.soap.ota.server.UniqueIDType;

import org.apache.log4j.Logger;

/**
 * The Class Examples is demonstration code for creating SOAP request documents that conform to 
 * the OTA2008B XML schema and that use Java Architecture for XML Binding (JAXB) generated classes 
 */
public class Examples {

	private final static BigDecimal VER_1000 = new BigDecimal("1.000");
	private final static BigDecimal VER_1005 = new BigDecimal("1.005");
	private final static BigDecimal VER_1009 = new BigDecimal("1.009");
	private final static BigDecimal VER_1010 = new BigDecimal("1.010");
	private final static BigDecimal VER_1011 = new BigDecimal("1.011");
	private final static BigDecimal VER_2004 = new BigDecimal("2.004");
	private final static BigDecimal VER_4001 = new BigDecimal("4.001");
	private final static BigInteger SEQUENCE = new BigInteger("1");


	private final static String CONTEXT = "RZ";
	private final static String CRITERION_ADULT_COUNT = "Adults";
	private final static String CRITERION_ARRIVAL_DATE = "ArrivalDate";
	private final static String CRITERION_BED = "BedType";
	private final static String CRITERION_CHILD_COUNT = "Children";
	private final static String CRITERION_DEPARTURE_DATE = "DepartureDate";
	private final static String CRITERION_DURATION = "Duration";	
	private final static String CRITERION_HAC = "Amenity";
	private final static String CRITERION_HOTEL_AREA_ID = "AreaID";
	private final static String CRITERION_HOTEL_BRAND_CODE = "BrandCode";
	private final static String CRITERION_HOTEL_BRAND_NAME = "BrandName";
	private final static String CRITERION_HOTEL_CHAIN_CODE = "ChainCode";
	private final static String CRITERION_HOTEL_CHAIN_NAME = "ChainName";
	private final static String CRITERION_HOTEL_CITY_CODE = "CityCode";
	private final static String CRITERION_HOTEL_CODE = "HotelCode";
	private final static String CRITERION_HOTEL_NAME = "HotelName";
	private final static String CRITERION_INFANT_COUNT = "Infants";
	private final static String CRITERION_LATITUDE = "Latitude";
	private final static String CRITERION_LOC = "LocationCategory";
	private final static String CRITERION_LONGITUDE = "Longitude";
	private final static String CRITERION_MAX_RESPONSES = "MaxResponses";
	private final static String CRITERION_PCT = "PropertyClass";
	private final static String CRITERION_PHY = "Accessibility";
	private final static String CRITERION_RADIUS = "Radius";
	private final static String CRITERION_RATE_CURRENCY = "Currency";
	private final static String CRITERION_RATE_MAXIMUM = "MaxRate";
	private final static String CRITERION_RATE_MINIMUM = "MinRate";
	private final static String DEFAULT_CURRENCY_CODE = "ZAR";
	private final static String DEFAULT_LANGUAGE_CODE = "en";
	private final static String TARGET = "Production";
	private final static String TRANSACTION_IDENTIFIER = "1";
	private final static String TYP_CONTACT_PERSON = "1";
	private final static String UIT_RESERVATION = "14";

	private static final Logger LOG = Logger.getLogger(Examples.class.getName());
	private static final ObjectFactory OF = new ObjectFactory();

	/**
	 * @param id of reservation to be cancelled
	 * @param reason for cancellation
	 * @param pos code in CONTEXT (RZ for Razor) of the request originator
	 * @return OTACancelRS cancel reservation request
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.2
	 */
	public static OTACancelRQ cancelRQ(
			String id, 
			String reason, 
			String pos
	) throws Exception {

		OTACancelRQ rq = OF.createOTACancelRQ();
		rq.setEchoToken("OTACancelRQ " + id + ", " + reason + ", " + pos);
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
		uid.setID(id);
		uid.setIDContext(CONTEXT);
		uid.setType(UIT_RESERVATION);
		return rq;
	}

	/**
	 * @param productids list of comma delimited properties for which availability is requested
	 * @param fromdate date from which the availability is requested
	 * @param todate date to which the availability is requested
	 * @param pos in context of CONTEXT (RZ for Razor) making the request
	 * @return OTAHotelAvailRQ hotel availability request
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.1
	 */
	public static OTAHotelAvailRQ hotelAvailRQ(
			String productids,
			String fromdate,
			String todate,
			String pos
	) {
		OTAHotelAvailRQ rq = OF.createOTAHotelAvailRQ();
		rq.setEchoToken("OTAHotelAvailRQ " + productids + ", " + fromdate + ", " + todate + ", " + pos);
		LOG.debug(rq.getEchoToken());
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
			DateTimeSpanType stayDateRange = OF.createDateTimeSpanType();
			criterion.setStayDateRange(stayDateRange);
			criterion.getStayDateRange().setStart(fromdate);
			criterion.getStayDateRange().setEnd(todate);
		}
		LOG.debug("\nhotelAvailRQ.result " + rq);
		return rq;
	}

	/**
	 * @param productids list of properties for which descriptive information is requested
	 * @param pos in context of CONTEXT (RZ for Razor) making the request
	 * @return OTAHotelDescriptiveInfoRQ hotel descriptive info request
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.7
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
			rq.getHotelDescriptiveInfos().getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
		}
		LOG.debug("\nhotelDescriptiveInfoRQ.result " + rq);
		return rq;
	}

	/**
	 * @param productid identity of property for which rate plan is to be uploaded
	 * @param pos code in CONTEXT (RZ for Razor) of the request originator
	 * @return OTAHotelRatePlanRQ requests for a rate plan
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.18
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
		RatePlan ratePlan = OF.createOTAHotelRatePlanRQRatePlansRatePlan();
		ratePlans.getRatePlan().add(ratePlan);

		RatePlan.HotelRef hotelRef = OF.createOTAHotelRatePlanRQRatePlansRatePlanHotelRef();
		ratePlan.setHotelRef(hotelRef);
		hotelRef.setHotelCode(productid);
		hotelRef.setHotelCodeContext(CONTEXT);

		DateRange dateRange = OF.createOTAHotelRatePlanRQRatePlansRatePlanDateRange();
		ratePlan.setDateRange(dateRange);
		dateRange.setStart(start);
		dateRange.setEnd(end);

		LOG.debug("\nhotelRatePlanRQ.result " + rq);
		return rq;
	}

	/**
	 * Notifies the system of a reservation
	 * @param productid of property to be reserved
	 * @param start date of the reservation in yyyy-MM-dd format
	 * @param end date of the reservation in yyyy-MM-dd format
	 * @param emailaddress of the guest for which the reservation is made
	 * @param familyname of the guest
	 * @param firstname of the primary guest
	 * @param pos code in CONTEXT (RZ for Razor) of the request originator
	 * @return OTAHotelResNotifRQ request to make a reservation
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.22
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
		roomStay.getWarningRPH().add("1");

		BasicPropertyInfoType basicPropertyInfo = OF.createBasicPropertyInfoType();
		roomStay.setBasicPropertyInfo(basicPropertyInfo);
		basicPropertyInfo.setHotelCode(productid);
		basicPropertyInfo.setHotelCodeContext(CONTEXT);

		DateTimeSpanType dateTimeSpan = OF.createDateTimeSpanType();
		roomStay.setTimeSpan(dateTimeSpan);
		dateTimeSpan.setStart(start);
		dateTimeSpan.setEnd(end);

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
	 * @param criteria as comma delimited list of key=value pairs for each criterion
	 * @param pos code in CONTEXT (RZ for Razor) of the request originator
	 * @return OTAHotelSearchRQ request to search for properties that meet the criteria
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 10.29
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

			if (CRITERION_LATITUDE.equalsIgnoreCase(key)){
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
			else if (CRITERION_ADULT_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_ADULT);}
			else if (CRITERION_CHILD_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_CHILD);}
			else if (CRITERION_INFANT_COUNT.equalsIgnoreCase(key)){addGuestCount(criterion, new Integer(value), Attribute.AQC_INFANT);}
			else if (CRITERION_RATE_CURRENCY.equalsIgnoreCase(key)){getRateRange(criterion).setCurrencyCode(value);}
			else if (CRITERION_RATE_MAXIMUM.equalsIgnoreCase(key)){getRateRange(criterion).setMaxRate(getBigDecimal(value));}
			else if (CRITERION_RATE_MINIMUM.equalsIgnoreCase(key)){getRateRange(criterion).setMinRate(getBigDecimal(value));}
			else if (CRITERION_MAX_RESPONSES.equalsIgnoreCase(key)){rq.setMaxResponses(getBigInteger(value));}
			else if (CRITERION_BED.equalsIgnoreCase(key) || Attribute.BED.equalsIgnoreCase(key)){getRoomStayCandidate(criterion).getBedTypeCode().add(value);}
			else if (CRITERION_HAC.equalsIgnoreCase(key) || Attribute.HAC.equalsIgnoreCase(key)){addHotelAmenity(criterion, value);}
			else if (CRITERION_LOC.equalsIgnoreCase(key) || Attribute.LOC.equalsIgnoreCase(key)){addHotelRef(criterion).setHotelName(value);}
			else if (CRITERION_PCT.equalsIgnoreCase(key) || Attribute.PCT.equalsIgnoreCase(key)){addHotelRef(criterion).setPropertyClassCode(value);}
			else if (CRITERION_PHY.equalsIgnoreCase(key) || Attribute.PHY.equalsIgnoreCase(key)){addAccessibilityCode(criterion, value);} //PHY physically challenged feature code
		}
		LOG.debug("hotelSearchRQ.result " + rq + "\n");
		return rq;
	}

	/**
	 * @param pos code in CONTEXT (RZ for Razor) of the request originator
	 * @return OTAPingRQ request to test application connectivity
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.6
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
	 * @param values of profile as a comma delimited list of key value pairs
	 * @param pos in context of CONTEXT (RZ for Razor) making the request
	 * @return OTAProfileCreateRQ request to transfer informatin about a customer
	 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 17.1
	 */
	enum Key {ADDRESS, BIRTHDATE, CITYNAME, COMPANYNAME, COUNTRYCODE, COUNTRYNAME, CURRENCYCODE, DAYPHONE, EMAILADDRESS, FAXPHONE, GIVENNAME, 
		IDENTITY, LANGUAGE, MARITALSTATUS, MOBILEPHONE, POBOXNUMBER, POSTALCODE, REGIONCODE, REGIONNAME, STREETNUMBER, SURNAME, TEXT, TYPE, URL, VIP};

		private static final HashMap<Key, String> keyvalueMap = new HashMap<Key, String>();

		private static final String getValue(Key key) {
			return keyvalueMap.get(key);
		}

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
			catch (Throwable x) {x.printStackTrace();}
			return rq;
		}


		/**
		 * @param id of reservation
		 * @param pos in context of CONTEXT (RZ for Razor) making the request
		 * @return OTAReadRQ request to retrieve an existing reservation
		 * @see OTAMessageUsersGuide2008BV1.0.pdf Section 3.8
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
		 * General helper functions
		 * @param value as String
		 * @return BigInteger value
		 */
		private static BigInteger getBigInteger(String value) {
			if (value == null) {return null;}
			else {return new BigInteger(value);}
		}

		/**
		 * @param value as String
		 * @return BigDecimal value
		 */
		private static BigDecimal getBigDecimal(String value) {
			if (value == null) {return null;}
			else {return new BigDecimal(value);}
		}

		/**
		 * @param value as Double
		 * @return BigDecimal value
		 */
		private static BigDecimal getBigDecimal(Double value) {
			if (value == null) {return null;}
			else {return new BigDecimal(String.valueOf(value));}
		}

		/**
		 * @param emailaddress as String
		 * @param eat from OTA EAT code list
		 * @return customer email object
		 */
		private static Email getCustomerEmail(String emailaddress, String eat){
			Email email = OF.createCustomerTypeEmail();
			email.setDefaultInd(true);
			email.setEmailType(eat);
			email.setRemark(null);
			email.setRPH(null);
			email.setShareMarketInd(null);
			email.setShareSynchInd(null);
			email.setValue(emailaddress);
			return email;
		}

		/**
		 * @param text as String
		 * @param language code from ISO two character code list
		 * @return free text object
		 */
		private static FreeTextType getFreeText(String text, String language) {
			FreeTextType freeText = OF.createFreeTextType();
			freeText.setLanguage(language);
			freeText.setValue(text);
			return freeText;
		}

		/**
		 * @return current time as XML Gregorian calendar
		 */
		private static XMLGregorianCalendar getXGC () {
			return getXGC(new Date());
		}

		/**
		 * @param date as java.util.Date
		 * @return date as XML Gregorian calendar
		 */
		private static XMLGregorianCalendar getXGC (Date date) {
			XMLGregorianCalendar xgc = null; 
			try { 
				xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
				xgc.setMonth(cal.get(Calendar.MONTH)+1); // +1 because XMLGregorianCalendar is from 1 to 12 while Calendar.MONTH is from 0 to 11 !!! 
				xgc.setYear(cal.get(Calendar.YEAR)); 
				xgc.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)); 
			} catch (DatatypeConfigurationException e) { e.printStackTrace();   }
			return xgc;
		}

		/**
		 * Search helper functions
		 */
		private static void addGuestCount(Criterion criterion, Integer value, String aqc) {
			GuestCountType guestCounts = getRoomStayCandidate(criterion).getGuestCounts();
			if (guestCounts == null) {
				guestCounts = OF.createGuestCountType();
				getRoomStayCandidate(criterion).setGuestCounts(guestCounts);
			}
			guestCounts.setIsPerRoom(true);
			GuestCount guestCount = OF.createGuestCountTypeGuestCount();
			guestCounts.getGuestCount().add(guestCount);
			guestCount.setCount(value);
			guestCount.setAgeQualifyingCode(aqc);
		}

		private static void addHotelAmenity(Criterion criterion, String code) {
			HotelAmenity hotelAmenity = OF.createHotelSearchCriterionTypeHotelAmenity();
			criterion.getHotelAmenity().add(hotelAmenity);
			hotelAmenity.setCode(code);
		}

		private static HotelRef addHotelRef(Criterion criterion) {
			HotelRef hotelRef = OF.createItemSearchCriterionTypeHotelRef();
			criterion.getHotelRef().add(hotelRef);
			return hotelRef;
		}

		private static void addAccessibilityCode(Criterion criterion, String feature ){ //PHY physically challenged feature code
			HotelFeature hotelFeature = OF.createHotelSearchCriterionTypeHotelFeature();
			criterion.getHotelFeature().add(hotelFeature);
			hotelFeature.setAccessibilityCode(feature);
		}

		private static HotelSearchCriterionType.RateRange getRateRange(Criterion criterion) {
			if (criterion.getRateRange() == null
					|| criterion.getRateRange().isEmpty()) {
				HotelSearchCriterionType.RateRange rateRange = OF.createHotelSearchCriterionTypeRateRange();
				criterion.getRateRange().add(rateRange);
				rateRange.setCurrencyCode(DEFAULT_CURRENCY_CODE);
				rateRange.setMinRate(getBigDecimal(0.0));
			}
			return criterion.getRateRange().get(0);
		}

		private static RoomStayCandidateType getRoomStayCandidate(Criterion criterion) {
			RoomStayCandidates roomStayCandidates = criterion.getRoomStayCandidates();
			if (roomStayCandidates == null){
				roomStayCandidates = OF.createHotelSearchCriterionTypeRoomStayCandidates();
				criterion.setRoomStayCandidates(roomStayCandidates);
				roomStayCandidates.getRoomStayCandidate().add(OF.createRoomStayCandidateType());
			}
			return roomStayCandidates.getRoomStayCandidate().get(0);
		}
		private static final CompanyNameType getCompanyName(String code, String name, String tvs) {
			CompanyNameType companyName = OF.createCompanyNameType();
			companyName.setCode(code);
			companyName.setCodeContext(CONTEXT);
			companyName.setValue(name);
			companyName.setDepartment(null);
			companyName.setDivision(null);
			companyName.setTravelSector(tvs);
			return companyName;
		}

		private static final CompanyInfoType.AddressInfo getAddressInfo(String streetnumber, String cityname, String regioncode, String countrycode, String postalcode) {
			CompanyInfoType.AddressInfo addressInfo = OF.createCompanyInfoTypeAddressInfo();
			addressInfo.setCityName(cityname);
			CountryNameType countryName = OF.createCountryNameType();
			addressInfo.setCountryName(countryName);
			countryName.setCode(countrycode);
			StateProvType stateProv = OF.createStateProvType();
			addressInfo.setStateProv(stateProv);
			stateProv.setStateCode(regioncode);
			addressInfo.setPostalCode(postalcode);
			addressInfo.setShareMarketInd(null);
			addressInfo.setShareSynchInd(null);
			addressInfo.setUseType(Attribute.AUT_CONTACT);//AUT OTA code
			StreetNmbr streetNmbr = OF.createAddressTypeStreetNmbr();
			addressInfo.setStreetNmbr(streetNmbr);
			streetNmbr.setValue(streetnumber);
			return addressInfo;
		}

		private static final ContactPersonType getContactPerson(String surname, String givenname) {
			ContactPersonType contactPerson = OF.createContactPersonType();
			contactPerson.setContactType(TYP_CONTACT_PERSON);
			contactPerson.setDefaultInd(true);
			contactPerson.setPersonName(getPersonName(surname, givenname, TYP_CONTACT_PERSON));
			contactPerson.setRelation(Attribute.REL_NONE);
			contactPerson.setShareMarketInd(null);
			contactPerson.setShareSynchInd(null);
			return contactPerson;
		}

		private static PersonNameType getPersonName(String surname, String givenname, String typ) {
			PersonNameType personName = OF.createPersonNameType();
			personName.setNameType(typ);
			personName.setShareMarketInd(null);
			personName.setShareSynchInd(null);
			personName.setSurname(surname);
			personName.getGivenName().add(givenname);
			return personName;
		}

		/**
		 * @param pos in context of CONTEXT (RZ for Razor) at the point of sale
		 * @param context of pos
		 * @return point of sale (POS) object
		 */
		private static POSType getPOS(
				String pos,
				String context
		) {
			POSType POS = OF.createPOSType();
			SourceType source = OF.createSourceType();
			POS.getSource().add(source);
			RequestorID requestorID = OF.createSourceTypeRequestorID();
			source.setRequestorID(requestorID);
			requestorID.setID(pos);
			requestorID.setIDContext(context);
			return POS;
		}

		private static final Telephone getTelephone(String number, String ptt, String put) {
			Telephone telephone = OF.createCustomerTypeTelephone();
			telephone.setPhoneNumber(number);
			telephone.setPhoneTechType(ptt);
			telephone.setPhoneUseType(put);
			return telephone;
		}

		private static final TelephoneInfo getTelephoneInfo(String number, String ptt, String put) {
			TelephoneInfo telephone = OF.createCompanyInfoTypeTelephoneInfo();
			telephone.setPhoneNumber(number);
			telephone.setPhoneTechType(ptt);
			telephone.setPhoneUseType(put);
			return telephone;
		}

		private static final XMLGregorianCalendar getXGC(String xgcstring) { //source in XGC format
			try {return DatatypeFactory.newInstance().newXMLGregorianCalendar(xgcstring);}
			catch (DatatypeConfigurationException e) {throw new OtaException("Date_Format_Invalid", Attribute.ERR_INVALID_DATE_FORMAT, Attribute.EWT_INVALID_DATE_FORMAT);}
		}

}
