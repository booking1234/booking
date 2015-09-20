/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.product;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ProductBundle
extends ClientBundle {

	ProductBundle INSTANCE = GWT.create(ProductBundle.class);

	@Source("Product.css")	ProductCssResource css();
	
	@Source("../image/assettableEmpty.png")	ImageResource alerttableEmpty();
	@Source("../image/assettableEmpty.png")	ImageResource assettableEmpty();
	@Source("../image/actortableEmpty.png")	ImageResource audittableEmpty();
	@Source("../image/financetableEmpty.png")	ImageResource featuretableEmpty();
	@Source("../image/imagegalleryEmpty.png") ImageResource imagegalleryEmpty();
	@Source("../image/mapfieldEmpty.png") ImageResource mapfieldEmpty();
	@Source("../image/pricetableEmpty.png")	ImageResource pricetableEmpty();
	@Source("../image/servicepricetableEmpty.png") ImageResource servicepricetableEmpty();
	@Source("../image/pricetableEmpty.png")	ImageResource taxtableEmpty();
	@Source("../image/yieldtableEmpty.png")	ImageResource yieldtableEmpty();
}