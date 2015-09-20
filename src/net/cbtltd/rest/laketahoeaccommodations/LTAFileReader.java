/**
 * @author David Lepe and Devin Held
 */

package net.cbtltd.rest.laketahoeaccommodations;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mybookingpal.utils.SFTP.SFTP;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

/* 
 * Lake Tahoe Accommodations File Reader
 * -------------------------------------
 * 
 * Lake Tahoe Accommodations provides three files that are periodically placed into 
 * the SFTP server specified below.
 * 
 * The three files are:
 * 	wduavl.idx - ASCII text file that indexes (zero-based index) wduavl.dat. Provides 
 * 		unit ID, number of bedrooms, number of bathrooms, maximum occupancy, property
 * 		amenities for each unit. Indexes in this file correspond to positions of each
 * 		unit in wduavl.dat
 * 
 * 	wduavl.dat - Binary file containing 965-byte fixed length records for each unit.
 * 		Provides unit ID, beginning date for availability data, the rates for each
 * 		property for each of the six rate periods, security deposit amount, cleaning
 * 		fee amount, tax rate, maximum occupancy, per-diem tax rate, and an array of
 * 		800 binary (not ASCII) bytes for the next 800 starting from the beginning
 * 		availability date that provide for each day: the rate period in effect for that
 *		date and the minimum stay requirement for that day. If the availability array
 *		has a value of 0 (null), that means that day is not available.
 * 
 * 	wdunit.dat - Comma delimited, ASCII format file containing unit ID's, number of
 *  	bedrooms, number of bathrooms, street addresses, property amenities for each
 *  	unit.
 *  
 *  Additionally, each file contains a 16 character timestamp at the bottom. If these
 *  are missing, the files should be considered to be corrupt. 
 *  If the time stamps between all three files are not consistent, the files should
 *  not be considered to be a pair.
 * 
 * Note: these three files provide some more additional information that was omitted
 * because some information is not necessary for BookingPal. For more information about
 * the specifics on each file, see the Google Drive folder for Lake Tahoe Accommodations.
 * 
 *  CHANGES
 *  -wduavl.idx not needed since all of the information pulled from it is provided in wdunit
 *  changes made to not parse wduavl.idx
 *  -changed storing of properties during parsing stage to HashMap from ArrayList to improve
 *  search times
 */



public class LTAFileReader {
	private String WDUAVL_DAT_PATH;
	private String WDUNIT_DAT_PATH;
	private static final String LTA_FILE_HOST = "silver.tahoe123.com";
	private static final String LTA_FILE_USERNAME = "mybkpal";
	private static final String LTA_FILE_PASSWORD = "MBP4ltares";
	private static final String SAVE_DIRECTORY = SystemUtils.IS_OS_LINUX ? "/root/laketahoefiles/" : "c:\\laketahoefiles\\"; //this should be using FTP site.
	private static final int LENGTH_OF_PROPERTY = 965; // every property in the binary file is 965 bytes long
	private static final int LENGTH_OF_EOF = 16;
	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");
	private static final Logger LOG = Logger.getLogger(LTAFileReader.class.getName());



	// Default constructor pulls files from the SFTP server
	// containing the LTA files
	LTAFileReader() throws JSchException, SftpException {
		LOG.debug("Downloading laketahoeaccommodations feed");
		
		new File(SAVE_DIRECTORY).mkdir(); // creates save directory if it does not exist already
		
		SFTP sftpClient = new SFTP(LTA_FILE_HOST, LTA_FILE_USERNAME, LTA_FILE_PASSWORD);
		
		List<String> files = new LinkedList<String>();
		files.add("wduavl.dat");
		files.add("wdunit.csv");
		sftpClient.retrieveFiles(files, SAVE_DIRECTORY);
		WDUNIT_DAT_PATH = SAVE_DIRECTORY + System.getProperty("file.separator") + "wdunit.csv";	
		WDUAVL_DAT_PATH = SAVE_DIRECTORY + System.getProperty("file.separator") + "wduavl.dat";
		
		LOG.debug("Done Downloading LTA files.");
	}


	// Constructor takes in the paths for each of the three files provided
	// by Lake Tahoe Accommodations. For debugging
	LTAFileReader(String dataFilePath, String unitInformationFilePath) {
		WDUNIT_DAT_PATH = unitInformationFilePath;
		WDUAVL_DAT_PATH = dataFilePath;
	}

	// Main entry point parsing LTA files
	// Goes through each of the two files. During each pass,
	// it populates the ArrayList of property objects with the information
	// each file provides.
	public HashMap<String, Property> parseLTAFiles() throws IOException, ParseException {
		HashMap<String, Property> properties = parseBinaryFile();
		return parseUnitDescriptionFile(properties);
	}

