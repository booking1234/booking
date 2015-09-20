package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.cbtltd.rest.error.ApiResponse;
@XStreamAlias("availability_calendar")
@XmlRootElement(name="availability_calendar")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityResponse extends ApiResponse {
	private String productid; 
	@XStreamImplicit(itemFieldName="calendar")
	private AvailabilityRange[] items;
	
	
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public AvailabilityResponse() {
		super();
	}

	public AvailabilityRange[] getItems() {
		return items;
	}

	public void setItems(AvailabilityRange[] items) {
		this.items = items;
	}
}
