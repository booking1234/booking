//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.11 at 11:28:40 AM IST 
//


package net.cbtltd.rest.expedia.parr.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelPenaltyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelPenaltyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="insideWindow" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="flatFee" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="perStayFee" use="required" type="{http://www.expediaconnect.com/EQC/PAR/2013/07}PerStayFeeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelPenaltyType")
public class CancelPenaltyType {

    @XmlAttribute(required = true)
    protected boolean insideWindow;
    @XmlAttribute
    protected Double flatFee;
    @XmlAttribute(required = true)
    protected String perStayFee;

    /**
     * Gets the value of the insideWindow property.
     * 
     */
    public boolean isInsideWindow() {
        return insideWindow;
    }

    /**
     * Sets the value of the insideWindow property.
     * 
     */
    public void setInsideWindow(boolean value) {
        this.insideWindow = value;
    }

    /**
     * Gets the value of the flatFee property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFlatFee() {
        return flatFee;
    }

    /**
     * Sets the value of the flatFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFlatFee(Double value) {
        this.flatFee = value;
    }

    /**
     * Gets the value of the perStayFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerStayFee() {
        return perStayFee;
    }

    /**
     * Sets the value of the perStayFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerStayFee(String value) {
        this.perStayFee = value;
    }

}
