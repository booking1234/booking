/**
 * @author	abookingnet
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.rest.interhome;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.interhome.accommodation.Accommodation;
import net.cbtltd.rest.interhome.accommodation.Accommodations;
import net.cbtltd.rest.interhome.accommodation.Distance;
import net.cbtltd.rest.interhome.accommodation.Distances;
import net.cbtltd.rest.interhome.accommodation.Geodata;
import net.cbtltd.rest.interhome.accommodation.Picture;
import net.cbtltd.rest.interhome.alert.Alert;
import net.cbtltd.rest.interhome.alert.Alerts;
import net.cbtltd.rest.interhome.countryregionplace.Countries;
import net.cbtltd.rest.interhome.countryregionplace.Country;
import net.cbtltd.rest.interhome.countryregionplace.PlaceType;
import net.cbtltd.rest.interhome.countryregionplace.RegionType;
import net.cbtltd.rest.interhome.countryregionplace.SubplaceType;
import net.cbtltd.rest.interhome.countryregionplace.SubregionType;
import net.cbtltd.rest.interhome.description.Description;
import net.cbtltd.rest.interhome.description.Descriptions;
import net.cbtltd.rest.interhome.price.Price;
import net.cbtltd.rest.interhome.price.Prices;
import net.cbtltd.rest.interhome.price.Service;
import net.cbtltd.rest.interhome.vacancy.Vacancies;
import net.cbtltd.rest.interhome.vacancy.Vacancy;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.AdjustmentMapper;
import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.util.FTPService;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Adjustment;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** 
 * Class A_Handler manages the Interhome API
 * 
 * @see http://www.roseindia.net/tutorial/java/corejava/zip/extract.html
 * @see http://www.devx.com/getHelpOn/10MinuteSolution/20447
 * @see http://www.exampledepot.com/egs/java.util.zip/UncompressFile.html
 * @see http://cxf.apache.org/faq.html#FAQ-HowcanIaddsoapheaderstotherequest%2Fresponse%3F
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
//	private static final QName SERVICE_NAME = new QName("http://www.interhome.com/webservice", "WebService");
//	private static final URL wsdlURL = WebService.WEBSERVICE_WSDL_LOCATION;
	private static HashMap<Integer, Double> DAILYPRICES = new HashMap<Integer, Double>();
	
	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {super(partner);}

//	/**	
//	 * Gets the port to which to connect.
//	 *
//	 * @return the port.
//	 */
//	private static final WebServiceSoap getPort() {
//		WebService ss = new WebService(wsdlURL, SERVICE_NAME);
//		return ss.getWebServiceSoap();  
//	}

	/**
	 * Gets the port to which to connect.
	 * I have now set up the follwowing 3 accounts for you in our LIVE environment (XML feeds are based on LIVE data), 
	 * so feel free to test the read-only web service calls using the accounts below.
	 * 
	 * Currency: USD
	 * Sales Office (SO): 2052
	 * PartnerID/Retailercode/Username: CH1000768
	 * Password: RCLOUD
	 * 
	 * Currency: EUR
	 * Sales Office (SO): 2048
	 * PartnerID/Retailercode/Username: CH1000769
	 * Password: RCLOUD
	 * 
	 * Currency: GBP
	 * Sales Office (SO): 2047
	 * PartnerID/Retailercode/Username: CH1000770
	 * Password: RCLOUD
	 *
	 * When it comes to testing bookings, please use the following account which links to our TEST environment.
	 * TEST account:
	 * Currency: USD
	 * Sales Office (SO): 2052
	 * PartnerID/Retailercode/Username: CH1000725
	 * Password: RCLOUD
	 * 
	 * @return the port.
	 * @throws Throwable 
	 */
	private static final WebServiceSoap getAuthenticatedPort(String username) throws Throwable {
		WebService ss = new WebService();
		WebServiceSoap port = ss.getWebServiceSoap();  

		ServiceAuthHeader sah = new ServiceAuthHeader();
		username = "CH1001116"; // BookingPal partner ID 
		//username = "CH1000768"; //live at "https://webservices.interhome.com/partnerV3/WebService.asmx"
		sah.setUsername(username);
		sah.setPassword("BOOKINGPAL");

		List<Header> headers = new ArrayList<Header>();
		((BindingProvider) port).getRequestContext().put(Header.HEADER_LIST, headers);
		Header authHeader = new Header(
				new QName("http://www.interhome.com/webservice", "ServiceAuthHeader"), 
				sah,
				new JAXBDataBinding(ServiceAuthHeader.class)
				);
		headers.add(authHeader);
		return port;
	}

	/**
	 * Get unzipped input stream for file name.
	 *
	 * @param fn the file name.
	 * @return the input stream.
	 * @throws Throwable the exception that can be thrown.
	 */
	private final synchronized FileInputStream ftp(String fn) throws Throwable {
		URL url = new URL("ftp://ihxmlpartner:S13oPjEu@ftp.interhome.com/" + fn + ".zip;type=i");
		URLConnection urlc = url.openConnection();

		byte[] buf = new byte[1024];
		ZipInputStream zinstream = new ZipInputStream(new BufferedInputStream(urlc.getInputStream()));
		ZipEntry zentry = zinstream.getNextEntry();
		String entryName = zentry.getName();
		FileOutputStream outstream = new FileOutputStream(entryName);
		int n;
		while ((n = zinstream.read(buf, 0, 1024)) > -1) {outstream.write(buf, 0, n);}
		outstream.close();
		zinstream.closeEntry();
		zinstream.close();
		return new FileInputStream(entryName);
	}

	/**
	 * Delete file.
	 *
	 * @param fn the file name.
	 */
	private static final void delete(String fn) {
		try {
			File file = new File(fn);
			file.delete();
		} 
		catch (Throwable x) {x.getMessage();}
	}

	/**
	 * Returns if the property is available for the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		AvailabilityRetunValue availabilityresult;
		AvailabilityInputValue availableinfo;
		String message = "Interhome isAvailable " + reservation.getId();
		LOG.debug(message);
		boolean isAvailable = false;
		try {

			availableinfo = new AvailabilityInputValue();
			availableinfo.setAccommodationCode(PartnerService.getProductaltid(sqlSession, reservation));
			availableinfo.setCheckIn(format(reservation.getFromdate()));
			availableinfo.setCheckOut(format(reservation.getTodate()));

			LOG.debug("isAvailable inputValue=" + availableinfo);

			availabilityresult = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).availability(availableinfo);
			if (availabilityresult.getState().contains(("N")) || availabilityresult.getState().contains(("n"))) {isAvailable = false;}
			else {isAvailable = true;}
			LOG.debug("checkBooking availableinfo=" + availableinfo);
			if (availabilityresult.getErrors() != null && availabilityresult.getErrors().getError() != null && !availabilityresult.getErrors().getError().isEmpty()) {throw new ServiceException(Error.reservation_api, availabilityresult.getErrors().getError().get(0).getDescription());}
		}
		catch (Throwable x) {reservation.setMessage(x.getMessage());} 
		MonitorService.monitor(message, timestamp);
		LOG.debug("\nisAvailable=" + isAvailable);
		return isAvailable;
	}

	/**
	 * Create reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId() ;
		LOG.debug(message);
		try {
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}

			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) {reservation.setCustomerid(Party.NO_ACTOR);}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}

			computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency());

			RetailerBookingInputValue rq = initializeRequest(product, reservation, agent, customer);

			LOG.debug("\nretailerBooking rq=" + rq + "\n");
			RetailerBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).retailerBooking(rq);
			LOG.debug("\nretailerBooking rs=" + rs + "\n");

			if (rs.isOk()) {
				reservation.setAltid(rs.getBookingID());
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
			}
			else {throw new ServiceException(Error.reservation_api, rs.getErrors().getError().get(0).getDescription());}
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
	}
	
	private RetailerBookingInputValue initializeRequest(Product product, Reservation reservation, Party agent, Party customer) {
		return initializeRequest(product, reservation, agent, customer, null);
	}
	
	private RetailerBookingInputValue initializeRequest(Product product, Reservation reservation, Party agent, Party customer, CreditCard creditCard) {
		RetailerBookingInputValue request = new RetailerBookingInputValue();
		request.setAccommodationCode(product.getAltid());
		request.setAdults(reservation.getAdult());
		request.setBabies(reservation.getInfant());
		request.setCheckIn(format(reservation.getFromdate()));
		request.setCheckOut(format(reservation.getTodate()));
		request.setChildren(reservation.getChild());
		request.setComment(reservation.getNotes());
		request.setCurrencyCode(reservation.getCurrency());
		request.setLanguageCode(agent.getLanguage());

		if(creditCard == null) {
			request.setPaymentType(PaymentType.SECURED_CREDIT_CARD);
			request.setCreditCardType(CCType.MASTER);
			request.setCreditCardCvc("");
			request.setCreditCardExpiry("");
			request.setCreditCardNumber("");
			request.setCreditCardHolder("");
		} else {
			request.setPaymentType(PaymentType.SECURED_CREDIT_CARD);
			request.setCreditCardType(getCreditCardType(creditCard));
			request.setCreditCardCvc(creditCard.getSecurityCode());
			request.setCreditCardExpiry(creditCard.getYear() + creditCard.getMonth());
			request.setCreditCardNumber(creditCard.getNumber());
			request.setCreditCardHolder(creditCard.getFirstName() + " " + creditCard.getLastName());
		}

		request.setCustomerFirstName(customer.getFirstName());
		request.setCustomerName(customer.getFamilyName());
		request.setCustomerSalutationType(SalutationType.MR_AND_MRS);
		request.setLanguageCode(Language.Code.en.name());
		request.setRetailerContact(agent.getName());
		request.setRetailerCode(reservation.getAgentname());
		request.setSalesOfficeCode(getSalesOfficeCode(null));
		
		return request;
	}
	
	private ClientBookingInputValue createClientBookingRequest(Product product, Reservation reservation, Party agent, Party customer, CreditCard creditCard) {
		ClientBookingInputValue request = new ClientBookingInputValue();
		request.setSalesOfficeCode(getSalesOfficeCode(null));
		request.setAccommodationCode(product.getAltid());
		
		request.setCustomerSalutationType(SalutationType.MR_AND_MRS);
		request.setCustomerName(customer.getFamilyName());
		request.setCustomerFirstName(customer.getFirstName());

		String s;
		if ((s = customer.getDayphone()) != null && !s.isEmpty()) {
			request.setCustomerPhone(s);
		} else if ((s = customer.getNightphone()) != null && !s.isEmpty()) {
			request.setCustomerPhone(customer.getNightphone());
		} else if (!customer.noMobilephone()) {
			request.setCustomerPhone(customer.getMobilephone());
		}
		request.setCustomerFax(customer.getFaxphone());
		request.setCustomerEmail(customer.getEmailaddress());
		request.setCustomerAddressStreet(customer.getLocalAddress());
		request.setCustomerAddressZIP(customer.getPostalcode());
		request.setCustomerAddressPlace(customer.getCity());
		request.setCustomerAddressState(customer.getState());
		request.setCustomerAddressCountryCode(customer.getCountry());
		request.setComment(reservation.getNotes());
		request.setAdults(reservation.getAdult());
		request.setChildren(reservation.getChild());
		request.setBabies(reservation.getInfant());
		request.setCheckIn(format(reservation.getFromdate()));
		request.setCheckOut(format(reservation.getTodate()));
		request.setLanguageCode(agent.noLanguage() ? Language.Code.en.name() : agent.getLanguage());
		request.setCurrencyCode(reservation.getCurrency());
		request.setRetailerCode(reservation.getAgentname());

		request.setPaymentType(PaymentType.SECURED_CREDIT_CARD);
		request.setCreditCardType(getCreditCardType(creditCard));
		request.setCreditCardCvc(creditCard.getSecurityCode());
		request.setCreditCardExpiry(creditCard.getYear() + creditCard.getMonth());
		request.setCreditCardNumber(creditCard.getNumber());
		request.setCreditCardHolder(creditCard.getFirstName() + " " + creditCard.getLastName());
		
		return request;
	}
	
	
	private CCType getCreditCardType(CreditCard creditCard) {
		if(creditCard.getType() == CreditCardType.MASTER_CARD) {
			return CCType.MASTER;				
		} else if(creditCard.getType() == CreditCardType.VISA) {
			return CCType.VISA;
		} else {
			throw new ServiceException(Error.card_type, creditCard.getType().name() + ", should be MasterCard or Visa.");
		}
	}

	public static void main(String[] args) {
		SqlSession sqlSession =  RazorServer.openSession();
		Partner partner = sqlSession.getMapper(PartnerMapper.class).read("1");
		A_Handler handler = new A_Handler(partner);
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read("7425809");
		handler.readPrice(sqlSession, reservation, "AT5090.1.2", "USD");
	}
	
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency)  {
		try {
			return computePrice(sqlSession, reservation, productAltId, currency);
		} catch (Throwable e) {
			throw new ServiceException(Error.reservation_api, e.getMessage());
		}
	}

	/**
	 * Create price and price detail
	 * price=958.00
	 * total=767.00
	 * currencyCode=EUR
	 * expirationPrePayment=0001-01-01
	 * expirationResidue=2012-10-22
	 * prepayment=0
	 * specialCode=PR/00000002
	 * specialDescription=20% Discount
	 * minimum stay 7 nights
	 * specialPrice=767.00
	 * 
	 * type=EXTRACOST_ON_PLACE, amount=3.30, code=LT, count=0, currency=CHF, description=, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=true, paymentInfo=Payable in resort per person/day, priceRule=PD, text=, type=Y4, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=10.00, code=PET, count=0, currency=EUR, description=Pet, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra per person/week, priceRule=U1, text=One pet is allowed in this property. Please reconfirm with our sales office if
		you need to take more than one animal with you., type=N1, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=16.00, code=WOOD, count=0, currency=EUR, description=Tanne, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra fixed price, priceRule=U1, text=, type=N1, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=IN_PRICE_INCLUDED, amount=0, code=LI, count=0, currency=, description=Laundry (initial supply of bed linen and towels), eitherOr=, isDefaultService=false, isIncluded=true, isInsurance=false, isMandatory=true, paymentInfo=Included in the price, priceRule=, text=, type=Y2, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=56.00, code=HCHR, count=4, currency=EUR, description=Highchair, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra per day, priceRule=U1, text=, type=N1, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=56.00, code=COT, count=1, currency=EUR, description=Cot (up to 2 years), eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra per day, priceRule=U1, text=Appropriate up to 2 years (max. 3 years), type=N1, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=56.00, code=BUGY, count=2, currency=EUR, description=, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra per day, priceRule=U1, text=, type=N1, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=IN_PRICE_INCLUDED, amount=0, code=FC, count=0, currency=, description=Final cleaning, eitherOr=, isDefaultService=false, isIncluded=true, isInsurance=false, isMandatory=true, paymentInfo=Included in the price, priceRule=, text=, type=Y2, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=EXTRACOST_ON_PLACE, amount=500.00, code=DK, count=0, currency=CHF, description=Breakage deposit by credit card Visa / Mastercard, eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=true, paymentInfo=Payable in resort fixed price, priceRule=U1, text=, type=Y4, validFrom=2012-12-01, validTo=2012-12-15], 
	 * type=BOOKABLE_SERVICE_ON_INVOICE, amount=1200.00, code=WIFI, count=5, currency=EUR, description=wireless internet access (WIFI), eitherOr=, isDefaultService=false, isIncluded=false, isInsurance=false, isMandatory=false, paymentInfo=Bookable extra per day, priceRule=U1, text=, type=N1, validFrom=2012-12-01, validTo=2012-12-15]
	 *
	 * @param sqlSession
	 * @param reservation
	 * @param productaltid
	 * @throws Throwable
	 */
	private ReservationPrice computePrice(SqlSession sqlSession, Reservation reservation, String productaltid, String currency) throws Throwable {
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		reservationPrice.setQuoteDetails(quoteDetails);
		PriceDetailInputValue rq = new PriceDetailInputValue();
		rq.setAccommodationCode(productaltid);
		rq.setCheckIn(format(reservation.getFromdate()));
		rq.setCheckOut(format(reservation.getTodate()));
		rq.setRetailerCode(reservation.getAgentname());
		rq.setSalesOfficeCode(getSalesOfficeCode(null));
		rq.setAdults(reservation.getAdult());
		rq.setChildren(reservation.getChild());
		rq.setBabies(reservation.getInfant());
		rq.setCurrencyCode(currency);
		rq.setLanguageCode(Language.Code.en.name());

		System.out.println("priceDetail rq=" + rq);
		PriceDetailRetunValue rs = getAuthenticatedPort(getRetailerCode(currency)).priceDetail(rq);
		System.out.println("priceDetail rs=" + rs);

		if (rs.isOk()) {
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			net.cbtltd.shared.Price price = new net.cbtltd.shared.Price();
			price.setEntitytype(NameId.Type.Reservation.name());
			price.setEntityid(reservation.getId());
			sqlSession.getMapper(PriceMapper.class).deletebyexample(price);

			Double rack = rs.getPrice().doubleValue();
			Double total = rs.getTotal().doubleValue();
			Double prepayment = rs.getPrepayment().doubleValue();
			Double special = rs.getSpecialPrice() == null ? 0.0 : rs.getSpecialPrice().doubleValue();

			if (special > 0.0) {
				price = new net.cbtltd.shared.Price();
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(reservation.getId());
				price.setName(rs.getSpecialDescription());
				price.setState(net.cbtltd.shared.Price.CREATED);
				price.setType(getSpecial(rs.getSpecialCode()));
				price.setDate(parse(rs.getExpirationResidue()));
				price.setQuantity(1.0);
				price.setUnit(Unit.EA);
				price.setValue(special - prepayment);
				price.setCurrency(rs.getCurrencyCode());
				sqlSession.getMapper(PriceMapper.class).create(price);			
				reservation.getQuotedetail().add(price);
				String specialPrice = (String.valueOf(special - rack));
				QuoteDetail quoteDetail = new QuoteDetail(specialPrice, price.getCurrency(), "Special price discount", "", "", false);
				quoteDetails.add(quoteDetail);
			}
			else {
				price = new net.cbtltd.shared.Price();
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(reservation.getId());
				price.setType(net.cbtltd.shared.Price.RATE);
				price.setName(net.cbtltd.shared.Price.RATE);
				price.setState(net.cbtltd.shared.Price.CREATED);
				price.setDate(parse(rs.getExpirationResidue()));
				price.setQuantity(1.0);
				price.setUnit(Unit.EA);
				price.setValue(total - prepayment);
				price.setCurrency(rs.getCurrencyCode());
				sqlSession.getMapper(PriceMapper.class).create(price);
				reservation.getQuotedetail().add(price);
			}

			if (prepayment > 0.0) {
				price = new net.cbtltd.shared.Price();
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(reservation.getId());
				price.setType(net.cbtltd.shared.Price.DEPOSIT);
				price.setName(net.cbtltd.shared.Price.DEPOSIT);
				price.setState(net.cbtltd.shared.Price.CREATED);
				price.setDate(parse(rs.getExpirationPrePayment()));
				price.setQuantity(1.0);
				price.setUnit(Unit.EA);
				price.setValue(prepayment);
				price.setCurrency(rs.getCurrencyCode());
				sqlSession.getMapper(PriceMapper.class).create(price);
				reservation.getQuotedetail().add(price);
			}

			for (AdditionalServiceItem item : rs.getAdditionalServices().getAdditionalServiceItem()) {
				if (item.getCode().equalsIgnoreCase("RTCO")) {continue;} // skip agency commission
				double amountvalue = item.getAmount().doubleValue();
				if(item.isIsMandatory()) {
					String itemCurrency = item.getCurrency().equals("") ? currency : item.getCurrency();
					String amount = amountvalue == 0 ? "0" : item.getAmount().toString();
/*					if ((!item.getCurrency().isEmpty() || item.getCurrency() != null) && !item.getCurrency().equalsIgnoreCase(currency)) {
						double amountconverted = PaymentService.convertCurrency(sqlSession, item.getCurrency(), currency, amountvalue);
						double amountround = PaymentHelper.getAmountWithTwoDecimals(amountconverted);
						amount = String.valueOf(amountround);
					}*/
					QuoteDetail quoteDetail = new QuoteDetail(amount, itemCurrency, item.getDescription(), item.getPaymentInfo(), item.getText(), item.isIsIncluded());
					quoteDetails.add(quoteDetail);
				}
				String type = getType(item.getAdditionalServiceType());
				if (type == null) {continue;}
				price = new net.cbtltd.shared.Price();
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(reservation.getId());
				price.setType(type);
				price.setName(NameId.trim(item.getDescription(), 24));
				price.setState(net.cbtltd.shared.Price.CREATED);
				price.setValue(amountvalue);
				price.setId(item.getCode());
				boolean mandatory = item.isIsMandatory();
				price.setQuantity(mandatory ? Double.valueOf(item.getCount()) : 0.0);
				price.setUnit(Unit.EA);
				price.setCurrency(rs.getCurrencyCode());
				price.setRule(item.getPriceRule());
				price.setDate(parse(item.getValidFrom()));
				price.setTodate(parse(item.getValidTo()));
				sqlSession.getMapper(PriceMapper.class).create(price);
				reservation.getQuotedetail().add(price);
			}
			reservation.setPrice(rack);
			reservation.setQuote(special > 0.0 ? special : total);
			reservation.setExtra(0.0);
			reservationPrice.setPrice(reservation.getPrice());
			reservationPrice.setTotal(reservation.getQuote());
			reservationPrice.setCurrency(currency);
			Double discountfactor = ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(reservation.getPrice() * discountfactor);
			reservation.setCurrency(rs.getCurrencyCode());
			Double dueNow = rs.getPrepayment().doubleValue();
			Double deposit = dueNow == null ? 0.0 : dueNow * 100 / reservation.getQuote();
			reservation.setDeposit(deposit.doubleValue());
			
		}
		else {throw new ServiceException(Error.reservation_api, rs.getErrors().getError().get(0).getDescription());}
		LOG.debug("computePrice " + reservation.getQuotedetail());
		return reservationPrice;
	}

	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Interhome readReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		} 
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
			throw new ServiceException(Error.service_absent, "Interhome updateReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
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
		Date timestamp = new Date();
		String message = "confirmReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			RatifyBookingInputValue rq = new RatifyBookingInputValue();
			rq.setBookingID(reservation.getAltid());
			rq.setRetailerCode(reservation.getAgentname());
			rq.setSalesOfficeCode(getSalesOfficeCode(null));
			LOG.debug("confirmReservation.rq=" + rq);
			RatifyBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).ratifyBooking(rq);
			LOG.debug("confirmReservation.rs=" + rs);
			if (!rs.isOk()) {throw new ServiceException(Error.reservation_api, rs.getErrors().getError().get(0).getDescription());}
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			//LOG.error(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
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
			CancelBookingInputValue rq = new CancelBookingInputValue();
			rq.setBookingID(reservation.getAltid());
			rq.setRetailerCode(reservation.getAgentname());
			rq.setSalesOfficeCode(getSalesOfficeCode(null));
			LOG.debug("cancelBooking.rq=" + rq);
			CancelBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).cancelBooking(rq);
			LOG.debug("cancelBooking.rs=" + rs);
			if (!rs.isOk()) {throw new ServiceException(Error.reservation_api, rs.getErrors().getError().get(0).getDescription());}
		}
		catch (Throwable x) {reservation.setMessage(x.getMessage());}
