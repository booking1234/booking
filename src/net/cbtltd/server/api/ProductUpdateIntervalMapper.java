package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.ProductUpdateInterval;

public interface ProductUpdateIntervalMapper {
	List<ProductUpdateInterval> getProductUpdateInterval(String partnerid);

}
