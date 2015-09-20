package net.cbtltd.rest.flipkey.supplier;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/**
 * This class will be posted as json by the Flipkey
 * 
 * @author Senthilnathan
 * 
 */
@XmlRootElement(name = "reservation")
@Description(value = "Reservation of a property between two dates", target = DocTarget.RESPONSE)
public class InquiryRequest {
	private Integer fk_property_id;
	private String client_property_id;
	private String traveller_name;
	private String email;
	private String phone_number;
	private Integer number_of_guests;
	private String arrival;
	private String departure;
	private String message;
	/**
	 * @return the fk_property_id
	 */
	public Integer getFk_property_id() {
		return fk_property_id;
	}
	/**
	 * @param fk_property_id the fk_property_id to set
	 */
	public void setFk_property_id(Integer fk_property_id) {
		this.fk_property_id = fk_property_id;
	}
	/**
	 * @return the client_property_id
	 */
	public String getClient_property_id() {
		return client_property_id;
	}
	/**
	 * @param client_property_id the client_property_id to set
	 */
	public void setClient_property_id(String client_property_id) {
		this.client_property_id = client_property_id;
	}
	/**
	 * @return the traveller_name
	 */
	public String getTraveller_name() {
		return traveller_name;
	}
	/**
	 * @param traveller_name the traveller_name to set
	 */
	public void setTraveller_name(String traveller_name) {
		this.traveller_name = traveller_name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone_number
	 */
	public String getPhone_number() {
		return phone_number;
	}
	/**
	 * @param phone_number the phone_number to set
	 */
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	/**
	 * @return the number_of_guests
	 */
	public Integer getNumber_of_guests() {
		return number_of_guests;
	}
	/**
	 * @param number_of_guests the number_of_guests to set
	 */
	public void setNumber_of_guests(Integer number_of_guests) {
		this.number_of_guests = number_of_guests;
	}
	/**
	 * @return the arrival
	 */
	public String getArrival() {
		return arrival;
	}
	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return departure;
	}
	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
}
