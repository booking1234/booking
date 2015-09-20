package com.mybookingpal.scripts.propertydistribution;

public class PropertyManager {

	Double commision;
	String propertycout;
	String name;
	String pmssoftware;
    String pmpartyid; 
    
	public PropertyManager(Double commision, String propertycout, String name, String pmssoftware , String pmpartyid) {
		
		super();
		this.commision = commision;
		this.propertycout = propertycout;
		this.name = name;
		this.pmssoftware = pmssoftware;
		this.pmpartyid = pmpartyid; 
	}

	public PropertyManager() {

	}

	public Double getCommision() {
		return commision;
	}

	public void setCommision(Double commision) {
		this.commision = commision;
	}

	public String getPropertycout() {
		return propertycout;
	}

	public void setPropertycout(String propertycout) {
		this.propertycout = propertycout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPmssoftware() {
		return pmssoftware;
	}

	public void setPmssoftware(String pmssoftware) {
		this.pmssoftware = pmssoftware;
	}
	
	public String toActiveCSVFormat(){
		return " "+ name +" , "+pmssoftware+" , "+ commision + " , " + propertycout + " , " + propertycout + "pmid:"+pmpartyid;
	}
	
	//their properties are not being distributed. 
	public String toInActiveCSVFormat(){
		return " "+ name +" , "+pmssoftware+" , "+ commision + " , " + propertycout + " , " + "0"+ "pmid:"+pmpartyid;
	}

	@Override
	public String toString() {
		return "PropertyManager [commision=" + commision + ", propertycout="
				+ propertycout + ", name=" + name + ", pmssoftware="
				+ pmssoftware + ", pmpartyid=" + pmpartyid + "]";
	}

}
