package com.mybookingpal.rest.junit;

import static org.junit.Assert.*;
import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.shared.Location;

import org.junit.Test;

public class GoogleLocationTest {

	@Test
	public void testGetGoogleLocationForLatLong() {
		try {
			Location location = GoogleLocationProcessor.getGoogleLocation("195 Mountain Top Road, Chittenden, Vermont, USA");
			System.out.println("Lat/Log: " + location.getLatitude() + "/" + location.getLongitude());
		} catch (GoogleLocationLimitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
