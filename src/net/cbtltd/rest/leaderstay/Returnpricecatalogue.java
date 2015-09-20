
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for returnpricecatalogue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="returnpricecatalogue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="sfrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pax" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="min_stay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "returnpricecatalogue", propOrder = {

})
public class Returnpricecatalogue {

    protected double price;
    @XmlElement(required = true)
    protected String sfrom;
    @XmlElement(required = true)
    protected String sto;
    protected int pax;
    @XmlElement(name = "min_stay")
    protected int minStay;

    /**
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the sfrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSfrom() {
        return sfrom;
    }

    /**
     * Sets the value of the sfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSfrom(String value) {
        this.sfrom = value;
    }

    /**
     * Gets the value of the sto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSto() {
        return sto;
    }

    /**
     * Sets the value of the sto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSto(String value) {
        this.sto = value;
    }

    /**
     * Gets the value of the pax property.
     * 
     */
    public int getPax() {
        return pax;
    }

    /**
     * Sets the value of the pax property.
     * 
     */
    public void setPax(int value) {
        this.pax = value;
    }

    /**
     * Gets the value of the minStay property.
     * 
     */
    public int getMinStay() {
        return minStay;
    }

    /**
     * Sets the value of the minStay property.
     * 
     */
    public void setMinStay(int value) {
        this.minStay = value;
    }

}
