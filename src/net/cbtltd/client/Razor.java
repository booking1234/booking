/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;


import net.cbtltd.client.form.AbstractForm;
import net.cbtltd.client.form.AvailableForm;
import net.cbtltd.client.form.OrganizationForm;
import net.cbtltd.client.form.PartyForm;
import net.cbtltd.client.form.ProductForm;
import net.cbtltd.client.form.ReportForm;
import net.cbtltd.client.form.ReservationForm;
import net.cbtltd.client.form.SearchForm;
import net.cbtltd.client.form.SessionForm;
import net.cbtltd.client.form.TaskForm;
import net.cbtltd.client.form.WorkflowForm;
import net.cbtltd.shared.api.HasState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * The Class Razor is the root of the application which defines the forms that are in the main menu.
 */
public class Razor
extends AbstractRoot {
	
	public static final int SESSION_TAB = 0;
	public static final int SEARCH_TAB = 1;
	public static final int WORKFLOW_TAB = 2;
	public static final int AVAILABLE_TAB = 3;
	public static final int RESERVATION_TAB = 4;
	public static final int TASK_TAB = 5;
	public static final int ORGANIZATION_TAB = 6;
	public static final int PRODUCT_TAB = 7;
	public static final int PARTY_TAB = 8;
	public static final int REPORT_TAB = 9;

	/**
	 * Instantiates the forms to be included in the application.
	 * This must match the sessionTabs string array in the FieldConstants file.
	 *
	 * @param index the index number of the form to be instantiated.
	 * @return the instantiated form.
	 */
	protected static AbstractForm<HasState> instantiate(int index) {
		switch(index) {
		case SESSION_TAB: return GWT.create(SessionForm.class);
		case SEARCH_TAB: return GWT.create(SearchForm.class);
		case WORKFLOW_TAB: return GWT.create(WorkflowForm.class);
		case AVAILABLE_TAB: return GWT.create(AvailableForm.class);
		case RESERVATION_TAB: return GWT.create(ReservationForm.class);
		case TASK_TAB: return GWT.create(TaskForm.class);
		case ORGANIZATION_TAB: return GWT.create(OrganizationForm.class);
		case PRODUCT_TAB: return GWT.create(ProductForm.class);
		case PARTY_TAB: return GWT.create(PartyForm.class);
		case REPORT_TAB: return GWT.create(ReportForm.class);
		}
		Window.alert("Unknown class " + index + " - should not happen!");
		return null;
	}
}

