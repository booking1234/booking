
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingOwnerRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ListingOwnerRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OWN_MANAGE"/>
 *     &lt;enumeration value="OWN_NOT_MANAGE"/>
 *     &lt;enumeration value="PM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ListingOwnerRole")
@XmlEnum
public enum ListingOwnerRole {


    /**
     * The contact owns the listing and manages it.
     * 
     */
    OWN_MANAGE ("REL","13"),

    /**
     * 
     * The contact owns the listing but does not manage it.
     * 
     * 
     */
    OWN_NOT_MANAGE ("CON","11"),

    /**
     * 
     * The contact doesn't own the listing but manages it, property managers.
     * 
     * 
     */
    PM ("BCT","4");
    String code,id;
    ListingOwnerRole(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static ListingOwnerRole fromCode(String v){
    	for(ListingOwnerRole obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static ListingOwnerRole fromValue(String v) {
        return valueOf(v);
    }

}
