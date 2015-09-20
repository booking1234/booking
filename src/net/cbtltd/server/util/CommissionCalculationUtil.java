package net.cbtltd.server.util;

import java.util.List;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.api.HasPrice;

import org.apache.ibatis.session.SqlSession;

public class CommissionCalculationUtil {
	
	public static final Double CREDIT_CARD_FEE = 0.03;
	
	private Double priceAmount;
	private Double extraAmount;
	private PropertyManagerInfo pmInfo = null;
	private ChannelPartner channelPartner = null;
	private Double channelPartnerCommission = 0.;
	private boolean isNetRate = false;
	
	public boolean isNetRate() {
		return isNetRate;
	}

	public void setNetRate(boolean isNetRate) {
		this.isNetRate = isNetRate;
	}

	public CommissionCalculationUtil(SqlSession sqlSession, HasPrice hasPrice, Double priceAmount, Double extraAmount) {
		this.setPriceAmount(priceAmount);
		this.setExtraAmount(extraAmount);		
		this.channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(hasPrice.getAgentid()));
		if (this.channelPartner != null && this.channelPartner.getCommission() !=  null){
			channelPartnerCommission = Double.valueOf(this.channelPartner.getCommission());
		}
		this.pmInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(hasPrice.getSupplierid()));
		
		if (this.pmInfo != null){
			this.isNetRate = pmInfo.isNetRate();
		}else {
			this.isNetRate = false;
		}
	}
	
	// Net Rate = Published Nightly Rates - (PM Commission Included * Published Nightly Rates) 
	public Double calculateNetRate() {
		double netRate = getPriceAmount() - (getPmCommission() * getPriceAmount());
		return netRate;
	}
	

