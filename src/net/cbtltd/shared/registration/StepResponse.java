package net.cbtltd.shared.registration;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;

public class StepResponse {

	public static final String DEFAULT_PRODUCTS_COUNT_PER_PAGE = "20";
	
	public StepResponse() {
		super();
		setStatus(new ApiResponse());
	}
	
	/**
	 * Check if property manager encrypted id is correct
	 * @param pos
	 */
	protected void setManagerId(String pos){
		
		this.setPmId(RegistrationUtils.getInteger(Model.decrypt(pos)));
		
		if (this.getPmId() == null) {
			throw new ServiceException(Error.organization_id, "Empty Pos value!");
		}
	}
	
	private ApiResponse status;
	
	private Integer pmId;
	
	public ApiResponse getStatus() {
		return status;
	}

	public void setStatus(ApiResponse status) {
		this.status = status;
	}

	public Integer getPmId() {
		return pmId;
	}

	public void setPmId(Integer pmId) {
		this.pmId = pmId;
	}
}

