/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import net.cbtltd.shared.Time;

public class Rate {
	private String label;
	private String startDate;
	private String endDate;
	private Double dailyMinRate;
	private Double dailyMaxRate;
	private Double weeklyMinRate;
	private Double weeklyMaxRate;
	private Double monthlyMinRate;
	private Double monthlyMaxRate;
	private Integer minimumStayLength;
	private Integer turnDay;
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public Rate(){}
	
	public Rate(Date startDate) {
		super();
		this.startDate = df.format(startDate);
		this.endDate = df.format(Time.addDuration(startDate, 5.0, Time.YEAR));
	}

	@XmlElement( name = "Label" , required = true )
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlElement( name = "StartDate" , required = true )
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = df.format(startDate);
	}

	@XmlElement( name = "EndDate" , required = true )
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = df.format(endDate);
	}

	@XmlElement( name = "DailyMinRate" , required = true )
	public Double getDailyMinRate() {
		return dailyMinRate;
	}

	public void setDailyMinRate(Double dailyMinRate) {
		this.dailyMinRate = dailyMinRate;
	}

	@XmlElement( name = "DailyMaxRate" , required = true )
	public Double getDailyMaxRate() {
		return dailyMaxRate;
	}

	public void setDailyMaxRate(Double dailyMaxRate) {
		this.dailyMaxRate = dailyMaxRate;
	}

	@XmlElement( name = "WeeklyMinRate" , required = false )
	public Double getWeeklyMinRate() {
		return weeklyMinRate;
	}

	public void setWeeklyMinRate(Double weeklyMinRate) {
		this.weeklyMinRate = weeklyMinRate;
	}

	@XmlElement( name = "WeeklyMaxRate" , required = false )
	public Double getWeeklyMaxRate() {
		return weeklyMaxRate;
	}

	public void setWeeklyMaxRate(Double weeklyMaxRate) {
		this.weeklyMaxRate = weeklyMaxRate;
	}

	@XmlElement( name = "MonthlyMinRate" , required = false )
	public Double getMonthlyMinRate() {
		return monthlyMinRate;
	}

	public void setMonthlyMinRate(Double monthlyMinRate) {
		this.monthlyMinRate = monthlyMinRate;
	}

	@XmlElement( name = "MonthlyMaxRate" , required = false )
	public Double getMonthlyMaxRate() {
		return monthlyMaxRate;
	}

	public void setMonthlyMaxRate(Double monthlyMaxRate) {
		this.monthlyMaxRate = monthlyMaxRate;
	}

	@XmlElement( name = "MinimumStayLength" , required = false )
	public Integer getMinimumStayLength() {
		return minimumStayLength;
	}

	public void setMinimumStayLength(Integer minimumStayLength) {
		this.minimumStayLength = minimumStayLength;
	}

	@XmlElement( name = "TurnDay" , required = false )
	public Integer getTurnDay() {
		return turnDay;
	}

	public void setTurnDay(Integer turnDay) {
		this.turnDay = turnDay;
	}
	
}
