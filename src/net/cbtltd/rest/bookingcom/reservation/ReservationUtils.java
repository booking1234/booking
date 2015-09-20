package net.cbtltd.rest.bookingcom.reservation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.bookingcom.reservation.domain.Request;
import net.cbtltd.rest.bookingcom.reservation.domain.Reservations;
import net.cbtltd.rest.bookingcom.reservation.domain.Reservations.Reservation.Room.Addons.Addon;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ChannelLastFetchMapper;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelLastFetch;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.ReservationExt;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.utils.BPThreadLocal;


/**
 * Utility for Reservation 
 * @author nibodha
 *
 */
public class ReservationUtils {

	/**
	 * default constructor for ReservationUtils
	 */
	public ReservationUtils() {
		super();
		addOnMap.put(1,"Breakfast");	
		addOnMap.put(2,"Continental breakfast");
		addOnMap.put(3,"American breakfast");
		addOnMap.put(4,"Buffet breakfast");
		addOnMap.put(5,"Full english breakfast");
		addOnMap.put(6,"Lunch");
		addOnMap.put(7,"Dinner");
		addOnMap.put(8,"Half board");
		addOnMap.put(9,"Full board");
		addOnMap.put(11,"Breakfast for Children");
		addOnMap.put(12,"Continental breakfast for Children");
		addOnMap.put(13,"American breakfast for Children");
		addOnMap.put(14,"Buffet breakfast for Children");
		addOnMap.put(15,"Full english breakfast for Children");
		addOnMap.put(16,"Lunch for Children");
		addOnMap.put(17,"Dinner for Children");
		addOnMap.put(18,"Half board for Children");
		addOnMap.put(19,"Full board for Children");
		addOnMap.put(20,"WiFi");
		addOnMap.put(21,"Internet");
		addOnMap.put(22,"Parking space");
		addOnMap.put(23,"Extrabed");
		addOnMap.put(24,"Babycot");

		
		
	}

	public static final String BOOKINGCOM_RESERVATION_URL = "bp.bookingcom.reservation.url";
	public static final String BOOKINGCOM_USERNAME = "bp.bookingcom.username";
	public static final String BOOKINGCOM_PWD = "bp.bookingcom.pwd";
	public static final String BP_RESERVATION_SERVICE_URL = "bp.reservation.service.url";
	private static final Logger LOG = Logger.getLogger(ReservationUtils.class.getName());
	
	private boolean processReservationWithPayment=false;
	private boolean processSpecificConfirmationId=false;
	private List<Integer> listConfirmationId=new ArrayList<Integer>();
	private String lastChange=null;
	
	private static final Map<Integer,String> addOnMap=new HashMap<Integer,String>();
	

	private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyy-MM-dd HH:mm:ss");

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyy-MM-dd");
	
	public static String RESERVATIONS_FILE = System.getProperty("user.home")
										+ File.separator + "booking" + File.separator + "reservations.log";
	
	public static String US_CURRENCY_CODE = "USD";
	public static String BOOKING_GUEST_ADDON_TYPE = "Guest";

	
	/**
	 * This menthod will be called based on the notification from booking.com
	 * @param hotelId
	 */
	public void fetchAndUpdateReservationBasedOnNotification(int hotelId, int reservationId) {
		LOG.info("Inside fetchAndUpdateReservationFromBookingCom ");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).read(RazorConfig.getBookingChannelPartnerId());
			// prepare the request
			Request request = this.createReservationRequest(sqlSession, hotelId,reservationId);
			// call the BookingCom reservation API
			
			String bookingcomResponse=this.callBookingComReservationAPI(request);
			
			 Reservations reservations =this.prepareReservationsFromXMLResponse(bookingcomResponse);
			// read the BookingCom reservation response and prepare BP
			// reservation domain object.
			List<Reservation> listReservation = this.readBookingComReservationResponse(reservations, channelPartner, sqlSession);
			// call webservice to persist the BP reservation domain objects
			BPThreadLocal.set(true);
			List<Reservation> listPersistedReservation = this.callBPReservationService(listReservation, sqlSession);

