
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EXTRA_NIGHT"/>
 *     &lt;enumeration value="NIGHTLY_WEEKDAY"/>
 *     &lt;enumeration value="NIGHTLY_WEEKEND"/>
 *     &lt;enumeration value="WEEKEND"/>
 *     &lt;enumeration value="WEEKLY"/>
 *     &lt;enumeration value="MONTHLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RateType")
@XmlEnum
public enum RateType {

    EXTRA_NIGHT,
    NIGHTLY_WEEKDAY,
    NIGHTLY_WEEKEND,
    WEEKEND,
    WEEKLY,
    MONTHLY;

    public String value() {
        return name();
    }

    public static RateType fromValue(String v) {
        return valueOf(v);
    }

}
