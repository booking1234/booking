/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.session;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface SessionBundle
extends ClientBundle {

	SessionBundle INSTANCE = GWT.create(SessionBundle.class);

	@Source("Session.css") @CssResource.NotStrict SessionCssResource css();
}