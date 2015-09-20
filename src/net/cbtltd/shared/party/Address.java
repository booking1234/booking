package net.cbtltd.shared.party;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Address implements IsSerializable {
	
	private Address() {
		super();
	}
	
	public enum Type {
		address, city, zip, state
	};
	
	public Address(String postalAddress) {
		this.address = getAddressValue(postalAddress, Type.address);
		this.city = getAddressValue(postalAddress, Type.city);
		this.zip = getAddressValue(postalAddress, Type.zip);
		this.state = getAddressValue(postalAddress, Type.state);
		if (!isEmptyString(postalAddress) && !this.isFormattedAddress()){
			this.postalAddress = postalAddress;
		}
	}

	private String address;
	private String city;
	private String zip;
	private String state;
	private String postalAddress;
	
	private static final String ADDRESS_APPENDER = Type.address.name() + ":";
	private static final String CITY_APPENDER = Type.city.name() + ":";
	private static final String ZIP_APPENDER = Type.zip.name() + ":";
	private static final String STATE_APPENDER = Type.state.name() + ":";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the specific value from party's address.
	 * 
	 * @param party party that contains address
	 * @param value value to get. E.g. city, zip
	 * @return value
	 */
	private String getAddressValue(String address, Address.Type value) {
		if(address == null) {
			return "";
		}
		String[] tokens = address.split(";");
		if(tokens.length > 1) {
			for(String string : tokens) {
				string = string.trim();
				if(string.contains(value.name())) {
					String[] splittedValue = string.split(":");
					if(splittedValue.length < 2) {
						return "";
					}
					return splittedValue[1];
				}
			}
		}
		return "";
	}

	// address:412 The Regent;city:Johannesburg;state:;zip:2196
	public String getPostalAddress() {
		StringBuilder postalAddress = new StringBuilder();
		if(!isEmptyString(address)) {
			postalAddress.append(ADDRESS_APPENDER + address + ";");
		}
		if(!isEmptyString(city)) {
			postalAddress.append(CITY_APPENDER + city + ";");
		}
		if(!isEmptyString(zip)) {
			postalAddress.append(ZIP_APPENDER + zip + ";");
		}
		if(!isEmptyString(state)) {
			postalAddress.append(STATE_APPENDER + state + ";");
		}
		if (!isFormattedAddress() && !isEmptyString(this.postalAddress)){
			return this.postalAddress;
		}
		return postalAddress.toString();
	}
	
	/**
	 * Check if postal address new formatted.
	 * 
	 * @return value
	 */
	public boolean isFormattedAddress(){
		if (isEmptyString(this.address) && isEmptyString(this.city) && 
				isEmptyString(this.zip) && isEmptyString(this.state)){
			return false;
		}else{
			return true;
		}			
	}

	private boolean isEmptyString(String value) {
		return value == null || value.equals("");
	}
}
