package com.mybookingpal.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.shared.Location;

public class LocationFinder {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws GoogleLocationLimitException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws IOException, NumberFormatException, GoogleLocationLimitException {
		File file = new File("//home//test//Desktop//locations.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		System.out.println("LineNum, Location_Name, State, County");
		int i = 0;
		while ((line = br.readLine()) != null) {
			i +=1;
			System.out.print(i + ",");
			if (line.equals("")) { 
				System.out.println(i + ",,,");
				continue; 
				
			}
			String[] splitLine = line.split(",");
			
			if(splitLine.length < 2 || splitLine.length > 2) { 
				System.out.println(i + ",,,");
				continue; 
				
			} 
			
			Location place = GoogleLocationProcessor.getGoogleLocation(Double.valueOf(splitLine[1]), Double.valueOf(splitLine[0]), "");
			
			if (place != null) {
			String state = place.getAdminarea_lvl_1() == null ? "" : place.getAdminarea_lvl_1();
			String county = place.getAdminarea_lvl_2() == null ? "" : place.getAdminarea_lvl_2();
			String locationName = place.getGname() == null ? "" : place.getGname();
			System.out.println(locationName + "," + state + "," + county);
			}
			else{
				System.out.println("Cannot find." + " Double.valueOf(splitLine[2]) "+Double.valueOf(splitLine[1]) + "Double.valueOf(splitLine[1]):"+ Double.valueOf(splitLine[0]) );  
			}
			}
			
		
			
		}

	}


