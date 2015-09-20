
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

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
 *         &lt;element name="APIUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APIPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GetPlainText" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "apiUserName",
    "apiPassword",
    "propertyID",
    "getPlainText"
})
@XmlRootElement(name = "GetDescriptions")
public class GetDescriptions {

    @XmlElement(name = "APIUserName")
    protected String apiUserName;
    @XmlElement(name = "APIPassword")
    protected String apiPassword;
    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "GetPlainText")
    protected boolean getPlainText;

    /**
     * Gets the value of the apiUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIUserName() {
        return apiUserName;
    }

    /**
     * Sets the value of the apiUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIUserName(String value) {
        this.apiUserName = value;
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
     * Gets the value of the getPlainText property.
     * 
     */
    public boolean isGetPlainText() {
        return getPlainText;
    }

    /**
     * Sets the value of the getPlainText property.
     * 
     */
    public void setGetPlainText(boolean value) {
        this.getPlainText = value;
    }

}
