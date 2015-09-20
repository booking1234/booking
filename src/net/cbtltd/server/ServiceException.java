/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.shared.Error;
/** 
 * The Class ServiceException records server exceptions that occur at run time.
 * System errors have negative constants, application errors have positive constant.
 */
public class ServiceException
extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	final Error error;
	String name = "";

	/**
	 * Instantiates a new service exception for the specified error.
	 *
	 * @param error the specified error.
	 */
	public ServiceException(Error error){
		this.error = error;
	}

	public ServiceException(Error error, String name){
		this.error = error;
		this.name = name;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error.
	 */
	public Error getError() {
		return error;
	}
	
	public String getDetailedMessage() {
		return error == null ? getDetailedMessage() : error.getDetailedMessage() + " " + name;
	}
	
	public String getMessage() {
		return error == null ? getMessage() : error.getMessage() + " " + name;
	}
}
