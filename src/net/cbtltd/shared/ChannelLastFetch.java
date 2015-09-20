package net.cbtltd.shared;

public class ChannelLastFetch {

	private Integer channelId;
	private String apiName;
	private String lastfetch;
	private String productlId = "0";

	public String getLastfetch() {
		return lastfetch;
	}

	public void setLastfetch(String lastfetch) {
		this.lastfetch = lastfetch;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getProductlId() {
		return productlId;
	}

	public void setProductlId(String productlId) {
		this.productlId = productlId;
	}
}
