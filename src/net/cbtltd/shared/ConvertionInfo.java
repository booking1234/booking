package net.cbtltd.shared;

import java.util.Date;

public class ConvertionInfo {
	private Integer id;
	private String fromCurrency;
	private Double fromAmount;
	private Double rate;
	private Date convertionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getConvertionDate() {
		return convertionDate;
	}

	public void setConvertionDate(Date convertionDate) {
		this.convertionDate = convertionDate;
	}
}
