package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "StayInfo"
})
@XmlRootElement(name = "StayInfos")
public class StayInfos {
	
	protected List<StayInfo> StayInfo;
	
	/**
	 * @return Informations about the stay
	 */
    public List<StayInfo> getStayInfo() {
        if (StayInfo == null) {
        	StayInfo = new ArrayList<StayInfo>();
        }
        return this.StayInfo;
    }
    
    public void setStayInfo(StayInfo stayInfo) {
        if (StayInfo == null) {
        	StayInfo = new ArrayList<StayInfo>();
        }
    	StayInfo.add(stayInfo);
    }

}
