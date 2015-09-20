/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

/** The Class Download lists the entities that have been downloaded and by whom. */
public enum Downloaded {
	ACCOUNT_DOWNLOAD, // account transactions that have been downloaded
	PRICE_DOWNLOAD,  // prices that have been downloaded, remove when product is updated: HeadID = productid, LineID = organizationid
	PRODUCT_DOWNLOAD, // products that have been downloaded, remove when product is updated: HeadID = productid, LineID = organizationid
	PRODUCT_DOWNLOAD_DATE, // headid = download from lineid = last download date in yyyy-MM-ddThh:mm:ss format eg 2010-07-29T00:00:00
	PRODUCT_IMPORT_DATE, // headid = imported from lineid = last import date in yyyy-MM-ddThh:mm:ss format eg 2010-07-29T00:00:00
//	PRODUCT_ATTRIBUTE_DOWNLOAD, // products that have been downloaded, remove when product is updated: HeadID = productid, LineID = organizationid
	PRODUCT_DETAIL_DOWNLOAD, // product details that have been downloaded: HeadID = productid, LineID = partyid
	PRODUCT_DESCRIPTION_VERSION, // date when product details was last updated/downloaded: HeadID = productid, LineID = version
	PRODUCT_IMAGE_DOWNLOAD, // images for product that have been downloaded: HeadID = productid, LineID = partyid
	PRODUCT_IMAGE_VERSION, // date when images for product was last updated/downloaded: HeadID = productid, LineID = version
	PRODUCT_PRICE_VERSION, // date when prices for product was last updated/downloaded: HeadID = productid, LineID = version
	PRODUCT_SCHEDULE_VERSION, // date when schedule for product was last updated/downloaded: HeadID = productid, LineID = version
	RESERVATION_DOWNLOAD, // reservations that have been downloaded: HeadID = reservationid, LineID = partyid
	RESERVATION_DOWNLOAD_DATE, // headid = download from lineid = last download date in yyyy-MM-ddThh:mm:ss format eg 2010-07-29T00:00:00
//	RESERVATION_KIGO, //map from Razor reservationid to Kigo RES_ID
	RESERVATION_UPLOAD, // reservations that have been uploaded: HeadID = reservationid, LineID = pos.foreignid
	SCHEDULE_DOWNLOAD_DATE; // most recent schedule download: HeadID=altpartyid, LineID=date
}