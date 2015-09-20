package net.cbtltd.json.booknew;

import net.cbtltd.json.JSONResponse;

public class ReservationPaymentWidgetItem implements JSONResponse {

	private String message;
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

}
