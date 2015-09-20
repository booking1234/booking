/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Type;

public interface TypeMapper
extends AbstractMapper<Type> {
	
	ArrayList<NameId> category(NameIdAction model); //Category

}
