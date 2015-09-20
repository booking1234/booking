package com.mybookingpal.scripts;

import java.util.ArrayList;

import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.shared.Location;

import org.apache.ibatis.session.SqlSession;

public class LocationEntryUpdater {

	public static void main(String args[])
	{
		SqlSession sqlSession = RazorServer.openSession();
		
		ArrayList<Location> unUpdatedEntries = sqlSession.getMapper(LocationMapper.class).getUnUpdatedEntries();
		
		for(Location unUpdatedEntry : unUpdatedEntries)
		{
			String name = unUpdatedEntry.getGname();
			String region = "";
			double latitude = unUpdatedEntry.getLatitude();
			double longitude = unUpdatedEntry.getLongitude();
			String country = unUpdatedEntry.getCountry();
			
			try{
				Location googleResult = GoogleLocationProcessor.getGoogleLocation(name, region, latitude, longitude, country);
				
				if(googleResult.getLocationtype().equalsIgnoreCase("locality"))
				{
					unUpdatedEntry.setLocationtype("locality");
					unUpdatedEntry.setParentID(Integer.parseInt(unUpdatedEntry.getId()));
					sqlSession.getMapper(LocationMapper.class).update(unUpdatedEntry);
				}
			}
			catch(GoogleLocationLimitException g)
			{
				g.printStackTrace();
			}
		}
	}
}
