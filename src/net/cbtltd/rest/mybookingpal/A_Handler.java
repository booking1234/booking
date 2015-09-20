/**
 * @author	 Isaac Mahgrefteh
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.rest.mybookingpal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.rest.Attribute;
import net.cbtltd.rest.Attributes;
import net.cbtltd.rest.Constants;
import net.cbtltd.rest.Countries;
import net.cbtltd.rest.Items;
import net.cbtltd.rest.PriceList;
import net.cbtltd.rest.Property;
import net.cbtltd.rest.Rating;
import net.cbtltd.rest.Ratings;
import net.cbtltd.rest.Review;
import net.cbtltd.rest.Reviews;
import net.cbtltd.rest.Schedule;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Currency.Code;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.price.PriceRange;
import net.cbtltd.shared.product.ProductList;

import org.apache.ibatis.session.SqlSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.thirdparty.guava.common.base.Stopwatch;
import com.mybookingpal.feed.service.LocationFeedGenerator;
import com.mybookingpal.feed.service.PartyFeedGenerator;
import com.mybookingpal.feed.service.ProductFeedGenerator;
import com.mybookingpal.feed.service.ReservationFeedGenerator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class A_Handler extends PartnerHandler implements IsPartner {
	//private static String POS = "5e7e3a77b3714ea2";
	//private static String POS = "834a55a7680c79fe";
	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private static final String UTF8METADATA = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	Stopwatch stopwatch;
	// TODO: make it work on any operating system.

	private static String OUTPUT_LOCATION_WINDOWS = "D:\\test\\";     
	private static String OUTPUT_LOCATION_LINUX = "/home/tmp/";

	private static ProductFeedGenerator productFeedGenerator = null;
	private static PartyFeedGenerator partyFeedGenerator = null;
	final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static boolean ispaging = false; // / create xml files using
	// multiple pages.
	private static int NUMBER_PRODUCT_PER_PAGE = 40; // number of properties per
	private static int COMMIT_SESSION = 50; 
	// a page.

	public A_Handler(Partner partner) {
		super(partner);
		// TODO Auto-generated constructor stub
	}

	///REMOVE DO NOT PLACE INTO SVN. 

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

	}

	@Override
	public void readProducts() {
		// List<Product> order = new ArrayList<Product>();
		// Product product;
		final SqlSession sqlSession = RazorServer.openSession();

		// List<Product> product =
		// sqlSession.getMapper(ProductMapper.class).readproductpaging(new
		// NameId("0","10"));
		XStream xstream = new XStream();
		xstream.alias("product", Product.class);

		File file = new File("D:\\OUTPUT_LOCATION\\properties.txt");
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {

				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			// bw.write(xstream.toXML(product));
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	//using the API method change it to be six months at a time. 
	public void writeScheduleUsingAPI(){
		long lStartTime = System.currentTimeMillis();
		int count = 0;
		LOG.debug("writeScheduleUsingAPI");
		Schedule schedule ;
		Calendar date = Calendar.getInstance();  
		date.setTime(new Date()); 
		Format f = new SimpleDateFormat("yyyy-MM-dd");  
		String fromdate =   f.format(date.getTime());  
		date.add(Calendar.YEAR,1);  
		String todate = f.format(date.getTime()); 


		BufferedWriter bw = null;
		final String TOP_LEVEL_ELEMENT = "products";
		ReservationFeedGenerator reservationfeedgenerator = null ;
		reservationfeedgenerator = reservationfeedgenerator.getInstance();

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Schedule.class);
		try {
			final SqlSession sqlSession = RazorServer.openSession();
			ArrayList<String> productidlist = sqlSession.getMapper(ProductMapper.class).activeproductid();

			for(int i =0; i < productidlist.size();i++){
				//System.out.println(xstream.toXML());

				schedule = reservationfeedgenerator.generatScheduleForProduct(productidlist.get(i), fromdate, todate, "POS");


				System.out.println(xstream.toXML(schedule));;


			}
		}catch(Throwable e){

		}

	}

	private String getChannelPartnerIDFromPartyID(String partyid, SqlSession sqlSession ){

		ChannelPartner channelpartnerid = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.parseInt( partyid));
		if(channelpartnerid == null && channelpartnerid.getId()== null ){
			throw new ServiceException(Error.party_value, "product [" + partyid + "]");
		}
		return channelpartnerid.getId().toString(); 
	}

	public void writeAvailableSchedule(String partyid){
		LOG.debug("writeAvailableSchedule");
		String filename = "availabilityschedule";
		String ROOT_ELEMENT = "products";
		BufferedWriter bw = createXMLFile(filename);
		writeOpeningXML(ROOT_ELEMENT, bw);
		final SqlSession sqlSession = RazorServer.openSession();
		final String POS = Model.encrypt(partyid); 
		
		ChannelPartner channelpartnerid = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.parseInt( partyid));
		if(channelpartnerid == null && channelpartnerid.getId()== null ){
			throw new ServiceException(Error.party_value, "product [" + partyid + "]");
		}
		
		ReservationFeedGenerator reservationfeedgenerator = null ;
		reservationfeedgenerator =	reservationfeedgenerator.getInstance();

	
		
		ArrayList<String> productidlist = PropertyList.getActivePropertyListByChannelPartner(sqlSession, channelpartnerid.getId().toString());
		
		XStream xstream = XstreamAnnotations.getAnnotationsForAvailableSchedule();


		try {
			for(int i =0; i < productidlist.size() ;i++){
				bw.write( (xstream.toXML(reservationfeedgenerator.generateAvailabilitySchedule(productidlist.get(i),POS))));
				bw.newLine(); 

				if (i % A_Handler.COMMIT_SESSION == 0) {
					sqlSession.commit();
				}

			}
		} catch (IOException e) {
			LOG.error(e.getMessage());;
		} finally {
			writeClosingXML(ROOT_ELEMENT, bw);
			sqlSession.commit(false);
			sqlSession.close();
		}


	}

	@Override
	public void readSchedule() {
		LOG.debug("readSchedule");
		long lStartTime = System.currentTimeMillis();

		String filename = "reserveddates";
		String ROOT_ELEMENT = "products";
		BufferedWriter bw = createXMLFile(filename);
		writeOpeningXML(ROOT_ELEMENT, bw);
		ReservationFeedGenerator reservationfeedgenerator = null ;
		reservationfeedgenerator =	reservationfeedgenerator.getInstance();

		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = sqlSession.getMapper(ProductMapper.class).activeproductid();

		Reservation reservation = new Reservation();
		Calendar date = Calendar.getInstance(); 
		date.setTime(new Date()); 
		reservation.setFromdate( date.getTime() );
		date.add(Calendar.YEAR,1); 
		reservation.setTodate(date.getTime());

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.mybookingpal.ProductSchedule.class);
		try {
			for(int i =0; i < productidlist.size();i++){

				reservation.setProductid(productidlist.get(i));
				bw.write( xstream.toXML(reservationfeedgenerator.generatScheduleForProduct(reservation,sqlSession)));
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());;
		} finally {
			writeClosingXML(ROOT_ELEMENT, bw);
			sqlSession.commit(false);
			sqlSession.close();
		}

		System.out.println("Elapsed seconds: "
				+ (System.currentTimeMillis() - lStartTime) / 1000.0);
	}

	private Attributes getAttributesByID(String productid, SqlSession sqlSession) {
		HashMap<String, ArrayList<String>> attributeMap = RelationService
				.readMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, productid,
						Attribute.ACCOMMODATION_SEARCH);

		if (attributeMap == null || attributeMap.isEmpty()) {
			LOG.error("ProductID :" + productid
					+ " does not contain any attributes");
		}
		Collection<Attribute> attributes = new ArrayList<Attribute>();
		for (String key : attributeMap.keySet()) {
			ArrayList<String> values = new ArrayList<String>();
			for (String value : attributeMap.get(key)) {
				values.add(getValueName(sqlSession, value));// getValueName;
				// need to be fixed.
			}
			if (attributeMap.get(key).size() > 0) {
				attributes.add(new Attribute(getValueName(sqlSession, key),
						values));
			}
		}
		return new Attributes(NameId.Type.Product.name(), productid, null,
				attributes, null);
	}

	public XStream xstreamAddAnnotations(Class... args) {
		XStream xstream = new XStream();
		for (Class arg : args) {
			xstream.processAnnotations(arg);
		}
		return xstream;
	}

	//	public void writeAttributes() {
	//		long lStartTime = System.currentTimeMillis();
	//		int count = 0;
	//		LOG.debug("write attribute");
	//		BufferedWriter bw = null;
	//		Attributes productattribute;
	//		XStream xstream;
	//		final String TOP_LEVEL_ELEMENT = "products";
	//		try {
	//
	//			bw = createXMLFile("attributes");
	//			writeOpeningXML(TOP_LEVEL_ELEMENT, bw);
	//
	//			final SqlSession sqlSession = RazorServer.openSession();
	//
	//			ArrayList<String> productidlist = sqlSession.getMapper(
	//					ProductMapper.class).activeproductid();
	//
	//			xstream = new XStream();
	//			xstream.processAnnotations(net.cbtltd.rest.Attribute.class);
	//			xstream.processAnnotations(net.cbtltd.rest.Values.class);
	//			xstream.processAnnotations(net.cbtltd.rest.Attributes.class);
	//
	//			for (String productid : productidlist) {
	//
	//				productattribute = getAttributesByID(productid, sqlSession);
	//
	//				bw.write(xstream.toXML(productattribute));
	//				LOG.debug("Count:" + ++count);
	//
	//			}
	//		} catch (Throwable e) {
	//			e.printStackTrace();
	//		} finally {
	//			writeClosingXML( TOP_LEVEL_ELEMENT, bw);
	//		}
	//
	//		long lEndTime = System.currentTimeMillis();
	//		LOG.debug("write attribute done Time: " + (lEndTime - lStartTime)
	//				/ 1000.0);
	//	}

	private static ArrayList<NameId> keyvalues = null;

	private String getValueName(SqlSession sqlSession, String id) {
		if (keyvalues == null) {
			keyvalues = sqlSession.getMapper(AttributeMapper.class).list();
			keyvalues.addAll(FlipKeyUtils.getAttributesAsNameId());
		}
		for (NameId keyvalue : keyvalues) {
			if (keyvalue.hasId(id)) {
				return keyvalue.getName();
			}
		}
		return null;
	}




	@Override
	public void readSpecials() {

	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO://finsh this method.
	public void writeReviews() {

		Log.error("writeReviews()");
		String toplevelelement = "products";
		BufferedWriter bw = createXMLFile("reviews");
		writeOpeningXML(toplevelelement, bw);
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = getActivePropertyList();
		Reviews result = null;
		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Review.class);
		xstream.processAnnotations(net.cbtltd.rest.Reviews.class);
		Collection<Review> items = null;
		try {
			for (String productid : productidlist) {
				items = sqlSession.getMapper(RateMapper.class).restreview(
						productid);
				if (items == null || items.isEmpty()) {
					LOG.error("No reviews for productID:" + productid);
					continue;
				}
				result = new Reviews(NameId.Type.Product.name(), productid,
						null, items, null);
				bw.write(xstream.toXML(result));
			}
		} catch (Throwable x) {
			LOG.error(x.getMessage());
		} finally {
			writeClosingXML(toplevelelement, bw);
		}
	}
	//create an XML file and use UTF-8 encoding. 
	private BufferedWriter createXMLFile(String filename) {
		String fullfilepath =  OUTPUT_LOCATION_LINUX;
		if(RazorServer.isWindowsEnviroment()){fullfilepath = OUTPUT_LOCATION_WINDOWS;}

		fullfilepath +=  filename + ".xml";
		File fileDir = new File(fullfilepath); 
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileDir), "UTF8"));
			if (!fileDir.exists()) {
				fileDir.createNewFile();
			}

		} catch (Throwable e) { LOG.error(e.getMessage());}

		return bw;
	}

public ChannelPartner ChannelPartnerIdFromPartyId(SqlSession sqlSession, String partyid){
	ChannelPartner channelpartnerid = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.parseInt( partyid));
	if(channelpartnerid == null && channelpartnerid.getId()== null ){
		throw new ServiceException(Error.party_value, "product [" + partyid + "]");
	}
	return channelpartnerid;
}

	public void writePricePerWeeklyRate(String partyid){
		int pagenumber=0;
		String filename = "weeklyrate_";
		BufferedWriter bw = createXMLFile(filename );
		String ROOT_ELEMENT = "properties";
		WeeklyPriceResponse weeklyprice;
		int propertywithprices = 0; 
		final SqlSession sqlSession = RazorServer.openSession();
		ChannelPartner 	channelpartnerid = ChannelPartnerIdFromPartyId(sqlSession,  partyid);
		final String POS = Model.encrypt(partyid);
		ArrayList<String> productidlist = PropertyList.getActivePropertyListByChannelPartner(sqlSession, channelpartnerid.getId().toString());
		
		try {
			writeOpeningXML(ROOT_ELEMENT, bw);

			
			//ArrayList<String> productidlist = getActivePropertyList();

			ReservationFeedGenerator reservationfeedgenerator = null ;
			reservationfeedgenerator = reservationfeedgenerator.getInstance();

			XStream xstream = XstreamAnnotations.getAnnotationsForPricePerWeeklyRate();


			for(int i =0; i< productidlist.size() ; i++){  
				
				try {
			System.out.println("ID = " +productidlist.get(i));
				weeklyprice = reservationfeedgenerator.getWeeklyPrices( POS , productidlist.get(i), Code.USD.name() , sqlSession );
				if(weeklyprice.getPriceRanges().size() > 0){
					propertywithprices++;
					bw.write((xstream.toXML(weeklyprice))); bw.newLine(); 
				}
				if(propertywithprices % NUMBER_PRODUCT_PER_PAGE == 0 & propertywithprices != 0){
					pagenumber++;
					bw = openNewXMLFile(ROOT_ELEMENT, filename+"_" + pagenumber, bw);
					propertywithprices=0; 
				}
				
				} catch (ServiceException e) {
					LOG.error("Error in writePricePerWeeklyRate with  ProductID: "+productidlist.get(i) +e.getMessage());
				}
			}
			

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}finally {
			writeClosingXML(ROOT_ELEMENT, bw);
		    sqlSession.close();
		}
	}
	public void writeDetailInformation(final String partyid) throws InterruptedException {

		final SqlSession sqlSession = RazorServer.openSession();
		long lStartTime = System.currentTimeMillis();
		int pagenumber=0;
		final String POS = Model.encrypt(partyid); 

		ChannelPartner channelpartnerid = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.parseInt( partyid));
		if(channelpartnerid == null && channelpartnerid.getId()== null ){
			throw new ServiceException(Error.party_value, "product [" + partyid + "]");
		}

		String ROOT_ELEMENT = "properties";
		String filename = "detailinformation_";
		BufferedWriter bw = createXMLFile(filename+pagenumber );
		writeOpeningXML(ROOT_ELEMENT, bw);
		productFeedGenerator = productFeedGenerator.getInstance();


			ArrayList<String> productidlist = PropertyList.getActivePropertyListByChannelPartner(sqlSession, channelpartnerid.getId().toString());
	    //	ArrayList<String> productidlist = getActivePropertyList();

		XStream  xstream = XstreamAnnotations.getAnnotationsForDetailInformation(); 

		try {
			for(int i =0; i<productidlist.size(); i++){
				System.out.println(productidlist.get(i));
				bw.write(xstream.toXML(productFeedGenerator.generateDetailInformation(productidlist.get(i),POS, sqlSession))); 

				if(i % NUMBER_PRODUCT_PER_PAGE == 0 & i != 0){
					pagenumber++;
					bw = openNewXMLFile(ROOT_ELEMENT, filename + pagenumber, bw);

				}

				if (i % 15 == 0) {
					sqlSession.commit();
				}
				if(i > 800)break;
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		finally{
			writeClosingXML(ROOT_ELEMENT, bw);
			sqlSession.close(); 
		}


		System.out.println("Elapsed seconds: " + (System.currentTimeMillis() - lStartTime)/1000.0);
	}

	// TODO:Remove fields. 
	public void writeSummaryInformation() {

		long lStartTime = System.currentTimeMillis();
		String toplevelelement = "products";
		BufferedWriter bw = createXMLFile("summaryinformation");
		writeOpeningXML(toplevelelement, bw);

		LOG.debug("Started_writeSummaryInformation :" + new Date());

		productFeedGenerator = productFeedGenerator.getInstance();
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = getActivePropertyList();

		XStream xstream = new XStream();

		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(net.cbtltd.rest.Ratings.class);
		xstream.processAnnotations(net.cbtltd.rest.Property.class);
		xstream.alias("property", net.cbtltd.rest.Property.class);   
		xstream.processAnnotations(net.cbtltd.rest.Values.class);
		xstream.processAnnotations(net.cbtltd.rest.Attributes.class);
		xstream.omitField(net.cbtltd.shared.ModelTable.class, "numrows");
		xstream.omitField(net.cbtltd.shared.ModelTable.class, "startrow"); 
		xstream.omitField(net.cbtltd.shared.Model.class, "status");
		xstream.processAnnotations(net.cbtltd.shared.NameIdAction.class);

		try {
			for (int i = 0; i < productidlist.size(); i++) {

				LOG.debug("Count = " + i + " ProductID ="+ productidlist.get(i));
				//bw.write(xstream.toXML(productFeedGenerator.generatPropertySummary(productidlist.get(i), POS,sqlSession)));
				String xml = xstream.toXML(productFeedGenerator.generatPropertySummary(productidlist.get(i), "POS",sqlSession));
				System.out.println(xml);
				if (i > 0 && i % IsPartner.PRICE_BATCH_SIZE == 0) {
					sqlSession.commit(false);
				}

			}
		} catch (Throwable e) {
			LOG.equals(e.getMessage());
		}
		writeClosingXML(toplevelelement, bw);
		System.out.println("Elapsed seconds: " + (System.currentTimeMillis() - lStartTime)/1000.0);
	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub

	}



	// using Chirayu method. //Works
	@Override
	public void readImages() {
		String toplevelelement = "imagelist";
		BufferedWriter bw = createXMLFile("imagelist");
		writeOpeningXML(toplevelelement, bw);

		final SqlSession sqlSession = RazorServer.openSession();

		ArrayList<String> productidimagelist = sqlSession.getMapper(
				ImageMapper.class).productidsofimages();
		int count = 0;
		productFeedGenerator = productFeedGenerator.getInstance();

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Items.class);

		try {
			for (String productid : productidimagelist) {
				bw.write(xstream.toXML(new Items(NameId.Type.Product.name(),
						productid, "Image", null, productFeedGenerator
						.generateProductRegularImageURLs(sqlSession,
								productid), null)));
				count++;
				LOG.debug("Count" + count + "ProductID: " + productid);
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

		writeClosingXML("</" + toplevelelement, bw);
	}

	private void writeOpeningXML(String element, BufferedWriter bw) {
		try {
			bw.write(UTF8METADATA);
			bw.newLine();
			bw.write("<" + element + ">");
			bw.newLine();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	private void writeClosingXML(String element, BufferedWriter bw) {
		try {
			bw.newLine();
			bw.write("</" + element + ">");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//	LOG.debug("--------------BUFFER_CLOSING------------------------");
				bw.flush();
				bw.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	// close connection error. after id = 3530 error.
	// /This priceList is generated from the api.
	public void writePriceList() {
		int countnonblankelement=0; 
		long lStartTime = System.currentTimeMillis();
		int pagenumber = 0;
		String filename = "pricelist_";
		PriceList productpricelist;
		String ROOT_ELEMENT = "pricelists";
		LOG.debug("writePriceList");
		BufferedWriter bw = createXMLFile(filename + pagenumber); // add page
		// number.
		writeOpeningXML(ROOT_ELEMENT, bw);
		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.PriceList.class);
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = getActivePropertyList();
		productFeedGenerator = productFeedGenerator.getInstance();

		try {
			for (int i = 0; i < productidlist.size(); i++) {
				//	for (int i = 0; i < 10; i++) {
				LOG.debug("PropertyID:" + productidlist.get(i));
				productpricelist = productFeedGenerator.generatePriceList(
						productidlist.get(i), "POS", sqlSession);
				if (productpricelist.prices != null) {
					bw.write(xstream.toXML(productpricelist));
					countnonblankelement++;
				}
				if (i % IsPartner.PRICE_BATCH_SIZE == 0) {
					sqlSession.commit(false);
				}
				if(countnonblankelement % NUMBER_PRODUCT_PER_PAGE == 0 & countnonblankelement != 0){//(i % NUMBER_PRODUCT_PER_PAGE == 0 & i != 0) {
					pagenumber++;
					bw = openNewXMLFile(ROOT_ELEMENT, filename + pagenumber, bw);

				}


			}
		} catch (IOException e) {
			LOG.error(e.getMessage());;
		} finally {
			writeClosingXML(ROOT_ELEMENT, bw);
			sqlSession.commit(false);
			sqlSession.close();
		}

		System.out.println("Elapsed seconds: "
				+ (System.currentTimeMillis() - lStartTime) / 1000.0);
	}

	private BufferedWriter openNewXMLFile(String rootelement, String filename,
			BufferedWriter bw) {
		writeClosingXML(rootelement, bw);
		bw = createXMLFile(filename);
		writeOpeningXML(rootelement, bw);
		return bw;
	}

	// close connection error.
	public void writeProductRegularImageURLs() {

		XStream xstream = new XStream();
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = getActivePropertyList();
		productFeedGenerator = productFeedGenerator.getInstance();
		for (int i = 0; i < productidlist.size(); i++) {
			productFeedGenerator.generateProductRegularImageURLs(sqlSession,
					productidlist.get(i));
		}
	}

	public void writeProductValues() {
		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Items.class);
		final SqlSession sqlSession = RazorServer.openSession();
		ArrayList<String> productidlist = getActivePropertyList();
		productFeedGenerator = productFeedGenerator.getInstance();
		for (int i = 0; i < productidlist.size(); i++) {
			System.out.println(xstream.toXML(productFeedGenerator
					.generateProductValues(productidlist.get(i), "POS")));
		}
	}


	public void writeListOrganizations() {
		String filename = "organizations";
		LOG.debug("OrganizationsList");
		partyFeedGenerator = partyFeedGenerator.getInstance();

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Items.class);

		BufferedWriter bw = createXMLFile(filename);

		try {

			bw.write(UTF8METADATA);bw.newLine();
			bw.write(xstream.toXML(partyFeedGenerator.generateProductValues(Constants.NO_POS)));

		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	private ArrayList<String> getActivePropertyList() {
		return RazorServer.openSession().getMapper(ProductMapper.class)
				.activeproductid();
	}

	public void writeImagesFromAPI() {


	}

	public void writeCountryNamesAndCode() {
		LOG.debug("writeCountryNamesAndCode");

		String filename = "countrynamecode";
		LocationFeedGenerator	locationfeedgenerator = null;
		locationfeedgenerator =  locationfeedgenerator.getInstance();

		BufferedWriter bw = createXMLFile(filename);

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.rest.Countries.class);

		try {
			bw.write(UTF8METADATA);
			bw.newLine();
			bw.write(xstream.toXML(locationfeedgenerator.generateCountryNamesAndCode()));
		}catch(IOException e){
			LOG.error(e.getMessage());
		}finally{
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}

		}
	}

	public void wrietActiveLocations(){

		LOG.debug("Active Locations");

		String filename = "location";
		LocationFeedGenerator	locationfeedgenerator = null;
		locationfeedgenerator =  locationfeedgenerator.getInstance();

		BufferedWriter bw = createXMLFile(filename);

		XStream xstream = new XStream();
		xstream.processAnnotations(net.cbtltd.shared.Location.class);

		try {
			bw.write(UTF8METADATA);
			bw.newLine();
			bw.write(xstream.toXML(locationfeedgenerator.generateActiveLocations()));
		}catch(IOException e){
			LOG.error(e.getMessage());
		}finally{
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}

		}

	}



	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Mybookingpal inquireReservation()");
	}
}