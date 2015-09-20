
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroupNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AvailableLimit" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="InitialValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="InternalPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardItem", propOrder = {
    "groupNumber",
    "type",
    "availableLimit",
    "initialValue",
    "internalPrice",
    "description",
    "startDate",
    "endDate"
})
public class DebitCardItem {

    @XmlElement(name = "GroupNumber")
    protected long groupNumber;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "AvailableLimit")
    protected double availableLimit;
    @XmlElement(name = "InitialValue")
    protected double initialValue;
    @XmlElement(name = "InternalPrice")
    protected double internalPrice;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "StartDate")
    protected String startDate;
    @XmlElement(name = "EndDate")
    protected String endDate;

    /**
     * Gets the value of the groupNumber property.
     * 
     */
    public long getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets the value of the groupNumber property.
     * 
     */
    public void setGroupNumber(long value) {
        this.groupNumber = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the availableLimit property.
     * 
     */
    public double getAvailableLimit() {
        return availableLimit;
    }

    /**
     * Sets the value of the availableLimit property.
     * 
     */
    public void setAvailableLimit(double value) {
        this.availableLimit = value;
    }

    /**
     * Gets the value of the initialValue property.
     * 
     */
    public double getInitialValue() {
        return initialValue;
    }

    /**
     * Sets the value of the initialValue property.
     * 
     */
    public void setInitialValue(double value) {
        this.initialValue = value;
    }

    /**
     * Gets the value of the internalPrice property.
     * 
     */
    public double getInternalPrice() {
        return internalPrice;
    }

    /**
     * Sets the value of the internalPrice property.
     * 
     */
    public void setInternalPrice(double value) {
        this.internalPrice = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

}
