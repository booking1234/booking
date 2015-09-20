
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitFeatureName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UnitFeatureName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUITABILITY_CHILDREN_ASK"/>
 *     &lt;enumeration value="SUITABILITY_CHILDREN_WELCOME"/>
 *     &lt;enumeration value="SUITABILITY_CHILDREN_NOT_ALLOWED"/>
 *     &lt;enumeration value="SUITABILITY_PETS_ASK"/>
 *     &lt;enumeration value="SUITABILITY_PETS_CONSIDERED"/>
 *     &lt;enumeration value="SUITABILITY_PETS_NOT_ALLOWED"/>
 *     &lt;enumeration value="SUITABILITY_SMOKING_ASK"/>
 *     &lt;enumeration value="SUITABILITY_SMOKING_ALLOWED"/>
 *     &lt;enumeration value="SUITABILITY_SMOKING_NOT_ALLOWED"/>
 *     &lt;enumeration value="SUITABILITY_ACCESSIBILITY_ASK"/>
 *     &lt;enumeration value="SUITABILITY_ACCESSIBILITY_WHEELCHAIR_ACCESSIBLE"/>
 *     &lt;enumeration value="SUITABILITY_ACCESSIBILITY_LIMITED_ACCESSIBILITY"/>
 *     &lt;enumeration value="SUITABILITY_ACCESSIBILITY_WHEELCHAIR_INACCESSIBLE"/>
 *     &lt;enumeration value="SUITABILITY_MINIMUM_AGE_LIMIT"/>
 *     &lt;enumeration value="SUITABILITY_SENIOR_ADULTS_ONLY"/>
 *     &lt;enumeration value="SUITABILITY_OTHER_LONG_TERM_RENTERS"/>
 *     &lt;enumeration value="SUITABILITY_OTHER_EVENTS_ALLOWED"/>
 *     &lt;enumeration value="SUITABILITY_WHEELCHAIRS"/>
 *     &lt;enumeration value="KITCHEN_DINING_KITCHEN"/>
 *     &lt;enumeration value="KITCHEN_DINING_AREA"/>
 *     &lt;enumeration value="KITCHEN_DINING_REFRIGERATOR"/>
 *     &lt;enumeration value="KITCHEN_DINING_MICROWAVE"/>
 *     &lt;enumeration value="KITCHEN_DINING_COFFEE_MAKER"/>
 *     &lt;enumeration value="KITCHEN_DINING_DISHWASHER"/>
 *     &lt;enumeration value="KITCHEN_DINING_DISHES_UTENSILS"/>
 *     &lt;enumeration value="KITCHEN_DINING_SPICES"/>
 *     &lt;enumeration value="KITCHEN_DINING_STOVE"/>
 *     &lt;enumeration value="KITCHEN_DINING_OVEN"/>
 *     &lt;enumeration value="KITCHEN_DINING_ICE_MAKER"/>
 *     &lt;enumeration value="KITCHEN_DINING_HIGHCHAIR"/>
 *     &lt;enumeration value="KITCHEN_DINING_TOASTER"/>
 *     &lt;enumeration value="KITCHEN_DINING_ROOM"/>
 *     &lt;enumeration value="KITCHEN_DINING_RACLETTE"/>
 *     &lt;enumeration value="ENTERTAINMENT_TELEVISION"/>
 *     &lt;enumeration value="ENTERTAINMENT_STEREO"/>
 *     &lt;enumeration value="ENTERTAINMENT_VIDEO_LIBRARY"/>
 *     &lt;enumeration value="ENTERTAINMENT_MUSIC_LIBRARY"/>
 *     &lt;enumeration value="ENTERTAINMENT_VIDEO_GAMES"/>
 *     &lt;enumeration value="ENTERTAINMENT_GAMES"/>
 *     &lt;enumeration value="ENTERTAINMENT_BOOKS"/>
 *     &lt;enumeration value="ENTERTAINMENT_TOYS"/>
 *     &lt;enumeration value="ENTERTAINMENT_POOL_TABLE"/>
 *     &lt;enumeration value="ENTERTAINMENT_PING_PONG_TABLE"/>
 *     &lt;enumeration value="ENTERTAINMENT_FOOSBALL"/>
 *     &lt;enumeration value="ENTERTAINMENT_DVD"/>
 *     &lt;enumeration value="ENTERTAINMENT_SATELLITE_OR_CABLE"/>
 *     &lt;enumeration value="OUTDOOR_GRILL"/>
 *     &lt;enumeration value="OUTDOOR_DECK_PATIO_UNCOVERED"/>
 *     &lt;enumeration value="OUTDOOR_LANAI_GAZEBO_COVERED"/>
 *     &lt;enumeration value="OUTDOOR_BALCONY"/>
 *     &lt;enumeration value="OUTDOOR_GARDEN"/>
 *     &lt;enumeration value="OUTDOOR_BICYCLE"/>
 *     &lt;enumeration value="OUTDOOR_TENNIS"/>
 *     &lt;enumeration value="OUTDOOR_GOLF"/>
 *     &lt;enumeration value="OUTDOOR_BOAT"/>
 *     &lt;enumeration value="OUTDOOR_BOAT_DOCK"/>
 *     &lt;enumeration value="OUTDOOR_KAYAK_CANOE"/>
 *     &lt;enumeration value="OUTDOOR_WATER_SPORTS_GEAR"/>
 *     &lt;enumeration value="OUTDOOR_SNOW_SPORTS_GEAR"/>
 *     &lt;enumeration value="OUTDOOR_VERANDA"/>
 *     &lt;enumeration value="OUTDOOR_PETANQUE"/>
 *     &lt;enumeration value="POOL_SPA_COMMUNAL_POOL"/>
 *     &lt;enumeration value="POOL_SPA_PRIVATE_POOL"/>
 *     &lt;enumeration value="POOL_SPA_HOT_TUB"/>
 *     &lt;enumeration value="POOL_SPA_SAUNA"/>
 *     &lt;enumeration value="POOL_SPA_INDOOR_POOL"/>
 *     &lt;enumeration value="POOL_SPA_HEATED_POOL"/>
 *     &lt;enumeration value="ACCOMMODATIONS_TYPE_BED_AND_BREAKFAST"/>
 *     &lt;enumeration value="ACCOMMODATIONS_TYPE_GUEST_HOUSE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_TYPE_HOTEL"/>
 *     &lt;enumeration value="ACCOMMODATIONS_TYPE_MOTEL"/>
 *     &lt;enumeration value="ACCOMMODATIONS_MEALS_GUESTS_FURNISH_OWN"/>
 *     &lt;enumeration value="ACCOMMODATIONS_MEALS_CATERING_AVAILABLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_BREAKFAST_NOT_AVAILABLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_BREAKFAST_BOOKING_POSSIBLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_BREAKFAST_INCLUDED_IN_PRICE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_LUNCH_NOT_AVAILABLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_LUNCH_BOOKING_POSSIBLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_LUNCH_INCLUDED_IN_PRICE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_DINNER_NOT_AVAILABLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_DINNER_BOOKING_POSSIBLE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_DINNER_INCLUDED_IN_PRICE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_HOUSE_CLEANING_INCLUDED"/>
 *     &lt;enumeration value="ACCOMMODATIONS_HOUSE_CLEANING_OPTIONAL"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_CHAUFFEUR"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_CONCIERGE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_PRIVATE_CHEF"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_MASSAGE"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_STAFF"/>
 *     &lt;enumeration value="ACCOMMODATIONS_OTHER_SERVICES_CAR_AVAILABLE"/>
 *     &lt;enumeration value="THEMES_FAMILY"/>
 *     &lt;enumeration value="THEMES_AWAY_FROM_IT_ALL"/>
 *     &lt;enumeration value="THEMES_LUXURY"/>
 *     &lt;enumeration value="THEMES_TOURIST_ATTRACTIONS"/>
 *     &lt;enumeration value="THEMES_ROMANTIC"/>
 *     &lt;enumeration value="THEMES_BUDGET"/>
 *     &lt;enumeration value="THEMES_HISTORIC"/>
 *     &lt;enumeration value="THEMES_ADVENTURE"/>
 *     &lt;enumeration value="THEMES_HOLIDAY_COMPLEX"/>
 *     &lt;enumeration value="THEMES_SPA"/>
 *     &lt;enumeration value="THEMES_FARM_HOLIDAYS"/>
 *     &lt;enumeration value="THEMES_SPORTS_ACTIVITIES"/>
 *     &lt;enumeration value="AMENITIES_INTERNET"/>
 *     &lt;enumeration value="AMENITIES_FIREPLACE"/>
 *     &lt;enumeration value="AMENITIES_WOOD_STOVE"/>
 *     &lt;enumeration value="AMENITIES_AIR_CONDITIONING"/>
 *     &lt;enumeration value="AMENITIES_HEATING"/>
 *     &lt;enumeration value="AMENITIES_LINENS"/>
 *     &lt;enumeration value="AMENITIES_TOWELS"/>
 *     &lt;enumeration value="AMENITIES_WASHER"/>
 *     &lt;enumeration value="AMENITIES_DRYER"/>
 *     &lt;enumeration value="AMENITIES_PARKING"/>
 *     &lt;enumeration value="AMENITIES_GARAGE"/>
 *     &lt;enumeration value="AMENITIES_TELEPHONE"/>
 *     &lt;enumeration value="AMENITIES_LIVING_ROOM"/>
 *     &lt;enumeration value="AMENITIES_FITNESS_ROOM"/>
 *     &lt;enumeration value="AMENITIES_GAME_ROOM"/>
 *     &lt;enumeration value="AMENITIES_IRON_BOARD"/>
 *     &lt;enumeration value="AMENITIES_HAIR_DRYER"/>
 *     &lt;enumeration value="AMENITIES_ELEVATOR"/>
 *     &lt;enumeration value="ARRIVAL_DAY_FLEXIBLE"/>
 *     &lt;enumeration value="ARRIVAL_DAY_NEGOTIABLE"/>
 *     &lt;enumeration value="ARRIVAL_DAY_MONDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_TUESDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_WEDNESDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_THURSDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_FRIDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_SATURDAY"/>
 *     &lt;enumeration value="ARRIVAL_DAY_SUNDAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitFeatureName")
