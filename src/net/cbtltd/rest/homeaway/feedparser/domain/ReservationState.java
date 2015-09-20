
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReservationState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReservationState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CANCEL"/>
 *     &lt;enumeration value="DELETE"/>
 *     &lt;enumeration value="HOLD"/>
 *     &lt;enumeration value="RESERVE"/>
 *     &lt;enumeration value="UNAVAILABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReservationState")
@XmlEnum
public enum ReservationState {

    CANCEL,
    DELETE,
    HOLD,
    RESERVE,
    UNAVAILABLE;

    public String value() {
        return name();
    }

    public static ReservationState fromValue(String v) {
        return valueOf(v);
    }

}
