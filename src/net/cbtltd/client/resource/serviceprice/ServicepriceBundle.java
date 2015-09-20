/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.serviceprice;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface ServicepriceBundle
extends ClientBundle {

	ServicepriceBundle INSTANCE = GWT.create(ServicepriceBundle.class);

	@Source("Serviceprice.css")	ServicepriceCssResource css();
}