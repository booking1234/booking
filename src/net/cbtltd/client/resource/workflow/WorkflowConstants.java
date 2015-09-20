/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.workflow;

import com.google.gwt.i18n.client.Constants;

public interface WorkflowConstants
extends Constants {

	String actorAll();
	String actorHelp();
	String actorLabel();
	String dateLabel();
	String serviceLabel();
	String maintenanceLabel();
	String marketingLabel();
	String reservationLabel();
	String progressHelp();
	String progressLabel();
	String selectorHelp();
	String selectorLabel();
	String workflowtableEmpty();
	String titleLabel();
	
	String[] maintenanceStates();
	String[] marketingStates();
	String[] reservationStates();
	String[] serviceStates();
	String[] workitemHeaders();
	String[] workStates();
}
