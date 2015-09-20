package com.mybookingpal.feed.service;

import java.util.ArrayList;

import net.cbtltd.rest.AbstractLocation;
import net.cbtltd.rest.Countries;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Location;

public class LocationFeedGenerator extends AbstractLocation {
	
	private static LocationFeedGenerator locationfeedgenerator;
	
	private LocationFeedGenerator() {
	} 
	
	public static LocationFeedGenerator getInstance() {
		if(locationfeedgenerator == null) {
			locationfeedgenerator = new LocationFeedGenerator();
		}
		return locationfeedgenerator;
	}
	
	public Countries generateCountryNamesAndCode(){
		return getCountryNameIds(null);
	}
	
	public ArrayList<Location>  generateActiveLocations(){
		return RazorServer.openSession().getMapper(LocationMapper.class).activeLocations();
	}
	
}
