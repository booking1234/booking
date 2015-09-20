package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "LOS"
})
@XmlRootElement(name = "LOSS")
public class LOSS {
	
	protected List<LOS> LOS;
	
    public List<LOS> getLOS() {
        if (LOS == null) {
        	LOS = new ArrayList<LOS>();
        }
        return this.LOS;
    }

}
