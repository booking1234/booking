/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.available;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AvailableBundle
extends ClientBundle {

	AvailableBundle INSTANCE = GWT.create(AvailableBundle.class);

	@Source("Available.css")
	AvailableCssResource css();
	
	@Source("../image/availableEmpty.png")
	ImageResource tableEmpty();
	
	@Source("../image/shadow_control_1200_20.png")
	ImageResource shadow_control_wide();
}