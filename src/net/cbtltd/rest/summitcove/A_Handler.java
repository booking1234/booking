/**
 * @author	abookingnet
 * @see		License at http://razor-vloud.com/razor/License.html
 * @version	3.00
 */
package net.cbtltd.rest.summitcove;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
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
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * 
 * Basic Authentication:
 * username:password razorcloud:-5z%8Apu6fA8gM5s
 * String PARTY_SUMMITCOVE_ID = "99064";
 * 
 * @see http://www.kodejava.org/examples/273.html
 * @see http://geonetwork-opensource.org/manuals/2.6.4/eng/developer/xml_services/java_xml_services.html
 * @see http://stackoverflow.com/questions/2026260/java-how-to-use-urlconnection-to-post-request-with-authorization (authorization with post)
 * @see http://stackoverflow.com/questions/3283234/http-basic-authentication-in-java-using-httpclient (authorization on get)
 *
 */
public class A_Handler extends PartnerHandler implements IsPartner {
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final String PROPERTIES_URL = "https://www.summitcove.com/xml/service/properties/";
//	private static final String PROPERTIES_URL = "http://www.summitcove.com/xml/properties.php";
//	private static final String RESERVATIONS_URL = "http://www.summitcove.com/xml/reservations.php";
	private static final String RESERVATION_URL = "https://www.summitcove.com/xml/service/reservations/";
	private static final String PICTURES_URL = "https://www.summitcove.com/i/building-pics/";
	
	private static final DateFormat TF = new SimpleDateFormat("hh:mm");
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat FTF = new SimpleDateFormat("h a", Locale.US);

