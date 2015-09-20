package net.cbtltd.rest.bookingcom.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.cbtltd.shared.Image;
import net.cbtltd.shared.PropertyManagerCancellationRule;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * Utility to export product details in csv
 * @author nibodha
 *
 */
public class ExcelExportUtils {
	private static final Logger LOG = Logger.getLogger(ExcelExportUtils.class
			.getName());
	public static String XLSX_LOCAL_FILE_PATH;
	public static String TEMPLATE_XLSX_FILE;
	public static String DEFAULT_FILE_NAME="Master File.xlsx";
	private String fileName;
	private Workbook workbook;
	private FileInputStream file;
	private int imageBaseRow;
	private int policeBaseRow;
	static{
		XLSX_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "xlsx"
				+ File.separator;
	}
	
	public void createXlsxFile(String file) throws IOException{
	    TEMPLATE_XLSX_FILE=ExcelExportUtils.class.getResource("/resources/template/bookingcom_export.xlsx").getPath();
	    if(file==null) fileName=XLSX_LOCAL_FILE_PATH+DEFAULT_FILE_NAME;
	    else fileName=XLSX_LOCAL_FILE_PATH+file;
		copyFileUsingStream(new File(TEMPLATE_XLSX_FILE),new File(fileName));
		this.file=new FileInputStream(fileName);
	    workbook = new XSSFWorkbook( this.file );
	    imageBaseRow=4; policeBaseRow=4;
	}

	public void finishXlsxFile() throws IOException {
		this.file.close();

		FileOutputStream outFile = new FileOutputStream(new File(fileName),
				false);
		workbook.write(outFile);
		outFile.close();
	}
	
	/**
	 * To write all property details into xlsx file, cnt represents the number of records to write into file
	 * @param listPropertyUploadData
	 * @param cnt
	 * @throws IOException
	 */
	public void exportProductDataToXLSX(int recordNum,
			PropertyUploadTemplate PropertyUploadData)
			throws IOException {
		LOG.info("Starting exportProductDataToXLSX");
	    Sheet propertyInfoSheet=workbook.getSheet("Property Information");
	    Sheet contactSheet=workbook.getSheet("Contact Details");
	    Sheet invoiceSheet=workbook.getSheet("Invoice Details");
	    
	    int baseRow1=2;
	    	loadGeneralInfo(propertyInfoSheet.createRow(baseRow1+recordNum),PropertyUploadData);
	    	loadContact(contactSheet.createRow(baseRow1+recordNum),PropertyUploadData);
	    	loadInvoice(invoiceSheet.createRow(baseRow1+recordNum),PropertyUploadData);
	    	//PropertyUploadData.getNumberOfChildrenAllowedStaying();
	    LOG.info("Exiting exportProductDataToCSV");
		
	}
	public void exportProductImage(String id,List<Image> images) {
		Sheet photosSheet=workbook.getSheet("Photos");
		for(Image image:images){
			Row row=photosSheet.createRow(imageBaseRow++);
			row.createCell(0).setCellValue(id );
			row.createCell(1).setCellValue( image.getName());
			row.createCell(2).setCellValue(image.getUrl());
		}
	}
	public void exportProductImage(List<String> images,String id) {
		Sheet photosSheet=workbook.getSheet("Photos");
		//CreationHelper createHelper = workbook.getCreationHelper();
	   //  Hyperlink  url_link=createHelper.createHyperlink(Hyperlink.LINK_URL);
		for(String image:images){
			Row row=photosSheet.createRow(imageBaseRow++);
			row.createCell(0).setCellValue(id );
			row.createCell(1).setCellValue( "");
			row.createCell(2).setCellValue(image);
			//row.getCell(2).setHyperlink(url_link);
		}
	}
	private static void loadInvoice(Row row,
			PropertyUploadTemplate propertyData) {
		int index=0; 
		row.createCell(index++).setCellValue( propertyData.getCompanyNameForInvoicing() );
		row.createCell(index++).setCellValue( propertyData.getAttentionOff() );
		row.createCell(index++).setCellValue( propertyData.getLegalAdress() );
		row.createCell(index++).setCellValue( propertyData.getLegalZipCode() );
		row.createCell(index++).setCellValue( propertyData.getLegalCity() );
		row.createCell(index++).setCellValue( propertyData.getCountry() );
		row.createCell(index++).setCellValue( propertyData.getVatOrTax() );
	}

