//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:35:11 PM IST 
//


package net.cbtltd.rest.flipkey.xmlfeed;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
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
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}day_max_rate"/>
 *         &lt;element ref="{}day_min_rate"/>
 *         &lt;element ref="{}minimum_length"/>
 *         &lt;element ref="{}month_max_rate"/>
 *         &lt;element ref="{}month_min_rate"/>
 *         &lt;element ref="{}week_max_rate"/>
 *         &lt;element ref="{}week_min_rate"/>
 *         &lt;element ref="{}user_day_max_rate"/>
 *         &lt;element ref="{}user_day_min_rate"/>
 *         &lt;element ref="{}user_month_max_rate"/>
 *         &lt;element ref="{}user_month_min_rate"/>
 *         &lt;element ref="{}user_week_max_rate"/>
 *         &lt;element ref="{}user_week_min_rate"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dayMaxRateOrDayMinRateOrMinimumLength"
})
@XmlRootElement(name = "property_rate_summary")
public class PropertyRateSummary {

    @XmlElementRefs({
        @XmlElementRef(name = "user_week_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "user_month_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "month_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "user_week_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "user_month_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "week_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "day_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "day_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "week_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "month_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "user_day_max_rate", type = JAXBElement.class),
        @XmlElementRef(name = "user_day_min_rate", type = JAXBElement.class),
        @XmlElementRef(name = "minimum_length", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> dayMaxRateOrDayMinRateOrMinimumLength;

    /**
     * Gets the value of the dayMaxRateOrDayMinRateOrMinimumLength property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dayMaxRateOrDayMinRateOrMinimumLength property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDayMaxRateOrDayMinRateOrMinimumLength().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getDayMaxRateOrDayMinRateOrMinimumLength() {
        if (dayMaxRateOrDayMinRateOrMinimumLength == null) {
            dayMaxRateOrDayMinRateOrMinimumLength = new ArrayList<JAXBElement<String>>();
        }
        return this.dayMaxRateOrDayMinRateOrMinimumLength;
    }

}
