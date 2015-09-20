
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for distribution-channel-restrictions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="distribution-channel-restrictions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="member" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="openMarketThirdParty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="openMarketConsumerDirect" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "distribution-channel-restrictions", propOrder = {
    "member",
    "openMarketThirdParty",
    "openMarketConsumerDirect"
})
public class DistributionChannelRestrictions {

    @XmlElement(required = true)
    protected String member;
    @XmlElement(required = true)
    protected String openMarketThirdParty;
    @XmlElement(required = true)
    protected String openMarketConsumerDirect;

    /**
     * Gets the value of the member property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMember() {
        return member;
    }

    /**
     * Sets the value of the member property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMember(String value) {
        this.member = value;
    }

    /**
     * Gets the value of the openMarketThirdParty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenMarketThirdParty() {
        return openMarketThirdParty;
    }

    /**
     * Sets the value of the openMarketThirdParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenMarketThirdParty(String value) {
        this.openMarketThirdParty = value;
    }

    /**
     * Gets the value of the openMarketConsumerDirect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenMarketConsumerDirect() {
        return openMarketConsumerDirect;
    }

    /**
     * Sets the value of the openMarketConsumerDirect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenMarketConsumerDirect(String value) {
        this.openMarketConsumerDirect = value;
    }

}
