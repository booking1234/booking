package net.cbtltd.rest.flipkey.supplier;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.AbstractReservationExt;
import net.cbtltd.rest.Available;
import net.cbtltd.rest.Constants;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.log4j.Logger;

/**
 * FlipKey will post inquiries received for the clients properties in realtime.
 * The client will provide a service that handles inquiry POSTs. The client will
 * provide the URL of their service to FlipKey and optionally a username and
 * password.So this class will act as the end point for Flipkey to post data
 * 
 * @author Senthilnathan
 * 
 */

@Path("/inquiry")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlipKeySupplierJSONAPI extends AbstractReservationExt {
	private static final Logger LOG = Logger
			.getLogger(FlipKeySupplierJSONAPI.class.getName());

	/**
	 * Indicates if a product (product) is available for the specified dates
	 * @param inquiryRequest
	 * @param pos
	 * @param test
	 * @param xsl
	 * @return Available
	 */
	@POST
	@Path("/inquiry")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Descriptions({
			@Description(value = "Indicates if a product (product) is available for the specified dates", target = DocTarget.METHOD),
			@Description(value = "Availability Indicator", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST),
			@Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public static synchronized Available availabilityInquiry(
			InquiryRequest inquiryRequest,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {

		LOG.info("Inside availabilityInquiry method");

		return getAvailable(inquiryRequest.getClient_property_id(),
				inquiryRequest.getArrival(), inquiryRequest.getDeparture(),
				pos, xsl);

	}
	
	/**
	 * Indicates if a product (product) is available for the specified dates
	 * @param pos
	 * @param test
	 * @param xsl
	 * @return "Successfully called"
	 */
	@GET
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Descriptions({
			@Description(value = "Indicates if a product (product) is available for the specified dates", target = DocTarget.METHOD),
			@Description(value = "Availability Indicator", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST),
			@Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public static synchronized String test(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {

		LOG.info("Inside availabilityInquiry method");

		return "Successfully called";

	}


}
