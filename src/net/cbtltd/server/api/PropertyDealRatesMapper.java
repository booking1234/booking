package net.cbtltd.server.api;

import net.cbtltd.shared.PropertyDealRates;

public interface PropertyDealRatesMapper extends AbstractMapper<PropertyDealRates> {

	public PropertyDealRates existsForProductId(PropertyDealRates action);
}
