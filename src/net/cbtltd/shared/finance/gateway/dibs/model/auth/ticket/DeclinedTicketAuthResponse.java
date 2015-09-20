package net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket;

public class DeclinedTicketAuthResponse extends TicketAuthResponse {
	private Integer reason;
	private String message;
	
	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
