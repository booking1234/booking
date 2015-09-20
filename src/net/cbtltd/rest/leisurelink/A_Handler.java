package net.cbtltd.rest.leisurelink;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.input.SAXBuilder;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.leisurelink.ExternalServices.ArrayOfDistributionProperty;
import net.cbtltd.rest.leisurelink.ExternalServices.ArrayOfRate;
import net.cbtltd.rest.leisurelink.ExternalServices.ArrayOfRateAvailability;
import net.cbtltd.rest.leisurelink.ExternalServices.ArrayOfRoomType;
import net.cbtltd.rest.leisurelink.ExternalServices.DistributionProperty;
import net.cbtltd.rest.leisurelink.ExternalServices.DistributionService;
import net.cbtltd.rest.leisurelink.ExternalServices.DistributionServiceSoap;
import net.cbtltd.rest.leisurelink.ExternalServices.RateAvailability;
import net.cbtltd.rest.leisurelink.ExternalServices.RateAvailabilityUpdateRQ;
import net.cbtltd.rest.leisurelink.ExternalServices.RoomType;
import net.cbtltd.rest.leisurelink.ExternalServices.Rate;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType;
import net.cbtltd.soap.ota.server.AvailRequestSegmentsType.AvailRequestSegment;
import net.cbtltd.soap.ota.server.BasicPropertyInfoType;
import net.cbtltd.soap.ota.server.CommentType;
import net.cbtltd.soap.ota.server.CountryNameType;
import net.cbtltd.soap.ota.server.CustomerType;
import net.cbtltd.soap.ota.server.DateTimeSpanType;
import net.cbtltd.soap.ota.server.GuaranteeType;
import net.cbtltd.soap.ota.server.GuestCountType;
import net.cbtltd.soap.ota.server.HotelDescriptiveInfoRequestType.ContactInfo;
import net.cbtltd.soap.ota.server.HotelDescriptiveInfoRequestType.FacilityInfo;
import net.cbtltd.soap.ota.server.HotelDescriptiveInfoRequestType.HotelInfo;
import net.cbtltd.soap.ota.server.HotelDescriptiveInfoRequestType.MultimediaObjects;
import net.cbtltd.soap.ota.server.HotelDescriptiveInfoRequestType.Policies;
import net.cbtltd.soap.ota.server.HotelReservationType;
import net.cbtltd.soap.ota.server.HotelReservationsType;
import net.cbtltd.soap.ota.server.HotelSearchCriteriaType;
import net.cbtltd.soap.ota.server.ItemSearchCriterionType;
import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;
import net.cbtltd.soap.ota.server.OTAHotelResRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.POSType;
import net.cbtltd.soap.ota.server.PaymentCardType;
import net.cbtltd.soap.ota.server.PersonNameType;
import net.cbtltd.soap.ota.server.ProfileType;
import net.cbtltd.soap.ota.server.ProfilesType;
import net.cbtltd.soap.ota.server.RatePlanCandidatesType;
import net.cbtltd.soap.ota.server.RatePlanCandidatesType.RatePlanCandidate;
import net.cbtltd.soap.ota.server.RatePlanType;
import net.cbtltd.soap.ota.server.ResGuestsType;
import net.cbtltd.soap.ota.server.RoomStayType;
import net.cbtltd.soap.ota.server.RoomStayType.RoomTypes;
import net.cbtltd.soap.ota.server.RoomStaysType;
import net.cbtltd.soap.ota.server.RoomTypeType;
import net.cbtltd.soap.ota.server.SourceType;
import net.cbtltd.soap.ota.server.SourceType.RequestorID;
import net.cbtltd.soap.ota.server.StateProvType;
import net.cbtltd.soap.ota.server.TransactionActionType;
import net.cbtltd.soap.ota.server.UniqueIDType;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class A_Handler extends PartnerHandler implements IsPartner {
	
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final String account = "99";
	private static final String password = "LL$99test0814";
	private static final String username = "TestChannel99";
	private static final String type = "13";
	private static final String versionService = "1.003";
	private static final String versionServiceCancel ="1.001";
	private static final String versionServiceRead ="1.001";
	private static final String planCode = "RAC";
	private static final String chainCode ="LI";
	
	private static final String LEISURELINK_API_NAMESPACE_URL = "http://www.opentravel.org/OTA/2003/05";
	private static final Namespace LEISURELINK_API_NAMESPACE = Namespace.getNamespace(LEISURELINK_API_NAMESPACE_URL);
	
	

	/**
	 * Instantiates a new partner handler.
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param partner
	 *            the partner.
	 */
	public A_Handler(Partner partner) {
		super(partner);
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		boolean isAvailable = false;
		Date timestamp = new Date();
		String message = "LeisureLink isAvailable " + reservation.getId();
		LOG.debug(message);
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		
        try{
        	Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			String productaltid =  product.getAltid();
			String hotelCode = product.getAltSupplierId();
			String roomCode = productaltid.substring(productaltid.indexOf("-")+1, productaltid.length());
			String dateFrom = DF.format(reservation.getFromdate());
			String dateTo = DF.format(reservation.getTodate());
			
			
			/*String otaRequest = "<OTA_HotelAvailRQ EchoToken=\" \" Version=\"1.003\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><AvailRequestSegments><AvailRequestSegment><StayDateRange Start=\""+dateFrom+"\" End=\""+dateTo+"\" /><RatePlanCandidates><RatePlanCandidate RatePlanCode=\"RAC\" RatePlanType=\"13\" /></RatePlanCandidates><RoomStayCandidates><RoomStayCandidate Quantity=\"1\" RoomTypeCode=\""+roomCode+"\"><GuestCounts><GuestCount Count=\"1\" /></GuestCounts></RoomStayCandidate></RoomStayCandidates><HotelSearchCriteria><Criterion><HotelRef ChainCode=\"LI\" HotelCode=\""+hotelCode+"\" /></Criterion></HotelSearchCriteria></AvailRequestSegment></AvailRequestSegments></OTA_HotelAvailRQ>";*/
			
			OTAHotelAvailRQ otaRequest = new OTAHotelAvailRQ();
			otaRequest.setEchoToken(" ");
			BigDecimal versionDecimal = new BigDecimal(versionService);
			otaRequest.setVersion(versionDecimal);
	
			
			POSType posType = new POSType();
			SourceType sourceType = new SourceType();
			RequestorID requestorID = new RequestorID();
			requestorID.setID(account);
			requestorID.setType(type);
			requestorID.setMessagePassword(password);
			sourceType.setRequestorID(requestorID);
			posType.getSource().add(sourceType);		
			otaRequest.setPOS(posType);
			
			OTAHotelAvailRQ.AvailRequestSegments availRequestSegments = new OTAHotelAvailRQ.AvailRequestSegments();
			
			
			//AvailRequestSegmentsType availRequestSegmentsType = new AvailRequestSegmentsType();
			AvailRequestSegment availRequestSegment = new AvailRequestSegmentsType.AvailRequestSegment();
			
			DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
			dateTimeSpanType.setStart(dateFrom);
			dateTimeSpanType.setEnd(dateTo);
			availRequestSegment.setStayDateRange(dateTimeSpanType);
			
			RatePlanCandidatesType ratePlanCandidatesType = new RatePlanCandidatesType();
			RatePlanCandidate ratePlanCandidate = new RatePlanCandidatesType.RatePlanCandidate();
			ratePlanCandidate.setRatePlanCode(planCode);
			ratePlanCandidate.setRatePlanType(type);
			ratePlanCandidatesType.getRatePlanCandidate().add(ratePlanCandidate);
			availRequestSegment.setRatePlanCandidates(ratePlanCandidatesType);
			
			AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates roomStayCandidates = new AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates();
			AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate roomStayCandidate = new AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate();
			roomStayCandidate.setRoomTypeCode(roomCode);
			roomStayCandidate.setQuantity(1);
			GuestCountType guestCountType = new GuestCountType();
			GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
			guestCount.setCount(1);
			guestCountType.getGuestCount().add(guestCount);
			roomStayCandidate.setGuestCounts(guestCountType);
			roomStayCandidates.getRoomStayCandidate().add(roomStayCandidate);
			availRequestSegment.setRoomStayCandidates(roomStayCandidates);
			
			HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
			HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();
			ItemSearchCriterionType.HotelRef hotelRef = new ItemSearchCriterionType.HotelRef();
			hotelRef.setHotelCode(hotelCode);
			hotelRef.setChainCode(chainCode);
			criterion.getHotelRef().add(hotelRef);
			hotelSearchCriteriaType.getCriterion().add(criterion);
			availRequestSegment.setHotelSearchCriteria(hotelSearchCriteriaType);
			
			
			availRequestSegments.getAvailRequestSegment().add(availRequestSegment);
			
			otaRequest.setAvailRequestSegments(availRequestSegments);
			
			String request = MarshallerHelper.toXML(otaRequest);
			request = request.replaceFirst("xmlns:ns2", "xmlns");
			
//			System.out.println("Request:"+request);
	        String response = port.otaRequest(request);
			
	        System.out.println("Response:"+response);
			if(response.indexOf("Success")>0){
				isAvailable = true;
			}else if(response.indexOf("Errors")>0){
				isAvailable = false;
			}
	        
        }catch (Throwable x) {
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		MonitorService.monitor(message, timestamp);
		//System.out.println(isAvailable);
		return isAvailable;
		
		
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Leisure Link createReservation()");
//		Date timestamp = new Date();
//		String message = "createReservation LeisureLink (ApiKey:"+this.getApikey()+") " + reservation.getId();
//		LOG.debug(message);
//		
//		//StringBuilder otaRequest = new StringBuilder();
//		
//		RGWService ss = new RGWService();
//		RGWServiceSoap port = ss.getRGWServiceSoap();
//		try{
//			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
//			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, " reservation " + reservation.getName());}
//			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}
//
//			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
//			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
//			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}
//			
//			String bookingCode= "BPAL-"+ reservation.getId();
//			String productaltid =  reservation.getProduct().getAltid();
//			String hotelCode = reservation.getProduct().getAltSupplierId();
//			String roomCode = productaltid.substring(productaltid.indexOf("-")+1, productaltid.length());
//			
//			/*otaRequest.append("<OTA_HotelResRQ EchoToken=\" \" Version=\"1.003\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
//			otaRequest.append("<POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS>");
//			otaRequest.append("<UniqueID Type=\"1\" ID=\""+bookingCode+"\" />");
//			otaRequest.append("<HotelReservations><HotelReservation>");
//			otaRequest.append("<RoomStays><RoomStay>");
//			otaRequest.append("<RoomTypes><RoomType RoomTypeCode=\""+roomCode+"\" NumberOfUnits=\"1\" /></RoomTypes>");			
//			otaRequest.append("<RatePlans><RatePlan BookingCode=\""+bookingCode+"\" RatePlanCode=\"RAC\" RatePlanType=\"13\" /></RatePlans>");
//			otaRequest.append("<GuestCounts><GuestCount Count=\"1\" /></GuestCounts>");
//			otaRequest.append("<TimeSpan Start=\""+DF.format(reservation.getFromdate())+"\" End=\""+DF.format(reservation.getTodate())+"\" />");
//			otaRequest.append("<BasicPropertyInfo ChainCode=\"LI\" HotelCode=\""+hotelCode+"\" />");
//			otaRequest.append("<Comments><Comment><Text> </Text></Comment></Comments>");
//			otaRequest.append("</RoomStay></RoomStays>");
//			otaRequest.append("<ResGuests><ResGuest>");
//			otaRequest.append("<Profiles><ProfileInfo><Profile ProfileType=\"1\">");
//			otaRequest.append("<Customer>");
//			otaRequest.append("<PersonName><GivenName>"+customer.getFirstName()+"</GivenName><Surname>"+customer.getFamilyName()+"</Surname></PersonName>");
//			otaRequest.append("<Telephone PhoneNumber=\""+customer.getMobilephone()+"\" PhoneTechType=\"1\" />");
//			otaRequest.append("<Email>"+customer.getEmailaddress()+"</Email>");
//			otaRequest.append("<Address>");
//			otaRequest.append("<AddressLine>"+customer.getAddress()+"</AddressLine>");
//			otaRequest.append("<CityName>"+customer.getCity()+"</CityName>");
//			otaRequest.append("<PostalCode>"+customer.getPostalcode()+"</PostalCode>");
//			otaRequest.append("<StateProv>"+customer.getState()+"</StateProv>");
//			otaRequest.append("<CountryName>"+customer.getCountry()+"</CountryName>");
//			otaRequest.append("</Address>");
//			otaRequest.append("</Customer>");
//			otaRequest.append("</Profile></ProfileInfo></Profiles>");
//			otaRequest.append("</ResGuest></ResGuests>");
//			otaRequest.append("</HotelReservation></HotelReservations>");
//			otaRequest.append("</OTA_HotelResRQ>");	*/
//			
//			OTAHotelResRQ otaRequest = new OTAHotelResRQ();
//			otaRequest.setEchoToken(" ");
//			BigDecimal versionDecimal = new BigDecimal(versionService);
//			otaRequest.setVersion(versionDecimal);
//			
//			POSType posType = new POSType();
//			SourceType sourceType = new SourceType();
//			RequestorID requestorID = new RequestorID();
//			requestorID.setID(account);
//			requestorID.setType(type);
//			requestorID.setMessagePassword(password);
//			sourceType.setRequestorID(requestorID);
//			posType.getSource().add(sourceType);		
//			otaRequest.setPOS(posType);
//			
//			UniqueIDType uniqueIdType = new UniqueIDType(); 
//			uniqueIdType.setType("1");
//			uniqueIdType.setID(bookingCode);
//			otaRequest.getUniqueID().add(uniqueIdType);
//			
//			HotelReservationsType hotelReservationsType = new HotelReservationsType();
//			HotelReservationType hotelRreservationType = new HotelReservationType();
//			RoomStaysType roomStaysType = new RoomStaysType();
//			RoomStaysType.RoomStay roomStay = new RoomStaysType.RoomStay();
//			
//			RoomTypes roomTypes = new RoomTypes();
//			RoomTypeType roomTypeType = new RoomTypeType();
//			roomTypeType.setRoomTypeCode(roomCode);
//			roomTypeType.setNumberOfUnits(1);
//			roomTypes.getRoomType().add(roomTypeType);
//			roomStay.setRoomTypes(roomTypes);
//			
//			RoomStayType.RatePlans ratePlans = new RoomStayType.RatePlans();
//			RatePlanType ratePlanType = new RatePlanType();
//			ratePlanType.setBookingCode(bookingCode);
//			ratePlanType.setRatePlanCode(planCode);
//			ratePlanType.setRatePlanType(type);
//			ratePlans.getRatePlan().add(ratePlanType);		
//			roomStay.setRatePlans(ratePlans);
//			
//			GuestCountType guestCountType = new GuestCountType();
//			GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
//			guestCount.setCount(reservation.getAdult());
//			guestCountType.getGuestCount().add(guestCount);
//			roomStay.setGuestCounts(guestCountType);
//			
//			DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
//			dateTimeSpanType.setStart(DF.format(reservation.getFromdate()));
//			dateTimeSpanType.setEnd(DF.format(reservation.getTodate()));
//			roomStay.setTimeSpan(dateTimeSpanType);
//			
//			BasicPropertyInfoType basicPropertyInfoType = new BasicPropertyInfoType();
//			basicPropertyInfoType.setChainCode(chainCode);
//			basicPropertyInfoType.setHotelCode(hotelCode);
//			roomStay.setBasicPropertyInfo(basicPropertyInfoType);
//			
//			CommentType commentType = new CommentType();
//			CommentType.Comment comment = new CommentType.Comment();
//			commentType.getComment().add(comment);
//			roomStay.setComments(commentType);
//			
//			roomStaysType.getRoomStay().add(roomStay);
//			
//			ResGuestsType.ResGuest resGuest = new ResGuestsType.ResGuest();
//			ProfilesType profilesType = new ProfilesType();
//			ProfilesType.ProfileInfo profileInfo = new ProfilesType.ProfileInfo();
//			ProfileType profileType = new ProfileType();
//			profileType.setProfileType("1");
//			CustomerType customerType = new CustomerType();
//			PersonNameType personNameType = new PersonNameType();
//			personNameType.getGivenName().add(customer.getFirstName());
//			personNameType.setSurname(customer.getFamilyName());
//			customerType.getPersonName().add(personNameType);
//			
//			CustomerType.Telephone telephone = new CustomerType.Telephone();
//			telephone.setPhoneNumber(customer.getMobilephone());
//			telephone.setPhoneTechType("1");
//			customerType.getTelephone().add(telephone);
//			
//			CustomerType.Email email = new CustomerType.Email();
//			email.setValue(customer.getEmailaddress());
//			customerType.getEmail().add(email);
//			
//			String customerAddress = PartyService.getSimpleAddress(customer);
//			
//			CustomerType.Address address = new CustomerType.Address();
//			address.getAddressLine().add(customerAddress);
//			address.setCityName(customer.getCity());
//			address.setPostalCode(customer.getPostalcode());
//			StateProvType stateProvType = new StateProvType();
//			stateProvType.setStateCode(customer.getState());
//			address.setStateProv(stateProvType);
//			CountryNameType countryNameType = new CountryNameType();
//			countryNameType.setValue(customer.getCountry());
//			address.setCountryName(countryNameType);
//			customerType.getAddress().add(address);
//			
//			profileType.setCustomer(customerType);
//			
//			profileInfo.setProfile(profileType);
//			profilesType.getProfileInfo().add(profileInfo);
//			resGuest.setProfiles(profilesType);
//			hotelRreservationType.getResGuests().getResGuest().add(resGuest);
//			hotelRreservationType.setRoomStays(roomStaysType);
//			hotelReservationsType.getHotelReservation().add(hotelRreservationType);
//			otaRequest.setHotelReservations(hotelReservationsType);
//			
//			String request = MarshallerHelper.toXML(otaRequest);
//			request = request.replaceFirst("xmlns:ns2", "xmlns");
//			
//			//System.out.println(request);
//			
//			LOG.debug("Request: " + request + "\n");
//			String response = port.otaRequest(request);
//			LOG.debug("Response: " + response + "\n");
//			
//			if(response.indexOf("Success")>0){
//			    reservation.setAltid(bookingCode);
//			    reservation.setAltpartyid(getAltpartyid());
//				reservation.setMessage(null);
//				LOG.debug("createReservation reservationAltId=" + bookingCode);
//			}
//			else if(response.indexOf("Errors")>0) {
//				throw new ServiceException(Error.reservation_api, "Reservation is not created");
//			}
//
//		}catch (Throwable x) {
//			reservation.setMessage(x.getMessage());
//			reservation.setState(Reservation.State.Cancelled.name());
//			LOG.error(x.getMessage());
//			//x.printStackTrace();
//		} 
//		sqlSession.getMapper(ReservationMapper.class).update(reservation);
//		sqlSession.commit();
//		MonitorService.monitor(message, timestamp);
//		
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		String message = "LeisureLink readPrice productAltId:" + productAltId;
		LOG.debug(message);
		ReservationPrice result = new ReservationPrice();
		Date timestamp = new Date();
		try {
			result = this.computePrice(sqlSession, reservation, productAltId, currency);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}

		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productaltid, String currency) throws Throwable {
		Date version = new Date();
		ReservationPrice result = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		
		String altid =  product.getAltid();
		String hotelCode = product.getAltSupplierId();
		String roomCode = altid.substring(altid.indexOf("-")+1, altid.length());
		String arrivalDate = DF.format(reservation.getFromdate());
		String departureDate = DF.format(reservation.getTodate());
		int numberOfGuests = reservation.getAdult() + + reservation.getChild();
				
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		
		try{
			/*otaRequest.append("<OTA_HotelAvailRQ EchoToken=\" \" Version=\"1.003\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
			otaRequest.append("<POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" />");
			otaRequest.append("<BookingChannel Type=\"OTA\"><CompanyName Code=\"99\" /></BookingChannel>");
			otaRequest.append("</Source></POS>");
			otaRequest.append("<AvailRequestSegments><AvailRequestSegment>");
			otaRequest.append("<StayDateRange Start=\""+arrivalDate+"\" End=\""+departureDate+"\" />");
			otaRequest.append("<RatePlanCandidates><RatePlanCandidate RatePlanCode=\"RAC\" RatePlanType=\"13\" /></RatePlanCandidates>");
			otaRequest.append("<RoomStayCandidates>");
			otaRequest.append("<RoomStayCandidate Quantity=\"1\" RoomTypeCode=\""+roomCode+"\"><GuestCounts><GuestCount Count=\""+numberOfGuests+"\" /></GuestCounts></RoomStayCandidate>");
			otaRequest.append("</RoomStayCandidates>");
			otaRequest.append("<HotelSearchCriteria><Criterion><HotelRef ChainCode=\"LI\" HotelCode=\""+hotelCode+"\" /></Criterion></HotelSearchCriteria>");
			otaRequest.append("</AvailRequestSegment></AvailRequestSegments>");
			otaRequest.append("</OTA_HotelAvailRQ>");*/
			
			OTAHotelAvailRQ otaRequest = new OTAHotelAvailRQ();
			otaRequest.setEchoToken(" ");
			BigDecimal versionDecimal = new BigDecimal(versionService);
			otaRequest.setVersion(versionDecimal);
	
			
			POSType posType = new POSType();
			SourceType sourceType = new SourceType();
			RequestorID requestorID = new RequestorID();
			requestorID.setID(account);
			requestorID.setType(type);
			requestorID.setMessagePassword(password);
			sourceType.setRequestorID(requestorID);
			posType.getSource().add(sourceType);		
			otaRequest.setPOS(posType);
			
			OTAHotelAvailRQ.AvailRequestSegments availRequestSegments = new OTAHotelAvailRQ.AvailRequestSegments();
			
			
			//AvailRequestSegmentsType availRequestSegmentsType = new AvailRequestSegmentsType();
			AvailRequestSegment availRequestSegment = new AvailRequestSegmentsType.AvailRequestSegment();
			
			DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
			dateTimeSpanType.setStart(arrivalDate);
			dateTimeSpanType.setEnd(departureDate);
			availRequestSegment.setStayDateRange(dateTimeSpanType);
			
			RatePlanCandidatesType ratePlanCandidatesType = new RatePlanCandidatesType();
			RatePlanCandidate ratePlanCandidate = new RatePlanCandidatesType.RatePlanCandidate();
			ratePlanCandidate.setRatePlanCode(planCode);
			ratePlanCandidate.setRatePlanType(type);
			ratePlanCandidatesType.getRatePlanCandidate().add(ratePlanCandidate);
			availRequestSegment.setRatePlanCandidates(ratePlanCandidatesType);
			
			AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates roomStayCandidates = new AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates();
			AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate roomStayCandidate = new AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate();
			roomStayCandidate.setRoomTypeCode(roomCode);
			roomStayCandidate.setQuantity(1);
			GuestCountType guestCountType = new GuestCountType();
			GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
			guestCount.setCount(numberOfGuests);
			guestCountType.getGuestCount().add(guestCount);
			roomStayCandidate.setGuestCounts(guestCountType);
			roomStayCandidates.getRoomStayCandidate().add(roomStayCandidate);
			availRequestSegment.setRoomStayCandidates(roomStayCandidates);
			
			HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
			HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();
			ItemSearchCriterionType.HotelRef hotelRef = new ItemSearchCriterionType.HotelRef();
			hotelRef.setHotelCode(hotelCode);
			hotelRef.setChainCode(chainCode);
			criterion.getHotelRef().add(hotelRef);
			hotelSearchCriteriaType.getCriterion().add(criterion);
			availRequestSegment.setHotelSearchCriteria(hotelSearchCriteriaType);
			
			
			availRequestSegments.getAvailRequestSegment().add(availRequestSegment);
			
			otaRequest.setAvailRequestSegments(availRequestSegments);
			
			String request = MarshallerHelper.toXML(otaRequest);
			request = request.replaceFirst("xmlns:ns2", "xmlns");
			
			
//			System.out.println(request);
			LOG.debug("Request: " + request + "\n");
			//String response = port.otaRequest(otaRequest.toString());
			String response = port.otaRequest(request);
			//System.out.println(response);
			LOG.debug("Response: " + response + "\n");
			

			if(response.indexOf("Errors")>0){
				throw new ServiceException(Error.reservation_api, "Property not available");
			}
			
			response = response.substring(response.indexOf("RoomStays")-1, response.indexOf("</RoomStays>")+12);
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(response));
			Element rootNode = document.getRootElement();
			List<Element> roomRate = rootNode.getChild("RoomStay").getChild("RoomRates").getChildren("RoomRate");
			String bookingCode = roomRate.get(0).getAttributeValue("BookingCode");
			reservation.setAltid(bookingCode);
			List<Element> rates = roomRate.get(0).getChild("Rates").getChildren("Rate");
			
			Element priceDetails = rates.get(rates.size()-1);

			Element totalDetails = priceDetails.getChild("Total");
			String currencyFromResponse = totalDetails.getAttributeValue("CurrencyCode"); 
			//System.out.println(currencyFromResponse);
			Double currencyRate = 1.0;
			if(!currency.equalsIgnoreCase(currencyFromResponse)){
				currencyRate = WebService.getRate(sqlSession, currencyFromResponse, currency, new Date());
			}
			
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			reservation.setCurrency(currency);
			
			//property Price also total price for now
			Double amountBeforeTax =  PaymentHelper.getAmountWithTwoDecimals(Double.parseDouble(totalDetails.getAttributeValue("AmountBeforeTax")) * currencyRate);
			Double amountAfterTax =  PaymentHelper.getAmountWithTwoDecimals(Double.parseDouble(totalDetails.getAttributeValue("AmountAfterTax")) * currencyRate);		
			
			//we do not adding quote detail for rate price
//			QuoteDetail propertyPriceQd = new QuoteDetail();
//			propertyPriceQd.setAmount(amountBeforeTax+"");
//			propertyPriceQd.setDescription("Property Price ");
//			propertyPriceQd.setIncluded(true);
//			quoteDetails.add(propertyPriceQd);
			
			net.cbtltd.shared.Price propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice.setEntitytype(NameId.Type.Reservation.name());
			propertyPrice.setEntityid(reservation.getId());
			propertyPrice.setType(net.cbtltd.shared.Price.RATE);
			propertyPrice.setName(net.cbtltd.shared.Price.RATE);
			propertyPrice.setState(net.cbtltd.shared.Price.CREATED);
			propertyPrice.setDate(version);
			propertyPrice.setQuantity(1.0);
			propertyPrice.setUnit(Unit.EA);
			propertyPrice.setValue(amountBeforeTax);
			propertyPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(propertyPrice);
			
			
			
			//fees
			if(priceDetails.getChild("Fees") != null && priceDetails.getChild("Fees").getChildren("Fee") != null){
				List<Element> fees = priceDetails.getChild("Fees").getChildren("Fee");
				for(Element fee : fees){
					Double feeAmount = Double.parseDouble(fee.getAttributeValue("Amount"));
					feeAmount =  PaymentHelper.getAmountWithTwoDecimals(feeAmount * currencyRate);
					String feeDescription =  fee.getChild("Description").getChildText("Text");
	
					QuoteDetail chargeFeeQd = new QuoteDetail(String.valueOf(feeAmount), currency, feeDescription, "Included in the price", "", true);
					quoteDetails.add(chargeFeeQd);
				}
			}
			
			//taxes
			if(totalDetails.getChild("Taxes") != null && totalDetails.getChild("Taxes").getChildren("Tax") != null){
				List<Element> taxes = totalDetails.getChild("Taxes").getChildren("Tax");
				for(Element tax:taxes){
					Double taxPercent = Double.parseDouble(tax.getAttributeValue("Percent"));
					Double taxAmount = PaymentHelper.getAmountWithTwoDecimals(amountBeforeTax * taxPercent / 100.0);
					String taxDescription =  tax.getChild("TaxDescription").getChildText("Text");
					
					QuoteDetail chargeTaxQd = new QuoteDetail(String.valueOf(taxAmount), currency, taxDescription, "Included in the price", "", true);
					quoteDetails.add(chargeTaxQd);
				}
			}

			result.setTotal(amountAfterTax);
			result.setPrice(amountAfterTax);
			result.setCurrency(reservation.getCurrency());
			result.setQuoteDetails(quoteDetails);
			
			reservation.setPrice(amountAfterTax);
			reservation.setQuote(amountAfterTax);
			reservation.setDeposit(0.0);
			reservation.setExtra(0.0);	
			
		}catch (Throwable x) {
//			x.printStackTrace();
			LOG.error(x.getMessage());
			throw new ServiceException(Error.reservation_api, x.getMessage());
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		Date timestamp = new Date();
		String message = "createReservationAndPayment LeisureLink, reservationID:" + reservation.getId();
		LOG.debug(message);
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		
		Map<String, String> result = new HashMap<String, String>();
		try{
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}
			
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}
			
			double oldQuote = reservation.getQuote();
			computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency());
			
			if(oldQuote != reservation.getQuote()) {
				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
			}
			
//			String uniqueReservationCode= "BPAL-002134";
			//this will be set in computePrice methig and it is necessary for creating booking 
			String bookingCode= reservation.getAltid();
			String productaltid =  product.getAltid();
			String hotelCode =  product.getAltSupplierId();
			String roomCode = productaltid.substring(productaltid.indexOf("-")+1, productaltid.length());
			
			/*otaRequest.append("<OTA_HotelResRQ EchoToken=\" \" Version=\"1.003\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
			otaRequest.append("<POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS>");
			otaRequest.append("<UniqueID Type=\"1\" ID=\""+bookingCode+"\" />");
			otaRequest.append("<HotelReservations><HotelReservation>");
			otaRequest.append("<RoomStays><RoomStay>");
			otaRequest.append("<RoomTypes><RoomType RoomTypeCode=\""+roomCode+"\" NumberOfUnits=\"1\" /></RoomTypes>");			
			otaRequest.append("<RatePlans><RatePlan BookingCode=\""+bookingCode+"\" RatePlanCode=\"RAC\" RatePlanType=\"13\" /></RatePlans>");
			otaRequest.append("<GuestCounts><GuestCount Count=\"1\" /></GuestCounts>");
			otaRequest.append("<TimeSpan Start=\""+DF.format(reservation.getFromdate())+"\" End=\""+DF.format(reservation.getTodate())+"\" />");
			otaRequest.append("<Guarantee GuaranteeType=\"GuaranteeRequired\"><GuaranteesAccepted><GuaranteeAccepted>");
			otaRequest.append("<PaymentCard CardCode=\""+creditCard.getType()+"\" CardNumber=\""+creditCard.getNumber()+"\" ExpireDate=\""+creditCard.getMonth()+creditCard.getYear()+"\">");
			otaRequest.append("<CardHolderName>"+creditCard.getFirstName()+" "+creditCard.getLastName()+"</CardHolderName>");
			otaRequest.append("</PaymentCard>");
			otaRequest.append("</GuaranteeAccepted></GuaranteesAccepted></Guarantee>");
			otaRequest.append("<BasicPropertyInfo ChainCode=\"LI\" HotelCode=\""+hotelCode+"\" />");
			otaRequest.append("<Comments><Comment><Text> </Text></Comment></Comments>");
			otaRequest.append("</RoomStay></RoomStays>");
			otaRequest.append("<ResGuests><ResGuest>");
			otaRequest.append("<Profiles><ProfileInfo><Profile ProfileType=\"1\">");
			otaRequest.append("<Customer>");
			otaRequest.append("<PersonName><GivenName>"+customer.getFirstName()+"</GivenName><Surname>"+customer.getFamilyName()+"</Surname></PersonName>");
			otaRequest.append("<Telephone PhoneNumber=\""+customer.getMobilephone()+"\" PhoneTechType=\"1\" />");
			otaRequest.append("<Email>"+customer.getEmailaddress()+"</Email>");
			otaRequest.append("<Address>");
			otaRequest.append("<AddressLine>"+customer.getAddress()+"</AddressLine>");
			otaRequest.append("<CityName>"+customer.getCity()+"</CityName>");
			otaRequest.append("<PostalCode>"+customer.getPostalcode()+"</PostalCode>");
			otaRequest.append("<StateProv>"+customer.getState()+"</StateProv>");
			otaRequest.append("<CountryName>"+customer.getCountry()+"</CountryName>");
			otaRequest.append("</Address>");
			otaRequest.append("</Customer>");
			otaRequest.append("</Profile></ProfileInfo></Profiles>");
			otaRequest.append("</ResGuest></ResGuests>");
			otaRequest.append("</HotelReservation></HotelReservations>");
			otaRequest.append("</OTA_HotelResRQ>");	*/
			
			OTAHotelResRQ otaRequest = new OTAHotelResRQ();
			otaRequest.setEchoToken(" ");
			BigDecimal versionDecimal = new BigDecimal(versionService);
			otaRequest.setVersion(versionDecimal);
			
			POSType posType = new POSType();
			SourceType sourceType = new SourceType();
			RequestorID requestorID = new RequestorID();
			requestorID.setID(account);
			requestorID.setType(type);
			requestorID.setMessagePassword(password);
			sourceType.setRequestorID(requestorID);
			posType.getSource().add(sourceType);		
			otaRequest.setPOS(posType);
			
			UniqueIDType uniqueIdType = new UniqueIDType(); 
			uniqueIdType.setType("1");
			uniqueIdType.setID(bookingCode);
			otaRequest.getUniqueID().add(uniqueIdType);
			
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			HotelReservationType hotelRreservationType = new HotelReservationType();
			RoomStaysType roomStaysType = new RoomStaysType();
			RoomStaysType.RoomStay roomStay = new RoomStaysType.RoomStay();
			
			RoomTypes roomTypes = new RoomTypes();
			RoomTypeType roomTypeType = new RoomTypeType();
			roomTypeType.setRoomTypeCode(roomCode);
			roomTypeType.setNumberOfUnits(1);
			roomTypes.getRoomType().add(roomTypeType);
			roomStay.setRoomTypes(roomTypes);
			
			RoomStayType.RatePlans ratePlans = new RoomStayType.RatePlans();
			RatePlanType ratePlanType = new RatePlanType();
			ratePlanType.setBookingCode(bookingCode);
			ratePlanType.setRatePlanCode(planCode);
			ratePlanType.setRatePlanType(type);
			ratePlans.getRatePlan().add(ratePlanType);		
			roomStay.setRatePlans(ratePlans);
			
			GuestCountType guestCountType = new GuestCountType();
			GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
			guestCount.setCount(reservation.getAdult());
			guestCountType.getGuestCount().add(guestCount);
			roomStay.setGuestCounts(guestCountType);
			
			DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
			dateTimeSpanType.setStart(DF.format(reservation.getFromdate()));
			dateTimeSpanType.setEnd(DF.format(reservation.getTodate()));
			roomStay.setTimeSpan(dateTimeSpanType);
			
			GuaranteeType guaranteeType = new GuaranteeType();
			guaranteeType.setGuaranteeType("GuaranteeRequired");
			GuaranteeType.GuaranteesAccepted guaranteesAccepted = new GuaranteeType.GuaranteesAccepted();
			GuaranteeType.GuaranteesAccepted.GuaranteeAccepted guaranteeAccepted = new GuaranteeType.GuaranteesAccepted.GuaranteeAccepted();
			PaymentCardType paymentCardType = new PaymentCardType();
			
			//card month need to be in MM format
			String cardMonth = creditCard.getMonth();
			if(cardMonth.length()==1){
				cardMonth = "0"+cardMonth;
			}
			//card year need to be in YY format
			String cardYear = creditCard.getYear();
			if(cardYear.length()==4){
				cardYear = cardYear.substring(2);
			}
			
			paymentCardType.setCardCode(this.getCreditCardCode(creditCard.getType()));
			paymentCardType.setCardNumber(creditCard.getNumber());
			paymentCardType.setExpireDate(cardMonth + cardYear);
			paymentCardType.setCardHolderName(creditCard.getFirstName()+" "+creditCard.getLastName());
			guaranteeAccepted.setPaymentCard(paymentCardType);
			guaranteesAccepted.getGuaranteeAccepted().add(guaranteeAccepted);
			guaranteeType.setGuaranteesAccepted(guaranteesAccepted);
			roomStay.getGuarantee().add(guaranteeType);
			
			BasicPropertyInfoType basicPropertyInfoType = new BasicPropertyInfoType();
			basicPropertyInfoType.setChainCode(chainCode);
			basicPropertyInfoType.setHotelCode(hotelCode);
			roomStay.setBasicPropertyInfo(basicPropertyInfoType);
			
			CommentType commentType = new CommentType();
			CommentType.Comment comment = new CommentType.Comment();
			commentType.getComment().add(comment);
			roomStay.setComments(commentType);
			
			roomStaysType.getRoomStay().add(roomStay);
			
			ResGuestsType.ResGuest resGuest = new ResGuestsType.ResGuest();
			ProfilesType profilesType = new ProfilesType();
			ProfilesType.ProfileInfo profileInfo = new ProfilesType.ProfileInfo();
			ProfileType profileType = new ProfileType();
			profileType.setProfileType("1");
			CustomerType customerType = new CustomerType();
			PersonNameType personNameType = new PersonNameType();
			personNameType.getGivenName().add(customer.getFirstName());
			personNameType.setSurname(customer.getFamilyName());
			customerType.getPersonName().add(personNameType);
			
			CustomerType.Telephone telephone = new CustomerType.Telephone();
			telephone.setPhoneNumber(customer.getDayphone());
			telephone.setPhoneTechType("1");
			customerType.getTelephone().add(telephone);
			
			CustomerType.Email email = new CustomerType.Email();
			email.setValue(customer.getEmailaddress());
			customerType.getEmail().add(email);
						
			CustomerType.Address address = new CustomerType.Address();
			address.getAddressLine().add(customer.getLocalAddress());
			address.setCityName(customer.getCity());
			address.setPostalCode(customer.getPostalcode());
			StateProvType stateProvType = new StateProvType();
			if(!checkIfValueNullOrEmpty(customer.getRegion())){
				stateProvType.setValue(customer.getRegion());
			}else{
				stateProvType.setValue("NONE");
			}
			address.setStateProv(stateProvType);
			
			CountryNameType countryNameType = new CountryNameType();
			countryNameType.setValue(customer.getCountry());
			address.setCountryName(countryNameType);
			customerType.getAddress().add(address);
			
			profileType.setCustomer(customerType);
			
			profileInfo.setProfile(profileType);
			profilesType.getProfileInfo().add(profileInfo);
			resGuest.setProfiles(profilesType);
			hotelRreservationType.setResGuests(new ResGuestsType());
			hotelRreservationType.getResGuests().getResGuest().add(resGuest);
			hotelRreservationType.setRoomStays(roomStaysType);
			hotelReservationsType.getHotelReservation().add(hotelRreservationType);
			otaRequest.setHotelReservations(hotelReservationsType);
			
			String request = MarshallerHelper.toXML(otaRequest);
			request = request.replaceFirst("xmlns:ns2", "xmlns");
						
			LOG.debug("Request: " + request + "\n");
			//String response = port.otaRequest(otaRequest.toString());
			String response = port.otaRequest(request);
			LOG.debug("Response: " + response + "\n");

			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new StringReader(response));
			Element rootNode = document.getRootElement();
			
			if(response.indexOf("Success")>0){
				//get confirmationID
				Element hotelResID = rootNode.getChild("HotelReservations",LEISURELINK_API_NAMESPACE).getChild("HotelReservation",LEISURELINK_API_NAMESPACE)
						.getChild("ResGlobalInfo",LEISURELINK_API_NAMESPACE).getChild("HotelReservationIDs",LEISURELINK_API_NAMESPACE).getChild("HotelReservationID",LEISURELINK_API_NAMESPACE);
				
				String reservationID = hotelResID.getAttributeValue("ResID_Value");
				
			    reservation.setAltid(reservationID);
			    reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
				LOG.debug("createReservation confirmationID=" + reservationID);
			}
			else {
				String errorMessage = "Error. Reservation is not created.";
				if(rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE)!=null &&
					rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE).getChildren("Error",LEISURELINK_API_NAMESPACE) !=null ){
					
					List<Element> errorsList = rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE).getChildren("Error",LEISURELINK_API_NAMESPACE);
					for (Element errorCurrent : errorsList){
						errorMessage += " " + errorCurrent.getAttributeValue("ShortText") + ";";
					}
				}
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				result.put(GatewayHandler.ERROR_MSG, errorMessage);
				return result;
