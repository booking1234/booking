package com.mybookingpal.wrapper;

import static net.cbtltd.server.mail.TextHelper.NEW_LINE;
import static net.cbtltd.server.mail.TextHelper.STRING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.ReservationExtMapper;
import net.cbtltd.server.mail.RenterConfirmationMailContent;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.ReservationExt;
import net.cbtltd.server.config.RazorHostsConfig;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;

public class ReservationWrapper {

	private DateTimeFormatter dtfOut = DateTimeFormat
			.forPattern("MMM  dd,yyyy h:mm.SSa");
	private Reservation reservation;
	private Integer lengthOfStay;
	private String totalCancellationCost = "0";// to be calculated based on
												// today
	private List<String> listCancellationPolicy = new ArrayList<String>();
	private List<String> listCancellationRules = new ArrayList<String>();
	private String cancellationPolicyStr = "If canceled or modified up to <<x>> days before date of arrival, <<y>> % will be refunded.";
	private DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	private boolean internetAvailable;
	private boolean parkingAvailable;

	private String mealPlan = "None";
	private String guestName = "";
	private String imageSrc = "https://s3.amazonaws.com/mybookingpal/templates/room.jpg";
	private String bookingFirstMadeOn = "";
	private String lastModificationDate = "";
	private PropertyManagerInfo propertyManagerInfo;
	private String url = "";
	private String cancellationLink="";

	public boolean isParkingAvailable() {
		return parkingAvailable;
	}

	public void setParkingAvailable(boolean parkingAvailable) {

		this.parkingAvailable = parkingAvailable;
	}

	public boolean isInternetAvailable() {
		return internetAvailable;
	}

