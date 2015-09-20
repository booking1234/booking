
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barefootAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leaseid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selectedServiceIDs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="waivedServiceIDs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "username",
    "password",
    "barefootAccount",
    "leaseid",
    "selectedServiceIDs",
    "waivedServiceIDs"
})
@XmlRootElement(name = "selectOptionalServices")
public class SelectOptionalServices {

    protected String username;
    protected String password;
    protected String barefootAccount;
    protected String leaseid;
    protected String selectedServiceIDs;
    protected String waivedServiceIDs;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the barefootAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarefootAccount() {
        return barefootAccount;
    }

    /**
     * Sets the value of the barefootAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarefootAccount(String value) {
        this.barefootAccount = value;
    }

    /**
     * Gets the value of the leaseid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeaseid() {
        return leaseid;
    }

    /**
     * Sets the value of the leaseid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeaseid(String value) {
        this.leaseid = value;
    }

    /**
     * Gets the value of the selectedServiceIDs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedServiceIDs() {
        return selectedServiceIDs;
    }

    /**
     * Sets the value of the selectedServiceIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedServiceIDs(String value) {
        this.selectedServiceIDs = value;
    }

    /**
     * Gets the value of the waivedServiceIDs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaivedServiceIDs() {
        return waivedServiceIDs;
    }

    /**
     * Sets the value of the waivedServiceIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaivedServiceIDs(String value) {
        this.waivedServiceIDs = value;
    }

}
