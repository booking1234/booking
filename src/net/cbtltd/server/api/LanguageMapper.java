/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.Parameter;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

public interface LanguageMapper
extends AbstractMapper<Language> {
//	ArrayList<NameId> languagenameid (NameIdAction action);
	ArrayList<NameIdWidgetItem> nameidwidget(Parameter action);
}