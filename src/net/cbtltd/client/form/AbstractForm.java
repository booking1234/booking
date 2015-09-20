/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRequest;
import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.MessagePopup;
import net.cbtltd.client.field.PasswordField;
import net.cbtltd.client.field.ProgressField;
import net.cbtltd.client.panel.AboutPopup;
import net.cbtltd.client.panel.SearchPopup;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldConstants;
import net.cbtltd.client.resource.FieldCssResource;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.session.PasswordUpdate;
import net.cbtltd.shared.session.SessionAutoLogin;
import net.cbtltd.shared.session.SessionLogout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mybookingpal.config.RazorConfig;

/**
 * The Class AbstractForm is the parent of all concrete form classes to display and change a value of type T.
 * The type T must extend HasState because every form has a finite state machine to help manage its appearance
 * and behaviour according to its current state. A form contains components such as fields and buttons which
 * implement this appearance and behaviour. AbstractForm extends the GWT DockLayoutPanel which allows its child
 * widgets to be docked at its outer edges, and for its last widget to take up the remaining space in its centre.
 * It attaches its header, including its main menu, at the top (north) and its optional information
 * bar at the bottom (south). Its data entry or detail panel is typically attached to the left (west) side, and
 * other widgets populate the remainder of the form.
 * It also implements HasComponents so that it can contain Component types such as fields and buttons.
 *
 * @param <T> the type, which extends the HasState interface, value displayed and changed by this class.
 * @see com.google.gwt.user.client.ui.DockLayoutPanel
 * @see http://www.htmlcodetutorial.com/linking/linking_famsupp_120.html for Window parameters
 * @see net.cbtltd.client.HasComponents
 */
