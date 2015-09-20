package net.cbtltd.rest.hotelscombined.rates;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.hotelscombined.HotelsCombinedUtils;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ.AvailStatusMessages;
import net.cbtltd.rest.hotelscombined.availability.domain.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage.Rates;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage.Rates.Rate;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage.Rates.Rate.BaseByGuestAmts;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage.Rates.Rate.BaseByGuestAmts.BaseByGuestAmt;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRateAmountNotifRQ.RateAmountMessages.RateAmountMessage.StatusApplicationControl;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRatePlanRQ;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRatePlanRQ.RatePlans;
import net.cbtltd.rest.hotelscombined.rates.domain.OTAHotelRatePlanRQ.RatePlans.RatePlan;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.integration.channel.ChannelIntegrationUtils;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.PriceExt;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Reservation.State;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class RateUtils {
	private static final Logger LOG = Logger.getLogger(RateUtils.class
			.getName());
	private boolean loadSelectedProducts = false;
	private List<ChannelProductMap> listToBeLoadedChannelProductMap = new ArrayList<ChannelProductMap>();
	private List<String> listInputProducts = new ArrayList<String>();
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private Integer fromPriceId;
	private Integer toPriceId;

	/**
	 * This method is fo bulk updates
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
							RazorConfig.getHotelsCombinedChannelPartnerId());
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
						.parseDateTime("2014-09-10").toDate());
				queryPrice.setTodate(dateTimeFormatter.parseDateTime(
						"2016-09-10").toDate());
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
			// get the calendar response for the product
			CalendarResponse calendarResponse = ChannelIntegrationUtils
					.getAvailabilityCalendar(channelProductMap.getProductId());

			// prepare the Rates request
			OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ = this
					.createRatesRequest(calendarResponse, listPrice,
							channelProductMap);
			
			// prepare the Availability request for Min lengthg of stay
			OTAHotelAvailNotifRQ otaHotelAvailNotifRQ = this
								.createAvailabilityRequest(calendarResponse, listPrice, channelProductMap);

			// call the HoetlsCombined Rates API
			String statusKey = channelProductMap.getProductId() + ","
					+ channelProductMap.getChannelProductId() + ","
					+ channelProductMap.getChannelRoomId();
			String statusCode="";
			statusCode = this.callRatesNotificationAPI(otaHotelRateAmountNotifRQ);
			statusCode=statusCode+"#"+this.callAvailabilityNotificationAPI(otaHotelAvailNotifRQ);
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
	
	public void getRates(String startDate,String endDate,String hotelCode,String ratePlanCode) throws Exception{
		OTAHotelRatePlanRQ otaHotelRatePlanRQ=new OTAHotelRatePlanRQ();
		otaHotelRatePlanRQ.setEchoToken(HotelsCombinedUtils.getGUID());
		otaHotelRatePlanRQ.setTimeStamp(HotelsCombinedUtils.getCurrentDateTimeInISOFormat());
		RatePlans ratePlans=new RatePlans();
		RatePlan ratePlan=new RatePlan();
		OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange dateRange=new OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange();
		
		dateRange.setStart(startDate);
		dateRange.setEnd(endDate);
		ratePlan.setDateRange(dateRange);
		
		OTAHotelRatePlanRQ.RatePlans.RatePlan.HotelRef hotelRef=new OTAHotelRatePlanRQ.RatePlans.RatePlan.HotelRef();
		hotelRef.setHotelCode(hotelCode);
		ratePlan.setHotelRef(hotelRef);
		
		OTAHotelRatePlanRQ.RatePlans.RatePlan.RatePlanCandidates ratePlanCandidates=new OTAHotelRatePlanRQ.RatePlans.RatePlan.RatePlanCandidates();
		OTAHotelRatePlanRQ.RatePlans.RatePlan.RatePlanCandidates.RatePlanCandidate ratePlanCandiate=new OTAHotelRatePlanRQ.RatePlans.RatePlan.RatePlanCandidates.RatePlanCandidate();
		ratePlanCandiate.setRatePlanCode(ratePlanCode);
		ratePlanCandidates.setRatePlanCandidate(ratePlanCandiate);
		ratePlan.setRatePlanCandidates(ratePlanCandidates);
		
		ratePlans.getRatePlan().add(ratePlan);
		
		otaHotelRatePlanRQ.setRatePlans(ratePlans);
		String response=this.callRatesFetchAPI(otaHotelRatePlanRQ);
		LOG.info("HotelsCombined Rates Fetch API Response "+response);
		
	}

	private OTAHotelRateAmountNotifRQ createRatesRequest(
			CalendarResponse calendarResponse, List<Price> listPrice,
			ChannelProductMap channelProductMap) {
		OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ=new OTAHotelRateAmountNotifRQ();
		otaHotelRateAmountNotifRQ.setEchoToken(HotelsCombinedUtils.getGUID());
		otaHotelRateAmountNotifRQ.setTimeStamp(HotelsCombinedUtils.getCurrentDateTimeInISOFormat());
		
		RateAmountMessages rateAmountMessages=new RateAmountMessages();
		rateAmountMessages.setHotelCode(channelProductMap.getChannelProductId());
		for(Price price:listPrice){
			RateAmountMessage rateAmountMessage=new RateAmountMessage();
			
			StatusApplicationControl statusApplicationControl=new StatusApplicationControl();
			
			statusApplicationControl.setStart(dateTimeFormatter.print(new DateTime(price
					.getDate())));
			statusApplicationControl.setEnd(dateTimeFormatter.print(new DateTime(price
					.getTodate())));
			statusApplicationControl.setRatePlanCode(channelProductMap.getChannelRateId());
			statusApplicationControl.setInvTypeCode(channelProductMap.getChannelRoomId());
			rateAmountMessage.setStatusApplicationControl(statusApplicationControl);
			
			Rates rates=new Rates();
			Rate rate=new Rate();
			BaseByGuestAmts baseByGuestAmts=new BaseByGuestAmts();
			
			BaseByGuestAmt baseByGuestAmt=new BaseByGuestAmt();
			baseByGuestAmt.setAgeQualifyingCode(HotelsCombinedUtils.HOTELS_COMBINED_ADULTS_CODE);
			baseByGuestAmt.setAmountAfterTax(price.getValue()+"");
			baseByGuestAmts.setBaseByGuestAmt(baseByGuestAmt);
			
			rate.setBaseByGuestAmts(baseByGuestAmts);
			rates.getRate().add(rate);
			
			rateAmountMessage.setRates(rates);
			
			rateAmountMessages.getRateAmountMessage().add(rateAmountMessage);
		}
		
		
		
		//set rateAmt messages in OTAHotelRateAmountNotifRQ object
		otaHotelRateAmountNotifRQ.setRateAmountMessages(rateAmountMessages);
		
		return otaHotelRateAmountNotifRQ;
	}
	
	private OTAHotelAvailNotifRQ createAvailabilityRequest(
			CalendarResponse calendarResponse, List<Price> listPrice,
			ChannelProductMap channelProductMap) {
		
		OTAHotelAvailNotifRQ otaHotelAvailNotifRQ=new OTAHotelAvailNotifRQ();
		otaHotelAvailNotifRQ.setEchoToken(HotelsCombinedUtils.getGUID());
		otaHotelAvailNotifRQ.setTimeStamp(HotelsCombinedUtils.getCurrentDateTimeInISOFormat());
		AvailStatusMessages availStatusMessages=new AvailStatusMessages();
		availStatusMessages.setHotelCode(channelProductMap.getChannelProductId());
		for(Price price:listPrice){
			AvailStatusMessage availStatusMessage=new AvailStatusMessage();
			availStatusMessage.setBookingLimit("1");
			
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl statusApplicationControl=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl();
			statusApplicationControl.setStart(dateTimeFormatter.print(new DateTime(price
					.getDate())));
			statusApplicationControl.setEnd(dateTimeFormatter.print(new DateTime(price
							.getTodate())));
			
			//checkin Rules...
			if(price.getRule().equalsIgnoreCase(Price.Rule.AnyCheckIn.name())){
				statusApplicationControl.setMon("1");
				statusApplicationControl.setTue("1");
				statusApplicationControl.setWeds("1");
				statusApplicationControl.setThur("1");
				statusApplicationControl.setFri("1");
				statusApplicationControl.setSat("1");
				statusApplicationControl.setSun("1");
			}else if(price.getRule().equalsIgnoreCase(Price.Rule.MonCheckIn.name())){
				statusApplicationControl.setMon("1");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("0");
			}
			else if(price.getRule().equalsIgnoreCase(Price.Rule.TueCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("1");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("0");
			}
			else if(price.getRule().equalsIgnoreCase(Price.Rule.WedCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("1");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("0");
			}
			else if(price.getRule().equalsIgnoreCase(Price.Rule.ThuCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("1");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("0");
			}else if(price.getRule().equalsIgnoreCase(Price.Rule.FriCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("1");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("0");
			}else if(price.getRule().equalsIgnoreCase(Price.Rule.SatCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("1");
				statusApplicationControl.setSun("0");
			}else if(price.getRule().equalsIgnoreCase(Price.Rule.SunCheckIn.name())){
				statusApplicationControl.setMon("0");
				statusApplicationControl.setTue("0");
				statusApplicationControl.setWeds("0");
				statusApplicationControl.setThur("0");
				statusApplicationControl.setFri("0");
				statusApplicationControl.setSat("0");
				statusApplicationControl.setSun("1");
			}
			statusApplicationControl.setInvTypeCode(channelProductMap.getChannelRoomId());
			statusApplicationControl.setRatePlanCode(channelProductMap.getChannelRateId());
			availStatusMessage.setStatusApplicationControl(statusApplicationControl);
			
			PriceExt priceExt=price.getPriceExt();
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.RestrictionStatus restrictionStatus=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.RestrictionStatus();
			boolean isRetrictionSet=false;
			if(priceExt!=null){
			if(priceExt.getClosedOnArrival()==1){
				restrictionStatus.setStatus("Open");
				restrictionStatus.setRestriction("Arrival");
				isRetrictionSet=true;
			}
			if(priceExt.getClosedOnDep()==1){
				restrictionStatus.setStatus("Open");
				restrictionStatus.setRestriction("Departure");
				isRetrictionSet=true;
			}
			if(priceExt.getClosed()==1){
				restrictionStatus.setStatus("Close");
				restrictionStatus.setRestriction("Master");
				isRetrictionSet=true;
			}
		}
			if(!isRetrictionSet){
				restrictionStatus.setStatus("Open");
				restrictionStatus.setRestriction("Master");
			}
			availStatusMessage.setRestrictionStatus(restrictionStatus);
			
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay lengthsOfStay=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay();
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay.LengthOfStay minLos=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay.LengthOfStay();
			OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay.LengthOfStay maxLos=new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.LengthsOfStay.LengthOfStay();
			// we use Minimum Revenue Value model to determine min stay for razor properties
			int minStay = 0;
			if(price.getUnit().equals(Unit.DAY)) {
				minStay = (int)(price.getMinimum()/price.getValue());
				minLos.setMinMaxMessageType("SetMinLOS");
				minLos.setTime(minStay+"");
				minLos.setTimeUnit("Day");
			}
			maxLos.setMinMaxMessageType("SetMaxLOS");
			maxLos.setTime(price.getMaxStay()+"");
			maxLos.setTimeUnit("Day");
			lengthsOfStay.getLengthOfStay().add(minLos);
			lengthsOfStay.getLengthOfStay().add(maxLos);
			availStatusMessage.getLengthsOfStay().add(lengthsOfStay);
			
			availStatusMessages.getAvailStatusMessage().add(availStatusMessage);
		}
		otaHotelAvailNotifRQ.getAvailStatusMessages().add(availStatusMessages);
		return otaHotelAvailNotifRQ;
		
	}
	
	

	private String callRatesNotificationAPI(
			OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelRateAmountNotifRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelRateAmountNotifRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Notification API Request " + xmlWriter);
	
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}
	
	private String callAvailabilityNotificationAPI(
			OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailNotifRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelAvailNotifRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Notification API Request " + xmlWriter);
	
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}
	
	private String callRatesFetchAPI(
			OTAHotelRatePlanRQ otaHotelRatePlanRQ) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelRatePlanRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(otaHotelRatePlanRQ, xmlWriter);
		LOG.info("HotelsCombined Rates Fetch API Request " + xmlWriter);
		
		
		return HotelsCombinedUtils.callHotelsCombinedAPI(xmlWriter.toString());

	}

	public boolean isLoadSelectedProducts() {
		return loadSelectedProducts;
	}

	public void setLoadSelectedProducts(boolean loadSelectedProducts) {
		this.loadSelectedProducts = loadSelectedProducts;
	}

	public List<ChannelProductMap> getListToBeLoadedChannelProductMap() {
		return listToBeLoadedChannelProductMap;
	}

	public void setListToBeLoadedChannelProductMap(
			List<ChannelProductMap> listToBeLoadedChannelProductMap) {
		this.listToBeLoadedChannelProductMap = listToBeLoadedChannelProductMap;
	}

	public List<String> getListInputProducts() {
		return listInputProducts;
	}

	public void setListInputProducts(List<String> listInputProducts) {
		this.listInputProducts = listInputProducts;
	}

	public Integer getFromPriceId() {
		return fromPriceId;
	}

	public void setFromPriceId(Integer fromPriceId) {
		this.fromPriceId = fromPriceId;
	}

	public Integer getToPriceId() {
		return toPriceId;
	}

	public void setToPriceId(Integer toPriceId) {
		this.toPriceId = toPriceId;
	}

}
