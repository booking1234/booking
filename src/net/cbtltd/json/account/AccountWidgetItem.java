package net.cbtltd.json.account;

import net.cbtltd.json.JSONResponse;

public class AccountWidgetItem implements JSONResponse {

	private String id;
	private String name;
	private String subledger;
	private String type;
	private String notes;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubledger() {
		return subledger;
	}

	public void setSubledger(String subledger) {
		this.subledger = subledger;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountWidgetItem [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", subledger=");
		builder.append(subledger);
		builder.append(", type=");
		builder.append(type);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}