
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingFeatureName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ListingFeatureName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SPORTS_BASKETBALL_COURT"/>
 *     &lt;enumeration value="SPORTS_CYCLING"/>
 *     &lt;enumeration value="SPORTS_DEEPSEA_FISHING"/>
 *     &lt;enumeration value="SPORTS_EQUESTRIAN_EVENTS"/>
 *     &lt;enumeration value="SPORTS_FISHING"/>
 *     &lt;enumeration value="SPORTS_FISHING_FLY"/>
 *     &lt;enumeration value="SPORTS_FISHING_FRESHWATER"/>
 *     &lt;enumeration value="SPORTS_GOLF"/>
 *     &lt;enumeration value="SPORTS_GOLF_OPTIONAL"/>
 *     &lt;enumeration value="SPORTS_HIKING"/>
 *     &lt;enumeration value="SPORTS_HUNTING"/>
 *     &lt;enumeration value="SPORTS_HUNTING_BIG_GAME"/>
 *     &lt;enumeration value="SPORTS_HUNTING_SMALL_GAME"/>
 *     &lt;enumeration value="SPORTS_ICE_SKATING"/>
 *     &lt;enumeration value="SPORTS_JET_SKIING"/>
 *     &lt;enumeration value="SPORTS_MOUNTAIN_BIKING"/>
 *     &lt;enumeration value="SPORTS_MOUNTAIN_CLIMBING"/>
 *     &lt;enumeration value="SPORTS_MOUNTAINEERING"/>
 *     &lt;enumeration value="SPORTS_PARAGLIDING"/>
 *     &lt;enumeration value="SPORTS_PIER_FISHING"/>
 *     &lt;enumeration value="SPORTS_RAFTING"/>
 *     &lt;enumeration value="SPORTS_ROLLER_BLADING"/>
 *     &lt;enumeration value="SPORTS_SAILING"/>
 *     &lt;enumeration value="SPORTS_SCUBA_OR_SNORKELING"/>
 *     &lt;enumeration value="SPORTS_SKI_LIFT_PRIVILEDGES"/>
 *     &lt;enumeration value="SPORTS_SKI_LIFT_PRIVILEDGES_OPTIONAL"/>
 *     &lt;enumeration value="SPORTS_SKI_LIFT_PRIVILEGES"/>
 *     &lt;enumeration value="SPORTS_SKI_LIFT_PRIVILEGES_OPTIONAL"/>
 *     &lt;enumeration value="SPORTS_SKIING"/>
 *     &lt;enumeration value="SPORTS_SNORKELING"/>
 *     &lt;enumeration value="SPORTS_FISHING_BAY"/>
 *     &lt;enumeration value="SPORTS_SPELUNKING"/>
 *     &lt;enumeration value="SPORTS_FISHING_SURF"/>
 *     &lt;enumeration value="SPORTS_SURFING"/>
 *     &lt;enumeration value="SPORTS_SWIMMING"/>
 *     &lt;enumeration value="SPORTS_TENNIS"/>
 *     &lt;enumeration value="SPORTS_SKIING_WATER"/>
 *     &lt;enumeration value="SPORTS_TUBING_WATER"/>
 *     &lt;enumeration value="SPORTS_WHITEWATER_RAFTING"/>
 *     &lt;enumeration value="SPORTS_WIND_SURFING"/>
 *     &lt;enumeration value="SPORTS_CROSS_COUNTRY_SKIING"/>
 *     &lt;enumeration value="SPORTS_HOT_AIR_BALLOONING"/>
 *     &lt;enumeration value="SPORTS_KAYAKING"/>
 *     &lt;enumeration value="SPORTS_PARASAILING"/>
 *     &lt;enumeration value="SPORTS_RACQUETBALL"/>
 *     &lt;enumeration value="SPORTS_ROCK_CLIMBING"/>
 *     &lt;enumeration value="SPORTS_SNOWBOARDING"/>
 *     &lt;enumeration value="SPORTS_SNOWMOBILING"/>
 *     &lt;enumeration value="CAR_NECESSARY"/>
 *     &lt;enumeration value="CAR_NOT_NECESSARY"/>
 *     &lt;enumeration value="CAR_RECOMMENDED"/>
 *     &lt;enumeration value="LOCATION_TYPE_BEACH"/>
 *     &lt;enumeration value="LOCATION_TYPE_BEACH_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_BEACH_FRONT"/>
 *     &lt;enumeration value="LOCATION_TYPE_LAKE"/>
 *     &lt;enumeration value="LOCATION_TYPE_LAKE_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_LAKE_FRONT"/>
 *     &lt;enumeration value="LOCATION_TYPE_NEAR_OCEAN"/>
 *     &lt;enumeration value="LOCATION_TYPE_OCEAN_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_OCEAN_FRONT"/>
 *     &lt;enumeration value="LOCATION_TYPE_RIVER"/>
 *     &lt;enumeration value="LOCATION_TYPE_WATERFRONT"/>
 *     &lt;enumeration value="LOCATION_TYPE_WATER_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_MOUNTAIN"/>
 *     &lt;enumeration value="LOCATION_TYPE_MOUNTAIN_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_MONUMENT_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_SKI_IN_OUT"/>
 *     &lt;enumeration value="LOCATION_TYPE_SKI_IN"/>
 *     &lt;enumeration value="LOCATION_TYPE_SKI_OUT"/>
 *     &lt;enumeration value="LOCATION_TYPE_GOLF_COURSE_FRONT"/>
 *     &lt;enumeration value="LOCATION_TYPE_GOLF_COURSE_VIEW"/>
 *     &lt;enumeration value="LOCATION_TYPE_DOWNTOWN"/>
 *     &lt;enumeration value="LOCATION_TYPE_RESORT"/>
 *     &lt;enumeration value="LOCATION_TYPE_RURAL"/>
 *     &lt;enumeration value="LOCATION_TYPE_TOWN"/>
 *     &lt;enumeration value="LOCATION_TYPE_VILLAGE"/>
 *     &lt;enumeration value="ATTRACTIONS_ARBORETUM"/>
 *     &lt;enumeration value="ATTRACTIONS_AUTUMN_FOLIAGE"/>
 *     &lt;enumeration value="ATTRACTIONS_BAY"/>
 *     &lt;enumeration value="ATTRACTIONS_BOTANICAL_GARDEN"/>
 *     &lt;enumeration value="ATTRACTIONS_CAVE"/>
 *     &lt;enumeration value="ATTRACTIONS_CHURCHES"/>
 *     &lt;enumeration value="ATTRACTIONS_CINEMAS"/>
 *     &lt;enumeration value="ATTRACTIONS_COIN_LAUNDRY"/>
 *     &lt;enumeration value="ATTRACTIONS_DUTY_FREE"/>
 *     &lt;enumeration value="ATTRACTIONS_FESTIVALS"/>
 *     &lt;enumeration value="ATTRACTIONS_FORESTS"/>
 *     &lt;enumeration value="ATTRACTIONS_HEALTH_BEAUTY_SPA"/>
 *     &lt;enumeration value="ATTRACTIONS_LIBRARY"/>
 *     &lt;enumeration value="ATTRACTIONS_LIVE_THEATER"/>
 *     &lt;enumeration value="ATTRACTIONS_MARINA"/>
 *     &lt;enumeration value="ATTRACTIONS_MUSEUMS"/>
 *     &lt;enumeration value="ATTRACTIONS_NUDE_BEACH"/>
 *     &lt;enumeration value="ATTRACTIONS_PLAYGROUND"/>
 *     &lt;enumeration value="ATTRACTIONS_POND"/>
 *     &lt;enumeration value="ATTRACTIONS_RAIN_FORESTS"/>
 *     &lt;enumeration value="ATTRACTIONS_REC_CENTER"/>
 *     &lt;enumeration value="ATTRACTIONS_REEF"/>
 *     &lt;enumeration value="ATTRACTIONS_RESTAURANTS"/>
 *     &lt;enumeration value="ATTRACTIONS_RUINS"/>
 *     &lt;enumeration value="ATTRACTIONS_SYNAGOGUES"/>
 *     &lt;enumeration value="ATTRACTIONS_THEME_PARKS"/>
 *     &lt;enumeration value="ATTRACTIONS_VOLCANO"/>
 *     &lt;enumeration value="ATTRACTIONS_WATER_PARKS"/>
 *     &lt;enumeration value="ATTRACTIONS_WATERFALLS"/>
 *     &lt;enumeration value="ATTRACTIONS_WINERY_TOURS"/>
 *     &lt;enumeration value="ATTRACTIONS_ZOO"/>
 *     &lt;enumeration value="LEISURE_ANTIQUING"/>
 *     &lt;enumeration value="LEISURE_BEACHCOMBING"/>
 *     &lt;enumeration value="LEISURE_BIRD_WATCHING"/>
 *     &lt;enumeration value="LEISURE_ECO_TOURISM"/>
 *     &lt;enumeration value="LEISURE_GAMBLING"/>
 *     &lt;enumeration value="LEISURE_HORSEBACK_RIDING"/>
 *     &lt;enumeration value="LEISURE_HORSESHOES"/>
 *     &lt;enumeration value="LEISURE_LUAUS"/>
 *     &lt;enumeration value="LEISURE_OUTLET_SHOPPING"/>
 *     &lt;enumeration value="LEISURE_PADDLE_BOATING"/>
 *     &lt;enumeration value="LEISURE_PHOTOGRAPHY"/>
 *     &lt;enumeration value="LEISURE_SCENIC_DRIVES"/>
 *     &lt;enumeration value="LEISURE_SIGHT_SEEING"/>
 *     &lt;enumeration value="LEISURE_SLEDDING"/>
 *     &lt;enumeration value="LEISURE_WALKING"/>
 *     &lt;enumeration value="LEISURE_WHALE_WATCHING"/>
 *     &lt;enumeration value="LEISURE_BOATING"/>
 *     &lt;enumeration value="LEISURE_MINIATURE_GOLF"/>
 *     &lt;enumeration value="LEISURE_SHELLING"/>
 *     &lt;enumeration value="LEISURE_SHOPPING"/>
 *     &lt;enumeration value="LEISURE_SHUFFLEBOARD"/>
 *     &lt;enumeration value="LEISURE_WILDLIFE_VIEWING"/>
 *     &lt;enumeration value="LEISURE_DISCO"/>
 *     &lt;enumeration value="LEISURE_BOWLING"/>
 *     &lt;enumeration value="LEISURE_WATER_SPORTS"/>
 *     &lt;enumeration value="LEISURE_THALASSOTHERAPY"/>
 *     &lt;enumeration value="LEISURE_THERMALISME"/>
 *     &lt;enumeration value="LEISURE_NATURISME"/>
 *     &lt;enumeration value="LOCAL_ATM_BANK"/>
 *     &lt;enumeration value="LOCAL_FITNESS_CENTER"/>
 *     &lt;enumeration value="LOCAL_GROCERIES"/>
 *     &lt;enumeration value="LOCAL_HOSPITAL"/>
 *     &lt;enumeration value="LOCAL_LAUNDROMAT"/>
 *     &lt;enumeration value="LOCAL_MASSAGE_THERAPIST"/>
 *     &lt;enumeration value="LOCAL_MEDICAL_SERVICES"/>
 *     &lt;enumeration value="LOCAL_BABYSITTING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ListingFeatureName")
