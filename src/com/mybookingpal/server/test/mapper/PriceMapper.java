package com.mybookingpal.server.test.mapper;

import java.util.ArrayList;

import net.cbtltd.server.api.AbstractMapper;
import net.cbtltd.shared.Price;

public interface PriceMapper extends AbstractMapper<Price>
{
	//Integration Check Table test cases
	ArrayList<Price> countDuplicateReservations(String partyId);
	ArrayList<Price> priceEntriesWithInvalidProductId(String supplierId);
}
