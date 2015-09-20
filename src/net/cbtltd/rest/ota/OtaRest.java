/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.ota;

import java.util.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.cbtltd.rest.Constants;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTACancelRS;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRS;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRQ;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRS;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRS;
import net.cbtltd.soap.ota.server.OTAPingRQ;
import net.cbtltd.soap.ota.server.OTAPingRS;
import net.cbtltd.soap.ota.server.OTAProfileCreateRQ;
import net.cbtltd.soap.ota.server.OTAProfileCreateRS;
import net.cbtltd.soap.ota.server.OTAReadRQ;
import net.cbtltd.soap.ota.server.OTAResRetrieveRS;
import net.cbtltd.soap.ota.server.OtaServiceRazor;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class OtaRest implements the Razor OTA REST Services. */
@Path("/ota")
@Produces("application/xml")
public class OtaRest {

	private static final Logger LOG = Logger.getLogger(OtaRest.class.getName());
	private static final String getXGCDate(String datestring) {
		return (datestring.length() == 10) ? datestring + "T00:00:00" : datestring;
	}

	@GET
	@Path("/list")
	@Descriptions({ 
	   @Description(value = "OTA lists", target = DocTarget.METHOD),
	   @Description(value = "OTA List", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getOtaTypes(
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		String message = "/ota/list?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {return new Items("OTA Types", null, sqlSession.getMapper(ProductMapper.class).otatypes(), xsl);} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			return new Items("OTA Types", x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	@GET
	@Path("/list/{type}")
	@Descriptions({ 
	   @Description(value = "OTA codes by list", target = DocTarget.METHOD),
	   @Description(value = "OTA List Codes", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getOtas(
			@Description("OTA List") @PathParam("type") String type,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		String message = "/ota/list/" + type + "?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {return new Items(type, null, sqlSession.getMapper(ProductMapper.class).productotas(type), xsl);} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage()); 
			return new Items(type, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	@GET
	@Path("/CancelRQ/{reservationid}/{reason}")
	@Descriptions({ 
	   @Description(value = "Create OTA_CancelRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_CancelRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTACancelRQ cancelRQ(
			@Description("Reservation ID") @PathParam("reservationid") String reservationid,
			@Description("Reason for Cancellation") @PathParam("reason") String reason,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/CancelRQ/" + reservationid + "/" + reason + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTACancelRQ result = OtaServiceRazor.cancelRQ(reservationid, reason, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage());
			return OtaServiceRazor.cancelRQ(reservationid, x.getMessage(), pos);
		}
	}

	@GET
	@Path("/CancelRS/{reservationid}/{reason}")
	@Descriptions({ 
	   @Description(value = "Cancel reservation with OTA_CancelRQ message", target = DocTarget.METHOD),
	   @Description(value = "OTACancelRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTACancelRS cancelRS(
			@PathParam("reservationid") String reservationid,
			@PathParam("reason") String reason,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/CancelRS/" + reservationid + "/" + reason + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTACancelRS result = OtaServiceRazor.cancelRS(OtaServiceRazor.cancelRQ(reservationid, reason, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelAvailRQ/{productids}/{fromdate}/{todate}/{summary}")
	@Descriptions({ 
	   @Description(value = "Create OTA_HotelAvailRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelAvailRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelAvailRQ hotelAvailRQ(
			@Description("Product ID") @PathParam("productids") String productids,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("True for availability summary for product(s) and date range") @DefaultValue("false") @PathParam("summary") Boolean summary,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelAvailRQ/" + productids + "/" + fromdate + "/" + todate + "/" + summary + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelAvailRQ result = OtaServiceRazor.hotelAvailRQ(productids, getXGCDate(fromdate), getXGCDate(todate), summary, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelAvailRS/{productids}/{fromdate}/{todate}/{summary}")
	@Descriptions({ 
	   @Description(value = "Get availability with OTA_HotelAvailRQ message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelAvailRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelAvailRS hotelAvailRS(
			@Description("Product ID") @PathParam("productids") String productids,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("True for availability summary for product(s) and date range") @DefaultValue("false") @PathParam("summary") Boolean summary,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelAvailRS/" + productids + "/" + fromdate + "/" + todate + "/" + summary + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelAvailRS result = OtaServiceRazor.hotelAvailRS(OtaServiceRazor.hotelAvailRQ(productids, getXGCDate(fromdate), getXGCDate(todate), summary, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelDescriptiveInfoRQ/{productids}")
	@Descriptions({ 
	   @Description(value = "Create OTA_HotelDescriptiveInfoRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelDescriptiveInfoRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ(
			@Description("Product ID") @PathParam("productids") String productids,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelDescriptiveInfoRQ/" + productids + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelDescriptiveInfoRQ result = OtaServiceRazor.hotelDescriptiveInfoRQ(productids, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelDescriptiveInfoRS/{productids}")
	@Descriptions({ 
	   @Description(value = "Get property description with OTA_HotelDescriptiveInfoRS request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelDescriptiveInfoRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelDescriptiveInfoRS hotelDescriptiveInfoRS(
			@PathParam("productids") String productids,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelDescriptiveInfoRS/" + productids + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelDescriptiveInfoRS result = OtaServiceRazor.hotelDescriptiveInfoRS(OtaServiceRazor.hotelDescriptiveInfoRQ(productids, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelRatePlanRQ/{productid}/{fromdate}/{todate}/{currency}")
	@Descriptions({ 
	   @Description(value = "Create OTA_HotelRatePlanRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelRatePlanRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelRatePlanRQ hotelRatePlanRQ(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Currency Code for Prices") @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelRatePlanRQ/" + productid + "/" + fromdate  + "/" + todate  + "/" + currency + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelRatePlanRQ result = OtaServiceRazor.hotelRatePlanRQ(productid, getXGCDate(fromdate), getXGCDate(todate), currency, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelRatePlanRS/{productid}/{fromdate}/{todate}/{currency}")
	@Descriptions({ 
	   @Description(value = "Get prices with OTA_HotelRatePlanRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelRatePlanRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelRatePlanRS hotelRatePlanRS(
			@PathParam("productid") String productid,
			@PathParam("fromdate") String fromdate,
			@PathParam("todate") String todate,
			@PathParam("currency") String currency,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelRatePlanRS/" + productid + "/" + fromdate  + "/" + todate  + "/" + currency + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelRatePlanRS result = OtaServiceRazor.hotelRatePlanRS(OtaServiceRazor.hotelRatePlanRQ(productid, getXGCDate(fromdate), getXGCDate(todate), currency, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/ReadRQ/{id}")
	@Descriptions({ 
	   @Description(value = "Create OTA_ReadRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_ReadRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAReadRQ readRQ(
			@Description("Reservation ID") @PathParam("id") String id,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/ReadRQ/" + id + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAReadRQ result = OtaServiceRazor.readRQ(id, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelResNotifRQ/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}")
	@Descriptions({ 
	   @Description(value = "Create OTA_HotelResNotifRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelResNotifRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelResNotifRQ hotelResNotifRQ(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("fromdate") String fromdate,
			@Description("To (Departure) Date") @PathParam("todate") String todate,
			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
			@Description("Guest Family Name") @PathParam("familyname") String familyname,
			@Description("Primary Guest Given Name") @PathParam("firstname") String firstname,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelResNotifRQ/" +  productid + "/" + fromdate + "/" + todate + "/" + emailaddress + "/" + familyname + "/" + firstname + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelResNotifRQ result = OtaServiceRazor.hotelResNotifRQ(productid, getXGCDate(fromdate), getXGCDate(todate), emailaddress, familyname, firstname, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelResNotifRS/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}")
	@Descriptions({ 
	   @Description(value = "Create provisional reservation with OTA_HotelResNotifRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelResNotifRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelResNotifRS hotelResNotifRS(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("fromdate") String fromdate,
			@Description("To (Departure) Date") @PathParam("todate") String todate,
			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
			@Description("Guest Family Name") @PathParam("familyname") String familyname,
			@Description("Primary Guest Given Name") @PathParam("firstname") String firstname,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelResNotifRS/" +  productid + "/" + fromdate + "/" + todate + "/" + emailaddress + "/" + familyname + "/" + firstname + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelResNotifRS result = OtaServiceRazor.hotelResNotifRS(OtaServiceRazor.hotelResNotifRQ(productid, getXGCDate(fromdate), getXGCDate(todate), emailaddress, familyname, firstname, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelSearchRQ/{criteria}")
	@Descriptions({ 
	   @Description(value = "Create OTA_HotelSearchRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelSearchRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelSearchRQ hotelSearchRQ(
			@Description("List of Key=Value Criteria") @PathParam("criteria") String criteria,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelSearchRQ/" + criteria + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelSearchRQ result = OtaServiceRazor.hotelSearchRQ(criteria, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/HotelSearchRS/{criteria}")
	@Descriptions({ 
	   @Description(value = "Get list of properties that match criteria with OTA_HotelSearchRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_HotelSearchRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAHotelSearchRS hotelSearchRS(
			@Description("List of Key=Value Criteria") @PathParam("criteria") String criteria,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/HotelSearchRS/" + criteria + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAHotelSearchRS result = OtaServiceRazor.hotelSearchRS(OtaServiceRazor.hotelSearchRQ(criteria, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/PingRQ")
	@Descriptions({ 
	   @Description(value = "Create PingRQ request message", target = DocTarget.METHOD),
	   @Description(value = "PingRS Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAPingRQ pingRQ(
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/PingRQ?pos=" + pos;
		LOG.debug(message);
		try {
			OTAPingRQ result = OtaServiceRazor.pingRQ(pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/PingRS")
	@Descriptions({ 
	   @Description(value = "Return OTA_PingRS response message with OTA_PingRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_PingRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAPingRS pingRS(
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/PingRS?pos=" + pos;
		LOG.debug(message);
		try {
			OTAPingRS result = OtaServiceRazor.pingRS(OtaServiceRazor.pingRQ(pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}
	
	@GET
	@Path("/ProfileCreateRQ/{keyvalues}")
	@Descriptions({ 
	   @Description(value = "Create OTA_ProfileCreateRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_ProfileCreateRQ Request Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAProfileCreateRQ profileCreateRQ(
			@Description("List of Key=Value Parameters") @PathParam("keyvalues") String keyvalues,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/ProfileCreateRQ/" + keyvalues + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAProfileCreateRQ result = OtaServiceRazor.profileCreateRQ(keyvalues, pos);
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		}
		catch(Throwable x){
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/ProfileCreateRS/{keyvalues}")
	@Descriptions({ 
	   @Description(value = "Create party record with OTA_ProfileCreateRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_ProfileCreateRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAProfileCreateRS profileCreateRS(
			@Description("List of Key=Value Parameters") @PathParam("keyvalues") String keyvalues,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/ProfileCreateRS/" + keyvalues + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAProfileCreateRS result = OtaServiceRazor.profileCreateRS(OtaServiceRazor.profileCreateRQ(keyvalues, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}
	
	@GET
	@Path("/ResRetrieveRS/{id}")
	@Descriptions({ 
	   @Description(value = "Get reservation with OTA_ReadRQ request message", target = DocTarget.METHOD),
	   @Description(value = "OTA_ResRetrieveRS Response Message", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public OTAResRetrieveRS resRetrieveRS(
			@Description("Reservation ID") @PathParam("id") String id,
			@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
		Date timestamp = new Date();
		String message = "/ota/ResRetrieveRS/" + id + "?pos=" + pos;
		LOG.debug(message);
		try {
			OTAResRetrieveRS result = OtaServiceRazor.resRetrieveRS(OtaServiceRazor.readRQ(id, pos));
			MonitorService.monitor(message, timestamp);
			LOG.debug(result);
			return result;
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}
}




//@GET
//@Path("/HotelInvCountNotifRQ/{productid}/{fromdate}/{todate}")
//public OTAHotelInvCountNotifRQ hotelInvCountNotifRQ(
//		@PathParam("productid") String productid,
//		@PathParam("fromdate") String fromdate,
//		@PathParam("todate") String todate,
//		@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
//	Date timestamp = new Date();
//	String message = "/ota/HotelInvCountNotifRQ/" + productid + "/" + fromdate + "/" + todate + "?pos=" + pos;
//	LOG.debug(message);
//	try {
//		OTAHotelInvCountNotifRQ result = OtaServiceRazor.hotelInvCountNotifRQ(productid, fromdate, todate, pos);
//		MonitorService.monitor(message, timestamp);
//		LOG.debug(result);
//		return result;
//	} catch(Throwable e){
//		e.printStackTrace();
//		throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
//	}
//}

//@GET
//@Path("/HotelRatePlanNotifRQ/{productid}")
//public OTAHotelRatePlanNotifRQ hotelRatePlanNotifRQ(
//		@PathParam("productid") String productid,
//		@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
//	Date timestamp = new Date();
//	String message = "/ota/HotelRatePlanNotifRQ/" + productid + "?pos=" + pos;
//	LOG.debug(message);
//	try {
//		OTAHotelRatePlanNotifRQ result = OtaServiceRazor.hotelRatePlanNotifRQ(productid, pos);
//		MonitorService.monitor(message, timestamp);
//		LOG.debug(result);
//		return result;
//	} catch(Throwable e){throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());}
//}

//@GET
//@Path("/HotelResNotifRS/{request}")
//public OTAHotelResNotifRS hotelResNotifRS(
//		@PathParam("request") OTAHotelResNotifRQ request,
//		@Description("Mandatory POS Code") @QueryParam("pos") String pos) {
//	Date timestamp = new Date();
//	String message = "/ota/HotelResNotifRS/" + request + "/" + "?pos=" + pos;
//	LOG.debug(message);
//	try {
//		OTAHotelResNotifRS result = OtaServiceRazor.hotelResNotifRS(request);
//		MonitorService.monitor(message, timestamp);
//		LOG.debug(result);
//		return result;
//	} catch(Throwable e){throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());}
//}


//http://java.sun.com/developer/technicalArticles/WebServices/jaxb/
//HotelSearchRS result = new HotelSearchRS(OtaServiceRazor.hotelSearchRS(OtaServiceRazor.hotelSearchRQ(criteria, pos)));
//HotelSearchRS result = new HotelSearchRS("This is a test");
//JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
//Marshaller marshaller = context.createMarshaller();
//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true)); // line breaks and indentation will appear in the output format
//StringWriter sw = new StringWriter();
//context.createMarshaller().marshal(result, sw);
//context.createMarshaller().marshal(result, System.out);
//http://osdir.com/ml/users-cxf-apache/2009-08/msg00060.html
//1) Return JAXBElement<Book>, not just Book. With jaxb, when marshalling, you 
//either have to hand it a class with an XmlRootElement annotation or you need 
//to hand it a JAXBElement. This should make it go the second route.
//return new JAXBElement<OTAHotelSearchRS>(new QName("OTAHotelSearchRS"), OTAHotelSearchRS.class, result);
//JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
//context.createMarshaller().marshal(rq, System.out);
//		OTAHotelSearchRS rs = OtaServiceRazor.hotelSearchRS(rq);
//context.createMarshaller().marshal(rs, System.out);
//		FileWriter outputStream = new FileWriter("C:/HotelSearchOtaRS.xml");
//		context.createMarshaller().marshal(rs, outputStream);
//		outputStream.close();

