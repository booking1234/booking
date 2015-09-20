/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.organization;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface OrganizationBundle
extends ClientBundle {

	OrganizationBundle INSTANCE = GWT.create(OrganizationBundle.class);
	
	@Source("Organization.css")	OrganizationCssResource css();
	@Source("../image/actortableEmpty.png")	ImageResource actortableEmpty();
	@Source("../image/contracttableEmpty.png")	ImageResource contracttableEmpty();
	@Source("../image/financetableEmpty.png")	ImageResource financetableEmpty();
	@Source("../image/contracttableEmpty.png")	ImageResource licensetableEmpty();
	@Source("../image/shadow_control_600_20.png")	ImageResource shadow_control_wide();
	@Source("../image/contracttableEmpty.png")	ImageResource partnertableEmpty();
	@Source("../image/pricetableEmpty.png")	ImageResource taxtableEmpty();
}