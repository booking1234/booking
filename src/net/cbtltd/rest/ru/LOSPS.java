package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "LOSP"
})
@XmlRootElement(name = "LOSPS")
public class LOSPS {
	
	protected List<LOSP> LOSP;
	
    public List<LOSP> getLOSP() {
        if (LOSP == null) {
        	LOSP = new ArrayList<LOSP>();
        }
        return this.LOSP;
    }

}
