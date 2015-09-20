package net.cbtltd.shared;

public class ChannelProductMap {
	private Integer id;
	private String productId;
	private String channelProductId;
	private String channelRoomId;
	private Integer channelId;
	private String channelRateId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String bpProductId) {
		this.productId = bpProductId;
	}

	public String getChannelProductId() {
		return channelProductId;
	}

	public void setChannelProductId(String channelProductId) {
		this.channelProductId = channelProductId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelRoomId() {
		return channelRoomId;
	}

	public void setChannelRoomId(String channelRoomId) {
		this.channelRoomId = channelRoomId;
	}

	public String getChannelRateId() {
		return channelRateId;
	}

	public void setChannelRateId(String channelRateId) {
		this.channelRateId = channelRateId;
	}
}