	public void setInternetAvailable(boolean internetAvailable) {
		this.internetAvailable = internetAvailable;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void buildReservationWrapper(Reservation reservation,String reservationPOS) {

		SqlSession sqlSession = RazorServer.openSession();

		Party customer = sqlSession.getMapper(PartyMapper.class).read(
				reservation.getCustomerid());
		reservation.setCustomer(customer);
		// System.out.println("Product " + reservation.getProductid());
		Product product = sqlSession.getMapper(ProductMapper.class).read(
				reservation.getProductid());
		// System.out.println("Location " + product.getLocationid());
		Location location = sqlSession.getMapper(LocationMapper.class).read(
				product.getLocationid());
		// System.out.println("Supplier " + reservation.getSupplierid());
		// System.out.println("Arrival Time " + reservation.getArrivaltime());
		Party supplier = sqlSession.getMapper(PartyMapper.class).read(
				reservation.getSupplierid());
		reservation.setSupplier(supplier);
		product.setLocation(location);

		reservation.setProduct(product);
		//
		this.buildCancellationCost(sqlSession, reservation);
		this.buildCancellationRules(sqlSession,
				new DateTime(reservation.getFromdate()),
				reservation.getCurrency(), reservation.getCost(),
				location.getName(), reservation.getAltpartyid());
		this.buildAttributeInformation(sqlSession, reservation);
		this.buildAddOn(sqlSession, reservation);
		this.buildImageDetails(sqlSession, reservation);
		this.lastModificationDate = dtfOut.print(new DateTime(reservation
				.getVersion()));
		this.bookingFirstMadeOn = dtfOut.print(new DateTime(reservation
				.getVersion()));
		this.reservation = reservation;
		this.url = RazorConfig.getValue("bp.email.url");
		System.out.println("URL " + url);
		RenterConfirmationMailContent renterConfirmationMailContent = new RenterConfirmationMailContent();
		
		this.cancellationLink=String.format(RazorHostsConfig.getCancellationLink() + STRING + NEW_LINE, reservationPOS);
		
		sqlSession.close();

	}

	private void buildImageDetails(SqlSession sqlSession,
			Reservation reservation) {
		List<String> listImageURLS = ImageService.getProductRegularImageURLs(
				sqlSession, reservation.getProductid());
		if (listImageURLS != null && listImageURLS.size() > 0) {
			imageSrc = listImageURLS.get(0);
		}

	}

	private void buildAddOn(SqlSession sqlSession, Reservation reservation) {
		ReservationExt temp = new ReservationExt();
		temp.setReservationId(reservation.getAltid());

		List<ReservationExt> reservationExts = sqlSession.getMapper(
				ReservationExtMapper.class).readReservationExt(temp);

		for (ReservationExt reservationExt : reservationExts) {

			if (reservationExt != null) {
				if (ReservationExt.GUEST_TYPE.equalsIgnoreCase(reservationExt
						.getType())) {
					this.guestName = reservationExt.getName();
				} else if ("Breakfast".equalsIgnoreCase(reservationExt
						.getName())) {
					this.mealPlan = "Breakfast";
				}
			}
		}
	}

	private void buildCancellationRules(SqlSession sqlSession,
			DateTime bookedDate, String currencyCode, Double totalCost,
			String location, String altPartyId) {
		Map<Integer, Integer> ruleMap = this.buildRuleMap(sqlSession,
				altPartyId);

		String firstTemplate = "Untill %[DATETIME]% %[LOCATION]% %[PRICE]%";
		String midTemplate = "From %[FROMDATETIME]% to Untill  %[TODATETIME]% %[LOCATION]% %[PRICE]%";
		// String endTemplate = "From %[DATETIME]% %[LOCATION]% %[PRICE]%";
		List<String> ruleList = new LinkedList<String>();
		List<Integer> dayCountList = sortDaysDescending(ruleMap);
		int lastDayCount = 0;
		for (int index = 0; index < dayCountList.size(); index++) {
			int day = dayCountList.get(index);
			double repayPercent = ruleMap.get(day);
			String rule = "";
			boolean validDay = false;
			if (validDay || bookedDate.minusDays(day).isAfterNow()) {
				validDay = true;
				if (index == 0) {
					rule = firstTemplate;
					rule = getExtremeRule(bookedDate, day, rule);
				} /*
				 * else if (index == dayCountList.size() - 1) { rule =
				 * endTemplate; rule = getExtremeRule(bookedDate, day, rule); }
				 */else {
					rule = midTemplate;
					if (lastDayCount != 0) {
						DateTime toDateTime = bookedDate.minusDays(day)
								.minusSeconds(1);
						DateTime fromDateTime = bookedDate
								.minusDays(lastDayCount);
						rule = rule.replace("%[FROMDATETIME]%",
								getFormattedDate(fromDateTime)).replace(
								"%[TODATETIME]%", getFormattedDate(toDateTime));
					}
				}
				Double refundAmount = totalCost
						- (totalCost * repayPercent / 100);
				String deductionAmount = currencyCode + " "
						+ String.format("%.2f", refundAmount);
				rule = rule.replace("%[LOCATION]%", location).replace(
						"%[PRICE]%", deductionAmount);
			}
			if (!rule.isEmpty()) {
				System.out.println(rule);
				ruleList.add(rule);
				rule = "";
			}
			lastDayCount = day;
		}
		listCancellationRules = ruleList;

	}

	private List<Integer> sortDaysDescending(Map<Integer, Integer> ruleMap) {
		List<Integer> dayCountList = new ArrayList(ruleMap.keySet());
		Collections.sort(dayCountList);
		Collections.reverse(dayCountList);
		return dayCountList;
	}

	private String getExtremeRule(DateTime bookedDate, int day, String rule) {
		DateTime dateTime = bookedDate.minusDays(day).minusSeconds(1);
		rule = rule.replace("%[DATETIME]%", getFormattedDate(dateTime));
		return rule;
	}

	private Map<Integer, Integer> buildRuleMap(SqlSession sqlSession,
			String altPartyId) {
		Map<Integer, Integer> ruleMap = new HashMap<Integer, Integer>();
		List<PropertyManagerCancellationRule> listPropertyManagerCancellationRule = sqlSession
				.getMapper(PropertyManagerCancellationRuleMapper.class)
				.readbypmid(Integer.parseInt(altPartyId));
		for (PropertyManagerCancellationRule rule : listPropertyManagerCancellationRule) {
			ruleMap.put(rule.getCancellationDate(),
					rule.getCancellationRefund());
		}
		return ruleMap;
	}

	private String getFormattedDate(DateTime dateTime) {

		return dtfOut.print(dateTime);
	}

	private void buildCancellationCost(SqlSession sqlSession,
			Reservation reservation) {
		Double totalCost = reservation.getCost();
		Map<Integer, Integer> ruleMap = this.buildRuleMap(sqlSession,
				reservation.getAltpartyid());
		int daysDiffer = Days.daysBetween(new DateTime(new Date()),
				new DateTime(reservation.getFromdate())).getDays();

		List<Integer> dayCountList = sortDaysDescending(ruleMap);
		StringBuffer cancellationCost = new StringBuffer();
		for (int dayCount : dayCountList) {

			if (daysDiffer > dayCount) {
				cancellationCost.append(String.format("%.2f", totalCost
						- totalCost * ruleMap.get(dayCount) / 100));
				break;
			}
		}

		this.totalCancellationCost = cancellationCost.toString();

	}

	private void buildPropertyManagerInfo(SqlSession sqlSession,
			Reservation reservation) {
		propertyManagerInfo = sqlSession.getMapper(
				PropertyManagerInfoMapper.class).readbypmid(
				Integer.parseInt(reservation.getAltpartyid()));

	}

	private void buildAttributeInformation(SqlSession sqlSession,
			Reservation reservation) {
		List<String> listAttributes = new ArrayList<String>();
		Relation relation = new Relation();
		relation.setLink("Product Attribute");
		relation.setHeadid(reservation.getProductid());
		relation.setLineid("HAC");// for parking details

		listAttributes.addAll(sqlSession.getMapper(RelationMapper.class)
				.attributes(relation));
		relation.setLineid("RMA");
		listAttributes.addAll(sqlSession.getMapper(RelationMapper.class)
				.attributes(relation));
		if (listAttributes.contains("RMA123")
				|| listAttributes.contains("RMA54")) {
			internetAvailable = true;
		}
		if (listAttributes.contains("HAC42")
				|| listAttributes.contains("HAC53")
				|| listAttributes.contains("HAC63")
				|| listAttributes.contains("HAC64")
				|| listAttributes.contains("HAC65")
				|| listAttributes.contains("HAC171")) {
			parkingAvailable = true;
		}
	}

	private void calculateCancellationCost(SqlSession sqlSession,
			Reservation reservation) {
		int diffDays = Days.daysBetween(
				new DateTime(reservation.getFromdate()),
				new DateTime(new Date())).getDays();
		Double CancellationCost = new Double(0);
		Double totalBookingCost = reservation.getCost();
		// rules sample for cancellation calculation 30 100,20 75,10 50, 0 25
		List<PropertyManagerCancellationRule> listPropertyManagerCancellationRule = sqlSession
				.getMapper(PropertyManagerCancellationRuleMapper.class)
				.readbypmid(Integer.parseInt(reservation.getAltpartyid()));
		if (listPropertyManagerCancellationRule != null) {
			for (PropertyManagerCancellationRule rule : listPropertyManagerCancellationRule) {
				String cancellationPolicyTemp = cancellationPolicyStr
						.replaceAll("<<x>>", rule.getCancellationDate() + "");
				cancellationPolicyTemp = cancellationPolicyTemp.replaceAll(
						"<<y>>", rule.getCancellationRefund() + "");
				listCancellationPolicy.add(cancellationPolicyTemp);
				if (rule.getCancellationDate().compareTo(diffDays) < 0) {
					// this.totalCancellationCost=totalBookingCost-(totalBookingCost*(rule.getCancellationRefund()/100))+"";
				}
			}
		}

	}

	public Integer getLengthOfStay() {

		DateTime dt1 = dateTimeFormatter
				.parseDateTime(reservation.getCheckin());
		DateTime dt2 = dateTimeFormatter.parseDateTime(reservation
				.getCheckout());
		lengthOfStay = Days.daysBetween(dt1, dt2).getDays() + 1;
		return lengthOfStay;
	}

	public DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	public String getDayOfArrivalDate() {
		return CommonUtils.dayOftheWeekMap.get((dateTimeFormatter
				.parseDateTime(reservation.getCheckin())).getDayOfWeek());

	}

	public String getDayOfDepDate() {
		return CommonUtils.dayOftheWeekMap.get((dateTimeFormatter
				.parseDateTime(reservation.getCheckout()).getDayOfWeek()));

	}

	public String getDayOfThreeDaysBfrArrivalDate() {
		return CommonUtils.dayOftheWeekMap.get((dateTimeFormatter
				.parseDateTime(reservation.getCheckin()).minusDays(3)
				.getDayOfWeek()));
	}

	public String getDayOfTwoDaysBfrArrivalDate() {
		return CommonUtils.dayOftheWeekMap.get(dateTimeFormatter
				.parseDateTime(reservation.getCheckin()).minusDays(2)
				.getDayOfWeek());
	}

	public String getMonthOfArrivalDate() {
		return CommonUtils.monthOftheYearMap.get(dateTimeFormatter
				.parseDateTime(reservation.getCheckin()).getMonthOfYear());

	}

	public String getMonthOfDepDate() {

		return CommonUtils.monthOftheYearMap.get(dateTimeFormatter
				.parseDateTime(reservation.getCheckout()).getMonthOfYear());
	}

	public String getMonthOfThreeDaysBfrArrivalDate() {

		return CommonUtils.monthOftheYearMap.get(dateTimeFormatter
				.parseDateTime(reservation.getCheckin()).minusDays(3)
				.getMonthOfYear());
	}

	public String getMonthOfTwoDaysBfrArrivalDate() {
		return CommonUtils.monthOftheYearMap.get(dateTimeFormatter
				.parseDateTime(reservation.getCheckin()).minusDays(2)
				.getMonthOfYear());

	}

	public String getDateOfArivalDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.getDayOfMonth() + "";

	}

