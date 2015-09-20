/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.party;

import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;

public class LanguageNameId extends NameIdAction {

	public LanguageNameId() {super(Service.PARTY);}

	public LanguageNameId(String type) {
		super(Service.PARTY);
		this.type = type;
	}

}
