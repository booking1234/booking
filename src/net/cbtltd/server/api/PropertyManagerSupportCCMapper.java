package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;

public interface PropertyManagerSupportCCMapper {

	void create(PropertyManagerSupportCC item);
	
	PropertyManagerSupportCC readbypartyid(Integer partyId);

	List<PropertyManagerSupportCC> list();

	void update(PropertyManagerSupportCC item);

	void updatebypartyid(PropertyManagerInfo item);
	
	void delete(Integer id);
	
	void deletebypartyid(Integer partyId);
	
}
