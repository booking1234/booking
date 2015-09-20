
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
 *         &lt;element name="GetCommunitiesResult" type="{http://xml.ciirus.com/}ArrayOfCommunities" minOccurs="0"/>
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
    "getCommunitiesResult"
})
@XmlRootElement(name = "GetCommunitiesResponse")
public class GetCommunitiesResponse {

    @XmlElement(name = "GetCommunitiesResult")
    protected ArrayOfCommunities getCommunitiesResult;

    /**
     * Gets the value of the getCommunitiesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCommunities }
     *     
     */
    public ArrayOfCommunities getGetCommunitiesResult() {
        return getCommunitiesResult;
    }

    /**
     * Sets the value of the getCommunitiesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCommunities }
     *     
     */
    public void setGetCommunitiesResult(ArrayOfCommunities value) {
        this.getCommunitiesResult = value;
    }

}
