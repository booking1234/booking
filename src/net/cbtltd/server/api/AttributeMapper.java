/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeAction;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

public interface AttributeMapper {
	ArrayList<NameId> list ();
	ArrayList<NameId> razorlist ();
	ArrayList<NameId> nameidbyid (NameIdAction action);
	ArrayList<NameId> nameidbyname (NameIdAction action);
	ArrayList<NameId> pctListValue(Attribute action);
	ArrayList<NameId> valuelist(AttributeAction action);
	ArrayList<Attribute> valuemap(AttributeMapAction action);
	ArrayList<String> readAttribute(List<String> relation);
}
	