/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */

package net.cbtltd.soap.ota.server;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferActionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransferActionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="Automatic"/>
 *     &lt;enumeration value="Mandatory"/>
 *     &lt;enumeration value="Selectable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransferActionType")
@XmlEnum
public enum TransferActionType {


    /**
     * Indicates transfer when in automatic mode.
     * 
     */
    @XmlEnumValue("Automatic")
    AUTOMATIC("Automatic"),

    /**
     * Indicates transfer required in all modes.
     * 
     */
    @XmlEnumValue("Mandatory")
    MANDATORY("Mandatory"),

    /**
     * Indicates transfer when in selectable mode.
     * 
     */
    @XmlEnumValue("Selectable")
    SELECTABLE("Selectable");
    private final String value;

    TransferActionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransferActionType fromValue(String v) {
        for (TransferActionType c: TransferActionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
