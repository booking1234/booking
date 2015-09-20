
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddressManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressCriteria", propOrder = {
    "addressManagerId"
})
public class AddressCriteria {

    @XmlElement(name = "AddressManagerId")
    protected long addressManagerId;

    /**
     * Gets the value of the addressManagerId property.
     * 
     */
    public long getAddressManagerId() {
        return addressManagerId;
    }

    /**
     * Sets the value of the addressManagerId property.
     * 
     */
    public void setAddressManagerId(long value) {
        this.addressManagerId = value;
    }

}
