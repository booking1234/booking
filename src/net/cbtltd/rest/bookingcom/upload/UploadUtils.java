package net.cbtltd.rest.bookingcom.upload;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.TextService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.integration.AttributeService;
import net.cbtltd.server.integration.LocationService;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.RelationService;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.location.LocationRead;
import net.cbtltd.shared.partner.PartnerRead;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Utility for Uploading work of booking.com
 * @author nibodha
 *
 */
public class UploadUtils {
	private static final Logger LOG = Logger.getLogger(UploadUtils.class
			.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	/**
	 * To upload the property data for the list of supplier 
	 * @param listSuppliedID
	 */
	public void uploadPropertyDataAsExcel(List<String> listSupplierID,List<String> listPropertyID, List<String> propertiesToSkip){
		LOG.info("Starting uploadPropertyData");
		SqlSession sqlSession = RazorServer.openSession();
		List<Product> listProduct=new ArrayList<Product>();
		int cnt=0;
		listProduct=this.fetchProductDetails(sqlSession,cnt,listSupplierID,listPropertyID);
		 
		ExcelExportUtils utils=new ExcelExportUtils();
		if(listProduct!=null){
			System.out.println("starting excel export");
			try {
				utils.createXlsxFile(ExcelExportUtils.DEFAULT_FILE_NAME);
			} catch (IOException e1) {
				LOG.error("Unable to create excel file from template",e1);
				e1.printStackTrace();
			}
			System.out.println("record count "+listProduct.size());
			for(Product bpProduct:listProduct){
				if(propertiesToSkip.contains(bpProduct.getId())) {
					continue;
				}
				try{
				PropertyUploadTemplate propertyUploadData=new PropertyUploadTemplate();
				LOG.info("populateGeneralAccomodationData");
				propertyUploadData=this.populateGeneralAccomodationData(sqlSession, propertyUploadData, bpProduct);
				List<String> listAttributeNames=this.fetchRelationDetails(sqlSession, bpProduct.getId(), "Product Attribute");
				/*for(String attrName:listAttributeNames){
					LOG.info("Available Attribute names are "+listAttributeNames);
				}*/
				LOG.info("populateGeneralAccomodationFacilities ");
				propertyUploadData=this.populateGeneralAccomodationFacilities(sqlSession, propertyUploadData, listAttributeNames);
				
				LOG.info("populatePropertyManagerInfo ");
				propertyUploadData=this.populatePropertyManagerInfo(sqlSession, propertyUploadData, bpProduct);
				
				LOG.info("populatePropertyManagerSupportCC ");
				propertyUploadData=this.populatePropertyManagerSupportCC(sqlSession, propertyUploadData, bpProduct);
				
				LOG.info("populatePartyInfo ");
				propertyUploadData=this.populatePartyInfo(sqlSession, propertyUploadData, bpProduct);
				
				
				LOG.info("populate extra cost & tax ");
				propertyUploadData=this.populateExtraCostInfo(sqlSession, propertyUploadData, bpProduct);
				
				//List<net.cbtltd.shared.Image> images=ImageService.getProductRegularImageURLsAndDescription(sqlSession, bpProduct.getId());
				List<PropertyManagerCancellationRule> rules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(Integer.valueOf(bpProduct.getSupplierid()));
				
				PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(bpProduct.getSupplierid()));
				LOG.info("populatePaymentTerms ");
				propertyUploadData=this.populatePaymentTerms(sqlSession, propertyUploadData, propertyManagerInfo);
				
				
				System.out.println("processing "+cnt+ " : "+bpProduct.getId()+" : "+bpProduct.getAltpartyid()+" : "+ bpProduct.getAltid());
				
				utils.exportProductDataToXLSX(cnt, propertyUploadData);
				utils.exportProductImage(com.mybookingpal.server.ImageService.getProductRegularImageURLs(sqlSession, bpProduct.getId()),bpProduct.getId());
				utils.exportExtraCost(cnt,bpProduct.getId(),propertyUploadData);
				utils.exportProductPolicies(propertyUploadData,rules,bpProduct.getId());
				} catch (Exception e) {
					LOG.error("Property id "+bpProduct.getId()+" Failed ");
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
				cnt++;
				
			}
			System.out.println("finishing excel export");
			try {
				utils.finishXlsxFile();
			} catch (IOException e1) {
				LOG.error("Unable to create excel file from template",e1);
				e1.printStackTrace();
			}

		}
		LOG.info("Exiting uploadPropertyData");
	}
	
	private PropertyUploadTemplate populatePaymentTerms(SqlSession sqlSession,
			PropertyUploadTemplate propertyUploadData,
			PropertyManagerInfo propertyManagerInfo) {
		if(propertyManagerInfo != null) {
			propertyUploadData.setPaymentAmount(propertyManagerInfo.getPaymentAmount() != null ? propertyManagerInfo.getPaymentAmount().toString() : "");
			propertyUploadData.setRemainderPaymentDate(propertyManagerInfo.getRemainderPaymentDate() != null ? propertyManagerInfo.getRemainderPaymentDate().toString() : "");
		}
		return propertyUploadData;
	}
	
	private String checkId(String id,String defaultValue){
		return 
				(id==null||"0".equalsIgnoreCase(id)||"null".equalsIgnoreCase(id))?
				defaultValue
				:
				id;
	}
	private boolean isNullOrEmpty(Boolean val){
		return (val==null||!val)? false: true;
		
	}
	
	private PropertyUploadTemplate populateExtraCostInfo(SqlSession sqlSession,
			PropertyUploadTemplate propertyUploadData,Product bpProduct){
		Price action=new Price();
		action.setEntityid(bpProduct.getId());
		Price price = sqlSession.getMapper(PriceMapper.class).readContractFee(action);
		if(price!=null){
			propertyUploadData.setContractFee(price.getValue()+"");
		}else{
			propertyUploadData.setContractFee("0.0");
		}
		
		action.setEntitytype(NameId.Type.Mandatory.name());
		action.setDate(new Date());
		action.setOrderby(Price.ID);
		
		//Tax included in price
		action.setType(Tax.Type.SalesTaxIncluded.name());
		ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			for (Tax tax : taxes) {
				if(tax.getName().equalsIgnoreCase("Output VAT")) {
					propertyUploadData.setVatOrTaxAmount(tax.getAmount() != null ? Double.toString(tax.getAmount()) : "NA");
					propertyUploadData.setVatType(PropertyUploadTemplate.ChargeSpecifications.PC.name());
					propertyUploadData.setTaxIncludedOrExcluded("Included");
				} else if(tax.getName().equalsIgnoreCase("City Sales Tax")) {
					propertyUploadData.setCityTaxAmount(tax.getAmount() != null ? Double.toString(tax.getAmount()) : "NA");
					propertyUploadData.setCityTaxType(PropertyUploadTemplate.ChargeSpecifications.PS.name());
					propertyUploadData.setCityTaxStatus("Included");
				}
			}
			
		}
		//Tax excluded from price
		action.setType(Tax.Type.SalesTaxExcluded.name());
		taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			
			for (Tax tax : taxes) {
				if(tax.getName().equalsIgnoreCase("Output VAT")) {
					propertyUploadData.setVatOrTaxAmount(tax.getAmount() != null ? Double.toString(tax.getAmount()) : "NA");
					propertyUploadData.setVatType(PropertyUploadTemplate.ChargeSpecifications.PC.name());
					propertyUploadData.setTaxIncludedOrExcluded("Excluded");
				} else if(tax.getName().equalsIgnoreCase("City Sales Tax")) {
					propertyUploadData.setCityTaxAmount(tax.getAmount() != null ? Double.toString(tax.getAmount()) : "NA");
					propertyUploadData.setCityTaxType(PropertyUploadTemplate.ChargeSpecifications.PS.name());
					propertyUploadData.setCityTaxStatus("Excluded");
				}
			}
		}
		
