/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.cbtltd.shared.Relation;

public interface RelationMapper {
	void create(Relation relation);
	Relation exists(Relation relation);
	Relation match(Relation relation);
	void update(Relation relation);
	void delete(Relation relation);
	void deletekey(Relation relation);
	ArrayList<Relation> list(Relation relation);
	ArrayList<String> headids(Relation relation);
	ArrayList<Relation> headidsattributes(List<String> headids);
	ArrayList<Relation> productsclasstype(List<String> headids);	
	ArrayList<String> lineids(Relation relation);
	ArrayList<String> attributes(Relation relation);
}
