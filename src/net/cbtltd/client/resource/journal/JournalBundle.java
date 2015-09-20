/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.journal;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface JournalBundle
extends ClientBundle {

	JournalBundle INSTANCE = GWT.create(JournalBundle.class);

	@Source("Journal.css")
	JournalCssResource css();
}