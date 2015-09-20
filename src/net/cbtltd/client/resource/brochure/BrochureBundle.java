/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.brochure;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface BrochureBundle
extends ClientBundle {

	BrochureBundle INSTANCE = GWT.create(BrochureBundle.class);

	@Source("Brochure.css")	BrochureCssResource css();
}