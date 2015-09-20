package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.Reservation;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "reservation")
@Description(value = "Reservation info of a property between two dates", target = DocTarget.RESPONSE)
public class ReservationInfo {

	private String id;
	private String checkin;
	private String checkout;
	private String currency;
	private Double price;
	
		
	public ReservationInfo() {
	}
	
	public ReservationInfo(Reservation reservation) {
		if (reservation != null){
			id = reservation.getId();
			checkin = reservation.getCheckin();
			checkout = reservation.getCheckout();
			currency = reservation.getCurrency();
			price = reservation.getPrice();	
		}				
	}
	
	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}		

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [id=");
		builder.append(id);
		builder.append(", \ncheckin=");
		builder.append(checkin);
		builder.append(", \ncheckout=");
		builder.append(checkout);
		builder.append(", \ncurrency=");
		builder.append(currency);
		builder.append(", \nprice=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}
}
