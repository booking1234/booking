package net.cbtltd.shared.finance.gateway.dibs.model.capture;

public abstract class CaptureResponse {
	private Integer result;
	private String status;
	private String message;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
