package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "EGP"
})
@XmlRootElement(name = "EGPS")
public class EGPS {

	protected List<EGP> EGP;
	
    public List<EGP> getEGP() {
        if (EGP == null) {
        	EGP = new ArrayList<EGP>();
        }
        return this.EGP;
    }

}
