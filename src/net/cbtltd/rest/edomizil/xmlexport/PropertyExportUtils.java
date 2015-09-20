/**
 * 
 */
package net.cbtltd.rest.edomizil.xmlexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Description;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Location;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Location.Country;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Location.Geocodes;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Occupancies;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.ProductUnits.Unit.Title;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.server.integration.AttributeService;
import net.cbtltd.server.integration.LocationService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.RelationService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * @author Suresh Kumar S
 *
 */
public class PropertyExportUtils  {

	private static final Logger LOG = Logger.getLogger(PropertyExportUtils.class.getName());
	private static final String FILE_LOCATION="bp.e-domizil.xml.export.location";
	private static final String PROPERTIES_PER_FILE="bp.e-domizil.xml.export.property.count";
	static String exportWithAccessPointAs=  "mybookingpal.com";
	
	List<ChannelProductMap> getProducts(SqlSession sqlSession,String id){
		
			List<ChannelProductMap> listChannelProductMap =new ArrayList<ChannelProductMap>();
//					ChannelProductService.getInstance()
//					.readAllProductFromChannelProductMapper(sqlSession,Integer.parseInt(id));
//			Set<String> lst=new HashSet<String>();
//			for (ChannelProductMap channelProductMap : listChannelProductMap) {
//				lst.add(channelProductMap.getProductId());
//			}
			 List<String> products=ProductService.getInstance().activeProductIdListBySupplier(sqlSession,id);
			 for (String string : products) {
				 ChannelProductMap productMap=new ChannelProductMap();
					productMap.setProductId(string);
					productMap.setChannelProductId(id);
					listChannelProductMap.add(productMap);
			}
			 return listChannelProductMap;
	}

