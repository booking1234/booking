
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for returninfoavailabilitylist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="returninfoavailabilitylist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="villaid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="vname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="is_on_request" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="persons" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bedroom_no" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bathroom_no" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="distancename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="distancekm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "returninfoavailabilitylist", propOrder = {

})
public class Returninfoavailabilitylist {

    protected int villaid;
    @XmlElement(required = true)
    protected String vname;
    @XmlElement(name = "is_on_request")
    protected int isOnRequest;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String area;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String image;
    @XmlElement(required = true)
    protected String persons;
    @XmlElement(name = "bedroom_no")
    protected int bedroomNo;
    @XmlElement(name = "bathroom_no")
    protected int bathroomNo;
    @XmlElement(required = true)
    protected String distancename;
    protected double distancekm;
    protected double price;
    @XmlElement(required = true)
    protected String currency;

    /**
     * Gets the value of the villaid property.
     * 
     */
    public int getVillaid() {
        return villaid;
    }

    /**
     * Sets the value of the villaid property.
     * 
     */
    public void setVillaid(int value) {
        this.villaid = value;
    }

    /**
     * Gets the value of the vname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVname() {
        return vname;
    }

    /**
     * Sets the value of the vname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVname(String value) {
        this.vname = value;
    }

    /**
     * Gets the value of the isOnRequest property.
     * 
     */
    public int getIsOnRequest() {
        return isOnRequest;
    }

    /**
     * Sets the value of the isOnRequest property.
     * 
     */
    public void setIsOnRequest(int value) {
        this.isOnRequest = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the persons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersons() {
        return persons;
    }

    /**
     * Sets the value of the persons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersons(String value) {
        this.persons = value;
    }

    /**
     * Gets the value of the bedroomNo property.
     * 
     */
    public int getBedroomNo() {
        return bedroomNo;
    }

    /**
     * Sets the value of the bedroomNo property.
     * 
     */
    public void setBedroomNo(int value) {
        this.bedroomNo = value;
    }

    /**
     * Gets the value of the bathroomNo property.
     * 
     */
    public int getBathroomNo() {
        return bathroomNo;
    }

    /**
     * Sets the value of the bathroomNo property.
     * 
     */
    public void setBathroomNo(int value) {
        this.bathroomNo = value;
    }

    /**
     * Gets the value of the distancename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistancename() {
        return distancename;
    }

    /**
     * Sets the value of the distancename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistancename(String value) {
        this.distancename = value;
    }

    /**
     * Gets the value of the distancekm property.
     * 
     */
    public double getDistancekm() {
        return distancekm;
    }

    /**
     * Sets the value of the distancekm property.
     * 
     */
    public void setDistancekm(double value) {
        this.distancekm = value;
    }

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
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
