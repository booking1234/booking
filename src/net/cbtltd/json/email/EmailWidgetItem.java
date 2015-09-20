/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.email;

import net.cbtltd.json.JSONResponse;

public class EmailWidgetItem implements JSONResponse {
	private String familyname;
	private String firstname;
	private String agerange; // OTA AQC tag
	private String message;

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAgerange() {
		return agerange;
	}

	public void setAgerange(String agerange) {
		this.agerange = agerange;
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
		builder.append("EmailWidgetItem [familyname=");
		builder.append(familyname);
		builder.append(", firstname=");
		builder.append(firstname);
		builder.append(", agerange=");
		builder.append(agerange);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
