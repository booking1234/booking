package net.cbtltd.rest.bookingcom.upload;

/**
 * The entity class to store the image update info
 * @author nibodha
 *
 */
public class ImageUploadTemplate {
private String bookingCode;
private String propertyCode;
private String sequenceNumber;
private String tag;
private String height;
private String url;
/**
 * @return the bookingCode
 */
public String getBookingCode() {
	return bookingCode;
}
/**
 * @param bookingCode the bookingCode to set
 */
public void setBookingCode(String bookingCode) {
	this.bookingCode = bookingCode;
}
/**
 * @return the propertyCode
 */
public String getPropertyCode() {
	return propertyCode;
}
/**
 * @param propertyCode the propertyCode to set
 */
public void setPropertyCode(String propertyCode) {
	this.propertyCode = propertyCode;
}
/**
 * @return the sequenceNumber
 */
public String getSequenceNumber() {
	return sequenceNumber;
}
/**
 * @param sequenceNumber the sequenceNumber to set
 */
public void setSequenceNumber(String sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
}
/**
 * @return the tag
 */
public String getTag() {
	return tag;
}
/**
 * @param tag the tag to set
 */
public void setTag(String tag) {
	this.tag = tag;
}
/**
 * @return the height
 */
public String getHeight() {
	return height;
}
/**
 * @param height the height to set
 */
public void setHeight(String height) {
	this.height = height;
}
/**
 * @return the url
 */
public String getUrl() {
	return url;
}
/**
 * @param url the url to set
 */
public void setUrl(String url) {
	this.url = url;
}

}
