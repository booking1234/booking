
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for infoparameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoparameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="villaid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="check" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="getIds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoparameters", propOrder = {

})
public class Infoparameters {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String villaid;
    protected int check;
    protected int getIds;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the villaid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVillaid() {
        return villaid;
    }

    /**
     * Sets the value of the villaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVillaid(String value) {
        this.villaid = value;
    }

    /**
     * Gets the value of the check property.
     * 
     */
    public int getCheck() {
        return check;
    }

    /**
     * Sets the value of the check property.
     * 
     */
    public void setCheck(int value) {
        this.check = value;
    }

    /**
     * Gets the value of the getIds property.
     * 
     */
    public int getGetIds() {
        return getIds;
    }

    /**
     * Sets the value of the getIds property.
     * 
     */
    public void setGetIds(int value) {
        this.getIds = value;
    }

}
