/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.cbtltd.json.Parameter;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.json.party.PartyWidgetItem;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PropertyManager;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.party.Organization;

import org.apache.ibatis.annotations.Param;

public interface PartyMapper extends AbstractMapper<Party> {

	/* == Property Manager Registration JSON == */	
	PropertyManager readbyemailwithinfo(String emailaddress);

	
	/* == Channel Partner block == */
	
	void insertChannelPatner(@Param("pmid") Integer managerId, @Param("channelid") Integer channelId);
	
	List<Integer> listChannelID(Integer pmId);
	
	void deleteChannelPartners(Integer pmId);
	
	/* == END Channel Partner block == */

	// Party exists(Party action);
	Party readbyemailaddress(String emailaddress);

	void suspendparty(String partyid);

	Integer actorsessioncount(HasTableService action); // CountByActor

	ArrayList<Session> actorsessionlist(HasTableService action); // ListByActor

	Organization organizationread(String id);

	void insertaccounts(String organizationid);

	void insertlogo(String organizationid);

	Integer accommodationcount(String licensorid);// AccommodationCount

	Integer employeecount(HasTableService action);

	ArrayList<Party> employeelist(HasTableService action);

	Integer relationcount(HasTableService action); // Relation count

	ArrayList<Party> relationlist(HasTableService action); // Relation list

	Integer partyrolecount(HasTableService action); // CountByRole

	ArrayList<Party> partyrolelist(HasTableService action); // PartyByRole
	
	// Integer uncontractedlocationcount(String
	// uncontractedagent);//UncontractedLocationCount

	// Integer uncontractedmanagercount(String
	// uncontractedagent);//UncontractedManagerCount
	// ArrayList<String> uncontractedmanagerlist(String
	// uncontractedagentid);//UncontractedManagers

	ArrayList<NameId> credentials();

	ArrayList<NameId> creatornameid(String emailaddress);

	ArrayList<NameId> employeenameid(NameIdAction action);

	ArrayList<NameId> jurisdictionnameid(NameIdAction action);

	ArrayList<NameId> organizationnameid(NameIdAction action);

	ArrayList<NameId> propertymanagernameid ();
	ArrayList<NameId> workflownameid(NameIdAction action);

	ArrayList<NameId> type(NameIdAction action);
	ArrayList<NameId> agentnameidbyname (NameIdAction action);
	
	// ArrayList<String> allagentemails(); //AllAgentUsers emails
	// ArrayList<String> employeeemails(String partyid); //AgentUsers emails
	// ArrayList<String> organizationemails(); //AllOrganizationUsers emails
	// ArrayList<String> uncontractedagentids(); //UncontractedAgents
	ArrayList<String> organizationidsbyagentid(String uncontractedagent);// OrganizationIdsByAgent

	HashMap<String, ArrayList<String>> attribute(Party party);

	void progressdelete();

	void progressactivitymax();

	void progressage();

	void progressagemax();

	void progressconfirm();

	void progresscreator();

	void progresscreatormax();

	void progressvalue();

	void progressvaluemax();

	// REST SQL queries
	// net.cbtltd.rest.Party restread(String partyid);

	PartyWidgetItem partywidget(String partyid);

	PartyWidgetItem partywidgetexists(String emailaddress);

	ArrayList<NameIdWidgetItem> nameidwidget(Parameter action);

	// KIGO SQL queries
	Party kigoread(Integer kigoid);

	// Foreign SQL queries
	Party altread(NameId action);
}