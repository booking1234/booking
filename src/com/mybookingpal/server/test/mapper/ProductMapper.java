package com.mybookingpal.server.test.mapper;

import java.util.ArrayList;

import net.cbtltd.server.api.AbstractMapper;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Product;

public interface ProductMapper 
extends AbstractMapper<Product> {
	
	/* test image links*/
	ArrayList<String> activeProductIdListBySupplier(NameIdAction altpartyid);
	ArrayList<String> activeproductidListbyChannelPartner(NameIdAction altpartyid);
	
	/* Integeration check*/
	ArrayList<String> productIdListWithEmptyValuesBySupplierId(String supplierId);
	ArrayList<String> getProductsWithNoCancellationPolicy();
	ArrayList<String> activeproductid();
}
