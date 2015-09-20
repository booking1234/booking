/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.client.secure.Secure;
import net.cbtltd.client.secure.crypto.CryptoException;
import net.cbtltd.shared.api.HasAttributes;
import net.cbtltd.shared.api.HasData;
import net.cbtltd.shared.api.HasImages;
import net.cbtltd.shared.api.HasServiceResponse;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.api.HasTexts;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * The Class Model.
 */
public abstract class Model
extends NameId
implements HasData, HasImages, HasState, HasTexts, HasAttributes, HasServiceResponse {

	/**
	 * Gets the duration in days from from one date to another.
	 * @param fromdate the from date.
	 * @param todate the to date.
	 * @return duration from from date to date.
	 */
	public static Integer getDuration(Date fromdate, Date todate) {
		return Time.getDuration(fromdate, todate, Time.DAY).intValue();
	}

	/**
	 * Gets the duration in days from from one date to another.
	 * @param fromdate the from date.
	 * @param todate the to date.
	 * @return duration from from date to date.
	 */
	public static boolean invalidDateRange(Date fromdate, Date todate){
		return fromdate == null | todate == null || fromdate.after(todate);
	}

	/*
	 * Gets a single string from the specified string list.
	 * @param items the specified string list
	 * @return the single string.
	 */
	private static final String getStringlist(ArrayList<String> items) {
		if (items == null) {return null;}
		if (items.isEmpty()) {return Model.BLANK;}
		StringBuilder sb = new StringBuilder();
		for (String item : items) {sb.append(item);}
		return sb.toString();
	}

	/**
	 * Encrypts the specified text.
	 * 
	 * @param plaintext the specified text.
	 * @return the encrypted value.
	 */
	public static String encrypt(String plaintext){
		if (plaintext == null || plaintext.trim().isEmpty()) {return plaintext;}
		
		if (ENCRYPTED.containsKey(plaintext)) {return ENCRYPTED.get(plaintext);}
		
		ArrayList<String>inputs = new ArrayList<String>();
		for (int i = 0; i < plaintext.length(); i += 7) {
			int j = i+7;
			if (j < plaintext.length()) inputs.add(plaintext.substring(i,j));
			else {inputs.add(plaintext.substring(i,plaintext.length()));}
		}
		String ciphertext = getStringlist(encryptList(inputs));
		ENCRYPTED.put(plaintext, ciphertext);
		return ciphertext;
	}

	/**
	 * Decrypts the specified encrypted text.
	 * 
	 * @param ciphertext the specified text.
	 * @return the encrypted value.
	 */
	public static String decrypt(String ciphertext) {
		if (ciphertext == null || ciphertext.trim().isEmpty()) {return ciphertext;}
		
		if (DECRYPTED.containsKey(ciphertext)) {return DECRYPTED.get(ciphertext);}
		
		ArrayList<String>inputs = new ArrayList<String>();
		for (int i = 0; i < ciphertext.length(); i += 16){
			int j = i+16;
			if (j < ciphertext.length()) inputs.add(ciphertext.substring(i,j));
			else {inputs.add(ciphertext.substring(i,ciphertext.length()));}
		}
		String plaintext = getStringlist(decryptList(inputs));
		DECRYPTED.put(ciphertext, plaintext);
		return plaintext;
	}

	/**
	 * Encrypts the specified list of text items.
	 * 
	 * @param list the specified list of text items.
	 * @return the encrypted values.
	 */
	public static ArrayList<String> encryptList(ArrayList<String> list){
		try {
			if (list == null || list.size() == 0) {return list;}
			Secure de = Secure.getInstance();
			ArrayList<String> outputs = new ArrayList<String>();
			for (String value : list){outputs.add(de.encrypt(value));}
			return outputs;
		}
		catch (CryptoException e){return null;}
	}

	/**
	 * Decrypts the specified list of encrypted text items.
	 * 
	 * @param list the specified list of text items.
	 * @return the decrypted values.
	 */
	public static ArrayList<String> decryptList(ArrayList<String> list){
		try {
			if (list == null || list.size() == 0){return list;}
			Secure de = Secure.getInstance();
			ArrayList<String> outputs = new ArrayList<String>();
			for (String input : list){outputs.add(de.decrypt(input));}
			return outputs;
		}
		catch (CryptoException e) {return null;}
	}

	/**
	 * Transforms the specified hash map of values to an array of key:value pairs.
	 *
	 * @param the specified hash map of values.
	 * @return the array of key:value pairs.
	 */
	public static final ArrayList<String> getValues(HashMap<String, String> values) {
		if (values == null) {return null;}
		ArrayList<String> keyvalues = new ArrayList<String>();
		for (Entry<String, String> entry : values.entrySet()) {keyvalues.add(entry.getKey() + DELIMITER + entry.getValue());}
		return keyvalues;
	}

	/**
	 * Gets the name id action.
	 *
	 * @param name the name
	 * @return enumerated service for the name
	 */
	public static final NameIdAction getNameIdAction(String name) {
		return new NameIdAction(Enum.valueOf(Service.class, name.toUpperCase()));
	}
	
	/**
	 * Checks if the value is true.
	 *
	 * @param value the value.
	 * @return true, if the value is true.
	 */
	public static final boolean isTrue (String value) {
		return TRUE.equalsIgnoreCase(value);
	}
	
	public static final String BLANK = "";
	public static final String ZERO = "0";
	public static final String TRUE = "1";
	public static final String DELIMITER = ":";
	public static final String STATE = "state";
	public static final String UNKNOWN = "Unknown";
	private static final HashMap<String, String> DECRYPTED = new HashMap<String, String>();
	private static final HashMap<String, String> ENCRYPTED = new HashMap<String, String>();
	
	protected String organizationid;
	protected String actorid;
	protected String altpartyid;
	protected String altid;
	@XStreamOmitField
	protected String state;
	protected String options;
	
	@XStreamOmitField
	protected int status = 0;
	
	private Date version; //latest change
	protected HashMap<String, String> values;
	protected ArrayList<KeyValue> keyvalues;
	protected HashMap<String, ArrayList<String>> attributemap;
	protected HashMap<String, Text> texts;
	protected ArrayList<String> imageurls;

	/**
	 * Instantiates a new model.
	 */
	public Model() {}
	
 	/* (non-Javadoc)
	  * @see net.cbtltd.shared.api.HasResponse#getStatus()
	  */
	@XmlTransient
	public final int getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasResponse#setStatus(int)
	 */
	public final void setStatus(int status) {
		this.status = status;
	}

	/**
	 * No status.
	 *
	 * @return true, if successful
	 */
	public final boolean noStatus() {
		return status == 0;
	}

	/**
	 * Gets the organizationid.
	 *
	 * @return the organizationid
	 */
	@XmlTransient
	public final String getOrganizationid() {
		return organizationid;
	}

	/**
	 * Sets the organizationid.
	 *
	 * @param organizationid the new organizationid
	 */
	public final void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	/**
	 * No organizationid.
	 *
	 * @return true, if successful
	 */
	public final boolean noOrganizationid() {
		return organizationid == null || organizationid.isEmpty() || organizationid.equalsIgnoreCase(Model.ZERO);
	}

	/**
	 * Checks for organizationid.
	 *
	 * @return true, if successful
	 */
	public final boolean hasOrganizationid() {
		return !noOrganizationid();
	}

	/**
	 * Checks for organizationid.
	 *
	 * @param organizationid the organizationid
	 * @return true, if successful
	 */
	public final boolean hasOrganizationid(String organizationid) {
		return this.organizationid != null && this.organizationid.equals(organizationid);
	}

    public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	@Description(value = "Identifies an alternative party, which, if not null, means the entity or process ID is that defined by the alternative party. ", target = DocTarget.METHOD)
	public String getAltpartyid() {
		return altpartyid;
	}

	public void setAltpartyid(String altpartyid) {
		this.altpartyid = altpartyid;
	}

	public final boolean noAltpartyid() {
		return this.altpartyid == null || altpartyid.isEmpty();
	}

	public final boolean hasAltpartyid() {
		return !noAltpartyid();
	}
	
	/**
	 * Checks for the alternative party ID.
	 *
	 * @param altpartyid the alternate party ID.
	 * @return true, if successful
	 */
	public final boolean hasAltpartyid(String altpartyid) {
		return this.altpartyid != null && altpartyid != null && this.altpartyid.equalsIgnoreCase(altpartyid);
	}

	/**
	 * Not the alternative party ID.
	 *
	 * @param altpartyid the alternative party ID.
	 * @return true, if successful
	 */
	public final boolean notAltpartyid(String altpartyid) {
		return !hasAltpartyid(altpartyid);
	}

	public String getAltid() {
		return altid;
	}

	public void setAltid(String altid) {
		this.altid = altid;
	}

	public boolean noAltid() {
		return altid == null || altid.isEmpty();
	}

	/* (non-Javadoc)
     * @see net.cbtltd.shared.api.HasState#getState()
     */
	@Description(value = "The current state of the entity or process", target = DocTarget.METHOD)
    public final String getState() {
        return state;
    }

    /* (non-Javadoc)
     * @see net.cbtltd.shared.api.HasState#setState(java.lang.String)
     */
    public final void setState(String state) {
        this.state = state;
    }

	/**
	 * No state.
	 *
	 * @return true, if successful
	 */
	public final boolean noState() {
		return state == null || state.isEmpty();
	}

	/**
	 * Checks for state.
	 *
	 * @return true, if successful
	 */
	public final boolean hasState() {
		return !noState();
	}

	/**
	 * Checks if the model is in the state.
	 *
	 * @param state the state.
	 * @return true, the model is in the state.
	 */
	public final boolean hasState(String state) {
		return this.state != null && state != null && this.state.equalsIgnoreCase(state);
	}

	/**
	 * Checks if the model is not in the state.
	 *
	 * @param state the state.
	 * @return true, if the model is not in the state.
	 */
	public final boolean notState(String state) {
		return !hasState(state);
	}

	/**
	 * Checks if in state list.
	 *
	 * @param state the state
	 * @return true, if successful
	 */
	public final boolean hasState(String[] states) {
		if (this.state == null || states == null || states.length <= 0) {return false;}
		for (String state : states) {
			if (state.equalsIgnoreCase(this.state)) {return true;}
		}
		return false;
	}

	/**
	 * Checks if not in state list.
	 *
	 * @param state the state
	 * @return true, if successful
	 */
	public final boolean notState(String[] states) {
		return !hasState(states);
	}

	
	@XmlTransient
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean hasOption(int option) {
		return options == null || options.length() <= option ? true : options.substring(option, option + 1).equalsIgnoreCase("1");
	}
	
	public boolean notOption(int option) {
		return !hasOption(option);
	}
	
	@XmlTransient
	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	//---------------------------------------------------------------------------------
	// Attribute functions
	//---------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasAttributes#getAttributemap()
	 */
	@Description(value = "The key value map of attributes of the entity or process", target = DocTarget.METHOD)
	public final HashMap<String, ArrayList<String>> getAttributemap() {
		return attributemap;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasAttributes#setAttributemap(java.util.HashMap)
	 */
	public final void setAttributemap(HashMap<String, ArrayList<String>> attributes) {
		this.attributemap = attributes;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasAttributes#noAttributemap()
	 */
	public final boolean noAttributemap() {
		return attributemap == null || attributemap.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasAttributes#hasAttributemap()
	 */
	public final boolean hasAttributemap() {
		return !noAttributemap();
	}

	/**
	 * Gets the attribute values.
	 *
	 * @param key the key
	 * @return the attribute values
	 */
	public final ArrayList<String> getAttributeValues(String key) {
		return attributemap == null ? null : attributemap.get(key);
	}

	//---------------------------------------------------------------------------------
	// Value functions
	//---------------------------------------------------------------------------------
	/**
	 * Transforms the hash map of values to an array of key:value pairs.
	 *
	 * @return the array of key:value pairs
	 */
	@Description(value = "The list of values of the entity or process", target = DocTarget.METHOD)
	public final ArrayList<String> getValues() {
		return getValues(values);
	}

	/**
	 * Sets the values.
	 *
	 * @param keyvalues the new values
	 */
	public final void setValues(ArrayList<String> keyvalues) {
		values = new HashMap<String, String>();
		addValues(keyvalues);
	}

	/**
	 * Adds the values.
	 *
	 * @param keyvalues the keyvalues
	 */
	public void addValues(ArrayList<String> keyvalues) {
		if (values == null) {values = new HashMap<String, String>();}
		if (keyvalues == null) {return;}
		for (String keyvalue : keyvalues) {
			String[] args = keyvalue.split(DELIMITER);
			if (args.length == 2) {values.put(args[0], args[1]);}
		}
	}

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public final String getValue(String key) {
		if (values == null || values.isEmpty()) {return null;}
		else {return values.get(key);}
	}

	/**
	 * Gets the boolean value.
	 *
	 * @param key the key
	 * @return the boolean value
	 */
	public final Boolean getBooleanValue(String key){
		String value = getValue(key);
		if (value == null){return false;}
		else {return Boolean.valueOf(value);}
	}

	/**
	 * Gets the double value.
	 *
	 * @param key the key
	 * @return the double value
	 */
	public final Double getDoubleValue(String key){
		String value = getValue(key);
		if (value == null){return 0.0;}
		else {return Double.valueOf(value);}
	}

	/**
	 * Gets the integer value.
	 *
	 * @param key the key
	 * @return the integer value
	 */
	public final Integer getIntegerValue(String key){
		String value = getValue(key);
		if (value == null){return 0;}
		else {return Integer.valueOf(value);}
	}

//	public final Date getTimeValue(String key){
//		String value = getValue(key);
//		if (value == null){return null;}
//		else {return Time.setTime(value) ;}
//	}

	/**
	 * Sets the value.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public final void setValue(String key, String value) {
		if (key == null || key.isEmpty() || value == null) {return;}
		if (this.values == null) {this.values = new HashMap<String, String>();}
		values.put(key, value);
	}

	/**
	 * Sets the boolean value.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public final void setBooleanValue(String key, Boolean value) {
		if (key == null || key.isEmpty() || value == null){return;}
		setValue(key, String.valueOf(value));
	}

	/**
	 * Sets the double value.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public final void setDoubleValue(String key, Double value) {
		if (key == null || key.isEmpty() || value == null){return;}
		setValue(key, String.valueOf(value));
	}

	/**
	 * Sets the integer value.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public final void setIntegerValue(String key, Integer value) {
		if (key == null || key.isEmpty() || value == null){return;}
		setValue(key, String.valueOf(value));
	}

//	public final void setTimeValues(String key, String value) {
//		if (key == null || key.isEmpty() || value == null){return;}
//		setValue(key, value);
//	}

/**
 * No values.
 *
 * @return true, if successful
 */
public final boolean noValues() {
		return values == null || values.isEmpty();
	}

	/**
	 * Checks for values.
	 *
	 * @return true, if successful
	 */
	public final boolean hasValues() {
		return !noValues();
	}

	/**
	 * No value.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public final boolean noValue(String key) {
		return getValue(key) == null;
	}

	/**
	 * Checks for value.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public final boolean hasValue(String key) {
		return !noValue(key);
	}

	//---------------------------------------------------------------------------------
	// Key/Value functions
	//---------------------------------------------------------------------------------
	public ArrayList<KeyValue> getKeyvalues() {
		return keyvalues;
	}

	public void setKeyvalues(ArrayList<KeyValue> keyvalues) {
		this.keyvalues = keyvalues;
	}

	//---------------------------------------------------------------------------------
	// Text functions
	//---------------------------------------------------------------------------------
	/**
	 * Sets the texts.
	 *
	 * @param texts the texts
	 */
	public void setTexts(HashMap<String, Text> texts) {
		this.texts = texts;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasTexts#getTexts()
	 */
	public HashMap<String, Text> getTexts() {
		return texts;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasTexts#noTexts()
	 */
	public boolean noTexts() {
		return texts == null || texts.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.HasTexts#hasTexts()
	 */
	public boolean hasTexts() {
		return !noTexts();
	}


	public String getTextId(NameId.Type model, String id, Text.Code type) {
		return model.name() + id + type.name();
	}

	/**
	 * Gets the specified text.
	 *
	 * @param model the NameId.Type of model
	 * @param id the id of the model
	 * @param type the Text.Type of the text
	 * @return the text.
	 */
	protected Text getText(NameId.Type model, String id, Text.Code type) {
		return id == null || id.isEmpty() ? null : getText("", model,id,type);
	}
	
	protected Text getText(String organizationid, NameId.Type model, String id) {
		return id == null || id.isEmpty() ? null : getText(organizationid, model, id, null);
	}
	
	protected Text getText(String organizationid, NameId.Type model, String id, Text.Code type) {
		if (id == null || id.isEmpty()) {return null;}
		String textid = organizationid + model.name() + id + (type == null ? "" : type.name());
		if (texts == null) {texts = new HashMap<String, Text>();}
		if (!texts.containsKey(textid)) {texts.put(textid, new Text(textid, name, Text.Type.HTML, new Date(), null, Language.EN));}
		return texts.get(textid);
	}

//	protected Text getText(String id, Text.Type type) {
//		if (texts == null) {texts = new HashMap<String, Text>();}
//		if (!texts.containsKey(id)) {texts.put(id, new Text(id, name, type, Text.Format.HTML, new Date(), null, Language.EN));}
//		return texts.get(id);
//	}

	/**
	 * Sets the text.
	 *
	 * @param id the id
	 * @param value the value
	 */
	protected void setText(NameId.Type model, String id, Text.Code type, Text value) {
		setText("", model, id, type, value);
	}
	
	protected void setText(String organizationid, NameId.Type model, String id, Text value) {
		setText(organizationid, model, id, null, value);
	}

	protected void setText(String organizationid, NameId.Type model, String id, Text.Code type, Text value) {
		if (value == null) {return;}
		String textid = organizationid + model.name() + id + (type == null ? "" : type.name());
		value.setId(textid);
		value.setName(name);
		if (texts == null) {texts = new HashMap<String, Text>();}
		texts.put(textid, value);
	}

//	protected void setText(String id, Text value) {
//		if (value == null) {return;}
//		value.setId(id);
//		value.setName(name);
//		if (texts == null) {texts = new HashMap<String, Text>();}
//		texts.put(id, value);
//	}

	public ArrayList<String> getImageurls() {
		return imageurls;
	}

	public void setImageurls(ArrayList<String> images) {
		this.imageurls = images;
	}

	public boolean noImages() {
		return imageurls == null || imageurls.isEmpty();
	}

	public boolean hasImages() {
		return !noImages();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.NameId#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Model [organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", state=");
		builder.append(state);
		builder.append("\nvalues=");
		builder.append(values);
		builder.append("\nattributes=");
		builder.append(attributemap);
		builder.append("\ntexts=");
		builder.append(texts);
		builder.append("]");
		return builder.toString();
	}
}
