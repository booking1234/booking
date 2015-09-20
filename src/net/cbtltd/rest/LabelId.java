package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlElement;

public class LabelId {

	private String label;
	private String id;
	
	public LabelId() {	}
		
	public LabelId(String label, String id) {
		super();
		this.label = label;
		this.id = id;
	}

	@XmlElement(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlElement(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
