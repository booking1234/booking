package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="image")
public class Image {

	private String image;
	private String text;

	public Image() {}

	public Image(String image, String text) {
		super();
		this.image = image;
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
