/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;

/**
 * The Class NameIdRequest is the parent of requests to the server that return NameId lists.
 * Almost every model of a real world entity has both a human readable name and a computer friendly ID.
 * The NameId type implements each name ID pair, and NameIdAction transfers the request to the server.
 * This is often used to populate list fields and other such elements with text (name) and value (ID) attributes.
 * The user can select the required name and the system can then use its ID.
 */
public abstract class NameIdRequest
extends RemoteRequest<NameIdAction, Table<NameId>> {
	
	private NameIdAction action;
	private boolean allOrganizations = false;
	
	/** Instantiates a new name id request. */
	public NameIdRequest() {}

	/**
	 * Instantiates a new name id request with the specified action.
	 *
	 * @param action the specified action.
	 */
	public NameIdRequest(NameIdAction action) {
		this.action = action;
	}

	/**
	 * Sets the requested action.
	 *
	 * @param action the new action.
	 */
	public void setAction(NameIdAction action) {
		this.action = action;
	}

	/**
	 * Gets the requested action.
	 *
	 * @return the requested action.
	 */
	public NameIdAction getAction() {
		return action;
	}
	
	/**
	 * Checks if there is no action.
	 *
	 * @return true, if if there is no action.
	 */
	public boolean noAction() {
		return action == null;
	}
	
	/**
	 * Sets if the items of all organizations are to be included in the requested list.
	 * Normally the list contains only items for the current organization.
	 * Set the parameter to true if the list is to contain items for all organizations. 
	 *
	 * @param allOrganizations is true if the list is to contain items for all organizations. 
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		this.allOrganizations = allOrganizations;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.RemoteRequest#execute(boolean)
	 */
	public boolean execute(boolean force) {
		return execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.RemoteRequest#execute(java.lang.Object)
	 */
	public void execute(NameIdAction action) {
		this.action = action;
		execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.RemoteRequest#cancel()
	 */
	public void cancel() {} // never used

	/* (non-Javadoc)
	 * @see net.cbtltd.client.RemoteRequest#execute()
	 */
	public boolean execute() {
		if (allOrganizations) {action.setOrganizationid(null);}
		else {action.setOrganizationid(AbstractRoot.getOrganizationid());}
		super.send(action);
		return false;
	}
}
