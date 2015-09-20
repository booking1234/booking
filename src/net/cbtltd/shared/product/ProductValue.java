/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.product;

public interface ProductValue {

	// Values
	String ADULT_COUNT = "X00";//guest count
	String CHILD_COUNT = "X01";//child count
	String INFANT_COUNT = "X02";//infant count
	String CHECK_IN_TIME = "X03";//check in time
	String CHECK_OUT_TIME = "X04";//check out time
	String MAX_CHILD_AGE = "X05";//max child age
	String MIN_GUEST_AGE = "X06";//min guest age
	String MIN_RECOMMENDED_GUEST_AGE = "X07";//min Recommended Guest Age
	String MAX_LENGTH_OF_STAY = "X08";//maximum length of stay
	String MIN_LENGTH_OF_STAY = "X09";//minimum length of stay
	String STAY_FREE_CHILD_PER_ADULT = "X10";//usual Stay Free Child Per Adult
	String STAY_FREE_CHILD_CUTOFF_AGE = "X11";//usual Stay Free Cutoff Age
	String KIDS_STAY_FREE_FLAG = "X12";//kids stay free
	String INTERNET_GUARANTEE_REQUIRED_FLAG = "X13";//internet guarantee required
	String WHEN_BUILT = "X14";//when property was built
	String CLASSIFICATION_CODE = "X15";//room class
	String SIZE_MEASUREMENT = "X16";//room size
	String CARD_CODE = "X17";//credit card codes
	String SELECTED_LOCATIONS = "X18";//locations where party operates
	String SELECTED_CONDITIONS = "X19";//attributes for party operations
	String PARTY_DISCOUNT = "X20";//commission or discount for party
	String MANAGER_COMMISSION_PERCENT = "X21"; //manager discount
	String ROOM_TYPE = "X22"; //OTA room type
	String TYP_CONTACT_PERSON = "1";//contact person type
	String TYP_CUSTOMER = "2";//customer person type
}
