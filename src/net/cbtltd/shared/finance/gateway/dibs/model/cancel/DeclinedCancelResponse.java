package net.cbtltd.shared.finance.gateway.dibs.model.cancel;

public class DeclinedCancelResponse extends CancelResponse {
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
