package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.ChannelPartner;

public interface ChannelPartnerMapper  {

	ChannelPartner read (Integer id);	
	List<ChannelPartner> list();
	void delete(Integer id);
	void create(ChannelPartner action);	
	void update(ChannelPartner partner);	
	
	ChannelPartner readBySupplierId(int supplierId);
	ChannelPartner readByPartyId(int partyId);
	List<String> readRelatedManagersByPartyId(int partyId);
	List<Integer> ManagerByChannelList(int channelID);
}
