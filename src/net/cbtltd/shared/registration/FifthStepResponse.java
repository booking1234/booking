package net.cbtltd.shared.registration;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.CreditCardTypes;
import net.cbtltd.rest.registration.PaymentGatewayInfo;
import net.cbtltd.rest.rent.RentProperty;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsAuthentication;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class FifthStepResponse extends StepResponse implements RegistrationResponse {

	@XmlElement(name = "step_data")
	private AdditionalParams stepData;
	@XmlTransient
	private Gson GSON = new Gson();
	private static final Logger LOG = Logger.getLogger(FifthStepResponse.class.getName());

	public FifthStepResponse() {
		super();
		setStepData(new AdditionalParams(5, "", ""));
	}
	
	@Override
	public RegistrationResponse getResponse() {
		// TODO Auto-generated method stub
		return this;
	}

	public FifthStepResponse(String request) {
		super();

		FifthStepRequest requestObject = null;
		SqlSession sqlSession = RazorServer.openSession();
		try {

			requestObject = GSON.fromJson(request, FifthStepRequest.class);

			if (requestObject != null) {
				setManagerId(requestObject.getPos());
				execute(sqlSession, requestObject);
				init(sqlSession, requestObject);
				
			}else {
				throw new ServiceException(Error.step_request_json);
			}
		} catch (Exception e) {
			sqlSession.rollback();
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), "", ""));
		} finally {sqlSession.close();}
	}

	/**
	 * Update data for pm regarding to step number and input request.
	 * @param pmId
	 * @param request
	 */
	private void execute(SqlSession sqlSession, FifthStepRequest request) {

		PaymentGatewayInfo gatewayInfo;
		PropertyManagerInfo managerInfo;
		Integer payment;

		if (!request.getPayment().isEmpty()) {
			
			payment = Integer.valueOf(request.getPayment());
			managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());
			
			if (managerInfo == null) {
				throw new ServiceException(Error.organization_info);
			}

			/* Funds holder:
				0 - Another payment gateway selected (PayPal, Authorize .NET),
				1 - Bookingpal selected to process payments, 
				0 - API(PMS support credit card processing). 
			*/
			
			/* Payment Processing Method:
				3 - API(PMS support credit card processing);
				2 - Email;
				1 - Payment gateway selected (BookingPal, PayPal, Authorize .NET).
			 */
			
			if (payment.equals(1)) { 			// Bookingpal payment gateway selected to process payments.

				PaymentGatewayProvider bookingpalPaymentGateway = sqlSession.getMapper(PaymentGatewayProviderMapper.class).readByName("BookingPal");
				
				if (bookingpalPaymentGateway != null){
					
					gatewayInfo  = new PaymentGatewayInfo("BookingPal", "", "", "", "");
					setManagerGateway(sqlSession, gatewayInfo, this.getPmId(), bookingpalPaymentGateway);
					
					managerInfo.setFundsHolder(FundsHolderEnum.BookingPal.value()); 				 
					managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.GATEWAY.type());
					
				}else {
					throw new ServiceException(Error.payment_gateway);
				}
			} else if (payment.equals(0)) { 	// Another payment gateway selected
				
				gatewayInfo = request.getPaymentGatewayInfo();
				
				if (gatewayInfo != null && !gatewayInfo.getId().isEmpty()) {
					
					Integer gatewayId = Integer.valueOf(gatewayInfo.getId());
					if (gatewayId != null) {
						PaymentGatewayProvider gatewayProvider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(gatewayId);

						if (gatewayProvider != null) {
							try{
								if (gatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)){
									DibsAuthentication dibsAuthentication = new DibsAuthentication();
									dibsAuthentication.setMerchantId(gatewayInfo.getAdditionalInfo());
									gatewayInfo.setAdditionalInfo(MarshallerHelper.toXML(dibsAuthentication));
								}else if (gatewayProvider.getName().equals(PaymentGatewayHolder.RENT)){
									RentProperty propertyCode = new RentProperty();
									propertyCode.setPropertyCode(gatewayInfo.getAdditionalInfo());
									gatewayInfo.setAdditionalInfo(MarshallerHelper.toXML(propertyCode));
								}
							}catch(Exception ex){								
								LOG.warn("Unable to marshal MerchantID.");
							}
							
							managerInfo.setFundsHolder(FundsHolderEnum.External.value());	// Another payment gateway selected (PayPal, Authorize .NET)
							managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.GATEWAY.type()); 

							setManagerGateway(sqlSession, gatewayInfo, this.getPmId(), gatewayProvider);
						}
					}
				}else {	throw new ServiceException(Error.payment_gateway_info); }
			} else if (payment.equals(2)) { /* None payment option selected (Notifications via email). */
				managerInfo.setFundsHolder(FundsHolderEnum.External.value()); 				 
				managerInfo.setPaymentProcessingType(PaymentProcessingTypeEnum.Mail.type()); 
			}
			
			if (request.getCreditCardTypes() != null){
				this.setCredirCardTypes(sqlSession, this.getPmId(), request.getCreditCardTypes());
			}

			managerInfo.setRegistrationStepId(request.getNextStep());
			sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);
			sqlSession.commit();
		}
	}

	/**
	 * Setting payment gateway provider for property manager. 
	 * @param sqlSession
	 * @param gatewayInfo
	 * @param managerId
	 * @param gatewayProvider
	 */
	private void setManagerGateway(SqlSession sqlSession, PaymentGatewayInfo gatewayInfo, Integer managerId, PaymentGatewayProvider gatewayProvider) {

		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(managerId);

		if (managerToGateway != null) {

			if (managerToGateway.getPaymentGatewayId() == gatewayProvider.getId()) {
				managerToGateway.setCreateDate(new Date());
				managerToGateway.setGatewayAccountId(gatewayInfo.getAccountId());
				managerToGateway.setGatewayCode(gatewayInfo.getTransactionKey());
				managerToGateway.setAdditionalInfo(gatewayInfo.getAdditionalInfo());

				sqlSession.getMapper(ManagerToGatewayMapper.class).update(managerToGateway);
			} else {

				sqlSession.getMapper(ManagerToGatewayMapper.class).delete(managerToGateway.getId());
			}
		}

		if (managerToGateway == null || managerToGateway.getPaymentGatewayId() != gatewayProvider.getId()) {
			managerToGateway = new ManagerToGateway();
			managerToGateway.setCreateDate(new Date());
			managerToGateway.setPaymentGatewayId(gatewayProvider.getId());
			managerToGateway.setSupplierId(managerId);
			managerToGateway.setGatewayAccountId(gatewayInfo.getAccountId());
			managerToGateway.setGatewayCode(gatewayInfo.getTransactionKey());
			managerToGateway.setAdditionalInfo(gatewayInfo.getAdditionalInfo());

			sqlSession.getMapper(ManagerToGatewayMapper.class).create(managerToGateway);
		}
	}

	/**
	 * Method update credit card types that property manager support to process payments.
	 * @param sqlSession
	 * @param managerId
	 * @param cardTypes
	 */
	private void setCredirCardTypes(SqlSession sqlSession, Integer managerId, CreditCardTypes cardTypes){
		
		if (managerId != null && cardTypes != null){
			
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).deletebypartyid(managerId);
			
			PropertyManagerSupportCC supportCC = new PropertyManagerSupportCC();
			supportCC.setPartyId(managerId);
			
			if (cardTypes.isNone()){
				supportCC.setNone(true);
			}else {				
				supportCC.setSupportMC(cardTypes.getMasterCard());
				supportCC.setSupportVISA(cardTypes.getVisa());
				supportCC.setSupportAE(cardTypes.getAmericanExpress());
				supportCC.setSupportDINERSCLUB(cardTypes.getDinersClub());
				supportCC.setSupportDISCOVER(cardTypes.getDiscover());
				supportCC.setSupportJCB(cardTypes.getJbc());				
			}			
			
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).create(supportCC);			
		}		
	}
	
	/**
	 * Initializing response data.
	 * @param request
	 */
	private void init(SqlSession sqlSession, FifthStepRequest request) {

		setStatus(new ApiResponse());
		setStepData(new AdditionalParams(request.getNextStep(), "", ""));
	}

	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

}
