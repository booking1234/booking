package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Text {

	@XmlAttribute(name="LanguageID")
	protected int languageID;
	@XmlValue
	protected String text;
	
	public Text() {}
	
	public Text(String text, int languageID) {
		this.languageID = languageID;
		this.text = text;
	}
	
	/**
	 * @return the code that identify the language of description
	 */
	public int getLanguageID() {
		return languageID;
	}
	
	/**
	 * set languageID
	 */
	public void setLanguageID(int languageID) {
		this.languageID = languageID;
	}
	
	/**
	 * @return the text description
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * set text
	 */
	public void setText(String text) {
		this.text = text;
	}
}
