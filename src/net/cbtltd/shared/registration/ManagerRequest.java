package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "manager_request")
@XmlAccessorType(XmlAccessType.FIELD)
@Description(value = "Property manager information", target = DocTarget.RESPONSE)
public class ManagerRequest {
	
	@XmlElement(name = "pos")
	private String pos;
	@XmlElement(name = "pmsid")
	private String pmsId;
	@XmlElement(name = "firstname")
	private String firstName;
	@XmlElement(name = "lastname")
	private String lastName;
	@XmlElement(name = "email")
	private String email;
	@XmlElement(name = "telephone")
	private String telephoneNumber;
	@XmlElement(name = "accountid")
	private String accountId;
	@XmlElement(name = "company")
	private String company;
	@XmlElement(name = "address")
	private String address;
	@XmlElement(name = "city")
	private String city;
	@XmlElement(name = "state")
	private String state;
	@XmlElement(name = "zip")
	private String zip;
	@XmlElement(name = "password")
	private String password;	
	
	public ManagerRequest() {	
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPmsId() {
		return pmsId;
	}

	public void setPmsId(String pmsId) {
		this.pmsId = pmsId;
	}
		
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
