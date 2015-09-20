
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PropertyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PROPERTY_TYPE_APARTMENT"/>
 *     &lt;enumeration value="PROPERTY_TYPE_BARN"/>
 *     &lt;enumeration value="PROPERTY_TYPE_BUNGALOW"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CABIN"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CASTLE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CHACARA"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CHALET"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CONDO"/>
 *     &lt;enumeration value="PROPERTY_TYPE_COTTAGE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_ESTATE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_FARMHOUSE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_HOTEL"/>
 *     &lt;enumeration value="PROPERTY_TYPE_HOUSE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_HOUSE_BOAT"/>
 *     &lt;enumeration value="PROPERTY_TYPE_LODGE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_TOWNHOME"/>
 *     &lt;enumeration value="PROPERTY_TYPE_VILLA"/>
 *     &lt;enumeration value="PROPERTY_TYPE_YACHT"/>
 *     &lt;enumeration value="PROPERTY_TYPE_STUDIO"/>
 *     &lt;enumeration value="PROPERTY_TYPE_MOBILE_HOME"/>
 *     &lt;enumeration value="PROPERTY_TYPE_CHATEAU"/>
 *     &lt;enumeration value="PROPERTY_TYPE_MILL"/>
 *     &lt;enumeration value="PROPERTY_TYPE_TOWER"/>
 *     &lt;enumeration value="PROPERTY_TYPE_BOAT"/>
 *     &lt;enumeration value="PROPERTY_TYPE_RECREATIONAL_VEHICLE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_GUESTHOUSE"/>
 *     &lt;enumeration value="PROPERTY_TYPE_BUILDING"/>
 *     &lt;enumeration value="PROPERTY_TYPE_MAS"/>
 *     &lt;enumeration value="PROPERTY_TYPE_RIAD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PropertyType")
@XmlEnum
public enum PropertyType {

    PROPERTY_TYPE_APARTMENT("PCT","3"),
    PROPERTY_TYPE_BUNGALOW("PCT","5"),
    PROPERTY_TYPE_CABIN("PCT","5"),
    PROPERTY_TYPE_CASTLE("PCT","37"),
    PROPERTY_TYPE_RECREATIONAL_VEHICLE("PCT","50"),
    PROPERTY_TYPE_GUESTHOUSE("PCT","16"),
    PROPERTY_TYPE_MOBILE_HOME("PCT","25"),
    PROPERTY_TYPE_FARMHOUSE("PCT","15"),
    PROPERTY_TYPE_HOTEL("PCT","20"),
    PROPERTY_TYPE_HOUSE("PCT","16"),
    PROPERTY_TYPE_LODGE("PCT","22"),
    PROPERTY_TYPE_VILLA("PCT","35"),
    PROPERTY_TYPE_YACHT("PCT","44"),
    PROPERTY_TYPE_CHALET("PCT","7"),
    PROPERTY_TYPE_CONDO("PCT","8"),
   
    PROPERTY_TYPE_BARN("PCT","15"),
    PROPERTY_TYPE_CHACARA("PCT","53"),
    PROPERTY_TYPE_COTTAGE("PCT","5"),
    PROPERTY_TYPE_ESTATE("PCT","52"),
    PROPERTY_TYPE_TOWNHOME("PCT","8"),
    PROPERTY_TYPE_STUDIO("PCT","3"),
    PROPERTY_TYPE_HOUSE_BOAT("PCT","31"),
    PROPERTY_TYPE_CHATEAU("PCT","37"),
    PROPERTY_TYPE_MILL("PCT","53"),
    PROPERTY_TYPE_TOWER("PCT","53"),
    PROPERTY_TYPE_BOAT("PCT","31"),
    
    PROPERTY_TYPE_BUILDING("PCT","53"),
    PROPERTY_TYPE_MAS("PCT","53"),
    PROPERTY_TYPE_RIAD("PCT","53");
    String code,id;
    PropertyType(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static PropertyType fromCode(String v){
    	for(PropertyType obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    
    public String value() {
        return name();
    }

    public static PropertyType fromValue(String v) {
        return valueOf(v);
    }

}
