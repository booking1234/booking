package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlSchemaType;


@XmlAccessorType(XmlAccessType.FIELD)
public class Image {
	
	@XmlAttribute(name="ImageTypeID")
	protected int ImageTypeID;
	@XmlValue
    @XmlSchemaType(name = "anyURI")
	protected String Url;
	
	/**
	 * @return the imageTypeID
	 */
	public int getImageTypeID() {
		return ImageTypeID;
	}
	
	/**
	 * set imageTypeID
	 */
	public void setImageTypeID(int imageTypeID) {
		ImageTypeID = imageTypeID;
	}
	
	/**
	 * @return Image URL
	 */
	public String getUrl() {
		return Url;
	}
	
	/**
	 * set Url
	 */
	public void setUrl(String url) {
		Url = url;
	}
	

}
