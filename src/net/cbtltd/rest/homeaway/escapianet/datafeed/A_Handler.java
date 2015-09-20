package net.cbtltd.rest.homeaway.escapianet.datafeed;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.cbtltd.rest.homeaway.EscapiaNetUtils;
import net.cbtltd.rest.homeaway.EscapiaNetUtils.AvailabilityType;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.integration.ImageService;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.server.integration.PriceExtService;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.PropertyService;
import net.cbtltd.server.integration.RelationService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.PriceExt;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.price.PriceCreate;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.escapia.evrn._2007._02.AddressType;
import com.escapia.evrn._2007._02.ArrayOfCalendarAvailType;
import com.escapia.evrn._2007._02.ArrayOfEVRNPoliciesTypePolicy.Policy;
import com.escapia.evrn._2007._02.ArrayOfRateRangeType;
import com.escapia.evrn._2007._02.ArrayOfSourceType;
import com.escapia.evrn._2007._02.ArrayOfUnitDescriptiveInfoRequestType;
import com.escapia.evrn._2007._02.ArrayOfUnitInfoTypeService.Service;
import com.escapia.evrn._2007._02.CalendarAvailType;
import com.escapia.evrn._2007._02.EVRNCategoryCodesType.RoomInfo;
import com.escapia.evrn._2007._02.EVRNContentService;
import com.escapia.evrn._2007._02.EVRNUnitCalendarAvailBatchRQ;
import com.escapia.evrn._2007._02.EVRNUnitCalendarAvailBatchRS;
import com.escapia.evrn._2007._02.EVRNUnitCalendarAvailBatchRSType.UnitCalendarAvails;
import com.escapia.evrn._2007._02.EVRNUnitDescriptiveInfoRQ;
import com.escapia.evrn._2007._02.ImageDescriptionType.ImageFormat;
import com.escapia.evrn._2007._02.ImageItemsType.ImageItem;
import com.escapia.evrn._2007._02.MultimediaDescriptionType;
import com.escapia.evrn._2007._02.RateRangeType;
import com.escapia.evrn._2007._02.SourceType;
import com.escapia.evrn._2007._02.SourceType.RequestorID;
import com.escapia.evrn._2007._02.TextItemsType.TextItem;
import com.escapia.evrn._2007._02.ArrayOfStayRequirementType;
import com.escapia.evrn._2007._02.UnitCalendarAvailSegmentType;
import com.escapia.evrn._2007._02.UnitCalendarAvailType;
import com.escapia.evrn._2007._02.UnitDescriptiveContentType;
import com.escapia.evrn._2007._02.UnitDescriptiveInfoRequestType;
import com.escapia.evrn._2007._02.UnitDescriptiveInfoStream.EVRNUnitDescriptiveInfoRS;
import com.escapia.evrn._2007._02.UnitDescriptiveInfoStream.EVRNUnitDescriptiveInfoRS.UnitDescriptiveContents;
import com.escapia.evrn._2007._02.UnitInfoType;
import com.google.gwt.thirdparty.guava.common.collect.ArrayListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.mybookingpal.config.RazorConfig;

public class A_Handler extends PartnerHandler implements IsPartner {
	
