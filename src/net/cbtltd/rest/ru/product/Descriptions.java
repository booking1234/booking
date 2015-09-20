package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Description"
})
@XmlRootElement(name = "Descriptions")
public class Descriptions {

	protected List<Description> Description;
	
    public List<Description> getDescription() {
        if (Description == null) {
        	Description = new ArrayList<Description>();
        }
        return this.Description;
    }

	public void setDescription(List<Description> descriptions) {
		this.Description = descriptions;
	}
}
