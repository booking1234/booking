package net.cbtltd.rest.registration;

import java.sql.Time;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.trial.test.DBTest;

import com.google.gson.Gson;
import com.lowagie.toolbox.Toolbox.Console;


public class _TestRegistration {

	public _TestRegistration() {
	}
	
	public static void main(String args[])  {
		
		java.sql.Time time = Time.valueOf("10:1:0");
		System.out.println(time.toString());
		java.sql.Time time1 = Time.valueOf("22:12:00");
		System.out.println(time1.toString());
		
		
		//testMarshalling();
		//System.out.println(Model.encrypt("90640"));
		//System.out.println(Model.encrypt("7457"));
		//System.out.println(Model.decrypt("334f8f6b2698fa12"));
				
	}
	
	
	private static void testMarshalling(){
		
		Gson GSON = new Gson();
		try {

			/*FourthStepResponse response = new FourthStepResponse(
					"{\"pos\":\"2544cbbebc94c3c0\",\"step\":4,\"billing_policies\":{\"number_of_payments\":2,\"first_payment_amount\":50," + 
					"\"first_payment_type\":1,\"second_payment_type\":1,\"cancelation_type\":2,\"cancelation_time\":0,\"cancelation_refund\":50," + 
							"\"cancelation_transaction_fee\":50,\"damage_coverage_type\":1,\"damage_insurance\":1,\"currency\":4}}");*/
			
			//String achpayment = "ACHPaymentInfo [bank=TEst1, holderName=TEst2, routingNumber=null, accountingNumber=TEast34, accountType=Type 2, socialOrTaxNumber=null, socialOrTaxType=ssn, birthdate=5-7-1953]";
			
			//ACHPaymentInfo info = GSON.fromJson(aRchpayment, ACHPaymentInfo.class);
			
			/*FourthStepResponse response = new FourthStepResponse();
			response.setStatus(new Status(Status.SUCCESS_CODE, ""));
			response.setStepData(new AdditionalParams(1, "", ""));
			response.setPaymentGatewayInfo(new PaymentGatewayInfo());
			response.getPaymentGatewayInfo().setAccountId("test account");
			response.getPaymentGatewayInfo().setTransactionKey("test transactionKey");*/
			
			
			
			ProductInfo info = new ProductInfo();
			info.setSecurityDeposit("123");
			info.setSelected(true);
			info.setCleaningFee("123");
			
			String json = GSON.toJson(info);
						
			String server_json = "{\"cleaning_fee\":2\"123\",\"deposit\":\"123\",\"selected\":\"1\"}";
			//String server_json1 = "ACHPaymentInfo [bank=1234, holderName=1234, routingNumber=1234567890, accountingNumber=1231234, accountType=Type 1, socialOrTaxNumber=null, socialOrTaxType=ssn, birthdate=1-1-2014]";
			
			//ProductInfo info2 =  GSON.fromJson(server_json, ProductInfo.class);
			
			CreditCardTypes cardTypes = new CreditCardTypes();
			cardTypes.setDinersClub(true);
			cardTypes.setAmericanExpress(true);
			cardTypes.setMasterCard(true);
			
			CancellationPolice police = new CancellationPolice();
			police.setCancelationRefund("100");
			police.setCancelationTime("100");
			police.setCancelationTransactionFee("19");
						
			JAXBContext context = JAXBContext.newInstance(CancellationPolice.class);
			Marshaller m = context.createMarshaller();
			m.marshal(police, System.out);
			
			System.out.println("Test OK");
			System.out.println("JSON test.. \n");
			
			System.out.println(GSON.toJson(cardTypes));
			
			
			
			System.out.println("JSON OK\n");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("ERROR TEST");
		}
	}
	
