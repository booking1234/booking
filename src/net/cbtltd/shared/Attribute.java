/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasServiceResponse;

public class Attribute
extends NameId
implements HasServiceResponse {

	private String list;
	private String type;
	private String definition;
	private Date date;
	private Date version;
	private int status = 0;

	public Attribute () {}
	
	public Attribute (String list, String id) {
		this.list = list;
		this.id = id;
	}

	public Service service() {return Service.ATTRIBUTE;}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public boolean isKey() {
		return id == null || id.equals(Model.ZERO);
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [list=");
		builder.append(list);
		builder.append(", type=");
		builder.append(type);
		builder.append(", definition=");
		builder.append(definition);
		builder.append(", date=");
		builder.append(date);
		builder.append(", version=");
		builder.append(version);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	//scope RZ=Razor
	public static final String RZ = "RZ";//attribute type
	
	// party lists
	public static final String BEP = "BEP";
	public static final String COM = "COM";
	public static final String GRP = "GRP";
	public static final String GUP = "GUP";
	public static final String HAP = "HAP";
	public static final String LOP = "LOP";
	public static final String MPP = "MPP";
	public static final String PCP = "PCP";
	public static final String PEP = "PEP";
	public static final String PHP = "PHP";
	public static final String REP = "REP";
	public static final String RMP = "RMP";
	public static final String RSN = "RSN";
	public static final String RSP = "RSP";
	public static final String SEP = "SEP";
	
	//non-OTA lists
	public static final String ASZ = "ASZ";//accommodation size
	public static final String ENV = "ENV";//environment
	public static final String FAC = "FAC";//facilities
	public static final String GRD = "GRD";//grading
	public static final String SCY = "SCY";//security
	public static final String SUI = "SUI";//suitability

	//OTA types
	public static final String ACC = "ACC";//attraction category code
	public static final String AMC = "AMC";//available meal category
	public static final String AQC = "AQC";//age qualifying code
	public static final String ARC = "ARC";//architecture style
	public static final String BED = "BED";//bed type
	public static final String BUS = "BUS";//business service type
	public static final String CDT = "CDT";//card type
	public static final String DEC = "DEC";//decline reason
	public static final String GRI = "GRI";//guest room info
	public static final String HAC = "HAC";//hotel amenity code
	public static final String HIC = "HIC";//hotel info code
	public static final String LOC = "LOC";//location category
	public static final String MPT = "MPT";//meal plan type
	public static final String MRC = "MRC";//meeting room code
	public static final String PCT = "PCT";//property class type
	public static final String PET = "PET";//pets policy
	public static final String PHY = "PHY";//physically disabled accessibility
	public static final String PMT = "PMT";//payment type
	public static final String RAC = "RAC";//room amenity code
	public static final String REC = "REC";//recreation service detail code
	public static final String RES = "RES";//restaurant category code
	public static final String RLT = "RLT";//room location
	public static final String RMA = "RMA";//room amenity type
	public static final String RSI = "RSI";//restaurant service info code
	public static final String RST = "RST";//recreation service type
	public static final String RVT = "RVT";//room view
	public static final String SEC = "SEC";//security feature
	public static final String SEG = "SEG";//segment category code
	public static final String TRP = "TRP";//transportation code

	public static final String[] ACCOMMODATION_SEARCH = {AQC, BED, GRD, HAC, LOC, PCT, PET, PHY, SEC,RMA};
	public static final String[] PARTY = {BEP,COM,GRD,GRP,GUP,HAP,LOP,MPP,PCP,PEP,PHP,REP,RMP,RSN,RSP,SEP};
//TODO: Interhome attributes 
	public static final String[] PROPERTY = {AQC, BED, FAC, GRD, HAC, LOC, PCT, PET, PHY, RMA, RSN, SEC};
//	public static final String[] PROPERTY = {AQC, BED, GRD, HAC, LOC, PCT, PET, PHY, SEC};
	public static final String[] UNIQUE_KEYS = {GRD, PCT};

	public static final String AQC_OVER_21 = "1";
	public static final String AQC_OVER_65 = "2";
	public static final String AQC_UNDER_2 = "3";
	public static final String AQC_UNDER_12 = "4";
	public static final String AQC_UNDER_17 = "5";
	public static final String AQC_UNDER_21 = "6";
	public static final String AQC_INFANT = "7";
	public static final String AQC_CHILD = "8";
	public static final String AQC_TEENAGER = "9";
	public static final String AQC_ADULT = "10";
	public static final String AQC_SENIOR_ = "11";
	public static final String AQC_ADDITIONAL_OCCUPANT_WITH_ADULT = "12";
	public static final String AQC_ADDITIONAL_OCCUPANT_WITHOUT_ADULT = "13";
	public static final String AQC_FREE_CHILD = "14";
	public static final String AQC_FREE_ADULT = "15";
	public static final String AQC_YOUNG_DRIVER = "16";
	public static final String AQC_YOUNGER_DRIVER = "17";
	public static final String AQC_UNDER_10 = "18";

	public static final String AUT_DELIVERY = "1";
	public static final String AUT_MAILING = "2";
	public static final String AUT_BILLING = "3";
	public static final String AUT_CREDIT_CARD = "4";
	public static final String AUT_OTHER = "5";
	public static final String AUT_CONTACT = "6";
	public static final String AUT_PHYSICAL = "7";
	public static final String AUT_PRE_OPENING_OFFICE = "8";
	public static final String AUT_COLLECTION = "9";
	public static final String AUT_CHAIN = "10";
	public static final String AUT_DEPOSIT = "11";
	public static final String AUT_HOTEL = "12";
	public static final String AUT_PERMANENT = "13";

	public static final String BCC_ALL_SUITE = "1";
	public static final String BCC_BUDGET = "2";
	public static final String BCC_CORPORATE_BUSINESS_TRANSIENT = "3";
	public static final String BCC_DELUXE = "4";
	public static final String BCC_ECONOMY = "5";
	public static final String BCC_EXTENDED_STAY = "6";
	public static final String BCC_FIRST_CLASS = "7";
	public static final String BCC_LUXURY = "8";
	public static final String BCC_MEETING_CONVENTION = "9";
	public static final String BCC_MODERATE = "10";
	public static final String BCC_RESIDENTIAL_APARTMENT = "11";
	public static final String BCC_RESORT = "12";
	public static final String BCC_TOURIST = "13";
	public static final String BCC_UPSCALE = "14";
	public static final String BCC_EFFICIENCY = "15";
	public static final String BCC_STANDARD = "16";

	public static final String BCT_GLOBAL_DISTRIBUTION_SYSTEM_GDS = "1";
	public static final String BCT_ALTERNATIVE_DISTRIBUTION_SYSTEM_ADS = "2";
	public static final String BCT_SALES_AND_CATERING_SYSTEM_SCS = "3";
	public static final String BCT_PROPERTY_MANAGEMENT_SYSTEM_PMS = "4";
	public static final String BCT_CENTRAL_RESERVATION_SYSTEM_CRS = "5";
	public static final String BCT_TOUR_OPERATOR_SYSTEM_TOS = "6";
	public static final String BCT_INTERNET = "7";

	public static final String BED_DOUBLE = "1";
	public static final String BED_FUTON = "2";
	public static final String BED_KING = "3";
	public static final String BED_MURPHY = "4";
	public static final String BED_QUEEN = "5";
	public static final String BED_SOFA = "6";
	public static final String BED_TATAMI = "7";
	public static final String BED_TWIN = "8";
	public static final String BED_SINGLE = "9";

	public static final String CDT_CREDIT = "1";
	public static final String CDT_DEBIT = "2";
	public static final String CDT_CENTRAL_BILL = "3";

	public static final String DEC_SPACE_NOT_AVAILABLE = "1";
	public static final String DEC_ROOMS_NOT_AVAILABLE = "2";
	public static final String DEC_FUNCTION_SPACE_NOT_AVAILABLE = "3";
	public static final String DEC_UNDER_CONSTRUCTION = "4";
	public static final String DEC_SLEEPING_ROOMS_NOT_AVAILABLE = "5";
	public static final String DEC_PREFERRED_DATES_NOT_AVAILABLE = "6";
	public static final String DEC_REQUESTED_RATE_NOT_AVAILABLE = "7";
	public static final String DEC_HOTEL_AND_GROUP_NEEDS_NOT_COMPATIBLE = "8";
	public static final String DEC_OTHER = "9";
	public static final String DEC_REPORT_DATA_NOT_AVAILABLE = "10";

	public static final String EAT_PERSONAL = "1";
	public static final String EAT_BUSINESS = "2";
	public static final String EAT_LISTSERVE = "3";
	public static final String EAT_INTERNET = "4";
	public static final String EAT_PROPERTY = "5";
	public static final String EAT_SALES_OFFICE = "6";
	public static final String EAT_RESERVATION_OFFICE = "7";
	public static final String EAT_MANAGING_COMPANY = "8";

	public static final String ERR_UNKNOWN = "1";
	public static final String ERR_NO_IMPLEMENTATION = "2";
	public static final String ERR_BIZ_RULE = "3";
	public static final String ERR_AUTHENTICATION = "4";
	public static final String ERR_AUTHENTICATION_TIMEOUT = "5";
	public static final String ERR_AUTHORIZATION = "6";
	public static final String ERR_PROTOCOL_VIOLATION = "7";
	public static final String ERR_TRANSACTION_MODEL = "8";
	public static final String ERR_AUTHENTICAL_MODEL = "9";
	public static final String ERR_REQUIRED_FIELD_MISSING = "Required field missing";
	public static final String ERR_START_DATE_IS_IN_THE_PAST = "Start date is in the past";
	public static final String ERR_END_DATE_IS_IN_THE_PAST = "End date is in the past";
	public static final String ERR_INVALID_AGENT_ID = "Invalid point of sale ID";
	public static final String ERR_INVALID_HOTEL_ID = "Invalid hotel ID";
	public static final String ERR_INVALID_RESERVATION_ID = "UniqueID_Invalid";
	public static final String ERR_INVALID_RESERVATION_STATE = "State_Invalid";
	public static final String ERR_END_DATE_IS_BEFORE_START_DATE = "End date is before start date";
	public static final String ERR_INVALID_DATE_FORMAT = "Date is not in the correct format";
	
	public static final String EWT_UNKNOWN = "1";
	public static final String EWT_NO_IMPLEMENTATION = "2";
	public static final String EWT_BIZ_RULE = "3";
	public static final String EWT_AUTHENTICATION = "4";
	public static final String EWT_AUTHENTICATION_TIMEOUT = "5";
	public static final String EWT_AUTHORIZATION = "6";
	public static final String EWT_PROTOCOL_VIOLATION = "7";
	public static final String EWT_TRANSACTION_MODEL = "8";
	public static final String EWT_AUTHENTICAL_MODEL = "9";
	public static final String EWT_REQUIRED_FIELD_MISSING = "10";
	public static final String EWT_UNSUPPORTED_LANGUAGE_REQUESTED = "184";
	public static final String EWT_NO_AVAILABILITY = "322";
	public static final String EWT_INVALID_ROOMSTAY_CANDIDATE_SPECIFIED = "363";
	public static final String EWT_INVALID_XML = "367";
	public static final String EWT_START_DATE_IS_IN_THE_PAST = "381";
	public static final String EWT_END_DATE_IS_IN_THE_PAST = "382";
	public static final String EWT_INVALID_DATE_FORMAT = "383";
	public static final String EWT_INVALID_AGENT_ID = "391";
	public static final String EWT_INVALID_HOTEL_ID = "392";
	public static final String EWT_INVALID_RESERVATION_ID = "393";
	public static final String EWT_INVALID_RESERVATION_STATE = "394";
	public static final String EWT_END_DATE_IS_BEFORE_START_DATE = "404";
	public static final String EWT_GENERAL_ERROR = "448";
	public static final String EWT_UNABLE_TO_PROCESS = "450";

	public static final String FTT_BED_TAX = "1";
	public static final String FTT_CITY_HOTEL_FEE = "2";
	public static final String FTT_CITY_TAX = "3";
	public static final String FTT_COUNTY_TAX = "4";
	public static final String FTT_ENERGY_TAX = "5";
	public static final String FTT_FEDERAL_TAX = "6";
	public static final String FTT_FOOD_BEVERAGE_TAX = "7";
	public static final String FTT_LODGING_TAX = "8";
	public static final String FTT_MAINTENANCE_FEE = "9";
	public static final String FTT_OCCUPANCY_TAX = "10";
	public static final String FTT_PACKAGE_FEE = "11";
	public static final String FTT_RESORT_FEE = "12";
	public static final String FTT_SALES_TAX = "13";
	public static final String FTT_SERVICE_CHARGE = "14";
	public static final String FTT_STATE_TAX = "15";
	public static final String FTT_SURCHARGE = "16";
	public static final String FTT_TOTAL_TAX = "17";
	public static final String FTT_TOURISM_TAX = "18";
	public static final String FTT_VAT_GST_TAX = "19";
	public static final String FTT_SURPLUS_LINES_TAX = "20";
	public static final String FTT_INSURANCE_PREMIUM_TAX = "21";
	public static final String FTT_APPLICATION_FEE = "22";
	public static final String FTT_EXPRESS_HANDLING_FEE = "23";
	public static final String FTT_EXEMPT = "24";
	public static final String FTT_STANDARD = "25";
	public static final String FTT_ZERO_RATED = "26";
	public static final String FTT_MISCELLANEOUS = "27";
	public static final String FTT_ROOM_TAX = "28";
	public static final String FTT_EARLY_CHECKOUT_FEE = "29";
	public static final String FTT_COUNTRY_TAX = "30";
	public static final String FTT_EXTRA_PERSON_CHARGE = "31";
	public static final String FTT_BANQUET_SERVICE_FEE = "32";
	public static final String FTT_ROOM_SERVICE_FEE = "33";
	public static final String FTT_LOCAL_FEE = "34";
	public static final String FTT_GOODS_AND_SERVICES_TAX_GST = "35";
	public static final String FTT_VALUE_ADDED_TAX_VAT = "36";
	public static final String FTT_CRIB_FEE = "37";
	public static final String FTT_ROLLAWAY_FEE = "38";
	public static final String FTT_ASSESSMENT_LICENSE_TAX = "39";
	public static final String FTT_PET_SANTITATION_FEE = "40";
	public static final String FTT_NOT_KNOWN = "41";

	public static final String INV_PHYSICAL = "1";
	public static final String INV_DEFINITIVE_AVAILABILITY = "2";
	public static final String INV_TENTATIVE_AVAILABILITY = "3";
	public static final String INV_DEFINITE_SOLD = "4";
	public static final String INV_TENTATIVE_SOLD = "5";
	public static final String INV_OUT_OF_ORDER = "6";
	public static final String INV_NOT_AVAILABLE_FOR_SALE = "7";
	public static final String INV_OUT_OF_INVENTORY = "8";
	public static final String INV_OFF_MARKET = "9";
	public static final String INV_UNDER_RENOVATION = "10";
	public static final String INV_UNDER_CONSTRUCTION = "11";
	public static final String INV_TRANSIENT_AUTHORIZED = "12";
	public static final String INV_TRANSIENT_SOLD = "13";
	public static final String INV_TRANSIENT_AVAILABLE = "14";
	public static final String INV_TRANSIENT_ACTUAL_AVAILABLE = "15";
	public static final String INV_GROUP_AUTHORIZED = "16";
	public static final String INV_GROUP_SOLD = "17";
	public static final String INV_GROUP_AVAILABLE = "18";
	public static final String INV_CONTRACT_AUTHORIZED = "19";
	public static final String INV_CONTRACT_SOLD = "20";
	public static final String INV_CONTRACT_AVAILABLE = "21";

	public static final String PET_CATS_ONLY = "1";
	public static final String PET_DOGS_ONLY = "2";
	public static final String PET_LARGE_DOMESTIC_ANIMALS = "3";
	public static final String PET_MIDSIZE_DOMESTIC_ANIMALS = "4";
	public static final String PET_SMALL_DOMESTIC_ANIMALS_ONLY = "5";
	public static final String PET_WORKING_ANIMALS_ONLY = "6";
	public static final String PET_ALL_PETS = "7";
	public static final String PET_SMALL_DOMESTIC_ANIMALS = "8";
	public static final String PET_WORKING_ANIMALS = "9";
	public static final String PET_DOMESTIC_PETS = "10";

	public static final String PIC_EXTERIOR_VIEW = "1";
	public static final String PIC_LOBBY_VIEW = "2";
	public static final String PIC_POOL_VIEW = "3";
	public static final String PIC_RESTAURANT = "4";
	public static final String PIC_HEALTH_CLUB = "5";
	public static final String PIC_GUEST_ROOM = "6";
	public static final String PIC_SUITE = "7";
	public static final String PIC_MEETING_ROOM = "8";
	public static final String PIC_BALLROOM_ = "9";
	public static final String PIC_GOLF_COURSE = "10";
	public static final String PIC_BEACH = "11";
	public static final String PIC_SPA = "12";
	public static final String PIC_BAR_LOUNGE = "13";
	public static final String PIC_RECREATIONAL_FACILITY = "14";
	public static final String PIC_LOGO = "15";
	public static final String PIC_BASICS = "16";
	public static final String PIC_MAP = "17";
	public static final String PIC_PROMOTIONAL = "18";
	public static final String PIC_HOT_NEWS = "19";
	public static final String PIC_MISCELLANEOUS = "20";
	public static final String PIC_GUEST_ROOM_AMENITY = "21";
	public static final String PIC_PROPERTY_AMENITY = "22";
	public static final String PIC_BUSINESS_CENTER = "23";

	public static final String PLT_BRAND_RESERVATIONS_OFFICE = "1";
	public static final String PLT_CENTRAL_RESERVATIONS_OFFICE = "2";
	public static final String PLT_PROPERTY_RESERVATION_OFFICE = "3";
	public static final String PLT_PROPERTY_DIRECT = "4";
	public static final String PLT_SALES_OFFICE = "5";
	public static final String PLT_HOME = "6";
	public static final String PLT_OFFICE = "7";
	public static final String PLT_OTHER = "8";
	public static final String PLT_MANAGING_COMPANY = "9";

	public static final String PMT_CASH = "1";
	public static final String PMT_DIRECT_BILL = "2";
	public static final String PMT_VOUCHER = "3";
	public static final String PMT_PRE_PAY = "4";
	public static final String PMT_CREDIT_CARD = "5";
	public static final String PMT_DEBIT_CARD = "6";
	public static final String PMT_CHECK = "7";
	public static final String PMT_DEPOSIT = "8";
	public static final String PMT_BUSINESS_ACCOUNT = "9";
	public static final String PMT_CENTRAL_BILL = "10";
	public static final String PMT_COUPON = "11";
	public static final String PMT_BUSINESS_CHECK = "12";
	public static final String PMT_PERSONAL_CHECK = "13";
	public static final String PMT_MONEY_ORDER = "14";
	public static final String PMT_REDEMPTION = "15";
	public static final String PMT_BARTER = "16";
	public static final String PMT_MISCELLANEOUS_CHARGE_ORDER = "17";
	public static final String PMT_TRAVEL_AGENCY_NAME_ADDRESS = "18";
	public static final String PMT_TRAVEL_AGENCY_IATA_NUMBER = "19";
	public static final String PMT_CERTIFIED_CHECK = "20";
	public static final String PMT_CLUB_MEMBERSHIP_ID = "21";
	public static final String PMT_FREQUENT_GUEST_NUMBER = "22";
	public static final String PMT_FREQUENT_TRAVELER_NUMBER = "23";
	public static final String PMT_GUEST_NAME_ADDRESS = "24";
	public static final String PMT_SPECIAL_INDUSTRY_PROGRAM = "25";
	public static final String PMT_TOUR_ORDER = "26";
	public static final String PMT_TRAVELERS_CHECK = "27";
	public static final String PMT_WIRE_PAYMENT = "28";
	public static final String PMT_COMPANY_NAME_ADDRESS = "29";
	public static final String PMT_CORPORATE_ID_CD_NUMBER = "30";
	public static final String PMT_GUARANTEE = "31";
	public static final String PMT_OTHER_INFORMATION = "32";
	public static final String PMT_OVERRIDE_GUARANTEE_INFORMATION = "33";
	public static final String PMT_CORPORATE = "34";
	public static final String PMT_AIRLINE_PAYMENT_CARD = "35";
	public static final String PMT_AIR_TRAVEL_CARD = "36";

	public static final String PRT_CUSTOMER = "1";
	public static final String PRT_GDS = "2";
	public static final String PRT_CORPORATION = "3";
	public static final String PRT_TRAVEL_AGENT = "4";
	public static final String PRT_WHOLESALER = "5";
	public static final String PRT_GROUP = "6";
	public static final String PRT_TOUR_OPERATOR = "7";
	public static final String PRT_CRO = "8";
	public static final String PRT_REP_COMPANY = "9";
	public static final String PRT_INTERNET_BROKER = "10";
	public static final String PRT_AIRLINE = "11";
	public static final String PRT_HOTEL = "12";
	public static final String PRT_CAR_RENTAL = "13";
	public static final String PRT_CRUISE_LINE = "14";
	public static final String PRT_EMPLOYEE = "15";
	public static final String PRT_EVENT_HOST = "16";
	public static final String PRT_SUPPLIER_PARTNER = "17";
	public static final String PRT_BILLING_CONTACT = "18";
	public static final String PRT_AUTHORIZED_SIGNATURE = "19";
	public static final String PRT_GENERAL_SERVICE_CONTRACTOR = "20";
	public static final String PRT_ARRANGER = "21";
	public static final String PRT_ASSOCIATION = "22";

	public static final String PTT_VOICE = "1";
	public static final String PTT_DATA = "2";
	public static final String PTT_FAX = "3";
	public static final String PTT_PAGER = "4";
	public static final String PTT_MOBILE = "5";
	public static final String PTT_TTY = "6";
	public static final String PTT_TELEX = "7";
	public static final String PTT_VOICE_OVER_IP = "8";

	public static final String PUT_EMERGENCY_CONTACT = "1";
	public static final String PUT_TRAVEL_ARRANGER = "2";
	public static final String PUT_DAYTIME_CONTACT = "3";
	public static final String PUT_EVENING_CONTACT = "4";
	public static final String PUT_CONTACT = "5";
	public static final String PUT_TOLL_FREE_NUMBER = "6";
	public static final String PUT_GUEST_USE = "7";
	public static final String PUT_PICKUP_CONTACT = "8";
	public static final String PUT_ELECTRONIC_DOCUMENT_REFERENCE = "9";

	public static final String REL_ASSET_MANAGER = "1";
	public static final String REL_FRANCHISEE = "2";
	public static final String REL_GENERAL_MANAGER = "3";
	public static final String REL_INTERMEDIARY = "4";
	public static final String REL_JOINT_VENTURE = "5";
	public static final String REL_LLC = "6";
	public static final String REL_LAND_OWNER = "7";
	public static final String REL_LESSEE = "8";
	public static final String REL_LESSOR = "9";
	public static final String REL_MANAGER = "10";
	public static final String REL_MARKETING_AGREEMENT = "11";
	public static final String REL_NONE = "12";
	public static final String REL_OWNER = "13";
	public static final String REL_PARTNER_JV = "14";
	public static final String REL_PARTNER_MINORITY = "15";
	public static final String REL_PARTNERSHIP = "16";
	public static final String REL_RELATED_INVESTMENTS = "17";
	public static final String REL_SUBSIDIARY = "18";

	public static final String RMO_MINIMUM= "1";
	public static final String RMO_MAXIMUM= "2";
	public static final String RMO_AVERAGE= "3";
	public static final String RMO_ARRIVAL= "4";
	public static final String RMO_ACTUAL= "5";
	public static final String RMO_QUOTED= "6";
	public static final String RMO_BOOKED= "7";

	public static final String RPT_ASSOCIATION = "1";
	public static final String RPT_CLUB_CONCIERGE = "2";
	public static final String RPT_CONVENTION = "3";
	public static final String RPT_CORPORATE = "4";
	public static final String RPT_DAY_RATE = "5";
	public static final String RPT_DISTRESSED_INVENTORY = "6";
	public static final String RPT_FAMILY_PLAN = "7";
	public static final String RPT_GOVERNMENT = "8";
	public static final String RPT_MILITARY = "9";
	public static final String RPT_NEGOTIATED = "10";
	public static final String RPT_PACKAGE = "11";
	public static final String RPT_PROMOTIONAL = "12";
	public static final String RPT_REGULAR_RACK = "13";
	public static final String RPT_SENIOR_CITIZEN = "14";
	public static final String RPT_TOUR_WHOLESALE = "15";
	public static final String RPT_TRAVEL_INDUSTRY = "16";
	public static final String RPT_WEEKEND  = "17";
	public static final String RPT_LAST_ROOM_AVAILABLE = "18";
	public static final String RPT_NON_LAST_ROOM_AVAILABLE = "19";
	public static final String RPT_CONSORTIA  = "20";
	public static final String RPT_GROUP = "21";
	public static final String RPT_CONTRACT = "22";
	public static final String RPT_PROMOTIONAL_PACKAGE_TOUR = "23";
	public static final String RPT_PUBLISHED = "24";
	public static final String RPT_NET = "25";
	public static final String RPT_MULTI_DAY_PACKAGE = "26";
	public static final String RPT_WEEKLY = "27";
	public static final String RPT_MONTHLY = "28";

	public static final String RST_ARCHERY = "1";
	public static final String RST_BADMINTON_COURT = "2";
	public static final String RST_BASEBALL_DIAMOND = "3";
	public static final String RST_BASKETBALL_COURT = "4";
	public static final String RST_BEACH = "5";
	public static final String RST_BIKE_TRAIL = "6";
	public static final String RST_BOATING = "7";
	public static final String RST_BOCCI_COURT = "8";
	public static final String RST_BOWLING_ALLEY = "9";
	public static final String RST_CHILDRENS_PROGRAM_ONSITE = "10";
	public static final String RST_CRICKET_PITCH = "12";
	public static final String RST_CROSS_COUNTRY_SKIING = "13";
	public static final String RST_DART_BOARD = "14";
	public static final String RST_DOWNHILL_SKIING = "18";
	public static final String RST_EQUIPMENT_AVAILABLE_IN_HEALTH_CLUB = "19";
	public static final String RST_FISHING = "20";
	public static final String RST_FITNESS_CENTER_OFF_SITE = "21";
	public static final String RST_FITNESS_CENTER_ON_SITE = "23";
	public static final String RST_FLY_FISHING = "25";
	public static final String RST_FOOTBALL_FIELD = "26";
	public static final String RST_GOLF = "27";
	public static final String RST_GOLF_LOCATION_ONSITE_NEARBY = "34";
	public static final String RST_GYM = "35";
	public static final String RST_HEALTH_CLUB = "36";
	public static final String RST_HIKING_TRAIL = "60";
	public static final String RST_HORSEBACK_RIDING = "61";
	public static final String RST_INDOOR_TENNIS_COURTS = "62";
	public static final String RST_JET_SKI = "63";
	public static final String RST_JOGGING_TRAIL = "64";
	public static final String RST_KAYAKING = "65";
	public static final String RST_LAKE = "66";
	public static final String RST_MINIATURE_GOLF = "67";
	public static final String RST_MOUNTAIN_BIKING_TRAIL = "68";
	public static final String RST_OCEAN = "70";
	public static final String RST_OUTDOOR_TENNIS_COURTS = "71";
	public static final String RST_PARASAILING = "73";
	public static final String RST_PLAYGROUND = "74";
	public static final String RST_POOL = "75";
	public static final String RST_PUTT_PUTT_GOLF = "76";
	public static final String RST_RACQUETBALL_COURT = "77";
	public static final String RST_RIVER = "78";
	public static final String RST_RIVER_RAFTING = "79";
	public static final String RST_SAILING = "80";
	public static final String RST_SAUNA = "81";
	public static final String RST_SCUBA_DIVING = "82";
	public static final String RST_SHOPPING = "83";
	public static final String RST_SKATING_RINK = "84";
	public static final String RST_SKEET_SHOOTING = "85";
	public static final String RST_SNORKELING = "86";
	public static final String RST_SNOW_BOARDING = "87";
	public static final String RST_SNOW_SKIING = "88";
	public static final String RST_SOCCER_FIELD = "89";
	public static final String RST_SOLARIUM = "90";
	public static final String RST_SPA = "91";
	public static final String RST_SQUASH_COURT = "92";
	public static final String RST_STEAM_BATH = "93";
	public static final String RST_TENNIS_COURT = "94";
	public static final String RST_TUBING = "96";
	public static final String RST_VELODROME = "97";
	public static final String RST_VOLLEYBALL = "98";
	public static final String RST_WATER_SKIING = "99";
	public static final String RST_WHIRLPOOL = "100";
	public static final String RST_WINDSURFING = "101";
	public static final String RST_DRIVING_RANGE = "102";
	public static final String RST_CAMPING = "103";
	public static final String RST_HOT_TUB = "104";
	public static final String RST_HUNTING = "105";
	public static final String RST_INDOOR_OUTDOOR_CONNECTING_POOL = "106";
	public static final String RST_JACUZZI = "107";
	public static final String RST_MOUNTAIN_CLIMBING = "108";
	public static final String RST_NATURE_PRESERVE_TRAIL = "109";
	public static final String RST_WATER_ACTIVITIES = "110";
	public static final String RST_BILLIARDS = "111";
	public static final String RST_ROCK_CLIMBING = "112";
	public static final String RST_SAFARI = "113";
	public static final String RST_SPORTS_COURT_ONSITE = "114";
	public static final String RST_SUN_TANNING_BED = "115";
	public static final String RST_SURFING = "116";
	public static final String RST_TABLE_TENNIS = "117";
	public static final String RST_WINE_TASTING = "118";
	public static final String RST_WINTER_SPORTS = "119";
	public static final String RST_SNOW_MOBILING = "120";
	public static final String RST_TEEN_PROGRAMS = "121";
	public static final String RST_INDOOR_POOL = "122";
	public static final String RST_OUTDOOR_POOL = "123";
	public static final String RST_CHILDRENS_PROGRAM_OFFSITE = "124";
	public static final String RST_CHILDRENS_PROGRAM = "125";
	public static final String RST_ANIMAL_WATCHING = "126";
	public static final String RST_BIRD_WATCHING = "127";
	public static final String RST_BOXING = "128";
	public static final String RST_CHILDRENS_POOL = "129";
	public static final String RST_DANCING = "130";
	public static final String RST_DOG_RACING = "131";
	public static final String RST_FINE_DINING = "132";
	public static final String RST_GAMBLING = "133";
	public static final String RST_GARDEN = "134";
	public static final String RST_HELICOPTER_AIRPLANE_SIGHTSEEING = "135";
	public static final String RST_HORSE_RACING = "136";
	public static final String RST_ICE_SKATING = "137";
	public static final String RST_KARAOKE = "138";
	public static final String RST_MUSEUM_GALLERY_VIEWING = "139";
	public static final String RST_NIGHTCLUBS = "140";
	public static final String RST_POLO = "141";
	public static final String RST_SIGHTSEEING_TOURS = "142";
	public static final String RST_SPORTS_EVENTS = "143";
	public static final String RST_SKYDIVING = "144";
	public static final String RST_SUNBATHING = "145";
	public static final String RST_THEATRE = "146";
	public static final String RST_WEIGHTLIFTING = "147";
	public static final String RST_WRESTLING = "148";
	public static final String RST_CANOEING = "149";
	public static final String RST_UPSCALE_SHOPPING = "150";
	public static final String RST_OUTLET_SHOPPING = "151";
	public static final String RST_ANTIQUE_SHOPPING = "152";
	public static final String RST_CARDIOVASCULAR_EXERCISE = "153";
	public static final String RST_BOCCE_COURT = "154";
	public static final String RST_DIRECT_ACCESS_TO_A_BEACH = "155";
	public static final String RST_SKI_IN_OUT_FACILITIES = "156";
	public static final String RST_TENNIS_PROFESSIONAL = "157";

	public static final String RTC_GOVERNMENT = "1";
	public static final String RTC_BUSINESS = "2";
	public static final String RTC_LEISURE = "3";
	public static final String RTC_EMPLOYEE = "4";
	public static final String RTC_VIP = "5";
	public static final String RTC_ALL = "6";
	public static final String RTC_ASSOCIATION = "7";
	public static final String RTC_BUSINESS_STANDARD = "8";
	public static final String RTC_CORPORATE = "9";
	public static final String RTC_INDUSTRY = "10";
	public static final String RTC_FULLY_INCLUSIVE = "10";
	public static final String RTC_INCLUSIVE = "11";
	public static final String RTC_NEGOTIATED = "12";
	public static final String RTC_PROMOTIONAL = "13";
	public static final String RTC_CREDENTIAL = "14";
	public static final String RTC_STANDARD = "15";
	public static final String RTC_CONSORTIUMS = "16";
	public static final String RTC_CONVENTIONS = "17";

	public static final String RUL_CANCEL= "1";
	public static final String RUL_PREPAY= "2";
	public static final String RUL_DEPOSIT= "3";
	public static final String RUL_GUARANTEE= "4";

	public static final String RVT_AIRPORT_VIEW = "1";
	public static final String RVT_BAY_VIEW = "2";
	public static final String RVT_CITY_VIEW = "3";
	public static final String RVT_COURTYARD_VIEW = "4";
	public static final String RVT_GOLF_VIEW = "5";
	public static final String RVT_HARBOR_VIEW = "6";
	public static final String RVT_INTERCOASTAL_VIEW = "7";
	public static final String RVT_LAKE_VIEW = "8";
	public static final String RVT_MARINA_VIEW = "9";
	public static final String RVT_MOUNTAIN_VIEW = "10";
	public static final String RVT_OCEAN_VIEW = "11";
	public static final String RVT_POOL_VIEW = "12";
	public static final String RVT_RIVER_VIEW = "13";
	public static final String RVT_WATER_VIEW = "14";
	public static final String RVT_BEACH_VIEW = "15";
	public static final String RVT_GARDEN_VIEW = "16";
	public static final String RVT_PARK_VIEW = "17";
	public static final String RVT_FOREST_VIEW = "18";
	public static final String RVT_RAIN_FOREST_VIEW = "19";
	public static final String RVT_VARIOUS_VIEWS = "20";
	public static final String RVT_LIMITED_VIEW = "21";
	public static final String RVT_SLOPE_VIEW = "22";
	public static final String RVT_STRIP_VIEW = "23";
	public static final String RVT_COUNTRYSIDE_VIEW = "24";
	public static final String RVT_SEA_VIEW = "25";

	public static final String TVS_AIR = "1";
	public static final String TVS_CAR = "2";
	public static final String TVS_HOTEL = "3";
	public static final String TVS_INSURANCE = "4";
	public static final String TVS_GOLF = "5";
	public static final String TVS_TOUR = "6";
	public static final String TVS_RAIL = "7";
	public static final String TVS_CRUISE = "8";
	public static final String TVS_EXCURSION = "9";
	public static final String TVS_FERRY = "10";

	public static final String UIT_CUSTOMER = "1";
	public static final String UIT_CUSTOMER_RESERVATIONS_OFFICE_CRO = "2";
	public static final String UIT_CORPORATION_REPRESENTATIVE = "3";
	public static final String UIT_COMPANY = "4";
	public static final String UIT_TRAVEL_AGENCY = "5";
	public static final String UIT_AIRLINE = "6";
	public static final String UIT_WHOLESALER = "7";
	public static final String UIT_CAR_RENTAL = "8";
	public static final String UIT_GROUP = "9";
	public static final String UIT_HOTEL = "10";
	public static final String UIT_TOUR_OPERATOR = "11";
	public static final String UIT_CRUISE_LINE = "12";
	public static final String UIT_INTERNET_BROKER = "13";
	public static final String UIT_RESERVATION = "14";
	public static final String UIT_CANCELLATION = "15";
	public static final String UIT_REFERENCE = "16";
	public static final String UIT_MEETING_PLANNING_AGENCY = "17";
	public static final String UIT_OTHER = "18";
	public static final String UIT_INSURANCE_AGENCY = "19";
	public static final String UIT_INSURANCE_AGENT = "20";
	public static final String UIT_PROFILE = "21";
	public static final String UIT_ELECTRONIC_RESERVATION_SERVICE_PROVIDER_ERSP = "22";

	public static final String UOM_MILES = "1";
	public static final String UOM_KILOMETERS = "2";
	public static final String UOM_METERS = "3";
	public static final String UOM_MILLIMETERS = "4";
	public static final String UOM_CENTIMETERS = "5";
	public static final String UOM_YARDS = "6";
	public static final String UOM_FEET = "7";
	public static final String UOM_INCHES = "8";
	public static final String UOM_PIXELS = "9";
	public static final String UOM_BLOCK = "10";
	public static final String UOM_MEGABYTES = "11";
	public static final String UOM_GIGABYTES = "12";
	public static final String UOM_SQUARE_FEET = "13";
	public static final String UOM_SQUARE_METERS = "14";
	public static final String UOM_POUNDS = "15";
	public static final String UOM_KILOGRAMS = "16";
	public static final String UOM_SQUARE_INCH = "17";
	public static final String UOM_SQUARE_YARD = "18";
	public static final String UOM_ACRE = "19";
	public static final String UOM_SQUARE_MILLIMETER = "20";
	public static final String UOM_SQUARE_CENTIMETER = "21";
	public static final String UOM_HECTARE = "22";
	public static final String UOM_OUNCE = "23";
	public static final String UOM_GRAM = "24";

	public static final String URL_HOME = "1";
}