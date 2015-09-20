package net.cbtltd.rest.bookingcom.availability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Price.Rule;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * Util class and it contains util method to work with Price Object
 * 
 * @author nibodha
 *
 */
public class PriceUtils {
	// yyyy-MM-dd format
	private String fromDate;
	// yyyy-MM-dd format
	private String toDate;
	private static final Logger LOG = Logger.getLogger(AvailabilityUtils.class
			.getName());
	private List<Integer> dayOfTheWeekToBeUpdated;
	private Integer minStayToBeUpdated;
	private Integer maxStayToBeUpdated;
	private Double costToBeUpdated;
	private Double singleUserCostToBeUpdated;
	private String entityId;
	private String partyId;
	private String currency;



	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the partyId
	 */
	public String getPartyId() {
		return partyId;
	}

	/**
	 * @param partyId the partyId to set
	 */
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the dateTimeFormatter
	 */
	public DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	/**
	 * @param dateTimeFormatter the dateTimeFormatter to set
	 */
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	
	
	/**
	 * To generate the dates between the fromdate and todate and push into DB
	 */
	public void prepareDataLoad(){
		SqlSession sqlSession=null;
		try{
		 sqlSession = RazorServer.openSession();
		 this.insertUpdatePriceTable(sqlSession, generateDatesBetweenTwoDays(this.fromDate,this.toDate));
		sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
		}finally{
			if(sqlSession!=null){
				sqlSession.close();	
			}
			
		}
	}
	
	/**
	 * Update DB with the list of Dates
	 * @param sqlSession
	 * @param listDates
	 */
	private void insertUpdatePriceTable(SqlSession sqlSession,List<DateTime> listDates){
		LOG.info("Entering insertUpdatePriceTable");
		//only when different rate types are introduced a date an appear in more than one row else it can appear in only one row
		
		for(DateTime fromDate:listDates){
			Price queryPrice=new Price();
			queryPrice.setDate(fromDate.toDate());
			queryPrice.setEntityid(this.entityId);
			queryPrice.setPartyid(this.partyId);
			Price priceFromDB = sqlSession.getMapper(PriceMapper.class)
					.readByFromDate(queryPrice);
			if(priceFromDB!=null && priceFromDB.hasId()){
				priceFromDB=this.updatePriceDomianObject(priceFromDB);
				sqlSession.getMapper(PriceMapper.class).update(priceFromDB);
				LOG.info("Updated Price for date "+dateTimeFormatter.print(fromDate));
				
			}else{
				priceFromDB=this.updatePriceDomianObject(this.buildPriceDomianObject(fromDate));
				sqlSession.getMapper(PriceMapper.class).create(priceFromDB);
				LOG.info("Created Price for date "+dateTimeFormatter.print(fromDate));
			}
		}
		
		LOG.info("Exiting insertUpdatePriceTable");
	}
	
	/**
	 * Create Price object using dateTime object
	 * @param dateTime
	 * @return price
	 */
	private Price buildPriceDomianObject(DateTime dateTime){
		Price priceObj=new Price();
		priceObj.setEntitytype("Product");
		priceObj.setEntityid(this.entityId);
		priceObj.setPartyid(this.partyId);
		priceObj.setName("Standard Rate");
		priceObj.setState("Created");
		priceObj.setType("Reservation");
		priceObj.setDate(dateTime.toDate());
		priceObj.setTodate(dateTime.toDate());
		priceObj.setQuantity(1.0);
		priceObj.setUnit("DAY");
		priceObj.setFactor(1.0);
		priceObj.setCurrency(this.currency);
		priceObj.setAvailable(1);
		priceObj.setRule(Rule.AnyCheckIn.name());
		
		return priceObj;
	}
	
