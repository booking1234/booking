package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Product;

public interface ChannelProductMapMapper {

	public ChannelProductMap readBPProduct(ChannelProductMap tempChannelProductMap);
	public List<ChannelProductMap> readAllBPProduct(ChannelProductMap tempChannelProductMap);
	Integer readChannelProductID(ChannelProductMap tempChannelProductMap);
	ArrayList<Integer> readProductIDs(Integer channelId);
	void createProductMap(ChannelProductMap tempChannelProductMap);
	public List<ChannelProductMap> findByBPProductID(String productID);
	public ChannelProductMap findByBPProductAndChannelId(ChannelProductMap tempChannelProductMap);
}
