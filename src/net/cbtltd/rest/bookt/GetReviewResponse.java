
package net.cbtltd.rest.bookt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="GetReviewResult" type="{http://connect.bookt.com/Schemas/Review.xsd}Review" minOccurs="0"/>
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
    "getReviewResult"
})
@XmlRootElement(name = "GetReviewResponse")
public class GetReviewResponse {

    @XmlElementRef(name = "GetReviewResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Review> getReviewResult;

    /**
     * Gets the value of the getReviewResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Review }{@code >}
     *     
     */
    public JAXBElement<Review> getGetReviewResult() {
        return getReviewResult;
    }

    /**
     * Sets the value of the getReviewResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Review }{@code >}
     *     
     */
    public void setGetReviewResult(JAXBElement<Review> value) {
        this.getReviewResult = ((JAXBElement<Review> ) value);
    }

}
