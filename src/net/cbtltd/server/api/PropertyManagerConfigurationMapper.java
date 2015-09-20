package net.cbtltd.server.api;

import net.cbtltd.shared.PropertyManagerConfiguration;

public interface PropertyManagerConfigurationMapper {
	void create(PropertyManagerConfiguration item);
	PropertyManagerConfiguration read(Integer id);
	void update(PropertyManagerConfiguration item);
	void delete(Integer id);
}