	/**
	 * Get the property details from the database for the party id and generate XML
	 * 
	 */
	public void generatePropertyXML() {
		
		SqlSession sqlSession = null;
		//List<Product> products = null;
		String fileNamePrefix = "Objects";
		int propertiesPerFile = Integer.parseInt(RazorConfig.getValue(PROPERTIES_PER_FILE));
		int totalNoOfProducts = 0; 
		int totalNoOfRecords = 0;
		boolean multiFile = false;
		final String EDOMIZIL_XML_LOCATION = System.getProperty("user.home") + RazorConfig.getValue(FILE_LOCATION);
		final String EDOMIZIL_XML_FILENAME_SUFFIX = ".xml";
		String fileName = null;
		String filePath = null;
		try {
			sqlSession = RazorServer.openSession();
			List<ChannelProductMap> listChannelProductMap = new ArrayList<ChannelProductMap>();

			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			listChannelProductMap = getProducts(sqlSession,"8800");
			totalNoOfProducts = listChannelProductMap.size();
			if(totalNoOfProducts > propertiesPerFile){
				multiFile = true;
				totalNoOfRecords = (totalNoOfProducts % propertiesPerFile == 0) ? (totalNoOfProducts / propertiesPerFile) : ((totalNoOfProducts / propertiesPerFile) + 1);
			}
			if(multiFile){
				int recordNo = 1;
				int productNo = 0;
				for(; (recordNo <= totalNoOfRecords && productNo < totalNoOfProducts) ; productNo++){
					fileName = String.format("%s_%sof%s%s", fileNamePrefix, recordNo, totalNoOfRecords, EDOMIZIL_XML_FILENAME_SUFFIX);
					filePath = EDOMIZIL_XML_LOCATION + File.separator + fileName;
					xmlExport(sqlSession,filePath, listChannelProductMap, ((recordNo - 1) * propertiesPerFile), propertiesPerFile);
				}
			} else {
				fileName = fileNamePrefix + EDOMIZIL_XML_FILENAME_SUFFIX;
				filePath = EDOMIZIL_XML_LOCATION + File.separator + fileName;
				xmlExport(sqlSession,filePath, listChannelProductMap, 0, propertiesPerFile);
			}
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
	 * @param filePath
	 * @param products
	 * @param startIndex
	 * @param propertiesPerFile
	 */
	private void xmlExport(SqlSession sqlSession,String filePath, List<ChannelProductMap> listChannelProductMap, int startIndex, int propertiesPerFile) {
		//ExecutorService executor = Executors.newFixedThreadPool(1);
		int index = startIndex;
		int productCount = 0;
		final ProductUnits units = new ProductUnits();
		for(; index < listChannelProductMap.size() && productCount < propertiesPerFile ; index++ , productCount++){
			try {
				final Product product = ProductService.getInstance().fetchProduct(sqlSession, listChannelProductMap.get(index).getProductId());
				//SN:Commenting the executor code for time being
				/*executor.execute(new Runnable() {
					@Override
					public void run() {
						Unit unit = buildUnit(product);
						if (unit != null) {
							units.getUnit().add(unit);
						}
					}
				});*/
				Unit unit = buildUnit(product, listChannelProductMap.get(index));
				if (unit != null) {
					units.getUnit().add(unit);
				}

			} finally {

			}
		}
		/*executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
		}*/
		exportAsXml(units, filePath);
	}

	/**
	 * @param product
	 * @return
	 */
	private Unit buildUnit(Product product,ChannelProductMap channelProductMap) {
		Unit unit = null;
		if (product != null) {
			unit = new Unit();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				buildUnitDetails(sqlSession,product,channelProductMap, unit);
				buildUnitDescription(sqlSession,product,unit);
				buildUnitLocation(sqlSession,product,unit);
				buildUnitFeatures(sqlSession,product,unit);
				buildUnitPictures(sqlSession,product,unit);
				unit.getLink().add(
				 RazorHostsConfig.getProductUrl().replaceFirst("%hostname%", exportWithAccessPointAs)
						+ product.getId() + "&pos=" + Model.encrypt(product.getAltpartyid()));
				if(unit.getPictures()==null||unit.getLocation()==null||unit.getOccupancies()==null)
				{
					return null; 
				}
				
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return unit;
	}

	private void buildUnitPictures(SqlSession sqlSession, Product product,
			Unit unit) {
		List<String> listImage=com.mybookingpal.server.ImageService.getProductRegularImageURLs(sqlSession, product.getId());
		ProductUnits.Unit.Pictures pictures=new ProductUnits.Unit.Pictures();
		ProductUnits.Unit.Pictures.Picture picture=null;
		int id=243; int count=1;
		for(String img:listImage){
			picture=new ProductUnits.Unit.Pictures.Picture();
			picture.setAttributeNo(String.valueOf(id));
			picture.setUrl(img);
			pictures.getPicture().add(picture);
			if(count==5) id=327;
			else if(count==21) id=391;
			else id++;
			count++;
		}
		if(!pictures.getPicture().isEmpty())
			unit.setPictures(pictures);
		
	}

	private void buildUnitFeatures(SqlSession sqlSession, Product product,
			Unit unit) {
		ProductUnits.Unit.Features features=new ProductUnits.Unit.Features();
		List<String> listAttributeNames=this.fetchRelationDetails(sqlSession, product.getId(), "Product Attribute");
		
		//set PropertyType attribute
		ProductUnits.Unit.Features.Attribute attribute=new ProductUnits.Unit.Features.Attribute();
		attribute.setId(BigInteger.valueOf(113));
		ProductUnits.Unit.Features.Attribute.Lookup lookupforPT=new ProductUnits.Unit.Features.Attribute.Lookup();
		lookupforPT.setId(496);
		attribute.getContent().add(lookupforPT);
		features.getAttribute().add(attribute);
		//Day of Arrival optional
		attribute=new ProductUnits.Unit.Features.Attribute();
		attribute.setId(BigInteger.valueOf(208));
		ProductUnits.Unit.Features.Attribute.Lookup lookupforDOA=new ProductUnits.Unit.Features.Attribute.Lookup();
		lookupforDOA.setId(442);
		attribute.getContent().add(lookupforDOA);
		features.getAttribute().add(attribute);
		
		ProductUnits.Unit.Features.SimpleAttribute attribute1=new ProductUnits.Unit.Features.SimpleAttribute();
		attribute1.setId(BigInteger.valueOf(260)); attribute1.setValue(String.valueOf(product.getBathroom()));
		if(product.getBathroom()!=null) features.getSimpleAttribute().add(attribute1);
		ProductUnits.Unit.Features.SimpleAttribute attribute2=new ProductUnits.Unit.Features.SimpleAttribute();
		attribute2.setId(BigInteger.valueOf(261)); attribute2.setValue(String.valueOf(product.getRoom()));
		if(product.getRoom()!=null) features.getSimpleAttribute().add(attribute2);
		
		if(listAttributeNames.contains("Heating")){
			ProductUnits.Unit.Features.Attribute heating=new ProductUnits.Unit.Features.Attribute();
			heating.setId(BigInteger.valueOf(129));
			ProductUnits.Unit.Features.Attribute.Lookup lookupForHeating=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookupForHeating.setId(526);
			attribute.getContent().add(lookupForHeating);
			features.getAttribute().add(heating);
			
		}
		
		if(listAttributeNames.contains("Air Conditioning")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(130));
			ProductUnits.Unit.Features.Attribute.Lookup lookupForAC=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookupForAC.setId(316);
			attribute.getContent().add(lookupForAC);
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Cable Television") || listAttributeNames.contains("TV")||listAttributeNames.contains("DVD")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(131));
			
			if(listAttributeNames.contains("Cable Television")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup1=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup1.setId(333);
				attribute.getContent().add(lookup1);
			}
			if(listAttributeNames.contains("TV")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup2=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup2.setId(531);
				attribute.getContent().add(lookup2);
			}
			if(listAttributeNames.contains("DVD")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(536);
				attribute.getContent().add(lookup3);
			}
			
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Internet access") || listAttributeNames.contains("Wireless Internet") || listAttributeNames.contains("WiFi Hotspot")|| listAttributeNames.contains("WiFi")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(133));
			ProductUnits.Unit.Features.Attribute.Lookup lookup=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookup.setId(269);
			attribute.getContent().add(lookup);
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Parking")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(134));
			ProductUnits.Unit.Features.Attribute.Lookup lookup=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookup.setId(532);
			attribute.getContent().add(lookup);
			features.getAttribute().add(attribute);
			
		}
	
		if(listAttributeNames.contains("Washing Machine")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(135));
			ProductUnits.Unit.Features.Attribute.Lookup lookup=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookup.setId(410);
			attribute.getContent().add(lookup);
			features.getAttribute().add(attribute);
			
		}
		if(listAttributeNames.contains("Sofa") || listAttributeNames.contains("Oven")||listAttributeNames.contains("Separate Dining area")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(144));
			
			if(listAttributeNames.contains("Sofa")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup1=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup1.setId(218);
				attribute.getContent().add(lookup1);
			}
			if(listAttributeNames.contains("Oven")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup2=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup2.setId(272);
				attribute.getContent().add(lookup2);
			}
			if(listAttributeNames.contains("Separate Dining area")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(360);
				attribute.getContent().add(lookup3);
			}
			
			features.getAttribute().add(attribute);
			
		}
		if(listAttributeNames.contains("Shower")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(165));
			ProductUnits.Unit.Features.Attribute.Lookup lookup=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookup.setId(581);
			attribute.getContent().add(lookup);
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Microwave Owen") || listAttributeNames.contains("Dishwasher")||listAttributeNames.contains("Oven")||listAttributeNames.contains("Refrigerator")||listAttributeNames.contains("Coffee Machine")||listAttributeNames.contains("Gas Stove")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(172));
			
			if(listAttributeNames.contains("Microwave Owen")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup1=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup1.setId(225);
				attribute.getContent().add(lookup1);
			}
			if(listAttributeNames.contains("Dishwasher")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup2=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup2.setId(295);
				attribute.getContent().add(lookup2);
			}
			if(listAttributeNames.contains("Oven")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(353);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Refrigerator")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(364);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Coffee Machine")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(397);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Gas Stove")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(503);
				attribute.getContent().add(lookup3);
			}
			
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Domestic Pets")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(220));
			ProductUnits.Unit.Features.Attribute.Lookup lookup=new ProductUnits.Unit.Features.Attribute.Lookup();
			lookup.setId(263);
			attribute.getContent().add(lookup);
			features.getAttribute().add(attribute);
			
		}
		
		if(listAttributeNames.contains("Pool outside") || listAttributeNames.contains("Pool inside")||listAttributeNames.contains("Steam Bath")||listAttributeNames.contains("Sauna")||listAttributeNames.contains("Fitness center")||listAttributeNames.contains("Solarium")||listAttributeNames.contains("Play Ground")||listAttributeNames.contains("Swimming Pool")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(172));
			
			if(listAttributeNames.contains("Pool outside")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup1=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup1.setId(192);
				attribute.getContent().add(lookup1);
			}
			if(listAttributeNames.contains("Pool inside")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup2=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup2.setId(259);
				attribute.getContent().add(lookup2);
			}
			if(listAttributeNames.contains("Steam Bath")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(219);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Sauna")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(350);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Fitness center")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(363);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Solarium")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(375);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Play Ground")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(377);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Swimming Pool")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(517);
				attribute.getContent().add(lookup3);
			}
			
			features.getAttribute().add(attribute);
			
		}
		if(listAttributeNames.contains("Fishing") || listAttributeNames.contains("Basket Ball")||listAttributeNames.contains("Ice Skating")||listAttributeNames.contains("Football")||listAttributeNames.contains("Golf")||listAttributeNames.contains("Canoeing")){
			attribute=new ProductUnits.Unit.Features.Attribute();
			attribute.setId(BigInteger.valueOf(223));
			
			if(listAttributeNames.contains("Fishing")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup1=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup1.setId(191);
				attribute.getContent().add(lookup1);
			}
			if(listAttributeNames.contains("Basket Ball")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup2=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup2.setId(195);
				attribute.getContent().add(lookup2);
			}
			if(listAttributeNames.contains("Ice Skating")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(212);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Football")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(202);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Golf")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(230);
				attribute.getContent().add(lookup3);
			}
			if(listAttributeNames.contains("Canoeing")){
				ProductUnits.Unit.Features.Attribute.Lookup lookup3=new ProductUnits.Unit.Features.Attribute.Lookup();
				lookup3.setId(232);
				attribute.getContent().add(lookup3);
			}
					
			features.getAttribute().add(attribute);
			
		}
		unit.setFeatures(features);
	}

	/**
	 * @param sqlSession
	 * @param product
	 * @param unit
	 */
	private void buildUnitLocation(SqlSession sqlSession, Product product,
			Unit unit) {
		net.cbtltd.shared.Location loc = getLocation(sqlSession, product);
		if(loc != null){
			Location location = new Location();
			if(loc.getCountry() != null){
				String countryName = ProductService.getInstance().getCountryName(sqlSession, loc.getCountry());
				Country country = new Country();
				country.setIsoCode(loc.getCountry());
				country.setValue(countryName);
				location.setCountry(country);
			}
			location.setZipCode("");
			location.setHousenumber("");
			location.setStreet("");
			if(loc.getName() != null){
				location.setCity(loc.getName());
			}
		
			Geocodes geocodes = new Geocodes();
			if(loc.getLatitude() != null) geocodes.setLatitude(BigDecimal.valueOf(loc.getLatitude()));
			if(loc.getLongitude() != null) geocodes.setLongitude(BigDecimal.valueOf(loc.getLongitude()));
			geocodes.setType(BigInteger.valueOf(0L)); // 1 - property related, 0 - city related
			location.setGeocodes(geocodes);
			unit.setLocation(location);
		}
	}
	
	/**
	 * To get the product location
	 * @param sqlSession
	 * @param product
	 * @return location
	 */
	private net.cbtltd.shared.Location getLocation(SqlSession sqlSession, Product product){
		if(product.getLocationid()==null) return null;
		return LocationService.getInstance().read(sqlSession, product.getLocationid());
	}

	/**
	 * @param sqlSession
	 * @param product
	 * @param unit
	 */
	private void buildUnitDetails(SqlSession sqlSession, Product product,ChannelProductMap channelProductMap, Unit unit) {
		//set the attributes  unit tag
		unit.setUnitownerid(getUnitOwnerId()==null?channelProductMap.getChannelProductId():getUnitOwnerId());
		unit.setInternalunitid(channelProductMap.getProductId()+"_"+channelProductMap.getChannelProductId());
		unit.setContainer("property_view");
		unit.setLanguage("EN");
		if(product.getLanguage() != null){
			unit.setLanguage(product.getLanguage());
		}
		
		//set the property_type element
		unit.getPropertyType().add(BigInteger.valueOf(1L)); // 1 - holiday home, 2 - apartment, 3 - holiday park. 
		
		//set the title element
		Title title = new Title();
		title.setLanguage("EN"); // default language
//		if(product.getLanguage() != null){
//			title.setLanguage(product.getLanguage());
//		}
		title.setValue(product.getName());
		unit.getTitle().add(title);
		title = new Title();
		title.setLanguage("DE"); // default language
//		if(product.getLanguage() != null){
//			title.setLanguage(product.getLanguage());
//		}
		title.setValue(product.getName());
		unit.getTitle().add(title);
		
		//set the occupancies element
		if(product.getPerson() != null){
			Occupancies occupancies = new Occupancies();
			occupancies.setNormal(BigInteger.valueOf(product.getPerson().intValue()));
			occupancies.setMaximum(BigInteger.valueOf(product.getPerson().intValue()));
			unit.setOccupancies(occupancies);
		}
		
				

	}
	
	private void buildUnitDescription(SqlSession sqlSession, Product product,
			Unit unit) {
		List<Text> texts = TextService.readallbyid(sqlSession, product.getPublicId());
		for (Text text : texts) {
			if(text != null && text.getNotes() != null){
				Description description = new Description();
				description.setLanguage("EN"); // default language
//				if(text.getLanguage() != null){
//					description.setLanguage(text.getLanguage());
//				}
				description.setValue(text.getNotes());
				unit.getDescription().add(description);
				
				description = new Description();
				description.setLanguage("DE"); // default language
//				if(text.getLanguage() != null){
//					description.setLanguage(text.getLanguage());
//				}
				description.setValue(text.getNotes());
				unit.getDescription().add(description);
			}
		}
	}

	/**
	 * @param units
	 * @param path
	 */
	private void exportAsXml(ProductUnits units ,String path){
		StringBuilder b = new StringBuilder();
		Writer str = new StringWriter();
		try {
			JAXBContext context = JAXBContext
					.newInstance(ProductUnits.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.setProperty(CharacterEscapeHandler.class.getName(),
					new CharacterEscapeHandler() {
				@Override
				public void escape(char[] ac, int i, int j,
						boolean flag, Writer writer)
								throws IOException {
					writer.write(ac, i, j);
				}
			});
			m.marshal(units, str);
			b.append(str.toString());
			String fileAbsPath = new File(path).getAbsolutePath();
			String dirLoc = fileAbsPath.substring(0,path.lastIndexOf(File.separator));
			File file = new File(dirLoc);
			file.mkdirs();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					path, false)));
			pw.write(b.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> fetchRelationDetails(SqlSession sqlSession,String productId,String link){
		List<String> listAttrNames=new ArrayList<String>();
		Relation relation=new Relation();
		relation.setHeadid(productId);
		relation.setLink("Product Attribute");
		List<String> listLineId=RelationService.getListRelationLineIds(sqlSession, relation);
		if(listLineId !=null  && listLineId.size()>0){
			listAttrNames=AttributeService.getInstance().fetchAttributeNames(sqlSession, listLineId);	
			/*if(listAttrNames!=null){
				for(String name:listAttrNames){
					LOG.info("Attribute Name "+name);
				}
			}*/
		}
		return listAttrNames;
		
		
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
		PropertyExportUtils.exportWithAccessPointAs = exportWithAccessPointAs;
	}
	private String unitOwnerId;
	/**
	 * @return the unitOwnerId
	 */
	public final String getUnitOwnerId() {
		return unitOwnerId;
	}
	/**
	 * @param unitOwnerId the unitOwnerId to set
	 */
	public final void setUnitOwnerId(String unitOwnerId) {
		this.unitOwnerId = unitOwnerId;
	}
	
}