//		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		Date timestamp = new Date();
		String message = "readAlerts " + getAltpartyid();
		LOG.debug(message);
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.alert");
			Unmarshaller um = jc.createUnmarshaller();

			fn = "alert_en.xml";
			Alerts alerts = (Alerts) um.unmarshal(ftp(fn));
			int i = 0;
			for (Alert alert : alerts.getAlert()) {
				LOG.debug(alert);
				net.cbtltd.shared.Alert action = new net.cbtltd.shared.Alert();
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), alert.getCode()));
				if (product == null) {LOG.error(Error.product_id.getMessage() + " " + alert.getCode());}
				else {
					action.setEntitytype(NameId.Type.Product.name());
					action.setEntityid(product.getId());
					action.setFromdate(alert.getStartdate().toGregorianCalendar().getTime());
					action.setTodate(alert.getEnddate().toGregorianCalendar().getTime());
					action.setName(alert.getText());
					net.cbtltd.shared.Alert duplicate = sqlSession.getMapper(AlertMapper.class).duplicate(action);
					if (duplicate == null) {sqlSession.getMapper(AlertMapper.class).create(action);}
					else {sqlSession.getMapper(AlertMapper.class).update(action);}
					sqlSession.commit();
					LOG.debug(i++ + " " + alert.getCode() + " = " + action.getId());
				}
				wait(getAlertwait());
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read property locations.
	 */
	public synchronized void readLocations() {
		String message = "Interhome readLocations " + getAltpartyid();
		LOG.debug(message);
		String codeinterhome = null;
		String countrycode = null;
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {

			StringBuilder sb = new StringBuilder();

			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.countryregionplace");
			//Create unmarshaller
			Unmarshaller um = jc.createUnmarshaller();

			fn = "countryregionplace_en.xml";

			Countries countries = (Countries) um.unmarshal(ftp(fn));			
			for (Country country : countries.getCountry()) {
				LOG.debug("Country " + country);
				countrycode = country.getCode();
				String usRegion = null;
				if (sqlSession.getMapper(CountryMapper.class).read(countrycode) == null) {throw new ServiceException(Error.country_code, countrycode);}
				int i = 0;
				for (RegionType region : country.getRegions().getRegion()) {
					if (region.getPlaces() != null && region.getPlaces().getPlace() != null) {
						for (PlaceType place : region.getPlaces().getPlace()) {
							codeinterhome = countrycode + "." + place.getCode();
							if (countrycode.equals("US")) {usRegion = region.getName();}
							setLocation(sqlSession, place.getName(), usRegion, countrycode, codeinterhome);
							if (place.getSubplaces() != null && place.getSubplaces().getSubplace() != null) {
								for (SubplaceType subplace : place.getSubplaces().getSubplace()) {
									codeinterhome = countrycode + "." + subplace.getCode();
									setLocation(sqlSession, subplace.getName(), usRegion, countrycode, codeinterhome);
								}
							}
						}
					}

					if (region.getSubregions() != null && region.getSubregions().getSubregion() != null) {
						for (SubregionType subregion : region.getSubregions().getSubregion()) {
							if (subregion.getPlaces() != null && subregion.getPlaces().getPlace() != null) {
								for (PlaceType place : subregion.getPlaces().getPlace()) {
									codeinterhome = countrycode + "." + place.getCode();
									if (countrycode.equals("US")) {usRegion = (region.getName() + "," + subregion.getName());}
									setLocation(sqlSession, place.getName(), usRegion, countrycode, codeinterhome);
									if (place.getSubplaces() != null && place.getSubplaces().getSubplace() != null) {
										for (SubplaceType subplace : place.getSubplaces().getSubplace()) {
											codeinterhome = countrycode + "." + subplace.getCode();
											setLocation(sqlSession, subplace.getName(), usRegion, countrycode, codeinterhome);
										}
									}
								}
							}
						}
					}
					sqlSession.commit();
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
	}

	/**
	 * Checks and creates the location from its name, region and country it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of current location
	 * @param region the name of current location region (currently only for US)
	 * @param countrycode the current country
	 * @param codeInterhome the unique Interhome code for current location
	 * @return the location object
	 */
	private final void setLocation(SqlSession sqlSession, String name, String region, String countrycode, String codeinterhome) {
		Location location = getLocation(sqlSession, codeinterhome);
		if (location == null) {
		location = PartnerService.getLocation(sqlSession, name, region, countrycode);
		location.setCodeinterhome(codeinterhome);
		sqlSession.getMapper(LocationMapper.class).update(location);
		sqlSession.commit();
		LOG.debug("Location changed: " + location);
		} else {LOG.debug("Location no changes: " + location);}
	}
	
	/**
	 * Checks modification date of file from svn with given date
	 * @param fn - filename string
	 * @param date - date to compare
	 * @return true if file version is after selected date
	 */
	private boolean checkVersion(String fn, Date date){			
		//check version of svn file and last price database entry
		FTPClient ftpClient = new FTPClient();
		boolean newVersion = false;
		try {
			ftpClient.connect("ftp.interhome.com");
			ftpClient.login("ihxmlpartner", "S13oPjEu");
			Date priceModifDate = FTPService.getFileModificationDate(ftpClient, fn + ".zip");
			if (priceModifDate.after(date)){newVersion = true;}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return newVersion;
	}
	
	/**
	 * Transfer accommodation daily prices and weekly prices for each sales office.
	 *
	 * TODO: Vacando prices in EUR only must use exchange rate for other prices
	 * 
	 * @param sqlSession the current SQL session.
	 */
	public void readPrices() {
		Date version = new Date();
		String altparty = getAltpartyid();
		boolean checkedVersion = false;
		String message = "Interhome readPrices " + altparty;
		LOG.debug(message);
		String fn = null;
		
		String salesoffice = getSalesOfficeCode(Currency.Code.EUR.name());
		Currency.Code currency = Currency.Code.EUR;
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			fn = "dailyprice_" + salesoffice + "_" + currency.name().toLowerCase() + ".xml";

			//check version of svn file and last price database entry
			net.cbtltd.shared.Price action = new net.cbtltd.shared.Price();
			action.setState(net.cbtltd.shared.Price.CREATED);
			action.setPartyid("90640");
			action.setAvailable(1);

			Date priceVersion = sqlSession.getMapper(PriceMapper.class).maxVersion(action);
			checkedVersion = checkVersion(fn, priceVersion);
			if (checkedVersion) {
				JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.price");
				Unmarshaller um = jc.createUnmarshaller();
				
				Prices prices = (Prices) um.unmarshal(ftp(fn));
				
				if (prices != null && prices.getPrice() != null) {
					int count = prices.getPrice().size();
					LOG.debug("Total daily prices count: " + count);
				} else {
					LOG.debug("Error, no dailyprices file");
					return;
				}
	
				int i = 0;
				for (Price price : prices.getPrice()) {
					String altid = price.getCode();
//					if (!altid.equals("CH3920.126.1") || !"CH3920.126.1".equalsIgnoreCase(altid)) continue;
					String productid = getProductId(altid);
					
					if (productid == null) {
						Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(altparty, altid));
						if (product == null) {
							LOG.error(Error.product_id.getMessage() + " " + price.getCode());
							continue;
						}
						PRODUCTS.put(altid, product.getId());
						productid = getProductId(altid);
					}
	
//					if (!productid.equals("27077") || !String.valueOf(27077).equalsIgnoreCase(productid)) continue;
	
					action = new net.cbtltd.shared.Price();
					action.setPartyid(altparty);
					action.setEntitytype(NameId.Type.Product.name());
					action.setEntityid(productid);
					action.setCurrency(currency.name());
					action.setQuantity(1.0);
					action.setUnit(Unit.DAY);
					action.setName(net.cbtltd.shared.Price.RACK_RATE);
					action.setType(NameId.Type.Reservation.name());
					action.setDate(price.getStartdate().toGregorianCalendar().getTime());
					action.setTodate(price.getEnddate().toGregorianCalendar().getTime());
	
					net.cbtltd.shared.Price exists = sqlSession.getMapper(PriceMapper.class).exists(action);
					if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(action);}
					else {action = exists;}
	
					action.setState(net.cbtltd.shared.Price.CREATED);
					action.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
					action.setFactor(1.0);
//					Double rentalPrice = price.getRentalprice().doubleValue();
//					Double value = duration > 0.0 ? NameId.round(rentalPrice / quantity) : rentalPrice;
					action.setValue(price.getRentalprice().doubleValue());
//					action.setMinimum(price.getMinrentalprice().doubleValue());
					action.setVersion(version);
					action.setAvailable(1);
					sqlSession.getMapper(PriceMapper.class).update(action);
					sqlSession.getMapper(PriceMapper.class).cancelversion(action);
	
					LOG.debug(i++ + " DailyPrice: " + " " + price.getCode() + " " + productid + " " + salesoffice + " " + action.getValue() + " " + currency + " daily");
	
//					if (price.getFixprice().signum() != 0) {
						Adjustment adjust = setAdjustment(sqlSession, productid, 5, 999, currency.name(), action.getDate(), action.getTodate(), price, Adjustment.WEEK, "fixprice", version);
						DAILYPRICES.put(adjust.getID(), price.getRentalprice().doubleValue());
	
						LOG.debug(i++ + " FixPrice: " + " " + price.getCode() + " " + productid + " " + salesoffice + " " + adjust.getExtra() + " " + currency + " fixprice");
//					}
					if (price.getMidweekrentalprice().signum() != 0) {
						adjust = setAdjustment(sqlSession, productid, 1, 1, currency.name(), action.getDate(), action.getTodate(), price, (byte)0x1E, "midweek", version);
						adjust = setAdjustment(sqlSession, productid, 2, 2, currency.name(), action.getDate(), action.getTodate(), price, (byte)0xE, "midweek", version);
						adjust = setAdjustment(sqlSession, productid, 3, 3, currency.name(), action.getDate(), action.getTodate(), price, (byte)0x6, "midweek", version);
						adjust = setAdjustment(sqlSession, productid, 4, 4, currency.name(), action.getDate(), action.getTodate(), price, Adjustment.MONDAY, "midweek", version);
	
						LOG.debug(i++ + " MidweekPrice: " + " " + price.getCode() + " " + productid + " " + salesoffice + " " + adjust.getExtra() + " " + currency + " midweek");
					}
					if (price.getWeekendrentalprice().signum() != 0) {
						adjust = setAdjustment(sqlSession, productid, 1, 1, currency.name(), action.getDate(), action.getTodate(), price, Adjustment.SATURDAY, "weekend", version);
						adjust = setAdjustment(sqlSession, productid, 2, 2, currency.name(), action.getDate(), action.getTodate(), price, (byte)0x60, "weekend", version);
						adjust = setAdjustment(sqlSession, productid, 3, 3, currency.name(), action.getDate(), action.getTodate(), price, (byte)0x70, "weekend", version);
							
						LOG.debug(i++ + " WeekendPrice: " + " " + price.getCode() + " " + productid + " " + salesoffice + " " + adjust.getExtra() + " " + currency + " weekend");
					}
//					LOG.debug(i++ + "DailyPrice: " + " " + price.getCode() + " " + product.getId() + " " + salesoffice + " " + action.getValue() + " " + currency + " " + duration);
				}
				Adjustment cancelAction = new Adjustment();
				cancelAction.setCurrency(currency.name());
				cancelAction.setPartyID(altparty);
				cancelAction.setVersion(version);
				
				sqlSession.getMapper(AdjustmentMapper.class).cancelversion(cancelAction);
				sqlSession.commit();
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
		if (checkedVersion) {
			readPrices(version, salesoffice, currency, 7);
			readPrices(version, salesoffice, currency, 14);
			readPrices(version, salesoffice, currency, 21);
			readPrices(version, salesoffice, currency, 28);
		}
	}

	/**
	 * Read prices.
	 *
	 * @param version the version
	 * @param salesoffice the salesoffice
	 * @param currency the currency
	 * @param duration the duration
	 */
	private synchronized void readPrices(Date version, String salesoffice, Currency.Code currency, int duration) {
		String altparty = getAltpartyid();
		String message = "Interhome readPrices Weekly " + altparty;
		LOG.debug(message);
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.price");
			Unmarshaller um = jc.createUnmarshaller();

			fn = "price_" + salesoffice + "_" + currency.name().toLowerCase() + (duration < 14 ? "" :  "_" + String.valueOf(duration)) + ".xml";
			Prices prices = (Prices) um.unmarshal(ftp(fn));

			if (prices != null && prices.getPrice() != null) {
				int count = prices.getPrice().size();
				LOG.debug("Total prices for " + duration + " days: " + count);
			} else {
				LOG.debug("Error, no price file for " + duration + " days");
				return;
			}
			
			int i = 0;
			for (Price weekprice : prices.getPrice()) {
				String altid = weekprice.getCode();
//				if (!altid.equals("CH3920.126.1") || !"CH3920.126.1".equalsIgnoreCase(altid)) continue;
				String productid = getProductId(altid);
				
				if (productid == null) {
					Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(altparty, altid));
					if (product == null) {
						LOG.error(Error.product_id.getMessage() + " " + weekprice.getCode());
						continue;
					}
					PRODUCTS.put(altid, product.getId());
					productid = getProductId(altid);
				}

//				if (!productid.equals("27077") || !String.valueOf(27077).equalsIgnoreCase(productid)) continue;
	
				Date fromdate = weekprice.getStartdate().toGregorianCalendar().getTime();
				Date todate = weekprice.getEnddate().toGregorianCalendar().getTime();

				if (weekprice.getServices() != null && weekprice.getServices().getService() != null) {
					net.cbtltd.shared.Price mandatory = new net.cbtltd.shared.Price();
					mandatory.setEntitytype(NameId.Type.Mandatory.name());
					mandatory.setEntityid(productid);
					mandatory.setCurrency(currency.name());
					mandatory.setPartyid(altparty);
					mandatory.setVersion(version);
		
					for (Service service : weekprice.getServices().getService()) {
						mandatory.setType("Fees");
						mandatory.setDate(fromdate);
						mandatory.setTodate(todate);
						mandatory.setQuantity(0.0);
						mandatory.setUnit(Unit.EA);
						mandatory.setAvailable(1);
						mandatory.setMinStay(1);
						net.cbtltd.shared.Price existprice = sqlSession.getMapper(PriceMapper.class).exists(mandatory);
						if (existprice == null) {sqlSession.getMapper(PriceMapper.class).create(mandatory);}
						else {mandatory = existprice;}

						mandatory.setState(net.cbtltd.shared.Price.CREATED);
						mandatory.setName(getServicename(service.getCode()));
						mandatory.setMinimum(0.0);
						mandatory.setValue(service.getServiceprice() == null ? 0.0 : service.getServiceprice().doubleValue());
						mandatory.setRule("Manual");
						sqlSession.getMapper(PriceMapper.class).update(mandatory);
					}
					sqlSession.getMapper(PriceMapper.class).cancelversion(mandatory);
				}

//				Adjustment adjustment = setAdjustment(sqlSession, productid, 7, 7, currency.name(), fromdate, todate, weekprice, "1111111", "week", version);
	
				Adjustment action = new Adjustment();
				action.setCurrency(currency.name());
				action.setState(Adjustment.Created);
				action.setPartyID(altparty);
				action.setProductID(productid);
				action.setFromDate(fromdate);
				action.setToDate(todate);
				action.setMaxStay(999);
				action.setMinStay(5);

				Adjustment example = sqlSession.getMapper(AdjustmentMapper.class).readbyexample(action);
				if (example == null) continue;

				Double rentalPrice;
				if (weekprice.getSpecialoffer() != null && weekprice.getSpecialoffer().getSpecialofferprice() != null) {
					rentalPrice = weekprice.getSpecialoffer().getSpecialofferprice().doubleValue();
				} else {rentalPrice = weekprice.getRentalprice().doubleValue();}
				
				Double dailyprice = DAILYPRICES.get(example.getID());
				Double fixprice = example.getFixPrice();
				Double extra = ((rentalPrice - fixprice) / duration) - dailyprice;// ( ( weekprice - fixprice ) / 7 ) - rentalprice[per/day]

				action.setMinStay(duration);
				action.setMaxStay(duration);
				action.setState(null);

				net.cbtltd.shared.Adjustment exists = sqlSession.getMapper(AdjustmentMapper.class).exists(action);
				if (exists == null) {sqlSession.getMapper(AdjustmentMapper.class).create(action);}
				else {action = exists;}

				action.setExtra(PaymentHelper.getAmountWithTwoDecimals(extra));
				action.setServicedays(Adjustment.WEEK);
				action.setState(Adjustment.Created);
				action.setFixPrice(fixprice);
				action.setVersion(version);

				sqlSession.getMapper(AdjustmentMapper.class).update(action);
				sqlSession.getMapper(AdjustmentMapper.class).cancelversion(action);

				LOG.debug(i++ + " WeekPrice: " + " " + weekprice.getCode() + " " + productid + " " + salesoffice + " " + action.getExtra() + " " + currency + " " + duration);
			}
			sqlSession.commit();
		}
		catch (NullPointerException ex) {
			ex.printStackTrace();
			LOG.error("NPE" + ex.getStackTrace());
			sqlSession.rollback();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			LOG.error("RuntimeExc " + e.getStackTrace());
			sqlSession.rollback();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getClass().getName() + ": " + x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
	}

	/**
	 * read Descriptions
	 */
	public synchronized void readDescriptions() {
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			Language.Code language = Language.Code.en;
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "House/Residence", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Interior", false);

			language = Language.Code.de; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "Haus/Residenz", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Innenbereich", false);

			language = Language.Code.fr; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "La maison/la r�sidence de vacances", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "L'int�rieur du logement", false);

			language = Language.Code.es; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "Haus/Residenz", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Innenbereich", false);
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Read descriptions.
	 *
	 * @param sqlSession the current SQL session.
	 * @param fn the description file name
	 * @param language the ISO language code of the description
	 * @param title the title of the description
	 */
	public void readDescriptions(SqlSession sqlSession, String fn, Language.Code language, String title, boolean first) {
		try {
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.description");
			Unmarshaller um = jc.createUnmarshaller();
			Descriptions descriptions = (Descriptions) um.unmarshal(ftp(fn));
			int i = 0;
			for (Description description : descriptions.getDescription()) {
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), description.getCode()));
				if (product == null) {LOG.error(Error.product_id.getMessage() + " " + description.getCode());}
				else {
					Text action = new Text(product.getPublicId(), language.name());
					Text text = sqlSession.getMapper(TextMapper.class).readbyexample(action);
					if (text == null) {text = new Text(product.getPublicId(), "Description", Text.Type.HTML, new Date(), "", language.name());}
					text.setState(Text.State.Created.name());

					if (first) {text.setNotes(title + "\n" + description.getText());}
					else if (!text.getNotes().startsWith(title)
							&& description.getText() != null 
							&& !description.getText().isEmpty()
							) {text.setNotes(title + "\n" + description.getText() + "\n\n" + text.getNotes());}

					product.setPublicText(text);
					TextService.update(sqlSession, product.getTexts());
					sqlSession.commit();
					LOG.debug(i++ + " " + language.name() + " " + description.getCode() + " = " + product.getId());
				}
				wait(getProductwait());
			}
		}
		catch (Throwable x) {x.getMessage();}
		finally {delete(fn);}
	}

	/**
	 * Test accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts1() {
		Date timestamp = new Date();
		String message = "Interhome testProducts " + getAltpartyid();
		LOG.debug(message);
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {

			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.accommodation");
			Unmarshaller um = jc.createUnmarshaller();
			fn = "accommodation.xml";
			Accommodations accommodations = (Accommodations) um.unmarshal(ftp(fn));
			LOG.debug("accommodation size " + accommodations.getAccommodation().size());
			int i = 0;
			Date version = new Date();
			ArrayList<String> attributes = new ArrayList<String>();
			for (Accommodation accommodation : accommodations.getAccommodation()) {
				try {
					attributes.clear();
					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), accommodation.getCode());
					if (product == null) {continue;}
					addType(attributes, accommodation.getType());
					if (accommodation.getAttributes() != null && accommodation.getAttributes().getAttribute() != null) {
						for (String attribute : accommodation.getAttributes().getAttribute()) {
							addAttribute(attributes, attribute);
						}
					}
					if (accommodation.getDetails() != null) {
						addDetail(attributes, accommodation.getDetails());
					}
					addQuality(attributes, String.valueOf(accommodation.getQuality()));
					if (accommodation.getThemes() != null && accommodation.getThemes().getTheme() != null) {
						for (String theme : accommodation.getThemes().getTheme()) {
							addTheme(attributes, theme);
						}
					}
					RelationService.replace(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
					sqlSession.commit();
					LOG.debug(i++ + " " + accommodation.getCode() + " " + product.getId());
					wait(getProductwait());
				}
				catch (Throwable x) {LOG.error(accommodation + " error " + x.getMessage());}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error("Product Error " + x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Read accommodation products.
	 */
	public synchronized void readProducts() {
		Date version = new Date();
		String message = "Interhome readProducts " + getAltpartyid();
		LOG.debug(message);
		String codeinterhome = null;
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {

			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), version.toString());

			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.accommodation");
			Unmarshaller um = jc.createUnmarshaller();
			fn = "accommodation.xml";
			Accommodations accommodations = (Accommodations) um.unmarshal(ftp(fn));
			int i = 0;
			StringBuilder sb = new StringBuilder();
