package net.cbtltd.rest.inntopia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * @see http://wikitopia.inntopia.com/index.php?title=XML_Data_Feeds
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final QName SERVICE_NAME = new QName("http://realtimerental.com/webservice", "Inntopia WSAPI");
	//	private static final String getApikey() = "D5CAC6CC-E290-4463-9BBD-B621D99A2C4B";
//	private static HashMap<String,String> ACTIVITIES = null;
//	private static HashMap<String,String> AMENITIES = null;
//	private static HashMap<String,String> LOCATIONS = null;
	private static final HashMap<String,String> VALUES = new HashMap<String, String>();

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {super(partner);}

	/**
	 * Returns collisions with the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Inntopia  isAvailable " + reservation.getId();
		LOG.debug(message);
		boolean isAvailable = false;
		try {
			return true;
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
		return isAvailable;
	}

	/**
	 * Create reservation in foreign system.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Inntopia createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message);
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Inntopia readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message);
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Update reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Inntopia updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message);
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Confirm reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be confirmed.
	 */
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		//DO NOTHING
	}

	/**
	 * Cancel reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			reservation = sqlSession.getMapper(ReservationMapper.class).read(reservation.getId());
			if (reservation == null) {throw new ServiceException(Error.reservation_id, "Inntopia  cancelReservation");}
			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, reservation.getId());}

			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());

			XMLOutputter outputter = new XMLOutputter();
			Element root = new Element("Inntopia BookingRequest");
			root.setAttribute("Version", String.valueOf(3));
			Element bookingRequest = new Element("BookingRequest");
			root.addContent(bookingRequest);
			bookingRequest.setAttribute("PropertyReferenceID", product.getAltid());
			bookingRequest.setAttribute("RequestType", "Cancel");
			bookingRequest.setAttribute("LeaseID", reservation.getAltid());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			outputter.output(root, os);
			String rq = os.toString();
			LOG.debug("cancelReservation rq=" + rq);
			String rs = null; //TODO getPort().Inntopia CancelBooking(getApikey(), rq);
			LOG.debug("Inntopia CancelBooking rs=" + rs);

			// Parse response XML string
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(rs));
			root = document.getRootElement();
			String status = root.getAttributeValue("Status");
			Boolean canceled = "Canceled".equalsIgnoreCase(status);
			LOG.debug("Inntopia CancelBooking canceled=" + status + " " + canceled);
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Inntopia  readAlerts()");
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		throw new ServiceException(Error.service_absent, "Inntopia  readPrices()");
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Inntopia  readLocations()");
	}

	/**
	 * Transfer accommodation products.
	 *
	 * Show PropDetails = 1;
	 * Show BrokerInfo = 2;
	 * Show PhotoURLs = 4;
	 * Show Availability = 8;
	 * Show Amenities = 16;
	 * Show Rates = 32;
	 * Show all = 63;
	 * 
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date timestamp = new Date();
		String message = "readProducts ";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());

			SAXBuilder builder = new SAXBuilder();
			//TODO: Document document = (Document) builder.build(rs);
			Document document = (Document) builder.build(new File("C:\\SupplierDetails.xml"));

			Element root = document.getRootElement();
			List<Element> places = root.getChildren("Locations");
			for (Element place : places) {
				String id = place.getAttribute("ID").getValue();

				Location location = PartnerService.getLocation(
						sqlSession, 
						place.getAttribute("City").getValue(), 
						place.getAttribute("Region").getValue(),
						place.getAttribute("Country").getValue()
						);

				List<Element> properties = place.getChildren("Products");

				for (Element property : properties) {
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), property.getAttributeValue("ProductID"));
					if (product == null) {continue;}
					product.setActorid(Party.NO_ACTOR);
					product.setAltitude(location.getAltitude());
					List<Element> attributes = property.getChildren("Attribute");
					for (Element attribute : attributes) {addValue(attribute.getAttribute("CodeType").getValue(), attribute.getAttribute("Code").getValue(), attribute.getAttribute("Value").getValue());}				

					product.setBathroom(getIntegerValue(501,2));
					product.setChild(0);
					product.setCommission(getCommission());
					product.setCurrency(getCurrency());
					product.setDiscount(getDiscount());
					product.setInfant(0);
					product.setLatitude(location.getLatitude());
					product.setLocationid(location.getId());
					product.setLongitude(location.getLongitude());
					
					product.setRoom(getIntegerValue(501,1));
					product.setPerson(product.getRoom() * 2);
					StringBuilder address = new StringBuilder();
					String placename = place.getAttribute("Name").getValue();
					if (placename != null && !placename.isEmpty()) {address.append(placename);}
					String city = place.getAttribute("City").getValue();
					if (city != null && !city.isEmpty()) {address.append("\n").append(city);}
					String region = place.getAttribute("Region").getValue();
					if (region != null && !region.isEmpty()) {address.append("\n").append(region);}
					String country = place.getAttribute("Country").getValue();
					if (country != null && !country.isEmpty()) {address.append("\n").append(country);}
					product.setPhysicaladdress(address.toString());
					product.setRank(getRank());
					product.setRating(5);
					product.setState(Product.CREATED);
					Party supplier = PartnerService.getParty(sqlSession, getAltpartyid(), property.getAttribute("SupplierID").getValue(), Party.Type.Organization);
					product.setSupplierid(supplier.getId());
					product.setToilet(product.getBathroom());
					product.setType(Product.Type.Accommodation.name());
					product.setUnit(Unit.DAY);
					product.setWebaddress(getWebaddress());
					
					String name = property.getAttribute("Name").getValue();
					name = (name == null || name.isEmpty()) ? product.getAltid() + " " + product.getPerson() + " person, " + product.getRoom() + " bedroom, " + product.getBathroom() + " bathroom" : name  + " " + product.getId();
					product.setName(name);

					sqlSession.getMapper(ProductMapper.class).update(product);

					product.setValue(Product.Value.City.name(), property.getAttribute(Product.Value.City.name()).getValue());
					product.setValue(Product.Value.Country.name(), property.getAttribute(Product.Value.Country.name()).getValue());
					product.setValue(Product.Value.Region.name(), property.getAttribute(Product.Value.Region.name()).getValue());

//					property.getAttribute("SuperCategoryID").getValue();
//					property.getAttribute("SuperCategory").getValue();
//					property.getAttribute("CategoryID").getValue();
//					property.getAttribute("Category").getValue();
//					property.getAttribute("SupplierID").getValue();
//					property.getAttribute("SupplierName").getValue();
//					property.getAttribute("SortRank").getValue();
//					property.getAttribute("DateActive").getValue();
//					property.getAttribute("DateInActive").getValue();

//TODO:					product.setImages(images);

					sqlSession.commit();
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.getMessage();
		}
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer reservation schedule.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule ";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Inntopia  readSchedule()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation special offers.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Inntopia  readSpecials()");
	}

	/**
	 * Gets the Razor price from the Inntopia  rate element.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altid the Rate element
	 * @return the Razor price
	 */
	private Price getRate(SqlSession sqlSession, String productid, String partyid, Element element) throws Throwable {
		Price price = new Price();
		price.setEntityid(productid);
		price.setEntitytype(NameId.Type.Product.name());
		price.setPartyid(partyid);
		price.setName(Price.RACK_RATE);
		price.setType(NameId.Type.Reservation.name());
		String checkInDate = element.getAttributeValue("CheckInDate");
		Date date = parse(checkInDate);
		price.setDate(date);
		String checkOutDate = element.getAttributeValue("CheckOutDate");
		Date todate = parse(checkOutDate);
		price.setTodate(todate);

		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}

		price.setState(Price.CREATED);
		String dailyRate = element.getAttributeValue("DailyRate");
		Double value = dailyRate == null ? 0.0 : Double.valueOf(dailyRate);
		price.setCost(value * getDiscount() / 100);
		price.setCurrency(getCurrency());
		String minimumStay = element.getAttributeValue("MinimumStay");
		Integer stay = minimumStay == null ? 1 : Integer.valueOf(minimumStay);
		Double minimum = value * stay;
		price.setMinimum(minimum);
		price.setQuantity(0.0);
