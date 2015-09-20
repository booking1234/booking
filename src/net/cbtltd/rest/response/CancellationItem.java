package net.cbtltd.rest.response;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.json.JSONService;

@XmlRootElement(name="cancellation_item")
public class CancellationItem implements Comparable<CancellationItem>{
	
	public CancellationItem() {
		super();
	}
	
	public CancellationItem(String cancellationDate, Double cancellationPercentage, Double cancellationAmount, Double transactionFee, Integer daysBeforeArrival) {
		this.cancellationDate = cancellationDate;
		this.cancellationAmount = cancellationAmount;
		this.cancellationPercentage = cancellationPercentage;
		this.transactionFee = transactionFee;
		this.daysBeforeArrival = daysBeforeArrival;
	}
	
	private String cancellationDate;
	private Double cancellationPercentage;
	private Double cancellationAmount;
	private Integer daysBeforeArrival;
	private Double  transactionFee; 

	public Double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(Double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public Double getCancellationPercentage() {
		return cancellationPercentage;
	}

	public void setCancellationPercentage(Double cancellationPercentage) {
		this.cancellationPercentage = cancellationPercentage;
	}

	public Double getCancellationAmount() {
		return cancellationAmount;
	}

	public void setCancellationAmount(Double cancellationAmount) {
		this.cancellationAmount = cancellationAmount;
	}

	public Integer getDaysBeforeArrival() {
		return daysBeforeArrival;
	}

	public void setDaysBeforeArrival(Integer daysBeforeArrival) {
		this.daysBeforeArrival = daysBeforeArrival;
	}

	@Override
	public int compareTo(CancellationItem item) {
		try {
			Date item1Date = JSONService.DF.parse(this.getCancellationDate());
			Date item2Date = JSONService.DF.parse(item.getCancellationDate());
			if(item1Date.after(item2Date)) {
				return 1;
			} else if(item1Date.before(item2Date)) {
				return -1;
			} else {
				return 0;
			}
		} catch (ParseException e) {
			return 0;
		}
	}
}
