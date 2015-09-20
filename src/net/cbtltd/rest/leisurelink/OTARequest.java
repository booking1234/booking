
package net.cbtltd.rest.leisurelink;

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
 *         &lt;element name="OTARequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "otaRequest"
})
@XmlRootElement(name = "OTARequest")
public class OTARequest {

    @XmlElement(name = "OTARequest")
    protected String otaRequest;

    /**
     * Gets the value of the otaRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTARequest() {
        return otaRequest;
    }

    /**
     * Sets the value of the otaRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTARequest(String value) {
        this.otaRequest = value;
    }

}
