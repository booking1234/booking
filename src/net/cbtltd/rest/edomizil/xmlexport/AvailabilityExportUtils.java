package net.cbtltd.rest.edomizil.xmlexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.AvailabilityUnits;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.AvailabilityUnits.Unit;
import net.cbtltd.rest.response.CalendarElement;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Price.Rule;
import net.cbtltd.shared.Product;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class AvailabilityExportUtils {
	private static final Logger LOG = Logger.getLogger(PriceExportUtils.class
			.getName());
	private static final String FILE_LOCATION = "bp.e-domizil.xml.export.location";
	private static final String TILL_YEAR_COUNT="bp.e-domizil.xml.export.until.no_of_years.from_now";
	private static final String PROPERTIES_PER_FILE = "bp.e-domizil.xml.export.property.count";
//	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
//			.forPattern("dd.MM.YYYY");


	List<ChannelProductMap> getProducts(SqlSession sqlSession,String id){
		
		List<ChannelProductMap> listChannelProductMap =new ArrayList<ChannelProductMap>();
//				ChannelProductService.getInstance()
//				.readAllProductFromChannelProductMapper(sqlSession,Integer.parseInt(id));
//		Set<String> lst=new HashSet<String>();
//		for (ChannelProductMap channelProductMap : listChannelProductMap) {
//			lst.add(channelProductMap.getProductId());
//		}
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
	 * Get the property details from the database for the party id and generate
	 * XML
	 * 
	 */
	public void generateAvailabilityXML() {

		SqlSession sqlSession = null;
		// List<Product> products = null;
		String fileNamePrefix = "availability";
		int propertiesPerFile = Integer.parseInt(RazorConfig
				.getValue(PROPERTIES_PER_FILE));
		int totalNoOfProducts = 0;
		int totalNoOfRecords = 0;
		boolean multiFile = false;
		final String EDOMIZIL_XML_LOCATION = System.getProperty("user.home")
				+ RazorConfig.getValue(FILE_LOCATION);
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
			if (totalNoOfProducts > propertiesPerFile) {
				multiFile = true;
				totalNoOfRecords = (totalNoOfProducts % propertiesPerFile == 0) ? (totalNoOfProducts / propertiesPerFile)
						: ((totalNoOfProducts / propertiesPerFile) + 1);
			}
			if (multiFile) {
				int recordNo = 1;
				int productNo = 0;
				for (; (recordNo <= totalNoOfRecords && productNo < totalNoOfProducts); productNo++) {
					fileName = String.format("%s_%sof%s%s", fileNamePrefix,
							recordNo, totalNoOfRecords,
							EDOMIZIL_XML_FILENAME_SUFFIX);
					filePath = EDOMIZIL_XML_LOCATION + File.separator
							+ fileName;
					xmlExport(sqlSession, filePath, listChannelProductMap,
							((recordNo - 1) * propertiesPerFile),
							propertiesPerFile);
				}
			} else {
				fileName = fileNamePrefix + EDOMIZIL_XML_FILENAME_SUFFIX;
				filePath = EDOMIZIL_XML_LOCATION + File.separator + fileName;
				xmlExport(sqlSession, filePath, listChannelProductMap, 0,
						propertiesPerFile);
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
	private void xmlExport(SqlSession sqlSession, String filePath,
			List<ChannelProductMap> listChannelProductMap, int startIndex,
			int propertiesPerFile) {

		int index = startIndex;
		int productCount = 0;
		final AvailabilityUnits units = new AvailabilityUnits();
		for (; index < listChannelProductMap.size()
				&& productCount < propertiesPerFile; index++, productCount++) {
			try {
				final Product product = ProductService
						.getInstance()
						.fetchProduct(sqlSession,
								listChannelProductMap.get(index).getProductId());

				Unit unit = buildUnit(product, listChannelProductMap.get(index));
				if (unit != null) {
					units.getUnit().add(unit);
				}

			} finally {

			}
		}

		exportAsXml(units, filePath);
	}

	/**
	 * @param product
	 * @return
	 */
	private Unit buildUnit(Product product, ChannelProductMap channelProductMap) {
		Unit unit = null;
		if (product != null) {
			unit = new Unit();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				buildUnitBasicDetails(sqlSession, product, channelProductMap,
						unit);
				buildAvailability(sqlSession, product,
						channelProductMap, unit);

			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return unit;
	}

	private void buildUnitBasicDetails(SqlSession sqlSession, Product product,
			ChannelProductMap channelProductMap, Unit unit) {
		// set the attributes unit tag
		unit.setUnitownerid(getUnitOwnerId()==null?channelProductMap.getChannelProductId():getUnitOwnerId());
		unit.setInternalunitid(channelProductMap.getProductId()+"_"+channelProductMap.getChannelProductId());
		unit.setContainer("availability");

	}
	private void buildAvailability(SqlSession sqlSession,
			Product product, ChannelProductMap channelProductMap, Unit unit) {
		int no_of_years=Integer.parseInt(RazorConfig.getValue(TILL_YEAR_COUNT)==null?"1":RazorConfig.getValue(TILL_YEAR_COUNT));
		DateTime startingDate=new DateTime();
		DateTime beginingDate=startingDate.withDayOfYear(1);
		DateTime endingDate=beginingDate.withYear(beginingDate.getYear()+1);
	    
		Set<String> reservedDates=new HashSet<String>();
		CalendarResponse calendarResponse = this
				.getAvailabilityCalendar(channelProductMap.getProductId());
		if(calendarResponse.getItems()!=null){
			for (CalendarElement calendarElement : calendarResponse.getItems()) {
				reservedDates.add( calendarElement.getDate() );
			}
		}
		
		ArrayList<Price> listPrice= PriceService.getInstance().get(sqlSession, product.getEntitytype(), product.getId(), 
				product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
		Set<String> priceDates=new HashSet<String>();
		Set<String> checkinDates=new HashSet<String>();
		List<String> rules=Arrays.asList(Price.RULES);
		for (Price price : listPrice) {
			DateTime eDate=new DateTime(price.getTodate()).plusDays(1);
			int checkinDay=price.getRule()==null|| !rules.contains(price.getRule())? -1:Rule.valueOf(price.getRule()).ordinal();
			String dateStr=null;
			for(DateTime itrDate=new DateTime(price.getDate());itrDate.isBefore(eDate);itrDate=itrDate.plusDays(1)){
				dateStr=getDateAsString(itrDate);
				priceDates.add( dateStr );
				if(checkinDay==7){
					checkinDates.add(dateStr);
				}else if(itrDate.dayOfWeek().get()==checkinDay){
					checkinDates.add(dateStr);
				}
			}
			
		}

		StringBuilder builder;
		AvailabilityUnits.Unit.AvailabilityYear availabilityYear = new AvailabilityUnits.Unit.AvailabilityYear();
		AvailabilityUnits.Unit.AvailabilityYear.Availability availability=null;
		int days=Days.daysBetween(beginingDate,startingDate).getDays();
		String value=StringUtils.rightPad("", days, 'V');
		for(int itr=0;itr<=no_of_years;itr++,
				startingDate=startingDate.withDate(endingDate.getYear(), 1, 1),
				endingDate=endingDate.withDate(endingDate.getYear()+1, 1, 1),
				value=""){
			builder=new StringBuilder(value);
			for(DateTime itrDate=new DateTime(startingDate);itrDate.isBefore(endingDate);itrDate=itrDate.plusDays(1)){
				value=getDateAsString(itrDate);
				if(!reservedDates.contains(value)&&priceDates.contains(value)) {  builder.append(checkinDates.contains(value)?"B":"A"); }
				else if(!reservedDates.contains(value)&&!priceDates.contains(value)) {  builder.append("V"); }
				else {  builder.append("V"); }
			}
		    availability=new AvailabilityUnits.Unit.AvailabilityYear.Availability();
		    availability.setYear(String.valueOf(startingDate.getYear()));
		    availability.setStart(builder.toString());
		    availabilityYear.getAvailability().add(availability);
		}
		unit.setAvailabilityYear(availabilityYear);
		
	}
	private String getDateAsString(DateTime date){
		return JSONService.DF.format(date.toDate());
	}
	
	public void buildAvailabilityDetails(SqlSession sqlSession,
			Product product, ChannelProductMap channelProductMap, Unit unit) {
		// set the attributes unit tag
		AvailabilityUnits.Unit.AvailabilityYear availabilityYear = new AvailabilityUnits.Unit.AvailabilityYear();
		AvailabilityUnits.Unit.AvailabilityYear.Availability availabilityFor2014 = new AvailabilityUnits.Unit.AvailabilityYear.Availability();
		AvailabilityUnits.Unit.AvailabilityYear.Availability availabilityFor2015 = new AvailabilityUnits.Unit.AvailabilityYear.Availability();
		CalendarResponse calendarResponse = this
				.getAvailabilityCalendar(channelProductMap.getProductId());
		availabilityFor2014.setYear("2014");
		availabilityFor2015.setYear("2015");
		StringBuilder strFor2014 = new StringBuilder();
		StringBuilder strFor2015 = new StringBuilder();
		List<DateTime> listDate = new ArrayList<DateTime>();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime startDate = new DateTime();
		int days = Days.daysBetween(startDate, dtf.parseDateTime("2015-12-31"))
				.getDays();
		for (int i = 0; i < days; i++) {
			DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
			listDate.add(d);
		}
		boolean isPriceAvailable=false;
		for (DateTime testDate : listDate) {
			Price queryPrice=new Price();
			queryPrice.setDate(testDate.toDate());
			queryPrice.setEntityid(channelProductMap.getProductId());
			ArrayList<Price> listPrice = sqlSession.getMapper(PriceMapper.class)
					.readByEntityIdAndDateRange(queryPrice);
			if(CollectionUtils.isNotEmpty(listPrice)){
				isPriceAvailable=true;
			}
			String testDateStr = dtf.print(testDate);
			if (isPriceAvailable && this.isDateReserved(testDateStr, calendarResponse)) {
				if (testDateStr.contains("2014")) {
					strFor2014.append("V");
				} else if (testDateStr.contains("2015")) {
					strFor2015.append("V");
				}

			} else if(isPriceAvailable){
				if (testDateStr.contains("2014")) {
					strFor2014.append("A");
				} else if (testDateStr.contains("2015")) {
					strFor2015.append("A");
				}

			}else{
				if (testDateStr.contains("2014")) {
					strFor2014.append("V");
				} else if (testDateStr.contains("2015")) {
					strFor2015.append("V");
				}

			}
	
		availabilityFor2014.setStart(strFor2014.toString());
		availabilityFor2015.setStart(strFor2015.toString());
		}
		availabilityYear.getAvailability().add(availabilityFor2014);
		availabilityYear.getAvailability().add(availabilityFor2015);
		unit.setAvailabilityYear(availabilityYear);

	
	}

	/**
	 * To check whether the priceDate is reserved on calendarResponse
	 * 
	 * @param priceDate
	 * @param calendarResponse
	 * @return boolean
	 */
	private boolean isDateReserved(String testDate,
			CalendarResponse calendarResponse) {

		if (calendarResponse == null
				|| calendarResponse.getItems() == null
				|| (calendarResponse.getItems() != null && calendarResponse
						.getItems().length == 0)) {
			// if availability is empty then obviously date is not reserved
			return false;
		} else {
			for (CalendarElement calendarElement : calendarResponse.getItems()) {

				if (calendarElement.getDate().equalsIgnoreCase(testDate)) {
					// any calendar element that we get from the API is a
					// blocked date
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * To get the calendar response for the particular product
	 * 
	 * @param productId
	 * @return CalendarResponse
	 * 
	 */
	private CalendarResponse getAvailabilityCalendar(String productId) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dateTime = new DateTime();

		return ReservationRest.getCalendar(RazorConfig.getValue("pos.code"),
				productId, dtf.print(dateTime));
	}

	private void exportAsXml(AvailabilityUnits units, String path) {
		StringBuilder b = new StringBuilder();
		Writer str = new StringWriter();
		try {
			JAXBContext context = JAXBContext
					.newInstance(AvailabilityUnits.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.setProperty(CharacterEscapeHandler.class.getName(),
					new CharacterEscapeHandler() {
						@Override
						public void escape(char[] ac, int i, int j,
								boolean flag, Writer writer) throws IOException {
							writer.write(ac, i, j);
						}
					});
			m.marshal(units, str);
			b.append(str.toString());
			String fileAbsPath = new File(path).getAbsolutePath();
			String dirLoc = fileAbsPath.substring(0,
					path.lastIndexOf(File.separator));
			File file = new File(dirLoc);
			file.mkdirs();
			PrintWriter pw = new PrintWriter(new FileOutputStream(path, false));
			pw.write(b.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
