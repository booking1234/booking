/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RegionMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeAction;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.shared.product.ProductValue;
import net.cbtltd.soap.ota.server.AcceptedPaymentsType;
import net.cbtltd.soap.ota.server.AddressInfoType;
import net.cbtltd.soap.ota.server.AddressType.StreetNmbr;
import net.cbtltd.soap.ota.server.AddressesType;
import net.cbtltd.soap.ota.server.AddressesType.Address;
import net.cbtltd.soap.ota.server.AmountPercentType;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType.ContactNumbers;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType.Position;
import net.cbtltd.soap.ota.server.CancelInfoRSType.CancelRules;
import net.cbtltd.soap.ota.server.CancelPenaltiesType;
import net.cbtltd.soap.ota.server.CancelPenaltyType;
import net.cbtltd.soap.ota.server.CancelRuleType;
import net.cbtltd.soap.ota.server.CompanyInfoType;
import net.cbtltd.soap.ota.server.CompanyInfoType.TelephoneInfo;
import net.cbtltd.soap.ota.server.CompanyNameType;
import net.cbtltd.soap.ota.server.ContactPersonType;
import net.cbtltd.soap.ota.server.CountryNameType;
import net.cbtltd.soap.ota.server.CustomerType.Email;
import net.cbtltd.soap.ota.server.CustomerType.Telephone;
import net.cbtltd.soap.ota.server.DateTimeSpanType;
import net.cbtltd.soap.ota.server.DirectBillType;
import net.cbtltd.soap.ota.server.EmailType;
import net.cbtltd.soap.ota.server.EmailsType;
import net.cbtltd.soap.ota.server.ErrorType;
import net.cbtltd.soap.ota.server.ErrorsType;
import net.cbtltd.soap.ota.server.FormattedTextTextType;
import net.cbtltd.soap.ota.server.FreeTextType;
import net.cbtltd.soap.ota.server.GuestCountType;
import net.cbtltd.soap.ota.server.GuestCountType.GuestCount;
import net.cbtltd.soap.ota.server.HotelSearchCriteriaType.Criterion;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelAmenity;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.HotelFeature;
import net.cbtltd.soap.ota.server.HotelSearchCriterionType.RoomStayCandidates;
import net.cbtltd.soap.ota.server.ImageDescriptionType;
import net.cbtltd.soap.ota.server.ImageDescriptionType.ImageFormat;
import net.cbtltd.soap.ota.server.ImageItemsType.ImageItem;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType.HotelRef;
import net.cbtltd.soap.ota.server.ObjectFactory;
import net.cbtltd.soap.ota.server.OtaException;
import net.cbtltd.soap.ota.server.POSType;
import net.cbtltd.soap.ota.server.ParagraphType;
import net.cbtltd.soap.ota.server.PaymentCardType;
import net.cbtltd.soap.ota.server.PaymentFormType;
import net.cbtltd.soap.ota.server.PaymentFormType.Cash;
import net.cbtltd.soap.ota.server.PersonNameType;
import net.cbtltd.soap.ota.server.PhonesType;
import net.cbtltd.soap.ota.server.PhonesType.Phone;
import net.cbtltd.soap.ota.server.RoomStayCandidateType;
import net.cbtltd.soap.ota.server.SourceType;
import net.cbtltd.soap.ota.server.SourceType.RequestorID;
import net.cbtltd.soap.ota.server.StateProvType;
import net.cbtltd.soap.ota.server.TextDescriptionType.Description;
import net.cbtltd.soap.ota.server.TextItemsType.TextItem;
import net.cbtltd.soap.ota.server.TimeUnitType;
import net.cbtltd.soap.ota.server.URLsType;
import net.cbtltd.soap.ota.server.URLsType.URL;
import net.cbtltd.soap.ota.server.UniqueIDType;
import net.cbtltd.soap.ota.server.WarningType;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;

/**
 * The Class OtaService is to provide the services required for OpenTravel Alliance messages.
 * @see	<pre>http://adriatic.pilotfish-net.com/ota-modelviewer/</pre>
 * @see	<pre>http://jibx.sourceforge.net/jibxota/</pre>
 * @see	<pre>http://www.seekom.com/xml/documentation/SeekomOta.htm</pre>
 */
public class OtaService {

	protected static final BigDecimal VER_1000 = new BigDecimal("1.000");
	protected static final BigDecimal VER_1005 = new BigDecimal("1.005");
	protected static final BigDecimal VER_1006 = new BigDecimal("1.006");
	protected static final BigDecimal VER_1007 = new BigDecimal("1.007");
	protected static final BigDecimal VER_1008 = new BigDecimal("1.008");
	protected static final BigDecimal VER_1009 = new BigDecimal("1.009");
	protected static final BigDecimal VER_1010 = new BigDecimal("1.010");
	protected static final BigDecimal VER_1011 = new BigDecimal("1.011");
	protected static final BigDecimal VER_2000 = new BigDecimal("2.000");
	protected static final BigDecimal VER_2004 = new BigDecimal("2.004");
	protected static final BigDecimal VER_2006 = new BigDecimal("2.006");
	protected static final BigDecimal VER_4001 = new BigDecimal("4.001");
	protected static final BigDecimal VER_4003 = new BigDecimal("4.003");
	protected static final BigDecimal VER_5001 = new BigDecimal("5.001");
	protected static final BigDecimal VER_5002 = new BigDecimal("5.002");
	protected static final BigDecimal VER_6004 = new BigDecimal("6.004");

	protected static final BigInteger SEQUENCE = new BigInteger("1");

	protected static final String CONTEXT = "RZ";

