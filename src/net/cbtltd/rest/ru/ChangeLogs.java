package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ChangeLog"
})
@XmlRootElement(name = "ChangeLogs")
public class ChangeLogs {

	protected List<ChangeLog> ChangeLog;
	
	public List<ChangeLog> getChangeLog() {
		if (ChangeLog == null) {
			ChangeLog = new ArrayList<ChangeLog>();
		}
		return this.ChangeLog;
	}
}
