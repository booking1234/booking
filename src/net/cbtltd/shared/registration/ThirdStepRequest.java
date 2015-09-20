package net.cbtltd.shared.registration;

import com.google.gson.annotations.SerializedName;
import net.cbtltd.rest.registration.ProductInfoList;

public class ThirdStepRequest extends RegistrationRequest {

	@SerializedName("products")
	private ProductInfoList products;
	
	public ThirdStepRequest() {
		super();
	}

	public ProductInfoList getProducts() {
		return products;
	}

	public void setProducts(ProductInfoList products) {
		this.products = products;
	}

}