//			boolean check = true;
			for (Accommodation accommodation : accommodations.getAccommodation()) {
				try {     
//					if (accommodation.getCode().equalsIgnoreCase("DK1026.631.1")) {check = false;}
//					if (accommodation.getCountry().equalsIgnoreCase("FR")) {check = false;}
//					if (check) continue;
//					Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), accommodation.getCode());
					Product product = getProduct(sqlSession, getAltpartyid(), accommodation.getCode());
//					if (product == null) {continue;}
					ArrayList<String> attributes = new ArrayList<String>();

					product.setBathroom(accommodation.getBathrooms() == null ? 2 : accommodation.getBathrooms().intValue());
					product.setRoom(accommodation.getBedrooms() == null ? 2 : accommodation.getBedrooms().intValue());
					net.cbtltd.shared.Country country = sqlSession.getMapper(CountryMapper.class).read(accommodation.getCountry());

					Geodata geodata = accommodation.getGeodata();
					product.setLatitude(geodata == null ? null : geodata.getLat().doubleValue());
					product.setLongitude(geodata == null ? null : geodata.getLng().doubleValue());
					product.setAltitude(0.0);

					product.setPerson(accommodation.getPax() == null ? 2 : accommodation.getPax().intValue());
					product.setRoom(accommodation.getBedrooms() == null ? 1 : accommodation.getBedrooms().intValue());
					product.setToilet(accommodation.getToilets() == null ? 0 : accommodation.getToilets().intValue());
					product.setChild(0);
					product.setSecuritydeposit(Product.DEFAULT_SECUIRTY_DEPOSIT);
					product.setCommission(20.0);
					product.setCleaningfee(Product.DEFAULT_CLEANING_FEE);
					product.setCurrency(country.getCurrency());
//					product.setDiscount(20.0);
					product.setInfant(0);
					String floor = accommodation.getFloor();
					Short space = accommodation.getSqm();
					if (NumberUtils.isNumber(floor)) {
						product.setFloor(Integer.valueOf(floor));
					} else {
						LOG.error("Invalid floor or space value for product id: " + product.getId() + 
								", altid: " + accommodation.getCode());
					}
					product.setSpace((space == null ? "0" : space) + " sq.m");
					
					product.setQuantity(1);
					product.setRank(getRank());
					product.setRating(5);
//					product.setState(Product.CREATED);
					product.setType(Product.Type.Accommodation.name());
					product.setUnit(Unit.DAY);
					product.setWebaddress(accommodation.getUrl());
					product.setVersion(version);

					// Search location by specific Interhome Code.
					codeinterhome = accommodation.getCountry() + "." + accommodation.getPlace();
					Location location = new Location();
					try {
						location.setCodeinterhome(codeinterhome);
						location = sqlSession.getMapper(LocationMapper.class).interhomeSearch(location);
					//location = getLocation(sqlSession, codeinterhome);
					} catch (Throwable x) {
						LOG.error("Location error for Accommodation: " + accommodation.getCode() + ", country: " +
								accommodation.getCountry() + ", region: " + accommodation.getRegion() + ", code: " +
								accommodation.getPlace()  + ". Error: " + x.getMessage());}
					if (location == null) {
						sb.append("\n").append("Accommodation: " + accommodation.getCode() + " country: " + accommodation.getCountry() +
								" region: " + accommodation.getRegion() + " code: " + accommodation.getPlace());
						product.setState(Product.SUSPENDED);
						}
					else {product.setLocationid(location.getId());}

					String name = accommodation.getName();
					product.setName(name == null || name.trim().isEmpty() ? getPropType(accommodation.getType()) + " " + (location == null ? "" : location.getName()) : getPropType(accommodation.getType()) + " " + name.trim());

					sqlSession.getMapper(ProductMapper.class).update(product);

					if (accommodation.getBrand() != null) {product.setValue(Product.Value.Brand.name(), accommodation.getBrand());}
					if (accommodation.getRegion() != null) {product.setValue(Product.Value.Region.name(), accommodation.getRegion());}
					if (accommodation.getMaxrentalprice() != null) {product.setDoubleValue(Product.Value.Maxrentalprice.name(), accommodation.getMaxrentalprice().doubleValue());}
					if (accommodation.getMinrentalprice() != null) {product.setDoubleValue(Product.Value.Minrentalprice.name(), accommodation.getMinrentalprice().doubleValue());}
					if (accommodation.getSqm() != null) {product.setIntegerValue(Product.Value.SquareMetre.name(), accommodation.getSqm().intValue());}
					if (accommodation.getFloor() != null) {product.setValue(Product.Value.Floor.name(), accommodation.getFloor());}

					addType(attributes, accommodation.getType());

					if (accommodation.getAttributes() != null && accommodation.getAttributes().getAttribute() != null) {
						for (String attribute : accommodation.getAttributes().getAttribute()) {
							addAttribute(attributes, attribute);
						}
					}

					if (accommodation.getDetails() != null) {
						addDetail(attributes, accommodation.getDetails());
					}

					addQuality(attributes, String.valueOf(accommodation.getQuality()));

					if (accommodation.getThemes() != null && accommodation.getThemes().getTheme() != null) {
						for (String theme : accommodation.getThemes().getTheme()) {
							addTheme(attributes, theme);
						}
					}

					RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
					RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);
					RelationService.removeDeprecatedData(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);

/*					StringBuilder description = new StringBuilder();
					Distances distances = accommodation.getDistances();
					if (distances != null && distances.getDistance() != null) {
						description.append("Distances to:");
						for (Distance distance : distances.getDistance()) {
							description.append(" ")
							.append(getDistance(distance.getType()))
							.append(" ")
							.append(distance.getValue())
							.append("m ");
						}
						description.append("\n");
					}

					Text action = new Text(product.getPublicId(), Language.Code.en.name());
					Text text = sqlSession.getMapper(TextMapper.class).readbyexample(action);
					if (text == null) {text = new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), "", Language.Code.en.name());}
					text.setState(Text.State.Created.name());
					text.setNotes(description.toString());
					product.setPublicText(text);
					TextService.update(sqlSession, product.getTexts());*/

					if (HasUrls.LIVE && accommodation.getPictures() != null && accommodation.getPictures().getPicture() != null) {
						ArrayList<NameId> images = new ArrayList<NameId>();
						List<Picture> pictures = accommodation.getPictures().getPicture();
						
						int k = 0;
						for (Picture picture : pictures) {
							if (picture.getType().equalsIgnoreCase("M")){
								String imageid = getImage(picture.getType()) + " " + getImage(picture.getSeason());
								images.add(new NameId(imageid, picture.getUrl().replace("partner-medium", "partner-xlarge")));
								pictures.remove(k);
								break;
							}
							k++;
						}
						k = 0;
						while (k < pictures.size()) {
							Picture picture = pictures.get(k);
							if (picture.getType().equalsIgnoreCase("I")){
								String imageid = getImage(picture.getType()) + " " + getImage(picture.getSeason());
								images.add(new NameId(imageid, picture.getUrl().replace("partner-medium", "partner-xlarge")));
								pictures.remove(k);
							} else k++;
						}
						for (Picture picture : pictures) {
							try {
								String imageid = getImage(picture.getType()) + " " + getImage(picture.getSeason());
								images.add(new NameId(imageid, picture.getUrl().replace("partner-medium", "partner-xlarge")));
							}
							catch (Throwable x) {LOG.error("\nURL error " + picture.getUrl());}
						}
						UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
					}
					sqlSession.commit();
					LOG.debug(i++ + " " + accommodation.getCode() + " = " + product.getId());
//					wait(getProductwait());
				}
				catch (Throwable x) {LOG.error(accommodation + " error " + x.getMessage());}
				// TODO: CJM if (i > 10) break;
			}
			Product action = new Product();
			action.setAltpartyid(getAltpartyid());
			action.setState(Product.CREATED);
			action.setVersion(version);
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
			sqlSession.commit();
			
			LOG.debug("Missing Places" + sb.toString());

