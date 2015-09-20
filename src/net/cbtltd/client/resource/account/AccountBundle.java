/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.account;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface AccountBundle
extends ClientBundle {

	AccountBundle INSTANCE = GWT.create(AccountBundle.class);

	@Source("Account.css")
	AccountCssResource css();
}