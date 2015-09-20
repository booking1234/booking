/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.00
 */
package net.cbtltd.rest.kigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Currency;
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
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.google.gson.Gson;

/**
 * API handler for Kigo PMS
 * Uses REST and JSON
 * API Key "89eea03e-b659-474f-9fd3-8ab86240651b"
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final Gson GSON = new Gson();
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static HashMap<Integer,String> ACTIVITIES = null;
	private static HashMap<Integer,String> AMENITIES = null;
	private static HashMap<Integer,String> LOCATIONS = null;
	private static HashMap<Integer,String> TYPES = null;
	
	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner ID.
	 */
	public A_Handler (Partner partner) {super(partner);}

	/**
	 * Gets the connection to the Kigo server and executes the specified request.
	 *
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the JSON string returned by the message.
	 * @throws Throwable the exception thrown by the operation.
	 */
	private static final String getConnection(URL url, String rq) throws Throwable {
		String jsonString = "";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
			String userpassword = "campsbayterrace" + ":" + "C8MYS2zUhJq1RO"; //key - Y2FtcHNiYXl0ZXJyYWNlOkM4TVlTMnpVaEpxMVJP
			String encodedAuthorization = enc.encode( userpassword.getBytes() );
			connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

			if (rq != null) {
				connection.setRequestProperty("Accept", "application/json");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {jsonString += line;}
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return jsonString;
	}

	/**
	 * Returns false if there are collisions with the specified reservation.
	 * 
	 * This is not yet implemented in the Kigo API so always returns true.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be tested for collisions.
	 * @return true if there are no collisions, else false.
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			return true;
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
		return true;
	}

	/**
	 * Create the price detail of the specified reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for which price details are to be created.
	 */
	private void createPrices(SqlSession sqlSession, Reservation reservation) throws Throwable {
		String rq = GSON.toJson(getBooking(sqlSession, reservation));
		String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/computePricing"), rq);
		PriceRS priceRS = GSON.fromJson(jsonString, PriceRS.class);

		ArrayList<Price> quotedetail = new ArrayList<Price>();
		reservation.setQuotedetail(quotedetail);

		Price price = new Price();
		quotedetail.add(price);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservation.getId());
		price.setCurrency(priceRS.getCURRENCY());
		price.setDate(DF.parse(priceRS.getRENT_DOWN_PMT_DUE_DATE()));
		price.setValue(priceRS.getRENT_DOWN_PMT_AMOUNT());
		price.setType(Price.RATE);
		sqlSession.getMapper(PriceMapper.class).create(price);

		price = new Price();
		quotedetail.add(price);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservation.getId());
		price.setCurrency(priceRS.getCURRENCY());
		price.setDate(DF.parse(priceRS.getRENT_REM_PMT_DUE_DATE()));
		price.setValue(priceRS.getRENT_REM_PMT_AMOUNT());
		price.setType(Price.RATE);
		sqlSession.getMapper(PriceMapper.class).create(price);

		price = new Price();
		quotedetail.add(price);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservation.getId());
		price.setCurrency(priceRS.getCURRENCY());
		price.setDate(DF.parse(priceRS.getDEPOSIT_DUE_DATE()));
		price.setValue(priceRS.getDEPOSIT_AMOUNT());
		price.setType(Price.DEPOSIT);
		sqlSession.getMapper(PriceMapper.class).create(price);

		price = new Price();
		quotedetail.add(price);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservation.getId());
		price.setCurrency(priceRS.getCURRENCY());
		price.setDate(DF.parse(priceRS.getFEES_DUE_DATE()));
		price.setValue(priceRS.getFEES_AMOUNT() + priceRS.getRENT_LIST_AMOUNT());
		price.setType(Price.MANDATORY);
		sqlSession.getMapper(PriceMapper.class).create(price);

		reservation.setPrice(priceRS.getRENT_AMOUNT());
		reservation.setQuote(priceRS.getRENT_AMOUNT());
		reservation.setExtra(priceRS.getRENT_FEES_AMOUNT() + priceRS.getRENT_LIST_AMOUNT());
	}

	/**
	 * Create the specified reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created in the Kigo PMS.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			String rq = GSON.toJson(getBooking(sqlSession, reservation));
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/createConfirmedReservation"), rq);
			ReservationRS reservationRS = GSON.fromJson(jsonString,ReservationRS.class);
			LOG.debug("rs " + reservationRS);
			if (reservationRS.isOK()) {
				Booking booking = reservationRS.getBooking();
				reservation.setAltpartyid(getAltpartyid());
				reservation.setAltid(String.valueOf(booking.getRES_ID()));
				reservation.setNotes(booking.getRES_COMMENT());
				createPrices(sqlSession, reservation);
			}
			else {
				reservation.setMessage(reservationRS.getMessage());
				reservation.setState(Reservation.State.Cancelled.name());
			}
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read an existing reservation via the Kigo API.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			String rq = GSON.toJson(getBooking(sqlSession, reservation));
			LOG.debug("rq " + rq);
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/readReservation"), rq);
			ReservationRS rs = GSON.fromJson(jsonString,ReservationRS.class);
			LOG.debug("rs " + rs);
			if (rs.isOK()) {
				Booking booking = rs.getBooking();
				String altid = String.valueOf(booking.getPROP_ID());
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (product == null) {throw new ServiceException(Error.product_altid, altid);}
				altid = String.valueOf(booking.getRES_ID());
				reservation = sqlSession.getMapper(ReservationMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (reservation == null) {throw new ServiceException(Error.reservation_altid, altid);}
				reservation.setDate(DF.parse(booking.getRES_CREATE()));
				reservation.setFromdate(DF.parse(booking.getRES_CHECK_IN()));
				reservation.setTodate(DF.parse(booking.getRES_CHECK_OUT()));
				reservation.setNotes(booking.getRES_COMMENT_GUEST() + "\n" + booking.getRES_COMMENT());
				reservation.setAdult(booking.getRES_N_ADULTS());
				reservation.setChild(booking.getRES_N_CHILDREN());
				reservation.setInfant(booking.getRES_N_BABIES());
				createPrices(sqlSession, reservation);
				reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
				if (reservation.hasCollisions()) {throw new ServiceException(Error.reservation_altid, altid);}
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
			}			
			LOG.debug("readReservation " + reservation);
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Update existing reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "updateReservation";
		LOG.debug(message);
		try {
			String rq = GSON.toJson(getBooking(sqlSession, reservation));
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/updateReservation"), rq);
			ReservationRS rs = GSON.fromJson(jsonString,ReservationRS.class);
			LOG.debug("rs " + rs);
		} catch (Throwable x) {LOG.error(message + x.getStackTrace());}
	}

	/**
	 * Confirm provisional reservation.
	 *
	 * This is not yet implemented in the Kigo API so does nothing.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be confirmed.
	 */
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Kigo confirmReservation()");
	}

	/**
	 * Cancel existing reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "cancelReservation";
		LOG.debug(message);
		try {
			String rq = GSON.toJson(getBooking(sqlSession, reservation));
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/cancelReservation"), rq);
			ReservationRS rs = GSON.fromJson(jsonString,ReservationRS.class);
			LOG.debug("rs " + rs);
		}
		catch (Throwable x) {LOG.error(message + x.getStackTrace());}
	}

	/**
	 * Read accommodation alerts.
	 *
	 * Not yet implemented in the Kigo API.
	 * 
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Kigo readAlerts()");
	}

	/**
	 * Read accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		Date timestamp = new Date();
		String message = "readPrices " + getAltpartyid();
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/listProperties"), "null");
			PropertiesRS rs = GSON.fromJson(jsonString, PropertiesRS.class);
			int i = 0;
			for (Property propid : rs.getAPI_REPLY()) {
				String rq = GSON.toJson(new Property (propid.getPROP_ID()));
				jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/readProperty"), rq);
				PropertyRS response = GSON.fromJson(jsonString, PropertyRS.class);
				Property property = response.getAPI_REPLY();
				String altid = String.valueOf(propid.getPROP_ID());
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (product == null) {LOG.error(Error.product_altid + altid);}
				else {
					setPrice(sqlSession, product.getId(), property.getPROP_RATE_NIGHTLY_FROM(), Unit.DAY, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());
					setPrice(sqlSession, product.getId(), property.getPROP_RATE_WEEKLY_FROM(), Unit.WEE, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());
					setPrice(sqlSession, product.getId(), property.getPROP_RATE_MONTHLY_FROM(), Unit.MON, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());
					sqlSession.commit();
					LOG.debug(i++ + " " + altid + " = " + product.getId());
					wait(getPricewait());
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read property locations.
	 *
	 * Not yet implemented in the Kigo API.

	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Kigo readLocations()");
	}

	/**
	 * Read Kigo properties and map to accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date timestamp = new Date();
		String message = "Kigo readProducts " + getAltpartyid();
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/listProperties"), "null");
			LOG.debug("listProperties " + jsonString);
			
			PropertiesRS rs = GSON.fromJson(jsonString, PropertiesRS.class);
			LOG.debug("rs " + rs);

			int i = 0;
			for (Property propid : rs.getAPI_REPLY()) {
				
				String rq = GSON.toJson(new Property (propid.getPROP_ID()));
				jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/readProperty"), rq);
				PropertyRS response = GSON.fromJson(jsonString, PropertyRS.class);
				Property property = response.getAPI_REPLY();
				if (property.noPROP_CITY()) {continue;}

				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), String.valueOf(property.getPROP_ID()));
				if (product == null) {continue;}
				StringBuilder description = new StringBuilder();
				ArrayList<String> attributes = new ArrayList<String>();

				description.append(property.getPROP_SHORTDESCRIPTION()).append("\n");
				description.append(property.getPROP_DESCRIPTION()).append("\n");
				description.append(property.getPROP_AREADESCRIPTION()).append("\n");
				
				product.setCurrency(property.noPROP_RATE_CURRENCY() ? Currency.Code.EUR.name() : property.getPROP_RATE_CURRENCY());
				product.setCommission(getCommission());
				product.setDiscount(getDiscount());

				Location location = PartnerService.getLocation(sqlSession, property.getPROP_CITY(), (property.hasPROP_REGION() ? property.getPROP_REGION() : null), property.getPROP_COUNTRY());
				product.setLocationid(location.getId());
				
				product.setLatitude(property.noPROP_LATITUDE() ? location.getLatitude() : property.getPROP_LATITUDE());
				product.setLongitude(property.noPROP_LONGITUDE() ? location.getLongitude() : property.getPROP_LONGITUDE());

				Party owner = PartnerService.getParty(sqlSession, getAltpartyid(), String.valueOf(property.getPROP_PROVIDER_OWNER_ID()), Party.Type.Owner);
				product.setOwnerid(owner.getId());
				StringBuilder sb = new StringBuilder();
				if (property.hasPROP_STREETNO()) {
					sb.append(property.getPROP_STREETNO());
					if (property.hasPROP_APTNO()) {sb.append("/").append(property.getPROP_APTNO());}					
					}
				else {sb.append(property.getPROP_APTNO());}
				if (property.hasPROP_ADDR1()) {sb.append(",").append(property.getPROP_ADDR1());}
				if (property.hasPROP_ADDR2()) {sb.append(",").append(property.getPROP_ADDR2());}
				if (property.hasPROP_ADDR3()) {sb.append(",").append(property.getPROP_ADDR3());}
				if (property.hasPROP_CITY()) {sb.append(",").append(property.getPROP_CITY());}
				product.setPhysicaladdress(sb.toString());

				product.setRank(getRank());
				product.setRating(5);
				product.setRoom(property.getPROP_BEDROOMS());
				product.setBathroom(property.getPROP_BATHROOMS());
				product.setToilet(property.getPROP_TOILETS());
				product.setQuantity(1);
				product.setPerson(property.getPROP_MAXGUESTS_ADULTS());
				product.setChild(property.getPROP_MAXGUESTS_CHILDREN());
				product.setInfant(property.getPROP_MAXGUESTS_BABIES());
//				product.setState(Product.CREATED);
//				product.setType(Product.Type.Accommodation.name());
				product.setUnit(Unit.DAY); //TODO:
				product.setWebaddress(getWebaddress());
				product.setCleaningfee(Product.DEFAULT_CLEANING_FEE);
				product.setSecuritydeposit(Product.DEFAULT_SECUIRTY_DEPOSIT);

				product.setName(property.getPROP_NAME());

				sqlSession.getMapper(ProductMapper.class).update(product);

				setPrice(sqlSession, product.getId(), property.getPROP_RATE_NIGHTLY_FROM(), Unit.DAY, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());
				setPrice(sqlSession, product.getId(), property.getPROP_RATE_WEEKLY_FROM(), Unit.WEE, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());
				setPrice(sqlSession, product.getId(), property.getPROP_RATE_MONTHLY_FROM(), Unit.MON, property.getPROP_STAY_MIN(), property.getPROP_STAY_MIN_UNIT(), property.getPROP_RATE_CURRENCY());

				if (property.getPROP_ELEVATOR() != null) {product.setBooleanValue(Product.Value.Elevator.name(), property.getPROP_ELEVATOR());}
				if ("SQMETER".equalsIgnoreCase(property.getPROP_SIZE_UNIT())) {product.setIntegerValue(Product.Value.SquareMetre.name(), property.getPROP_SIZE());}
				else {product.setIntegerValue(Product.Value.SquareFeet.name(), property.getPROP_SIZE());}

				addType(attributes, property.getPROP_TYPE_ID());
				addLocation(attributes, property.getPROP_TYPE_ID());
				addActivities(attributes,property.getPROP_ACTIVITIES());
				addAmenities(attributes, property.getPROP_AMENITIES());
				
				product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), description.toString(), Language.EN));
				TextService.update(sqlSession, product.getTexts());
				
				RelationService.create(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);

				Photo[] photos = property.getPROP_PHOTOS();
				if (HasUrls.LIVE && photos != null && photos.length > 0) {
					int index = 0;
					for (Photo photo : photos) {
						rq = GSON.toJson(new Photo (property.getPROP_ID(),photo.getPHOTO_ID()));
						jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/readPropertyPhotoFile"), rq);
						PhotoRS photoRS = GSON.fromJson(jsonString, PhotoRS.class);
						String textname = NameId.Type.Product.name() + product.getId() + "-" + String.format("%03d", index++);
						Text text = sqlSession.getMapper(TextMapper.class).read(textname);
						if (text == null) {
							//when uploading to s3, use following ROOT_DIRECTORY:	String ROOT_DIRECTORY = "/data2/htdocs/";
							String path = RazorServer.ROOT_DIRECTORY + HasUrls.PICTURES_DIRECTORY + getAltpartyid() + "/" + product.getAltSupplierId() + "/" + product.getAltid() + "/";
							new File(path).mkdirs();
							String fn = path + textname + Text.FULLSIZE_JPG; 	//input file path
							byte[] contents = DatatypeConverter.parseBase64Binary(photoRS.getAPI_REPLY());
							FileOutputStream out = new FileOutputStream(fn);
						    out.write(contents);
						    out.close();
							UploadFileService.setText(sqlSession, new File(fn), fn, textname, Text.IMAGE, photo.getPHOTO_COMMENTS(), Language.EN, Text.FULLSIZE_PIXELS_VALUE, Text.THUMBNAIL_PIXELS_VALUE);
						}
					}
				}
				sqlSession.commit();
				LOG.debug(i++ + " " + property.getPROP_ID() + " = " + product.getId());
				wait(getProductwait());
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read Kigo reservation schedule and update calendar.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule ";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
//			String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/listProperties"), "null");
//			PropertiesRS rs = GSON.fromJson(jsonString, PropertiesRS.class);
			String fromdate = DF.format(timestamp);
			String todate = DF.format(Time.addDuration(timestamp, 729, Time.DAY));
			ArrayList<Product> properties = sqlSession.getMapper(ProductMapper.class).altlist(getAltpartyid());//Arrays.asList(rs.getAPI_REPLY());
			for (Product product : properties) {
//				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), String.valueOf(property.getPROP_ID()));
//				if (product == null) {continue;}
				String rq = GSON.toJson(new Calendar (Integer.parseInt(product.getAltid()), fromdate, todate));
				LOG.debug("rq " + rq);
				String jsonString = getConnection(new URL("https://app.kigo.net/api/ra/v1/listPropertyCalendarReservations"), rq); //diffPropertyCalendarReservations
				LOG.debug("listPropertyCalendarReservations " + jsonString);
				CalendarsRS schedule = GSON.fromJson(jsonString, CalendarsRS.class);
				CalendarRS[] calendars = schedule.getAPI_REPLY();
				
				for (CalendarRS calendar : calendars) {
					PartnerService.createSchedule(sqlSession, product, DF.parse(calendar.getRES_CHECK_IN()), DF.parse(calendar.getRES_CHECK_OUT()), timestamp);
				}
				PartnerService.cancelSchedule(sqlSession, product, timestamp);
				sqlSession.commit();
				wait(getSchedulewait());
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read accommodation special offers.
	 *
	 * Not yet implemented in the Kigo API.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Kigo readSpecials()");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Kigo createReservationAndPayment()");
	}

	/**
	 * Gets the Kigo booking for a reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservationid the ID of the reservation
	 * @return the Kigo booking
	 */
	private Booking getBooking(SqlSession sqlSession, Reservation reservation) {
		if (reservation == null) {throw new ServiceException(Error.reservation_id, "Kigo createReservation");}
		if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " " + reservation.getState());}
		Booking booking = new Booking();
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		booking.setPROP_ID(Integer.valueOf(product.getAltid()));
		booking.setRES_CREATE(DF.format(reservation.getDate()));
		booking.setRES_CHECK_IN(DF.format(reservation.getFromdate()));
		booking.setRES_CHECK_OUT(DF.format(reservation.getTodate()));
		booking.setRES_COMMENT_GUEST(reservation.getNotes());
		booking.setRES_COMMENT("Razor Cloud Reservation ID " + reservation.getId());
		booking.setRES_N_ADULTS(reservation.getAdult());
		booking.setRES_N_CHILDREN(reservation.getChild());
		booking.setRES_N_BABIES(reservation.getInfant());
		return booking;
	}

	/**
	 * Creates or updates the default price of a property.
	 * Note that Kigo does not publish a price list so an estimate is made.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param productid the ID of the property
	 * @param value the price value per unit
	 * @param unit the unit of measure of the price
	 * @param minstay the minimum stay
	 * @param minstayunit the unit of measure of the minimum stay
	 * @param currency the ISO currency code of the price
	 * @throws Throwable the exception thrown by the operation.
	 */
	private void setPrice(SqlSession sqlSession, String productid, Number value, String unit, int minstay, String minstayunit, String currency) throws Throwable {
		if (value == null || currency == null) {return;}
		Price price = new Price();
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(productid);
		price.setPartyid(getAltpartyid());
		price.setName(Price.RACK_RATE);
		price.setType(NameId.Type.Reservation.name());
		price.setDate(parse("2012-01-01"));
		price.setTodate(parse("2015-12-31"));
		price.setCurrency(currency);
		price.setQuantity(0.0);
		price.setUnit(unit);

		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}
		
		price.setState(Price.CREATED);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setValue(value.doubleValue());
		price.setCost(price.getValue() * (1.0 - getDiscount() / 100.0));
		price.setAvailable(1);
		price.setMinStay(minstay);
		
		if (Unit.DAY.equalsIgnoreCase(unit) && "NIGHT".equalsIgnoreCase(minstayunit)) {price.setMinimum(price.getValue() * minstay);}
		else if (Unit.DAY.equalsIgnoreCase(unit) && "MONTH".equalsIgnoreCase(minstayunit)) {price.setMinimum(price.getValue() * minstay * 30);}
		else if (Unit.WEE.equalsIgnoreCase(unit) && "MONTH".equalsIgnoreCase(minstayunit)) {price.setMinimum(price.getValue() * minstay * 4);}
		else if (Unit.MON.equalsIgnoreCase(unit) && "MONTH".equalsIgnoreCase(minstayunit)) {price.setMinimum(price.getValue() * minstay);}
		else if (Unit.MON.equalsIgnoreCase(unit) && "YEAR".equalsIgnoreCase(minstayunit)) {price.setMinimum(price.getValue() * minstay * 12);}
		else {price.setMinimum(price.getValue());}
		sqlSession.getMapper(PriceMapper.class).update(price);
	}

	/**
	 * Maps the Kigo type to its OTA equivalent and adds it to the list of property attributes.
	 *
	 * @param attributes the list of property attributes
	 * @param type the type to be added to the list
	 */
	private static final void addType(ArrayList<String> attributes, int type) {
		if (TYPES == null) {
			TYPES = new HashMap<Integer, String>();
			TYPES.put(1,"PCT16");
			TYPES.put(2,"PCT16");
			TYPES.put(3,"PCT16");
			TYPES.put(4,"PCT16");
			TYPES.put(5,"PCT16");
			TYPES.put(6,"PCT35");
			TYPES.put(7,"PCT7");
			TYPES.put(8,"PCT8");
			TYPES.put(9,"PCT16");
			TYPES.put(10,"PCT25");
			TYPES.put(11,"PCT4");
			TYPES.put(13,"PCT19");
			TYPES.put(14,"PCT3");
			TYPES.put(15,"PCT8");
			TYPES.put(17,"PCT6");
			TYPES.put(18,"PCT30");
			TYPES.put(19,"PCT45"); //Boutique Hotel
		}
		if (TYPES.get(type) != null) {attributes.add(TYPES.get(type));}
	}

	/**
	 * Maps the Kigo location to its OTA equivalent and adds it to the list of property attributes.
	 *
	 * @param attributes the list of property attributes
	 * @param location the location to be added to the list
	 */
	private static final void addLocation(ArrayList<String> attributes, int location) {
		if (LOCATIONS == null) {
			LOCATIONS = new HashMap<Integer, String>();
			LOCATIONS.put(2,"LOC15");
			LOCATIONS.put(3,"LOC7");
			LOCATIONS.put(4,"LOC8");
			LOCATIONS.put(5,"LOC23");
			LOCATIONS.put(9,"LOC3");
			LOCATIONS.put(18,"LOC10");
		}
		if (LOCATIONS.get(location) != null) {attributes.add(LOCATIONS.get(location));}
	}
	
	/**
	 * Maps the Kigo activities to their OTA equivalents and adds them to the list of property attributes.
	 *
	 * @param attributes the list of property attributes
	 * @param activities the activities to be added to the list
	 */
	private static final void addActivities(ArrayList<String> attributes, int[] activities) {
		if (ACTIVITIES == null) {
			ACTIVITIES = new HashMap<Integer, String>();
			ACTIVITIES.put(1,"RST142");
			ACTIVITIES.put(2,"ACC65");
			ACTIVITIES.put(3,"ACC41");
			ACTIVITIES.put(4,"ACC50");
			ACTIVITIES.put(5,"ACC32");
			ACTIVITIES.put(6,"RSP146");
			ACTIVITIES.put(7,"RSN7");
			ACTIVITIES.put(8,"RSN12");
			ACTIVITIES.put(9,"RST152");
			ACTIVITIES.put(10,"RSN2");
			ACTIVITIES.put(11,"ACC57");
			ACTIVITIES.put(12,"RST21");
			ACTIVITIES.put(13,"RSN5");
			ACTIVITIES.put(14,"RSP94");
			ACTIVITIES.put(15,"RSP99");
			ACTIVITIES.put(16,"RST106");
			ACTIVITIES.put(18,"RSP68");
			ACTIVITIES.put(19,"RSN6");
			ACTIVITIES.put(20,"PCT36");
			ACTIVITIES.put(21,"RSP60");
			ACTIVITIES.put(22,"RSP20");
			ACTIVITIES.put(23,"RSP105");
			ACTIVITIES.put(24,"RSP112");
			ACTIVITIES.put(25,"RSP77");
			ACTIVITIES.put(26,"RSP4");
			ACTIVITIES.put(27,"RST67");
			ACTIVITIES.put(28,"RSP61");
		}
		for (int activity : activities) {
			if (ACTIVITIES.get(activity) != null) {attributes.add(ACTIVITIES.get(activity));}
		}
	}
	
	/**
	 * Maps the Kigo amentities to their OTA equivalents and adds them to the list of property attributes.
	 *
	 * @param attributes the list of property attributes
	 * @param amentities the amentities to be added to the list
	 */
	private static final void addAmenities(ArrayList<String> attributes, int[] amentities) {
		if (AMENITIES == null) {
			AMENITIES = new HashMap<Integer, String>();
			AMENITIES.put(1,"HAC33");
			AMENITIES.put(2,"CBF45");
			AMENITIES.put(4,"SCY2");
			AMENITIES.put(5,"HAC53");
			AMENITIES.put(7,"HAC65");
			AMENITIES.put(8,"HAC75");
			AMENITIES.put(10,"PHP24");
			AMENITIES.put(13,"RMA149");
			AMENITIES.put(14,"RMA55");
			AMENITIES.put(15,"HAC241");
			AMENITIES.put(21,"RMP210");
			AMENITIES.put(22,"RMP90");
			AMENITIES.put(23,"RMA163");
			AMENITIES.put(25,"RMA46");
			AMENITIES.put(28,"RMA104");
			AMENITIES.put(29,"RMP214");
			AMENITIES.put(30,"RMA129");
			AMENITIES.put(37,"RMA1107");
			AMENITIES.put(41,"RMP54");
			AMENITIES.put(42,"HAC221");
			AMENITIES.put(43,"RMA77");
			AMENITIES.put(44,"RMA68");
			AMENITIES.put(45,"RMA105");
			AMENITIES.put(46,"RMA19");
			AMENITIES.put(48,"RMA32");
			AMENITIES.put(54,"RMA167");
			AMENITIES.put(56,"HAC52");
			AMENITIES.put(57,"RMA59");
			AMENITIES.put(59,"RMA7");
			AMENITIES.put(60,"RMA7");
			AMENITIES.put(63,"HAC5");
			AMENITIES.put(64,"RMA157");
			AMENITIES.put(65,"FAC3");
			AMENITIES.put(67,"HAC79");
			AMENITIES.put(69,"RST104");
			AMENITIES.put(71,"HAC253");
			AMENITIES.put(72,"HAC71");
			AMENITIES.put(73,"RMA41");
			AMENITIES.put(74,"RMA41");
			AMENITIES.put(75,"RMA142");
			AMENITIES.put(76,"RMP85");
			AMENITIES.put(77,"REC143");
			AMENITIES.put(78,"HAC240");
			AMENITIES.put(79,"HAC243");
			AMENITIES.put(80,"HAC55");
			AMENITIES.put(81,"RMP101");
			AMENITIES.put(82,"HAC218");
			AMENITIES.put(83,"PET7");
			AMENITIES.put(84,"LOC15");
			AMENITIES.put(85,"RVT15");
			AMENITIES.put(86,"LOC16");
			AMENITIES.put(88,"LOC7");
			AMENITIES.put(89,"LOC7");
			AMENITIES.put(90,"ACC55");
			AMENITIES.put(92,"LOC8");
			AMENITIES.put(93,"LOC3");
			AMENITIES.put(95,"RVT5");
			AMENITIES.put(98,"HAC242");
			AMENITIES.put(95,"CBF6");
			AMENITIES.put(103,"RST91");
			AMENITIES.put(104,"LOC27");
			AMENITIES.put(106,"FAC4");
			AMENITIES.put(107,"CBF74");
		}
		for (int amenity : amentities) {
			if (AMENITIES.get(amenity) != null) {attributes.add(AMENITIES.get(amenity));}
		}
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Kigo inquireReservation()");
	}
}