	public A_Handler(Partner partner) {
		super(partner);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		return false;
	}
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		// TODO Auto-generated method stub
		return null;
	}
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	public void readAlerts() {
		// TODO Auto-generated method stub
		
	}
	public void readPrices() {
		EVRNContentService evrnContentService = new EVRNContentService(getUrl());
		EVRNUnitCalendarAvailBatchRQ evrnUnitCalendarAvailBatchRQ = new EVRNUnitCalendarAvailBatchRQ();
		evrnUnitCalendarAvailBatchRQ.setVersion(new BigDecimal(1.0));
		ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
		SourceType sourceType = new SourceType();
		RequestorID requestorID = new RequestorID();
		requestorID.setID(RazorConfig.getValue(EscapiaNetUtils.ESCAPIA_USER));
		requestorID.setMessagePassword(RazorConfig.getValue(EscapiaNetUtils.ESCAPIA_PWD));
		sourceType.setRequestorID(requestorID);
		arrayOfSourceType.getSource().add(sourceType);
		evrnUnitCalendarAvailBatchRQ.setPOS(arrayOfSourceType);
		ArrayOfCalendarAvailType aoc = new ArrayOfCalendarAvailType();
		CalendarAvailType calendarAvailType = new CalendarAvailType();
		calendarAvailType.getCountryCodeList().add(EscapiaNetUtils.US_COUNTRY_CODE);
		aoc.getCalendarAvail().add(calendarAvailType);
		evrnUnitCalendarAvailBatchRQ.setCalenadAvails(aoc);
		EVRNUnitCalendarAvailBatchRS evrnUnitCalendarAvailBatchRS = evrnContentService.getBasicHttpBindingIEVRNContentService().unitCalendarAvailBatch(evrnUnitCalendarAvailBatchRQ);
		if(evrnUnitCalendarAvailBatchRS.getEVRNUnitCalendarAvailBatchRS() != null && evrnUnitCalendarAvailBatchRS.getEVRNUnitCalendarAvailBatchRS().getUnitCalendarAvailsOrErrorsOrWarnings() != null && !evrnUnitCalendarAvailBatchRS.getEVRNUnitCalendarAvailBatchRS().getUnitCalendarAvailsOrErrorsOrWarnings().isEmpty()){
			EVRNUnitDescriptiveInfoRS evrnUnitDescriptiveInfoRS = getEVRNUnitDescriptiveInfoRSForProducts();
			Map<String, ArrayOfRateRangeType> rateRangesWithUnitCode = getRateRangesWithUnitCode(evrnUnitDescriptiveInfoRS);
			List<Object> objects = evrnUnitCalendarAvailBatchRS.getEVRNUnitCalendarAvailBatchRS().getUnitCalendarAvailsOrErrorsOrWarnings();
			SqlSession sqlSession = RazorServer.openSession();
			try{
				for(Object obj : objects){
					if(! (obj instanceof UnitCalendarAvails)) continue;
					UnitCalendarAvails unitCalendarAvails = (UnitCalendarAvails) obj;
					List<UnitCalendarAvailType> unitCalendarAvailTypes = unitCalendarAvails.getUnitCalendarAvail();
					if(unitCalendarAvailTypes != null && !unitCalendarAvailTypes.isEmpty()){
						// Available entry
						persistAvailableEntries(rateRangesWithUnitCode, sqlSession,
								unitCalendarAvailTypes);

						// Unavailable entry
						persistUnavailableEntries(sqlSession, unitCalendarAvailTypes);
					}
					sqlSession.commit();
				}
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		
	}
	private void persistUnavailableEntries(SqlSession sqlSession,
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Product product;
		Multimap<String, Date> unavailableDatesWithUnitCode = getUnavailableDatesWithUnitCode(unitCalendarAvailTypes);
		for (final String key : unavailableDatesWithUnitCode.keySet()) {
			try {
				product = ProductService.getInstance().getProduct(sqlSession,
						getAltpartyid(), EscapiaNetUtils.removeSpecialCharacters(String
								.valueOf(key)));
				if (product != null) {
					Collection<Date> bookedDates = unavailableDatesWithUnitCode.get(key);
					persistBookedDate(sqlSession, product,
							bookedDates);
				}
				sqlSession.commit();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				sqlSession.rollback();
			}
		}
	}
	
	/**
	 * @param sqlSession
	 * @param product
	 * @param listBookedDate
	 * 
	 *            if the date for that particular property already exists then
	 *            remove that entry from database and make a new entry
	 */
	private void persistBookedDate(SqlSession sqlSession, Product product,
			Collection<Date> listBookedDate) {
		List<Date> reservationList = PartnerService.getReservationList(
				sqlSession, product.getId());

		for (Date bkDate : listBookedDate) {
			if (!reservationList.contains(bkDate)) {
				PartnerService.createSchedule(sqlSession, product, bkDate,
						bkDate, new Date());
			}
		}
		for (Date cdDate : reservationList) {
			if (!listBookedDate.contains(cdDate)) {
				PartnerService.deleteReservation(sqlSession, product.getId(),
						cdDate);
			}
		}
	}
	public void persistAvailableEntries(
			Map<String, ArrayOfRateRangeType> rateRangesWithUnitCode,
			SqlSession sqlSession,
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Multimap<String, Date> availableDatesWithUnitCode = getAvailableDatesWithUnitCode(unitCalendarAvailTypes);
		Multimap<String, Date> availableDatesForCheckinWithUnitCode = getAvailableDatesForCheckinWithUnitCode(unitCalendarAvailTypes);
		Multimap<String, Date> availableDatesForCheckoutWithUnitCode = getAvailableDatesForCheckoutWithUnitCode(unitCalendarAvailTypes);
		Map<String, ArrayOfStayRequirementType> stayRequirementsWithUnitCode = getStayRequirementsWithUnitCode(unitCalendarAvailTypes);
		if(availableDatesWithUnitCode != null){
			persistPriceWithAvailability(rateRangesWithUnitCode, sqlSession,
					availableDatesWithUnitCode, stayRequirementsWithUnitCode, AvailabilityType.AVAILABLE);
		}
		if(availableDatesForCheckinWithUnitCode != null){
			persistPriceWithAvailability(rateRangesWithUnitCode, sqlSession,
					availableDatesForCheckinWithUnitCode, stayRequirementsWithUnitCode, AvailabilityType.CHECKIN);
		}
		if(availableDatesForCheckoutWithUnitCode != null){
			persistPriceWithAvailability(rateRangesWithUnitCode, sqlSession,
					availableDatesForCheckoutWithUnitCode, stayRequirementsWithUnitCode, AvailabilityType.CHECKOUT);
		}
	}
	public void persistPriceWithAvailability(
			Map<String, ArrayOfRateRangeType> rateRangesWithUnitCode,
			SqlSession sqlSession,
			Multimap<String, Date> availableDatesWithUnitCode,
			Map<String, ArrayOfStayRequirementType> stayRequirementsWithUnitCode, AvailabilityType availabilityType) {
		Product product;
		PriceCreate priceCreate;
		/**
		 * Persist price table based on the available dates for the
		 * property
		 */
		for (final String key : availableDatesWithUnitCode.keySet()) {
			try {
				product = ProductService.getInstance().getProduct(sqlSession,
						getAltpartyid(), EscapiaNetUtils.removeSpecialCharacters(String
								.valueOf(key))); 
				List<PriceCreate> priceCreates = new ArrayList<PriceCreate>();
				if (product != null) {
					Collection<Date> availableDates = availableDatesWithUnitCode
							.get(key);

					priceCreate = new PriceCreate();
					priceCreate.setAltid(key);
					priceCreate.setAltpartyid(getAltpartyid());
					priceCreate.setType(NameId.Type.Reservation.name());
					priceCreate.setOrganizationid(getAltpartyid());
					priceCreate.setAvailable(1);
					priceCreate.setFactor(1.0);
					priceCreate
							.setState(net.cbtltd.shared.Price.CREATED);
					priceCreate.setCurrency(EscapiaNetUtils.CURRENCY);
					priceCreate.setName("Daily Rates");
					priceCreate.setUnit(Unit.DAY);
					if(stayRequirementsWithUnitCode.containsKey(key)){
						priceCreate.setMinStay(getMinStayValue(stayRequirementsWithUnitCode.get(key)));
						priceCreate.setMaxStay(getMaxStayValue(stayRequirementsWithUnitCode.get(key)));
					}
					
					for (Date availDate : availableDates) {
						priceCreate.setDate(availDate);
						priceCreate.setTodate(availDate);
						priceCreate.setRule(EscapiaNetUtils
								.getDayOfWeek(availDate));
						if (rateRangesWithUnitCode.containsKey(key)) {
							buildMinMaxAndCostForPriceCreate(priceCreate, availDate, rateRangesWithUnitCode.get(key));
						} else {
							priceCreate.setCost(0.0);
							priceCreate.setValue(0.0);
						}
						priceCreates.add(priceCreate);
						if(availabilityType != AvailabilityType.AVAILABLE){
							PriceExt priceExt = new PriceExt();
							priceExt.setPriceID(Integer.parseInt(priceCreate.getId()));
							priceExt.setVersion(new Date());
							if(availabilityType == AvailabilityType.CHECKIN){
								priceExt.setClosedOnArrival(0);
								priceExt.setClosedOnDep(1);
							}else if(availabilityType == AvailabilityType.CHECKOUT){
								priceExt.setClosedOnArrival(1);
								priceExt.setClosedOnDep(0);
							}
							persistPriceExt(sqlSession,priceExt);
						}
					}
					if(priceCreates != null && !priceCreates.isEmpty()){
						PriceService.getInstance().persistPriceData(sqlSession, product,
								priceCreates);
						
					}
				}
				sqlSession.commit();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				sqlSession.rollback();
			}
		}
	}
	
	
	private void persistPriceExt(SqlSession sqlSession, PriceExt priceExt) {
		// remove the records from price_ext table
		PriceExtService.getInstance().remove(sqlSession, String.valueOf(priceExt.getPriceID()));
		PriceExtService.getInstance().create(sqlSession, priceExt);
		
	}
	private Integer getMaxStayValue(
			ArrayOfStayRequirementType arrayOfStayRequirementType) {
		if(arrayOfStayRequirementType != null && arrayOfStayRequirementType.getStayRequirement() != null && !arrayOfStayRequirementType.getStayRequirement().isEmpty()){
			return arrayOfStayRequirementType.getStayRequirement().get(0).getMaxLOS().intValue();
		}
		return 0;
	}
	private Integer getMinStayValue(
			ArrayOfStayRequirementType arrayOfStayRequirementType) {
		if(arrayOfStayRequirementType != null && arrayOfStayRequirementType.getStayRequirement() != null && !arrayOfStayRequirementType.getStayRequirement().isEmpty()){
			return arrayOfStayRequirementType.getStayRequirement().get(0).getMinLOS().intValue();
		}
		return 0;
	}
	private Map<String, ArrayOfStayRequirementType> getStayRequirementsWithUnitCode(
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Map<String, ArrayOfStayRequirementType> stayRequirementsWithUnitCode = new HashMap<String, ArrayOfStayRequirementType>();
		for (UnitCalendarAvailType unitCalendarAvailType : unitCalendarAvailTypes) {
			if(unitCalendarAvailType != null && unitCalendarAvailType.getUnitCalendarAvailSegment() != null && !unitCalendarAvailType.getUnitCalendarAvailSegment().isEmpty()){
				List<UnitCalendarAvailSegmentType> unitCalendarAvailSegmentTypes = unitCalendarAvailType.getUnitCalendarAvailSegment();
				for (UnitCalendarAvailSegmentType unitCalendarAvailSegmentType : unitCalendarAvailSegmentTypes) {
					stayRequirementsWithUnitCode.put(unitCalendarAvailType.getUnitCode(), unitCalendarAvailSegmentType.getStayRequirements());
				}
			}
		}
		return stayRequirementsWithUnitCode;
	}
	private void buildMinMaxAndCostForPriceCreate(PriceCreate priceCreate,
			Date availDate, ArrayOfRateRangeType arrayOfRateRangeType) {
		if(arrayOfRateRangeType != null && arrayOfRateRangeType.getRateRange() != null && !arrayOfRateRangeType.getRateRange().isEmpty()){
			List<RateRangeType> rateRangeTypes = arrayOfRateRangeType.getRateRange();
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EscapiaNetUtils.MONTH_DAY_YEAR);
		        GregorianCalendar startDate, endDate;
		        Date date;
			for (RateRangeType rateRangeType : rateRangeTypes) {
				// below line converting our string to date
				date = simpleDateFormat.parse(rateRangeType.getStart());
				// here we are getting the object of GregorianCalendar class
				startDate =(GregorianCalendar) GregorianCalendar.getInstance();
				// by below we are setting the time of date into gregorianCalendar
				startDate.setTime(date);

				date = simpleDateFormat.parse(rateRangeType.getEnd());
				endDate =(GregorianCalendar) GregorianCalendar.getInstance();
				endDate.setTime(date);
				if((startDate.getTime().before(availDate) && startDate.getTime().after(availDate)) || startDate.getTime().equals(availDate) || endDate.getTime().equals(availDate)){
					priceCreate.setMinimum(rateRangeType.getMinRate().doubleValue());
					priceCreate.setCost(rateRangeType.getMaxRate().doubleValue());
					priceCreate.setValue(rateRangeType.getMaxRate().doubleValue());
				}
			}
			} catch (Exception ex) {
				LOG.error("Exception in String to Greg Calander Conversion. " + ex.getMessage(), ex);
	        }
		}
		
	}
	private Map<String, ArrayOfRateRangeType> getRateRangesWithUnitCode(
			EVRNUnitDescriptiveInfoRS evrnUnitDescriptiveInfoRS) {
		Map<String, ArrayOfRateRangeType> rateRangesWithUnitCode = null;
		if(evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess() != null && !evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess().isEmpty()){
			List<Object> objects = evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess();
			for(Object obj : objects){
				if(! (obj instanceof UnitDescriptiveContents)) continue;
				rateRangesWithUnitCode = new HashMap<String, ArrayOfRateRangeType>();
				UnitDescriptiveContents unitDescriptiveContents = (UnitDescriptiveContents)obj;
				List<UnitDescriptiveContentType> unitDescriptiveContentTypes = unitDescriptiveContents.getUnitDescriptiveContent();
				if(unitDescriptiveContentTypes != null && !unitDescriptiveContentTypes.isEmpty()){
					for (UnitDescriptiveContentType unitDescriptiveContentType : unitDescriptiveContentTypes) {
						if(unitDescriptiveContentType.getUnitInfo() != null && unitDescriptiveContentType.getUnitInfo().getRateRanges() != null){
							rateRangesWithUnitCode.put(unitDescriptiveContentType.getUnitCode(), unitDescriptiveContentType.getUnitInfo().getRateRanges());
						}
					}
				}
			}
		}
		return rateRangesWithUnitCode;
	}

	public Multimap<String, Date> getAvailableDatesWithUnitCode(
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Multimap<String, Date> availableDatesWithUnitCode = ArrayListMultimap
				.create();
		GregorianCalendar startDate = null;
		for (UnitCalendarAvailType unitCalendarAvailType : unitCalendarAvailTypes) {
			if(unitCalendarAvailType != null && unitCalendarAvailType.getUnitCalendarAvailSegment() != null && !unitCalendarAvailType.getUnitCalendarAvailSegment().isEmpty()){
				List<UnitCalendarAvailSegmentType> unitCalendarAvailSegmentTypes = unitCalendarAvailType.getUnitCalendarAvailSegment();
				for (UnitCalendarAvailSegmentType unitCalendarAvailSegmentType : unitCalendarAvailSegmentTypes) {
					String availability = unitCalendarAvailSegmentType.getDailyAvailability();
					startDate = unitCalendarAvailSegmentType.getStartDate().toGregorianCalendar();
					for (int index = 0; index < availability.length(); index++, startDate
							.add(Calendar.DAY_OF_MONTH, 1)) {
						if ("A".equalsIgnoreCase("" + availability.charAt(index))) {
							availableDatesWithUnitCode.put(unitCalendarAvailType.getUnitCode(), startDate.getTime());
						}
					}
				}
			}
		}
		return availableDatesWithUnitCode;
	}
	
	public Multimap<String, Date> getUnavailableDatesWithUnitCode(
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Multimap<String, Date> unavailableDatesWithUnitCode = ArrayListMultimap
				.create();
		GregorianCalendar startDate = null;
		for (UnitCalendarAvailType unitCalendarAvailType : unitCalendarAvailTypes) {
			if(unitCalendarAvailType != null && unitCalendarAvailType.getUnitCalendarAvailSegment() != null && !unitCalendarAvailType.getUnitCalendarAvailSegment().isEmpty()){
				List<UnitCalendarAvailSegmentType> unitCalendarAvailSegmentTypes = unitCalendarAvailType.getUnitCalendarAvailSegment();
				for (UnitCalendarAvailSegmentType unitCalendarAvailSegmentType : unitCalendarAvailSegmentTypes) {
					String availability = unitCalendarAvailSegmentType.getDailyAvailability();
					startDate = unitCalendarAvailSegmentType.getStartDate().toGregorianCalendar();
					for (int index = 0; index < availability.length(); index++, startDate
							.add(Calendar.DAY_OF_MONTH, 1)) {
						if ("U".equalsIgnoreCase("" + availability.charAt(index))) {
							unavailableDatesWithUnitCode.put(unitCalendarAvailType.getUnitCode(), startDate.getTime());
						}
					}
				}
			}
		}
		return unavailableDatesWithUnitCode;
	}
	
	public Multimap<String, Date> getAvailableDatesForCheckinWithUnitCode(
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Multimap<String, Date> availableDatesForCheckinWithUnitCode = ArrayListMultimap
				.create();
		GregorianCalendar startDate = null;
		for (UnitCalendarAvailType unitCalendarAvailType : unitCalendarAvailTypes) {
			if(unitCalendarAvailType != null && unitCalendarAvailType.getUnitCalendarAvailSegment() != null && !unitCalendarAvailType.getUnitCalendarAvailSegment().isEmpty()){
				List<UnitCalendarAvailSegmentType> unitCalendarAvailSegmentTypes = unitCalendarAvailType.getUnitCalendarAvailSegment();
				for (UnitCalendarAvailSegmentType unitCalendarAvailSegmentType : unitCalendarAvailSegmentTypes) {
					String availability = unitCalendarAvailSegmentType.getDailyAvailability();
					startDate = unitCalendarAvailSegmentType.getStartDate().toGregorianCalendar();
					for (int index = 0; index < availability.length(); index++, startDate
							.add(Calendar.DAY_OF_MONTH, 1)) {
						if ("I".equalsIgnoreCase("" + availability.charAt(index))) {
							availableDatesForCheckinWithUnitCode.put(unitCalendarAvailType.getUnitCode(), startDate.getTime());
						}
					}
				}
			}
		}
		return availableDatesForCheckinWithUnitCode;
	}
	
	public Multimap<String, Date> getAvailableDatesForCheckoutWithUnitCode(
			List<UnitCalendarAvailType> unitCalendarAvailTypes) {
		Multimap<String, Date> availableDatesForCheckoutWithUnitCode = ArrayListMultimap
				.create();
		GregorianCalendar startDate = null;
		for (UnitCalendarAvailType unitCalendarAvailType : unitCalendarAvailTypes) {
			if(unitCalendarAvailType != null && unitCalendarAvailType.getUnitCalendarAvailSegment() != null && !unitCalendarAvailType.getUnitCalendarAvailSegment().isEmpty()){
				List<UnitCalendarAvailSegmentType> unitCalendarAvailSegmentTypes = unitCalendarAvailType.getUnitCalendarAvailSegment();
				for (UnitCalendarAvailSegmentType unitCalendarAvailSegmentType : unitCalendarAvailSegmentTypes) {
					String availability = unitCalendarAvailSegmentType.getDailyAvailability();
					startDate = unitCalendarAvailSegmentType.getStartDate().toGregorianCalendar();
					for (int index = 0; index < availability.length(); index++, startDate
							.add(Calendar.DAY_OF_MONTH, 1)) {
						if ("O".equalsIgnoreCase("" + availability.charAt(index))) {
							availableDatesForCheckoutWithUnitCode.put(unitCalendarAvailType.getUnitCode(), startDate.getTime());
						}
					}
				}
			}
		}
		return availableDatesForCheckoutWithUnitCode;
	}
	
	public URL getUrl() {
		try {
			return new URL(RazorConfig.getValue(EscapiaNetUtils.ESCAPIA_WSDL_URL));
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
		}
		return new EVRNContentService().getWSDLDocumentLocation();
	}
	public void readProducts() {
		EVRNUnitDescriptiveInfoRS evrnUnitDescriptiveInfoRS = getEVRNUnitDescriptiveInfoRSForProducts();
		if(evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess() != null && !evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess().isEmpty()){
			List<Object> objects = evrnUnitDescriptiveInfoRS.getWarningsOrErrorsOrSuccess();
			for(Object obj : objects){
				if(! (obj instanceof UnitDescriptiveContents)) continue;
				UnitDescriptiveContents unitDescriptiveContents = (UnitDescriptiveContents)obj;
				List<UnitDescriptiveContentType> unitDescriptiveContentTypes = unitDescriptiveContents.getUnitDescriptiveContent();
				if(unitDescriptiveContentTypes != null && !unitDescriptiveContentTypes.isEmpty()){
					saveUnitDescriptiveContentTypes(unitDescriptiveContentTypes);
				}
				
			}
		}
	}
	public EVRNUnitDescriptiveInfoRS getEVRNUnitDescriptiveInfoRSForProducts() {
		EVRNContentService evrnContentService = new EVRNContentService(getUrl());
		EVRNUnitDescriptiveInfoRQ evrnUnitDescriptiveInfoRQ = new EVRNUnitDescriptiveInfoRQ();
		evrnUnitDescriptiveInfoRQ.setVersion(new BigDecimal(1.0));
		//evrnUnitDescriptiveInfoRQ.setSummaryOnly(true);
		ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
		SourceType sourceType = new SourceType();
		RequestorID requestorID = new RequestorID();
		requestorID.setID(RazorConfig.getValue(EscapiaNetUtils.ESCAPIA_USER));
		requestorID.setMessagePassword(RazorConfig.getValue(EscapiaNetUtils.ESCAPIA_PWD));
		sourceType.setRequestorID(requestorID);
		arrayOfSourceType.getSource().add(sourceType);
		evrnUnitDescriptiveInfoRQ.setPOS(arrayOfSourceType);
		UnitDescriptiveInfoRequestType unitDescriptiveInfoRequestType = new UnitDescriptiveInfoRequestType();
		unitDescriptiveInfoRequestType.getCountryCodeList().add(EscapiaNetUtils.US_COUNTRY_CODE);
		ArrayOfUnitDescriptiveInfoRequestType arrayOfUnitDescriptiveInfoRequestType = new ArrayOfUnitDescriptiveInfoRequestType();
		arrayOfUnitDescriptiveInfoRequestType.getUnitDescriptiveInfo().add(unitDescriptiveInfoRequestType);
		evrnUnitDescriptiveInfoRQ.setUnitDescriptiveInfos(arrayOfUnitDescriptiveInfoRequestType);
		EVRNUnitDescriptiveInfoRS evrnUnitDescriptiveInfoRS = evrnContentService.getBasicHttpBindingIEVRNContentService().unitDescriptiveInfoStream(evrnUnitDescriptiveInfoRQ).getEVRNUnitDescriptiveInfoRS();
		return evrnUnitDescriptiveInfoRS;
	}
	private void saveUnitDescriptiveContentTypes(
			List<UnitDescriptiveContentType> unitDescriptiveContentTypes) {
		Product product = null;
		List<String> attributes = null;
		String altPartyId = getAltpartyid();
		boolean isNewProduct = false;
		SqlSession sqlSession = RazorServer.openSession();
		int persistedPropCount = 0;
		LOG.info("Total number of properties available are "
				+ unitDescriptiveContentTypes.size());
		try {
			for (UnitDescriptiveContentType unitDescriptiveContent : unitDescriptiveContentTypes) {
				try {
					if (unitDescriptiveContent != null) {
						LOG.info("Processing for the unitDescriptiveContent with unit code : "
								+ unitDescriptiveContent.getUnitCode());
						product = ProductService.getInstance().getProduct(sqlSession,
								altPartyId, EscapiaNetUtils.removeSpecialCharacters(unitDescriptiveContent.getUnitCode()));
						if(product != null){
							if(unitDescriptiveContent.getUnitInfo() != null){
								Date lastUpdated = unitDescriptiveContent.getUnitInfo().getLastUpdated().toGregorianCalendar().getTime();
								if((product.getVersion().after(lastUpdated) || product.getVersion().equals(lastUpdated))) return;
							}
							else isNewProduct = false;
						}
						if (product == null) {
							isNewProduct = true;
							product = new Product();
						}
						// location
						buildLocationDetails(product, sqlSession,
								unitDescriptiveContent);

						product.setAltid(EscapiaNetUtils.removeSpecialCharacters(unitDescriptiveContent.getUnitCode()));
						product.setAltpartyid(altPartyId);
						product.setState(Product.CREATED);
						product.setType(Product.Type.Accommodation.name());
						product.setUnit(Unit.DAY);
						product.setOwnerid(altPartyId);
						product.setAltSupplierId(altPartyId);
						product.setSupplierid(altPartyId);
						product.setOwnerid(altPartyId);
						product.setQuantity(1);
						product.setVersion(new Date());
						product.setInquireState(Product.USE_API);
						product.setPhysicaladdress(buildAddress(unitDescriptiveContent.getUnitInfo().getAddress()));
						
						if(unitDescriptiveContent.getUnitInfo() != null){
							UnitInfoType unitInfoType = unitDescriptiveContent.getUnitInfo();
							product.setName(unitInfoType.getUnitName().getUnitShortName());
							product.setPerson(unitInfoType.getMaxOccupancy().intValue());
							if(unitInfoType.getCategoryCodes() != null && unitInfoType.getCategoryCodes().getRoomInfo() != null && !unitInfoType.getCategoryCodes().getRoomInfo().isEmpty()){
								List<RoomInfo> roomInfos = unitInfoType.getCategoryCodes().getRoomInfo();
								for(RoomInfo roomInfo : roomInfos){
									if(roomInfo.getCodeDetail().contains(EscapiaNetUtils.BED_ROOM)){
										product.setRoom(roomInfo.getQuantity().intValue());
									}else if (roomInfo.getCodeDetail().contains(EscapiaNetUtils.BATH_ROOM)){
										product.setBathroom(roomInfo.getQuantity().intValue());
									}
								}
							}
							product = ProductService.getInstance().persistProduct(sqlSession,
									product, isNewProduct);
							if(unitInfoType.getDescriptions() != null){
								// persist description
								if(unitInfoType.getDescriptions().getDescriptiveText() != null){
									TextService.createDescription(sqlSession, new Text(product.getId() + "_Product", "product", Text.Type.Text, new Date(), unitInfoType.getDescriptions().getDescriptiveText(), "EN"));
								}

								if(unitInfoType.getDescriptions().getMultimediaDescriptions() != null){
									List<MultimediaDescriptionType> multimediaDescriptionTypes = unitInfoType.getDescriptions().getMultimediaDescriptions().getMultimediaDescription();
									if(multimediaDescriptionTypes != null && !multimediaDescriptionTypes.isEmpty()){
										for(MultimediaDescriptionType multimediaDescriptionType : multimediaDescriptionTypes){
											if(multimediaDescriptionType.getImageItems() != null){
												List<ImageItem> imageItems = multimediaDescriptionType.getImageItems().getImageItem();
												if(imageItems != null && !imageItems.isEmpty()){
													for(ImageItem imageItem : imageItems){
														String imgCategory = imageItem.getCategory();
														List<ImageFormat> imageFormats = imageItem.getImageFormat();
														if(imageFormats != null && !imageFormats.isEmpty()){
															for(ImageFormat imageFormat : imageFormats){
																persistImage(sqlSession, Integer.parseInt(product.getId()), imageFormat, imgCategory);
															}
														}
													}
												}
											}
											if(multimediaDescriptionType.getTextItems() != null){
												List<TextItem> textItems = multimediaDescriptionType.getTextItems().getTextItem();
												if(textItems != null && !textItems.isEmpty()){
													for(TextItem textItem : textItems){
														StringBuilder description = new StringBuilder();
														if(textItem.getDescriptionOrURL() != null){
															for(Object desc : textItem.getDescriptionOrURL()){
																description.append(desc + " ");
															}
														}
														TextService.createDescription(sqlSession, new Text(product.getId() + "_" + textItem.getTitle(), "product", Text.Type.Text, new Date(), description.toString(), "EN"));
													}
												}
											}
										}
									}
								}
							}
							if(unitInfoType.getServices() != null){
								List<Service> services = unitInfoType.getServices().getService();
								if(services != null && !services.isEmpty()){
									attributes = new ArrayList<String>();
									for(Service service : services){
										attributes.add(EscapiaNetUtils.getATTRIBUTES().get(service.getDescriptiveText()));
									}
								}
							}
						}
						
						PropertyManagerInfo info= new PropertyManagerInfo(); 
						info.setPmsId(Integer.getInteger(getAltpartyid()));
						info.setPmsId(Integer.parseInt(getAltpartyid()));
						info.setPropertyManagerId(Integer.parseInt(getAltpartyid()));
						info.setCreatedDate(new Date());
						if(unitDescriptiveContent.getPolicies() != null){
							Policy policy = unitDescriptiveContent.getPolicies().getPolicy().get(0);
							if(policy.getPolicyInfo() != null){
								if(policy.getPolicyInfo().getCheckInTime() != null) info.setCheckInTime( Time.valueOf(policy.getPolicyInfo().getCheckInTime()) );
								if(policy.getPolicyInfo().getCheckOutTime() != null) info.setCheckOutTime( Time.valueOf(policy.getPolicyInfo().getCheckOutTime()) );
							}
							if(policy.getPetsPolicies() != null){
								attributes.add(EscapiaNetUtils.getATTRIBUTES().get(policy.getPetsPolicies().getPetsAllowedCode()));
							}
						}
						 PropertyService.getInstance().updatePropertySupportInfo(sqlSession, info);
					}
					// persist attributes
					persistAttributes(sqlSession, product, attributes);
				}catch (Exception e) {
					LOG.error("Error while persisting product ", e);
				}
			}
			LOG.info("Total number of properties persisted in DB are "
					+ persistedPropCount);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		
	}
	
	/**
	 * @param sqlSession
	 * @param product
	 * @param attributes
	 * 
	 *            this will persist all the attributes in database
	 */
	private void persistAttributes(SqlSession sqlSession, Product product,
			List<String> attributes) {
		attributes.remove(null);
		ListIterator<String> listIterator = attributes.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next() == null) {
				listIterator.remove();
			}
		}
		RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE,
				product.getId(), attributes);

	}
	
	private void persistImage(SqlSession sqlSession, int productId,
			ImageFormat imageFormat, String imgCategory) {
		boolean isStandard = false;
		boolean isThumbnail = false;
		if(EscapiaNetUtils.STANDARD.equalsIgnoreCase(imageFormat.getDimensionCategory())){ isStandard = true; }
		else if(EscapiaNetUtils.THUMBNAIL.equalsIgnoreCase(imageFormat.getDimensionCategory())){ isThumbnail = true; }
		String title = imageFormat.getTitle() != null ? imageFormat.getTitle() : imageFormat.getFileName();
		ImageService.persistImage(title, productId, "EN", imgCategory, net.cbtltd.shared.Image.Type.Linked, imageFormat.getURL(), isStandard, isThumbnail, sqlSession);
		
	}
	private String buildAddress(AddressType address) {
		StringBuilder builder = new StringBuilder();
		if(address.getAddressLine() != null && !address.getAddressLine().isEmpty()){
			for(String addressLine : address.getAddressLine()){
				if (StringUtils.isNotEmpty(addressLine)) {
					builder.append(addressLine).append(", ");
				}
			}
		}
		if (StringUtils.isNotEmpty(address.getCityName())) {
			builder.append(address.getCityName()).append(", ");
		}
		if (StringUtils.isNotEmpty(address.getStateProv().getValue())) {
			builder.append(address.getStateProv().getValue()).append(" ");
		}
		if (StringUtils.isNotEmpty(address.getCountryName().getValue())) {
			builder.append(address.getCountryName().getValue()).append(" ");
		}
		builder.append(address.getPostalCode());
		return builder.toString();
	}
	private void buildLocationDetails(Product product, SqlSession sqlSession,
			UnitDescriptiveContentType unitDescriptiveContent) {
		try {
			Location location = null;
			if (unitDescriptiveContent.getUnitInfo() != null) {
				AddressType addressType = unitDescriptiveContent.getUnitInfo().getAddress();
				if(addressType != null){
					location = PartnerService.getLocation(sqlSession, addressType.getCityName(), addressType.getStateProv().getStateCode(), addressType.getCountryName().getCode());
				}
			}

			if (location != null) {
				product.setLocationid(location.getId());
			}
		} catch (Throwable t) {
			LOG.error("Error while doing location lookup ", t);
		}
		
	}
	public void readSchedule() {
		// TODO Auto-generated method stub
		
	}
	public void readSpecials() {
		// TODO Auto-generated method stub
		
	}
	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}
	public void readImages() {
		// TODO Auto-generated method stub
		
	}
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}
}
