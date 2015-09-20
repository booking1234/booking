package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.ChangeLog;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "ChangeLog"
})
@XmlRootElement(name = "Pull_ListPropertyChangeLog_RS")
public class ListPropertyChangeLog {

	@XmlElement(required = true)
	protected Status Status;
	protected ChangeLog ChangeLog;
	
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
		this.Status = status;
	}
	
	/**
	 * @return the changeLog data
	 */
	public ChangeLog getChangeLog() {
		return ChangeLog;
	}
	
	/**
	 * set the changeLog
	 */
	public void setChangeLog(ChangeLog changeLog) {
		this.ChangeLog = changeLog;
	}

}