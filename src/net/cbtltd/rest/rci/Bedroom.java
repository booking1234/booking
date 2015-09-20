
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bedroom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bedroom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bedroomType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bedRoomTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="config" type="{}unit-bedroom-config" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bedroom", propOrder = {
    "bedroomType",
    "bedRoomTypeDesc",
    "config"
})
public class Bedroom {

    @XmlElement(required = true)
    protected String bedroomType;
    protected String bedRoomTypeDesc;
    protected UnitBedroomConfig config;

    /**
     * Gets the value of the bedroomType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedroomType() {
        return bedroomType;
    }

    /**
     * Sets the value of the bedroomType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedroomType(String value) {
        this.bedroomType = value;
    }

    /**
     * Gets the value of the bedRoomTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedRoomTypeDesc() {
        return bedRoomTypeDesc;
    }

    /**
     * Sets the value of the bedRoomTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedRoomTypeDesc(String value) {
        this.bedRoomTypeDesc = value;
    }

    /**
     * Gets the value of the config property.
     * 
     * @return
     *     possible object is
     *     {@link UnitBedroomConfig }
     *     
     */
    public UnitBedroomConfig getConfig() {
        return config;
    }

    /**
     * Sets the value of the config property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitBedroomConfig }
     *     
     */
    public void setConfig(UnitBedroomConfig value) {
        this.config = value;
    }

}
