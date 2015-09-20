package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Text"
})
@XmlRootElement(name = "PickupService")
public class PickupService {
	
	 protected List<Text> Text;
	 
	 public List<Text> getText() {
	        if (Text == null) {
	        	Text = new ArrayList<Text>();
	        }
	        return this.Text;
	    }

	/**
	 * set the text that describe how to arrive to the property
	 */
	public void setText(List<Text> text) {
		Text = text;
	}

}