	protected static final String CRITERION_ADULT_COUNT = "Adults";
	protected static final String CRITERION_ARC = "ArchitectureStyle";
	protected static final String CRITERION_ARRIVAL_DATE = "ArrivalDate";
	protected static final String CRITERION_BED = "BedType";
	protected static final String CRITERION_CARD_CODE = "CardCode";
	protected static final String CRITERION_CHILD_COUNT = "Children";
	protected static final String CRITERION_DEPARTURE_DATE = "DepartureDate";
	protected static final String CRITERION_DURATION = "Duration";
	protected static final String CRITERION_EXACT_MATCH = "ExactMatch";
	protected static final String CRITERION_HAC = "Amenity";
	protected static final String CRITERION_HOTEL_AREA_ID = "AreaID";
	protected static final String CRITERION_HOTEL_BRAND_CODE = "BrandCode";
	protected static final String CRITERION_HOTEL_BRAND_NAME = "BrandName";
	protected static final String CRITERION_HOTEL_CHAIN_CODE = "ChainCode";
	protected static final String CRITERION_HOTEL_CHAIN_NAME = "ChainName";
	protected static final String CRITERION_HOTEL_CITY_CODE = "CityCode";
	protected static final String CRITERION_HOTEL_CODE = "HotelCode";
	protected static final String CRITERION_HOTEL_NAME = "HotelName";
	protected static final String CRITERION_IMPORTANCE	= "Importance";
	protected static final String CRITERION_INFANT_COUNT = "Infants";
	protected static final String CRITERION_LATITUDE = "Latitude";
	protected static final String CRITERION_LOC = "LocationCategory";
	protected static final String CRITERION_LONGITUDE = "Longitude";
	protected static final String CRITERION_MAX_RESPONSES = "MaxResponses";
	protected static final String CRITERION_MRC = "MeetingFacility";
	protected static final String CRITERION_PCT = "PropertyClass";
	protected static final String CRITERION_PHY = "Accessibility";
	protected static final String CRITERION_PET = "PaymentType";
	protected static final String CRITERION_RADIUS = "Radius";
	protected static final String CRITERION_RATE_CURRENCY = "Currency";
	protected static final String CRITERION_RATE_MAXIMUM = "MaxRate";
	protected static final String CRITERION_RATE_MINIMUM = "MinRate";
	protected static final String CRITERION_REC = "Recreation";
	protected static final String CRITERION_RLT = "RoomLocation";
	protected static final String CRITERION_RMA = "RoomAmenity";
	protected static final String CRITERION_ROOM_SIZE = "Accommodation";
	//	protected static final String CRITERION_RVT = "View";
	//	protected static final String CRITERION_SEC = "Security";
	//	protected static final String CRITERION_SEG = "Category";

	protected static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
	protected static final String DEFAULT_CURRENCY_CODE = "ZAR";
	protected static final String DEFAULT_LANGUAGE_CODE = "en";
	protected static final String DEFAULT_UNIT = "DAY";
	protected static final String MARKET = "OTA Message";
	protected static final String PROMOTION_VENDOR_CODE = "1";
	protected static final String STAY_CONTEXT = "Checkin";
	protected static final String TARGET = "Production";
	protected static final String TRANSACTION_IDENTIFIER = "1";

	protected static final ObjectFactory OF = new ObjectFactory();
	protected static final Logger LOG = Logger.getLogger(OtaService.class.getName());
	protected static final DateFormat TF = new SimpleDateFormat("hh:mm");
	
	/**
	 * AF.
	 *
	 * @return the number format
	 */
	protected static final NumberFormat AF() {
		NumberFormat AF = NumberFormat.getInstance();
		AF.setMaximumFractionDigits(2);
		AF.setMinimumFractionDigits(2);
		AF.setGroupingUsed(false);
		return AF;
	}

	/** Instantiates a new OTA service. */
	public OtaService(){
		LOG.debug("Starting OTA Service");
	}

	/**
	 * Adds the error.
	 *
	 * @param x the x
	 * @return the errors type
	 */
	protected static ErrorsType addError(OtaException x){
		ErrorsType errors = OF.createErrorsType();
		ErrorType error = new ErrorType();
		errors.getError().add(error);
		error.setCode(x.getCode());
		error.setLanguage(DEFAULT_LANGUAGE_CODE);
		error.setShortText(x.getText());
		error.setType(x.getType());
		return errors;
	}

	/**
	 * Adds the error.
	 *
	 * @param x the x
	 * @return the errors type
	 */
	protected static ErrorsType addMessage(Throwable x){
		MonitorService.log(x);
		ErrorsType errors = OF.createErrorsType();
		ErrorType error = new ErrorType();
		errors.getError().add(error);
		error.setCode(UNKNOWN_ERROR);
		error.setLanguage(DEFAULT_LANGUAGE_CODE);
		error.setShortText(x.getMessage());
		error.setType(x.getClass().getSimpleName());
		return errors;
	}

	/**
	 * Adds the warning.
	 *
	 * @param code the code
	 * @param text the text
	 * @param type the type
	 * @return the warning type
	 */
	protected static WarningType addWarning(
			String code,
			String text,
			String type
			){
		WarningType warning = new WarningType();
		warning.setCode(code);
		warning.setLanguage(DEFAULT_LANGUAGE_CODE);
		warning.setShortText(text);
		warning.setType(type);
		LOG.debug("Warning " + code + " " + text + " " + type);
		return warning;
	}

	//		protected static String getChainCode(String productid){
	//			Product product = null;
	//			SqlSession sqlSession = RazorServer.openSession();
	//			try {product = sqlSession.getMapper(ProductMapper.class).read(productid);} 
	//			catch (Throwable x) {LOG.error(x.getMessage());}
	//			finally {sqlSession.close();}
	//			return product == null ? null : product.getSupplierid();
	//		}

