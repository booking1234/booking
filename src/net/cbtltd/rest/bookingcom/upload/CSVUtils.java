package net.cbtltd.rest.bookingcom.upload;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * Utility to export product details in csv
 * @author nibodha
 *
 */
public class CSVUtils {
	private static final Logger LOG = Logger.getLogger(CSVUtils.class
			.getName());
	public static String CSV_LOCAL_FILE_PATH;
	static{
		CSV_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "csv"
				+ File.separator;
	}
	/**
	 * To write all property details into CSV file, cnt represents the number of records to write into file
	 * @param listPropertyUploadData
	 * @param cnt
	 * @throws IOException
	 */
	public static void exportProductDataToCSV(
			List<PropertyUploadTemplate> listPropertyUploadData,int cnt)
			throws IOException {
		LOG.info("Starting exportProductDataToCSV");
		ICsvBeanWriter beanWriter = null;
		FileWriter writer = new FileWriter(CSV_LOCAL_FILE_PATH+"productData.csv",true);
		
		try {
			beanWriter = new CsvBeanWriter(writer,
					CsvPreference.STANDARD_PREFERENCE);
			
			final String[] header = new String[] { "propertyReferenceID",
					"propertyName",
					"address",
					"city",
					"latitude",
					"longitude",
					"zipcode",
					"countryCode",
					"accommodationType",
					"chain",
					"starrating",
					"totalNumberofBookableOptions",
					"checkInFrom",
					"checkInUntil",
					"checkOutFrom",
					"checkOutUntil",
					"currencyCode",
					"acceptedCreditCards",
					"vatNumber",
					"propertyURL",
					"quickDescription",
					"importantInformation",
					"hour24FrontDesk",
					"petsAllowed",
					"parking",
					"internetAccess",
					"nonSmokingArea",
					"allergyFreeRoom",
					"breakfastBuffet",
					"elevator",
					"garden",
					"gayFriendly",
					"heating",
					"luggageStorage",
					"nonSmokingRooms",
					"privateBeachArea",
					"restaurant",
					"roomsFacilitiesForDisabled",
					"safeDepositBox",
					"skiStorage",
					"sunTerrace",
					"terrace",
					"bbqfacilities",
					"fitnessRoom",
					"outdoorSwimmingpool",
					"hottub",
					"billiard",
					"bowling",
					"canoeing",
					"childrenPlayground",
					"cycling",
					"darts",
					"diving",
					"fishing",
					"gameRoom",
					"golfcourse",
					"hiking",
					"horseRiding",
					"hotSpringBath",
					"library",
					"miniGolf",
					"sauna",
					"skiSchool",
					"skiing",
					"snorkeling",
					"solarium",
					"spaWellnessCentre",
					"squash",
					"tableTennis",
					"tennisCourt",
					"turkishSteambath",
					"windSurfing",
					"bicyclerent",
					"bikesAvailable",
					"skiEquipmenThireonsite",
					"skiPassVendor",
					"skiToDoorAccess",
					"generalContactName",
					"generalContactEmail",
					"generalContactPhone",
					"generalContactFax",
					"generalContactLanguage",
					"availabilityContactName",
					"availabilityContactEmail",
					"availabilityContactPhone",
					"availabilityContactLanguage",
					"centralReservationsContactName",
					"centralReservationsContactEmail",
					"centralReservationsContactPhone",
					"centralReservationsContactLanguage",
					"invoicesContactName",
					"invoicesContactEmail",
					"invoicesContactPhone",
					"invoicesContactLanguage",
					"contractContactName",
					"contractContactEmail",
					"contractContactPhone",
					"contractContactLanguage",
					"parityContactName",
					"parityContactEmail",
					"parityContactPhone",
					"parityContactLanguage",
					"specialRequestContactName",
					"specialRequestContactEmail",
					"specialRequestContactPhone",
					"specialRequestContactLanguage",
					"reservationContactName",
					"reservationContactEmail",
					"reservationContactPhone",
					"reservationContactFax",
					"reversationContactLanguage",
					"contentContactName",
					"contentContactEmail",
					"contentContactPhone",
					"contentContactLanguage",
					"roomName",
					"roomType",
					"maxPersonOfStayInTheVilla",
					"minimumSizeofRoom",
					"smokingNonSmoking",
					"airconditioning",
					"washingmachine",
					"clothesdryer",
					"desk",
					"extralongbeds",
					"fan",
					"firePlace",
					"roomHeating",
					"roomHottub",
					"interConnectingRoomsAvailable",
					"clothingIron",
					"ironingFacilities",
					"mosquitonet",
					"privatePool",
					"roomSafeDepositBox",
					"seatingarea",
					"sofa",
					"soundProofing",
					"trouserpress",
					"shower",
					"bathroom",
					"sharedBathroom",
					"handShower",
					"bidet",
					"hairdryer",
					"roomSauna",
					"spabath",
					"toilet",
					"cdPlayer",
					"radio",
					"ipad",
					"ipodDockingStation",
					"cableChannels",
					"satelliteChannels",
					"tv",
					"dvdPlayer",
					"computer",
					"laptop",
					"laptopSafeBox",
					"gameConsole",
					"telephone",
					"videoGames",
					"barbecue",
					"diningarea",
					"dishwasher",
					"electrickettle",
					"kitchen",
					"kitchenette",
					"kitchenware",
					"microwave",
					"minibar",
					"oven",
					"refrigerator",
					"stove",
					"coffeeTeamaker",
					"toaster",
					"alarmclock",
					"balcony",
					"gardenView",
					"lakeView",
					"landmarkView",
					"mountainView",
					"patio",
					"poolview",
					"seaview",
					"companyNameForInvoicing",
					"attentionOff",
					"legalAdress",
					"legalZipCode",
					"legalCity",
					"country",
					"invoiceMedium",
					"vat_tax",
					"vat_type",
					"included_Excluded",
					"vatAmount",
					"cityTaxType",
					"cityTaxStatus",
					"cityTaxAmount",
					"serviceChargetype",
					"serviceChargeStatus",
					"serviceChargeAmount",
					"childrenAllowed",
					"numberOfChildrenAllowedStaying",
					"childBedcost",
					"groupPolicy",
					"roomPetsAllowed",
					"petsFee",
					"parkingFee",
					"roomParking",
					"parkingReservationNeeded",
					"roomInternetAccess",
					"internet",
					"internetAccessType",
					"internetAccessInRoom",
					"internetAccessInPublicArea",
					"internetAccessInBusinessCentre",
					"mealsPlan",
					"mealsPlanType"
 };
			// final CellProcessor[] processors = getProcessors();
			// write the header
			if(cnt==0){
				beanWriter.writeHeader(header);
			}
			

			// write the beans data
			for (PropertyUploadTemplate propertyData : listPropertyUploadData) {
				beanWriter.write(propertyData, header);
			}
		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}
		LOG.info("Exiting exportProductDataToCSV");
		
	}
	
	/**
	 * To write all property's image into CSV file, cnt represents the number of records to write into file
	 * @param listPropertyUploadData
	 * @param cnt
	 * @throws IOException
	 */
	public static void exportImageDataToCSV(
			List<ImageUploadTemplate> listImageUploadData,int cnt)
			throws IOException {
		LOG.info("Starting exportImageDataToCSV");
		ICsvBeanWriter beanWriter = null;
		FileWriter writer = new FileWriter(CSV_LOCAL_FILE_PATH+"imageData.csv",true);
		try {
			beanWriter = new CsvBeanWriter(writer,
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = new String[] { "bookingCode",
					"propertyCode", "sequenceNumber", "tag", "height",
					"url"};
			// final CellProcessor[] processors = getProcessors();
			// write the header
			if(cnt==0){
				beanWriter.writeHeader(header);
			}
			

			// write the beans data
			for (ImageUploadTemplate imageData : listImageUploadData) {
				beanWriter.write(imageData, header);
			}
		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}
		LOG.info("Exiting exportImageDataToCSV");
		
	}
}
