package net.cbtltd.shared;

public class ChannelDistribution {

	public ChannelDistribution(){

	}
	
	private String channelname; 
	private String channelcommission; 


	private String channel_id; 
	private String property_manager_id; 
	
	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	
	public String getChannelcommission() {
		return channelcommission;
	}

	public void setChannelcommission(String channelcommission) {
		this.channelcommission = channelcommission;
	}
	
	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getProperty_manager_id() {
		return property_manager_id;
	}

	public void setProperty_manager_id(String property_manager_id) {
		this.property_manager_id = property_manager_id;
	}

	public String toCSVFormat(){
	 	return channelname +" , " + channelcommission + " , " ;
		//return channelname +" , " + channelcommission + " , " +"channel_Id = "+channel_id;
	}
	@Override
	public String toString() {
		return "ChannelDistribution [channelname=" + channelname
				+ ", commission=" + channelcommission + ", channel_id=" + channel_id
				+ ", property_manager_id=" + property_manager_id + "]";
	}

}