@XmlEnum
public enum UnitFeatureName {

    SUITABILITY_CHILDREN_ASK ("RSI","13"),
    SUITABILITY_CHILDREN_WELCOME ("HAC","218"),
    SUITABILITY_CHILDREN_NOT_ALLOWED ("HIC","3"),
    SUITABILITY_PETS_ASK ("PET","7"),
    SUITABILITY_PETS_CONSIDERED ("SUI","4"),
    SUITABILITY_PETS_NOT_ALLOWED ("OTC","4"),
    SUITABILITY_SMOKING_ASK ("RMA","101"),
    SUITABILITY_SMOKING_ALLOWED ("RMA","101"),
    SUITABILITY_SMOKING_NOT_ALLOWED ("ROW","11"),
    SUITABILITY_ACCESSIBILITY_ASK ("SUI","3"),
    SUITABILITY_ACCESSIBILITY_WHEELCHAIR_ACCESSIBLE ("PHY","24"),
    SUITABILITY_ACCESSIBILITY_LIMITED_ACCESSIBILITY ("AST","88"),
    SUITABILITY_ACCESSIBILITY_WHEELCHAIR_INACCESSIBLE ("AST","88"),
    SUITABILITY_MINIMUM_AGE_LIMIT ("AQC","0"),
    SUITABILITY_SENIOR_ADULTS_ONLY ("AQC","11"),
    SUITABILITY_OTHER_LONG_TERM_RENTERS ("ETT","18"),
    SUITABILITY_OTHER_EVENTS_ALLOWED ("ETT","18"),
    SUITABILITY_WHEELCHAIRS ("EQP","54"),
    KITCHEN_DINING_KITCHEN ("RMA","59"),
    KITCHEN_DINING_AREA ("RMA","60"),
    KITCHEN_DINING_REFRIGERATOR ("CBF","16"),
    KITCHEN_DINING_MICROWAVE ("RMA","68"),
    KITCHEN_DINING_COFFEE_MAKER ("RMA","19"),
    KITCHEN_DINING_DISHWASHER ("RMA","32"),
    KITCHEN_DINING_DISHES_UTENSILS ("RMA","237"),
    KITCHEN_DINING_SPICES ("RMA","237"),
    KITCHEN_DINING_STOVE ("RMA","237"),
    KITCHEN_DINING_OVEN ("RMA","68"),
    KITCHEN_DINING_ICE_MAKER ("HAC","52"),
    KITCHEN_DINING_HIGHCHAIR ("RLT","8"),
    KITCHEN_DINING_TOASTER ("RMA","167"),
    KITCHEN_DINING_ROOM ("RST","132"),
    KITCHEN_DINING_RACLETTE ("RST","132"),
    ENTERTAINMENT_TELEVISION ("EQP","38"),
    ENTERTAINMENT_STEREO ("CBF","66"),
    ENTERTAINMENT_VIDEO_LIBRARY ("CBF","62"),
    ENTERTAINMENT_MUSIC_LIBRARY ("CBF","61"),
    ENTERTAINMENT_VIDEO_GAMES ("HAC","123"),
    ENTERTAINMENT_GAMES ("HAC","44"),
    ENTERTAINMENT_BOOKS ("ACC","27"),
    ENTERTAINMENT_TOYS ("RSI","13"),
    ENTERTAINMENT_POOL_TABLE ("HAC","193"),
    ENTERTAINMENT_PING_PONG_TABLE ("HAC","193"),
    ENTERTAINMENT_FOOSBALL ("RST","26"),
    ENTERTAINMENT_DVD ("RMA","163"),
    ENTERTAINMENT_SATELLITE_OR_CABLE ("RMP","210"),
    OUTDOOR_GRILL ("HAC","118"),
    OUTDOOR_DECK_PATIO_UNCOVERED ("RMA","243"),
    OUTDOOR_LANAI_GAZEBO_COVERED ("RMA","243"),
    OUTDOOR_BALCONY ("CBF","1"),
    OUTDOOR_GARDEN ("RST","134"),
    OUTDOOR_BICYCLE ("TRP","1"),
    OUTDOOR_TENNIS ("RCC","15"),
    OUTDOOR_GOLF ("TVS","5"),
    OUTDOOR_BOAT ("TRP","2"),
    OUTDOOR_BOAT_DOCK ("ACC","6"),
    OUTDOOR_KAYAK_CANOE ("RST","65"),
    OUTDOOR_WATER_SPORTS_GEAR ("ACC","54"),
    OUTDOOR_SNOW_SPORTS_GEAR ("RST","87"),
    OUTDOOR_VERANDA ("RMA","99"),
    OUTDOOR_PETANQUE ("RMA","99"),
    POOL_SPA_COMMUNAL_POOL ("RCC","11"),
    POOL_SPA_PRIVATE_POOL ("RCC","11"),
    POOL_SPA_HOT_TUB ("RCC","11"),
    POOL_SPA_SAUNA ("REC","61"),
    POOL_SPA_INDOOR_POOL ("RCC","11"),
    POOL_SPA_HEATED_POOL ("RCC","11"),
    ACCOMMODATIONS_TYPE_BED_AND_BREAKFAST ("MPT","3"),
    ACCOMMODATIONS_TYPE_GUEST_HOUSE ("PCT","16"),
    ACCOMMODATIONS_TYPE_HOTEL ("GUE","0"),
    ACCOMMODATIONS_TYPE_MOTEL ("PCT","27"),
    ACCOMMODATIONS_MEALS_GUESTS_FURNISH_OWN ("MPT","0"),
    ACCOMMODATIONS_MEALS_CATERING_AVAILABLE ("AMC","0"),
    ACCOMMODATIONS_BREAKFAST_NOT_AVAILABLE ("OTC","4"),
    ACCOMMODATIONS_BREAKFAST_BOOKING_POSSIBLE ("EVT","4"),
    ACCOMMODATIONS_BREAKFAST_INCLUDED_IN_PRICE ("EVT","4"),
    ACCOMMODATIONS_LUNCH_NOT_AVAILABLE ("EVT","5"),
    ACCOMMODATIONS_LUNCH_BOOKING_POSSIBLE ("EVT","5"),
    ACCOMMODATIONS_LUNCH_INCLUDED_IN_PRICE ("EVT","5"),
    ACCOMMODATIONS_DINNER_NOT_AVAILABLE ("AMC","3"),
    ACCOMMODATIONS_DINNER_BOOKING_POSSIBLE ("AMC","3"),
    ACCOMMODATIONS_DINNER_INCLUDED_IN_PRICE ("AMC","3"),
    ACCOMMODATIONS_HOUSE_CLEANING_INCLUDED ("CSC","6"),
    ACCOMMODATIONS_HOUSE_CLEANING_OPTIONAL ("CSC","6"),
    ACCOMMODATIONS_OTHER_SERVICES_CHAUFFEUR ("RCC","20"),
    ACCOMMODATIONS_OTHER_SERVICES_CONCIERGE ("RPT","2"),
    ACCOMMODATIONS_OTHER_SERVICES_PRIVATE_CHEF ("RSI","4"),
    ACCOMMODATIONS_OTHER_SERVICES_MASSAGE ("HAC","61"),
    ACCOMMODATIONS_OTHER_SERVICES_STAFF ("HAC","61"),
    ACCOMMODATIONS_OTHER_SERVICES_CAR_AVAILABLE ("UIT","8"),
    THEMES_FAMILY ("DIS","5"),
    THEMES_AWAY_FROM_IT_ALL ("RPT","7"),
    THEMES_LUXURY ("PCT","57"),
    THEMES_TOURIST_ATTRACTIONS ("BCC","13"),
    THEMES_ROMANTIC ("GRP","2"),
    THEMES_BUDGET ("SEG","2"),
    THEMES_HISTORIC ("ACC","23"),
    THEMES_ADVENTURE ("RSN","10"),
    THEMES_HOLIDAY_COMPLEX ("PCT","18"),
    THEMES_SPA ("CSC","3"),
    THEMES_FARM_HOLIDAYS ("PCT","18"),
    THEMES_SPORTS_ACTIVITIES ("RST","143"),
    AMENITIES_INTERNET ("BCT","7"),
    AMENITIES_FIREPLACE ("RMA","41"),
    AMENITIES_WOOD_STOVE ("RMA","105"),
    AMENITIES_AIR_CONDITIONING ("RMA","2"),
    AMENITIES_HEATING ("RST","153"),
    AMENITIES_LINENS ("RMA","234"),
    AMENITIES_TOWELS ("REC","43"),
    AMENITIES_WASHER ("RMA","149"),
    AMENITIES_DRYER ("RMA","149"),
    AMENITIES_PARKING ("HAC","63"),
    AMENITIES_GARAGE ("TRP","6"),
    AMENITIES_TELEPHONE ("RMA","107"),
    AMENITIES_LIVING_ROOM ("CBF","72"),
    AMENITIES_FITNESS_ROOM ("RST","21"),
    AMENITIES_GAME_ROOM ("HAC","44"),
    AMENITIES_IRON_BOARD ("RMA","56"),
    AMENITIES_HAIR_DRYER ("RMA","50"),
    AMENITIES_ELEVATOR ("HAC","33"),
    ARRIVAL_DAY_FLEXIBLE ("SRO","30"),
    ARRIVAL_DAY_NEGOTIABLE ("SRO","30"),
    ARRIVAL_DAY_MONDAY ("Mon","CheckIn"),
    ARRIVAL_DAY_TUESDAY ("Tue","CheckIn"),
    ARRIVAL_DAY_WEDNESDAY ("Wed","CheckIn"),
    ARRIVAL_DAY_THURSDAY ("Thu","CheckIn"),
    ARRIVAL_DAY_FRIDAY ("Fri","CheckIn"),
    ARRIVAL_DAY_SATURDAY ("Sat","CheckIn"),
    ARRIVAL_DAY_SUNDAY ("Sun","CheckIn");
    String code,id;
    UnitFeatureName(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static UnitFeatureName fromCode(String v){
    	for(UnitFeatureName obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static UnitFeatureName fromValue(String v) {
        return valueOf(v);
    }

}
