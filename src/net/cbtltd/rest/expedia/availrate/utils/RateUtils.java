package net.cbtltd.rest.expedia.availrate.utils;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.expedia.availrate.domain.AvailRateUpdateRQ;
import net.cbtltd.rest.expedia.availrate.domain.AvailRateUpdateRS;
import net.cbtltd.rest.expedia.availrate.domain.DateRangeType;
import net.cbtltd.rest.expedia.availrate.domain.RatePlanType;
import net.cbtltd.rest.expedia.availrate.domain.RoomTypeType;
import net.cbtltd.rest.expedia.utils.ExpediaUtils;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.PriceExt;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;
public class RateUtils {
	private static final Logger LOG = Logger.getLogger(RateUtils.class
			.getName());
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private boolean loadSelectedProducts = false;
	private List<ChannelProductMap> listToBeLoadedChannelProductMap = new ArrayList<ChannelProductMap>();
	private List<String> listInputProducts = new ArrayList<String>();
	private Integer fromPriceId;
	private Integer toPriceId;
	
	/**
	 * This method is for bulk updates
	 * 
	 */
	public void notifyRates() {

		SqlSession sqlSession = RazorServer.openSession();
		try {
			List<ChannelProductMap> listChannelProductMap = new ArrayList<ChannelProductMap>();

			// Get the BP Hotel id from the mapping table and set the hotel id
			// here
			listChannelProductMap = ChannelProductService.getInstance()
					.readAllProductFromChannelProductMapper(sqlSession,
							RazorConfig.getExpediaChannelPartnerId());
			if (loadSelectedProducts) {
				for (ChannelProductMap channelProductMap : listChannelProductMap) {
					
					if (listInputProducts.contains(channelProductMap
							.getProductId())) {
						
						listToBeLoadedChannelProductMap.add(channelProductMap);
					}
				}
			} else {
				listToBeLoadedChannelProductMap = listChannelProductMap;
			}
			System.out.println("After filtering Products "+listToBeLoadedChannelProductMap);
			Map<ChannelProductMap, List<Price>> mapPrice = new HashMap<ChannelProductMap, List<Price>>();
			Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
			for (ChannelProductMap channelProductMap : listToBeLoadedChannelProductMap) {
				LOG.info("Loading rates for property id <"
						+ channelProductMap.getChannelProductId()
						+ ">.................");

				// get the Price entry for the product
				Price queryPrice = new Price();
				queryPrice.setEntityid(channelProductMap.getProductId());
				queryPrice.setDate(dateTimeFormatter
						.parseDateTime("2014-12-08").toDate());
				queryPrice.setTodate(dateTimeFormatter.parseDateTime(
						"2014-12-15").toDate());
				if ((this.fromPriceId != null && this.fromPriceId != 0)
						&& (this.toPriceId != null && this.toPriceId != 0)) {
					queryPrice.setIdFrom(fromPriceId);
					queryPrice.setIdTo(toPriceId);
				}

				List<Price> listPrice = sqlSession.getMapper(PriceMapper.class)
						.readByEntityId(queryPrice);

				if (listPrice != null) {
					LOG.info("Size of Price is " + listPrice.size());
				}
				if (listPrice != null && listPrice.size() > 0) {
					LOG.info("listPrice.size() " + listPrice.size());
					
					mapPrice.put(channelProductMap,listPrice);
				}

				

			}
			if(mapPrice!=null && !mapPrice.isEmpty()){
				for (Entry<ChannelProductMap, List<Price>> entry : mapPrice.entrySet()) {
					LOG.info("Processing Price for Product id "+entry.getKey().getProductId());
					mapStatusCodeMap.putAll(this.notifyRates(entry.getKey(),entry.getValue()));
				}
			
		
			}
			LOG.info("Bulk opertaion result is ");
			for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
				LOG.info("Upload status for <" + entry.getKey() + ">: "
						+ entry.getValue());
			}

		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}

	}
	
	/**
	 * This method will handle the publish/subscribe model.Bulk updates will use
	 * this method
	 * @param mapPrice
	 * @return
	 */
	public Map<String, String> notifyRates(Map<ChannelProductMap, List<Price>> mapPrice){
		System.out.println("Inisde notifyRates "+mapPrice.size());
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		for (Entry<ChannelProductMap, List<Price>> entry : mapPrice.entrySet()) {
			LOG.info("Processing Price for Product id "+entry.getKey().getProductId());
			mapStatusCodeMap.putAll(this.notifyRates(entry.getKey(),entry.getValue()));
		}
		for (Entry<String, String> entry : mapStatusCodeMap.entrySet()) {
			LOG.info("Upload status for <" + entry.getKey() + ">: "
					+ entry.getValue());
		}
		return mapStatusCodeMap;
	}

	/**
	 *
	 * 
	 * @param price
	 * @param channelProductMap
	 * @return
	 */
	public Map<String, String> notifyRates(ChannelProductMap channelProductMap,List<Price> listPrice) {
		SqlSession sqlSession = RazorServer.openSession();
		Map<String, String> mapStatusCodeMap = new HashMap<String, String>();
		try {
		

			// prepare the Rates request
			AvailRateUpdateRQ availRateUpdateRQ = this
					.createRatesRequest(listPrice,
							channelProductMap);
			
			
			// call the Expedia Rates API
			String statusKey = channelProductMap.getProductId() + ","
					+ channelProductMap.getChannelProductId() + ","
					+ channelProductMap.getChannelRoomId();
			String statusCode="";
			statusCode = this.callRatesUpdateAPI(availRateUpdateRQ);
			
			mapStatusCodeMap.put(statusKey, statusCode);
			LOG.info("statusKey " + statusKey + " statusCode " + statusCode);

		} catch (Exception e) {
			LOG.error(
					"Exception occured while calling booking com reservation api",
					e);
		} finally {
			sqlSession.commit();
			sqlSession.close();

		}
		return mapStatusCodeMap;
	}
	
	
	private AvailRateUpdateRQ createRatesRequest(List<Price> listPrice,
			ChannelProductMap channelProductMap) {
		//http://www.expediaquickconnect.com/EQC/AR/2011/06
		AvailRateUpdateRQ availRateUpdateRQ=new AvailRateUpdateRQ();
		
		
		AvailRateUpdateRQ.Authentication authentication=new AvailRateUpdateRQ.Authentication();
		//set authentication details
		authentication.setUsername(RazorConfig.getValue(ExpediaUtils.EXPEDIA_USERNAME,"testuser"));
		authentication.setPassword(RazorConfig.getValue(ExpediaUtils.EXPEDIA_PWD,"ECLPASS"));
		availRateUpdateRQ.setAuthentication(authentication);

		//set hotel details
		AvailRateUpdateRQ.Hotel hotel=new AvailRateUpdateRQ.Hotel();
		hotel.setId(Integer.parseInt(channelProductMap.getChannelProductId()));
		availRateUpdateRQ.setHotel(hotel);
		
		for(Price price:listPrice){
		//set AvailRateUpdate details
		AvailRateUpdateRQ.AvailRateUpdate availRateUpdate=new AvailRateUpdateRQ.AvailRateUpdate();
		
		//set dateRangeType
		DateRangeType dateRangeType=new DateRangeType();
		dateRangeType.setFrom(dateTimeFormatter.print(new DateTime(price.getDate())));
		dateRangeType.setTo(dateTimeFormatter.print(new DateTime(price.getTodate())));
		availRateUpdate.getDateRange().add(dateRangeType);
		
		//set roomType details
		RoomTypeType roomTypeType=new RoomTypeType();
		roomTypeType.setId(channelProductMap.getChannelRoomId());
		roomTypeType.setClosed(false);
		
		//set Inventory details
		RoomTypeType.Inventory inv=new RoomTypeType.Inventory();
		inv.setTotalInventoryAvailable(price.getAvailable());
		roomTypeType.setInventory(inv);
		
		//set Rate Plan details
		RoomTypeType.RatePlan ratePlan=new RoomTypeType.RatePlan();
		ratePlan.setId(channelProductMap.getChannelRateId());
		ratePlan.setClosed(false);
		
		//set rate details
		RatePlanType.Rate rate=new RatePlanType.Rate();
		rate.setCurrency(price.getCurrency());
		RatePlanType.Rate.PerDay perDay=new RatePlanType.Rate.PerDay();
		perDay.setRate(new BigDecimal(price.getValue()));
		rate.setPerDay(perDay);
		
		
		ratePlan.getRate().add(rate);
		
		RatePlanType.Restrictions restrictions=new RatePlanType.Restrictions();
		int minStay = 0;
		if(price.getUnit().equals(Unit.DAY)) {
			minStay = (int)(price.getMinimum()/price.getValue());
			}
		restrictions.setMinLOS(minStay);
		restrictions.setMaxLOS(price.getMaxStay());
		PriceExt priceExt=price.getPriceExt();
		if(priceExt!=null){
			if(priceExt.getClosedOnArrival()==1){
				restrictions.setClosedToArrival(true);
			}else{
				restrictions.setClosedToArrival(false);
			}
			if(priceExt.getClosedOnDep()==1){
				restrictions.setClosedToDeparture(true);
			}else{
				restrictions.setClosedToArrival(false);
			}
			
		}
		ratePlan.setRestrictions(restrictions);
		
		roomTypeType.getRatePlan().add(ratePlan);
		
		
		availRateUpdate.getRoomType().add(roomTypeType);
		
		
		
		
		availRateUpdateRQ.getAvailRateUpdate().add(availRateUpdate);
		}
		
		return availRateUpdateRQ;
	}
	
	
	

	private String callRatesUpdateAPI(
			AvailRateUpdateRQ availRateUpdateRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(AvailRateUpdateRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(availRateUpdateRQ, xmlWriter);
		//LOG.info("Expedia Rates Update API Request " + xmlWriter);
		return ExpediaUtils.callExpediaAPI(xmlWriter.toString(),RazorConfig.getValue(ExpediaUtils.EXPEDIA_AR_URL,"https://simulator.expediaquickconnect.com/connect/ar"),true);

	}

	public boolean isLoadSelectedProducts() {
		return loadSelectedProducts;
	}

	public void setLoadSelectedProducts(boolean loadSelectedProducts) {
		this.loadSelectedProducts = loadSelectedProducts;
	}

	public List<String> getListInputProducts() {
		return listInputProducts;
	}

	public void setListInputProducts(List<String> listInputProducts) {
		this.listInputProducts = listInputProducts;
	}
	
}
