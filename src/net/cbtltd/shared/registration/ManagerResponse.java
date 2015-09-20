package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerResponse  {

	private ApiResponse status;
	
	public ManagerResponse() {
	}
		
	public ManagerResponse(ApiResponse status) {
		super();
		this.status = status;
	}

	public ApiResponse getStatus() {
		return status;
	}

	public void setStatus(ApiResponse status) {
		this.status = status;
	}

}
