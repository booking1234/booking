/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.flipkey.Reservation;
import net.cbtltd.rest.flipkey.Reservations;
import net.cbtltd.rest.flipkey.ScheduleItem;
import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;
import net.cbtltd.rest.response.ItemsResponse;
import net.cbtltd.rest.response.PropertyResponse;
import net.cbtltd.server.LicenseService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.TextService;
import net.cbtltd.server.UploadFileService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.ContractMapper;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.api.PropertyMinStayMapper;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.KeyValue;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.MinStay;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.server.config.RazorHostsConfig;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;

/** 
 * The Class ProductRest implements REST service for products and properties.
 * 
 * See URL limits 	http://www.danrigsby.com/blog/index.php/2008/06/17/rest-and-max-url-size/
 * 					http://en.wikipedia.org/wiki/Query_string
 */

public abstract class AbstractProduct {

	protected static final Logger LOG = Logger.getLogger(AbstractProduct.class.getName());
	private static ArrayList<NameId> keyvalues = null;
	
	/**
	 * Check access and throw exception if not allowed.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the POS code for access.
	 * @param productid the product ID
	 * @param test the flag is true if this is a test.
	 * @param version the date of last download.
	 * @return the product if successful.
	 * @throws Throwable the exception
	 */
	private static final Property getProperty (
			SqlSession sqlSession,
			String pos,
			String productid,
			Boolean test,
			String version
		) throws Throwable {

		Party party = Constants.getParty(sqlSession, pos);
		String partyid = party.getId();
		//String partyid = Constants.decryptPos(pos);

		Property property = sqlSession.getMapper(ProductMapper.class).property(productid);
		if (property == null) {throw new ServiceException(Error.product_id, productid);}
		if (property.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid);}
		if (property.hasState(Constants.UNLICENSED)) {property = sqlSession.getMapper(ProductMapper.class).property(Constants.UNLICENSED_ID);}
		else if (!property.hasState(Constants.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}
		
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(property.getSupplierid()));
		String currency = PartyService.checkMybookingpalCurrency(property.getCurrency(), propertyManagerInfo);
		property.setCurrency(currency);
		
		LicenseService.checkAccess(sqlSession, property.getSupplierid(), partyid, productid, License.Type.JSON_XML, License.PRODUCT_WAIT);

		Date date = Constants.parseDate(version);
		if (!test && version == null && RelationService.loaded(sqlSession, Downloaded.PRODUCT_DOWNLOAD, productid, partyid)) {throw new ServiceException(Error.data_unchanged, productid);}
		if (version != null && MonitorService.getLastUpdate(sqlSession, NameId.Type.Product, productid).before(date)) {throw new ServiceException(Error.data_unchanged, productid);}
		RelationService.load(sqlSession, Downloaded.PRODUCT_DOWNLOAD, productid, partyid);

		LicenseService.setAccess(partyid, System.currentTimeMillis());

