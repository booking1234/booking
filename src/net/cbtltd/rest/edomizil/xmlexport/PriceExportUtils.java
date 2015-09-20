package net.cbtltd.rest.edomizil.xmlexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.edomizil.xmlexport.datamodel.PriceUnits;
import net.cbtltd.rest.edomizil.xmlexport.datamodel.PriceUnits.Unit;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.WebService;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Price.Rule;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.mybookingpal.config.RazorConfig;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class PriceExportUtils {
	
	private static final Logger LOG = Logger.getLogger(PriceExportUtils.class.getName());
	private static final String FILE_LOCATION="bp.e-domizil.xml.export.location";
	private static final String PROPERTIES_PER_FILE="bp.e-domizil.xml.export.property.count";
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("dd.MM.YYYY");
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
	 * Get the property details from the database for the party id and generate XML
	 * 
	 */
	public void generatePriceXML() {
		
		SqlSession sqlSession = null;
		//List<Product> products = null;
		String fileNamePrefix = "prices";
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
		
		int index = startIndex;
		int productCount = 0;
		final PriceUnits units = new PriceUnits();
		for(; index < listChannelProductMap.size() && productCount < propertiesPerFile ; index++ , productCount++){
			try {
				final Product product = ProductService.getInstance().fetchProduct(sqlSession, listChannelProductMap.get(index).getProductId());
				
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
	private Unit buildUnit(Product product,ChannelProductMap channelProductMap) {
		Unit unit = null;
		if (product != null) {
			unit = new Unit();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();
				buildUnitBasicDetails(sqlSession,product,channelProductMap, unit);
				Price queryPrice=new Price();
				queryPrice.setDate(new Date());
				queryPrice.setEntityid(channelProductMap.getProductId());
				ArrayList<Price> listPrice= PriceService.getInstance().get(sqlSession, product.getEntitytype(), product.getId(), 
						product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
//				ArrayList<Price> listPrice = sqlSession.getMapper(PriceMapper.class)
//						.readByEntityIdAndDate(queryPrice);
				buildPriceDetails(sqlSession,product,channelProductMap, unit,listPrice);
				ArrayList<Price> featurePrice= PriceService.getInstance().get(sqlSession, "Feature", product.getId(), 
						product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
				ArrayList<Price> mandatoryPrice= PriceService.getInstance().get(sqlSession,"Mandatory", product.getId(), 
						product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
				buildPriceDetails(sqlSession,product,channelProductMap, unit,featurePrice,mandatoryPrice);
				
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return unit;
	}
	private String getPriceInCurrency(SqlSession sqlSession,Price price,String currency){
		String propertyCurrency=null;
		propertyCurrency=price.getCurrency();
		Double value=price.getValue();
		if(!propertyCurrency.equalsIgnoreCase(currency)){ 
			try {
				Double currencyRate = WebService.getRate(sqlSession, propertyCurrency, currency, new Date());
				value = PaymentHelper.getAmountWithTwoDecimals(value * currencyRate);
			} catch (ParseException e) {
				LOG.error(e);
			}
		}
		return new DecimalFormat("0.00").format(value);
	}

	private void buildPriceDetails(SqlSession sqlSession, Product product,
			ChannelProductMap channelProductMap, Unit unit,
			ArrayList<Price> featurePrice, ArrayList<Price> mandatoryPrice) {
		PriceUnits.Unit.ExtraCosts extraCosts=new PriceUnits.Unit.ExtraCosts();
		String currency=null;
		for(Price price:featurePrice){
			if(price.getValue()==null||price.getValue()==0) continue;
			currency=getSupportedCurrency(price.getCurrency());
			PriceUnits.Unit.ExtraCosts.ExtraCost extraCost=new PriceUnits.Unit.ExtraCosts.ExtraCost();
			extraCost.setOptional("Y");
			extraCost.setPrice(getPriceInCurrency(sqlSession, price, currency) );
			extraCost.setType((short)1);
			extraCost.setMeasure(0);
			extraCost.setPaymentMode(4);
			extraCost.setCurrency( currency );
			extraCost.setLanguage(product.getLanguage());
			extraCost.setValidFrom(dateTimeFormatter.print(new DateTime(price.getDate())) );
			extraCost.setValidTill(dateTimeFormatter.print(new DateTime(price.getTodate())) );
			extraCosts.getExtraCost().add(extraCost);
		}
		for(Price price:mandatoryPrice){
			if(price.getValue()==null||price.getValue()==0) continue;
			currency=getSupportedCurrency(price.getCurrency());
			PriceUnits.Unit.ExtraCosts.ExtraCost extraCost=new PriceUnits.Unit.ExtraCosts.ExtraCost();
			extraCost.setOptional("N");
			extraCost.setPrice(getPriceInCurrency(sqlSession, price, currency) );
			extraCost.setType((short)1);
			extraCost.setMeasure(0);
			extraCost.setPaymentMode(7);
			extraCost.setCurrency(currency);
			extraCost.setLanguage(product.getLanguage());
			extraCost.setValidFrom(dateTimeFormatter.print(new DateTime(price.getDate())) );
			extraCost.setValidTill(dateTimeFormatter.print(new DateTime(price.getTodate())) );
			extraCosts.getExtraCost().add(extraCost);
		}
		unit.setExtraCosts(extraCosts);
		
	}
	final static String[] SUPPORTED_CURRENCIES= { "GBP", "EUR", "USD", "CHF", "CZK", "DKK", "NOK", "SEK", "SKK", "PLN"};
	final static String DEFAULT_CURRENCY="USD";
	private String getSupportedCurrency(String currency) {
		if(Strings.isNullOrEmpty(currency)) return DEFAULT_CURRENCY;
		if(Arrays.binarySearch(SUPPORTED_CURRENCIES,currency)>=0){
			return currency;
		}
		return DEFAULT_CURRENCY;
	}
	private void buildPriceDetails(SqlSession sqlSession, Product product,ChannelProductMap channelProductMap, Unit unit,List<Price> listPrice) {
		PriceUnits.Unit.SeasonInfoE1 seasonInfoE1=new PriceUnits.Unit.SeasonInfoE1();
		int id=0;
		String currency=null;
		for(Price price:listPrice){
			if(price.getValue()==null||price.getValue()==0) continue;
			currency=getSupportedCurrency(price.getCurrency());
			PriceUnits.Unit.SeasonInfoE1.Season season=new PriceUnits.Unit.SeasonInfoE1.Season();
			season.setId(String.valueOf(++id) );
			season.setName(price.getName()+"-"+price.getId());
			PriceUnits.Unit.SeasonInfoE1 .Season.Dates seasonDates=new PriceUnits.Unit.SeasonInfoE1 .Season.Dates();
			PriceUnits.Unit.SeasonInfoE1 .Season.Dates.Date seasonDate=new PriceUnits.Unit.SeasonInfoE1 .Season.Dates.Date();
			seasonDate.setFrom(dateTimeFormatter.print(new DateTime(price.getDate())));
			seasonDate.setTo(dateTimeFormatter.print(new DateTime(price.getTodate())));
			seasonDates.getDate().add(seasonDate);
			season.setDates(seasonDates);
			PriceUnits.Unit.SeasonInfoE1.Season.RentalPrices rentalPrices=new PriceUnits.Unit.SeasonInfoE1.Season.RentalPrices();
			PriceUnits.Unit.SeasonInfoE1 .Season.RentalPrices.RentalPrice rentalPrice=new PriceUnits.Unit.SeasonInfoE1 .Season.RentalPrices.RentalPrice();
			rentalPrice.setPriceType((short) 0);
			DateTime dt1 =new DateTime(price.getTodate());
			DateTime dt2 = new DateTime(price.getTodate());
			rentalPrice.setPrice(getPriceInCurrency(sqlSession, price, currency) );
			int diff = Days.daysBetween(dt1, dt2).getDays() + 1;
			rentalPrice.setCntDays(diff);//find diff between the days and set the value
			int minStay = 0;
			if(price.getUnit().equals(net.cbtltd.shared.Unit.DAY)) {
				minStay = (int)(price.getMinimum()/price.getValue());
			}
			rentalPrice.setDurationMin(minStay==0?(price.getMaxStay()==null)?minStay:price.getMinStay():minStay);//minstay value
			if(price.getMaxStay()!=null) rentalPrice.setDurationMax(price.getMaxStay());//maxstay value
			int weekDayCode=127;
			if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.MonCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0000001");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.TueCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0000010");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.WedCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0000100");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.ThuCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0001000");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.FriCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0010000");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.SatCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("0100000");
			}else if(price.getRule()!=null && price.getRule().equalsIgnoreCase(Rule.SunCheckIn.name())){
				weekDayCode=this.convertBitmaskToInt("1000000");
			}
			rentalPrice.setWeekdayCode(weekDayCode);//set this vaue based on CheckinRule
			short person=0;
			if(product.getPerson()!=null) person=product.getPerson().shortValue();
			rentalPrice.setPersonenMin(person);//0 means no limit
			rentalPrice.setPersonenMax(person);//0 means no limit
			rentalPrices.getRentalPrice().add(rentalPrice);
			season.setRentalPrices(rentalPrices);
			seasonInfoE1.getSeason().add(season);
		}

		unit.getSeasonInfoE1().add(seasonInfoE1);
		
	}
	
	private int convertBitmaskToInt(String binaryString) {
		 int decimal = Integer.parseInt(binaryString, 2);
        //System.out.println("INPUT=" + binaryString + " decimal=" + decimal) ;
        return decimal;
	}
	
	private void buildUnitBasicDetails(SqlSession sqlSession, Product product,ChannelProductMap channelProductMap, Unit unit) {
		//set the attributes  unit tag
		unit.setUnitownerid(getUnitOwnerId()==null?channelProductMap.getChannelProductId():getUnitOwnerId());
		unit.setInternalunitid(channelProductMap.getProductId()+"_"+channelProductMap.getChannelProductId());
		unit.setContainer("prices");
		
	}
	

	
	private void exportAsXml(PriceUnits units ,String path){
		StringBuilder b = new StringBuilder();
		Writer str = new StringWriter();
		try {
			JAXBContext context = JAXBContext
					.newInstance(PriceUnits.class);
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
			PrintWriter pw = new PrintWriter(new FileOutputStream(
					path, false));
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
