/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.api.HasService;

public interface AbstractMapper <T extends HasService> {
	
	void create(T action);
	T read(String id);
	void update(T action);
	void write(T action);

	ArrayList<NameId> nameidcurrency (NameIdAction action);
	ArrayList<NameId> nameidbyid (NameIdAction action);
	ArrayList<NameId> nameidbyname (NameIdAction action);
}
