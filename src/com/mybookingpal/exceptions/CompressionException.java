package com.mybookingpal.exceptions;

public class CompressionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 62329204795355426L;
	
	public CompressionException(String message) {
		super(message);
	}
	public CompressionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
