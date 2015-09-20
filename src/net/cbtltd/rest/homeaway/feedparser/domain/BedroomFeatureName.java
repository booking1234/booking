
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BedroomFeatureName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BedroomFeatureName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AMENITY_KING"/>
 *     &lt;enumeration value="AMENITY_QUEEN"/>
 *     &lt;enumeration value="AMENITY_DOUBLE"/>
 *     &lt;enumeration value="AMENITY_TWIN_SINGLE"/>
 *     &lt;enumeration value="AMENITY_CHILD_BED"/>
 *     &lt;enumeration value="AMENITY_CRIB"/>
 *     &lt;enumeration value="AMENITY_SLEEP_SOFA"/>
 *     &lt;enumeration value="AMENITY_BUNK_BED"/>
 *     &lt;enumeration value="AMENITY_MURPHY_BED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BedroomFeatureName")
@XmlEnum
public enum BedroomFeatureName {

	AMENITY_KING ("BED","3"),
    AMENITY_QUEEN ("BED","5"),
    AMENITY_DOUBLE ("BED","1"),
    AMENITY_TWIN_SINGLE ("BED","8"),
    AMENITY_CHILD_BED ("BED","2"),
    AMENITY_CRIB ("BED","2"),
    AMENITY_SLEEP_SOFA ("BED","6"),
    AMENITY_BUNK_BED ("BED","4"),
    AMENITY_MURPHY_BED ("BED","4");
    String code,id;
    BedroomFeatureName(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static BedroomFeatureName fromCode(String v){
    	for(BedroomFeatureName obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static BedroomFeatureName fromValue(String v) {
        return valueOf(v);
    }

}
