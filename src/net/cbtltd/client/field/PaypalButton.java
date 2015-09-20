/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.shared.Paypal;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class PaypalButton is to submit payment requests to PayPal.
 */
public class PaypalButton
extends PushButton
implements ClickHandler, Component {


//	private final ModelRequest<Paypal> paypalPay = new ModelRequest<Paypal>() {
//		
//		public void send(String type) {
//			getParam().setType(type);
//			send();
//		}
//		
//		public void receive(Paypal paypal) {
//			if (paypal.hasState(Paypal.SUCCESS) && paypal.hasApprovalUrl()
//			){Window.open(paypal.getApprovalUrl(), "PayPal", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");}
//			else {Window.alert("PayPal Message: " + paypal.getApprovalUrl());}
//			//redirect(paypal.getApprovalUrl()); // 
//			fireChange(PaypalButton.this);
//		}		
//	};

	private static Paypal paypal = new Paypal();

	/**
	 * Instantiates a new PayPal button.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param tab index of the field.
	 */
	public PaypalButton(HasComponents form, int tab) {
		this(form, null, null, tab);
	}

	/**
	 * Instantiates a new PayPal button.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param title the text to be displayed when the mouse hovers over the button.
	 * @param tab index of the field.
	 */
	public PaypalButton(HasComponents form, String title, int tab) {
		this(form, null, title, tab);
	}

	/**
	 * Instantiates a new PayPal button.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param title the text to be displayed when the mouse hovers over the button.
	 * @param tab index of the field.
	 */
	public PaypalButton(
			HasComponents form,
			short[] permission,
			String title,
			int tab) {
		super(new Image(FieldBundle.INSTANCE.paypal()));
		paypal.setState(Paypal.CREATED);
		setTitle(title);
		setTabIndex(tab++);
		addClickHandler(this);
		form.addComponent(this);
		setStylePrimaryName(AbstractField.CSS.cbtCommandButton());
		addStyleName(AbstractField.CSS.cbtGradientBlue());
		setEnabled(AbstractRoot.writeable(permission));
	}

	/**
	 * Fires a change event when clicked.
	 *
	 * @param widget that created the click event.
	 */
	protected void fireChange(Widget widget) {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(nativeEvent, widget);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event){
//TODO:		if (event.getSource() == this && paypal != null && paypal.isValid()) {paypalPay.send(paypal);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	@Override
	public boolean hasChanged(){return false;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#setChanged()
	 */
	@Override
	public void setChanged(){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onStateChange(java.util.ArrayList)
	 */
	@Override
	public void onStateChange(ArrayList<HasCommand> commands){
		setVisible(commands.contains(this));
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onOptionChange(java.lang.String)
	 */
	@Override
	public void onOptionChange(String option) {}

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

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#is(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean is(Widget sender) {
		return (sender == this);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#sent(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean sent(ChangeEvent event) {
		return is((Widget)event.getSource());
	}
}

