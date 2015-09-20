
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
 *         &lt;element name="date1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weekly" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sleeps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="baths" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="bedrooms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="strwhere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partneridx" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "date1",
    "date2",
    "weekly",
    "sleeps",
    "baths",
    "bedrooms",
    "strwhere",
    "partneridx"
})
@XmlRootElement(name = "GetPropertyAvailabilityByTerm")
public class GetPropertyAvailabilityByTerm {

    protected String username;
    protected String password;
    protected String barefootAccount;
    protected String date1;
    protected String date2;
    protected int weekly;
    protected int sleeps;
    protected float baths;
    protected int bedrooms;
    protected String strwhere;
    protected int partneridx;

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
     * Gets the value of the date1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate1() {
        return date1;
    }

    /**
     * Sets the value of the date1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate1(String value) {
        this.date1 = value;
    }

    /**
     * Gets the value of the date2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate2() {
        return date2;
    }

    /**
     * Sets the value of the date2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate2(String value) {
        this.date2 = value;
    }

    /**
     * Gets the value of the weekly property.
     * 
     */
    public int getWeekly() {
        return weekly;
    }

    /**
     * Sets the value of the weekly property.
     * 
     */
    public void setWeekly(int value) {
        this.weekly = value;
    }

    /**
     * Gets the value of the sleeps property.
     * 
     */
    public int getSleeps() {
        return sleeps;
    }

    /**
     * Sets the value of the sleeps property.
     * 
     */
    public void setSleeps(int value) {
        this.sleeps = value;
    }

    /**
     * Gets the value of the baths property.
     * 
     */
    public float getBaths() {
        return baths;
    }

    /**
     * Sets the value of the baths property.
     * 
     */
    public void setBaths(float value) {
        this.baths = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     */
    public int getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     */
    public void setBedrooms(int value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the strwhere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrwhere() {
        return strwhere;
    }

    /**
     * Sets the value of the strwhere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrwhere(String value) {
        this.strwhere = value;
    }

    /**
     * Gets the value of the partneridx property.
     * 
     */
    public int getPartneridx() {
        return partneridx;
    }

    /**
     * Sets the value of the partneridx property.
     * 
     */
    public void setPartneridx(int value) {
        this.partneridx = value;
    }

}
