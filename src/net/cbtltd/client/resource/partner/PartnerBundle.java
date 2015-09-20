/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.partner;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface PartnerBundle
extends ClientBundle {

	PartnerBundle INSTANCE = GWT.create(PartnerBundle.class);

	@Source("Partner.css")
	PartnerCssResource css();
}