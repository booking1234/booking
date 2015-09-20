package com.mybookingpal.eventbus;

import java.util.List;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;

public class PriceEvent {
	
	Price price;
	List<ChannelProductMap> channelProductMaps;
	
	public PriceEvent(Price price,List<ChannelProductMap> channelProductMaps) {
		this.price = price;
		this.channelProductMaps=channelProductMaps;
	}

	public Price getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	public List<ChannelProductMap> getChannelProductMaps() {
		return channelProductMaps;
	}

	public void setChannelProductMaps(List<ChannelProductMap> channelProductMaps) {
		this.channelProductMaps = channelProductMaps;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}
