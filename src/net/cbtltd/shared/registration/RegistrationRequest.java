package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.Model;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {
	
	@SerializedName("step")
	@XmlElement(name = "step")
	private Integer step;
	@SerializedName("next_step")
	@XmlElement(name = "next_step")
	private Boolean isNextStep;
	@SerializedName("pos")
	@XmlElement(name = "pos")
	private String pos;
	private Integer pmId = null;

	public RegistrationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getPmId() {
		if (pmId.equals(null)) {
			if (!this.getPos().isEmpty())
				pmId = Integer.valueOf(Model.decrypt(this.getPos()));
		}
		return pmId;
	}

	public Integer getNextStep() {
		if (this.isNextStep != null && this.isNextStep){
			return this.getStep() + 1;
		}
		else {
			return this.step;
		}
	}

	public Integer getStep() {
		return step;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

}
