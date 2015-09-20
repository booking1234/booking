/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Country {
	@XmlAttribute (name = "country_code")
	public String country_code;
	@XmlValue
	public String Country;
	
	Country(){}
	
	Country(String country_code, String country){
		this.country_code = country_code;
		this.Country = country;
	}
}
