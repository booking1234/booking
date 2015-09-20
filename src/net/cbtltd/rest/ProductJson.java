/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.response.ItemsResponse;
import net.cbtltd.rest.response.PropertyResponse;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;

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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductJson extends AbstractProductExt {

	@GET
	@Path("/list")
	@Descriptions({ 
		@Description(value = "List of product name ID pairs", target = DocTarget.METHOD),
		@Description(value = "Product List", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getNameIdsJSON(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getNameIds(null, pos, rows,  version, offline, xsl);
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
	public Items getNameIdsJSON(
			@Description("Organization ID") @DefaultValue("1000") @PathParam("organizationid") String organizationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getAttributesJSON(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getTypesJSON(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getNameIdsByTypeJSON(
			@Description("Product Type") @DefaultValue("Accommodation") @PathParam("type") String type,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Number of Rows") @DefaultValue("1000") @QueryParam("rows") Integer rows,
			@Description("Optional Version Date") @QueryParam("version") String version,
			@Description("Optional Offline Flag") @DefaultValue("true") @QueryParam("offline") Boolean offline,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getNameIdsByLocationJSON(
			@Description("Product Type") @DefaultValue("Accommodation") @PathParam("type") String type,
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getNameIdsNearLocationJSON(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Maximum Distance from Location") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getNameIdsInAreaJSON(
			@Description("Latitude of Position") @PathParam("latitude") Double latitude,
			@Description("Longitude of Position") @PathParam("longitude") Double longitude,
			@Description("Maximum Distance from Position") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
		return getProperty(productid, pos, language, test, version,xsl);
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
	public Property getDetailJSON(
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
	public Property getFullDetailJSON(
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
	@Path("/{productid}/propertydetail")
	@Descriptions({ 
		@Description(value = "Detailed product information", target = DocTarget.METHOD),
		@Description(value = "Product Information", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public PropertyResponse getPropertyDetailJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Language Code") @DefaultValue(Language.EN) @QueryParam("language") String language,
			@Description("From date") @QueryParam("date") String date,
			@Description("To date") @QueryParam("toDate") String toDate,
			@Description("Currency") @QueryParam("currency") String currency,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getPropertyDetail(productid, pos, language, date, toDate, currency, test, version, xsl);
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
	public Attributes getAttributesJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Items getImagesByIdJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public PriceList getPriceListJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Ratings getRatingsJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
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
	public Reviews getReviewsJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional Download Date") @QueryParam("version") String version,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getReviews(productid, pos, test, version, xsl);
	}
	
	@GET
	@Path("/upload/{name}/{type}/{unit}")
	@Descriptions({ 
		@Description(value = "Upload product data", target = DocTarget.METHOD),
		@Description(value = "Product ID", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setProductJSON(
			@Description("Product Name") @PathParam("name") String name,
			@Description("Product Type") @PathParam("type") String type,
			@Description("Product Unit") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return setProperty(name, type, unit, pos, test, xsl);
	}

//    @POST
//    @Path("/upload")
//	@Descriptions({ 
//		@Description(value = "Upload property data", target = DocTarget.METHOD),
//		@Description(value = "Product Record", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})	
//    public Product setProperty(
//    		Product rq,
//    		@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//    		@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//    		@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//
//    	if (rq == null) {System.out.println("HELP");}
//    	else {System.out.println(rq);}
//    	
//    	Product rs = setProperty(
//    			rq.getName(),
//    			rq.getOwnerid(), 
//    			rq.getLocationid(),
//    			rq.getRoom(),
//    			rq.getPerson(),
//    			rq.getCommission(),
//    			rq.getDiscount(),
//    			rq.getLatitude(),
//    			rq.getLongitude(),
//    			rq.getPhysicaladdress(),
//    			pos, test, xsl);
//		return rs;
//    }

    @GET
	@Path("/upload/{name}/{ownerid}/{locationid}/{room}/{person}/{commission}/{discount}/{latitude}/{longitude}/{address}")
	@Descriptions({ 
		@Description(value = "Upload product data", target = DocTarget.METHOD),
		@Description(value = "Product Record", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Property setPropertyJSON(
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
		return setProperty(name, ownerid, locationid, room, person, commission, discount, latitude, longitude, address, pos, test, xsl);
	}
	
	@GET
	@Path("/upload/{productid}/text/{name}/{type}/{notes}")
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
		return setText(productid, name, type, language, notes, pos, test, xsl);		
	}

	@GET
	@Path("/upload/{productid}/text/{name}/{type}/{language}/{notes}")
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
		return setText(productid, name, type, language, notes, pos, test, xsl);
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
		return setImages(productid, image_url, language, image_notes, pos, test, xsl);
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
		return setImages(productid, image_url, language, image_notes, pos, test, xsl);
	}

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

	@GET
	@Path("/upload/{productid}/attribute/{attribute}")
	@Descriptions({ 
		@Description(value = "Upload product attribute(s)", target = DocTarget.METHOD),
		@Description(value = "Attribute Upload", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Attributes setAttributeJSON(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("Attribute Code(s)") @PathParam("attribute") String attribute,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return setAttribute(productid, attribute, pos, test, xsl);
	}
	
}

//	@GET
//	@Path("/bad")
//	public Response getBadRequest() {
//		return Response.status(Status.BAD_REQUEST).build();
//	}
//}
