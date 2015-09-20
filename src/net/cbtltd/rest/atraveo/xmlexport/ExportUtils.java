package net.cbtltd.rest.atraveo.xmlexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import net.cbtltd.rest.atraveo.datamodel.PricingFacilities;
import net.cbtltd.rest.atraveo.datamodel.PricingFacilities.Facility.Objects.Object;
import net.cbtltd.rest.atraveo.datamodel.PricingFactory;
import net.cbtltd.rest.atraveo.datamodel.ProductFacilities;
import net.cbtltd.rest.atraveo.datamodel.ProductFacilities.Facility;
import net.cbtltd.rest.atraveo.datamodel.ProductFacilities.Facility.CityPosition;
import net.cbtltd.rest.atraveo.datamodel.ProductFactory;
import net.cbtltd.rest.response.CalendarElement;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.server.integration.ImageService;
import net.cbtltd.server.integration.LocationService;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.RelationService;
import net.cbtltd.server.integration.ReservationService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * @author nibodha
 * 
 */
public class ExportUtils {
	
	public static enum EXPORT_USING { CHANNEL_ID, SUPPLIER_ID }; 

	private static final Logger LOG = Logger.getLogger(ExportUtils.class
			.getName());
	private static final String FILE_LOCATION="bp.atraveo.xml.export.location";
	static String exportWithAccessPointAs=  "localhost:8080";
	ProductFactory productFactory=new ProductFactory();
	PricingFactory pricingFactory=new PricingFactory();
	
	Collection<String> getProducts(String id,EXPORT_USING fieldType, SqlSession sqlSession){
		if(fieldType==EXPORT_USING.CHANNEL_ID){
			List<ChannelProductMap> listChannelProductMap = ChannelProductService.getInstance()
					.readAllProductFromChannelProductMapper(sqlSession,Integer.parseInt(id));
			Set<String> lst=new HashSet<String>();
			for (ChannelProductMap channelProductMap : listChannelProductMap) {
				lst.add(channelProductMap.getProductId());
			}
			return lst;
		}else if(fieldType==EXPORT_USING.SUPPLIER_ID){
			return ProductService.getInstance().activeProductIdListBySupplier(sqlSession,id);
		}
		return null;
	}

