
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistanceUnit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DistanceUnit">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="KILOMETRES"/>
 *     &lt;enumeration value="METRES"/>
 *     &lt;enumeration value="MILES"/>
 *     &lt;enumeration value="MINUTES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DistanceUnit")
@XmlEnum
public enum DistanceUnit {

    KILOMETRES ("UOM","2"),
    METRES ("UOM","3"),
    MILES ("UOM","1"),
    MINUTES ("VLI","2");
    String code,id;
    DistanceUnit(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static DistanceUnit fromCode(String v){
    	for(DistanceUnit obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static DistanceUnit fromValue(String v) {
        return valueOf(v);
    }

}
