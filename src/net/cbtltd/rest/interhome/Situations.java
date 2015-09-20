
package net.cbtltd.rest.interhome;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Situations.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Situations">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotSet"/>
 *     &lt;enumeration value="ByTheSea"/>
 *     &lt;enumeration value="InAHistoricTown"/>
 *     &lt;enumeration value="InAMajorCity"/>
 *     &lt;enumeration value="InTheCountryside"/>
 *     &lt;enumeration value="InALakesideTown"/>
 *     &lt;enumeration value="SomewhereQuiet"/>
 *     &lt;enumeration value="OnAIsland"/>
 *     &lt;enumeration value="Center100"/>
 *     &lt;enumeration value="Center500"/>
 *     &lt;enumeration value="Center1000"/>
 *     &lt;enumeration value="Golf200"/>
 *     &lt;enumeration value="Golf500"/>
 *     &lt;enumeration value="Golf5000"/>
 *     &lt;enumeration value="Lake50"/>
 *     &lt;enumeration value="Lake1000"/>
 *     &lt;enumeration value="Lake10000"/>
 *     &lt;enumeration value="Sea50"/>
 *     &lt;enumeration value="Sea1000"/>
 *     &lt;enumeration value="Sea10000"/>
 *     &lt;enumeration value="Skilift50"/>
 *     &lt;enumeration value="Skilift500"/>
 *     &lt;enumeration value="Skilift10000"/>
 *     &lt;enumeration value="CountryView"/>
 *     &lt;enumeration value="LakeView"/>
 *     &lt;enumeration value="MountainView"/>
 *     &lt;enumeration value="SeaView"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Situations")
@XmlEnum
public enum Situations {

    @XmlEnumValue("NotSet")
    NOT_SET("NotSet"),
    @XmlEnumValue("ByTheSea")
    BY_THE_SEA("ByTheSea"),
    @XmlEnumValue("InAHistoricTown")
    IN_A_HISTORIC_TOWN("InAHistoricTown"),
    @XmlEnumValue("InAMajorCity")
    IN_A_MAJOR_CITY("InAMajorCity"),
    @XmlEnumValue("InTheCountryside")
    IN_THE_COUNTRYSIDE("InTheCountryside"),
    @XmlEnumValue("InALakesideTown")
    IN_A_LAKESIDE_TOWN("InALakesideTown"),
    @XmlEnumValue("SomewhereQuiet")
    SOMEWHERE_QUIET("SomewhereQuiet"),
    @XmlEnumValue("OnAIsland")
    ON_A_ISLAND("OnAIsland"),
    @XmlEnumValue("Center100")
    CENTER_100("Center100"),
    @XmlEnumValue("Center500")
    CENTER_500("Center500"),
    @XmlEnumValue("Center1000")
    CENTER_1000("Center1000"),
    @XmlEnumValue("Golf200")
    GOLF_200("Golf200"),
    @XmlEnumValue("Golf500")
    GOLF_500("Golf500"),
    @XmlEnumValue("Golf5000")
    GOLF_5000("Golf5000"),
    @XmlEnumValue("Lake50")
    LAKE_50("Lake50"),
    @XmlEnumValue("Lake1000")
    LAKE_1000("Lake1000"),
    @XmlEnumValue("Lake10000")
    LAKE_10000("Lake10000"),
    @XmlEnumValue("Sea50")
    SEA_50("Sea50"),
    @XmlEnumValue("Sea1000")
    SEA_1000("Sea1000"),
    @XmlEnumValue("Sea10000")
    SEA_10000("Sea10000"),
    @XmlEnumValue("Skilift50")
    SKILIFT_50("Skilift50"),
    @XmlEnumValue("Skilift500")
    SKILIFT_500("Skilift500"),
    @XmlEnumValue("Skilift10000")
    SKILIFT_10000("Skilift10000"),
    @XmlEnumValue("CountryView")
    COUNTRY_VIEW("CountryView"),
    @XmlEnumValue("LakeView")
    LAKE_VIEW("LakeView"),
    @XmlEnumValue("MountainView")
    MOUNTAIN_VIEW("MountainView"),
    @XmlEnumValue("SeaView")
    SEA_VIEW("SeaView");
    private final String value;

    Situations(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Situations fromValue(String v) {
        for (Situations c: Situations.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