	/**
	 * Gets the party.
	 *
	 * @param sqlSession the current SQL session.
	 * @param uniqueID the unique id
	 * @return the party
	 */
	protected static Party getParty(SqlSession sqlSession, List<UniqueIDType> uniqueID) {
		if (uniqueID == null
				|| uniqueID.isEmpty()
				|| uniqueID.get(0).getID() == null
				|| uniqueID.get(0).getID().isEmpty()
				) {throw new OtaException("UniqueID_Missing", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

		if(uniqueID.get(0).getIDContext() == null
				|| !uniqueID.get(0).getIDContext().equalsIgnoreCase(CONTEXT)
				) {throw new OtaException("UniqueID_Context", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

		String partyid = Model.decrypt(uniqueID.get(0).getID());
		return getParty(sqlSession, partyid);
	}

	/**
	 * Gets the party having the specified ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partyid the specified ID.
	 * @return the party
	 * @throws OtaException the ota exception
	 */
	protected static Party getParty(SqlSession sqlSession, String partyid) throws OtaException {
		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null || party.noId()) {throw new OtaException("POS_Invalid", Attribute.ERR_BIZ_RULE, Attribute.EWT_BIZ_RULE);}
		LOG.debug("getParty " + party);
		return party;
	}

	/**
	 * Gets the accepted payments.
	 *
	 * @param product the product
	 * @param agent the agent
	 * @return the accepted payments
	 */
	protected static AcceptedPaymentsType getAcceptedPayments(Product product, Party agent) {
		AcceptedPaymentsType acceptedPayments = OF.createAcceptedPaymentsType();
		if (product.hasValue(Attribute.PMT)) {
			//Payment types
			for (String pmt : product.getAttributeValues(Attribute.PMT)) {
				PaymentFormType paymentForm = OF.createPaymentFormType();
				acceptedPayments.getAcceptedPayment().add(paymentForm);
				if (Attribute.PMT_AIR_TRAVEL_CARD.equalsIgnoreCase(pmt)
						|| Attribute.PMT_AIRLINE_PAYMENT_CARD.equalsIgnoreCase(pmt)
						|| Attribute.PMT_AIRLINE_PAYMENT_CARD.equalsIgnoreCase(pmt)
						|| Attribute.PMT_CREDIT_CARD.equalsIgnoreCase(pmt)
						|| Attribute.PMT_DEBIT_CARD.equalsIgnoreCase(pmt)
						){
					//Card payment type
					if (product.hasValue(ProductValue.CARD_CODE)) {
						//Card codes
						for (String cct : product.getAttributeValues(ProductValue.CARD_CODE)) {
							PaymentCardType paymentCard = OF.createPaymentCardType();
							paymentForm.setPaymentCard(paymentCard);
							paymentCard.setCardType(pmt);
							paymentCard.setCardCode(cct);
						}
					}
				}
				else if (Attribute.PMT_DIRECT_BILL.equalsIgnoreCase(pmt) && agent != null){
					DirectBillType directBill = OF.createDirectBillType();
					paymentForm.setDirectBill(directBill);
					directBill.setCompanyName(getDirectBillCompanyName(agent, Attribute.TVS_HOTEL));
					directBill.setAddress(getAddressInfo(agent, Attribute.AUT_BILLING));
					directBill.setEmail(getEmail(agent, Attribute.EAT_BUSINESS));
					directBill.setShareMarketInd(null);
					directBill.setShareSynchInd(null);
					directBill.setTelephone(getDirectBillTelephone(agent, Attribute.PLT_OFFICE));
				}
				else if (Attribute.PMT_BARTER.equalsIgnoreCase(pmt)
						|| Attribute.PMT_CASH.equalsIgnoreCase(pmt)
						|| Attribute.PMT_TRAVELERS_CHECK.equalsIgnoreCase(pmt)
						) {
					Cash cash = OF.createPaymentFormTypeCash();
					paymentForm.setCash(cash);
					cash.setCashIndicator(true);
				}
				else {
					SqlSession sqlSession = RazorServer.openSession();
					try {paymentForm.setRemark(sqlSession.getMapper(ProductMapper.class).valuenameid(new AttributeAction(Attribute.PMT, pmt)));} 
					catch (Throwable x) {LOG.error(x.getMessage());}
					finally {sqlSession.close();}
				}
			}
		}
		return acceptedPayments;
	}

	/**
	 * Gets the address info.
	 *
	 * @param party the party
	 * @param aut the aut
	 * @return the address info
	 */
	protected static AddressInfoType getAddressInfo(Party party, String aut) {
		AddressInfoType address = OF.createAddressInfoType();
		if (party == null) {return address;}
		address.setPostalCode(party.getPostalcode());
		address.setShareMarketInd(null);
		address.setShareSynchInd(null);
		//		address.setType(value)
		address.setUseType(aut);//AUT OTA code
		if (party.hasPostaladdress()) {
			StreetNmbr streetNmbr = OF.createAddressTypeStreetNmbr();
			address.setStreetNmbr(streetNmbr);
			streetNmbr.setValue(party.getPostaladdress());
		}
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Location location = sqlSession.getMapper(LocationMapper.class).read(party.getLocationid());
			if (location != null) {
				address.setCityName(location.getName());
				address.setCountryName(getCountryName(sqlSession.getMapper(CountryMapper.class).read(location.getCountry())));
				address.setStateProv(getStateProv(sqlSession.getMapper(RegionMapper.class).readbylocation(location)));
			}
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		finally {sqlSession.close();}
		return address;
	}

	/**
	 * Gets the addresses.
	 *
	 * @param party the party
	 * @param aut the aut
	 * @return the addresses
	 */
	protected static AddressesType getAddresses(Party party, String aut) {
		if (party == null) {return null;}
		AddressesType addresses = OF.createAddressesType();
		Address address = OF.createAddressesTypeAddress();
		addresses.getAddress().add(address);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Location location = sqlSession.getMapper(LocationMapper.class).read(party.getLocationid());
			if (location != null) {
				address.setCityName(location.getName());
				address.setCountryName(getCountryName(sqlSession.getMapper(CountryMapper.class).read(location.getCountry())));
				address.setStateProv(getStateProv(sqlSession.getMapper(RegionMapper.class).readbylocation(location)));
				address.setID(location.getId());
			}
			address.setPostalCode(party.getPostalcode());
			address.setShareMarketInd(null);
			address.setShareSynchInd(null);
			//		address.setType(value)
			address.setType(aut);
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		finally {sqlSession.close();}
		return addresses;
	}

	/**
	 * Gets the booking channel type.
	 *
	 * @param pos the pos
	 * @param index the index
	 * @return the booking channel type
	 */
	protected static String getBookingChannelType(POSType pos, int index) {
		SourceType source = getSource(pos, index);
		if (source.getBookingChannel() == null
				|| source.getBookingChannel().getType() == null
				|| source.getBookingChannel().getType().isEmpty()
				) {throw new OtaException("BookingChannel", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		return source.getBookingChannel().getType();
	}

	/**
	 * Gets the cancel penalties.
	 *
	 * @return the cancel penalties
	 */
	protected static CancelPenaltiesType getCancelPenalties() {
		CancelPenaltiesType cancelPenalties = OF.createCancelPenaltiesType();
		cancelPenalties.setCancelPolicyIndicator(true);

		//		Bookings cancelled less than 30 days from arrival date pay a 100% cancellation fee.
		CancelPenaltyType cancelPenalty = OF.createCancelPenaltyType();
		cancelPenalties.getCancelPenalty().add(cancelPenalty);
		cancelPenalty.getPenaltyDescription().add(getParagraph(
				"Refunds and waiver of cancellation policies are at the discretion of management."
						+ "\nRefunds are dependent on releting of the room."
						+ "\nGuests are advised to take out cancellation insurance."));
		AmountPercentType amountPercent = OF.createAmountPercentType();
		CancelPenaltyType.Deadline deadline = OF.createCancelPenaltyTypeDeadline();
		amountPercent.setPercent(new BigDecimal("100"));
		cancelPenalty.setAmountPercent(amountPercent);
		deadline.setOffsetTimeUnit(TimeUnitType.DAY);
		deadline.setOffsetUnitMultiplier(30);
		cancelPenalty.setDeadline(deadline);

		//		Bookings cancelled between 60 and 31 days of arrival date pay a 50% cancellation fee.
		cancelPenalty = OF.createCancelPenaltyType();
		cancelPenalties.getCancelPenalty().add(cancelPenalty);
		amountPercent = OF.createAmountPercentType();
		deadline = OF.createCancelPenaltyTypeDeadline();
		amountPercent.setPercent(new BigDecimal("50"));
		cancelPenalty.setAmountPercent(amountPercent);
		deadline.setOffsetTimeUnit(TimeUnitType.DAY);
		deadline.setOffsetUnitMultiplier(60);
		cancelPenalty.setDeadline(deadline);

		//		Bookings cancelled between 90 and 61 days of arrival date pay a 25% cancellation fee.
		cancelPenalty = OF.createCancelPenaltyType();
		cancelPenalties.getCancelPenalty().add(cancelPenalty);
		amountPercent = OF.createAmountPercentType();
		deadline = OF.createCancelPenaltyTypeDeadline();
		amountPercent.setPercent(new BigDecimal("25"));
		cancelPenalty.setAmountPercent(amountPercent);
		deadline.setOffsetTimeUnit(TimeUnitType.DAY);
		deadline.setOffsetUnitMultiplier(90);
		cancelPenalty.setDeadline(deadline);
		//		Bookings cancelled more than 90 days prior to arrival date pays no cancellation fee.
		return cancelPenalties;
	}

	/**
	 * Gets the cancel rules.
	 *
	 * @param cancelDate the cancel date
	 * @param arrivalDate the arrival date
	 * @return the cancel rules
	 */
	protected static CancelRules getCancelRules(Date cancelDate, Date arrivalDate) {
		CancelRules crs = OF.createCancelInfoRSTypeCancelRules();
		CancelRuleType cr = OF.createCancelRuleType();
		crs.getCancelRule().add(cr);
		double duration = Time.getDuration(cancelDate, arrivalDate, Time.DAY);
		if (duration > 90.0) {cr.setPercent(new BigDecimal("25"));}
		else if (duration > 60.0) {cr.setPercent(new BigDecimal("50"));}
		else {cr.setPercent(new BigDecimal("100"));}
		cr.setType(Attribute.RUL_CANCEL);
		return crs;
	}

	/**
	 * Gets the company name.
	 *
	 * @param code the code
	 * @param name the name
	 * @param tvs the tvs
	 * @return the company name
	 */
	protected static final CompanyNameType getCompanyName(String code, String name, String tvs) {
		CompanyNameType companyName = OF.createCompanyNameType();
		companyName.setCode(code);
		companyName.setCodeContext(CONTEXT);
		companyName.setValue(name);
		companyName.setDepartment(null);
		companyName.setDivision(null);
		companyName.setTravelSector(tvs);
		return companyName;
	}

	/**
	 * Gets the address info.
	 *
	 * @param streetnumber the streetnumber
	 * @param cityname the cityname
	 * @param regioncode the regioncode
	 * @param countrycode the countrycode
	 * @param postalcode the postalcode
	 * @return the address info
	 */
	protected static final CompanyInfoType.AddressInfo getAddressInfo(String streetnumber, String cityname, String regioncode, String countrycode, String postalcode) {
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

	/**
	 * Gets the contact person.
	 *
	 * @param surname the surname
	 * @param givenname the givenname
	 * @return the contact person
	 */
	protected static final ContactPersonType getContactPerson(String surname, String givenname) {
		ContactPersonType contactPerson = OF.createContactPersonType();
		contactPerson.setContactType(ProductValue.TYP_CONTACT_PERSON);
		contactPerson.setDefaultInd(true);
		contactPerson.setPersonName(getPersonName(surname, givenname, ProductValue.TYP_CONTACT_PERSON));
		contactPerson.setRelation(Attribute.REL_NONE);
		contactPerson.setShareMarketInd(null);
		contactPerson.setShareSynchInd(null);
		return contactPerson;
	}

	/**
	 * Gets the contact numbers.
	 *
	 * @param party the party
	 * @return the contact numbers
	 */
	protected static ContactNumbers getContactNumbers(Party party) {
		if (party == null) {return null;}
		return getContactNumbers (party.getDayphone(), party.getFaxphone(), party.getMobilephone());
	}

	/**
	 * Gets the contact numbers.
	 *
	 * @param dayphone the dayphone
	 * @param faxphone the faxphone
	 * @param mobilephone the mobilephone
	 * @return the contact numbers
	 */
	protected static ContactNumbers getContactNumbers(String dayphone, String faxphone, String mobilephone) {
		ContactNumbers contactNumbers = OF.createBasicPropertyInfoTypeContactNumbers();
		ContactNumber contactNumber = getContactNumber(dayphone, Attribute.PUT_DAYTIME_CONTACT, Attribute.PTT_VOICE);
		if (contactNumber != null) {contactNumbers.getContactNumber().add(contactNumber);}
		contactNumber = getContactNumber(faxphone, Attribute.PUT_DAYTIME_CONTACT, Attribute.PTT_FAX);
		if (contactNumber != null) {contactNumbers.getContactNumber().add(contactNumber);}
		contactNumber = getContactNumber(mobilephone, Attribute.PUT_CONTACT, Attribute.PTT_MOBILE);
		if (contactNumber != null) {contactNumbers.getContactNumber().add(contactNumber);}
		if (contactNumbers.getContactNumber().size() == 0) {return null;}
		else {return contactNumbers;}
	}

	/**
	 * Gets the contact number.
	 *
	 * @param number the number
	 * @param put the put
	 * @param ptt the ptt
	 * @return the contact number
	 */
	protected static ContactNumber getContactNumber(String number, String put, String ptt) {
		if (number == null || number.isEmpty()) {return null;}
		ContactNumber contactNumber = OF.createBasicPropertyInfoTypeContactNumbersContactNumber();
		contactNumber.setPhoneUseType(put);
		contactNumber.setPhoneTechType(ptt);
		contactNumber.setPhoneNumber(number);
		return contactNumber;
	}

	/**
	 * Gets the country name.
	 *
	 * @param country the country
	 * @return the country name
	 */
	protected static CountryNameType getCountryName(Country country) {
		if (country == null) {return null;}
		CountryNameType countryName = OF.createCountryNameType();
		countryName.setCode(country.getId());
		countryName.setValue(country.getName());
		return countryName;
	}

	/**
	 * Gets the direct bill company name.
	 *
	 * @param party the party
	 * @param tvs the tvs
	 * @return the direct bill company name
	 */
	protected static DirectBillType.CompanyName getDirectBillCompanyName(Party party, String tvs) {
		if (party == null) {return null;}
		DirectBillType.CompanyName companyName = OF.createDirectBillTypeCompanyName();
		companyName.setCode(party.getId());
		companyName.setCodeContext(CONTEXT);
		companyName.setCompanyShortName(party.getName());
		companyName.setDepartment(null);
		companyName.setDivision(null);
		companyName.setTravelSector(tvs);
		companyName.setValue(party.getName());
		return companyName;
	}

	/**
	 * Gets the direct bill telephone.
	 *
	 * @param party the party
	 * @param plt the plt
	 * @return the direct bill telephone
	 */
	protected static final DirectBillType.Telephone getDirectBillTelephone(Party party, String plt){
		if (party == null) {return null;}
		DirectBillType.Telephone telephone = OF.createDirectBillTypeTelephone();
		telephone.setPhoneNumber(party.getDayphone());
		telephone.setPhoneLocationType(plt);
		telephone.setShareMarketInd(null);
		telephone.setShareSynchInd(null);
		return telephone;
	}

	/**
	 * Gets the customer email.
	 *
	 * @param emailaddress the emailaddress
	 * @param eat the eat
	 * @return the customer email
	 */
	protected static Email getCustomerEmail(String emailaddress, String eat){
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
	 * Gets the email.
	 *
	 * @param party the party
	 * @param eat the eat
	 * @return the email
	 */
	protected static EmailType getEmail(Party party, String eat){
		EmailType email = OF.createEmailType();
		email.setDefaultInd(true);
		email.setEmailType(eat);
		email.setRemark(null);
		email.setRPH(null);
		email.setShareMarketInd(null);
		email.setShareSynchInd(null);
		email.setValue(party.getEmailaddress());
		return email;
	}

	/**
	 * Gets the emails.
	 *
	 * @param party the party
	 * @param eat the eat
	 * @return the emails
	 */
	protected static EmailsType getEmails(Party party, String eat){
		EmailsType emails = OF.createEmailsType();
		EmailsType.Email email = OF.createEmailsTypeEmail();
		emails.getEmail().add(email);
		email.setDefaultInd(true);
		email.setEmailType(eat);
		email.setRemark(null);
		email.setRPH(null);
		email.setShareMarketInd(null);
		email.setShareSynchInd(null);
		email.setValue(party.getEmailaddress());
		return emails;
	}

	/**
	 * Gets the free text.
	 *
	 * @param text the text
	 * @param language the language
	 * @return the free text
	 */
	protected static FreeTextType getFreeText(String text, String language) {
		FreeTextType freeText = OF.createFreeTextType();
		freeText.setLanguage(language);
		freeText.setValue(text);
		return freeText;
	}

	/**
	 * Gets the image item.
	 *
	 * @param sqlSession the current SQL session.
	 * @param category the category
	 * @param copyrightNotice the copyright notice
	 * @param copyrightOwner the copyright owner
	 * @param imageId the image id
	 * @return the image item
	 */
	protected static ImageItem getImageItem(
			SqlSession sqlSession,
			String category,
			String copyrightNotice,
			String copyrightOwner,
			String imageId
			) {
		ImageItem imageItem = OF.createImageItemsTypeImageItem();
		imageItem.setCategory(category);
		Text text = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(imageId, DEFAULT_LANGUAGE_CODE));
		if (text != null) {
			ImageDescriptionType.Description description = OF.createImageDescriptionTypeDescription();
			imageItem.getDescription().add(description);
			description.setValue(stripHTML(text.getNotes()));
		}
		ImageFormat fullsize = OF.createImageDescriptionTypeImageFormat();
		imageItem.getImageFormat().add(fullsize);
		fullsize.setCopyrightNotice(copyrightNotice);
		fullsize.setCopyrightOwner(copyrightOwner);
		//fullsize.setFileName(imageURL);
		fullsize.setTitle("Fullsize Image");
		fullsize.setURL(RazorHostsConfig.getPicturesUrl() + imageId);
		return imageItem;
	}

	/**
	 * Gets the paragraph.
	 *
	 * @param text the text
	 * @return the paragraph
	 */
	protected static ParagraphType getParagraph(String text) {
		ParagraphType paragraph = OF.createParagraphType();
		paragraph.setCreateDateTime(getXGC());
		paragraph.setLanguage(DEFAULT_LANGUAGE_CODE);
		FormattedTextTextType formattedTextText = OF.createFormattedTextTextType();
		paragraph.getTextOrImageOrURL().add(OF.createParagraphTypeText(formattedTextText));
		formattedTextText.setFormatted(false);
		formattedTextText.setValue(text);
		return paragraph;
	}


	/**
	 * Gets the person name.
	 *
	 * @param surname the surname
	 * @param givenname the givenname
	 * @param typ the typ
	 * @return the person name
	 */
	protected static PersonNameType getPersonName(String surname, String givenname, String typ) {
		PersonNameType personName = OF.createPersonNameType();
		personName.setNameType(typ);
		personName.setShareMarketInd(null);
		personName.setShareSynchInd(null);
		personName.setSurname(surname);
		personName.getGivenName().add(givenname);
		return personName;
	}

	/**
	 * Gets the phones.
	 *
	 * @param party the party
	 * @return the phones
	 */
	protected static PhonesType getPhones(Party party){
		PhonesType phones = OF.createPhonesType();
		phones.getPhone().add(getPhone(party.getDayphone(), Attribute.PLT_MANAGING_COMPANY, Attribute.PUT_DAYTIME_CONTACT, Attribute.PTT_VOICE));
		phones.getPhone().add(getPhone(party.getFaxphone(), Attribute.PLT_MANAGING_COMPANY, Attribute.PUT_DAYTIME_CONTACT, Attribute.PTT_FAX));
		phones.getPhone().add(getPhone(party.getMobilephone(), Attribute.PLT_MANAGING_COMPANY, Attribute.PUT_CONTACT, Attribute.PTT_MOBILE));
		return phones;
	}

	/**
	 * Gets the phone.
	 *
	 * @param phoneNumber the phone number
	 * @param plt the plt
	 * @param put the put
	 * @param ptt the ptt
	 * @return the phone
	 */
	protected static Phone getPhone(String phoneNumber, String plt, String put, String ptt){
		Phone phone = OF.createPhonesTypePhone();
		phone.setPhoneLocationType(plt);
		phone.setPhoneUseType(put);
		phone.setPhoneTechType(ptt);
		phone.setPhoneNumber(phoneNumber);
		phone.setShareMarketInd(null);
		phone.setShareSynchInd(null);
		return phone;
	}

	/**
	 * Gets the position.
	 *
	 * @param position the position
	 * @return the position
	 */
	protected static Position getPosition(net.cbtltd.shared.Entity position) {
		if (position == null) {return null;}
		return getPosition(position.getLatitude(), position.getLongitude(), position.getAltitude());
	}

	/**
	 * Gets the position.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param altitude the altitude
	 * @return the position
	 */
	protected static Position getPosition(Double latitude, Double longitude, Double altitude) {
		BasicPropertyInfoType.Position position = OF.createBasicPropertyInfoTypePosition();
		position.setLatitude(String.valueOf(latitude));
		position.setLongitude(String.valueOf(longitude));
		position.setAltitude(String.valueOf(altitude));
		position.setAltitudeUnitOfMeasureCode("MTR");
		return position;
	}

	/**
	 * Gets the pOS.
	 *
	 * @param id the id
	 * @param context the context
	 * @return the pOS
	 */
	protected static POSType getPOS(
			String id,
			String context
			) {
		POSType pos = OF.createPOSType();
		SourceType source = OF.createSourceType();
		pos.getSource().add(source);
		RequestorID requestorID = OF.createSourceTypeRequestorID();
		source.setRequestorID(requestorID);
		requestorID.setID(id);
		requestorID.setIDContext(context);
		return pos;
	}

	/**
	 * Gets the source.
	 *
	 * @param pos the pos
	 * @param index the index
	 * @return the source
	 * @throws OtaException the ota exception
	 */
	protected static SourceType getSource(POSType pos, int index) throws OtaException {
		if (pos == null
				|| pos.getSource() == null
				|| pos.getSource().isEmpty()
				) {throw new OtaException("Source", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		return pos.getSource().get(index);
	}

	/**
	 * Gets the requestor id.
	 *
	 * @param pos the pos
	 * @param index the index
	 * @return the requestor id
	 * @throws OtaException the ota exception
	 */
	protected static String getRequestorID(POSType pos, int index) throws OtaException {
		SourceType source = getSource(pos, index);
		if (source.getRequestorID() == null
				|| source.getRequestorID().getID() == null
				|| source.getRequestorID().getID().isEmpty()
				|| source.getRequestorID().getIDContext() == null
				|| source.getRequestorID().getIDContext().isEmpty()
				|| !source.getRequestorID().getIDContext().equalsIgnoreCase(CONTEXT)
				) {throw new OtaException("RequestorID", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		return source.getRequestorID().getID();
	}

	/**
	 * Gets the requestor type.
	 *
	 * @param pos the pos
	 * @param index the index
	 * @return the requestor type
	 * @throws OtaException the ota exception
	 */
	protected static String getRequestorType(POSType pos, int index) throws OtaException {
		SourceType source = getSource(pos, index);
		if (source.getRequestorID() == null
				|| source.getRequestorID().getType() == null
				|| source.getRequestorID().getType().isEmpty()
				) {throw new OtaException("RequestorType", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}
		return source.getRequestorID().getType();
	}

	/**
	 * Gets the state prov.
	 *
	 * @param nameid the nameid
	 * @return the state prov
	 */
	protected static StateProvType getStateProv(NameId nameid) {
		if (nameid == null){return null;}
		StateProvType stateProv = OF.createStateProvType();
		stateProv.setStateCode(nameid.getId());
		stateProv.setValue(nameid.getName());
		return stateProv;
	}


	/**
	 * Gets the date time span type.
	 *
	 * @param dateTimeSpan the date time span
	 * @return the date time span type
	 */
	protected static DateTimeSpanType getDateTimeSpanType(DateTimeSpanType dateTimeSpan) {

		if (dateTimeSpan == null
				|| dateTimeSpan.getStart() == null
				|| dateTimeSpan.getStart().isEmpty()
				) {throw new OtaException("StartDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

		if (dateTimeSpan == null
				|| dateTimeSpan.getEnd() == null
				|| dateTimeSpan.getEnd().isEmpty()
				) {throw new OtaException("EndDate", Attribute.ERR_REQUIRED_FIELD_MISSING, Attribute.EWT_REQUIRED_FIELD_MISSING);}

		Date start = getDate(dateTimeSpan.getStart());
		Date end = getDate(dateTimeSpan.getEnd());
		if (!end.after(start)) {throw new OtaException("EndBeforeStart", Attribute.ERR_END_DATE_IS_BEFORE_START_DATE, Attribute.EWT_END_DATE_IS_BEFORE_START_DATE);}
		return dateTimeSpan;
	}

	/**
	 * Gets the telephone.
	 *
	 * @param number the number
	 * @param ptt the ptt
	 * @param put the put
	 * @return the telephone
	 */
	protected static final Telephone getTelephone(String number, String ptt, String put) {
		Telephone telephone = OF.createCustomerTypeTelephone();
		telephone.setPhoneNumber(number);
		telephone.setPhoneTechType(ptt);
		telephone.setPhoneUseType(put);
		return telephone;
	}

	/**
	 * Gets the telephone info.
	 *
	 * @param number the number
	 * @param ptt the ptt
	 * @param put the put
	 * @return the telephone info
	 */
	protected static final TelephoneInfo getTelephoneInfo(String number, String ptt, String put) {
		TelephoneInfo telephone = OF.createCompanyInfoTypeTelephoneInfo();
		telephone.setPhoneNumber(number);
		telephone.setPhoneTechType(ptt);
		telephone.setPhoneUseType(put);
		return telephone;
	}

	/**
	 * Gets the text item.
	 *
	 * @param copyrightNotice the copyright notice
	 * @param copyrightOwner the copyright owner
	 * @param title the title
	 * @param language the language
	 * @param value the value
	 * @return the text item
	 */
	protected static TextItem getTextItem(
			String copyrightNotice,
			String copyrightOwner,
			String title,
			String language,
			String value) {
		TextItem textItem = OF.createTextItemsTypeTextItem();
		textItem.setCopyrightNotice(copyrightNotice);
		textItem.setCopyrightOwner(copyrightOwner);
		textItem.setTitle(title);
		Description description = OF.createTextDescriptionTypeDescription();
		textItem.getDescription().add(description);
		description.setLanguage(language);
		description.setValue(stripHTML(value));
		return textItem;
	}

	/**
	 * Gets the uR ls.
	 *
	 * @param party the party
	 * @param type the type
	 * @return the uR ls
	 */
	protected static URLsType getURLs(Party party, String type) {
		URLsType urls = OF.createURLsType();
		URL url = OF.createURLsTypeURL();
		urls.getURL().add(url);
		url.setType(type);
		url.setValue(party.getWebaddress());
		return urls;
	}

	/**
	 * Adds the guest count.
	 *
	 * @param criterion the criterion
	 * @param value the value
	 * @param aqc the AGC parameter
	 */
	protected static void addGuestCount(Criterion criterion, Integer value, String aqc) {
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

	/**
	 * Adds the hotel amenity.
	 *
	 * @param criterion the criterion
	 * @param code the code
	 */
	protected static void addHotelAmenity(Criterion criterion, String code) {
		HotelAmenity hotelAmenity = OF.createHotelSearchCriterionTypeHotelAmenity();
		criterion.getHotelAmenity().add(hotelAmenity);
		hotelAmenity.setCode(code);
	}

	/**
	 * Adds the hotel ref.
	 *
	 * @param criterion the criterion
	 * @return the hotel ref
	 */
	protected static HotelRef addHotelRef(Criterion criterion) {
		HotelRef hotelRef = OF.createItemSearchCriterionTypeHotelRef();
		criterion.getHotelRef().add(hotelRef);
		return hotelRef;
	}

	/**
	 * Adds the accessibility code.
	 *
	 * @param criterion the criterion
	 * @param feature the feature
	 */
	protected static void addAccessibilityCode(Criterion criterion, String feature ){ //PHY physically challenged feature code
		HotelFeature hotelFeature = OF.createHotelSearchCriterionTypeHotelFeature();
		criterion.getHotelFeature().add(hotelFeature);
		hotelFeature.setAccessibilityCode(feature);
	}
	
	/**
	 * Gets the rate range.
	 *
	 * @param criterion the criterion
	 * @return the rate range
	 */
	protected static HotelSearchCriterionType.RateRange getRateRange(Criterion criterion) {
		if (criterion.getRateRange() == null
				|| criterion.getRateRange().isEmpty()) {
			HotelSearchCriterionType.RateRange rateRange = OF.createHotelSearchCriterionTypeRateRange();
			criterion.getRateRange().add(rateRange);
			rateRange.setCurrencyCode(DEFAULT_CURRENCY_CODE);
			rateRange.setMinRate(getBigDecimal(0.0));
		}
		return criterion.getRateRange().get(0);
	}

	/**
	 * Gets the room stay candidate.
	 *
	 * @param criterion the criterion
	 * @return the room stay candidate
	 */
	protected static RoomStayCandidateType getRoomStayCandidate(Criterion criterion) {
		RoomStayCandidates roomStayCandidates = criterion.getRoomStayCandidates();
		if (roomStayCandidates == null){
			roomStayCandidates = OF.createHotelSearchCriterionTypeRoomStayCandidates();
			criterion.setRoomStayCandidates(roomStayCandidates);
			roomStayCandidates.getRoomStayCandidate().add(OF.createRoomStayCandidateType());
		}
		return roomStayCandidates.getRoomStayCandidate().get(0);
	}

	/**
	 * Gets the xGC.
	 *
	 * @return the xGC
	 */
	protected static final XMLGregorianCalendar getXGC () {
		return getXGC(new Date());
	}

	/**
	 * Gets the xGC.
	 *
	 * @param date the date
	 * @return the xGC
	 */
	protected static final XMLGregorianCalendar getXGC (Date date) {
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH)+1); // +1 because XMLGregorianCalendar is from 1 to 12 while Calendar.MONTH is from 0 to 11 !!!
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
			//GregorianCalendar c = xgc.toGregorianCalendar();
			//Date fecha = c.getTime();
			//java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
		} 
		catch (DatatypeConfigurationException e) {throw new OtaException("Date_Format_Invalid", Attribute.ERR_INVALID_DATE_FORMAT, Attribute.EWT_INVALID_DATE_FORMAT);}
		return xgc;
	}

	/**
	 * Gets the xGC.
	 *
	 * @param xgcstring the xgcstring
	 * @return the xGC
	 */
	protected static final XMLGregorianCalendar getXGC(String xgcstring) { //source in XGC format
		try {return DatatypeFactory.newInstance().newXMLGregorianCalendar(xgcstring);}
		catch (DatatypeConfigurationException e) {throw new OtaException("Date_Format_Invalid", Attribute.ERR_INVALID_DATE_FORMAT, Attribute.EWT_INVALID_DATE_FORMAT);}
	}

	/**
	 * Gets the date.
	 *
	 * @param xgc the xgc
	 * @return the date
	 */
	protected static final Date getDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		return xgc.toGregorianCalendar().getTime();
	}

	/**
	 * Gets the date.
	 *
	 * @param xgcstring the xgcstring
	 * @return the date
	 */
	protected static final Date getDate(String xgcstring) {
		return getDate(getXGC(xgcstring));
	}

	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	protected static final String getDate(Date date) {
		if (date == null) {return null;}
		else {return getXGC(date).toXMLFormat();}
	}

	/*
	 * Gets the string with text matching the regular expression being removed from the specified html.
	 *
	 * @param regex the regular expression.
	 * @param replacement the replacement text.
	 * @param html the specified html.
	 * @return without text matching the regular expression.
	 */
	/**
	 * Match.
	 *
	 * @param regex the regex
	 * @param replacement the replacement
	 * @param html the html
	 * @return the string
	 */
	protected static String match(String regex, String replacement, String html){
		if (html == null || html.isEmpty()){return html;}
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(html).replaceAll(replacement);
	}

	/*
	 * Gets the string with HTML mark up removed from the specified html.
	 *
	 * @param html the HTML text from which mark up is to be removed.
	 * @return text without mark up.
	 */
	/**
	 * Strip html.
	 *
	 * @param html the html
	 * @return the string
	 */
	protected static String stripHTML(String html){
		String text = match("<[^>]*>", " ", html); //removes all html tags
		//	   	text = text.replaceAll("\\&quot;", "\"");
		//	   	text = text.replaceAll("&lt;", "<![CDATA[ < ]]>");
		//	   	text = text.replaceAll("&gt;", "<![CDATA[ > ]]>");
		//	   	text = text.replaceAll("&amp;", "<![CDATA[ & ]]>");
		text = text.replaceAll("nbsp;", " ");
		text = match("&[^;]*;", " ", text);
		return text;
	}

	/**
	 * Gets the big integer.
	 *
	 * @param value the value
	 * @return the big integer
	 */
	protected static BigInteger getBigInteger(String value) {
		if (value == null) {return null;}
		else {return new BigInteger(value);}
	}

	/**
	 * Gets the big integer.
	 *
	 * @param value the value
	 * @return the big integer
	 */
	protected static BigInteger getBigInteger(Integer value) {
		if (value == null) {return null;}
		else {return new BigInteger(String.valueOf(value));}
	}

	/**
	 * Gets the integer.
	 *
	 * @param value the value
	 * @return the integer
	 */
	protected static Integer getInteger(BigInteger value) {
		if (value == null) {return null;}
		else {return Integer.valueOf(value.toString());}
	}

	/**
	 * Gets the big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	protected static BigDecimal getBigDecimal(String value) {
		if (value == null) {return null;}
		else {return new BigDecimal(value);}
	}

	/**
	 * Gets the big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	protected static BigDecimal getBigDecimal(Double value) {
		if (value == null) {return null;}
		else {return new BigDecimal(String.valueOf(value));}
	}

	/**
	 * Gets the double.
	 *
	 * @param value the value
	 * @return the double
	 */
	protected static Double getDouble(BigDecimal value) {
		if (value == null) {return null;}
		else {return Double.valueOf(value.toString());}
	}

	/**
	 * Gets the date time.
	 *
	 * @param xgc the xgc
	 * @return the date time
	 */
	protected static Date getDateTime(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		else {return xgc.toGregorianCalendar().getTime();}
	}

	/**
	 * Gets the time.
	 *
	 * @param xgc the xgc
	 * @return the time
	 */
	protected static String getTime(XMLGregorianCalendar xgc) {
		if (xgc == null) {return null;}
		else {return xgc.toGregorianCalendar().getTime().toString();}
	}
}
