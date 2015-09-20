package net.cbtltd.rest.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.price.PriceRange;

@XmlRootElement(name="ranges")
@XStreamAlias("ranges")
public class WeeklyPriceResponse extends ApiResponse {
	
	private Integer propertyId;
	private String currency;
	@XStreamImplicit
	private List<PriceRange> ranges;
	private List<Price> prices;
	
	
	@XmlElement(name="range")
	public List<PriceRange> getPriceRanges() {
		return ranges;
	}

	public void setPriceRanges(List<PriceRange> ranges) {
		this.ranges = ranges;
	}
	
	@XmlElement(name="prices")
	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
