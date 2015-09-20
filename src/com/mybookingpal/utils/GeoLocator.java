package com.mybookingpal.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import net.cbtltd.rest.AbstractLocation;
import net.cbtltd.rest.Items;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameId;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class GeoLocator {
	
	private static final Logger LOG = Logger.getLogger(AbstractLocation.class.getName());

	public static void getPropertiesNearLocation(Double latitude, Double longitude,
			Double distance, String unit, String resultFilePath)
	{
		String message = "/location/list/" + latitude + "/" + longitude + "/" + distance + "/" + unit;
		LOG.debug(message);
		
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if (latitude < -90.0 || latitude > 90.0) {throw new ServiceException(Error.position_latitude, "Latitude " + latitude);}
			if (longitude < -180.0 || longitude > 180.0) {throw new ServiceException(Error.position_longitude, "Longitude " + longitude);}
			Area action = new Area(latitude, longitude, distance, unit);
			Collection<NameId> items = sqlSession.getMapper(LocationMapper.class).nameidnearby(action);
			if (items == null) {throw new ServiceException(Error.position_nearby, action.toString());}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(resultFilePath)));
			
//			bw.write("Properties within a radius of " + distance + " " + unit + " from location " + latitude + " latitude " 
//					+ longitude + "longitude\n");
			for(NameId item : items)
			{
				bw.write(item.getId() + ", " + item.getName());
				bw.write("\n");
			}
			
			bw.close();
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			LOG.error("Problem with file " + resultFilePath);
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
		}
		finally {sqlSession.close();}
	}
	
	public static void findPropertiesNearListOfLocations(String inputFilePath)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFilePath)));
			
			br.readLine();
			String line;
			
			while((line = br.readLine()) != null)
			{
				String[] tokens = line.split(",");
				
				if(tokens.length == 5)
				{
					String outputFileName = "/home/test/Desktop/BoundingBox/" + tokens[2].replace("/", "") + "_" + tokens[3] + ".csv";
					
					try
					{
						getPropertiesNearLocation(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), 5.0, "KMT",
							outputFileName);
					}
					catch (NumberFormatException e)
					{
						LOG.error("Problem with input " + tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
					}
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getNumberOfPropertiesInlocation(Double latitude, Double longitude,
			Double distance, String unit)
	{
		String message = "/location/list/" + latitude + "/" + longitude + "/" + distance + "/" + unit;
		LOG.debug(message);
		
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if (latitude < -90.0 || latitude > 90.0) {throw new ServiceException(Error.position_latitude, "Latitude " + latitude);}
			if (longitude < -180.0 || longitude > 180.0) {throw new ServiceException(Error.position_longitude, "Longitude " + longitude);}
			Area action = new Area(latitude, longitude, distance, unit);
			Collection<NameId> items = sqlSession.getMapper(LocationMapper.class).nameidnearby(action);
			if (items == null) {throw new ServiceException(Error.position_nearby, action.toString());}
			
			return items.size();
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
		}
		finally {sqlSession.close();}
		
		return -1;
	}
	
	public static void findNumberOfPropertiesNearLocation(String inputFilePath)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFilePath)));
			
			br.readLine();
			String line;
			String outputFileName = "/home/test/Desktop/geoLocatorResults.csv";
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFileName)));
			
			while((line = br.readLine()) != null)
			{
				String[] tokens = line.split(",");
				
				if(tokens.length == 5)
				{	
					try
					{
						int numOfProperties = getNumberOfPropertiesInlocation(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]),
								10.0, "KMT");
						
						bw.write(tokens[2].replace("/", "") + ", " + tokens[3] + ", " + numOfProperties + "\n");
					}
					catch (NumberFormatException e)
					{
						LOG.error("Problem with input " + tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
					}
				}
			}
			
			bw.close();
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		/*input file should have format with 5 columns - latitude, longitude, Area, Country, URL*/
		findNumberOfPropertiesNearLocation("/home/test/Desktop/SkiResortGeolocation.csv");
//		findPropertiesNearListOfLocations("/home/test/Desktop/SkiResortGeolocation.csv");
//		getPropertiesNearLocation(37.831316, -122.285247, 5.0, "KMT","/home/test/Desktop/locator.txt");
	}
}
