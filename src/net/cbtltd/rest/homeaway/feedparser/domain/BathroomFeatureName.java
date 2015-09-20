
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BathroomFeatureName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BathroomFeatureName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AMENITY_TOILET"/>
 *     &lt;enumeration value="AMENITY_COMBO_TUB_SHOWER"/>
 *     &lt;enumeration value="AMENITY_TUB"/>
 *     &lt;enumeration value="AMENITY_SHOWER"/>
 *     &lt;enumeration value="AMENITY_JETTED_TUB"/>
 *     &lt;enumeration value="AMENITY_BIDET"/>
 *     &lt;enumeration value="AMENITY_OUTDOOR_SHOWER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BathroomFeatureName")
@XmlEnum
public enum BathroomFeatureName {

    AMENITY_TOILET ("HAC","243"),
    AMENITY_COMBO_TUB_SHOWER ("RMA","155"),
    AMENITY_TUB ("RMA","13"),
    AMENITY_SHOWER ("RMA","142"),
    AMENITY_JETTED_TUB ("RMA","141"),
    AMENITY_BIDET ("RMA","16"),
    AMENITY_OUTDOOR_SHOWER ("PHY","48");

    String code,id;
    BathroomFeatureName(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static BathroomFeatureName fromCode(String v){
    	for(BathroomFeatureName obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static BathroomFeatureName fromValue(String v) {
        return valueOf(v);
    }

}
