package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.PropertyManagerInfo;

public interface PropertyManagerInfoMapper {

	void create(PropertyManagerInfo property);
	
	PropertyManagerInfo readbypmid(Integer pmID);

	List<PropertyManagerInfo> list();

	void update(PropertyManagerInfo item);

	void updatebypmid(PropertyManagerInfo item);
}
