/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.StringTokenizer;

import net.cbtltd.client.form.AccessControl;
import net.cbtltd.rest.Constants;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LanguageMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.party.ActorDelete;
import net.cbtltd.shared.party.ActorRead;
import net.cbtltd.shared.party.ActorUpdate;
import net.cbtltd.shared.party.Address;
import net.cbtltd.shared.party.AgentCreate;
import net.cbtltd.shared.party.AgentNameIdAction;
import net.cbtltd.shared.party.EmployeeNameId;
import net.cbtltd.shared.party.LanguageNameId;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.party.OrganizationActorTable;
import net.cbtltd.shared.party.OrganizationCreate;
import net.cbtltd.shared.party.OrganizationNameId;
import net.cbtltd.shared.party.OrganizationRead;
import net.cbtltd.shared.party.OrganizationUpdate;
import net.cbtltd.shared.party.PartyCreate;
import net.cbtltd.shared.party.PartyCreator;
import net.cbtltd.shared.party.PartyCreatorList;
import net.cbtltd.shared.party.PartyDelete;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.party.PartyLicense;
import net.cbtltd.shared.party.PartyPartnerExists;
import net.cbtltd.shared.party.PartyRead;
import net.cbtltd.shared.party.PartyRelationTable;
import net.cbtltd.shared.party.PartySessionTable;
import net.cbtltd.shared.party.PartyTypes;
import net.cbtltd.shared.party.PartyUpdate;
import net.cbtltd.shared.party.RolePartyTable;
import net.cbtltd.shared.party.WorkflowNameId;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class PartyService responds to party, including managers, agencies, agents and users, requests. */
public class PartyService
implements IsService {

	private static final Logger LOG = Logger.getLogger(PartyService.class.getName());
	private static PartyService service;

	/**
	 * Gets the single instance of PartyService to manage Party instances.
	 * @see net.cbtltd.shared.Party
	 *
	 * @return single instance of PartyService
	 */
	public static synchronized PartyService getInstance() {
		if (service == null){service = new PartyService();}
		return service;
	}

	/**
	 * Executes the ActorRead action to read an actor Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, ActorRead action) {
		Party party = null;
		try {
			party = sqlSession.getMapper(PartyMapper.class).read(action.getId());
			party.setRoles(RelationService.read(sqlSession, Relation.PARTY_ROLE, party.getId(), null));
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return party;
	}

	/**
	 * Executes the ActorUpdate action to update an actor Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, ActorUpdate action) {
		try {
			if (action.hasPassword()) {
				String password = Party.decrypt(action.getPassword());
				action.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			}
			else {action.setPassword(null);}
			sqlSession.getMapper(PartyMapper.class).update(action);
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Actor.name(), action.getOrganizationid(), action.getId());
			RelationService.replace(sqlSession, Relation.PARTY_ROLE, action.getId(), action.getRoles());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Party, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return action;
	}

	/**
	 * Executes the ActorDelete action to delete an actor Party instance.
	 * This deletes the actor roles.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, ActorDelete action) {
		try {
			if (action.getOrganizationid().equalsIgnoreCase(action.getEmployerid())) {
				action.setEmployerid(Model.ZERO);
				sqlSession.getMapper(PartyMapper.class).update(action);
			}
			RelationService.delete(sqlSession, Relation.ORG_PARTY_ + Party.Type.Actor.name(), action.getOrganizationid(), action.getId());
			RelationService.delete(sqlSession, Relation.PARTY_ROLE, action.getId(), null);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return null;
	}

	/**
	 * Executes the PartyLicense action to check the party license and reinstate if OK.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyLicense action) {
		try {LicenseService.check(sqlSession, action.getId());} 
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return action;
	}

	/**
	 * Executes the PartyCreate action to create a Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyCreate action) {
		LOG.debug("PartyCreate " + action);
		Party exists = null;
		try {
			exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
			if (exists == null) {
				sqlSession.getMapper(PartyMapper.class).create(action);
				action.setContractText(Party.getContractText(action.getId(), Language.EN));
				action.setPrivateText(action.getOrganizationid(), Party.getPrivateText(action.getOrganizationid(), action.getId(), Language.EN));
				action.setPublicText(Party.getPublicText(action.getId(), Language.EN));
				exists = action;
			}
			else {exists.setState(action.getState());}
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + action.getType(), action.getOrganizationid(), action.getId());
		} 
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return exists;
	}

	/**
	 * Executes the PartyExists action to read a Party instance if it exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyExists action) {
		LOG.debug("PartyExists " + action);
		Party exists =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
		return exists == null ? null : partyRead(sqlSession, action.getOrganizationid(), exists.getId());
	}

	/**
	 * Executes the PartyPartnerExists action to read if a partner Party instance exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyPartnerExists action) {
		if (RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Agent.name(), action.getOrganizationid(), action.getId()) != null
			|| RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), action.getOrganizationid(), action.getId()) != null
		) {return partyRead(sqlSession, action.getOrganizationid(), action.getId());}
		else {return null;}
	}

	/**
	 * Executes the PartyCreator action to create an affiliate Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyCreator action) {
		try {
			RelationService.create(sqlSession, Relation.PARTY_CREATOR, action.getCreatorid(), action.getEmailaddress());
			EmailService.partyCreator(sqlSession, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return action;
	}

	/**
	 * Executes the PartyRead action to read a Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyRead action) {
		return partyRead(sqlSession, action.getOrganizationid(), action.getId());
	}

	/*
	 * Reads a Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	private final Party partyRead(SqlSession sqlSession, String organizationid, String partyid) {
		LOG.debug("\npartyRead " + organizationid + ", " + partyid);
		Party party = null;
		try {
			party = sqlSession.getMapper(PartyMapper.class).read(partyid);
			if (party != null) {
				party.setImageurls(sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Party.name(), partyid)));
				party.setRoles(RelationService.read(sqlSession, Relation.PARTY_ROLE, partyid, null));
				party.setValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, partyid, null));
				party.setTypes(getTypes(sqlSession, organizationid, partyid));
				party.setOrganization(RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), organizationid, partyid) != null);
				party.setAgent(RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Agent.name(), organizationid, partyid) != null);
			}
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		LOG.debug("partyRead " + party);
		return party;
	}

	/*
	 * Gets the types of the specified party with respect to the organization
	 * 
	 * @param sqlSession the current SQL session.
	 * @param organizationid the ID of the organization
	 * @param partyid the ID of the party
	 * @return list of party types
	 */
	private static ArrayList<String> getTypes(SqlSession sqlSession, String organizationid, String partyid) {
		Party.Type[] values = Party.Type.values();
		ArrayList<String> types = new ArrayList<String>();
		for (Party.Type value : values) {
			if (RelationService.exists(sqlSession, Relation.ORG_PARTY_ + value.name(), organizationid, partyid))
				{types.add(value.name());}
		}
		return types;
	}
	
	/**
	 * Executes the PartyUpdate action to update a Party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyUpdate action) {
		LOG.debug("PartyUpdate in " + action);
		try {
			if (action.hasPassword()) {
				String password = Party.decrypt(action.getPassword());
				action.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			}
			else {action.setPassword(null);}
			if (action.hasState(Party.FINAL)) {action.setState(Party.CREATED);}
			if (action.hasType(Party.Type.Affiliate.name())) {EmailService.affiliate(action);}
			sqlSession.getMapper(PartyMapper.class).update(action);
			if (action.hasValues()) {RelationService.replace(sqlSession, Relation.PARTY_VALUE, action.getId(), action.getValues());}
			
			Party.Type[] values = Party.Type.values();
			for (Party.Type value : values) {RelationService.delete(sqlSession, Relation.ORG_PARTY_ + value.name(), action.getOrganizationid(), action.getId());}
			for (String type : action.getTypes()) {RelationService.create(sqlSession, Relation.ORG_PARTY_ + type, action.getOrganizationid(), action.getId());}

			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Party, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		LOG.debug("PartyUpdate out " + action);
		return action;
	}

	/**
	 * Executes the PartyDelete action to delete a Party instance.
	 * This deletes the relation between the party and the current organization, not the party instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Party execute(SqlSession sqlSession, PartyDelete action) {
		try {
			if (action.getOrganizationid().equalsIgnoreCase(action.getEmployerid())) {
				action.setEmployerid(Model.ZERO);
				sqlSession.getMapper(PartyMapper.class).update(action);
			}
			for (String type : action.getTypes()) {RelationService.delete(sqlSession, Relation.ORG_PARTY_ + type, action.getOrganizationid(), action.getId());}
			
//			RelationService.delete(sqlSession, Relation.ORGANIZATION_PARTY, action.getOrganizationid(), action.getId());
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return null;
	}

	/**
	 * Executes the PartyTypes action to get a Party type list.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<String> execute(SqlSession sqlSession, PartyTypes action) {
		LOG.debug("PartyTypes " + action);
		Table<String> table = new Table<String>();
//		try {table.setValue(RelationService.read(sqlSession, Relation.PARTY_TYPE, action.getId(), null));}
		try {table.setValue(getTypes(sqlSession, action.getOrganizationid(), action.getId()));}
		catch (Throwable x) {LOG.error(x.getMessage());}
		LOG.debug("PartyTypes " + table);
		return table;
	}

	/**
	 * Executes the NameIdAction action to read a list of party NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		LOG.debug("NameIdAction " + action);
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(PartyMapper.class).nameidbyid(action));}
			else {  
			    table.setValue(sqlSession.getMapper(PartyMapper.class).nameidbyname(action));
			}
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		LOG.debug("NameIdAction " + table);
		return table;
	}
	
	/**
	 * Executes the AgentNameIdAction action to read a list of agent NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, AgentNameIdAction action) {
		LOG.debug("NameIdAction " + action);
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(PartyMapper.class).nameidbyid(action));}
			else {
			    ArrayList<String> agentRoles = new ArrayList<String>();
			    agentRoles.add(String.valueOf(AccessControl.AGENCY));
			    agentRoles.add(String.valueOf(AccessControl.AGENT));
			    action.setIds(agentRoles);
			    table.setValue(sqlSession.getMapper(PartyMapper.class).agentnameidbyname(action));
			}
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		LOG.debug("NameIdAction " + table);
		return table;
	}
	

	/**
	 * Executes the PartyCreatorList action to read a list of affiliate NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, PartyCreatorList action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PartyMapper.class).creatornameid(action.getEmailaddress()));}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the PartySessionTable action to read a list of session instances for a user.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Session> execute(SqlSession sqlSession, PartySessionTable action) {
		Table<Session> table = new Table<Session>();
		try {
			table.setDatasize(sqlSession.getMapper(PartyMapper.class).actorsessioncount(action));
			table.setValue(sqlSession.getMapper(PartyMapper.class).actorsessionlist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the AgentCreate action to create an agency (Party of type agent) instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Organization execute(SqlSession sqlSession, AgentCreate action) {
		try {
			Party exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
			if (exists == null) {sqlSession.getMapper(PartyMapper.class).create(action);}
			action.setCurrency(Currency.Code.USD.name());
			action.setLanguage(Language.EN);
			RelationService.create(sqlSession, Relation.PARTY_ROLE, action.getId(), String.valueOf(AccessControl.AGENCY));
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Actor.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Employer.name(), action.getId(), action.getId());
			ArrayList<String> organizationids = RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), null, null);
			organizationids.add(action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Agent.name(), organizationids, action.getId());
			if (action.hasOldstate(Party.INITIAL)) {EmailService.partyCreate(action);}
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Party, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return action;
	}

	/**
	 * Executes the OrganizationCreate action to create a property manager (Party of type organization) instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the organization
	 */
	public final Organization execute(SqlSession sqlSession, OrganizationCreate action) {
		LOG.debug("OrganizationCreate in " + action);
		try {

			Party exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
			if (exists == null) {sqlSession.getMapper(PartyMapper.class).create(action);}

			action.setEmployerid(action.getId());
			action.setCurrency(Currency.Code.USD.name());
			action.setLanguage(Language.EN);
			sqlSession.getMapper(PartyMapper.class).insertlogo(action.getId());
			sqlSession.getMapper(PartyMapper.class).insertaccounts(action.getId());

			RelationService.create(sqlSession, Relation.PARTY_ROLE, action.getId(), String.valueOf(AccessControl.ADMINISTRATOR));
			RelationService.replace(sqlSession, Relation.PARTY_VALUE, action.getId(), action.getValues());
			RelationService.create(sqlSession, Relation.ORGANIZATION_CURRENCY, action.getId(), Currency.Code.USD.name());
			RelationService.create(sqlSession, Relation.ORGANIZATION_LANGUAGE, action.getId(), Language.EN);

			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Actor.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Employer.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Owner.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Supplier.name(), action.getId(), action.getId());
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Supplier.name(), action.getId(), Party.CBT_LTD_PARTY); //Licensor
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Actor.name(), action.getId(), Party.NO_ACTOR);
			ArrayList<String> organizationids = RelationService.read(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), null, null);
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), action.getId(), organizationids);
			//allow partner APIS to be scheduled into  a single Parnter API. 
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), organizationids, action.getId());
			
			sqlSession.getMapper(PartyMapper.class).update(action);
			sqlSession.commit();

			final NameIdAction param = new NameIdAction();
			param.setOrganizationid(action.getId());
			action.setCurrencies(RelationService.read(sqlSession, Relation.ORGANIZATION_CURRENCY, action.getId(), null));
			action.setLanguages(RelationService.read(sqlSession, Relation.ORGANIZATION_LANGUAGE, action.getId(), null));
			action.setCurrency(Currency.Code.USD.name());
			action.setLanguage(Language.EN);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Party, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		LOG.debug("OrganizationCreate out " + action);
		return action;
	}

	/**
	 * Executes the OrganizationRead action to read a property manager instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the organization
	 */
	public final Organization execute(SqlSession sqlSession, OrganizationRead action) {
		Organization organization = null;
		try {
			organization = sqlSession.getMapper(PartyMapper.class).organizationread(action.getId());
			organization.setCurrencies(RelationService.read(sqlSession, Relation.ORGANIZATION_CURRENCY, action.getId(), null));
			organization.setLanguages(RelationService.read(sqlSession, Relation.ORGANIZATION_LANGUAGE, action.getId(), null));
			organization.setValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, organization.getId(), null));
			organization.setWorkflows(WorkflowService.read(sqlSession, action.getId()));
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		LOG.debug("\n\nOrganizationRead " + organization);
		return organization;
	}

	/**
	 * Executes the OrganizationUpdate action to update a property manager instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the organization
	 */
	public static final Organization execute(SqlSession sqlSession, OrganizationUpdate action) {
		//LOG.debug("OrganizationUpdate " + action);
		try {
			if (action.hasPassword()) {
				String password = Party.decrypt(action.getPassword());
				action.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			}
			else {action.setPassword(null);}
			sqlSession.getMapper(PartyMapper.class).update(action);
			RelationService.replace(sqlSession, Relation.PARTY_VALUE, action.getId(), action.getValues());
			RelationService.replace(sqlSession, Relation.ORGANIZATION_CURRENCY, action.getId(), action.getCurrencies());
			RelationService.replace(sqlSession, Relation.ORGANIZATION_LANGUAGE, action.getId(), action.getLanguages());
			TextService.update(sqlSession, action.getTexts());
			WorkflowService.update(sqlSession, action.getWorkflows());
			if (action.hasOldstate(Party.INITIAL) && action.getNeedToSendEmail()) {EmailService.partyCreate(action);}
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Party, action);
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return action;
	}

	/**
	 * Executes the PartyRelationTable action to read a list of role instances for a user.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Party> execute(SqlSession sqlSession, PartyRelationTable action) {
		Table<Party> table = new Table<Party>();
		try {
			table.setDatasize(sqlSession.getMapper(PartyMapper.class).relationcount(action));
			table.setValue(sqlSession.getMapper(PartyMapper.class).relationlist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the RolePartyTable action to read a list of role instances for a user.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Party> execute(SqlSession sqlSession, RolePartyTable action) {
		Table<Party> table = new Table<Party>();
		try {
			table.setDatasize(sqlSession.getMapper(PartyMapper.class).partyrolecount(action));
			table.setValue(sqlSession.getMapper(PartyMapper.class).partyrolelist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the OrganizationActorTable action to read a list of users of a property manager or agency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Party> execute(SqlSession sqlSession, OrganizationActorTable action) {
		Table<Party> table = new Table<Party>();
		try {
			table.setDatasize(sqlSession.getMapper(PartyMapper.class).employeecount(action));
			table.setValue(sqlSession.getMapper(PartyMapper.class).employeelist(action));
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the OrganizationNameId action to read a list of property manager or agency NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, OrganizationNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(PartyMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(PartyMapper.class).organizationnameid(action));}
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the EmployeeNameId action to read a list of employee NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, EmployeeNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(PartyMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(PartyMapper.class).employeenameid(action));}
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the LanguageNameId action to read a list of language NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, LanguageNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(LanguageMapper.class).nameidbyname(action));}
		catch (Throwable x) {LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the WorkflowNameId action to read a list of work flow NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, WorkflowNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PartyMapper.class).workflownameid(action));}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}

	/**
	 * Executes the NameIdType action to read a list of account type NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdType action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PartyMapper.class).type(action));}
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		return table;
	}
	
	/**
	 * Updates the progress of users towards certain goals.
	 * Executed periodically by the RazorServer scheduler. 
	 */
	public static final void progress() {
		LOG.debug("PartyService progress");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			sqlSession.getMapper(PartyMapper.class).progressdelete();
			sqlSession.getMapper(PartyMapper.class).progressactivitymax();
			sqlSession.getMapper(PartyMapper.class).progressage();
			sqlSession.getMapper(PartyMapper.class).progressagemax();
			sqlSession.getMapper(PartyMapper.class).progressconfirm();
			sqlSession.getMapper(PartyMapper.class).progresscreator();
			sqlSession.getMapper(PartyMapper.class).progresscreatormax();
//			sqlSession.getMapper(PartyMapper.class).progressvalue();
//			sqlSession.getMapper(PartyMapper.class).progressvaluemax();
			sqlSession.commit();
		} 
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		finally {sqlSession.close();}
	}
	
	/**
	 * Gets the specific value from party's address.
	 * 
	 * @param party party that contains address
	 * @param value value to get. E.g. city, zip
	 * @return value
	 */
	public static String getAddressValue(Party party, Address.Type value) {
		if(party == null || party.getPostaladdress() == null) {
			throw new ServiceException(Error.address_invalid, "or party is null");
		}
		String address = party.getPostaladdress();
		return getAddressValue(address, value);
	}
	
	public static String getAddressValue(String address, Address.Type value) {
		if(address == null) {
			throw new ServiceException(Error.address_invalid, "or party is null");
		}
		StringTokenizer tokenizer = new StringTokenizer(address, ";");
		if(tokenizer.countTokens() < 2) {
			return "";
		}
		while (tokenizer.hasMoreTokens()) {
			String string = tokenizer.nextToken();
			if(string.contains(value.name())) {
				String[] splittedValue = string.split(":");
				if(splittedValue.length < 2) {
					return "";
				}
				return splittedValue[1];
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
		String address = "address:97/99 Chervonoarmeyskaya str;city:Chernivtsi;zip:58013";
		Party party = new Party();
		party.setPostaladdress(address);
		System.out.println(getSimpleAddress(party));
	}
	
	/**
	 * Parse address:value;city:value;zip:value format and returns formatted address. 
	 * 
	 * @param party party to get address from
	 * @return formatted address
	 */
	public static String getSimpleAddress(Party party) {
		if(party == null || party.getPostaladdress() == null) {
			throw new ServiceException(Error.address_invalid, "or party is null");
		}
		String address = party.getPostaladdress();
		StringTokenizer tokenizer = new StringTokenizer(address, ";");
		String result = "";
		while (tokenizer.hasMoreTokens()) {
			String string = tokenizer.nextToken();
			String[] splittedValue = string.split(":");
			result += splittedValue[1] + ", ";
		}
		if(result.contains(",")) {
			result = result.substring(0, result.lastIndexOf(", "));
		} else {
			return "";
		}
		return result;
	}
	
	public static String checkMybookingpalCurrency(String currency, PropertyManagerInfo propertyManagerInfo) {
		if((Constants.NO_CURRENCY.equalsIgnoreCase(currency) || StringUtils.isEmpty(currency)) && ManagerToGateway.BOOKINGPAL_HOLDER == propertyManagerInfo.getFundsHolder()) {
			currency = Currency.Code.USD.name();
		}
		return currency;
	}
}

