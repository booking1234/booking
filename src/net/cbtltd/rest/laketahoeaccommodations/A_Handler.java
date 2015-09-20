/*
 * @author David Lepe and Devin Held
 * Version 1.0
 */

package net.cbtltd.rest.laketahoeaccommodations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;
import com.mybookingpal.storage.exception.StorageException;

import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
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
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

public class A_Handler extends PartnerHandler implements IsPartner {
	// private static final int EIGHTEEN_MONTHS = 548;
	private static final double BOOKFEE = 30.00;
	private static final double SERVICECHARGE = 0.10;
	private static final double DEPOSITPERCENTAGE = 0.40;
	private static final int FTP_ID_START_NUM = 1001;
	private static final String IMAGES_LOCATION = "ftp.japeck.com";
	private static final String IMAGES_USERNAME = "mybookingpal@japeck.com";
	private static final String IMAGES_PASSWORD = "HH88bb567a";
	private static final String LAKE_TAHOE_AFFILIATE_NUMBER = "153980";
	private static final String RESERVATION_REQUEST_COLUMN_HEADER = "Request Number,Reservation,Property ID,Arrive Date,Depart Date,Number Nights,RentalAmtOnly,Booking Fee,ResSvc / Clng,Rental Amt,OcpTax Amt,Insure Amt,Total Amt,Deposit Amt,Special Rate,Nmbr Guests,First Name,Last Name,Street Address P.O.box,City,State,Zip Code,Daytime Phone,Alternate Phone,E-Mail Address,E-Mail LTA Newsletter,Credit Card Type,Credit Card Number,CCard Expiration Date,CSV Number,Sales Agent,Guest Comments,Agent Comments,Affiliate ID";
	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private static HashMap<String, String> ATTRIBUTES = null;
	// private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");
	private static final DateFormat LTA_DF_WITH_TIME = new SimpleDateFormat(
			"MM/dd/yyyy hh:mm:ss a");
	private static final DateFormat LTA_DF = new SimpleDateFormat("MM/dd/yyyy");
	private static HashMap<Integer, Double> priceTable = null;
	private static String PAYMENT_INFO_FOR_QUOTES = "Included in the price";
	private static String CREDIT_CARD_NOTE = "\r\nYOUR CREDIT CARD WILL NOT BE PROCESSED. IT IS ONLY TAKEN FOR SECURITY PURPOSES TO SECURE RESERVATION. THE PROPERTY MANAGER WILL CONTACT YOU FOR YOUR CREDIT CARD DETAILS";
	static {
		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("", "");
			ATTRIBUTES.put("AMEN", "HAC180");
			ATTRIBUTES.put("BDBK", "PCT4");
			ATTRIBUTES.put("BIKE", "RSP6");
			ATTRIBUTES.put("CITY", "LOC4");
			ATTRIBUTES.put("CTEN", "RST94");
			ATTRIBUTES.put("CTUB", "CBF25");
			ATTRIBUTES.put("DOCK", "ACC7");
			ATTRIBUTES.put("DUPL", "");
			ATTRIBUTES.put("GOLF", "RST27");
			ATTRIBUTES.put("GRUP", "");
			ATTRIBUTES.put("HOUS", "PCT34");
			ATTRIBUTES.put("HTUB", "RST104");
			ATTRIBUTES.put("INET", "RMA54");
			ATTRIBUTES.put("JACZ", "RST107");
			ATTRIBUTES.put("LGON", "LOC33");
			ATTRIBUTES.put("LKFT", "LOC7");
			ATTRIBUTES.put("LKVW", "RVT8");
			ATTRIBUTES.put("LVRP", "LOC10");
			ATTRIBUTES.put("NBCH", "LOC2");
			ATTRIBUTES.put("NBHD", "LOC13");
			ATTRIBUTES.put("NCAS", "ACC12");
			ATTRIBUTES.put("NSKI", "ACC45");
			ATTRIBUTES.put("NSMO", "RMA74");
			ATTRIBUTES.put("NVEW", "");
			ATTRIBUTES.put("PBCH", "ACC5");
			ATTRIBUTES.put("PETS", "PET10");
			ATTRIBUTES.put("PNUD", "REC46");
			ATTRIBUTES.put("RNT", "");
			ATTRIBUTES.put("POOL", "HAC71");
			ATTRIBUTES.put("PTBL", "RST111");
			ATTRIBUTES.put("SISO", "RST18");
			ATTRIBUTES.put("SMOK", "FSC16");
			ATTRIBUTES.put("THTR", "RMA251");
			ATTRIBUTES.put("TOWN", "LOC4");
			ATTRIBUTES.put("VIEW", "RVT20");
			ATTRIBUTES.put("WDSY", "RVT18");
			ATTRIBUTES.put("WIFI", "EQP55");
			ATTRIBUTES.put("WODS", "");
			ATTRIBUTES.put("WOOD", "");
		}

	}

	static {
		if (priceTable == null) {
			priceTable = new HashMap<Integer, Double>();
			priceTable.put(1, .80); // for off=season
			priceTable.put(2, 1.0); // standard rate except for selected
									// properties
			priceTable.put(3, 1.0); // standard rate
			priceTable.put(4, 1.20); // 120% of standard rate
			priceTable.put(5, 1.5); // 150% of standard rate
			priceTable.put(6, 2.0); // 200% of standard rate
		}
	}

	public A_Handler(Partner partner) {
		super(partner);
	}

	/**
	 * Constructor for use in development stage to allow the input of a file
	 * object
	 * 
	 * @param partner
	 * @param file
	 */
	public A_Handler(Partner partner, File aFile) {
		super(partner);
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		
		if (reservation.notActive()) { throw new ServiceException(Error.reservation_state,
					reservation.getId() + " state " + reservation.getState());
		}
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid()); }
		
		List<String> result = sqlSession.getMapper(ProductMapper.class).idswithreservationcollision(product.getLocationid(), reservation.getFromdate().toString(), reservation.getTodate().toString());
		return result!=null && result.size() == 0;
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		createReservationAndPayment(sqlSession, reservation, null);
	}


	@Override
	public Map<String, String> createReservationAndPayment( SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		
		Date timestamp = new Date();
		LOG.debug("Started createReservationAndPayment for Lake Tahoe Accommodations");
		int reservationNum = FTP_ID_START_NUM;
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			if (creditCard == null || 
				!creditCard.getType().equals(CreditCardType.VISA) && !creditCard.getType().equals( CreditCardType.MASTER_CARD)) {
				throw new ServiceException(Error.card_type, " card type: " + creditCard.getType());
				}
			
			if (reservation.notActive()) { throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState()); }
			Product product = sqlSession.getMapper(ProductMapper.class).read( reservation.getProductid());
			if (product == null) { throw new ServiceException(Error.product_id, reservation.getProductid()); }
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) { throw new ServiceException(Error.party_id, reservation.getAgentid());}
			if (reservation.noCustomerid()) { reservation.setCustomerid(Party.NO_ACTOR); }
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) { throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}
			
			reservationNum += sqlSession.getMapper(ReservationMapper.class).readreservationcount(getAltpartyid());
			
			String requestNumber = String.valueOf(reservationNum).length() == 4 ? "0"
					+ String.valueOf(reservationNum)
					: String.valueOf(reservationNum);
			
			// Price calculation
			// Finding daily rate for reservation period
			Price price = new Price();
			price.setEntityid(reservation.getProductid());
			price.setPartyid(getAltpartyid());
			price.setDate(reservation.getFromdate());
			price.setTodate(reservation.getTodate());
			ArrayList<Price> result = sqlSession.getMapper(PriceMapper.class)
					.readsplitpriceperiods(price);
		
			double dailyRate = 0;
			int minstay      = 0; 
			
			for (Price p : result){
				if (p.getValue() > dailyRate) {
					dailyRate = p.getValue();
				}
				
				if(p.getMinStay() > minstay){
					minstay = p.getMinStay();	
				}
			}
			
			
			int numDaysBooked = numDaysBetween(reservation.getFromdate(), reservation.getTodate());
		
			// get the minstay from price table. 
			if( numDaysBooked < minstay){ throw new ServiceException(Error.minimum_stay, minstay +" days"); }
			if (dailyRate == 0.0)      {  throw new ServiceException(Error.price_not_match, "Cannot find price for given dates");}

            	
			// Spl tax (per-diem tax rate)
			Price spl = new Price();
			spl.setEntityid(product.getId());
			spl.setEntitytype(NameId.Type.Tax.name());
			spl.setPartyid(getAltpartyid());
			LOG.debug("Values for spl: " + spl.getEntityid() + " " + spl.getEntitytype() + " " + spl.getPartyid());
			Price splTaxPrice = sqlSession.getMapper(PriceMapper.class)
					.readperdiemtax(spl);
			if (splTaxPrice == null) {
				throw new ServiceException(Error.price_missing,
						"The perdiem tax rate is not available for this property");
			}
			double rentalFee = numDaysBooked * dailyRate;
			double cleaningFee = product.getCleaningfee();
			double serviceCharge = (cleaningFee + rentalFee) * SERVICECHARGE
					+ cleaningFee;
			double rentalAmt = serviceCharge + rentalFee + BOOKFEE;
			double tax = Double.valueOf(product.getTax()) * (rentalAmt)
					+ (spl.getValue() * numDaysBooked);
			double tripInsurance = 0.00; // 0.0 because we currently don't
											// support trip insurance
			double totalCost = rentalAmt + tax + tripInsurance;

			////////////////////////////////End Price calculation//////////////////////////////////////////////////////////////////////////////////////////////
			
			// Set the time to PST time so the file has times in LTA's timezone
			LTA_DF.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			String version = LTA_DF_WITH_TIME.format(timestamp);
			String fromDate = LTA_DF.format(reservation.getFromdate())
					.toString();
			String toDate = LTA_DF.format(reservation.getTodate()).toString();

			LOG.debug("Request Number for Lake Tahoe Accommodations: RBP"+ requestNumber);
			
			LOG.debug("Creating reservation from " + fromDate + " " + toDate);
			
			String fileName = "RBP" + requestNumber + ".csv";
			String filePath = System.getProperty("user.home") + System.getProperty("file.separator") + fileName;
			FileWriter reservationRequest = new FileWriter(filePath);
			reservationRequest.write(RESERVATION_REQUEST_COLUMN_HEADER + "\n"); // Column
																				// header
																				// for
																				// CSV
																				// file
			reservationRequest.write("BP" + requestNumber + ","); // Request
																	// number
			reservationRequest.write(version + ","); // Reservation date
			reservationRequest.write(product.getAltid() + ","); // Property ID
			reservationRequest.write(fromDate + ","); // Start date for
														// reservation
			reservationRequest.write(toDate + ","); // End date for reservation
			reservationRequest.write(String.valueOf(numDaysBooked) + ","); // Number
																			// of
																			// nights
			reservationRequest.write("$" + rentalFee + ","); // Rental amount
																// (number of
																// days * daily
																// rate)
			reservationRequest.write("$" + BOOKFEE + ","); // Booking fee
															// (always $30)
			reservationRequest.write("$" + serviceCharge + ","); // Service
																	// charge +
																	// cleaning
																	// fee (svc
																	// charge is
																	// 10% of
																	// combined
																	// rentalAmt
																	// +
																	// cleaningFee)
			reservationRequest.write("$" + rentalAmt + ",");
			reservationRequest.write("$" + tax + ",");
			reservationRequest.write("$" + tripInsurance + ","); // Insurance
			// Amount,
			// currently
			// not
			// supported
			// so $0.00
			reservationRequest.write("$" + totalCost + ","); // Total Amount
			reservationRequest.write("$" + (totalCost * DEPOSITPERCENTAGE)
					+ ","); // Deposit Amount
			reservationRequest.write("No" + ","); // Special Rate. Currently not
			// supported
			reservationRequest.write(reservation.getAdult()
					+ reservation.getChild() + reservation.getInfant() + ","); // Number
			// of
			// guests
			reservationRequest.write(customer.getFirstName() == null ? ","
					: customer.getFirstName().replaceAll("\\n|\\r", "")
					.replaceAll(",", "")
					+ ","); // Customer first name
			reservationRequest.write(customer.getFamilyName() == null ? ","
					: customer.getFamilyName().replaceAll("\\n|\\r", "")
					.replaceAll(",", "")
					+ ","); // Customer last name
			reservationRequest.write(customer.getPostaladdress() == null ? ","
					: customer.getPostaladdress().replaceAll("\\n|\\r", "")
					.replaceAll(",", "")
					+ ","); // Customer address
			reservationRequest.write(customer.getCity() == null ? ","
					: customer.getCity().replaceAll("\\n|\\r", "")
					.replaceAll(",", "")
					+ ","); // Customer city
			reservationRequest.write(customer.getRegion() == null ? ","
					: customer.getRegion().replaceAll("\\n|\\r", "")
							.replaceAll(",", "")
							+ ","); // Customer state
			reservationRequest.write(customer.getPostalcode() == null ? ","
					: customer.getPostalcode().replaceAll("\\n|\\r", "")
							.replaceAll(",", "")
							+ ","); // Customer zip code
			reservationRequest.write(customer.getDayphone() == null ? ","
					: customer.getDayphone().replaceAll("\\n|\\r", "")
							.replaceAll(",", "")
							+ ","); // Customer daytime phone
			reservationRequest.write(customer.getMobilephone() == null ? ","
					: customer.getMobilephone().replaceAll("\\n|\\r", "")
							.replaceAll(",", "")
							+ ","); // customer alternate phone
			reservationRequest.write(customer.getEmailaddress() == null ? ","
					: customer.getEmailaddress().replaceAll("\\n|\\r", "")
							.replaceAll(",", "")
							+ ","); // customer e-mail address
			reservationRequest.write("No" + ","); // sign up e-mail for LTA
													// newsletter, not supported
			// Credit Card information should go here but it is thrown away 
			reservationRequest.write(",");
			reservationRequest.write(",");
			reservationRequest.write(",");
			reservationRequest.write(",");

			reservationRequest.write("BookingPal,"); // Agent name
			reservationRequest.write(reservation.getNotes() == null ? ","
					: reservation.getNotes().replaceAll(",", "") + ","); // Reservation notes
			reservationRequest.write(","); // Agent notes
			reservationRequest.write(LAKE_TAHOE_AFFILIATE_NUMBER + "\n"); // Lake
																			// Tahoe
																			// Accommodations
																			// affiliate
																			// number

			reservationRequest.flush();
			reservationRequest.close();
			reservation.setState(Reservation.State.Confirmed.name());
			reservation.setPrice(price.getValue());
			reservation.setCost(totalCost);
			reservation.setAltid(requestNumber);
			reservation.setVersion(timestamp);
			reservation.setName(reservation.getId());
			sendFileToLTA(filePath, fileName); // sending a file will
												// automatically delete it after
												// sending

			resultMap.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
		} catch (Exception e) {
			reservation.setMessage(e.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			e.printStackTrace();
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();

		return resultMap;
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {

		  Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), productAltId ,  false);
		if (product == null) {
			throw new ServiceException(Error.product_altid, "Product not found");
		}
		ReservationPrice reservationPrice = new ReservationPrice();
		ArrayList<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();

		reservationPrice.setCurrency(currency);

		Price price = new Price();
		price.setEntityid(reservation.getProductid());
		price.setPartyid(getAltpartyid());
		price.setDate(reservation.getFromdate());
		price.setTodate(reservation.getTodate());
		ArrayList<Price> result = sqlSession.getMapper(PriceMapper.class).readsplitpriceperiods(price);
	
		double dailyRate = 0;
		int minstay      = 0; 
		
		for (Price p : result){
			if (p.getValue() > dailyRate) {
				dailyRate = p.getValue();
			}
			
			if(p.getMinStay() > minstay){
				minstay = p.getMinStay();	
			}
		}
		
		
		int numDaysBooked = numDaysBetween(reservation.getFromdate(), reservation.getTodate());
	
		if( numDaysBooked < minstay){ throw new ServiceException(Error.minimum_stay, minstay +" days");}
		if (dailyRate == 0.0)       { throw new ServiceException(Error.price_not_match, "Cannot find price for given dates");}

		Price spl = new Price();
		spl.setEntityid(product.getId());
		spl.setEntitytype(NameId.Type.Tax.name());
		spl.setPartyid(getAltpartyid());
		LOG.debug("Values for spl: " + spl.getEntityid() + " " + spl.getEntitytype() + " " + spl.getPartyid());
		spl = sqlSession.getMapper(PriceMapper.class)
				.readperdiemtax(spl);
		if (spl == null) {
			LOG.error("Lake Tahoe Accommodations: " + product.getId() + ": cannot find spl tax");
			throw new ServiceException(Error.price_missing,
					"The perdiem tax rate is not available for this property");
		}

		// Rental fee
		QuoteDetail rentalFee = new QuoteDetail();
		rentalFee.setDescription("Rental fee: current daily rate times number of days booked");
		rentalFee.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		rentalFee.setAmount(String.valueOf(numDaysBooked * dailyRate));
		rentalFee.setIncluded(true);
		quoteDetails.add(rentalFee);

		// Cleaning fee
		QuoteDetail cleaningFee = new QuoteDetail();
		cleaningFee.setDescription("Cleaning fee");
		cleaningFee.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		cleaningFee.setAmount(String.valueOf(product.getCleaningfee()));
		cleaningFee.setIncluded(true);
		quoteDetails.add(cleaningFee);

		// Booking fee
		QuoteDetail bookingFee = new QuoteDetail();
		bookingFee
				.setDescription("Lake Tahoe Accommodations booking fee, non-refundable");
		bookingFee.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		bookingFee.setAmount(String.valueOf(BOOKFEE));
		bookingFee.setIncluded(true);
		quoteDetails.add(bookingFee);

		// Reservation service charge
		QuoteDetail reservationServiceCharge = new QuoteDetail();
		reservationServiceCharge
				.setDescription("Lake Tahoe Accommodations reservation service charge");
		reservationServiceCharge.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		reservationServiceCharge.setAmount(String.valueOf(.10
				* Double.valueOf(rentalFee.getAmount())
				+ Double.valueOf(cleaningFee.getAmount())));
		reservationServiceCharge.setIncluded(true);
		quoteDetails.add(reservationServiceCharge);

		// Occupancy tax
		QuoteDetail occupancyTax = new QuoteDetail();
		occupancyTax.setDescription("Tax rate for property");
		occupancyTax.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		occupancyTax.setAmount(product.getTax());
		occupancyTax.setIncluded(true);
		quoteDetails.add(occupancyTax);

		// SPL tax (daily tax rate)
		QuoteDetail splTax = new QuoteDetail();
		splTax.setDescription("Per diem tax. May or may not be 0.");
		splTax.setPaymentInfo(PAYMENT_INFO_FOR_QUOTES);
		splTax.setAmount(String.valueOf(numDaysBooked * spl.getValue()));
		splTax.setIncluded(true);
		quoteDetails.add(splTax);

		// Trip Insurance (not currently supported)
	//	QuoteDetail tripInsurance = new QuoteDetail();
	//	tripInsurance
	//			.setDescription("Lake Tahoe Accommodations trip insurance (currently not supported, not included)");
	//	tripInsurance
	//			.setPaymentInfo("Not currently supported by BookingPal. Not included in total.");
	//	tripInsurance.setAmount(String.valueOf("0"));
	//	tripInsurance.setIncluded(false);
	//	quoteDetails.add(tripInsurance);

		double total = computeTotal(Double.valueOf(rentalFee.getAmount()),
				Double.valueOf(cleaningFee.getAmount()),
				Double.valueOf(occupancyTax.getAmount()),
				Double.valueOf(splTax.getAmount()), false);
		
		Double rate;
		if(currency.equalsIgnoreCase(Currency.Code.USD.name())){
			rate= 1.0; 
		}else{
		    rate = WebService.getRate(sqlSession, Currency.Code.USD.name(), currency, new Date());
		}
		
		reservationPrice.setTotal(rate*total);
		reservationPrice.setQuoteDetails(quoteDetails);
		reservationPrice.setPrice(rate*Double.valueOf(rentalFee.getAmount()));
		reservationPrice.setCurrency(currency);
	
		reservation.setQuote(reservationPrice.getTotal());
		reservation.setPrice(Double.valueOf(rentalFee.getAmount()));
		reservation.setTaxrate(Double.valueOf(product.getTax()));
		reservation.setDeposit(reservationPrice.getTotal() * DEPOSITPERCENTAGE);
		
		return reservationPrice;
	}

	/*
	 * private double computeTotal(double rentalFee, double cleaningFee, double
	 * taxRate) { return computeTotal(rentalFee, cleaningFee, taxRate, false); }
	 */

	private double computeTotal(double rentalFee, double cleaningFee,
			double taxRate, double splTax, boolean tripInsurance) {
		double tripInsuranceAmt = tripInsurance ? 1.065 : 1.0;
		return ((((rentalFee + cleaningFee) * 1.1) + BOOKFEE) * (1 + taxRate))
				* tripInsuranceAmt + splTax;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		Date timestamp = new Date();
		String message = "confirmReservation " + reservation.getAltid();
		try {
			reservation.setState(Reservation.State.Confirmed.name());
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			sqlSession.rollback();
		} finally {
			sqlSession.commit();
		}
		MonitorService.monitor(message, timestamp);
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		try {
			throw new ServiceException(Error.service_absent,
					"LTA readReservation absent");
		} catch (Throwable x) {
			LOG.debug(x.getMessage());
		}
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation " + reservation.getId();
		try {
			throw new ServiceException(Error.service_absent,
					"LTA updateReservation()");
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	// According to LTA website, a reservation can only be cancelled if its
	// within 7 days from the
	// reservation creation. Completely refundable minus $30 booking fee
	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getAltid();

		try {
			throw new ServiceException(Error.cancel_unavailable, ": Could not process reservation cancellation");
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	@Override
	public void readAlerts() {
		try {
			throw new ServiceException(Error.service_absent,
					"LTA readAlerts absent");
		} catch (Throwable x) {
			LOG.debug(x.getMessage());
		}
	}

	@Override
	public void readPrices() {
		Date version = new Date();
		LOG.debug("Starting readPrices for Lake Tahoe Accommodations");
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			long startParseTime = System.currentTimeMillis();
			LTAFileReader reader = new LTAFileReader();
			HashMap<String, Property> properties = reader.parseLTAFiles();
			long endParseTime = System.currentTimeMillis();
			LOG.debug("Total time taken to parse and download LTA files: "
					+ (endParseTime - startParseTime));
			int count = 0;
			long startReadPrice, endReadPrice, startReadProduct, endReadProduct, startPriceUpdate, endPriceUpdate;
			for (Map.Entry<String, Property> entry : properties.entrySet()) {
				startReadPrice = System.currentTimeMillis();
				Property property = entry.getValue();
				String altid = property.getUnitID(); // because we don't know
														// the altID for LTA
				startReadProduct = System.currentTimeMillis();
				
				Product product =  PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
				
				endReadProduct = System.currentTimeMillis();
				if (product == null) {
					System.out.println("Product not found: " + altid);
					continue;
				}
				LOG.debug("Total time taken to read product: "
						+ product.getId() + ": "
						+ (endReadProduct - startReadProduct));
				startReadPrice = System.currentTimeMillis();
				ArrayList<ArrayList<Day>> priceRates = findConsecutiveAndSimilarlyPricesDays(extractAvailableDays(property
						.getAvailability()));
				for (ArrayList<Day> days : priceRates) {
					if (days.size() == 0) {
						continue;
					}
					// System.out.println(days.get(0).getDate() + " --> " +
					// days.get(days.size()-1).getDate() + " for " +
					// days.get(0).getDayPrice() + " per day");
					Price price = new Price();
					price.setSupplierid(getAltpartyid());
					price.setAltid(product.getAltid());
					price.setEntityid(product.getId());
					price.setEntityname(product.getName());
					price.setEntitytype(NameId.Type.Product.name());
					price.setPartyid(getAltpartyid());
					price.setPartyname(partner.getName());
					price.setName(Price.RACK_RATE);
					price.setType(NameId.Type.Reservation.name());
					price.setMinStay(days.get(0).getMinStay());
					Price exists = sqlSession.getMapper(PriceMapper.class)
							.exists(price);
					if (exists == null) {
						sqlSession.getMapper(PriceMapper.class).create(price);
					} else {
						price = exists;
					}
					price.setDate(days.get(0).getDate());
					price.setTodate(days.get(days.size() - 1).getDate());
					price.setQuantity(1.0);
					price.setCurrency(getCurrency());
					price.setUnit(Unit.DAY);
					price.setState(Price.CREATED);
					price.setRule(Price.Rule.AnyCheckIn.name());
					price.setValue((double) days.get(0).getDayPrice());
					price.setAvailable(1);
					price.setVersion(version);
					endReadPrice = System.currentTimeMillis();
					LOG.debug("\tTime taken to read pricing for product "
							+ product.getId() + ": "
							+ (endReadPrice - startReadPrice));
					startPriceUpdate = System.currentTimeMillis();
					sqlSession.getMapper(PriceMapper.class).update(price);
					endPriceUpdate = System.currentTimeMillis();
					LOG.debug("\tTotal time taken to update prices for product: "
							+ product.getId()
							+ ": "
							+ (endPriceUpdate - startPriceUpdate));
					count++;
					if (count % 10 == 0) {
						sqlSession.commit();
					}
				}
			}
			long startCancelOldPrices = System.currentTimeMillis();
			cancelOldPrices(version, sqlSession);
			long endCancelOldPrices = System.currentTimeMillis();
			LOG.debug("Time taken to cancel old prices: "
					+ (endCancelOldPrices - startCancelOldPrices));
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}

	@Override
	public void readProducts() {
		LOG.debug("Starting readProducts for Lake Tahoe Accommodations");
		Date version = new Date();
		final SqlSession sqlSession = RazorServer.openSession();
		int numBathrooms = 0;
		int numHalfBaths = 0;
		int count = 0;

		try {
			LTAFileReader reader = new LTAFileReader();
			HashMap<String, Property> properties = reader.parseLTAFiles();
			for (Map.Entry<String, Property> entry : properties.entrySet()) {
				Property property = entry.getValue();
				String altid = property.getUnitID();
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid() , altid);
				if (product == null) {
					LOG.debug("Lake Product was null " + altid);
					continue;
				}
				LOG.debug("Loading product: " + product.getId());
				product.setPhysicaladdress(property.getStreetAddress().toLowerCase());
				product.setRoom(property.getBedrooms());
				String numBathAsText = String.valueOf(property.getBathrooms());
				chooseName(property);
				product.setName(chooseName(property));
				String[] fullbathroomcount = numBathAsText.split("\\.");
				if (fullbathroomcount.length == 2) {
					numBathrooms = Integer.parseInt(fullbathroomcount[0]);
					numHalfBaths = Integer.parseInt(fullbathroomcount[1]);
				} else {
					numBathrooms = Integer.parseInt(fullbathroomcount[0]);
				}

				int numToilets = numBathrooms + numHalfBaths;
				product.setBathroom(numBathrooms);
				product.setToilet(numToilets);
				product.setCurrency(getCurrency());
				product.setLatitude(property.getLatitude());
				product.setLongitude(property.getLongitude());
				product.setState(Product.CREATED);
				product.setType(Product.Type.Accommodation.name());
				product.setPerson(property.getMaxOccupancy());
				product.setChild(0);
				product.setInfant(0);
				product.setQuantity(1);
				product.setServicedays("0000000");
				product.setUnit(Unit.DAY);
				product.setTax(String.valueOf(property.getTaxRate()));
				product.setCleaningfee(property.getCleaningFee());
				product.setSecuritydeposit(property.getSecurityDeposit());
				product.setUseonepricerow(false);
				product.setVersion(version);
				product.setAssignedtomanager(true);
				product.setAltitude(0.0);
				
				// Location
				double longitude = product.getLongitude();
				double latitude = product.getLatitude();

				String place = property.getLocationCode();
				String region = property.getUSState(place);
				Location location = PartnerService.getLocation(
						sqlSession,
						place, // location code from Unit ID
						region,
						"US", // Country
						latitude == 0.0 ? 0.0 : latitude,
						longitude == 0.0 ? 0.0 : longitude);
				product.setLocationid(location.getId());

				sqlSession.getMapper(ProductMapper.class).update(product);

				// Loading product attributes
				readProductAttributes(sqlSession, product, property);
				
				generatePerDiemTax(sqlSession, property, product, version);
				
				count++;
				if (count % 10 == 0) { // prevent exceptions from AutoCommit
					sqlSession.commit();
				}
			}
			//If product does not appear in feed it is no longer active cancel out old products///
			removeUnManagedProperties(version,sqlSession);
			
			
		} catch (Throwable x) {
			sqlSession.rollback();
			// x.printStackTrace();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	private void generatePerDiemTax(SqlSession sqlSession, Property property, Product product, Date version){
		
		Price perdiemTax = new Price();
		
		perdiemTax.setEntityid(product.getId()); 
		perdiemTax.setEntitytype(NameId.Type.Tax.name()); 
		perdiemTax.setPartyid(getAltpartyid());   
		perdiemTax.setValue(property.getSplTax());
		
		Price price = sqlSession.getMapper(PriceMapper.class).readperdiemtax(perdiemTax);
		
		if (price == null) {
			
			perdiemTax.setName("Per Diem Tax");
			perdiemTax.setType(NameId.Type.MandatoryPerDay.name());
			perdiemTax.setQuantity(1.0);
			perdiemTax.setUnit(Unit.DAY);
			perdiemTax.setCurrency(getCurrency());
			//perdiemTax.setAltid(property.getUnitID());
			perdiemTax.setState(Price.CREATED);
			perdiemTax.setMinStay(0);
			perdiemTax.setVersion(version);
			
			sqlSession.getMapper(PriceMapper.class).create(perdiemTax);
			
		}
		else if(property.getSplTax() != price.getValue()){
			
			price.setValue(property.getSplTax());
			price.setVersion(version);
			sqlSession.getMapper(PriceMapper.class).update(price);
			
		}
		
	}
	private void  removeUnManagedProperties(Date version, SqlSession sqlSession){
		Product oldProduct = new  Product();
		oldProduct.setVersion(version);
		oldProduct.setAltpartyid(getAltpartyid());
		sqlSession.getMapper(ProductMapper.class).initializeProductsNotInFeed(oldProduct);
	}
	@Override
	public void readSchedule() {
		LOG.debug("Starting readSchedule for Lake Tahoe Accommodations");
		Date version = new Date();
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			LTAFileReader reader = new LTAFileReader();
			HashMap<String, Property> properties = reader.parseLTAFiles();
			for (Map.Entry<String, Property> entry : properties.entrySet()) {
				Property property = entry.getValue();
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid() , property.getUnitID(), false);
				if (product == null) {
					continue;
				}
				// Generate blocked dates periods
				ArrayList<ArrayList<Date>> blockedDates = findConsecutiveDates(extractNonAvailableDates(property
						.getAvailability()));
				for (ArrayList<Date> dates : blockedDates) {
					if (dates.size() == 0) {
						continue;
					}
					PartnerService
							.createSchedule(sqlSession, product, dates.get(0),
									modifyDate(dates.get(dates.size() - 1), 1),
									version);
					LOG.debug("Schedule for product " + product.getId() + ": " + dates.get(0) + " to " + dates.get(dates.size()-1));
					PartnerService.cancelSchedule(sqlSession, product, version);
				}

			}
		} catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.not_implemented, ": no specials for Lake Tahoe Accommodations");

	}

	@Override
	public void readDescriptions() {
		LOG.debug("Starting readDescriptions for Lake Tahoe Accommodations");
		final SqlSession sqlSession = RazorServer.openSession();
		
		Date version = new Date();
		try {
			LTAFileReader reader = new LTAFileReader();
			HashMap<String, Property> properties = reader.parseLTAFiles();
			for (Map.Entry<String, Property> entry : properties.entrySet()) {
				Property property = entry.getValue();
				String altid = property.getUnitID(); // because we don't know
														// the altID for LTA
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid() , altid);
				if (product == null) {
					LOG.error("Didn't find: " + altid + " skipping...");
					continue;
				}
				LOG.debug("Loading description for product: " + product.getId());
				String description = property.getUnitDescription();
				description.replaceAll("\t", " ");
				description = description + CREDIT_CARD_NOTE;
				product.setPublicText(new Text(product.getPublicId(), product
						.getPublicLabel(), Text.Type.Text, version, NameId
						.trim(description, 20000), Language.EN));
				TextService.update(sqlSession, product.getTexts());
			}

		} catch (Throwable x) {
			LOG.error(x.getMessage());
			sqlSession.rollback();
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}

	@Override
	public void readImages() {
		try {
			//readLTAImagesFromFTP();
			readLTAImagesLocally(RazorConfig.getLaketahoeaccommodationsImagesLocation());
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void readAdditionCosts() {
	}

	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		throw new ServiceException(Error.not_implemented, "Not implemented");

	}

	// Crashes ftp server
	private void readLTAImagesFromFTP() { 
		Product product;
		final SqlSession sqlSession = RazorServer.openSession();
		FTPClient ftpClient = new FTPClient();
		ArrayList<NameId> images;
		try {

			ftpClient.connect(IMAGES_LOCATION);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // must set ftpClient
															// to Binary File
															// Type so it
															// downloads images
															// correctly
			ftpClient.enterLocalPassiveMode(); // passive mode must be set

			ftpClient.login(IMAGES_USERNAME, IMAGES_PASSWORD);

			for (FTPFile property : ftpClient.listDirectories("/big_photos/")) {
				if (property.getName().length() == 7) { // prevents going back
														// into an infinite loop
														// because parent
														// directories are given
					
					product = PartnerService.getProduct(sqlSession, getAltpartyid(), property.getName().toUpperCase(),false);
					if (product == null) {
						continue;
					}
					images = new ArrayList<NameId>();
					for (FTPFile propertyImage : ftpClient
							.listFiles("/big_photos/" + property.getName())) {
						if (propertyImage.getName().length() >= 7) {
							// //System.out.println("\t" +
							// propertyImage.getName());
							String imageURL = "ftp://"
									+ URLEncoder.encode(IMAGES_USERNAME,
											"UTF-8")
									+ ":"
									+ URLEncoder.encode(IMAGES_PASSWORD,
											"UTF-8") + "@" + IMAGES_LOCATION
									+ "/big_photos/" + property.getName() + "/"
									+ propertyImage.getName();
							images.add(new NameId(propertyImage.getName(),
									imageURL)); // caution, this causes the
												// password to appear in the
												// table
						}
					}
					if (!images.isEmpty())
						ImageService.uploadImages(sqlSession,
								NameId.Type.Product, product.getId(),
								Language.EN, images, null); // /
					TimeUnit.SECONDS.sleep(5);
				}
			}

			// logout the user, returned true if logout successfully
			ftpClient.logout();
		} catch (Throwable e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			sqlSession.commit();
			sqlSession.close();
			try {
				ftpClient.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void readLTAImagesLocally(String path) throws StorageException,
			IOException {
		Product product;
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<NameId> images;
		File root = new File(path);
		for (File folder : root.listFiles()) {
			LOG.debug("Reading property images for: " + folder.getName());
			if (folder.getName().length() == 7) {
				
				product = PartnerService.getProduct(sqlSession,getAltpartyid(), folder.getName().toUpperCase(),false);
				
				if (product == null) {
					continue;
				}
				images = new ArrayList<NameId>();
				for (File image : folder.listFiles()) {
					images.add(new NameId(image.getAbsolutePath(), image
							.getName()));
				}
				ImageService.uploadImages(
						sqlSession,
						NameId.Type.Product,
						product.getId(),
						Language.EN,
						images,
						folder.getCanonicalPath()
								+ System.getProperty("file.separator")); // /
			}
		}
	}

	private void readProductAttributes(SqlSession sqlSession, Product product,
			Property property) {
		ArrayList<String> propertyAmenities = new ArrayList<String>();
	//	LOG.debug("Property: " + product.getName());

		// Pulls amenities provided by files
		String[] descriptionCodes = property.getDescriptionCodes();

		if (descriptionCodes != null) {
			for (String code : property.getDescriptionCodes()) {
				String attr = ATTRIBUTES.get(code);
				if (attr != null && attr != "") {
					propertyAmenities.add(attr);
					//LOG.debug("Attribute was found: " + code + " --> " + attr);
				} else {
					//LOG.debug("Attribute was null: " + code);
				}
			}
		}
		// Add other misc amenities provided by the files
		if (!property.getBarbequeType().equals("")) {
			propertyAmenities.add("HAC118");
		}
		if (property.getNumberOfSofaBed() > 0) {
			propertyAmenities.add("BED6");
		}

		StringBuilder beds = new StringBuilder();
		for (String s : property.getBedList()) {
			if (s.equals("")) {
				break;
			}
			beds.append(s);
		}
		String bedInfo = beds.toString();
		if (bedInfo.contains("K")) {
			propertyAmenities.add("BED3");
		}
		if (bedInfo.contains("Q")) {
			propertyAmenities.add("BED5");
		}
		if (bedInfo.contains("S")) {
			propertyAmenities.add("BED9");
		}
		if (bedInfo.contains("D")) {
			propertyAmenities.add("BED1");
		}

		if (property.getWasherDescription().equals("YES")) {
			propertyAmenities.add("RMA149");
		}
		if (!property.getFireplaceDescription().equals("")) {
			propertyAmenities.add("RMA41");
		}

		// Add property type
		if (property.getUnitID().substring(2, 3).equals("C")) {
			propertyAmenities.add("PCT8");
		} else {
			propertyAmenities.add("PCT16");
		}

		// for (String s : propertyAmenities)
		// System.out.print(s + " ");
		// System.out.println();

		if (propertyAmenities != null) {
			RelationService.replace(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE,
					product.getId(), propertyAmenities);
		}
		sqlSession.commit();
	}

	// extracts dates from a day array
	// that are not available
	private ArrayList<Date> extractNonAvailableDates(Day[] days) {
		ArrayList<Date> result = new ArrayList<Date>();
		for (Day day : days) {
			if (day.getRateTable() == 0)
				result.add(day.date);
		}
		return result;
	}

	// Extract Day objects from a day array
	// that are available
	private ArrayList<Day> extractAvailableDays(Day[] days) {
		ArrayList<Day> result = new ArrayList<Day>();
		for (Day day : days) {
			if (day.getRateTable() != 0)
				result.add(day);
		}
		return result;
	}

	// arr is assumed to only have available days
	private ArrayList<ArrayList<Day>> findConsecutiveAndSimilarlyPricesDays(
			ArrayList<Day> arr) {
		ArrayList<ArrayList<Day>> result = new ArrayList<ArrayList<Day>>();
		ArrayList<Day> consecutives = new ArrayList<Day>();
		for (int i = 0; i < arr.size(); i++) {
			if ((i + 1) == arr.size()) {
				result.add(consecutives);
				consecutives = new ArrayList<Day>();
			} else if ((!modifyDate(arr.get(i).getDate(), 1).equals(
					arr.get(i + 1).getDate()))
					|| (arr.get(i).getDayPrice() != arr.get(i + 1)
							.getDayPrice())) {
				consecutives.add(arr.get(i));
				result.add(consecutives);
				consecutives = new ArrayList<Day>();
			} else {
				consecutives.add(arr.get(i));
			}
		}
		return result;
	}


	@SuppressWarnings("unused")
	private ArrayList<ArrayList<Day>> findConsecutiveMinStayDates(
			ArrayList<Day> arr) {
		ArrayList<ArrayList<Day>> result = new ArrayList<ArrayList<Day>>();
		ArrayList<Day> consecutives = new ArrayList<Day>();
		for (int i = 0; i < arr.size(); i++) {
			if ((i + 1) == arr.size()) {
				result.add(consecutives);
				consecutives = new ArrayList<Day>();
			} else if ((!modifyDate(arr.get(i).getDate(), 1).equals(
					arr.get(i + 1).getDate()))
					|| (arr.get(i).getMinStay() != arr.get(i + 1).getMinStay())) {
				consecutives.add(arr.get(i));
				result.add(consecutives);
				consecutives = new ArrayList<Day>();
			} else {
				consecutives.add(arr.get(i));
			}
		}
		return result;
	}

	// extracts ArrayLists of consecutive blocked dates
	private ArrayList<ArrayList<Date>> findConsecutiveDates(ArrayList<Date> arr) {
		ArrayList<Date> consecutives = new ArrayList<Date>();
		ArrayList<ArrayList<Date>> result = new ArrayList<ArrayList<Date>>();
		for (int i = 0; i < arr.size(); i++) {
			if ((i + 1) == arr.size()) {
				result.add(consecutives);
				consecutives = new ArrayList<Date>();
			} else if (!modifyDate(arr.get(i), 1).equals(arr.get(i + 1))) { // if
																			// the
																			// next
																			// date
																			// is
																			// not
																			// the
																			// next
																			// day
																			// over...
				consecutives.add(arr.get(i)); // then add it into the current
												// consecutives list
				result.add(consecutives);
				consecutives = new ArrayList<Date>(); // and request a new
														// arraylist
			} else {
				consecutives.add(arr.get(i));
			}
		}
		return result;
	}

	// Adds the number of days to the date given
	private Date modifyDate(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount); // minus number would decrement the days
		return cal.getTime();
	}

	private int numDaysBetween(Date fromDate, Date toDate) {
		long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		int diffInDays = (int) ((fromDate.getTime() - toDate.getTime()) / DAY_IN_MILLIS);
		return Math.abs(diffInDays);
	}

	
	// This function assigns names that will be
	// shown for LTA properties
	// change accordingly
	private String chooseName(Property property) {
		StringBuilder sb = new StringBuilder();
		sb.append("Lake Tahoe ");
		sb.append(property.getBedrooms());
		sb.append("bdrm(s)");
		return sb.toString();
	}

	private void sendFileToLTA(String filePath, String fileName)
			throws SocketException, IOException {
		String host = RazorConfig.getLaketahoeaccommodationsRequestFtpsHostname();
		int port = 22;
		String user = RazorConfig.getLaketahoeaccommodationsRequestFtpsUsername();
		String password = RazorConfig.getLaketahoeaccommodationsRequestFtpsPassword();

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			File f = new File(filePath);
			channelSftp.put(new FileInputStream(f), f.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channelSftp.exit();
			channel.disconnect();
			session.disconnect();
		}
	}

	private void cancelOldPrices(Date version, SqlSession sqlSession) {
		Price price = new Price();
		price.setVersion(version);
		price.setAltpartyid(getAltpartyid());
		price.setEntitytype(NameId.Type.Product.name());
		price.setPartyid(getAltpartyid());
		price.setCurrency(getCurrency());
		sqlSession.getMapper(PriceMapper.class).cancelversionbypartyid(price);
		sqlSession.commit();
	}
}