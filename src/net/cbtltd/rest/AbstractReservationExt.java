package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.TextService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;

/************************************************************************************************************************
 * PMS Exchange old style - do not use or change
************************************************************************************************************************/
public class AbstractReservationExt extends AbstractReservation {

	/**
	 * Creates a provisional reservation.
	 *
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param emailaddress the emailaddress
	 * @param familyname the familyname
	 * @param firstname the firstname
	 * @param notes the reservation notes
	 * @param quote the quoted price for the stay
	 * @param tax the total tax on the quoted price for the stay
	 * @param currency the currency code of the quote
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the provisional
	 */
	protected static Provisional getProvisional(
			String productid,
			String fromdate,
			String todate,
			String emailaddress,
			String familyname,
			String firstname,
			String notes,
			String quote,
			String tax,
			String currency,
			String pos,
			Boolean test,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/" + productid + "/" + fromdate + "/" + todate + "/" + emailaddress + "/" + familyname + "/" + firstname + "/" + notes + "/" + quote + "/" + tax + "/" + currency + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Provisional result = null;
		try {
			Party agent = Constants.getParty(sqlSession, pos);

			Product product = getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT); //sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null 
					|| !product.hasState(Constants.CREATED) 
			) {	throw new ServiceException(Error.product_id, productid);}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid + " is not Accommodation");}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			
			if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.party_emailaddress, emailaddress);}
			if (fromdate == null || fromdate.isEmpty() 
					|| todate == null || todate.isEmpty()) {throw new ServiceException(Error.date_invalid, fromdate + " to " + todate);}
			if (!Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_format, fromdate + " to " + todate);}
			if (familyname == null || familyname.isEmpty()) {throw new ServiceException(Error.family_name, familyname);}
			Party customer =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
			if (customer == null) {
				customer = new Party();
				customer.setEmailaddress(emailaddress);
				customer.setName(familyname, firstname);
				sqlSession.getMapper(PartyMapper.class).create(customer);
				RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), agent.getId(), customer.getId());
				RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), product.getSupplierid(), customer.getId());
			}

			Reservation reservation = new Reservation();
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agent.getId());
			reservation.setCustomerid(customer.getId());
			reservation.setFromdate(Constants.parseDate(fromdate));
			reservation.setTodate(Constants.parseDate(todate));
			reservation.setDate(new Date());
			reservation.setArrivaltime("14:00");
			reservation.setDeparturetime("10:00");
			reservation.setDuedate(reservation.getDate());
			reservation.setDonedate(null);
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setNotes(notes == null || notes.isEmpty() ? "XML Reservation Request" : notes);
			reservation.setState(Reservation.State.Provisional.name());
			reservation.setProductid(productid);
			ReservationService.computePrice(sqlSession, reservation, null);
			
			if (quote != null && tax != null) {
				Double oldquote = reservation.getQuote();
				if (currency != null && !product.hasCurrency(currency)) {
					reservation.setQuote(WebService.getRate(sqlSession, currency, product.getCurrency()) * ((oldquote == null) ? 0.0 : oldquote));
				}
				Double newquote = Double.valueOf(quote);
				reservation.setQuote(newquote);
				if (reservation.hasQuotedetail()) {
					for (net.cbtltd.shared.Price price : reservation.getQuotedetail()) {
						if (
								price.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)
								|| price.hasType(net.cbtltd.shared.Price.TAX_INCLUDED)
								|| price.hasType(net.cbtltd.shared.Price.TAX_ON_TAX)
							) {
							price.setValue(price.getValue() * newquote / oldquote);
						}
					}
				}
			}
			
			//TODO: save tax allocations
			reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));

			String textid = NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Contract.name();
			String terms = TextService.notes(sqlSession, textid, Language.EN);

			if (!test) {
				reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
				sqlSession.getMapper(ReservationMapper.class).create(reservation);

				reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
				if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, productid + " " + fromdate + " " + todate);}

				message = "The provisional reservation number is " + reservation.getName();
				sqlSession.commit();
				EmailService.provisionalReservation(sqlSession, reservation);
			}

			result = new Provisional(
					reservation.getId(),
					reservation.getName(),
					productid, 
					product.getName(), 
					fromdate, 
					todate, 
					emailaddress, 
					familyname, 
					firstname, 
					String.valueOf(NameId.round(reservation.getPrice())), 
					String.valueOf(NameId.round(reservation.getQuote())), 
					String.valueOf(NameId.round(reservation.getCost())), 
					String.valueOf(NameId.round(reservation.getDeposit(reservation.getQuote()))), 
					reservation.getCurrency(),
					getAlert(sqlSession, reservation),
					reservation.getNotes(),
					terms, 
					message,
					xsl);
		}
		catch(Throwable x) {
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Provisional(null, null, productid, null, fromdate, todate, emailaddress, familyname, firstname, null, null, null, null, null, null, null, null, message + " " + x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the upload.
	 *
	 * @param reservationid the reservationid
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param emailaddress the emailaddress
	 * @param familyname the familyname
	 * @param firstname the firstname
	 * @param price the price
	 * @param currency the currency
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the upload
	 */
	protected static Download getUpload(
			String reservationid,
			String productid,
			String fromdate,
			String todate,
			String emailaddress,
			String familyname,
			String firstname,
			String price,
			String currency,
			String pos,
			String xsl) {
		Date timestamp = new Date();
		String message = "/reservation/upload/" + reservationid + "/" + productid + "/" + fromdate + "/" + todate + "/" + emailaddress + "/" + familyname + "/" + firstname + "/" + price + "/" + currency + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		
		//TODO: CJM
		//if ("76e3fef6c90093eb".equalsIgnoreCase(pos)) {
		//	return new Download("0", "0", "0", "2010-01-01", "2020-12-31", "aaa@bbb.com", "familyname", "firstname", null, null, null, price, currency, message, xsl);
		//}
		
		SqlSession sqlSession = RazorServer.openSession();
		Download result = null;
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			Party customer = organization;

			if (fromdate == null || fromdate.isEmpty() 
					|| todate == null || todate.isEmpty()) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}
			if (!Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new ServiceException(Error.date_format, fromdate + " to " + todate);}

			Product product = getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT); //sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null || !product.hasState(Constants.CREATED)) {throw new ServiceException(Error.product_id, productid);}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_id, productid + " not type Accommodation");}
			if (!organization.hasId(product.getSupplierid())) {throw new ServiceException(Error.pos_party, organization.getId() + " pos code <> manager " + product.getSupplierid());}

			Double quote = Double.valueOf(price);
			if (!product.hasCurrency(currency)) { // convert
				Double exchangerate = WebService.getRate(sqlSession, currency, product.getCurrency(), new Date());
				quote = NameId.round(quote * exchangerate);
			}

			if (emailaddress != null 
					&& !emailaddress.isEmpty()
					&& !emailaddress.equals(Constants.BLANK)
					&& Party.isEmailAddress(emailaddress)
					&& familyname != null 
					&& !familyname.isEmpty()
					&& !familyname.equals(Constants.UNALLOCATED)
					) {
				//Find or create customer
				customer =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
				if (customer == null) {
					customer = new Party();
					customer.setEmailaddress(emailaddress);
					customer.setName(familyname, firstname);
					customer.setCountry(organization.getCountry());
					customer.setCreatorid(organization.getId());
					customer.setCurrency(organization.getCurrency());
					customer.setLanguage(organization.getLanguage());
					customer.setLocationid(organization.getLocationid());
					customer.setState(Party.CREATED);
					sqlSession.getMapper(PartyMapper.class).create(customer);
					RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), organization.getId(), customer.getId());
					RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), product.getSupplierid(), customer.getId());
				}
			}
			//LOG.debug("ReservationService getUpload 1 " + customer);

			Reservation reservation = new Reservation();
			reservation.setName(reservationid);
			reservation.setOrganizationid(product.getSupplierid());
			//			reservation.setOrganizationid(organization.getId());
			if (reservationid.contains(".")) {reservation = sqlSession.getMapper(ReservationMapper.class).readbyforeignid(reservationid);}
			else {reservation = sqlSession.getMapper(ReservationMapper.class).readbyname(reservation);}

			//LOG.debug("ReservationService getUpload 2 " + reservation);

			if (reservation == null && reservationid.contains(".")) { // upload new foreign reservation
				//Create reservation
				reservation = new Reservation();
				String supplierId = product.getSupplierid();
				PropertyManagerInfo proeprtyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(supplierId));
				reservation.setOrganizationid(supplierId);
				reservation.setActorid(Party.NO_ACTOR);
				reservation.setAgentid(organization.getId());
				reservation.setCustomerid(customer.getId());
				reservation.setFromdate(Constants.parseDate(fromdate));
				reservation.setTodate(Constants.parseDate(todate));
				reservation.setArrivaltime(proeprtyManagerInfo.getCheckInTime().toString());
				reservation.setDeparturetime(proeprtyManagerInfo.getCheckOutTime().toString());
				reservation.setDate(new Date());
				reservation.setDuedate(reservation.getDate());
				reservation.setDonedate(null);
				//reservation.setDeposit();
				reservation.setUnit(product.getUnit());
				reservation.setCurrency(product.getCurrency());
				reservation.setNotes("Reservation Upload by " + organization.getName());
				reservation.setPrice(NameId.round(Double.valueOf(price)));
				reservation.setPrice(quote);
				reservation.setQuote(reservation.getPrice());
				reservation.setCost(reservation.getPrice());
				reservation.setState(Reservation.State.Confirmed.name());
				reservation.setProductid(productid);

				reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
				sqlSession.getMapper(ReservationMapper.class).create(reservation);
				RelationService.load(sqlSession, Downloaded.RESERVATION_UPLOAD, reservation.getId(), reservationid);
				RelationService.load(sqlSession, Downloaded.RESERVATION_DOWNLOAD, reservation.getName(), reservation.getOrganizationid());
				//To prevent an uploaded reservation from being downloaded again
			}
			else if (reservation != null) { // update reservation
				reservation.setOrganizationid(product.getSupplierid());
				reservation.setCustomerid(customer.getId());
				reservation.setFromdate(Constants.parseDate(fromdate));
				reservation.setTodate(Constants.parseDate(todate));
				reservation.setUnit(product.getUnit());
				reservation.setCurrency(product.getCurrency());
				reservation.setPrice(NameId.round(Double.valueOf(price)));
//				reservation.setPrice(Double.valueOf(price));
				reservation.setQuote(reservation.getPrice());
				reservation.setCost(reservation.getPrice());
				reservation.setState(Reservation.State.Confirmed.name());
				reservation.setProductid(productid);
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
			}
			else {throw new ServiceException(Error.reservation_upload_id, reservationid);}
			//LOG.debug("getUpload 3 " + reservation);

