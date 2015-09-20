package net.cbtltd.rest.registration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.shared.ChannelPartner;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelPartnerResponse {

	@XmlElement(name = "step_data")
	private AdditionalParams stepData;
	private ApiResponse status;
	@XmlElement(name = "channel_partners")
	private List<ChannelPartner> channelPartners;

	@XmlElement(name = "support_credit_card")
	@SerializedName("support_credit_card")
	private Boolean supportsCreditCard = false;
	
	public ApiResponse getStatus() {
		return status;
	}

	public void setStatus(ApiResponse status) {
		this.status = status;
	}

	public List<ChannelPartner> getChannelPartners() {
		return channelPartners;
	}

	public void setChannelPartners(List<ChannelPartner> channelPartners) {
		this.channelPartners = channelPartners;
	}

	public AdditionalParams getParams() {
		return stepData;
	}

	public void setParams(AdditionalParams stepData) {
		this.stepData = stepData;
	}

	public Boolean getSupportsCreditCard() {
		return supportsCreditCard;
	}

	public void setSupportsCreditCard(Boolean supportsCreditCard) {
		this.supportsCreditCard = supportsCreditCard;
	}

}
