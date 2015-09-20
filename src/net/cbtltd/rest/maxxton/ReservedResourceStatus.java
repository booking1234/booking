
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservedResourceStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reservedResourceStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DELETED"/>
 *     &lt;enumeration value="DEF_CANCELLED"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="DECLINED"/>
 *     &lt;enumeration value="CHOICE"/>
 *     &lt;enumeration value="BASKET"/>
 *     &lt;enumeration value="INITIAL"/>
 *     &lt;enumeration value="OPTIONAL"/>
 *     &lt;enumeration value="PROVISIONAL"/>
 *     &lt;enumeration value="DEFINITIVE"/>
 *     &lt;enumeration value="CHECKED_IN"/>
 *     &lt;enumeration value="CHECKED_OUT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "reservedResourceStatus")
@XmlEnum
public enum ReservedResourceStatus {

    DELETED,
    DEF_CANCELLED,
    CANCELLED,
    EXPIRED,
    DECLINED,
    CHOICE,
    BASKET,
    INITIAL,
    OPTIONAL,
    PROVISIONAL,
    DEFINITIVE,
    CHECKED_IN,
    CHECKED_OUT;

    public String value() {
        return name();
    }

    public static ReservedResourceStatus fromValue(String v) {
        return valueOf(v);
    }

}