	private static void loadContact(Row row,
			PropertyUploadTemplate propertyData) {
		int index=0; 
		row.createCell(index++).setCellValue( propertyData.getPropertyName() );
		row.createCell(index++).setCellValue( propertyData.getGeneralContactName() );
		row.createCell(index++).setCellValue( propertyData.getGeneralContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getGeneralContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getGeneralContactFax() );
		row.createCell(index++).setCellValue( propertyData.getGeneralContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getReservationContactName() );
		row.createCell(index++).setCellValue( propertyData.getReservationContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getReservationContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getReservationContactFax() );
		row.createCell(index++).setCellValue( propertyData.getReversationContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getCentralReservationsContactName() );
		row.createCell(index++).setCellValue( propertyData.getCentralReservationsContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getCentralReservationsContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getCentralReservationsContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getInvoicesContactName() );
		row.createCell(index++).setCellValue( propertyData.getInvoicesContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getInvoicesContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getInvoicesContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getSpecialRequestContactName() );
		row.createCell(index++).setCellValue( propertyData.getSpecialRequestContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getSpecialRequestContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getSpecialRequestContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getAvailabilityContactName() );
		row.createCell(index++).setCellValue( propertyData.getAvailabilityContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getAvailabilityContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getAvailabilityContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getContentContactName() );
		row.createCell(index++).setCellValue( propertyData.getContentContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getContentContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getContentContactLanguage() );
		row.createCell(index++).setCellValue( propertyData.getPriceContactName() );
		row.createCell(index++).setCellValue( propertyData.getPriceContactEmail() );
		row.createCell(index++).setCellValue( propertyData.getPriceContactPhone() );
		row.createCell(index++).setCellValue( propertyData.getPriceContactLanguage() );
	}

