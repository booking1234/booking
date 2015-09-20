/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.PasswordField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.session.SessionBundle;
import net.cbtltd.client.resource.session.SessionConstants;
import net.cbtltd.client.resource.session.SessionCssResource;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.session.PasswordCreate;
import net.cbtltd.shared.session.SessionExists;
import net.cbtltd.shared.session.SessionLogin;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class SessionPopup is to log in to the application.
 * Login to create session and maintain a work list for the session.
 * Uses email address and password to establish session, which returns the actor's roles.
 * The roles determine which forms, fields, buttons and code segments can be seen and used.
 * @see <pre>http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ</pre>
 * @see <pre>http://code.google.com/p/gwtoauthlogindemo/</pre>
 * TODO: use OAuth login.
 */
public class SessionPopup
extends AbstractPopup<Session> {

	private static final SessionConstants CONSTANTS = GWT.create(SessionConstants.class);
	private static final SessionBundle BUNDLE = SessionBundle.INSTANCE;
	private static final SessionCssResource CSS = BUNDLE.css();

	private GuardedRequest<Party> partyExists;
	private GuardedRequest<Session> passwordCreate;
	private GuardedRequest<Session> sessionExists;
	private GuardedRequest<Session> sessionLogin;

	private Label passwordcreateButton;
	private TextField emailaddressField;
	private PasswordField passwordField;
	private CheckField autologinField;
	private boolean isPopup = false;
	
	/** Instantiates a new log in (session) pop up panel.
	 */
	public SessionPopup() {
		super(true);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	public void show(boolean isPopup) {
		this.isPopup = isPopup;
		sessionExists.execute();
		center();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (emailaddressField.sent(change)) {
			partyExists.execute();
			Cookies.setCookie("emailaddress", emailaddressField.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	public Session getValue() {return getValue(new Session());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Session getValue(Session session) {
		session.setState(state);
		session.setEmailaddress(emailaddressField.getValue());
		session.setId(passwordField.getValue());
		Log.debug("getValue " + session);
		return session;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Session session) {
		Log.debug("setValue " + session);
		if (session == null || session.hasState(Session.LOGGED_OUT)) {
			AbstractField.addMessage(Level.ERROR, CONSTANTS.loginError(), emailaddressField);
			onReset(Session.LOGGED_OUT);
		}
		else if (session.hasState(Session.SUSPENDED)) {
			AbstractField.addMessage(Level.ERROR, CONSTANTS.suspendedError(), emailaddressField);
			onReset(Session.LOGGED_OUT);
		}
		else {
			onStateChange(session.getState());
			passwordField.clear();
			AbstractRoot.setSession(session);
//			Window.alert("setValue " + session);
			Date expires = new Date(System.currentTimeMillis() + Time.WEEK.milliseconds());//duration remembering login = 7 days
			if (autologinField.getValue()) {Cookies.setCookie("sid", session.getId(), expires);} //, null, "/", false);
			//else {Cookies.removeCookie("sid");}
			if (session.hasPermission(AccessControl.ORGANIZATION_ROLES)) {AbstractRoot.render(Razor.WORKFLOW_TAB);}
			else {AbstractRoot.render(Razor.SEARCH_TAB);}
			hide();
		}
	}

	/*
	 * Gets the specified user (party) action with its attributes set.
	 *
	 * @param action the specified action.
	 * @return the action with its attributes set.
	 */
	private Party getValue(Party party) {
		party.setEmailaddress(emailaddressField.getValue());
		return party;
	}

	/*
	 * Creates the panel of log in fields and buttons.
	 * 
	 * @return the panel of log in fields and buttons.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		form.addStyleName(CSS.magnify());
		

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SessionPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		final HTML helpLabel = new HTML(CONSTANTS.helpLabel());
		helpLabel.addStyleName(AbstractField.CSS.cbtAbstractField());
		helpLabel.addStyleName(CSS.helpLabel());
		form.add(helpLabel);
		
		//-----------------------------------------------
		// Email address to identify user
		//-----------------------------------------------

		emailaddressField = new TextField(this, null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setDefaultValue(Cookies.getCookie("emailaddress"));
		emailaddressField.setFocus(true);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

		//-----------------------------------------------
		// Password field
		//-----------------------------------------------
		passwordField = new PasswordField(this, null,
				CONSTANTS.passwordLabel(),
				tab++);
		passwordField.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				sessionLogin.execute();
			}
		});
		passwordField.setSecure(true);
		passwordField.addStyleName(CSS.passwordField());
		passwordField.setHelpText(CONSTANTS.passwordHelp());
		form.add(passwordField);

		//-----------------------------------------------
		// Request New Password button
		//-----------------------------------------------
		passwordcreateButton = new Label(CONSTANTS.passwordcreateLabel());
		passwordcreateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				passwordCreate.execute();				
			}
		});
		passwordcreateButton.addStyleName(CSS.passwordcreateStyle());
		form.add(passwordcreateButton);

		//-----------------------------------------------
		// Auto Login field
		//-----------------------------------------------
		autologinField = new CheckField(this, null,
				CONSTANTS.autologinLabel(),
				tab++);
		autologinField.setReadOption(Session.LOGGED_OUT, true);
		autologinField.setDefaultValue(false);
		//form.add(autologinField);

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		//-----------------------------------------------
		// Login button
		//-----------------------------------------------
		final CommandButton loginButton = new CommandButton(this, CONSTANTS.loginLabel(), sessionLogin, tab++);
		loginButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		bar.add(loginButton);
		bar.add(autologinField);
		form.add(bar);
		
		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Session.LOGGED_OUT, loginButton, Session.LOGGED_IN));

		onReset(Session.LOGGED_OUT);
		return form;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Check if User Exists
		//-----------------------------------------------
		partyExists = new GuardedRequest<Party>() {
			protected boolean error() {
				return (
						emailaddressField.noValue()
						|| !Party.isEmailAddress(emailaddressField.getValue())
				);
			}
			protected void send() {super.send(getValue(new PartyExists()));}
			protected void receive(Party party){
				ifMessage((party == null), Level.ERROR, CONSTANTS.partyError(), emailaddressField);
			}
		};

		//-----------------------------------------------
		// Check if Session Exists
		//-----------------------------------------------
		sessionExists = new GuardedRequest<Session>() {
			protected boolean error() {return (
					isPopup
					|| Cookies.getCookie("sid") == null 
					|| Cookies.getCookie("sid").isEmpty()
			);}
			protected void send() {super.send(new SessionExists(Cookies.getCookie("sid")));}
			protected void receive(Session session) {if (session != null) {setValue(session);}}
		};

		//-----------------------------------------------
		// Login to Create Session
		//-----------------------------------------------
		sessionLogin = new GuardedRequest<Session>() {
			protected boolean error() {
				return (
						ifMessage(emailaddressField.noValue(), Level.ERROR, CONSTANTS.emailaddressnoneError(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, CONSTANTS.emailaddressinvalidError(), emailaddressField)
						|| ifMessage(passwordField.noValue(), Level.ERROR, CONSTANTS.passwordError(), passwordField)
				);
			}
			protected void send() {super.send(getValue(new SessionLogin()));}
			protected void receive(Session session) {setValue(session);}
		};

		//-----------------------------------------------
		// Create New Password
		//-----------------------------------------------
		passwordCreate = new GuardedRequest<Session>() {
			protected boolean error() {return false;} //return AbstractRoot.noSession() || AbstractRoot.getSession().notState(Session.LOGGED_OUT);}
			protected void send() {super.send(getValue(new PasswordCreate()));}
			protected void receive(Session session) {AbstractField.addMessage(Level.TERSE, CONSTANTS.passwordsentLabel(), passwordField);}
		};
	}
}
