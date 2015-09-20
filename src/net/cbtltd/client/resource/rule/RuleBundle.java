/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.rule;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface RuleBundle
extends ClientBundle {

	RuleBundle INSTANCE = GWT.create(RuleBundle.class);

	@Source("Rule.css")
	RuleCssResource css();
}