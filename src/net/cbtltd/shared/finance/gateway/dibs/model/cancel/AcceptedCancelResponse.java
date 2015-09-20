package net.cbtltd.shared.finance.gateway.dibs.model.cancel;

public class AcceptedCancelResponse extends CancelResponse {
	private String cardtype;
	private String transact;
	
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getTransact() {
		return transact;
	}

	public void setTransact(String transact) {
		this.transact = transact;
	}
}
