/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.finance;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface FinanceBundle
extends ClientBundle {

	FinanceBundle INSTANCE = GWT.create(FinanceBundle.class);

	@Source("Finance.css")
	FinanceCssResource css();
}