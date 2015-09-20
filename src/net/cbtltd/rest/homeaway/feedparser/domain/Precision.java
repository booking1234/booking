
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Precision.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Precision">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TRANSLATED"/>
 *     &lt;enumeration value="COUNTRY"/>
 *     &lt;enumeration value="CITY"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="STREET"/>
 *     &lt;enumeration value="MASSMOVE"/>
 *     &lt;enumeration value="EYEBALL"/>
 *     &lt;enumeration value="OWNERSEYE"/>
 *     &lt;enumeration value="ADDRESS"/>
 *     &lt;enumeration value="STATE"/>
 *     &lt;enumeration value="ZIP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Precision")
@XmlEnum
public enum Precision {

    TRANSLATED,
    COUNTRY,
    CITY,
    NONE,
    STREET,
    MASSMOVE,
    EYEBALL,
    OWNERSEYE,
    ADDRESS,
    STATE,
    ZIP;

    public String value() {
        return name();
    }

    public static Precision fromValue(String v) {
        return valueOf(v);
    }

}
