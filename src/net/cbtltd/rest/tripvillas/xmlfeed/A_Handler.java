/**
 * 
 */
package net.cbtltd.rest.tripvillas.xmlfeed;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import net.cbtltd.rest.common.utils.KeyValuePair;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.paypal.PaypalHandlerNew;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation.Images.Image;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation.Location.Address;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation.Rooms.Room;
import net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices;
import net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price;
import net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room.SpecialRates.SpecialRate;
import net.cbtltd.rest.tripvillas.xmlfeed.vacancies.Vacancies;
import net.cbtltd.rest.tripvillas.xmlfeed.vacancies.Vacancies.Vacancy;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.integration.ImageService;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.RelationService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.GatewayInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gwt.thirdparty.guava.common.collect.ArrayListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.LinkedListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.mybookingpal.config.RazorConfig;

/**
 * Handler to do the load operation of xml from tripvillas
 * 
 * @author Suresh Kumar S
 * 
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private boolean downloadFlag = true;

	/**
	 * Constructor
	 * 
	 * @param partner
	 */
	public A_Handler(Partner partner) {
		super(partner);
	}

	public A_Handler(Partner partner, boolean downloadFlag) {
		super(partner);
		this.downloadFlag = downloadFlag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#isAvailable(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {

		if (reservation.getAltid() != null) {
			StringBuilder requestUrl = new StringBuilder();
			requestUrl.append(RazorConfig
					.getValue(TripvillasUtils.TRIPVILLAS_AVAILABILITY_URL));
			Product product = ProductService.getInstance().getProduct(sqlSession, reservation.getProductid());
			if(product != null){
				if(product.getOptions() != null && !product.getOptions().isEmpty()){
					if(product.getOptions().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
						Product prod = ProductService.getInstance().getProduct(sqlSession, product.getPartofid());
						requestUrl.append("?property_id=" + prod.getAltid());
						requestUrl.append("&room_id=" + product.getAltid());
					} else {
						requestUrl.append("?property_id=" + reservation.getAltid());
						List<String> roomIds = ProductService.getInstance().getRoomsForProduct(sqlSession, product.getId());
						if(roomIds != null && !roomIds.isEmpty()){
							requestUrl.append("&room_id=" + roomIds.get(0));
						}
					}
				}
			}
			if (reservation.getCheckin() != null) {
				requestUrl.append("&check_in=" + reservation.getCheckin());
			}
			if (reservation.getCheckout() != null) {
				requestUrl.append("&check_out=" + reservation.getCheckout());
			}
			requestUrl.append("&format=json");
			StringReader jsonReader = TripvillasUtils.readDataFromUrlGet(
					requestUrl.toString(), RazorConfig.getValue(TripvillasUtils.AUTHORIZATION));

			JsonElement result = new JsonParser().parse(jsonReader);
			if("Success".equalsIgnoreCase(result.getAsJsonObject().get("status").getAsString())){
				JsonArray rooms = result.getAsJsonObject().get("data").getAsJsonObject().get("rooms").getAsJsonArray();
				if(rooms.size() > 0){
					String availablity=rooms.get(0).getAsJsonObject().get("availability").getAsString();
					return (!availablity.contains("N")); 
				}
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#createReservation(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		if (reservation.getAltid() != null) {
			try {
				String productId = null;
				String roomId = null;
				Product product = ProductService.getInstance().getProduct(sqlSession, reservation.getProductid());
				if(product != null){
					if(product.getOptions() != null && !product.getOptions().isEmpty()){
						if(product.getOptions().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
							Product prod = ProductService.getInstance().getProduct(sqlSession, product.getPartofid());
							productId = prod.getAltid();
							roomId = product.getAltid();
						} else {
							productId =  reservation.getAltid();
							List<String> roomIds = ProductService.getInstance().getRoomsForProduct(sqlSession, product.getId());
							if(roomIds != null && !roomIds.isEmpty()){
								roomId =  roomIds.get(0);
							}
						}
					}
				}
				String requestUrl = RazorConfig
						.getValue(TripvillasUtils.TRIPVILLAS_BOOKING_URL);
				Set<KeyValuePair> keyValueSet = new HashSet<KeyValuePair>();
				keyValueSet.add(new KeyValuePair(TripvillasUtils.ADULTS_NUM,
						reservation.getAdult()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.KIDS_NUM,
						reservation.getChild()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.CHECK_IN,
						TripvillasUtils.YYYY_MM_DD_TIME.format(reservation.getFromdate())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.CHECK_OUT,
						TripvillasUtils.YYYY_MM_DD_TIME.format(reservation.getTodate())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.ROOM_ID, roomId));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.GUEST_EMAIL,
						reservation.getEmailaddress()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.FIRST_NAME,
						reservation.getFirstname()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.LAST_NAME,
						reservation.getFamilyname()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.COUNTRY_CODE,
						ProductService.getInstance().getPhoneCode(sqlSession, reservation.getCountry())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.MOBILE_NO,
						reservation.getPhoneNumber()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.TOTAL_AMOUNT,
						reservation.getPrice()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.PROPERTY, productId));
				String urlParameters = TripvillasUtils
						.formJsonString(keyValueSet);

				StringReader jsonReader = TripvillasUtils.readDataFromUrlPost(
						requestUrl, urlParameters, RazorConfig.getValue(TripvillasUtils.AUTHORIZATION));
				JsonElement result = new JsonParser().parse(jsonReader);
				if(result instanceof JsonArray){
					JsonObject jsonObj = result.getAsJsonArray().get(0).getAsJsonObject();
					if("Success".equalsIgnoreCase(jsonObj.get("status").getAsString())){
						reservation.setState(jsonObj.get("data").getAsJsonObject().get("state").getAsString());
						reservation.setConfirmationId(jsonObj.get("data").getAsJsonObject().get("conversation").getAsString());
						reservation.setCost(jsonObj.get("data").getAsJsonObject().get("total_amount").getAsDouble());
					}
				}
			} catch (Exception e) {
				LOG.error("Error while creating reservation... " + e);
			}
		}

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readPrice(org.apache.ibatis.session.
	 * SqlSession, net.cbtltd.shared.Reservation, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		ReservationPrice reservationPrice = null;
		if (reservation.getAltid() != null && (reservation.getAltid().equalsIgnoreCase(productAltId))) {
			reservationPrice = new ReservationPrice();
			StringBuilder requestUrl = new StringBuilder();
			requestUrl.append(RazorConfig
					.getValue(TripvillasUtils.TRIPVILLAS_RATES_URL));
			Product product = ProductService.getInstance().getProduct(sqlSession, reservation.getProductid());
			if(product != null){
				if(product.getOptions() != null && !product.getOptions().isEmpty()){
					if(product.getOptions().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
						Product prod = ProductService.getInstance().getProduct(sqlSession, product.getPartofid());
						requestUrl.append("?property_id=" + prod.getAltid());
						requestUrl.append("&rooms=" + product.getAltid());
					} else {
						requestUrl.append("?property_id=" + reservation.getAltid());
						List<String> roomIds = ProductService.getInstance().getRoomsForProduct(sqlSession, product.getId());
						if(roomIds != null && !roomIds.isEmpty()){
							requestUrl.append("&rooms=" + roomIds.get(0));
						}
					}
				}
			}
			if (reservation.getCheckin() != null) {
				requestUrl.append("&check_in=" + reservation.getCheckin());
			}
			if (reservation.getCheckout() != null) {
				requestUrl.append("&check_out=" + reservation.getCheckout());
			}
			requestUrl.append("&format=json");
			StringReader jsonReader = TripvillasUtils.readDataFromUrlGet(
					requestUrl.toString(), RazorConfig.getValue(TripvillasUtils.AUTHORIZATION));

			JsonElement result = new JsonParser().parse(jsonReader);
			if("Success".equalsIgnoreCase(result.getAsJsonObject().get("status").getAsString())){
				reservationPrice.setCurrency(result.getAsJsonObject().get("data").getAsJsonObject().get("currency").getAsString());
				Double totalAmt = result.getAsJsonObject().get("data").getAsJsonObject().get("total_amount").getAsDouble();
				reservation.setCost(totalAmt);
				reservationPrice.setTotal(totalAmt);
			}
		}
		return reservationPrice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#createReservationAndPayment(org.apache
	 * .ibatis.session.SqlSession, net.cbtltd.shared.Reservation,
	 * net.cbtltd.shared.finance.gateway.CreditCard)
	 */
	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		Map<String, String> result = null;
		try {
		result = new HashMap<String,String>();
		GatewayInfo paypalGatewayInfo = new GatewayInfo();
		paypalGatewayInfo.setId(RazorConfig.getValue(TripvillasUtils.PAYPAL_CLIENTID));
		paypalGatewayInfo.setCreditCartd(creditCard);
		paypalGatewayInfo.setToken(RazorConfig.getValue(TripvillasUtils.PAYPAL_SECRET));
		
		result = new PaypalHandlerNew(paypalGatewayInfo).createPaymentByCreditCard(sqlSession, reservation.getCurrency(), reservation.getCost(), reservation.getId());
		} catch (Exception e) {
			LOG.error("Error while doing payment... " + e);
			e.printStackTrace();
		}
		if(result.get("state").equalsIgnoreCase("accepted")){
			createReservation(sqlSession, reservation);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#inquireReservation(org.apache.ibatis.
	 * session.SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		if (reservation.getAltid() != null) {
			try {
				String productId = null;
				String roomId = null;
				Product product = ProductService.getInstance().getProduct(sqlSession, reservation.getProductid());
				if(product != null){
					if(product.getOptions() != null && !product.getOptions().isEmpty()){
						if(product.getOptions().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
							Product prod = ProductService.getInstance().getProduct(sqlSession, product.getPartofid());
							productId = prod.getAltid();
							roomId = product.getAltid();
						} else {
							productId =  reservation.getAltid();
							List<String> roomIds = ProductService.getInstance().getRoomsForProduct(sqlSession, product.getId());
							if(roomIds != null && !roomIds.isEmpty()){
								roomId =  roomIds.get(0);
							}
						}
					}
				}
				String requestUrl = RazorConfig
						.getValue(TripvillasUtils.TRIPVILLAS_ENQUIRY_URL);
				Set<KeyValuePair> keyValueSet = new HashSet<KeyValuePair>();
				keyValueSet.add(new KeyValuePair(TripvillasUtils.ADULTS_NUM,
						reservation.getAdult()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.KIDS_NUM,
						reservation.getChild()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.CHECK_IN,
						TripvillasUtils.YYYY_MM_DD_TIME.format(reservation.getFromdate())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.CHECK_OUT,
						TripvillasUtils.YYYY_MM_DD_TIME.format(reservation.getTodate())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.GUEST_EMAIL,
						reservation.getEmailaddress()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.FIRST_NAME,
						reservation.getFirstname()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.LAST_NAME,
						reservation.getFamilyname()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.MOBILE_COUNTRY_CODE,
						ProductService.getInstance().getPhoneCode(sqlSession, reservation.getCountry())));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.MOBILE_NO,
						reservation.getPhoneNumber()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.MESSAGE,
						reservation.getMessage()));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.PROPERTY, productId));
				keyValueSet.add(new KeyValuePair(TripvillasUtils.ROOM_ID, roomId));
				String urlParameters = TripvillasUtils
						.formJsonString(keyValueSet);
				StringReader jsonReader = TripvillasUtils.readDataFromUrlPost(
						requestUrl, urlParameters, RazorConfig.getValue(TripvillasUtils.AUTHORIZATION));
				
				BufferedReader rd = new BufferedReader(jsonReader);
				StringBuilder result = new StringBuilder();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				LOG.info("Result " + result);
			} catch (Exception e) {
				LOG.error("Error while inquiring reservation... " + e);
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#confirmReservation(org.apache.ibatis.
	 * session.SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#readReservation(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#updateReservation(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#cancelReservation(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readAlerts()
	 */
	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readPrices()
	 */
	@Override
	public void readPrices() {
		String[] filesToDownload = { TripvillasUtils.getPriceXMLFileName(),
				TripvillasUtils.getVacancyXMLFileName() };
		if (downloadFlag) {
			for (String fileName : filesToDownload) {
				downloadZipFileFromServer(
						RazorConfig
								.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_SERVER),
						Integer.parseInt(RazorConfig
								.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PORT)),
						RazorConfig
								.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_USER),
						RazorConfig
								.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PWD),
						fileName,
						TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH);
				this.unzipFile(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
						fileName, TripvillasUtils.TAR_EXTENSION);
				this.unzipTarFile(
						TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
						fileName + TripvillasUtils.TAR_EXTENSION,
						TripvillasUtils.XML_EXTENSION);
			}
		}
		Vacancies vacancies = new Vacancies();
		vacancies = TripvillasUtils.unmarshallFile(vacancies, Vacancies.class,
				new File(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getVacancyXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION));
		persistReservation(vacancies);
		Multimap<String, Date> availableDatesWithPropertyId = getAvailableDatesWithPropertyId(vacancies);
		Prices prices = new Prices();
		prices = TripvillasUtils.unmarshallFile(prices, Prices.class, new File(
				TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getPriceXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION));
		if (prices.getPrice() != null && !prices.getPrice().isEmpty()) {

			Product product = null;
			net.cbtltd.shared.Price priceCreate = null;
			SqlSession sqlSession = RazorServer.openSession();
			Map<String, net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room> priceListWithPropertyId = new HashMap<String, net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room>();
			try {
				/**
				 * form the map with property id as key and price object as
				 * value so that in future we can get this price with out more
				 * iteration
				 */
				getPriceListWithPropertyID(prices, priceListWithPropertyId);
				

				/**
				 * Persist price table based on the available dates for the
				 * property
				 */
				for (final String key : availableDatesWithPropertyId.keySet()) {
					List<net.cbtltd.shared.Price> priceCreates = new ArrayList<net.cbtltd.shared.Price>();
					try {
						product = PartnerService
								.getProductWithoutInsert(
										sqlSession,
										new NameId(getAltpartyid(), String
												.valueOf(key)));
						if (product != null) {
							Collection<Date> availableDates = availableDatesWithPropertyId
									.get(key);
							for (Date availDate : availableDates) {
								priceCreate = new net.cbtltd.shared.Price();
								priceCreate.setAltid(key);
								priceCreate.setAltpartyid(getAltpartyid());
								priceCreate.setType(NameId.Type.Reservation.name());
								priceCreate.setOrganizationid(getAltpartyid());
								priceCreate.setAvailable(1);
								priceCreate.setFactor(1.0);
								priceCreate
										.setState(net.cbtltd.shared.Price.CREATED);
								priceCreate.setCurrency(TripvillasUtils.CURRENCY);
								priceCreate.setName("Daily Rates");
								priceCreate.setUnit(Unit.DAY);
								priceCreate.setDate(availDate);
								priceCreate.setTodate(availDate);
								priceCreate.setRule(TripvillasUtils
										.getDayOfWeek(availDate));
								if (priceListWithPropertyId.containsKey(key)) {
									double cost = pickPriceFromList(availDate,
											priceListWithPropertyId.get(key));
									priceCreate.setCost(cost);
									priceCreate.setValue(cost);
								} else {
									priceCreate.setCost(0.0);
									priceCreate.setValue(0.0);
								}
								priceCreate.setEntitytype(NameId.Type.Product.name());
								priceCreate.setEntityid(product.getId());
								priceCreate.setPartyid(product.getAltpartyid());
								priceCreates.add(priceCreate);
								
							}
							PriceService.getInstance().persistPriceData(sqlSession, priceCreates);
						}
						sqlSession.commit();
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						sqlSession.rollback();
					}
				}
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}

	}

	/**
	 * @param prices
	 * @param priceListWithPropertyId
	 * 
	 *            form the map with property id as key and price object as value
	 *            so that in future we can get this price with out more
	 *            iteration
	 */
	public void getPriceListWithPropertyID(Prices prices,
			Map<String, net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room> priceListWithPropertyId) {
		for (Price price : prices.getPrice()) {
			if(price.getPropertyType() != null && !price.getPropertyType().isEmpty()){
				if(price.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
					priceListWithPropertyId.put(String.valueOf(price.getPropertyID()),(price.getRooms() != null && price.getRooms().getRoom() != null && !price.getRooms().getRoom().isEmpty()) ? price.getRooms().getRoom().get(0) : null);
				} else if(price.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
					if(price.getRooms() != null && price.getRooms().getRoom() != null && !price.getRooms().getRoom().isEmpty()){
						for(net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room room : price.getRooms().getRoom()){
							priceListWithPropertyId.put(room.getRoomID(), room);
						}
					}
				}
			}

		}
	}

	/**
	 * @param availDate
	 * @param price
	 * @return cost
	 * 
	 *         based on the following decision if the availDate falls between
	 *         the start and end dates of special rate then return the special
	 *         rate else if the availDate falls under weekend then return
	 *         weekend rate or else return dailyPrice
	 */
	public Double pickPriceFromList(Date availDate, net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices.Price.Rooms.Room room) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(availDate);
		for (SpecialRate specialRate : room.getSpecialRates()
				.getSpecialRate()) {
			if ((specialRate.getStartDay().toGregorianCalendar().getTime()
					.before(availDate) && specialRate.getEndDay()
					.toGregorianCalendar().getTime().after(availDate))
					|| specialRate.getStartDay().toGregorianCalendar()
					.getTime().equals(availDate)
					|| specialRate.getEndDay().toGregorianCalendar()
					.getTime().equals(availDate)) {
				return specialRate.getRate();

			}
		}
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return room.getWeekendRates() == 0.0 ? room.getDailyPrice()
					: room.getWeekendRates();
		}
		return room.getDailyPrice();
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readProducts()
	 */
	@Override
	public void readProducts() {
		if (downloadFlag) {
			downloadZipFileFromServer(
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_SERVER),
					Integer.parseInt(RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PORT)),
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_USER),
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PWD),
					TripvillasUtils.getAccomodationXMLFileName(),
					TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH);
			this.unzipFile(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
					TripvillasUtils.getAccomodationXMLFileName(),
					TripvillasUtils.TAR_EXTENSION);
			this.unzipTarFile(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
					TripvillasUtils.getAccomodationXMLFileName()
							+ TripvillasUtils.TAR_EXTENSION,
					TripvillasUtils.XML_EXTENSION);
		}
		Accommodations accommodations = new Accommodations();
		accommodations = TripvillasUtils.unmarshallFile(accommodations,
				Accommodations.class, new File(
						TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
								+ TripvillasUtils.getAccomodationXMLFileName()
								+ TripvillasUtils.TAR_EXTENSION
								+ TripvillasUtils.XML_EXTENSION));
		this.saveProductFromAccommodation(accommodations);
	}

	/**
	 * @param accommodations
	 * 
	 *            form product information from accommodation and persist those
	 *            details in product table
	 */
	private void saveProductFromAccommodation(Accommodations accommodations) {
		Product product = null;
		Product xmlProduct = null;
		Multimap<String, String> attributes = null;
		Map<String, String> mapImages = null;
		SqlSession sqlSession = RazorServer.openSession();
		int persistedPropCount = 0;
		LOG.info("Total number of properties available are "
				+ accommodations.getAccommodation().size());
		try {
			for (Accommodation accommodation : accommodations
					.getAccommodation()) {
				try {
					if (accommodation != null) {
						LOG.info("Processing for the accommodation with property id : "
								+ accommodation.getPropertyID());
						xmlProduct = new Product();
						attributes = LinkedListMultimap.create();
						mapImages = new HashMap<String, String>();

						// build product
						buildProductDetails(xmlProduct, accommodation);

						// build attributes
						buildProductAttributes(xmlProduct, attributes,
								accommodation);

						// location
						buildLocationDetails(xmlProduct, sqlSession,
								accommodation);
						
						//persist Breakage Deposit
						if(accommodation.getOnsiteFees() != null && !accommodation.getOnsiteFees().isEmpty()){
							Map<String, String> extraCost = getExtraCost(accommodation.getOnsiteFees());
							if(extraCost != null && !extraCost.isEmpty()){
								for(Entry<String, String> entry : extraCost.entrySet()){
									if(entry.getKey().toLowerCase().contains(TripvillasUtils.BREAKAGE_DEPOSIT.toLowerCase())){
										xmlProduct.setSecuritydeposit(Double.parseDouble(entry.getValue()));
									} 
								}
							}
						}
						
						String productId = null; // for extra cost

						// persist product
						if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){
							if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
								int bathroomCount = xmlProduct.getBathroom();
								xmlProduct.setBathroom(0);
								xmlProduct.setPerson(0);
								xmlProduct.setRoom(0);
								product = persistProduct(sqlSession, xmlProduct);
								productId = product.getId();
								xmlProduct.setPartofid(product.getId());
								if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
									for(Room room : accommodation.getRooms().getRoom()){
										xmlProduct.setAltid(room.getRoomID());
										xmlProduct.setBathroom(bathroomCount);
										xmlProduct.setRoom(1);
										xmlProduct.setPerson(room.getMaxSleep());
										product = persistProduct(sqlSession, xmlProduct);
									}
								}
							}else if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
								if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
									xmlProduct.setRoom(accommodation.getRooms().getRoom().size());
								}
								product = persistProduct(sqlSession, xmlProduct);
								productId = product.getId();
							}
						}
						
						//persist extra cost
						
						if(accommodation.getOnsiteFees() != null && !accommodation.getOnsiteFees().isEmpty()){
							Map<String, String> extraCost = getExtraCost(accommodation.getOnsiteFees());
							if(extraCost != null && !extraCost.isEmpty()){
								for(Entry<String, String> entry : extraCost.entrySet()){
									if(!entry.getKey().toLowerCase().contains(TripvillasUtils.BREAKAGE_DEPOSIT.toLowerCase())){
										net.cbtltd.shared.Price priceCreate = new net.cbtltd.shared.Price();
										priceCreate.setAltpartyid(getAltpartyid());
										priceCreate.setType("FEE");
										priceCreate.setOrganizationid(getAltpartyid());
										priceCreate.setAvailable(1);
										priceCreate.setFactor(1.0);
										priceCreate
										.setState(net.cbtltd.shared.Price.CREATED);
										priceCreate.setCurrency(TripvillasUtils.CURRENCY);
										priceCreate.setName(entry.getKey());
										priceCreate.setCost(Double.parseDouble(entry.getValue()));
										priceCreate.setEntitytype(NameId.Type.Product.name());
										priceCreate.setEntityid(productId);
										priceCreate.setPartyid(product.getAltpartyid());
										PriceService.getInstance().persistPriceData(sqlSession, priceCreate);
									}
								}
							}
						}
						
						//persist room details
						if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
							List<NameId> roomIds = new ArrayList<NameId>();
							if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){

								Product prod = null;
								for(Room room : accommodation.getRooms().getRoom()){
									if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
										prod = ProductService.getInstance().getProduct(sqlSession, getAltpartyid(), room.getRoomID());
										roomIds.add(new NameId(room.getRoomID(),prod.getId()));
									}else {
										roomIds.add(new NameId(room.getRoomID(),product.getId()));
									}
								}
								if(!roomIds.isEmpty()){
									ProductService.getInstance().persistRooms(sqlSession, roomIds);
								}
							}
						}
						
						// persist attributes
						persistAttributes(sqlSession, attributes);
						
						// persist description
						if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){
							if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
								Product prod = null;
								if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
									for(Room room : accommodation.getRooms().getRoom()){
										prod = ProductService.getInstance().getProduct(sqlSession, getAltpartyid(), room.getRoomID());
										TextService.createDescription(sqlSession, new Text(prod.getPublicId(), "product", Text.Type.Text, new Date(), accommodation.getDescription(), "EN"));
									}
								}
							}else if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
								TextService.createDescription(sqlSession, new Text(product.getPublicId(), "product", Text.Type.Text, new Date(), accommodation.getDescription(), "EN"));
							}
						}

						// persist minimum stay
						if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){
							if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
								Product prod = null;
								if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
									for(Room room : accommodation.getRooms().getRoom()){
										prod = ProductService.getInstance().getProduct(sqlSession, getAltpartyid(), room.getRoomID());
										persistMinStay(sqlSession, prod, accommodation.getMinimumStay());
									}
								}
							}else if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
								persistMinStay(sqlSession, product, accommodation.getMinimumStay());
							}
						}

						// images
						if (accommodation.getImages() != null) {
							buildImageData(mapImages, accommodation);
							if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){
								if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
									Product prod = null;
									if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
										for(Room room : accommodation.getRooms().getRoom()){
											prod = ProductService.getInstance().getProduct(sqlSession, getAltpartyid(), room.getRoomID());
											persistImages(prod, mapImages, sqlSession);
										}
									}
								}else if(accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
									persistImages(product, mapImages, sqlSession);
								}
							}
							
						}
					}
					sqlSession.commit();
					persistedPropCount++;
					LOG.info("The accommodation with property id "
							+ accommodation.getPropertyID()
							+ " got persisted...");
				} catch (Exception e) {
					LOG.error("Error while persisting product ", e);
				}
			}
			LOG.info("Total number of properties persisted in DB are "
					+ persistedPropCount);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}

	}
	
	
	/**
	 * @param text
	 * @return map contains extra cost details
	 */
	private Map<String, String> getExtraCost(String text){
		String[] textSplit=text.split(",");
		Map<String,String> items = null;
		Map<String,String> itemPrice = null;
		String current = null;
		if(textSplit.length > 0){
			items = new LinkedHashMap<String,String>();
			for (String string : textSplit) {
				int index=string.indexOf(':');
				if(index>0){
					current=string.substring(0, index-1);
					items.put(
							current,
							string.substring(index+1));
				}
				else if(current!=null) items.put(current, items.get(current)+", "+string);
			}
			Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d{1,2})?)");
			if(!items.isEmpty()){  
				itemPrice = new LinkedHashMap<String,String>();
				for(Entry<String, String> entry : items.entrySet()){
					Matcher matcher = pattern.matcher(entry.getValue());
					if (matcher.find()) {
						itemPrice.put(entry.getKey(), matcher.group());
					}
				}
			}
		}
		return itemPrice;
	}

	/**
	 * @param product
	 * @param mapImages
	 * @param sqlSession
	 */
	private void persistImages(Product product, Map<String, String> mapImages,
			SqlSession sqlSession) {
		List<net.cbtltd.shared.Image> existingImages = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(product.getId()));
		List<String> imageUrls = new ArrayList<String>();
		for (net.cbtltd.shared.Image image : existingImages) {
			imageUrls.add(image.getUrl());
		}
		for (Entry<String, String> entryProductDesc : mapImages
				.entrySet()) {
			if(imageUrls.contains(entryProductDesc.getValue())) continue;
			ImageService.persistImage(entryProductDesc.getKey(), Integer.parseInt(product.getId()), "EN", "", net.cbtltd.shared.Image.Type.Linked, entryProductDesc.getValue(), true, false, sqlSession);
		}
	}

	/**
	 * @param mapImages
	 * @param accommodation
	 * 
	 *            builds image data for a property
	 */
	public void buildImageData(Map<String, String> mapImages,
			Accommodation accommodation) {
		List<Image> images = accommodation.getImages().getImage();
		for (Image image : images) {
			String title = image.getTitle();
			if (title == null || title.trim().isEmpty()) {
				int index=image.getUri().lastIndexOf('/');
				if( index>0 && image.getUri().indexOf('.',index)>0){
				title = image.getUri().substring(
						image.getUri().lastIndexOf('/') + 1,
						image.getUri().lastIndexOf('.'));
				} else {
					title = image.getUri().substring(
							image.getUri().lastIndexOf('/') + 1);
				}
			}
			mapImages.put(title, image.getUri());
		}
	}

	/**
	 * @param xmlProduct
	 * @param sqlSession
	 * @param accommodation
	 * 
	 *            gathers location details for a property
	 */
	public void buildLocationDetails(Product xmlProduct, SqlSession sqlSession,
			Accommodation accommodation) {
		try {
			Location location = null;
			String zipCode = null;
			if (accommodation.getLocation() != null
					&& accommodation.getLocation().getAddress() != null) {
				zipCode = TripvillasUtils.removeSpecialCharacters(String
						.valueOf(accommodation.getLocation().getAddress()
								.getPostalCode()));
				location = PartnerService.getLocation(sqlSession, zipCode,
						accommodation.getLocation().getAddress().getCountry(),
						xmlProduct.getLatitude(), xmlProduct.getLongitude());
			}

			if (location != null) {
				xmlProduct.setLocationid(location.getId());
			}
		} catch (Throwable t) {
			LOG.error("Error while doing location lookup ", t);
		}
	}

	/**
	 * @param xmlProduct
	 * @param attributes
	 * @param accommodation
	 * 
	 *            builds product attributes like amenities
	 */
	public void buildProductAttributes(Product xmlProduct,
			Multimap<String, String> attributes, Accommodation accommodation) {
		Set<String> attrs = new HashSet<String>();
		if (accommodation.getPropertyType() != null) {
			attrs.add(TripvillasUtils.getATTRIBUTES().get(
					accommodation.getPropertyType().toLowerCase()));
		}
		if (accommodation.getAmenities() != null) {
			List<String> amenities = accommodation.getAmenities()
					.getAmenityName();
			for (String amenity : amenities) {
				attrs.add(TripvillasUtils.getATTRIBUTES().get(amenity.toLowerCase()));
			}
		}
		if (accommodation.getActivities() != null) {
			List<String> activities = accommodation.getActivities()
					.getActivityName();
			for (String activity : activities) {
				attrs.add(TripvillasUtils.getATTRIBUTES().get(activity.toLowerCase()));
			}
		}
		if (accommodation.getRooms() != null) {
			int personCount = 0;
			List<Room> rooms = accommodation.getRooms().getRoom();
			for (Room room : rooms) {
				if (room != null) {
					if (room.getAmenities() != null
							&& room.getAmenities().getAmenity() != null) {
						if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty() && accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
							Set<String> attribs = new HashSet<String>(attrs);
							attribs.add(TripvillasUtils.getATTRIBUTES().get(room.getAmenities().getAmenity().getBedroomFeatureName().toLowerCase()));
							attribs.add("PCT15"); //Guest farm - Property Class Type (PCT15)
							attributes.putAll(room.getRoomID(), attribs);
						}else {
						attrs.add(TripvillasUtils.getATTRIBUTES().get(room.getAmenities().getAmenity().getBedroomFeatureName().toLowerCase()));
						}
					}
					personCount += room.getMaxSleep();
				}
			}
			if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty() && accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
				xmlProduct.setPerson(personCount);
				attrs.add("PCT3"); //Apartment - Property Class Type (PCT3)
				attributes.putAll(String.valueOf(accommodation.getPropertyID()), attrs);
			}
		}
	}

	/**
	 * @param xmlProduct
	 * @param accommodation
	 * 
	 *            builds productdetails from accommodation
	 */
	public void buildProductDetails(Product xmlProduct,
			Accommodation accommodation) {
		if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty()){
			xmlProduct.setAltid(String.valueOf(accommodation.getPropertyID()));
			xmlProduct.setOptions(accommodation.getPropertyType());
			xmlProduct.setName(accommodation.getHeadline());
			xmlProduct.setBathroom(accommodation.getBathroomCount());
			xmlProduct.setWebaddress(accommodation.getUrl());
			xmlProduct.setLatitude(accommodation.getLocation().getGeoCode()
					.getLatitude());
			xmlProduct.setLongitude(accommodation.getLocation().getGeoCode()
					.getLongitude());
			xmlProduct.setCurrency(TripvillasUtils.CURRENCY);
			xmlProduct.setPhysicaladdress(buildAddress(accommodation.getLocation()
					.getAddress()));
		}
	}

	private String buildAddress(Address address) {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(address.getAddressLine1())) {
			builder.append(address.getAddressLine1()).append(", ");
		}
		if (StringUtils.isNotEmpty(address.getCity())) {
			builder.append(address.getCity()).append(", ");
		}
		if (StringUtils.isNotEmpty(address.getStateOrProvince())) {
			builder.append(address.getStateOrProvince()).append(" ");
		}
		if (StringUtils.isNotEmpty(address.getCountry())) {
			builder.append(address.getCountry()).append(" ");
		}
		builder.append(address.getPostalCode());
		return builder.toString();
	}

	/**
	 * @param sqlSession
	 * @param product
	 * @param minStay
	 * 
	 *            this will persist the minimum stay property in the table
	 */
	private void persistMinStay(SqlSession sqlSession, Product product,
			Integer minStay) {
		LOG.info("Updating the minstay of Product " + product.getName());
		// update the minStay of product
		ProductService.getInstance().createMinStay(sqlSession, minStay, product, new Date(),
				new Date(), new Date());
	}

	/**
	 * @param sqlSession
	 * @param xmlProduct
	 * @return product
	 * 
	 *         if the product is already there in the table then it will simply
	 *         update or else it will persist the product
	 */
	private Product persistProduct(SqlSession sqlSession, Product xmlProduct) {
		LOG.info("Check whether Product data exists for data "
				+ xmlProduct.getName() + " ID " + xmlProduct.getAltid());
		// the name can be common with other existing products....also
		// pass in supplier id - tripvillas' partner id
		// PartnerService.getProduct(sqlSession, getAltpartyid(),
		// altid);
		xmlProduct.setName((xmlProduct.getName().length() > 100) ? xmlProduct
				.getName().substring(0, 99) : xmlProduct.getName());
		String altPartyId = getAltpartyid();
		xmlProduct.setAltpartyid(altPartyId);

		xmlProduct.setState(Product.CREATED);
		xmlProduct.setType(Product.Type.Accommodation.name());
		xmlProduct.setUnit(Unit.DAY);
		xmlProduct.setOrganizationid(altPartyId);
		xmlProduct.setAltSupplierId(altPartyId);
		xmlProduct.setSupplierid(altPartyId);
		xmlProduct.setOwnerid(altPartyId);
		xmlProduct.setQuantity(1);
		xmlProduct.setInquireState(Product.USE_API);
		
		// Fixed modified lookup as alt id and altpartyid
		Product product = PartnerService.getProductWithoutInsert(sqlSession,
				new NameId(xmlProduct.getAltpartyid(), xmlProduct.getAltid()));
		xmlProduct.setVersion(new Date());
		if (product == null) {
			LOG.info("Product with " + xmlProduct.getName()
					+ " does not exists, so creating the product entry");
			// if a particular product doesn't not exists create the product
			// after creation retrieve the product once again so that we
			// can insert its attributes in relation table.
			product = PartnerService.createProduct(sqlSession, xmlProduct);
			return product;
		} else {
			xmlProduct.setId(product.getId());
			PartnerService.updateProduct(sqlSession, xmlProduct);
			return xmlProduct;
		}
	}

	/**
	 * @param sqlSession
	 * @param attributes
	 * 
	 *            this will persist all the attributes like amenities in
	 *            database
	 */
	private void persistAttributes(SqlSession sqlSession, Multimap<String, String> attributes) {
		Product product = null;
		for (final String key : attributes.keySet()) {
			List<String> attribs = new LinkedList<String>(attributes.get(key));
			attribs.removeAll(Collections.singleton(null));
			product = ProductService.getInstance().getProduct(sqlSession, getAltpartyid(), key);
			RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE,
					product.getId(), attribs );
		}
	}

	/**
	 * @param server
	 * @param port
	 * @param user
	 * @param pwd
	 * @param fileToDownload
	 * @param storagePath
	 * @return success/failure
	 * 
	 *         this method will download the file from server and returns the
	 *         status
	 */
	private boolean downloadZipFileFromServer(String server, int port,
			String user, String pwd, String fileToDownload, String storagePath) {
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pwd);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.COMPRESSED_TRANSFER_MODE);

			File downloadFile = new File(storagePath + fileToDownload);
			OutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(downloadFile));
			boolean success = ftpClient.retrieveFile(fileToDownload,
					outputStream);
			outputStream.close();
			return success;
		} catch (IOException ex) {
			LOG.error(ex.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				LOG.error(ex.getMessage());
			}
		}
		return false;
	}

	/**
	 * @param filePath
	 * @param fileName
	 * @param extension
	 * 
	 *            this is used to unzip gz files
	 */
	public void unzipFile(String filePath, String fileName, String extension) {
		try {
			File inputFile = new File(filePath + fileName);
			FileInputStream fis = new FileInputStream(inputFile);

			GZIPInputStream gzis = new GZIPInputStream(fis);
			File outputFile = new File(filePath + fileName + extension);
			FileOutputStream fos = new FileOutputStream(outputFile);
			byte[] buf = new byte[1024];
			int len = 0;

			while (gzis.available() == 1 && (len = gzis.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			gzis.close();
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			LOG.error("File " + filePath + fileName + " not exists ...", e);
		} catch (IOException e) {
			LOG.error("Error while reading file " + filePath + fileName, e);
		}

	}

	/**
	 * @param filePath
	 * @param fileName
	 * @param extension
	 * 
	 *            this is used to unzip tar files
	 */
	@SuppressWarnings("unused")
	public void unzipTarFile(String filePath, String fileName, String extension) {
		try {
			File tarFile = new File(filePath + fileName);
			// Create a TarInputStream
			TarInputStream tis = new TarInputStream(new BufferedInputStream(
					new FileInputStream(tarFile)));
			TarEntry entry;
			File outputFile = new File(filePath + fileName + extension);
			while ((entry = tis.getNextEntry()) != null) {
				int count;
				byte data[] = new byte[2048];
				BufferedOutputStream dest = new BufferedOutputStream(
						new FileOutputStream(outputFile));
				while ((count = tis.read(data)) != -1) {
					dest.write(data, 0, count);
				}

				dest.flush();
				dest.close();
			}

			tis.close();
		} catch (FileNotFoundException e) {
			LOG.error("File " + filePath + fileName + " not exists ...", e);
		} catch (IOException e) {
			LOG.error("Error while reading file " + filePath + fileName, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readSchedule()
	 */
	@Override
	public void readSchedule() {
		if (downloadFlag) {
			downloadZipFileFromServer(
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_SERVER),
					Integer.parseInt(RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PORT)),
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_USER),
					RazorConfig
							.getValue(TripvillasUtils.TRIPVILLAS_XML_FEED_PWD),
					TripvillasUtils.getVacancyXMLFileName(),
					TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH);
			this.unzipFile(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
					TripvillasUtils.getVacancyXMLFileName(),
					TripvillasUtils.TAR_EXTENSION);
			this.unzipTarFile(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH,
					TripvillasUtils.getVacancyXMLFileName()
							+ TripvillasUtils.TAR_EXTENSION,
					TripvillasUtils.XML_EXTENSION);
		}
		Vacancies vacancies = new Vacancies();
		vacancies = TripvillasUtils.unmarshallFile(vacancies, Vacancies.class,
				new File(TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getVacancyXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION));
		persistReservation(vacancies);

	}

	/**
	 * @param vacancies
	 * 
	 *            this method is used to find the reserved dates of the property
	 *            and store those reserved properties in database
	 */
	public void persistReservation(Vacancies vacancies) {
		SqlSession sqlSession = RazorServer.openSession();
		Product product = null;
		try {
			Multimap<String, Date> bookedDatesWithPropertyId = getBookedDatesWithPropertyId(vacancies);
			for (final String key : bookedDatesWithPropertyId.keySet()) {
				try {
					product = PartnerService
							.getProductWithoutInsert(
									sqlSession,
									new NameId(getAltpartyid(), String
											.valueOf(key)));
					if (product != null) {
						persistBookedDate(sqlSession, product,
								new ArrayList<Date>(bookedDatesWithPropertyId.get(key)));
					}
					sqlSession.commit();
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					sqlSession.rollback();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	
	
	/**
	 * @param vacancies
	 * @return map that contains booked dates with property id
	 * 
	 *         this will find the booked dates for the property and store
	 *         those details in a map
	 */
	public Multimap<String, Date> getBookedDatesWithPropertyId(
			Vacancies vacancies) {
		Multimap<String, Date> bookedDatesWithPropertyId = ArrayListMultimap
				.create();
		SqlSession sqlSession = RazorServer.openSession();
		Product product = null;
		GregorianCalendar startDate = null;
		try {
			if (vacancies.getVacancy() != null && !vacancies.getVacancy().isEmpty()) {
				for (Vacancy vacancy : vacancies.getVacancy()) {
					String propertyType = null;
					try {
						if (vacancy.getRooms() != null) {
							if (vacancy.getRooms().getAvailability() != null
									&& !vacancy.getRooms().getAvailability().isEmpty()) {
								product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), String.valueOf(vacancy.getPropertyID())));
								if (product != null && product.getOptions().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)) {
									propertyType = TripvillasUtils.INDEPENDENT_STAY;
								}else {
									propertyType = TripvillasUtils.OWNERS_GUEST;
								}
								/*if((propertyType == null || propertyType.isEmpty()) && vacancy.getRooms().getAvailability().size() >= 1){
									if (vacancy.getRooms().getRoomID() != null && !vacancy.getRooms().getRoomID().isEmpty()) {
										product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), vacancy.getRooms().getRoomID().get(0)));
									}
									if (product != null) {
										propertyType = TripvillasUtils.OWNERS_GUEST;
									}
								}*/
								if(propertyType != null && !propertyType.isEmpty()){
									if(propertyType.equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
										String availability = vacancy.getRooms().getAvailability().get(0);
										startDate = vacancy.getStartday().toGregorianCalendar();
										for (int index = 0; index < availability.length(); index++, startDate.add(Calendar.DAY_OF_MONTH, 1)) {
											if ("N".equalsIgnoreCase(""+ availability.charAt(index))) {
												bookedDatesWithPropertyId.put(String.valueOf(vacancy.getPropertyID()), startDate.getTime());
											}
										}
									} else if(propertyType.equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
										if (vacancy.getRooms().getRoomID() != null && !vacancy.getRooms().getRoomID().isEmpty() && (vacancy.getRooms().getRoomID().size() == vacancy.getRooms().getAvailability().size())) {
											for(int position = 0; position < vacancy.getRooms().getRoomID().size(); position++){
												product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), vacancy.getRooms().getRoomID().get(position)));
												String availability = vacancy.getRooms().getAvailability().get(position);
												startDate = vacancy.getStartday().toGregorianCalendar();
												for (int index = 0; index < availability.length(); index++, startDate.add(Calendar.DAY_OF_MONTH, 1)) {
													if ("N".equalsIgnoreCase("" + availability.charAt(index))) {
														bookedDatesWithPropertyId.put(vacancy.getRooms().getRoomID().get(position), startDate.getTime());
													}
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						sqlSession.rollback();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return bookedDatesWithPropertyId;
	}
	
	

	/**
	 * @param sqlSession
	 * @param product
	 * @param listBookedDate
	 * 
	 *            if the date for that particular property already exists then
	 *            remove that entry from database and make a new entry
	 */
	private void persistBookedDate(SqlSession sqlSession, Product product,
			List<Date> listBookedDate) {
		List<Date> reservationList = PartnerService.getReservationList(
				sqlSession, product.getId());

		for (Date bkDate : listBookedDate) {
			if (!reservationList.contains(bkDate)) {
				PartnerService.createSchedule(sqlSession, product, bkDate,
						bkDate, new Date());
			}
		}
		for (Date cdDate : reservationList) {
			if (!listBookedDate.contains(cdDate)) {
				PartnerService.deleteReservation(sqlSession, product.getId(),
						cdDate);
			}
		}
	}

	/**
	 * @param vacancies
	 * @return map that contains available dates with property id
	 * 
	 *         this will find the available dates for the property and store
	 *         those details in a map
	 */
	public Multimap<String, Date> getAvailableDatesWithPropertyId(
			Vacancies vacancies) {
		Multimap<String, Date> availableDatesWithPropertyId = ArrayListMultimap
				.create();
		SqlSession sqlSession = RazorServer.openSession();
		Product product = null;
		GregorianCalendar startDate = null;
		try {
			if (vacancies.getVacancy() != null && !vacancies.getVacancy().isEmpty()) {
				for (Vacancy vacancy : vacancies.getVacancy()) {
					String propertyType = null;
					try {
						if (vacancy.getRooms() != null) {
							if (vacancy.getRooms().getAvailability() != null
									&& !vacancy.getRooms().getAvailability().isEmpty()) {
								product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), String.valueOf(vacancy.getPropertyID())));
								if (product != null && product.getOptions().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)) {
									propertyType = TripvillasUtils.INDEPENDENT_STAY;
								}else {
									propertyType = TripvillasUtils.OWNERS_GUEST;
								}
								/*if((propertyType == null || propertyType.isEmpty()) && vacancy.getRooms().getAvailability().size() >= 1){
									if (vacancy.getRooms().getRoomID() != null && !vacancy.getRooms().getRoomID().isEmpty()) {
										product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), vacancy.getRooms().getRoomID().get(0)));
									}
									if (product != null) {
										propertyType = TripvillasUtils.OWNERS_GUEST;
									}
								}*/
								if(propertyType != null && !propertyType.isEmpty()){
									if(propertyType.equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
										String availability = vacancy.getRooms().getAvailability().get(0);
										startDate = vacancy.getStartday().toGregorianCalendar();
										for (int index = 0; index < availability.length(); index++, startDate.add(Calendar.DAY_OF_MONTH, 1)) {
											if ("Y".equalsIgnoreCase(""+ availability.charAt(index))) {
												availableDatesWithPropertyId.put(String.valueOf(vacancy.getPropertyID()), startDate.getTime());
											}
										}
									} else if(propertyType.equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
										if (vacancy.getRooms().getRoomID() != null && !vacancy.getRooms().getRoomID().isEmpty() && (vacancy.getRooms().getRoomID().size() == vacancy.getRooms().getAvailability().size())) {
											for(int position = 0; position < vacancy.getRooms().getRoomID().size(); position++){
												product = PartnerService .getProductWithoutInsert(sqlSession, new NameId(getAltpartyid(), vacancy.getRooms().getRoomID().get(position)));
												String availability = vacancy.getRooms().getAvailability().get(position);
												startDate = vacancy.getStartday().toGregorianCalendar();
												for (int index = 0; index < availability.length(); index++, startDate.add(Calendar.DAY_OF_MONTH, 1)) {
													if ("Y".equalsIgnoreCase("" + availability.charAt(index))) {
														availableDatesWithPropertyId.put(vacancy.getRooms().getRoomID().get(position), startDate.getTime());
													}
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						sqlSession.rollback();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return availableDatesWithPropertyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readSpecials()
	 */
	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readDescriptions()
	 */
	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readImages()
	 */
	@Override
	public void readImages() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readAdditionCosts()
	 */
	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub

	}
	/**
	 * fetch the null location products from DB and load the location details again
	 * 
	 * @param partyid
	 */
	public void reloadNullLocationProducts(String partyid) {
		TripvillasUtils.reloadNullLocationProducts(partyid);
	}

}