	/**
	 * To Update the price object with domain object values
	 * @param priceObj
	 * @return price
	 */
	private Price updatePriceDomianObject(Price priceObj){
		if(minStayToBeUpdated!=null){
			priceObj.setMinStay(minStayToBeUpdated);
		}
		if(maxStayToBeUpdated!=null){
			priceObj.setMaxStay(maxStayToBeUpdated);
		}
		if(costToBeUpdated!=null){
			priceObj.setValue(costToBeUpdated);
			priceObj.setCost(costToBeUpdated);
		}
		if(singleUserCostToBeUpdated!=null){
			priceObj.setMinimum(singleUserCostToBeUpdated);
			
		}
		return priceObj;
	}
	
	/**
	 * To generate the list of dates betweeen the fromdate and todate
	 * @param fromDate
	 * @param toDate
	 * @return list of DateTime
	 */
	private List<DateTime> generateDatesBetweenTwoDays(String fromDate,String toDate){
		LOG.info("Entering generateDatesBetweenTwoDays");
		List<DateTime> listDates=new ArrayList<DateTime>();
		DateTime dt1 = dateTimeFormatter.parseDateTime(fromDate);
		DateTime dt2 = dateTimeFormatter.parseDateTime(toDate);
		int diff = Days.daysBetween(dt1, dt2).getDays() + 1;
		LOG.info("Diff between two days is "+diff);
		for (int i = 0; i < diff; i++) {
			Map<QName, String> dateValue = new HashMap<QName, String>();
			DateTime dateTime = dt1
					.withFieldAdded(DurationFieldType.days(), i);
			if(this.dayOfTheWeekToBeUpdated.contains(dateTime.dayOfWeek().get())){
				listDates.add(dateTime);
			}
			
		}
		LOG.info("Before exiting generateDatesBetweenTwoDays "+listDates.size());
		return listDates;
		
	}

	

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the dayOfTheWeekToBeUpdated
	 */
	public List<Integer> getDayOfTheWeekToBeUpdated() {
		return dayOfTheWeekToBeUpdated;
	}

	/**
	 * @param dayOfTheWeekToBeUpdated the dayOfTheWeekToBeUpdated to set
	 */
	public void setDayOfTheWeekToBeUpdated(List<Integer> dayOfTheWeekToBeUpdated) {
		this.dayOfTheWeekToBeUpdated = dayOfTheWeekToBeUpdated;
	}

	/**
	 * @return the minStayToBeUpdated
	 */
	public Integer getMinStayToBeUpdated() {
		return minStayToBeUpdated;
	}

	/**
	 * @param minStayToBeUpdated the minStayToBeUpdated to set
	 */
	public void setMinStayToBeUpdated(Integer minStayToBeUpdated) {
		this.minStayToBeUpdated = minStayToBeUpdated;
	}

	/**
	 * @return the maxStayToBeUpdated
	 */
	public Integer getMaxStayToBeUpdated() {
		return maxStayToBeUpdated;
	}

	/**
	 * @param maxStayToBeUpdated the maxStayToBeUpdated to set
	 */
	public void setMaxStayToBeUpdated(Integer maxStayToBeUpdated) {
		this.maxStayToBeUpdated = maxStayToBeUpdated;
	}

	/**
	 * @return the costToBeUpdated
	 */
	public Double getCostToBeUpdated() {
		return costToBeUpdated;
	}

	/**
	 * @param costToBeUpdated the costToBeUpdated to set
	 */
	public void setCostToBeUpdated(Double costToBeUpdated) {
		this.costToBeUpdated = costToBeUpdated;
	}

	/**
	 * @return the singleUserCostToBeUpdated
	 */
	public Double getSingleUserCostToBeUpdated() {
		return singleUserCostToBeUpdated;
	}

	/**
	 * @param singleUserCostToBeUpdated the singleUserCostToBeUpdated to set
	 */
	public void setSingleUserCostToBeUpdated(Double singleUserCostToBeUpdated) {
		this.singleUserCostToBeUpdated = singleUserCostToBeUpdated;
	}

	

}
