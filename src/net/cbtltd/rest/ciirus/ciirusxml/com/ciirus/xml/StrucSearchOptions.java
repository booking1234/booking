
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for strucSearchOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="strucSearchOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReturnTopX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ReturnFullDetails" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ReturnQuote" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IncludePoolHeatInQuote" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "strucSearchOptions", propOrder = {
    "returnTopX",
    "returnFullDetails",
    "returnQuote",
    "includePoolHeatInQuote"
})
public class StrucSearchOptions {

    @XmlElement(name = "ReturnTopX")
    protected int returnTopX;
    @XmlElement(name = "ReturnFullDetails")
    protected boolean returnFullDetails;
    @XmlElement(name = "ReturnQuote")
    protected boolean returnQuote;
    @XmlElement(name = "IncludePoolHeatInQuote")
    protected boolean includePoolHeatInQuote;

    /**
     * Gets the value of the returnTopX property.
     * 
     */
    public int getReturnTopX() {
        return returnTopX;
    }

    /**
     * Sets the value of the returnTopX property.
     * 
     */
    public void setReturnTopX(int value) {
        this.returnTopX = value;
    }

    /**
     * Gets the value of the returnFullDetails property.
     * 
     */
    public boolean isReturnFullDetails() {
        return returnFullDetails;
    }

    /**
     * Sets the value of the returnFullDetails property.
     * 
     */
    public void setReturnFullDetails(boolean value) {
        this.returnFullDetails = value;
    }

    /**
     * Gets the value of the returnQuote property.
     * 
     */
    public boolean isReturnQuote() {
        return returnQuote;
    }

    /**
     * Sets the value of the returnQuote property.
     * 
     */
    public void setReturnQuote(boolean value) {
        this.returnQuote = value;
    }

    /**
     * Gets the value of the includePoolHeatInQuote property.
     * 
     */
    public boolean isIncludePoolHeatInQuote() {
        return includePoolHeatInQuote;
    }

    /**
     * Sets the value of the includePoolHeatInQuote property.
     * 
     */
    public void setIncludePoolHeatInQuote(boolean value) {
        this.includePoolHeatInQuote = value;
    }

}
