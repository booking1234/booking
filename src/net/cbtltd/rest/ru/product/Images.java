package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Image"
})
@XmlRootElement(name = "Images")
public class Images {
	
	 protected List<Image> Image;
	 
	 public List<Image> getImage() {
	        if (Image == null) {
	            Image = new ArrayList<Image>();
	        }
	        return this.Image;
	    }

	 public void setImage(List<Image> images) {
		 this.Image = images;
	 }
}
