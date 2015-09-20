package com.mybookingpal.server.test.mapper;

import java.util.ArrayList;

import net.cbtltd.server.api.AbstractMapper;
import net.cbtltd.shared.Location;

public interface LocationMapper extends AbstractMapper<Location> {
	
	ArrayList<String> getLocations(String locationName);
	String getLocationIdbyLocationName(String locationName);
}
