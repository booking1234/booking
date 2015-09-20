package net.cbtltd.rest.bookingcom.availability;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.bookingcom.availability.domain.Request;
import net.cbtltd.rest.response.CalendarElement;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Reservation.State;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;
/**
 * AvailabilityUtils is the util class
 * using AvailabilityUtils - price and availablity changes are pushed to booking.com 
 * @author nibodha
 *
 */
public class AvailabilityUtils {
	public static final String BOOKINGCOM_AVAILABILITY_URL = "bp.bookingcom.availability.url";
	public static final String BOOKINGCOM_USERNAME = "bp.bookingcom.username";
	public static final String BOOKINGCOM_PWD = "bp.bookingcom.pwd";
	public static final String BP_AVAILABILITY_SERVICE_URL = "bp.availability.service.url";
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private static final Logger LOG = Logger.getLogger(AvailabilityUtils.class
			.getName());

	private int rateType = 0;
	private int roomsToSell = 1;
	private int minimunStay = 1;
	private int maximumStay = 20;
	private int closed = 0;
	private int closedOnArrival = 1;
	private int closedOnDeparture = 1;
	private Integer fromPriceId;
	private Integer toPriceId;
	private boolean loadSelectedProducts = false;
	private List<ChannelProductMap> listToBeLoadedChannelProductMap = new ArrayList<ChannelProductMap>();
	private List<String> listInputProducts = new ArrayList<String>();
	
	/**
	 * This method is to fetch the availabilities and updates to Booking.com
	 */
	
	public void fetchAndUpdateAvailabilityToBookingCom(Reservation reservation,ChannelProductMap channelProductMap) {
		SqlSession sqlSession = RazorServer.openSession();
		List<Request> listAvailabilityRequest = new ArrayList<Request>();
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		try {
			
			//prepare the AvailabilityRequest
			listAvailabilityRequest.add(this
					.createAvailabilityRequestFromTo(reservation,channelProductMap));
			
			// call the BookingCom Availability API
			for (Request availabilityRequest : listAvailabilityRequest) {
				String statusKey = availabilityRequest.getHotelId() + ",";
				if (availabilityRequest.getRoom() != null) {
					statusKey = statusKey
							+ availabilityRequest.getRoom().getId();
				}

				mapStatusCodeMap
						.put(statusKey,
								this.callBookingComAvailabilityAPI(availabilityRequest));

			}
			for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
				LOG.info(entry.getKey() + " " + entry.getValue());
			}
			
		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
	}

	public void fetchAndUpdateAvailabilityToBookingCom(Price price,ChannelProductMap channelProductMap) {
		SqlSession sqlSession = RazorServer.openSession();
		List<Request> listAvailabilityRequest = new ArrayList<Request>();
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		try {
			//get the calendar response for the product
			CalendarResponse calendarResponse = this
					.getAvailabilityCalendar(channelProductMap
							.getProductId());
			
			//prepare the AvailabilityRequest
			listAvailabilityRequest.add(this
					.createAvailabilityRequestFromTo(
							calendarResponse, channelProductMap,
							price));
			
			// call the BookingCom Availability API
			for (Request availabilityRequest : listAvailabilityRequest) {
				String statusKey = availabilityRequest.getHotelId() + ",";
				if (availabilityRequest.getRoom() != null) {
					statusKey = statusKey
							+ availabilityRequest.getRoom().getId();
				}

				mapStatusCodeMap
						.put(statusKey,
								this.callBookingComAvailabilityAPI(availabilityRequest));

			}
			for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
				LOG.info(entry.getKey() + " " + entry.getValue());
			}
			
		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
	}

