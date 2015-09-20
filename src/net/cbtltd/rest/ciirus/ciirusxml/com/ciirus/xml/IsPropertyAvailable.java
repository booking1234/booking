
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="APIUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APIPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ArrivalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "apiUsername",
    "apiPassword",
    "propertyID",
    "arrivalDate",
    "departureDate"
})
@XmlRootElement(name = "IsPropertyAvailable")
public class IsPropertyAvailable {

    @XmlElement(name = "APIUsername")
    protected String apiUsername;
    @XmlElement(name = "APIPassword")
    protected String apiPassword;
    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "ArrivalDate")
    protected String arrivalDate;
    @XmlElement(name = "DepartureDate")
    protected String departureDate;

    /**
     * Gets the value of the apiUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIUsername() {
        return apiUsername;
    }

    /**
     * Sets the value of the apiUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIUsername(String value) {
        this.apiUsername = value;
    }

    /**
     * Gets the value of the apiPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIPassword() {
        return apiPassword;
    }

    /**
     * Sets the value of the apiPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIPassword(String value) {
        this.apiPassword = value;
    }

    /**
     * Gets the value of the propertyID property.
     * 
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     */
    public void setPropertyID(int value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalDate(String value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

}
