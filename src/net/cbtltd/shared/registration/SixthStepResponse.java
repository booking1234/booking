package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentMethodMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentMethod;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SixthStepResponse extends StepResponse implements RegistrationResponse {

	public SixthStepResponse() {
		super();
	}
	
	private AdditionalParams stepData;
	private Gson GSON = new Gson();

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}

	public SixthStepResponse(String request) {
		super();
		
		SixthStepRequest requestObject = null;
		SqlSession sqlSession = RazorServer.openSession();

		try {

			requestObject = GSON.fromJson(request, SixthStepRequest.class);

			if (requestObject != null) {

				setManagerId(requestObject.getPos());
				execute(requestObject, sqlSession);
				init(requestObject, sqlSession);
			}else {
				throw new ServiceException(Error.step_request_json);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), "", ""));
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * Update data for pm regarding to step number and input request.
	 * @param pmId
	 * @param request
	 */
	private void execute(SixthStepRequest requestObject, SqlSession sqlSession) {

		PaymentMethod paymentMethod = null;
		Integer paymentTypeValue = -1;

		if (requestObject.getManagerPaymentType() != null) {

			/* PaymentType: receiving payment from BookingPal
				1 - Mail Checks;
				2 - PayPal;
				3 - ACH/Direct deposit.
			 */
			
			String paymentType = requestObject.getManagerPaymentType();

			if (!paymentType.isEmpty()) {

				paymentTypeValue = Integer.valueOf(paymentType);

				paymentMethod = sqlSession.getMapper(PaymentMethodMapper.class).read_by_pm(this.getPmId());

				if (paymentMethod != null) {
					if (paymentMethod.getType().equals(paymentType)) {

						if (paymentTypeValue == 3) {
							// ACH/Direct deposit.
							paymentMethod.setPaymentInfo(GSON.toJson(requestObject.getManagerACH()));
						} else if (paymentTypeValue == 2) {
							// PayPal.
							paymentMethod.setPaymentInfo(requestObject.getManagerPaypal());
						}

						sqlSession.getMapper(PaymentMethodMapper.class).update(paymentMethod);
					} else {
						sqlSession.getMapper(PaymentMethodMapper.class).delete(paymentMethod.getId());
					}
				}

				if (paymentMethod == null || !paymentMethod.getType().equals(paymentType)) {

					paymentMethod = new PaymentMethod();

					paymentMethod.setPmID(this.getPmId());
					paymentMethod.setType(paymentType);

					if (paymentTypeValue == 3) {
						// ACH/Direct deposit.
						paymentMethod.setPaymentInfo(GSON.toJson(requestObject.getManagerACH()));
					} else if (paymentTypeValue == 2) {
						// PayPal.
						paymentMethod.setPaymentInfo(requestObject.getManagerPaypal());
					}

					sqlSession.getMapper(PaymentMethodMapper.class).create(paymentMethod);
				}
			}
		}
		
		PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());
		if (managerInfo != null){
			Boolean supportCreditCard = false;
			Boolean isFinalStep = false;
			Boolean paymentProcessingByEmail = false;
			if (managerInfo.getFundsHolder() == null ||managerInfo.getPaymentProcessingType() == null ){
				throw new ServiceException(Error.payment_type_unsupported);
			}
			
			/* Funds holder:
				0 - Another payment gateway selected (PayPal, Authorize .NET, ...) OR API (PMS support credit card processing);
				1 - BookingPal selected to process payments.
			*/	
		
			/* Payment Processing Type:
				3 - API(PMS support credit card processing);
				2 - Email;
				1 - Payment gateway selected (BookingPal, PayPal, Authorize .NET).
			*/
			
			Party action = new Party();
			action.setId(this.getPmId().toString());
			
			/* If funds holder isn't BookingPal then Final step. */
			if (managerInfo.getFundsHolder() == FundsHolderEnum.External.value()){
				isFinalStep= true;

				/* DEPREACATED: if PMS support credit card processing */
				/*if (managerInfo.getPaymentProcessingType() == PaymentProcessingTypeEnum.API.type()){
					supportCreditCard = true;
				}
				if (managerInfo.getPaymentProcessingType() == PaymentProcessingTypeEnum.Mail.type()){  
					paymentProcessingByEmail = true;
				}*/
			}
			
			/* DEPREACATED: If paymentTypeValue is 'Mail Checks' or PMS support credit card or payment processing method is 'Email' then final step.  
			if (paymentTypeValue == 1 || supportCreditCard || paymentProcessingByEmail){*/
			if (paymentTypeValue == 1 || isFinalStep){
				managerInfo.setRegistrationStepId(RegistrationConstants.FINAL_STEP);
				action.setState(Party.CREATED);
				setStepData(new AdditionalParams(RegistrationConstants.FINAL_STEP, "", ""));
				sqlSession.getMapper(PartyMapper.class).update(action);
			}else {
				managerInfo.setRegistrationStepId(requestObject.getNextStep());
				setStepData(new AdditionalParams(requestObject.getNextStep(), "", ""));
			}
			sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);			
		}
		sqlSession.commit();
	}

	/**
	 * Initializing response data.
	 * @param request
	 */
	private void init(SixthStepRequest requestObject, SqlSession sqlSession) {
		setStatus(new ApiResponse());
	}

	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

}
