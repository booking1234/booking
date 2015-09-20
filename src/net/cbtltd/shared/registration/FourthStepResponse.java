package net.cbtltd.shared.registration;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.BillingPoliciesInfo;
import net.cbtltd.rest.registration.CancellationPolice;
import net.cbtltd.rest.registration.PaymentGatewayInfo;
import net.cbtltd.rest.registration.TIME_INTERVAL;
import net.cbtltd.rest.rent.RentProperty;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsAuthentication;
import net.cbtltd.shared.party.Organization;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gson.Gson;

@XmlRootElement(name = "data")
public class FourthStepResponse extends StepResponse implements RegistrationResponse {

	private AdditionalParams stepData;
	private PaymentGatewayInfo paymentGatewayInfo;
	RegistrationHelper helper = null;
	@XmlTransient
	private Gson GSON = new Gson();
	private static final Logger LOG = Logger.getLogger(FourthStepResponse.class.getName());

	public FourthStepResponse() {
		super();
	}

	public FourthStepResponse(String request) {
		super();

		helper = new RegistrationHelper();
		FourthStepRequest requestObject = null;
		String result = "";

		SqlSession sqlSession = RazorServer.openSession();
		try {

			requestObject = GSON.fromJson(request, FourthStepRequest.class);

			if (requestObject != null) {
				
				setManagerId(requestObject.getPos());
				result += execute(sqlSession, requestObject); 
				if (result.isEmpty()){  result += init(sqlSession, requestObject); }
								
				if (!result.isEmpty()) {
					setStatus(new ApiResponse(result));
					setStepData(new AdditionalParams(requestObject.getStep(), "", ""));					
				}else{
					sqlSession.commit();
				}
			}else {
				throw new ServiceException(Error.step_request_json);
			}
		} catch (Exception e) {
			sqlSession.rollback();
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), "", ""));
		} finally { sqlSession.close();}
	}

	/**
	 * Initializing response data.
	 * @param request
	 */
	private String init(SqlSession sqlSession, FourthStepRequest requestObject) {

		// SET Payment gateways list

		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(this.getPmId());
		if (managerToGateway != null) {
			PaymentGatewayInfo gatewayInfo = new PaymentGatewayInfo();
			gatewayInfo.setId(String.valueOf(managerToGateway.getPaymentGatewayId()));
			gatewayInfo.setName("");
			gatewayInfo.setAccountId(managerToGateway.getGatewayAccountId());
			gatewayInfo.setTransactionKey(managerToGateway.getGatewayCode());
			try {
				if (PaymentGatewayHolder.getPaymentGateway(managerToGateway.getPaymentGatewayId()).getName().equals(PaymentGatewayHolder.DIBS)){
					gatewayInfo.setAdditionalInfo(MarshallerHelper.fromXml(DibsAuthentication.class, managerToGateway.getAdditionalInfo()).getMerchantId());
				}else if (PaymentGatewayHolder.getPaymentGateway(managerToGateway.getPaymentGatewayId()).getName().equals(PaymentGatewayHolder.RENT)){
					gatewayInfo.setAdditionalInfo(MarshallerHelper.fromXml(RentProperty.class, managerToGateway.getAdditionalInfo()).getPropertyCode());
				}else
				{
					gatewayInfo.setAdditionalInfo(managerToGateway.getAdditionalInfo());
				}
			} catch (JAXBException e) {
				Log.warn("Unable to unmarshal MerchantID.");
				gatewayInfo.setAdditionalInfo(null);
			}

			this.setPaymentGatewayInfo(gatewayInfo);
		}

		this.setStatus(new ApiResponse());
		this.setStepData(new AdditionalParams(requestObject.getNextStep(), "", ""));

		return "";
	}

	/**
	 * Update data for pm regarding to step number and input request.
	 * @param pmId
	 * @param request
	 */
	private String execute(SqlSession sqlSession, FourthStepRequest requestObject) throws Exception {

		PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());

		if (managerInfo == null) {
			return Error.organization_info.getMessage();
		}

		if (requestObject.getBillingPolicies() != null) {

			BillingPoliciesInfo policiesInfo = requestObject.getBillingPolicies();

			Organization organization = new Organization();
			organization.setId(this.getPmId().toString());

			setPaymentTerms(policiesInfo, managerInfo, organization);
			setCurrencies(sqlSession, policiesInfo, organization);
			setCancellationPolicies(sqlSession, policiesInfo, managerInfo);
			setDamageCoverage(policiesInfo, managerInfo);
			
			if (policiesInfo.getCheckInTime() != null && !policiesInfo.getCheckInTime().isEmpty()){
				managerInfo.setCheckInTime(java.sql.Time.valueOf(policiesInfo.getCheckInTime() + ":00"));				
			}			
			if (policiesInfo.getCheckOutTime() != null && !policiesInfo.getCheckOutTime().isEmpty()){
				managerInfo.setCheckOutTime(java.sql.Time.valueOf(policiesInfo.getCheckOutTime() + ":00"));				
			}
			if (policiesInfo.getTermsLink() != null){
				managerInfo.setTermsLink(policiesInfo.getTermsLink());
			}
			
			if(helper.isManagerSupportCredirCard(sqlSession, managerInfo)){
				
				if (managerInfo.getPaymentProcessingType() == PaymentProcessingTypeEnum.GATEWAY.type()){
					sqlSession.getMapper(ManagerToGatewayMapper.class).deleteBySupplierId(this.getPmId());
				}
				
				managerInfo.setFundsHolder(FundsHolderEnum.External.value()); 	
				managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.API.type());	/* Payment Processing Method: API(PMS support credit card processing); */
			}
			
			managerInfo.setRegistrationStepId(requestObject.getNextStep());
			
			RelationService.replace(sqlSession, Relation.PARTY_VALUE, organization.getId(), organization.getValues());
			sqlSession.getMapper(PartyMapper.class).update(organization);
			sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);
		}

		return "";
	}

	private void setDamageCoverage(BillingPoliciesInfo policiesInfo, PropertyManagerInfo managerInfo) throws Exception {
		if (policiesInfo.getDamageCoverageType() == null || policiesInfo.getDamageInsurance() == null) {
			throw new Exception("Invalid damage coverage parameters.");
		}

		managerInfo.setDamageCoverageType(Integer.valueOf(policiesInfo.getDamageCoverageType()));
		managerInfo.setDamageInsurance(policiesInfo.getDamageInsurance());
	}

	private void setCancellationPolicies(SqlSession sqlSession, BillingPoliciesInfo policiesInfo, PropertyManagerInfo managerInfo) throws Exception {

		if (policiesInfo.getCancelationType() == null) { throw new Exception("Invalid cancellation type."); }
		Integer cancellationType = RegistrationUtils.getInteger(policiesInfo.getCancelationType());
		if (cancellationType != null){
			if (cancellationType == RegistrationConstants.CANCEL_TO_DAY_OF_TRIP){
				/* remove old cancellation rules. */
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).deletebypmid(managerInfo.getPropertyManagerId());
				
				PropertyManagerCancellationRule cancellationRule = new PropertyManagerCancellationRule();
				cancellationRule.setPropertyManagerId(managerInfo.getPropertyManagerId());
				cancellationRule.setCancellationDate(0);
				cancellationRule.setCancellationRefund(100);
				cancellationRule.setCancellationTransactionFee(0.0);
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).create(cancellationRule);
				
				managerInfo.setCancellationType(cancellationType);
			}else if (cancellationType == RegistrationConstants.CANCEL_CUSTOM ){
				/* remove old cancellation rules. */
				sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).deletebypmid(managerInfo.getPropertyManagerId());
				if (policiesInfo.getCancellationPolicies() != null){
					for(CancellationPolice cancellationPolice : policiesInfo.getCancellationPolicies()) {
						
						PropertyManagerCancellationRule cancellationRule = new PropertyManagerCancellationRule();
						if (cancellationPolice != null && cancellationPolice.getCancelationTime() != null && cancellationPolice.getCancelationRefund() != null
									&& cancellationPolice.getCancelationTransactionFee() != null){
							
							/*Integer cancellationTimeType = Integer.valueOf(cancellationPolice.getCancelationTime());
							TIME_INTERVAL cancelDate = TIME_INTERVAL.getDate(cancellationTimeType);*/
							Integer cancellationDays = RegistrationUtils.getInteger(cancellationPolice.getCancelationTime());
							if (cancellationDays != null){
								cancellationRule.setCancellationDate(cancellationDays);
							}else {
								throw new Exception("Invalid cancellation days count.");
							}
							
							Integer refund = RegistrationUtils.getInteger(cancellationPolice.getCancelationRefund());
							if (refund != null){
								cancellationRule.setCancellationRefund(refund);
							}else {
								throw new Exception("Invalid cancellation refund.");
							}
							
							Double transactionFee = RegistrationUtils.getDouble(cancellationPolice.getCancelationTransactionFee());
							if (transactionFee != null){
								cancellationRule.setCancellationTransactionFee(transactionFee);
							}else {
								throw new Exception("Invalid cancellation transaction Fee.");
							}
							
							cancellationRule.setPropertyManagerId(managerInfo.getPropertyManagerId());
							sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).create(cancellationRule);
						}
					}
					managerInfo.setCancellationType(cancellationType);
				} else { throw new Exception("Invalid cancellation parameters.");  }
			}else { throw new Exception("Invalid cancellation type."); }
		}else { throw new Exception("Missing cancellation type."); }
	}
			
	private void setPaymentTerms(BillingPoliciesInfo policiesInfo, PropertyManagerInfo managerInfo, Organization organization) throws Exception {

		if (policiesInfo.getNumberOfPayments() != null) {

			String paymentsNumber = policiesInfo.getNumberOfPayments();

			if (paymentsNumber.equals("1")) {

				/* PM Info block */
				managerInfo.setNumberOfPayments(1);

				/* Relation block */
				organization.setValue(Party.Value.Deposit.name(), BillingPoliciesInfo.ONE_PAYMENT_VALUE);
				organization.setValue(Party.Value.DepositType.name(), BillingPoliciesInfo.ONE_PAYMENT_TYPE);
			} else if (paymentsNumber.equals("2")) {

				if (policiesInfo.getFirstPaymentAmount() == null || policiesInfo.getFirstPaymentType() == null
						|| policiesInfo.getSecondPaymentType() == null) {
					throw new Exception("Invalid payment terms parameters.");
				}

				Integer firstPaymentType = Integer.valueOf(policiesInfo.getFirstPaymentType());
				Integer secondPaymentType = Integer.valueOf(policiesInfo.getSecondPaymentType());
				String firstPaymentAmount = policiesInfo.getFirstPaymentAmount();

				/* PM Info block */
				managerInfo.setNumberOfPayments(2);
				managerInfo.setPaymentAmount(Double.valueOf(firstPaymentAmount));
				managerInfo.setPaymentType(firstPaymentType);

				TIME_INTERVAL remDate = TIME_INTERVAL.getDate(secondPaymentType);
				managerInfo.setRemainderPaymentDate(remDate.getDaysCount());
				/* End PM Info block */

				/* Relation block */
				organization.setValue(Party.Value.Deposit.name(), firstPaymentAmount);
				
				// if (% of booking) is selected 
				if (firstPaymentType == 1){
					organization.setValue(Party.Value.DepositType.name(), Party.DEPOSITS[0]);
				}
				// if "Flat Rate" is selected
				if (firstPaymentType == 2){
					organization.setValue(Party.Value.DepositType.name(), Party.DEPOSITS[2]);
				}

				String[] payfullValue = Party.TIME_INTERVAL[secondPaymentType].split(":");
				if (payfullValue.length == 2) {
					organization.setValue(Party.Value.Payfull.name(), payfullValue[0]);
					organization.setValue(Party.Value.Payunit.name(), payfullValue[1]);
				}
				/* End Relation block */
			}
		}
	}

	private void setCurrencies(SqlSession sqlSession, BillingPoliciesInfo policiesInfo, Organization organization) throws Exception {

		if (policiesInfo.getCurrency() == null) {
			throw new Exception("Invalid currency parameter.");
		}
		 
		/*ArrayList<String> currency = new ArrayList<String>();
		currency.add(policiesInfo.getCurrency());
		organization.setCurrencies(currency);
		RelationService.create(sqlSession, Relation.ORGANIZATION_CURRENCY, organization.getId(), Currency.Code.USD.name());*/
		
		organization.setCurrency(policiesInfo.getCurrency());
	}

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}

	@XmlElement(name = "step_data")
	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

	@XmlElement(name = "gateway")
	public PaymentGatewayInfo getPaymentGatewayInfo() {
		return paymentGatewayInfo;
	}

	public void setPaymentGatewayInfo(PaymentGatewayInfo paymentGatewayInfo) {
		this.paymentGatewayInfo = paymentGatewayInfo;
	}

}
