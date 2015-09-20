
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
 *         &lt;element name="RTRRentalHistoryResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "rtrRentalHistoryResult"
})
@XmlRootElement(name = "RTRRentalHistoryResponse")
public class RTRRentalHistoryResponse {

    @XmlElement(name = "RTRRentalHistoryResult")
    protected String rtrRentalHistoryResult;

    /**
     * Gets the value of the rtrRentalHistoryResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRRentalHistoryResult() {
        return rtrRentalHistoryResult;
    }

    /**
     * Sets the value of the rtrRentalHistoryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRRentalHistoryResult(String value) {
        this.rtrRentalHistoryResult = value;
    }

}
