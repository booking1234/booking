package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="pending_payment")
public class PendingPaymentResponse extends ApiResponse {
	private boolean successful;

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
}
