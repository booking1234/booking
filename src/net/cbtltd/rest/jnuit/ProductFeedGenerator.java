package net.cbtltd.rest.jnuit;

import net.cbtltd.rest.AbstractProduct;
import net.cbtltd.rest.response.PropertyResponse;

public class ProductFeedGenerator extends AbstractProduct {
	private static ProductFeedGenerator productFeedGenerator;
	public ProductFeedGenerator() {}
	
	public static ProductFeedGenerator getInstance() {
		if (productFeedGenerator == null) {
			productFeedGenerator = new ProductFeedGenerator();
		}
		return productFeedGenerator;
	}
	
	public PropertyResponse getDetails(
			String productid,
			String pos,
			String language,
			String date,
			String toDate,
			String currency,
			Boolean test,
			String version,
			String xsl) {
		
		return getPropertyDetail(productid, pos, language, date, toDate, currency, test, version, xsl);
	}
}
