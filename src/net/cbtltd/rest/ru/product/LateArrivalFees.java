package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "LateArrivalFee"
})
@XmlRootElement(name = "LateArrivalFees")
public class LateArrivalFees {
	
	protected List<LateArrivalFee> LateArrivalFee;
	
    public List<LateArrivalFee> getLateArrivalFee() {
        if (LateArrivalFee == null) {
        	LateArrivalFee = new ArrayList<LateArrivalFee>();
        }
        return this.LateArrivalFee;
    }

}
