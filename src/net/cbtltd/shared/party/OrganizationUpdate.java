/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.party;


public class OrganizationUpdate extends Organization {

	private Boolean needToSendEmail = true;

	public Boolean getNeedToSendEmail() {
		return needToSendEmail;
	}

	public void setNeedToSendEmail(Boolean needToSendEmail) {
		this.needToSendEmail = needToSendEmail;
	}
}