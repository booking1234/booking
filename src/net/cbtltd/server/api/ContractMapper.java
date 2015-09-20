/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Contract;
import net.cbtltd.shared.api.HasTableService;

public interface ContractMapper
extends AbstractMapper<Contract> {
	
	Contract readbyexample(Contract contract);
	
	Integer contractcount(HasTableService action);
	ArrayList<Contract> contractlist(HasTableService action);
	
	Integer discountcount(HasTableService action);
	ArrayList<Contract> discountlist(HasTableService action);
}