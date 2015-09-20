/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.yield;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface YieldBundle
extends ClientBundle {

	YieldBundle INSTANCE = GWT.create(YieldBundle.class);

	@Source("Yield.css")	YieldCssResource css();
}