//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vatRate",
    "commission",
    "vatCommission",
    "vatPrice"
})
@XmlRootElement(name = "VATDetails")
public class VATDetails {

    @XmlElement(name = "VATRate", required = true)
    protected String vatRate;
    @XmlElement(name = "Commission", required = true)
    protected Commission commission;
    @XmlElement(name = "VATCommission", required = true)
    protected VATCommission vatCommission;
    @XmlElement(name = "VATPrice", required = true)
    protected VATPrice vatPrice;

    /**
     * Gets the value of the vatRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVATRate() {
        return vatRate;
    }

    /**
     * Sets the value of the vatRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVATRate(String value) {
        this.vatRate = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link Commission }
     *     
     */
    public Commission getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link Commission }
     *     
     */
    public void setCommission(Commission value) {
        this.commission = value;
    }

    /**
     * Gets the value of the vatCommission property.
     * 
     * @return
     *     possible object is
     *     {@link VATCommission }
     *     
     */
    public VATCommission getVATCommission() {
        return vatCommission;
    }

    /**
     * Sets the value of the vatCommission property.
     * 
     * @param value
     *     allowed object is
     *     {@link VATCommission }
     *     
     */
    public void setVATCommission(VATCommission value) {
        this.vatCommission = value;
    }

    /**
     * Gets the value of the vatPrice property.
     * 
     * @return
     *     possible object is
     *     {@link VATPrice }
     *     
     */
    public VATPrice getVATPrice() {
        return vatPrice;
    }

    /**
     * Sets the value of the vatPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link VATPrice }
     *     
     */
    public void setVATPrice(VATPrice value) {
        this.vatPrice = value;
    }

}
