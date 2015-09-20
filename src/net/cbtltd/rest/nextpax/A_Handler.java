/**
 * @author	Chirayu Shah, Isaac Mahgrefteh
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.rest.nextpax;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import jdk.internal.jfr.events.FileWriteEvent;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Price.Rule;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.server.api.FeeMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.xml.sax.InputSource;

import sun.misc.BASE64Encoder;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.utils.CustomHttpConnection;
import com.mybookingpal.utils.ISO8859CharacterNormalizer;

/**
 * Class A_Handler manages the Nextpax API
 * ONLY file being used. ALL other files have NO use.
 * FTP server = ftp://secure.nextpax.com
 * FTP username = mybookingpal
 * FTP password = FEJvvn$LYGCUd-2_Vq4zI
 * Super-XML sender ID = MYB112 (OLD)
 * New SenderID = FLI112 (updated on Dec. 2, 2014)
 * https://secure.nextpax.com/extranet/index.php
 * The username for the Extranet is "mybookingpal" and the password is "BAD5PqtE".
 * 
 * @author cshah issac
 * @version Apr 18, 2014
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat TF = new SimpleDateFormat("hh:mm:ss");
	private static final String IMAGE_URL = "http://secure.nextpax.net/img/";
	private static final String BASE_NEXTPAX_URL = "https://secure.nextpax.com";
	private static final String CANCELLATION_URL = BASE_NEXTPAX_URL + "/comm/bookingpal_cancel_novasol.php?";
	private static final String SENDERID = "FLI112";
	// private static final String NEXTPAXTESTURL = "https://secure.nextpax.com/testxml/xml/";
	// private static final String NEXTPAX_PRODUCTION_URL = "https://secure.nextpax.com/live/xml/";
	private static final String EMAILCREDITCARD =
			"\nOnce the transaction is complete, the property manager will contact you for payment information.";
	private static final Pattern PATTERN_FOR_SPECIAL_CHARACTERS = Pattern.compile("[?]");

	private static long startTime = 0;
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	static {
//		Security.addProvider(new BouncyCastleProvider());
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
	}
	
	/**
	 * Instantiates a new partner handler.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler(Partner partner) {
		super(partner);
	}
	
	/**
	 * Gets the connection to the nextpax server and executes the specified request.
	 * 
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the XML string returned by the message.
	 * @throws Throwable the exception thrown by the operation.
	 */
	private static final String getConnection(String rq) throws Throwable {
		String xmlString = "";
		CustomHttpConnection connection = new CustomHttpConnection();
		
//		try {
			LOG.debug("NEXTPAX is using: " + RazorConfig.getNextPaxRequestURL());
//			URL url = new URL(RazorConfig.getNextPaxRequestURL());
//			connection = (HttpsURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setDoOutput(true);
//			connection.setRequestProperty("Content-Type", "application/xml");
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
			Security.addProvider(new BouncyCastleProvider());
			String userpassword = "mybookingpal" + ":" + "BAD5PqtE";
			String encodedAuthorization = enc.encode(userpassword.getBytes());
//			connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
//			connection.setRequestProperty("Authorization", "Basic " + userpassword);
			xmlString = connection.createPostRequest(RazorConfig.getNextPaxRequestURL(), encodedAuthorization, "application/xml", rq);
//
//			Provider[] providers = Security.getProviders();
//			for(int i = 0; i < providers.length; i++) {
//				LOG.debug(providers[i].getName());
//			}
//			
//			LOG.debug("Request process started");
//			
//			if (rq != null) {
//				LOG.debug("Setting request property");
//				connection.setRequestProperty("Accept", "application/xml"); // this
//				LOG.debug("Connecting...");
//				connection.connect(); // this
//				LOG.debug("Connected");
//				byte[] outputBytes = rq.getBytes("UTF-8");
//				
//				OutputStream os = connection.getOutputStream();
//				os.write(outputBytes);
//				LOG.debug("Writing output");
//			}
//
//			LOG.debug("Check connection response code");
//			if (connection.getResponseCode() != 200) {
//				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
//			}
//			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//			String line;
//			LOG.debug("Reading XML");
//			while ((line = br.readLine()) != null) {
//				xmlString += line;
//			}
//		} catch (Throwable x) {
//			LOG.error((x.getMessage()));
//			x.printStackTrace();
//			throw new RuntimeException(x.getMessage());
//		} finally {
//			if (connection != null) {
//				connection.disconnect();
//			}
//		}
		return xmlString;
	}

	/**
	 * Get unzipped input stream for file name.
	 * 
	 * @param fn the file name.
	 * @return the input stream.
	 * @throws Throwable the exception that can be thrown.
	 */
	private final synchronized InputStream ftp(String fn) throws Throwable {
		String urlname = "ftp://mybookingpal:FEJvvn$LYGCUd-2_Vq4zI@secure.nextpax.com/" + fn + ".gz;type=i";
		URL url = new URL(urlname);
		URLConnection urlc = url.openConnection();

		byte[] buf = new byte[1024];
		GZIPInputStream zinstream = new GZIPInputStream(urlc.getInputStream());
		FileOutputStream outstream = new FileOutputStream(fn);
		int n;
		while ((n = zinstream.read(buf, 0, 1024)) > -1) {
			outstream.write(buf, 0, n);
		}
		outstream.close();
		zinstream.close();
		return new BufferedInputStream(new FileInputStream(fn));
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
		} catch (Throwable x) {
			LOG.error(x.getMessage());
		}
	}

	/**
	 * Returns if the property is available for the reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		long time = now.getTime();
		String SenderSessionID = time + "BookingpalS";
		String ReceiverSessionID = time + "BookingpalR";
		String rq;
		String rs = null;
		boolean available = false;
		if (reservation.notActive()) {
			throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());
		}

		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {
			throw new ServiceException(Error.product_id, reservation.getProductid());
		}
		if (reservation.noAgentid()) {
			throw new ServiceException(Error.reservation_agentid);
		}
		Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
		if (agent == null) {
			throw new ServiceException(Error.party_id, reservation.getAgentid());
		}
		if (reservation.noCustomerid()) {
			reservation.setCustomerid(Party.NO_ACTOR);
		}
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (customer == null) {
			throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());
		}

		try {
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append(" <Control Language='NL' Test='nee'>");
			sb.append("    <SenderSessionID>" + SenderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + ReceiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("   <MessageSequence>1</MessageSequence>");
			sb.append("   <SenderID>" + SENDERID + "</SenderID>");
			sb.append("  <ReceiverID>NPS001</ReceiverID>");
			sb.append("   <RequestID>AvailabilityBookingPalRequest</RequestID>");
			sb.append("   <ResponseID>AvailabilityBookingPalResponse</ResponseID>");
			sb.append(" </Control>");
			sb.append("  <TRequest>");
			sb.append("    <AvailabilityBookingPalRequest>");
			sb.append("      <PackageDetails WaitListCheck='ja'>");
			sb.append("          <AccommodationID>" + "A" + product.getAltid() + "</AccommodationID>");
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("        <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("     </PackageDetails>");
			sb.append("   </AvailabilityBookingPalRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			LOG.debug("isAvailable rq: \n" + rq + "\n");

			rs = getConnection(rq);
			LOG.debug("isAvailable rs: \n" + rs + "\n");

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			InputSource inputSource = new InputSource(new StringReader(rs.toString()));
			Double bookID = (Double) xpath.evaluate("//PackageID", inputSource, XPathConstants.NUMBER);
			available = !bookID.isNaN();
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
		return available;
	}

	/**
	 * Create reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		LOG.error("You got into CreateBooking for nextpax");
		Date now = new Date();
		long timestamp = now.getTime();
		String message = "createReservation " + reservation.getId();
		String rq;
		String rs;
		Map<String, String> result = new HashMap<String, String>(); // never used.
		LOG.debug(message);
		LOG.error("Block 1 entry");

		StringBuilder sb = new StringBuilder();
		long time = timestamp;
		String SenderSessionID = time + "BookingpalS";
		String ReceiverSessionID = time + "BookingpalR";
		LOG.error("Block 2 entry");
		try {
			if (reservation.notActive()) {
				throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());
			}

			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {
				throw new ServiceException(Error.product_id,reservation.getProductid());
			}
			if (reservation.noAgentid()) {
				throw new ServiceException(Error.reservation_agentid);
			}
			LOG.error("Block 3 entry");
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {
				throw new ServiceException(Error.party_id, reservation.getAgentid());
			}
			if (reservation.noCustomerid()) {
				reservation.setCustomerid(Party.NO_ACTOR);
			}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			LOG.error("Block 4 entry");
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}
			Price price = sqlSession.getMapper(PriceMapper.class).altidfromdate(reservation); 
			if( price == null || (!price.hasAltid())) {throw new ServiceException(Error.price_data, reservation.getCustomerid());}
            
			if(creditCardOnly(Integer.parseInt(product.getAltSupplierId())))  {throw new ServiceException(Error.card_notsupported, product.getId() +"NextPax Create Reservation");}


			LOG.error("Block 5 entry");
			// sequence SellRequest.xml -> AssignRequest.xml -> BookRequest.xml
			// SellRequest.xml
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + SenderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + ReceiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");// Default \91NPS001\92
			sb.append("    <RequestID>SellRequest</RequestID>");
			sb.append("    <ResponseID>SellResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <SellRequest>");
			sb.append("      <PackageDetails>");
			sb.append("        <PackageID>" + price.getAltid() + "</PackageID>");
			sb.append("        <NumberOfAdults>" + reservation.getAdult() + "</NumberOfAdults>");
			sb.append("        <NumberOfChildren>" + reservation.getChild() + "</NumberOfChildren>");
			sb.append("        <NumberOfBabies>0</NumberOfBabies>");
			sb.append("        <DepartureDate>" + DF.format(reservation.getFromdate()) + "</DepartureDate>");
			sb.append("        <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("      </PackageDetails>");
			sb.append("    </SellRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");
			rq = sb.toString();

			// reservation.
			// sequence SellRequest.xml -> AssignRequest -> BookRequest
			LOG.debug("\nSellRequest rq: \n" + rq + "\n");
			LOG.error("Block 6 entry");
			rs = getConnection(rq);
			LOG.error("Block 7 entry");
			LOG.debug("\nSellRequest rs: \n" + rs + "\n");

			// AssignResuest.xml
			sb = new StringBuilder();
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + SenderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + ReceiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");
			sb.append("    <RequestID>AssignRequest</RequestID>");
			sb.append("    <ResponseID>AssignResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <AssignRequest>");
			sb.append("      <PaxDetails>");
			sb.append("        <PaxDescription>");
			sb.append("          <PaxID>P01</PaxID>");
			sb.append("          <Initials>" + customer.getFirstName().substring(0, 1) + "</Initials>");
			sb.append("          <Infix>''</Infix>");
			sb.append("          <Name>" + customer.getFamilyName() + "</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("        </PaxDescription>");
			sb.append("        <HomeStay>");
			sb.append("          <Name>" + customer.getName() + "</Name>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>");
			sb.append("        </HomeStay>");
			sb.append("      </PaxDetails>");
			sb.append("      <PaxAssignment>");
			sb.append("        <AccoAssignment>");
			sb.append("          <AccommodationID>" + "A" + product.getAltid() + "</AccommodationID>");
			// <AccommodationID> 'A' + HouseID, e.g. 'A1712' if the XML contained <HouseID>1712</HouseID>.
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("          <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("          <UnitAssignment>");
			sb.append("            <UnitID>U01</UnitID>");
			sb.append("            <Quantity>1</Quantity>");
			sb.append("            <RoomAssignment>");
			sb.append("              <Sequence>1</Sequence>");
			sb.append("              <PaxID>P01</PaxID>");
			sb.append("            </RoomAssignment>");
			sb.append("          </UnitAssignment>");
			sb.append("        </AccoAssignment>");
			sb.append("      </PaxAssignment>");
			sb.append("    </AssignRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			LOG.debug("\nAssignRequest rq: \n" + rq + "\n");
			rs = getConnection(rq);
			LOG.debug("\nAssignRequest rs: \n" + rs + "\n");

			// /BookRequest.xml
			sb = new StringBuilder();
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + SenderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + ReceiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");
			sb.append("    <RequestID>BookRequest</RequestID>");
			sb.append("    <ResponseID>BookResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <BookRequest>");
			sb.append("      <PaxDetails>");
			sb.append("        <PaxDescription>");
			sb.append("          <PaxID>P01</PaxID>");
			sb.append("          <Initials>" + customer.getFirstName().substring(0, 1) + "</Initials>");
			sb.append("          <Infix>" + "de" + "</Infix>");
			sb.append("          <Name>" + customer.getFamilyName() + "</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("        </PaxDescription>");
			sb.append("        <Contact>");
			sb.append("          <Initials>"+customer.getFirstName().substring(0,1)+"</Initials>");
			sb.append("          <Name>"+customer.getFamilyName()+"</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("          <Address>" + customer.getLocalAddress() + "</Address>");
			sb.append("          <Zipcode>"+customer.getPostalcode()+"</Zipcode>"); ///makse sure their is a value
			sb.append("          <Place>"+customer.getLocalAddress()+"</Place>");
			sb.append("          <Country>" + customer.getCountry() + "</Country>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>"); // TODO should be nightphone
			sb.append("          <TelWork>" + customer.getDayphone() + "</TelWork>");
			sb.append("          <TelMobile>" + customer.getDayphone() + "</TelMobile>");
			sb.append("          <Email>" + customer.getEmailaddress() + "</Email>");
			sb.append("          <BankAccount>0000</BankAccount>");
			sb.append("        </Contact>");
			sb.append("        <HomeStay>");
			sb.append("          <Name>" + customer.getName() + "</Name>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>");
			sb.append("        </HomeStay>");
			sb.append("      </PaxDetails>");
			sb.append("      <PaxAssignment>");
			sb.append("        <AccoAssignment>");
			sb.append("          <AccommodationID>" + "A" + product.getAltid() + "</AccommodationID>");
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("          <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("          <UnitAssignment>");
			sb.append("            <UnitID>U01</UnitID>");
			sb.append("            <Quantity>1</Quantity>");
			sb.append("            <RoomAssignment>");
			sb.append("              <Sequence>1</Sequence>");
			sb.append("              <PaxID>P01</PaxID>");
			sb.append("            </RoomAssignment>");
			sb.append("          </UnitAssignment>");
			sb.append("        </AccoAssignment>");
			sb.append("      </PaxAssignment>");
			sb.append("    </BookRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			LOG.debug("\nBookRequest rq: \n" + rq + "\n");

			createBooking(rq, reservation, timestamp, result);
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		LOG.error(">>>NextPax CreateBooking finished");
		MonitorService.monitor(message, new Date(timestamp));
	}

	/**
	 * Read reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "NextPax readReservation()");
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Update reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "NextPax updateReservation()");
		} catch (Throwable x) {
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
	@Override
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "confirmReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			WebClient webClient = WebClient.create(BASE_NEXTPAX_URL);
			webClient.path("comm/bookingpal_confirm_option_novasol.php");
			webClient.accept(MediaType.TEXT_XML_TYPE);
			webClient.query("reservation_number", reservation.getAltid()).query("sender_id", SENDERID).query("house_id", product.getAltid()).query("email_address", customer.getEmailaddress());
			Response response = webClient.get();
			if(response.getStatus() != 200) {
				throw new ServiceException(Error.reservation_confirmation);
			}
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}
	
	/**
	 * Cancel reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			// Example of URL: 
			// https://secure.nextpax.com/comm/bookingpal_cancel_novasol.php?
			// reservation_number=[NOVASOL_RESERVATION_NUMBER]
			// &sender_id=[YOUR_SENDER_ID]
			// &house_id=[NEXTPAX_HOUSE_ID]
			// &email_address=[CUSTOMER_EMAIL]
			String email = "";
			if (!reservation.getEmailaddress().isEmpty()) {
				email = reservation.getEmailaddress();
			} else {
				Party renter = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
				email = renter.getEmailaddress();
			}
			String customUrl = CANCELLATION_URL +
					"reservation_number=" + reservation.getAltid() + // RESERVATION_NUMBER
					"&sender_id=" + SENDERID + // SENDER_ID (SENDERID)
					"&house_id=" + product.getAltid() + // HOUSE_ID (ALTID)
					"&email_address=" + email; // CUSTOMER_EMAIL
			URL url = new URL(customUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(connection.getInputStream());
			Element rootNode = document.getRootElement();
			connection.disconnect();
			
			if (rootNode.getChild("Success") != null) {
				message = rootNode.getChildText("Success");
			} else if (rootNode.getChild("Error") != null) {
				message = rootNode.getChildText("Error");
				throw new ServiceException(Error.reservation_api, message);
			} else {
				message = "Something went wrong, we didn't receive any response from NextPax API";
				throw new ServiceException(Error.reservation_api, message);
			}
			
			LOG.debug(message);
			
		} catch (Throwable x) {
			reservation.setMessage(x.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	
	public void extractUniqueAdditionalFees(String[] srcs, String[] dests) {
		int i = 0;
		for (String src: srcs) {
			extractUniqueAdditionalFees(src, dests[i]);
			i++;
		}
	}
	
	
	public void extractUniqueAdditionalFees(String src, String dest){
		BufferedWriter bw = null;

		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(new File(src));
			Element rootNode = document.getRootElement();

			bw = new BufferedWriter(new FileWriter(dest));
			HashSet<String> fees = new HashSet<String>();

			List<Element> addCosts = rootNode.getChildren("AdditionalCosts");
			
			for (Element addCost : addCosts) {
			
				List<Element> costs = addCost.getChildren("AdditionalCost");
				for (Element costData : costs) {
					String costCode = costData.getChildText("CostCode");
					String costType = costData.getChildText("CostType");
					String costAmountType = costData.getChildText("CostAmountType");
					
					String fee;
					StringBuilder str = new StringBuilder();
					StringBuilder fullStr = new StringBuilder();
					
					str.append(costCode).append(" ").append(costType).append(" ").append(costAmountType);
					fee = str.toString();
					
					if (!fees.contains(fee)) {
						fees.add(fee);
						bw.write(str.append("\n").toString());
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void extractAdditionalFees(String[] srcs, String[] dests) {
		int i = 0;
		for (String src: srcs) {
			extractAdditionalFees(src, dests[i]);
			i++;
		}
	}
	
	
	public void extractAdditionalFees(String src, String dest) {
		BufferedWriter bw = null;

		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(new File(src));
			Element rootNode = document.getRootElement();

			bw = new BufferedWriter(new FileWriter(dest));
			HashMap<String, Integer> fees = new HashMap<String, Integer>();

			List<Element> addCosts = rootNode.getChildren("AdditionalCosts");
			
			for (Element addCost : addCosts) {
			
				List<Element> costs = addCost.getChildren("AdditionalCost");
				//Element costData = addCost.getChild("AdditionalCost");
				for (Element costData : costs) {
					String costCode = costData.getChildText("CostCode");
					String costType = costData.getChildText("CostType");
					String costAmountType = costData.getChildText("CostAmountType");
					
					String fee;
					StringBuilder str = new StringBuilder();
					StringBuilder fullStr = new StringBuilder();
					
					str.append(costCode).append(" ").append(costType).append(" ").append(costAmountType);
					fullStr.append(addCost.getChildText("HouseID")).append(" ").append(str).append("\n");
					fee = str.toString();
					
					if (!fees.containsKey(fee)) {
						fees.put(fee, 0);
						bw.write(fullStr.toString());
						continue;
					}
					if (fees.get(fee) < 9) {
						fees.put(fee, fees.get(fee)+1);
						bw.write(fullStr.toString());
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Transfer accommodation alerts.
	 * 
	 * @param sqlSession the current SQL session.
	 */
	@Override
	public synchronized void readAlerts() {
		Date version = new Date();
		
		LOG.debug("readAlerts NextpaxAPIKEY: " + getApikey() + "STARTED");
		int i = 0;
		String message = "readAlerts " + getAltpartyid();
		LOG.debug(message);
		String fn = null;
		String language;
		String text;
		String startdate;
		String enddate;
		float deposit = 0;
		final SqlSession sqlSession = RazorServer.openSession();
		String partyId = getAltpartyid();
		
		SAXBuilder parser = new SAXBuilder();
		Document document;
		net.cbtltd.shared.Alert action = new net.cbtltd.shared.Alert();
		try {
		
			fn = "paxgenerator_house_notices_" + getApikey() + ".xml";
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(ftp(fn)));
				String line;
				while((line = br.readLine()) !=null) {
					while(line.contains("&")) {
						line = line.replace("&", "");
					}
					sb.append(line);
				}
				br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
						
			document = parser.build(new ByteArrayInputStream(sb.toString().getBytes()));		 
			Element rootNode = document.getRootElement();				
			List<Element> notices = rootNode.getChildren("Notices");
		
			for (Element note : notices) {
				String altid = note.getChildText("HouseID");
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(getAltpartyid(), altid));
				if (product == null) {
					LOG.error(Error.product_id.getMessage());
					continue;
				}

				HashMap<String, String> texts = new HashMap<String, String>(); 
				List<Element> descriptions = note.getChildren("Notice");
				for (Element description : descriptions) {
					language = description.getChildText("Language");
					text = description.getChildText("Text");
					
					String trimText = text.trim();
					int depositIndex = trimText.indexOf("deposit:");
					
					if (depositIndex >= 0) {
						deposit = Float.parseFloat(trimText.substring(depositIndex + 9, 
						trimText.indexOf(" ", depositIndex + 9)).replace(",", "."));
						LOG.debug("House deposit: " + deposit);
					}
					
					startdate = description.getChildText("From");
					enddate = description.getChildText("Until");

					action.setEntitytype(NameId.Type.Product.name());
					action.setEntityid(product.getId());
					action.setFromdate(DF.parse(startdate));
					action.setTodate(DF.parse(enddate));
					action.setLanguage(language);
					action.setName(text);

					net.cbtltd.shared.Alert duplicate = sqlSession.getMapper(AlertMapper.class).duplicate(action);
					if (duplicate == null) {
						sqlSession.getMapper(AlertMapper.class).create(action);
					} else {
						sqlSession.getMapper(AlertMapper.class).update(action);
					}
					
					product.setSecuritydeposit((double)deposit);		
					sqlSession.getMapper(ProductMapper.class).update(product);				
					sqlSession.commit();
					LOG.debug(i++ + " " + action.toString());
				}
				sqlSession.commit();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
			delete(fn);
		}
		LOG.debug("readAlerts NextpaxAPIKEY: " + getApikey() + " DONE");
		MonitorService.monitor(message, version);
	}

	private static HashMap<String, String> AMOUNTTYPE = null;

	private static final String getAmountType(String amounttype) {
		if (AMOUNTTYPE == null) {
			AMOUNTTYPE = new HashMap<String, String>();
			AMOUNTTYPE.put("BAG ", "Price per refuse bag");
			AMOUNTTYPE.put("CUB ", "Per cubic meter");
			AMOUNTTYPE.put("DAY ", "Price per day");
			AMOUNTTYPE.put("DAU ", "Price per day per unit");
			AMOUNTTYPE.put("HOU", " Per hour");
			AMOUNTTYPE.put("KWH ", "Price per kWh");
			AMOUNTTYPE.put("MON ", "Price per month");
			AMOUNTTYPE.put("LIT ", "Price per litre");
			AMOUNTTYPE.put("NIG", " Price per night");
			AMOUNTTYPE.put("NOR", " Entity not relevant");
			AMOUNTTYPE.put("PAC ", "Price per set");
			AMOUNTTYPE.put("PER ", "Per person");
			AMOUNTTYPE.put("PPD", " Price per person per day");
			AMOUNTTYPE.put("PPH ", "Price per person per hour");
			AMOUNTTYPE.put("PPN ", "Price per person per night");
			AMOUNTTYPE.put("PPS", " Price per person per stay");
			AMOUNTTYPE.put("PPW ", "Price per person per week");
			AMOUNTTYPE.put("STA ", "Price per stay");
			AMOUNTTYPE.put("STU ", "Price per stay per unit");
			AMOUNTTYPE.put("UNI", " Price per unit");
			AMOUNTTYPE.put("USE", " Based on usage");
			AMOUNTTYPE.put("WEE ", "Price per week");
			AMOUNTTYPE.put("WEU", "Price per week per unit");
		}
		return AMOUNTTYPE.get(amounttype);
	}

	private static HashMap<String, String> COSTCODE = null;

	private static final String getCostCode(String costcode) {
		if (COSTCODE == null) {
			COSTCODE = new HashMap<String, String>();
			COSTCODE.put("AIR", "Air conditioning");
			COSTCODE.put("BAT", "Bath linen");
			COSTCODE.put("BBE", "Baby bed");
			COSTCODE.put("BED", "Bed linen");
			COSTCODE.put("BEM", "Bed making");
			COSTCODE.put("BIK", "Bikes");
			COSTCODE.put("BRF", "Breakfast");
			COSTCODE.put("BSK", "Basket");
			COSTCODE.put("C6N", "Final cleaning for < 6 nights");
			COSTCODE.put("CBE", "Child bed");
			COSTCODE.put("CCH", "Child chair");
			COSTCODE.put("CFR", "Toeslag kindvriendelijke woning");
			COSTCODE.put("CHP", "Kinderpakket (kinderstoel en kinderbed)");
			COSTCODE.put("CRP", "Parking place");
			COSTCODE.put("DCL", "Dishcloths");
			COSTCODE.put("DEP", "Guarantee / deposit");
			COSTCODE.put("ELC", "Electricity");
			COSTCODE.put("ENE", "Energy use");
			COSTCODE.put("ENS", "Energy use in summer");
			COSTCODE.put("ENV", "Environment tax");
			COSTCODE.put("ENW", "Energy use in winter");
			COSTCODE.put("F10", "Fishing boat/10 hp");
			COSTCODE.put("F15", "Fishing boat /15 hp (small)");
			COSTCODE.put("F25", "Fishing boat /25 hp (maxi)");
			COSTCODE.put("FIN", "Final cleaning");
			COSTCODE.put("FIR", "Firewood");
			COSTCODE.put("GAR", "Garage");
			COSTCODE.put("GAS", "Gas");
			COSTCODE.put("HEA", "Heating");
			COSTCODE.put("HLF", "Half-board");
			COSTCODE.put("ICL", "Interim cleaning");
			COSTCODE.put("INT", "Internet");
			COSTCODE.put("KAY", "Kayak");
			COSTCODE.put("KIT", "Kitchen linen");
			COSTCODE.put("G7", "Super-XML 33/43");
			COSTCODE.put("L6N", "Bed & bath linen for < 6 nights");
			COSTCODE.put("LAD", "Legoland ticket, adult");
			COSTCODE.put("LAY", "Layette (baby)");
			COSTCODE.put("LCH", "Legoland ticket, child");
			COSTCODE.put("LIN", "Linen (First set bed- en bath linen)");
			COSTCODE.put("LOC", "Local taxes");
			COSTCODE.put("LWC", "Weekly change linen (bed & bath linen)");
			COSTCODE.put("MBO", "Motorboat");
			COSTCODE.put("MOT", "Motor");
			COSTCODE.put("OIL", "Oil");
			COSTCODE.put("PCL", "Pet cleaning fee");
			COSTCODE.put("PET", "Pets");
			COSTCODE.put("PLH", "Pool heating");
			COSTCODE.put("ROW", "Rowing boat");
			COSTCODE.put("SAT", "Satellite TV");
			COSTCODE.put("SER", "Service package");
			COSTCODE.put("TEL", "Telephone");
			COSTCODE.put("TOC", "Tourist tax children");
			COSTCODE.put("TOU", "Tourist tax");
			COSTCODE.put("TWL", "Towels");
			COSTCODE.put("TWP", "Towel package (bath sheet & bath towel)");
			COSTCODE.put("WAT", "Water");
			COSTCODE.put("XBD", "Extra bed");
			COSTCODE.put("XCP", "Extra parking place");
			COSTCODE.put("XPT", "Extra person");
		}
		if (costcode == null) {
			return "";
		} else if (COSTCODE.get(costcode.toUpperCase()) == null) {
			return "";
		} else {
			return COSTCODE.get(costcode.toUpperCase());
		}
	}

	// This is only for additional cost. TODO://check for breakfest and if it is
	// 0 put ATTRIBUTES.put("breakfast", "HAC138");

	
	public void readFees() {
		
		Date version = new Date();
		String message = "readPrices NextpaxAPIKEY: " + this.getApikey() + "STARTED";
		LOG.debug(message);

		String fn = "c:\\parsing\\p.xml";
		
		final SqlSession sqlSession = RazorServer.openSession();
		try {
		
			SAXBuilder parser = new SAXBuilder();
		
		//	Document document = parser.build(ftp(fn));
			Document document = parser.build(new FileInputStream(fn));
			
			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("AdditionalCosts");
			int i = 0;
			for (Element house : houses) {
				String altid = house.getChildText("HouseID");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);

				if (product == null) {
					continue;
				}

				List<Element> costs = house.getChildren("AdditionalCost");
				for (Element cost : costs) {
					LOG.debug("cost " + cost);
					String costcode = cost.getChildText("CostCode");
					String costtype = cost.getChildText("CostType");
					String costamount = cost.getChildText("CostAmount");
					String costamounttype = cost.getChildText("CostAmountType");
					String costcurrency = cost.getChildText("CostCurrency");
					String number = cost.getChildText("Number");
					String from = cost.getChildText("From");
					String until = cost.getChildText("Until");
					
					Fee feeObject = new Fee();
					feeObject.setProductId(product.getId());
					feeObject.setPartyId(product.getAltpartyid());
					//feeObject.setState(Fee.CREATED);
				
					feeObject.setName(getCostCode(costcode));
					feeObject.setEntityType(21);
					sqlSession.getMapper(FeeMapper.class).create(feeObject);
                   
			
				
				}
				sqlSession.commit();
				LOG.debug("readPrices NextpaxAPIKEY: " + this.getApikey() + "DONE");
			}
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			// x.printStackTrace();
		} finally {
			sqlSession.close();
			delete(fn);
		}
		MonitorService.monitor(message, version);
		
	}
	
	@Override
	public void readPrices() {
		Date version = new Date();
		String message = "readPrices NextpaxAPIKEY: " + this.getApikey() + "STARTED";
		LOG.debug(message);

		String fn = "paxgenerator_house_additional_costs_" + getApikey() + ".xml";
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			// costs
			// additional_costs
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(ftp(fn));
			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("AdditionalCosts");
			int i = 0;
			for (Element house : houses) {
				String altid = house.getChildText("HouseID");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);

				if (product == null) {
					continue;
				}

				List<Element> costs = house.getChildren("AdditionalCost");
				for (Element cost : costs) {
					LOG.debug("cost " + cost);
					String costcode = cost.getChildText("CostCode");
					String costtype = cost.getChildText("CostType");
					String costamount = cost.getChildText("CostAmount");
					String costamounttype = cost.getChildText("CostAmountType");
					String costcurrency = cost.getChildText("CostCurrency");
					String number = cost.getChildText("Number");
					String from = cost.getChildText("From");
					String until = cost.getChildText("Until");
					
					Price price = new Price();
					price.setPartyid(getAltpartyid());
					price.setEntitytype(NameId.Type.Product.name());
					price.setEntityid(product.getId());// productID
					price.setCurrency(costcurrency);
					price.setQuantity(0.0);
					price.setUnit(costamounttype);
					price.setName(getCostCode(costcode));
					price.setType(costtype.equalsIgnoreCase("MAN") ? NameId.Type.Mandatory.name() : NameId.Type.Feature.name());
					price.setDate(DF.parse(from));
					price.setTodate(DF.parse(until));
					price.setAvailable(1);

					LOG.debug("price " + price);
					Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
					if (exists == null) {
						sqlSession.getMapper(PriceMapper.class).create(price);
					} else {
						price = exists;
					}

					// price.setAltid(costcode);
					price.setAltpartyid(getAltpartyid());
					price.setFactor(1.0);
					price.setOrganizationid(getAltpartyid());
					price.setRule(Price.Rule.FixedRate.name());
					price.setState(Price.CREATED);
					price.setValue(Double.valueOf(costamount));
					price.setMinimum(0.0);
					price.setVersion(version);
					sqlSession.getMapper(PriceMapper.class).update(price);
					// sqlSession.getMapper(PriceMapper.class).cancelversion(price);
					// If it just has not been updated we are deleting it.
					// Will decide how we want to handle updates
				}
				sqlSession.commit();
				LOG.debug("readPrices NextpaxAPIKEY: " + this.getApikey() + "DONE");
			}
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			// x.printStackTrace();
		} finally {
			sqlSession.close();
			delete(fn);
		}
		MonitorService.monitor(message, version);
	}

	/**
	 * Transfer accommodation products.
	 * 
	 * @param sqlSession the current SQL session.
	 */
	@Override
	public synchronized void readProducts() {
		String message = "Read Products started NextpaxAPIKey: " + getApikey();
		LOG.debug(message);
		Date version = new Date();
		HashSet<String> productsProceeded = new HashSet<String>(); 

		try {
			createOrUpdateProducts(productsProceeded);
			LOG.debug("NextpaxAPIKEY: " + getApikey() + " Products update done.");
			readHousePropertyCodes();
			LOG.debug("NextpaxAPIKEY: " + getApikey() + " readHousePropertyCodes() done.");
			updateInactiveProducts(productsProceeded);
			// readDescriptions();
			// LOG.debug("NextpaxAPIKEY: "+getApikey()+" readDescriptions(); done.");
			// update/create images.
			// createImages();
			// readImages();
			// setLocation();
			// LOG.debug("NextpaxAPIKEY: "+getApikey()+" setLocation done.");
			LOG.debug("NextpaxAPIKEY: " + getApikey() + " ReadProducts_DONE");
			MonitorService.monitor(message, version);
		} catch (Throwable x) {
			LOG.error(x.getStackTrace());
		}
	}

	private void updateInactiveProducts(HashSet<String> productsProceeded) {
		LOG.debug("Starting update for inactive products");
		String partyId = getAltpartyid();
		final SqlSession sqlSession = RazorServer.openSession();
		HashSet<String> activeProducts = new HashSet<String>();
		activeProducts.addAll(sqlSession.getMapper(ProductMapper.class).activeProductAltIdListBySupplier(partyId));
		try {
			activeProducts.removeAll(productsProceeded);
			for (String altId : activeProducts) {
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(partyId, altId));
				product.setState(Product.FINAL);
				sqlSession.getMapper(ProductMapper.class).update(product);
				LOG.debug("Product " + product.getName() + ", "+ product.getId() + " inactive. Moving to Final state.");
			}
			LOG.debug("Update for inactive products finished, no errors");
		} catch (Throwable e){
			LOG.error("Could not finish update for inactive products, reason: " + e.getMessage());
		}
	}
	
	public void testCreateOrUpdateProducts() {
		HashSet<String> productsProceeded = new HashSet<String>();
		createOrUpdateProducts(productsProceeded);
	}

	private void createOrUpdateProducts(HashSet<String> productsProceeded) {
		long startTime = System.currentTimeMillis();
		final SqlSession sqlSession = RazorServer.openSession();

		String partyId = getAltpartyid();
		String fileName = "paxgenerator_houses_" + getApikey() + ".xml";
		Map<String, Location> locations = new HashMap<String, Location>();
		try {
			// House attributes.
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, partyId, new Date().toString());
			SAXBuilder parser = new SAXBuilder();

			long startParsing = System.currentTimeMillis();
			Document document = null;
			//document = parser.build(ftp(fileName));
			System.out.println("HERE IS FILE PARSING");
			
			 document = parser.build(new File(System.getProperty("user.home")
			 + File.separator
			 + "PMS"
			 + File.separator
			 + "nextpax"
			 + File.separator
			 + getApikey()
			 + File.separator + fileName));
			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");

			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("House");
			int i = 0;
			for (Element house : houses) {
				ArrayList<String> attributes = new ArrayList<String>();
				// ArrayList<NameId> images = new ArrayList<NameId>();
				// StringBuilder description = new StringBuilder();

				String altid = house.getChildText("HouseID");
				String country = house.getChildText("Country");
				String region = house.getChildText("Region"); // might be empty? TODO check what to do in such case
				if (StringUtils.isNotBlank(region) && region.length() > 2) {
					region = region.substring(2);
				}
				String name = house.getChildText("Name"); // sometimes empty
				String place = house.getChildText("Place");
				String zipCode = house.getChildText("ZipCode");
				String minchildren = house.getChildText("MinChildren"); // MinChildren
				String minpersons = house.getChildText("MinPersons"); // MaxPersons
				String maxpersons = house.getChildText("MaxPersons");
				String currency = house.getChildText("Currency");
				String longitudeValue = house.getChildText("Longitude");
				String latitudeValue = house.getChildText("Latitude");
				
				Double longitude = longitudeValue == null ? null : Double.valueOf(longitudeValue);
				Double latitude = latitudeValue == null ? null : Double.valueOf(latitudeValue);
				
				// attribute list
				String pool = house.getChildText("Pool");
				String tennis = house.getChildText("Tennis");
				String ski = house.getChildText("Ski");
				String type = house.getChildText("Type");
				String numberofstars = house.getChildText("NumberOfStars");
			    String numberofpets= house.getChildText("NumberOfPets");
			    
			    
				// String water = house.getChildText("Water"); // mapping is not known, probable match LOC33
				// String arrivalday = house.getChildText("ArrivalDay"); // we may need this.
				// String brand = house.getChildText("Brand");

				// build the list
			   
				if (StringUtils.isNotBlank(pool) && pool.equals("Y")) {
					attributes.add(ATTRIBUTES.get("pool"));
				}
				if (StringUtils.isNotBlank(tennis) && tennis.equals("Y")) {
					attributes.add(ATTRIBUTES.get("tennis"));
				}
				if (StringUtils.isNotBlank(ski) && ski.equals("Y")) {
					attributes.add(ATTRIBUTES.get("ski"));
				}
				
			   if (Integer.parseInt(numberofpets) > 0){
					attributes.add(ATTRIBUTES.get("All pets"));				
			   }
			   
				if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(HOUSE_TYPES.get(type))) {
					if (type.equals("APO")) {
						String[] apartmentTypes = HOUSE_TYPES.get(type).split(",");
						for (String ota : apartmentTypes) {
							attributes.add(ota);
						}
					} else {
						attributes.add(HOUSE_TYPES.get(type));
					}
				} else {
					LOG.debug("Property type is not available for product <" + altid + ">: " + type);
				}

				// we can use these later on when we build POI database. Nextpax provides separate feed for holiday parks
				String holidaypark = house.getChildText("HolidayPark");
				String skiarea = house.getChildText("SkiArea");

				Product product = PartnerService.getProduct(sqlSession, partyId, altid);
				Location location;
				if (product != null && product.getLocationid() != null) {
					location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
				} else {
					location = locations.get(StringUtils.isNotBlank(place) ? place + country : zipCode + country);
				}
				// set location if null
				// look in the database first
				// then do a google geocode API lookup
				if (location == null && (StringUtils.isBlank(place) || PATTERN_FOR_SPECIAL_CHARACTERS.matcher(place).find())) {
					location = PartnerService.getLocation(
							sqlSession,
							zipCode,
							country,
							latitude,
							longitude);
					locations.put(StringUtils.isNotBlank(place) ? place + country : zipCode + country, location);
				} else if (location == null) {
					place = ISO8859CharacterNormalizer.decode(place);
					location = PartnerService.getLocation(
							sqlSession,
							place,
							region,
							country,
							latitude,
							longitude,
							zipCode);
					locations.put(place + country, location); // will create too big map. Maybe use only location ID instead of whole object
				}

				// set the name if it is blank
				if (StringUtils.isBlank(name) && location != null) {
					String locationName = location.getName() == null ? location.getGname() : location.getName();
					// sometimes GeoLocation returns null, in this case, use gname.
					locationName = locationName == null ? location.getAdminarea_lvl_1() : locationName;
					name = "House" + " in " + locationName; // Set the Name to be sure it will not be null after this point.
					if (attributes.size() > 0) {
						ArrayList<NameId> pctList = sqlSession.getMapper(AttributeMapper.class)
								.pctListValue(new net.cbtltd.shared.Attribute("PCT", attributes.get(0).substring("PCT".length())));
						for (NameId attribute : pctList) { // what if list is empty?
							name = attribute.getName() + " in " + locationName;
						}
					}
				} // check if this might go for 'else'

				if (product == null) {
					LOG.debug("Incative property <" + altid + ">. Updating...");
					//according to the code, following line will give us exact property, but with inactive state.
					product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(partyId, altid));
				}
				product = createOrUpdateProductModel(location, place,
						region, zipCode, country, maxpersons, minchildren,
						numberofstars, currency, latitude, longitude, name,
						product);
				sqlSession.getMapper(ProductMapper.class).update(product);
				productsProceeded.add(altid);

				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE, product.getId(), attributes);

				LOG.debug(i++ + " " + altid + " " + product.getId() + " " + product.getId() + " " + product.getName());
				sqlSession.commit();
			}
		} catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} finally {
//			sqlSession.commit(); //TODO remove? not sure if we need this
			sqlSession.close();
			delete(fileName);
		}
		LOG.debug("Time taken to complete create products for " + getApikey()
				+ " : " + ((System.currentTimeMillis() - startTime)) / 1000 + " seconds.");
	}

	// Leave for now.
	private void setLocation() {
		final SqlSession sqlSession = RazorServer.openSession();
		String altid = "";
		Product product;

		long startTime = System.currentTimeMillis();
		String fileName = "paxgenerator_houses_" + getApikey() + ".xml";
		Map<String, Location> locations = new HashMap<String, Location>();
		int i = 0;
		try {
			// RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			SAXBuilder parser = new SAXBuilder();
			long startParsing = System.currentTimeMillis();
			Document document = null;
			document = parser.build(new File(System.getProperty("user.home")
					+ File.separator + "PMS" + File.separator + "nextpax"
					+ File.separator + fileName));
			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");

			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("House");
			for (Element house : houses) {
				altid = house.getChildText("HouseID");
				ArrayList<String> attributes = new ArrayList<String>();
				ArrayList<NameId> images = new ArrayList<NameId>();
				StringBuilder description = new StringBuilder();

				String country = house.getChildText("Country");
				String region = house.getChildText("Region");
				if (StringUtils.isNotBlank(region) && region.length() > 2) {
					region = region.substring(2);
				}
				String place = house.getChildText("Place");
				String zipCode = house.getChildText("ZipCode");
				String name = house.getChildText("Name");
				String latitude = house.getChildText("Latitude");
				String longitude = house.getChildText("Longitude");
				// attribute list
				String type = house.getChildText("Type");
				// build the list
				if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(HOUSE_TYPES.get(type))) {
					if (type.equals("APO")) {
						String[] apartmentTypes = HOUSE_TYPES.get(type).split(",");
						for (String ota : apartmentTypes) {
							attributes.add(ota);
						}
					} else {
						attributes.add(HOUSE_TYPES.get(type));
					}
				} else {
					LOG.debug("Property type is not available for product <" + altid + ">: " + type);
				}

				product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {
					LOG.debug("Property does not exist <" + altid + ">");
				} else if (StringUtils.isBlank(product.getLocationid())) {
					Location location = null;
					LOG.debug("Processing location altid/place: " + altid + " " + place);
					location = locations.get(StringUtils.isNotBlank(place) ? place + country : zipCode + country);
					if (StringUtils.isBlank(place) || PATTERN_FOR_SPECIAL_CHARACTERS.matcher(place).find()) {
						if (location == null) {
							location = PartnerService.getLocation(
									sqlSession,
									zipCode,
									country,
									latitude == null ? null : Double.valueOf(latitude),
									longitude == null ? null : Double.valueOf(longitude));
							locations.put(StringUtils.isNotBlank(place) ? place + country : zipCode + country, location);
						}
					} else {
						if (location == null) {
							try {
								place = ISO8859CharacterNormalizer.decode(place);
							} catch (Exception e) {
								LOG.error("Character mapping does not have mapping for place/altid/country: "
										+ place + ":" + altid + ":" + country);
								continue;
							}
							location = PartnerService.getLocation(
									sqlSession,
									place,
									region,
									country,
									latitude == null ? null : Double.valueOf(latitude),
									longitude == null ? null : Double.valueOf(longitude),
									zipCode);
							locations.put(place + country, location);
						}
					}

					// set the name if it is blank
					if (StringUtils.isBlank(name)) {
						if (attributes.size() > 0) {
							ArrayList<NameId> pctList = sqlSession.getMapper(AttributeMapper.class)
									.pctListValue(new net.cbtltd.shared.Attribute("PCT", attributes.get(0).substring("PCT".length())));
							for (NameId attribute : pctList) {
								name = attribute.getName() + " in " + (StringUtils.isNotBlank(location.getName()) ?
																		location.getName() : place);
							}
						} else {
							name = "House" + " in " + (StringUtils.isNotBlank(location.getName()) ?
														location.getName() : place);
						}
					} else {
						name = ISO8859CharacterNormalizer.decode(name);
					}

					updateProductModelForLocation(location, name, product);
					sqlSession.getMapper(ProductMapper.class).update(product);
					LOG.debug("Location processed altID/Name/Location: "
							+ altid + " " + product.getName() + " "
							+ (location == null ? null : location.getName()));
					i++;
				}
				sqlSession.commit();
			}
		} catch (GoogleLocationLimitException e) {
			sqlSession.commit();
			sqlSession.close();
			LOG.error(e.getMessage());
			LOG.debug("Processing location for house id: " + altid + " total locations processed: " + i);
		} catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} finally {
			sqlSession.close();
		}
		LOG.debug("Time taken to update houses(" + i + ")" + "locations("
				+ locations.size() + ") for " + getApikey() + " : "
				+ ((System.currentTimeMillis() - startTime)) / 1000
				+ " seconds.");
	}

	private Product createOrUpdateProductModel(Location location, String place,
			String region, String zipCode, String country, String maxpersons,
			String minchildren, String numberofstars, String currency,
			Double latitude, Double longitude, String name, Product product) {
		if (location != null) {
			product.setLocationid(location.getId()); // if we will use only loc id, there'll be less data coming in and out between methods.
		} else {
			LOG.debug("No location is available for property: " + product.getAltid());
		}
		product.setLatitude(latitude);
		product.setLongitude(longitude);
		product.setAltitude(0.0);

		StringBuilder address = new StringBuilder();
		if (place != null && !place.isEmpty()) {
			address.append(place).append("\n");
		}
		if (region != null && !region.isEmpty()) {
			address.append(region).append("\n");
		}
		if (zipCode != null && !zipCode.isEmpty()) {
			address.append(zipCode).append("\n");
		}
		if (country != null && !country.isEmpty()) {
			address.append(country).append("\n");
		}
		product.setPhysicaladdress(address.toString());

		String pmID = String.valueOf(getPropertyManagerID(getApikey()));
		product.setAltSupplierId(pmID); // ID of propertyManager.
		product.setAltpartyid(getAltpartyid());
		product.setSupplierid(getAltpartyid());
		product.setPerson(maxpersons == null || maxpersons.isEmpty() ? 1 : Integer.valueOf(maxpersons));
		product.setChild(minchildren == null || minchildren.isEmpty() ? 1 : Integer.valueOf(minchildren));
		product.setInfant(0);
		product.setQuantity(1);
		product.setRating(Integer.valueOf(numberofstars));
		product.setCommission(getCommission());
		product.setSecuritydeposit(Product.DEFAULT_SECUIRTY_DEPOSIT);
		product.setCleaningfee(Product.DEFAULT_CLEANING_FEE);
		product.setDiscount(getDiscount());
		product.setRank(getRank());
		product.setUseonepricerow(true);
		product.setType(Product.Type.Accommodation.name());
		product.setCurrency(currency);
		product.setUnit(Unit.DAY);// don't use week ever.
		product.setWebaddress(getWebaddress());
		product.setServicedays("0000000");
		product.setCode("");
		product.setDynamicpricingenabled(false);
		product.setName(name);
		product.setAdult(0);

		return product;
	}

	private Product updateProductModelForLocation(Location location, String name, Product product) {
		if (product == null) {
			return null;
		}

		if (location != null) {
			product.setLocationid(location.getId());
		} else {
			LOG.debug("No location is available for property: " + product.getAltid());
		}
		product.setName(name);

		return product;
	}

	private void readHousePropertyCodes() {
		long startTime = System.currentTimeMillis();

		SAXBuilder parser = new SAXBuilder();
		Document document = null;
		final SqlSession sqlSession = RazorServer.openSession();
		String fileName = "paxgenerator_house_properties_" + getApikey() + ".xml";
		int updatedHouses = 0;
		try {
			long startParsing = System.currentTimeMillis();
			document = parser.build(ftp(fileName));
			// document = parser.build(new File(System
			// .getProperty("user.home")
			// + File.separator
			// + "PMS"
			// + File.separator
			// + "nextpax"
			// + File.separator
			// + getApikey()
			// + File.separator + fileName));
			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");

			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("Properties");

			for (Element house : houses) {
				String altid = house.getChildText("HouseID");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
				if (product == null) {
					continue;
				}

				Integer toilet = 0;
				LOG.debug("Setting properties for: " + product.getId());
				List<Element> properties = house.getChildren("Property");
				for (Element property : properties) {
					String attrKey = property.getChildText("PropertyCode");
					// Use `case` when we will move to JAVA 7 or higher
					if (attrKey.equals("B02")) {
						int numbedroom = 0;
						try {
							numbedroom = Integer.parseInt(property.getChildText("Value")); // Number of bedrooms.
						} catch (NumberFormatException e) {
							// do nothing...move onto next property
						}
						product.setRoom(numbedroom); // need to decide
						// attribute for bedroom
						if (numbedroom > 8) {
							RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), ATTRIBUTES.get(attrKey) + 9);
						} else if (numbedroom > 0) {
							RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(),
									ATTRIBUTES.get(attrKey) + numbedroom);
						}
					} else if (attrKey.equals("B01")) {
						try {
							product.setBathroom(Integer.parseInt(property.getChildText("Value"))); // Number of bedrooms.
						} catch (NumberFormatException e) {
							// do nothing...move onto next property
						}
					} else if (attrKey.equals("T07")) {
						try {
							toilet = Integer.parseInt(property.getChildText("Value"));
						} catch (NumberFormatException e) {
							// do nothing...move onto next property
						}
					}// do not have mapping do nothing. *Maybe create attributes or add into description?
					else if (attrKey.equals("D02")) {
						// number of double beds
					} else if (attrKey.equals("S02")) {
						// number of single beds
					} else if (attrKey.equals("S03")) {
						// number of sofas
					} else {
						// look up ATTRIBUTE Map
						String attrList = ATTRIBUTES.get(attrKey);
						if (!StringUtils.isEmpty(attrList)) {
							RelationService.delete(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE, product.getId(), attrList);
							// if we are running delta import....this will make sure create will not fail.
							RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE, product.getId(), attrList);
						}
					}
				}

				product.setToilet(toilet);
				if ((product.getRoom() == null || product.getRoom() < 1) || (product.getBathroom() == null)){
					product.setState(Product.SUSPENDED);
				} else {
					product.setState(Product.CREATED);
				}
				product.setAssignedtomanager(true);
				sqlSession.getMapper(ProductMapper.class).update(product);
				sqlSession.commit();
				updatedHouses++;
			}

		} catch (Throwable e) {
			sqlSession.rollback();
			LOG.error(e.getMessage());
		} finally {
			sqlSession.close();
			delete(fileName);
		}
		LOG.debug("Time taken to complete house properties for " + getApikey()
				+ " with number of houses " + updatedHouses + " : "
				+ ((System.currentTimeMillis() - startTime)) / 1000
				+ " seconds.");
	}

	@Override
	public void readDescriptions() {
		long startTime = System.currentTimeMillis();
		String altid;
		SAXBuilder parser = new SAXBuilder();
		Product product;
		Document document = null;

		String fileName = "paxgenerator_house_descriptions_" + getApikey() + ".xml";
		int propertymanagerid = getPropertyManagerID(getApikey());

		final SqlSession sqlSession = RazorServer.openSession();
		try {
			long startParsing = System.currentTimeMillis();

			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");
			document = parser.build(ftp(fileName));
			// document = parser.build(new File(System
			// .getProperty("user.home")
			// + File.separator
			// + "PMS"
			// + File.separator
			// + "nextpax"
			// + File.separator
			// + getApikey()
			// + File.separator + fileName));
			Element rootNode = document.getRootElement();
			List<Element> houses = rootNode.getChildren("Descriptions");
			int i = 0;
			StringBuilder sb;
			for (Element house : houses) {
				altid = house.getChildText("HouseID");
				product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid, false);
				if (product == null) {
					continue;
				}

				HashMap<String, String> texts = new HashMap<String, String>(); // Maps language to its text
				List<Element> descriptions = house.getChildren("Description");
				for (Element description : descriptions) {
					String language = description.getChildText("Language").toUpperCase();
					String text = description.getChildText("Text");
					String type = description.getChildText("Type");
					if (type.equalsIgnoreCase("p")) {
						continue; // price table.
					}
					sb = new StringBuilder();
					if (texts.get(language) == null) {
						sb.append(getDescriptionType(language, type)).append(":").append(text).append("\n");
					} else {
						sb.append(texts.get(language)).append(getDescriptionType(language, type)).append(":").append(text).append("\n");
					}
					texts.put(language, sb.toString().length() <= IsPartner.TEXT_NOTES_LENGTH ?
							sb.toString() : sb.toString().substring(0, IsPartner.TEXT_NOTES_LENGTH));
				}

				if (!texts.containsKey(Language.EN) && RazorConfig.doTranslation()) {// must have an English description!
					String englishTranslation;
					for (String language : texts.keySet()) {
						// sleep for a while to meet google's rate limit
						Thread.sleep(500);
						englishTranslation = TextService.translate(texts.get(language), language, Language.EN);
						if (StringUtils.isNotEmpty(englishTranslation)) {
							texts.put(Language.EN, englishTranslation);
							LOG.debug("English_Translation : " + texts.get(Language.EN));
							break;
						}
					}
				}
				// if(emailPayment(propertymanagerid)){
				// for(String language : texts.keySet()){
				// texts.put(language, NameId.trim(texts.get(language),20000 - EMAILCREDITCARD.length()) + EMAILCREDITCARD);
				// }
				// }
				for (String language : texts.keySet()) {
					LOG.debug("language " + language + " notes " + texts.get(language));
					product.setPublicText(new Text(product.getPublicId(),
							product.getPublicLabel(),
							Text.Type.HTML,
							new Date(),
							texts.get(language),
							language));
					TextService.update(sqlSession, product.getTexts());
				}

				i++;
				if (i > 0 && i % IsPartner.PRICE_BATCH_SIZE == 0) {
					LOG.debug("Smart flush for description: " + i);
					sqlSession.commit();
				}
			}
			sqlSession.commit();
		} catch (Throwable e) {
			LOG.error(e.getMessage());
			sqlSession.rollback();
		} finally {
			sqlSession.close();
			delete(fileName);
		}
		LOG.debug("Time taken to complete product descriptions for " + getApikey() + " : "
				+ ((System.currentTimeMillis() - startTime)) / 1000 + " seconds.");
	}

	public void incremenalImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		long startTime = System.currentTimeMillis();
		Document document;
		String message = "Create Images started NextpaxAPIKey: " + getApikey();
		LOG.debug(message);
		Date version = new Date();

		String fileName = "paxgenerator_house_pictures_" + getApikey() + ".xml";
		try {
			// House attributes.
			// RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			SAXBuilder parser = new SAXBuilder();

			long startParsing = System.currentTimeMillis();
			document = parser.build(ftp(fileName));
			// document = parser.build(new File(System
			// .getProperty("user.home")
			// + File.separator
			// + "PMS"
			// + File.separator
			// + "nextpax"
			// + File.separator
			// + getApikey()
			// + File.separator + fileName));
			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");

			Element rootNode = document.getRootElement();
			String partner = rootNode.getChildText("Partner");
			List<Element> houses = rootNode.getChildren("Pictures");
			for (Element house : houses) {
				String altid = house.getChildText("HouseID");
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {
					continue;
				}
				String lastimage = sqlSession.getMapper(TextMapper.class).lastimage(NameId.Type.Product.name() + product.getId() + "-%");
				// if the images exist move to the next property images
				if (lastimage != null) {
					continue;
				}

				ArrayList<NameId> images = new ArrayList<NameId>();
				List<Element> pictures = house.getChildren("Picture");
				for (Element picture : pictures) {
					String name = picture.getChildText("Name");
					// String size = picture.getChildText("Size");
					String type = picture.getChildText("Type");
					String title = getImageTitle(type);

					images.add(new NameId(title, IMAGE_URL + getBrandAbbreviation(partner) + "/" + altid + "/" + name));
				}
				LOG.debug("Total images uploading for the property " + product.getId() + ": " + images.size());

				UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
				sqlSession.commit();

				MonitorService.monitor(message, version);
			}
		} catch (Throwable e) {
			sqlSession.rollback();
			LOG.error(e.getMessage());
		} finally {
			sqlSession.close();
			delete(fileName);
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for createImage execution " + getApikey()
				+ " : " + (endTime - startTime) / 1000 + " seconds.");
	}

	@Override
	public void readImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		// first download all the images