	public HashMap<String, Property> parseBinaryFile() throws IOException, ParseException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(WDUAVL_DAT_PATH));
		return readBinaryFile(in);
	}

	public HashMap<String, Property> parseUnitDescriptionFile(HashMap<String, Property> properties) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(WDUNIT_DAT_PATH)));
		String line = in.readLine();
		line = line.replaceAll("\"", "");
		
		while ((line = in.readLine()) != null && (!line.contains("eof"))) {
			line = line.replaceAll(",", "");
			String[] splitLine = line.split("\"\"");
			String unitID = splitLine[0].replaceAll("\"", "").replaceAll(" ", "");
			Property p = properties.get(unitID);
			if (p == null) {
				LOG.error("Property " + unitID + " was in hashmap  but not in unit description file");
				continue;
			}
			p.setBedrooms(Integer.parseInt(splitLine[2]));
			p.setBathrooms(Double.parseDouble(splitLine[3]));
			p.setSquareFeet(Integer.parseInt(splitLine[4]));
			p.setStreetAddress(splitLine[6]);
			p.setDescriptionCodes(Arrays.copyOfRange(splitLine, 7, 14));
			p.setMaxOccupancy(Integer.parseInt(splitLine[16]));
			p.setFireplaceDescription(splitLine[17]);
			p.setParkingDescription(splitLine[18]);
			p.setBarbequeType(splitLine[19]);
			p.setWasherDescription(splitLine[20]);
			p.setBedList(Arrays.copyOfRange(splitLine, 46, 52));
			p.setTvList(Arrays.copyOfRange(splitLine, 58, 64));
			p.setUnitDescription(splitLine[68]);
			p.setGuestComment(splitLine[69]);
			p.setLatitude((splitLine[91].contains(".")) ? Double.parseDouble(splitLine[91]) : 0.0); // This means that the latitude is not given
			p.setLongitude((splitLine[92].contains(".")) ? Double.parseDouble(splitLine[92].replaceAll("\"", "")) : 0.0); // This means that the longitude is not given
			
		}
		in.close();
		return properties;
	}

	private HashMap<String, Property> readBinaryFile(BufferedInputStream in) throws IOException, ParseException {
		HashMap<String, Property> properties = new HashMap<String, Property>();
		int numberOfBytesToRead = in.available() - LENGTH_OF_EOF; // 16 bytes subtracted off to account for the eof at the end		
		byte[] propertyBytes = new byte[numberOfBytesToRead];
		in.read(propertyBytes, 0, numberOfBytesToRead);	
		in.close();
		
		int position = 0; // current position in file
		while (position < numberOfBytesToRead) {
			byte[] property = Arrays.copyOfRange(propertyBytes, position, position+LENGTH_OF_PROPERTY);
			position += LENGTH_OF_PROPERTY;
			Property p = createProperty(property);
			properties.put(p.getUnitID(), p);
		}
		return properties;
	}

	private Property createProperty(byte[] property) throws ParseException {
		Property createdProperty = new Property();
		createdProperty.setUnitID(byteArrayToString(Arrays.copyOfRange(property, 1, 9)).replaceAll(" ", ""));
		createdProperty.setBeginningAvailibilityDate(byteArrayToString(Arrays.copyOfRange(property, 9, 17)));
		int[] rates = new int[6];
		for (int i = 0; i < 30; i+=5)
			rates[i/5] = byteArrayToInt(Arrays.copyOfRange(property, 51+i, 51+i+5)); // extract rates
		createdProperty.setSecurityDeposit(byteArrayToInt(Arrays.copyOfRange(property, 141, 146)));
		createdProperty.setCleaningFee(byteArrayToInt(Arrays.copyOfRange(property, 146, 151)));
		createdProperty.setTaxRate(byteArrayToDouble(Arrays.copyOfRange(property, 151, 156)));
		createdProperty.setTaxFlags(byteArrayToString(Arrays.copyOfRange(property, 156, 161)));
		createdProperty.setMaxOccupancy(byteArrayToInt(Arrays.copyOfRange(property, 161, 163)));
		createdProperty.setSplTax(property[164] / (double) 10); // per-diem tax rate, make sure to include in price calculation
		byte[] availability = Arrays.copyOfRange(property, 165, 715); // Only reads 18 months worth of data even though 800 days are given
		createdProperty.setAvailability(byteArrayToDayArray(availability, createdProperty.getBeginningAvailibilityDate(), rates));
		return createdProperty;
	}

	private static String byteArrayToString(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : byteArray) {
			sb.append((char) b);
		}
		return sb.toString();
	}

	private static int byteArrayToInt(byte[] byteArray) {
		return Integer.parseInt(byteArrayToString(byteArray).replaceAll(" ", ""));
	}

	private static double byteArrayToDouble(byte[] byteArray) {
		return Double.parseDouble(byteArrayToString(byteArray).replaceAll(" ", ""));
	}

	// Because availability information is stored as two 4-bit nibbles, bitwise operations are needed
	// low 4 bits are which rate table is in effect, high 4 bits are the minimum stay requirements
	private static Day[] byteArrayToDayArray(byte[] byteArray, String startDate, int[] rateTable) throws ParseException {
		Day[] result = new Day[byteArray.length];
		Day day;
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(DF.parse(startDate));
		for (int i = 0; i < byteArray.length; i++) {
			day = new Day();
			day.setDate(currentDate.getTime());
			day.setRateTable(byteArray[i] & (byte) 0x0F);
			day.setMinStay((byteArray[i] >> 4) & 0xF);
			day.setDayPrice(day.getMinStay() == 0 ? 0 : rateTable[day.getRateTable()-1]);
			result[i] = day;
			currentDate.add(Calendar.DATE, 1); // Increments the date by one day
		}
		return result;
	}
}
