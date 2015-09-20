/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.audit;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface AuditBundle
extends ClientBundle {

	AuditBundle INSTANCE = GWT.create(AuditBundle.class);

	@Source("Audit.css")	AuditCssResource css();
}