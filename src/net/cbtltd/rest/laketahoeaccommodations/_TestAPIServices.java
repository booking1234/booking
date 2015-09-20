package net.cbtltd.rest.laketahoeaccommodations;

import java.util.concurrent.TimeUnit;

import net.cbtltd.rest.Items;
import net.cbtltd.rest.Property;
import net.cbtltd.shared.Language;

// Test API services on Lake Tahoe Accommodations properties


public class _TestAPIServices {

	public static Items readImagesUsingAPI() {
		Retriever retriever = null;
		retriever = Retriever.getInstance();
		return retriever.getImagesUsingAPI("323932", "5e7e3a77b3714ea2", true, null, null);
	}
	
	public static Property readProductsUsingAPI() {
		Retriever retriever = null;
		retriever = Retriever.getInstance();
		return retriever.getProductUsingAPI("323932", "5e7e3a77b3714ea2", true, Language.EN, null, null);
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(readProductsUsingAPI());
	}
	
	

}