//	/**
//	 * Create reservation.
//	 *
//   * @param sqlSession the current SQL session.
//	 * @param reservation the reservation
//	 */
//	public void createReservation(SqlSession sqlSession, Reservation reservation) {
//		Date timestamp = new Date();
//		String message = "Kigo createReservation";
//		LOG.debug(message);
//		IConnectREST port = getPort();
//		try {
//			Booking rq = getBooking(sqlSession, reservation);
//			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
//			context.createMarshaller().marshal(rq, System.out);
//			LOG.debug("createReservation rq " + rq);
//			Boolean infoOnly = false;
//			Booking rs = port.makeBooking(APIKEY, rq, infoOnly);
//			LOG.debug("createReservation rs " + rs.getID().intValue() + " " + rs);
//			reservation.setAltpartyid(HasUrls.PARTY_KIGO_ID);
//			reservation.setAltid(String.valueOf(rs.getID().intValue()));
//			sqlSession.getMapper(ReservationMapper.class).update(reservation);
//		}
//		catch (Throwable x) {x.getMessage();} 
//		MonitorService.monitor(message, timestamp);
//	}
//
//
//	/**
//	 * Update reservation.
//	 *
//   * @param sqlSession the current SQL session.
//	 * @param reservation the reservation
//	 */
//	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
//		Date timestamp = new Date();
//		String message = "Kigo updateReservation";
//		LOG.debug(message);
//		IConnectREST port = getPort();
//		try {
//			Booking rq = getBooking(sqlSession, reservation);
//			Integer options = null;
//			port.modifyBooking(APIKEY, rq, options);
//		}
//		catch (Throwable x) {x.getMessage();} 
//		MonitorService.monitor(message, timestamp);
//	}
//
//	/**
//	 * Cancel reservation.
//	 *
//   * @param sqlSession the current SQL session.
//	 * @param reservation the reservation
//	 */
//	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
//		Date timestamp = new Date();
//		String message = "cancelReservation " + reservation.getAltid();
//		LOG.debug(message);
//		IConnectREST port = getPort();
//		try {
//			Boolean useInternalID = true;
//			Double refundAmount = 0.0;
//			Integer options = null;
//			Booking rs = port.cancelBooking(APIKEY, reservation.getAltid(), useInternalID, getBigDecimal(refundAmount), options);
//			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
//			context.createMarshaller().marshal(rs, System.out);
//		}
//		catch (Throwable x) {x.getMessage();} 
//		MonitorService.monitor(message, timestamp);
//	}
//
//	/**
//	 * Read availability between two dates.
//	 */
//	public void readAvailability(SqlSession sqlSession, String altpartyid) {
//		Date timestamp = new Date();
//		String message = "Kigo downloadAvailability for " + altpartyid;
//		LOG.debug(message);
//		ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist(altpartyid);
//		if (products == null || products.isEmpty()) {return;}
//		for (Product product : products) {
//			Date startDate = Time.getDateStart();
//			Date endDate = Time.addDuration(startDate, 500, Time.DAY);
//			readAvailability(sqlSession, product, startDate, endDate);
//		}
//		MonitorService.monitor(message, timestamp);
//	}

