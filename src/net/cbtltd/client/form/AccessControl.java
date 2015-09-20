/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

/**
 * Interface AccessControl defines the role constants and arrays which control access to the application resources.
 * They are not stored in a database to improve security, reliability and speed.
 * UNDER NO CIRCUMSTANCES CHANGE THE CONSTANTS, OR ADD NEW ROLES TO THE LISTS, UNLESS YOU FULLY UNDERSTAND THE CONSEQUENCES.
 */

public interface AccessControl {
	short READONLY = -32768;
	short SUPERUSER = 32767;
	short LICENSE = 32766;
	short UPSTREAM = 32765;
	short DOWNSTREAM = 32764;
	short PARTNER_DEFAULT = 32763;
	short PARTNER_SCHEDULE = 32762;
	short NO_LOGIN = 0;
	short ACCOUNTANT = 1;
	short ADMINISTRATOR = 2;
	short GUEST_RELATIONS = 3;
	short HOUSEKEEPER = 4;
	short MAINTAINER = 5;
	short MARKETER = 6;
	short OWNER = 7;
	short AGENCY = 10;
	short AGENT = 11;
	short LESSOR = 12;

	int[] ROLE_LIST = {};
	
	short[] ORGANIZATION_ROLES = { //see PartyConstants organizationroleNames
			ACCOUNTANT,
			ADMINISTRATOR,
			GUEST_RELATIONS,
			HOUSEKEEPER,
			MAINTAINER,
			MARKETER,
			OWNER
	};
	
	short[] AGENT_ROLES = { //see PartyConstants agentroleNames
			AGENCY,
			AGENT
	};
	
	short[] LICENSE_ROLES = {
			SUPERUSER,
			LICENSE,
			UPSTREAM,
			SUPERUSER,
			DOWNSTREAM
	};
	
	short[] PARTNER_ROLES = {
			SUPERUSER,
			PARTNER_DEFAULT,
			PARTNER_SCHEDULE
	};

	/**
	 * Each array of roles defines a permission. A user must have one or more permissions to access the application.
	 */
	short[] NO_LOGIN_PERMISSION = {NO_LOGIN}; //,RESERVATION_MANAGER,RESERVATION_CLERK,RESERVATION_READER,RESERVATION_GUEST};
	short[] NO_PERMISSION = {};
	short[] ALL_PERMISSION = {SUPERUSER};
	short[] AGENCY_PERMISSION = {AGENCY, AGENT};
	short[] AVAILABLE_PERMISSION = {ADMINISTRATOR, AGENCY, AGENT, GUEST_RELATIONS, HOUSEKEEPER, MAINTAINER, OWNER};
	short[] DELETE_PERMISSION = {ADMINISTRATOR};
	short[] GAMES_PERMISSION = {ACCOUNTANT, ADMINISTRATOR, AGENCY, AGENT, GUEST_RELATIONS, HOUSEKEEPER, MAINTAINER};
	short[] SEARCH_PERMISSION = {ADMINISTRATOR, AGENCY, AGENT, GUEST_RELATIONS};
	short[] ORGANIZATION_PERMISSION = {ADMINISTRATOR, AGENCY, OWNER};
	short[] PARTY_PERMISSION = {ADMINISTRATOR, AGENCY};
	short[] PRODUCT_PERMISSION = {ADMINISTRATOR, OWNER};
	short[] QUOTE_PERMISSION = {ADMINISTRATOR, GUEST_RELATIONS};
	short[] RATE_PERMISSION = {ADMINISTRATOR, GUEST_RELATIONS};
	short[] REPORT_PERMISSION = {ACCOUNTANT, ADMINISTRATOR};
	short[] RESERVATION_PERMISSION = {ACCOUNTANT, ADMINISTRATOR, GUEST_RELATIONS, HOUSEKEEPER, MAINTAINER, OWNER};
	short[] LEASE_PERMISSION = {LESSOR};
	short[] TASK_PERMISSION = {ADMINISTRATOR, AGENCY, AGENT, MARKETER};
}
