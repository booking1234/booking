package net.cbtltd.server.api;

import net.cbtltd.shared.PriceExt;

public interface PriceExtMapper extends AbstractMapper<PriceExt> {
	void delete(String priceID);
}
