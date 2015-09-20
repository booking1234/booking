/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.rate;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface RateBundle
extends ClientBundle {

	RateBundle INSTANCE = GWT.create(RateBundle.class);

	@Source("Rate.css")	RateCssResource css();
}