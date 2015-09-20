/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.ReportButtonItem;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.report.ReportBundle;
import net.cbtltd.client.resource.report.ReportConstants;
import net.cbtltd.client.resource.report.ReportCssResource;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Mail;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.mail.MailSend;
import net.cbtltd.shared.party.PartyRead;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/** The Class ReportPopup is to generate and display report instances. */
public class ReportPopup
extends AbstractPopup<Mail> {

	private static final ReportConstants CONSTANTS = GWT.create(ReportConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final ReportBundle BUNDLE = ReportBundle.INSTANCE;
	private static final ReportCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Mail> mailSend;
	private static GuardedRequest<Party> partyRead;

	private static SuggestField partyField;
	private static TextField emailaddressField;
	private static TextField emailsubjectField;
	private static TextAreaField emailcontentField;

	private static String url;
	
	/** Instantiates a new report pop up panel. */
	public ReportPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		CSS.ensureInjected();
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}
	
	private static ReportPopup instance;
	
	/**
	 * Gets the single instance of ReportPopup.
	 *
	 * @return single instance of ReportPopup
	 */
	public static synchronized ReportPopup getInstance() {
		if (instance == null) {instance = new ReportPopup();}
		return instance;
	}
	
	/**
	 * Shows the panel for the specified report.
	 *
	 * @param report the specified report.
	 */
	public void show(Report report) {
		if (report == null) {AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errReport(), emailcontentField);}
		else {
			url = HOSTS.reportUrl() + report.getId() + "." + report.getFormat();
			center();
		}
	}

	/**
	 * Shows the panel for the specified report.
	 *
	 * @param report the specified report.
	 */
	public void show(ReportButtonItem report) {
		if (report == null) {AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errReport(), emailcontentField);}
		else {
			url = HOSTS.reportUrl() + report.getId() + "." + report.getFormat();
			center();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (partyField.sent(change)) {partyRead.execute();}
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Mail getValue() {return getValue(new Mail());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Mail getValue(Mail mail) {
		mail.setRecipients(emailaddressField.getValue());
		mail.setSubject(emailsubjectField.getValue());
		String content = emailcontentField.getValue() + "\n" + CONSTANTS.reportContentPre() + url + " " + CONSTANTS.reportContentPost();
		mail.setContent(content);
		Log.debug("getValue " + mail);
		return mail;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Mail mail) {
		Log.debug("setValue " + mail);
		if (mail == null) {return;}
		else {
			setResetting(true);
			emailaddressField.setValue(mail.getRecipients());
			emailcontentField.setValue(mail.getContent());
			emailsubjectField.setValue(mail.getSubject());
			setResetting(false);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Party getValue(Party party) {
		party.setOrganizationid(AbstractRoot.getOrganizationid());
		party.setActorid(AbstractRoot.getActorid());
		party.setId(partyField.getValue());
		Log.debug("getValue " + party);
		return party;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Party party) {
		Log.debug("setValue " + party);
		if (party == null) {onReset(Party.CREATED);}
		else {
			setResetting(true);
			onStateChange(party.getState());
			partyField.setValue(party.getId());
			emailaddressField.setValue(party.getEmailaddresses());
			emailaddressField.setEnabled(hasState(Party.INITIAL) || party.noRoles());
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of report fields.
	 * 
	 * @return the panel of report fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.emailTitle());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ReportPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Party (Addressee) field
		//-----------------------------------------------
		partyField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.addresseeLabel(),
				20,
				tab++);
//		customerField.setType(Party.Type.Customer.name());
		partyField.setHelpText(CONSTANTS.addresseeHelp());

		final Image customerButton = new Image(AbstractField.BUNDLE.plus());
		customerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, partyField, null);
			}
		});
		customerButton.setTitle(CONSTANTS.addresseebuttonHelp());
		partyField.addButton(customerButton);
		form.add(partyField);

		//-----------------------------------------------
		// Email Address field
		//-----------------------------------------------
		emailaddressField = new TextField(this,  null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		form.add(emailaddressField);

		//-----------------------------------------------
		// Email Subject field
		//-----------------------------------------------
		emailsubjectField = new TextField(this,  null,
				CONSTANTS.emailsubjectLabel(),
				tab++);
		emailsubjectField.setMaxLength(100);
		form.add(emailsubjectField);

		//-----------------------------------------------
		// Email Contents field
		//-----------------------------------------------
		emailcontentField = new TextAreaField(this, null,
				CONSTANTS.emailcontentLabel(),
				tab++);
		emailcontentField.setFieldStyle(CSS.emailcontentField());
		emailcontentField.setMaxLength(Text.MAX_TEXT_SIZE);
		form.add(emailcontentField);

		form.add(createCommands());
		
		onRefresh();
		onReset(Report.CREATED);
		return form;
	}

	/*
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final Button sendButton = new Button(CONSTANTS.emailButton(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				mailSend.execute();
			}
		});
		sendButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		sendButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		bar.add(sendButton);

		//-----------------------------------------------
		// Preview button
		//-----------------------------------------------
		final Button previewButton = new Button(CONSTANTS.previewLabel(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open(url, "Report", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		previewButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		previewButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		bar.add(previewButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final Button cancelButton = new Button(AbstractField.CONSTANTS.allCancel(), new ClickHandler() {
			public void onClick(ClickEvent event) {hide();}
		});
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		bar.add(cancelButton);

		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Send Email having URL to Report
		//-----------------------------------------------
		mailSend = new GuardedRequest<Mail>() {

			protected boolean error() {
				return (
						ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(emailsubjectField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errEmailsubject(), emailsubjectField)
						|| ifMessage(emailcontentField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errEmailcontent(), emailcontentField)
				);
			}
			protected void send() {super.send(getValue(new MailSend()));}
			protected void receive(Mail mail){ReportPopup.this.hide();}
		};

		//-----------------------------------------------
		// Read Party 
		//-----------------------------------------------
		partyRead = new GuardedRequest<Party>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(getValue(new PartyRead()));}
			protected void receive(Party response) {setValue(response);}
		};
	}
}