//				throw new ServiceException(Error.reservation_api, "Reservation is not created");
			}
			
			
		}catch (ServiceException e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			throw new ServiceException(e.getError(), e.getMessage());	
		}catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
		return result;
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "LeisureLink inquireReservation()");
	}

	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "LeisureLink confirmReservation(), reservationId="+reservation.getId());		
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "LeisureLink readReservation(), reservationId="+reservation.getId());		
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation LeisureLink " + reservation.getId();
		LOG.debug(message);
		StringBuilder otaRequest = new StringBuilder();
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		try{
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, " reservation " + reservation.getName());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}

			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}
			
			String bookingCode= "BPAL-"+ reservation.getId();
			String productaltid =  reservation.getProduct().getAltid();
			String hotelCode = reservation.getProduct().getAltSupplierId();
			String roomCode = productaltid.substring(productaltid.indexOf("-")+1, productaltid.length());
			
			//TODO change this part with a new model of object
			
			String customerAddress = PartyService.getSimpleAddress(customer);
			
			otaRequest.append("<OTA_HotelResModifyRQ EchoToken=\" \" Version=\"1.003\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
			otaRequest.append("<POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS>");
			otaRequest.append("<UniqueID Type=\"14\" ID=\""+bookingCode+"\" />");
			otaRequest.append("<HotelResModifies><HotelResModify>");
			otaRequest.append("<RoomStays><RoomStay>");
			otaRequest.append("<RoomTypes><RoomType RoomTypeCode=\""+roomCode+"\" NumberOfUnits=\"1\" /></RoomTypes>");			
			otaRequest.append("<RatePlans><RatePlan BookingCode=\""+bookingCode+"\" RatePlanCode=\"RAC\" RatePlanType=\"13\" /></RatePlans>");
			otaRequest.append("<GuestCounts><GuestCount Count=\""+reservation.getAdult()+"\" /></GuestCounts>");
			otaRequest.append("<TimeSpan Start=\""+DF.format(reservation.getFromdate())+"\" End=\""+DF.format(reservation.getTodate())+"\" />");
			otaRequest.append("<Guarantee GuaranteeType=\"GuaranteeRequired\"><GuaranteesAccepted><GuaranteeAccepted>");
			otaRequest.append("<PaymentCard CardCode=\""+reservation.getCardType()+"\" CardNumber=\""+reservation.getCardnumber()+"\" ExpireDate=\""+reservation.getCardmonth()+reservation.getCardyear()+"\">");
			otaRequest.append("<CardHolderName>"+reservation.getCardholder()+"</CardHolderName>");
			otaRequest.append("</PaymentCard>");
			otaRequest.append("</GuaranteeAccepted></GuaranteesAccepted></Guarantee>");
			otaRequest.append("<BasicPropertyInfo ChainCode=\"LI\" HotelCode=\""+hotelCode+"\" />");
			otaRequest.append("<Comments><Comment><Text> </Text></Comment></Comments>");
			otaRequest.append("</RoomStay></RoomStays>");
			otaRequest.append("<ResGuests><ResGuest>");
			otaRequest.append("<Profiles><ProfileInfo><Profile ProfileType=\"1\">");
			otaRequest.append("<Customer>");
			otaRequest.append("<PersonName><GivenName>"+customer.getFirstName()+"</GivenName><Surname>"+customer.getFamilyName()+"</Surname></PersonName>");
			otaRequest.append("<Telephone PhoneNumber=\""+customer.getMobilephone()+"\" PhoneTechType=\"1\" />");
			otaRequest.append("<Email>"+customer.getEmailaddress()+"</Email>");
			otaRequest.append("<Address>");
			otaRequest.append("<AddressLine>"+customerAddress+"</AddressLine>");
			otaRequest.append("<CityName>"+customer.getCity()+"</CityName>");
			otaRequest.append("<PostalCode>"+customer.getPostalcode()+"</PostalCode>");
			otaRequest.append("<StateProv>"+customer.getState()+"</StateProv>");
			otaRequest.append("<CountryName>"+customer.getCountry()+"</CountryName>");
			otaRequest.append("</Address>");
			otaRequest.append("</Customer>");
			otaRequest.append("</Profile></ProfileInfo></Profiles>");
			otaRequest.append("</ResGuest></ResGuests>");
			otaRequest.append("</HotelResModify></HotelResModifies>");
			otaRequest.append("</OTA_HotelResModifyRQ>");	
			
			LOG.debug("Request: " + otaRequest + "\n");
			String response = port.otaRequest(otaRequest.toString());
			LOG.debug("Response: " + response + "\n");
			
			if(response.indexOf("Success")>0){
			    reservation.setAltid(bookingCode);
			    reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage("Updated Reservation.");
				LOG.debug("updateReservation reservationAltId=" + bookingCode);
			}
			else if(response.indexOf("Errors")>0)
				throw new ServiceException(Error.reservation_api, "Reservation is not updated");
			
			
		}catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			//reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
			//x.printStackTrace();
		} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "LeisureLink cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		//StringBuilder otaRequest = new StringBuilder();
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		try{
//			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}
			
			/*otaRequest.append("<OTA_CancelRQ EchoToken=\" \" Version=\"1.001\" CancelType=\"Commit\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
			otaRequest.append("<POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS>");
			otaRequest.append("<UniqueID Type=\"14\" ID=\""+bookingCode+"\" />");
			otaRequest.append("</OTA_CancelRQ>");*/
			
			OTACancelRQ otaRequest = new OTACancelRQ();
			
			otaRequest.setEchoToken(" ");
			BigDecimal versionDecimal = new BigDecimal(versionServiceCancel);
			otaRequest.setVersion(versionDecimal);
			otaRequest.setCancelType(TransactionActionType.COMMIT);
			
			POSType posType = new POSType();
			SourceType sourceType = new SourceType();
			RequestorID requestorID = new RequestorID();
			requestorID.setID(account);
			requestorID.setType(type);
			requestorID.setMessagePassword(password);
			sourceType.setRequestorID(requestorID);
			posType.getSource().add(sourceType);		
			otaRequest.setPOS(posType);
			
			OTACancelRQ.UniqueID uniqueId = new OTACancelRQ.UniqueID(); 
			uniqueId.setType("14");
			uniqueId.setID(reservation.getAltid());
			otaRequest.getUniqueID().add(uniqueId);
			
			String request = MarshallerHelper.toXML(otaRequest);
			request = request.replaceFirst("xmlns:ns2", "xmlns");
			
			LOG.debug("Request: " + request + "\n");
			//String response = port.otaRequest(otaRequest.toString());
			String response = port.otaRequest(request);
			LOG.debug("Response: " + response + "\n");
			
			if(response.indexOf("Success")>0){
				LOG.debug("cancelReservation SUCCESS, reservationAltId=" + reservation.getAltid());
			}
			else{
				SAXBuilder builder = new SAXBuilder();
				Document document = (Document) builder.build(new StringReader(response));
				Element rootNode = document.getRootElement();
				
				String errorMessage = "Error. Error in cancelling reservations.";
				if(rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE)!=null &&
					rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE).getChildren("Error",LEISURELINK_API_NAMESPACE) !=null ){
					
					List<Element> errorsList = rootNode.getChild("Errors",LEISURELINK_API_NAMESPACE).getChildren("Error",LEISURELINK_API_NAMESPACE);
					for (Element errorCurrent : errorsList){
						errorMessage += " " + errorCurrent.getAttributeValue("ShortText") + ";";
					}
				}
				throw new ServiceException(Error.reservation_api, errorMessage);
			}
			
		}catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
			//x.printStackTrace();
		} 
