/**
 * @author	Zoran Kocevski
 * @version	1.1
 */

package net.cbtltd.rest.aaxsys;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.rest.GatewayHandler;
//import org.joda.time.DateTime;
//import org.joda.time.LocalDate;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;


public class A_Handler extends PartnerHandler implements IsPartner {
	
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final String SERVICE_URL = "http://www.aaxsys.com/cgi-bin/feed?Name=BookingPal2";
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat API_FORMAT = new SimpleDateFormat("MM/dd/yy");
	private static final String AAXSYS_RESERVATION_URL = "http://www.globalaaxsys.com/cgi-bin/makeres?BookingKey=DHJDBIHDHBECDBEEEEBHFIABEADCFEFEEDGHIEDE";
	private static final int FEES_PRICES_UPDATE_DAYS= 365;

	public A_Handler(Partner partner) {super(partner);}
	
	/**
	 * Gets the connection to the AAXSYS server and executes the specified request.
	 *
	 * @param url the connection URL.
	 * @param rq the request object.
	 * @return the XML string returned by the message.
	 * @throws Throwable the exception thrown by the operation.
	 */
	private static final String getConnection(String rq) throws Throwable {
		String xmlString = "";
		HttpURLConnection  connection = null;
		try {
			URL url = new URL(AAXSYS_RESERVATION_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/xml");

			
			if (rq != null) {
				connection.setRequestProperty("Accept", "application/xml");
				connection.connect();
				byte[] outputBytes = rq.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputBytes);
			}
			
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:" + connection.getResponseCode() + " URL " + url);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {xmlString += line;}
			
			System.out.println(xmlString);
		}
		catch (Throwable x) {throw new RuntimeException(x.getMessage());}
		finally {
			if(connection != null) {connection.disconnect();}
		}
		return xmlString;
	}

	@SuppressWarnings("unchecked")
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		/*TODO WE SHOULD SAVE CALENDAR LINK FOR SPECIFIC PROPERTY IN THE TABLE PRODUCT
		IN THAT CASE, THIS OPERATION WILL BE FASTER. CHECK IT!*/
		boolean isAvailableResult = false;
		Date timestamp = new Date();
		String message = "isAvailable AaxSys STARTED";
		
		LOG.debug(message);
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			String productaltid =  product.getAltid();
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(SERVICE_URL);
			Element root = document.getRootElement();
			
			List<Element> units  = root.getChildren("UnitCode");
			ArrayList<String> names = new ArrayList<String>();
			for (Element unit : units) {
				names.add(unit.getText());
			}
			
			List<Element> properties  = root.getChildren("property");

			for(int i = 0; i < properties.size(); i++){
				String altid = names.get(i).toString();
				if(altid.compareTo(productaltid)==0){
					isAvailableResult = true;
					String calendarlink = properties.get(i).getChildText("CalendarLink");
					calendarlink = calendarlink.replaceFirst("S=AMSI-SDS&amp;", "");
					calendarlink = calendarlink.replaceFirst("S=AMSI-SF&amp;", "");
					Document board = (Document) builder.build(calendarlink);
					Element reservationboard = board.getRootElement(); 
					List<Element> reservedperiods  = reservationboard.getChildren("Reservation");
					for(Element period : reservedperiods){
						Date periodfrom = DF.parse(DF.format(API_FORMAT.parse(period.getChildText("BeginDate")))); 
						Date periodto = DF.parse(DF.format(API_FORMAT.parse(period.getChildText("EndDate"))));
						if (reservation.getTodate().compareTo(periodfrom)<0) isAvailableResult = true;
						else if (reservation.getTodate().compareTo(periodto)>0){
							if(reservation.getFromdate().compareTo(periodto)>0) isAvailableResult = true;
							else {return isAvailableResult = false; }
						}
						else{
							return isAvailableResult = false;						
						}
					}
				}else{
					continue;
				}			
			}
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
		MonitorService.monitor(message, timestamp);
		
		return isAvailableResult;
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Aaxsys createReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
		
