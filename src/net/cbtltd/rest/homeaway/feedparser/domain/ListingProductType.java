
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingProductType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ListingProductType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUBSCRIPTION_V1"/>
 *     &lt;enumeration value="SUBSCRIPTION_V2"/>
 *     &lt;enumeration value="PAY_PER_BOOKING"/>
 *     &lt;enumeration value="PAY_PER_BOOKING_EXTERNAL"/>
 *     &lt;enumeration value="PREVIEW"/>
 *     &lt;enumeration value="PAY_PER_LEAD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ListingProductType")
@XmlEnum
public enum ListingProductType {

    @XmlEnumValue("SUBSCRIPTION_V1")
    SUBSCRIPTION_V_1("SUBSCRIPTION_V1"),
    @XmlEnumValue("SUBSCRIPTION_V2")
    SUBSCRIPTION_V_2("SUBSCRIPTION_V2"),
    PAY_PER_BOOKING("PAY_PER_BOOKING"),
    PAY_PER_BOOKING_EXTERNAL("PAY_PER_BOOKING_EXTERNAL"),
    PREVIEW("PREVIEW"),
    PAY_PER_LEAD("PAY_PER_LEAD");
    private final String value;

    ListingProductType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ListingProductType fromValue(String v) {
        for (ListingProductType c: ListingProductType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
