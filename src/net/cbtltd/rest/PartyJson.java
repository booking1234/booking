package net.cbtltd.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.shared.Party;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/** 
 * The Class PartyRest implements REST service for parties.
 */
@Path("/party")
@Produces(MediaType.APPLICATION_JSON)
public class PartyJson extends AbstractParty {

	@GET
	@Path("/list")
	@Descriptions({ 
		   @Description(value = "List of party name ID pairs", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})
	public Items getNameIdsJSON(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date")  @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getTypesJSON(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getTypes(pos, xsl);
	}

	@GET
	@Path("/list/{type}/{numrows}")
	@Descriptions({ 
		   @Description(value = "List of parties by type", target = DocTarget.METHOD),
		   @Description(value = "Party List", target = DocTarget.RETURN),
		   @Description(value = "Request", target = DocTarget.REQUEST),
		   @Description(value = "Response", target = DocTarget.RESPONSE),
		   @Description(value = "Resource", target = DocTarget.RESOURCE)
		})	
	public Items getNameIdsByTypeJSON(
			@Description("Party Type") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Number of Rows") @DefaultValue("1000") @PathParam("rows") Integer rows,
			@Description("Optional Version Date")  @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getNameIdsByType(type, pos, rows, version, xsl);
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
	public Party getByIdJSON(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Attributes getAttributesJSON(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getImagesByIdJSON(
			@Description("Party ID") @PathParam("partyid") String partyid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getImagesById(partyid, pos, test, xsl);
	}
	
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
}
