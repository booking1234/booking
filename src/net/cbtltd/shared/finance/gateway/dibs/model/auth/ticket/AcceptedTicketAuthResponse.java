package net.cbtltd.shared.finance.gateway.dibs.model.auth.ticket;

public class AcceptedTicketAuthResponse extends TicketAuthResponse {
	private String approvalcode;
	private String authkey;
	private String cardtype;
	private String fee;
	private String transact;

	public String getApprovalcode() {
		return approvalcode;
	}

	public void setApprovalcode(String approvalcode) {
		this.approvalcode = approvalcode;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTransact() {
		return transact;
	}

	public void setTransact(String transact) {
		this.transact = transact;
	}
}
