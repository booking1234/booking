package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "Name",
	    "SurName",
	    "Email",
	    "Phone",
	    "SkypeID",
	    "Address",
	    "ZipCode",
	    "CountryID"
	})
@XmlRootElement(name = "CustomerInfo")
public class CustomerInfo {
	
	@XmlElement(required = true)
	protected String Name;
	@XmlElement(required = true)
	protected String SurName;
	@XmlElement(required = true)
	protected String Email;
	@XmlElement
	protected String Phone;
	@XmlElement
	protected String SkypeID;
	@XmlElement
	protected String Address;
	@XmlElement
	protected String ZipCode;
	@XmlElement
	protected int CountryID;
	
	/**
	 * @return the customer's name
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * set the customer's name
	 */
	public void setName(String name) {
		Name = name;
	}
	
	/**
	 * @return the customer's surname
	 */
	public String getSurName() {
		return SurName;
	}
	
	/**
	 * set the customer's surname
	 */
	public void setSurName(String surName) {
		SurName = surName;
	}
	
	/**
	 * @return the customer's email address
	 */
	public String getEmail() {
		return Email;
	}
	
	/**
	 * set the customer's email address
	 */
	public void setEmail(String email) {
		Email = email;
	}
	
	/**
	 * @return the customer's phone number with country code
	 */
	public String getPhone() {
		return Phone;
	}
	
	/**
	 * set the customer's phone number with country code
	 */
	public void setPhone(String phone) {
		Phone = phone;
	}
	
	/**
	 * @return the customer's Skype identifier
	 */
	public String getSkypeID() {
		return SkypeID;
	}
	
	/**
	 * set the customer's Skype identifier
	 */
	public void setSkypeID(String skypeID) {
		SkypeID = skypeID;
	}
	
	/**
	 * @return the customer's address
	 */
	public String getAddress() {
		return Address;
	}
	
	/**
	 * set the customer's address
	 */
	public void setAddress(String address) {
		Address = address;
	}
	
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return ZipCode;
	}
	
	/**
	 * set the zipCode
	 */
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	
	/**
	 * @return the code that uniquely identifies a country
	 */
	public int getCountryID() {
		return CountryID;
	}
	
	/**
	 *set the code that uniquely identifies a country
	 */
	public void setCountryID(int countryID) {
		CountryID = countryID;
	}

}
