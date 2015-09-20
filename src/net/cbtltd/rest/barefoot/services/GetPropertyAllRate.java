
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
 *         &lt;element name="propertyid" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "propertyid",
    "partneridx"
})
@XmlRootElement(name = "GetPropertyAllRate")
public class GetPropertyAllRate {

    protected String username;
    protected String password;
    protected String barefootAccount;
    protected int propertyid;
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
     * Gets the value of the propertyid property.
     * 
     */
    public int getPropertyid() {
        return propertyid;
    }

    /**
     * Sets the value of the propertyid property.
     * 
     */
    public void setPropertyid(int value) {
        this.propertyid = value;
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
