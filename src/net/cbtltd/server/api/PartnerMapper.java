/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Partner;
import net.cbtltd.shared.partner.PartnerTable;

public interface PartnerMapper
extends AbstractMapper<Partner> {
	void delete(String id);
	Partner exists(String partyid);
	Integer count(PartnerTable action);
	ArrayList<Partner> list(PartnerTable action);
	String partyIDFromEmail(String emailaddress);
	Partner getPartnerByFromEmail(String emailaddress);
	Boolean isSupportCreditCard(String partyid);
	List<String> getPartnerPartyIDByAbbreviation(String[] abbrevations);
	ArrayList<Partner> listPropertyManagementSoftware();
	List<String> getAllChildPartnerPartyIDByAbbreviation(String abbrevation);
}
