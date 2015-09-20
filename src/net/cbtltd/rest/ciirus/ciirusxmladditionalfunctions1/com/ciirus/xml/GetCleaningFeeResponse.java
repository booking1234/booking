
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

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
 *         &lt;element name="GetCleaningFeeResult" type="{http://xml.ciirus.com/}CleaningFeeSettings"/>
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
    "getCleaningFeeResult"
})
@XmlRootElement(name = "GetCleaningFeeResponse")
public class GetCleaningFeeResponse {

    @XmlElement(name = "GetCleaningFeeResult", required = true)
    protected CleaningFeeSettings getCleaningFeeResult;

    /**
     * Gets the value of the getCleaningFeeResult property.
     * 
     * @return
     *     possible object is
     *     {@link CleaningFeeSettings }
     *     
     */
    public CleaningFeeSettings getGetCleaningFeeResult() {
        return getCleaningFeeResult;
    }

    /**
     * Sets the value of the getCleaningFeeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CleaningFeeSettings }
     *     
     */
    public void setGetCleaningFeeResult(CleaningFeeSettings value) {
        this.getCleaningFeeResult = value;
    }

}
