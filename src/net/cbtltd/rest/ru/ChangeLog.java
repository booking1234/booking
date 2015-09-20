package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "StaticData",
	    "Pricing",
	    "Availability",
	    "Image",
	    "Description"
	})
@XmlRootElement(name = "ChangeLog")
public class ChangeLog {
	
	@XmlAttribute(name = "PropertyID", required = true)
	protected Integer PropertyID;
	@XmlAttribute(name="NLA")
	protected boolean NLA;
	@XmlElement(name = "StaticData", required = true)
//	@XmlSchemaType(name = "dateTime")
	protected String StaticData;
	@XmlElement(name = "Pricing", required = true)
//	@XmlSchemaType(name = "dateTime")
	protected String Pricing;
	@XmlElement(name = "Availability", required = true)
//	@XmlSchemaType(name = "dateTime")
	protected String Availability;
	@XmlElement(name = "Image", required = true)
//	@XmlSchemaType(name = "dateTime")
	protected String Image;
	@XmlElement(name = "Description", required = true)
//	@XmlSchemaType(name = "dateTime")
	protected String Description;
	
	/**
	 * @return the code that uniquely identifies a property
	 */
	public Integer getPropertyID() {
		return PropertyID;
	}
	
	/**
	 *set propertyID
	 */
	public void setPropertyID(Integer propertyID) {
		PropertyID = propertyID;
	}
	
	/**
	 * @return the NLA value (The TRUE value means that property is no longer available, 
	 * should be removed from Agent system, NLA properties are automatically removed from XML feed after 1 week from the date of deactivation.)
	 */
	public boolean isNLA() {
		return NLA;
	}
	
	/**
	 * set NLA value
	 */
	public void setNLA(boolean value) {
		NLA = value;
	}
	
	/**
	 * @return the last modification of property's data (living space, address, coordinates, amenities, composition etc) (YYYY-MM-DD HH:MM:SS format – 24h format)
	 */
	public String getStaticData() {
		return StaticData;
	}
	
	/**
	 * set the staticData changeLog
	 */
	public void setStaticData(String staticData) {
		StaticData = staticData;
	}
	
	/**
	 * @return the last modification of property's price (base price, seasonal price, discounts) (YYYY-MM-DD HH:MM:SS format – 24h format)
	 */
	public String getPricing() {
		return Pricing;
	}
	
	/**
	 * set the pricing changeLog
	 */
	public void setPricing(String pricing) {
		Pricing = pricing;
	}
	
	/**
	 * @return the last modification of property's availability (YYYY-MM-DD HH:MM:SS format – 24h format)
	 */
	public String getAvailability() {
		return Availability;
	}
	
	/**
	 * set the availability changeLog
	 */
	public void setAvailability(String availability) {
		Availability = availability;
	}
	
	/**
	 * @return the last modification of property's images (YYYY-MM-DD HH:MM:SS format – 24h format)

	 */
	public String getImage() {
		return Image;
	}
	
	/**
	 * set the image changeLog
	 */
	public void setImage(String image) {
		Image = image;
	}
	
	/**
	 * @return the last modification of property's description (YYYY-MM-DD HH:MM:SS format – 24h format)
	 */
	public String getDescription() {
		return Description;
	}
	
	/**
	 * set the description changeLog
	 */
	public void setDescription(String description) {
		Description = description;
	}

}
