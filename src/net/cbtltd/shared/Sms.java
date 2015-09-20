/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

public class Sms
implements HasResponse, HasService {

	private String mobilephone;
	private String sender;
	private String content;
	private int status;

	public Service service() {return Service.SMS;}

	public String getMobilephone() {
		return mobilephone;
	}


	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sms [mobilephone=");
		builder.append(mobilephone);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", content=");
		builder.append(content);
		builder.append(", status=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}
