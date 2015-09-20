/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.workflow;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface WorkflowBundle
extends ClientBundle {

	WorkflowBundle INSTANCE = GWT.create(WorkflowBundle.class);

	@Source("Workflow.css")	WorkflowCssResource css();
	@Source("../image/workflowEmpty.png")	ImageResource workflowtableEmpty();
	@Source("../image/shadow_control.png")	ImageResource shadow_control();
}