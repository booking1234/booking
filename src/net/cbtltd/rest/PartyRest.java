package net.cbtltd.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.logging.ServiceTimer;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Text;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/** 
 * The Class PartyRest implements REST service for parties.
 */
@Path("/party")
@Produces(MediaType.APPLICATION_XML)
public class PartyRest extends AbstractParty {

	@GET
	@Path("/list")
	@Descriptions({ 
		   @Description(value = "List of party name ID pairs", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})
	public Items getNameIdsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date")  @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getNameIdsXML");
		return getNameIds(pos, rows, version, xsl);
	}
	
	@GET
	@Path("/types")
	@Descriptions({ 
		   @Description(value = "List of party types", target = DocTarget.METHOD),
		   @Description(value = "Party Types", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public Items getTypesXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getTypesXML");
		return getTypes(pos, xsl);
	}
	
	@GET
	@Path("/list/{type}")
	@Descriptions({ 
		   @Description(value = "List of parties by type", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public Items getNameIdsByTypeXML(
			@Description("Party Type") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date")  @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getNameIdsByTypeXML");
		return getNameIdsByType(type, pos, rows, version, xsl);
	}
	
	/*@GET
	@Path("/listorganizations")
	@Descriptions({ 
		   @Description(value = "List of parties by type", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public Items getNameIdsOrganizations(
			@Description("Party Type") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date")  @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getNameIdsByType(type, pos, rows, version, xsl);
	}*/
	
	@GET
	@Path("/posorid")
	@Descriptions({ 
		   @Description(value = "List of parties by type", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public String getCryptedValue(
			@Description("POS Code (encrypted party id)") @QueryParam("pos") String pos,
			@Description("Party ID") @QueryParam("id") String id) {
		return this.getPosOrId(pos, id);
	}
	
	@GET
	@Path("/listorganizations")
	@Descriptions({ 
		   @Description(value = "List of parties by type organization", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public Items getOrganizationNameIds(
			@Description("Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getPMNameIds(rows, xsl);
	}
	
	@GET
	@Path("/{partyid}")
	@Descriptions({ 
		   @Description(value = "Party information", target = DocTarget.METHOD),
		   @Description(value = "Party Information", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})
	public Party getByIdXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getByIdXML");
		return getById(partyid, pos, xsl);
	}
	
	@GET
	@Path("/{partyid}/attribute")
	@Descriptions({ 
	   @Description(value = "List of OTA party attributes", target = DocTarget.METHOD),
	   @Description(value = "Attribute List", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Attributes getAttributesXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getAttributesXML");
		return getAttributes(partyid, pos, test, xsl);
	}
	
	@GET
	@Path("/{partyid}/image")
	@Descriptions({ 
	   @Description(value = "List of party image URLs", target = DocTarget.METHOD),
	   @Description(value = "Image URL List", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getImagesByIdXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getImagesByIdXML");
		return getImagesById(partyid, pos, test, xsl);
	}

	@GET
	@Path("/{partyid}/text/{name}")
	@Descriptions({ 
	   @Description(value = "Get party named text", target = DocTarget.METHOD),
	   @Description(value = "Party text", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Text getTextByIdNameXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Text Name") @PathParam("name") String name,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Notes") @DefaultValue(Constants.BLANK) @QueryParam("notes") String notes,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getTextByIdNameXML");
		return setTextByIdName(partyid, name, notes, pos, xsl);
	}

	@GET
	@Path("/{partyid}/value")
	@Descriptions({ 
		@Description(value = "List of party key value pairs", target = DocTarget.METHOD),
		@Description(value = "Party Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getValuesXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getValuesXML");
		return getValues(partyid, pos, xsl);
	}

	@GET
	@Path("/{partyid}/value")
	@Descriptions({ 
		@Description(value = "List of party key value pairs", target = DocTarget.METHOD),
		@Description(value = "Party Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public KeyValues getKeyvaluesXML(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("PartyRest", "getKeyvaluesXML");
		return getKeyvalues(partyid, pos, xsl);
	}
	
}
