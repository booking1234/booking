package net.cbtltd.shared.registration;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

@XmlRootElement(name = "data")
public class SeventhStepResponse extends StepResponse implements RegistrationResponse {

	private AdditionalParams stepData;
	@XmlTransient
	private Gson GSON = new Gson();

	public SeventhStepResponse() {
		super();
	}

	public SeventhStepResponse(String request) {
		super();
		SeventhStepRequest requestObject = null;
		try {

			requestObject = GSON.fromJson(request, SeventhStepRequest.class);

			if (requestObject != null) {
				setManagerId(requestObject.getPos());
				execute(requestObject);
				init(requestObject);
			}else {
				throw new ServiceException(Error.step_request_json);
			}
			
		} catch (Exception e) {
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), "", ""));
		} 
	}

	/**
	 * Update data for pm regarding to step number and input request.
	 * @param pmId
	 * @param request
	 */
	private void execute(SeventhStepRequest requestObject) throws Exception {

		String amount = requestObject.getAmount();

		if (amount == null || amount.isEmpty())
			throw new Exception("The amount field is blank.");

		SqlSession sqlSession = RazorServer.openSession();
		
		PaymentMethod managerPaymentMethod = sqlSession.getMapper(PaymentMethodMapper.class).read_by_pm(this.getPmId());
		
		Party action = new Party();
		action.setId(this.getPmId().toString());

		if (managerPaymentMethod != null && managerPaymentMethod.getAmount() != null){
			
			PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());
			if (managerInfo == null){ throw new ServiceException(Error.organization_info);}
			if (amount.equals(managerPaymentMethod.getAmount().toString())) {
				managerPaymentMethod.setVerifiedDate(new Date());
				action.setState(Party.CREATED);
				managerInfo.setRegistrationStepId(RegistrationConstants.FINAL_STEP);
				
				sqlSession.getMapper(PaymentMethodMapper.class).update(managerPaymentMethod);
				sqlSession.getMapper(PropertyManagerInfoMapper.class).update(managerInfo);
				sqlSession.getMapper(PartyMapper.class).update(action);
			}else {
				throw new ServiceException(Error.card_amount);
			}			
		}else {
			throw new ServiceException(Error.payment_method_unsupported);
		}
		
		sqlSession.commit();
			
	}

	/**
	 * Initializing response data.
	 * @param request
	 */
	private void init(SeventhStepRequest requestObject) {

		setStepData(new AdditionalParams(RegistrationConstants.FINAL_STEP, "", ""));
		setStatus(new ApiResponse());
	}

	@XmlElement(name = "step_data")
	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}

}
