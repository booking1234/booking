/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.task;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface TaskBundle
extends ClientBundle {

	TaskBundle INSTANCE = GWT.create(TaskBundle.class);

	@Source("Task.css")	TaskCssResource css();
	@Source("../image/contacttableEmpty.png")	ImageResource contacttableEmpty();
	@Source("../image/eventjournaltableEmpty.png")	ImageResource eventjournaltableEmpty();
	@Source("../image/shadow_control.png")		ImageResource shadow_control();
	@Source("../image/phone18.png")				ImageResource phone(); 
	@Source("../image/email18.png")				ImageResource email(); 
	@Source("../image/facebook18.png")			ImageResource facebook();
	@Source("../image/sms18.png")				ImageResource sms();
	@Source("../image/twitter18.png")			ImageResource twitter();
}