	private static final NumberFormat NF = NumberFormat.getInstance();
	private static HashMap<String,String> AMENITIES = null;
	private static HashMap<String,String> LOCATIONS = null;
	private static HashMap<String,String> TYPES = null;

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
		String message = "Summitcove isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			return true;
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		return true;
	}

	/**
	 * Create reservation.
	 *
	 * 200 with response XML <SummitCove_Result>Success</SummitCove_Result>
	 * 400 with response XML <SummitCove_Result>Parse Error</SummitCove_Result>
	 * 409 with response XML <SummitCove_Result>Property Unavailable</SummitCove_Result>
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, " reservation " + reservation.getName());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}

			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}

			
			// Create request XML string
			XMLOutputter outputter = new XMLOutputter();
			Element root = new Element("SummitCove_Reservations");
//			root.setAttribute("Version", String.valueOf(3));
			Element element = new Element("Reservation");
			root.addContent(element);
			
			Element propertyID = new Element("PropertyID");
			element.addContent(propertyID);
			String productaltid = PartnerService.getProductaltid(sqlSession, reservation);
			propertyID.setText(productaltid);
			
			Element arrivalDate = new Element("ArrivalDate");
			element.addContent(arrivalDate);
			arrivalDate.setText(format(reservation.getFromdate()));
			
			Element departureDate = new Element("DepartureDate");
			element.addContent(departureDate);
			departureDate.setText(format(reservation.getTodate()));
			
			Element firstName = new Element("FirstName");
			element.addContent(firstName);
			firstName.setText(customer.noFirstName() ? Model.UNKNOWN : customer.getFirstName());
			
			Element lastName = new Element("LastName");
			element.addContent(lastName);
			lastName.setText(customer.noFamilyName() ? Model.UNKNOWN : customer.getFamilyName());
			
			Element address = new Element("Address");
			element.addContent(address);
//			address.setText(customer.noAddress(0) ? Model.UNKNOWN : customer.getAddress(0));
			String customerLocalAddress = StringUtils.isEmpty(customer.getLocalAddress()) ? Model.UNKNOWN : customer.getLocalAddress();
			address.setText(customerLocalAddress);

			Element city = new Element("City");
			element.addContent(city);
//			city.setText(customer.noAddress(1) ? Model.UNKNOWN : customer.getAddress(1));
			String customerCity = StringUtils.isEmpty(customer.getCity()) ? Model.UNKNOWN : customer.getCity();
			city.setText(customerCity);

			Element state = new Element("State");
			element.addContent(state);
//			state.setText(customer.noAddress(2) ? Model.UNKNOWN : customer.getAddress(2));
			String customerState = StringUtils.isEmpty(customer.getRegion()) ? Model.UNKNOWN : customer.getState();
			state.setText(customerState);

			Element zip = new Element("Zip");
			element.addContent(zip);
//			zip.setText(customer.getPostalcode() == null || customer.getPostalcode().trim().isEmpty() ? Model.UNKNOWN : customer.getPostalcode());
			String customerZip = StringUtils.isEmpty(customer.getPostalcode()) ? Model.UNKNOWN : customer.getPostalcode();
			zip.setText(customerZip);

			Element country = new Element("Country");
			element.addContent(country);
			country.setText(customer.noCountry() ? Country.US : customer.getCountry());

			Element dayPhone = new Element("DayPhone");
			element.addContent(dayPhone);
			dayPhone.setText(customer.getDayphone());

			Element eveningPhone = new Element("EveningPhone");
			element.addContent(eveningPhone);
			eveningPhone.setText(customer.getNightphone() == null || customer.getNightphone().trim().isEmpty() ? customer.getDayphone() : customer.getNightphone());

			Element email = new Element("Email");
			element.addContent(email);
			email.setText(customer.getEmailaddress());

			Element adults = new Element("Adults");
			element.addContent(adults);
			adults.setText(String.valueOf(reservation.getAdult()));

			Element children = new Element("Children");
			element.addContent(children);
			children.setText(String.valueOf(reservation.getChild()));
			
			Element insuranceOptIn = new Element("InsuranceOptIn");
			element.addContent(insuranceOptIn);
			insuranceOptIn.setText("1");

			// Submit request
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			outputter.output(element, os);
			String rq = os.toString();
			LOG.debug(message + " rq=" + rq);

			InputStream is = PartnerService.getInputStreamNoSSL(RESERVATION_URL, getApikey(), rq);
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(is);
			Element rootNode = document.getRootElement();
			String result = rootNode.getValue();
			String[] args = result.split("\\.");
		    reservation.setAltid(args[1]);
		    reservation.setAltpartyid(getAltpartyid());
//		    computePrice(sqlSession, reservation, productaltid);
			LOG.error("createReservation " + reservation.getId() + " " + reservation.getAltid());
		}
		catch (Throwable x) {
			if (x.getMessage() != null && x.getMessage().contains("409")) {reservation.setMessage(Error.reservation_api + "Collides with another reservation");}
			else {reservation.setMessage(x.getMessage());}
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(reservation.getMessage());
		}
//		sqlSession.getMapper(ReservationMapper.class).update(reservation);
//		sqlSession.commit();
	}

	/**
	 * taxableTotal = [sum of nightly rates for each night]
	 * see /xml/service/properties to calculate salesTaxRate, which is the amount in <TaxRate> divided by 100
	 * salesTaxAmount = taxableTotal * salesTaxRate
	 * see /xml/service/properties to calculate resortTaxRate, which is the amount in <ResortTaxRate> divided by 100
	 * resortTaxAmount = taxableTotal * resortTaxRate
	 * see /xml/service/properties to calculate insuranceRate, which is the amount in <InsuranceRate> divided by 100
	 * if(insuranceOptIn == 1)
	 *   insuranceAmount = (taxableTotal + cleaningFee + damageFee)*insuranceRate
	 * else
	 *   insuranceAmount = 0
	 * insurance_Total := IF(insurance_opt_in = 1 AND insurance_enable = 1,(lodging_Total+extras_Total+cleaning_amount+damage_fee)*insurance_rate/100,0.00);
	 * cleaningFee and damageFee are also provided in /xml/service/properties
	 * grandTotal = taxableTotal + salesTaxAmount + resortTaxAmount + cleaningFee + damageFee + insuranceAmount
	 * 
	 * @param sqlSession
	 * @param reservation
	 * @param productaltid
	 * @throws Throwable
	 */
	private void computePrice(SqlSession sqlSession, Reservation reservation, String productaltid) throws Throwable {

		//example.setPartyid(hasprice.getSupplierid());
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		reservation.setSupplierid(product.getSupplierid());
		Price action = new Price();
		action.setPartyid(product.getSupplierid());
		action.setEntitytype(NameId.Type.Product.name());
		action.setEntityid(reservation.getProductid());
		action.setDate(Time.addDuration(reservation.getTodate(), -1.0, Time.DAY));
		action.setQuantity(reservation.getDuration(Time.DAY));
		action.setUnit(reservation.getUnit());

		double totValue = 0.0;
		double totMinimum = 0.0;
		double totExtra = 0.0;
		double discount = 0.0;
		
		Price price = new Price();
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservation.getId());
		sqlSession.getMapper(PriceMapper.class).deletebyexample(price);
		reservation.setQuotedetail(new ArrayList<Price>());

		int startDay = Time.getDay(reservation.getFromdate());
		int endDay = Time.getDay(reservation.getTodate());
		int i = 0;
		while (i++ < 1000) {
			action.setDate(Time.getDate(endDay - 1));
			//LOG.debug("\nEXAMPLE " + example.getEntitytype() + " " + example.getEntityid() + " " + example.getPartyid() + " " + example.getCurrency() + " "  + example.getDate() + " "  + startDay + " "  + endDay);
			price = sqlSession.getMapper(PriceMapper.class).readbydate(action);
			if (reservation == null || price == null || price.getDate().after(price.getTodate())) {
				reservation.setPrice(0.0);
				reservation.setQuote(0.0);
				reservation.setExtra(0.0);
				reservation.setQuotedetail(new ArrayList<Price>());
				return;
			}
			reservation.setCurrency(price.getCurrency());
			int priceDay = Time.getDay(price.getDate());
			totMinimum = (totMinimum < price.getMinimum()) ? price.getMinimum() : totMinimum;

			int duration = (startDay > priceDay) ? endDay - startDay : endDay - priceDay; 
			double value = (endDay <= startDay) ? price.getValue() : price.getValue() * duration;
			addQuotedetail(sqlSession, reservation, price.getId(), price.getName(), Price.RATE, price.getSupplierid(), Double.valueOf(duration), price.getUnit(), (value < totMinimum) ? totMinimum : value);
			totValue += value;

			if (priceDay <= startDay) {break;}
			else {endDay = priceDay;}
		}

		totValue = (totValue < totMinimum) ? totMinimum : totValue;
		
		//Deduct agent commission
		Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
		if (discountfactor != null) {
			discount = NameId.round(totValue * (1.0 - discountfactor));
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			addQuotedetail(sqlSession, reservation, Model.ZERO, agent.getName(), Price.COMMISSION, agent.getName(), 1.0, Unit.EA, -discount);
		}

		//Calculate taxes and fees
		action = new Price();
		action.setEntitytype(NameId.Type.Mandatory.name());
		action.setEntityid(reservation.getProductid());
		action.setDate(reservation.getFromdate());
		action.setOrderby(Price.ID);

		//Tax excluded from price
		action.setType(Tax.Type.SalesTaxExcluded.name());
		ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			double taxvalue = 0.0;
			for (Tax tax : taxes) {
				Double value = tax.getTaxExcluded(totValue - discount);
				addQuotedetail(sqlSession, reservation, tax.getId(), tax.getName(), Price.TAX_EXCLUDED, tax.getPartyname(), 1.0, Unit.EA, value);
				taxvalue += value;
			}
			totValue += taxvalue;
			totExtra += taxvalue;				
		}

		ArrayList<Price> features = sqlSession.getMapper(PriceMapper.class).entityfeature(action);
		if (features != null && !features.isEmpty()) {
			for (Price feature : features) {
				if (feature.isTaxable()){
					double value = feature.getValue();
					totValue += value;
					totExtra += value;
					addQuotedetail(sqlSession, reservation, feature.getId(), feature.getName(), Price.MANDATORY, feature.getSupplierid(), 1.0, feature.getUnit(), value);
				}
			}
		}

		//Optional features
		features = sqlSession.getMapper(PriceMapper.class).quotedetail(reservation.getReservationid());
		if (features != null && !features.isEmpty()) {
			for (Price feature : features) {
				if (feature.isOptional()) {
					double value = feature.getValue();
					totValue += value;
					totExtra += value;
					addQuotedetail(sqlSession, reservation, feature.getId(), feature.getName(), Price.OPTIONAL, feature.getPartyname(), 1.0, feature.getUnit(), value);
				}
			}
		}

		//Add insurance
		product.setValues(RelationService.read(sqlSession, Relation.PRODUCT_VALUE, product.getId(), null));
		Double insuranceRate = product.getDoubleValue(Product.Value.InsuranceRate.name());
		if (insuranceRate != null && insuranceRate > 0.0) {
			double value = NameId.round((totValue - discount) * insuranceRate / 100.0);
			totValue += value;
			totExtra += value;
			addQuotedetail(sqlSession, reservation, Model.ZERO, Product.Value.InsuranceRate.name(), Price.INSURANCE, "Insurance Amount", 1.0, Unit.EA, value);			
		}

		LOG.debug("computePrice getQuotedetail " + reservation.getQuotedetail());
		reservation.setPrice(totValue);
		reservation.setQuote(totValue);
		reservation.setExtra(totExtra);
		reservation.setCost(totValue - discount);
	}
	
	/*
	 * Increments an existing price and creates one and adds it to the quote detail if it does not exist.
	 *
	 * @param hasprice the reservation or quote being priced
	 * @param id the price, yield, feature or tax ID
	 * @param name the price, yield, feature or tax name
	 * @param type the price, yield, feature or tax type
	 * @param value the value to be added
	 */
	private static void addQuotedetail(
			SqlSession sqlSession, 
			HasPrice hasprice, 
			String id, 
			String name, 
			String type, 
			String partyname, 
			Double quantity,
			String unit,
			double value) {
		Collection<Price> quotedetail = hasprice.getQuotedetail();
		for (Price price : quotedetail) {
			if (price.hasId(id)) {
				price.addValue(value);
				return;
			}
		}
		Price price = new Price();
		price.setId(id);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(hasprice.getReservationid());
		price.setName(name);
		price.setState(Price.CREATED);
		price.setType(type);
		price.setPartyname(partyname);
		price.setQuantity(quantity);
		price.setUnit(unit);
		//		price.setUnit(Unit.EA);
		price.setValue(value/quantity);
		price.setCurrency(hasprice.getCurrency());
		sqlSession.getMapper(PriceMapper.class).create(price);			
		quotedetail.add(price);
		LOG.debug("addQuotedetail " + price);
	}

	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Summitcove readReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			x.getMessage();
		} 
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
		String message = "updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Summitcove updateReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			x.getMessage();
		} 
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
			throw new ServiceException(Error.service_absent, "Summitcove cancelReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			x.getMessage();
		}
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Summitcove readAlerts()");
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		//throw new ServiceException(Error.service_absent, "Summitcove readPrices()");
		LOG.debug("Summitcove readPrices() is not required");
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Summitcove readLocations()");
	}

	/**
	 * Transfer accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date version = new Date();
		String message = "readProducts ";
		String altpartyid = getAltpartyid();
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, altpartyid, new Date().toString());
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(PartnerService.getInputStreamNoSSL(PROPERTIES_URL, getApikey(), null));
//			Document document = parser.build(PROPERTIES_URL);

			Element rootNode = document.getRootElement();
			List<Element> properties = rootNode.getChildren("Property");
			int i = 0;
			for (Element property : properties) {
				String altid = property.getChildText("PropertyID");
				Product product = PartnerService.getProduct(sqlSession, altpartyid, altid);
				if (product == null) {continue;}
				ArrayList<String> attributes = new ArrayList<String>();
				ArrayList<NameId> images = new ArrayList<NameId>();
				StringBuilder description = new StringBuilder();
				boolean isActive = property.getChildText("PropertyActive").equals("1") ? true : false;
				product.setState((isActive ? Product.CREATED : Product.SUSPENDED));
				
				String ProductID = product.getId();
				String propertyAddress = property.getChildText("PropertyAddress");
				String propertyCity = property.getChildText("PropertyCity");
				String propertyState = property.getChildText("PropertyState");
				if(propertyState.equals("COLO")) {propertyState = "CO";}
				String propertyZip = property.getChildText("PropertyZip");
				String latitude = property.getChildText("Latitude");
				String longitude = property.getChildText("Longitude");
				
				Location location = PartnerService.getLocation (sqlSession, propertyCity, propertyState, Country.US);
				product.setLocationid(location.getId());
				product.setLatitude(latitude == null ? location.getLatitude() : Double.valueOf(latitude));
				product.setLongitude(longitude == null ? location.getLongitude() : Double.valueOf(longitude));
				product.setAltitude(0.0);

				StringBuilder address = new StringBuilder();
				if (propertyAddress != null && !propertyAddress.isEmpty()) {address.append(propertyAddress).append("\n");}
				if (propertyCity != null && !propertyCity.isEmpty()) {address.append(propertyCity).append("\n");}
				if (propertyState != null && !propertyState.isEmpty()) {address.append(propertyState).append("\n");}
				if (propertyZip != null && !propertyZip.isEmpty()) {address.append(propertyZip).append("\n");}
				address.append(Country.US);
				product.setPhysicaladdress(address.toString());

				product.setOwnerid(altpartyid);
				product.setSupplierid(altpartyid);

				String sleepingCapacity = property.getChildText("SleepingCapacity");
				product.setPerson(sleepingCapacity == null || sleepingCapacity.isEmpty() ? 1 : Integer.valueOf(sleepingCapacity));
				product.setChild(0);
				product.setInfant(0);
				product.setQuantity(1);

				product.setRating(5);
				product.setCommission(getCommission());
//				product.setCleaningfee(Double.parseDouble(property.getChildText("CleaningFee")));
//				product.setSecuritydeposit(Double.parseDouble(property.getChildText("DamageFee")));
				product.setDiscount(getDiscount());
				product.setRank(getRank());

				String bedrooms = property.getChildText("Bedrooms");
				product.setRoom(bedrooms == null || bedrooms.isEmpty() ? 1 : Integer.valueOf(bedrooms));

				String bathrooms = property.getChildText("Bathrooms");
				String[] bathtoilet = bathrooms == null ? null : bathrooms.split("\\.");
				product.setBathroom(bathtoilet == null || bathtoilet[0].isEmpty() ? 0 : Integer.valueOf(bathtoilet[0]));
				product.setToilet(bathtoilet == null || bathtoilet.length < 2 ? product.getBathroom() : product.getBathroom() + 1);

				product.setType(Product.Type.Accommodation.name());
				product.setCurrency(getCurrency());
				product.setUnit(Unit.DAY);
				product.setWebaddress(getWebaddress());
				product.setVersion(version);

				String propertyUnit = property.getChildText("PropertyUnit");
				String propertyName = property.getChildText("PropertyName");
				String name = propertyName + " " + propertyUnit;
				product.setName(name);
				//product.setName(PartnerService.getProductname(name, location.getName(), ProductID));

				sqlSession.getMapper(ProductMapper.class).update(product);

				product.setValue(Product.Value.InsuranceRate.name(), property.getChildText(Product.Value.InsuranceRate.name()));
				product.setValue(Product.Value.SquareFeet.name(), property.getChildText(Product.Value.SquareFeet.name()));
				product.setValue(Product.Value.SlopeProximity.name(), property.getChildText(Product.Value.SlopeProximity.name()));
				product.setValue(Product.Value.SingleBeds.name(), property.getChildText(Product.Value.SingleBeds.name()));
				product.setValue(Product.Value.QueenBeds.name(), property.getChildText(Product.Value.QueenBeds.name()));
				product.setValue(Product.Value.KingBeds.name(), property.getChildText(Product.Value.KingBeds.name()));

				String oneLiner = property.getChildText("OneLiner");
				if (oneLiner != null && !oneLiner.isEmpty()) {description.append(oneLiner);}
				String propertyDescriptionOverview = property.getChildText("PropertyDescriptionOverview");
				if (propertyDescriptionOverview != null && !propertyDescriptionOverview.isEmpty()) {description.append("\n").append(propertyDescriptionOverview);}
				
				Price action = new Price();
				action.setEntitytype(NameId.Type.Mandatory.name());
				action.setType(Tax.Type.SalesTaxExcluded.name());
				action.setEntityid(ProductID);
				action.setOrderby(Price.ID);
				ArrayList <Tax> list = sqlSession.getMapper(TaxMapper.class).taxbyrelation(action);
				if (list.size() == 0) {
					addTax(sqlSession, null, "Tax Rate", Double.valueOf(property.getChildText("TaxRate")), ProductID);
					addTax(sqlSession, null, "Resort Tax Rate", Double.valueOf(property.getChildText("ResortTaxRate")), ProductID);					
				} 
				else if (list.size() == 2) {
					for (Tax tax : list) {
						if (tax.getName().equals("Tax Rate") && !tax.getAmount().equals(Double.valueOf(property.getChildText("TaxRate")))) {
							addTax(sqlSession, tax.getId(), "Tax Rate", Double.valueOf(property.getChildText("TaxRate")), ProductID);
							continue;
						}
						if (tax.getName().equals("Resort Tax Rate") && !tax.getAmount().equals(Double.valueOf(property.getChildText("ResortTaxRate")))) {
							addTax(sqlSession, tax.getId(), "Resort Tax Rate", Double.valueOf(property.getChildText("ResortTaxRate")), ProductID);							
						}
					}
				}
				else {LOG.error("You have too many relations for product: " + ProductID + ", check your database.");}
				
//				tax = new Tax();
//				tax.setAccountid(Account.VAT_OUTPUT);
//				tax.setPartyid(getAltpartyid());
//				tax.setName("Resort Tax Rate");
//				tax.setType(Tax.Type.SalesTaxExcluded.name());
//				tax.setDate(new Date(0));
//				exists = sqlSession.getMapper(TaxMapper.class).exists(tax);
//				if (exists == null) {sqlSession.getMapper(TaxMapper.class).create(tax);}
//				else {tax = exists;}
//				tax.setState(Tax.CREATED);
//				tax.setDate(new Date(0));
//				tax.setAmount(Double.valueOf(property.getChildText("ResortTaxRate")));
//				tax.setThreshold(0);
//				tax.setVersion(version);
//				sqlSession.getMapper(TaxMapper.class).update(tax);
//				RelationService.replace(sqlSession, Relation.PRODUCT_TAX, ProductID, tax.getId());
//				sqlSession.getMapper(TaxMapper.class).cancelversion(tax);

				String title;
				String descr;
				for (int j=1; j<=10; j++) {
					title = property.getChildText("RoomTitle" + j);
					descr = property.getChildText("RoomDescription" + j);
					addText(description, title);
					addText(description, descr);
					}

				addText(description, property.getChildText("PropertyFeaturesOverview"));

				for (int j=1; j<=10; j++) {
					title = property.getChildText("FeatureTitle" + j);
					descr = property.getChildText("FeatureDescription" + j);
					addText(description, title);
					addText(description, descr);
					}

				for (int j=1; j <=20; j++) {
					String url = property.getChildText("PictureURL" + j);
					if (url == null || url.isEmpty()) {continue;}
					url = PICTURES_URL + url.replace(".", "_full.");
					String text = property.getChildText("PictureCaption" + j);
					addImage(images, url, text);
					}

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Regular_Start"),
						property.getChildText("Season_Regular_End"),
						property.getChildText("Season_Regular_NightlyRate"),
						property.getChildText("Season_Regular_NightMin"), "Regular Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_MLK-Weekend_Start"),
						property.getChildText("Season_MLK-Weekend_End"),
						property.getChildText("Season_MLK-Weekend_NightlyRate"),
						property.getChildText("Season_MLK-Weekend_NightMin"),"MLK Weekend");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Pre-Powder_Start"),
						property.getChildText("Season_Pre-Powder_End"),
						property.getChildText("Season_Pre-Powder_NightlyRate"),
						property.getChildText("Season_Pre-Powder_NightMin"), "Pre-Powder Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Presidents-Day_Start"),
						property.getChildText("Season_Presidents-Day_End"),
						property.getChildText("Season_Presidents-Day_NightlyRate"),
						property.getChildText("Season_Presidents-Day_NightMin"), "President's Day");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Powder_Start"),
						property.getChildText("Season_Powder_End"),
						property.getChildText("Season_Powder_NightlyRate"),
						property.getChildText("Season_Powder_NightMin"), "Powder Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Spring-Break_Start"),
						property.getChildText("Season_Spring-Break_End"),
						property.getChildText("Season_Spring-Break_NightlyRate"),
						property.getChildText("Season_Spring-Break_NightMin"), "Spring Break");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Spring-Prime_Start"),
						property.getChildText("Season_Spring-Prime_End"),
						property.getChildText("Season_Spring-Prime_NightlyRate"),
						property.getChildText("Season_Spring-Prime_NightMin"), "Spring Prime Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Late-Spring_Start"),
						property.getChildText("Season_Late-Spring_End"),
						property.getChildText("Season_Late-Spring_NightlyRate"),
						property.getChildText("Season_Late-Spring_NightMin"), "Late Spring Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Summer_Start"),
						property.getChildText("Season_Summer_End"),
						property.getChildText("Season_Summer_NightlyRate"),
						property.getChildText("Season_Summer_NightMin"), "Summer Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Fall_Start"),
						property.getChildText("Season_Fall_End"),
						property.getChildText("Season_Fall_NightlyRate"),
						property.getChildText("Season_Fall_NightMin"), "Fall Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Thanksgiving_Start"),
						property.getChildText("Season_Thanksgiving_End"),
						property.getChildText("Season_Thanksgiving_NightlyRate"),
						property.getChildText("Season_Thanksgiving_NightMin"), "Thanksgiving");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Early_Start"),
						property.getChildText("Season_Early_End"),
						property.getChildText("Season_Early_NightlyRate"),
						property.getChildText("Season_Early_NightMin"), "Early Season");

				addPrice(sqlSession, version, ProductID, altpartyid, 
						property.getChildText("Season_Holiday_Start"),
						property.getChildText("Season_Holiday_End"),
						property.getChildText("Season_Holiday_NightlyRate"),
						property.getChildText("Season_Holiday_NightMin"), "Holiday Season");

				addFee(sqlSession, version, ProductID, property.getChildText("CleaningFee"), "Cleaning Fee");
				addFee(sqlSession, version, ProductID, property.getChildText("DamageFee"), "Damage Fee");

				Price price = new Price();
				price.setEntitytype(NameId.Type.Product.name());
				price.setEntityid(ProductID);
				price.setPartyid(altpartyid);
				price.setCurrency(Currency.Code.USD.name());
				price.setVersion(version);
				sqlSession.getMapper(PriceMapper.class).cancelversion(price);
				price.setEntitytype(NameId.Type.Mandatory.name());
				sqlSession.getMapper(PriceMapper.class).cancelversion(price);

				product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), description.toString(), Language.EN));
				TextService.update(sqlSession, product.getTexts());
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, ProductID, product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, ProductID, attributes);
				UploadFileService.uploadImages(sqlSession, NameId.Type.Product, ProductID, Language.EN, images);

				sqlSession.commit();
				LOG.debug(i++ + " " + altid + " " + ProductID + " " + product.getName());
				wait(getProductwait());
				//TODO: TEST break;
			}
			Product action = new Product();
			action.setAltpartyid(altpartyid);
			action.setState(Product.CREATED);
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		//MonitorService.monitor(message, version);
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
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(PartnerService.getInputStreamNoSSL(RESERVATION_URL, getApikey(), null));

			Element rootNode = document.getRootElement();
			List<Element> bookings = rootNode.getChildren("Reservation");
			for (Element booking : bookings) {
				String altid = booking.getChildText("PropertyID");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {continue;}
				//String altid = booking.getChildText("ReservationID");
				PartnerService.createSchedule(sqlSession, product, parse(booking.getChildText("ArrivalDate")), parse(booking.getChildText("DepartureDate")), timestamp);
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

//	private String getTime(String time) throws Throwable {
//		Date date = FTF.parse(time.replace("AM", " AM").replace("PM", " PM"));
//		return TF.format(date);
//	}

	/**
	 * Transfer accommodation special offers.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Summitcove readSpecials()");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}
			if (reservation.noProductid()) {throw new ServiceException(Error.product_id, " reservation " + reservation.getName());}
			if (reservation.noAgentid() && reservation.noCustomerid()) {throw new ServiceException(Error.party_id, reservation.getId());}

			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getId());}

			String productaltid = PartnerService.getProductaltid(sqlSession, reservation);

			double oldQuote = reservation.getQuote();
		    
		    computePrice(sqlSession, reservation, productaltid);
		    
			if(oldQuote != reservation.getQuote()) {
				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
			}

			// Create request XML string
			XMLOutputter outputter = new XMLOutputter();
			Element root = new Element("SummitCove_Reservations");
//			root.setAttribute("Version", String.valueOf(3));
			Element element = new Element("Reservation");
			root.addContent(element);
			
			Element propertyID = new Element("PropertyID");
			element.addContent(propertyID);
			propertyID.setText(productaltid);
			
			Element arrivalDate = new Element("ArrivalDate");
			element.addContent(arrivalDate);
			arrivalDate.setText(format(reservation.getFromdate()));
			
			Element departureDate = new Element("DepartureDate");
			element.addContent(departureDate);
			departureDate.setText(format(reservation.getTodate()));
			
			Element firstName = new Element("FirstName");
			element.addContent(firstName);
			firstName.setText(customer.noFirstName() ? Model.UNKNOWN : customer.getFirstName());
			
			Element lastName = new Element("LastName");
			element.addContent(lastName);
			lastName.setText(customer.noFamilyName() ? Model.UNKNOWN : customer.getFamilyName());
			
			Element address = new Element("Address");
			element.addContent(address);
//			address.setText(customer.noAddress(0) ? Model.UNKNOWN : customer.getAddress(0));
			String customerLocalAddress = StringUtils.isEmpty(customer.getLocalAddress()) ? Model.UNKNOWN : customer.getLocalAddress();
			address.setText(customerLocalAddress);

			Element city = new Element("City");
			element.addContent(city);
//			city.setText(customer.noAddress(1) ? Model.UNKNOWN : customer.getAddress(1));
			String customerCity = StringUtils.isEmpty(customer.getCity()) ? Model.UNKNOWN : customer.getCity();
			city.setText(customerCity);

			Element state = new Element("State");
			element.addContent(state);
//			state.setText(customer.noAddress(2) ? Model.UNKNOWN : customer.getAddress(2));
			String customerState = StringUtils.isEmpty(customer.getRegion()) ? Model.UNKNOWN : customer.getState();
			state.setText(customerState);

			Element zip = new Element("Zip");
			element.addContent(zip);
//			zip.setText(customer.getPostalcode() == null || customer.getPostalcode().trim().isEmpty() ? Model.UNKNOWN : customer.getPostalcode());
			String customerZip = StringUtils.isEmpty(customer.getPostalcode()) ? Model.UNKNOWN : customer.getPostalcode();
			zip.setText(customerZip);

			Element country = new Element("Country");
			element.addContent(country);
			country.setText(customer.noCountry() ? Country.US : customer.getCountry());

			Element dayPhone = new Element("DayPhone");
			element.addContent(dayPhone);
			dayPhone.setText(customer.getDayphone());

			Element eveningPhone = new Element("EveningPhone");
			element.addContent(eveningPhone);
			eveningPhone.setText(customer.getNightphone() == null || customer.getNightphone().trim().isEmpty() ? customer.getDayphone() : customer.getNightphone());

			Element email = new Element("Email");
			element.addContent(email);
			email.setText(customer.getEmailaddress());

			Element adults = new Element("Adults");
			element.addContent(adults);
			adults.setText(String.valueOf(reservation.getAdult()));

			Element children = new Element("Children");
			element.addContent(children);
			children.setText(String.valueOf(reservation.getChild()));
			
			Element insuranceOptIn = new Element("InsuranceOptIn");
			element.addContent(insuranceOptIn);
			insuranceOptIn.setText("1");

			// Submit request
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			outputter.output(element, os);
			String rq = os.toString();
			LOG.debug(message + " rq=" + rq);

			InputStream is = PartnerService.getInputStreamNoSSL(RESERVATION_URL, getApikey(), rq);
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(is);
			Element rootNode = document.getRootElement();
			String res = rootNode.getValue();
			String[] args = res.split("\\.");
		    reservation.setAltid(args[1]);
		    reservation.setAltpartyid(getAltpartyid());

			result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
		    
			LOG.error("createReservation " + reservation.getId() + " " + reservation.getAltid());
		}
		catch (ServiceException e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			throw new ServiceException(e.getError(), e.getMessage());	
		}
		catch (Throwable x) {
			if (x.getMessage() != null && x.getMessage().contains("409")) {reservation.setMessage(Error.reservation_api + "Collides with another reservation");}
			else {reservation.setMessage(x.getMessage());}
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(reservation.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		
		return result;
			
	}

	private static void addText(StringBuilder sb, String text) {
		if (text == null || text.isEmpty()) {return;}
		sb.append("\n").append(text);	
		LOG.debug(text);
	}

	private static void addImage(ArrayList<NameId> pictures, String url, String text) {
		if (pictures == null || url == null || url.isEmpty()) {return;}
		pictures.add(new NameId(text, url));
		LOG.debug(url);
	}

	private void addPrice(SqlSession sqlSession, Date version, String productid, String partyid, String fromdate, String todate,
			String value, String minimum, String name) throws Throwable {
		if (fromdate == null || fromdate.isEmpty() || todate == null || todate.isEmpty() || value == null || value.isEmpty()) {return;}
		Calendar nowDate = Calendar.getInstance();
		//compare todate with nowdate, if nowdate > todate, YEAR+1.
		if (nowDate.getTime().after(DF.parse(todate.replace("2000", String.valueOf(nowDate.get(Calendar.YEAR))))) && !name.equals("Holiday Season")) {
			nowDate.add(Calendar.YEAR, +1);
		}
		fromdate = fromdate.replace("2000", String.valueOf(nowDate.get(Calendar.YEAR)));
		if (name.equals("Holiday Season")) {nowDate.add(Calendar.YEAR, +1);}
		todate = todate.replace("2000", String.valueOf(nowDate.get(Calendar.YEAR)));
		
		Price price = new Price();
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(productid);
		price.setPartyid(partyid);
		price.setName(name);
		price.setType(NameId.Type.Reservation.name());
		price.setDate(parse(fromdate));
		price.setTodate(parse(todate));
		price.setCurrency(Currency.Code.USD.name());
		price.setQuantity(1.0);
		price.setUnit(Unit.DAY);

		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}
		
		price.setState(Price.CREATED);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setValue(NF.parse(value).doubleValue());
		price.setCost(price.getValue() * (1.0 - getDiscount() / 100.0));
		price.setMinimum(minimum == null || minimum.isEmpty() ? 0.0 : price.getValue() * Double.valueOf(minimum));
		price.setMinStay(Integer.parseInt(minimum));
		price.setVersion(version);
		price.setAvailable(1);
		sqlSession.getMapper(PriceMapper.class).update(price);
		LOG.debug(price);
	}
	
	private void addFee(SqlSession sqlSession, Date version, String productid, String value, String name) throws Throwable {
		
		Price price = new Price();
		price.setEntitytype(NameId.Type.Mandatory.name());
		price.setEntityid(productid);
		price.setPartyid(getAltpartyid());
		price.setName(name);
		price.setType(Price.NOT_TAXABLE);
		price.setDate(parse("2012-01-01"));
		price.setTodate(parse("2015-12-31"));
		price.setCurrency(Currency.Code.USD.name());
		price.setQuantity(0.0);
		price.setUnit(Unit.EA);

		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
		else {price = exists;}

		price.setState(Price.CREATED);
		price.setValue(NF.parse(value).doubleValue());
		price.setCost(price.getValue() * (1.0 - getDiscount() / 100.0));
		price.setMinimum(0.0);
		price.setVersion(version);
		price.setAvailable(1);
		sqlSession.getMapper(PriceMapper.class).update(price);
		LOG.debug(price);
	}
	
	private void addTax(SqlSession sqlSession, String taxID, String taxName, Double taxValue, String ProductID) {
		Tax tax = new Tax();
		if (taxID == null) {
			tax.setAccountid(Account.VAT_OUTPUT);
			tax.setPartyid(getAltpartyid());
			tax.setType(Tax.Type.SalesTaxExcluded.name());
			tax.setDate(new Date(0));
			tax.setThreshold(0);
			sqlSession.getMapper(TaxMapper.class).create(tax);
		}else {
			tax = sqlSession.getMapper(TaxMapper.class).read(taxID);
		}
		tax.setName(taxName);
		tax.setAmount(taxValue);
		tax.setState(Tax.CREATED);
		sqlSession.getMapper(TaxMapper.class).update(tax);
		RelationService.replace(sqlSession, Relation.PRODUCT_TAX, ProductID, tax.getId());
		LOG.debug("Tax created: " + tax);
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
		throw new ServiceException(Error.service_absent, "Summitcove inquireReservation()");
	}
}
