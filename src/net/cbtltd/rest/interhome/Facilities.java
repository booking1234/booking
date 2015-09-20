
package net.cbtltd.rest.interhome;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Facilities.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Facilities">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotSet"/>
 *     &lt;enumeration value="Aircondition"/>
 *     &lt;enumeration value="Balcony"/>
 *     &lt;enumeration value="BBQ"/>
 *     &lt;enumeration value="Cot"/>
 *     &lt;enumeration value="Dishwasher"/>
 *     &lt;enumeration value="Fireplace"/>
 *     &lt;enumeration value="Internet"/>
 *     &lt;enumeration value="InternetWiFi"/>
 *     &lt;enumeration value="Jacuzzi"/>
 *     &lt;enumeration value="LuxuriousFurnishings"/>
 *     &lt;enumeration value="ModernFurnishings"/>
 *     &lt;enumeration value="NiceKitchen"/>
 *     &lt;enumeration value="NiceSourroundings"/>
 *     &lt;enumeration value="Parking"/>
 *     &lt;enumeration value="LiftsInhouse"/>
 *     &lt;enumeration value="NonSmoker"/>
 *     &lt;enumeration value="Pets1"/>
 *     &lt;enumeration value="Pets2"/>
 *     &lt;enumeration value="Pets3"/>
 *     &lt;enumeration value="PetsNo"/>
 *     &lt;enumeration value="PoolAll"/>
 *     &lt;enumeration value="PoolChildren"/>
 *     &lt;enumeration value="PoolIndoor"/>
 *     &lt;enumeration value="PoolPrivate"/>
 *     &lt;enumeration value="Sauna"/>
 *     &lt;enumeration value="Swimmingpool"/>
 *     &lt;enumeration value="TV"/>
 *     &lt;enumeration value="WashingMachine"/>
 *     &lt;enumeration value="Wheelchair"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Facilities")
@XmlEnum
public enum Facilities {

    @XmlEnumValue("NotSet")
    NOT_SET("NotSet"),
    @XmlEnumValue("Aircondition")
    AIRCONDITION("Aircondition"),
    @XmlEnumValue("Balcony")
    BALCONY("Balcony"),
    BBQ("BBQ"),
    @XmlEnumValue("Cot")
    COT("Cot"),
    @XmlEnumValue("Dishwasher")
    DISHWASHER("Dishwasher"),
    @XmlEnumValue("Fireplace")
    FIREPLACE("Fireplace"),
    @XmlEnumValue("Internet")
    INTERNET("Internet"),
    @XmlEnumValue("InternetWiFi")
    INTERNET_WI_FI("InternetWiFi"),
    @XmlEnumValue("Jacuzzi")
    JACUZZI("Jacuzzi"),
    @XmlEnumValue("LuxuriousFurnishings")
    LUXURIOUS_FURNISHINGS("LuxuriousFurnishings"),
    @XmlEnumValue("ModernFurnishings")
    MODERN_FURNISHINGS("ModernFurnishings"),
    @XmlEnumValue("NiceKitchen")
    NICE_KITCHEN("NiceKitchen"),
    @XmlEnumValue("NiceSourroundings")
    NICE_SOURROUNDINGS("NiceSourroundings"),
    @XmlEnumValue("Parking")
    PARKING("Parking"),
    @XmlEnumValue("LiftsInhouse")
    LIFTS_INHOUSE("LiftsInhouse"),
    @XmlEnumValue("NonSmoker")
    NON_SMOKER("NonSmoker"),
    @XmlEnumValue("Pets1")
    PETS_1("Pets1"),
    @XmlEnumValue("Pets2")
    PETS_2("Pets2"),
    @XmlEnumValue("Pets3")
    PETS_3("Pets3"),
    @XmlEnumValue("PetsNo")
    PETS_NO("PetsNo"),
    @XmlEnumValue("PoolAll")
    POOL_ALL("PoolAll"),
    @XmlEnumValue("PoolChildren")
    POOL_CHILDREN("PoolChildren"),
    @XmlEnumValue("PoolIndoor")
    POOL_INDOOR("PoolIndoor"),
    @XmlEnumValue("PoolPrivate")
    POOL_PRIVATE("PoolPrivate"),
    @XmlEnumValue("Sauna")
    SAUNA("Sauna"),
    @XmlEnumValue("Swimmingpool")
    SWIMMINGPOOL("Swimmingpool"),
    TV("TV"),
    @XmlEnumValue("WashingMachine")
    WASHING_MACHINE("WashingMachine"),
    @XmlEnumValue("Wheelchair")
    WHEELCHAIR("Wheelchair");
    private final String value;

    Facilities(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Facilities fromValue(String v) {
        for (Facilities c: Facilities.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
