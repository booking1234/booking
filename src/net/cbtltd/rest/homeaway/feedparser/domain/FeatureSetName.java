
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeatureSetName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FeatureSetName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACCOMMODATION_TYPE"/>
 *     &lt;enumeration value="ADVERTISER_PROFILE_AFFILIATIONS"/>
 *     &lt;enumeration value="AMENITY"/>
 *     &lt;enumeration value="ARRIVAL_DAY"/>
 *     &lt;enumeration value="ATTRACTIONS"/>
 *     &lt;enumeration value="BUILDING_TYPE"/>
 *     &lt;enumeration value="CAR"/>
 *     &lt;enumeration value="CLE_VACANCES"/>
 *     &lt;enumeration value="COMMUNICATION_INTERNET"/>
 *     &lt;enumeration value="DINING"/>
 *     &lt;enumeration value="DINING_CHECKBOX"/>
 *     &lt;enumeration value="ENTERTAINMENT"/>
 *     &lt;enumeration value="FINDUS"/>
 *     &lt;enumeration value="FORMS_OF_PAYMENT"/>
 *     &lt;enumeration value="GENERAL"/>
 *     &lt;enumeration value="INTERNET_ACCESS"/>
 *     &lt;enumeration value="KATRINAHOUSING"/>
 *     &lt;enumeration value="KITCHEN"/>
 *     &lt;enumeration value="LANGUAGE"/>
 *     &lt;enumeration value="LEISURE_ACTIVITIES"/>
 *     &lt;enumeration value="LIVING_ROOM"/>
 *     &lt;enumeration value="LOCAL_ACTIVITIES"/>
 *     &lt;enumeration value="LOCAL_SERVICES_AND_BUSINESSES"/>
 *     &lt;enumeration value="LOCATION_TYPE"/>
 *     &lt;enumeration value="MEALS_AVAILABILITY"/>
 *     &lt;enumeration value="MEALS_TYPES"/>
 *     &lt;enumeration value="NATURAL_FEATURES"/>
 *     &lt;enumeration value="NEARBY_ACTIVITIES"/>
 *     &lt;enumeration value="NEARBY_ATTRACTION_FACILITY"/>
 *     &lt;enumeration value="NEARBY_SERVICES"/>
 *     &lt;enumeration value="ONSITE_EQUIPMENT"/>
 *     &lt;enumeration value="ONSITE_SERVICES"/>
 *     &lt;enumeration value="OUTSIDE"/>
 *     &lt;enumeration value="PAYMENT_TYPE"/>
 *     &lt;enumeration value="POOL_SPA"/>
 *     &lt;enumeration value="PROPERTY_DESCRIPTOR"/>
 *     &lt;enumeration value="PROPERTY_SUPERTYPE"/>
 *     &lt;enumeration value="PROPERTY_TYPE"/>
 *     &lt;enumeration value="RATING"/>
 *     &lt;enumeration value="RENTAL_CHARGES"/>
 *     &lt;enumeration value="RENTAL_CHARGES_RB"/>
 *     &lt;enumeration value="ROOM_SUBTYPE"/>
 *     &lt;enumeration value="ROOM_TYPE"/>
 *     &lt;enumeration value="ROOMS"/>
 *     &lt;enumeration value="SPORTS_AND_ADVENTURE_ACTIVITIES"/>
 *     &lt;enumeration value="SUITABILITY"/>
 *     &lt;enumeration value="SUITABILITY_CHKBOX"/>
 *     &lt;enumeration value="SWIMMING_POOL"/>
 *     &lt;enumeration value="THEME"/>
 *     &lt;enumeration value="WASHING_FACILITIES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FeatureSetName")
@XmlEnum
public enum FeatureSetName {
    ACCOMMODATION_TYPE ("PKG","7"),
    ADVERTISER_PROFILE_AFFILIATIONS ("INF","18"),
    AMENITY ("INF","6"),
    ARRIVAL_DAY ("RMO","4"),
    ATTRACTIONS ("INF","7"),
    BUILDING_TYPE ("SEC","7"),
    CAR ("VEC","1"),
    CLE_VACANCES ("INF","5"),
    COMMUNICATION_INTERNET ("BCT","7"),
    DINING ("CBF","71"),
    DINING_CHECKBOX ("CBF","71"),
    ENTERTAINMENT ("ACC","18"),
    FINDUS ("INF","5"),
    FORMS_OF_PAYMENT ("PMT","0"),
    GENERAL ("HAC","0"),
    INTERNET_ACCESS ("RMA","54"),
    KATRINAHOUSING ("INF","5"),
    KITCHEN ("RMA","59"),
    LANGUAGE ("COM","0"),
    LEISURE_ACTIVITIES ("PCT","59"),
    LIVING_ROOM ("CBF","72"),
    LOCAL_ACTIVITIES ("REF","37"),
    LOCAL_SERVICES_AND_BUSINESSES ("IBT","13"),
    LOCATION_TYPE ("LOC","0"),
    MEALS_AVAILABILITY ("AMC","0"),
    MEALS_TYPES ("RSI","7"),
    NATURAL_FEATURES ("RST","109"),
    NEARBY_ACTIVITIES ("CADT","35"),
    NEARBY_ATTRACTION_FACILITY ("ACC","0"),
    NEARBY_SERVICES ("ADT","26"),
    ONSITE_EQUIPMENT ("EQP","0"),
    ONSITE_SERVICES ("ADT","25"),
    OUTSIDE ("CBF","13"),
    PAYMENT_TYPE ("PMT","0"),
    POOL_SPA ("HAC","54"),
    PROPERTY_DESCRIPTOR ("ADT","2"),
    PROPERTY_SUPERTYPE ("PCT","0"),
    PROPERTY_TYPE ("PCT","0"),
    RATING ("ADT","1"),
    RENTAL_CHARGES ("CHG","0"),
    RENTAL_CHARGES_RB ("CHG","0"),
    ROOM_SUBTYPE ("SCC","3"),
    ROOM_TYPE ("SCC","3"),
    ROOMS ("MPT","14"),
    SPORTS_AND_ADVENTURE_ACTIVITIES ("ACC","66"),
    SUITABILITY ("SUI","0"),
    SUITABILITY_CHKBOX ("SUI","0"),
    SWIMMING_POOL ("RST","106"),
    THEME ("ARC","14"),
    WASHING_FACILITIES ("VWF","0");
    String code,id;
    FeatureSetName(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static FeatureSetName fromCode(String v){
    	for(FeatureSetName obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static FeatureSetName fromValue(String v) {
        return valueOf(v);
    }

}
