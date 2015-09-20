
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
 *         &lt;element name="GetCheckInOutRuleResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getCheckInOutRuleResult"
})
@XmlRootElement(name = "GetCheckInOutRuleResponse")
public class GetCheckInOutRuleResponse {

    @XmlElement(name = "GetCheckInOutRuleResult")
    protected String getCheckInOutRuleResult;

    /**
     * Gets the value of the getCheckInOutRuleResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCheckInOutRuleResult() {
        return getCheckInOutRuleResult;
    }

    /**
     * Sets the value of the getCheckInOutRuleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCheckInOutRuleResult(String value) {
        this.getCheckInOutRuleResult = value;
    }

}