		return property;
	}

		private static final Property getPropertyForXMLFeed (
			SqlSession sqlSession,
			String pos,
			String productid,
			Boolean test,
			String version
		) throws Throwable {

		//Party party = Constants.getParty(sqlSession, pos);
		//String partyid = party.getId();
		//String partyid = Constants.decryptPos(pos);

		Property property = sqlSession.getMapper(ProductMapper.class).property(productid);
		if (property == null) {throw new ServiceException(Error.product_id, productid);}
		if (property.notType(Product.Type.Accommodation.name())) {throw new ServiceException(Error.product_type, productid);}
		else if (!property.hasState(Constants.CREATED)) {throw new ServiceException(Error.product_inactive, productid);}
		return property;
	}

	
	protected  synchronized Property getProperty(
			String productid,
			String pos,
			String language,
			Boolean test,
			String version,
			String xsl) { 

		final   SqlSession sqlSession =  RazorServer.openSession();
		Property property = getProperty(productid, pos, language, test, version, xsl , sqlSession);
		sqlSession.commit();
		sqlSession.close();
		return property;	
	}
	
	/**
	 * Gets the product.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param language the language
	 * @param test the test
	 * @param xsl the xsl
	 * @return the product
	 */
	protected synchronized Property getProperty(
			String productid,
			String pos,
			String language,
			Boolean test,
			String version,
			String xsl,
			SqlSession sqlSession) {
		
		Date timestamp = new Date();
		String message = "/product/" + productid + "?pos=" + pos + "&language=" + language + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		//SqlSession sqlSession = RazorServer.openSession();
		Property property = null;
		try {
			property = getProperty(sqlSession, pos, productid, test, version);
			property.setWebaddress(RazorHostsConfig.getProductUrl() + productid + "&pos=" + pos);
			Location location = sqlSession.getMapper(LocationMapper.class).read(property.getLocationid());
			property.setCity(location == null ? null : location.getName());
			property.setRegion(location == null ? null : location.getRegion());
			property.setCountry(location == null ? null : location.getCountry());
			property.setLanguage(language);
			property.setDescription(TextService.notes(sqlSession, property.getPublicId(), property.getLanguage()));
			property.setCheckin(TextService.notes(sqlSession, property.getCheckinId(), property.getLanguage()));
			property.setContents(TextService.notes(sqlSession, property.getContentsId(), property.getLanguage()));
			property.setOptions(TextService.notes(sqlSession, property.getOptionsId(), property.getLanguage()));
			property.setConditions(TextService.notes(sqlSession, property.getConditionsId(), property.getLanguage()));
			property.setXsl(xsl);
			String propertyAddress = ReservationService.getPropertyLocation(sqlSession, property);
			property.setPhysicaladdress(propertyAddress == null ? "" : propertyAddress);
			//sqlSession.commit(); //
			
		}
		catch (Throwable x) {
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			property = new Property();
			property.setMessage(message + " " + x.getMessage());
			property.setXsl(xsl);
		}
		//finally {sqlSession.close();}
		LOG.debug(property);
    	MonitorService.monitor(message, timestamp);
		return property;
	}

	/**
	 * Gets the product detail.
	 *
	 * @param sqlSession the current SQL session.
	 * @param productid the productid
	 * @param pos the pos
	 * @param language the language
	 * @return the product detail
	 * @throws ServiceException the service exception
	 */
	protected synchronized Property getPropertyDetail(
			SqlSession sqlSession, 
			String productid, 
			String pos, 
			String language,
			String dateString,
			String toDateString,
			String currency,
			Integer countImages,
			Boolean test,
			String version
		) throws Throwable {
		
	
		Party agent = Constants.getParty(sqlSession, pos);
		Property property = getProperty(sqlSession, pos, productid, test, version);
		String propertySupplierid = property.getSupplierid(); 
		JSONService.getPartyWithPMCheck(sqlSession, pos, propertySupplierid);
		property.setLanguage(language);
		property.setWebaddress(null); 
	//	property.setWebaddress(HasUrls.PRODUCT_URL + productid + "&pos=" + pos); removed in API since it is not corret. 
		String description = TextService.notes(sqlSession, property.getPublicId(), property.getLanguage());
		property.setDescription(description == null ? "" : description);
		property.setContents(TextService.notes(sqlSession, property.getContentsId(), property.getLanguage()));
		property.setOptions(TextService.notes(sqlSession, property.getOptionsId(), property.getLanguage()));
		property.setCheckin(TextService.notes(sqlSession, property.getCheckinId(), property.getLanguage()));
		property.setConditions(TextService.notes(sqlSession, property.getConditionsId(), property.getLanguage()));
		String textid = NameId.Type.Party.name() + property.getSupplierid() + Text.Code.Contract.name();
		property.setTerms(TextService.notes(sqlSession, textid, property.getLanguage()));
		property.setMinstay("1");

		HashMap<String, ArrayList<String>> attributeMap = RelationService.readAttributesMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, property.getId());
		
		Collection<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("", new ArrayList<String>()));
		for (String key : attributeMap.keySet()) {
			ArrayList<String> values = new ArrayList<String>();
			for (String value : attributeMap.get(key)) {
				String name = getValueName(sqlSession, value);
				if (name != null && !name.isEmpty()) {
					values.add(name);
				}
			}
			if (attributeMap.get(key).size() > 0 && values.size() > 0) {
				attributes.add(new Attribute(getValueName(sqlSession, key),
						values));
			}
		}
		property.setAttributes(new Attributes(attributes));
		
		Double agentCommission = 0.0;
		if (agent != null){
			Contract contract = new Contract(NameId.Type.Reservation.name(), property.getSupplierid(), agent.getId());
			contract = sqlSession.getMapper(ContractMapper.class).readbyexample(contract);
			if (contract == null) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
				agentCommission =  (product == null || product.getDiscount() == null) ? 0.0 : product.getDiscount();
			}
			else if (contract.hasState(Contract.SUSPENDED)) {agentCommission = 0.0;}
			else agentCommission = Double.valueOf(contract.getDiscount());
		}

		property.setDiscount(agentCommission);
		
		PropertyManagerInfo pmInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(propertySupplierid));
		if(pmInfo != null) {
			property.setInquiryOnly(pmInfo.isInquireOnly());
		} else {
			property.setInquiryOnly(true);
		}
		
		List<String> imageids = ImageService.getProductRegularImageURLs(sqlSession, property.getId(), countImages);
		property.setImages(new Images(imageids));
		
		//property.setPricelist(new PriceList(createPricelist(sqlSession, property, date, toDate, currency)));

		Date date = Constants.parseDate(dateString);
		Date toDate = Constants.parseDate(toDateString);
		
		Price action = new Price();
		action.setPartyid(property.getSupplierid());
		action.setEntitytype(NameId.Type.Product.name());
		action.setEntityid(property.getId());
		action.setDate(date);
		action.setTodate(toDate);
		action.setCurrency(currency);
		
//		List<Price> pricetable = new ArrayList<Price>();		
		
		if (PartyIds.PARTY_INTERHOME_ID.equals(property.getSupplierid()) || PartyIds.PARTY_RENTALS_UNITED_ID.equals(property.getSupplierid())){
			MinStay minstayAction = new MinStay(property.getSupplierid(), productid, Constants.parseDate(dateString), null, 0);					
			minstayAction = sqlSession.getMapper(PropertyMinStayMapper.class).readbyexample(minstayAction);
			if (minstayAction != null && minstayAction.getValue() != null && minstayAction.getValue() > 0){
				property.setMinstay(String.valueOf(minstayAction.getValue()));
			}
//			pricetable = sqlSession.getMapper(PriceMapper.class).getpricetable(action);
		}else {
			Price checkInPrice = null;
			if (property.IsUseonepricerow()){
				checkInPrice = sqlSession.getMapper(PriceMapper.class).readexactmatch(action);
			}else{
				checkInPrice = sqlSession.getMapper(PriceMapper.class).getpropertydetailcheckinprice(action);
			}
			if (checkInPrice != null && checkInPrice.getMinStay() != null && checkInPrice.getMinStay() > 0){
				property.setMinstay(checkInPrice.getMinStay().toString());
			}
//			pricetable = sqlSession.getMapper(PriceMapper.class).restread(action);
		}
		
//		pricetable = CurrencyConverter.convertCurrency(sqlSession, pricetable, currency);	
		
