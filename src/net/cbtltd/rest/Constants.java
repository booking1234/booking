/**
 * @author	abookingnet
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Relation;

import org.apache.ibatis.session.SqlSession;

public class Constants {
	public static final String NO_POS = "'no_pos'";
//	public static final String NO_VERSION = "'no_version'";
	public static final String NO_XSL = "'no_xsl'";
	public static final String NO_CURRENCY = "NOC";
	public static final String NO_TERMS = "false";
	public static final String CHECK_AVAILABILITY = "true";
	public static final String JSON = "JSON";
	public static final String NOW = "NOW";
	public static final String USD = "USD";
	public static final String BLANK = "";
	public static final String ZERO = "0";
	public static final String UNALLOCATED = "Unallocated";
	public static final String CREATED = "Created";
	public static final String UNLICENSED = "Unlicensed";
	public static final String UNLICENSED_ID = "1";
	public static final String PM_USER_TYPE = "PropertyManager";
	public static final String AGENT_USER_TYPE = "ChannelPartner";
	public static final String RENTER_USER_TYPE = "Renter";
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	public static final String ANET_BOOKINGPAL_ACC_ID = "47R5Rjv2PP";
	public static final String ANET_BOOKINGPAL_TRANSACTION_KEY = "544j66HY9seWAv4k";
	

	/**
	 * Checks if the pos code is blacklisted.
	 *
	 * @param pos the pos code
	 * @param test the test flag
	 * @return false, if pos code is blacklisted, else test
	 */
//	public static boolean isBlacklisted(String pos, boolean test) throws Throwable {
//if ("c3de7878c53a8ae5".equalsIgnoreCase(pos)) {throw new ServiceException(Error.pos_invalid, pos);}
//if ("736e116266059c69".equalsIgnoreCase(pos)) {throw new ServiceException(Error.pos_invalid, pos);}
//if ("76e3fef6c90093eb".equalsIgnoreCase(pos)) {throw new ServiceException(Error.pos_invalid, pos);}
//GO FISH		if ("33f318d792b5b325".equalsIgnoreCase(pos)){return false;}
//		else if ("5e87034a45ca60fe".equalsIgnoreCase(pos)){return false;}
//		else if ("c73f8de0ec583527".equalsIgnoreCase(pos)){return false;}
//Chalets-a-louer		else if ("0e1564f4ee901df9".equalsIgnoreCase(pos)){return false;}
//		else if ("ad12e836046bac7b".equalsIgnoreCase(pos)){return false;}
//NOX		if ("5e7e3a77b3714ea2".equalsIgnoreCase(pos)){return false;}
//		else if ("ad12e836046bac7b".equalsIgnoreCase(pos)){return false;}
//		else if ("529fdf92e32322b5".equalsIgnoreCase(pos)){return false;}
//		76e3fef6c90093eb
//		return test;
//	}

	/**
	 * Format date.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static final String formatDate(Date value) {
		if (value == null) {return null;}
		try {return DF.format(value);}
		catch (Throwable x) {return null;}
	}
	
	/**
	 * Parses the date.
	 *
	 * @param value the value
	 * @return the date
	 */
	public static final Date parseDate(String value) {
		if (value == null || value.isEmpty()) {return null;}
		try {return DF.parse(value);}
		catch (ParseException x) {throw new ServiceException(Error.date_format, value);}
	}
	
	/**
	 * Decrypt pos.
	 *
	 * @param pos the pos
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	public static String decryptPos(String pos) throws ServiceException {
		if (pos == null || pos.isEmpty() || Constants.NO_POS.equalsIgnoreCase(pos)){throw new ServiceException(Error.pos_absent, pos);}
		String decrypted = null;
		try {decrypted = Model.decrypt(pos);}
		catch (Throwable x) {throw new ServiceException(Error.pos_invalid, pos);}
		if (decrypted == null || decrypted.isEmpty()){throw new ServiceException(Error.pos_invalid, pos);}
		return decrypted;
	}

	/**
	 * Gets the party.
	 *
	 * @param sqlSession the sql session
	 * @param pos the pos
	 * @return the party
	 * @throws ServiceException the service exception
	 */
	public static Party getParty(SqlSession sqlSession, String pos) throws ServiceException {
		String partyid = decryptPos(pos);
		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null) {throw new ServiceException(Error.party_id);}
		if (party.notState(Party.CREATED)) {throw new ServiceException(Error.party_inactive);}
		return party;
	}

	/**
	 * Decrypt organizationid.
	 *
	 * @param sqlSession the sql session
	 * @param pos the pos
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	public static String decryptOrganizationid(SqlSession sqlSession, String pos) throws ServiceException {
		try {
			String partyid = Model.decrypt(pos);
			if (RelationService.exists(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), partyid, partyid)) {return partyid;}
			else {throw new ServiceException(Error.party_type, partyid + " not " + Party.Type.Organization.name());}
		}
		catch (Throwable x) {throw new ServiceException(Error.pos_invalid, pos);}
	}
}
