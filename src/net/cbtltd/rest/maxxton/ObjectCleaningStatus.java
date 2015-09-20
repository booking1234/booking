
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for objectCleaningStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="objectCleaningStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CLEAN"/>
 *     &lt;enumeration value="TO_BE_CLEANED"/>
 *     &lt;enumeration value="IN_PROGRESS"/>
 *     &lt;enumeration value="CHECKED"/>
 *     &lt;enumeration value="POSTPONED"/>
 *     &lt;enumeration value="OCCUPIED_CLEAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "objectCleaningStatus")
@XmlEnum
public enum ObjectCleaningStatus {

    CLEAN,
    TO_BE_CLEANED,
    IN_PROGRESS,
    CHECKED,
    POSTPONED,
    OCCUPIED_CLEAN;

    public String value() {
        return name();
    }

    public static ObjectCleaningStatus fromValue(String v) {
        return valueOf(v);
    }

}
