
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
 *         &lt;element name="GetReviewsResult" type="{http://connect.bookt.com/Schemas/Review.xsd}ArrayOfReview" minOccurs="0"/>
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
    "getReviewsResult"
})
@XmlRootElement(name = "GetReviewsResponse")
public class GetReviewsResponse {

    @XmlElementRef(name = "GetReviewsResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfReview> getReviewsResult;

    /**
     * Gets the value of the getReviewsResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfReview }{@code >}
     *     
     */
    public JAXBElement<ArrayOfReview> getGetReviewsResult() {
        return getReviewsResult;
    }

    /**
     * Sets the value of the getReviewsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfReview }{@code >}
     *     
     */
    public void setGetReviewsResult(JAXBElement<ArrayOfReview> value) {
        this.getReviewsResult = ((JAXBElement<ArrayOfReview> ) value);
    }

}
