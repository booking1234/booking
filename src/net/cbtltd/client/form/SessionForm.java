/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.SessionPopup;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.session.SessionBundle;
import net.cbtltd.client.resource.session.SessionConstants;
import net.cbtltd.client.resource.session.SessionCssResource;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.party.Agent;
import net.cbtltd.shared.party.Organization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mybookingpal.config.RazorConfig;

public class SessionForm
extends AbstractForm<Session> {

	private static final SessionConstants CONSTANTS = GWT.create(SessionConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final SessionBundle BUNDLE = SessionBundle.INSTANCE;
	private static final SessionCssResource CSS = BUNDLE.css();

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.NO_LOGIN_PERMISSION;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Session getValue() {return getValue(new Session());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	public Session getValue(Session session) {return null;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Session session) {}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
	@Override
	public void initialize() {
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();

		final ScrollPanel scroll = new ScrollPanel();
		add(scroll);
		final HorizontalPanel panel = new HorizontalPanel();
		//panel.addStyleName(AbstractField.CSS.cbtAbstractForm());
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		scroll.add(panel);
		
		final FlowPanel west = new FlowPanel();
		panel.add(west);
		final FlowPanel sessionForm = new FlowPanel();
		sessionForm.addStyleName(AbstractField.CSS.cbtAbstractControl());
		sessionForm.addStyleName(CSS.formStyle());
		sessionForm.addStyleName(CSS.magnify());
		west.add(sessionForm);
		
		final Frame frame = new Frame(HOSTS.cloudUrl());
		frame.setStylePrimaryName(CSS.frameStyle());

		panel.add(frame);

		//-----------------------------------------------
		// Log In button
		//-----------------------------------------------
		final HTML loginButton = new HTML(CONSTANTS.loginLabel());
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new SessionPopup().center();
			}
		});
		loginButton.addStyleName(CSS.sessionButton());
		loginButton.addStyleName(CSS.loginButton());
		loginButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		sessionForm.add(loginButton);

		final Label registerLabel = new Label(CONSTANTS.registerLabel());
		registerLabel.addStyleName(CSS.registerLabel());
		sessionForm.add(registerLabel);
		
		//-----------------------------------------------
		// Register Agent button
		//-----------------------------------------------
		final HTML registeragentButton = new HTML(CONSTANTS.registeragentLabel());
		registeragentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AbstractRoot.renderTabs(Razor.ORGANIZATION_TAB, new Agent());
			}
		});
		registeragentButton.addStyleName(CSS.sessionButton());
		registeragentButton.addStyleName(CSS.registerButton());
		registeragentButton.addStyleName(AbstractField.CSS.cbtGradientBase());
		sessionForm.add(registeragentButton);

		//-----------------------------------------------
		// Register Manager button
		//-----------------------------------------------
		final HTML registermanagerButton = new HTML(CONSTANTS.registermanagerLabel());
		registermanagerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AbstractRoot.renderTabs(Razor.ORGANIZATION_TAB, new Organization());
			}
		});
		registermanagerButton.addStyleName(CSS.sessionButton());
		registermanagerButton.addStyleName(CSS.registerButton());
		registermanagerButton.addStyleName(AbstractField.CSS.cbtGradientBase());
		sessionForm.add(registermanagerButton);
		
		//-----------------------------------------------
		// Register Affiliate button
		//-----------------------------------------------
		final HTML registercreatorButton = new HTML(CONSTANTS.registercreatorLabel());
		registercreatorButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Affiliate, null, null);
			}
		});
		registercreatorButton.addStyleName(CSS.sessionButton());
		registercreatorButton.addStyleName(CSS.registerButton());
		registercreatorButton.addStyleName(AbstractField.CSS.cbtGradientBase());
		sessionForm.add(registercreatorButton);
		
		Label infoLabel = new Label(CONSTANTS.infoLabel());
		infoLabel.addStyleName(CSS.registerLabel());
		sessionForm.add(infoLabel);

		//-----------------------------------------------
		// More Info button
		//-----------------------------------------------
		final HTML infoButton = new HTML(CONSTANTS.infoButton());
		infoButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(HOSTS.infoUrl(), CONSTANTS.infoTitle(), "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		infoButton.addStyleName(CSS.sessionButton());
		infoButton.addStyleName(CSS.registerButton());
		infoButton.addStyleName(AbstractField.CSS.cbtGradientBase());
		sessionForm.add(infoButton);
		
		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		west.add(shadow);

//		if(getUserAgent().contains("msie")) {
//			loginButton.setVisible(false);
//			registerLabel.setVisible(false);
//			registeragentButton.setVisible(false);
//			registermanagerButton.setVisible(false);
//			sessionError.setVisible(true);
//		}
		onRefresh();
		onReset(Session.LOGGED_OUT);
	}

	/*
	 * Gets the browser type.
	 * 
	 * @return browser type
	 */
	private static native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
}
