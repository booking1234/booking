/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.Parameter;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

public interface CurrencyMapper
extends AbstractMapper<Currency> {
	ArrayList<NameId> nameidbyrate(NameIdAction action);
	ArrayList<NameId> nameidbypaypal(NameIdAction action);
	ArrayList<NameId> nameidbyjetpay(NameIdAction action);
	ArrayList<NameIdWidgetItem> nameidwidget(Parameter action);
}