	public static void loadGeneralInfo(Row row,PropertyUploadTemplate propertyData){
		int index=0; 
		row.createCell(index++).setCellValue( propertyData.getPropertyReferenceID() );
		row.createCell(index++).setCellValue( propertyData.getPropertyName() );
		row.createCell(index++).setCellValue( propertyData.getAddress() );
		row.createCell(index++).setCellValue( propertyData.getCity() );
		row.createCell(index++).setCellValue( propertyData.getLatitude() );
		row.createCell(index++).setCellValue( propertyData.getLongitude() );
		row.createCell(index++).setCellValue( propertyData.getZipcode() );
		row.createCell(index++).setCellValue( propertyData.getCountryCode() );
		row.createCell(index++).setCellValue( propertyData.getAccommodationType() );
		row.createCell(index++).setCellValue( propertyData.getCheckInFrom() );
		row.createCell(index++).setCellValue( propertyData.getCheckInUntil() );
		row.createCell(index++).setCellValue( propertyData.getCheckOutFrom() );
		row.createCell(index++).setCellValue( propertyData.getCheckOutUntil() );
		row.createCell(index++).setCellValue( propertyData.getCurrencyCode() );
		row.createCell(index++).setCellValue( propertyData.getAcceptedCreditCards() );
		row.createCell(index++).setCellValue( propertyData.getVatNumber() );
		row.createCell(index++).setCellValue( propertyData.getPropertyURL() );
		row.createCell(index++).setCellValue( propertyData.getQuickDescription() );
		row.createCell(index++).setCellValue( propertyData.getHeating() );
		row.createCell(index++).setCellValue( propertyData.getAirconditioning() );
		row.createCell(index++).setCellValue( propertyData.getRoomsFacilitiesForDisabled() );
		row.createCell(index++).setCellValue( propertyData.getSafeDepositBox() );
		row.createCell(index++).setCellValue( propertyData.getGarden() );
		row.createCell(index++).setCellValue( propertyData.getTerrace() );
		row.createCell(index++).setCellValue( propertyData.getBbqfacilities() );
		row.createCell(index++).setCellValue( propertyData.getOutdoorSwimmingpool() );
		row.createCell(index++).setCellValue( propertyData.getIndoorSwimmingpool());
		row.createCell(index++).setCellValue( propertyData.getBilliard() );
		row.createCell(index++).setCellValue( propertyData.getChildrenPlayground() );
		row.createCell(index++).setCellValue( propertyData.getDarts() );
		row.createCell(index++).setCellValue( propertyData.getTableTennis() );
		row.createCell(index++).setCellValue( propertyData.getGameRoom() );
		row.createCell(index++).setCellValue( propertyData.getLibrary() );
		row.createCell(index++).setCellValue( propertyData.getFitnessRoom() );
		row.createCell(index++).setCellValue( propertyData.getHottub() );
		row.createCell(index++).setCellValue( propertyData.getSauna() );
		row.createCell(index++).setCellValue( propertyData.getSolarium() );
		row.createCell(index++).setCellValue( propertyData.getTurkishSteambath() );
		row.createCell(index++).setCellValue( propertyData.getBikesAvailable() );
		row.createCell(index++).setCellValue( propertyData.getPrivateBeachArea() );
		row.createCell(index++).setCellValue( propertyData.getPrivateBeachArea() );//same for beach front
		row.createCell(index++).setCellValue( propertyData.getSkiToDoorAccess() );
		row.createCell(index++).setCellValue( propertyData.getSkiStorage() );
		row.createCell(index++).setCellValue( propertyData.getRoomName() );
		row.createCell(index++).setCellValue( propertyData.getRoomType() );
		row.createCell(index++).setCellValue( propertyData.getMaxPersonOfStayInTheVilla() );
		row.createCell(index++).setCellValue( propertyData.getMinimumSizeofRoom() );
		row.createCell(index++).setCellValue( 0 );//Entire unit wheelchair accesible
		row.createCell(index++).setCellValue( propertyData.getBathroom() );
		row.createCell(index++).setCellValue( propertyData.getShower() );
		row.createCell(index++).setCellValue( propertyData.getBidet() );
		row.createCell(index++).setCellValue( propertyData.getSharedBathroom() );
		row.createCell(index++).setCellValue( propertyData.getHairdryer() );
		row.createCell(index++).setCellValue( propertyData.getSpabath() );
		row.createCell(index++).setCellValue( propertyData.getToilet() );
		row.createCell(index++).setCellValue( propertyData.getAirconditioning() );
		row.createCell(index++).setCellValue( propertyData.getHeating() );
		row.createCell(index++).setCellValue( propertyData.getFan() );
		row.createCell(index++).setCellValue( propertyData.getFirePlace() );
		row.createCell(index++).setCellValue( propertyData.getDiningarea() );
		row.createCell(index++).setCellValue( propertyData.getSofa() );
		row.createCell(index++).setCellValue( propertyData.getTerrace() );
		row.createCell(index++).setCellValue( propertyData.getBalcony() );
		row.createCell(index++).setCellValue( propertyData.getPatio() );
		row.createCell(index++).setCellValue( propertyData.getBarbecue() );
		row.createCell(index++).setCellValue( propertyData.getPrivatePool() );
		row.createCell(index++).setCellValue( propertyData.getHottub() );
		row.createCell(index++).setCellValue( propertyData.getSauna() );
		row.createCell(index++).setCellValue( propertyData.getTelephone() );
		row.createCell(index++).setCellValue( propertyData.getTv() );
		row.createCell(index++).setCellValue( propertyData.getDvdPlayer() );
		row.createCell(index++).setCellValue( propertyData.getCableChannels() );
		row.createCell(index++).setCellValue( propertyData.getSatelliteChannels() );
		row.createCell(index++).setCellValue( propertyData.getCdPlayer() );
		row.createCell(index++).setCellValue( propertyData.getRadio() );
		row.createCell(index++).setCellValue( propertyData.getIpad() );
		row.createCell(index++).setCellValue( propertyData.getIpodDockingStation() );
		row.createCell(index++).setCellValue( propertyData.getSafeDepositBox() );
		row.createCell(index++).setCellValue( propertyData.getComputer() );
		row.createCell(index++).setCellValue( propertyData.getLaptop() );
		row.createCell(index++).setCellValue( propertyData.getLaptopSafeBox() );
		row.createCell(index++).setCellValue( propertyData.getGameConsole() );
		row.createCell(index++).setCellValue( propertyData.getVideoGames() );
		row.createCell(index++).setCellValue( propertyData.getWashingmachine() );
		row.createCell(index++).setCellValue( propertyData.getClothesdryer() );
		row.createCell(index++).setCellValue( propertyData.getClothingIron() );
		row.createCell(index++).setCellValue( propertyData.getDishwasher() );
		row.createCell(index++).setCellValue( propertyData.getElectrickettle() );
		row.createCell(index++).setCellValue( propertyData.getKitchen() );
		row.createCell(index++).setCellValue( propertyData.getKitchenette() );
		row.createCell(index++).setCellValue( propertyData.getKitchenware() );
		row.createCell(index++).setCellValue( propertyData.getMicrowave() );
		row.createCell(index++).setCellValue( propertyData.getMinibar() );
		row.createCell(index++).setCellValue( propertyData.getOven() );
		row.createCell(index++).setCellValue( propertyData.getRefrigerator() );
		row.createCell(index++).setCellValue( propertyData.getStove() );
		row.createCell(index++).setCellValue( propertyData.getCoffeeTeamaker() );
		row.createCell(index++).setCellValue( propertyData.getToaster() );
		row.createCell(index++).setCellValue( 0 ); //Cleaning Products
		row.createCell(index++).setCellValue( 0 ); //Bed Linen (included in rental price)
		row.createCell(index++).setCellValue( 0 ); //Towels (included in rental price)
		row.createCell(index++).setCellValue( 0 ); //Towels & Bed Linen at surcharge (extra cost)
		row.createCell(index++).setCellValue( propertyData.getGardenView() );
		row.createCell(index++).setCellValue( propertyData.getLakeView() );
		row.createCell(index++).setCellValue( propertyData.getMountainView() );
		row.createCell(index++).setCellValue( propertyData.getPoolview() );
		row.createCell(index++).setCellValue( propertyData.getSeaview() );
	}
	
