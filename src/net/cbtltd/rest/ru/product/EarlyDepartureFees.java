package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "EarlyDepartureFee"
})
@XmlRootElement(name = "EarlyDepartureFees")
public class EarlyDepartureFees {

	protected List<EarlyDepartureFee> EarlyDepartureFee;
	
    public List<EarlyDepartureFee> getEarlyDepartureFee() {
        if (EarlyDepartureFee == null) {
        	EarlyDepartureFee = new ArrayList<EarlyDepartureFee>();
        }
        return this.EarlyDepartureFee;
    }
	
}
