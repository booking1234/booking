/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import net.cbtltd.shared.Relation;

public interface DownloadMapper {
	void create(Relation relation);
	Relation exists(Relation relation);
	void delete(Relation relation);
	String find(Relation relation);
}