/*			Language.Code language = Language.Code.en;
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "House/Residence", false);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Interior", false);

			language = Language.Code.de; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "Haus/Residenz", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Innenbereich", false);

			language = Language.Code.fr; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "La maison/la r�sidence de vacances", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "L'int�rieur du logement", false);

			language = Language.Code.es; 
			readDescriptions(sqlSession, "outsidedescription_" + language.name() + ".xml", language, "Haus/Residenz", true);
			readDescriptions(sqlSession, "insidedescription_" + language.name() + ".xml", language, "Innenbereich", false);
			*/
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
		//MonitorService.monitor(message, timestamp);
	}
	
	/**
	 * Gets the product or creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altpartyid the foreign API party ID
	 * @param altid the foreign product ID
	 * @return the product
	 */
	public synchronized static Product getProduct(SqlSession sqlSession, String altpartyid, String altid) {
		Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(altpartyid, altid));
		if (product == null) {
			product = new Product();
			product.setAltpartyid(altpartyid);
			product.setAltid(altid);
			product.setState(Product.INITIAL);
			product.setType(Product.Type.Accommodation.name());
			product.setOrganizationid(altpartyid);
			product.setAltSupplierId(altpartyid);
			product.setSupplierid(altpartyid);
			product.setOwnerid(altpartyid);
			sqlSession.getMapper(ProductMapper.class).create(product);
		}
		RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, altpartyid, product.getId());
		LOG.debug("getProduct  " + altpartyid + ", " + altid + ", " + product.getId());
		return product;
	}

	/**
	 * Gets the location.
	 *
	 * @param sqlSession the current SQL session.
	 * @param codeInterhome the place code.
	 * @return the location object.
	 */
	private static Location getLocation(SqlSession sqlSession, String codeinterhome) {
		Location action = new Location();
		action.setCodeinterhome(codeinterhome);
		action.setState(Location.CREATED);
		action = sqlSession.getMapper(LocationMapper.class).interhomeSearch(action);
		return action;
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
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			RelationService.load(sqlSession, Downloaded.SCHEDULE_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.vacancy");
			Unmarshaller um = jc.createUnmarshaller();
			fn = "vacancy.xml";
			Vacancies vacancies = (Vacancies) um.unmarshal(ftp(fn));
			for (Vacancy vacancy : vacancies.getVacancy()) {
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), vacancy.getCode());
				if (product == null) {continue;}
				String availabilities = vacancy.getAvailability();
				Date startdate = vacancy.getStartday().toGregorianCalendar().getTime();
				Date fromdate = null;
				Date todate = null;
				int index;
				for (index = 0; index < availabilities.length(); index++) {
					char availability = availabilities.charAt(index);
					if (fromdate == null && (availability == 'N' || availability == 'Q')) {fromdate = Time.addDuration(startdate, index, Time.DAY);}
					else if (fromdate != null && availability == 'Y') {
						todate = Time.addDuration(startdate, index, Time.DAY);
						PartnerService.createSchedule(sqlSession, product, fromdate, todate, timestamp);
						fromdate = null;
						todate = null;
					}
				}
				if (todate == null && fromdate != null) {
					todate = Time.addDuration(startdate, index, Time.DAY);
					PartnerService.createSchedule(sqlSession, product, fromdate, todate, timestamp);
				}
				Reservation reservation = new Reservation();
				reservation.setProductid(product.getId());
				reservation.setVersion(timestamp);
				PartnerService.cancelSchedule(sqlSession, reservation);
				sqlSession.commit();
				wait(getSchedulewait());
			}
			readMinStay();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
