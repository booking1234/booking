package net.cbtltd.rest.laketahoeaccommodations;

import net.cbtltd.rest.Items;
import net.cbtltd.rest.ProductRest;
import net.cbtltd.rest.Property;

public class Retriever extends ProductRest {
	private static Retriever retriever;
	
	private Retriever() {}
	public static Retriever getInstance() {
		if (retriever == null) {
			retriever = new Retriever();
		}
		return retriever;
	}
	
	public Items getImagesUsingAPI(String productid, String pos, Boolean test, String version, String xsl) {
		return getImagesByIdXML(productid, pos, test, version, xsl);
	}
	
	public Property getProductUsingAPI(String productid, String pos, boolean test, String language, String version, String xsl) {
		return getProductNoLanguage(productid, pos, test, language, version, xsl);
	}
}
