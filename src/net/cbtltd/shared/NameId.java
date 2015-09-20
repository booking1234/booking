/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.shared.api.HasNameId;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NameId
implements HasNameId, IsSerializable {
	
	/**
	 * Rounds up a double or floating point value to a specified number of decimal places.
	 * If the number of decimal places is negative, round to the left of the decimal place
	 * (eg: round(12345.67, -2) returns 12300.00)
	 * 
	 * @param value the value to be rounded.
	 * @param places the number of decimal places to round to.
	 * @return value rounded to places decimal places.
	 */
	public static double ceil(double value, int places) {
		if (places < 0) {
			double factor = Math.pow(10, -places);
			value = Math.ceil(value/factor);
			return value * factor;
		}
		else {
			double factor = Math.pow(10,places);
			value = value * factor;
			return Math.ceil(value/factor);
		}
	}

	/**
	 * Rounds down a double or floating point value to a specified number of decimal places.
	 * If the number of decimal places is negative, round to the left of the decimal place
	 * (eg: round(12345.67, -2) returns 12300.00)
	 * 
	 * @param value the value to be rounded.
	 * @param places the number of decimal places to round to.
	 * @return value rounded to places decimal places.
	 */
	public static double floor(double value, int places) {
		if (places < 0) {
			double factor = Math.pow(10, -places);
			value = Math.floor(value/factor);
			return value * factor;
		}
		else {
			double factor = Math.pow(10,places);
			value = value * factor;
			return Math.floor(value/factor);
		}
	}

	/**
	 * Rounds a double or floating point value to a specified number of decimal places.
	 * If the number of decimal places is negative, round to the left of the decimal place
	 * (eg: round(12345.67, -2) returns 12300.00)
	 * 
	 * @param value the value to be rounded.
	 * @param places the number of decimal places to round to.
	 * @return value rounded to places decimal places.
	 */
	public static double round(double value, int places) {
		if (places < 0) {
			long factor = (long)Math.pow(10, 1-places);
			long tmp;
			if (value < 0){tmp = -Math.round(-value/factor);}
			else{tmp = Math.round(value/factor);}
			return (double)tmp * factor;
		}
		else {
			long factor = (long)Math.pow(10,places);
			value = value * factor;
			long tmp = Math.round(value);
			return (double)tmp / factor;
		}
	}

	public static double round(double value) {
		return round(value, 2);
	}

	public static float round(float value, int places) {
		return (float)round((double)value, places);
	}

	public static float round(float value) {
		return round(value, 2);
	}
	
	/**
	 * Gets an array of string values from the specified comma delimited string from the specified.
	 * 
	 * @param value the specified comma delimited string.
	 * @return the array of string values.
	 */
	public static ArrayList<String> getCdlist(String value) {
		if (value == null || value.isEmpty()) {return null;}
		value.replace("'", "");
		String[] items = value.split(",");
		ArrayList<String> values = new ArrayList<String>();
		for (String item : items) {values.add(item);}
		return values;
	}

	/**
	 * Gets a comma delimited string from the specified array of string values.
	 * 
	 * @param values the specified array of string values.
	 * @return the comma delimited string.
	 */
	public static String getCdlist(String[] values) {
		if (values == null || values.length == 0) {return EMPTY_LIST;}
		StringBuilder sb = new StringBuilder();
		for (String item : values) {sb.append("'" + item + "',");}
		if (sb.length() > 0) {sb.deleteCharAt(sb.length() - 1);}
		else {sb.append(EMPTY_LIST);}
		return sb.toString();
	}

	/**
	 * Gets a comma delimited string from the specified list of string values.
	 * 
	 * @param values the specified list of string values.
	 * @return the comma delimited string.
	 */
	public static String getCdlist(ArrayList<String> values) {
		if (values == null || values.isEmpty()) {return EMPTY_LIST;}
		StringBuilder sb = new StringBuilder();
		for (String item : values) {sb.append("'" + item + "',");}
		if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
		else {sb.append(EMPTY_LIST);}
		return sb.toString();
	}
	
	/**
	 * Gets a comma delimited string from the specified list of string values without quotes.
	 * This is needed for proper work of Mail recipients.
	 * 
	 * @param values the specified list of string values.
	 * @return the comma delimited string.
	 */
	public static String getCdlistWithoutQuotes(ArrayList<String> values) {
		if (values == null || values.isEmpty()) {return EMPTY_LIST;}
		StringBuilder sb = new StringBuilder();
		for (String item : values) {sb.append(item + ",");}
		if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
		else {sb.append(EMPTY_LIST);}
		return sb.toString();
	}

	/**
	 * Gets a comma delimited string from the specified map of string values.
	 * 
	 * @param map the specified map of string values.
	 * @return the comma delimited string.
	 */
	public static String getCdlist(HashMap<String, ArrayList<String>> map) {
		if (map == null || map.isEmpty()) {return EMPTY_LIST;}
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			for (String item : map.get(key)) {sb.append("'" + item + "',");}
		}
		if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
		else {sb.append(EMPTY_LIST);}
		return sb.toString();
	}

	/**
	 * Gets a list of name ID pairs from the specified name and id arrays.
	 * 
	 * @param names array of names or labels.
	 * @param ids array of internal representations or IDs.
	 * @return the list of name ID pairs.
	 */
	public static ArrayList<NameId> getList(String[] names, String[] ids) {
		ArrayList<NameId> nameids = new ArrayList<NameId>();
		for (int i = 0; i < ids.length; i++) {nameids.add(new NameId(names[i], ids[i]));}
		return nameids;
	}

	/**
	 * Gets a list of name ID pairs from the specified name and integer id arrays.
	 * 
	 * @param names array of names or labels.
	 * @param ids array of internal representations or IDs.
	 * @return the list of name ID pairs.
	 */
	public static ArrayList<NameId> getList(String[] names, int[] ids) {
		ArrayList<NameId> nameids = new ArrayList<NameId>();
		for (int i = 0; i < ids.length; i++) {nameids.add(new NameId(names[i], String.valueOf(ids[i])));}
		return nameids;
	}

	/**
	 * Gets a list of IDs from the specified list of name ID pairs.
	 * 
	 * @param nameids the specified list of name ID pairs.
	 * @return the list of IDs.
	 */
	public static final ArrayList<String> getIdList(ArrayList<NameId> nameids) {
		if (nameids == null) {return null;}
		ArrayList<String> ids = new ArrayList<String>();
		for (NameId nameid : nameids) {ids.add(nameid.getId());}
		return ids;
	}

	/**
	 * Gets a list of names from the specified list of name ID pairs.
	 * 
	 * @param nameids the specified list of name ID pairs.
	 * @return the list of names.
	 */
	public static final ArrayList<String> getNameList(ArrayList<NameId> nameids) {
		if (nameids == null) {return null;}
		ArrayList<String> names = new ArrayList<String>();
		for (NameId nameid : nameids) {names.add(nameid.getName());}
		return names;
	}
	
	/**
	 * @return text trimmed to length and/or str
	 * @param text to trim
	 * @param length in characters
	 */
	public static final String trim(String text, String str, int length){
		return trim(trim(text, str), length);
	}

	/**
	 * Gets the specified text trimmed to length.
	 * 
	 * @param text the specified text.
	 * @param length the trimmed length in characters.
	 * @return text trimmed to length.
	 */
	public static final String trim(String text, int length){
		if (text == null || length == 0) {return null;}
		else if (text.length() <= length){return text;}
		else {return text.substring(0, length-1);}
	}

	/**
	 * Gets the specified text trimmed to the end text.
	 * 
	 * @param text the specified text.
	 * @param end the end text.
	 * @return text trimmed to the end text.
	 */
	public static final String trim(String text, String end){
		if (text == null) {return null;}
		if (end == null || end.isEmpty()) {return text;}
		int endIndex = text.indexOf(end);
		if (endIndex < 0) return text;
		else return text.substring(0, endIndex);
	}

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String EMPTY_LIST = "'-1'";
	public enum Type {
		Account, 
		Asset, 
		Contract, 
		Country, 
		Currency, 
		Design,
		Event,
		Feature, 
		Finance, 
		Language,
		Lease,
		License,
		Location,
		Mandatory,
		MandatoryPerDay,
		Partner,
		Party, 
		Price, 
		Product, 
		Reservation, 
		Rule,
		Task,
		Tax,
		Yield
	}
	public static final String[] TYPES = { Type.Asset.name(), Type.Finance.name(), Type.Party.name(), Type.Product.name()};

	protected String name;
	protected String id;

	public NameId() {}

	public NameId(String id) {
		this.name = id;
		this.id = id;
	}

	public NameId(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public int compareTo(HasNameId nameid) {
		return getName().compareTo(nameid.getName());
	}

	@Description(value = "The name of an entity or number of a process", target = DocTarget.METHOD)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName(int length) {
		return NameId.trim(name, length);
	}

	public boolean noName() {
		return name == null || name.trim().isEmpty();
	}

	public boolean hasName() {
		return !noName();
	}

	public boolean hasName(String name) {
		return hasName() && getName().equals(name);
	}

	public boolean maxName(int length) {
		return name.length() > length;
	}

	@Description(value = "The unique ID of the entity or process", target = DocTarget.METHOD)
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public void setIds(ArrayList<String> ids) {
		id = NameId.getCdlist(ids);
	}

	public boolean noId() {
		return id == null || id.isEmpty() || id.equals(Model.ZERO);
	}

	public boolean hasId() {
		return !noId();
	}

	public boolean hasId(String id) {
		return id != null && this.id != null && this.id.equals(id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NameId [name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