//	public Double calculateTotalCommission() {
//		double bpCommission = pmInfo.getBpCommission() / 100.;
//		double pmsMarkup = partner.getCommission() / 100.;
//		double channelPartnerCommission = Integer.valueOf(channelPartner.getCommission()) / 100.; 
//		double creditCardFee = pmInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER ? CREDIT_CARD_FEE : 0;
//		double totalCommission = bpCommission + pmsMarkup + channelPartnerCommission + creditCardFee;
//		return totalCommission;
//	}
	
	// Net Rate / (1 - Total Commission) + Credit Card Fee Value
	public Double calculateNetRates() {
		if(isNetRate) {
			double newPublishedNightlyRate = calculateNetRate() / (1 - getTotalCommission());
			double ccFeeValue = getCreditCardFeeValue(newPublishedNightlyRate);			
			return newPublishedNightlyRate + ccFeeValue;
		} else {
			return priceAmount;
		}
	}
	
	// NPNRate =  Net Rate / (1 - Total Commission) 
	public Double getNewPublishedNightlyRate() {
		if(isNetRate) {
			double newPublishedNightlyRate = calculateNetRate() / (1 - getTotalCommission());
			return newPublishedNightlyRate;
		} else {
			return priceAmount;
		}
	}
	
	// CreditCardFee for Net Rate 	
	// PM FundHolder  - AddFee=0 => CCfee  % of (BPCommValue + ChannelCommValue)
	// BP FundsHolder - CCfee % of (PriceValue + ExtraValue)
	public double getCreditCardFeeValue(double newPublishedNightlyRate ) {
		double resultFee = 0.0;
		if (isPMFundsHolder()){
			if (pmInfo.getAdditionalCommission() == null || pmInfo.getAdditionalCommission() <= 0){
				Double amountToBP = getTotalCommission() * newPublishedNightlyRate;		
				resultFee = getCommissionValue(amountToBP, CREDIT_CARD_FEE);				
			}
		}else if (isBPFundsHolder()) {
			resultFee = getCommissionValue(newPublishedNightlyRate + getExtraAmount(), CREDIT_CARD_FEE);
		} 
		
		return resultFee;
	}
	
	/*public void calculateAddittionalCommision(HasPrice hasprice){
		if (isPMFundsHolder()) {
			if (!isNetRate) {
				if (pmInfo.getAdditionalCommission() != null &&	pmInfo.getAdditionalCommission() >= 0){
					PriceUtil.addCommission(hasprice, pmInfo.getAdditionalCommission() / 100.);
				}
			} else {
				resultFee = getCommissionValue(newPublishedNightlyRate, getAdditionalCommission());
			}
		}
	}*/
	
	public Double getAgentCommission() {
		Double cpCommission = channelPartnerCommission / 100.;
		return cpCommission;
	}
	
	public Double getAgentCommissionValue() {
		Double channelPartnerCommissionValue = getAgentCommission() * priceAmount;
		return channelPartnerCommissionValue;
	}
	
	public Double getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(Double amount) {
		this.priceAmount = amount;
	}

	public Double getExtraAmount() {
		return extraAmount;
	}

	public void setExtraAmount(Double extraAmount) {
		this.extraAmount = extraAmount;
	}
	
	public Double getBpCommission() {
		Double bpCommission = pmInfo.getBpCommission() / 100.;
		return bpCommission;
	}
	
	public Double getBpCommissionValue() {
		Double bpCommissionValue = getCommissionValue(calculateNetRate(), getBpCommission());
		return bpCommissionValue;
	}
	
	public Double getCpCommission() {
		Double cpCommission = channelPartnerCommission / 100.;
		return cpCommission;
	}
	
	public Double getCpCommissionValue() {
		Double cpCommissionValue = getCommissionValue(calculateNetRate(), getCpCommission());
		return cpCommissionValue;
	}
	
	public Double getPmsMarkup() {
		Double pmsCommission = pmInfo.getPmsMarkup() / 100.;
		return pmsCommission;
	}
	
	public Double getPmsMarkupValue() {
		Double pmsMarkup = getCommissionValue(calculateNetRate(), getPmsMarkup());
		return pmsMarkup;
	}
	
	public double getCreditCardFeeValue() {
		return getCreditCardFeeValue(calculateNetRate() / (1 - getTotalCommission()));
	}
	
	public Boolean isPMFundsHolder(){
		return pmInfo.getFundsHolder() == ManagerToGateway.PROPERTY_MANAGER_HOLDER;
	}
	
	public Boolean isBPFundsHolder(){
		return pmInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER;
	}
	
	public Double getAdditionalCommission() {
		if(pmInfo.getAdditionalCommission() == null) {
			return 0.;
		} else {
			Double additionalCommission = pmInfo.getAdditionalCommission() / 100.;
			return additionalCommission;
		}
	}
	
	public Double getAdditionalCommissionValue() {
		Double additionalCommissionValue = getCommissionValue(calculateNetRate() + extraAmount, getAdditionalCommission());
		return additionalCommissionValue;
	}
	
	/*public Double getCreditCardFeeValue() {
		if(getAdditionalCommissionValue() == 0) {
			Double creditCardFeeValue = getCommissionValue(extraAmount, getCreditCardFee());
			return creditCardFeeValue;
		} else {
			return 0.;
		}
	}*/
	
	public Double getPmCommission() {
		Double pmCommission = pmInfo.getCommission() / 100.;
		return pmCommission;
	}
	
	public Double getPmCommissionValue() {
		Double pmCommissionValue = getCommissionValue(priceAmount, getPmCommission());
		return pmCommissionValue;
	}
	
	public Double getTotalCommission() {
		Double totalCommission = getBpCommission() + getPmsMarkup() + getCpCommission(); //  + getCreditCardFee()
		return totalCommission;
	}
	
	// Total commission = BookingPal Commission + PMS Markup + Channel Partner Commission + Credit Card Fee (if MBP is holder)
	public Double getTotalCommissionValue() {
		
		Double totalCommissionValue = getCommissionValue(calculateNetRate(), getTotalCommission());
		//Double totalCommissionValue = (newPublishedNightlyRate * getTotalCommission()) + getCreditCardFeeValue(newPublishedNightlyRate / (1 - getTotalCommission()));
		return totalCommissionValue;
	}
	
	public static double getCommissionValue(Double amount, double commission) {
		double commissionValue = amount / (1 - commission) - amount;
		return commissionValue;
	}
	
	public static void addCommissionToPrice(Price price, Double commission) {
		Double quoteValue = price.getValue();
		Double quoteMinimum = price.getMinimum();
		
		if(quoteValue != null) {
			price.setValue(quoteValue + getCommissionValue(quoteValue, commission));
		}
		
		if(quoteMinimum != null) {
			price.setMinimum(quoteMinimum + getCommissionValue(quoteMinimum, commission));
		}
	}
	
	public ReservationPrice addLivePriceCommission(HasPrice hasPrice, ReservationPrice reservationPrice){
	    
	    if (isNetRate && hasPrice != null){
		
		List<Price> prices = hasPrice.getQuotedetail();
		
		for (Price price : prices) {
		    
		    // Search for tax on which NetRate should be calculated
		    if (price.hasEntitytype(NameId.Type.Reservation.name()) && 
			    price.hasType(net.cbtltd.shared.Price.TAX_EXCLUDED)){
			
			Double netRateTax = price.getValue() - (price.getValue() * getPmCommission());
			
			// new tax amount with NetRate commissions
			Double newTaxAmount = netRateTax / (1 - getTotalCommission());
			Double taxAmountDifference = Double.valueOf(newTaxAmount - price.getValue());
			//Incremental CC Fee for Tax ("Tax CC Fee")
			Double taxCCFeeAmount = getCommissionValue(taxAmountDifference, CommissionCalculationUtil.CREDIT_CARD_FEE);
			newTaxAmount = newTaxAmount + taxCCFeeAmount;
			
			if (reservationPrice != null && reservationPrice.getQuoteDetails() != null){
			    for (QuoteDetail quoteDetail : reservationPrice.getQuoteDetails()) {
				    if (quoteDetail.getDescription() != null && quoteDetail.getDescription().equals(price.getName())){
					
					if (quoteDetail.getAmount() != null && price.getValue().toString().equals(quoteDetail.getAmount())){
					    
					    quoteDetail.setAmount(newTaxAmount.toString());
					}
				    }
				}
			}
			
			
			hasPrice.setQuote(hasPrice.getQuote() + taxAmountDifference + taxCCFeeAmount);
			hasPrice.setExtra(hasPrice.getExtra() + taxAmountDifference + taxCCFeeAmount);
			price.setValue(newTaxAmount);
		    }
		}
	    }	    	    
	    
	    return reservationPrice;
	}
	
}
