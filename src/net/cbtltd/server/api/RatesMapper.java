package net.cbtltd.server.api;

import net.cbtltd.shared.Rates;

public interface RatesMapper extends AbstractMapper<Rates> {
	
	public Rates fetchRates(Rates rates);

}
