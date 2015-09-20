package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "UserName",
    "Password"
})
@XmlRootElement(name = "Authentication")
public class Authentication {
	
	@XmlElement(required = true)
	protected String UserName;
	@XmlElement(required = true)
	protected String Password;

	/**
	 * @return the UserName
	 */
	public String getUsername() {
		return UserName;
	}
    
	/**
	 * Set UserName
	 */
	public void setUsername(String username) {
		this.UserName = username;
	}
	
	/**
	 * @return the Password
	 */
	public String getPassword() {
		return Password;
	}	

	/**
	 * Set Password
	 */
	public void setPassword(String password) {
		this.Password = password;
	}
}