		//This PMS use the method createReservationAndPayment
	}
	
	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		
		Date timestamp = new Date();
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		StringBuilder sb = new StringBuilder();
		String rq;
		String rs = null;
		
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
			
			
			String customerAddress = PartyService.getSimpleAddress(customer);
			
			sb.append("<request>");
			sb.append("<RESACTION>NEW</RESACTION>");
			sb.append("<RESTYPE>1</RESTYPE>");
			sb.append("<EXTBOOKINGID>BPAL-"+ reservation.getId()+"</EXTBOOKINGID>");
			sb.append("<ADDRESS>"+ customerAddress +"</ADDRESS>");
			sb.append("<CVVSNO></CVVSNO>");
			sb.append("<CCEXPMONTH>"+ creditCard.getMonth() +"</CCEXPMONTH>");
			sb.append("<CCEXPYEAR>"+ creditCard.getYear() +"</CCEXPYEAR>");
			sb.append("<CCHOLDER>"+ creditCard.getFirstName()+ " "+ creditCard.getLastName() +"</CCHOLDER>");
			sb.append("<CREDITCARD>" + creditCard.getNumber()+ "</CREDITCARD>");
			sb.append("<CCTYPE>"+ creditCard.getType() +"</CCTYPE>");
			sb.append("<CITY>"+ customer.getCity() +"</CITY>");
			sb.append("<COMPANY></COMPANY>");
			sb.append("<COUNTRY>" + customer.getCountry() + "</COUNTRY>");
			sb.append("<EMAIL>"+ customer.getEmailaddress() +"</EMAIL>");
			sb.append("<EMAIL2></EMAIL2>");
			sb.append("<FIRSTNAME>"+ customer.getFirstName() +"</FIRSTNAME>");
			sb.append("<LASTNAME>"+ customer.getFamilyName() +"</LASTNAME>");
			sb.append("<PHONE>"+ customer.getMobilephone() +"</PHONE>");
			sb.append("<ZIP></ZIP>");
			sb.append("<PROPERTYCODE>" + reservation.getProduct().getAltid() + "</PROPERTYCODE>");
			sb.append("<VENDOR>" + reservation.getProduct().getAltSupplierId() + "</VENDOR>");
			sb.append("<BEGINDATE>" + DF.format(reservation.getFromdate()) + "</BEGINDATE>");
			sb.append("<CURRENCY>USD</CURRENCY>");
			sb.append("<ENDDATE>" + DF.format(reservation.getTodate()) + "</ENDDATE>");
			sb.append("<RENT></RENT>");
			sb.append("</request>");
	
			rq = sb.toString();
			LOG.debug("XML: " + rq + "\n");

			rs = getConnection(rq);
			LOG.debug("Response: " + rs + "\n");
			
			String bookID;
			
			if(rs.indexOf("Confirmation") > 0) {
				bookID = rs.substring(rs.indexOf("Reservation number"));
				
				// detect 1st digit after start of string
				int beginIndex=0;
				int endIndex=1;
				int length=bookID.length();
				while(!bookID.substring(beginIndex,endIndex).matches("\\d")) {
					beginIndex++;
				    endIndex++;
				    if(endIndex>length) {
				     throw new Exception("No digit found.");
				    }
				}
				
				int i_startNumber= beginIndex;
				
				// detect 1st non digit after start previously detected 1st digit   
				beginIndex=i_startNumber;
				endIndex=i_startNumber+1;
				while(bookID.substring(beginIndex,endIndex).matches("\\d")) {
				   beginIndex++;
				   endIndex++;
				   if(endIndex>length) {
				    break;
				   }
				 }
				 
				int i_endNumber= beginIndex;
				
				bookID = bookID.substring(i_startNumber, i_endNumber);
				LOG.debug("ReservationAndPayment Confirmation ID:"+bookID);
				reservation.setAltid(bookID);
				reservation.setAltpartyid(getAltpartyid());
				reservation.setVersion(timestamp);
				reservation.setState(Reservation.State.Confirmed.name());
				reservation.setMessage(null);
				result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
			}else{
				reservation.setState(Reservation.State.Cancelled.name());
				result.put(GatewayHandler.STATE, GatewayHandler.FAILED);
				result.put(GatewayHandler.ERROR_MSG, "There was a problem during the process of creating reservation!");
				return result;
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
			x.printStackTrace();
		}
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		MonitorService.monitor(message, timestamp);
		
		return result;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		String message = "confirmReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Aaxsys confirmReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		} 
		
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		String message = "readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Aaxsys readReservation()");
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		} 
		
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation " + reservation.getAltid();
		LOG.debug(message);
		StringBuilder sb = new StringBuilder();
		String rq;
		String rs = null;
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
			
			String customerAddress = PartyService.getSimpleAddress(customer);
			
			sb.append("<request>");
			sb.append("<RESACTION>UPDATE</RESACTION>");
			sb.append("<RESTYPE>1</RESTYPE>");
			sb.append("<EXTBOOKINGID>BPAL-"+ reservation.getId() +"</EXTBOOKINGID>");
			sb.append("<ADDRESS>"+ customerAddress +"</ADDRESS>");
			sb.append("<CVVSNO></CVVSNO>");
			sb.append("<CCEXPMONTH></CCEXPMONTH>");
			sb.append("<CCEXPYEAR></CCEXPYEAR>");
			sb.append("<CCHOLDER></CCHOLDER>");
			sb.append("<CREDITCARD></CREDITCARD>");
			sb.append("<CCTYPE></CCTYPE>");
			sb.append("<CITY>"+ customer.getCity() +"</CITY>");
			sb.append("<COMPANY></COMPANY>");
			sb.append("<COUNTRY>"+ customer.getCountry() +"</COUNTRY>");
			sb.append("<EMAIL>"+ customer.getEmailaddress() +"</EMAIL>");
			sb.append("<EMAIL2></EMAIL2>");
			sb.append("<FIRSTNAME>"+ customer.getFirstName() +"</FIRSTNAME>");
			sb.append("<LASTNAME>"+ customer.getFamilyName() +"</LASTNAME>");
			sb.append("<PHONE>"+ customer.getMobilephone() +"</PHONE>");
			sb.append("<ZIP></ZIP>");
			sb.append("<PROPERTYCODE>" + reservation.getProduct().getAltid() + "</PROPERTYCODE>");
			sb.append("<VENDOR>" + reservation.getProduct().getAltSupplierId() + "</VENDOR>");
			sb.append("<BEGINDATE>" + DF.format(reservation.getFromdate()) + "</BEGINDATE>");
			sb.append("<CURRENCY>USD</CURRENCY>");
			sb.append("<ENDDATE>" + DF.format(reservation.getTodate()) + "</ENDDATE>");
			sb.append("<RENT></RENT>");
			sb.append("</request>");
			
			rq = sb.toString();
			LOG.debug("XML: " + rq + "\n");

			rs = getConnection(rq);
			LOG.debug("Response: " + rs + "\n");
			
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			InputSource inputSource = new InputSource(new StringReader(rs.toString()));
			String confirmmessage = (String) xpath.evaluate("Confirmation", inputSource,XPathConstants.STRING);
			if (confirmmessage == null) {throw new ServiceException(Error.reservation_api, reservation.getId());}
			else { //TODO: DO WE NEED TO CHANGE SOME STATUS?
				//reservation.setState(); check it for update
				//System.out.println("Update is DONE!");
				
			}
		}
		catch (Throwable x) {reservation.setMessage(x.getMessage());} 
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		MonitorService.monitor(message, timestamp);
		
	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		StringBuilder sb = new StringBuilder();
		String rq;
		String rs = null;
		try {
			sb.append("<request>");
			sb.append("<RESACTION>CANCEL</RESACTION>");
			sb.append("<RESTYPE>1</RESTYPE>");
			sb.append("<EXTBOOKINGID>BPAL-"+reservation.getId()+"</EXTBOOKINGID>");
			sb.append("<ADDRESS></ADDRESS>");
			sb.append("<CVVSNO></CVVSNO>");
			sb.append("<CCEXPMONTH></CCEXPMONTH>");
			sb.append("<CCEXPYEAR></CCEXPYEAR>");
			sb.append("<CCHOLDER></CCHOLDER>");
			sb.append("<CREDITCARD></CREDITCARD>");
			sb.append("<CCTYPE></CCTYPE>");
			sb.append("<CITY></CITY>");
			sb.append("<COMPANY></COMPANY>");
			sb.append("<COUNTRY></COUNTRY>");
			sb.append("<EMAIL></EMAIL>");
			sb.append("<EMAIL2></EMAIL2>");
			sb.append("<FIRSTNAME></FIRSTNAME>");
			sb.append("<LASTNAME></LASTNAME>");
			sb.append("<PHONE></PHONE>");
			sb.append("<ZIP></ZIP>");
			sb.append("<PROPERTYCODE>" + reservation.getProduct().getAltid() + "</PROPERTYCODE>");
			sb.append("<VENDOR>" + reservation.getProduct().getAltSupplierId() + "</VENDOR>");
			sb.append("<BEGINDATE>" + DF.format(reservation.getFromdate()) + "</BEGINDATE>");
			sb.append("<CURRENCY>USD</CURRENCY>");
			sb.append("<ENDDATE>" + DF.format(reservation.getTodate()) + "</ENDDATE>");
			sb.append("<RENT></RENT>");
			sb.append("</request>");
			
			rq = sb.toString();
			LOG.debug("XML: " + rq + "\n");

			rs = getConnection(rq);
			LOG.debug("Response: " + rs + "\n");
			
			if(rs.indexOf("Error") > 0){
				LOG.error(rs.substring(rs.indexOf("<Message>")+9, rs.indexOf("</Message>")));
			}
			else if(rs.indexOf("Confirmation") > 0) {
				reservation.setState(Reservation.State.Cancelled.name());
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				sqlSession.commit();
				LOG.debug("cancelReservation " + reservation);
			}
		}
		catch (Throwable x) {reservation.setMessage(x.getMessage());} 
		MonitorService.monitor(message, timestamp);
		
	}

	@Override
	public void readAlerts() {
		String message = "readAlerts AaxSys";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Aaxsys readAlerts()");
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
		
	}

	@SuppressWarnings("unchecked")
	public void readPrices() {
		Date version = new Date();
		Date minimumToDateForExtraPricesUpdate = Time.addDuration(version, FEES_PRICES_UPDATE_DAYS, Time.DAY);
		String message = "readPrices() AaxSys STARTED";
		LOG.debug(message + version.toString());

		final SqlSession sqlSession = RazorServer.openSession();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String partyAltID =  party.getAltid();
			
		try {	
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(SERVICE_URL);
			Element root = document.getRootElement();
			
			List<Element> units  = root.getChildren("UnitCode");
			ArrayList<String> names = new ArrayList<String>();
			for (Element unit : units) {
				names.add(unit.getText());
			}

			List<Element> vendors  = root.getChildren("Vendor");
			ArrayList<String> vendornames = new ArrayList<String>();
			for (Element vendor : vendors) {
				vendornames.add(vendor.getText());
			}
			
			List<Element> properties  = root.getChildren("property");

		    for(int i = 0; i < properties.size(); i++){
		    	List<Element> details  = properties.get(i).getChildren("details");
				String altid = names.get(i).toString();
				LOG.debug("readPrices() AaxSys product:"+altid);
				String partyCode = vendornames.get(i).toString();			
				
				if(partyAltID.compareTo(partyCode)!=0)
					continue;	
				
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
				if (product == null) {
					LOG.error("ERROR product not exsit:" + altid);
					continue;
				}
				
				List<Element> fees  = details.get(0).getChildren("Fees");
				Double cleaningfee = Double.parseDouble(fees.get(0).getChildText("CleaningFee"));
				Double taxrate = Double.parseDouble(fees.get(0).getChildText("TaxRate"));

				List<Element> ratedetails = details.get(0).getChildren("Rates");
				List<Element> rates = ratedetails.get(0).getChildren("Rate");
				ArrayList<Price> pricesFromApi = new ArrayList<Price>();
				int counterMinMax = 0;
				Date minRateDate = new Date();
				Date maxRateDate = new Date();
				for (Element rate : rates) {
					String ratefrom = rate.getChildText("StartDate");
					Double ratedailyvalue = Double.valueOf(rate.getChildText("DailyRate"));
					String rateend = rate.getChildText("EndDate");
					
					if(counterMinMax==0){
						minRateDate = DF.parse(DF.format(API_FORMAT.parse(ratefrom)));
						maxRateDate = DF.parse(DF.format(API_FORMAT.parse(rateend)));
					}else{
						if(DF.parse(DF.format(API_FORMAT.parse(ratefrom))).compareTo(minRateDate)<0){
							minRateDate = DF.parse(DF.format(API_FORMAT.parse(ratefrom)));
						}
						if(DF.parse(DF.format(API_FORMAT.parse(rateend))).compareTo(maxRateDate)>0){
							maxRateDate = DF.parse(DF.format(API_FORMAT.parse(rateend)));
						}
					}
					
					counterMinMax++;
					
					int rateminimumstay = 0;
					//TODO CHECK IT WITH ERIC FROM AAXSYS
					
					if (rate.getChildText("MinimumStay") != null && !rate.getChildText("MinimumStay").isEmpty())
						rateminimumstay = Integer.parseInt(rate.getChildText("MinimumStay"));
					else if((rate.getChildText("MinimumStayLength") != null && !rate.getChildText("MinimumStayLength").isEmpty()))
						rateminimumstay = Integer.parseInt(rate.getChildText("MinimumStayLength"));
					else
						rateminimumstay = 1;
					
					this.addPrice(pricesFromApi, product, "USD", Unit.DAY, DF.parse(DF.format(API_FORMAT.parse(ratefrom))), DF.parse(DF.format(API_FORMAT.parse(rateend))), Double.valueOf(ratedailyvalue), rateminimumstay*Double.valueOf(ratedailyvalue), rateminimumstay);
					
				}
				PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Product.name(), pricesFromApi, version, false, null);
				
				
				//fee
				ArrayList<Price> feeMandatoryFromApi = new ArrayList<Price>();
				ArrayList<Price> feeNotMandatoryFromApi = new ArrayList<Price>();
				this.insertFee(feeMandatoryFromApi, feeNotMandatoryFromApi, product.getId(), "Cleaning Fee", cleaningfee, false, true, minRateDate, maxRateDate, product.getCurrency(), version);
				
				//taxes percentage
				ArrayList<Tax> taxListFromApi = new ArrayList<Tax>();
				this.insertTax(taxListFromApi, product.getId(), "Taxes", taxrate, true);
				
				PartnerService.updateProductTaxes(sqlSession, product, taxListFromApi, version);
				PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Mandatory.name(), feeMandatoryFromApi, version, true, minimumToDateForExtraPricesUpdate);
				PartnerService.updateProductPrices(sqlSession, product, NameId.Type.Feature.name(), feeNotMandatoryFromApi, version, true, minimumToDateForExtraPricesUpdate);
				
								
				sqlSession.commit();				
				LOG.debug("readPrices() AaxSys DONE");
								
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
		
	}
	
	private void addPrice(ArrayList<Price> pricesFromApi, Product product, String currency, String unit, 
			Date fromDate, Date toDate, double rateValue, double minimumValue, Integer minimumStay){
		Price price = new Price();
		price.setEntityid(product.getId());
		price.setEntitytype(NameId.Type.Product.name());
		price.setPartyid(getAltpartyid());
		price.setName(Price.RACK_RATE);
		price.setType(NameId.Type.Reservation.name());
		price.setQuantity(1.0);
		price.setCurrency(currency);
		price.setUnit(unit);
		price.setDate(fromDate);
		price.setTodate(toDate);

		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setValue(rateValue);
		price.setCost(rateValue);
		price.setMinStay(minimumStay);
		price.setMinimum(minimumValue);
		price.setAvailable(1);
		
		pricesFromApi.add(price);
	}
	
	private void insertTax(ArrayList<Tax> taxListFromApi, String productId, String taxName, Double percentValue, boolean isMandatory){
		Tax tax = new Tax();
		tax.setAccountid(Account.VAT_OUTPUT);
		tax.setPartyid(getAltpartyid());
		tax.setProductId(productId);
		tax.setType(Tax.Type.SalesTaxExcluded.name());
		tax.setDate(new Date());
		tax.setThreshold(0);
		tax.setName(taxName);
		tax.setAmount(percentValue);
		if(isMandatory){
			tax.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
		}else{
			tax.setMandatoryType(Tax.MandatoryType.OptionalTax.name());
		}
		taxListFromApi.add(tax);
	}
	
	private void insertFee(ArrayList<Price> feeMandatoryFromApi, ArrayList<Price> feeNotMandatoryFromApi, String productId, 
			String feeName, Double amountValue, boolean isTaxable, boolean isFeeMandatory,  Date fromDate, Date toDate, 
			String currency, Date version){
		Price priceFee = new Price();
		priceFee.setEntityid(productId);
		priceFee.setPartyid(getAltpartyid());
		
		priceFee.setDate(fromDate);
		priceFee.setTodate(toDate);
		priceFee.setCurrency(currency);
		priceFee.setQuantity(1.0);
		priceFee.setAvailable(1);
		priceFee.setName(feeName);
		priceFee.setValue(amountValue);
		priceFee.setRule(net.cbtltd.shared.Price.Rule.AnyCheckIn.name());
		priceFee.setUnit(Unit.EA); 
		if(isFeeMandatory){
			priceFee.setEntitytype(NameId.Type.Mandatory.name());
			feeMandatoryFromApi.add(priceFee);
		}else{
			priceFee.setEntitytype(NameId.Type.Feature.name());
			feeNotMandatoryFromApi.add(priceFee);
		}
		if(isTaxable){
			priceFee.setType("Fees");
		}else{
			priceFee.setType(Price.NOT_TAXABLE);
		}

	}
	
	@Override
	public synchronized void readProducts() {
		
		String message = "readProducts started AaxSys";
		LOG.debug(message);
		Date version = new Date();
		
		try{
			//update/create products. 
			createProduct();
			LOG.debug("AaxSys:  createProduct done.");
			//update/create images. 
			createImages();
			LOG.debug("AaxSys:  createImages done.");
			LOG.debug("AaxSys:  ReadProducts_DONE"  );
			MonitorService.monitor(message, version);
		}
		catch (Throwable x) {
			LOG.error(x.getStackTrace());
		}
				
	}
	
	@SuppressWarnings("unchecked")
	public void createProduct() {
		String altid = null; 
		String partyCode = null;
		Product product;
		Date timestamp = new Date();
		String message = "createProduct AaxSys";	
		Date version = new Date();
		
		final SqlSession sqlSession = RazorServer.openSession();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String partyAltID =  party.getAltid();

		try {			
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(SERVICE_URL);
			Element root = document.getRootElement();			

			List<Element> units  = root.getChildren("UnitCode");
			ArrayList<String> names = new ArrayList<String>();
			for (Element unit : units) {
				names.add(unit.getText());
			}

			List<Element> vendors  = root.getChildren("Vendor");
			ArrayList<String> vendornames = new ArrayList<String>();
			for (Element vendor : vendors) {
				vendornames.add(vendor.getText());
			}
			List<Element> properties  = root.getChildren("property");

			for(int i = 0; i < properties.size(); i++){
				List<Element> details  = properties.get(i).getChildren("details");
				altid = names.get(i).toString();
				partyCode = vendornames.get(i).toString();

				if(partyAltID.compareTo(partyCode)!=0)
					continue;
				
				System.out.println(altid);
				StringBuilder name = new StringBuilder();
				String unitcode = names.get(i).toString();
				String altsupplierid = vendornames.get(i).toString();
				if (unitcode != null && !unitcode.isEmpty()) {name.append(unitcode);}
				String propertytype = details.get(0).getChildText("property-type");
				propertytype = "apartment";
				if (propertytype != null && !propertytype.isEmpty()) {name.append(" ").append(propertytype);}			
				int bathroom = (int) (Double.parseDouble(details.get(0).getChildText("num-bathrooms"))); 
				int rooms = Integer.parseInt(details.get(0).getChildText("num-bedrooms"));
				int toilet = 0;
				if (details.get(0).getChildText("num-half-bathrooms") != null && !details.get(0).getChildText("num-half-bathrooms").isEmpty()) {
					toilet = Integer.parseInt(details.get(0).getChildText("num-half-bathrooms"));
				}
				
				String maxpersons = details.get(0).getChildText("Sleeps"); 
				Double cleaningfee = 0.0;
				
				if(altid.compareTo("PARK-513")==0 || altid.compareTo("GC19-179")==0 || altid.compareTo("WE1207")==0)
					continue;
				
				product = PartnerService.getProduct(sqlSession, getAltpartyid(), altid);
				if (product == null) {continue;}
						
				List<Element> locationdetails  = properties.get(i).getChildren("location");
				String country = Country.US;
				String region = locationdetails.get(0).getChildText("state-code");
				int existComma = locationdetails.get(0).getChildText("city-name").indexOf(",");
				String place;
				if(existComma>0)
					 place = locationdetails.get(0).getChildText("city-name").substring(0,existComma);
				else
					 place = locationdetails.get(0).getChildText("city-name");
				
				
				String zipCode = locationdetails.get(0).getChildText("zipcode");
				StringBuilder address = new StringBuilder();
				String placename = locationdetails.get(0).getChildText("street-address");
				if (placename != null && !placename.isEmpty()) {address.append(placename);}
				if (place != null && !place.isEmpty()) {address.append("\n").append(place);}
				if (region != null && !region.isEmpty()) {address.append("\n").append(region);}
				if (country != null && !country.isEmpty()) {address.append("\n").append(country);}
				
				Location location = PartnerService.getLocation(
								sqlSession, 
								place, 
								region, 
								country);
				
				List<Element> website  = properties.get(i).getChildren("site");
				String webaddress = website.get(0).getChildText("site-url");	
				
				List<Element> rentalterms  = properties.get(i).getChildren("rental-terms");
				String currency = "USD"; //TODO CHECK!
				Double deposit = 0.0;//Double.parseDouble(rentalterms.get(0).getChildText("security-deposit"));//They confirmed that it isn't necessary to include in total amount
				String terms = rentalterms.get(0).getChildText("price-term");
				
				ArrayList<String> attributes = new ArrayList<String>();
				addType(attributes, propertytype);
				
				List<Element> detailedcharacteristics  = properties.get(i).getChildren("detailed-characteristics");
				List<Element> appliances  = detailedcharacteristics.get(0).getChildren("appliances");
				List<Element> heatingsystems  = detailedcharacteristics.get(0).getChildren("heating-systems");
				String washer = appliances.get(0).getChildText("has-washer");
				String dishwasher = appliances.get(0).getChildText("has-dishwasher"); 
				String microwave = appliances.get(0).getChildText("has-microwave");
				String fireplace = heatingsystems.get(0).getChildText("has-fireplace");
				String satellite = detailedcharacteristics.get(0).getChildText("has-cable-satellite");
				String deck = detailedcharacteristics.get(0).getChildText("has-deck");
				String garden = detailedcharacteristics.get(0).getChildText("has-garden");
				String bathtub = detailedcharacteristics.get(0).getChildText("has-jetted-bath-tub");
				String pool = detailedcharacteristics.get(0).getChildText("has-pool");
				String sauna = detailedcharacteristics.get(0).getChildText("has-sauna");
				String fitnesscenter = detailedcharacteristics.get(0).getChildText("building-has-fitness-center");
				
				if(washer.compareTo("yes")==0)addAttribute(attributes, "has-washer");
				if(dishwasher.compareTo("yes")==0)addAttribute(attributes, "has-dishwasher");
				if(microwave.compareTo("yes")==0)addAttribute(attributes, "has-microwave");
				if(fireplace.compareTo("yes")==0)addAttribute(attributes, "has-fireplace");
				if(satellite.compareTo("yes")==0)addAttribute(attributes, "has-cable-satellite");
				if(deck.compareTo("yes")==0)addAttribute(attributes, "has-deck");
				if(garden.compareTo("yes")==0)addAttribute(attributes, "has-garden");
				if(bathtub.compareTo("yes")==0)addAttribute(attributes, "has-jetted-bath-tub");
				if(pool.compareTo("yes")==0)addAttribute(attributes, "has-pool");
				if(sauna.compareTo("yes")==0)addAttribute(attributes, "has-sauna");
				if(fitnesscenter.compareTo("yes")==0)addAttribute(attributes, "building-has-fitness-center");
				
				StringBuilder sb = new StringBuilder();
				String language = Language.EN;
				String text;
				text = details.get(0).getChildText("description");
				if (text != null && !text.isEmpty()) {
					sb.append(text).append("\n");
					product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), NameId.trim(sb.toString(), 20000), language));
					TextService.update(sqlSession, product.getTexts());
				}
				
				product.setVersion(version);
				
				createOrUpdateProductModel(location, place, region, zipCode, country, maxpersons, 
						bathroom, toilet, rooms, propertytype, currency, deposit, terms, cleaningfee, webaddress,
						address, name,altid,altsupplierid, product);
				sqlSession.getMapper(ProductMapper.class).update(product);
		
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, product.getId(), product.getValues());
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), attributes);			
			}
			sqlSession.commit();
			
			//canceling not updated products
			Product action = new Product();
			action.setAltpartyid(getAltpartyid());
			action.setState(Product.CREATED);
			action.setVersion(version); 
			
			sqlSession.getMapper(ProductMapper.class).cancelversion(action);
			sqlSession.commit();
		}catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		} 		
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
	}
	
	private Product createOrUpdateProductModel(Location location, String place, String region, String zipCode, 
			String country, String maxpersons, Integer bathroom, Integer toilet, Integer rooms, String propertytype,
			String currency, Double deposit, String terms, Double cleaningfee,
			String webaddress, StringBuilder address, StringBuilder name, String altid,
			String altsupplierid, Product product) {
		
		if(product == null) {
		product = new Product();
		}
		if(location!=null){
			product.setLocationid(location.getId());
			product.setLatitude(location.getLatitude());
			product.setLongitude(location.getLongitude());
		}else{
			product.setState(Product.SUSPENDED);
		}
		
		product.setAltitude(0.0);
		product.setPhysicaladdress(address.toString());
		
		product.setAltSupplierId(altsupplierid);
		product.setAltpartyid(getAltpartyid()); 
		product.setSupplierid(getAltpartyid());  
		product.setAltid(altid);
		
		product.setPerson(maxpersons == null || maxpersons.isEmpty() ? 1 : Integer.valueOf(maxpersons));
		product.setChild(0); 
		product.setInfant(0);
		product.setQuantity(1); 
		
		product.setRating(5);
		product.setCommission(getCommission());
		product.setDiscount(getDiscount());
		product.setRank(getRank());
		
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
	
	@SuppressWarnings("unchecked")
	public void createImages() {
		final SqlSession sqlSession = RazorServer.openSession();
		long startTime = System.currentTimeMillis();
		String altid = null; 
		String message = "Create Images started AaxSys. " ;
		LOG.debug(message);
		Date version = new Date();
		
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String partyAltID =  party.getAltid();
		
		try {
			RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD_DATE, getAltpartyid(), new Date().toString());
			
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(SERVICE_URL);
			Element root = document.getRootElement();
			
			List<Element> units  = root.getChildren("UnitCode");
			ArrayList<String> names = new ArrayList<String>();
			for (Element unit : units) {
				names.add(unit.getText());
			}

			List<Element> vendors  = root.getChildren("Vendor");
			ArrayList<String> vendornames = new ArrayList<String>();
			for (Element vendor : vendors) {
				vendornames.add(vendor.getText());
			}
			
			List<Element> properties  = root.getChildren("property");

			for(int i = 0; i < properties.size(); i++){
				altid = names.get(i).toString();
				String partyCode = vendornames.get(i).toString();
				
				if(partyAltID.compareTo(partyCode)!=0)
					continue;
				
				Product product = PartnerService.getProduct(sqlSession,
						getAltpartyid(), altid,false);
				if (product == null) {
					continue;
				}
				
				List<Element> picturedetails = properties.get(i).getChildren("pictures");
				List<Element> pictures = picturedetails.get(0).getChildren("picture");
				ArrayList<NameId> images = new ArrayList<NameId>();	
				for (Element picture : pictures) {									
					images.add(new NameId(picture.getChildText("picture-url").substring(picture.getChildText("picture-url").lastIndexOf("/")+1),  picture.getChildText("picture-url")));
				}
				LOG.debug("images " + images);
				UploadFileService.uploadImages(sqlSession,NameId.Type.Product, product.getId(), Language.EN,images);
				
				sqlSession.commit();
				
			}
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {
			sqlSession.close();
		}
		MonitorService.monitor(message, version);
		long endTime = System.currentTimeMillis();
		LOG.debug("Total time taken for createImage execution " + getApikey() + " : " + (endTime - startTime)/1000 + " seconds.");
	}
	
	@SuppressWarnings("unchecked")
	public void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule AaxSys";
		LOG.debug(message);
		final SqlSession sqlSession = RazorServer.openSession();
		Party party = sqlSession.getMapper(PartyMapper.class).read(this.getAltpartyid());
		String partyAltID =  party.getAltid();
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(SERVICE_URL);
			Element root = document.getRootElement();
			
			List<Element> units  = root.getChildren("UnitCode");
			ArrayList<String> names = new ArrayList<String>();
			for (Element unit : units) {
				names.add(unit.getText());
			}
			List<Element> vendors  = root.getChildren("Vendor");
			ArrayList<String> vendornames = new ArrayList<String>();
			for (Element vendor : vendors) {
				vendornames.add(vendor.getText());
			}
			
			List<Element> properties  = root.getChildren("property");

			for(int i = 0; i < properties.size(); i++){
				String altid = names.get(i).toString();
				
				String partyCode = vendornames.get(i).toString();
				
				if(partyAltID.compareTo(partyCode)!=0)
					continue;
				
				LOG.debug("Property: " + altid);
				
				Product product = PartnerService.getProduct(sqlSession,getAltpartyid(), altid,false);
				if (product == null) {
					continue;
				}
				
				String calendarlink = properties.get(i).getChildText("CalendarLink");
				calendarlink = calendarlink.replaceFirst("S=AMSI-SDS&amp;", "");
				calendarlink = calendarlink.replaceFirst("S=AMSI-SF&amp;", "");
				Document board = (Document) builder.build(calendarlink);
				Element reservationboard = board.getRootElement(); 
				List<Element> reservations  = reservationboard.getChildren("Reservation");
				for(Element reservation : reservations){
					PartnerService.createSchedule(sqlSession, product, 
							DF.parse(DF.format(API_FORMAT.parse(reservation.getChildText("BeginDate")))), 
							DF.parse(DF.format(API_FORMAT.parse(reservation.getChildText("EndDate")))), 
							timestamp);
					PartnerService.cancelSchedule(sqlSession, product, timestamp);
				}
			}
			
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} 
		finally {sqlSession.close();}
		MonitorService.monitor(message, timestamp);
		
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
			TYPES.put("townhouse","PCT16"); //Guesthouse
			TYPES.put("condo","PCT8");      //Condominium
			TYPES.put("apartment","PCT3");  //Appartement
		}
		if (TYPES.get(type) == null) {attributes.add(type);}
		else  {attributes.add(TYPES.get(type));}
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
			
			ATTRIBUTES.put("has-washer", "RMA149");
			ATTRIBUTES.put("has-dishwasher", "RMA32");
			ATTRIBUTES.put("has-microwave", "RMA68");
			ATTRIBUTES.put("has-fireplace", "RMA41");
			ATTRIBUTES.put("has-cable-satellite", "RMA210");
			ATTRIBUTES.put("has-deck", "CBF8");
			ATTRIBUTES.put("has-garden", "FAC4");
			ATTRIBUTES.put("has-jetted-bath-tub", "RMA13");
			ATTRIBUTES.put("has-pool", "HAC71");
			ATTRIBUTES.put("has-sauna", "HAC79");
			ATTRIBUTES.put("building-has-fitness-center", "RST12");
			
		}
		
		if(ATTRIBUTES.get(attribute) == null){attributes.add(attribute);}
		
		if (ATTRIBUTES.get(attribute) != null) {attributes.add(ATTRIBUTES.get(attribute));}
	}
	
	@Override
	public void readSpecials() {
		String message = "readSpecials Aaxsys" ;
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Aaxsys readSpecials()");
		}
		catch (Throwable x) {
			LOG.error(x.getMessage());
		} 
		
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {		
		throw new ServiceException(Error.service_absent, "Aaxsys (ApiKey:"+this.getApikey()+") readPrice()");
	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Aaxsys readDescriptions()");	
	}

	@Override
	public void readImages() {
		throw new ServiceException(Error.service_absent, "Aaxsys readImages()");
		//added in readProducts();
	}

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.service_absent, "Aaxsys readAdditionCosts()");
		
	}

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Aaxsys inquireReservation()");
	}
	
	
}