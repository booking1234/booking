/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.account.AccountWidgetItem;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdType;

public interface AccountMapper
extends AbstractMapper<Account> {
	
	ArrayList<NameId> nameidsubledger(NameIdAction action);
	ArrayList<NameId> nameidtype(NameIdType action);
	AccountWidgetItem accountwidget(String accountid);
	ArrayList<AccountWidgetItem> ledgerwidget(String organizationid);
}