package net.cbtltd.server.util;

import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.api.HasPrice;

import org.apache.ibatis.session.SqlSession;

public class CommissionSeparatorUtil {

	private Boolean netRate = false;
	private Double totalAmount;
	private Double nightlyRate;
	private PropertyManagerInfo pmInfo;
	private ChannelPartner channelPartner;
	private Double channelPartnerCommission = 0.0;
		
	public CommissionSeparatorUtil(SqlSession sqlSession, HasPrice hasPrice, Double totalAmount, Double nightlyRate) {		
		this.setTotalAmount(totalAmount);
		this.setNightlyRate(nightlyRate);
		this.channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(hasPrice.getAgentid()));
		if (this.channelPartner != null && this.channelPartner.getCommission() !=  null){
			channelPartnerCommission = Double.valueOf(this.channelPartner.getCommission());
		}
		this.pmInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(hasPrice.getSupplierid()));
		
		if (this.pmInfo != null){
			this.netRate = pmInfo.isNetRate();
		}else {
			this.netRate = false;
		}
	}
	
	// Net Rate = Published Nightly Rates - (PM Commission Included * Published Nightly Rates) 
	private Double calculateNetRate() {
		double netRate = getNightlyRate() - (getPmCommission() * getNightlyRate());
		return netRate;
	}
	
	private Double calculateNewPublishedNetRate() {
		double newNetRate = calculateNetRate() / (1 - getTotalCommission());
		return newNetRate;
	}
	
	public Double getCreditCardFeeValue(){
		
		Double result = 0.0;
		
		if (isPMFundsHolder()){
			
			Double newPublishedNightlyRate = calculateNewPublishedNetRate();
			if (this.pmInfo.getAdditionalCommission() != null && this.pmInfo.getAdditionalCommission() > 0){
				
				result = CommissionCalculationUtil.getCommissionValue(newPublishedNightlyRate, getAdditionalCommission());
				
			}else{				
				
				Double amountToBP = getTotalCommission() * newPublishedNightlyRate;
				result = CommissionCalculationUtil.getCommissionValue(amountToBP, CommissionCalculationUtil.CREDIT_CARD_FEE);
				
			}
		}else if (isBPFundsHolder()){
			
			// Credit card fee was calculated as : totalAmount / (1 - CreditCardFee) - totalAmount
			result = getTotalAmount() * CommissionCalculationUtil.CREDIT_CARD_FEE;
		}
		
		return result;
	}
	
	public Double getAdditionalCommission(){
		
		Double additionalCommissoion =  this.pmInfo.getAdditionalCommission() / 100.;
		return additionalCommissoion;
		
	}
	
	public Double getBpCommission(){
		
		Double bpCommission =  this.pmInfo.getBpCommission() / 100.;
		return bpCommission;
		
	}
	
	public Double getBpCommissionValue(){
				
		Double bpCommissionValue = calculateNewPublishedNetRate() * getBpCommission();
		return bpCommissionValue;
		
	}
	
	public Double getChannelPartnerCommission(){
		
		return this.channelPartnerCommission / 100.;
	}
	
	public Double getChannelPartnerCommissionValue(){
		
		Double cpCommission = calculateNewPublishedNetRate() * getChannelPartnerCommission();
		return cpCommission;
	}
	
	public Double getPmsMarkupCommission() {
		
		Double pmsCommission = pmInfo.getPmsMarkup() / 100.;
		return pmsCommission;
		
	}
	
	public Double getPmsMarkupValue() {
		
		Double pmsMarkupVlaue = calculateNewPublishedNetRate() * getPmsMarkupCommission();
		return pmsMarkupVlaue;
		
	}
	
	public Double getPmCommission() {
		
		Double pmCommission = pmInfo.getCommission() / 100.;
		return pmCommission;
		
	}
	
	public Double getPmCommissionValue() {
		
		Double pmCommissionValue = getPmCommission() * getNightlyRate();
		return pmCommissionValue;
	}
	
	public Double getTotalCommission() {
		
		Double totalCommission = getBpCommission() + getPmsMarkupCommission() + getChannelPartnerCommission();
		return totalCommission;
		
	}
	
	public Double getTotalCommissionValue() {
		
		Double totalCommissionValue = CommissionCalculationUtil.getCommissionValue(calculateNetRate(), getTotalCommission());
		return totalCommissionValue;
			
	}
	
	public Boolean isPMFundsHolder(){
		return pmInfo.getFundsHolder() == ManagerToGateway.PROPERTY_MANAGER_HOLDER;
	}
	
	public Boolean isBPFundsHolder(){
		return pmInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER;
	}
	
	public Double getNightlyRate() {
		return nightlyRate;
	}

	public void setNightlyRate(Double nightlyRate) {
		this.nightlyRate = nightlyRate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Boolean isNetRate() {
	    return netRate;
	}

	public void setNetRate(Boolean isNetRate) {
	    this.netRate = isNetRate;
	}

	public PropertyManagerInfo getPmInfo() {
	    return pmInfo;
	}

	public void setPmInfo(PropertyManagerInfo pmInfo) {
	    this.pmInfo = pmInfo;
	}

	public ChannelPartner getChannelPartner() {
	    return channelPartner;
	}

	public void setChannelPartner(ChannelPartner channelPartner) {
	    this.channelPartner = channelPartner;
	}

	public void setChannelPartnerCommission(Double channelPartnerCommission) {
	    this.channelPartnerCommission = channelPartnerCommission;
	}
	
	

}
