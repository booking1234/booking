//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "roundTripID",
    "sequence",
    "roundTripTransport",
    "accommodation"
})
@XmlRootElement(name = "RoundTrip")
public class RoundTrip {

    @XmlElement(name = "RoundTripID")
    protected String roundTripID;
    @XmlElement(name = "Sequence")
    protected Sequence sequence;
    @XmlElement(name = "RoundTripTransport")
    protected List<RoundTripTransport> roundTripTransport;
    @XmlElement(name = "Accommodation")
    protected List<Accommodation> accommodation;

    /**
     * Gets the value of the roundTripID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoundTripID() {
        return roundTripID;
    }

    /**
     * Sets the value of the roundTripID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoundTripID(String value) {
        this.roundTripID = value;
    }

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link Sequence }
     *     
     */
    public Sequence getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sequence }
     *     
     */
    public void setSequence(Sequence value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the roundTripTransport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roundTripTransport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoundTripTransport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoundTripTransport }
     * 
     * 
     */
    public List<RoundTripTransport> getRoundTripTransport() {
        if (roundTripTransport == null) {
            roundTripTransport = new ArrayList<RoundTripTransport>();
        }
        return this.roundTripTransport;
    }

    /**
     * Gets the value of the accommodation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accommodation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccommodation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Accommodation }
     * 
     * 
     */
    public List<Accommodation> getAccommodation() {
        if (accommodation == null) {
            accommodation = new ArrayList<Accommodation>();
        }
        return this.accommodation;
    }

}