	public String getDateOfDepDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckout())
				.getDayOfMonth() + "";
	}

	public String getDateOfThreeDaysBfrArrivalDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.minusDays(3).getDayOfMonth()
				+ "";
	}

	public String getDateOfTwoDaysBfrArrivalDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.minusDays(2).getDayOfMonth()
				+ "";
	}

	public String getYearOfArivalDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.getYear() + "";
	}

	public String getYearOfDepDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckout())
				.getYear() + "";
	}

	public String getYearOfThreeDaysBfrArrivalDate() {

		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.minusDays(3).getYear()
				+ "";
	}

	public String getYearOfTwoDaysBfrArrivalDate() {
		return dateTimeFormatter.parseDateTime(reservation.getCheckin())
				.minusDays(2).getYear()
				+ "";
	}

	public String getTotalCancellationCost() {
		return totalCancellationCost;
	}

	public void setTotalCancellationCost(String totalCancellationCost) {
		this.totalCancellationCost = totalCancellationCost;
	}

	public List<String> getListCancellationPolicy() {
		return listCancellationPolicy;
	}

	public void setListCancellationPolicy(List<String> listCancellationPolicy) {
		this.listCancellationPolicy = listCancellationPolicy;
	}

	public String getCancellationPolicyStr() {
		return cancellationPolicyStr;
	}

	public void setCancellationPolicyStr(String cancellationPolicyStr) {
		this.cancellationPolicyStr = cancellationPolicyStr;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void setLengthOfStay(Integer lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	public List<String> getListCancellationRules() {
		return listCancellationRules;
	}

	public void setListCancellationRules(List<String> listCancellationRules) {
		this.listCancellationRules = listCancellationRules;
	}

	public PropertyManagerInfo getPropertyManagerInfo() {
		return propertyManagerInfo;
	}

	public void setPropertyManagerInfo(PropertyManagerInfo propertyManagerInfo) {
		this.propertyManagerInfo = propertyManagerInfo;
	}

	public String getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(String mealPlan) {
		this.mealPlan = mealPlan;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getBookingFirstMadeOn() {
		return bookingFirstMadeOn;
	}

	public void setBookingFirstMadeOn(String bookingFirstMadeOn) {
		this.bookingFirstMadeOn = bookingFirstMadeOn;
	}

	public String getLastModificationDate() {
		return lastModificationDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getCancellationLink() {
		return cancellationLink;
	}

	public void setCancellationLink(String cancellationLink) {
		this.cancellationLink = cancellationLink;
	}

}