	public static PropertyManagerInfo CreatePropertyManagerInfo(){

		PropertyManagerInfo managerInfo = new PropertyManagerInfo();
		
		managerInfo.setPropertyManagerId(111);
		managerInfo.setPmsId(222);
		managerInfo.setFundsHolder(FundsHolderEnum.BookingPal.value());
		managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.API.type());
		managerInfo.setRegistrationStepId(8);
		managerInfo.setDamageCoverageType(1);
		managerInfo.setDamageInsurance("insuranse");
		managerInfo.setNumberOfPayments(2);
		managerInfo.setPaymentAmount(50.0);
		managerInfo.setPaymentType(1);
		managerInfo.setRemainderPaymentDate(10);
		managerInfo.setNewRegistration(true);
		managerInfo.setCreatedDate(new Date());
		
		return managerInfo;
	}

	/*public static void main(String args[]) throws Exception {
		System.out.println("Starting REST Client");

		try {

			RegistrationJson reg = new RegistrationJson();
			StringBuilder sb = new StringBuilder();

			
			 == Registration == 

			sb.append("{");
			sb.append("\"pos\":\"" + Model.encrypt("150274") + "\",");
			sb.append("\"account\":\"ALTID\",");
			sb.append("\"company\":\"REgistrationTESTCompany\",");
			sb.append("\"firstname\":\"REgistrationTESTfirstName\",");
			sb.append("\"lastname\":\"REgistrationTESTlastName\",");
			sb.append("\"address\":\"REgistrationTESTaddress\",");
			sb.append("\"city\":\"REgistrationTESTcity\",");
//			sb.append("\"state\":\"state\",");
			sb.append("\"code\":\"code\",");
			sb.append("\"countryid\":\"AD\",");
			sb.append("\"phone\":\"telephone\",");
			sb.append("\"email\":\"localhost4567.test@gmail.com\",");
			sb.append("\"password\":\"localhost\"");
			sb.append("}");		
			
			 == End Registration == 

			 === Generate test POST request to the Second Step === 
			
			 {"pos":"ffaf0647c1d664e1",
			"account":"1234567",
			"company":"Blouberg Living",
			"firstname":"Vova",
			"lastname":"Kambur",
			"address":"Golovna 154",
			"city":"Chern",
			"code":"12425",
			"countryid":"UK",
			"phone":"12-12345677",
			"email":"testregistration@razor.com",
			"password":"testregistration",
			"step":1} 
			
			
			
			 * SecondStepRequest request = new SecondStepRequest();
			 * request.setStep("2"); request.setPos("90640"); List<Integer> list =
			 * new ArrayList<Integer>(); list.add(1); list.add(3); list.add(5);
			 * request.setChannel_partners_list_ids(list);
			 

			 END 
			 //=== Generate test POST request for the Third Step === 

			//sb.append("{\"pos\":\"6a31a94a43a70691\",\"channel_partners_list_ids\":[1,2],\"step\":2}");
			
			
			 ThirdStepRequest thirdRequest = new ThirdStepRequest();
			 List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
			 
			 ProductInfo pInfo = new ProductInfo();
			 pInfo.setId("51608");
			 pInfo.setCommission("100"); 
			 pInfo.setCleaningFee("400");
			 pInfo.setDeposit("2000"); 
			 productInfos.add(pInfo);
			 
			 pInfo = new ProductInfo(); 
			 pInfo.setId("51635"); 
			 pInfo.setCommission("124");
			 pInfo.setCleaningFee("444"); 
			 pInfo.setDeposit("5555");
			 productInfos.add(pInfo);
			 
			 thirdRequest.setProducts(productInfos);
			 thirdRequest.setPos("3e33c72f045cec6a");
			 thirdRequest.setStep(3);
			 

			 
			 === END Generate test response for the Third Step === 

			
			 * SecondStepResponse ssResponse = new SecondStepResponse();
			 * List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
			 * ProductInfo pInfo = new ProductInfo(); pInfo.setId("2");
			 * pInfo.setDecription("descr"); pInfo.setBathrooms("2");
			 * pInfo.setBedrooms("3"); pInfo.setCommission("100");
			 * productInfos.add(pInfo); ssResponse.setProducts(productInfos);
			 * ssResponse.setStatus(new Status(Status.SUCCESS_CODE, ""));
			 * ssResponse.setStepData(new AdditionalParams("3", "", ""));
			 

			 
			 ===END  Generate test POST request to the Fourth Step === 

			
			 *//** FourthStepRequest stepRequest = new FourthStepRequest();
			 * BillingPoliciesInfo info = new BillingPoliciesInfo("50", "1", "1",
			 * "1", "0", "100", "20", "2", "1,000 - 00000", "US");
			 * stepRequest.setStep("4"); stepRequest.setPos("123455");
			 * stepRequest.setBillingPolicies(info);*//*
			 

			 

			//System.out.println(Model.encrypt("161064"));
			
			String reqstring = GSON.toJson(thirdRequest);
			//System.out.print(string);
			
			String string = GSON.toJson(reg.doNextStep(reqstring));
			System.out.println(string);
			ThirdStepResponse response = GSON.fromJson(string, ThirdStepResponse.class);
			System.out.println(GSON.toJson(response));
			


			// reg.createManagerJson(sb.toString());
			// RegistrationResponse response = reg.doNextStep(string);
			
			
			 * String extraName = " ierre-Andre "; String first = ""; String
			 * last = ""; extraName = extraName.trim(); if
			 * (!extraName.isEmpty()) {
			 * 
			 * String lastName = ""; Integer spaceIndex =
			 * extraName.indexOf(" "); if (spaceIndex > 1 && spaceIndex <
			 * extraName.length()) { lastName = extraName.substring(spaceIndex);
			 * first = extraName.replace(lastName, ""); last = lastName; } else
			 * { first = extraName; }
			 * 
			 * }
			 

			String dayPhone = "+27(( 21 438 1))234";

			String first = "";
			String last = "";

			dayPhone = dayPhone.replace("(", "");
			dayPhone = dayPhone.replace(")", "");
			
			if (dayPhone != null) {
				//if (dayPhone.matches("^.*\\).*$")) {
					String[] arrayPhone = dayPhone.split("\\)");
					if (arrayPhone.length == 2) {
						first = arrayPhone[0].replace("(", "");
						last = arrayPhone[1];
					} else {
						last = dayPhone;
					}
				//}
			}

			//System.out.println("first:" + first);
			//System.out.println("last:" + last);

			if (false) {
				// Create request JSON string
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"partyid\":\"test party id\",");
				sb.append("\"account\":\"account\",");
				sb.append("\"company\":\"companyName\",");
				sb.append("\"firstname\":\"firstName\",");
				sb.append("\"lastname\":\"lastName\",");
				sb.append("\"address\":\"address\",");
				sb.append("\"city\":\"city\",");
				sb.append("\"state\":\"state\",");
				sb.append("\"code\":\"code\",");
				sb.append("\"countryid\":\"AD\",");
				sb.append("\"tel\":\"telephone\",");
				sb.append("\"email\":\"localhost3.test@gmail.com\",");
				sb.append("\"password\":\"localhost3\"");
				sb.append("}");
				// {"product":{"bedroom":2,"latitude":-33.9571457582702,"locationid":58777,"longitude":18.3773481845856,"name":"Camps Bay Terrace Palm Suite","room":2,"supplierid":5,"unit":"DAY"}}

				String rq = sb.toString();

				System.out.println("rq=" + rq);

				PostMethod post = new PostMethod("http://localhost:8280/xml/json/registration");
				// PostMethod post = new
				// PostMethod("http://localhost:8080/Registration/rest/manager/step");
				post.addRequestHeader("Accept", "application/json");
				// post.addRequestHeader("Content-Type", "application/json");
				RequestEntity entity = new StringRequestEntity(rq, "application/json", null);
				post.setRequestEntity(entity);
				HttpClient httpclient = new HttpClient();
				int rs = httpclient.executeMethod(post);
				System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());
			}
		} catch (Throwable x) {
			x.printStackTrace();
		}
		System.out.println("Stopping REST Client");
	}*/

}
