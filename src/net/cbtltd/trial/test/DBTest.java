package net.cbtltd.trial.test;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.response.ReservationInfo;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.api.FeeMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.util.price.PriceComplexValue;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.shared.party.OrganizationCreate;
import net.cbtltd.shared.registration.RegistrationHelper;

import org.apache.ibatis.session.SqlSession;

public class DBTest {

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		RegistrationHelper helper = new RegistrationHelper();
		try {
			
			sqlSession = RazorServer.openSession();
			
			// ** Tax and fees db mappers test.
			//
			//	checkFeeandTaxTable(sqlSession);
			// 
			
			
			// ** Taxes and fees logic test
			//
			
			
			
			//testReservationResponse(sqlSession);

			//testPriceAdjustments(sqlSession);
			
			/* registration block */
			
			//sqlSession.getMapper(PropertyManagerInfoMapper.class).create(_TestRegistration.CreatePropertyManagerInfo());
			
			//System.out.println(helper.getSelectedProductsCount(sqlSession, "179895"));
			
			/*
			PartyService partyService = PartyService.getInstance();
			
			OrganizationCreate actionCreate = DBTest.CreatePartyOfTypePropertyManger();
			partyService.execute(sqlSession, actionCreate);*/
			
			//PropertyManagerSupportCC item = DBTest.CreateItemOfPMSupportCC();
			//sqlSession.getMapper(PropertyManagerSupportCCMapper.class).create(item);
			//List<PropertyManagerSupportCC> list = sqlSession.getMapper(PropertyManagerSupportCCMapper.class).list();
			
			
			/*PropertyManagerSupportCC supportCC = sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(179846);
			
			if (supportCC != null){
				sqlSession.getMapper(PropertyManagerSupportCCMapper.class).delete(supportCC.getId());
			}
			
			supportCC = new PropertyManagerSupportCC();
			supportCC.setPartyId(179846);
			
			supportCC.setSupportMC(true);
			supportCC.setSupportVISA(true);
			supportCC.setSupportAE(true);				
			
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).create(supportCC);*/	
			
			
			/* end registration block */
			
			/*ArrayList<String> organizationids = RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), null, null);*/
			//System.out.println("Start");

			
			//List<String> idsCollisions =  sqlSession.getMapper(ProductMapper.class).nameidwithreservationcollision("93576", "2014-04-25", "2014-05-03");
			//List<String> idsWithPrices =  sqlSession.getMapper(ProductMapper.class).nameidbyparentlocationandprice("93576", "2014-04-25", "2014-05-03");
			
			//SearchQuotes searchQuotes = AbstractReservation.getSearchProducts("89765", "2014-03-19", "2014-03-21", "2544cbbebc94c3c0", Constants.NO_CURRENCY, false, false, "", "", Constants.NO_XSL);
			
			//System.out.println("Stop" + searchQuotes.getQuotesCount());
			
			/*org.apache.commons.lang.time.StopWatch sw = new org.apache.commons.lang.time.StopWatch();
			
			sw.start()
			sw.stop();
			System.out.println("AbstractReservation.getSearchQuotes :: End Time : " + sw.getTime());*/

			//System.out.println(count);
			
			/*Product product = new Product();
			product.setSupplierid("123");
			product.setAltpartyid("133832");
			product.setAltSupplierId("133832");
			
			sqlSession.getMapper(ProductMapper.class).updateSupplierIdByAltSupplier(product);*/
			

			
			//PaymentTransaction paymentTransaction = createRandomPaymentTransaction();
//			sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
//			sqlSession.commit();
//			sqlSession.getMapper(PaymentTransactionMapper.class).read(1);
//			System.out.println(paymentTransaction.getFundsHolder());
//			PaymentGatewayProvider paymentGatewayProvider = createRandomPaymentGatewayProvider();
//			sqlSession.getMapper(PaymentGatewayProviderMapper.class).create(paymentGatewayProvider);
//			sqlSession.commit();
			
//			ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).read(1);
//			System.out.println(managerToGateway.getFundsHolder());
//			List<ProductInfo> productInfos = sqlSession.getMapper(ProductMapper.class).productInfoListByAltSupplier("30", "179803");
//			System.out.println(productInfos.size());
			//ManagerToGateway managerToGateway = createRandomManagerToGatewayRecord();
			//sqlSession.getMapper(ManagerToGatewayMapper.class).create(managerToGateway);
			
			sqlSession.commit();
		} catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
	}


	/**
	 * Method to check fees and taxes db table mappers.
	 * 
	 * @param sqlSession
	 */
	private static void checkFeeandTaxTable(SqlSession sqlSession){
		
		Fee feeAction = new Fee();
		
		feeAction.setEntityType(Fee.MANDATORY);
		feeAction.setProductId("425242");
		feeAction.setPartyId("325804");  // TODO: Check this if its needed
		feeAction.setFromDate(new Date(2000, 1, 1));
		feeAction.setTaxType(Fee.NOT_TAXABLE);
		feeAction.setState(Fee.CREATED);
				
		ArrayList<Fee> fees = sqlSession.getMapper(FeeMapper.class).readbydateandproduct(feeAction);
		
		if (fees != null && fees.size() > 0){
			System.out.println("Success: Fees are good. Total fees selected:" + fees.size());
		} else {
			System.out.println("Error with fee: Total fees selected 0.");
		}
		
		Tax taxAction = new Tax();		
		taxAction.setPartyid("325806");
		taxAction.setProductId("427819");
		taxAction.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
		
		ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).readbyproductid(taxAction);
		
		if (taxes != null && taxes.size() > 0){
			System.out.println("Success: Taxes are good. Total taxes selected:" + taxes.size());
		} else {
			System.out.println("Error with tax: Total taxes selected 0.");
		}
		
	}
	
	private static void testReservationResponse(SqlSession sqlSession)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ReservationResponse.class);
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		ReservationResponse rr = new ReservationResponse();
		rr.setDownPayment(555.55);
		rr.setPropertyName("property");
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read("20727");
		ReservationInfo info = new ReservationInfo(reservation);			
		rr.setReservationInfo(info);
		
		StringWriter sw = new StringWriter();						
		jaxbMarshaller.marshal(rr, sw);
		
		String rr_xml = sw.toString();
		
		//String rr_xml = MarshallerHelper.toXML(rr);
		System.out.println(rr_xml);
		
		ReservationResponse reservationResponse = (ReservationResponse) jaxbUnMarshaller.unmarshal(new StringReader(rr_xml));
		ReservationInfo reservation2 = reservationResponse.getReservationInfo();
		
		System.out.println("1");
	}
	
	
	public static void testPriceAdjustments(SqlSession session){
		
		Reservation reservation = new Reservation();
		reservation.setProductid("3606");
		reservation.setSupplierid("90640");
		reservation.setFromdate(Constants.parseDate("2014-09-28"));
		reservation.setTodate(Constants.parseDate("2014-09-30"));
		PriceComplexValue complexValue = ReservationService.addPriceAdjustments(session, reservation, new PriceComplexValue());
		
		if (complexValue != null){
			System.out.println("OK." + complexValue.getTotPrice());
		}
	}
	
	/* property_manager_info table  */
	public static PropertyManagerInfo createRandomPropertyManagerInfo(){
		PropertyManagerInfo managerInfo = new PropertyManagerInfo();
		managerInfo.setPropertyManagerId(55);
		managerInfo.setPmsId((int)Math.random() * 100);
		managerInfo.setFundsHolder(FundsHolderEnum.BookingPal.value());
		managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.API.type());
		managerInfo.setRegistrationStepId(1);
		managerInfo.setDamageCoverageType(1);
		managerInfo.setDamageInsurance("insuranse");
		managerInfo.setNumberOfPayments(2);
		managerInfo.setPaymentAmount(50.0);
		managerInfo.setPaymentType(1);
		managerInfo.setRemainderPaymentDate(10);
		managerInfo.setCheckInTime(Time.valueOf("10:00:00"));
		managerInfo.setCheckOutTime(Time.valueOf("14:00:00"));
		managerInfo.setTermsLink("http://razor-cloud.com/razor/");
		managerInfo.setNewRegistration(true);
		managerInfo.setCreatedDate(new Date());
		return managerInfo;
	}
	
	/* property_manager_cancelltion_rule table */
	public static PropertyManagerCancellationRule createRandomPMCancellationRule(){
		PropertyManagerCancellationRule cancellationRule = new PropertyManagerCancellationRule();
		cancellationRule.setPropertyManagerId(55);
		cancellationRule.setCancellationDate((int)Math.random() * 100);
		cancellationRule.setCancellationRefund(10);
		cancellationRule.setCancellationTransactionFee(25.0);
		return cancellationRule;
	}
	
	public static OrganizationCreate CreatePartyOfTypePropertyManger(){

		OrganizationCreate actionCreate = new OrganizationCreate();
		actionCreate.setEmployerid("11111");
		actionCreate.setCreatorid("4");
		actionCreate.setOldstate(Party.INITIAL);
		actionCreate.setOptions("11");
		actionCreate.setUsertype(Constants.PM_USER_TYPE);
		//action = partyService.execute(sqlSession, actionCreate);
		actionCreate.setCurrency(Currency.Code.USD.name());
		actionCreate.setLanguage(Language.EN);
		actionCreate.setState(Party.CREATED);
		actionCreate.setAltid("111");
		actionCreate.setName("TEST KAMBUR COMPANY");
		actionCreate.setExtraname("TEST KAMBUR");
		actionCreate.setPostaladdress("Golovna 2" + ",\t" + "Chernivtsi" + ",\t" + "");
		actionCreate.setPostalcode("6666");
		actionCreate.setCountry("66");
		actionCreate.setDayphone("123456789");
		actionCreate.setEmailaddress("test1.kambur@gmail.com");
		actionCreate.setPassword(Model.encrypt("password"));

		return actionCreate;
	}
	
	
	/* property_manager_support_cc table */
	private static PropertyManagerSupportCC CreateItemOfPMSupportCC(){
		
		PropertyManagerSupportCC result = new PropertyManagerSupportCC();
		result.setPartyId(179842);
		result.setSupportMC(true);
		result.setSupportVISA(true);
		result.setSupportAE(true);
		result.setSupportJCB(true);
		return result;
	}
	
	private static ManagerToGateway createRandomManagerToGatewayRecord() {
		ManagerToGateway managerToGateway = new ManagerToGateway();
		managerToGateway.setCreateDate(new Date());
		managerToGateway.setFundsHolder((int)(Math.random() * 2));
		managerToGateway.setGatewayAccountId("EOJ2S-Z6OoN_le_KS1d75wsZ6y0SFdVsY9183IvxFyZp");
		managerToGateway.setGatewayCode("EClusMEUk8e9ihI7ZdVLF5cZ6y0SFdVsY9183IvxFyZp");
		managerToGateway.setPaymentGatewayId(1);
		managerToGateway.setSupplierId(8062);
		managerToGateway.setAdditionalInfo("additional");
		
		return managerToGateway;
	}
	
	private static PaymentGatewayProvider createRandomPaymentGatewayProvider() {
		PaymentGatewayProvider paymentGatewayProvider = new PaymentGatewayProvider();
		paymentGatewayProvider.setAutopay(((int)Math.random()) == 1 ? true : false);
		paymentGatewayProvider.setCreateDate(new Date());
		paymentGatewayProvider.setFee((short) (Math.random() * 100));
		paymentGatewayProvider.setName("some string");
		
		return paymentGatewayProvider;
	}
	
	private static PaymentTransaction createRandomPaymentTransaction() {
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setChargeDate(new Date());
		paymentTransaction.setChargeType("Full");
		paymentTransaction.setCreateDate(new Date());
		paymentTransaction.setCreditCardFee(Math.random() * 100);
		paymentTransaction.setCurrency("UAH");
		paymentTransaction.setFinalAmount(Math.random() * 100);
		paymentTransaction.setFundsHolder((int)(Math.random() * 3));
		paymentTransaction.setGatewayId(1);
		paymentTransaction.setGatewayTransactionId("");
		paymentTransaction.setMessage("some string");
		paymentTransaction.setPartialIin((int)(Math.random() * 8999) + 1000);
		paymentTransaction.setPartnerId(2); // Bookt Test Manager ID
		paymentTransaction.setPartnerPayment(Math.random() * 100);
		paymentTransaction.setPaymentMethod(0);
		paymentTransaction.setPmsConfirmationId((int)(Math.random() * 1000));
		paymentTransaction.setReservationId((int)(Math.random() * 1000));
		paymentTransaction.setServerId(1);
		paymentTransaction.setStatus("accepted");
		paymentTransaction.setSupplierId((int)(Math.random() * 1000));
		paymentTransaction.setTotalAmount(Math.random() * 100);
		paymentTransaction.setTotalBookingpalPayment(Math.random() * 100);
		paymentTransaction.setTotalCommission(Math.random() * 100);
		
		return paymentTransaction;
	}
	
	
}