@XmlEnum
public enum ListingFeatureName {

    SPORTS_BASKETBALL_COURT ("RST","4"),
    SPORTS_CYCLING ("TRP","1"),
    SPORTS_DEEPSEA_FISHING ("RST","20"),
    SPORTS_EQUESTRIAN_EVENTS ("ACC","93"),
    SPORTS_FISHING ("RST","20"),
    SPORTS_FISHING_FLY ("RST","25"),
    SPORTS_FISHING_FRESHWATER ("RST","20"),
    SPORTS_GOLF ("RCC","4"),
    SPORTS_GOLF_OPTIONAL  ("RCC","4"),
    SPORTS_HIKING ("RST","60"),
    SPORTS_HUNTING ("RST","105"),
    SPORTS_HUNTING_BIG_GAME ("RST","105"),
    SPORTS_HUNTING_SMALL_GAME ("RST","105"),
    SPORTS_ICE_SKATING ("RST","137"),
    SPORTS_JET_SKIING ("RST","63"),
    SPORTS_MOUNTAIN_BIKING ("RST","68"),
    SPORTS_MOUNTAIN_CLIMBING ("RST","108"),
    SPORTS_MOUNTAINEERING ("ENV","2"),
    SPORTS_PARAGLIDING ("RST","73"),
    SPORTS_PIER_FISHING ("ACC","37"),
    SPORTS_RAFTING ("RST","79"),
    SPORTS_ROLLER_BLADING ("RST","84"),
    SPORTS_SAILING ("RST","80"),
    SPORTS_SCUBA_OR_SNORKELING ("RST","82"),
    SPORTS_SKI_LIFT_PRIVILEDGES ("EQP","36"),
    SPORTS_SKI_LIFT_PRIVILEDGES_OPTIONAL ("EQP","36"),
    SPORTS_SKI_LIFT_PRIVILEGES ("ACC","45"),
    SPORTS_SKI_LIFT_PRIVILEGES_OPTIONAL ("ACC","45"),
    SPORTS_SKIING ("ACC","45"),
    SPORTS_SNORKELING ("RST","86"),
    SPORTS_FISHING_BAY ("RST","20"),
    SPORTS_SPELUNKING ("RST","157"),
    SPORTS_FISHING_SURF  ("RST","20"),
    SPORTS_SURFING ("RST","116"),
    SPORTS_SWIMMING ("RST","106"),
    SPORTS_TENNIS ("CSC","25"),
    SPORTS_SKIING_WATER ("RST","99"),
    SPORTS_TUBING_WATER ("RST","96"),
    SPORTS_WHITEWATER_RAFTING ("RST","79"),
    SPORTS_WIND_SURFING ("RST","79"),
    SPORTS_CROSS_COUNTRY_SKIING ("RSN","6"),
    SPORTS_HOT_AIR_BALLOONING ("RST","153"),
    SPORTS_KAYAKING ("RST","65"),
    SPORTS_PARASAILING ("RST","73"),
    SPORTS_RACQUETBALL ("RST","77"),
    SPORTS_ROCK_CLIMBING ("RST","112"),
    SPORTS_SNOWBOARDING ("RST","87"),
    SPORTS_SNOWMOBILING ("RST","88"),
    CAR_NECESSARY ("TRP","5"),
    CAR_NOT_NECESSARY ("TRP","5"),
    CAR_RECOMMENDED ("TRP","5"),
    LOCATION_TYPE_BEACH ("RST","5"),
    LOCATION_TYPE_BEACH_VIEW ("RVT","15"),
    LOCATION_TYPE_BEACH_FRONT ("RST","155"),
    LOCATION_TYPE_LAKE ("RST","66"),
    LOCATION_TYPE_LAKE_VIEW ("RVT","8"),
    LOCATION_TYPE_LAKE_FRONT ("RST","66"),
    LOCATION_TYPE_NEAR_OCEAN ("RST","70"),
    LOCATION_TYPE_OCEAN_VIEW ("RST","70"),
    LOCATION_TYPE_OCEAN_FRONT ("RST","70"),
    LOCATION_TYPE_RIVER ("RVT","13"),
    LOCATION_TYPE_WATERFRONT ("RMA","120"),
    LOCATION_TYPE_WATER_VIEW ("BEV","20"),
    LOCATION_TYPE_MOUNTAIN ("LOC","8"),
    LOCATION_TYPE_MOUNTAIN_VIEW ("RVT","10"),
    LOCATION_TYPE_MONUMENT_VIEW ("REF","23"),
    LOCATION_TYPE_SKI_IN_OUT ("ACC","45"),
    LOCATION_TYPE_SKI_IN ("ACC","45"),
    LOCATION_TYPE_SKI_OUT ("ACC","45"),
    LOCATION_TYPE_GOLF_COURSE_FRONT ("TVS","5"),
    LOCATION_TYPE_GOLF_COURSE_VIEW ("RVT","5"),
    LOCATION_TYPE_DOWNTOWN ("REF","33"),
    LOCATION_TYPE_RESORT ("LOC","10"),
    LOCATION_TYPE_RURAL ("LOC","11"),
    LOCATION_TYPE_TOWN ("REF","33"),
    LOCATION_TYPE_VILLAGE ("LOC","23"),
    ATTRACTIONS_ARBORETUM ("ACC","0"),
    ATTRACTIONS_AUTUMN_FOLIAGE ("ACC","0"),
    ATTRACTIONS_BAY ("ACC","74"),
    ATTRACTIONS_BOTANICAL_GARDEN ("FAC","4"),
    ATTRACTIONS_CAVE ("RST","157"),
    ATTRACTIONS_CHURCHES ("ACC","14"),
    ATTRACTIONS_CINEMAS ("ACC","50"),
    ATTRACTIONS_COIN_LAUNDRY ("HAC","58"),
    ATTRACTIONS_DUTY_FREE ("ACC","0"),
    ATTRACTIONS_FESTIVALS ("RSN","7"),
    ATTRACTIONS_FORESTS ("RVT","18"),
    ATTRACTIONS_HEALTH_BEAUTY_SPA ("RSN","12"),
    ATTRACTIONS_LIBRARY ("ACC","27"),
    ATTRACTIONS_LIVE_THEATER ("ACC","71"),
    ATTRACTIONS_MARINA ("ACC","28"),
    ATTRACTIONS_MUSEUMS ("MRC","26"),
    ATTRACTIONS_NUDE_BEACH ("ACC","5"),
    ATTRACTIONS_PLAYGROUND ("RST","74"),
    ATTRACTIONS_POND ("BEV","20"),
    ATTRACTIONS_RAIN_FORESTS ("RVT","18"),
    ATTRACTIONS_REC_CENTER ("ACC","39"),
    ATTRACTIONS_REEF ("ACC","0"),
    ATTRACTIONS_RESTAURANTS ("ACC","41"),
    ATTRACTIONS_RUINS ("ACC","0"),
    ATTRACTIONS_SYNAGOGUES ("ACC","0"),
    ATTRACTIONS_THEME_PARKS ("RSN","2"),
    ATTRACTIONS_VOLCANO ("ACC","0"),
    ATTRACTIONS_WATER_PARKS ("ACC","54"),
    ATTRACTIONS_WATERFALLS ("ACC","55"),
    ATTRACTIONS_WINERY_TOURS ("ACC","56"),
    ATTRACTIONS_ZOO ("ACC","57"),
    LEISURE_ANTIQUING ("RST","152"),
    LEISURE_BEACHCOMBING ("ACC","5"),
    LEISURE_BIRD_WATCHING ("RST","127"),
    LEISURE_ECO_TOURISM ("RTC","3"),
    LEISURE_GAMBLING ("RST","133"),
    LEISURE_HORSEBACK_RIDING ("RST","61"),
    LEISURE_HORSESHOES ("RST","61"),
    LEISURE_LUAUS ("RTC","3"),
    LEISURE_OUTLET_SHOPPING ("RST","151"),
    LEISURE_PADDLE_BOATING ("RST","7"),
    LEISURE_PHOTOGRAPHY ("MRC","32"),
    LEISURE_SCENIC_DRIVES ("RVT","3"),
    LEISURE_SIGHT_SEEING ("RST","142"),
    LEISURE_SLEDDING ("RTC","3"),
    LEISURE_WALKING ("TRP","24"),
    LEISURE_WHALE_WATCHING ("RST","126"),
    LEISURE_BOATING ("RST","7"),
    LEISURE_MINIATURE_GOLF ("RST","67"),
    LEISURE_SHELLING ("RTC","3"),
    LEISURE_SHOPPING ("RST","151"),
    LEISURE_SHUFFLEBOARD ("RST","156"),
    LEISURE_WILDLIFE_VIEWING ("RST","126"),
    LEISURE_DISCO ("RES","18"),
    LEISURE_BOWLING ("ACC","91"),
    LEISURE_WATER_SPORTS ("ACC","54"),
    LEISURE_THALASSOTHERAPY ("RTC","3"),
    LEISURE_THERMALISME ("RTC","3"),
    LEISURE_NATURISME ("RST","109"),
    LOCAL_ATM_BANK ("HAC","7"),
    LOCAL_FITNESS_CENTER ("RST","21"),
    LOCAL_GROCERIES ("HAC","149"),
    LOCAL_HOSPITAL ("ACC","24"),
    LOCAL_LAUNDROMAT ("RCC","5"),
    LOCAL_MASSAGE_THERAPIST ("REC","22"),
    LOCAL_MEDICAL_SERVICES ("ACC","80"),
    LOCAL_BABYSITTING ("HAC","8");
    String code,id;
    ListingFeatureName(String code,String id){
    	this.code=code; this.id=id;
    }
    public String getCode(){
    	return code+id;
    }
    public static ListingFeatureName fromCode(String v){
    	for(ListingFeatureName obj:values()){
    		if(obj.getCode().equalsIgnoreCase(v)) return obj;
    	}
    	return null;
    }
    public String value() {
        return name();
    }

    public static ListingFeatureName fromValue(String v) {
        return valueOf(v);
    }

}
