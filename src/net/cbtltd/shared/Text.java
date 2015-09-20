/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.client.secure.javalang.Str;
import net.cbtltd.shared.api.HasPath;
import net.cbtltd.shared.api.HasServiceResponse;

@XmlRootElement(name="text")
public class Text
implements HasPath, HasServiceResponse {

	//form upload
	public static final String THUMBNAIL_PIXELS = "Thumbnail Pixels";
	public static final String FULLSIZE_PIXELS = "Fullsize Pixels";
	public static final String FILE_NAME = "File Name";
	public static final String FILE_NOTES = "File Notes";
	public static final String FILE_TYPE = "File Type"; //Upload file type
	public static final int THUMBNAIL_PIXELS_VALUE = 100;
	public static final int FULLSIZE_PIXELS_VALUE = 500;
	public static final int LOGO_PIXELS_VALUE = 100; //high
	public static final int MAX_BLOB_SIZE = 200000;
	public static final int MAX_TEXT_SIZE = 20000;
	public static final String TEMP_FILE = "Temp";

	public static final String IMAGE = "Image";

	
	public static final String AUDIO_OGG = ".ogg"; //qualifies text id
	public static final String AUDIO_MP3 = ".mp3";
	public static final String AUDIO_WAV = ".wav";
	
	public static final String VIDEO_OGG = ".ogg";
	public static final String VIDEO_MP4 = ".mp4";
	public static final String VIDEO_WEBM = ".webm";
	
	public static final String DEFAULT_IMAGE = "Product144-000";
	public static final String FULLSIZE_JPG = ".jpg";
	public static final String THUMBNAIL_JPG = "Thumb.jpg";
	public static final String THUMBNAIL_INFIX = "Thumb";
	public static final String IMAGE_PREFIX = "Product";
	public static final String DEFAULT_IMAGE_URL = "https://s3.amazonaws.com/mybookingpal/pictures/Product144-000Thumb.jpg";
	public static String getLogo(String organizationid) {return "Logo." + organizationid;}//NO TYPE EXTENSION!

	public enum Code {Checkin, Contents, Condition, Contract, Contact, File, Inside, Options, Outside, Pictures, Private, Public, Service, Url}

	public static final Code getCode(String type) {
		if (type == null || type.isEmpty()) {return Code.Public;}
		try {return Code.valueOf(type);}
		catch (Throwable x) {return Code.Public;}
	}

	public enum State {Initial, Created, Final}
	public enum Type {Blob, File, Image, ImageBlob, PublicFile, Text, HTML}
	
	/**
	 * Checks if the URL is a valid image file type, otherwise false.
	 * 
	 * @param url the URL of the file.
	 * @return true, if a valid image file type, otherwise false.
	 */
	public static boolean isImageFile(String url){
		if (url == null) {return false;}
		String filename = url.trim().toLowerCase();
		return filename.endsWith(".jpg")
		|| filename.endsWith(".jpeg")
		|| filename.endsWith(".bmp")
		|| filename.endsWith(".gif")
		|| filename.endsWith(".png");
	}

	/**
	 * Checks if the URL is not a valid image file type, otherwise false.
	 * 
	 * @param url the URL of the file.
	 * @return true, if not a valid image file type, otherwise false.
	 */
	public static boolean notImageFile(String url){return !isImageFile(url);}

	/**
	 * Checks if the URL is not a valid audio file type, otherwise false.
	 * 
	 * @param url the URL of the file.
	 * @return true if a valid audio file type, otherwise false.
	 */
	public static boolean isAudioFile(String url){
		if (url == null) {return false;}
		String filename = url.trim().toLowerCase();
		return filename.endsWith(".au")
		|| filename.endsWith(".mp3")
		|| filename.endsWith(".ogg")
		|| filename.endsWith(".wav");
	}

	public static boolean notAudioFile(String url){return !isAudioFile(url);}

	/**
	 * Gets the extension type of the specified filename.
	 * 
	 * @param filename the specified filename.
	 */
	public static final String getExtension(String filename) {
		String[] args = filename.split("\\.");
		if(args.length < 2) {
			return "";
		}
		else {
			return "." + args[args.length -1].toLowerCase();
		}
	}

	/**
	 * Gets the specified file name without its extension.
	 * 
	 * @param value the specified file name with extension.
	 * @return the file name without extension.
	 */
	public static String trimExtension(String value) {
		if (value == null || value.lastIndexOf('.') <= 0) {return(value);}
		else {return value.substring(0, value.lastIndexOf('.'));}
	}
	
	/**
	 * Gets the specified file name without its extension.
	 * 
	 * @param value the specified file name with extension.
	 * @return the file name only last part of URL.
	 */
	public static String getS3ImageFilename(String value) {
		String filename = "";
		if (value == null || value.lastIndexOf('/') <= 0) {
			filename = value;
		}
		else {
			filename = value.substring(value.lastIndexOf('/') + 1);
		}
		int index = filename.indexOf(Text.THUMBNAIL_INFIX);
		if(index > 0) {
			filename = filename.substring(0, index) + filename.substring(index + Text.THUMBNAIL_INFIX.length());
		}
		return filename;
	}
	
	/**
	 * Gets the specified file name without its extension.
	 * 
	 * @param value the specified file name with extension.
	 * @return part of URL without filename.
	 */
	public static String getProductURL(String value) {
		if (value == null || value.lastIndexOf('/') <= 0) {
			return(value);
		}
		else {
			return value.substring(0, value.lastIndexOf('/') + 1);
		}
	}
	
	/**
	 * 
	 * @param imageurls
	 * @return part of URL without filename
	 */
	public static String getProductImageURL(ArrayList<String> imageurls) {
		String url = "";
		if(imageurls.size() > 0) {
			url = imageurls.get(0);
			url = url.substring(0, url.lastIndexOf('/') + 1);
		}
		return url;
	}

	/**
	 * Gets a string with any HTML mark up removed.
	 * 
	 * @param html the text from which HTML mark up is to be removed.
	 * @return text without mark up.
	 * @see <pre>http://developer.ean.com/faqs/Development</pre>
	 * TODO:
xmlText=Replace(xmlText,"&amp;lt;","&lt;")
xmlText=Replace(xmlText,"&amp;gt;","&gt;")
xmlText=Replace(xmlText,"&amp;apos;","&apos;")
xmlText=Replace(xmlText,"&amp;#x0A","")
xmlText=Replace(xmlText,"&amp;#x0D","")
xmlText=Replace(xmlText,"&#x0D","")
xmlText=Replace(xmlText,"&#x0A","")
xmlText=Replace(xmlText,"&amp;#x09","")
xmlText=Replace(xmlText,"&amp;amp;amp;","&amp;")
xmlText=Replace(xmlText,"&lt;br&gt;","<br />")
	 */
	public static final String stripHTML(String html){
		if (html == null || html.isEmpty()){return html;}
		html = html.replaceAll("&nbsp;", " ");
		html=html.replaceAll("&amp;lt;","&lt;");
		html=html.replaceAll("&amp;gt;","&gt;");
		html=html.replaceAll("&amp;apos;","&apos;");
		html=html.replaceAll("&amp;#x0A","");
		html=html.replaceAll("&amp;#x0D","");
		html=html.replaceAll("&#x0D","");
		html=html.replaceAll("&#x0A","");
		html=html.replaceAll("&amp;#x09","");
		html=html.replaceAll("&amp;amp;amp;","&amp;");
		html=html.replaceAll("&lt;br&gt;","<br />");
		return html.replaceAll("\\<.*?>",""); // alt return html.replaceAll("\\<.*?\\>", "");
	}

	/**
	 * Splits the specified text into separate lines where the line separators characters are
	 * newline (hex 0x0a) or carriage return (hex 0x0d) characters.
	 * 
	 * @param text the specified text.
	 * @return the text split into separate lines
	 */
	public static final String[] getSentences(String text){
		if (text == null || text.isEmpty()) {return null;}
		return text.split("\\r?\\n");
	}

	protected int status = 0;
	protected String id;
	protected String name;
	protected String state;
	protected String type;
	protected String notes;
	protected String language;
	protected Date date;
	protected byte[] data;
	private Date version; //latest change

	public Text() {}

	public Text(String id, String language) {
		this.id = id;
		this.language = language;
	}

	public Text(String id, String name, Type type, Date date, String notes, String language) {
		this.id = id;
		this.name = name;
		this.state = State.Created.name();
		this.type = type == null ? null : type.name();
		this.date = date;
		this.notes = notes;
		this.language = language;
	}

	public Service service() {return Service.TEXT;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean noId() {
		return id == null || id.isEmpty() || id.equals(Model.ZERO);
	}

	public boolean hasId() {
		return !noId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? " " : name;
	}

	public String getName(int length) {
		return NameId.trim(name, ",", length);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(Type type) {
		this.type = type == null ? null : type.name();
	}

	public boolean notType(Type type) {
		return this.type == null || type == null || !this.type.equalsIgnoreCase(type.name());
	}

	public boolean hasType(Type type) {
		return !notType(type);
	}

	@XmlTransient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean noStatus() {
		return status == 0;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes == null ? " " : notes;
	}

	public boolean noNotes() {
		return notes == null || notes.trim().isEmpty();
	}

	public String getPlainText() {
		if (notes == null){return "";}
		return notes.replaceAll("\\<.*?>","");
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language == null ? "EN" : language;
	}

	public boolean noLanguage() {
		return language == null || language.isEmpty();
	}

	public boolean hasLanguage(String language) {
		return this.language != null && this.language.equalsIgnoreCase(language);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date == null ? new Date() : date;
	}

	public boolean noDate() {
		return date == null;
	}

	public byte[] getData() {
		return data;
	}

	public String getDataString() {
		if (data == null) {return null;}
		String value = new String();
		for (byte datum : data) {value += (char)datum;}
		return value;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	// getBytes(value) is equivalent to value.getBytes() which is not supported by GWT<=1.5
	// @see <pre>http://code.google.com/p/gwt-crypto/source/browse/trunk/src/main/java/com/googlecode/gwt/crypto/TripleDesCipher.java?r=6</pre>
	public void setDataString(String text) {
		if (text == null) {data = null;}
		else {data = Str.toBytes(text.toCharArray());}
	}

	public boolean noData(long min, long max) {
		return data == null || data.length < min || data.length > max;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Text [status=");
		builder.append(status);
		builder.append(", id=");
		builder.append(id);
		builder.append(", language=");
		builder.append(language);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", state=");
		builder.append(state);
		builder.append(", date=");
		builder.append(date);
		builder.append(", notes=");
		builder.append(notes);
//		builder.append(", data=");
//		builder.append(Arrays.toString(data));
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
	
	
}
