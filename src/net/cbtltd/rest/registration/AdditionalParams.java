package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;


@XmlRootElement(name = "step_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalParams {

	@XmlElement(name="pos")
	private String pos;
	
	@XmlElement(name="pms_name")
	private String pmsName;
	
	@XmlElement(name="need_to_login")
	private String needToLogin = "0";
	
	@XmlElement(name="next_step")
	private Integer nextStep;

	public AdditionalParams() {
		super();
	}

	public AdditionalParams(Integer nextStep, String pos, String pmsName) {
		super();
		this.nextStep = nextStep;
		if (pos != "") {
			this.pos = pos;
		}
		if (pmsName != "") {
			this.pmsName = pmsName;
		}
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPmsName() {
		return pmsName;
	}

	public void setPmsName(String pmsName) {
		this.pmsName = pmsName;
	}

	public String getNeedToLogin() {
		return needToLogin;
	}

	public void setNeedToLogin(String needToLogin) {
		this.needToLogin = needToLogin;
	}

	public Integer getNextStep() {
		return nextStep;
	}

	public void setNextStep(Integer nextStep) {
		this.nextStep = nextStep;
	}

}
