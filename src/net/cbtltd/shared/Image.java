package net.cbtltd.shared;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.client.secure.javalang.Str;
import net.cbtltd.shared.api.HasPath;
import net.cbtltd.shared.api.HasServiceResponse;

/**
 * @author	Chirayu Shah
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */


@XmlRootElement(name="text")
public class Image extends ModelTable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + productId;
//		result = prime * result + ((state == null) ? 0 : state.hashCode());
//		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productId != other.productId)
			return false;
//		if (state == null) {
//			if (other.state != null)
//				return false;
//		} else if (!state.equals(other.state))
//			return false;
//		if (url == null) {
//			if (other.url != null)
//				return false;
//		} else if (!url.equals(other.url))
//			return false;
		return true;
	}

	//form upload
	public static final String 	THUMBNAIL_PIXELS 		= "Thumbnail Pixels";
	public static final String 	FULLSIZE_PIXELS 		= "Fullsize Pixels";
	public static final String 	FILE_NAME 				= "File Name";
	public static final String 	FILE_NOTES 				= "File Notes";
	public static final String 	FILE_TYPE 				= "File Type"; //Upload file type
	public static final int 	THUMBNAIL_PIXELS_VALUE 	= 100;
	public static final int 	FULLSIZE_PIXELS_VALUE 	= 500;
	public static final int 	LOGO_PIXELS_VALUE 		= 100; //high
	public static final int 	MAX_BLOB_SIZE 			= 200000;
	public static final int 	MAX_TEXT_SIZE 			= 20000;
	public static final String  TEMP_FILE 				= "Temp";
	public static final String 	FULLSIZE_JPG 			= ".jpg";
	public static final String 	THUMBNAIL_JPG 			= "Thumb.jpg";

	public static final String IMAGE = "Image";

	
	public static final String AUDIO_OGG = ".ogg"; //qualifies text id
	public static final String AUDIO_MP3 = ".mp3";
	public static final String AUDIO_WAV = ".wav";
	
	public static final String VIDEO_OGG = ".ogg";
	public static final String VIDEO_MP4 = ".mp4";
	public static final String VIDEO_WEBM = ".webm";
	

	public static String getLogo(String organizationid) {return "Logo." + organizationid;}//NO TYPE EXTENSION!

	public enum Code {Checkin, Contents, Condition, Contract, Contact, File, Inside, Options, Outside, Pictures, Private, Public, Service, Url}

	public static final Code getCode(String type) {
		if (type == null || type.isEmpty()) {return Code.Public;}
		try {return Code.valueOf(type);}
		catch (Throwable x) {return Code.Public;}
	}

	public enum State {Initial, Created, Final}
	public enum Type {Hosted, Linked, Blob}
	
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
	 * Gets the extension type of the specified filename.
	 * 
	 * @param filename the specified filename.
	 */
	public static final String getExtension(String filename) {
		String[] args = filename.split("\\.");
		return args[1].toLowerCase();
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
	protected int id;
	protected String name;
	protected String oldName;
	protected String state;
	protected String type;
	protected String notes;
	protected String language;
    protected int productId;
	protected byte[] data;
	protected String url;
	protected boolean standard;
	protected boolean thumbnail;
	// 1 - image has type (e.g. "Main Image", "Exterior", "Interior" picture etc.)
	// 2 - default number for images which do not have any type.
	protected int sort;
	private Date version; //latest change

	public Image() {}

	public Image(int id, String language) {
		this.id = id;
		this.language = language;
	}

	public Image(int id, String name, Type type, String notes, String language) {
		this.id = id;
		this.name = name;
		this.state = State.Created.name();
		this.type = type == null ? null : type.name().toLowerCase();
		this.notes = notes;
		this.language = language;
	}
	
	public Image(String name, int productId, Type type, String notes, String language, String url) {
		this.name = name;
		this.state = State.Created.name();
		this.type = type == null ? null : type.name().toLowerCase();
		this.notes = notes;
		this.language = language;
		this.productId = productId;
		this.url = url;
	}

	public Service service() {return Service.IMAGE;}



	public boolean hasId() {
		return !noId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? " " : name;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName(int length) {
		return NameId.trim(name, ",", length);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
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

	public boolean isStandard() {
		return standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	public boolean isThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(boolean thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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
		builder.append("Image [status=");
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
