/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Price;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.api.HasTableService;

public interface TaxMapper extends AbstractMapper<Tax> {
	Tax exists(Tax action);
	void cancelversion(Tax action); 
	void canceltaxlist(List<String> taxIdList);
	ArrayList<Tax> taxdetail(Price action);
	ArrayList<Tax> taxbyrelation(Price action);
	Integer count(HasTableService action);
	ArrayList<Tax> list(HasTableService action);
	ArrayList<Tax> readbyproductid(Tax action);
}