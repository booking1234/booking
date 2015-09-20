package com.mybookingpal.exceptions;

public class ImageConversionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 62329204795355426L;
	
	public ImageConversionException(String message) {
		super(message);
	}
	public ImageConversionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
