
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardConsumption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardConsumption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroupNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Consumed" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ProductDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardConsumption", propOrder = {
    "groupNumber",
    "consumed",
    "productDescription"
})
public class DebitCardConsumption {

    @XmlElement(name = "GroupNumber")
    protected long groupNumber;
    @XmlElement(name = "Consumed")
    protected double consumed;
    @XmlElement(name = "ProductDescription")
    protected String productDescription;

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
     * Gets the value of the consumed property.
     * 
     */
    public double getConsumed() {
        return consumed;
    }

    /**
     * Sets the value of the consumed property.
     * 
     */
    public void setConsumed(double value) {
        this.consumed = value;
    }

    /**
     * Gets the value of the productDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDescription(String value) {
        this.productDescription = value;
    }

}
