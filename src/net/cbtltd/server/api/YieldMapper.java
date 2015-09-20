/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Yield;

public interface YieldMapper extends AbstractMapper<Yield> {

	void create(Yield action);
	void copy(NameId oldnew);
	Integer countbyentity(Yield action);
	ArrayList<Yield> listbyentity(Yield action);
	ArrayList<Yield> maximumgapfillers();
	Yield exists(Yield action);
	ArrayList<Yield> readByProductState(Yield action);
	void cancelYieldList(List<String> yieldIdList);
}
