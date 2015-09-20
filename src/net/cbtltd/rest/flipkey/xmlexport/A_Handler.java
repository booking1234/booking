

package net.cbtltd.rest.flipkey.xmlexport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;
import net.cbtltd.rest.flipkey.xmlfeed.HasFull;
import net.cbtltd.rest.flipkey.xmlfeed.Property;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyAddresses;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyCalendar;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyData;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyDescription;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyDescriptions;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyDetails;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyPhoto;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyPhotos;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyRate;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyRates;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.TextService;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductExportSettingsMapper;
import net.cbtltd.server.api.ProductFilterSettingsMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.ProductExportSettings;
import net.cbtltd.shared.ProductFilterSettings;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * Handler class to do the work of XML export to flipkey 
 * @author nibodha
 *
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final String FILE_LOCATION="fileLocation";
	private static final String FILE_NAME="fileName";
	private static final String PROPERTIES_PER_PAGE="propertiesPerFile";
	
	private static Map<String,String> settings=new HashMap<String, String>();
	private static Map<String,List<String>> filter=null;
	public A_Handler(Partner partner) {
		super(partner);
		loadProductExportSettings(partner.getPartyid());
		filter=getProductFilterSettings(partner.getPartyid());
	}

	/**
	 * To get the filter settings for the particular partner
	 * @param partnerid
	 * @return map of filter settings
	 */
	public static Map<String,List<String>> getProductFilterSettings(String partnerid){
		SqlSession sqlSession = null;
		Map<String,List<String>> filters=null;
		try {
			sqlSession = RazorServer.openSession();
			ProductFilterSettingsMapper mapper = sqlSession
					.getMapper(ProductFilterSettingsMapper.class);
			filters=new HashMap<String,List<String>>();
			for (ProductFilterSettings filterSettings : mapper.getExportProductFilterSettings(partnerid)) {
				filters.put(filterSettings.getField().toLowerCase(), filterSettings.getFieldsValues());
			}
		} catch (Exception e) {
			Log.error("DB Exception fetching country codes ", e);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return filters;
	}
	/**
	 * To get the product export settings for the particular partner
	 * @param partnerid
	 */
	public static void loadProductExportSettings(String partnerid){
		SqlSession sqlSession = null;
		
		try {
			sqlSession = RazorServer.openSession();
			ProductExportSettingsMapper mapper = sqlSession
					.getMapper(ProductExportSettingsMapper.class);
			
			for (ProductExportSettings exportSettings : mapper.getProductExportSettings(partnerid)) {
				settings.put(exportSettings.getField(), exportSettings.getValue());
			}
		} catch (Exception e) {
			Log.error("DB Exception fetching country codes ", e);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	/**
	 * @param variable
	 * @return settings as string
	 */
	public static String get(String variable){
		if(settings.containsKey(variable)) return settings.get(variable);
		return null;
	}
	/**
	 * Get the property details from the database for the party id and generate XML
	 * 
	 */
	public void generatePropertyXML() {
		String altpartyid = partner.getPartyid();
		SqlSession sqlSession = null;
		List<Product> products = null;
		
		String t = get(PROPERTIES_PER_PAGE);
		int propertiesPerPage = (t != null ? Integer.parseInt(t) : 1000);
		final String FLIPKEY_XML_EXPORT = get(FILE_LOCATION);
		final String FLIPKEY_XML_FILENAME = get(FILE_NAME);
		try {
			sqlSession = RazorServer.openSession();
			products = sqlSession.getMapper(ProductMapper.class).getProductsForChannel(
					altpartyid);
			for (int pageNo = 0, recIndex=0;recIndex<products.size(); pageNo++)
				recIndex = buildExportPage(sqlSession,products, propertiesPerPage,
						FLIPKEY_XML_EXPORT + "/" + FLIPKEY_XML_FILENAME,
						recIndex,pageNo);
		} catch (Throwable x) {
			if (sqlSession != null)
				sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
			return;
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		
	}
	/**
	 * To prepare for the exporting of products and paginating it 
	 * @param sqlSession
	 * @param products
	 * @param propertiesPerPage
	 * @param path
	 * @param recStart
	 * @param pageNo
	 * @return recordPointer
	 */
	private int buildExportPage(SqlSession sqlSession ,List<Product> products,int propertiesPerPage,String path,int recStart,int pageNo){
		ExecutorService executor = Executors.newFixedThreadPool(1);
		int noOfProperty=0;
		final PropertyData properties = new PropertyData();
		int rec =recStart;
		for ( ; rec < products.size() && noOfProperty<propertiesPerPage; rec++) {
			try {

				final Product product = products.get(rec);
				if ( FlipKeyUtils. isProductExportRequired(getLocation(sqlSession,product),filter) ) {
					noOfProperty++;
				}
				executor.execute(new Runnable() {

					@Override
					public void run() {
						Property property = buildProperty(product);
						if (property != null) {
							properties.getProperty().add(property);
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
		exportAsXml(properties, path + pageNo + ".xml");
		return rec;
	}
	
/**
 * To write the property data into the file 
 * @param properties
 * @param path
 */
private void exportAsXml(PropertyData properties ,String path){
	StringBuilder b = new StringBuilder();
	Writer str = new StringWriter();
	try {
		JAXBContext context = JAXBContext
				.newInstance(PropertyData.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		m.setProperty(CharacterEscapeHandler.class.getName(),
				new CharacterEscapeHandler() {
					@Override
					public void escape(char[] ac, int i, int j,
							boolean flag, Writer writer)
							throws IOException {
						writer.write(ac, i, j);
					}
				});
		m.marshal(properties, str);
		b.append(str.toString());
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(
				path, false));
		pw.write(b.toString());
		pw.close();

	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * To generate the property object from the product 
	 * @param product
	 * @return property
	 */
	private Property buildProperty(Product product) {
		Property property = null;
		if (product != null) {
			property = new Property();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				buildPropertyDetails(sqlSession,product, property);
				buildPropertyDescriptions(sqlSession,product,property);
				buildPropertyAddresses(sqlSession,product,property);
				/*
				 * 
				 * buildPropertyFlags(product,property);
				 * buildPropertyBathrooms(product,property);
				 * buildPropertyAmenities(product,property);
				 * buildPropertyAttributes(product,property);
				 * buildPropertyNearbyDetails(product,property);
				 * buildPropertyDefaultRates(product,property);
				 * buildPropertySpecial(product,property);
				 */
				buildPropertyPhotos(sqlSession,product,property);
				 
				buildPropertyCalendar(sqlSession, product, property);
				 
				buildPropertyRates(sqlSession,product,property);
				/*
				 * buildPropertyThemes(product,property);
				 * buildPropertyBedrooms(product,property);
				 *
				 * 
				 * buildPropertySeating(product,property);
				 * buildPropertyFees(product,property);
				 * buildPropertyCalendars(product,property);
				 * buildReviewPhotos(product,property);
				 * buildPropertyRateSummary(product,property);
				 */
			} finally {
				if (sqlSession != null)
					sqlSession.close();

			}
		}
		return property;
	}

	/**
	 * load the property rates for the property
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyRates(SqlSession sqlSession, Product product,
			Property property) {
		Price queryPrice=new Price();
		queryPrice.setEntityid(product.getId());
		List<Price> listPrice = sqlSession.getMapper(PriceMapper.class)
				.readByEntityId(queryPrice);
		PropertyRates rates=new PropertyRates();
		List<PropertyRate> rateList=rates.getPropertyRate();
		PropertyRate rate=null;
		for(Price price : listPrice){
			rate=new PropertyRate();
			rate.setWeekendMinRate("");
			rate.setWeeknightMaxRate("");
			List<Object> obj=rate.getChangeoverDayOrIsChangeoverDayDefinedOrMinStayCount();
			rate.setEndDate(sdf.format(price.getTodate()));
			obj.add(getJAXBElement("label", price.getType()));
			obj.add(getJAXBElement("turn_day", price.getName()));
			obj.add(getJAXBElement("start_date", sdf.format(price.getDate())));
			obj.add(getJAXBElement("minimum_length", price.getMinStay() ));
			obj.add(getJAXBElement("month_max_rate", price.getValue().toString() ));
			obj.add(getJAXBElement("month_min_rate", price.getMinimum().toString()));
			obj.add(getJAXBElement("min_stay_unit", price.getUnit() ));
			rate.setRule(price.getRule());
			rateList.add(rate);
		}
		property.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities().add(rates);
	}

	/**
	 * To get the product location
	 * @param sqlSession
	 * @param product
	 * @return location
	 */
	private net.cbtltd.shared.Location getLocation(SqlSession sqlSession, Product product){
		if(product.getLocationid()==null) return null;
		return sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
		
	}
	/**
	 * To load the property address for the property
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyAddresses(SqlSession sqlSession, Product product,
			Property property) {
		net.cbtltd.shared.Location location=getLocation(sqlSession, product);
		PropertyAddresses addresses =new PropertyAddresses();
		addresses.setCity( location.getName() );
		addresses.setLocationName( location.getGname());
		addresses.setState(location.getRegion());
		addresses.setCountry(location.getCountry());
		if(location.getLatitude()!=null)
		addresses.setLatitude(location.getLatitude().toString());
		if(location.getLongitude()!=null)
		addresses.setLongitude(location.getLongitude().toString());
		addresses.setShowExactAddress("");
		addresses.setNewLocationId(BigInteger.ZERO);
		addresses.setPhysicalAddress(product.getPhysicaladdress());
		property.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities().add(addresses);
		
	}
	/**
	 * To load the property description for the property
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyDescriptions(SqlSession sqlSession, Product product, Property property) {
		PropertyDescriptions propertyDescriptions = new PropertyDescriptions();
		PropertyDescription propertyDescription = null;
		List<Text> texts = TextService.readallbyid(sqlSession, product.getId()+"%");
		for(Text txt : texts){
			propertyDescription = new PropertyDescription();
			propertyDescription.setType(getPropDescriptionType(txt.getId()));
			propertyDescription.setDescription(txt.getNotes());
			propertyDescriptions.getPropertyDescription().add(propertyDescription);
		}
		property.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities().add(propertyDescriptions);
	}

	/**
	 * To retrieve the property type 
	 * @param idWithType
	 * @return property type 
	 */
	private String getPropDescriptionType(String idWithType) {
		//value for idWithType will be like productId_propType (325732_property) 
		return idWithType.substring(idWithType.indexOf('_')+1);
	}

	/**
	 * TO load the property photo details for the property
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyPhotos(SqlSession sqlSession,Product product, Property property) {
		ArrayList<Image> imags=sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(product.getId()) );
		final String BASE_URL="http://images1.flipkey.com/img/photos"; 
		PropertyPhotos photos=new PropertyPhotos();
		List<PropertyPhoto> photoList=photos.getPropertyPhoto(); int count=0;
		for(Image img : imags ){
			PropertyPhoto photo=new PropertyPhoto();
			if(img.getName().endsWith("640x480")){
				photo.setDescription("");
				photo.setBaseUrl(BASE_URL );
				photo.setHasFull(new HasFull());
				photo.setPhotoFileName( img.getName() );
				List<Object> addons=photo.getOrderOrTypeOrHeight();
				addons.add(getJAXBElement("ta_image_width",800));
				addons.add(getJAXBElement("ta_image_height",533));
				addons.add(new JAXBElement(new QName("width"),BigInteger.class, BigInteger.valueOf(640l)));
				addons.add(new JAXBElement(new QName("height"),BigInteger.class, BigInteger.valueOf(426l)));
				addons.add(getJAXBElement("largest_image_prefix","640x480"));
				addons.add(getJAXBElement("ta_image_orientation","landscape"));
				addons.add(getJAXBElement("ta_image",img.getUrl()));
				addons.add(getJAXBElement("order",count));
				photoList.add(photo);
			}else if(img.getName().endsWith("large")){
				photo.setDescription("");
				photo.setBaseUrl(BASE_URL );
				photo.setHasFull(new HasFull());
				photo.setPhotoFileName( img.getName() );
				List<Object> addons=photo.getOrderOrTypeOrHeight();
				addons.add(getJAXBElement("ta_image_width",320));
				addons.add(getJAXBElement("ta_image_height",240));
				addons.add(new JAXBElement(new QName("width"),BigInteger.class, BigInteger.valueOf(320l)));
				addons.add(new JAXBElement(new QName("height"),BigInteger.class, BigInteger.valueOf(240l)));
				addons.add(getJAXBElement("largest_image_prefix","large"));
				addons.add(getJAXBElement("ta_image_orientation","landscape"));
				addons.add(getJAXBElement("ta_image",img.getUrl()));
				addons.add(getJAXBElement("order",count));
				photoList.add(photo);
			}else{
				photo.setDescription("");
				photo.setBaseUrl(BASE_URL );
				photo.setHasFull(new HasFull());
				photo.setPhotoFileName( img.getName() );
				List<Object> addons=photo.getOrderOrTypeOrHeight();
				addons.add(getJAXBElement("ta_image_width",200));
				addons.add(getJAXBElement("ta_image_height",150));
				addons.add(new JAXBElement(new QName("width"),BigInteger.class, BigInteger.valueOf(200l)));
				addons.add(new JAXBElement(new QName("height"),BigInteger.class, BigInteger.valueOf(150l)));
				addons.add(getJAXBElement("largest_image_prefix","thumb"));
				addons.add(getJAXBElement("ta_image_orientation","landscape"));
				addons.add(getJAXBElement("ta_image",img.getUrl()));
				addons.add(getJAXBElement("order",count));
				photoList.add(photo);
			}
			count++;
		}
		property.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities().add(photos);
	}

	/**
	 * To load the property and its basic details
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyDetails(SqlSession sqlSession, Product product, Property property) {
		PropertyDetails propertyDetails = new PropertyDetails();
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("property_type",product.getType()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("check_in"),String.class));
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("url",product.getWebaddress()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("check_out"),String.class));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("pet"),String.class));
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("bedroom_count",product.getRoom()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("handicap"),String.class));
		if(product.getChild() > 0){
			propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("children_over_five",product.getChild()));
		} else {
			propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("children_over_five",""));
		}
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("bathroom_count",product.getBathroom()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("children"),String.class));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("elder"),String.class));
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("name",product.getName()));
		Map<String, String> attributes = FlipKeyUtils.getATTRIBUTES();
		Set<String> keys = attributes.keySet();
		List<String> availAttribs = RelationService.read(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), null);
		for(String key : availAttribs){
			if(!"PCT53".equalsIgnoreCase(key))
			propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement(FlipKeyUtils.getKeyFromValue(key),"Yes"));
		}
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("smoking"),String.class));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("unit_size"),String.class));
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("occupancy",product.getPerson()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("elder_elevator"),String.class));
		propertyDetails.getCurrencyOrNameOrBathroomCount().add(getJAXBElement("currency",product.getCurrency()));
		//propertyDetails.getCurrencyOrNameOrBathroomCount().add(new JAXBElement(new QName("unit_size_units"),String.class));
		property.setPropertyDetails(propertyDetails);
	}

	/**
	 * To convert the any object into JAXBElement
	 * @param name
	 * @param value
	 * @return JAXBElement
	 */
	private JAXBElement getJAXBElement(String name,Object value) {
		String val;
//		val=String.valueOf(value).matches("[A-Z0-9-]*")?String.valueOf(value):
//		String.format("<![CDATA[%s]]>",String.valueOf(value));
		 val=value.toString();
		return new JAXBElement(new QName(name),String.class,String.valueOf(value));
	}
	

	/**
	 * To load the calendar info for the property
	 * @param sqlSession
	 * @param product
	 * @param property
	 */
	private void buildPropertyCalendar(SqlSession sqlSession, Product product,
			Property property) {
		PropertyCalendar propertyCalendar = new PropertyCalendar();
		List<Date> reservationList = sqlSession.getMapper(
				ReservationMapper.class).reservedDateForProperty(
				product.getId());
		List<JAXBElement> serializedDates = new ArrayList<JAXBElement>();
		for (Date dt : reservationList) {
			serializedDates.add(getJAXBElement("booked_date", sdf.format(dt)));
		}
		propertyCalendar.getContent().addAll(serializedDates);
		property.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities()
				.add(propertyCalendar);
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readPrices() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readProducts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSchedule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
}
