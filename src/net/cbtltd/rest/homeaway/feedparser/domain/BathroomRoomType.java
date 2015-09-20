
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BathroomRoomType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BathroomRoomType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FULL_BATH"/>
 *     &lt;enumeration value="HALF_BATH"/>
 *     &lt;enumeration value="SHOWER_INDOOR_OR_OUTDOOR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BathroomRoomType")
@XmlEnum
public enum BathroomRoomType {

    FULL_BATH ("RMA","85"),
    HALF_BATH ("RMA","193"),
    SHOWER_INDOOR_OR_OUTDOOR ("RMA","142");
    String code,id;
    BathroomRoomType(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static BathroomRoomType fromCode(String v){
    	for(BathroomRoomType obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static BathroomRoomType fromValue(String v) {
        return valueOf(v);
    }

}
