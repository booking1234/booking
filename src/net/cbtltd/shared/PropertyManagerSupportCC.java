package net.cbtltd.shared;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XmlRootElement(name="support_cc")
public class PropertyManagerSupportCC {
//	@XStreamOmitField 
	private Integer id; // auto id ;
//	@XStreamOmitField 
	private Integer partyId; // id of PM from 'party' table;
	private Boolean supportMC; // is support Master Card cards;
	private Boolean supportVISA; // is support VISA cards;
	private Boolean supportAE; // is support AMERICAN EXPRESS cards;
	private Boolean supportDISCOVER; // is support `DISCOVER` cards;
	private Boolean supportDINERSCLUB; // is support `DINES CLUB` cards;
	private Boolean supportJCB; // is support `JCB` cards;
	private Boolean none; // PM does not support CC payments

	@XmlTransient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public Boolean getSupportMC() {
		return supportMC;
	}

	public void setSupportMC(Boolean supportMC) {
		this.supportMC = supportMC;
	}

	public Boolean getSupportVISA() {
		return supportVISA;
	}

	public void setSupportVISA(Boolean supportVISA) {
		this.supportVISA = supportVISA;
	}

	public Boolean getSupportAE() {
		return supportAE;
	}

	public void setSupportAE(Boolean supportAE) {
		this.supportAE = supportAE;
	}

	public Boolean getSupportDISCOVER() {
		return supportDISCOVER;
	}

	public void setSupportDISCOVER(Boolean supportDISCOVER) {
		this.supportDISCOVER = supportDISCOVER;
	}

	public Boolean getSupportJCB() {
		return supportJCB;
	}

	public void setSupportJCB(Boolean supportJCB) {
		this.supportJCB = supportJCB;
	}

	/**
	 * @return the supportDINERSCLUB
	 */
	public Boolean getSupportDINERSCLUB() {
		return supportDINERSCLUB;
	}

	/**
	 * @param supportDINERSCLUB the supportDINERSCLUB to set
	 */
	public void setSupportDINERSCLUB(Boolean supportDINERSCLUB) {
		this.supportDINERSCLUB = supportDINERSCLUB;
	}

	public Boolean getNone() {
		return none;
	}

	public void setNone(Boolean none) {
		this.none = none;
	}

	public Boolean isNone() {
		if (supportMC == null && supportVISA == null && supportAE == null
				&& supportDISCOVER == null && supportDINERSCLUB == null
				&& supportJCB == null) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyManagerSupportCC [id=");
		builder.append(id);
		builder.append(", \npartyId=");
		builder.append(partyId);
		builder.append(", \nsupportMC=");
		builder.append(supportMC);
		builder.append(", \n=supportVISA=");
		builder.append(supportVISA);
		builder.append(", \nsupportAE=");
		builder.append(supportAE);
		builder.append(", \nsupportDISCOVER=");
		builder.append(supportDISCOVER);
		builder.append(", \n=supportDINERSCLUB");
		builder.append(supportDINERSCLUB);
		builder.append(", \nsupportJCB=");
		builder.append(supportJCB);
		builder.append(", \nnone=");
		builder.append(none);
		builder.append("]");
		return builder.toString();
	}

}
