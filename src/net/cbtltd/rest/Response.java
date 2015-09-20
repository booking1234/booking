/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;

@XmlRootElement(name = "response")
public class Response {
	private String request;
	private String message;
	private String xsl;

	public Response() {}
	
	public Response(String request, String message, String xsl) {
		super();
		this.request = request;
		this.message = message;
		this.xsl = xsl;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}
