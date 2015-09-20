/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.location;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface LocationBundle
extends ClientBundle {

	LocationBundle INSTANCE = GWT.create(LocationBundle.class);

	@Source("Location.css")
	LocationCssResource css();
}