
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ePaymentTakenBy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ePaymentTakenBy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MC"/>
 *     &lt;enumeration value="Agent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ePaymentTakenBy")
@XmlEnum
public enum EPaymentTakenBy {

    MC("MC"),
    @XmlEnumValue("Agent")
    AGENT("Agent");
    private final String value;

    EPaymentTakenBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EPaymentTakenBy fromValue(String v) {
        for (EPaymentTakenBy c: EPaymentTakenBy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
