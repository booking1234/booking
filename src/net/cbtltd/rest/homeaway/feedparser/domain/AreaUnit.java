
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AreaUnit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AreaUnit">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="METERS_SQUARED"/>
 *     &lt;enumeration value="SQUARE_FEET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AreaUnit")
@XmlEnum
public enum AreaUnit {


    /**
     * meters squared (m�)
     * 
     */
    METERS_SQUARED ("UOM","14"),

    /**
     * square feet (ft�)
     * 
     */
    SQUARE_FEET("UOM","13");

    String code,id;
    AreaUnit(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static AreaUnit fromCode(String v){
    	for(AreaUnit obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static AreaUnit fromValue(String v) {
        return valueOf(v); 
    }

}
