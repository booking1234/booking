package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.ChangeLogs;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "ChangeLogs"
})
@XmlRootElement(name = "Pull_ListPropertiesChangeLog_RS")
public class ListPropertiesChangeLog {

	@XmlElement(required = true)
	protected Status Status;
	protected ChangeLogs ChangeLogs;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * set the status
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
	/**
	 * @return the list of change logs for each property
	 */
	public ChangeLogs getChangeLogs() {
		return ChangeLogs;
	}
	
	/**
	 * set changeLogs
	 */
	public void setChangeLogs(ChangeLogs changeLogs) {
		ChangeLogs = changeLogs;
	}
	
}
