/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

/**
 * The Class OtaException is thrown if an error occurs during an OTA request.
 * @see net.cbtltd.soap.ota.OtaService
 */

public class OtaException extends RuntimeException {
	
	private final String code;
	private final String text;
	private final String type;

	/**
	 * Instantiates a new OTA exception.
	 * 
	 * @param code the identity of the exception.
	 * @param text the description of the exception.
	 * @param type the type of the exception.
	 */
	public OtaException(String code, String text, String type) {
		this.code = code;
		this.text = text;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

}