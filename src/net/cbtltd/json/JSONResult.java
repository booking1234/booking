package net.cbtltd.json;

public class JSONResult implements JSONResponse {
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JSONResult [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
