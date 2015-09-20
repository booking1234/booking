package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "Text",
	    "Image"
	})
@XmlRootElement(name = "Description")
public class Description {
	
	@XmlAttribute(name="LanguageID")
	protected int LanguageID;
	@XmlElement
	protected String Text;
	@XmlElement
    @XmlSchemaType(name = "anyURI")
	protected String Image;
	
	/**
	 * @return code that identify the language of description
	 */
	public int getLanguageID() {
		return LanguageID;
	}
	
	/**
	 * set languageID
	 */
	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}
	
	/**
	 * @return description in selected language
	 */
	public String getText() {
		return Text;
	}
	
	/**
	 * set text
	 */
	public void setText(String text) {
		Text = text;
	}
	
	/**
	 * @return the url of image that contains property description („description as an image”)
	 */
	public String getImage() {
		return Image;
	}
	
	/**
	 * set image
	 */
	public void setImage(String image) {
		Image = image;
	}

}
