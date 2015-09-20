/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "available")
@Description(value = "Property availability", target = DocTarget.RESPONSE)
public class Available implements HasXsl {
	private String productid;
	private String arrive;
	private String depart;
	private String alert;
	private String message;
	private String xsl;
	private Boolean available = true;
	private Integer quantity = 1;
	
	public Available() {}
	
	public Available(String productid, String alert, String message, String arrive, String depart, String xsl, Boolean available, Integer quantity) {
		super();
		this.productid = productid;
		this.alert = alert;
		this.message = message;
		this.arrive = arrive;
		this.depart = depart;
		this.xsl = xsl;
		this.available = available;
		this.quantity = quantity;
	}

	@Description(value = "Product ID of the availaility", target = DocTarget.METHOD)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Description(value = "Check in date for the availability", target = DocTarget.METHOD)
	public String getCheckin() {
		return arrive;
	}

	@Description(value = "Arrival date for the availability", target = DocTarget.METHOD)
	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Check out date for the availability", target = DocTarget.METHOD)
	public String getCheckout() {
		return depart;
	}
	
	@Description(value = "Departure date for the availability", target = DocTarget.METHOD)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	@Description(value = "True if available for the dates", target = DocTarget.METHOD)
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Description(value = "Quantity of the product available for the dates", target = DocTarget.METHOD)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Description(value = "Alert(s) for the specified dates", target = DocTarget.METHOD)
	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
