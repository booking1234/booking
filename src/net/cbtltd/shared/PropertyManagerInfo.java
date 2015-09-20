package net.cbtltd.shared;

import java.sql.Time;
import java.util.Date;

public class PropertyManagerInfo {

	public PropertyManagerInfo() {
		super();
		// default values regarding Net rates story
		this.bpCommission = 2.;
		this.netRate = false;
	}
		
	public PropertyManagerInfo(Integer id, Integer propertyManagerId, Integer pmsId, Integer fundsHolder, Integer paymentProcessingType,
			Integer registrationStepId, boolean inquireOnly, Integer cancellationType, Integer damageCoverageType, String damageInsurance, Time checkInTime,
			Time checkOutTime, String termsLink, Integer numberOfPayments, Double paymentAmount, Integer paymentType,
			Integer remainderPaymentDate, Boolean newRegistration, Date createdDate, Double commission, Double bpCommission, boolean netRate,
			Integer configurationId) {
		super();
		this.id = id;
		this.propertyManagerId = propertyManagerId;
		this.pmsId = pmsId;
		this.fundsHolder = fundsHolder;
		this.paymentProcessingType = paymentProcessingType;
		this.registrationStepId = registrationStepId;
		this.inquireOnly = inquireOnly;
		this.cancellationType = cancellationType;
		this.damageCoverageType = damageCoverageType;
		this.damageInsurance = damageInsurance;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.termsLink = termsLink;
		this.numberOfPayments = numberOfPayments;
		this.paymentAmount = paymentAmount;
		this.paymentType = paymentType;
		this.remainderPaymentDate = remainderPaymentDate;
		this.newRegistration = newRegistration;
		this.createdDate = createdDate;
		this.commission = commission;
		this.bpCommission = bpCommission;
		this.netRate = netRate;
		this.configurationId = configurationId;
	}

	private Integer id;	
	// ID of PM in Party table
	private Integer propertyManagerId; 			
	// ID of Property Management System in Party table
	private Integer pmsId;						
	// This value is set based on whether the PM is using their Payment Gateway account or BookingPal payment gateway account:
	// 	1 - BookingPal payment gateway account;
	//  0 - using their payment gateway account.
	private Integer fundsHolder;				
	// This value is set based on the PM  Online Payment Options:
	//  3 - Property Management System of PM is supporting credit card processing;
	//  2 - PM will get booking confirmations via email and will be responsible for charging the renter;
	//  1 - PM use the payment gateway.
	private Integer paymentProcessingType;	 
	// Step of registration (1 - 8).
	private Integer registrationStepId;			
	// PM Inquire only properties support
	private boolean inquireOnly;

	/* Billing Policies block */			
	
	// This value is set based on whether the PM can cancel the reservation up to the day of the trip or up to the fixed period:
	//  1 - traveler can cancel up to the day of the trip and still receive fill refunds;
	//  2 - traveler can cancel up to the fixed periods;
	private Integer cancellationType;
	// Damage coverage types:
	//  2 - Damage Insurance;
	//  1 - Refundable Deposit - this value will be included in the display of the pricing for each property 
	//      and should be charged when the property is booked;
	//  0 - None damage coverage.
	private Integer damageCoverageType;			
	// Holds the damage insurance value if damageCoverageType=2;
	private String damageInsurance;				
	// Provides check in time;
	private java.sql.Time checkInTime;								
	// Provides check out time;
	private java.sql.Time checkOutTime;			
	// Provides link to terms and conditions;
	private String termsLink;					
	// This value is set based on whether traveler pays 100% at the time of booking or had two payments:
	//  1 - traveler pays 100% at the time of booking;
	//  2 - traveler will have two payments.
	private Integer numberOfPayments;			
	// First payment amount that due at time of booking (in case traveler will have two payments.)
	private Double paymentAmount;				
	// Type of the first payment amount:	
	//  1 - % of booking;
	//  2 - flat rate.
	private Integer paymentType;				
	// Period in days to check in to pay a remainder payment.
	private Integer remainderPaymentDate;		

	/* End Billing Policies block */
	
	// Is this PM new registered or he already has a account.
	private Boolean newRegistration;			
	// Created date of the entity.
	private Date createdDate;					
	private Double additionalCommission;
	
	// Net rates story
	private Double commission; // PM commission
	private Double bpCommission;
	private boolean netRate;
	private Double pmsMarkup;
	private Integer configurationId;
	
	public Integer getCancellationType() {
		return cancellationType;
	}

	public void setCancellationType(Integer cancellationType) {
		this.cancellationType = cancellationType;
	}

	public Integer getPmsId() {
		return pmsId;
	}

	public void setPmsId(Integer pmsId) {
		this.pmsId = pmsId;
	}

	public Boolean getNewRegistration() {
		return newRegistration;
	}

	public void setNewRegistration(Boolean newRegistration) {
		this.newRegistration = newRegistration;
	}

	public Integer getNumberOfPayments() {
		return numberOfPayments;
	}

	public void setNumberOfPayments(Integer numberOfPayments) {
		this.numberOfPayments = numberOfPayments;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getRemainderPaymentDate() {
		return remainderPaymentDate;
	}

	public void setRemainderPaymentDate(Integer remainderPaymentDate) {
		this.remainderPaymentDate = remainderPaymentDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPropertyManagerId() {
		return propertyManagerId;
	}

	public void setPropertyManagerId(Integer propertyManagerId) {
		this.propertyManagerId = propertyManagerId;
	}

	public Integer getFundsHolder() {
		return fundsHolder;
	}

	public void setFundsHolder(Integer fundsHolder) {
		this.fundsHolder = fundsHolder;
	}

	public Integer getRegistrationStepId() {
		return registrationStepId;
	}

	public void setRegistrationStepId(Integer registrationStepId) {
		this.registrationStepId = registrationStepId;
	}

	public Integer getDamageCoverageType() {
		return damageCoverageType;
	}

	public void setDamageCoverageType(Integer damageCoverageType) {
		this.damageCoverageType = damageCoverageType;
	}

	public String getDamageInsurance() {
		return damageInsurance;
	}

	public void setDamageInsurance(String damageInsurance) {
		this.damageInsurance = damageInsurance;
	}
	
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPaymentProcessingType() {
		return paymentProcessingType;
	}

	public void setPaymentProcessingType(Integer paymentProcessingType) {
		this.paymentProcessingType = paymentProcessingType;
	}

	public java.sql.Time getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(java.sql.Time checkInTime) {
		this.checkInTime = checkInTime;
	}

	public java.sql.Time getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(java.sql.Time checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getTermsLink() {
		return termsLink;
	}

	public void setTermsLink(String termsLink) {
		this.termsLink = termsLink;
	}


	public boolean isInquireOnly() {
		return inquireOnly;
	}


	public void setInquireOnly(boolean inquireOnly) {
		this.inquireOnly = inquireOnly;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getBpCommission() {
		return bpCommission;
	}

	public void setBpCommission(Double bpCommission) {
		this.bpCommission = bpCommission;
	}

	public boolean isNetRate() {
		return netRate;
	}

	public void setNetRate(boolean netRate) {
		this.netRate = netRate;
	}

	public Double getPmsMarkup() {
		return pmsMarkup;
	}

	public void setPmsMarkup(Double pmsMarkup) {
		this.pmsMarkup = pmsMarkup;
	}

	public Double getAdditionalCommission() {
		return additionalCommission;
	}

	public void setAdditionalCommission(Double additionalCommission) {
		this.additionalCommission = additionalCommission;
	}

	public Integer getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Integer configurationId) {
		this.configurationId = configurationId;
	}
}
