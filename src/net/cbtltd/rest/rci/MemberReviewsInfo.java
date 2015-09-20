
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for member-reviews-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="member-reviews-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numberOfMemberReviews" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="averageRating" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "member-reviews-info", propOrder = {
    "numberOfMemberReviews",
    "averageRating"
})
public class MemberReviewsInfo {

    protected int numberOfMemberReviews;
    protected double averageRating;

    /**
     * Gets the value of the numberOfMemberReviews property.
     * 
     */
    public int getNumberOfMemberReviews() {
        return numberOfMemberReviews;
    }

    /**
     * Sets the value of the numberOfMemberReviews property.
     * 
     */
    public void setNumberOfMemberReviews(int value) {
        this.numberOfMemberReviews = value;
    }

    /**
     * Gets the value of the averageRating property.
     * 
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the value of the averageRating property.
     * 
     */
    public void setAverageRating(double value) {
        this.averageRating = value;
    }

}
