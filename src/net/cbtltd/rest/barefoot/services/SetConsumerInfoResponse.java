
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
 *         &lt;element name="SetConsumerInfoResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "setConsumerInfoResult"
})
@XmlRootElement(name = "SetConsumerInfoResponse")
public class SetConsumerInfoResponse {

    @XmlElement(name = "SetConsumerInfoResult")
    protected int setConsumerInfoResult;

    /**
     * Gets the value of the setConsumerInfoResult property.
     * 
     */
    public int getSetConsumerInfoResult() {
        return setConsumerInfoResult;
    }

    /**
     * Sets the value of the setConsumerInfoResult property.
     * 
     */
    public void setSetConsumerInfoResult(int value) {
        this.setConsumerInfoResult = value;
    }

}