		// set security deposit
		if(bpProduct.getSecuritydeposit() != null && bpProduct.getSecuritydeposit().doubleValue() != NumberUtils.DOUBLE_ZERO) {
			propertyUploadData.setSecurityDeposit(bpProduct.getSecuritydeposit().toString());
		}
		
		return propertyUploadData;
	}
	private PropertyUploadTemplate populatePartyInfo(SqlSession sqlSession,
			PropertyUploadTemplate propertyUploadData,Product bpProduct) {
		Party party = sqlSession.getMapper(PartyMapper.class).read(
				checkId(bpProduct.getAltpartyid(),bpProduct.getSupplierid()));
		if(party==null) return propertyUploadData;
		propertyUploadData.setPropertyName(bpProduct.getName()) ;
		propertyUploadData.setGeneralContactName(party.getExtraname()) ;
		propertyUploadData.setGeneralContactEmail(party.getEmailaddress()) ;
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setGeneralContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setGeneralContactPhone(party.getDayphone()) ;	
		}
		
		propertyUploadData.setGeneralContactFax(party.getFaxphone()) ;
		propertyUploadData.setGeneralContactLanguage(party.getLanguage()) ;
		propertyUploadData.setReservationContactName(party.getExtraname()) ;
		propertyUploadData.setReservationContactEmail(party.getEmailaddress()) ;
		
		
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setReservationContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setReservationContactPhone(party.getDayphone()) ;	
		}
		
		propertyUploadData.setReservationContactFax(party.getFaxphone()) ;
		propertyUploadData.setReversationContactLanguage(party.getLanguage()) ;
		propertyUploadData.setCentralReservationsContactName(party.getExtraname()) ;
		propertyUploadData.setCentralReservationsContactEmail(party.getEmailaddress()) ;
		
		
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setCentralReservationsContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setCentralReservationsContactPhone(party.getDayphone()) ;	
		}
		
		
		propertyUploadData.setCentralReservationsContactLanguage(party.getLanguage());
		propertyUploadData.setInvoicesContactName(party.getExtraname()) ;
		propertyUploadData.setInvoicesContactEmail(party.getEmailaddress()) ;
		
		
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setInvoicesContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setInvoicesContactPhone(party.getDayphone()) ;	
		}
		
		propertyUploadData.setInvoicesContactLanguage(party.getLanguage()) ;
		propertyUploadData.setAvailabilityContactName(party.getExtraname()) ;
		propertyUploadData.setAvailabilityContactEmail(party.getEmailaddress()) ;
		
		
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setAvailabilityContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setAvailabilityContactPhone(party.getDayphone()) ;	
		}
		
		propertyUploadData.setAvailabilityContactLanguage(party.getLanguage()) ;
		propertyUploadData.setSpecialRequestContactName(bpProduct.getName()) ;
		propertyUploadData.setSpecialRequestContactEmail(party.getEmailaddress()) ;
		propertyUploadData.setSpecialRequestContactPhone(party.getFormatphone()) ;
		propertyUploadData.setSpecialRequestContactLanguage(party.getLanguage()) ;
		propertyUploadData.setContentContactName(party.getExtraname()) ;
		propertyUploadData.setContentContactEmail(party.getEmailaddress()) ;
		
		if(StringUtils.isNotEmpty(party.getMobilephone())){
			propertyUploadData.setContentContactPhone(party.getMobilephone());
		}else{
			propertyUploadData.setContentContactPhone(party.getDayphone()) ;	
		}
		
		propertyUploadData.setContentContactLanguage(party.getLanguage()) ;
		propertyUploadData.setVatNumber(party.getTaxnumber());
		//propertyUploadData.setPropertyURL(party.getUrlText()!=null?party.getUrlText().getPlainText():"");
		
		propertyUploadData.setCompanyNameForInvoicing(bpProduct.getName());
		if(!propertyUploadData.isUseBPInvoiceDetails()) {
			propertyUploadData.setAttentionOff(party.getName() );
			propertyUploadData.setLegalAdress(party.getLocalAddress());
			propertyUploadData.setLegalZipCode(party.getPostaladdress());
			propertyUploadData.setLegalCity(party.getCity());
			propertyUploadData.setCountry(party.getCountry());
		}
		String taxType = StringUtils.isEmpty(bpProduct.getTax()) ? "tax" 
				                                : (bpProduct.getTax().toLowerCase().contains("VAT".toLowerCase()) ? "vat" : "tax");
		propertyUploadData.setVatOrTax(taxType);
		return propertyUploadData;
	}

	private PropertyUploadTemplate populatePropertyManagerSupportCC(
			SqlSession sqlSession, PropertyUploadTemplate propertyUploadData,Product bpProduct) {
		PropertyManagerSupportCC support=sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid( 
				Integer.parseInt(checkId(bpProduct.getAltpartyid(),bpProduct.getSupplierid())) );
		if(support==null) return propertyUploadData;
		StringBuffer buf=new StringBuffer();
		if(isNullOrEmpty(support.getSupportVISA())) buf.append("VISA |");
		if(isNullOrEmpty(support.getSupportMC())) buf.append("MC |");
		if(isNullOrEmpty(support.getSupportDINERSCLUB())) buf.append("DINERSCLUB |");
		if(isNullOrEmpty(support.getSupportAE())) buf.append("AE |");
		if(isNullOrEmpty(support.getSupportDISCOVER())) buf.append("DISCOVER |");
		if(isNullOrEmpty(support.getSupportJCB())) buf.append("JCB |");
		if(isNullOrEmpty(support.getNone())) buf.append("NONE |");
		if(buf.length()>2)
		propertyUploadData.setAcceptedCreditCards(buf.substring(0, buf.length()-2));
		
		return propertyUploadData;
	}

	private PropertyUploadTemplate populatePropertyManagerInfo(
			SqlSession sqlSession, PropertyUploadTemplate propertyUploadData,Product bpProduct) {
		PropertyManagerInfo info= sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(
				Integer.parseInt(checkId(bpProduct.getAltpartyid(),bpProduct.getSupplierid())));
		try{
		propertyUploadData.setCheckInFrom(sdf.format(info.getCheckInTime()));
		propertyUploadData.setCheckInUntil(sdf.format(info.getCheckInTime()));
		propertyUploadData.setCheckOutFrom(sdf.format(info.getCheckOutTime()));
		propertyUploadData.setCheckOutUntil(sdf.format(info.getCheckOutTime()));
		}catch(Exception e){}
		return propertyUploadData;
	}

	/**
	 * To upload the property data for the list of supplier 
	 * @param listSuppliedID
	 */
	public void uploadPropertyData(List<String> listSupplierID,List<String> listPropertyID){
		LOG.info("Starting uploadPropertyData");
		SqlSession sqlSession = RazorServer.openSession();
		List<Product> listProduct=new ArrayList<Product>();
		int cnt=0;
		while(listProduct!=null){
			
		
		 listProduct=this.fetchProductDetails(sqlSession,cnt,listSupplierID,listPropertyID);
		List<PropertyUploadTemplate> listPropertyUploadTemplate=new ArrayList<PropertyUploadTemplate>();
		if(listProduct!=null){
		for(Product bpProduct:listProduct){
			try{
			PropertyUploadTemplate propertyUploadData=new PropertyUploadTemplate();
			LOG.info("populateGeneralAccomodationData");
			propertyUploadData=this.populateGeneralAccomodationData(sqlSession, propertyUploadData, bpProduct);
			List<String> listAttributeNames=this.fetchRelationDetails(sqlSession, bpProduct.getId(), "Product Attribute");
			/*for(String attrName:listAttributeNames){
				LOG.info("Available Attribute names are "+listAttributeNames);
			}*/
			LOG.info("populateGeneralAccomodationFacilities ");
			propertyUploadData=this.populateGeneralAccomodationFacilities(sqlSession, propertyUploadData, listAttributeNames);
			listPropertyUploadTemplate.add(propertyUploadData);
			} catch (Exception e) {
				LOG.error("Property id "+bpProduct.getId()+" Failed ");
				LOG.error(e.getMessage());
			}
		}
		}
		//write the first batch to CSV file
		try {
			CSVUtils.exportProductDataToCSV(listPropertyUploadTemplate,cnt);
		} catch (IOException e) {
			LOG.error("Writing to CSV for batch # "+cnt+"Failed for the following reason");
			LOG.error(e.getMessage());
		}
		cnt++;
		//break;
	
		}
		
		LOG.info("Exiting uploadPropertyData");
	}
	
	/**
	 * @param sqlSession
	 * @param propertyUploadData
	 * @param bpProduct
	 * @return propertyUploadData as PropertyUploadTemplate
	 */
	private PropertyUploadTemplate populateGeneralRoomDetails(SqlSession sqlSession,PropertyUploadTemplate propertyUploadData,Product bpProduct){
		return propertyUploadData;
	}
	
	/**
	 * @param sqlSession
	 * @param propertyUploadData
	 * @param bpProduct
	 * @return propertyUploadData as PropertyUploadTemplate
	 */
	private PropertyUploadTemplate populateContactInformation(SqlSession sqlSession,PropertyUploadTemplate propertyUploadData,Product bpProduct){
		//TODO:Check with Chirayu
		return propertyUploadData;
	}
	
	
	
	/**
	 * @param sqlSession
	 * @param propertyUploadData
	 * @param bpProduct
	 * @return propertyUploadData  as PropertyUploadTemplate
	 */
	private PropertyUploadTemplate populateInvoicingDetails(SqlSession sqlSession,PropertyUploadTemplate propertyUploadData,Product bpProduct){
		//TODO:Check with Chirayu
		return propertyUploadData;
	}

	/**
	 * 
	 * update propertyUploadData with some Accommodation Facilities details using list of attributes
	 * @param sqlSession
	 * @param propertyUploadData
	 * @param listAttributeNames
	 * @return propertyUploadData
	 */
	private PropertyUploadTemplate populateGeneralAccomodationFacilities(SqlSession sqlSession,PropertyUploadTemplate propertyUploadData,List<String> listAttributeNames){
		//Front desk 24h
		if(listAttributeNames.contains("Front desk 24h")){
			propertyUploadData.setHour24FrontDesk(1);
			
		}
		//Domestic Pets
		if(listAttributeNames.contains("Domestic Pets")){
			propertyUploadData.setPetsAllowed(1);
		}
		if(listAttributeNames.contains("Parking")){
			propertyUploadData.setParking(1);
		}
		if(listAttributeNames.contains("Internet access")){
			propertyUploadData.setInternetAccess(1);
			propertyUploadData.setInternet("Y");
			propertyUploadData.setInternetAccessType("Wired");
			propertyUploadData.setInternetAccessInBusinessCentre("Y");
			propertyUploadData.setInternetAccessInPublicArea("Y");
			propertyUploadData.setInternetAccessInRoom("Y");
		}
		if(listAttributeNames.contains("Wireless Internet") || listAttributeNames.contains("WiFi Hotspot")|| listAttributeNames.contains("WiFi")){
			propertyUploadData.setInternetAccess(1);
			propertyUploadData.setInternet("Y");
			propertyUploadData.setInternetAccessType("Wireless");
			propertyUploadData.setInternetAccessInBusinessCentre("Y");
			propertyUploadData.setInternetAccessInPublicArea("Y");
			propertyUploadData.setInternetAccessInRoom("Y");
			
		}
		
		if(listAttributeNames.contains("Non-smoking")){
			propertyUploadData.setNonSmokingArea(1);
		}
		if(listAttributeNames.contains("Breakfast")){
			propertyUploadData.setBreakfastBuffet(1);
			propertyUploadData.setMealsPlan("Breakfast");
		}
		if(listAttributeNames.contains("Elevators")){
			propertyUploadData.setElevator(1);
		}
		if(listAttributeNames.contains("Heated rooms")){
			propertyUploadData.setHeating(1);
			propertyUploadData.setRoomHeating(1);
		}
		if(listAttributeNames.contains("Luggage rack")){
			propertyUploadData.setLuggageStorage(1);
		}
		if(listAttributeNames.contains("Non-smoking Rooms")){
			propertyUploadData.setNonSmokingRooms(1);
			propertyUploadData.setSmokingNonSmoking("Nonsmoking");
		}
		if(listAttributeNames.contains("Beach")){
			propertyUploadData.setPrivateBeachArea(1);
		}
		if(listAttributeNames.contains("Restaurant")){
			propertyUploadData.setRestaurant(1);
		}
		if(listAttributeNames.contains("Disabled")){
			propertyUploadData.setRoomsFacilitiesForDisabled(1);
		}
		if(listAttributeNames.contains("Safe deposit box")){
			propertyUploadData.setSafeDepositBox(1);
			propertyUploadData.setRoomSafeDepositBox(1);
		}
		if(listAttributeNames.contains("Balcony/Terrace")){
			propertyUploadData.setTerrace(1);
		}
		if(listAttributeNames.contains("Braai/BBQ") || listAttributeNames.contains("Barbeque Grill")){
			propertyUploadData.setBarbecue(1);
		}
		if(listAttributeNames.contains("Fitness center")){
			propertyUploadData.setFitnessRoom(1);
		}
		if(listAttributeNames.contains("Pool outside")){
			propertyUploadData.setOutdoorSwimmingpool(1);
		}
		if(listAttributeNames.contains("Pool inside")){
			propertyUploadData.setIndoorSwimmingpool(1);
		}
		if(listAttributeNames.contains("Hot tub") || listAttributeNames.contains("Jacuzzi")){
			propertyUploadData.setHottub(1);
			propertyUploadData.setRoomHottub(1);
		}
		if(listAttributeNames.contains("Billiards")){
			propertyUploadData.setBilliard(1);
		}
		if(listAttributeNames.contains("Bowling")){
			propertyUploadData.setBowling(1);
		}
		if(listAttributeNames.contains("canoeing")){
			propertyUploadData.setCanoeing(1);
		}
		if(listAttributeNames.contains("Children's Area")){
			propertyUploadData.setChildrenPlayground(1);
		}
		if(listAttributeNames.contains("Cycling")){
			propertyUploadData.setChildrenPlayground(1);
		}
		if(listAttributeNames.contains("Darts")){
			propertyUploadData.setDarts(1);
		}
		if(listAttributeNames.contains("Skydiving")){
			propertyUploadData.setDiving(1);
		}
		if(listAttributeNames.contains("Fishing")){
			propertyUploadData.setFishing(1);
		}
		if(listAttributeNames.contains("Games room")){
			propertyUploadData.setGameRoom(1);
		}
		if(listAttributeNames.contains("Golf course")){
			propertyUploadData.setGolfcourse(1);
		}
		if(listAttributeNames.contains("Hiking trail")){
			propertyUploadData.setHiking(1);
		}
		if(listAttributeNames.contains("Horse Riding")){
			propertyUploadData.setHorseRiding(1);
		}
		if(listAttributeNames.contains("Library")){
			propertyUploadData.setLibrary(1);
		}
		if(listAttributeNames.contains("Miniature golf")){
			propertyUploadData.setLibrary(1);
		}
		if(listAttributeNames.contains("Sauna")){
			propertyUploadData.setSauna(1);
			propertyUploadData.setRoomSauna(1);
		}
		if(listAttributeNames.contains("Skiing / Winter Sports")){
			propertyUploadData.setSkiing(1);
		}
		if(listAttributeNames.contains("Snorkeling")){
			propertyUploadData.setSnorkeling(1);
		}
		if(listAttributeNames.contains("Solarium")){
			propertyUploadData.setSolarium(1);
		}
		if(listAttributeNames.contains("Spa")){
			propertyUploadData.setSpaWellnessCentre(1);
			propertyUploadData.setSpabath(1);
		}
		if(listAttributeNames.contains("Squash Court")){
			propertyUploadData.setSquash(1);
		
		}
		if(listAttributeNames.contains("Table Tennis")){
			propertyUploadData.setTableTennis(1);
		
		}
		if(listAttributeNames.contains("Steam Bath")){
			propertyUploadData.setTurkishSteambath(1);
		
		}
		if(listAttributeNames.contains("Windsurfing")){
			propertyUploadData.setWindSurfing(1);
		
		}
		if(listAttributeNames.contains("Bicycle")){
			propertyUploadData.setBicyclerent(1);
		
		}
		if(listAttributeNames.contains("Motorbike")){
			propertyUploadData.setBikesAvailable(1);
		
		}
		if(listAttributeNames.contains("Ski equipped")){
			propertyUploadData.setSkiEquipmenThireonsite(1);
			propertyUploadData.setSkiToDoorAccess(1);
			propertyUploadData.setSkiStorage(1);
		
		}
		if(listAttributeNames.contains("Air Conditioning") || listAttributeNames.contains("Air conditioning")){
			propertyUploadData.setAirconditioning(1);
		
		}
		if(listAttributeNames.contains("Washing Machine")){
			propertyUploadData.setWashingmachine(1);
		
		}
		if(listAttributeNames.contains("Washer/dryer")){
			propertyUploadData.setClothesdryer(1);
		
		}
		if(listAttributeNames.contains("Desk")){
			propertyUploadData.setDesk(1);
		
		}
		if(listAttributeNames.contains("King size bed")){
			propertyUploadData.setExtralongbeds(1);
		}
		if(listAttributeNames.contains("Ceiling fan")){
			propertyUploadData.setFan(1);
		}
		if(listAttributeNames.contains("Fireplace")){
			propertyUploadData.setFirePlace(1);
		}
		if(listAttributeNames.contains("Iron")){
			propertyUploadData.setClothingIron(1);
			propertyUploadData.setIroningFacilities(1);
		}
		if(listAttributeNames.contains("Private Pool")){
			propertyUploadData.setPrivatePool(1);
		}
		if(listAttributeNames.contains("Sofa")){
			propertyUploadData.setSofa(1);
		}
		if(listAttributeNames.contains("Soundproofed room")){
			propertyUploadData.setSoundProofing(1);
		}
		if(listAttributeNames.contains("Shower")){
			propertyUploadData.setShower(1);
			propertyUploadData.setHandShower(1);
		}
		if(listAttributeNames.contains("Shared bathroom")){
			propertyUploadData.setSharedBathroom(1);
		}
		if(listAttributeNames.contains("Bidet")){
			propertyUploadData.setBidet(1);
		}
		if(listAttributeNames.contains("Hair dryer")){
			propertyUploadData.setHairdryer(1);
		}
		if(listAttributeNames.contains("Toilet")){
			propertyUploadData.setToilet(1);
		}
		if(listAttributeNames.contains("CD  player")){
			propertyUploadData.setCdPlayer(1);
		}
		if(listAttributeNames.contains("AM/FM radio")){
			propertyUploadData.setRadio(1);
		}
		if(listAttributeNames.contains("TV")){
			propertyUploadData.setTv(1);
		}
		if(listAttributeNames.contains("Satellite TV")){
			propertyUploadData.setSatelliteChannels(1);
		}
		if(listAttributeNames.contains("DVD player")){
			propertyUploadData.setDvdPlayer(1);
		}
		if(listAttributeNames.contains("Video games")){
			propertyUploadData.setGameConsole(1);
			propertyUploadData.setVideoGames(1);
		}
		if(listAttributeNames.contains("Barbeque")){
			propertyUploadData.setBarbecue(1);
			
		}
		if(listAttributeNames.contains("Dining")){
			propertyUploadData.setDiningarea(1);
			
		}
		if(listAttributeNames.contains("Dishwasher")){
			propertyUploadData.setDishwasher(1);
			
		}
		if(listAttributeNames.contains("Kitchen")){
			propertyUploadData.setKitchen(1);
			propertyUploadData.setKitchenware(1);
			
		}
		if(listAttributeNames.contains("Kitchenette")){
			propertyUploadData.setKitchenette(1);
						
		}
		if(listAttributeNames.contains("Microwave")){
			propertyUploadData.setMicrowave(1);
			
		}
		if(listAttributeNames.contains("Minibar")){
			propertyUploadData.setMinibar(1);
						
		}
		if(listAttributeNames.contains("Oven")){
			propertyUploadData.setOven(1);
						
		}
		if(listAttributeNames.contains("Refrigerator")){
			propertyUploadData.setRefrigerator(1);
						
		}
		if(listAttributeNames.contains("Stove")){
			propertyUploadData.setStove(1);
						
		}
		if(listAttributeNames.contains("Coffee/Tea in Room") || listAttributeNames.contains("Coffee/Tea maker")){
			propertyUploadData.setCoffeeTeamaker(1);
						
		}
		if(listAttributeNames.contains("Toaster")){
			propertyUploadData.setToaster(1);
						
		}
		if(listAttributeNames.contains("Alarm clock")){
			propertyUploadData.setAlarmclock(1);
						
		}
		if(listAttributeNames.contains("Balcony")){
			propertyUploadData.setBalcony(1);
						
		}
		if(listAttributeNames.contains("Garden")){
			propertyUploadData.setGarden(1);;
			
		}
		if(listAttributeNames.contains("Garden view")){
			propertyUploadData.setGardenView(1);
						
		}
		if(listAttributeNames.contains("Lake view")){
			propertyUploadData.setLakeView(1);
						
		}
		if(listAttributeNames.contains("Mountain Views")){
			propertyUploadData.setMountainView(1);
						
		}
		if(listAttributeNames.contains("Pool view")){
			propertyUploadData.setPoolview(1);
						
		}
		if(listAttributeNames.contains("Sea view")){
			propertyUploadData.setSeaview(1);
						
		}
		if(listAttributeNames.contains("Children welcome")){
			propertyUploadData.setChildrenAllowed("Y");
						
		}
		//
		return propertyUploadData;
	}
	
	
	
	
	/**
	 * update propertyUploadData with some Accommodation data using list of attributes
	 * @param sqlSession
	 * @param propertyUploadData
	 * @param bpProduct
	 * @return PropertyUploadTemplate
	 */
	private PropertyUploadTemplate populateGeneralAccomodationData(SqlSession sqlSession,PropertyUploadTemplate propertyUploadData,Product bpProduct){
		//set the general property related data here
		propertyUploadData.setPropertyReferenceID(bpProduct.getId());
		propertyUploadData.setPropertyName(bpProduct.getName());
		String address = bpProduct.getFormattedAddress();
		if(StringUtils.isEmpty(address)) {
			address = "";
		}
		propertyUploadData.setAddress(address);
		//System.out.println("bpProduct.bpProduct.getAddress() "+bpProduct.getAddress());
		LocationRead locationRead=new LocationRead();
		locationRead.setId(bpProduct.getLocationid());
		LOG.info("Calling Location service for id "+bpProduct.getLocationid());
		Location location=LocationService.getInstance().findLocationById(sqlSession, locationRead);
		if(location!=null){
			propertyUploadData.setCity(location.getName());
			propertyUploadData.setLatitude(bpProduct.getLatitude()+"");
			propertyUploadData.setLongitude(bpProduct.getLongitude()+"");
		
			propertyUploadData.setCountryCode(location.getCountry());
			
			// use google location to set zip code and lat/long if it is missing
			try {
				if(StringUtils.isNotEmpty(address)) {
					Location googleLocation = GoogleLocationProcessor.getGoogleLocation(address);
					DecimalFormat df = new DecimalFormat("#.#####");
					df.setRoundingMode(RoundingMode.DOWN);
					if(googleLocation != null && StringUtils.isNotEmpty(googleLocation.getZipCode()) 
							&& (bpProduct.getLatitude() == null || (GoogleLocationProcessor.distance(bpProduct.getLatitude(), bpProduct.getLongitude(), googleLocation.getLatitude(), googleLocation.getLongitude()) < 0.5 ))) {
						propertyUploadData.setZipcode(googleLocation.getZipCode());
					} else if(bpProduct.getLatitude() != NumberUtils.DOUBLE_ZERO){
						googleLocation = GoogleLocationProcessor.getGoogleLocation(bpProduct.getLatitude(), bpProduct.getLongitude(), location.getCountry());
						if(googleLocation != null && StringUtils.isNotEmpty(googleLocation.getZipCode()) 
								&& (bpProduct.getLatitude() == null || (GoogleLocationProcessor.distance(bpProduct.getLatitude(), bpProduct.getLongitude(), googleLocation.getLatitude(), googleLocation.getLongitude()) < 0.5 ))) {
							propertyUploadData.setZipcode(googleLocation.getZipCode());
						}
					}
					
					
					if(bpProduct.getLatitude() == NumberUtils.DOUBLE_ZERO) {
						propertyUploadData.setLatitude(googleLocation.getLatitude()+"");
						propertyUploadData.setLongitude(googleLocation.getLatitude()+"");
					}
				} else if(bpProduct.getLatitude() != NumberUtils.DOUBLE_ZERO){
					Location googleLocation = GoogleLocationProcessor.getGoogleLocation(bpProduct.getLatitude(), bpProduct.getLongitude(), location.getCountry());
					if(googleLocation != null && StringUtils.isNotEmpty(googleLocation.getZipCode()) 
							&& (bpProduct.getLatitude() == null || (GoogleLocationProcessor.distance(bpProduct.getLatitude(), bpProduct.getLongitude(), googleLocation.getLatitude(), googleLocation.getLongitude()) < 0.5 ))) {
						propertyUploadData.setZipcode(googleLocation.getZipCode());
					}
				}
			
			} catch (GoogleLocationLimitException e) {
				// do not do anything
				e.printStackTrace();
			}
			
		}
		LOG.info("Calling Partner service for id "+bpProduct.getAltpartyid());
		PartnerRead partnerRead=new PartnerRead();
		partnerRead.setId(bpProduct.getAltpartyid());
		Partner partner=PartnerService.getInstance().fetchPartnerServiceById(sqlSession, partnerRead);
		if(partner!=null){
			propertyUploadData.setChain(partner.getName());
		}
		//propertyUploadData.setStarrating(starrating)//TODO:Check with Chirayu
		//propertyUploadData.setCheckInFrom(checkInFrom)//TODO:Check with Chirayu
		//propertyUploadData.setCheckInUntil(checkInUntil)//TODO:Check with Chirayu
		//propertyUploadData.setCheckOutFrom(checkOutFrom)//TODO:Check with Chirayu
		//propertyUploadData.setCheckOutUntil(checkOutUntil)//TODO:Check with Chirayu
		propertyUploadData.setCurrencyCode(bpProduct.getCurrency());
		//propertyUploadData.setAcceptedCreditCards(acceptedCreditCards);//TODO:Check with Chirayu
		//propertyUploadData.setVatNumber(vatNumber);//TODO:Check with Chirayu
		propertyUploadData.setPropertyURL(bpProduct.getWebaddress());
		String quickDescription = TextService.notes(sqlSession, bpProduct.getPublicId(), bpProduct.getLanguage());
		propertyUploadData.setQuickDescription(quickDescription);
		//propertyUploadData.setImportantInformation(importantInformation);//TODO:Check with Chirayu
		//
		if(bpProduct.getChild()!=null){
			propertyUploadData.setNumberOfChildrenAllowedStaying(bpProduct.getChild()+"");	
		//	propertyUploadData.setChildBedcost(cost)//TODO:Check with Chirayu
		}
		if(bpProduct.getPerson()!=null){
			propertyUploadData.setMaxPersonOfStayInTheVilla(bpProduct.getPerson()+"");
		}
		if(bpProduct.getRoom()!=null){
			propertyUploadData.setNumOfBedRooms(bpProduct.getRoom());
		}
		//propertyUploadData.setPetsFee(petsFee)//TODO:Check with Chirayu
		//propertyUploadData.setParkingFee(parkingFee)//TODO:Check with Chirayu
		return propertyUploadData;
	}
	private List<Product> fetchProductDetails(SqlSession sqlSession,int count,List<String> listSupplierID){
		return ProductService.getInstance().fetchAllProduct(sqlSession,count,listSupplierID);
		 
	}
	

	private List<Product> fetchProductDetails(SqlSession sqlSession,int count,List<String> listSupplierID,List<String> listProductID){
		return ProductService.getInstance().fetchAllProduct(sqlSession,count,listSupplierID,listProductID);
		 
	}
	

	/**
	 * To get the list of attributes relation details for the products
	 * @param sqlSession
	 * @param productId
	 * @param link
	 * @return list of attributes
	 */

	private List<String> fetchRelationDetails(SqlSession sqlSession,String productId,String link){
		List<String> listAttrNames=new ArrayList<String>();
		Relation relation=new Relation();
		relation.setHeadid(productId);
		relation.setLink("Product Attribute");
		List<String> listLineId=RelationService.getListRelationLineIds(sqlSession, relation);
		if(listLineId !=null  && listLineId.size()>0){
			listAttrNames=AttributeService.getInstance().fetchAttributeNames(sqlSession, listLineId);	
			/*if(listAttrNames!=null){
				for(String name:listAttrNames){
					LOG.info("Attribute Name "+name);
				}
			}*/
		}
		return listAttrNames;
		
		
	}
	
	/*private Party fetchContactDetails(){
		
	}*/

	/*private void prepareCSVFileForUpload(){
		
	}*/
}