
package net.cbtltd.rest.interhome;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ThemeFilterTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ThemeFilterTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotSet"/>
 *     &lt;enumeration value="Cheepcheep"/>
 *     &lt;enumeration value="Countryside"/>
 *     &lt;enumeration value="Familyfriendly"/>
 *     &lt;enumeration value="HolidayVillage"/>
 *     &lt;enumeration value="LakesAndMountains"/>
 *     &lt;enumeration value="Nightlife"/>
 *     &lt;enumeration value="Selection"/>
 *     &lt;enumeration value="SomewhereQuiet"/>
 *     &lt;enumeration value="SummerHoliday"/>
 *     &lt;enumeration value="Cities"/>
 *     &lt;enumeration value="SuitableForSeniors"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ThemeFilterTypes")
@XmlEnum
public enum ThemeFilterTypes {

    @XmlEnumValue("NotSet")
    NOT_SET("NotSet"),
    @XmlEnumValue("Cheepcheep")
    CHEEPCHEEP("Cheepcheep"),
    @XmlEnumValue("Countryside")
    COUNTRYSIDE("Countryside"),
    @XmlEnumValue("Familyfriendly")
    FAMILYFRIENDLY("Familyfriendly"),
    @XmlEnumValue("HolidayVillage")
    HOLIDAY_VILLAGE("HolidayVillage"),
    @XmlEnumValue("LakesAndMountains")
    LAKES_AND_MOUNTAINS("LakesAndMountains"),
    @XmlEnumValue("Nightlife")
    NIGHTLIFE("Nightlife"),
    @XmlEnumValue("Selection")
    SELECTION("Selection"),
    @XmlEnumValue("SomewhereQuiet")
    SOMEWHERE_QUIET("SomewhereQuiet"),
    @XmlEnumValue("SummerHoliday")
    SUMMER_HOLIDAY("SummerHoliday"),
    @XmlEnumValue("Cities")
    CITIES("Cities"),
    @XmlEnumValue("SuitableForSeniors")
    SUITABLE_FOR_SENIORS("SuitableForSeniors");
    private final String value;

    ThemeFilterTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ThemeFilterTypes fromValue(String v) {
        for (ThemeFilterTypes c: ThemeFilterTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
