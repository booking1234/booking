package net.cbtltd.shared.registration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SecondStepRequest extends RegistrationRequest {

	@SerializedName("channel_partners_list_ids")
	private List<Integer> channel_partners_list_ids;

	public SecondStepRequest() {

	}

	public List<Integer> getChannel_partners_list_ids() {
		return channel_partners_list_ids;
	}

	public void setChannel_partners_list_ids(List<Integer> channel_partners_list_ids) {
		this.channel_partners_list_ids = channel_partners_list_ids;
	}

}
