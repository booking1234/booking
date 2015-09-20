
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="strADate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strDDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propertyId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="num_adult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="num_pet" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="num_baby" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="num_child" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reztypeid" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "strADate",
    "strDDate",
    "propertyId",
    "numAdult",
    "numPet",
    "numBaby",
    "numChild",
    "reztypeid",
    "partneridx"
})
@XmlRootElement(name = "GetLeaseidByReztypeid")
public class GetLeaseidByReztypeid {

    protected String username;
    protected String password;
    protected String barefootAccount;
    protected String strADate;
    protected String strDDate;
    protected int propertyId;
    @XmlElement(name = "num_adult")
    protected int numAdult;
    @XmlElement(name = "num_pet")
    protected int numPet;
    @XmlElement(name = "num_baby")
    protected int numBaby;
    @XmlElement(name = "num_child")
    protected int numChild;
    protected int reztypeid;
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
     * Gets the value of the strADate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrADate() {
        return strADate;
    }

    /**
     * Sets the value of the strADate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrADate(String value) {
        this.strADate = value;
    }

    /**
     * Gets the value of the strDDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDDate() {
        return strDDate;
    }

    /**
     * Sets the value of the strDDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDDate(String value) {
        this.strDDate = value;
    }

    /**
     * Gets the value of the propertyId property.
     * 
     */
    public int getPropertyId() {
        return propertyId;
    }

    /**
     * Sets the value of the propertyId property.
     * 
     */
    public void setPropertyId(int value) {
        this.propertyId = value;
    }

    /**
     * Gets the value of the numAdult property.
     * 
     */
    public int getNumAdult() {
        return numAdult;
    }

    /**
     * Sets the value of the numAdult property.
     * 
     */
    public void setNumAdult(int value) {
        this.numAdult = value;
    }

    /**
     * Gets the value of the numPet property.
     * 
     */
    public int getNumPet() {
        return numPet;
    }

    /**
     * Sets the value of the numPet property.
     * 
     */
    public void setNumPet(int value) {
        this.numPet = value;
    }

    /**
     * Gets the value of the numBaby property.
     * 
     */
    public int getNumBaby() {
        return numBaby;
    }

    /**
     * Sets the value of the numBaby property.
     * 
     */
    public void setNumBaby(int value) {
        this.numBaby = value;
    }

    /**
     * Gets the value of the numChild property.
     * 
     */
    public int getNumChild() {
        return numChild;
    }

    /**
     * Sets the value of the numChild property.
     * 
     */
    public void setNumChild(int value) {
        this.numChild = value;
    }

    /**
     * Gets the value of the reztypeid property.
     * 
     */
    public int getReztypeid() {
        return reztypeid;
    }

    /**
     * Sets the value of the reztypeid property.
     * 
     */
    public void setReztypeid(int value) {
        this.reztypeid = value;
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
