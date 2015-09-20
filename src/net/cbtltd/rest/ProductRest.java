/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.logging.ServiceTimer;
import net.cbtltd.rest.response.ItemsResponse;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Text;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/** 
 * The Class ProductRest implements REST service for products and properties.
 * 
 * See URL limits 	http://www.danrigsby.com/blog/index.php/2008/06/17/rest-and-max-url-size/
 * 					http://en.wikipedia.org/wiki/Query_string
 */

@Path("/product")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class ProductRest extends AbstractProductExt {

	@GET
	@Path("/list")
	@Descriptions({ 
		@Description(value = "List of product name ID pairs", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getNameIdsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsXML");
		return getNameIds(null, pos, rows, version, offline, xsl);
	}

	@GET
	@Path("/dynamic")
	@Descriptions({ 
		@Description(value = "List of dynamic pricing product name ID pairs", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getDynamicNameIdsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getDynamicNameIdsXML");
		return getDynamicNameIds(pos, xsl);
	}

	@GET
	@Path("/organization/{organizationid}")
	@Descriptions({ 
		@Description(value = "List of product name ID pairs", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getNameIdsXML(
			@Description("Organization ID") @DefaultValue("1000") @PathParam("organizationid") String organizationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsXML");
		return getNameIds(organizationid, pos, rows, version, offline, xsl);
	}

	@GET
	@Path("/attributes")
	@Descriptions({ 
		@Description(value = "List of product attributes", target = DocTarget.METHOD),
		@Description(value = "Product Attributes", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getAttributesXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getAttributesXML");
		return getAttributes(pos, xsl);
	}
	
	@GET
	@Path("/types")
	@Descriptions({ 
		@Description(value = "List of product types", target = DocTarget.METHOD),
		@Description(value = "Product Types", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getTypesXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getTypesXML");
		return getTypes(pos, xsl);
	}
	
	@GET
	@Path("/list/{type}")
	@Descriptions({ 
		@Description(value = "List of products by type", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getNameIdsByTypeXML(
			@Description("Product Type") @DefaultValue("Accommodation") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsByTypeXML");
		return getNameIdsByType(type, pos, rows, version, offline, xsl);
	}
	
	@GET
	@Path("/list/channel/{type}")
	@Descriptions({ 
		@Description(value = "List of products by type", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public ItemsResponse getListChannel(
			@Description("Product Type") @DefaultValue("Accommodation") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return super.getListOfProductsForChannel(type, pos, version, offline, xsl);
	}
	
	@GET
	@Path("/list/{type}/{locationid}")
	@Descriptions({ 
		@Description(value = "List of products of a type at a location", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getNameIdsByLocationXML(
			@Description("Product Type") @DefaultValue("Accommodation") @PathParam("type") String type,
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsByLocationXML");
		return getNameIdsByLocation(type, locationid, pos, xsl);
	}
	
	@GET
	@Path("/list/{locationid}/{distance}/{unit}")
	@Descriptions({ 
		@Description(value = "List of products near a location", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getNameIdsNearLocationXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Maximum Distance from Location") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsNearLocationXML");
		return getNameIdsNearLocation(locationid, distance, unit, pos, xsl);
	}
	
	@GET
	@Path("/list/{latitude}/{longitude}/{distance}/{unit}")
	@Descriptions({ 
		@Description(value = "List of products near to a position", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getNameIdsInAreaXML(
			@Description("Latitude of Position") @PathParam("latitude") Double latitude,
			@Description("Longitude of Position") @PathParam("longitude") Double longitude,
			@Description("Maximum Distance from Position") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getNameIdsInAreaXML");
		return getNameIdsInArea(latitude, longitude, distance, unit, pos, xsl);
	}
	
	@GET
	@Path("/{productid}")
	@Descriptions({ 
		@Description(value = "Product information", target = DocTarget.METHOD),
		@Description(value = "Product Information", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Property getProductNoLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getProductNoLanguage");
		return getProperty(productid, pos, language, test, version, xsl);
	}

	@GET
	@Path("/{productid}/{language}")
	@Descriptions({ 
		@Description(value = "Product description by language", target = DocTarget.METHOD),
		@Description(value = "Product Description", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property getProductLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Language Code") @DefaultValue(Language.EN) @PathParam("language") String language,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getProductLanguage");
		return getProperty(productid, pos, language, test, version, xsl);
	}

	@GET
	@Path("/{productid}/detail")
	@Descriptions({ 
		@Description(value = "Detailed product information", target = DocTarget.METHOD),
		@Description(value = "Product Information", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property getDetailXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("From date") @QueryParam("date") String date,
			@Description("To date") @QueryParam("toDate") String toDate,
			@Description("Currency") @QueryParam("currency") String currency,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getDetail(productid, pos, language, date, toDate, currency, test, version, xsl,true);
	}
	
	@GET
	@Path("/{productid}/fulldetail")
	@Descriptions({ 
		@Description(value = "Detailed product information", target = DocTarget.METHOD),
		@Description(value = "Product Information", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property getFullDetailXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("From date") @QueryParam("date") String date,
			@Description("To date") @QueryParam("toDate") String toDate,
			@Description("Currency") @QueryParam("currency") String currency,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getCompleteDetail(productid, pos, language, date, toDate, currency, test, version, xsl);
	}
	
	@GET
	@Path("/{productid}/attribute")
	@Descriptions({ 
		@Description(value = "List of OTA product attributes", target = DocTarget.METHOD),
		@Description(value = "Attribute List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Attributes getAttributesXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getAttributesXML");
		return getAttributes(productid, pos, test, version, xsl);
	}
	
	@GET
	@Path("/{productid}/image")
	@Descriptions({ 
		@Description(value = "List of product image URLs", target = DocTarget.METHOD),
		@Description(value = "Image URL List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getImagesByIdXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getImagesByIdXML");
		return getImagesById(productid, pos, test, version, xsl);
	}
	
	@GET
	@Path("/{productid}/pricelist")
	@Descriptions({ 
		@Description(value = "List of product prices", target = DocTarget.METHOD),
		@Description(value = "Price List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public PriceList getPriceListXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getPriceListXML");
		return getPriceList(productid, pos, test, version, xsl);
	}
	
	@GET
	@Path("/{productid}/rating")
	@Descriptions({ 
		@Description(value = "List of product ratings", target = DocTarget.METHOD),
		@Description(value = "Rating List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Ratings getRatingsXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getRatingsXML");
		return getRatings(productid, pos, test, version, xsl);
	}
	
	@GET
	@Path("/{productid}/review")
	@Descriptions({ 
		@Description(value = "List of product reviews", target = DocTarget.METHOD),
		@Description(value = "Review List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Reviews getReviewsXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getReviewsXML");
		return getReviews(productid, pos, test, version, xsl);
	}

	@GET
	@Path("/{productid}/value")
	@Descriptions({ 
		@Description(value = "List of product key value pairs", target = DocTarget.METHOD),
		@Description(value = "Product Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getValuesXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getValuesXML");
		return getValues(productid, pos, xsl);
	}

	@GET
	@Path("/{productid}/keyvalue")
	@Descriptions({ 
		@Description(value = "List of product key value pairs", target = DocTarget.METHOD),
		@Description(value = "Product Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public KeyValues getKeyValuesXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "getKeyValuesXML");
		return getKeyvalues(productid, pos, xsl);
	}

    @POST
    @Path("/")
	@Descriptions({ 
		@Description(value = "Upload property data", target = DocTarget.METHOD),
		@Description(value = "Product Record", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
    public static synchronized Property postProperty(
    		Property rq,
    		@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
    		@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "postProperty");
    	return setProperty(rq, pos, xsl);
    }
    
    //DEPRECATED ==================================================================================================
	@GET
	@Path("/upload/{name}/{type}/{unit}")
	@Descriptions({ 
		@Description(value = "Upload product data", target = DocTarget.METHOD),
		@Description(value = "Product ID", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setProductXML(
			@Description("Product Name") @PathParam("name") String name,
			@Description("Product Type") @PathParam("type") String type,
			@Description("Product Unit") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setProductXML");
		return setProperty(name, type, unit, pos, test, xsl);
	}

    @GET
	@Path("/upload/{name}/{ownerid}/{locationid}/{room}/{person}/{commission}/{discount}/{latitude}/{longitude}/{address}")
	@Descriptions({ 
		@Description(value = "Upload property data", target = DocTarget.METHOD),
		@Description(value = "Property Record", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setPropertyXML(
			@Description("Product Name") @PathParam("name") String name,
			@Description("Owner ID") @PathParam("ownerid") String ownerid,
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Room Count") @DefaultValue("1") @PathParam("room") Integer room,
			@Description("Person Count") @DefaultValue("2") @PathParam("person") Integer person,
			@Description("Manager Commission") @DefaultValue("20.0") @PathParam("commission") Double commission,
			@Description("Agent Discount") @DefaultValue("20.0") @PathParam("discount") Double discount,
			@Description("Latitude") @DefaultValue("0.0") @PathParam("latitude") Double latitude,
			@Description("Longitude") @DefaultValue("0.0") @PathParam("longitude") Double longitude,
			@Description("Street Address") @PathParam("address") String address,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setPropertyXML");
		return setProperty(name, ownerid, locationid, room, person, commission, discount, latitude, longitude, address, pos, test, xsl);
	}

	@GET
	@Path("/upload/{productid}/text/{type}/{language}/{notes}")
	@Descriptions({ 
		@Description(value = "Upload descriptive text", target = DocTarget.METHOD),
		@Description(value = "Product Text", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setTextLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Text Label") @PathParam("name") String name,
			@Description("Text Type") @PathParam("type") String type,
			@Description("Language Code") @DefaultValue(Language.EN) @PathParam("language") String language,
			@Description("Descriptive Text") @PathParam("notes") String notes,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setTextLanguage");
		return setText(productid, name, type, language, notes, pos, test, xsl);
	}

    @POST
    @Path("/upload/text")
	@Descriptions({ 
		@Description(value = "Upload descriptive text", target = DocTarget.METHOD),
		@Description(value = "Product Text", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
    public Property setPropertyXML(
    		Text rq,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setPropertyXML");
    	if (rq == null) {System.out.println("HELP");}
    	else {System.out.println(rq);}
		return setText(rq.getId(), rq.getName(), rq.getType(), rq.getLanguage(), rq.getNotes(), pos, test, xsl);		
    }

    @GET
	@Path("/upload/{productid}/text/{type}/{notes}")
	@Descriptions({ 
		@Description(value = "Upload descriptive text", target = DocTarget.METHOD),
		@Description(value = "Product Text", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setTextNoLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Text Label") @PathParam("name") String name,
			@Description("Text Type") @PathParam("type") String type,
			@Description("Descriptive Text") @PathParam("notes") String notes,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setTextNoLanguage");
		return setText(productid, name, type, language, notes, pos, test, xsl);		
	}

    @POST
    @Path("/upload/image")
	@Descriptions({ 
		@Description(value = "Upload product image(s)", target = DocTarget.METHOD),
		@Description(value = "Image Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
    public Items setImageLanguage(
    		Property rq,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setImageLanguage");

    	if (rq == null) {System.out.println("HELP");}
    	else {System.out.println(rq);}
    	
//    	if (rq == null || rq.getImages() == null || rq.getImages().getImage() == null) {return rq;}
    	Collection<String> images = rq.getImages().getImage();
    	StringBuffer sb = new StringBuffer();
    	for (String image : images) {sb.append(image.replace('/', '~')).append(",");}
		if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
    	System.out.println(sb.toString());
    	return setImages(rq.getProductid(), sb.toString(), Language.EN, "", pos, test, xsl);
    }

	@GET
	@Path("/upload/{productid}/image/{image_url}/{language}/{image_notes}")
	@Descriptions({ 
		@Description(value = "Upload product image(s)", target = DocTarget.METHOD),
		@Description(value = "Image Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items setImageLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Image URL (encoded)") @PathParam("image_url") String image_url,
			@Description("Language Code") @DefaultValue(Language.EN) @PathParam("language") String language,
			@Description("Image Notes") @PathParam("image_notes") String image_notes,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setImageLanguage");
		return setImages(productid, image_url, language, image_notes, pos, test, xsl);
	}

	@GET
	@Path("/upload/{productid}/image/{image_url}/{image_notes}")
	@Descriptions({ 
		@Description(value = "Upload product image(s)", target = DocTarget.METHOD),
		@Description(value = "Image Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items setImageNoLanguage(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Image URL (encoded)") @PathParam("image_url") String image_url,
			@Description("Image Notes") @PathParam("image_notes") String image_notes,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setImageNoLanguage");
		return setImages(productid, image_url, language, image_notes, pos, test, xsl);
	}

//    @POST
//    @Path("/upload/price")
//	@Descriptions({ 
//		@Description(value = "Upload product price", target = DocTarget.METHOD),
//		@Description(value = "Price Upload", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})	
//    public Price setPriceCurrency(
//    		Price rq,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//
//    	if (rq == null) {System.out.println("HELP");}
//    	else {System.out.println(rq);}
//		return setPrice(rq.getEntity(), rq.getFromdate(), rq.getTodate(), rq.getValue(), rq.getMinimum(), rq.getCurrency(), pos, test, xsl);			
//    }
//
//	@GET
//	@Path("/upload/{productid}/price/{fromdate}/{todate}/{value}/{minimum}/{currency}")
//	@Descriptions({ 
//		@Description(value = "Upload product price", target = DocTarget.METHOD),
//		@Description(value = "Price Upload", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})	
//	public Price setPriceCurrency(
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From Date") @PathParam("fromdate") Date fromdate,
//			@Description("To Date") @PathParam("todate") Date todate,
//			@Description("Price") @PathParam("value") Double value,
//			@Description("Minimum") @PathParam("minimum") Double minimum,
//			@Description("Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		return setPrice(productid, fromdate, todate, value, minimum, currency, pos, test, xsl);			
//	}
//
//	@GET
//	@Path("/upload/{productid}/price/{fromdate}/{todate}/{value}/{minimum}")
//	@Descriptions({ 
//		@Description(value = "Upload product price", target = DocTarget.METHOD),
//		@Description(value = "Price Upload", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})	
//	public Price setPriceNoCurrency(
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From Date") @PathParam("fromdate") Date fromdate,
//			@Description("To Date") @PathParam("todate") Date todate,
//			@Description("Price") @PathParam("value") Double value,
//			@Description("Minimum") @PathParam("minimum") Double minimum,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Currency Code") @DefaultValue(Constants.USD) @QueryParam("currency") String currency,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		return setPrice(productid, fromdate, todate, value, minimum, currency, pos, test, xsl);
//	}
	
    @POST
    @Path("/upload/attributelist")
	@Descriptions({ 
		@Description(value = "Upload product price", target = DocTarget.METHOD),
		@Description(value = "Price Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
    public Attributes setAttributeXML(
    		Property rq,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setAttributeXML");

    	if (rq == null) {System.out.println("HELP");}
    	else {System.out.println(rq);}
    	
		return setAttribute(rq.getProductid(), rq.getAttributelist(), pos, test, xsl);
    }

	@GET
	@Path("/upload/{productid}/attributelist/{attributelist}")
	@Descriptions({ 
		@Description(value = "Upload product attribute(s)", target = DocTarget.METHOD),
		@Description(value = "Attribute Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Attributes setAttributeXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Attribute Code(s)") @PathParam("attributelist") String attributelist,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ProductRest", "setAttributeXML");
		return setAttribute(productid, attributelist, pos, test, xsl);
	}
}
