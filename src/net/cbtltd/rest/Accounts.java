/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name = "accounts")
public class Accounts
implements HasXsl {
	public String message;
	public Collection<Account> account;
	private String xsl; //NB HAS GET&SET = private

	public Accounts(){} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Accounts(String message, Collection<Account> account, String xsl) {
		super();
		this.message = message;
		this.account = account;
		this.xsl = xsl;
	}

	//---------------------------------------------------------------------------------
	// Implements HasXsl interface
	//---------------------------------------------------------------------------------
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

}