/*		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration("storage.properties"));
		} catch (ConfigurationException e) {
			LOG.error(e.getMessage());
		}

		String rootFolder = config.getString("bp.root.folder");
		Element rootNode = downloadImages(rootFolder);
		String partner = rootNode.getChildText("Partner");
		List<Element> houses = rootNode.getChildren("Pictures");
		long startTime = System.currentTimeMillis();
*/
		String message = "Read Images started NextpaxAPIKey: " + getApikey();
		LOG.debug(message);
		Date version = new Date();
		String fileName = "paxgenerator_house_pictures_" + getApikey() + ".xml";
		try {
/*			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(ftp(fileName));
			Element rootNode = document.getRootElement();
			String partner = rootNode.getChildText("Partner");
			List<Element> houses = rootNode.getChildren("Pictures");*/
			
			JAXBContext jc = JAXBContext.newInstance(net.cbtltd.rest.nextpax.ProductPictures.class);
			Unmarshaller um = jc.createUnmarshaller();
			ProductPictures productPictures = (ProductPictures) um.unmarshal(ftp(fileName));
			String partner = productPictures.getPartner();
			List<Pictures> houses = productPictures.getPictures();//this should give us list of house objects
			
			// RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());

			for (Pictures pictures : houses) {
				String altid = pictures.getHouseID();
				Product product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {
					continue;
				}
				ArrayList<NameId> images = new ArrayList<NameId>();
				// List<Element> pictures = house.getChildren("Picture");
				for (Picture picture : pictures.getPicture()) {
					String size = picture.getSize(); // need to check size, to prevent loading small images.
																// n - normal, s - small.
					if (size.equalsIgnoreCase("s")) {
						continue; // we don't need small images
					}
					String name = picture.getName();
					String type = picture.getType();
					String title = getImageTitle(type);

					images.add(new NameId(title, IMAGE_URL + getBrandAbbreviation(partner) + "/" + altid + "/" + name));
				}
				LOG.debug("Total images uploading for the property " + product.getId() + ": " + images.size());
				UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);

				sqlSession.commit();
				MonitorService.monitor(message, version);
			}
		} catch (Throwable e) {
			sqlSession.rollback();
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			sqlSession.close();
			delete(fileName);
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for readImage execution " + getApikey() + " : " + (endTime - startTime) / 1000 + " seconds.");
	}

	public Element downloadImages(String rootFolder) {
		long startTime = System.currentTimeMillis();
		String message = "Download Images started NextpaxAPIKey: " + getApikey();
		LOG.debug(message);
		Date version = new Date();
		Document document;

		String fileName = "paxgenerator_house_pictures_" + getApikey() + ".xml";
		Element rootNode = null;
		try {
			// House attributes.
			long startParsing = System.currentTimeMillis();
			SAXBuilder parser = new SAXBuilder();
			// document = parser.build(new File(System
			// .getProperty("user.home")
			// + File.separator
			// + "PMS"
			// + File.separator
			// + "nextpax"
			// + File.separator
			// + getApikey()
			// + File.separator + fileName));
			document = parser.build(ftp(fileName));
			LOG.debug("Time taken to parse " + fileName + " : " + (System.currentTimeMillis() - startParsing) + " milli seconds.");

			rootNode = document.getRootElement();
			String partner = rootNode.getChildText("Partner");
			List<Element> houses = rootNode.getChildren("Pictures");

			for (Element house : houses) {
				String altid = house.getChildText("HouseID");
				ArrayList<NameId> images = new ArrayList<NameId>();
				List<Element> pictures = house.getChildren("Picture");
				for (Element picture : pictures) {
					String size = picture.getChildText("Size"); // need to check size, to prevent loading small images.
																// n - normal, s - small.
					if (size.equalsIgnoreCase("s")) {
						continue; // we don't need small images
					}
					String name = picture.getChildText("Name");
					String type = picture.getChildText("Type");
					String title = getImageTitle(type);
					String filename = getBrandAbbreviation(partner) + "/" + altid + "/" + name;
					if (!new File(rootFolder + "/" + filename).exists()) {
						images.add(new NameId(rootFolder + "/" + filename, IMAGE_URL + filename));
					}
				}
				// multi threading image download and text creation
				// number of thread can be optimized by finding out available resources
				ExecutorService executor = Executors.newFixedThreadPool(25);
				Collection<Future<?>> futures = new LinkedList<Future<?>>();
				// download each image in it's own thread
				for (NameId image : images) {
					try {
						futures.add(executor.submit(new A_Handler.ImageDownloadTask(image.getName(), image.getId())));
					} catch (Throwable x) {
						LOG.error("Bad or Absent Image URL: " + image.getId());
						x.printStackTrace();
					}
				}
				executor.shutdown();
				for (Future<?> future : futures) {
					future.get();
				}
				LOG.debug("images " + images);
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for download image execution " + getApikey() + " : " + (endTime - startTime) / 1000 + " seconds.");
		return rootNode;
	}

	static class ImageDownloadTask extends Thread {
		String filename;
		String imageURL;

		public ImageDownloadTask(String filename, String imageURL) {
			this.filename = filename;
			this.imageURL = imageURL;
		}

		@Override
		public void run() {
			try {
				final long startTime = System.currentTimeMillis();
				URL url = new URL(imageURL.replace(" ", "%20"));
				LOG.error("Downloading file named: " + url);
				BufferedImage image = ImageIO.read(url);
				File file = new File(filename);
				file.getParentFile().mkdirs();
				// file.createNewFile();
				ImageIO.write(image, "jpeg", file);
				final long endTime = System.currentTimeMillis();
				LOG.error("Total time taken to download image " + url + " : " + (endTime - startTime) + " miliseconds.");
			} catch (FileNotFoundException e) {
				LOG.error("Bad or Absent Image URL: " + imageURL + " " + e.getMessage());
			} catch (MalformedURLException e) {
				LOG.error("Bad or Absent Image URL: " + imageURL);
			} catch (IOException e) {
				LOG.error("Bad or Absent Image URL: " + imageURL);
			} catch (Exception e) {
				LOG.error("Error saving image to the filesystem: " + e.getMessage() + " Image URL: " + imageURL);
			}
		}
	}

	private static HashMap<String, Integer> PROPERTY_MANAGER_ID = null;

	private static final int getPropertyManagerID(String abbreviation) {
		abbreviation = abbreviation.toUpperCase();
		if (PROPERTY_MANAGER_ID == null) {
			PROPERTY_MANAGER_ID = new HashMap<String, Integer>();
			PROPERTY_MANAGER_ID.put("NS", 11);
			PROPERTY_MANAGER_ID.put("ER", 12);
			PROPERTY_MANAGER_ID.put("IH", 14);
			PROPERTY_MANAGER_ID.put("BN", 16);
			PROPERTY_MANAGER_ID.put("TU", 17);
			PROPERTY_MANAGER_ID.put("TT", 18);
			PROPERTY_MANAGER_ID.put("IC", 20);
			PROPERTY_MANAGER_ID.put("DC", 21);
			PROPERTY_MANAGER_ID.put("HH", 24);
			PROPERTY_MANAGER_ID.put("VC", 25);
			PROPERTY_MANAGER_ID.put("BS", 26);
			PROPERTY_MANAGER_ID.put("HA", 27);
			PROPERTY_MANAGER_ID.put("HB", 28);
			PROPERTY_MANAGER_ID.put("RP", 29);
			PROPERTY_MANAGER_ID.put("UT", 30);// no longer nextpax partner.
			PROPERTY_MANAGER_ID.put("GA", 71);
			PROPERTY_MANAGER_ID.put("NS", 25);
		}
		if (PROPERTY_MANAGER_ID.get(abbreviation) == null) {
			return -1;
		}
		return PROPERTY_MANAGER_ID.get(abbreviation);
	}

	private static HashMap<String, String> DESCRIPTION_TYPES = null;

	private static final String getDescriptionType(String language, String type) {
		if (DESCRIPTION_TYPES == null) {
			DESCRIPTION_TYPES = new HashMap<String, String>();
			// TODO:Translate to German
			DESCRIPTION_TYPES = new HashMap<String, String>();
			// TODO:Translate to German
			DESCRIPTION_TYPES.put("de-a", "Umwelt/Umgebung ");
			DESCRIPTION_TYPES.put("de-b", "Nebenkosten ");
			DESCRIPTION_TYPES.put("de-c", "Additional costs ");
			DESCRIPTION_TYPES.put("de-d", "Rabatte ");
			DESCRIPTION_TYPES.put("de-e", "Optionale Kosten ");
			DESCRIPTION_TYPES.put("de-h", "Haus ");
			DESCRIPTION_TYPES.put("de-i", "Interior ");
			DESCRIPTION_TYPES.put("de-l", "Ankunft tage ");
			DESCRIPTION_TYPES.put("de-m", "Mindestaufenthalt LРїС—Р…nge ");
			DESCRIPTION_TYPES.put("de-o", "РїС—Р…ffnungszeiten");
			DESCRIPTION_TYPES.put("de-a", "Umwelt/Umgebung");
			DESCRIPTION_TYPES.put("de-b", "Nebenkosten");
			DESCRIPTION_TYPES.put("de-c", "Additional costs");
			DESCRIPTION_TYPES.put("de-d", "Rabatte");
			DESCRIPTION_TYPES.put("de-e", "Optionale Kosten");
			DESCRIPTION_TYPES.put("de-h", "Haus");
			DESCRIPTION_TYPES.put("de-i", "Interior");
			DESCRIPTION_TYPES.put("de-l", "Ankunft tage");
			DESCRIPTION_TYPES.put("de-m", "Mindestaufenthalt LРїС—Р…nge");
			DESCRIPTION_TYPES.put("de-o", "РїС—Р…ffnungszeiten");
			DESCRIPTION_TYPES.put("de-p", "Preis in der Tabelle");
			DESCRIPTION_TYPES.put("de-r", "Remarks ");
			DESCRIPTION_TYPES.put("de-s", "Short introduction ");
			DESCRIPTION_TYPES.put("de-t", "Tips of the owner ");
			DESCRIPTION_TYPES.put("de-w", "Winter-Text ");
			DESCRIPTION_TYPES.put("de-z", "Winter-Text ");
			DESCRIPTION_TYPES.put("en-a", "Environment/surroundings ");
			DESCRIPTION_TYPES.put("en-b", "Distances ");
			DESCRIPTION_TYPES.put("en-c", "Additional costs ");
			DESCRIPTION_TYPES.put("en-d", "Discounts ");
			DESCRIPTION_TYPES.put("en-e", "Optional costs ");
			DESCRIPTION_TYPES.put("en-h", "House ");
			DESCRIPTION_TYPES.put("en-i", "Interior ");
			DESCRIPTION_TYPES.put("en-l", "Arrival days ");
			DESCRIPTION_TYPES.put("en-m", "Minimum stay length ");
			DESCRIPTION_TYPES.put("en-o", "Opening hours ");
			DESCRIPTION_TYPES.put("en-p", "Price table ");
			DESCRIPTION_TYPES.put("en-r", "Remarks ");
			DESCRIPTION_TYPES.put("en-s", "Short introduction ");
			DESCRIPTION_TYPES.put("en-t", "Tips of the owner ");
			DESCRIPTION_TYPES.put("en-w", "Winter text ");
			DESCRIPTION_TYPES.put("en-z", "Short winter text ");
			// TODO:Translate to Espanol
			DESCRIPTION_TYPES.put("es-a", "Environment/surroundings ");
			DESCRIPTION_TYPES.put("es-b", "Distances ");
			DESCRIPTION_TYPES.put("es-c", "Additional costs ");
			DESCRIPTION_TYPES.put("es-d", "Discounts ");
			DESCRIPTION_TYPES.put("es-e", "Optional costs ");
			DESCRIPTION_TYPES.put("es-h", "House ");
			DESCRIPTION_TYPES.put("es-i", "Interior ");
			DESCRIPTION_TYPES.put("es-l", "Arrival days ");
			DESCRIPTION_TYPES.put("es-m", "Minimum stay length ");
			DESCRIPTION_TYPES.put("es-o", "Opening hours ");
			DESCRIPTION_TYPES.put("es-p", "Price table ");
			DESCRIPTION_TYPES.put("es-r", "Remarks ");
			DESCRIPTION_TYPES.put("es-s", "Short introduction ");
			DESCRIPTION_TYPES.put("es-t", "Tips of the owner ");
			DESCRIPTION_TYPES.put("es-w", "Winter text ");
			DESCRIPTION_TYPES.put("es-z", "Short winter text ");
			// TODO:Translate to French
			DESCRIPTION_TYPES.put("fr-a", "Environment/surroundings ");
			DESCRIPTION_TYPES.put("fr-b", "Distances ");
			DESCRIPTION_TYPES.put("fr-c", "Additional costs ");
			DESCRIPTION_TYPES.put("fr-d", "Discounts ");
			DESCRIPTION_TYPES.put("fr-e", "Optional costs ");
			DESCRIPTION_TYPES.put("fr-h", "Maison ");
			DESCRIPTION_TYPES.put("fr-i", "Interior ");
			DESCRIPTION_TYPES.put("fr-l", "Arrival days ");
			DESCRIPTION_TYPES.put("fr-m", "Minimum stay length ");
			DESCRIPTION_TYPES.put("fr-o", "Opening hours ");
			DESCRIPTION_TYPES.put("fr-p", "Price table ");
			DESCRIPTION_TYPES.put("fr-r", "Remarks ");
			DESCRIPTION_TYPES.put("fr-s", "Short introduction ");
			DESCRIPTION_TYPES.put("fr-t", "Tips of the owner ");
			DESCRIPTION_TYPES.put("fr-w", "Winter text ");
			DESCRIPTION_TYPES.put("fr-z", "Short winter text ");
			// TODO:Translate to Dutch
			DESCRIPTION_TYPES.put("nl-a", "Environment/surroundings ");
			DESCRIPTION_TYPES.put("nl-b", "Distances ");
			DESCRIPTION_TYPES.put("nl-c", "Additional costs ");
			DESCRIPTION_TYPES.put("nl-d", "Discounts ");
			DESCRIPTION_TYPES.put("nl-e", "Optional costs ");
			DESCRIPTION_TYPES.put("nl-h", "House ");
			DESCRIPTION_TYPES.put("nl-i", "Interior ");
			DESCRIPTION_TYPES.put("nl-l", "Arrival days ");
			DESCRIPTION_TYPES.put("nl-m", "Minimum stay length ");
			DESCRIPTION_TYPES.put("nl-o", "Opening hours ");
			DESCRIPTION_TYPES.put("nl-p", "Price table ");
			DESCRIPTION_TYPES.put("nl-r", "Remarks ");
			DESCRIPTION_TYPES.put("nl-s", "Short introduction ");
			DESCRIPTION_TYPES.put("nl-t", "Tips of the owner ");
			DESCRIPTION_TYPES.put("nl-w", "Winter text ");
			DESCRIPTION_TYPES.put("nl-z", "Short winter text ");
		}
		String key = language.toLowerCase() + "-" + type.toLowerCase();
		// LOG.debug("key " + key + " value " + DESCRIPTION_TYPES.get(key));
		if (DESCRIPTION_TYPES.get(key) == null) {
			return "";
		}
		return DESCRIPTION_TYPES.get(key);
	}

	private static HashMap<String, String> ATTRIBUTES = null;

	private static HashMap<String, String> HOUSE_TYPES = null;

	/**
	 * Attributes map.
	 * 
	 * @param attributes the attributes
	 * @param attribute the attribute
	 */
	static {
		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("A01", "FAC1"); // airconditionning
			ATTRIBUTES.put("A05", "FAC1"); // airconditionning living room
			ATTRIBUTES.put("A06", "FAC1"); // airconditionning bedroom
			ATTRIBUTES.put("B01", ""); // number of bathrooms...saved in product table
			ATTRIBUTES.put("B02", "ASZ"); // number of bedrooms. We can save up to 8 and anything greater than 8 will go into 9
			ATTRIBUTES.put("B08", "TRP1"); // Bicycles ('Y' = available) ?
			ATTRIBUTES.put("B11", "FAC2"); // Balcony (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("B15", "RMA26"); // babycot
			ATTRIBUTES.put("C01", "SUI1"); // House is suitable for children ('Y' = applicable to this house)
			ATTRIBUTES.put("C07", ""); // Bicycle routes ('Y' = available)
			ATTRIBUTES.put("C08", ""); // Cooker (вЂ�CвЂ™ = ceramic,
										// вЂ�EвЂ™ = electric,
										// вЂ�GвЂ™ = gas,
										// вЂ�IвЂ™ = induction,
										// вЂ�UвЂ™ = type unknown,
										// вЂ�WвЂ™ = wood)
			ATTRIBUTES.put("C11", ""); // Coffee maker (вЂ�EвЂ™ = espresso,
										// вЂ�MвЂ™ = moka,
										// вЂ�RвЂ™ = regular,
										// вЂ�SвЂ™ = Senseo)
			ATTRIBUTES.put("C13", ""); // Child's bed(s)/ baby bed(s) on request
			ATTRIBUTES.put("C02", "HAC51"); // C02 Cleaning service ('Y' = available)
			ATTRIBUTES.put("C06", "RSN6"); // C06 Cross-country skiing ('Y' = available)
			ATTRIBUTES.put("D01", "RMA32"); // Dishwasher ('Y' = available)
			ATTRIBUTES.put("D02", ""); // Number of double beds
			ATTRIBUTES.put("D06", ""); // Douche (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("D07", "RMA163"); // D07 DVD-playerr (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("E01", ""); // Electric kettle
			ATTRIBUTES.put("F02", "RMA41"); // Fireplace or heater ('Y' = available)
			ATTRIBUTES.put("F04", "RMA88"); // Fridge ('Y' = available, вЂ�FвЂ™ = with freezer, вЂ�HвЂ™ = with hood)
			ATTRIBUTES.put("F06", ""); // Freezer (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("G01", "HAC63"); // Garage or parking place ('Y' = available)
			ATTRIBUTES.put("G03", "RST134"); // Garden
			ATTRIBUTES.put("G04", ""); // Garden furniture (вЂ�YвЂ™ = available)
			// ATTRIBUTES.put("H03", "RSN16"); // H03 Horse riding (riding school) ('Y' = available)
			ATTRIBUTES.put("H03", "RST61"); // Horse riding (riding school) ('Y' = available)
			ATTRIBUTES.put("H40", "HAC242"); // Heating ('C' = central heating,
												// 'E' = electric,
												// 'F' = floor,
												// 'G' = gas,
												// вЂ�HвЂ™ = hot air,
												// 'S' = stove, 'Y' = available)
			ATTRIBUTES.put("I02", "RMA54"); // Internet ('Y' = available,
											// вЂ�AвЂ™ = ADSL,
											// вЂ�CвЂ™ = cable,
											// вЂ�IвЂ™ = ISDN,
											// вЂ�SвЂ™ = satellite)
			ATTRIBUTES.put("I03", "RMA55"); // Iron (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("I04", "RMA56"); // Ironing board (вЂ�YвЂ™ = available)
			// Map JAC_ATTRS = new HashMap<String, String>();
			// JAC_ATTRS.put("I")
			ATTRIBUTES.put("J01", "HAC55"); // Jacuzzi ('Y' = available,
											// вЂ�IвЂ™ = indoor available,
											// вЂ�OвЂ™ = outdoor available)
											// HAC55, RMA57
			ATTRIBUTES.put("L01", "RMA13"); // bathtub
			ATTRIBUTES.put("L02", "SEG8"); // House has a luxurious appearance ('Y' = applicable to this house)
			ATTRIBUTES.put("L04", ""); // Laundromat ('Y' = available,
										// 'C' = in the city,
										// 'P' = park facility,
										// вЂ�SвЂ™ = service)
			ATTRIBUTES.put("M02", "RMA68"); // Microwave oven ('Y' = available)
			ATTRIBUTES.put("M03", "LOC25"); // Marina ('Y' = available)
			ATTRIBUTES.put("N01", "RMA74"); // House is for non-smokers ('Y' = applicable to this house)
			ATTRIBUTES.put("O01", "RMA77"); // Oven ('Y' = available, 'E' = electric, 'F' = fan, 'G' = gas, 'M' = microwave)
			ATTRIBUTES.put("P02", "SUI4"); // House is suitable for pets ('Y' = applicable to this house, 'R' = on request)
			ATTRIBUTES.put("P03", "RMA107"); // Telephone ('Y' = available)
			ATTRIBUTES.put("P04", "RST122"); // Indoor swimming pool ('Y' = available, 'P' = private, 'C' = communal)
			ATTRIBUTES.put("P05", "RST123"); // Outdoor swimming pool ('Y' = available, 'P' = private, 'C' = communal)
			ATTRIBUTES.put("P06", ""); // Surface area of house (in m 2 )
			ATTRIBUTES.put("P08", "RST74"); // Playground ('Y' = available)
			ATTRIBUTES.put("P09", "HAC253"); // P09 Private swimming pool, can be inside or outside ('Y' = available)
			ATTRIBUTES.put("P10", "HAC49"); // P10 Heated swimming pool, can be inside or outside (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("P15", "HAC186"); // Number of parking places street side
			ATTRIBUTES.put("S01", "HAC79"); // Sauna ('Y' = available) REC61
			ATTRIBUTES.put("S02", ""); // Number of single beds
			ATTRIBUTES.put("S03", ""); // Number of sofas (Sofa-->CBF19
			ATTRIBUTES.put("S04", "RST90"); // Solarium ('Y' = available)
			ATTRIBUTES.put("S05", "RMA210"); // Satellite TV ('Y' = available)
			ATTRIBUTES.put("S06", "LOC16"); // House with sea view ('Y'= applicable to this house)
			ATTRIBUTES.put("S07", "RSN14"); // S07 House is well suited to go sailing ('Y' = applicable to this house)
			ATTRIBUTES.put("S10", "RSN15"); // S10 House is well suited to go surfing ('Y' = applicable to this house)
			ATTRIBUTES.put("S13", ""); // Situation: Detached
			ATTRIBUTES.put("S14", ""); // Situation: Semi detached
			ATTRIBUTES.put("S17", ""); // Situation: On an island
			ATTRIBUTES.put("S18", "LOC23"); // Situation: In the countryside
			ATTRIBUTES.put("S19", "LOC8"); // Situation: In the mountains > 1000 m.
			ATTRIBUTES.put("S20", ""); // Situation: Residential area
			ATTRIBUTES.put("S21", ""); // Situation: On holiday park
			ATTRIBUTES.put("T01", "RMA90"); // Television ('Y' = available)
			ATTRIBUTES.put("T02", "RMA7"); // Terrace ('Y' = available)
			ATTRIBUTES.put("T04", "RSP94"); // Private tennis court ('Y' = available)
			ATTRIBUTES.put("T05", "RMA140"); // Table tennis ('Y' = available)
												// RST117
			ATTRIBUTES.put("T07", ""); // Number of toilets --save this in product table
			ATTRIBUTES.put("T08", "STP2"); // ?Houses are located together (вЂ�YвЂ™ = applicable to his house)
			ATTRIBUTES.put("T09", "RSP94"); // Shared tennis court (вЂ�YвЂ™ = available)
			ATTRIBUTES.put("V03", ""); // Vacuum cleaner ('Y' = available)
			ATTRIBUTES.put("W01", "RMA31"); // Washing machine ('Y' = available)
			ATTRIBUTES.put("W03", "PHP24"); // House with access for wheelchairs ('Y' = applicable to this house)
			ATTRIBUTES.put("W05", ""); // Walking routes ('Y' = available)
			ATTRIBUTES.put("W06", "RST99"); // Water-skiing ('Y' = available)
			ATTRIBUTES.put("W07", "EQP55"); // WiFi ('Y' = available)
			ATTRIBUTES.put("W09", ""); // Available for longer stay in winter (вЂ�YвЂ™ = applicable to this house)
			ATTRIBUTES.put("W04", "FAC7"); // Whirlpool ('Y' = available)
			// ATTRIBUTES.put("bathandshower", "HAC263");
			ATTRIBUTES.put("bath", "HAC264");
			// ATTRIBUTES.put("bbq", "HAC118");
			// ATTRIBUTES.put("bikingplains", "RSN10");
			// ATTRIBUTES.put("breakfast", "HAC138");
			ATTRIBUTES.put("childrenplayground", "HAC193");
			// ATTRIBUTES.put("elevator", "HAC33");
			// ATTRIBUTES.put("fitness", "RSN12");
			// ATTRIBUTES.put("golfing", "RSN5");
			// ATTRIBUTES.put("hikingmountains", "RSN10");
			// ATTRIBUTES.put("hikingplains", "RSN10");
			// ATTRIBUTES.put("icerink", "RST84");
			// ATTRIBUTES.put("massage", "RSN12");
			// ATTRIBUTES.put("mountainbike", "RSP10");
			ATTRIBUTES.put("parkingprivate", "HAC63");
			ATTRIBUTES.put("parkingcovered", "HAC63");
			ATTRIBUTES.put("pool", "HAC71");
			ATTRIBUTES.put("All pets", "PET7");
			// ATTRIBUTES.put("poolindor", "HAC54");
			// ATTRIBUTES.put("poolchildren", "HAC48");
			// ATTRIBUTES.put("shower", "HAC265");
			ATTRIBUTES.put("ski", "ACC45");
			ATTRIBUTES.put("skiaera", "ACC45");
			// ATTRIBUTES.put("snowboard", "RSN6");
			ATTRIBUTES.put("tennis", "RST94"); // RSN17
			// ATTRIBUTES.put("terrace", "FAC2");
			// ATTRIBUTES.put("themepark", "RSN2");
			// ATTRIBUTES.put("wlan", "HAC221");
			// ATTRIBUTES.put("winterwalking", "RSN6");
			// ATTRIBUTES.put("water", "BEV20");
			ATTRIBUTES.put("1star", "GRD1");
			ATTRIBUTES.put("2star", "GRD2");
			ATTRIBUTES.put("3star", "GRD3");
			ATTRIBUTES.put("4star", "GRD4");
			ATTRIBUTES.put("5star", "GRD5");
		}
		if (HOUSE_TYPES == null) {
			HOUSE_TYPES = new HashMap<String, String>();
			HOUSE_TYPES.put("ALB", "PCT19");
			HOUSE_TYPES.put("APO", "PCT3, PCT20");
			HOUSE_TYPES.put("APP", "PCT3");
			HOUSE_TYPES.put("BNG", "PCT5");
			HOUSE_TYPES.put("BRY", "PCT15");
			HOUSE_TYPES.put("CAM", "PCT6");
			HOUSE_TYPES.put("CAR", "");
			HOUSE_TYPES.put("CHA", "PCT7");
			HOUSE_TYPES.put("CPR", "");
			HOUSE_TYPES.put("CRU", "PCT12");
			HOUSE_TYPES.put("EST", "PCT21");
			HOUSE_TYPES.put("GHF", "PCT21");
			HOUSE_TYPES.put("GUE", "PCT16");
			HOUSE_TYPES.put("HOS", "PCT19");
			HOUSE_TYPES.put("HOT", "PCT20");
			HOUSE_TYPES.put("HUI", "PCT34");
			HOUSE_TYPES.put("KAS", "PCT37");
			HOUSE_TYPES.put("MAI", "PCT34");
			HOUSE_TYPES.put("MOT", "PCT27");
			HOUSE_TYPES.put("PEN", "");
			HOUSE_TYPES.put("POU", "PCT21");
			HOUSE_TYPES.put("RES", "");
			HOUSE_TYPES.put("SCH", "PCT44");
			HOUSE_TYPES.put("TNT", "PCT33");
			HOUSE_TYPES.put("VIL", "PCT35");
			HOUSE_TYPES.put("WNG", "PCT34");
		}
	}

	// canel old reservations that do not have a reservation.altid.
	private void cancelOldReservations(SqlSession sqlSession, Date version) {
		// sqlSession = RazorServer.openSession();
		Reservation reservation = new Reservation();
		reservation.setVersion(version);
		reservation.setAltpartyid(getAltpartyid());
		sqlSession.getMapper(ReservationMapper.class).cancelversionbypartyid(reservation);

		sqlSession.commit();
	}

	private void resetStartTime() {
		startTime = System.currentTimeMillis();
	}

	private long getStartTime() {
		return startTime;
	}

	@Override
	public synchronized void readSchedule() {
		LOG.debug("NextPax readSchedule() key: " + this.getApikey() + "_started");
		Scanner sc = null;
		Date version = new Date();
		String line = "";
		String message = "readSchedule";
		String altid;
		String periodid;
		String startdate;
		String enddate;
		Double length;
		String lastminute;
		String rentprice;
		String bookingcosts;
		String discount;
		String othercosts;
		String totalprice;
		String discountcode;
		String recordhash;
		String[] houseinformation;

		Product product = null;

		String partyId = getAltpartyid();

		boolean firsttime = true;
		long startTime = System.currentTimeMillis();

		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		String filename = "paxgenerator_available_bookingpal_" + getApikey() + ".csv";
		try {
			// RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());

			long startTimeForCSVRead = System.currentTimeMillis();
			sc = new Scanner(ftp(filename));
			// sc = new Scanner(new BufferedReader(new InputStreamReader(
			// new FileInputStream(System.getProperty("user.home")
			// + File.separator
			// + "PMS"
			// + File.separator
			// + "nextpax"
			// + File.separator
			// + getApikey()
			// + File.separator
			// + filename)
			// )));
			long endTimeForCSVRead = System.currentTimeMillis();
			LOG.debug("Time taken to read csv: " + (endTimeForCSVRead - startTimeForCSVRead));

			SortedSet<DateTime> availableDates = new TreeSet<DateTime>();
			List<Price> prices = new ArrayList<Price>();
			long startTimeForProductProcessing = 0;
			int i = 0;

			// remove header information.
			line = sc.nextLine();
			String currenthouse = "";
			while (sc.hasNext()) {
				line = sc.nextLine();
				if (line.isEmpty()) {
					continue;
				}
				houseinformation = line.split("\\|");

				if (firsttime) {
					currenthouse = houseinformation[0];
					product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(partyId, currenthouse));
					if (product == null) {
						continue;
					}
					firsttime = false;
					// product = PartnerService.getProduct(sqlSession, partyId, currenthouse); // check for null
					startTimeForProductProcessing = System.currentTimeMillis();
				}

				altid = houseinformation[0];
				periodid = houseinformation[1];
				startdate = houseinformation[2];
				enddate = houseinformation[3];
				length = Double.valueOf(houseinformation[4]);
				lastminute = houseinformation[5];
				rentprice = houseinformation[6];
				bookingcosts = houseinformation[7];
				discount = houseinformation[8];
				othercosts = houseinformation[9];
				totalprice = houseinformation[10];
				discountcode = houseinformation[11];
				recordhash = houseinformation[12];

				// price table
				Price price = new Price();
				price.setAltid(periodid);
				price.setPartyid(getAltpartyid());
				price.setEntitytype(NameId.Type.Product.name());
				price.setEntityid(product.getId());
				price.setCurrency(Currency.Code.EUR.name());
				price.setQuantity(length);
				price.setUnit(Unit.DAY);
				price.setName(Price.RACK_RATE);
				price.setType(NameId.Type.Reservation.name());
				price.setDate(DF.parse(startdate));
				price.setTodate(DF.parse(enddate));

				net.cbtltd.shared.Price exists = sqlSession.getMapper(PriceMapper.class).existsAltID(price);

				price.setFactor(1.0);
				price.setOrganizationid(getAltpartyid());
				price.setRule(getDayOfWeek(DF.parse(startdate)));
				price.setAvailable(1);
				price.setState(Price.CREATED);
				double unitprice = Double.valueOf(totalprice) / length; // what is this ?
				price.setValue(NameId.round(Double.valueOf(totalprice) / length));
				price.setMinimum(Double.valueOf(totalprice));
				price.setMinStay(length.intValue());
				price.setVersion(version);

				price.setEntityid(product.getId());
				if (exists != null) {
					//add price into array and update it after
					// sqlSession.getMapper(PriceMapper.class).create(price);
					
					// check if there is reservation for this price.
					// if reservation is created and we still get the price,
					// we need to cancel the reservation
					Reservation action = new Reservation();
					action.setProductid(product.getId());
					action.setFromdate(price.getDate());
					action.setTodate(price.getTodate());
					action = sqlSession.getMapper(ReservationMapper.class).collides(action);
					if (action != null && StringUtils.isNotBlank(action.getAltid())) {
						// cancel this reservation
						PartnerService.cancelReservation(sqlSession, action);
					}
					price.setId(exists.getId());
					// sqlSession.getMapper(PriceMapper.class).update(price);
				}
				prices.add(price);

				// when we come across a new house will create reservations for
				// the blocked dates for the previous house.
				// create a map of available dates
				// iterate through prices and check off the dates from the
				// collection of month dates and use the rest of the dates
				// to create the reservation
				if (currenthouse.compareTo(altid) != 0 || !sc.hasNext()) {
					if (!sc.hasNext()) {
						addToAvailableDates(availableDates, startdate, enddate);
					}

					createSchedule(availableDates, product, version, sqlSession);
					// insert list of prices
					sqlSession.getMapper(PriceMapper.class).insertList(prices);
					// clear list
					prices.clear();
					sqlSession.commit();
					
					LOG.debug("Time taken for Product Process " + currenthouse + " : "
							+ (System.currentTimeMillis() - startTimeForProductProcessing));

					// now cancel price for existing property that we did not process in this run.
					sqlSession.getMapper(PriceMapper.class).cancelversion(price);

					// reset fields
					// create a new set for the next home
					currenthouse = altid;
					availableDates = new TreeSet<DateTime>();
					// product = PartnerService.getProduct(sqlSession, partyId, currenthouse);
					product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(partyId, currenthouse));
					startTimeForProductProcessing = System.currentTimeMillis();
				}

				if (sc.hasNext()) {
					addToAvailableDates(availableDates, startdate, enddate);
				}

/*				i++;
				if (i > 0 && i % IsPartner.PRICE_BATCH_SIZE == 0) {
					LOG.debug("Smart flush for price: " + i);
					sqlSession.commit();
				}*/
			}
			sqlSession.commit();

			// Cancel old reservations.
			resetStartTime();
			cancelOldReservations(sqlSession, version);
			LOG.debug("Canceled old reservations: " + (System.currentTimeMillis() - getStartTime()));
			// sqlSession.commit(); // why duplicate?

		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} finally {
			if (sc != null) {
				try {
					sc.close();
					delete(filename);
					sqlSession.close();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}
		long endTime = System.currentTimeMillis();
		LOG.debug("NextPax readSchedule() for key: " + this.getApikey() + " done." + " Time: " + (endTime - startTime));
		MonitorService.monitor(message, version);
	}

	// Monday = 1 - Sunday = 7.
	public String getDayOfWeek(Date startdate) {
		String checkinday = Rule.AnyCheckIn.name();
		switch (new DateTime(startdate).getDayOfWeek()) {
		case 1:
			checkinday = Rule.MonCheckIn.name();
			break;
		case 2:
			checkinday = Rule.TueCheckIn.name();
			break;
		case 3:
			checkinday = Rule.WedCheckIn.name();
			break;
		case 4:
			checkinday = Rule.ThuCheckIn.name();
			break;
		case 5:
			checkinday = Rule.FriCheckIn.name();
			break;
		case 6:
			checkinday = Rule.SatCheckIn.name();
			break;
		case 7:
			checkinday = Rule.SunCheckIn.name();
			break;
		default:
			LOG.error("error in parsing DayOfWeek: " + startdate);
		}

		return checkinday;
	}

	private void addToAvailableDates(SortedSet<DateTime> availableDates, String startdate, String enddate) throws ParseException {
		int daysBetween = Days.daysBetween(new DateTime(DF.parse(startdate)), new DateTime(DF.parse(enddate))).getDays();
		DateTime startDate = new DateTime(DF.parse(startdate));
		for (int i = 0; i <= daysBetween; i++) {
			availableDates.add(startDate.withFieldAdded(DurationFieldType.days(), i));
		}
	}

	private void createSchedule(SortedSet<DateTime> availableDates, Product product, Date version, SqlSession sqlSession) {
		DateTime currentDate = new DateTime(version).withTime(0, 0, 0, 0);

		// create reservation if current date is less than the first date in the available dates set
		DateTime firstAvailableDate = availableDates.first();
		int daysBetween = Days.daysBetween(currentDate, availableDates.first()).getDays();
		if (daysBetween > 1) {
			PartnerService.createSchedule(
					sqlSession,
					product,
					currentDate.toDate(),
					firstAvailableDate.withFieldAdded(DurationFieldType.days(),-1).toDate(),
					version);
		}

		DateTime fromDate = firstAvailableDate;

		boolean first = true;
		for (DateTime toDate : availableDates) {
			if (first) {
				first = false;
				continue;
			}
			daysBetween = Days.daysBetween(fromDate, toDate).getDays();
			if (daysBetween > 1 && toDate.isAfterNow()) {
				PartnerService.createSchedule(sqlSession,
						product,
						fromDate.withFieldAdded(DurationFieldType.days(), 1).toDate(),
						toDate.withFieldAdded(DurationFieldType.days(), -1).toDate(),
						version);
			}
			fromDate = toDate;
		}
	}

	/**
	 * Read special accommodation prices.
	 */
	@Override
	public void readSpecials() {
		throw new ServiceException(Error.service_absent, "NextPax readSpecials()");
	}

	private static HashMap<String, String> BRAND_ABBREVIATON = null;

	private static final String getBrandAbbreviation(String brandname) {
		if (BRAND_ABBREVIATON == null) {
			BRAND_ABBREVIATON = new HashMap<String, String>();
			BRAND_ABBREVIATON.put("BUNGALOWNET", "bn");
			BRAND_ABBREVIATON.put("CENTERPARCS", "cp");
			BRAND_ABBREVIATON.put("CLICKANDRENT", "bs");
			BRAND_ABBREVIATON.put("CUENDET", "cu");
			BRAND_ABBREVIATON.put("DANCENTER", "dc");
			BRAND_ABBREVIATON.put("DANSOMMER", "ds");
			BRAND_ABBREVIATON.put("BELVILLA", "er");
			BRAND_ABBREVIATON.put("FERATEL", "fe");
			BRAND_ABBREVIATON.put("GRANALACANT", "ga");
			BRAND_ABBREVIATON.put("HAPPYHOME", "ha");
			BRAND_ABBREVIATON.put("HOGENBOOM", "hb");
			BRAND_ABBREVIATON.put("HOLIDAYHOME", "hh");
			BRAND_ABBREVIATON.put("INTERCHALET", "ic");
			BRAND_ABBREVIATON.put("INTERHOME", "ih");
			BRAND_ABBREVIATON.put("JUSTFRANCE", "jf");
			BRAND_ABBREVIATON.put("JULESVILLAS", "jv");
			BRAND_ABBREVIATON.put("NOVASOL", "ns");
			BRAND_ABBREVIATON.put("ROOMPOT", "rp");
			BRAND_ABBREVIATON.put("SOLOGSTRAND", "sg");
			BRAND_ABBREVIATON.put("SUNPARCS", "sp");
			BRAND_ABBREVIATON.put("TOPICTRAVEL", "tt");
			BRAND_ABBREVIATON.put("TUI", "tu");
			BRAND_ABBREVIATON.put("UPHILLTRAVEL", "ut");
			BRAND_ABBREVIATON.put("VACASOL", "vc");
		}
		return BRAND_ABBREVIATON.get(brandname.toUpperCase());
	}

	private static HashMap<String, String> HOUSE_PROPERTY = null;
	// see appendix D for more information.
	private static final String getHouseProperty(String houseproperty) {
		if (HOUSE_PROPERTY == null) {
			HOUSE_PROPERTY = new HashMap<String, String>();
			HOUSE_PROPERTY.put("B01", "Number of bathrooms");
			HOUSE_PROPERTY.put("B02", "Number of bedrooms");
			HOUSE_PROPERTY.put("A01", "Air conditioning");
		}
		return HOUSE_PROPERTY.get(houseproperty);
	}

	private static HashMap<String, String> IMAGE_TYPES = null;

	/**
	 * Gets the image.
	 * 
	 * @param value the image code.
	 * @return the image description.
	 */
	// TODO: put for other lanauges beside english.
	private static String getImageTitle(String value) {
		if (IMAGE_TYPES == null) {
			IMAGE_TYPES = new HashMap<String, String>();
			IMAGE_TYPES.put("a", "Exterior house");
			IMAGE_TYPES.put("c", "Living room");
			IMAGE_TYPES.put("d", "Living/dining room");
			IMAGE_TYPES.put("e", "Dining room");
			IMAGE_TYPES.put("f", "Map");
			IMAGE_TYPES.put("g", "Fireplace room");
			IMAGE_TYPES.put("h", "House");
			IMAGE_TYPES.put("i", "Kitchen diner");
			IMAGE_TYPES.put("j", "Kitchen");
			IMAGE_TYPES.put("k", "Bedroom");
			IMAGE_TYPES.put("l", "Bathroom");
			IMAGE_TYPES.put("m", "Model");
			IMAGE_TYPES.put("n", "Sauna");
			IMAGE_TYPES.put("o", "Whirlpool");
			IMAGE_TYPES.put("p", "Indoor swimming pool");
			IMAGE_TYPES.put("q", "Terrace");
			IMAGE_TYPES.put("r", "Outside");
			IMAGE_TYPES.put("s", "Summer");
			IMAGE_TYPES.put("t", "Fishing");
			IMAGE_TYPES.put("u", "Other");
			IMAGE_TYPES.put("w", "Winter");
		}
		if (value == null || IMAGE_TYPES.get(value.toLowerCase()) == null) {
			return null;
		} else {
			return IMAGE_TYPES.get(value.toLowerCase());
		}
	}

	private String getXMLTestAttrubuiteStatus() {
		return RazorConfig.isProduction() ? "nee" : "ja";
	}

	public String formatCreditCardType(CreditCardType creditCardType) {
		if (creditCardType.name().equals(CreditCardType.VISA.name()))
			return CreditCardType.VISA.name().toLowerCase();
		return "mast";
	}

	public boolean aceptedCreditCardTypes(CreditCardType creditCardType) {// maybe allow for amercain express?
		return creditCardType.name().equals(CreditCardType.MASTER_CARD.name())
				|| creditCardType.name().equals(CreditCardType.VISA.name());
	}

	// information :
	// https://docs.google.com/spreadsheet/ccc?key=0ArV0twP1-FUedHRJWDg0UXRTUVdLSHpFdGNFLXFLV3c&usp=drive_web#gid=0
	private boolean emailPayment(int nextpaxpartnerid) {
		return nextpaxpartnerid == 11 || nextpaxpartnerid == 12
				|| nextpaxpartnerid == 18 || nextpaxpartnerid == 21
				|| nextpaxpartnerid == 24 || nextpaxpartnerid == 27
				|| nextpaxpartnerid == 28 || nextpaxpartnerid == 71
				|| nextpaxpartnerid == 29 || nextpaxpartnerid == 26;
	}

	private boolean bankTransferOnly(int nextpaxpartnerid) // Confirming with Robert to add 21.
	{
		return (nextpaxpartnerid == 11 || nextpaxpartnerid == 18
				|| nextpaxpartnerid == 21 || nextpaxpartnerid == 24
				|| nextpaxpartnerid == 27 || nextpaxpartnerid == 28
				|| nextpaxpartnerid == 71 || nextpaxpartnerid == 29 || nextpaxpartnerid == 26);
	}

	private boolean creditCardOnly(int nextpaxpartnerid) {
		return nextpaxpartnerid == 20 || nextpaxpartnerid == 17;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		LOG.error("Using Configuration: " + RazorConfig.getEnvironmentId());
		long timestamp = new Date().getTime();
		String message = "createReservationAndPayment " + reservation.getId();
		LOG.debug(message);
		String rq;
		String rs;

		Map<String, String> result = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		String senderSessionID = timestamp + "BookingpalS";
		String receiverSessionID = timestamp + "BookingpalR";
		String creditCardInormation;
		String fakeBankAccountInformation;
		try {
			if (reservation.notActive()) {
				throw new ServiceException(Error.reservation_state, reservation.getId() + " state " + reservation.getState());
			}

			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {
				throw new ServiceException(Error.product_id, reservation.getProductid());
			}
			if (reservation.noAgentid()) {
				throw new ServiceException(Error.reservation_agentid);
			}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			if (agent == null) {
				throw new ServiceException(Error.party_id, reservation.getAgentid());
			}
			if (reservation.noCustomerid()) {
				reservation.setCustomerid(Party.NO_ACTOR);
			}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid());}

			final  long startTime = System.currentTimeMillis();
			Price price = sqlSession.getMapper(PriceMapper.class).altidfromdate(reservation);
			final long endTime = System.currentTimeMillis();

			System.out.println("Total execution time: " + (endTime - startTime)/ 1000.00 );

			if( price == null || (!price.hasAltid())) {throw new ServiceException(Error.price_data, reservation.getCustomerid());}
			//if(bankTransferOnly(nextpaxpartnerid)) {throw new ServiceException(Error.bank_transfer,product.getAltpartyid());}
			if(!aceptedCreditCardTypes(creditCard.getType())){throw new ServiceException(Error.card_type,creditCard.getType().name());}

			int nextpaxpartnerid = Integer.parseInt(product.getAltSupplierId());
			// if(bankTransferOnly(nextpaxpartnerid)) {throw new ServiceException(Error.bank_transfer,product.getAltpartyid());}
			if (!aceptedCreditCardTypes(creditCard.getType())) {
				throw new ServiceException(Error.card_type, creditCard.getType().name());
			}

			// sequence SellRequest.xml -> AssignRequest.xml -> BookRequest.xml
			// SellRequest.xml
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + senderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + receiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");// Default \91NPS001\92
			sb.append("    <RequestID>SellRequest</RequestID>");
			sb.append("    <ResponseID>SellResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <SellRequest>");
			sb.append("      <PackageDetails>");
			sb.append("        <PackageID>" + price.getAltid() + "</PackageID>");
			sb.append("        <NumberOfAdults>" + reservation.getAdult() + "</NumberOfAdults>");
			sb.append("        <NumberOfChildren>" + reservation.getChild() + "</NumberOfChildren>");
			sb.append("        <NumberOfBabies>0</NumberOfBabies>");
			sb.append("        <DepartureDate>" + DF.format(reservation.getFromdate()) + "</DepartureDate>");
			sb.append("        <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("      </PackageDetails>");
			sb.append("    </SellRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");
			rq = sb.toString();

			// reservation.
			// sequence SellRequest.xml -> AssignRequest -> BookRequest
			if (LOG.isDebugEnabled()) {
				LOG.debug("\nSellRequest rq: \n" + rq + "\n");
			}
			rs = getConnection(rq);
			LOG.debug("\nSellRequest rs: \n" + rs + "\n");

			// AssignResuest.xml
			sb = new StringBuilder();
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + senderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + receiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");
			sb.append("    <RequestID>AssignRequest</RequestID>");
			sb.append("    <ResponseID>AssignResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <AssignRequest>");
			sb.append("      <PaxDetails>");
			sb.append("        <PaxDescription>");
			sb.append("          <PaxID>P01</PaxID>");
			sb.append("          <Initials>" + customer.getFirstName().substring(0, 1) + "</Initials>");
			sb.append("          <Infix>''</Infix>");
			sb.append("          <Name>" + customer.getName() + "</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("        </PaxDescription>");
			sb.append("        <HomeStay>");
			sb.append("          <Name>" + customer.getName() + "</Name>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>");
			sb.append("        </HomeStay>");
			sb.append("      </PaxDetails>");
			sb.append("      <PaxAssignment>");
			sb.append("        <AccoAssignment>");
			sb.append("          <AccommodationID>" + "A" + product.getAltid() + "</AccommodationID>");
			// <AccommodationID> 'A' + HouseID, e.g. 'A1712' if the XML contained <HouseID>1712</HouseID>.
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("          <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("          <UnitAssignment>");
			sb.append("            <UnitID>U01</UnitID>");
			sb.append("            <Quantity>1</Quantity>");
			sb.append("            <RoomAssignment>");
			sb.append("              <Sequence>1</Sequence>");
			sb.append("              <PaxID>P01</PaxID>");
			sb.append("            </RoomAssignment>");
			sb.append("          </UnitAssignment>");
			sb.append("        </AccoAssignment>");
			sb.append("      </PaxAssignment>");
			sb.append("    </AssignRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			LOG.debug("\nAssignRequest rq: \n" + rq + "\n");
			rs = getConnection(rq);
			LOG.debug("\nAssignRequest rs: \n" + rs + "\n");

			creditCardInormation = "          <BankAccount>creditcard;"
					+ formatCreditCardType(creditCard.getType()) + ";"
					+ creditCard.getNumber() + ";"
					+ creditCard.getYear() + "-"
					+ creditCard.getMonth() + ";"
					+ creditCard.getFirstName() + ";"
					+ creditCard.getLastName() + ";"
					+ creditCard.getSecurityCode() + "</BankAccount>";
			fakeBankAccountInformation = "          <BankAccount>0000</BankAccount>";

			// /BookRequest.xml
			sb = new StringBuilder();
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append("  <Control Language='NL' Test='nee'>");// + getXMLTestAttrubuiteStatus() + "'>");
			sb.append("    <SenderSessionID>" + senderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + receiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("    <MessageSequence>1</MessageSequence>");
			sb.append("    <SenderID>" + SENDERID + "</SenderID>");
			sb.append("    <ReceiverID>NPS001</ReceiverID>");
			sb.append("    <RequestID>BookRequest</RequestID>");
			sb.append("    <ResponseID>BookResponse</ResponseID>");
			sb.append("  </Control>");
			sb.append("  <TRequest>");
			sb.append("    <BookRequest>");
			sb.append("      <PaxDetails>");
			sb.append("        <PaxDescription>");
			sb.append("          <PaxID>P01</PaxID>");
			sb.append("          <Initials>" + customer.getFirstName().substring(0, 1) + "</Initials>");
			sb.append("          <Infix>" + "de" + "</Infix>");
			sb.append("          <Name>" + customer.getFamilyName() + "</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("        </PaxDescription>");
			sb.append("        <Contact>");
			sb.append("          <Initials>" + customer.getFirstName().substring(0, 1) + "</Initials>");
			sb.append("          <Name>" + customer.getFamilyName() + "</Name>");
			sb.append("          <Gender>m</Gender>");
			sb.append("          <Address>" + customer.getLocalAddress() + "</Address>");
			sb.append("          <Zipcode>"+customer.getPostalcode()+"</Zipcode>"); ///makse sure their is a value
			sb.append("          <Place>"+customer.getLocalAddress()+"</Place>");
			sb.append("          <Country>" + customer.getCountry() + "</Country>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>");
			sb.append("          <TelWork>" + customer.getDayphone() + "</TelWork>");
			sb.append("          <TelMobile>" + customer.getDayphone() + "</TelMobile>");
			sb.append("          <Email>" + customer.getEmailaddress() + "</Email>");
			sb.append(bankTransferOnly(nextpaxpartnerid) ? fakeBankAccountInformation : creditCardInormation);
			sb.append("        </Contact>");
			sb.append("        <HomeStay>");
			sb.append("          <Name>" + customer.getName() + "</Name>");
			sb.append("          <TelHome>" + customer.getDayphone() + "</TelHome>");
			sb.append("        </HomeStay>");
			sb.append("      </PaxDetails>");
			sb.append("      <PaxAssignment>");
			sb.append("        <AccoAssignment>");
			sb.append("          <AccommodationID>" + "A" + product.getAltid() + "</AccommodationID>");
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("          <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("          <UnitAssignment>");
			sb.append("            <UnitID>U01</UnitID>");
			sb.append("            <Quantity>1</Quantity>");
			sb.append("            <RoomAssignment>");
			sb.append("              <Sequence>1</Sequence>");
			sb.append("              <PaxID>P01</PaxID>");
			sb.append("            </RoomAssignment>");
			sb.append("          </UnitAssignment>");
			sb.append("        </AccoAssignment>");
			sb.append("      </PaxAssignment>");
			sb.append("    </BookRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			LOG.debug("\nBookRequest rq: \n" + rq + "\n");
			createBooking(rq, reservation, timestamp, result);
		} catch (Throwable x) {
			result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			LOG.error(x.getMessage());
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		MonitorService.monitor(message, new Date(timestamp));

		return result;
	}

	private void createBooking(String rq, Reservation reservation, long timestamp, Map<String, String> result) throws Throwable {
		String rs = getConnection(rq);
		LOG.debug("\nBookResponse rs: \n" + rs + "\n");
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		InputSource inputSource = new InputSource(new StringReader(rs.toString()));
		String bookID = (String) xpath.evaluate("//BookID", inputSource, XPathConstants.STRING);
		if (bookID.isEmpty()) {
			throw new ServiceException(Error.reservation_api, reservation.getId());
		} else {
			reservation.setAltid(bookID);
			reservation.setAltpartyid(getAltpartyid());
			reservation.setMessage(reservation.getMessage());
			reservation.setVersion(new Date(timestamp));
			reservation.setState(Reservation.State.Confirmed.name());
			result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
		}
	}
	
	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		ReservationPrice reservationPrice = new ReservationPrice();
		//don't need to include Total Quote into quote details
		QuoteDetail qd = new QuoteDetail();
		List<QuoteDetail> quoteDetails = new LinkedList<QuoteDetail>();

		reservation.setCurrency(currency);
		readPrice(sqlSession, reservation, productAltId);
		  	  
	  	if(reservation.getPrice() <= 0) {
	  		throw new ServiceException(Error.product_not_available/*, reservation.getProductFromToDate()*/);
	  	}
	  	
		qd.setDescription("Total quote");
		qd.setAmount(reservation.getPrice() + "");
		qd.setIncluded(true);
		qd.setCurrency(currency);
		reservation.setQuote(reservation.getPrice());
		quoteDetails.add(qd);
		reservationPrice.setQuoteDetails(quoteDetails);
		reservationPrice.setCurrency(currency);
		reservationPrice.setTotal(reservation.getPrice()); // TODO : set quotes values instead of price
		reservationPrice.setPrice(reservation.getPrice());
		// LOG.debug("Isaac : Quote()"+reservation.getQuote());

		return reservationPrice;
	}

	private static Reservation readPrice(SqlSession sqlSession, Reservation reservation, String productAltId) {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		long time = now.getTime();
		String SenderSessionID = time + "BookingpalS";
		String ReceiverSessionID = time + "BookingpalR";
		String rq;
		String rs = null;

		try {
			sb.append("<?xml version='1.0' ?>");
			sb.append("<TravelMessage VersionID='1.8N'>");
			sb.append(" <Control Language='NL' Test='nee'>");
			sb.append("    <SenderSessionID>" + SenderSessionID + "</SenderSessionID>");
			sb.append("    <ReceiverSessionID>" + ReceiverSessionID + "</ReceiverSessionID>");
			sb.append("    <Date>" + DF.format(new Date()) + "</Date>");
			sb.append("    <Time Reliability='zeker'>" + TF.format(new Date()) + "</Time>");
			sb.append("   <MessageSequence>1</MessageSequence>");
			sb.append("   <SenderID>" + SENDERID + "</SenderID>");
			sb.append("  <ReceiverID>NPS001</ReceiverID>");
			sb.append("   <RequestID>AvailabilityBookingPalRequest</RequestID>");
			sb.append("   <ResponseID>AvailabilityBookingPalResponse</ResponseID>");
			sb.append(" </Control>");
			sb.append("  <TRequest>");
			sb.append("    <AvailabilityBookingPalRequest>");
			sb.append("      <PackageDetails WaitListCheck='ja'>");
			sb.append("          <AccommodationID>" + "A" + productAltId + "</AccommodationID>");
			sb.append("          <ArrivalDate>" + DF.format(reservation.getFromdate()) + "</ArrivalDate>");
			sb.append("        <Duration DurationType='dagen'>" + reservation.getDuration(Time.DAY).intValue() + "</Duration>");
			sb.append("     </PackageDetails>");
			sb.append("   </AvailabilityBookingPalRequest>");
			sb.append("  </TRequest>");
			sb.append("</TravelMessage>");

			rq = sb.toString();
			rs = getConnection(rq); // fix code have better support. gettting errors when deploying with tomcat.

			LOG.debug("computePrice rq: \n" + rq + "\n");
			LOG.debug("computePrice rs: \n" + rs + "\n");

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			InputSource inputSource = new InputSource(new StringReader(rs.toString()));
			Double price =  (Double) xpath.evaluate("//Price", inputSource,XPathConstants.NUMBER);
			if(Double.isNaN(price)){throw new ServiceException(Error.product_not_available);}
			
			price = price / 100.00; //price is a whole number

			factory = XPathFactory.newInstance();
			xpath = factory.newXPath();
			inputSource = new InputSource(new StringReader(rs.toString()));
			String fromCurrency = (String) xpath.evaluate("//Price/@Currency", inputSource, XPathConstants.STRING);
			if ("".equals(fromCurrency)) {
				throw new ServiceException(Error.price_missing, reservation.getId() + " State = "
								+ reservation.getState() + " currency = " + reservation.getCurrency());
			}
			String tocurrency = reservation.getCurrency();
			if (!fromCurrency.equalsIgnoreCase(tocurrency)) {
				// Double rate = OpenExchangeRates.getExchangeRate(currency,rescurrency);
				Double rate = WebService.getRate(sqlSession, fromCurrency, tocurrency, new Date());
				price = rate * price;
			}

			LOG.debug("price = " + price);
			reservation.setPrice(price);
			reservation.setQuote(price);
		} catch (Throwable e) {
			LOG.error(e.getMessage());
			reservation.setPrice(0.00);
		}
		return reservation;
	}
	
	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Nextpax inquireReservation()");
	}
}