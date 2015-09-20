/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRequest;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.panel.ReportPopup;
import net.cbtltd.shared.Report;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class ReportButton is a widget to send a request for a report to the ReportService.
 * The report is displayed in a pop up window if the request is successful.
 * The report loading icon is displayed when the request is sent and is hidden when a response is received.
 */
public abstract class ReportButton
extends HorizontalPanel
implements HasCommand {

	/* The JSONP request callback to create the report. */
//	private void getJsonpReport() {
//
//		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
//			String url = HasUrls.JSON_URL
//			+ "?type=" + JSONRequest.REPORT
//			+ "&design=" + getReport().getDesign() 
//			+ "&format=" + getReport().getFormat() 
//			+ "&notes=" + getReport().getNotes() 
//		;
//
//		reportLoader.setVisible(true);
//		jsonp.requestObject(url, new AsyncCallback<ReportButtonItem>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				reportLoader.setVisible(false);
//				throw new RuntimeException("Failure:" + caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(ReportButtonItem response) {
//				reportLoader.setVisible(false);
//				ReportPopup.getInstance().show(response);
//			}
//		});
//	}

	/* 
	 * Executed to send the report parameters to ReportService to create and view the report.
	 * The report must not be null and must have at least have the design of the report.
	 */
	private final GuardedRequest<Report> reportView = new GuardedRequest<Report>() {
		protected boolean error() {return (getReport() == null || getReport().noDesign());}
		protected void send() {super.send(getReport());}
		public void receive(Report report) {ReportPopup.getInstance().show(report);}
	};

	private static Label reportLabel;
//	private static final Image reportLoader = new Image(AbstractField.BUNDLE.loader());
	private boolean enabled = true;
	
	/**
	 * Gets the report to be generated - implement to have the required report paramenters.
	 *
	 * @return the report to be generated.
	 */
	public abstract Report getReport();

	/**
	 * Instantiates a new report button.
	 *
	 * @param form is the form or other HasComponents element that contains the button.
	 * @param label is the optional text to identify the button.  
	 * @param tab index of the button.
	 */
	public ReportButton(HasComponents form, String label, int tab) {
		
		reportLabel = new Label(label);
		reportLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (enabled) {reportView.execute(true);}
			}
		});
		reportLabel.setStylePrimaryName(AbstractField.CSS.cbtCommandReport());
		add(reportLabel);

//		reportLoader.setStylePrimaryName(AbstractField.CSS.cbtButtonReport());
//		reportLoader.setVisible(false);
//		add(reportLoader);
		if (form != null) {form.addComponent(this);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onStateChange()
	 */
	@Override
	public void onStateChange(ArrayList<HasCommand> commands){
		setVisible(commands.contains(this));
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
	public void setChanged() {}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	@Override
	public boolean hasChanged() {return false;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onReset()
	 */
	@Override
	public void onReset(){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onRefresh()
	 */
	@Override
	public void onRefresh(){
		setChanged();
	}

	/**
	 * Sets if the button is enabled and can be clicked.
	 *
	 * @param enabled is true if the button can be clicked.
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * Sets if the request must be sent via JSON.
	 *
	 * @param enabled is true if the request must be sent via JSON.
	 */
//	public void setJson(boolean json) {
//		this.json = json;
//	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		reportLabel.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#setText(java.lang.String)
	 */
	public void setText(String text) {
		reportLabel.setText(text);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#getText()
	 */
	public String getText() {
		return reportLabel.getText();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#getRequest()
	 */
	public AbstractRequest getRequest() {
		return null; //request;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasCommand#is(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean is(Widget sender){
		return (sender == this);
	}
}
