/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.finance.FinanceWidgetItem;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.api.HasTableService;

public interface FinanceMapper
extends AbstractMapper<Finance> {
	Finance exists(Finance action);
	Integer count(HasTableService action);
	ArrayList<Finance> list(HasTableService action);
	FinanceWidgetItem financewidget(String financeid);
}