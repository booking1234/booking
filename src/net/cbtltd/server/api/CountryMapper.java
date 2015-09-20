/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.Parameter;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.shared.Country;

public interface CountryMapper
extends AbstractMapper<Country> {
	Country readbyname(String name);
	String idbyname(String name);
	ArrayList<NameIdWidgetItem> nameidwidget(Parameter action);
}