			LOG.info("The following reservations are persisted");
			for (Reservation reservation : listPersistedReservation) {
				LOG.info(reservation.getId());
			}
			//only when the reservation is persited sucessfully we get the reservation id.Else reservation id will be empty
			if(listPersistedReservation==null || listPersistedReservation.isEmpty()){
				net.cbtltd.server.EmailService.reservationNotPersisted(bookingcomResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Exception occured while calling booking com reservation api", e);
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}

	}
	
	
	/**
	 * To fetch Reservation information of hotelId from Booking.com and update it our DB
	 * @param hotelId
	 */
	public void fetchAndUpdateReservationFromBookingCom(int hotelId) {
		LOG.info("Inside fetchAndUpdateReservationFromBookingCom ");
		SqlSession sqlSession = RazorServer.openSession();
		BPThreadLocal.set(true);
		try {
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).read(RazorConfig.getBookingChannelPartnerId());
			// prepare the request
			Request request = this.createReservationRequest(sqlSession, hotelId);
			// call the BookingCom reservation API
			String bookingcomResponse=this.callBookingComReservationAPI(request);
			Reservations reservations = this.prepareReservationsFromXMLResponse(bookingcomResponse);
			if(reservations.getReservation() != null) {
				// read the BookingCom reservation response and prepare BP
				// reservation domain object.
				List<Reservation> listReservation = this.readBookingComReservationResponse(reservations, channelPartner, sqlSession);
				// call webservice to persist the BP reservation domain objects
				List<Reservation> listPersistedReservation = this.callBPReservationService(listReservation, sqlSession);
	
				// Only when no exception occured and when webserice call is
				// successful we need to updated the last
				// fetch time in BookingComLastFetch
				this.updateBookingComLastFetch(sqlSession);
				
				LOG.info("The following reservations are persisted");
				for (Reservation reservation : listPersistedReservation) {
					LOG.info(reservation.getId());
				}
				
				//only when the reservation is persited sucessfully we get the reservation id.Else reservation id will be empty
				if(listPersistedReservation==null || listPersistedReservation.isEmpty()){
					net.cbtltd.server.EmailService.reservationNotPersisted(bookingcomResponse);
				}
			} else {
				LOG.info("There is no reservation for property<" + hotelId + ">");
				// Only when no exception occured and when webserice call is
				// successful we need to updated the last
				// fetch time in BookingComLastFetch
				this.updateBookingComLastFetch(sqlSession);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Exception occured while calling booking com reservation api", e);
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
		if(BPThreadLocal.get() != null) {
			// remove the variable the end
			BPThreadLocal.unset();
		}
	}
	
	/**
	 * To fetch Reservation information of hotelId from Booking.com and update it our DB
	 * @param hotelId
	 */
	public void fetchReservationFromBookingCom(int hotelId, String currentTime) {
		LOG.info("Inside fetchReservationFromBookingCom ");
		SqlSession sqlSession = RazorServer.openSession();
		BPThreadLocal.set(true);
		try {
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).read(RazorConfig.getBookingChannelPartnerId());
			// prepare the request
			Request request = this.createReservationRequest(sqlSession, hotelId);
			// call the BookingCom reservation API
			String bookingcomResponse=this.callBookingComReservationAPI(request);
			File file = new File(ReservationUtils.RESERVATIONS_FILE + "." + hotelId + "." + currentTime);
			file.createNewFile();
			FileUtils.writeStringToFile(file, bookingcomResponse);
			LOG.error(bookingcomResponse);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Exception occured while calling booking com reservation api", e);
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}

	
	/**
	 * To fetch all Reservations information from Booking.com and update it our DB
	 */
	public void fetchAndUpdateAllReservationsFromBookingCom() {
		SqlSession sqlSession = RazorServer.openSession();
		BPThreadLocal.set(true);
		try {
			// get the channel partner entity for booking
			ChannelPartner channelPartner = sqlSession.getMapper(
					ChannelPartnerMapper.class).read(
					RazorConfig.getBookingChannelPartnerId());
			// prepare the request
			// retrieve all the properties for which we want to retireve
			// reservation
			Request request = this.createReservationRequest(sqlSession);
			// call the BookingCom reservation API
			String bookingcomResponse=this
			.callBookingComReservationAPI(request);
			Reservations reservations = this.prepareReservationsFromXMLResponse(bookingcomResponse);
			// read the BookingCom reservation response and prepare BP
			// reservation domain object.
			List<Reservation> listReservation = this
					.readBookingComReservationResponse(reservations,
							channelPartner, sqlSession);
			// call webservice to persist the BP reservation domain objects
			List<Reservation> listPersistedReservation = this.callBPReservationService(listReservation, sqlSession);
			
			// Only when no exception occured and when webserice call is
			// successful we need to updated the last
			// fetch time in BookingComLastFetch
			this.updateBookingComLastFetch(sqlSession);
			
			LOG.info("The following reservations are persisted");
			for (Reservation reservation : listPersistedReservation) {
				LOG.info(reservation.getId());
			}
			//only when the reservation is persited sucessfully we get the reservation id.Else reservation id will be empty
			if(listPersistedReservation==null || listPersistedReservation.isEmpty()){
				net.cbtltd.server.EmailService.reservationNotPersisted(bookingcomResponse);
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
		if(BPThreadLocal.get() != null) {
			// remove the variable the end
			BPThreadLocal.unset();
		}

	}
	
	/**
	 * To fetch all Reservations information from Booking.com and update it our DB fom a LocalFile system.
	 */
	public void fetchAndUpdateAllReservationsFromFile(int hotelId, String currentTime) {
		SqlSession sqlSession = RazorServer.openSession();
		BPThreadLocal.set(true);
		try {
			// get the channel partner entity for booking
						ChannelPartner channelPartner = sqlSession.getMapper(
								ChannelPartnerMapper.class).read(
								RazorConfig.getBookingChannelPartnerId());
			File file = new File(ReservationUtils.RESERVATIONS_FILE + "." + hotelId + "." + currentTime);
			String bookingcomResponse=this.readResponseFromFile(file.getPath());
			Reservations reservations = this.prepareReservationsFromXMLResponse(bookingcomResponse);
			if(reservations.getReservation() != null) {
				// read the BookingCom reservation response and prepare BP
				// reservation domain object.
				List<Reservation> listReservation = this
						.readBookingComReservationResponse(reservations,
								channelPartner, sqlSession);
				// call webservice to persist the BP reservation domain objects
				List<Reservation> listPersistedReservation = this
						.callBPReservationService(listReservation, sqlSession);
				
				// now let's find the ReservationExt
	
				// Only when no exception occured and when webserice call is
				// successful we need to updated the last
				// fetch time in BookingComLastFetch
				this.updateBookingComLastFetch(sqlSession, hotelId);
				
				LOG.info("The following reservations are persisted");
				for (Reservation reservation : listPersistedReservation) {
					LOG.info(reservation.getId());
				}
				//only when the reservation is persited sucessfully we get the reservation id.Else reservation id will be empty
				if(listPersistedReservation==null || listPersistedReservation.isEmpty()){
					net.cbtltd.server.EmailService.reservationNotPersisted(bookingcomResponse);
				}
			} else {
				LOG.info("There is no reservation for property<" + hotelId + ">");
				// Only when no exception occured and when webserice call is
				// successful we need to updated the last
				// fetch time in BookingComLastFetch
				this.updateBookingComLastFetch(sqlSession, hotelId);
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
		if(BPThreadLocal.get() != null) {
			// remove the variable the end
			BPThreadLocal.unset();
		}
	}
	
	/**
	 * @param path
	 * @return
	 */
	private String readResponseFromFile(String path){

		 
		BufferedReader br = null;
		String bookingResponse="";
 
		try {
 
			String sCurrentLine="";
			
			br = new BufferedReader(new FileReader(path));
 
			while ((sCurrentLine = br.readLine()) != null) {
				bookingResponse=bookingResponse+sCurrentLine;
			}
			
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
 
	return bookingResponse;
	}
	
	/**
	 * @param reservations
	 * @param bpReservations
	 * @return list of ReservationExt
	 */
	private List<ReservationExt> retrieveReservationExt(Reservations reservations, List<Reservation> bpReservations) {
		List<ReservationExt> exts = new ArrayList<ReservationExt>();
		
		for(Reservation bpReservation:bpReservations) {
			String bookingReservationId = bpReservation.getAltid();
			
			for(Reservations.Reservation bookingReservation:reservations.getReservation()) {
				if(bookingReservationId.equals(bookingReservation.getId())) {
					List<Reservations.Reservation.Room.Addons.Addon> addons = bookingReservation.getRoom().getAddons().getAddon();
					// now create ReservationExt list
					for(Reservations.Reservation.Room.Addons.Addon addon:addons) {
						ReservationExt ext = new ReservationExt();
						ext.setCurrency(bookingReservation.getCurrencycode());
						ext.setAddonType(ReservationTypes.getAddOnType(addon.getType()));
						Double totalPrice = (Double)Double.valueOf((double)addon.getTotalprice());
						if(totalPrice != null && totalPrice > 0.0) {
							ext.setCost(totalPrice);
						} else {
							// TO DO use price mode to do calculation
							if(addon.getPriceMode() == 1) {
//								ext.setCost(addon.)
							}
						}
					}
				}
			}
			
		}
		
		return exts;
	}

	/**
	 * To call Booking.com API and query for Reservations objects
	 * @param request
	 * @return Reservations
	 * @throws Exception
	 */
	private String callBookingComReservationAPI(Request request)
			throws Exception {
		/*// ---
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
	        @Override
	        public boolean isTrusted(X509Certificate[] certificate, String authType) {
	            return true;
	        }
	    };
	    SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    SchemeRegistry registry = new SchemeRegistry();
	    registry.register(new Scheme("https", 443, sf));
	    ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);
		// ---
*/	    
		LOG.info("Inside callBookingComReservationAPI ");
		DefaultHttpClient client = new DefaultHttpClient();
		// keytool -import -trustcacerts -file
		// /home/cshah/certificates/\*.mybookingpal.com -alias mybookingpal.com
		// -keystore /usr/lib/jvm/jdk1.7.0_51/jre/lib/security/cacerts
		HttpPost post = new HttpPost(RazorConfig.getValue(BOOKINGCOM_RESERVATION_URL));
		LOG.info("BOOKINGCOM_RESERVATION_URL : "
				+ RazorConfig.getValue(BOOKINGCOM_RESERVATION_URL));
		String reservationRequestXML = this
				.prepareXMLFromReservationRequest(request);

		StringEntity entity = new StringEntity(reservationRequestXML, "UTF-8");
		entity.setContentType("application/xml");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOG.info(" BookingCom Response Code : " + statusCode);

		//return this.prepareReservationsFromXMLResponse(result.toString());
		return result.toString();
	}

	/**
	 * To prepare XML from the request object of Reservation 
	 * @param request
	 * @return XML
	 * @throws Exception
	 */
	private String prepareXMLFromReservationRequest(Request request)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(request, xmlWriter);
		LOG.info("BookingCom Request " + xmlWriter);
		String reservationRequestXML = URLEncoder.encode(xmlWriter.toString(),
				"UTF-8");
		LOG.info("Encoded xmlWriter " + reservationRequestXML);
		return reservationRequestXML;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String prepareXMLFromBPReservation(Reservation reservation)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(Reservation.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(reservation, xmlWriter);
		LOG.info("Booking Pal reservation Object " + xmlWriter);
		return xmlWriter.toString();
	}

	/**
	 * To form Reservations object from XML response
	 * @param reservationResponse
	 * @return Reservations
	 * @throws Exception
	 */
	private Reservations prepareReservationsFromXMLResponse(
			String reservationResponse) throws Exception {
		LOG.info("BookingCom Response" + reservationResponse);
		StringReader xmlReader = new StringReader(
				reservationResponse.toString());
		JAXBContext jaxbContext = JAXBContext.newInstance(Reservations.class);
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		// jaxbUnMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		return (Reservations) jaxbUnMarshaller.unmarshal(xmlReader);
	}

	
	/**
	 * To form List of reservation object from the response of API
	 * @param reservations
	 * @param channelPartner
	 * @param sqlSession
	 * @return List of reservation object
	 */
	private List<Reservation> readBookingComReservationResponse(
			Reservations reservations, ChannelPartner channelPartner,
			SqlSession sqlSession) {
		LOG.info("Inside readBookingComReservationResponse ");
		List<Reservation> listBPReservation = new ArrayList<Reservation>();
		if(processSpecificConfirmationId && listConfirmationId!=null && listConfirmationId.size()>0){
			
			Iterator<net.cbtltd.rest.bookingcom.reservation.domain.Reservations.Reservation> iteratorReservation=reservations.getReservation().iterator();
			while(iteratorReservation.hasNext()){
				net.cbtltd.rest.bookingcom.reservation.domain.Reservations.Reservation tmpReservation=iteratorReservation.next();
				if(!listConfirmationId.contains(tmpReservation.getId())){
					iteratorReservation.remove();
				}
			}
		}

		for (Reservations.Reservation reservation : reservations
				.getReservation()) {
			
		
			Reservation bpReservation = new Reservation();

			// set channel as travel agent and booking id as alt id
			bpReservation.setAltid(String.valueOf(reservation.getId()));
			bpReservation
					.setAgentid(String.valueOf(channelPartner.getPartyId()));

			// check if this reservation exist in our system
			Reservation existing = sqlSession
					.getMapper(ReservationMapper.class).altreadforchannel(
							bpReservation);
		
			if (existing != null) {
				if("new".equalsIgnoreCase(reservation.getStatus())) {
					continue;
				}
				bpReservation = existing;
			}

			if (StringUtils.isNotEmpty(reservation.getCurrencycode())) {
				bpReservation.setCurrency(reservation.getCurrencycode());
			}

			if (reservation.getCustomer() != null) {
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getCcName())) {
					// set credit card details when cc deatils are available
					
					bpReservation.setCardholder(reservation.getCustomer()
							.getCcName());

					bpReservation.setCardnumber(reservation.getCustomer()
							.getCcNumber() + "");
					if (StringUtils.isNotEmpty(reservation.getCustomer()
							.getCcExpirationDate())) {
						StringTokenizer token = new StringTokenizer(reservation
								.getCustomer().getCcExpirationDate(), "/");
						int cnt = 0;
						while (token.hasMoreTokens()) {
							if (cnt == 0) {
								bpReservation.setCardmonth(token.nextToken());
							} else {
								bpReservation.setCardyear(token.nextToken());
							}
							cnt++;
						}
					}

					if (StringUtils.isNotEmpty(reservation.getCustomer()
							.getCcCvc())) {
						bpReservation.setCardcode(reservation.getCustomer()
								.getCcCvc());
					}

				} else {
					// set debit card details when cc details are available

					bpReservation.setCardholder(reservation.getCustomer()
							.getFirstName()
							+ " "
							+ reservation.getCustomer().getLastName());

					bpReservation.setCardnumber(reservation.getCustomer()
							.getDcIssueNumber());

					if (StringUtils.isNotEmpty(reservation.getCustomer()
							.getDcStartDate())) {
						StringTokenizer token = new StringTokenizer(reservation
								.getCustomer().getDcStartDate(), "/");
						int cnt = 0;
						while (token.hasMoreTokens()) {
							if (cnt == 0) {
								bpReservation.setCardmonth(token.nextToken());
							} else {
								bpReservation.setCardyear(token.nextToken());
							}
							cnt++;
						}
					}

				}
				// Set other details of cutomer here
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getCcType())) {
					bpReservation.setCardType(reservation.getCustomer()
							.getCcType());
				}
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getTelephone())) {
					bpReservation.setPhoneNumber(reservation.getCustomer()
							.getTelephone());
				}
				if (StringUtils
						.isNotEmpty(reservation.getCustomer().getEmail())) {
					bpReservation.setEmailaddress(reservation.getCustomer()
							.getEmail());

				}
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getAddress())) {
					bpReservation.setAddrress(reservation.getCustomer()
							.getAddress());

				}
				if (StringUtils.isNotEmpty(reservation.getCustomer().getCity())) {
					bpReservation.setCity(reservation.getCustomer().getCity());

				}
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getCountrycode())) {
					bpReservation.setCountry(reservation.getCustomer()
							.getCountrycode());

				}
				if (StringUtils.isNotEmpty(reservation.getCustomer()
						.getZip())) {
					bpReservation.setZip(reservation.getCustomer()
							.getZip());
					
				}
				//update reservation name here
			
				bpReservation.setCustomername(reservation.getCustomer()
							.getLastName(), reservation.getCustomer()
						.getFirstName());
				
			
				//capture the special request here
				if(StringUtils.isNotEmpty(reservation.getCustomer().getRemarks())){
					bpReservation.setNotes(reservation.getCustomer().getRemarks());
				}
				else {
					bpReservation.setNotes("From Booking.com");
				}
				
			}
			

			// set arrival date and departure date
			Reservations.Reservation.Room room = reservation.getRoom();
			if (room != null) {
				
				if (room.getArrivalDate() != null) {

					bpReservation.setFromdate(dateTimeFormatter.parseDateTime(
							room.getArrivalDate().toXMLFormat()).toDate());

				}
				if (room.getDepartureDate() != null) {
					bpReservation.setTodate(dateTimeFormatter.parseDateTime(
							room.getDepartureDate().toXMLFormat()).toDate());
				}

				// set number of occupant details
				bpReservation.setAdult((int) room.getNumberofguests());
				bpReservation.setChild((int) room.getMaxChildren());
				
				List<ReservationExt> listReservationExt=new ArrayList<ReservationExt>();
				//Add Guest Details
				ReservationExt guestName=new ReservationExt();
				guestName.setName(room.getGuestName());
				guestName.setType(ReservationUtils.BOOKING_GUEST_ADDON_TYPE);
				guestName.setAddonType("100");
				listReservationExt.add(guestName);
				
				//Fetch Add on and apply to the reservation notes
				
				if(room.getAddons()!=null && room.getAddons().getAddon()!=null && room.getAddons().getAddon().size()>0){
					StringBuilder notesBuilder=new StringBuilder();
					LOG.info("Processing Addons");
					int cnt=1;
					for(Addon addon:room.getAddons().getAddon()){
						ReservationExt addOnExt=new ReservationExt();
						addOnExt.setType("AddOn");
						addOnExt.setAddonType(addon.getType()+"");
						addOnExt.setName(addOnMap.get(addon.getType()));
						addOnExt.setCost((double) addon.getTotalprice());
						addOnExt.setCurrency(bpReservation.getCurrency());
						listReservationExt.add(addOnExt);				
						notesBuilder.append(addOnMap.get(addon.getType()));
						if(cnt<room.getAddons().getAddon().size()){
							notesBuilder.append(",");
						}
						cnt++;
					}
					if(StringUtils.isEmpty(bpReservation.getNotes())){
						bpReservation.setNotes(notesBuilder.toString());
					}else{
						bpReservation.setNotes(bpReservation.getNotes()+","+notesBuilder.toString());
					}
					
				}
				bpReservation.setListReservationExt(listReservationExt);
				//capture special request for a room here
				if(StringUtils.isNotEmpty(room.getRemarks())){
					if(StringUtils.isEmpty(bpReservation.getNotes())){
						bpReservation.setNotes(room.getRemarks());
					}else{
						bpReservation.setNotes(bpReservation.getNotes()+","+room.getRemarks());
					}
				}
				
				
				
			}
			bpReservation.setPrice((double) reservation.getTotalprice());
			bpReservation.setCost((double) reservation.getTotalprice());

			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			Product bpProduct = null;
			if(reservation.getRoom()!=null){
				 bpProduct = ChannelProductService.getInstance()
							.readProductFromChannelProductMapper(sqlSession,
									RazorConfig.getBookingChannelPartnerId(),
									reservation.getHotelId() + "",
									reservation.getRoom().getId() + "");
				 
				 
			}else {
				// for cancellation we will not get the room id
				bpProduct = ChannelProductService.getInstance()
						.readProductFromChannelProductMapper(sqlSession,
								RazorConfig.getBookingChannelPartnerId(),
								reservation.getHotelId() + "",null);
				
			}
			
			bpReservation.setProductid(bpProduct.getId());
			
			if ("new".equalsIgnoreCase(reservation.getStatus()) || "modified".equalsIgnoreCase(reservation.getStatus())) {
				if(bpReservation.hasCard())
					bpReservation.setState(State.Reserved.name());
				else
					bpReservation.setState(State.Provisional.name());
			} else if ("cancelled".equalsIgnoreCase(reservation.getStatus())) {
				bpReservation.setState(State.Cancelled.name());
			} else {
				bpReservation.setState(State.Initial.name());
			}

			listBPReservation.add(bpReservation);
		
		}
		return listBPReservation;
	}

	/**
	 * To call Booking Pal service and load some more values for each of reservation objects
	 * @param listReservation
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private List<Reservation> callBPReservationService(
			List<Reservation> listReservation, SqlSession sqlSession)  {
		
		LOG.info(" Inside callBPReservationService " + listReservation.size());
		Reservation bpreservation = null;
		List<Reservation> listBPReservation = new ArrayList<Reservation>();
		for (Reservation reservation : listReservation) {
			LOG.info("Processing reservation id "+reservation.getAltid());
			try {
				if (State.Confirmed.name().equalsIgnoreCase(reservation.getState())) {
					
					bpreservation = ReservationRest.postReservation(reservation,
							RazorConfig.getValue(RazorConfig.BOOKING_POS), Constants.NO_XSL);
				} else if (State.Reserved.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.hasCard()) {
					
					// convert the currency into us dollars as BP is merchant of record
//					if(!reservation.getCurrency().equalsIgnoreCase(ReservationUtils.US_CURRENCY_CODE)) {
//						double price = reservation.getPrice();
////						CurrencyConverter.convertCurrency(sqlSession, reservation, ReservationUtils.US_CURRENCY_CODE);
//						for(ReservationExt reservationExt:reservation.getListReservationExt()) {
//							if(reservationExt.getAddonType().equals(ReservationUtils.BOOKING_GUEST_ADDON_TYPE)) {
//								reservationExt.setFactor((reservation.getPrice()/price));
//							}
//						}
//					}
					ReservationResponse reservationResponse = ReservationRest
							.createReservationAndPayment(reservation,
									RazorConfig.getValue(RazorConfig.BOOKING_POS));
					if(reservationResponse.isError()) {
						LOG.error(reservationResponse.getErrorMessage() + " booking hotel/reservation id <" + reservation.getProductname() + "/" + reservation.getAltid() + ">");
					}
					if (reservationResponse.getReservationInfo() != null){
						String resId = reservationResponse.getReservationInfo().getId();
						bpreservation = sqlSession.getMapper(ReservationMapper.class).read(resId);
					}
										
				} else if (State.Reserved.name().equalsIgnoreCase(
						reservation.getState())
						&& reservation.noCard()) {
					
					bpreservation = ReservationRest.postReservation(reservation,
							RazorConfig.getValue(RazorConfig.BOOKING_POS), Constants.NO_XSL);
				} else if(State.Cancelled.name().equalsIgnoreCase(
						reservation.getState())){
					System.out.println("Inisde Cancel Reservation");
					List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
					if(paymentTransactions == null || paymentTransactions.isEmpty()) {
						bpreservation = ReservationRest.cancelUnPaidReservation(reservation,
								RazorConfig.getValue(RazorConfig.BOOKING_POS), Constants.NO_XSL);
					}
					else {
						ReservationRest.cancelReservation(Model.encrypt(reservation.getId()));
					}
					
				}else {
				
					bpreservation = ReservationRest.postReservation(reservation,
							RazorConfig.getValue(RazorConfig.BOOKING_POS), Constants.NO_XSL);
					
				}
			}catch(Exception e){
				e.printStackTrace();
				LOG.error("Error while calling the Reservation service",e);
			}
			listBPReservation.add(bpreservation);

		}
		return listBPReservation;
	}

	
	

	/**
	 * @param sqlSession
	 * @param hotelId
	 * @param reservationId
	 * @return
	 */
	private Request createReservationRequest(SqlSession sqlSession, int hotelId,int reservationId) {
		LOG.info("Inside createReservationRequest ");
		Request request = new Request();
		request.setUsername(RazorConfig.getValue(BOOKINGCOM_USERNAME));
		request.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		request.setHotelId(hotelId);
		request.setId(reservationId);
		return request;
	}
	
	/**
	 * To create request object for fetching Reservation details 
	 * @param sqlSession
	 * @param hotelId
	 * @return request
	 */
	private Request createReservationRequest(SqlSession sqlSession, int hotelId) {
		LOG.info("Inside createReservationRequest ");
		Request request = new Request();
		request.setUsername(RazorConfig.getValue(BOOKINGCOM_USERNAME));
		request.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		
		// fetch the last change timestamp from DB using reservation type and property id
		ChannelLastFetch queryBookingComLastFetch = new ChannelLastFetch();
		queryBookingComLastFetch.setApiName("reservation");
		queryBookingComLastFetch.setChannelId(RazorConfig.getBookingChannelPartnerId());
		queryBookingComLastFetch.setProductlId(String.valueOf(hotelId));
		ChannelLastFetch bookingComLastFetch = sqlSession.getMapper(ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		if(bookingComLastFetch != null)
			LOG.info("bookingComLastFetch.getLastfetch() " + bookingComLastFetch.getLastfetch());
		else {
			// query at the reservation level
			queryBookingComLastFetch.setProductlId("0");
			bookingComLastFetch = sqlSession.getMapper(ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		}

		if (bookingComLastFetch == null || "initial".equalsIgnoreCase(bookingComLastFetch.getLastfetch())) {
			// set the current date - ten min while making the request for the
			// first time.
			request.setLastChange(dtf.print(new DateTime().minusHours(10)));
		} else {
			request.setLastChange(bookingComLastFetch.getLastfetch());
		}
		if(lastChange!=null){
			request.setLastChange(lastChange);
		}
		
		request.setHotelId(hotelId);

		return request;
	}

	/**
	 * To generate the request object
	 * @param sqlSession
	 * @return request object
	 */
	private Request createReservationRequest(SqlSession sqlSession) {

		Request request = new Request();
		request.setUsername(RazorConfig.getValue(BOOKINGCOM_USERNAME));
		request.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		// fetch the last change timestamp from DB and set the same here
		ChannelLastFetch queryBookingComLastFetch = new ChannelLastFetch();
		queryBookingComLastFetch.setApiName("reservation");
		queryBookingComLastFetch.setChannelId(RazorConfig
				.getBookingChannelPartnerId());
		ChannelLastFetch bookingComLastFetch = sqlSession.getMapper(
				ChannelLastFetchMapper.class).read(queryBookingComLastFetch);
		if (!"initial".equalsIgnoreCase(bookingComLastFetch.getLastfetch())) {
			// set the current date - ten min while making the request for the
			// first time.

			request.setLastChange(dtf.print(new DateTime().minusMinutes(10)));
		}

		return request;
	}

	/**
	 * To update the reservation detials of the last record
	 * @param sqlSession
	 */
	private void updateBookingComLastFetch(SqlSession sqlSession, int hotelId) {
		ChannelLastFetch bookingComLastFetch = new ChannelLastFetch();
		bookingComLastFetch.setApiName("reservation");
		bookingComLastFetch.setChannelId(RazorConfig
				.getBookingChannelPartnerId());
		bookingComLastFetch.setLastfetch(dtf.print(new DateTime()));
		bookingComLastFetch.setProductlId(String.valueOf(hotelId));
		LOG.info(" bookingComLastFetch.LastFetch "
				+ bookingComLastFetch.getLastfetch());
		ChannelLastFetch bookingComLastFetchByProduct = sqlSession.getMapper(ChannelLastFetchMapper.class)
												.read(bookingComLastFetch);
		if(bookingComLastFetchByProduct != null) {
			sqlSession.getMapper(ChannelLastFetchMapper.class).update(bookingComLastFetch);
		} else {
			sqlSession.getMapper(ChannelLastFetchMapper.class).create(bookingComLastFetch);
		}
	}
	
	/**
	 * To update the reservation detials of the last record
	 * @param sqlSession
	 */
	private void updateBookingComLastFetch(SqlSession sqlSession) {
		ChannelLastFetch bookingComLastFetch = new ChannelLastFetch();
		bookingComLastFetch.setApiName("reservation");
		bookingComLastFetch.setChannelId(RazorConfig
				.getBookingChannelPartnerId());
		bookingComLastFetch.setLastfetch(dtf.print(new DateTime()));
		LOG.info(" bookingComLastFetch.LastFetch "
				+ bookingComLastFetch.getLastfetch());
		sqlSession.getMapper(ChannelLastFetchMapper.class).update(
				bookingComLastFetch);
	}

	
	/**
	 * @return the processReservationWithPayment
	 */
	public boolean isProcessReservationWithPayment() {
		return processReservationWithPayment;
	}


	/**
	 * @param processReservationWithPayment the processReservationWithPayment to set
	 */
	public void setProcessReservationWithPayment(
			boolean processReservationWithPayment) {
		this.processReservationWithPayment = processReservationWithPayment;
	}


	/**
	 * @return the processSpecificConfirmationId
	 */
	public boolean isProcessSpecificConfirmationId() {
		return processSpecificConfirmationId;
	}


	/**
	 * @param processSpecificConfirmationId the processSpecificConfirmationId to set
	 */
	public void setProcessSpecificConfirmationId(
			boolean processSpecificConfirmationId) {
		this.processSpecificConfirmationId = processSpecificConfirmationId;
	}


	/**
	 * @return the listConfirmationId
	 */
	public List<Integer> getListConfirmationId() {
		return listConfirmationId;
	}

	/**
	 * @return teh addonMap
	 */
	public static Map<Integer, String> getAddonmap() {
		return addOnMap;
	}

	/**
	 * @param listConfirmationId
	 */
	public void setListConfirmationId(List<Integer> listConfirmationId) {
		this.listConfirmationId = listConfirmationId;
	}

	/**
	 * @return the lastChange
	 */
	public String getLastChange() {
		return lastChange;
	}

	/**
	 * @param lastChange
	 */
	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}

}