//		price.setRule(element.getAttributeValue("Rules"));
		price.setUnit(Unit.DAY);
		//TODO:						element.getAttributeValue("Description");
		//						element.getAttributeValue("Rate");
		//						element.getAttributeValue("CheckInDay"); //1 = Sunday2 = Monday4 = Tuesday8 = Wednesday16 = Thursday32 = Friday64 = Saturday127 = Any Day
		sqlSession.getMapper(PriceMapper.class).update(price);
		LOG.debug("price " + price);
		return price;
	}

	/**
	 * Gets the Razor party from the Inntopia  property manager element.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altid the PropertyManager element
	 * @return the Razor party
	 */
	private Party getPropertyManager(SqlSession sqlSession, Element element) {
		String altid = element.getAttributeValue("ID");
		Party party = PartnerService.getParty(sqlSession, getAltpartyid(), altid, Party.Type.Organization);
		party.setEmployerid(party.getId());
		party.setRank(0);
		party.setState(Party.CREATED);
		party.setType(Party.Type.Organization.name());
		party.setAltid(altid);
		party.setName(element.getAttributeValue("Name"));

		StringBuilder address = new StringBuilder();
		address.append(element.getAttributeValue("Office"));
		address.append("\n").append(element.getAttributeValue("Street"));
		address.append("\n").append(element.getAttributeValue("City"));
		address.append("\n").append(element.getAttributeValue("State-Province"));
		address.append("\n").append(element.getAttributeValue("Country"));
		party.setPostaladdress(address.toString());

		party.setPostalcode(element.getAttributeValue("Zip"));
		party.setDayphone(element.getAttributeValue("Phone1"));
		party.setNightphone(element.getAttributeValue("Phone2"));
		party.setFaxphone(element.getAttributeValue("Fax"));
		party.setEmailaddress(element.getAttributeValue("Email"));
		party.setWebaddress(element.getAttributeValue("WebSite"));
		party.setCountry(Country.US);
		party.setCurrency(getCurrency());
		party.setLanguage(Language.EN);
		party.setNotes("Downloaded from Inntopia ");
		party.setRank(0);
		party.setUnit(Unit.EA);
		sqlSession.getMapper(PartyMapper.class).update(party);
		LOG.debug("party " + party);
		return party;
	}

	private static final void addValue(String type, String code, String value) {
		String key = type + "_" + code;
		VALUES.put(key,  value);
	}

	private static final String getValue(int type, int code) {
		String key = type + "_" + code;
		return VALUES.get(key);
	}

	private static final Integer getIntegerValue(int type, int code) {
		return Integer.valueOf(getValue(type, code));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Inntopia  readSpecials()");
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
		throw new ServiceException(Error.service_absent, "Inntopia inquireReservation()");
	}
}