//		property.setPricetable(new PriceTable(pricetable));
		
		//action.setEntitytype(NameId.Type.Mandatory.name());
		//ArrayList<Price> mandatory = sqlSession.getMapper(PriceMapper.class).restread(action);
		//property.setMandatory(new PriceTable(mandatory));
		
	//	action.setEntitytype(NameId.Type.Feature.name());
	//	ArrayList<Price> feature = sqlSession.getMapper(PriceMapper.class).restread(action);
	//	property.setFeature(new PriceTable(feature));
		
		//Reservation will be moved to its own API call. 
    //  ArrayList<ScheduleItem> items = sqlSession.getMapper(ReservationMapper.class).flipkeyavailabilityproduct(productid);
	//	Collection<Reservation> reservation = new ArrayList<Reservation>();
	//	if(items!=null&&!items.isEmpty()){
	//		for (ScheduleItem item : items) {reservation.add(new Reservation(item));}
	//		property.setReservations( new Reservations(reservation, Constants.NO_XSL) );
	//	}
		
		//Get Accepted credit Card types.
		PropertyManagerSupportCC propertyManagerSupportCC =  sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(Integer.parseInt(propertySupplierid));
		property.setSupportedcreditcardtypes(propertyManagerSupportCC);
		
		//Get cancellation policy
		/*List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(Integer.valueOf(propertySupplierid));
		property.setCancellationrules(rules);*/
	
		return property;
	}
	
	/** 
	 * Gets the origin razor-cloud product detail.
	 * @param productid
	 * @param pos
	 * @param language
	 * @param date
	 * @param toDate
	 * @param currency
	 * @param test
	 * @param version
	 * @param xsl
	 * @return
	 * Created function to have control over sqlsession commits. This is needed for XML writer. 
	 */
	protected Property getDetail(
			String productid,
			String pos,
			String language,
			String date,
			String toDate,
			String currency,
			Boolean test,
			String version,
			String xsl,
			boolean sourceAPI){
	
		Date timestamp = new Date();				
		Property    property;
		
		String debugMessage = "/product/" + productid + "/detail?pos=" + pos + "&language=" + language + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(debugMessage);
		
		SqlSession	sqlSession = RazorServer.openSession();
		
		try {
			property = getOriginDetail(sqlSession, productid, pos, language, test, version);
			property.setXsl(xsl);
		}
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(debugMessage + "\n" + x.getMessage());}
			property = new Property();
			property.setMessage(debugMessage + " " + x.getMessage());
			property.setXsl(xsl);
		}				
		if(sourceAPI){
			String message = "/product/" + productid + "/detail?pos=" + pos + "&language=" + language + "&xsl=" + xsl + "&test=" + test;
			MonitorService.monitor(message, timestamp);	
		}
		sqlSession.close();
		return  property;
	}

	
	/**
	 * Gets the origin razor-cloud product detail.
	 *
	 * @param sqlSession the current SQL session.
	 * @param productid the productid
	 * @param pos the pos
	 * @param language the language
	 * @return the product detail
	 * @throws ServiceException the service exception
	 */
	protected synchronized Property getOriginDetail(
			SqlSession sqlSession, 
			String productid, 
			String pos, 
			String language,
			Boolean test,
			String version
		) throws Throwable {

		Property property = getProperty(sqlSession, pos, productid, test, version);
		property.setLanguage(language);
		property.setWebaddress(RazorHostsConfig.getProductUrl() + productid + "&pos=" + pos);
		property.setDescription(TextService.notes(sqlSession, property.getPublicId(), property.getLanguage()));
		property.setContents(TextService.notes(sqlSession, property.getContentsId(), property.getLanguage()));
		property.setOptions(TextService.notes(sqlSession, property.getOptionsId(), property.getLanguage()));
		property.setCheckin(TextService.notes(sqlSession, property.getCheckinId(), property.getLanguage()));
		property.setConditions(TextService.notes(sqlSession, property.getConditionsId(), property.getLanguage()));
		String textid = NameId.Type.Party.name() + property.getSupplierid() + Text.Code.Contract.name();
		property.setTerms(TextService.notes(sqlSession, textid, property.getLanguage()));
		
		HashMap<String, ArrayList<String>> attributeMap = RelationService.readMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, property.getId(), Attribute.ACCOMMODATION_SEARCH);
		Collection<Attribute> attributes = new ArrayList<Attribute>();
		for (String key : attributeMap.keySet()) {
			ArrayList<String> values = new ArrayList<String>();
			for (String value : attributeMap.get(key)) {values.add(getValueName(sqlSession, value));}
			if (attributeMap.get(key).size() > 0) {attributes.add(new Attribute(getValueName(sqlSession, key), values));}
		}
		property.setAttributes(new Attributes(attributes));

		List<String> imageids = ImageService.getProductRegularImageURLs(sqlSession, property.getId());
		Images images = new Images(imageids);
		images.sortImages();
		property.setImages(images);
		
		property.setPricelist(new PriceList(createPricelist(sqlSession, property, null, null, null)));

		Price action = new Price();
		action.setPartyid(property.getSupplierid());
		action.setEntitytype(NameId.Type.Product.name());
		action.setEntityid(property.getId());
		ArrayList<Price> pricetable = sqlSession.getMapper(PriceMapper.class).restread(action);
		property.setPricetable(new PriceTable(pricetable));
		
		action.setEntitytype(NameId.Type.Mandatory.name());
		ArrayList<Price> mandatory = sqlSession.getMapper(PriceMapper.class).restread(action);
		property.setMandatory(new PriceTable(mandatory));
		
		action.setEntitytype(NameId.Type.Feature.name());
		ArrayList<Price> feature = sqlSession.getMapper(PriceMapper.class).restread(action);
		property.setFeature(new PriceTable(feature));
		return property;
	}
	
	
	/**
	 * Gets the value name.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the id
	 * @return the value name
	 */
	protected static String getValueName(SqlSession sqlSession, String id) {
		if (keyvalues == null) {
			keyvalues = sqlSession.getMapper(AttributeMapper.class).list();
			keyvalues.addAll(FlipKeyUtils.getAttributesAsNameId());
		}
		LOG.info("Inside getValueName of Abstract Product ");
		LOG.info("Size of  keyvalues from  DB "+keyvalues.size());
		LOG.info("Size of  keyvalues from  DB + Static Map "+keyvalues.size());
		for (NameId keyvalue : keyvalues) {
			if (keyvalue.hasId(id)) {
				return keyvalue.getName();
			}
		}
		return null;
	}

	/**
	 * Pricelist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param productid the productid
	 * @param partyid the partyid
	 * @return the collection
	 * @throws ServiceException the service exception
	 */
	private Collection<Prices> createPricelist(SqlSession sqlSession, Product product, Date date, Date toDate, String currency) throws Throwable {

		Collection<Prices> pricelist = new ArrayList<Prices>();
		
		Price action = new Price();
		action.setPartyid(product.getSupplierid());
		action.setEntitytype(NameId.Type.Product.name());
		action.setEntityid(product.getId());
		action.setDate(date);
		action.setTodate(toDate);
		action.setCurrency(currency);

		ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).restread(action);
		if (prices == null || prices.isEmpty()) {throw new ServiceException(Error.price_data, product.getId());}

		for (Price price : prices) {
			try {
				double value = price.getValue();
				Prices listprices = new Prices(
						price.getDate(), 
						price.getTodate(), 
						price.getQuantity(), 
						price.getUnit());
				
				if(currency != null) {
					Currency.Code currencyCode = Currency.Code.valueOf(currency);
					switch(currencyCode) {
					case AUD :
						listprices.setAUD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.AUD.name()),0));
						break;
					case BGN :
						listprices.setBGN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.BGN.name()),0));
						break;
						
					case BRL :
						listprices.setBRL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.BRL.name()),0));
						break;
						
					case CAD :
						listprices.setCAD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CAD.name()),0));
						break;
						
					case CNY :
						listprices.setCNY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CNY.name()),0));
						break;
						
					case CZK :
						listprices.setCZK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CZK.name()),0));
						break;
						
					case DKK :
						listprices.setDKK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.DKK.name()),0));
						break;
						
					case EUR :
						listprices.setEUR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.EUR.name()),0));
						break;
						
					case HKD :
						listprices.setHKD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HKD.name()),0));
						break;
						
					case HRK :
						listprices.setHRK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HRK.name()),0));
						break;
						
					case HUF :
						listprices.setHUF(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HUF.name()),0));
						break;
						
					case IDR :
						listprices.setIDR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.IDR.name()),0));
						break;
						
					case INR :
						listprices.setINR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.INR.name()),0));
						break;
						
					case ISK :
						listprices.setISK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.ISK.name()),0));
						break;
						
					case JPY :
						listprices.setJPY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.JPY.name()),-1));
						break;
						
					case KRW :
						listprices.setKRW(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.KRW.name()),0));
						break;
						
					case LTL :
						listprices.setLTL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.LTL.name()),0));
						break;
						
					case LVL :
						listprices.setLVL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.LVL.name()),0));
						break;
						
					case MXN :
						listprices.setMXN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.MXN.name()),0));
						break;
						
					case MYR :
						listprices.setMYR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.MYR.name()),0));
						break;
						
					case NOK :
						listprices.setNOK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.NOK.name()),0));
						break;
						
					case NZD :
						listprices.setNZD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.NZD.name()),0));
						break;
						
					case PHP :
						listprices.setPHP(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.PHP.name()),0));
						break;
						
					case PLN :
						listprices.setPLN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.PLN.name()),0));
						break;
						
					case RON :
						listprices.setRON(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.RON.name()),0));
						break;
						
					case RUB :
						listprices.setRUB(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.RUB.name()),0));
						break;
						
					case GBP :
						listprices.setGBP(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.GBP.name()),0));
						break;
						
					case SGD :
						listprices.setSGD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.SGD.name()),0));
						break;
						
					case SEK :
						listprices.setSEK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.SEK.name()),0));
						break;
						
					case CHF :
						listprices.setCHF(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CHF.name()),0));
						break;
						
					case THB :
						listprices.setTHB(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.THB.name()),0));
						break;
						
					case TRY :
						listprices.setTRY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.TRY.name()),0));
						break;
						
					case USD :
						listprices.setUSD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.USD.name()),0));
						break;
						
					case ZAR :
						listprices.setZAR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.ZAR.name()),0));
						break;
						
					default :
						throw new ServiceException(Error.currency_code, currency);
					}
				} else {
					listprices.setAUD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.AUD.name()),0));
					listprices.setBGN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.BGN.name()),0));
					listprices.setBRL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.BRL.name()),0));
					listprices.setCAD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CAD.name()),0));
					listprices.setCNY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CNY.name()),0));
					listprices.setCZK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CZK.name()),0));
					listprices.setDKK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.DKK.name()),0));
					listprices.setEUR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.EUR.name()),0));
					listprices.setHKD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HKD.name()),0));
					listprices.setHRK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HRK.name()),0));
					listprices.setHUF(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.HUF.name()),0));
					listprices.setIDR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.IDR.name()),0));
