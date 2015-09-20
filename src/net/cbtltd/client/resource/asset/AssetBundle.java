/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.asset;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AssetBundle
extends ClientBundle {

	AssetBundle INSTANCE = GWT.create(AssetBundle.class);

	@Source("Asset.css") AssetCssResource css();
	@Source("../image/imagegalleryEmpty.png")	ImageResource imagegalleryEmpty();
	@Source("../image/mapfieldEmpty.png")	ImageResource mapfieldEmpty();
	@Source("../image/pricetableEmpty.png")	ImageResource pricetableEmpty();
	@Source("../image/servicepricetableEmpty.png")	ImageResource servicepricetableEmpty();
	@Source("../image/yieldtableEmpty.png")	ImageResource yieldtableEmpty();
}