//TODO: CJM			reservation.setCollisions(sqlSession.getMapper(ReservationMapper.class).collisions(reservation));
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, productid + " from " + fromdate + " to " + todate);}
			result = new Download(reservation.getName(), reservationid, productid, fromdate, todate, emailaddress, familyname, firstname, null, null, null, price, currency, message, xsl);
			MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Reservation, reservation);
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.product_not_available.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new Download(null, reservationid, productid, fromdate, todate, emailaddress, familyname, firstname, null, null, null, price, currency, message + " " + x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Sets the provisional reservation.
	 *
	 * @param reservationid the reservation ID
	 * @param productid the product ID
	 * @param fromdate the arrival date
	 * @param todate the departure date
	 * @param quote the quoted price
	 * @param cost the sto rate
	 * @param currency the quote currency
	 * @param pos the pos code
	 * @param test the test flag
	 * @param xsl the style sheet
	 * @return the provisional reservation
	 */
	protected static Provisional setProvisional(
			String reservationid,
			String productid,
			String fromdate,
			String todate,
			Double quote,
			Double cost,
			String currency,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/update/" + reservationid + "/" + productid + "/" + fromdate + "/" + todate + "/" + quote + "/" + cost + "/" + currency + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Provisional result = null;
		try {
			Party agent = Constants.getParty(sqlSession, pos);

			if (reservationid == null || reservationid.isEmpty()) {throw new ServiceException(Error.reservation_id, reservationid);}
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
			if (reservation == null || reservationid.isEmpty()) {throw new ServiceException(Error.reservation_id, reservationid);}
			if (reservation.hasState(Reservation.State.Initial.name()) 
					|| reservation.hasState(Reservation.State.Closed.name()) 
					|| reservation.hasState(Reservation.State.Cancelled.name()) 
					|| reservation.hasState(Reservation.State.Arrived.name()) 
					|| reservation.hasState(Reservation.State.Departed.name()) 
					|| reservation.hasState(Reservation.State.Final.name()) 
			) {	throw new ServiceException(Error.reservation_state, reservation.getName() + " " + reservation.getState());}

			if (productid == null || productid.isEmpty()) {productid = reservation.getProductid();}
			Product product = getProduct(sqlSession, pos, productid, License.DEFAULT_WAIT); //sqlSession.getMapper(ProductMapper.class).read(productid);

			if (product == null) {throw new ServiceException(Error.product_id, productid);}
			if (!product.hasState(Constants.CREATED)) {throw new ServiceException(Error.product_state, productid + " has state " + product.getState());}
			if (product.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, product.getType());}
			if (product.noRank()) {throw new ServiceException(Error.product_not_online, productid);}
			reservation.setProductid(productid);
			
			if (fromdate != null && !fromdate.isEmpty()) {reservation.setFromdate(Constants.parseDate(fromdate));}
			if (todate != null && !todate.isEmpty()) {reservation.setTodate(Constants.parseDate(todate));}
			if (!reservation.getTodate().after(reservation.getFromdate())) {throw new ServiceException(Error.date_range, fromdate + " to " + todate);}

			if (quote != null && quote > 0.0 && reservation.hasCurrency(currency)) {reservation.setQuote(quote);}

			if (cost != null && cost > 0.0 && reservation.hasCurrency(currency)) {reservation.setCost(cost);}

			if (!test) {
				sqlSession.getMapper(ReservationMapper.class).update(reservation);

//TODO: CJM				reservation.setCollisions(sqlSession.getMapper(ReservationMapper.class).collisions(reservation));
				reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
				if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, productid + " from " + fromdate + " to " + todate);}
				MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Reservation, reservation);
				sqlSession.commit();
			}
			result = new Provisional(
					productid, 
					product.getName(), 
					Constants.formatDate(reservation.getFromdate()), 
					Constants.formatDate(reservation.getTodate()), 
					String.valueOf(NameId.round(reservation.getQuote())), 
					reservation.getCurrency(),
					getAlert(sqlSession, reservation),
					message, xsl);
		}
		catch(Throwable x){
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Provisional(productid, null, fromdate, todate, String.valueOf(NameId.round(quote)), currency, null, message, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Cancel download.
	 *
	 * @param reservationname the reservation number
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the response
	 */
	protected static Response cancelDownload(
			String reservationname,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/cancel/" + reservationname  + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		
		SqlSession sqlSession = RazorServer.openSession();
		Response result = null;
		try {
			//String organizationid = Constants.decryptPos(pos);
			Party organization = Constants.getParty(sqlSession, pos);
			Reservation reservation = new Reservation();
			reservation.setName(reservationname);
			reservation.setOrganizationid(organization.getId());
			if (reservationname.contains(".")) {reservation = sqlSession.getMapper(ReservationMapper.class).readbyforeignid(reservationname);}
			else {reservation = sqlSession.getMapper(ReservationMapper.class).readbyname(reservation);}
			if (reservation == null) {reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationname);}
			if (reservation == null || !organization.hasId(reservation.getOrganizationid())) {throw new ServiceException(Error.reservation_bad, reservationname);}

			getProduct(sqlSession, pos, reservation.getProductid(), License.DEFAULT_WAIT);

			if (!test) {
				reservation.setState(Reservation.State.Cancelled.name());
					//EmailService.canceledReservation(sqlSession, organizationid, reservation);
				reservation.setDonedate(new Date());
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Reservation, reservation);
				sqlSession.commit();
			}
			result = new Response("Canceled " + reservation.getName(), message, xsl);
		}
		catch(Throwable x){
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Response("Error " + x.getMessage(), message, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the download.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the download
	 */
	protected static Downloads getDownload(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/download/" + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Downloads result = null;
		try {
			String organizationid = Constants.decryptPos(pos);
			ArrayList<Download> downloads = sqlSession.getMapper(ReservationMapper.class).download(organizationid);
			//LOG.debug("downloads " + downloads);
			if (downloads == null || downloads.isEmpty()) {throw new ServiceException(Error.reservation_download, organizationid);}
			result = new Downloads(message, downloads, xsl);
			for (Download download : downloads) {RelationService.load(sqlSession, Downloaded.RESERVATION_DOWNLOAD, download.getReservationid(), organizationid);}
			sqlSession.commit();
		}
		catch(Throwable x){
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Downloads(message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the downloads.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the downloads
	 */
	protected static Downloads getDownloads(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/downloads/" + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Downloads result = null;
		try {
			String organizationid = Constants.decryptPos(pos);
			ArrayList<Download> downloads = sqlSession.getMapper(ReservationMapper.class).downloads(organizationid);
			if (downloads == null || downloads.isEmpty()) {throw new ServiceException(Error.reservation_download, organizationid);}
			result = new Downloads(message, downloads, xsl);
			for (Download download : downloads) {RelationService.load(sqlSession, Downloaded.RESERVATION_DOWNLOAD, download.getReservationid(), organizationid);}
			sqlSession.commit();
		}
		catch(Throwable x) {
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Downloads(message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the uploads.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the uploads
	 */
	protected static Downloads getUploads(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/reservation/uploads/" + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Downloads result = null;
		try {
			String organizationid = Constants.decryptPos(pos);
			ArrayList<Download> downloads = sqlSession.getMapper(ReservationMapper.class).uploads(organizationid);
			if (downloads == null || downloads.isEmpty()) {throw new ServiceException(Error.reservation_upload, organizationid);}
			result = new Downloads(message, downloads, xsl);
			sqlSession.commit();
		}
		catch(Throwable x) {
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result = new Downloads(message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	
}