public abstract class AbstractForm<T extends HasState>
extends DockLayoutPanel
implements HasComponents {

	/* Request to log in to the server automatically. */
	private final GuardedRequest<Session> sessionAutologin = new GuardedRequest<Session>() {
		protected boolean error() {return pos == null || pos.isEmpty();}
		protected void send() {super.send(new SessionAutoLogin(pos));}
		protected void receive(Session session) {setValue(session);}
	};

	/**
	 * Sets the point of sale code and attempts to to log into the server automatically.
	 *
	 * @param pos the point of sale code with which to to log into the server.
	 */
	public void setPos(String pos) {
		AbstractForm.pos = pos;
		sessionAutologin.execute();
	}

	/* Sets the attributes of the specified session action. */
	private static Session getValue(Session session) {
		session.setId(AbstractRoot.getSession().getId());
		session.setOrganizationid(AbstractRoot.getSession().getOrganizationid());
		session.setActorid(AbstractRoot.getSession().getActorid());
		session.setEmailaddress(AbstractRoot.getSession().getEmailaddress());
		return session;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	private void setValue(Session session) {
//		Window.alert("AbstractForm setValue " + session);
		if (session == null || session.notState(Session.LOGGED_IN)) {new MessagePanel(Level.ERROR, AbstractField.CONSTANTS.errPOScode());}
		else {
			AbstractRoot.setSession(session);
			setup(0); //TODO:
		}
	}

	/* Remote request to enable automatic login to the server given a valid point of sale code */
	private GuardedRequest<Session> sessionLogout = new GuardedRequest<Session>() {
		protected boolean error() {return false;}
		protected void send() {super.send(getValue(new SessionLogout()));}
		protected void receive(Session session) {
			AbstractRoot.newSession();
			AbstractRoot.render(Razor.SESSION_TAB);
		}
	};

	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;
	private static final FieldCssResource CSS = BUNDLE.css();
	private static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	
	private static final int MAX_SEARCH = 30;
	protected static double NORTH_SIZE = 3.6;
	protected static double SOUTH_SIZE = 2.0;
	protected final Components components = new Components();
	protected final HorizontalPanel header = new HorizontalPanel();
	private final HorizontalPanel footer = new HorizontalPanel();
	private TextBox searchText;
	private boolean isSetup = false;
	private static boolean resetting = false;
	
	/** The finite state machine to control the appearance and behaviour of the form. */
	protected ArrayList<Transition> fsm;
	
	/** The current state of the form, which is used by the finite state machine. */
	protected String state;
	
	/** The old state is the previous state when executing a state transition - the transition is from sate oldsate to state if successful. */
	protected String oldstate;
	
	/** The tab counter which is incremented each time a field or button is added. */
	protected int tab = 0;
	
	/** The optional point of sale code (pos) enables the form to automatically log into the server. */
	protected static String pos;
	
	/**
	 * Checks if the form is a widget and is not part of an application.
	 *
	 * @return true, if the form is a widget and is not part of an application.
	 */
	protected static boolean isWidget() {return pos != null;}
	
	/** Initializes the form and its components. Delay this until the form is actually needed (lazy  loading). */
	protected abstract void initialize();
	
	/**
	 * Gets the permissions needed to include the form in the current session of the application.
	 *
	 * @return the permissions needed to include the form in the current session of the application.
	 */
	public abstract short[] permission();
	
	/**
	 * Gets the current value of the form, which is typically a data transfer object.
	 *
	 * @return the value of the form.
	 */
	public abstract T getValue();
	
	/**
	 * Sets the form to the specified value, which is typically a data transfer object.
	 *
	 * @param value the specified value.
	 */
	public abstract void setValue(T value);

	/** Instantiates a new abstract form with em size units. */
	public AbstractForm() {super(Unit.EM);}

	/**
	 * Checks if the current value of the form (or its components) has changed since the most recent setChanged() even.
	 *
	 * @return true if the value has changed.
	 */
	public boolean hasChanged() {return components.hasChanged();}

	/** Initialize the form if it has not yet been initialized. */
	public final void setup(int index) {
		if (!isSetup) {
			isSetup = true;
			CSS.ensureInjected();
			addStyleName(CSS.cbtGradientBar());
			createHeader(header, index);
			addNorth(header, NORTH_SIZE);
			addSouth(createFooter(footer), SOUTH_SIZE);
			Window.addResizeHandler(new ResizeHandler() {

				Timer resizeTimer = new Timer() {  
					@Override
					public void run() {onResizeForm();}
				};

				@Override
				public void onResize(ResizeEvent event) {
					resizeTimer.schedule(250);
				}
			});
			initialize();
		}
	}

	/**
	 * Executed when window is resized
	 */
	protected void onResizeForm() {}
	
	/**
	 * Create the header for the specified panel.
	 *
	 * @param panel the specified panel.
	 */
	protected void createHeader(HorizontalPanel panel, int index) {
		panel.clear();
		panel.addStyleName(CSS.cbtCommandBar());
		panel.addStyleName(CSS.cbtGradientBar());
		panel.add(createLogo());
		createTabs(panel, index);
		panel.add(createUtilities());
	}

	/* Create the header logo. */
	private FlowPanel createLogo() {
		final FlowPanel logoPanel = new FlowPanel();
		Image logoImage = new Image(BUNDLE.logo());
//		if (AbstractRoot.logo != null) {logoImage = new Image(AbstractRoot.logo);}
		if (AbstractRoot.hasLogo()) {logoImage = new Image(AbstractRoot.getLogo());}
		logoPanel.add(logoImage);
		return logoPanel;
	}

	/* Adds the tabs for which the current session has permission to the specified panel. */
	private void createTabs(HorizontalPanel panel, int this_index) {
		if (this instanceof SessionForm || AbstractRoot.noOrganizationid()) {return;}
		Tab tab = null;
		for (int index = 1; index < CONSTANTS.sessionTabs().length; index++) {
			if (AbstractRoot.permitted(index)) {
				tab = new Tab(index, CONSTANTS.sessionTabs()[index]);
				tab.addStyleName(CSS.cbtCommand());
				tab.addStyleName(CSS.cbtCommandBegin());
				if (index == this_index) {tab.addStyleName(CSS.cbtCommandSelected());}

				tab.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						int index = ((Tab)event.getSource()).getIndex();
						if (hasChanged()) {MessagePopup.getInstance().showYesNo(CONSTANTS.errChangeOK(), index, null);}
						else {AbstractRoot.render(index);}
					}
				});
				panel.add(tab);
			}
		}
		if (tab != null) {tab.addStyleName(CSS.cbtCommandEnd());}
	}

	/* Creates the utility buttons to be added to the header panel. */
	private HorizontalPanel createUtilities() {
		final HorizontalPanel panel = new HorizontalPanel();
		panel.addStyleName(CSS.cbtCommandRight());

		final Label homeButton = new Label(CONSTANTS.homeButton());
		homeButton.setTitle(CONSTANTS.helpHome());
		homeButton.addStyleName(CSS.cbtCommandHyperlink());
		homeButton.setVisible(AbstractRoot.noLogo());
		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(HOSTS.homeUrl(), "_blank",
				"menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});

		Label aboutButton = new Label(CONSTANTS.allAbout());
		aboutButton.setTitle(CONSTANTS.helpAbout());
		aboutButton.setVisible(true);
		aboutButton.addStyleName(CSS.cbtCommandHyperlink());
		aboutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new AboutPopup().center();
			}
		});

		Label passwordButton = new Label(CONSTANTS.allPassword());
		passwordButton.setTitle(CONSTANTS.helpPassword());
		passwordButton.setVisible(!(this instanceof SessionForm));
		passwordButton.addStyleName(CSS.cbtCommandHyperlink());
		passwordButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new PasswordPopup().center();
			}
		});

		Label logoutButton = new Label(CONSTANTS.sessionLogout());
		logoutButton.setVisible(!(this instanceof SessionForm));
		logoutButton.addStyleName(CSS.cbtCommandHyperlink());
		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cookies.removeCookie("sid");
				sessionLogout.execute();
			}
		});

		if (!(this instanceof SessionForm)) {panel.add(createSearch());}
		panel.add(homeButton);
		panel.add(aboutButton);
		panel.add(passwordButton);
		panel.add(logoutButton);
		return panel;
	}

	/* Creates the search box to be added to the header panel. */
	private FlowPanel createSearch() {
		FlowPanel panel = new FlowPanel();
		final Image searchLeft = new Image(BUNDLE.searchLeft());
		final Image searchCenter = new Image(BUNDLE.searchCenter());
		final Image searchLoading = new Image(BUNDLE.searchLoading());
		searchLoading.addStyleName(CSS.cbtCommandSearchLoading());
		searchLoading.setVisible(false);
		
		searchText = new TextBox();
		searchText.addStyleName(CSS.cbtCommandSearchField());
		searchText.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					NameIdRequest searchRequest = new NameIdRequest(new NameIdAction(Service.SESSION, searchText.getText())) {
						public void receive(Table<NameId> response) {
							searchLoading.setVisible(false);
							searchText.setVisible(true);
							if (!ifMessage(response == null || response.noValue(), Level.ERROR, CONSTANTS.searchNoresult(), searchText)) {
								new SearchPopup(response.getValue()).center();
							}
						}
					};
					searchRequest.getAction().setNumrows(MAX_SEARCH);
					searchText.setVisible(false);
					searchLoading.setVisible(true);
					searchRequest.execute();
				}				
			}
		});

		final Image searchRight = new Image(BUNDLE.searchRight());
		searchRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NameIdRequest searchRequest = new NameIdRequest(new NameIdAction(Service.SESSION, searchText.getText())) {
					public void receive(Table<NameId> response) {
						searchText.setVisible(true);
						searchLoading.setVisible(false);
						if (!ifMessage(response == null || response.noValue(), Level.ERROR, CONSTANTS.searchNoresult(), searchText)) {
							new SearchPopup(response.getValue()).center();
						}
					}
				};
				searchRequest.getAction().setNumrows(MAX_SEARCH);
				searchText.setVisible(false);
				searchLoading.setVisible(true);
				searchRequest.execute();
			}
		});

		panel.add(searchLeft);
		panel.add(searchCenter);
		panel.add(searchText);
		panel.add(searchLoading);
		panel.add(searchRight);
		panel.addStyleName(CSS.cbtCommandSearch());
		return panel;
	}

	/* 
	 * Creates the footer region to be added to the specified panel.
	 * 
	 * @param panel the specified panel. 
	 */
	private HorizontalPanel createFooter(HorizontalPanel panel) {
		panel.addStyleName(CSS.cbtCommandFooter());
		panel.addStyleName(CSS.cbtGradientBar());
//		if (this instanceof SessionForm) {return panel;}
//		final Image image = new Image(BUNDLE.actor());
//		image.addStyleName(CSS.cbtCommandImage());
//		image.setTitle(CONSTANTS.imageHelp());
//		image.setVisible(false);
//		panel.add(image);

//		final FlowPanel up = new FlowPanel();
//		final HorizontalPanel user = new HorizontalPanel();
//		final Label userLabel = new Label(AbstractRoot.getActorname());
//		userLabel.addStyleName(CSS.cbtCommandUser());
//		user.add(userLabel);
//		
//		final Double rank = totalProgress() / 10.0;
//		final Label rankLabel = new Label(CONSTANTS.userRanks()[rank.intValue()]);
//		rankLabel.addStyleName(CSS.cbtCommandRank());
//		user.add(rankLabel);
//		up.add(user);
//		progressField = new ProgressField(this, null, null, 0, 100, tab);
//		progressField.addStyleName(CSS.cbtCommandProgress());
//		progressField.addStyleName(CSS.cbtAbstractCursor());
//		progressField.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				new ProgressPopup().center();
//			}
//		});
//		progressField.setDefaultValue(totalProgress());
//		up.add(progressField);
//		panel.add(up);
		
		final Label copyrightLabel = new Label(CONSTANTS.allCopyright());
		copyrightLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(HOSTS.homeUrl(), "_blank",
				"menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		copyrightLabel.addStyleName(CSS.cbtCommandHyperlink());
		panel.add(copyrightLabel);
		
		final HTML supportLabel =  new HTML("<a href='mailto:info@razor-cloud.com?subject=Feedback' title='Click to send a feedback & support message'>Feedback & Support</a>");
		supportLabel.removeStyleName("a");
		supportLabel.addStyleName(CSS.cbtCommandHyperlink());
		supportLabel.addStyleName(CSS.cbtCommandRight());
		supportLabel.setVisible(AbstractRoot.noLogo());
		panel.add(supportLabel);
		return panel;
	}

	/**
	 * Displays a pop up message if a condition is satisfied.
	 *
	 * @param condition the condition is true if the message is to be displayed.
	 * @param level the level of the message which dictates its importance and sets its colour.
	 * @param text the text to be displayed in the message.
	 * @param target the field or other widget next to which the message is to be displayed.
	 * @return true, if the condition is satisfied.
	 */
	protected boolean ifMessage(boolean condition, Level level, String text, UIObject target) {
		if (condition) {AbstractField.addMessage(level, text, target);}
		return condition;
	}

	/**
	 * Checks if the form is being reset or updated.
	 *
	 * @return true if the form is being reset or updated.
	 */
	protected boolean isResetting() {return resetting;}
	
	/**
	 * Checks if the form is reset and is not being updated.
	 *
	 * @return true, if is reset.
	 */
	protected boolean isReset() {return !isResetting();}
	
	/**
	 * Sets the reset or update status of the form.
	 *
	 * @param resetting is true when a reset or update starts, and false when it ends.
	 */
	public void setResetting(boolean resetting) {AbstractForm.resetting = resetting;}

	/**
	 * Handles change events.
	 *
	 * @param event when changed.
	 */
	@Override
	public void onChange(ChangeEvent event) {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent click) {execute(fsm, click);}
	
	/** Executed when the form gets focus. */
	public void onFocus() {}
	
	/** Executed when the form loses focus. */
	public void onBlur() {}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#addComponent(net.cbtltd.client.Component)
	 */
	public void addComponent(Component component) {components.add(component);}
	
	/**
	 * Removes the specified component from the form.
	 *
	 * @param component the component to be removed.
	 */
	public void removeComponent(Component component) {
		if (components != null) {components.remove(component);}
	}
	
	/**
	 * Resets this form and its components to the specified state.
	 *
	 * @param state the state to which the form is set.
	 */
	public void onReset(String state) {
		setResetting(true);
		components.onReset();
		onStateChange(state);
		setResetting(false);
	}

	/**
	 * Resets the form to the values of the HasResetId having the specified ID.
	 *
	 * @param resetid the ID of the HasResetId to whose value the form is to be set.  
	 */
	public void onReset(HasResetId resetid) {}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onRefresh()
	 */
	public void onRefresh() {components.onRefresh();}
	
	/**
	 * Changes the option of the components of the form to the specified value.
	 *
	 * @param option the specified option value.
	 */
	public void onOptionChange(String option) {components.onOptionChange(option);}
	
	/**
	 * Checks if the form has no current state.
	 *
	 * @return true, if the form has no current state.
	 */
	protected boolean noState() {return state == null;}
	
	/**
	 * Checks if the form had a state prior to the most recent transition.
	 *
	 * @return true, if the form had a state prior to the most recent transition.
	 */
	public boolean hadState(String state) {return this.oldstate != null && this.oldstate.equals(state);}
	
	/**
	 * Checks if the form is in the specified state.
	 *
	 * @param state the specified state
	 * @return true, if the form is in the specified state.
	 */
	public boolean hasState(String state) {return this.state != null && this.state.equals(state);}

	/**
	 * Checks if the form is not in the specified state.
	 *
	 * @param state the specified state
	 * @return true, if the form is not in the specified state.
	 */
	public boolean notState(String state) {return !hasState(state);}

	/**
	 * Handles changes to the form state.
	 *
	 * @param state the new state.
	 */
	protected void onStateChange(String state) {
		this.state = state;
		components.onStateChange(fsm, state);
		components.onOptionChange(state);
	}

	/**
	 * Executes the finite state machine in response to the specified event.
	 *
	 * @param fsm is the finite state machine defining the rules governing state transition.
	 * @param event the event that triggers the transition.
	 */
	private final void execute(ArrayList<Transition> fsm, ClickEvent event) {
		//Window.alert("execute " + state + " " + oldstate + " " + fsm);
		
		if (fsm == null || fsm.isEmpty()) {return;}
		if (GuardedRequest.isSending()) {new MessagePanel(Level.VERBOSE, CONSTANTS.allLoading()).center(); return;}
		for (Transition t : fsm) {
			HasCommand c = t.getCommand();
			AbstractRequest r = c.getRequest();
			oldstate = state;
			if (t.inState(state) && t.is((Widget) event.getSource())) {
				state = t.getToState();
				if (r == null || r.execute(true)) {onStateChange(state);}
				else {state = oldstate;}
				return;
			}
		}
	}

	/* Inner Class PasswordPopup is to change the password when the Password utility in the header is clicked. */
	private class PasswordPopup 
	extends PopupPanel {

		/* Request to update the password on the server. */ 
		private GuardedRequest<Session> passwordUpdate = new GuardedRequest<Session>() {

			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noActorid(), Level.ERROR, CONSTANTS.errActorid(), passwordField)
						|| ifMessage(passwordField.noValue(), Level.ERROR, CONSTANTS.errPassword(), passwordField)
						|| ifMessage(passwordcheckField.noValue(), Level.ERROR, CONSTANTS.errPasswordcheck(), passwordcheckField)
						|| ifMessage(passwordField.notValue(passwordcheckField.getValue()), Level.ERROR, CONSTANTS.errPasswordsUnequal(), passwordcheckField)
				);
			}
			protected void send() {super.send(getPasswordValue());}
			protected void receive(Session session) {hide();}
		};

		private Session getPasswordValue() {
			Session session = new PasswordUpdate();
			session.setId(passwordField.getValue());
			session.setOrganizationid(AbstractRoot.getSession().getOrganizationid());
			session.setActorid(AbstractRoot.getSession().getActorid());
			session.setEmailaddress(AbstractRoot.getSession().getEmailaddress());
			return session;
		}

		private int tab = 0;
		private PasswordField passwordField;
		private PasswordField passwordcheckField;

		/** Instantiates a new password pop up panel. */
		public PasswordPopup() {
			super(true);

			final VerticalPanel form = new VerticalPanel();
			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
			final Label title = new Label(CONSTANTS.passwordtitleLabel());
			title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(title);

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PasswordPopup.this.hide();
					passwordField.clear();
					passwordcheckField.clear();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);

			//-----------------------------------------------
			// Password field
			//-----------------------------------------------
			passwordField = new PasswordField(AbstractForm.this, null,
					CONSTANTS.passwordLabel(),
					tab++);

			passwordField.setSecure(true);
			form.add(passwordField);

			//-----------------------------------------------
			// Check Password field
			//-----------------------------------------------
			passwordcheckField = new PasswordField(AbstractForm.this, null,
					CONSTANTS.checkPasswordLabel(),
					tab++);
			passwordcheckField.setSecure(true);
			form.add(passwordcheckField);

			final HorizontalPanel bar = new HorizontalPanel();
			bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

			Button saveButton = new Button(AbstractField.CONSTANTS.allSave(), new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					passwordUpdate.execute();
				}
			});
			saveButton.addStyleName(AbstractField.CSS.cbtCommandButton());
			saveButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
			saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
			bar.add(saveButton);

			Button cancelButton = new Button(AbstractField.CONSTANTS.allCancel(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {hide();}
			});
			cancelButton.addStyleName(AbstractField.CSS.cbtCommandButton());
			cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
			cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
			bar.add(cancelButton);

			form.add(bar);
		}
	}
	
	/* Gets the total progress to completion of the form. */
	private Integer totalProgress() {
		Double progress = 0.0;
		progress += getProgressActivity() * 0.10;
		progress += getProgressAge() * 0.10;
		progress += getProgressBrochure() * 0.15;
		progress += getProgressConfirm() * 0.15;
		progress += getProgressCreator() * 0.30;
		progress += getProgressValue() * 0.20;
		return progress.intValue();
	}

	private Double getProgressActivity() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressActivity.name());
		Double valuemax = AbstractRoot.getDoubleValue(Party.Value.ProgressActivityMax.name());
		return Math.min(100.0, (value == null || valuemax == null || valuemax == 0.0) ? 0.0 : value * 100 / valuemax);
	}
	
	private Double getProgressAge() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressAge.name());
		Double valuemax = AbstractRoot.getDoubleValue(Party.Value.ProgressAgeMax.name());
		return Math.min(100.0, (value == null || valuemax == null || valuemax == 0.0) ? 0.0 : value * 100 / valuemax);
	}
	
	private Double getProgressBrochure() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressBrochure.name());
		Double valuemax = AbstractRoot.getDoubleValue(Party.Value.ProgressBrochureMax.name());
		return Math.min(100.0, (value == null || valuemax == null || valuemax == 0.0) ? 0.0 : value * 100 / valuemax);
	}
	
	private Double getProgressConfirm() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressConfirm.name());
		return Math.min(100.0, (value == null) ? 0.0 : value * 100);
	}
	
	private Double getProgressCreator() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressCreator.name());
		Double valuemax = AbstractRoot.getDoubleValue(Party.Value.ProgressCreatorMax.name());
		return Math.min(100.0, (value == null || valuemax == null || valuemax == 0.0) ? 0.0 : value * 100 / valuemax);
	}
	
	private Double getProgressValue() {
		Double value = AbstractRoot.getDoubleValue(Party.Value.ProgressValue.name());
		Double valuemax = AbstractRoot.getDoubleValue(Party.Value.ProgressValueMax.name());
		return Math.min(100.0, (value == null || valuemax == null || valuemax == 0.0) ? 0.0 : value * 100 / valuemax);
	}
	
	/* Inner Class ProgressPopup is to display progress in the footer bar of the form. */
	private class ProgressPopup
	extends PopupPanel {

		/** Instantiates a new progress pop up panel. */
		public ProgressPopup() {
			super(true);
			AbstractField.CSS.ensureInjected();
			CSS.ensureInjected();

			final VerticalPanel form = new VerticalPanel();
			final HelpLabel title = new HelpLabel(CONSTANTS.progressTitle(), CONSTANTS.progressHelp(), this);
			title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(title);

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ProgressPopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);
			
			ProgressField progressactivityField = new ProgressField(AbstractForm.this, null, CONSTANTS.progressactivityLabel(), 0, 100, tab);
			progressactivityField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progressactivityField.setHelpText(CONSTANTS.progressactivityHelp());
			progressactivityField.setValue(getProgressActivity().intValue());
			form.add(progressactivityField);

			ProgressField progressageField = new ProgressField(AbstractForm.this, null, CONSTANTS.progressageLabel(), 0, 100, tab);
			progressageField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progressageField.setHelpText(CONSTANTS.progressageHelp());
			progressageField.setValue(getProgressAge().intValue());
			form.add(progressageField);

			ProgressField progressbrochureField = new ProgressField(AbstractForm.this, null, CONSTANTS.progressbrochureLabel(), 0, 100, tab);
			progressbrochureField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progressbrochureField.setHelpText(CONSTANTS.progressbrochureHelp());
			progressbrochureField.setValue(getProgressBrochure().intValue());
			form.add(progressbrochureField);

			ProgressField progressconfirmField = new ProgressField(AbstractForm.this, null, CONSTANTS.progressconfirmLabel(), 0, 100, tab);
			progressconfirmField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progressconfirmField.setHelpText(CONSTANTS.progressconfirmHelp());
			progressconfirmField.setValue(getProgressConfirm().intValue());
			form.add(progressconfirmField);

			ProgressField progresscreatorField = new ProgressField(AbstractForm.this, null, CONSTANTS.progresscreatorLabel(), 0, 100, tab);
			progresscreatorField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progresscreatorField.setHelpText(CONSTANTS.progresscreatorHelp());
			progresscreatorField.setValue(getProgressCreator().intValue());
			form.add(progresscreatorField);

			ProgressField progressvalueField = new ProgressField(AbstractForm.this, null, CONSTANTS.progressvalueLabel(), 0, 100, tab);
			progressvalueField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			progressvalueField.setHelpText(CONSTANTS.progressvalueHelp());
			progressvalueField.setValue(getProgressValue().intValue());
			form.add(progressvalueField);

			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		}
	}
}
