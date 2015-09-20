package net.cbtltd.shared.finance.gateway.dibs.model.cancel;

public abstract class CancelResponse {
	private String result;
	private String status;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
