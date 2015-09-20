package net.cbtltd.shared;
public class ManagerToChannel {

	private Integer id; 
	private Integer property_manager_id; 
	private Integer channel_partner_id; 

	public ManagerToChannel(){
		
	}
			
	public ManagerToChannel(Integer id,  Integer  property_manager_id,  Integer channel_partner_id) {

		this.id = id;
		this.property_manager_id = property_manager_id; 
		this.channel_partner_id = channel_partner_id; 

	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProperty_manager_id() {
		return property_manager_id;
	}
	public void setProperty_manager_id(Integer property_manager_id) {
		this.property_manager_id = property_manager_id;
	}
	public Integer getChannel_partner_id() {
		return channel_partner_id;
	}
	public void setChannel_partner_id(Integer channel_partner_id) {
		this.channel_partner_id = channel_partner_id;
	}

	@Override
	public String toString() {
		return "ManagerToChannel [id=" + id + ", property_manager_id="
				+ property_manager_id + ", channel_partner_id="
				+ channel_partner_id + "]";
	}


}
