
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
 *         &lt;element name="GetExtrasResult" type="{http://xml.ciirus.com/}GetExtrasResult"/>
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
    "getExtrasResult"
})
@XmlRootElement(name = "GetExtrasResponse")
public class GetExtrasResponse {

    @XmlElement(name = "GetExtrasResult", required = true)
    protected GetExtrasResult getExtrasResult;

    /**
     * Gets the value of the getExtrasResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetExtrasResult }
     *     
     */
    public GetExtrasResult getGetExtrasResult() {
        return getExtrasResult;
    }

    /**
     * Sets the value of the getExtrasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetExtrasResult }
     *     
     */
    public void setGetExtrasResult(GetExtrasResult value) {
        this.getExtrasResult = value;
    }

}