//		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.service_absent, "LeisureLink readAlerts()");		
	}

	@Override
	public void readPrices() {
		final SqlSession sqlSession = RazorServer.openSession();
		Date version = new Date();
		String altid = null; 
		String message = "Read Prices LeisureLink started ." ;
		LOG.debug(message);
		try {
			Calendar date = Calendar.getInstance();  
		    date.setTime(new Date());
		    date.add(Calendar.DATE, -1);
			Date fromDate = date.getTime();
			date.add(Calendar.YEAR,1);
			Date toDate = date.getTime();
			
			GregorianCalendar gregoryFrom = new GregorianCalendar();
			gregoryFrom.setTime(fromDate);
			XMLGregorianCalendar beginDate =  null;
			
			try {
				beginDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoryFrom);
			} catch (DatatypeConfigurationException e1) {
				e1.printStackTrace();
			}
			
			GregorianCalendar gregoryTo = new GregorianCalendar();
			gregoryTo.setTime(toDate);
			XMLGregorianCalendar endDate = null;
			try {
				endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoryTo);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			
			DistributionService ss = new DistributionService();
			DistributionServiceSoap port = ss.getDistributionServiceSoap();
			
			
			ArrayOfDistributionProperty properties = port.getDistributionPropertes(account, username, password);
			
			List<DistributionProperty> propertyList  = properties.getDistributionProperty();
			
			for(DistributionProperty property:propertyList){
				RateAvailabilityUpdateRQ rateAvailibilityUpdate = port.getRatesAndAvailabilty(account, username, password, property.getPropertyCode(), beginDate, endDate);
				//RateAvailabilityUpdateRQ rateAvailibilityUpdate = port.getRatesAndAvailabilty(account, username, password, "00883", beginDate, endDate);
				ArrayOfRateAvailability arrayRateAvailibility = rateAvailibilityUpdate.getRateAvailabilities();
				List<RateAvailability> listRateAvailibility = arrayRateAvailibility.getRateAvailability();
				
				System.out.println("Property:"+property.getPropertyCode()+"--"+property.getPropertyName());
				XMLGregorianCalendar BeginDateRate = null;
				XMLGregorianCalendar EndDateRate = null;
				
				
				for(RateAvailability rateAvailability : listRateAvailibility){
						ArrayOfRoomType arrayOfRoomType = rateAvailability.getRoomTypes();
						List<RoomType> listRoomType =  arrayOfRoomType.getRoomType();
						String roomTypeCode = null;
						String rateCurrency = null;
						int rateMinStay = 1;
						int rateMaxStay = 1;
	//					String productID = null;
						BigDecimal rateValue = null;
						for(RoomType roomType:listRoomType){
							if(roomTypeCode == null || roomTypeCode.equals(roomType.getId())){
								roomTypeCode = roomType.getId();
								//System.out.println(roomTypeCode);					
							}else if(!roomTypeCode.equals(roomType.getId())){
								if(BeginDateRate != null && EndDateRate != null){
									
									altid = property.getPropertyCode()+"-"+roomTypeCode;
									//altid = "00883-1820";
									Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
									if (product == null) {
										continue;
									}
	
								}
								roomTypeCode = roomType.getId();
								BeginDateRate = null;
								EndDateRate = null;
								rateValue = null;
								rateCurrency = null;
								rateMinStay = 1;
								rateMaxStay = 1;
								System.out.println(roomTypeCode);
							}
							
							altid = property.getPropertyCode()+"-"+roomType.getId();
							//altid = "00883-1820";
							Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
							if (product == null) {
								continue;
							}
							
							//System.out.println(roomType.getId());
							//System.out.println(roomType.getDate());
							ArrayOfRate arrayOfRate =  roomType.getRates();
							List<Rate> listRate = arrayOfRate.getRate();
							int i=0;
							for(Rate rate:listRate){
								
								if(i==0){
									if(rateValue == null){
										rateValue = rate.getPerDay();
										rateCurrency = rate.getCurrency();
										rateMinStay = rate.getMinStay();
										rateMaxStay = rate.getMaxStay();
									}
									if(BeginDateRate == null && rateValue.compareTo(rate.getPerDay())==0){
										BeginDateRate = roomType.getDate();
									}else if(BeginDateRate != null && rateValue.compareTo(rate.getPerDay())==0){
										EndDateRate = roomType.getDate();
									}else if(BeginDateRate != null && EndDateRate == null && rateValue.compareTo(rate.getPerDay())!=0){
										EndDateRate = BeginDateRate;
									}
									
									if(BeginDateRate != null && EndDateRate != null && rateValue.compareTo(rate.getPerDay())!=0){
										System.out.println("Property:"+altid+"Range:"+BeginDateRate.toString()+"--------"+EndDateRate.toString()+"----Rate:"+rateValue);									
										
										Price price = new Price();
										price.setAltid(altid);
										price.setPartyid(getAltpartyid());
										price.setName(net.cbtltd.shared.Price.RACK_RATE);
										price.setType(NameId.Type.Reservation.name());
										price.setEntitytype(NameId.Type.Product.name());
										price.setEntityid(product.getId());
										price.setCurrency(rateCurrency);
									
										price.setQuantity(1.0); // TODO CHECK IT!
										price.setUnit("DAY"); 
										price.setDate(toDate(BeginDateRate));
										price.setTodate(toDate(EndDateRate));
										price.setMinStay(rateMinStay);
										price.setMaxStay(rateMaxStay);									
										price.setAvailable(1); //TODO CHECK IT WITH MARKO!
	
										//LOG.debug("price " + price);
										Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
										if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
										else {price = exists;}
	
										price.setAltid(altid);
										price.setAltpartyid(getAltpartyid());
										price.setFactor(1.0);
										price.setOrganizationid(getAltpartyid());
										price.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
										price.setState(Price.CREATED);
										price.setValue(rateValue.doubleValue());
										price.setMinimum(rateMinStay*rateValue.doubleValue());
										price.setMinStay(rateMinStay);								
										price.setVersion(version);
										sqlSession.getMapper(PriceMapper.class).update(price);
										sqlSession.getMapper(PriceMapper.class).cancelversion(price);
										
										
										BeginDateRate = roomType.getDate();
										rateValue = rate.getPerDay();
										rateCurrency = rate.getCurrency();
										rateMinStay = rate.getMinStay();
										rateMaxStay = rate.getMaxStay();
										EndDateRate=null;
									}
								}
								i++;					
							}
							//System.out.println("End Rates!");			
						}
				}
				
				sqlSession.commit();
			
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, version);
		LOG.debug("readPrices() LeisureLink DONE");
		
	}

	@Override
	public void readProducts() {
		String message = "readProducts LeisureLink";
		LOG.debug(message);
		Date version = new Date();		
		try{
			//update/create products. 
			createProduct();
			LOG.debug("LeisureLink:  createProduct done.");
			MonitorService.monitor(message, version);
		}
		catch (Throwable x) {
			LOG.error(x.getStackTrace());
		}
        
        
	}
	
	@SuppressWarnings("unchecked")
	public void createProduct(){
		String altid = null; 
		Product product;
		Date timestamp = new Date();
		String message = "createProduct started LeisureLink";		
		
		final SqlSession sqlSession = RazorServer.openSession();
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		
		// otaRequest for OTA_HotelSearchRQ (gets list of properties)
		//String otaRequest = "<OTA_HotelSearchRQ EchoToken=\" \" Version=\"1.001\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><Criteria><Criterion><Address><CountryName Code=\"US\" /></Address></Criterion></Criteria></OTA_HotelSearchRQ>";
		
		OTAHotelSearchRQ otaRequest = new OTAHotelSearchRQ();
		
		otaRequest.setEchoToken(" ");
		BigDecimal versionDecimal = new BigDecimal(versionServiceRead);
		otaRequest.setVersion(versionDecimal);
		
		POSType posType = new POSType();
		SourceType sourceType = new SourceType();
		RequestorID requestorID = new RequestorID();
		requestorID.setID(account);
		requestorID.setType(type);
		requestorID.setMessagePassword(password);
		sourceType.setRequestorID(requestorID);
		posType.getSource().add(sourceType);		
		otaRequest.setPOS(posType);
		
		HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
		HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();
		ItemSearchCriterionType.Address addressReq = new ItemSearchCriterionType.Address();
		CountryNameType countryNameType = new CountryNameType();
		countryNameType.setCode("US");
		addressReq.setCountryName(countryNameType);
		criterion.setAddress(addressReq);
		hotelSearchCriteriaType.getCriterion().add(criterion);
		otaRequest.setCriteria(hotelSearchCriteriaType);
		
		String request = null;
		try {
			request = MarshallerHelper.toXML(otaRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		request = request.replaceFirst("xmlns:ns2", "xmlns");
		//System.out.println(request);
        //String propertyList = port.otaRequest(otaRequest);
		String propertyList = port.otaRequest(request);
        propertyList = propertyList.substring(propertyList.indexOf("Properties")-1, propertyList.indexOf("</OTA_HotelSearchRS>"));
		
		try {			
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
        	SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(propertyList));
			Element rootNode = document.getRootElement();
      	  	
			List<Element> list = rootNode.getChildren("Property");
      	  	String codes = "";
      	  	int codesInfo = 0;
	      	for (int i = 0; i < list.size(); i++) {
	      		 
				Element node = (Element) list.get(i);
				codesInfo++;

				// String hotelCode = node.getAttributeValue("HotelCode");
				if (i > 0){
					codes = codes + "," + node.getAttributeValue("HotelCode");
				} else{
					codes = node.getAttributeValue("HotelCode");
				}
	   		   
	   		  /* List<Element> address  = node.getChildren("Address");
	   		   String cityName = address.get(0).getChildText("CityName");
	   		   String countryCode = address.get(0).getChild("CountryName").getAttributeValue("Code");
	   		   String countryName = address.get(0).getChildText("CountryName");
	   		   
	   		   List<Element> raterange  = node.getChildren("RateRange");
	   		   int minRate = Integer.parseInt(raterange.get(0).getAttributeValue("MinRate"));
	   		   int maxRate = Integer.parseInt(raterange.get(0).getAttributeValue("MaxRate"));
	   		   String currencyCode = raterange.get(0).getAttributeValue("CurrencyCode"); */	
	   		   
	   		   if (codesInfo>1){ //TODO check is there possibility to call service OTA_HotelDescriptiveInfoRQ with more than 2 hotel codes
	   			   	
		   			//String otaRequestInfo = "<OTA_HotelDescriptiveInfoRQ EchoToken=\" \" Version=\"1.001\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><HotelDescriptiveInfos><HotelDescriptiveInfo ChainCode=\"LL\" BrandCode=\"LL\" HotelCode=\""+codes+"\"><HotelInfo SendData=\"true\" /><FacilityInfo SendGuestRooms=\"true\" /><Policies SendPolicies=\"true\" /><ContactInfo SendData=\"true\" /><MultimediaObjects SendData=\"true\" /></HotelDescriptiveInfo></HotelDescriptiveInfos></OTA_HotelDescriptiveInfoRQ>";
			      	//System.out.println(otaRequestInfo);
	   			   
	   			   	OTAHotelDescriptiveInfoRQ otaRequestInfo = new OTAHotelDescriptiveInfoRQ();
	   			   	
	   			   	otaRequestInfo.setEchoToken(" ");
	   			   	otaRequestInfo.setVersion(versionDecimal);
	   			   	
	   			   	otaRequestInfo.setPOS(posType);
	   			   	
	   			   	HotelDescriptiveInfos hotelDescriptiveInfos = new HotelDescriptiveInfos();
	   			   	HotelDescriptiveInfo hotelDescriptiveInfo = new HotelDescriptiveInfo();
	   			   	hotelDescriptiveInfo.setChainCode(chainCode);
	   			   	hotelDescriptiveInfo.setBrandCode(chainCode);
	   			   	hotelDescriptiveInfo.setHotelCode(codes);
	   			   	
	   			   	HotelInfo hotelInfoReq = new HotelInfo();
	   			   	hotelInfoReq.setSendData(true);
	   			   	hotelDescriptiveInfo.setHotelInfo(hotelInfoReq);
	   			   	
	   			   	FacilityInfo facilityInfo = new FacilityInfo();
	   			   	facilityInfo.setSendGuestRooms(true);
	   			   	hotelDescriptiveInfo.setFacilityInfo(facilityInfo);
	   			   	
	   			   	Policies policies = new Policies();
	   			   	policies.setSendPolicies(true);
	   			   	hotelDescriptiveInfo.setPolicies(policies);
	   			   	
	   			   	ContactInfo contactInfoReq = new ContactInfo();
	   			   	contactInfoReq.setSendData(true);
	   			   	hotelDescriptiveInfo.setContactInfo(contactInfoReq);
	   			   	
	   			   	MultimediaObjects  multimediaObjects = new MultimediaObjects();
	   			   	multimediaObjects.setSendData(true);
	   			   	hotelDescriptiveInfo.setMultimediaObjects(multimediaObjects);
	   			   	
	   			   	hotelDescriptiveInfos.getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
	   			   	otaRequestInfo.setHotelDescriptiveInfos(hotelDescriptiveInfos);
	   			   	
	   			   	String requestInfo = MarshallerHelper.toXML(otaRequestInfo);
	   			   	requestInfo = requestInfo.replaceFirst("EchoToken=\" \"", "EchoToken=\" \" xmlns=\"http://www.opentravel.org/OTA/2003/05\"");
	   			   	//System.out.println(requestInfo);
	   			   	
			   		//String propertyInfo = port.otaRequest(otaRequestInfo);
	   			   	String propertyInfo = port.otaRequest(requestInfo);
			   		propertyInfo = propertyInfo.substring(propertyInfo.indexOf("HotelDescriptiveContents")-1, propertyInfo.indexOf("</OTA_HotelDescriptiveInfoRS>"));
			   		   
			   		builder = new SAXBuilder();
			   		document = builder.build(new StringReader(propertyInfo));
			   		Element rootInfoNode = document.getRootElement();
			   		List<Element> listInfo = rootInfoNode.getChildren("HotelDescriptiveContent");
			   		for (int j = 0; j < listInfo.size(); j++) {
			   			Element nodeInfo = (Element) listInfo.get(j);
			   			//main info
				   		String hotelCode = nodeInfo.getAttributeValue("HotelCode");
				   		String hotelName = nodeInfo.getAttributeValue("HotelName");
				   		String currencyCode = nodeInfo.getAttributeValue("CurrencyCode");
				   		//end main info
				   		
				   		//hotel info
				   		List<Element> hotelInfo = nodeInfo.getChildren("HotelInfo");
				   		List<Element> position  = hotelInfo.get(0).getChildren("Position");
				   		Double latitude = Double.parseDouble(position.get(0).getAttributeValue("Latitude"));
				   		Double longitude = Double.parseDouble(position.get(0).getAttributeValue("Longitude"));
				   		
				   		List<Element> servicesDetails = hotelInfo.get(0).getChildren("Services");
						List<Element> services = servicesDetails.get(0).getChildren("Service");
						
				   		//end hotel info
				   		
				   		//contact info
				   		Location location = null;
				   		
				   		List<Element> contactInfos = nodeInfo.getChildren("ContactInfos");
				   		List<Element> contactInfo = contactInfos.get(0).getChildren("ContactInfo");
				   		List<Element> addresses = contactInfo.get(0).getChildren("Addresses");
				   		List<Element> mainaddress = addresses.get(0).getChildren("Address");
				   		String address = mainaddress.get(0).getChildText("AddressLine");
				   		String cityName = mainaddress.get(0).getChildText("CityName");
				   		String postalCode = mainaddress.get(0).getChildText("PostalCode");
				   		String stateCode = mainaddress.get(0).getChild("StateProv").getAttributeValue("StateCode");
//				   		String stateName = mainaddress.get(0).getChildText("StateProv"); 
				   		String countryCode = mainaddress.get(0).getChild("CountryName").getAttributeValue("Code");
//				   		String countryName = mainaddress.get(0).getChildText("CountryName");
				   		
				   		StringBuilder addressBuilder = new StringBuilder();
						
						if (address != null && !address.isEmpty()) {addressBuilder.append(address);}
						if (cityName != null && !cityName.isEmpty()) {addressBuilder.append("\n").append(cityName);}
						if (stateCode != null && !stateCode.isEmpty()) {addressBuilder.append("\n").append(stateCode);}
						if (countryCode != null && !countryCode.isEmpty()) {addressBuilder.append("\n").append(countryCode);}
				   		
				   		location = PartnerService.getLocation(sqlSession, 
				   				cityName,
								stateCode,
								countryCode, 
								latitude, 
								longitude,
								postalCode);
				   		//end contact info
				   		
				   		//property info
				   		List<Element> extensions = nodeInfo.getChildren("TPA_Extensions");
				   		List<Element> extension = extensions.get(0).getChildren("TPA_Extension");
				   		String propertyType = extension.get(0).getChild("PropertyInfo").getAttributeValue("Type");
				   		String cancelPolicy = extension.get(0).getChildText("CancelPolicy");
				   		
				   		//end property info
				   		
				   		//description
				   		StringBuilder sb = new StringBuilder();
						String language = Language.EN;
						String type;
						String hotelDescription;
						List<Element> descriptions  = hotelInfo.get(0).getChildren("Descriptions");
						hotelDescription = descriptions.get(0).getChildText("DescriptiveText");
						type = "Hotel Description:";
						
						//end description
						
						
						/*System.out.println("Hotel CODE : " + hotelCode + " Hotel name : " + hotelName);
				   		System.out.println("Hotel Description: " + hotelDescription);
				   		System.out.println("Longitude: " + longitude + " Latitude: "+ latitude);
				   		System.out.println("Address: "+ address + ","+cityName+","+postalCode+","+stateCode+","+countryCode);
				   		System.out.println("Type: "+ propertyType);*/
						
						//facility info
						List<Element> facilities = nodeInfo.getChildren("FacilityInfo");
						List<Element> roomsdetails = facilities.get(0).getChildren("GuestRooms");
						List<Element> rooms = roomsdetails.get(0).getChildren("GuestRoom");
						for (Element room : rooms) {
							String roomID = room.getAttributeValue("ID");
							String maxPersons =  room.getAttributeValue("MaxOccupancy");
							String typeRoom = room.getChild("TypeRoom").getAttributeValue("Name");
							List<Element> roomExtensions =  room.getChildren("TPA_Extensions");
							List<Element> roomExtension = roomExtensions.get(0).getChildren("TPA_Extension");
//							int roomBeds = Integer.parseInt(roomExtension.get(0).getChild("Room").getAttributeValue("Beds"));
							int roomBaths = Integer.parseInt(roomExtension.get(0).getChild("Room").getAttributeValue("Baths"));
							double roomServiceFees = Double.parseDouble(roomExtension.get(0).getChild("Room").getAttributeValue("ServiceFee"));
							List<Element> roomDetails = roomExtension.get(0).getChildren("Room");
							String roomDescription = roomDetails.get(0).getChildText("RoomDesc");
							
							String name = hotelName + " " +typeRoom;
							
							//amenities
							ArrayList<String> attributes = new ArrayList<String>();
							List<Element> amenitiesDetails = room.getChildren("Amenities");
							List<Element> amenities = amenitiesDetails.get(0).getChildren("Amenity");
							for(Element amenity : amenities){
								//System.out.println("Amenity:"+amenity.getAttributeValue("CodeDetail"));
								addAttribute(attributes, amenity.getAttributeValue("CodeDetail"));
							}
							
							for(Element service : services){
								//System.out.println("Service:"+service.getAttributeValue("CodeDetail"));
								addAttribute(attributes, service.getAttributeValue("CodeDetail"));
							}
							
							addType(attributes,propertyType);
							
							//end amenities
							
							//System.out.println("RoomID: "+ roomID + " Max: "+maxPersons+" Type:"+typeRoom+" Beds:"+roomBeds+" Baths:"+roomBaths+" Fees:"+roomServiceFees);
							//System.out.println("Room Description: "+ roomDescription);
							
							altid = hotelCode+"-"+roomID;
							
							System.out.println("Current product altId: "+ altid);
							
							product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
							if (product == null) {continue;}
							
							if (hotelDescription != null && !hotelDescription.isEmpty()) {
								sb.append(type).append(":").append(hotelDescription).append("\n");
							}
							if (roomDescription != null && !roomDescription.isEmpty()) {
								sb.append("Room description ").append(":").append(roomDescription).append("\n");
							}
							product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), NameId.trim(sb.toString(), 20000), language));
							TextService.update(sqlSession, product.getTexts());
							
							this.createOrUpdateProductModel(location, cityName, stateCode, postalCode, countryCode, maxPersons, 
									roomBaths, 0, 1, typeRoom, currencyCode, 0.0, cancelPolicy, roomServiceFees, "",
									addressBuilder, name,altid,hotelCode, product);
							
							sqlSession.getMapper(ProductMapper.class).update(product);
					
							RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
							RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
						}
						//end facility info

			   		}
			   		
			   		codes = "";
	   			   	codesInfo = 0;
	   		   }
	   		   
	   		}			
			
			sqlSession.commit();
		}catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} 		
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}
	
	private Product createOrUpdateProductModel(Location location, String place, String region, String zipCode, 
			String country, String maxpersons, Integer bathroom, Integer toilet, Integer rooms, String propertytype,
			String currency, Double deposit, String terms, Double cleaningfee,
			String webaddress, StringBuilder address, String name, String altid,
			String altsupplierid, Product product) {
		
		if(product == null) {
		product = new Product();
		}
		//product.setState(Product.CREATED);
		if(location!=null){
			product.setLocationid(location.getId());
			product.setLatitude(location.getLatitude());
			product.setLongitude(location.getLongitude());
		}else{
			product.setState(Product.SUSPENDED);
		}
		//product.setLocationid(location.getId());
		
		product.setAltitude(0.0);
		product.setPhysicaladdress(address.toString());
		
		product.setAltSupplierId(altsupplierid);
		product.setAltpartyid(getAltpartyid()); 
		product.setSupplierid(getAltpartyid());  
		product.setAltid(altid);
		
		product.setPerson(maxpersons == null || maxpersons.isEmpty() ? 1 : Integer.valueOf(maxpersons));
		product.setChild(0); //TODO IF WE DON'T HAVE THIS VALUE WHAT TO DO, SET DEFAULT VALUE?
		product.setInfant(0);
		product.setQuantity(1); //TODO CHECK!
		
		product.setRating(5);
		product.setCommission(getCommission());
		product.setDiscount(getDiscount());
		product.setRank(getRank());
		
		//product.setType(propertytype);
		product.setType(Product.Type.Accommodation.name());
		product.setCurrency(currency);
		product.setSecuritydeposit(deposit);
		product.setCleaningfee(cleaningfee);
		product.setUnit(Unit.DAY);
		product.setWebaddress(webaddress);
		product.setServicedays("0000000");
		product.setCode("");
		product.setToilet(toilet);
		product.setBathroom(bathroom);
		product.setRoom(rooms);
		product.setDynamicpricingenabled(false);
		
		product.setName(name.toString());
		
		return product;
	}
	
	
	/*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

	
	@Override
	public void readSchedule() {	
		final SqlSession sqlSession = RazorServer.openSession();
//		long startTime = System.currentTimeMillis();
		Date timestamp = new Date();
		String altid = null; 
		String message = "Read Schedule started LeisureLink." ;
		LOG.debug(message);
		
		try { 
			Calendar date = Calendar.getInstance();  
		    date.setTime(new Date());
		    date.add(Calendar.DATE, -1);
			Date fromDate = date.getTime();
			date.add(Calendar.YEAR,1);
			Date toDate = date.getTime();
			
			GregorianCalendar gregoryFrom = new GregorianCalendar();
			gregoryFrom.setTime(fromDate);
			XMLGregorianCalendar beginDate =  null;
			
			try {
				beginDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoryFrom);
			} catch (DatatypeConfigurationException e1) {
				e1.printStackTrace();
			}
			
			GregorianCalendar gregoryTo = new GregorianCalendar();
			gregoryTo.setTime(toDate);
			XMLGregorianCalendar endDate = null;
			try {
				endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoryTo);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			
			DistributionService ss = new DistributionService();
			DistributionServiceSoap port = ss.getDistributionServiceSoap();
			
			
			ArrayOfDistributionProperty properties = port.getDistributionPropertes(account, username, password);
			
			List<DistributionProperty> propertyList = properties.getDistributionProperty();

			for (DistributionProperty property : propertyList) {
				RateAvailabilityUpdateRQ rateAvailibilityUpdate = port.getRatesAndAvailabilty(account, username, password,
						property.getPropertyCode(), beginDate, endDate);
				// RateAvailabilityUpdateRQ rateAvailibilityUpdate =
				// port.getRatesAndAvailabilty(account, username, password,
				// "00567", beginDate, endDate);
				ArrayOfRateAvailability arrayRateAvailibility = rateAvailibilityUpdate.getRateAvailabilities();
				List<RateAvailability> listRateAvailibility = arrayRateAvailibility.getRateAvailability();

				System.out.println("Property:" + property.getPropertyCode() + "--" + property.getPropertyName());
				XMLGregorianCalendar BeginDateReservation = null;
				XMLGregorianCalendar EndDateReservation = null;

				for (RateAvailability rateAvailability : listRateAvailibility) {
					ArrayOfRoomType arrayOfRoomType = rateAvailability.getRoomTypes();
					List<RoomType> listRoomType = arrayOfRoomType.getRoomType();
					String roomTypeCode = null;
					// String productID = null;
					for (RoomType roomType : listRoomType) {
						if (roomTypeCode == null || roomTypeCode.equals(roomType.getId())) {
							roomTypeCode = roomType.getId();
							// System.out.println(roomTypeCode);
						} else if (!roomTypeCode.equals(roomType.getId())) {
							if (BeginDateReservation != null && EndDateReservation != null) {

								altid = property.getPropertyCode() + "-" + roomTypeCode;
								Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
								if (product == null) {
									continue;
								}

								System.out.println("Property:" + altid + "Range:" + BeginDateReservation.toString() + "--------"
										+ EndDateReservation.toString());
								PartnerService.createSchedule(sqlSession, product, toDate(BeginDateReservation), toDate(EndDateReservation),
										timestamp);
								PartnerService.cancelSchedule(sqlSession, product, timestamp);

								BeginDateReservation = null;
								EndDateReservation = null;
							}
							roomTypeCode = roomType.getId();
							BeginDateReservation = null;
							EndDateReservation = null;
							System.out.println(roomTypeCode);
						}

						altid = property.getPropertyCode() + "-" + roomType.getId();
						Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
						if (product == null) {
							continue;
						}

						// System.out.println(roomType.getId());
						// System.out.println(roomType.getDate());
						ArrayOfRate arrayOfRate = roomType.getRates();
						List<Rate> listRate = arrayOfRate.getRate();
						int i = 0;
						for (Rate rate : listRate) {
							if (i == 0) {
								if (rate.isClosedToArrival() && BeginDateReservation == null) {
									BeginDateReservation = roomType.getDate();
								} else if (rate.isClosedToArrival() && BeginDateReservation != null) {
									EndDateReservation = roomType.getDate();
								} else if (!rate.isClosedToArrival() && BeginDateReservation != null && EndDateReservation == null) {
									EndDateReservation = BeginDateReservation;
								}

								if (!rate.isClosedToArrival() && BeginDateReservation != null && EndDateReservation != null) {
									System.out.println("Property:" + altid + "Range:" + BeginDateReservation.toString() + "--------"
											+ EndDateReservation.toString());
									PartnerService.createSchedule(sqlSession, product, toDate(BeginDateReservation), toDate(EndDateReservation),
											timestamp);
									PartnerService.cancelSchedule(sqlSession, product, timestamp);

									BeginDateReservation = null;
									EndDateReservation = null;
								}
							}
							i++;
						}
						// System.out.println("End Rates!");

					}

				}

				sqlSession.commit();
			}
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);

	}
	

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Leisure Link readSpecials()");
	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Leisure Link readDescriptions()");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void readImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		long startTime = System.currentTimeMillis();
		String altid = null; 
		String message = "Create Images started LeisureLink." ;
		LOG.debug(message);
		Date version = new Date();
		
		RGWService ss = new RGWService();
		RGWServiceSoap port = ss.getRGWServiceSoap();
		
		// otaRequest for OTA_HotelSearchRQ (gets list of properties)
		//String otaRequest = "<OTA_HotelSearchRQ EchoToken=\" \" Version=\"1.001\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><Criteria><Criterion><Address><CountryName Code=\"US\" /></Address></Criterion></Criteria></OTA_HotelSearchRQ>";
		OTAHotelSearchRQ otaRequest = new OTAHotelSearchRQ();
		
		otaRequest.setEchoToken(" ");
		BigDecimal versionDecimal = new BigDecimal(versionServiceRead);
		otaRequest.setVersion(versionDecimal);
		
		POSType posType = new POSType();
		SourceType sourceType = new SourceType();
		RequestorID requestorID = new RequestorID();
		requestorID.setID(account);
		requestorID.setType(type);
		requestorID.setMessagePassword(password);
		sourceType.setRequestorID(requestorID);
		posType.getSource().add(sourceType);		
		otaRequest.setPOS(posType);
		
		HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
		HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();
		ItemSearchCriterionType.Address addressReq = new ItemSearchCriterionType.Address();
		CountryNameType countryNameType = new CountryNameType();
		countryNameType.setCode("US");
		addressReq.setCountryName(countryNameType);
		criterion.setAddress(addressReq);
		hotelSearchCriteriaType.getCriterion().add(criterion);
		otaRequest.setCriteria(hotelSearchCriteriaType);
		
		String request = null;
		try {
			request = MarshallerHelper.toXML(otaRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		request = request.replaceFirst("xmlns:ns2", "xmlns");
		
        //String propertyList = port.otaRequest(otaRequest);
		String propertyList = port.otaRequest(request);
        propertyList = propertyList.substring(propertyList.indexOf("Properties")-1, propertyList.indexOf("</OTA_HotelSearchRS>"));
		
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(propertyList));
			Element rootNode = document.getRootElement();
      	  	
      	  	List<Element> list = rootNode.getChildren("Property");
      	  	String codes = "";
      	  	int codesInfo = 0;
      	  	
      	  	for (int i = 0; i < list.size(); i++) {
	      		 
	   		   Element node = (Element) list.get(i);
	   		   codesInfo++;
	   		   
	   		   //String hotelCode = node.getAttributeValue("HotelCode");
	   		   if (i>0)
	   		   	codes = codes + "," + node.getAttributeValue("HotelCode");
	   		   else
	   			codes = node.getAttributeValue("HotelCode");
	   		   
	   		   //System.out.println(codes);
	   		   
	   		   if (codesInfo>1){ //TODO check is there possibility to call service OTA_HotelDescriptiveInfoRQ with more than 2 hotel codes
	   			   	
	   			   //String otaRequestInfo = "<OTA_HotelDescriptiveInfoRQ EchoToken=\" \" Version=\"1.001\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><HotelDescriptiveInfos><HotelDescriptiveInfo ChainCode=\"LL\" BrandCode=\"LL\" HotelCode=\""+codes+"\"><HotelInfo SendData=\"true\" /><FacilityInfo SendGuestRooms=\"true\" /><Policies SendPolicies=\"true\" /><ContactInfo SendData=\"true\" /><MultimediaObjects SendData=\"true\" /></HotelDescriptiveInfo></HotelDescriptiveInfos></OTA_HotelDescriptiveInfoRQ>";
			      	//System.out.println(otaRequestInfo);
	   			   
	   			   	OTAHotelDescriptiveInfoRQ otaRequestInfo = new OTAHotelDescriptiveInfoRQ();
	   			   	
	   			   	otaRequestInfo.setEchoToken(" ");
	   			   	otaRequestInfo.setVersion(versionDecimal);
	   			   	
	   			   	otaRequestInfo.setPOS(posType);
	   			   	
	   			   	HotelDescriptiveInfos hotelDescriptiveInfos = new HotelDescriptiveInfos();
	   			   	HotelDescriptiveInfo hotelDescriptiveInfo = new HotelDescriptiveInfo();
	   			   	hotelDescriptiveInfo.setChainCode(chainCode);
	   			   	hotelDescriptiveInfo.setBrandCode(chainCode);
	   			   	hotelDescriptiveInfo.setHotelCode(codes);
	   			   	
	   			   	HotelInfo hotelInfoReq = new HotelInfo();
	   			   	hotelInfoReq.setSendData(true);
	   			   	hotelDescriptiveInfo.setHotelInfo(hotelInfoReq);
	   			   	
	   			   	FacilityInfo facilityInfo = new FacilityInfo();
	   			   	facilityInfo.setSendGuestRooms(true);
	   			   	hotelDescriptiveInfo.setFacilityInfo(facilityInfo);
	   			   	
	   			   	Policies policies = new Policies();
	   			   	policies.setSendPolicies(true);
	   			   	hotelDescriptiveInfo.setPolicies(policies);
	   			   	
	   			   	ContactInfo contactInfoReq = new ContactInfo();
	   			   	contactInfoReq.setSendData(true);
	   			   	hotelDescriptiveInfo.setContactInfo(contactInfoReq);
	   			   	
	   			   	MultimediaObjects  multimediaObjects = new MultimediaObjects();
	   			   	multimediaObjects.setSendData(true);
	   			   	hotelDescriptiveInfo.setMultimediaObjects(multimediaObjects);
	   			   	
	   			   	hotelDescriptiveInfos.getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
	   			   	otaRequestInfo.setHotelDescriptiveInfos(hotelDescriptiveInfos);
	   			   	
	   			   	String requestInfo = MarshallerHelper.toXML(otaRequestInfo);
	   			   	requestInfo = requestInfo.replaceFirst("EchoToken=\" \"", "EchoToken=\" \" xmlns=\"http://www.opentravel.org/OTA/2003/05\"");
	   			   	
			   		//String propertyInfo = port.otaRequest(otaRequestInfo);
	   			   	String propertyInfo = port.otaRequest(requestInfo);
			   		propertyInfo = propertyInfo.substring(propertyInfo.indexOf("HotelDescriptiveContents")-1, propertyInfo.indexOf("</OTA_HotelDescriptiveInfoRS>"));

			   		builder = new SAXBuilder();
			   		document = builder.build(new StringReader(propertyInfo));
			   		Element rootInfoNode = document.getRootElement();
			   		List<Element> listInfo = rootInfoNode.getChildren("HotelDescriptiveContent");
			   		for (int j = 0; j < listInfo.size(); j++) {
			   			Element nodeInfo = (Element) listInfo.get(j);
			   			String hotelCode = nodeInfo.getAttributeValue("HotelCode");
			   			List<Element> multimediaDescriptions = nodeInfo.getChildren("MultimediaDescriptions");
				   		List<Element> multimediaDescription = multimediaDescriptions.get(0).getChildren("MultimediaDescription");
				   		List<Element> imagesHotel = multimediaDescription.get(0).getChildren("ImageItem");
				   		
				   		List<Element> facilities = nodeInfo.getChildren("FacilityInfo");
						List<Element> roomsdetails = facilities.get(0).getChildren("GuestRooms");
						List<Element> rooms = roomsdetails.get(0).getChildren("GuestRoom");
						
						for (Element room : rooms) {				
							String roomID = room.getAttributeValue("ID");
							altid = hotelCode+"-"+roomID;
							Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
							if (product == null) {
								continue;
							}
							
							ArrayList<NameId> images = new ArrayList<NameId>();
							
							for (Element image : imagesHotel) {
					   			String imageName = image.getChild("ImageFormat").getAttributeValue("FileName");
					   			List<Element> URLs = image.getChildren("ImageFormat");
					   			String imageURL = URLs.get(0).getChildText("URL");
					   			//System.out.println(imageName);
					   			//System.out.println(imageURL);
					   			images.add(new NameId(imageName, imageURL));
					   		}
														
							List<Element> roomMultimediaDescriptions =  room.getChildren("MultimediaDescriptions");
							List<Element> roomMultimediaDescription = roomMultimediaDescriptions.get(0).getChildren("MultimediaDescription");
							List<Element> imagesRoom = roomMultimediaDescription.get(0).getChildren("ImageItem");
					   		for (Element image : imagesRoom) {
					   			String imageName = image.getChild("ImageFormat").getAttributeValue("FileName");
					   			List<Element> URLs = image.getChildren("ImageFormat");
					   			String imageURL = URLs.get(0).getChildText("URL");
					   			//System.out.println(imageName);
					   			//System.out.println(imageURL);
					   			images.add(new NameId(imageName, imageURL));
					   		}
					   		
					   		LOG.debug("images " + images);
							UploadFileService.uploadImages(sqlSession,NameId.Type.Product, product.getId(), Language.EN,images);
							
							sqlSession.commit();
						}
			   		}
			   		
			   		codes = "";
	   			   	codesInfo = 0;
		   		}	
	   		   
      	  	}
			
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			//x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for createImage execution: " + (endTime - startTime)/1000 + " seconds.");
		
	}

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Leisure Link readAdditionCosts()");
	}
	
	private static HashMap<String, String> TYPES = null;
	/**
	 * Adds the type.
	 *
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (type == null || type.isEmpty()) {return;}
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("Guesthouse","PCT16"); //Guesthouse
			TYPES.put("Condo","PCT8"); //Condominium
			TYPES.put("Hotel","PCT20");
			TYPES.put("Hostel","PCT19");
			TYPES.put("Apartment","PCT3"); //Appartement
			TYPES.put("Villas","PCT35");
			TYPES.put("Townhouse","PCT16");
			TYPES.put("Inn","PCT21");//Inn
			TYPES.put("Cabin","PCT5");//Cabin or Bungalow
			TYPES.put("Motel","PCT27");//Motel
			TYPES.put("Lodge","PCT22");//Lodge
			TYPES.put("Bed & Breakfast","PCT4");//Bed & Breakfast
		}
		
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
	}
	
	private static HashMap<String,String> PROPERTY_ATTRIBUTES = null;
	/**
	 * PROPERTY_ATTRIBUTES map.
	 *
	 * @param PROPERTY_ATTRIBUTES the PROPERTY_ATTRIBUTES
	 * @param attribute the attribute
	 */
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {
		if (PROPERTY_ATTRIBUTES == null) {
			PROPERTY_ATTRIBUTES = new HashMap<String, String>();
			
			PROPERTY_ATTRIBUTES.put("Room Amenities", "RMA0");
			PROPERTY_ATTRIBUTES.put("Air conditioning", "RMA2");
			PROPERTY_ATTRIBUTES.put("Alarm clock", "RMA3");
			PROPERTY_ATTRIBUTES.put("AM/FM radio", "RMA5");
			PROPERTY_ATTRIBUTES.put("Balcony/Terrace", "RMA7");
			PROPERTY_ATTRIBUTES.put("Bathrobe", "RMA10");
			PROPERTY_ATTRIBUTES.put("Bathtub", "RMA13");
			PROPERTY_ATTRIBUTES.put("Bidet", "RMA16");
			PROPERTY_ATTRIBUTES.put("Coffee/Tea maker", "RMA19");
			PROPERTY_ATTRIBUTES.put("Computer", "RMA21");
			PROPERTY_ATTRIBUTES.put("Plug adaptors", "RMA23");
			PROPERTY_ATTRIBUTES.put("Crib/Cot", "RMA26");
			PROPERTY_ATTRIBUTES.put("Desk", "RMA28");	
			PROPERTY_ATTRIBUTES.put("Washing Machine", "RMA31");	
			PROPERTY_ATTRIBUTES.put("Dishwasher", "RMA32");	
			PROPERTY_ATTRIBUTES.put("Double bed", "RMA33");	
			PROPERTY_ATTRIBUTES.put("Fireplace", "RMA41");	
			PROPERTY_ATTRIBUTES.put("Free movies/video", "RMA46");	
			PROPERTY_ATTRIBUTES.put("Hairdryer", "RMA50");	
			PROPERTY_ATTRIBUTES.put("Iron", "RMA55");	
			PROPERTY_ATTRIBUTES.put("Ironing board", "RMA56");
			PROPERTY_ATTRIBUTES.put("Jacuzzi", "RMA57");
			PROPERTY_ATTRIBUTES.put("King bed", "RMA58");
			PROPERTY_ATTRIBUTES.put("Kitchen", "RMA59");
			PROPERTY_ATTRIBUTES.put("Kitchen supplies", "RMA60");
			PROPERTY_ATTRIBUTES.put("Kitchenette", "RMA61");
			PROPERTY_ATTRIBUTES.put("Microwave", "RMA68");
			PROPERTY_ATTRIBUTES.put("Minibar", "RMA69");
			PROPERTY_ATTRIBUTES.put("Newspaper", "RMA73");
			PROPERTY_ATTRIBUTES.put("Non-smoking", "RMA74");
			PROPERTY_ATTRIBUTES.put("Oven", "RMA77");
			PROPERTY_ATTRIBUTES.put("Plates and bowls", "RMA81");
			PROPERTY_ATTRIBUTES.put("Pots and pans", "RMA82");
			PROPERTY_ATTRIBUTES.put("Private bathroom", "RMA85");
			PROPERTY_ATTRIBUTES.put("Queen bed", "RMA86");
			PROPERTY_ATTRIBUTES.put("Refrigerator", "RMA88");
			PROPERTY_ATTRIBUTES.put("Refrigerator w/icemaker", "RMA89");
			PROPERTY_ATTRIBUTES.put("TV", "RMA90");
			PROPERTY_ATTRIBUTES.put("Safe", "RMA92");
			PROPERTY_ATTRIBUTES.put("Separate closet", "RMA94");
			PROPERTY_ATTRIBUTES.put("Shower only", "RMA97");
			PROPERTY_ATTRIBUTES.put("Silverware/utensils", "RMA98");
			PROPERTY_ATTRIBUTES.put("Sitting area", "RMA99");
			PROPERTY_ATTRIBUTES.put("Smoke detectors", "RMA100");
			PROPERTY_ATTRIBUTES.put("Smoking", "RMA101");
			PROPERTY_ATTRIBUTES.put("Sofa Bed", "RMA102");
			PROPERTY_ATTRIBUTES.put("Stereo", "RMA104");
			PROPERTY_ATTRIBUTES.put("Stove", "RMA105");
			PROPERTY_ATTRIBUTES.put("Telephone", "RMA107");
			PROPERTY_ATTRIBUTES.put("Twin bed", "RMA113");
			PROPERTY_ATTRIBUTES.put("Water closet", "RMA120");
			PROPERTY_ATTRIBUTES.put("Water filter", "RMA121");
			PROPERTY_ATTRIBUTES.put("Wireless Internet", "RMA123");
			PROPERTY_ATTRIBUTES.put("Bathtub & jacuzzi", "RMA127");
			PROPERTY_ATTRIBUTES.put("CD  player", "RMA129");
			PROPERTY_ATTRIBUTES.put("Down/feather pillows", "RMA132");
			PROPERTY_ATTRIBUTES.put("Foam pillows", "RMA135");
			PROPERTY_ATTRIBUTES.put("Table Tennis", "RMA140");
			PROPERTY_ATTRIBUTES.put("Bathtub oversize", "RMA141");
			PROPERTY_ATTRIBUTES.put("Shower", "RMA142");
			PROPERTY_ATTRIBUTES.put("Soundproofed room", "RMA144");
			PROPERTY_ATTRIBUTES.put("Storage space", "RMA145");
			PROPERTY_ATTRIBUTES.put("Tables and chairs", "RMA146");
			PROPERTY_ATTRIBUTES.put("Walk-in closet", "RMA148");
			PROPERTY_ATTRIBUTES.put("Washer/dryer", "RMA149");
			PROPERTY_ATTRIBUTES.put("Weight scale", "RMA150");
			PROPERTY_ATTRIBUTES.put("Bathtub & shower", "RMA155");
			PROPERTY_ATTRIBUTES.put("Ceiling fan", "RMA157");
			PROPERTY_ATTRIBUTES.put("Plug adaptors", "RMA159");
			PROPERTY_ATTRIBUTES.put("DVD player", "RMA163");
			PROPERTY_ATTRIBUTES.put("Toaster", "RMA167");
			PROPERTY_ATTRIBUTES.put("Cups/glassware", "RMA183");
			PROPERTY_ATTRIBUTES.put("Shared bathroom", "RMA193");
			PROPERTY_ATTRIBUTES.put("Single bed", "RMA203");
			PROPERTY_ATTRIBUTES.put("Honeymoon suites", "RMA206");
			PROPERTY_ATTRIBUTES.put("Maid service", "RMA208");
			PROPERTY_ATTRIBUTES.put("Satellite TV", "RMA210");
			PROPERTY_ATTRIBUTES.put("iPod docking station", "RMA214");
			PROPERTY_ATTRIBUTES.put("Satellite radio", "RMA217");
			PROPERTY_ATTRIBUTES.put("Duvet", "RMA233");
			PROPERTY_ATTRIBUTES.put("Luxury linen type", "RMA234");
			PROPERTY_ATTRIBUTES.put("Pantry", "RMA236");
			PROPERTY_ATTRIBUTES.put("Dish-cleaning supplies", "RMA237");
			PROPERTY_ATTRIBUTES.put("Double vanity", "RMA238");
			PROPERTY_ATTRIBUTES.put("Makeup mirror", "RMA239");
			PROPERTY_ATTRIBUTES.put("Outdoor space", "RMA243");
			PROPERTY_ATTRIBUTES.put("Private pool", "RMA245");
			PROPERTY_ATTRIBUTES.put("HDTV", "RMA246");
			
			PROPERTY_ATTRIBUTES.put("Smoking rooms", "RMA101");
			PROPERTY_ATTRIBUTES.put("Nonsmoking rooms", "RMA74");
			PROPERTY_ATTRIBUTES.put("Rooms with internet access", "RMA54");
			PROPERTY_ATTRIBUTES.put("Wake-up calls", "HAC100");
			PROPERTY_ATTRIBUTES.put("Pets allowed", "SUI4");
			PROPERTY_ATTRIBUTES.put("Air conditioned guest rooms", "RMA2");
			PROPERTY_ATTRIBUTES.put("Mini-refrigerator", "RMA88");
			PROPERTY_ATTRIBUTES.put("Cable television", "RMA210");
			PROPERTY_ATTRIBUTES.put("Wireless internet connection", "RMA123");
			
			PROPERTY_ATTRIBUTES.put("Game room", "HAC44");
			PROPERTY_ATTRIBUTES.put("Restaurant", "ACC41");
			PROPERTY_ATTRIBUTES.put("Accessible facilities", "INF12");
			PROPERTY_ATTRIBUTES.put("24-hour front desk", "HAC1");
			PROPERTY_ATTRIBUTES.put("Multilingual staff", "HAC103");
			PROPERTY_ATTRIBUTES.put("Laundry/Valet service", "HAC58");
			PROPERTY_ATTRIBUTES.put("Concierge desk", "CSC1");
			PROPERTY_ATTRIBUTES.put("Safe deposit box", "HAC78");
			PROPERTY_ATTRIBUTES.put("Banquet facilities", "HAC105");
			PROPERTY_ATTRIBUTES.put("Jacuzzi", "HAC55");
			PROPERTY_ATTRIBUTES.put("Tennis court", "RST94");
			PROPERTY_ATTRIBUTES.put("Exercise gym", "HAC35");
			PROPERTY_ATTRIBUTES.put("Secretarial services", "BUS49");
			PROPERTY_ATTRIBUTES.put("Meeting/board rooms", "BUS46");
			PROPERTY_ATTRIBUTES.put("Whirlpool", "RST100");
			PROPERTY_ATTRIBUTES.put("Playground", "RST74");
			PROPERTY_ATTRIBUTES.put("Security", "SEC47");
			PROPERTY_ATTRIBUTES.put("Cocktail lounge", "HAC19");
			PROPERTY_ATTRIBUTES.put("Free parking", "HAC42");
			PROPERTY_ATTRIBUTES.put("Parking", "HAC63");
			PROPERTY_ATTRIBUTES.put("Business center", "BUS39");
			PROPERTY_ATTRIBUTES.put("Conference facilities", "MRF3");
			PROPERTY_ATTRIBUTES.put("Outdoor pool", "RST123");
			PROPERTY_ATTRIBUTES.put("Continental breakfast", "AMC8");
			PROPERTY_ATTRIBUTES.put("Child programs", "REC44");
			PROPERTY_ATTRIBUTES.put("Elevators", "HAC33");
			PROPERTY_ATTRIBUTES.put("Gift/News stand", "CSC17");
			PROPERTY_ATTRIBUTES.put("Full kitchen", "RMA59");
			PROPERTY_ATTRIBUTES.put("Golf", "RST27");
			PROPERTY_ATTRIBUTES.put("Sauna", "HAC79");
			PROPERTY_ATTRIBUTES.put("Indoor pool", "RST122");
			PROPERTY_ATTRIBUTES.put("Massage services", "HAC61");
			PROPERTY_ATTRIBUTES.put("BBQ/Picnic area", "FAC3");
			PROPERTY_ATTRIBUTES.put("Jogging track", "RST64");
			PROPERTY_ATTRIBUTES.put("VCR player", "CBF69");
			PROPERTY_ATTRIBUTES.put("Barbeque grills", "HAC118");
			PROPERTY_ATTRIBUTES.put("DVD/video rental", "BUS83");
			PROPERTY_ATTRIBUTES.put("Spa", "RST91");
			PROPERTY_ATTRIBUTES.put("Steam bath", "HAC86");
			PROPERTY_ATTRIBUTES.put("Satellite television", "RMA210");
			PROPERTY_ATTRIBUTES.put("Locker room", "REC9");
			PROPERTY_ATTRIBUTES.put("Meeting facilities", "CSC18");
			PROPERTY_ATTRIBUTES.put("Baby sitting", "HAC8");
			PROPERTY_ATTRIBUTES.put("Fax machine", "BUS9");
			PROPERTY_ATTRIBUTES.put("Racquetball", "RST77");
			PROPERTY_ATTRIBUTES.put("Shops and commercial services", "RST83");
			PROPERTY_ATTRIBUTES.put("Office supplies", "BUS26");
		}
		
		if (PROPERTY_ATTRIBUTES.get(attribute) != null) {
			attributes.add(PROPERTY_ATTRIBUTES.get(attribute));
		}else{
			//System.out.println("Missing attribute: " + attribute);
		}
	}
	
	
	private String getCreditCardCode(CreditCardType systemCCtype) {
		String ccTypeId = "";
		if(systemCCtype == CreditCardType.VISA){
			ccTypeId = "VI";
		}else if(systemCCtype == CreditCardType.MASTER_CARD){
			ccTypeId = "MC";
		}else if(systemCCtype == CreditCardType.AMERICAN_EXPRESS){
			ccTypeId = "AX";
		}else if(systemCCtype == CreditCardType.DISCOVER){
			ccTypeId = "DS";
		}else if(systemCCtype == CreditCardType.DINES_CLUB){
			ccTypeId = "DC";
		}
		return ccTypeId;
	}

	private boolean checkIfValueNullOrEmpty(String value){
		boolean result = false;
		if(value==null || value.isEmpty()){
			result = true;
		}
			
		return result;
	}
	
}
