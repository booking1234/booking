package net.cbtltd.rest.webchalet;

import java.util.List;


public class LocationWC {
	private List<Double> latlng;
	private String street_address;
	
	public List<Double> getLatLng(){
		return this.latlng;
	}
	
	public String getAddressDetails(){
		return this.street_address;
	}
	
	public String getAddress(){
		return this.street_address.substring(0, this.street_address.indexOf(','));
	}
	
	public String getCity(){
		String modifiedString = this.street_address.substring(this.street_address.indexOf(',')+1,this.street_address.length());
		return modifiedString.substring(1, modifiedString.indexOf(','));
	}
	
	public String getState(){
		String modifiedString = this.street_address.substring(this.street_address.indexOf(',')+1,this.street_address.length());
		modifiedString = modifiedString.substring(modifiedString.indexOf(',')+1,modifiedString.length());
		return modifiedString.substring(1, 3);
	}
	
	public String getZip(){
		String modifiedString = this.street_address.substring(this.street_address.indexOf(',')+1,this.street_address.length());
		modifiedString = modifiedString.substring(modifiedString.indexOf(',')+1,modifiedString.length());
		return modifiedString.substring(4, modifiedString.indexOf(','));
	}
	
	public Double getLatitude(){
		return this.getLatLng().get(0);
	}
	
	public Double getLongitude(){
		return this.getLatLng().get(1);
	}
	
	

}