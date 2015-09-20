/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRequest;
import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldCssResource;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class CommandButton is a widget that has an associated request which is executed when clicked.
 * It may be used in a finite state machine as the command element of a state transition object.
 * The finite state machine controls its visibility so that it is only visible in states from which it can transition.
 * @see <pre>net.cbtltd.client.form.AbstractForm#execute(ArrayList<Transition>, ClickEvent)</pre>
 * 
 * The default format is a button using CSS3 styles instead of background images.
 * @see net.cbtltd.client.resource.Field.css#cbtCommandButton
 * 
 * The colour of the button is changed by adding a gradient colour style:
 * @see net.cbtltd.client.resource.Field.css#cbtGradientBlue
 * 
 * The size of the button is changed by adding a width style:
 * @see net.cbtltd.client.resource.Field.css#cbtCommandButtonFour
 * 
 * It may also have a different styling to make it appear as a label, hyperlink, etc. 
 * @see net.cbtltd.client.resource.Field.css#cbtCommandHyperlink
 */
public class CommandButton
extends Button
implements HasCommand, ClickHandler {

	private static final FieldCssResource CSS = FieldBundle.INSTANCE.css();
	private final AbstractRequest request;
	private boolean visiblealways = false;
	
	/**
	 * Instantiates a new command button.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param label is the optional text to identify the field.  
	 * @param request the action to be executed when the widget is clicked.
	 * @param tab index of the field.
	 */
	public CommandButton(HasComponents form, String label, AbstractRequest request, int tab) {
		this(form, null, label, request, tab);
	}
	
	/**
	 * Instantiates a new command button.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param request the action to be executed when the widget is clicked.
	 * @param tab index of the field.
	 */
	public CommandButton (
			HasComponents form,
			short[] permission,
			String label,
			AbstractRequest request,
			int tab) {
		super(label);
		CSS.ensureInjected();
		this.request = request;
		setTabIndex(tab++);
		if (form != null) {
			addClickHandler(form);
			form.addComponent(this);
		}
		setStylePrimaryName(CSS.cbtCommandButton());
		setEnabled(AbstractRoot.writeable(permission));
	}

	/**
	 * Handles click events.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onStateChange()
	 */
	@Override
	public void onStateChange(ArrayList<HasCommand> commands) {
		super.setVisible(visiblealways || commands.contains(this));
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onOptionChange()
	 */
	@Override
	public void onOptionChange(String option){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#setChanged()
	 */
	@Override
	public void setChanged(){}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	@Override
	public boolean hasChanged(){return false;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onRefresh()
	 */
	@Override
	public void onRefresh(){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onReset()
	 */
	@Override
	public void onReset(){}

	/**
	 * Sets if the widget is visible irrespective of the state of its container.
	 *
	 * @param visiblealways is true is the widget is visible irrespective of the its container's state.
	 */
	public void setVisiblealways(boolean visiblealways) {
		this.visiblealways = visiblealways;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#getRequest()
	 */
	public AbstractRequest getRequest() {
		return request;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
}
