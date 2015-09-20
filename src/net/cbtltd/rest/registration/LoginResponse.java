package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import net.cbtltd.rest.error.ApiResponse;

import org.apache.log4j.Logger;

@XmlRootElement(name="data")
public class LoginResponse {
	
	private static final Logger LOG = Logger.getLogger(LoginResponse.class.getName());
	
	@XmlElement(name = "step_data")
	private AdditionalParams stepData;
	private ApiResponse status;	
	
	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginResponse(AdditionalParams stepData, ApiResponse status) {
		super();
		this.stepData = stepData;
		this.status = status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [nextStep=");
		builder.append(stepData.getNextStep());
		builder.append(", pmsName=");
		builder.append(stepData.getPmsName());
		builder.append(", pos=");
		builder.append(stepData.getPos());		
		builder.append("]");
		return builder.toString();
	}
		
}
