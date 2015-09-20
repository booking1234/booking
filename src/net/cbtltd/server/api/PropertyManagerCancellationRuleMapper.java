package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.PropertyManagerCancellationRule;

public interface PropertyManagerCancellationRuleMapper {

	void create(PropertyManagerCancellationRule cancellationRule);
	
	PropertyManagerCancellationRule read(Integer id);
	
	List<PropertyManagerCancellationRule> readbypmid(Integer propertyManagerId);
	
	PropertyManagerCancellationRule readbydate(Integer cancelationDate);
	
	List<PropertyManagerCancellationRule> list();
	
	void update(PropertyManagerCancellationRule cancellationRule);
	void delete(Integer id);
	void deletebypmid(Integer propertyManagerId);
	
}
