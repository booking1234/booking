package net.cbtltd.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;

public class AbstractProductExt extends AbstractProduct {
	/**
	 * Sets the product.
	 *
	 * @param name the name
	 * @param type the type
	 * @param unit the unit
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the product
	 */
	protected Property setProperty(
			String name,
			String type,
			String unit,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/upload/" + name + "/" + type + "/" + unit + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Property result = new Property();
		try {
			Party supplier = Constants.getParty(sqlSession, pos);
			Product product = new Product();
			product.setLocationid(supplier.getLocationid());
			product.setOwnerid(supplier.getId());
			product.setSupplierid(supplier.getId());
			product.setName(name);
			product.setState(Product.CREATED);
			product.setType(type);
			product.setCurrency(supplier.getCurrency());
			product.setUnit(unit);

			Product exists = sqlSession.getMapper(ProductMapper.class).exists(product);
			if (exists != null) {throw new ServiceException(Error.product_exists, name);}

			if (test) {
				result.setLocationid(supplier.getLocationid());
				result.setOwnerid(supplier.getId());
				result.setSupplierid(supplier.getId());
				result.setName(name);
				result.setState(Product.CREATED);
				result.setType(type);
				result.setCurrency(supplier.getCurrency());
				result.setUnit(unit);
			}
			else {
				sqlSession.getMapper(ProductMapper.class).create(product);
				RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, supplier.getId(), product.getId());
				sqlSession.commit();
				MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Product, product);
				result = sqlSession.getMapper(ProductMapper.class).property(product.getId());
			}
		}
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			sqlSession.rollback();
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Sets the property.
	 *
	 * @param name the name
	 * @param ownerid the ownerid
	 * @param locationid the locationid
	 * @param room the room
	 * @param person the person
	 * @param commission the commission
	 * @param discount the discount
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param address the address
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the product
	 */
	protected Property setProperty(
			String name,
			String ownerid,
			String locationid,
			Integer room,
			Integer person,
			Double commission,
			Double discount,
			Double latitude,
			Double longitude,
			String address,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/upload/" + name + "/" + ownerid + "/" + locationid + "/" + room + "/" + person + "/" + commission + "/" + discount + "/" + latitude + "/" + longitude + "/" + address + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Property result = new Property();
		try {
			Party supplier = Constants.getParty(sqlSession, pos);
			Product product = new Product();
			product.setName(name);
			product.setLocationid(locationid);
			product.setOwnerid(ownerid);
			product.setSupplierid(supplier.getId());
			product.setState(Product.SUSPENDED);
			product.setType(Product.Type.Accommodation.name());
			product.setPerson(person);
			product.setRoom(room);
			product.setCommission(commission);
			product.setDiscount(discount);
			product.setAltitude(0.0);
			product.setLatitude(latitude);
			product.setLongitude(longitude);
			product.setPhysicaladdress(address);
			product.setRank(0.0);
			product.setRating(6);
			product.setUnit(Unit.DAY);

			Product exists = sqlSession.getMapper(ProductMapper.class).exists(product);
			if (exists != null) {throw new ServiceException(Error.product_exists, name);}

			if (product.noLatLng() || product.zeroLatLng()) {
				Location location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
				if (location == null || location.noLatLng() || location.zeroLatLng()) {throw new ServiceException(Error.product_position, name);}
				product.setAltitude(location.getAltitude());
				product.setLatitude(location.getLatitude());
				product.setLongitude(location.getLongitude());
			}
			if (test) {
				result.setName(name);
				result.setLocationid(locationid);
				result.setOwnerid(ownerid);
				result.setSupplierid(supplier.getId());
				result.setState(Product.SUSPENDED);
				result.setType(Product.Type.Accommodation.name());
				result.setPerson(person);
				result.setRoom(room);
				result.setAltitude(0.0);
				result.setLatitude(latitude);
				result.setLongitude(longitude);
				result.setRank(0.0);
				result.setUnit(Unit.DAY);
			}
			else {
				sqlSession.getMapper(ProductMapper.class).create(product);
				RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, supplier.getId(), product.getId());
				sqlSession.commit();
				MonitorService.update(sqlSession, Data.Origin.XML_JSON, NameId.Type.Product, product);
				result = sqlSession.getMapper(ProductMapper.class).property(product.getId());
			}
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			sqlSession.rollback();
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Sets the text.
	 *
	 * @param productid the productid
	 * @param name the name or label fof the text
	 * @param type the type
	 * @param language the language
	 * @param notes the notes
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the product
	 */
	protected Property setText(
			String productid,
			String name,
			String type,
			String language,
			String notes,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/upload/" + productid + "/text/" + name + "/" + type + "/" + notes + "?pos=" + pos + "&language=" + language + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Property result = new Property();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			if (productid == null || productid.isEmpty()) {throw new ServiceException(Error.product_id, productid);}
			if (type == null || type.isEmpty()) {throw new ServiceException(Error.text_type, productid);}
			Text text = new Text();
			if (Text.Code.Private.name().equalsIgnoreCase(type)) {text.setId(organization.getId() + NameId.Type.Product + productid);}
			else {text.setId(NameId.Type.Product + productid + type);}
			text.setName(name);
			text.setLanguage(language);
			text.setNotes(notes);
			text.setState(Text.State.Created.name());
			text.setDate(new Date());
			if (test) {
				result.setId(productid);
				result.setLanguage(language);
				result.setDescription(notes);				
			}
			else {
				TextService.update(sqlSession, text);
				result.setId(productid);
				result.setLanguage(language);
				result.setDescription(TextService.notes(sqlSession, result.getPublicId(), result.getLanguage()));
				sqlSession.commit();
			}
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			sqlSession.rollback();
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Sets the image.
	 *
	 * @param productid the productid
	 * @param image_url the image_url
	 * @param language the language
	 * @param image_notes the image_notes
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the items
	 */
	protected Items setImages(
			String productid,
			String image_url,
			String language,
			String image_notes,
			String pos,
			Boolean test,
			String xsl) {
		Date timestamp = new Date();
		String message = "/product/upload/" + productid + "/image/" + image_url + "/" + image_notes + "?pos=" + pos + "&language=" + language + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null; //new Product();

		InputStream in = null;
		FileOutputStream out = null;

		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			if (productid == null || productid.isEmpty()) {throw new ServiceException(Error.product_id, productid);}
			if (image_url == null || image_url.isEmpty()) {throw new ServiceException(Error.product_upload, productid);}

			String[] url_list = image_url.split(",");
			String[] notes_list = image_notes.split(",");
			boolean hasNotes = notes_list != null && notes_list.length > 0;
			if (hasNotes && url_list.length != notes_list.length) {throw new ServiceException(Error.product_upload, "because URLs and notes are not equal");}
			if (!Language.EN.equalsIgnoreCase(language) && !Language.isTranslatable(language)) {throw new ServiceException(Error.product_upload, "because the language code " + language + " cannot be translated");}

			ArrayList<NameId> images = new ArrayList<NameId>();
			for (int index = 0; index < url_list.length; index++) {
				images.add(new NameId(hasNotes ? notes_list[index] : null, url_list[index].replace('~', '/')));
			}
			UploadFileService.uploadImages(sqlSession, NameId.Type.Product, productid, language, images);
			result = new Items(NameId.Type.Product.name(), productid, "Image", null, sqlSession.getMapper(TextMapper.class).imagesbynameid(new NameId(NameId.Type.Product.name(), productid)), xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			sqlSession.rollback();
			result = new Items(NameId.Type.Product.name(), productid, "Image", message + " " + x.getMessage(), null, xsl);
		}
		finally {
			sqlSession.close();
			try {
				if (in != null) {in.close();}
				if (out != null) {out.close();}
			} catch(IOException x) {}
		}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Sets the attribute.
	 *
	 * @param productid the productid
	 * @param attribute the attribute
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the attributes
	 */
	protected Attributes setAttribute(
			String productid,
			String attribute,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/upload/" + productid + "/attribute/" + attribute + "?pos=" + pos + "&test=" + test + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Attributes result = new Attributes();
		try {
			Constants.decryptPos(pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null) {throw new ServiceException(Error.product_id, productid);}

			Collection<Attribute> attributes = new ArrayList<Attribute>();
			ArrayList<String> kvs = new ArrayList<String>(Arrays.asList(attribute.split(",")));
			HashMap<String, ArrayList<String>> attributeMap;
			if (test) {
				attributeMap = new HashMap<String, ArrayList<String>>();
				for (String kv : kvs) {
					String key = kv.substring(0, 3);
					String value = kv.substring(3);
					if (!attributeMap.containsKey(key)) {attributeMap.put(key, new ArrayList<String>());}
					ArrayList<String> values = attributeMap.get(key);
					values.add(value);
				}
			}
			else {
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, productid, kvs);
				sqlSession.commit();
				attributeMap = RelationService.readMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), Attribute.ACCOMMODATION_SEARCH);
			}
			for (String key : attributeMap.keySet()) {
				ArrayList<String> values = new ArrayList<String>();
				for (String value : attributeMap.get(key)) {values.add(getValueName(sqlSession, value));}
				if (attributeMap.get(key).size() > 0) {attributes.add(new Attribute(getValueName(sqlSession, key), values));}
			}
			result = new Attributes(attributes);
		} 
		catch(Throwable x) {
			sqlSession.rollback();
			LOG.error(message + "\n" + x.getMessage());
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

}

///**
//* Sets the price.
//*
//* @param productid the productid
//* @param fromdate the fromdate
//* @param todate the todate
//* @param value the value
//* @param minimum the minimum
//* @param currency the currency
//* @param pos the pos
//* @param test the test
//* @param xsl the xsl
//* @return the price
//*/
//protected Price setPrice(
//		String productid,
//		Date fromdate,
//		Date todate,
//		Double value,
//		Double minimum,
//		String currency,
//		String pos,
//		Boolean test,
//		String xsl) {
//	Date timestamp = new Date();
//	String message = "/product/upload/" + productid + "/price/" + fromdate + "/" + todate + "/" + value + "/" + minimum + "?pos=" + pos + "&currency=" + currency + "&test=" + test + "&xsl=" + xsl;
//	LOG.debug(message);
//	SqlSession sqlSession = RazorServer.openSession();
//	Price result = new Price();
//	try {
//		Party organization = Constants.getParty(sqlSession, pos);
//		Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
//		if (product == null) {throw new ServiceException(Error.product_id, productid);}
//
//		net.cbtltd.shared.Price price = new net.cbtltd.shared.Price();
//		price.setEntitytype(NameId.Type.Product.name());
//		price.setEntityid(productid);
//		price.setPartyid(organization.getId());
//		price.setDate(fromdate);
//		price.setTodate(todate);
//		
//		net.cbtltd.shared.Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
//		if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
//		else {price = exists;}
//
//		price.setCurrency(currency);
//		price.setMinimum(minimum);
//		price.setName(net.cbtltd.shared.Price.RACK_RATE);
//		price.setOrganizationid(organization.getId());
//		price.setQuantity(0.0);
//		price.setState(net.cbtltd.shared.Price.CREATED);
//		price.setType(NameId.Type.Reservation.name());
//		price.setUnit(Unit.DAY);
//		price.setValue(value);
//		sqlSession.getMapper(PriceMapper.class).update(price);
//		
//		sqlSession.commit();
//		MonitorService.update(sqlSession, NameId.Type.Price, price);
//
//		result = new Price(
//				organization.getId(), 
//				NameId.Type.Product.name(), 
//				productid, 
//				price.getName(),
//				price.getDate(), 
//				price.getTodate(), 
//				Unit.DAY, 
//				currency, 
//				value,
//				minimum
//			);
//	} 
//	catch(Throwable x) {
//		LOG.error(message + "\n" + x.getMessage());
//		sqlSession.rollback();
//		result.setMessage(message + " " + x.getMessage());
//	}
//	finally {sqlSession.close();}
//	result.setXsl(xsl);
//	LOG.debug(result);
//	MonitorService.monitor(message, timestamp);
//	return result;
//}