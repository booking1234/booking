/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

public class Mail
implements HasResponse, HasService {

	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";  
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	
	private String subject;
	private String content;
	private String recipients;
	private String contentType;
	private int status;	
	private static final String SENDER = "noreply@mybookingpal.com";
	
	public Mail() {
		super();
		contentType =  Mail.CONTENT_TYPE_TEXT_PLAIN;
	}

	public Service service() {return Service.MAIL;}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return SENDER;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public void setRecipients(ArrayList<String> recipients) {
		this.recipients = NameId.getCdlistWithoutQuotes(recipients);
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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
		builder.append("Mail [subject=");
		builder.append(subject);
		builder.append(", content=");
		builder.append(content);
		builder.append(", sender=");
		builder.append(getSender());
		builder.append(", recipients=");
		builder.append(recipients);
		builder.append(", status=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}