	/**
	 * Get the property details from the database for the party id and generate XML
	 * 
	 */
	public void generatePropertyXML(String id,EXPORT_USING using) {
		SqlSession sqlSession = null;
		final String ATREVO_XML_LOCATION = System.getProperty("user.home") + RazorConfig.getValue(FILE_LOCATION);
		String fileName = "Facilities.xml";
		String filePath = null;
		try {
			sqlSession = RazorServer.openSession();
			Collection<String> products=getProducts(id,using,sqlSession);
			filePath = ATREVO_XML_LOCATION + File.separator + fileName;
			if (products!=null) {
				productExport(filePath, products);
			}
			//int totalNoOfProducts = products.size();
			
		} catch (Throwable x) {
			if (sqlSession != null)
				sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	
	public void generatePricingXML(String id,EXPORT_USING using) {
		SqlSession sqlSession = null;
		final String ATREVO_XML_LOCATION = System.getProperty("user.home") + RazorConfig.getValue(FILE_LOCATION);
		String fileName = "Pricing.xml";
		String filePath = null;
		try {
			sqlSession = RazorServer.openSession();
			Collection<String> products=getProducts(id,using,sqlSession);
			//int totalNoOfProducts = products.size();
			filePath = ATREVO_XML_LOCATION + File.separator + fileName;
			if(products!=null){
			pricingExport(filePath, products);
			}
		} catch (Throwable x) {
			if (sqlSession != null)
				sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	private void pricingExport(String filePath, Collection<String> products) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		final PricingFacilities facilities = pricingFactory.createFacilities();
		for (final String product : products) {
			try {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						PricingFacilities.Facility facility = buildPricing(product);
						if (facility != null) {
							facilities.getFacility().add(facility);
						}
					}
				});

			} finally {

			}
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
		}
		exportAsXml(PricingFacilities.class, facilities, filePath);
	}
	protected PricingFacilities.Facility buildPricing(
			String productId) {
		PricingFacilities.Facility facility = null;
		if (productId != null) {
			facility = pricingFactory.createFacilitiesFacility();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				Product product =ProductService.getInstance().getProduct(sqlSession, productId);
				if(product.getPartofid()!=null) return null;
				facility.setID(productId);
				buildUnit(sqlSession,product, facility);
			}catch(Exception e){
				LOG.error(e,e);
			}
			finally {	
			if (sqlSession != null)
					sqlSession.close();
			}
		}
		return facility;
	}

	private void buildUnit(SqlSession sqlSession, Product product,
			PricingFacilities.Facility facility) throws DatatypeConfigurationException {
		PricingFacilities.Facility.Objects objects= pricingFactory.createFacilitiesFacilityObjects();
		PricingFacilities.Facility.Objects.Object object;
		List<Product> units=ProductService.getInstance().getUnitlist(sqlSession, product.getId());
		List<Price> prices= PriceService.getInstance().get(sqlSession, product.getEntitytype(), product.getId(), 
				product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
		if(units==null||units.size()==0){
			object=pricingFactory.createFacilitiesFacilityObjectsObject();
			object.setID(product.getId());
			buildUnitAvailability(sqlSession, product, object,prices);
			buildPricing(sqlSession, product, object,prices);
			buildObjectInfo(sqlSession, product, object,prices);
			objects.getObject().add(object);
		}else{
			for (Product subunit : units) {
				prices= PriceService.getInstance().get(sqlSession, subunit.getEntitytype(), subunit.getId(), subunit.getAltpartyid(), null);
				object=pricingFactory.createFacilitiesFacilityObjectsObject();
				object.setID(product.getId()+"/"+subunit.getId());
				buildUnitAvailability(sqlSession, subunit, object,prices);
				buildPricing(sqlSession, subunit, object,prices);
				buildObjectInfo(sqlSession, subunit, object,prices);
				objects.getObject().add(object);
			}
			buildAvailability(sqlSession,product, facility,prices);
		}
		facility.setObjects(objects);
	}

	private void buildObjectInfo(SqlSession sqlSession, Product subunit,
			Object object, List<Price> prices) {
		PricingFacilities.Facility.Objects.Object.ObjInfos objectInfos= pricingFactory.createFacilitiesFacilityObjectsObjectObjInfos();
		StringBuilder builder=null;
		for (Price price : prices) {
			PricingFacilities.Facility.Objects.Object.ObjInfos.ObjInfo objectInfo=pricingFactory.createFacilitiesFacilityObjectsObjectObjInfosObjInfo();
			Calendar cal=Calendar.getInstance();
			cal.setTime(price.getDate());
			builder=new StringBuilder ("NNNNNNN");
			builder.setCharAt(cal.get(Calendar.DAY_OF_WEEK)-1, 'Y');
			objectInfo.setArrivalDays(builder.toString());
			objectInfo.setDateFrom(price.getDate());
			objectInfo.setDateTo(price.getTodate());
			objectInfo.setMinStay( (byte)(price.getValue()/price.getMinimum()) );
			//price2.setMinOccupancy( 0 );
			objectInfos.getObjInfo().add(objectInfo);
		}
		object.setObjInfos(objectInfos);
	}
	private void buildPricing(SqlSession sqlSession, Product subunit,
			Object object, List<Price> prices) {
		PricingFacilities.Facility.Objects.Object.Prices prices2= pricingFactory.createFacilitiesFacilityObjectsObjectPrices();
		for (Price price : prices) {
			PricingFacilities.Facility.Objects.Object.Prices.Price price2=pricingFactory.createFacilitiesFacilityObjectsObjectPricesPrice();
			//price2.setType((Price.getDuration(price.getDate(), price.getTodate())>=7)?"wo":"ta");
			price2.setType("ta");
			price2.setDateFrom(price.getDate());
			price2.setDateTo(price.getTodate());
			if(price.getValue()!=null && price.getValue()!=0) price2.setPrice(price.getValue().floatValue());
			else if(price.getMinimum()!=null && price.getMinimum()!=0) price2.setPrice(price.getMinimum().floatValue());
			else if(price.getCost()!=null && price.getCost()!=0) price2.setPrice(price.getCost().floatValue());
			price2.setPersons((byte)0);
			prices2.getPrice().add(price2);
		}
		object.setPrices(prices2);
	}
	
	private void buildAvailability(SqlSession sqlSession, Product product,
			PricingFacilities.Facility facility, List<Price> prices) throws DatatypeConfigurationException {
		Date startingDate=new Date();
		Calendar end = Calendar.getInstance();
	    end.setTime(startingDate);
	    end.add(Calendar.YEAR, 1);
		StringBuilder builder = getAvailabilityString(sqlSession, product,
				prices, startingDate, end.getTime());
		PricingFacilities.Facility.Vacancy vacancy= pricingFactory.createFacilitiesFacilityVacancy();
		GregorianCalendar calendar= new GregorianCalendar();
		calendar.setTime(startingDate);
		vacancy.setStartDate( calendar.getTime() );
		vacancy.setVacancy(builder.toString());
		facility.setVacancy(vacancy);
	}
	private void buildUnitAvailability(SqlSession sqlSession, Product product,
			PricingFacilities.Facility.Objects.Object object, List<Price> prices) throws DatatypeConfigurationException {
		Date startingDate=new Date();
		Calendar end = Calendar.getInstance();
	    end.setTime(startingDate);
	    end.add(Calendar.YEAR, 1);
		StringBuilder builder = getAvailabilityString(sqlSession, product,
				prices, startingDate, end.getTime());
		
		PricingFacilities.Facility.Objects.Object.Vacancy vacancy= pricingFactory.createFacilitiesFacilityObjectsObjectVacancy();
		GregorianCalendar calendar= new GregorianCalendar();
		calendar.setTime(startingDate);
		vacancy.setStartDate( calendar.getTime() );
		vacancy.setVacancy(builder.toString());
		object.setVacancy(vacancy);
	}

	public static StringBuilder getAvailabilityString(SqlSession sqlSession,
			Product product, List<Price> prices, Date startingDate, Date end) {
		ArrayList<CalendarElement> calendarElements= ReservationService.getInstance().productSchedule(sqlSession, product.getId(),startingDate,end);
		StringBuilder builder=new StringBuilder();
		Set<String> reservedDates=new HashSet<String>();
		Set<String> priceDates=new HashSet<String>();
		Set<String> calendarDates=getDateStrings(startingDate, end);
		for (CalendarElement calendarElement : calendarElements) {
			reservedDates.add( calendarElement.getDate() );
		}
		for (Price price : prices) {
			priceDates.addAll(getDateStrings(price.getDate(),price.getTodate()));
		}
		
		for (String date : calendarDates) {
			if(!reservedDates.contains(date)&&priceDates.contains(date)) {  builder.append("Y"); }
			else if(!reservedDates.contains(date)&&!priceDates.contains(date)) {  builder.append("C"); }
			else {  builder.append("N"); }
		}
		return builder;
	}
	private static Set<Date> getDateRange(Date startDate,Date endDate){
		Calendar start = Calendar.getInstance();
	    start.setTime(startDate);
	    Calendar end = Calendar.getInstance();
	    end.setTime(endDate);
	    Set<Date> dates=new HashSet<Date>();
		for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) 
	    {
	        dates.add(date);
	    }
		return dates;
	}
	private static Set<String> getDateStrings(Date startDate,Date endDate){
		Set<String>  dates=new HashSet<String>();
		for (Date date : getDateRange(startDate, endDate)) 
	    {
	        dates.add( new SimpleDateFormat(DateAdapter.getPattern()).format(date) );
	    }
		return dates;
	}
	private void productExport(String filePath, Collection<String> products) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		final ProductFacilities facilities = productFactory.createFacilities();
		for (final String product : products) {
			try {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						Facility facility = buildFacility(product);
						if (facility != null) {
							facilities.getFacility().add(facility);
						}
					}
				});

			} finally {

			}
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
		}
		exportAsXml(ProductFacilities.class, facilities, filePath);
	}

	private Facility buildFacility(String productid) {
		Facility facility = null;
		if (productid != null) {
			facility = productFactory.createFacilitiesFacility();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				Product product =ProductService.getInstance().getProduct(sqlSession, productid);
				if(product.getPartofid()!=null) return null;
				buildFacilityDetails(sqlSession,product, facility);
				buildDescription(sqlSession,product,facility);
				buildUnits(sqlSession,product,facility);
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return facility;
	}
	
	private void buildUnits(SqlSession sqlSession, Product product,
			Facility facility) {
		List<Product> units=ProductService.getInstance().getUnitlist(sqlSession, product.getId());
		ProductFacilities.Facility.Objects objects= productFactory.createFacilitiesFacilityObjects();
		facility.setObjects(objects);
		ProductFacilities.Facility.Objects.Object object;
		if(units==null||units.size()==0){
			object=buildUnit(sqlSession,product,facility,product.getId());
			object.setType(ProductType.MOBILE_HOME.getId());
			objects.getObject().add(object);
			return;
		}
		for(Product unit:units){
			object=buildUnit(sqlSession,unit,facility,product.getId()+"/"+unit.getId());
			object.setType(ProductType.APARTMENT.getId());
			objects.getObject().add(object);
			
		}
	}

	private ProductFacilities.Facility.Objects.Object buildUnit(SqlSession sqlSession, Product product,
			Facility facility, String id) {
		ProductFacilities.Facility.Objects.Object object= productFactory.createFacilitiesFacilityObjectsObject();
		object.setID(id);
		if(product.getPerson()!=null) object.setPersons(product.getPerson().byteValue());
		if(product.getPerson()!=null) object.setMinOccupancy(product.getPerson().byteValue());
		if(product.getChild()!=null) object.setChildren(product.getChild().byteValue());
		if(product.getRoom()!=null) object.setRooms(product.getRoom().byteValue());
		if(product.getRoom()!=null) object.setBedrooms(product.getRoom().byteValue());
		if(product.getBathroom()!=null) object.setBathrooms(product.getBathroom().byteValue());
		object.setArrivalDays("YYYYYYY");
		List<Text> texts = TextService.readallbyid(sqlSession, product.getPublicId());
		ProductFacilities.Facility.Objects.Object.Descriptions descriptions=productFactory.createFacilitiesFacilityObjectsObjectDescriptions();
		object.setDescriptions(descriptions );
		for (Text text : texts) {
			if(text != null && text.getNotes() != null){
				ProductFacilities.Facility.Objects.Object.Descriptions.Description description=productFactory.createFacilitiesFacilityObjectsObjectDescriptionsDescription();
				description.setLanguage("DE"); 
				if(text.getLanguage() != null){
					description.setLanguage(text.getLanguage());
				}
				description.setValue(text.getNotes());
				descriptions.getDescription().add(description);
			}
		}
		ProductFacilities.Facility.Objects.Object.Position position= productFactory.createFacilitiesFacilityObjectsObjectPosition();
		Location location = getLocation(sqlSession, product);
		if(location!=null){
		position.setCity(location.getName());
		position.setStreet(location.getAdminarea_lvl_1());
		position.setLatitude(location.getLatitude().floatValue());
		position.setLongitude(location.getLongitude().floatValue() );
		}
		String address=product.getPhysicaladdress();
		position.setZipCode( address!=null&&address.length()>6 ? address.substring(address.length()-6) :"" );
		Relation relation=new Relation();
		relation.setHeadid(product.getId());
		relation.setLink("Product Attribute");
		List<String> listLineId=RelationService.getListRelationLineIds(sqlSession, relation);
		ProductFacilities.Facility.Objects.Object.Pictures pictures= buildPictures(sqlSession, product, facility);
		ProductFacilities.Facility.Objects.Object.Features features=buildFeatures(sqlSession, product, facility,listLineId);
		object.setPictures(pictures);
		object.setFeatures(features);
		if(listLineId.contains("PCT3")) object.setType(ProductType.APARTMENT.getId());
		else if(listLineId.contains("PCT25")) object.setType(ProductType.MOBILE_HOME.getId());
		else if(listLineId.contains("PCT33")) object.setType(ProductType.TENT.getId());
		else if(listLineId.contains("PCT16")) object.setType(ProductType.HOUSE.getId());
		
		object.setDirectLink( RazorHostsConfig.getProductUrl().replaceFirst("%hostname%", exportWithAccessPointAs)
				+ product.getId() + "&pos=" + Model.encrypt(product.getAltpartyid()));
		return object;
	}

	private ProductFacilities.Facility.Objects.Object.Pictures buildPictures(SqlSession sqlSession, Product product,
			Facility facility) {
		ProductFacilities.Facility.Objects.Object.Pictures pictures= productFactory.createFacilitiesFacilityObjectsObjectPictures();
		List<String> listImage=com.mybookingpal.server.ImageService.getProductRegularImageURLs(sqlSession, product.getId());
		Iterator<String> urls= listImage.iterator(); 
		List<Image> images=ImageService.getProductRegularImage(sqlSession, product.getId());
		for (Image image : images) {
			ProductFacilities.Facility.Objects.Object.Pictures.Picture picture=productFactory.createFacilitiesFacilityObjectsObjectPicturesPicture();
			ProductFacilities.Facility.Objects.Object.Pictures.Picture.Descriptions descriptions=productFactory.createFacilitiesFacilityObjectsObjectPicturesPictureDescriptions();
			ProductFacilities.Facility.Objects.Object.Pictures.Picture.Descriptions.Description description=productFactory.createFacilitiesFacilityObjectsObjectPicturesPictureDescriptionsDescription();
			descriptions.getDescription().add(description);
			picture.setDescriptions(descriptions);
			picture.setURL(urls.hasNext()?urls.next():image.getUrl());
			description.setLanguage(image.getLanguage());
			description.setValue(image.getName() );
			pictures.getPicture().add(picture);
		}
		return pictures;
		
	}

	private ProductFacilities.Facility.Objects.Object.Features buildFeatures(SqlSession sqlSession, Product product,
			Facility facility,List<String> listLineId) {
		
		
		ProductFacilities.Facility.Objects.Object.Features features= productFactory.createFacilitiesFacilityObjectsObjectFeatures();
		ProductFacilities.Facility.Objects.Object.Features.Feature feature;
		
		for (String featureType : listLineId) {
			Feature featureEnum=Feature.find(featureType);
			if(featureEnum==null) { System.out.println(featureType);continue;}
			feature=productFactory.createFacilitiesFacilityObjectsObjectFeaturesFeature();
			feature.setCode(featureEnum.name());
			feature.setValue((byte)featureEnum.getValue());
			ProductFacilities.Facility.Objects.Object.Features.Feature.Details details=productFactory.createFacilitiesFacilityObjectsObjectFeaturesFeatureDetails();
			ProductFacilities.Facility.Objects.Object.Features.Feature.Details.Detail detail=productFactory.createFacilitiesFacilityObjectsObjectFeaturesFeatureDetailsDetail();
			detail.setLanguage("en");
			detail.setValue(featureEnum.getDesc());
			details.getDetail().add(detail);
			feature.setDetails( details );
			features.getFeature().add(feature);
		}
		return features;
	}


	private void buildDescription(SqlSession sqlSession, Product product,
			Facility facility) {
		List<Text> texts = TextService.readallbyid(sqlSession, product.getPublicId());
		ProductFacilities.Facility.Descriptions descriptions=productFactory.createFacilitiesFacilityDescriptions();
		facility.setDescriptions(descriptions );
		for (Text text : texts) {
			if(text != null && text.getNotes() != null){
				ProductFacilities.Facility.Descriptions.Description description=productFactory.createFacilitiesFacilityDescriptionsDescription();
				description.setLanguage("DE"); 
				if(text.getLanguage() != null){
					description.setLanguage(text.getLanguage());
				}
				description.setValue(text.getNotes());
				descriptions.getDescription().add(description);
			}
		}
	}

	private void buildFacilityDetails(SqlSession sqlSession, Product product,
			Facility facility) {
		facility.setID(product.getId());
		facility.setName(product.getName());
		Location location = getLocation(sqlSession, product);
		if(location != null){
			if(location.getCountry() != null) facility.setCountry(location.getCountry());
			if(location.getName() != null) facility.setCity(location.getName());
			if(location.getRegion() != null) facility.setRegion(location.getAdminarea_lvl_1());
			if(location.getRegion() != null) facility.setSubregion(location.getAdminarea_lvl_2());
			CityPosition cityPosition = productFactory.createFacilitiesFacilityCityPosition();
			if(location.getLatitude() != null) cityPosition.setLatitude(location.getLatitude().floatValue());
			if(location.getLongitude() != null) cityPosition.setLongitude(location.getLongitude().floatValue());
			facility.setDistrict(location.getName());
			facility.setCityPosition(cityPosition);
		}
		
	}
	
	/**
	 * To get the product location
	 * @param sqlSession
	 * @param product
	 * @return location
	 */
	private Location getLocation(SqlSession sqlSession, Product product){
		if(product.getLocationid()==null) return null;
		return LocationService.getInstance().read(sqlSession, product.getLocationid());
	}

	private <T> void exportAsXml(Class<T> classType,T facilities, String filePath) {
		StringBuilder b = new StringBuilder();
		Writer str = new StringWriter();
		try {
			JAXBContext context = JAXBContext
					.newInstance(classType);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(CharacterEscapeHandler.class.getName(),
					new CharacterEscapeHandler() {
				@Override
				public void escape(char[] ac, int i, int j,
						boolean flag, Writer writer)
								throws IOException {
					writer.write(ac, i, j);
				}
			});
			m.marshal(facilities, str);
			b.append(str.toString());
			String fileAbsPath = new File(filePath).getAbsolutePath();
			String dirLoc = fileAbsPath.substring(0,filePath.lastIndexOf(File.separator));
			File file = new File(dirLoc);
			file.mkdirs();
			PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, false));
			pw.write(b.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the exportWithAccessPointAs
	 */
	public static final String getExportWithAccessPointAs() {
		return exportWithAccessPointAs;
	}

	/**
	 * @param exportWithAccessPointAs the exportWithAccessPointAs to set
	 */
	public static final void setExportWithAccessPointAs(
			String exportWithAccessPointAs) {
		ExportUtils.exportWithAccessPointAs = exportWithAccessPointAs;
	}
	
}

