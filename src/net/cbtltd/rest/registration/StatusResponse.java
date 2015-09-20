package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name = "data")
public class StatusResponse {

	private AdditionalParams stepData;
	private ApiResponse status;
	
	public StatusResponse() {
		super();
	}

	public StatusResponse(ApiResponse status, AdditionalParams stepParams)
	{
		this.setStatus(status);
		this.setStepData(stepParams);
	}
	
	@XmlElement(name = "status")
	public ApiResponse getStatus() {
		return status;
	}

	public void setStatus(ApiResponse status) {
		this.status = status;
	}

	@XmlElement(name = "step_data")
	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}
	
}
