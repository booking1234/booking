
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-ownership-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ownership-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resort-ownership-info-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resort-ownership-info-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-ownership-info", propOrder = {
    "resortOwnershipInfoName",
    "resortOwnershipInfoType"
})
public class ResortOwnershipInfo {

    @XmlElement(name = "resort-ownership-info-name")
    protected String resortOwnershipInfoName;
    @XmlElement(name = "resort-ownership-info-type")
    protected String resortOwnershipInfoType;

    /**
     * Gets the value of the resortOwnershipInfoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortOwnershipInfoName() {
        return resortOwnershipInfoName;
    }

    /**
     * Sets the value of the resortOwnershipInfoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortOwnershipInfoName(String value) {
        this.resortOwnershipInfoName = value;
    }

    /**
     * Gets the value of the resortOwnershipInfoType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortOwnershipInfoType() {
        return resortOwnershipInfoType;
    }

    /**
     * Sets the value of the resortOwnershipInfoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortOwnershipInfoType(String value) {
        this.resortOwnershipInfoType = value;
    }

}
