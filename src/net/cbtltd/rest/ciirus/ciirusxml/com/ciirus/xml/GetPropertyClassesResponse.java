
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

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
 *         &lt;element name="GetPropertyClassesResult" type="{http://xml.ciirus.com/}ArrayOfPropertyClasses" minOccurs="0"/>
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
    "getPropertyClassesResult"
})
@XmlRootElement(name = "GetPropertyClassesResponse")
public class GetPropertyClassesResponse {

    @XmlElement(name = "GetPropertyClassesResult")
    protected ArrayOfPropertyClasses getPropertyClassesResult;

    /**
     * Gets the value of the getPropertyClassesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPropertyClasses }
     *     
     */
    public ArrayOfPropertyClasses getGetPropertyClassesResult() {
        return getPropertyClassesResult;
    }

    /**
     * Sets the value of the getPropertyClassesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPropertyClasses }
     *     
     */
    public void setGetPropertyClassesResult(ArrayOfPropertyClasses value) {
        this.getPropertyClassesResult = value;
    }

}
