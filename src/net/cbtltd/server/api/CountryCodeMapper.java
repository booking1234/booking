package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.CountryCode;


public interface CountryCodeMapper {
	List<CountryCode> getCountryCodes();
	List<String> getAllCountries();
	String getCountryName(String iso_code2);
	String getPhoneCode(String countryName);
}
