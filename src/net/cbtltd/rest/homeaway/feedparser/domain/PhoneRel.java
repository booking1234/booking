
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhoneRel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PhoneRel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HOME"/>
 *     &lt;enumeration value="FAX"/>
 *     &lt;enumeration value="OTHER"/>
 *     &lt;enumeration value="MOBILE"/>
 *     &lt;enumeration value="PERSONAL"/>
 *     &lt;enumeration value="PRIMARY"/>
 *     &lt;enumeration value="SECONDARY"/>
 *     &lt;enumeration value="SMS"/>
 *     &lt;enumeration value="TERTIARY"/>
 *     &lt;enumeration value="WORK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PhoneRel")
@XmlEnum
public enum PhoneRel {

	HOME ("PLT","6"),
    FAX ("PTT","3"),
    OTHER ("PLT","8"),
    MOBILE ("PTT","5"),
    PERSONAL ("PTT","5"),
    PRIMARY ("PTT","5"),
    SECONDARY ("PLT","7"),
    SMS ("PTT","2"),
    TERTIARY ("PLT","8"),
    WORK ("PLT","7");
    String code,id;
    PhoneRel(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static PhoneRel fromCode(String v){
    	for(PhoneRel obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static PhoneRel fromValue(String v) {
        return valueOf(v);
    }

}
