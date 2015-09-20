package net.cbtltd.shared;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("propertyManagerCancellationRule")
public class PropertyManagerCancellationRule {

	public PropertyManagerCancellationRule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PropertyManagerCancellationRule(Integer id, Integer propertyManagerId, Integer cancellationDate, Integer cancellationRefund,
			Double cancellationTransactionFee) {
		super();
		this.id = id;
		this.propertyManagerId = propertyManagerId;
		this.cancellationDate = cancellationDate;
		this.cancellationRefund = cancellationRefund;
		this.cancellationTransactionFee = cancellationTransactionFee;
	}
	@XStreamOmitField
	private Integer id;	
	@XStreamOmitField
	private Integer propertyManagerId; 			// ID of PM in Party table
	
	private Integer cancellationDate;			// Period (days count) at what traveler can cancel reservation if cancelationType=2;   
	
	private Integer cancellationRefund;			// Refund value (%) that traveler receive in case cancellation.   
	
	private Double cancellationTransactionFee; 	// Fee value that traveler pay for the transaction cancellation. 

	
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

	public Integer getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Integer cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public Integer getCancellationRefund() {
		return cancellationRefund;
	}

	public void setCancellationRefund(Integer cancellationRefund) {
		this.cancellationRefund = cancellationRefund;
	}

	public Double getCancellationTransactionFee() {
		return cancellationTransactionFee;
	}

	public void setCancellationTransactionFee(Double cancellationTransactionFee) {
		this.cancellationTransactionFee = cancellationTransactionFee;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PropertyManagerCancellationRules [id=");
		sb.append(id);
		sb.append(", \npropertyManagerId=");
		sb.append(propertyManagerId);
		sb.append(", \ncancelationDate=");
		sb.append(cancellationDate);
		sb.append(", \ncancelationRefund=");
		sb.append(cancellationRefund);
		sb.append(", \ncancelationTransactionFee=");
		sb.append(cancellationTransactionFee);
		
		return sb.toString();
	}
	
}
