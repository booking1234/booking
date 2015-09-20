package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "PropertyID",
	    "DateFrom",
	    "DateTo",
	    "NumberOfGuests",
	    "Costs"
	})
@XmlRootElement(name = "StayInfo")
public class StayInfo {

	@XmlElement(required = true)
	protected int PropertyID;
	@XmlElement(name="DateFrom")
    @XmlSchemaType(name = "date")
    protected String DateFrom;
	@XmlElement(name="DateTo")
    @XmlSchemaType(name = "date")
    protected String DateTo;
	@XmlElement(required = true)
	protected int NumberOfGuests;
	@XmlElement(required = true)
	protected Costs Costs;
	
	/**
	 * @return the code that uniquely identifies a property
	 */
	public int getPropertyID() {
		return PropertyID;
	}
	
	/**
	 * set the code that uniquely identifies a property
	 */
	public void setPropertyID(int propertyID) {
		PropertyID = propertyID;
	}
	
	/**
	 * @return the arrival date (YYYY-MM-DD format)
	 */
	public String getDateFrom() {
		return DateFrom;
	}
	
	/**
	 * set the departure date (YYYY-MM-DD format)
	 */
	public void setDateFrom(String dateFrom) {
		DateFrom = dateFrom;
	}
	
	/**
	 * @return the dateTo departure date (YYYY-MM-DD format)
	 */
	public String getDateTo() {
		return DateTo;
	}
	
	/**
	 * set the dateTo departure date (YYYY-MM-DD format)
	 */
	public void setDateTo(String dateTo) {
		DateTo = dateTo;
	}
	
	/**
	 * @return the number of guests in a given property
	 */
	public int getNumberOfGuests() {
		return NumberOfGuests;
	}
	
	/**
	 * set the number of guests in a given property
	 */
	public void setNumberOfGuests(int numberOfGuests) {
		NumberOfGuests = numberOfGuests;
	}
	
	/**
	 * set the information about property costs
	 */
	public Costs getCosts() {
		return Costs;
	}
	
	/**
	 * set the information about property costs
	 */
	public void setCosts(Costs costs) {
		Costs = costs;
	}
	
}
