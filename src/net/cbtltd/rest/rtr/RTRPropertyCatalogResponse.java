
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
 *         &lt;element name="RTRPropertyCatalogResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "rtrPropertyCatalogResult"
})
@XmlRootElement(name = "RTRPropertyCatalogResponse")
public class RTRPropertyCatalogResponse {

    @XmlElement(name = "RTRPropertyCatalogResult")
    protected String rtrPropertyCatalogResult;

    /**
     * Gets the value of the rtrPropertyCatalogResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRPropertyCatalogResult() {
        return rtrPropertyCatalogResult;
    }

    /**
     * Sets the value of the rtrPropertyCatalogResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRPropertyCatalogResult(String value) {
        this.rtrPropertyCatalogResult = value;
    }

}