	/**
	 * This method is to fetch the availabilities and updates to Booking.com 
	 */
	public void fetchAndUpdateAvailabilityToBookingCom() {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			List<ChannelProductMap> listChannelProductMap = new ArrayList<ChannelProductMap>();
			
			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			listChannelProductMap = ChannelProductService.getInstance()
					.readAllProductFromChannelProductMapper(sqlSession,
							RazorConfig.getBookingChannelPartnerId());
			if (loadSelectedProducts) {
				for (ChannelProductMap channelProductMap : listChannelProductMap) {
					if (listInputProducts.contains(channelProductMap
							.getProductId())) {
						listToBeLoadedChannelProductMap.add(channelProductMap);
					}
				}
			} else {
				listToBeLoadedChannelProductMap = listChannelProductMap;
			}
			for (ChannelProductMap channelProductMap : listToBeLoadedChannelProductMap) {
				LOG.info("Loading rates for property id <" + channelProductMap.getChannelProductId() + ">.................");
				List<Request> listAvailabilityRequest = new ArrayList<Request>();
				Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
				
				CalendarResponse calendarResponse = this
						.getAvailabilityCalendar(channelProductMap
								.getProductId());

				// get the Price entry for the product
				Price queryPrice=new Price();
				queryPrice.setEntityid(channelProductMap.getProductId());
				MutableDateTime dateTime = new MutableDateTime();
				queryPrice.setDate(dateTime.toDate());
				dateTime.addYears(2);
				queryPrice.setTodate(dateTime.toDate());
				if((this.fromPriceId!=null && this.fromPriceId!=0) && (this.toPriceId!=null && this.toPriceId!=0)){
					queryPrice.setIdFrom(fromPriceId);
					queryPrice.setIdTo(toPriceId);
				}
				
				
				List<Price> listPrice = sqlSession.getMapper(PriceMapper.class)
						.readByEntityId(queryPrice);

				if (listPrice != null) {
					LOG.info("Size of Price is " + listPrice.size());
				}
				if (listPrice != null && listPrice.size() > 0) {
					LOG.info("listPrice.size() " + listPrice.size());
					for (Price price : listPrice) {
						listAvailabilityRequest.add(this
								.createAvailabilityRequestFromTo(
										calendarResponse, channelProductMap,
										price));
					}

				}
				
				// call the BookingCom Availability API for this product
				for (Request availabilityRequest : listAvailabilityRequest) {
					String statusKey = availabilityRequest.getHotelId() + ":";
					if (availabilityRequest.getRoom() != null) {
						statusKey = statusKey
								+ availabilityRequest.getRoom().getId() + ":" + availabilityRequest.hashCode();
					}

					mapStatusCodeMap
							.put(statusKey,
									this.callBookingComAvailabilityAPI(availabilityRequest));

				}
				for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
					LOG.info("Upload status for <" + entry.getKey() + ">: " + entry.getValue());
				}

			}
			

		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
	}

	/**
	 * To get the calendar response for the particular product
	 * 
	 * @param productId
	 * @return CalendarResponse
	 *
	 */
	private CalendarResponse getAvailabilityCalendar(String productId) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dateTime = new DateTime();

		return ReservationRest.getCalendar(RazorConfig.getValue("pos.code"),
				productId, dateTimeFormatter.print(dateTime));
	}

	/**
	 * To generate the request object using Calendar Response, Relation of Channel & Product,list of Price          
	 * 
	 * @param calendarResponse
	 * @param channelProductMap
	 * @param listPrice
	 * @return request object
	 */
	private Request createAvailabilityRequest(
			CalendarResponse calendarResponse,
			ChannelProductMap channelProductMap, List<Price> listPrice) {

		Request availabilityRequest = new Request();
		availabilityRequest.setUsername(RazorConfig
				.getValue(BOOKINGCOM_USERNAME));
		availabilityRequest.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		availabilityRequest.setHotelId(Integer.parseInt(channelProductMap
				.getChannelProductId()));
		availabilityRequest.setVersion((float) 1.0);
		Request.Room room = new Request.Room();
		room.setId(Integer.parseInt(channelProductMap.getChannelRoomId()));

		Request.Room.Date roomDate = new Request.Room.Date();
		Request.Room.Date.Rate rate = new Request.Room.Date.Rate();
		if (rateType == 0) {
			rate.setId(RateType.STANDARD_RATE.getRateId());
		} else {
			rate.setId(rateType);
		}

		int cnt = 1;
		Map<QName, String> dateValue = new HashMap<QName, String>();
		for (Price price : listPrice) {
			String priceDate = "";
			if (price.getDate() != null) {
				try {
					priceDate = dateTimeFormatter.print(new DateTime(price
							.getDate()));
				} catch (Exception e) {
					// parsing exception during date processing
					LOG.error("Exception while Parsing Price Date ", e);
					// when date is invalid we need not push to Booking.com
					continue;
				}
			}

			if (!isDateReserved(priceDate, calendarResponse)) {
				dateValue.put(new QName("value" + cnt), priceDate);
				cnt++;
			}

		}

		roomDate.setOtherAttributes(dateValue);
		roomDate.setRate(rate);
		roomDate.setRoomstosell(roomsToSell);
		roomDate.setMinimumstay(minimunStay + "");
		roomDate.setMaximumstay(maximumStay + "");
		roomDate.setClosed(closed + "");
		roomDate.setClosedonarrival(closedOnArrival + "");
		roomDate.setClosedondeparture(closedOnDeparture + "");
		if (listPrice != null && listPrice.size() > 0) {
			roomDate.setPrice(listPrice.get(0).getCost() + "");
			if (!(room.getId() == 1310404)) {
				// for single room we shldnt add price1 tag
				roomDate.setPrice1(listPrice.get(0).getCost() + "");
			}

		}
		List<Request.Room.Date> listRoomDate = new ArrayList<Request.Room.Date>();
		listRoomDate.add(roomDate);
		room.setDate(listRoomDate);
		availabilityRequest.setRoom(room);
		return availabilityRequest;

	}
	
	/**
	 * To generate the request object using Reservation object        
	 * 
	 * @param calendarResponse
	 * @param channelProductMap
	 * @param price
	 * @return  request object
	 */
	private Request createAvailabilityRequestFromTo(
			Reservation reservation,
			ChannelProductMap channelProductMap) {

		Request availabilityRequest = new Request();
		availabilityRequest.setUsername(RazorConfig
				.getValue(BOOKINGCOM_USERNAME));
		availabilityRequest.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		availabilityRequest.setHotelId(Integer.parseInt(channelProductMap
				.getChannelProductId()));
		availabilityRequest.setVersion((float) 1.0);
		Request.Room room = new Request.Room();
		room.setId(Integer.parseInt(channelProductMap.getChannelRoomId()));
		
		Request.Room.Date.Rate rate = new Request.Room.Date.Rate();
		if (rateType == 0) {
			rate.setId(Integer.parseInt(channelProductMap.getChannelRateId()));
		} else {
			rate.setId(rateType);
		}

		// restriction starts here
		List<Request.Room.Date> listRoomDate = new ArrayList<Request.Room.Date>();
		Request.Room.Date reservedDate=new Request.Room.Date();
		
		int cnt = 1;

		String priceFromDate = "";
		String priceToDate = "";
		Map<QName, String> dateValue = new HashMap<QName, String>();
		if (reservation.getFromdate() != null && reservation.getTodate() != null) {
			try {
				priceFromDate = dateTimeFormatter.print(new DateTime(reservation
						.getFromdate()));
				priceToDate = dateTimeFormatter.print(new DateTime(reservation
						.getTodate()));
				 dateValue.put(new QName("from"), priceFromDate);
				 dateValue.put(new QName("to"), priceToDate);
				 if(State.Cancelled.name().equalsIgnoreCase(reservation.getState())){
					 reservedDate.setClosed("0");
				 }else{
					 reservedDate.setClosed("1");
				 }
				
				 reservedDate.setOtherAttributes(dateValue);
			} catch (Exception e) {
				// parsing exception during date processing
				LOG.error("Exception while Parsing Price Date ", e);
				// when date is invalid we need not push to Booking.com

			}
			reservedDate.setRate(rate);
			listRoomDate.add(reservedDate);
		}
	
		

		room.setDate(listRoomDate);
		

		availabilityRequest.setRoom(room);
		return availabilityRequest;

	}

	/**
	 * To generate the request object using Calendar Response, Relation of Channel & Product, Price          
	 * 
	 * @param calendarResponse
	 * @param channelProductMap
	 * @param price
	 * @return  request object
	 */
	private Request createAvailabilityRequestFromTo(
			CalendarResponse calendarResponse,
			ChannelProductMap channelProductMap, Price price) {

		Request availabilityRequest = new Request();
		availabilityRequest.setUsername(RazorConfig
				.getValue(BOOKINGCOM_USERNAME));
		availabilityRequest.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		availabilityRequest.setHotelId(Integer.parseInt(channelProductMap
				.getChannelProductId()));
		availabilityRequest.setVersion((float) 1.0);
		Request.Room room = new Request.Room();
		room.setId(Integer.parseInt(channelProductMap.getChannelRoomId()));

		Request.Room.Date.Rate rate = new Request.Room.Date.Rate();
		if (rateType == 0) {
//			rate.setId(RateType.STANDARD_RATE.getRateId());
			rate.setId(Integer.parseInt(channelProductMap.getChannelRateId()));
		} else {
			rate.setId(rateType);
		}

		// restriction starts here
		List<Request.Room.Date> listRoomDate = new ArrayList<Request.Room.Date>();
		int cnt = 1;

		String priceFromDate = "";
		String priceToDate = "";
		if (price.getDate() != null && price.getTodate() != null) {
			try {
				priceFromDate = dateTimeFormatter.print(new DateTime(price
						.getDate()));
				priceToDate = dateTimeFormatter.print(new DateTime(price
						.getTodate()));
				// dateValue.put(new QName("from"), priceFromDate);
				// dateValue.put(new QName("to"), priceToDate);
			} catch (Exception e) {
				// parsing exception during date processing
				LOG.error("Exception while Parsing Price Date ", e);
				// when date is invalid we need not push to Booking.com

			}
		}
	//	if (RateType.STANDARD_RATE.getName().equalsIgnoreCase(price.getName())) {
			List<String> listDate = new ArrayList<String>();
			// check Minstay here
			DateTime dt1 = dateTimeFormatter.parseDateTime(priceFromDate);
			DateTime dt2 = dateTimeFormatter.parseDateTime(priceToDate);
			int diff = Days.daysBetween(dt1, dt2).getDays() + 1;
			
			// we use Minimum Revenue Value model to determine min stay for razor properties
			int minStay = 0;
			if(price.getUnit().equals(Unit.DAY)) {
				minStay = (int)(price.getMinimum()/price.getValue());
			}
			
			if (price.getMinStay() == diff || minStay == diff) {
				LOG.info("price.getMinStay() == diff condition met "+" price.getMinStay() "+price.getMinStay()+" diff "+diff);
				for (int i = 0; i < diff; i++) {
					Map<QName, String> dateValue = new HashMap<QName, String>();
					DateTime d = dt1
							.withFieldAdded(DurationFieldType.days(), i);
					if (!isDateReserved(dateTimeFormatter.print(d),
							calendarResponse)) {

						dateValue.put(new QName("value"),
								dateTimeFormatter.print(d));
						Request.Room.Date roomDate = new Request.Room.Date();
						roomDate.setRate(rate);
						roomDate.setRoomstosell(roomsToSell);
						roomDate.setPrice(price.getValue() + "");
						// not needed for the production env.
//						if (!(room.getId() == 1310404)) {
//							// for single room we shldnt add price1 tag
//							roomDate.setPrice1(price.getMinimum() + "");
//						}

						roomDate.setOtherAttributes(dateValue);
						if(price.getMinStay() != null && price.getMinStay() > 0)
							roomDate.setMinimumstay(price.getMinStay() + "");
						if(price.getMaxStay() != null && price.getMaxStay() > 0)
							roomDate.setMaximumstay(price.getMaxStay() + "");
						if (i != 0) {
							roomDate.setClosedonarrival(closedOnArrival + "");
						}
						if (i <diff - 1) {
							roomDate.setClosedondeparture(closedOnDeparture
									+ "");
						}
						listRoomDate.add(roomDate);
					}

				}
			} else if (price.getMinStay() < diff || minStay < diff) {
				LOG.info("price.getMinStay() < diff condition met "+" price.getMinStay() "+price.getMinStay()+" diff "+diff);
				Map<QName, String> dateValue = new HashMap<QName, String>();
				for (int i = 0; i < diff; i++) {
					DateTime d = dt1
							.withFieldAdded(DurationFieldType.days(), i);
					if (DateTimeComparator.getDateOnlyInstance().compare(d, new DateTime()) >= 0 
							&& !isDateReserved(dateTimeFormatter.print(d),
							calendarResponse)) {
						dateValue.put(new QName("value" + (i + 1)),
								dateTimeFormatter.print(d));
					}
				}
//				dateValue.put(new QName("from"), priceFromDate);
//				dateValue.put(new QName("to"), priceToDate);
				Request.Room.Date roomDate = new Request.Room.Date();
				roomDate.setRate(rate);
				roomDate.setRoomstosell(roomsToSell);
				roomDate.setPrice(price.getValue() + "");
				// not needed for the production env.
//				if (!(room.getId() == 1310404)) {
//					// for single room we shldnt add price1 tag
//					roomDate.setPrice1(price.getMinimum() + "");
//				}
				roomDate.setOtherAttributes(dateValue);
				if(minStay > 0)
					roomDate.setMinimumstay(minStay + "");
				if(price.getMaxStay() != null && price.getMaxStay() > 0)
					roomDate.setMaximumstay(price.getMaxStay() + "");

				listRoomDate.add(roomDate);

			}else if (price.getMinStay() > diff) {
				LOG.info("price.getMinStay() > diff condition met "+" price.getMinStay() "+price.getMinStay()+" diff "+diff);
				Map<QName, String> dateValue = new HashMap<QName, String>();
				for (int i = 0; i < diff; i++) {
					DateTime d = dt1
							.withFieldAdded(DurationFieldType.days(), i);
					if (DateTimeComparator.getDateOnlyInstance().compare(d, new DateTime()) >= 0 && !isDateReserved(dateTimeFormatter.print(d),
							calendarResponse)) {
						dateValue.put(new QName("value" + (i + 1)),
								dateTimeFormatter.print(d));
					}
				}
				Request.Room.Date roomDate = new Request.Room.Date();
				roomDate.setRate(rate);
				roomDate.setRoomstosell(roomsToSell);
				roomDate.setPrice(price.getValue() + "");
//				if (!(room.getId() == 1310404)) {
//					// for single room we shldnt add price1 tag
//					roomDate.setPrice1(price.getMinimum() + "");
//				}
				roomDate.setOtherAttributes(dateValue);
				roomDate.setMinimumstay(price.getMinStay() + "");
				roomDate.setMaximumstay(price.getMaxStay() + "");

				listRoomDate.add(roomDate);

			}
			//commenting the below else if since minimumStay will solve the purpose of closedOnArrival
		/*	else if (price.getMinStay() < diff && price.getMaxStay() > 1) {

				for (int i = 0; i < diff; i++) {
					Map<QName, String> dateValue = new HashMap<QName, String>();
					DateTime d = dt1
							.withFieldAdded(DurationFieldType.days(), i);
					if (!isDateReserved(dateTimeFormatter.print(d),
							calendarResponse)) {
						dateValue.put(new QName("value"),
								dateTimeFormatter.print(d));
						Request.Room.Date roomDate = new Request.Room.Date();
						roomDate.setRate(rate);
						roomDate.setRoomstosell(roomsToSell);
						roomDate.setPrice(price.getCost() + "");
						if (!(room.getId() == 1310404)) {
							// for single room we shldnt add price1 tag
							roomDate.setPrice1(price.getMinimum() + "");
						}
						roomDate.setOtherAttributes(dateValue);
						roomDate.setMinimumstay(price.getMinStay() + "");
						roomDate.setMaximumstay(price.getMaxStay() + "");
						
						if (i>(diff - price.getMaxStay())-1) {
							roomDate.setClosedonarrival(closedOnArrival + "");
						}

						listRoomDate.add(roomDate);
					}
				}
			}*/

		//}

		

		room.setDate(listRoomDate);

		availabilityRequest.setRoom(room);
		return availabilityRequest;

	}

	/**
	 * To generate the request object using Calendar Response, Relation of Channel & Product, Price          
	 * 
	 * @param calendarResponse
	 * @param channelProductMap
	 * @param price
	 * @return  request object
	 */
	private Request createAvailabilityRequest(
			CalendarResponse calendarResponse,
			ChannelProductMap channelProductMap, Price price) {
		Request availabilityRequest = new Request();
		availabilityRequest.setUsername(RazorConfig
				.getValue(BOOKINGCOM_USERNAME));
		availabilityRequest.setPassword(RazorConfig.getValue(BOOKINGCOM_PWD));
		availabilityRequest.setHotelId(Integer.parseInt(channelProductMap
				.getChannelProductId()));
		availabilityRequest.setVersion((float) 1.0);
		Request.Room room = new Request.Room();
		room.setId(Integer.parseInt(channelProductMap.getChannelRoomId()));

		Request.Room.Date roomDate = new Request.Room.Date();
		Request.Room.Date.Rate rate = new Request.Room.Date.Rate();

		rate.setId(RateType.STANDARD_RATE.getRateId());
		int cnt = 1;
		Map<QName, String> dateValue = new HashMap<QName, String>();

		String priceDate = "";
		if (price.getDate() != null) {
			try {
				priceDate = dateTimeFormatter.print(new DateTime(price
						.getDate()));
			} catch (Exception e) {
				// parsing exception during date processing
				LOG.error("Exception while Parsing Price Date ", e);
				// when date is invalid we need not push to Booking.com

			}
		}

		if (!isDateReserved(priceDate, calendarResponse)) {
			dateValue.put(new QName("value" + cnt), priceDate);
			cnt++;
		}

		roomDate.setOtherAttributes(dateValue);
		roomDate.setRate(rate);
		roomDate.setRoomstosell(1);

		roomDate.setPrice(price.getCost() + "");
		roomDate.setPrice1(price.getCost() + "");

		List<Request.Room.Date> listRoomDate = new ArrayList<Request.Room.Date>();
		listRoomDate.add(roomDate);
		room.setDate(listRoomDate);
		availabilityRequest.setRoom(room);
		return availabilityRequest;

	}

	/**
	 * To check whether the priceDate is reserved on calendarResponse
	 * 
	 * @param priceDate
	 * @param calendarResponse
	 * @return boolean 
	 */
	private boolean isDateReserved(String priceDate,
			CalendarResponse calendarResponse) {

		if (calendarResponse == null
				|| calendarResponse.getItems() == null
				|| (calendarResponse.getItems() != null && calendarResponse
						.getItems().length == 0)) {
			// if availability is empty then obviously date is not reserved
			return false;
		} else {
			for (CalendarElement calendarElement : calendarResponse.getItems()) {

				if (calendarElement.getDate().equalsIgnoreCase(priceDate)
//						&& "reserved".equalsIgnoreCase(calendarElement.getState())    // any calendar element that we get from the API is a blocked date
						) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * To generate XML from request object of availability 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String prepareXMLFromAvailabilityRequest(Request request)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(request, xmlWriter);
		LOG.info("BookingCom Request " + xmlWriter);
		String availabilityRequestXML = URLEncoder.encode(xmlWriter.toString(),
				"UTF-8");
//		LOG.info("Encoded xmlWriter " + availabilityRequestXML);
		return availabilityRequestXML;
	}

	/**
	 * using request object, This method calls the Booking.com API and return the status code
	 * @param request
	 * @return statusCode
	 * @throws Exception
	 */
	private String callBookingComAvailabilityAPI(Request request) throws Exception {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				RazorConfig.getValue(BOOKINGCOM_AVAILABILITY_URL));
		LOG.info("BOOKINGCOM_AVAILABILITY_URL : "
				+ RazorConfig.getValue(BOOKINGCOM_AVAILABILITY_URL));
		String availabilityRequestXML = this
				.prepareXMLFromAvailabilityRequest(request);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

		StringEntity entity = new StringEntity(availabilityRequestXML, "UTF-8");
		entity.setContentType("application/xml");
		post.setEntity(entity);
		int statusCode=0;
		HttpResponse response = client.execute(post);
		statusCode = response.getStatusLine().getStatusCode();

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOG.info("BookingCom Response Status Code : " + statusCode);
		
		return statusCode + ":" + result.toString();

	}

	/**
	 * getter for rateType
	 * @return rateType
	 */
	public int getRateType() {
		return rateType;
	}

	/**
	 * setter for rateType
	 * @param rateType
	 */
	public void setRateType(int rateType) {
		this.rateType = rateType;
	}

	/**
	 * getter for roomsToSell
	 * @return roomsToSell
	 */
	public int getRoomsToSell() {
		return roomsToSell;
	}

	/**
	 * setter for roomsToSell
	 * @param roomsToSell
	 */
	public void setRoomsToSell(int roomsToSell) {
		this.roomsToSell = roomsToSell;
	}

	/**
	 * getter for minimunStay
	 * @return minimunStay
	 */
	public int getMinimunStay() {
		return minimunStay;
	}

	/** 
	 * setter for minimunStay
	 * @param minimunStay
	 */
	public void setMinimunStay(int minimunStay) {
		this.minimunStay = minimunStay;
	}

	/**
	 * getter for maximumStay
	 * @return maximumStay
	 */
	public int getMaximumStay() {
		return maximumStay;
	}

	/**
	 * setter for maximumStay
	 * @param maximumStay
	 */
	public void setMaximumStay(int maximumStay) {
		this.maximumStay = maximumStay;
	}

	/**
	 * getter for closed state
	 * @return closed state
	 */
	public int getClosed() {
		return closed;
	}

	/**
	 * setter for closed state
	 * @param closed
	 */
	public void setClosed(int closed) {
		this.closed = closed;
	}

	/**
	 * getter for closedOnArrival
	 * @return closedOnArrival
	 */
	public int getClosedOnArrival() {
		return closedOnArrival;
	}

	/**
	 * setter for closedOnArrival
	 * @param closedOnArrival
	 */
	public void setClosedOnArrival(int closedOnArrival) {
		this.closedOnArrival = closedOnArrival;
	}

	/**
	 * getter for closedOnDeparture
	 * @return closedOnDeparture
	 */
	public int getClosedOnDeparture() {
		return closedOnDeparture;
	}

	/**
	 * setter for closedOnDeparture
	 * @param closedOnDeparture
	 */
	public void setClosedOnDeparture(int closedOnDeparture) {
		this.closedOnDeparture = closedOnDeparture;
	}

	/**
	 * getter for loadSelectedProducts flag
	 * @return loadSelectedProducts
	 */
	public boolean isLoadSelectedProducts() {
		return loadSelectedProducts;
	}

	/**
	 * setter for flag to loadSelectedProducts or not
	 * @param loadSelectedProducts
	 */
	public void setLoadSelectedProducts(boolean loadSelectedProducts) {
		this.loadSelectedProducts = loadSelectedProducts;
	}

	/**
	 * getter for list of InputProducts
	 * @return list of InputProducts
	 */
	public List<String> getListInputProducts() {
		return listInputProducts;
	}

	/**
	 * setter for list of InputProducts
	 * @param listInputProducts
	 */
	public void setListInputProducts(List<String> listInputProducts) {
		this.listInputProducts = listInputProducts;
	}

	/**
	 * getter for  fromPriceId
	 * @return fromPriceId
	 */
	public Integer getFromPriceId() {
		return fromPriceId;
	}

	/**
	 * setter for fromPriceId
	 * @param fromPriceId
	 */
	public void setFromPriceId(Integer fromPriceId) {
		this.fromPriceId = fromPriceId;
	}

	/**
	 * getter for toPriceId
	 * @return toPriceId
	 */
	public Integer getToPriceId() {
		return toPriceId;
	}

	/**
	 * setter for toPriceId
	 * @param toPriceId
	 */
	public void setToPriceId(Integer toPriceId) {
		this.toPriceId = toPriceId;
	}


}
