package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name="credit_card_types")
public class CreditCardTypes {
	
	@SerializedName("american_express")
	private Boolean americanExpress;
	private Boolean discover;
	@SerializedName("diners_club")
	private Boolean dinersClub;
	private Boolean jbc;
	@SerializedName("master_card")
	private Boolean masterCard;
	private Boolean visa;
	private Boolean none;
	
		
	public CreditCardTypes() {
		super();
	}

	public Boolean isNone(){
		if (americanExpress == null && discover == null && dinersClub == null && 
				jbc == null && masterCard == null && visa == null){
			return true;
		}
		
		return false;
	}
	
	@XmlElement(name = "american_express")
	public Boolean getAmericanExpress() {
		return americanExpress;
	}

	public void setAmericanExpress(Boolean americanExpress) {
		this.americanExpress = americanExpress;
	}
	
	@XmlElement(name = "discover")
	public Boolean getDiscover() {
		return discover;
	}
	public void setDiscover(Boolean discover) {
		this.discover = discover;
	}
	
	@XmlElement(name = "diners_club")
	public Boolean getDinersClub() {
		return dinersClub;
	}
	public void setDinersClub(Boolean dinersClub) {
		this.dinersClub = dinersClub;
	}
	
	@XmlElement(name = "jbc")
	public Boolean getJbc() {
		return jbc;
	}
	public void setJbc(Boolean jbc) {
		this.jbc = jbc;
	}
	
	@XmlElement(name = "master_card")
	public Boolean getMasterCard() {
		return masterCard;
	}
	public void setMasterCard(Boolean masterCard) {
		this.masterCard = masterCard;
	}
	
	@XmlElement(name = "visa")
	public Boolean getVisa() {
		return visa;
	}
	public void setVisa(Boolean visa) {
		this.visa = visa;
	}

	@XmlElement(name = "none")
	public Boolean getNone() {
		return none;
	}

	public void setNone(Boolean none) {
		this.none = none;
	}	
	
}