	/**
	 * To write all property's image into CSV file, cnt represents the number of records to write into file
	 * @param listPropertyUploadData
	 * @param cnt
	 * @throws IOException
	 */
	public static void loadImage(
			List<ImageUploadTemplate> listImageUploadData,int cnt)
			throws IOException {
		LOG.info("Starting exportImageDataToCSV");
		ICsvBeanWriter beanWriter = null;
		FileWriter writer = new FileWriter(XLSX_LOCAL_FILE_PATH+"imageData.csv",true);
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
	
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally {
	        is.close();
	        os.close();
	    }
	}
	public void exportExtraCost(int cnt,String popertyId,PropertyUploadTemplate propertyData) {
		Sheet extraCost=workbook.getSheet("Extra costs & Tax");
		Row row=extraCost.createRow(cnt+3);
		int index=0; 
		row.createCell(index++).setCellValue(popertyId);
		row.createCell(index++).setCellValue(propertyData.getContractFee());
		
		// VAT or Tax
		row.createCell(index++).setCellValue(propertyData.getVatOrTax());
		row.createCell(index++).setCellValue(propertyData.getTaxIncludedOrExcluded());
		row.createCell(index++).setCellValue(propertyData.getVatOrTaxAmount());
		row.createCell(index++).setCellValue(propertyData.getVatType());
		
		// City Tax
		row.createCell(index++).setCellValue(propertyData.getCityTaxAmount());
		row.createCell(index++).setCellValue(propertyData.getCityTaxType());
		row.createCell(index++).setCellValue(propertyData.getCityTaxType());
		
		
		row.createCell(index++).setCellValue(propertyData.getSecurityDeposit());
		
		// cleaning
		row.createCell(index++).setCellValue(propertyData.getFinalCleaningFee());
		row.createCell(index++).setCellValue(propertyData.getFinalCleaningIncludedOrExcluded());
		row.createCell(index++).setCellValue(propertyData.getFinalCleaningPrice());
		row.createCell(index++).setCellValue(propertyData.getFinalCleaningSpecification());
		
		// bed linen
		row.createCell(index++).setCellValue(propertyData.getBedLinenAndTowels());
		row.createCell(index++).setCellValue(propertyData.getBedLinenAndTowelsPrice());
		
		// air conditioning
		row.createCell(index++).setCellValue(propertyData.getAirconditioningType());
		row.createCell(index++).setCellValue(propertyData.getAirconditioningPrice());
		
		// electirycity, gasoil, heating
		row.createCell(index++).setCellValue(propertyData.getElectircity());
		row.createCell(index++).setCellValue(propertyData.getGasOil());
		row.createCell(index++).setCellValue(propertyData.getHeatingType());
	}

	public void exportProductPolicies(PropertyUploadTemplate propertyData,
			List<PropertyManagerCancellationRule> rules, String id) {
		Sheet policies=workbook.getSheet("Policies");
		
		Row row=policies.createRow(policeBaseRow++);
		int index=0; 
		row.createCell(index++).setCellValue( id );
		row.createCell(index++).setCellValue("");									
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue(propertyData.getInternet());					
		row.createCell(index++).setCellValue(propertyData.getInternetAccess());		
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue(propertyData.getParking());
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue(propertyData.getParkingFee());		
		row.createCell(index++).setCellValue(propertyData.getParkingReservationNeeded());		
		row.createCell(index++).setCellValue("");	
		row.createCell(index++).setCellValue("");	
		StringBuilder bld=new StringBuilder();
		for (PropertyManagerCancellationRule rule : rules) {
			if(rule.getCancellationDate()!=0){
				bld.append("Traveler can cancel upto ").append(rule.getCancellationDate()).append(" days prior to arrival and receive a refund of ").append(rule.getCancellationRefund()).append("\n");	
			}else{
				bld.append("Traveler can cancel upto the day of arrival and receive a refund of ").append(rule.getCancellationRefund()).append("\n");
			}
			
		}
		row.createCell(index++).setCellValue(bld.toString());
		
		String paymentTerms = "Deposit of %s of Booking Value required, final payment required %s days before arrival";
		if(StringUtils.isNotEmpty(propertyData.getPaymentAmount()) && StringUtils.isNotEmpty(propertyData.getRemainderPaymentDate())) {
			paymentTerms = String.format(paymentTerms, propertyData.getPaymentAmount()+"%", propertyData.getRemainderPaymentDate());
		} else {
			paymentTerms = "NA";
		}
		row.createCell(index++).setCellValue(paymentTerms);
		
		// Important Details
		row.createCell(index++).setCellValue("NA");
	}

	
}