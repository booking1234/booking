
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="ratePeriod" type="{}RatePeriod" maxOccurs="unbounded"/>
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
    "ratePeriod",
    "rateModifiers"
})
@XmlRootElement(name = "ratePeriods")
public class RatePeriods {

    @XmlElement(required = true)
    protected List<RatePeriod> ratePeriod;

    @XmlElement
    protected List<RateModifiers> rateModifiers;
    /**
     * Gets the value of the ratePeriod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ratePeriod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRatePeriod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RatePeriod }
     * 
     * 
     */
    public List<RatePeriod> getRatePeriod() {
        if (ratePeriod == null) {
            ratePeriod = new ArrayList<RatePeriod>();
        }
        return this.ratePeriod;
    }

    public List<RateModifiers> getRateModifiers() {
        if (rateModifiers == null) {
        	rateModifiers = new ArrayList<RateModifiers>();
        }
        return this.rateModifiers;
    }
}
