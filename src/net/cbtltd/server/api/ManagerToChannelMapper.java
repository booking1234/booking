package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.ChannelDistribution;
import net.cbtltd.shared.ManagerToChannel;

public interface ManagerToChannelMapper {


	ArrayList<ManagerToChannel> listAllChannelPropertyManagers ();
	ArrayList<ChannelDistribution> propertyManagersDistributingToChannels ();

	ArrayList<ManagerToChannel> listPropertyManagersDistributedChannel(String channel_id); 
	ArrayList<String> listPropertyManagersNotDistributedChannel(String channel_id); 

	ArrayList<String> justPropertyManagersDistributedChannel (String channel_id); 
	
	List<String> listPMNotDistributedToChannelString (List<String> supplierIds);   

}