//					listprices.setILS(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.ILS.name()),0));
					listprices.setINR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.INR.name()),0));
					listprices.setISK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.ISK.name()),0));
					listprices.setJPY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.JPY.name()),-1));
					listprices.setKRW(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.KRW.name()),0));
					listprices.setLTL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.LTL.name()),0));
					listprices.setLVL(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.LVL.name()),0));
					listprices.setMXN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.MXN.name()),0));
					listprices.setMYR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.MYR.name()),0));
					listprices.setNOK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.NOK.name()),0));
					listprices.setNZD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.NZD.name()),0));
					listprices.setPHP(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.PHP.name()),0));
					listprices.setPLN(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.PLN.name()),0));
					listprices.setRON(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.RON.name()),0));
					listprices.setRUB(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.RUB.name()),0));
					listprices.setGBP(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.GBP.name()),0));
					listprices.setSGD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.SGD.name()),0));
					listprices.setSEK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.SEK.name()),0));
//					listprices.setSKK(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.SKK.name()),0));
					listprices.setCHF(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.CHF.name()),0));
//					listprices.setTWD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.TWD.name()),0));
					listprices.setTHB(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.THB.name()),0));
					listprices.setTRY(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.TRY.name()),0));
					listprices.setUSD(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.USD.name()),0));
					listprices.setZAR(Model.round(value * WebService.getRate(sqlSession, price.getCurrency(), Currency.Code.ZAR.name()),0));
					
				}
				
				pricelist.add(listprices);
			} catch (Throwable x) {} //throw new ServiceException(Error.currency_exchange_rate, price.getCurrency());}
		}
		LOG.debug("pricelist " + pricelist);
		return pricelist;
	}

	/**
	 * Gets the name id pairs of products.
	 *
	 * @param organizationid the organization ID
	 * @param pos the mandatory point of sale code
	 * @param version the optional version date
	 * @param xsl the optional style sheet
	 * @return the name id pairs
	 */
	protected Items getNameIds(
			String organizationid,
			String pos,
			Integer rows,
			String version,
			Boolean offline,
			String xsl) {
		Date timestamp = new Date();
		String message = "/product/list?pos=" + pos + "&rows=" + rows + "&version=" + version + "&offline=" + offline + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			Date date = Constants.parseDate(version);
			NameIdAction action = new NameIdAction(Service.PRODUCT);
			action.setOrganizationid(organizationid);
			action.setState(Constants.CREATED);
			action.setNumrows(rows);
			action.setVersion(date);
			action.setRank(offline == null || offline ? -1.0 : 0.0);
			result = new Items(NameId.Type.Product.name(), null, null, null, sqlSession.getMapper(ProductMapper.class).nameidbyname(action), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name id pairs of products.
	 *
	 * @param organizationid the organization ID
	 * @param pos the mandatory point of sale code
	 * @param version the optional version date
	 * @param xsl the optional style sheet
	 * @return the name id pairs
	 */
	protected Items getDynamicNameIds(
			String pos,
			String xsl) {
		Date timestamp = new Date();
		String message = "/product/dynamic?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
//			NameIdAction action = new NameIdAction(Service.PRODUCT);
//			action.setOrganizationid(organizationid);
//			action.setState(Constants.CREATED);
//			action.setNumrows(rows);
//			action.setVersion(date);
//			action.setRank(offline == null || offline ? -1.0 : 0.0);
			result = new Items("Dynamic Price Product", null, null, null, sqlSession.getMapper(ProductMapper.class).nameiddynamic(), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the attributes.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the attributes
	 */
	protected Items getAttributes(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/attributes?xsl=" + xsl + "?pos=" + pos;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			result = new Items(NameId.Type.Product.name(), null, "Attributes", null, sqlSession.getMapper(AttributeMapper.class).razorlist(), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, "Attributes", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the product types.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the types
	 */
	protected Items getTypes(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/types?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			result = new Items(NameId.Type.Product.name(), null, "Types", null, sqlSession.getMapper(ProductMapper.class).type(), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, "Types", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids by type.
	 *
	 * @param type the type
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids by type
	 */
	protected Items getNameIdsByType(
			String type,
			String pos,
			Integer rows,
			String version,
			Boolean offline,
			String xsl) {
		Date timestamp = new Date();
		String message = "/product/list/" + type + "?pos=" + pos + "&rows=" + rows + "&version=" + version + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			Date date = Constants.parseDate(version);
			NameIdAction action = new NameIdAction(Service.PRODUCT);
			action.setType(type);
			action.setNumrows(rows);
			action.setState(Constants.CREATED);
			action.setVersion(date);
			action.setRank(offline == null || offline ? -1.0 : 0.0);
			Collection<NameId> item = sqlSession.getMapper(ProductMapper.class).nameidbyname(action);
			if (item == null) {throw new ServiceException(Error.type_invalid, type);}
			result = new Items(NameId.Type.Product.name(), null, type, null, item, xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, type, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids by type.
	 *
	 * @param type the type
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids by type
	 */
	protected ItemsResponse getListOfProductsForChannel(
			String type,
			String pos,
			String version,
			Boolean offline,
			String xsl) {
		Date timestamp = new Date();
		String message = "/product/list/" + type + "?pos=" + pos + "&version=" + version + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		ItemsResponse result = new ItemsResponse();
		try {
			Party agent = Constants.getParty(sqlSession, pos);
			Date date = Constants.parseDate(version);
			NameIdAction action = new NameIdAction(Service.PRODUCT);
			action.setType(type);
			action.setVersion(date);
			action.setId(agent.getId());
			Collection<NameId> item = sqlSession.getMapper(ProductMapper.class).channelProductsNameIdByName(action);
			if (item.size() == 0) {throw new ServiceException(Error.type_invalid, type);}
			result.setItems(new Items(NameId.Type.Product.name(), null, type, null, item, xsl));
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result.setErrorMessage(x.getMessage());
			result.setItems(new Items(NameId.Type.Product.name(), null, type, x.getMessage(), null, xsl));
		}
		finally {sqlSession.close();}
	//	LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids by location.
	 *
	 * @param type the type
	 * @param locationid the locationid
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids by location
	 */
	protected Items getNameIdsByLocation(
			String type,
			String locationid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/list/" + type + "/" + locationid + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			String[] locationids = locationid.split(",");
			result = new Items(NameId.Type.Product.name(), locationid, type, null, sqlSession.getMapper(ProductMapper.class).nameidbylocationid(locationids), xsl);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), locationid, type, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids near location.
	 *
	 * @param locationid the locationid
	 * @param distance the distance
	 * @param unit the unit
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids near location
	 */
	protected Items getNameIdsNearLocation(
			String locationid,
			Double distance,
			String unit,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/list/" + locationid + "/" + distance + "/" + unit + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			Location location = sqlSession.getMapper(LocationMapper.class).read(locationid);
			Area area = new Area();
			area.setLatitude(location.getLatitude());
			area.setLongitude(location.getLongitude());
			area.setDistance(distance);
			area.setUnit(unit);
			result = new Items(NameId.Type.Product.name(), locationid, "Accommodation", null, sqlSession.getMapper(ProductMapper.class).nameidbyarea(area), xsl);
		} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), locationid, "Accommodation", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids in area.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @param unit the unit
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids in area
	 */
	protected Items getNameIdsInArea(
			Double latitude,
			Double longitude,
			Double distance,
			String unit,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/list/" + latitude + "/"  + longitude + "/" + distance + "/" + unit + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Constants.getParty(sqlSession, pos);
			//Constants.decryptPos(pos);
			Area area = new Area();
			area.setLatitude(latitude);
			area.setLongitude(longitude);
			area.setDistance(distance);
			area.setUnit(unit);
			result = new Items(NameId.Type.Product.name(), null, null, null, sqlSession.getMapper(ProductMapper.class).nameidbyarea(area), xsl);
		} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), null, "Accommodation", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	
	/**
	 * Gets the detail along with reservation.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param language the language
	 * @param test the test
	 * @param xsl the xsl
	 * @return the detail
	 */
	protected Property getCompleteDetail(
			String productid,
			String pos,
			String language,
			String date,
			String toDate,
			String currency,
			Boolean test,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/fulldetail?pos=" + pos + "&language=" + language + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Property result = null;
		try {
			result = getPropertyDetail(sqlSession, productid, pos, language, date, toDate, currency, null, test, version);
			ArrayList<ScheduleItem> items = sqlSession.getMapper(
					ReservationMapper.class).flipkeyavailabilityproduct(productid);
			Collection<Reservation> reservation = new ArrayList<Reservation>();
			if (items != null && !items.isEmpty()) {
				for (ScheduleItem item : items) {
					reservation.add(new Reservation(item));
				}
				result.setReservations(new Reservations(reservation,
						Constants.NO_XSL));
			}
			result.setXsl(xsl);
			sqlSession.commit();
		}
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new Property();
			result.setMessage(message + " " + x.getMessage());
			result.setXsl(xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the property detail.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param language the language
	 * @param test the test
	 * @param xsl the xsl
	 * @return the detail
	 */
	protected PropertyResponse getPropertyDetail(
			String productid,
			String pos,
			String language,
			String date,
			String toDate,
			String currency,
			Boolean test,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/propertydetail?pos=" + pos + "&language=" + language + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		// Integer countImages = 20;
		SqlSession sqlSession = RazorServer.openSession();
		PropertyResponse response = new PropertyResponse();
		Property result = null;
		try {
			// TODO: if unlimited images worked wel - remove limit from query
			result = getPropertyDetail(sqlSession, productid, pos, language, date, toDate, currency, null, test, version);
			response.setProperty(result);
			result.setXsl(xsl);
			sqlSession.commit();
		}
		catch (JAXBException e) {
			LOG.error(e.getMessage() + "\n");
		}
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new Property();
			result.setMessage(message + " " + x.getMessage());
			response.setErrorMessage(message + " " + x.getMessage());
			result.setXsl(xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return response;
	}

	/**
	 * Gets the attributes.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the attributes 
	 */
	protected Attributes getAttributes(String productid, String pos,         
			Boolean test, String version, String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/attribute" + "?pos=" + pos
				+ "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Attributes result = null;
		try {                   
			getProperty(sqlSession, pos, productid, test, version);            //gets property. 
			HashMap<String, ArrayList<String>> attributeMap = RelationService
					.readMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, productid,
							Attribute.ACCOMMODATION_SEARCH);
			if (attributeMap == null || attributeMap.isEmpty()) {
				throw new ServiceException(Error.product_attribute, productid);
			}
			Collection<Attribute> attributes = new ArrayList<Attribute>();
			for (String key : attributeMap.keySet()) {
				ArrayList<String> values = new ArrayList<String>();
				for (String value : attributeMap.get(key)) {
					values.add(getValueName(sqlSession, value));
				}
				if (attributeMap.get(key).size() > 0) {
					attributes.add(new Attribute(getValueName(sqlSession, key),
							values));
				}
			}
			result = new Attributes(NameId.Type.Product.name(), productid,
					null, attributes, xsl);
			sqlSession.commit();
		} catch (Throwable x) {
			sqlSession.rollback();
			if (x != null && x.getMessage() != null
					&& !x.getMessage().startsWith(Error.data_unchanged.name())) {
				LOG.error(message + "\n" + x.getMessage());
			}
			result = new Attributes(NameId.Type.Product.name(), productid,
					message + " " + x.getMessage(), null, xsl);
		} finally {
			sqlSession.close();
		}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the images by id.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the images by id
	 */
	protected Items getImagesById (
			String productid,
			String pos,
			Boolean test,
			String version,
			String xsl) {
		List<NameId> listNameId;
		Date timestamp = new Date();
		String message = "/product/" + productid + "/image?pos=" + pos + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Property product = getProperty(sqlSession, pos, productid, test, version);
			if (product.hasSupplierid(PartyIds.PARTY_INTERHOME_ID)) {
				result = new Items(NameId.Type.Product.name(), productid, "Image", null, sqlSession.getMapper(TextMapper.class).imagesbyurl(new NameId(NameId.Type.Product.name(), productid)), xsl);
			}
			else {
			
//				listNameId=sqlSession.getMapper(TextMapper.class).imagesbynameid(new NameId(NameId.Type.Product.name(), productid));
//				if(listNameId!=null){
//					LOG.info("listNameId from Text Mapper is "+listNameId.size());	
//				}
//				
//				if(listNameId==null || (listNameId!=null && listNameId.size()==0)){
//					listNameId=sqlSession.getMapper(ImageMapper.class).imagesbynameid(new NameId(NameId.Type.Product.name(), productid));
//				}
				
				listNameId = ImageService.getProductRegularImageURLsAndDescription(sqlSession, productid);
				
				if(listNameId!=null){
					LOG.info("listNameId from Image Mapper is "+listNameId.size());	
				}
				result = new Items(NameId.Type.Product.name(), productid, "Image", null,listNameId, xsl);
			}
			sqlSession.commit();
		} 
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new Items(NameId.Type.Product.name(), productid, "Image", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the price list.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the price list
	 */
	protected PriceList getPriceList(
			String productid,
			String pos,
			Boolean test,
			String version,
			String xsl) {

		final SqlSession sqlSession = RazorServer.openSession();
		PriceList propertypricelist = null;
		try {
			propertypricelist = getPriceList(productid, pos, test, version, xsl,sqlSession,false);
		}
		finally {
			sqlSession.commit();
			sqlSession.close();
		}

		return propertypricelist;
	}
	
	protected PriceList getPriceList(
			String productid,
			String pos,
			Boolean test,
			String version,
			String xsl,
			SqlSession sqlSession,
			boolean fromxml) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/pricelist?pos=" + pos + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		PriceList result = null;
		Property product;
		try {
            product = (fromxml) ? getPropertyForXMLFeed(sqlSession, pos, productid, test, version) : getProperty(sqlSession, pos, productid, test, version);
			result = new PriceList(NameId.Type.Product.name(), productid, null, createPricelist(sqlSession, product, null, null, null), xsl);
		}
		catch(Throwable x) {
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new PriceList(NameId.Type.Product.name(), productid, message + " " + x.getMessage(), null, xsl);
		}
	//	finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the ratings.
	 *
	 * @param productid the productid
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the ratings
	 */
	protected Ratings getRatings(
			String productid,
			String pos,
			Boolean test,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/rating?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Ratings result = null;
		try {
			getProperty(sqlSession, pos, productid, test, version);
			Collection<Rating> items = sqlSession.getMapper(RateMapper.class).restrating(productid);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.product_rating, productid);}
			result = new Ratings(NameId.Type.Product.name(), productid, null, items, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Ratings(NameId.Type.Product.name(), productid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the product reviews.
	 *
	 * @param productid the product id
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the reviews
	 */
	protected Reviews getReviews(
			String productid,
			String pos,
			Boolean test,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/review?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Reviews result = null;
		try {
			getProperty(sqlSession, pos, productid, test, version);
			Collection<Review> items = sqlSession.getMapper(RateMapper.class).restreview(productid);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.product_rating, productid);}
			result = new Reviews(NameId.Type.Product.name(), productid, null, items, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Reviews(NameId.Type.Product.name(), productid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the product key values.
	 *
	 * @param productid the product ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected Items getValues(
			String productid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/values?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.PRODUCT_VALUE, productid, null);
			if (keyvalues == null) {throw new ServiceException(Error.product_value);}
			Collection<NameId> item = new ArrayList<NameId>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new NameId(args[0], args[1]));}
			}
			result = new Items(NameId.Type.Product.name(), productid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Product.name(), productid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * TODO: Gets the product key values.
	 *
	 * @param productid the product ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected KeyValues getKeyvalues(
			String productid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/product/" + productid + "/keyvalues?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		KeyValues result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.PRODUCT_VALUE, productid, null);
			if (keyvalues == null) {throw new ServiceException(Error.product_value);}
			Collection<KeyValue> item = new ArrayList<KeyValue>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new KeyValue(args[0], args[1]));}
			}
			result = new KeyValues(NameId.Type.Product.name(), productid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new KeyValues(NameId.Type.Product.name(), productid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Creates, updates or deletes a product.
	 *
	 * @param action the product parameter.
	 * @param pos the point of sale code.
	 * @param xsl the style sheet.
	 * @return the product.
	 */
	protected static synchronized Property setProperty (
			Property action,
			String pos,
			String xsl
			) {
		Date timestamp = new Date();
		String message = "\nPOST product?pos=" + pos + "&xsl=" + xsl + " params " + action.getId() + " " + action.toString();
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Property result = new Property();

		try {
			Party party = Constants.getParty(sqlSession, pos);
			String partyid = party.getId(); //Constants.decryptPos(pos); //TODO: only for managers
	
			Product product = null;
			if (action.hasAltpartyid()) {product = sqlSession.getMapper(ProductMapper.class).altread(action);}
			else if (action.hasId()) {product = sqlSession.getMapper(ProductMapper.class).read(action.getId());}

			if (product == null) { //new product
				product = new Product();
				product.setActorid(partyid);
				product.setAltid(action.getAltid());
				product.setAltpartyid(action.getAltpartyid());
				product.setType(Product.Type.Accommodation.name());
				product.setSupplierid(action.hasSupplierid() ? action.getSupplierid() : partyid);
				product.setUnit(Unit.DAY);
				sqlSession.getMapper(ProductMapper.class).create(product);
				RelationService.replace(sqlSession, Relation.ORGANIZATION_PRODUCT, product.getSupplierid(), product.getId());
			}

			product.setAltitude(action.getAltitude());
			product.setBathroom(action.getBathroom());
			product.setChild(action.getChild());
			product.setCode(action.getCode());
			product.setCommission(action.getCommissionValue());
			product.setCurrency(action.hasCurrency() ? action.getCurrency() : Currency.Code.USD.name());
			product.setDiscount(action.getDiscount());
			product.setInfant(action.getInfant());
			product.setLanguage(action.hasLanguage() ? action.getLanguage() : Language.Code.en.name());
			
//			Location location = null;
//			if (action.hasLocationid()) {location = sqlSession.getMapper(LocationMapper.class).read(action.getLocationid());}
//			else {location = PartnerService.getLocation(sqlSession, action.getCity(), action.getRegion(), action.getCountry(), action.getLatitude(), action.getLongitude(), action.getAltitude());}
//			if (location == null) {throw new ServiceException(Error.location_id, product.getLocationid());}
//
//			product.setLocationid(location.getId());
//			product.setLatitude(action.getLatitude());
//			product.setLongitude(action.getLongitude());
//			if (product.noLatLng()) {
//				product.setLatitude(location == null ? null : location.getLatitude());
//				product.setLongitude(location == null ? null : location.getLongitude());
//			}		
//			product.setName(action.hasName() ? action.getName() : location == null ? product.getId() : location.getName() + " " + product.getId());

			product.setLocationid(action.getLocationid());
			product.setLatitude(action.getLatitude());
			product.setLongitude(action.getLongitude());
			product.setName(action.getName());
			product.setOwnerid(action.hasOwnerid() ? action.getOwnerid() : partyid);
			product.setPerson(action.getPerson());
			product.setPhysicaladdress(action.getPhysicaladdress());
			product.setQuantity(action.getQuantity());
			product.setRank(0.0);
			product.setRating(action.getRating());
			product.setRoom(action.getRoom());
			product.setState(action.hasState() ? action.getState() : Product.CREATED);
			product.setToilet(action.getToilet());
			product.setWebaddress(action.getWebaddress());
			
			sqlSession.getMapper(ProductMapper.class).update(product);
			
			if (action.getAttributes() != null) {
				ArrayList<String> kvs = new ArrayList<String>();
				Collection<Attribute> attributes = action.getAttributes().attribute;
				for (Attribute attribute : attributes) {
					Collection<String> values = attribute.values.value;
					if(values != null) {
						for (String value : values) {kvs.add(value);}
					}
				}
				RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), kvs);
			}

			if (action.getImages() != null && HasUrls.LIVE) {
				ArrayList<NameId> images = new ArrayList<NameId>();
				Collection<String> urls = action.getImages().getImage();
				if(urls != null) {
					for (String url : urls) {images.add(new NameId("", url));}
					UploadFileService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
				}
				
			}

			if (action.getKeyvalues() != null) {
				ArrayList<String> kvs = new ArrayList<String>();
				Collection<KeyValue> keyvalues = action.getKeyvalues();
				for (KeyValue keyvalue : keyvalues) {kvs.add(keyvalue.getKey() + Model.DELIMITER + keyvalue.getValue());}
				RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, action.getId(), kvs);
			}

			LOG.debug("\nPricetable " + action.getPricetable());
			if (action.getPricetable() != null) {
				Collection<Price> prices = action.getPricetable().price;
				LOG.debug("\nPrices " + prices);
				for (Price price : prices) {
					if (price.noValue() || price.getValue() <= 0.0) {throw new ServiceException(Error.price_data, "Invalid Value");}
//					if (price.noDuration(0.0, Time.DAY)) {throw new ServiceException(Error.price_data, "Invalid Duration");}
					price.setEntitytype(NameId.Type.Product.name());
					price.setEntityid(product.getId());
					price.setPartyid(product.getSupplierid());
					price.setName(price.noName() ? Price.RACK_RATE : price.getName());
					price.setType(NameId.Type.Reservation.name());
					LOG.debug("\nParameter " + price);
					
					Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
					if (exists == null) {sqlSession.getMapper(PriceMapper.class).create(price);}
					else {price.setId(exists.getId());}
					LOG.debug("\nExists " + exists);
					
					price.setUnit(price.getUnit() == null ? Unit.DAY : price.getUnit());
					price.setState(Price.CREATED);
					price.setAvailable(price.getAvailable() == null ? 1 : price.getAvailable());
					price.setQuantity(price.noQuantity() ? 0.0 : price.getQuantity());
					price.setMinimum(price.getMinimum() == null ? 0.0 : price.getMinimum());
					sqlSession.getMapper(PriceMapper.class).update(price);
					LOG.debug("\nPrice " + price);
				}
			}

			if (action.getReviews() != null) {
				Collection<Review> reviews = action.getReviews().review;
				for (Review review : reviews) {
					Event<Rate> event = new Event<Rate>();
					event.setActivity(NameId.Type.Reservation.name());
					event.setActorid(partyid);
					event.setDate(review.getDate());
					event.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.RATE));
					event.setNotes(review.getNotes());
					event.setOrganizationid(product.getOrganizationid());
					event.setProcess(Event.Type.Rate.name());
					event.setState(review.getState() == null ? Event.CREATED : review.getState());
					event.setType(Event.NOMINAL);
					if (event.hasId()) {sqlSession.getMapper(EventMapper.class).update(event);}
					else {
						event.setName(SessionService.pop(sqlSession, event.getOrganizationid(), Serial.RATE));
						sqlSession.getMapper(EventMapper.class).create(event);
					}

					if (review.getRatings() != null) {
						Collection<Rating> ratings = review.getRatings();
						for (Rating rating : ratings) {
							Rate rate = new Rate();
							rate.setCustomerid(partyid);
							rate.setEventid(event.getId());
							rate.setName(rating.getName());
							rate.setQuantity(rating.getCount());
							rate.setProductid(product.getId());
							rate.setType(Rate.RATING);
							sqlSession.getMapper(RateMapper.class).create(rate);
						}
					}
				}
			}

			if (action.hasDescription()) {product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), action.getDescription(), product.getLanguage()));}
			if (action.hasContents()) {product.setContentsText(new Text(product.getContentsId(), product.getContentsLabel(), Text.Type.HTML, new Date(), action.getDescription(), product.getLanguage()));}
			if (action.hasOptions()) {product.setOptionsText(new Text(product.getOptionsId(), product.getOptionsLabel(), Text.Type.HTML, new Date(), action.getOptions(), product.getLanguage()));}
			if (action.hasCheckin()) {product.setCheckinText(new Text(product.getCheckinId(), product.getCheckinLabel(), Text.Type.HTML, new Date(), action.getCheckin(), product.getLanguage()));}
			if (action.hasConditions()) {product.setConditionsText(new Text(product.getConditionsId(), product.getConditionsLabel(), Text.Type.HTML, new Date(), action.getConditions(), product.getLanguage()));}
			TextService.update(sqlSession, product.getTexts());

//			RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, action.getId(), action.getValues());
			RelationService.create(sqlSession, Relation.PRODUCT_ATTRIBUTE, action.getId(), action.getAttributemap());
			RelationService.unload(sqlSession, Downloaded.PRODUCT_DOWNLOAD, action.getId(), null);

			sqlSession.commit();
			result.setId(product.getId());
		}
		catch(Throwable x) {
			sqlSession.rollback();
			result.setMessage(message + " " + x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
}