//		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer minstay values.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readMinStay() {
		Date timestamp = new Date();
		String message = "readMinStay ";
		LOG.debug(message);
		String fn = null;
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.vacancy");
			Unmarshaller um = jc.createUnmarshaller();
			fn = "vacancy.xml";
			Vacancies vacancies = (Vacancies) um.unmarshal(ftp(fn));
			for (Vacancy vacancy : vacancies.getVacancy()) {
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), vacancy.getCode());
				if (product == null) {continue;}
				String minstays = vacancy.getMinstay();
				char prevVal = minstays.charAt(0);
				Date startdate = vacancy.getStartday().toGregorianCalendar().getTime();
				Date fromdate = startdate;
				Date todate = null;
				int value = getMinStay(prevVal);
				int index;
				for (index = 1; index < minstays.length(); index++) {
					char currVal = minstays.charAt(index);
					if (currVal != prevVal) {
						todate = Time.addDuration(startdate, index - 1, Time.DAY);
						PartnerService.createMinStay(sqlSession, value, product, fromdate, todate, timestamp);
						fromdate = Time.addDuration(startdate, index, Time.DAY);
					}
					prevVal = currVal;
					value = getMinStay(currVal);
				}
				todate = Time.addDuration(startdate, index, Time.DAY);
				PartnerService.createMinStay(sqlSession, value, product, fromdate, todate, timestamp);
				PartnerService.deleteMinStay(sqlSession, product, timestamp);
				sqlSession.commit();
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			delete(fn);
		}
		finally {
			sqlSession.close();
			delete(fn);
		}
	}
	
	/**
	 * Read special accommodation prices.
	 */
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "Interhome readSpecials()");
	}
	
	private static HashMap<String,String> PRODUCTS = null;
	
	/**
	 * Creates the map of distance types to descriptions.
	 *
	 * @param type the type of distance
	 * @return the description of the distance
	 */
	private static final String getProductId(String altid) {
		if (PRODUCTS == null) {
			PRODUCTS = new HashMap<String, String>();
		}
		if (PRODUCTS.get(altid) == null) {return null;}
		else {return PRODUCTS.get(altid);}
	}

	private static HashMap<String,String> DISTANCES = null;
	
	/**
	 * Creates the map of distance types to descriptions.
	 *
	 * @param type the type of distance
	 * @return the description of the distance
	 */
	private static final String getDistance(String type) {
		if (DISTANCES == null) {
			DISTANCES = new HashMap<String, String>();
			DISTANCES.put("center","Center of Town");
			DISTANCES.put("golf","Golf Course");
			DISTANCES.put("lake","Lake or River");
			DISTANCES.put("publictransport","Public Transport");
			DISTANCES.put("sea","Sea or Beach");
			DISTANCES.put("ski","Ski Lifts");
		}
		if (DISTANCES.get(type) == null) {return type;}
		else {return DISTANCES.get(type);}
	}

	private static HashMap<String,String> TYPES = null;
	
	/**
	 * Creates the map of property types to OTA codes.
	 *
	 * [Detached: D, Townhouse: T, Apts: A]
	 * @param attributes the attributes
	 * @param type the type
	 */
	private static final void addType(ArrayList<String> attributes, String type) {
		if (TYPES == null) {
			TYPES = new HashMap<String, String>();
			TYPES.put("A","PCT3");
			TYPES.put("D","PCT16");
			TYPES.put("T","PCT8");
		}
		if (TYPES.get(type) != null) {attributes.add(TYPES.get(type));}
	}

	private static HashMap<String,String> DETAILS = null;
	
	/**
	 * Creates the map of property detail to OTA codes.
	 *
	A : Apart- Hotel
	B : Bungalow
	C : Chalet
	D : Divers
	F : Farmhouse
	H : Holiday Village
	R : Residence
	S : Castle / Mansion
	V : Villa
	Y : Yacht
	 * @param attributes the attributes
	 * @param detail the type
	 */
	private static final void addDetail(ArrayList<String> attributes, String detail) {
		if (DETAILS == null) {
			DETAILS = new HashMap<String, String>();
			DETAILS.put("A","PCT3");
			DETAILS.put("B","PCT5");
			DETAILS.put("C","PCT7");
			//			DETAILS.put("D","RSP82");
			DETAILS.put("F","PCT15");
			DETAILS.put("H","PCT18");
			DETAILS.put("R","PCT16");
			DETAILS.put("S","PCT37");
			DETAILS.put("V","PCT35");
			DETAILS.put("Y","PCT44");
		}
		if (DETAILS.get(detail) != null) {attributes.add(DETAILS.get(detail));}
	}

	/**
	 * Creates the map of property quality to OTA codes.
	 * 0: not rated yet
	 * 1: basic
	 * 2: average
	 * 3: above average
	 * 4: top quality
	 * 5: luxurious
	 * @param attributes the attributes
	 * @param quality the type
	 */
	private static final void addQuality(ArrayList<String> attributes, String quality) {
		attributes.add("GRD" + quality);
	}

	private static HashMap<String,String> THEMES = null;
	
	/**
	 * Adds the theme.
	 *
	 * @param attributes the attributes
	 * @param detail the detail
	 */
	private static final void addTheme(ArrayList<String> attributes, String detail) {
		if (THEMES == null) {
			THEMES = new HashMap<String, String>();
			THEMES.put("citytrips", "LOC3"); // OK
			THEMES.put("country", "LOC23"); // OK
			THEMES.put("lakes and mountains", "LOC7");  // OK part
			THEMES.put("sun and beach", "RSN1");
			THEMES.put("ski / winter", "RSN6");
			THEMES.put("in a lakeside town", "LOC7"); // OK
			THEMES.put("villas with pool", "PCT34"); // OK
			//			THEMES.put("family", "GRP3");
			THEMES.put("nightlife", "RSN18");
			THEMES.put("somewherequiet", "RSN19");
			//			THEMES.put("special property", "SIZ32");
			THEMES.put("farmhouse", "LOC23");
		}
		if (THEMES.get(detail) != null) {attributes.add(THEMES.get(detail));}
	}

	private static HashMap<String,String> ATTRIBUTES = null;
	
	/**
	 * Attributes map.
	 *
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	private static final void addAttribute(ArrayList<String> attributes, String attribute) {

		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("bathandshower", "HAC263");
			ATTRIBUTES.put("bath", "HAC264");
			ATTRIBUTES.put("shower", "HAC265");
			ATTRIBUTES.put("nonsmoking", "HAC198");
			ATTRIBUTES.put("petsallowed", "PET7");
			ATTRIBUTES.put("hikingmountains", "RSN10");
			ATTRIBUTES.put("hikingplains", "RSN10");
			ATTRIBUTES.put("mountainbike", "RSP10");
			ATTRIBUTES.put("bikingplains", "RSN10");
			ATTRIBUTES.put("sailing", "RSN14");
			ATTRIBUTES.put("surfing", "RSN15");
			ATTRIBUTES.put("crosscountryski", "RSN6");
			ATTRIBUTES.put("icerink", "RST84");
			ATTRIBUTES.put("skiaera", "RSN6");
			ATTRIBUTES.put("snowboard", "RSN6");
			ATTRIBUTES.put("winterwalking", "RSN6");
			ATTRIBUTES.put("golfing", "RSN5");
			ATTRIBUTES.put("riding", "RSN16");
			ATTRIBUTES.put("tennis", "RSN17");
			ATTRIBUTES.put("themepark", "RSN2");
			ATTRIBUTES.put("breakfast", "HAC138");
			ATTRIBUTES.put("elevator", "HAC33");
			ATTRIBUTES.put("garden", "FAC4");
			ATTRIBUTES.put("solarium", "RSN12");
			ATTRIBUTES.put("fitness", "RSN12");
			ATTRIBUTES.put("massage", "RSN12");
			ATTRIBUTES.put("whirlpool", "FAC7");
			ATTRIBUTES.put("seaview", "LOC16");
			ATTRIBUTES.put("childrenplayground", "HAC193");
			ATTRIBUTES.put("airconditionning", "FAC1");
			ATTRIBUTES.put("balcony", "FAC2");
			ATTRIBUTES.put("terrace", "FAC2");
			ATTRIBUTES.put("bbq", "HAC118");
			ATTRIBUTES.put("babycot", "RMA26");
			ATTRIBUTES.put("dishwasher", "RMA32");
			ATTRIBUTES.put("fireplace", "RMA41");
			ATTRIBUTES.put("parking", "HAC63");
			ATTRIBUTES.put("parkingprivate", "HAC63");
			ATTRIBUTES.put("parkingcovered", "HAC63");
			ATTRIBUTES.put("sauna", "HAC79");
			ATTRIBUTES.put("pool", "HAC71");
			ATTRIBUTES.put("poolprivat", "HAC253");
			ATTRIBUTES.put("poolindor", "HAC54");
			ATTRIBUTES.put("poolheated", "HAC49");
			ATTRIBUTES.put("poolchildren", "HAC48");
			ATTRIBUTES.put("tv", "RMA90");
			ATTRIBUTES.put("washingmachine", "RMA31");
			ATTRIBUTES.put("internet", "HAC223");
			//			ATTRIBUTES.put("isdn", "BUS14");
			ATTRIBUTES.put("wlan", "HAC221");
			ATTRIBUTES.put("cleaning", "HAC51");
			ATTRIBUTES.put("dvdplayer", "RMA163");
			ATTRIBUTES.put("tabletennis", "RMA140");
			ATTRIBUTES.put("microwave", "RMA68");
			ATTRIBUTES.put("oven", "RMA77");
			ATTRIBUTES.put("telephon", "RMA107");
		}
		if (ATTRIBUTES.get(attribute) != null) {attributes.add(ATTRIBUTES.get(attribute));}
	}

	private static HashMap<String, String> IMAGES = null;

	/**
	 * Gets the image.
	 *
	 * @param value the image code.
	 * @return the image description.
	 */
	private static String getImage(String value) {
		if (IMAGES == null) {
			IMAGES = new HashMap<String, String>();
			IMAGES.put("M", "Main Picture");
			IMAGES.put("I", "Interior Picture");
			IMAGES.put("O", "Exterior View");
			IMAGES.put("S", "Summer Season");
			IMAGES.put("W", "Winter Season");
		}
		if (value == null || IMAGES.get(value.toUpperCase()) == null) {return null;}
		else {return IMAGES.get(value.toUpperCase());}
	}

	private static HashMap<String, String> SERVICES = null;

	/**
	 * Gets the servicename.
	 *
	 * @param value the service code.
	 * @return the service description.
	 */
	private static String getServicename(String value) {
		if (SERVICES == null) {
			SERVICES = new HashMap<String, String>();
			SERVICES.put("AC", "Air-conditioning");
			SERVICES.put("BF", "Breakfast");
			SERVICES.put("BFC", "Breakfast - children");
			SERVICES.put("BG", "Booking fee");
			SERVICES.put("BIK1", "Fahrrad");
			SERVICES.put("BIK2", "Fahrrad");
			SERVICES.put("BOOS", "Booster seat");
			SERVICES.put("BUGY", "Buggy");
			SERVICES.put("BW", "Bed linen (initial supply)");
			SERVICES.put("BW2", "Bed linen 2nd set");
			SERVICES.put("CF", "Emergency Fund");
			SERVICES.put("CM", "Cleaning between bookings");
			SERVICES.put("COT", "Cot (up to 2 years)");
			SERVICES.put("DC", "Breakage deposit in cash or by credit card (Visa/Mastercard)");
			SERVICES.put("DE", "Breakage deposit in cash");
			SERVICES.put("DES", "Deposit (will be refunded after optional local charges have been deducted)");
			SERVICES.put("DISC", "Company discount");
			SERVICES.put("DK", "Breakage deposit by credit card Visa / Mastercard");
			SERVICES.put("EFC", "Final Cleaning (Please note: included for stays less than 7 days)");
			SERVICES.put("EG", "Power costs");
			SERVICES.put("EH", "Heating");
			SERVICES.put("EL", "Electricity");
			SERVICES.put("FBAS", "Welcome food basket");
			SERVICES.put("FC", "Final cleaning");
			SERVICES.put("GA", "Gas");
			SERVICES.put("GC1", "Guest Card");
			SERVICES.put("GC2", "Guest Card");
			SERVICES.put("GR", "Garage");
			SERVICES.put("HB", "Half-board");
			SERVICES.put("HBC", "Half-board - children");
			SERVICES.put("HCHR", "Highchair");
			SERVICES.put("HICO", "Hidden Cost Service");
			SERVICES.put("INS", "Elvia Insurance");
			SERVICES.put("INSA", "Insurance");
			SERVICES.put("KW", "Tea Towels");
			SERVICES.put("LEG1", "Legoland Eintritt");
			SERVICES.put("LEG2", "Legoland Frühstück");
			SERVICES.put("LI", "Laundry (initial supply of bed linen and towels)");
			SERVICES.put("LI2", "Laundry (second supply of bed linen and towels)");
			SERVICES.put("LIB", "Bedmaking Service");
			SERVICES.put("LIBT", "bedlinen (bedding included) &amp; towels");
			SERVICES.put("LIW", "Weekly laundry (bed linen and towels)");
			SERVICES.put("LT", "Local tax");
			SERVICES.put("LTC", "Local tax children");
			SERVICES.put("LTI", "Environmental tax");
			SERVICES.put("LTM", "enrollment / registration fee");
			SERVICES.put("OIL", "Oil");
			SERVICES.put("PE", "Pool Entry");
			SERVICES.put("PET", "Pet");
			SERVICES.put("PH", "Pool heating");
			SERVICES.put("PS", "Parking");
			SERVICES.put("PSR", "Parking");
			SERVICES.put("SAUN", "Sauna");
			SERVICES.put("SFC", "Final cleaning");
			SERVICES.put("SP", "Service Package");
			SERVICES.put("TEL", "Telephone");
			SERVICES.put("TF", "Transfer Hurghada - El Gouna (one way)");
			SERVICES.put("TFI", "Transfer Information");
			SERVICES.put("TW", "Towels (initial supply)");
			SERVICES.put("TW2", "Towels 2nd set");
			SERVICES.put("UFCT", "Final cleaning and Bed linen / towels for stays shorter than 6 nights");
			SERVICES.put("WA", "Water");
			SERVICES.put("WIFI", "wireless internet access (WIFI)");
			SERVICES.put("WIFL", "wireless internet access (WIFI)");
			SERVICES.put("WK", "Welcome Kit");
			SERVICES.put("WOOD", "wood");
			SERVICES.put("XBED", "Extra bed");
		}
		if (value == null || SERVICES.get(value.toUpperCase()) == null) {return value;}
		else {return SERVICES.get(value.toUpperCase());}
	}
	
	private static HashMap<Character, Integer> MINSTAY = null;

	/**
	 * Gets the minstay value.
	 *
	 * @param value of the minstay char code.
	 * @return the number of days.
	 */
	private static Integer getMinStay(char value) {
		if (MINSTAY == null) {
			int i = 1;
			MINSTAY = new HashMap<Character, Integer>();
			for(char alphabet = 'A'; alphabet <= 'Z';alphabet++){
				MINSTAY.put(alphabet, i);
				i++;
			 }
		}
		if (String.valueOf(value) == null || MINSTAY.get(value) == null) {return 0;}
		else {return MINSTAY.get(value);}
	}

	private static HashMap<String, String> SPECIALS = null;

	/**
	 * Gets the special.
	 *
	 * @param value the special code.
	 * @return the yield type.
	 */
	private static String getSpecial(String value) {
		if (SPECIALS == null) {
			SPECIALS = new HashMap<String, String>();
			SPECIALS.put("EB/00000001", Yield.EARLY_BIRD);
			SPECIALS.put("FD/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000002", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000003", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000004", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000005", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000006", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000007", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000008", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000009", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000010", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000011", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000012", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000013", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000014", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000015", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000016", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000017", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000018", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000019", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000020", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000021", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FD/00000022", Yield.LENGTH_OF_STAY);
			SPECIALS.put("FR/00000109", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JD/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JD/00000002", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JD/00000003", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000002", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000003", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000004", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000005", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000006", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000007", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000008", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000009", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000010", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000011", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000012", Yield.LAST_MINUTE);
			SPECIALS.put("JF/00000020", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000021", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000022", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000023", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000024", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000025", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000026", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000027", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000028", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000029", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000030", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000031", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000032", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000033", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000034", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000035", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000036", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000037", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000038", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000039", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000040", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000041", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000042", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000043", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000044", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000045", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000046", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000047", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000048", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000049", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000050", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000051", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000052", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000053", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000054", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000055", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000056", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000057", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000058", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000059", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000060", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000061", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000062", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000063", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000064", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000065", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000066", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000067", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000068", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000075", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000076", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000077", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000078", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000079", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000080", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000081", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000082", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000083", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000084", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000085", Yield.EARLY_BIRD);
			SPECIALS.put("JF/00000086", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000087", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000088", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000089", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000090", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000091", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000092", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000093", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000094", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000095", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000096", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000097", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000098", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000099", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000100", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000101", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000102", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000103", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000104", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000105", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000106", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000107", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000108", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000110", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000111", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000112", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000113", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000114", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000115", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000116", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000117", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000118", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000119", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000120", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000121", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000122", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000123", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000124", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000125", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000126", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000127", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000128", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000129", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000130", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000131", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000132", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000133", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000134", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000135", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000136", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000137", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000138", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000139", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000140", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000141", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000142", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000143", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000144", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000145", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000146", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000147", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000148", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000149", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000150", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000151", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000152", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000153", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000154", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000155", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000156", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000157", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000158", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000159", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000160", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000161", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000162", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000163", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000201", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000202", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000203", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000204", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000205", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000206", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000207", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000208", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000209", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000210", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000211", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000212", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000213", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000214", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000215", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000216", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000217", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000218", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000219", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000220", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000221", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000222", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000223", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000224", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000225", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000226", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000227", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000228", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000229", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000230", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000231", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000232", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000233", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000234", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000235", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000236", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000237", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000238", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000239", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000240", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000241", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000242", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000243", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000244", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000245", Yield.LENGTH_OF_STAY);
			SPECIALS.put("JF/00000246", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LM/00000001", Yield.LAST_MINUTE);
			SPECIALS.put("LO/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000002", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000003", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000004", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000005", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000006", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000007", Yield.LENGTH_OF_STAY);
			SPECIALS.put("LS/00000028", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000002", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000003", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000004", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000005", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000006", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000007", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000008", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000009", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000010", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000011", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000012", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000013", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000014", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000015", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000016", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000017", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000018", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000019", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000020", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000021", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000022", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000023", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000024", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000025", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000026", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000027", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000028", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000029", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000030", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000031", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000032", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000033", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000034", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000035", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000036", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000037", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000038", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000039", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000040", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000050", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000059", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000060", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000061", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000062", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000063", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000064", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000065", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000066", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000067", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000068", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000069", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000070", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000071", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000072", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000073", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000074", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000075", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000076", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000078", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000079", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000080", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000081", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000082", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000083", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000085", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000086", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000087", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000088", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000089", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000090", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000091", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000092", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000093", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000094", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000095", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000096", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000098", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000099", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000100", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000101", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000102", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000105", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000106", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000107", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000108", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000111", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000112", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000119", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000120", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000121", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000122", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000123", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000124", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000125", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000126", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000127", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000128", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000150", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000151", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000152", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000153", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000154", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000155", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000156", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000157", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000158", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000159", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000160", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000161", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000162", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000163", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000164", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000165", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000166", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000167", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000168", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000169", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000170", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000171", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000180", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000181", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000182", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000183", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000184", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000185", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000186", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000187", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000188", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000189", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000190", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000191", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000192", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000193", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000197", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000198", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000199", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000200", Yield.LAST_MINUTE);
			SPECIALS.put("PR/00000201", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000202", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000203", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000204", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000205", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000206", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000207", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000208", Yield.LAST_MINUTE);
			SPECIALS.put("PR/00000209", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000210", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000211", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000212", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000213", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000214", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000215", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000216", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000217", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000218", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000219", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000220", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000221", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000222", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000223", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000224", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000225", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000237", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000238", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000239", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000240", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000241", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000242", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000243", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000244", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000245", Yield.LAST_MINUTE);
			SPECIALS.put("PR/00000246", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000247", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000248", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000249", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000250", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000251", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000252", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000253", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000254", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000298", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000299", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000300", Yield.LAST_MINUTE);
			SPECIALS.put("PR/00000301", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000302", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000303", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000304", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000305", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000306", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000307", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000308", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000309", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000310", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000311", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000312", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000313", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000314", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000320", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000321", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000322", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000323", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000324", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000325", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000326", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000327", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000328", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000330", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000331", Yield.EARLY_BIRD);
			SPECIALS.put("PR/00000332", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000333", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000334", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000335", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000336", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000337", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000338", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000339", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000340", Yield.LENGTH_OF_STAY);
			SPECIALS.put("PR/00000341", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SOLDES/00000001", Yield.EARLY_BIRD);
			SPECIALS.put("SR/00000001", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000002", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000003", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000004", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000005", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000006", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000007", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000008", Yield.LENGTH_OF_STAY);
			SPECIALS.put("SR/00000009", Yield.LENGTH_OF_STAY);
		}
		if (value == null || SPECIALS.get(value.toUpperCase()) == null) {return value;}
		else {return SPECIALS.get(value);}
	}

	private static HashMap<AdditionalServiceType, String> TYPE = null;

	/**
	 * Gets the type.
	 *
	 * @param value the special code.
	 * @return the yield type.
	 */
	private static String getType(AdditionalServiceType value) {
		if (TYPE == null) {
			TYPE = new HashMap<AdditionalServiceType, String>();
			TYPE.put(AdditionalServiceType.BOOKABLE_SERVICE_NO_COST, net.cbtltd.shared.Price.OPTIONAL);
			TYPE.put(AdditionalServiceType.BOOKABLE_SERVICE_ON_INVOICE, net.cbtltd.shared.Price.MANDATORY);
			TYPE.put(AdditionalServiceType.BOOKBALE_SERVICE_ON_PLACE, net.cbtltd.shared.Price.OPTIONAL);
			TYPE.put(AdditionalServiceType.BOOKABLE_ON_PLACE_PAYABLE_ON_PLACE, net.cbtltd.shared.Price.OPTIONAL);
			TYPE.put(AdditionalServiceType.COMMISSION_DISCOUNT, net.cbtltd.shared.Price.COMMISSION);
			TYPE.put(AdditionalServiceType.COSTS_ON_INVOICE, net.cbtltd.shared.Price.MANDATORY);
			TYPE.put(AdditionalServiceType.EXTRACOST_ON_PLACE, net.cbtltd.shared.Price.MANDATORY);
			TYPE.put(AdditionalServiceType.IN_PRICE_INCLUDED, net.cbtltd.shared.Price.INCLUDED);
			TYPE.put(AdditionalServiceType.NOT_SET, null);
			TYPE.put(AdditionalServiceType.SELF_ORGANISED, null);
		}
		if (value == null || TYPE.get(value) == null) {return null;}
		else {return TYPE.get(value);}
	}
	
	private static HashMap<String, String> PROPTYPE = null;
	
	/**
	 * Gets the property type.
	 *
	 * @param value the image code.
	 * @return the image description.
	 */
	private static String getPropType(String value) {
		if (PROPTYPE == null) {
			PROPTYPE = new HashMap<String, String>();
			PROPTYPE.put("H", "House");
			PROPTYPE.put("A", "Apartment");
			PROPTYPE.put("D", "Holiday House");
			PROPTYPE.put("T", "Townhouse");
		}
		if (value == null || PROPTYPE.get(value.toUpperCase()) == null) {return null;}
		else {return PROPTYPE.get(value.toUpperCase());}
	}

	/**
	 * Gets the retailer code for the currency code.
	 *
	 * @param currency the currency code.
	 * @return the retailer code.
	 */
	private String getRetailerCode (String currency) {
/*		if (net.cbtltd.shared.Currency.Code.AUD.name().equalsIgnoreCase(currency)) {return "CH1000768";}
		else if (net.cbtltd.shared.Currency.Code.CAD.name().equalsIgnoreCase(currency)) {return "CH1000768";}
		else if (net.cbtltd.shared.Currency.Code.GBP.name().equalsIgnoreCase(currency)) {return "CH1000770";}
		else if (net.cbtltd.shared.Currency.Code.USD.name().equalsIgnoreCase(currency)) {return "CH1000768";}
		else {return "CH1000769";}*/
		return "CH1001116";
	}

	/**
	 * Gets the sales office code.
	 *
	 * @param currency the currency code.
	 * @return the sales office code.
	 */
	private String getSalesOfficeCode (String currency) {
		if (net.cbtltd.shared.Currency.Code.GBP.name().equalsIgnoreCase(currency)) {return "2047";}
		else if (net.cbtltd.shared.Currency.Code.USD.name().equalsIgnoreCase(currency)) {return "2052";}
		else return "2048";
	}

	private Adjustment setAdjustment(SqlSession sqlSession, String productid, int minstay, int maxstay, String currency, Date fromdate, Date todate, Price price, byte servicedays, String pricetype, Date version){
		Adjustment action = new Adjustment();
		action.setProductID(productid);
		action.setPartyID(getAltpartyid());
		action.setMinStay(minstay);
		action.setMaxStay(maxstay);
		action.setCurrency(currency);
		action.setFromDate(fromdate);
		action.setToDate(todate);
		
		net.cbtltd.shared.Adjustment exists = sqlSession.getMapper(AdjustmentMapper.class).exists(action);
		if (exists == null) {sqlSession.getMapper(AdjustmentMapper.class).create(action);}
		else {action = exists;}
		
		Double extra = 0.0;
		BigDecimal rentalprice = price.getRentalprice();
		if (pricetype.equals("midweek")) {extra = (price.getMidweekrentalprice().subtract(rentalprice)).doubleValue();}
		if (pricetype.equals("weekend")) {extra = (price.getWeekendrentalprice().subtract(price.getRentalprice())).doubleValue();}
		
		action.setExtra(extra);
		action.setState(Adjustment.Created);
		action.setFixPrice(price.getFixprice().doubleValue());
		action.setServicedays(servicedays);
		action.setVersion(version);

		sqlSession.getMapper(AdjustmentMapper.class).update(action);
		
		return action;
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
			if (reservation.notActive()) {throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());}

			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			if (reservation.noAgentid()) {throw new ServiceException(Error.reservation_agentid);}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) {reservation.setCustomerid(Party.NO_ACTOR);}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}

			double oldQuote = reservation.getQuote();
			computePrice(sqlSession, reservation, product.getAltid(), reservation.getCurrency());
			
			if(oldQuote != reservation.getQuote()) {
				throw new ServiceException(Error.price_not_match, "old: " + oldQuote + ", new: " + reservation.getQuote());
			}

			ClientBookingInputValue rq = createClientBookingRequest(product, reservation, agent, customer, creditCard);

			LOG.debug("\nclientBooking rq=" + rq + "\n");
			ClientBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).clientBooking(rq);
			LOG.debug("\nclientBooking rs=" + rs + "\n");

			if (rs.isOk()) {
				reservation.setAltid(rs.getBookingID());
				reservation.setAltpartyid(getAltpartyid());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
			}
			else {
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				result.put(GatewayHandler.ERROR_MSG, rs.getErrors().getError().get(0).getDescription());
				return result;
//				throw new ServiceException(Error.reservation_api, rs.getErrors().getError().get(0).getDescription());
			}
		}
		catch (ServiceException e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			throw new ServiceException(e.getError(), e.getMessage());	
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
		return result;
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
		throw new ServiceException(Error.service_absent, "Interhome inquireReservation()");
	}
}

///**
// * Create customer reservation.
// *
// * @param sqlSession the current SQL session.
// * @param reservation the reservation to be created.
// */
//private String createReservationCustomer(SqlSession sqlSession, Reservation reservation) throws Throwable {
//	Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//	if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
//	Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
//	if (customer == null) {throw new ServiceException(Error.party_id, reservation.getCustomerid());}
//
//	ClientBookingInputValue rq = new ClientBookingInputValue();
//	rq.setAccommodationCode(product.getAltid());
//	//        rq.setAdditionalServices(value);
//	rq.setAdults(reservation.getAdult());
//	rq.setBabies(reservation.getInfant());
//	rq.setCheckIn(format(reservation.getFromdate()));
//	rq.setCheckOut(format(reservation.getTodate()));
//	rq.setChildren(reservation.getChild());
//	rq.setComment(reservation.getNotes());
//
//	Finance finance = sqlSession.getMapper(FinanceMapper.class).read(reservation.getFinanceid());
//	if (finance != null) {
//		if (finance.hasType(Finance.Type.Bank.name())) {
//			rq.setPaymentType(PaymentType.INVOICE);
//			Party accountholder = sqlSession.getMapper(PartyMapper.class).read(finance.getOwnerid());
//			if (accountholder == null) {throw new ServiceException(Error.party_id, finance.getOwnerid());}
//			rq.setBankAccountHolder(accountholder.getName());
//			rq.setBankAccountNumber(finance.getAccountnumber());
//			rq.setBankCode(finance.getBranchnumber());
//		}
//		else if (finance.hasType(Finance.Type.Card.name())) {
//			rq.setPaymentType(PaymentType.CREDIT_CARD);
//			rq.setCreditCardCvc(finance.getCode());
//			rq.setCreditCardExpiry(finance.getYyyymm());
//			rq.setCreditCardNumber(finance.getAccountnumber());
//			if (finance.hasCardType(Finance.CardType.MC)) {rq.setCreditCardType(CCType.MASTER);}
//			else if (finance.hasCardType(Finance.CardType.VI)) {rq.setCreditCardType(CCType.VISA);}
//			else {rq.setCreditCardType(CCType.NOT_SET);}
//		}
//		else {rq.setPaymentType(PaymentType.NOT_SET);}
//	}
//	else {rq.setPaymentType(PaymentType.NOT_SET);}
//
//	rq.setCustomerAddressCountryCode(customer.getCountry());
//	rq.setCustomerAddressPlace(customer.getAddress(0));
//	rq.setCustomerAddressState(customer.getRegion());
//	rq.setCustomerAddressStreet(customer.getAddress(1));
//	rq.setCustomerAddressZIP(customer.getPostaladdress());
//	rq.setCustomerFirstName(customer.getFirstName());
//	rq.setCustomerName(customer.getFamilyName());
//	rq.setCustomerEmail(customer.getEmailaddress());
//	rq.setCustomerPhone(customer.getDayphone());
//	rq.setLanguageCode(Language.Code.en.name());
//	rq.setRetailerCode(getRetailerCode(reservation.getCurrency())); //"CH1000725"
//	rq.setSalesOfficeCode(getSalesOfficeCode(reservation.getCurrency())); //"2052"
//	LOG.debug("clientBooking rq=" + rq);
////	ClientBookingReturnValue rs = getPort().clientBooking(rq);
//	ClientBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).clientBooking(rq);
//	LOG.debug("clientBooking rs=" + rs);
//	if (rs.getErrors() == null) {return rs.getBookingID();}
//	else {throw new ServiceException(Error.product_not_available, rs.getErrors().getError().get(0).getDescription());}
//}
//
///**
// * Create retailer reservation.
// *
// * @param sqlSession the current SQL session.
// * @param reservation the reservation to be created.
// */
//private String createReservationAgent(SqlSession sqlSession, Reservation reservation) throws Throwable {
//	Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//	if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
//	Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
//	if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
//
//	RetailerBookingInputValue rq = new RetailerBookingInputValue();
//	rq.setAccommodationCode(product.getAltid());
//	//        rq.setAdditionalServices(value);
//	rq.setAdults(reservation.getAdult());
//	rq.setBabies(reservation.getInfant());
//	rq.setCheckIn(format(reservation.getFromdate()));
//	rq.setCheckOut(format(reservation.getTodate()));
//	rq.setChildren(reservation.getChild());
//	rq.setComment(reservation.getNotes());
//
//	Finance finance = sqlSession.getMapper(FinanceMapper.class).read(reservation.getFinanceid());
//	if (finance != null) {
//		if (finance.hasType(Finance.Type.Bank.name())) {
//			rq.setPaymentType(PaymentType.INVOICE);
//			Party accountholder = sqlSession.getMapper(PartyMapper.class).read(finance.getOwnerid());
//			if (accountholder == null) {throw new ServiceException(Error.party_id, finance.getOwnerid());}
//			rq.setBankAccountHolder(accountholder.getName());
//			rq.setBankAccountNumber(finance.getAccountnumber());
//			rq.setBankCode(finance.getBranchnumber());
//		}
//		else if (finance.hasType(Finance.Type.Card.name())) {
//			rq.setPaymentType(PaymentType.CREDIT_CARD);
//			rq.setCreditCardCvc(finance.getCode());
//			rq.setCreditCardExpiry(finance.getYyyymm());
//			rq.setCreditCardNumber(finance.getAccountnumber());
//			if (finance.hasCardType(Finance.CardType.MC)) {rq.setCreditCardType(CCType.MASTER);}
//			else if (finance.hasCardType(Finance.CardType.VI)) {rq.setCreditCardType(CCType.VISA);}
//			else {rq.setCreditCardType(CCType.NOT_SET);}
//		}
//		else {rq.setPaymentType(PaymentType.NOT_SET);}
//	}
//	else {rq.setPaymentType(PaymentType.NOT_SET);}
//
//	Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
//	if (customer != null) {
//		rq.setCustomerFirstName(customer.getFirstName());
//		rq.setCustomerName(customer.getFamilyName());
//		//rq.setCustomerSalutationType(value)
//	}
//	rq.setRetailerCode(agent.getAltid());
//	rq.setLanguageCode(agent.getLanguage());
//	rq.setRetailerCode(getRetailerCode(reservation.getCurrency())); //"CH1000725"
//	rq.setSalesOfficeCode(getSalesOfficeCode(reservation.getCurrency())); //"2052"
//	LOG.debug("retailerBooking rq=" + rq);
////	JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rq, System.out);
////	RetailerBookingReturnValue rs = getPort().retailerBooking(rq);
//	RetailerBookingReturnValue rs = getAuthenticatedPort(getRetailerCode(reservation.getCurrency())).retailerBooking(rq);
//	LOG.debug("retailerBooking rs=" + rs);
////	JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
//	if (rs.getErrors() == null) {return rs.getBookingID();}
//	else {throw new ServiceException(Error.product_not_available, rs.getErrors().getError().get(0).getDescription());}
//}
