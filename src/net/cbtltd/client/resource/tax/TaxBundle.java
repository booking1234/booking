/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.tax;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface TaxBundle
extends ClientBundle {

	TaxBundle INSTANCE = GWT.create(TaxBundle.class);

	@Source("Tax.css")	TaxCssResource css();
}