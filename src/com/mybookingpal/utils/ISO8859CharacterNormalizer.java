package com.mybookingpal.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class converts/decodes subset of ISO 8859 alpha characters. This maps has 198 characters. It should eventually cover 
 * all the characters from ISO 8859 standard.
 *
 * We will create reverse maps for Hex, Decimal and HTML Entity for eventual encoding method.
 *
 * @author cshah
 * @version @version@
 * Mar 20, 2014
 */
public class ISO8859CharacterNormalizer {
	
	public static final Map<String, String> HEX_TO_CHAR_MAP		= new HashMap<String, String>();
	public static final Map<String, String> DECIMAL_TO_CHAR_MAP = new HashMap<String, String>();
	
	public static final String DESCRIPTION						= "Description";
	public static final String CHARACTER						= "Character";
	public static final String HTML_ENTITY						= "HTML Entity";
	public static final String DECIMAL							= "Decimal";
	public static final String HEX								= "Hex";
	
	public static final Pattern HEX_PATTERN 					= Pattern.compile("&#x(\\d+);");
	public static final Pattern DECIMAL_PATTERN					= Pattern.compile("&#(\\d+);");
	
	
	private static final Logger logger = Logger.getLogger(ISO8859CharacterNormalizer.class);
	
	static {
		String fileName = "characters_mapping.csv";
		Reader in =  new InputStreamReader(ISO8859CharacterNormalizer.class.getClassLoader().getResourceAsStream(fileName));
		try {
			CSVParser parser = CSVFormat.DEFAULT.withHeader().withDelimiter('|').parse(in);
			for(CSVRecord record:parser.getRecords()) {
				HEX_TO_CHAR_MAP.put(record.get("Hex"), record.get("Character"));
				DECIMAL_TO_CHAR_MAP.put(record.get("Decimal"), record.get("Character"));
			}
		} catch (IOException e) {
			logger.error("The mapping file is not <" + fileName + "> available.");
		}
		
	}
	
	/**
	 * This decodes character represented in decimal or hex code.
	 * @param input
	 * @return
	 */
	public static String decode(String input) {
		if(StringUtils.isBlank(input)) {
			return input;
		}
		String result = input;
		// hex conversion
		result = input.replaceAll("&amp;#x", "&#x");
		Matcher m = HEX_PATTERN.matcher(result);
		while(m.find()) {
			result = result.replace(m.group(0), HEX_TO_CHAR_MAP.get(m.group(0)));
		}
		
		// decimal conversion
		result = result.replaceAll("&amp;#", "&#");
		m = DECIMAL_PATTERN.matcher(result);
		while(m.find()) {
			result = result.replace(m.group(0), DECIMAL_TO_CHAR_MAP.get(m.group(0)));
		}
		
		return result;
	}
	
	public static boolean containsHexOrDecCode(String input) {
		return (input.contains("&amp;#x") || input.contains("&#x") || input.contains("&amp;#") || input.contains("&#"));
	}
	
	public static void main(String[] args) {
//		String entry = "L&amp;#248;gst&amp;#248;r";
//		String entry = "Løgstør";
//		String entry = "Fa&#158;ana";
//		String entry = "Murviel-l&#232;s-B&#233;ziers";
		String entry = "Gile&amp;#154;i&amp;#263;i";
//		entry = entry.replaceAll("&amp;#", "&#");
//		Matcher matcher = decimalPattern.matcher(entry);
//		while(matcher.find()) {
//			System.out.println("Match: " + matcher);
//			entry = entry.replace(matcher.group(0), DECIMAL_TO_CHAR_MAP.get(matcher.group(0)));
//			
//		}
		logger.error("Decoded string: " + decode(entry));
	}
	
	
	
	
	

}
