/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.Balance;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;


public interface JournalMapper {
	void create(Journal item);
	void update(Journal item);
	ArrayList<Journal> read(String eventid);
	
	ArrayList<NameId> currencynameid (NameIdAction action);
	ArrayList<NameId> unitnameid (NameIdAction action);
	ArrayList<NameId> locationnameid (NameIdAction action);
	ArrayList<NameId> entitytypenameid (NameIdAction action);
	ArrayList<NameId> eventtypenameid (NameIdAction action);
	ArrayList<NameId> statenameid (NameIdAction action);
	ArrayList<NameId> typenameid (NameIdAction action);

	ArrayList<String> step(String actorid); //TODO: needs actorid

	Date firstlicensedate(String organizationid); //FirstLicenseDate
	Balance licensebalance(NameId nameid); // single balance
	ArrayList<Balance> licensebalances(String licensorid); //LicenseBalances
	ArrayList<Journal> commission(Date date); //Commission
	ArrayList<Journal> license(Date date); //License
}
