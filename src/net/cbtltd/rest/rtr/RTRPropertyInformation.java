
package net.cbtltd.rest.rtr;

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
 *         &lt;element name="RTRKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RTRPropertyInfoRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "rtrKey",
    "rtrPropertyInfoRequest"
})
@XmlRootElement(name = "RTRPropertyInformation")
public class RTRPropertyInformation {

    @XmlElement(name = "RTRKey")
    protected String rtrKey;
    @XmlElement(name = "RTRPropertyInfoRequest")
    protected String rtrPropertyInfoRequest;

    /**
     * Gets the value of the rtrKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRKey() {
        return rtrKey;
    }

    /**
     * Sets the value of the rtrKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRKey(String value) {
        this.rtrKey = value;
    }

    /**
     * Gets the value of the rtrPropertyInfoRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRPropertyInfoRequest() {
        return rtrPropertyInfoRequest;
    }

    /**
     * Sets the value of the rtrPropertyInfoRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRPropertyInfoRequest(String value) {
        this.rtrPropertyInfoRequest = value;
    }

}
