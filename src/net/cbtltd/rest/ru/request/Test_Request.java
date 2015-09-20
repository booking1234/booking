package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"authentication",
    "ownerid"
})
@XmlRootElement(name = "Pull_ListLanguages_RQ")
public class Test_Request {

	@XmlElement(required = true)
	protected Authentication authentication;
	@XmlElement(required = true)
	protected String ownerid;
	
	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	public String getOwnerid() {
		return ownerid;
	}
	
	public void setOwnerID(String ownerid) {
		this.ownerid = ownerid;
	}

}
