package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Landlord",
    "Email",
    "Phone",
    "DaysBeforeArrival",
    "PickupService",
    "HowToArrive"
})
@XmlRootElement(name = "ArrivalInstructions")
public class ArrivalInstructions {
	
	@XmlElement(required = true)
	protected String Landlord;
	@XmlElement(required = true)
	protected String Email;
	@XmlElement(required = true)
	protected String Phone;
	@XmlElement(required = true)
	protected int DaysBeforeArrival;
	@XmlElement(required = true)
	protected PickupService PickupService;
	@XmlElement(required = true)
	protected HowToArrive HowToArrive;
	
	/**
	 * @return the name of the property landlord
	 */
	public String getLandlord() {
		return Landlord;
	}
	
	/**
	 * set landlord
	 */
	public void setLandlord(String landlord) {
		Landlord = landlord;
	}
	
	/**
	 * @return the email address to the landlord
	 */
	public String getEmail() {
		return Email;
	}
	
	/**
	 * set email
	 */
	public void setEmail(String email) {
		Email = email;
	}
	
	/**
	 * @return phone number (with country code) to the landlord
	 */
	public String getPhone() {
		return Phone;
	}
	
	/**
	 * set phone
	 */
	public void setPhone(String phone) {
		Phone = phone;
	}
	
	/**
	 * @return the daysBeforeArrival
	 */
	public int getDaysBeforeArrival() {
		return DaysBeforeArrival;
	}
	
	/**
	 * set daysBeforeArrival
	 */
	public void setDaysBeforeArrival(int daysBeforeArrival) {
		DaysBeforeArrival = daysBeforeArrival;
	}
	
	/**
	 * @return the pickupService
	 */
	public PickupService getPickupService() {
		return PickupService;
	}
	
	/**
	 * set pickupService
	 */
	public void setPickupService(PickupService pickupService) {
		PickupService = pickupService;
	}
	
	/**
	 * @return the howToArrive
	 */
	public HowToArrive getHowToArrive() {
		return HowToArrive;
	}
	
	/**
	 * set howToArrive 
	 */
	public void setHowToArrive(HowToArrive howToArrive) {
		HowToArrive = howToArrive;
	}

}
