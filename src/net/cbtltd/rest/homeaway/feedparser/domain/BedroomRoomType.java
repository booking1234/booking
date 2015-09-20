
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BedroomRoomType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BedroomRoomType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BEDROOM"/>
 *     &lt;enumeration value="LIVING_SLEEPING_COMBO"/>
 *     &lt;enumeration value="OTHER_SLEEPING_AREA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BedroomRoomType")
@XmlEnum
public enum BedroomRoomType {

    BEDROOM ("CBF","76"),
    LIVING_SLEEPING_COMBO ("CBF","72"),
    OTHER_SLEEPING_AREA ("CBF","79");
    String code,id;
    BedroomRoomType(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static BedroomRoomType fromCode(String v){
    	for(BedroomRoomType obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static BedroomRoomType fromValue(String v) {
        return valueOf(v);
    }

}
