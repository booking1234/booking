package net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket;

public abstract class TicketAuthResponse {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
