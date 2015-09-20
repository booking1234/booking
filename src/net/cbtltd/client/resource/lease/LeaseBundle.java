/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.lease;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface LeaseBundle
extends ClientBundle {

	LeaseBundle INSTANCE = GWT.create(LeaseBundle.class);

	@Source("Lease.css")	LeaseCssResource css();
	@Source("../image/eventactiontableEmpty.png")	ImageResource eventactiontableEmpty();
	@Source("../image/financetableEmpty.png")	ImageResource ruletableEmpty();
	@Source("../image/maintenancetableEmpty.png")	ImageResource maintenancetableEmpty();
}