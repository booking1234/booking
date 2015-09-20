package com.mybookingpal.server;

import org.apache.ibatis.session.SqlSession;

public interface Uploadable {

	//List of methods used for uploading process
	void uploadProperties();
	void uploadPrices();
	void uploadReservations();
	void uploadAvailability();
	
	/*
	 * possible outer methods for single use calls:
	 * 
	 * PropertyObject getProperty(String productId); - ask Isaac about product objects which are already available and used for FTP files creation.
	 * List<Price> getPrices(String productId); - should build list available and actual prices for current property
	 * 
	 */
	
}
