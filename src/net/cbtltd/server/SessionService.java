/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.SerialMapper;
import net.cbtltd.server.api.SessionMapper;
import net.cbtltd.shared.Mail;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.ModelValue;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.session.PasswordCreate;
import net.cbtltd.shared.session.PasswordUpdate;
import net.cbtltd.shared.session.SessionAutoLogin;
import net.cbtltd.shared.session.SessionExists;
import net.cbtltd.shared.session.SessionLogin;
import net.cbtltd.shared.session.SessionLogout;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class ProductService responds to login requests, authenticates their validity, and responds with a session instance. */
public final class SessionService
implements IsService {

	private static final Logger LOG = Logger.getLogger(SessionService.class.getName());
	private static SessionService service;
	
	/**
	 * Gets the single instance of SessionService to manage Session instances.
	 *
	 * @return single instance of SessionService
	 * @see net.cbtltd.shared.Session
	 */
	public static synchronized SessionService getInstance() {
		if (service == null) {service = new SessionService();}
		return service;
	}

	/**
	 * Executes the SessionExists action to check if a Session instance already exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the session if it exists, else null.
	 */
	public final Session execute(SqlSession sqlSession, SessionExists action) {
		try {
			Session session = sqlSession.getMapper(SessionMapper.class).read(action.getId());
			if (session == null || session.isLoggedOut()) {return null;}
			else {return login(sqlSession, sqlSession.getMapper(PartyMapper.class).read(session.getActorid()));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the SessionAutoLogin action to create a Session instance given a valid point of sale code.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response to indicate if the session is or is not logged in.
	 */
	public final Session execute(SqlSession sqlSession, SessionAutoLogin action) {
		LOG.debug("SessionAutoLogin in " + action);
		try {
			action.setState(Session.LOGGED_OUT);
			String partyid = Party.decrypt(action.getId());
			Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
			LOG.debug("SessionAutoLogin party " + partyid + ", " + party);
			if (party != null) {return login(sqlSession, party);}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("SessionAutoLogin out " + action);
		return action;
	}

	/**
	 * Executes the SessionLogin action to create a Session instance given a valid email address-password combination.
	 * The request password must be encrypted, typically by a field with its secure flag set to true.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Session execute(SqlSession sqlSession, SessionLogin action) {
		LOG.debug("SessionLogin in " + action);
		try {
			action.setState(Session.LOGGED_OUT);
			String password = Party.decrypt(action.getId());
			Party actor = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
			if (actor != null && BCrypt.checkpw(password, actor.getPassword())) {return login(sqlSession, actor);}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("SessionLogin out " + action);
		return action;
	}
	
	/* 
	 * Behaviour shared by all login actions. If successful, the session which is returned
	 * contains the default language and currency codes, the user and its employer's IDs and names,
	 * the permissions allowed to the user based on its role(s), and the map of key-value pairs 
	 * containing information about the the user and its employer.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param actor the real or virtual user (actor) requesting permission to log in.
	 * @return a session instance to indicate if the actor is or is not logged in.
	 */
	private Session login(SqlSession sqlSession, Party actor) {
		LOG.debug("SessionService login actor " + actor);
		boolean suspended = false;
		Session session = new Session();
		session.setId(null);
		session.setActorid(actor.getId());
		session.setLanguage(actor.getLanguage());
		session.setFormatdate(actor.getFormatdate());
		session.setFormatphone(actor.getFormatphone());
		session.setActorname(actor.getName());
		session.setRank(actor.getRank());
		session.setLogin(new Date());
		session.setLogout(null);
		Party employer = sqlSession.getMapper(PartyMapper.class).read(actor.getEmployerid());
		if (employer != null) {
			session.setEmailaddress(employer.getEmailaddress());
			session.setOrganizationid(employer.getId());
			session.setOrganizationname(employer.getName());
			session.setCountry(employer.getCountry());
			session.setCurrency(employer.getCurrency());
			suspended = employer.notState(Party.CREATED);
		}
		sqlSession.getMapper(SessionMapper.class).create(session);
		session.setPermission(RelationService.read(sqlSession, Relation.PARTY_ROLE, actor.getId(), null));
		session.setValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, actor.getId(), null));
		session.addValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, "0", null));
		if (suspended) {session.setState(Session.SUSPENDED);}
		else if (session.noPermission()) {session.setState(Session.LOGGED_OUT);}
		else {session.setState(Session.LOGGED_IN);}
		LOG.debug("SessionService login session " + session);
		return session;
	}

	/**
	 * Executes the ModelValue action to read the key-value pairs containing information about the the user and its employer.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response with the key-value pairs.
	 */
	public final ModelValue execute(SqlSession sqlSession, ModelValue action) {
		RelationService.delete(sqlSession, Relation.PARTY_VALUE, action.getId(), null);
		RelationService.create(sqlSession, Relation.PARTY_VALUE, action.getId(), action.getValues());
		return action;
	}

	/**
	 * Executes the SessionLogout action to log out of a Session instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the session to be ended.
	 * @return the response.
	 */
	public final Session execute(SqlSession sqlSession, SessionLogout action) {
		try {
			action.setState(Session.LOGGED_OUT);
			Session session = sqlSession.getMapper(SessionMapper.class).read(action.getId());
			if (session != null) {
				if (session.noLogout()){
					session.setLogout(new Date());
					sqlSession.getMapper(SessionMapper.class).update(session);
				}
			}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("SessionLogout action " + action);
		return action;
	}

	/**
	 * Executes the PasswordUpdate action to change the current user's password.
	 * The request password must be encrypted, typically by a field with its secure flag set to true.
	 * The password is decrypted and then hashed before being updated. 
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Session execute(SqlSession sqlSession, PasswordUpdate action) {
		try {
			String password = Party.decrypt(action.getId());
			action.setId(Model.ZERO);
			Party actor = sqlSession.getMapper(PartyMapper.class).read(action.getActorid());
			if (actor != null) {
				actor.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
				sqlSession.getMapper(PartyMapper.class).update(actor);
			}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the PasswordCreate action to create a new password for the user in specified session.
	 * An email message with the new password is sent to the user's email address.
	 * Set the logging priority value to debug in the net.cbtltd.server.SessionService category
	 * in the log4j.xml configuration file to display the new password in the server log.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Session execute(SqlSession sqlSession, PasswordCreate action) {
		try {
			Party actor = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(action.getEmailaddress());
			if (actor != null) {
				String password = getPassword(PasswordType.LOWERCASE, 8);
				actor.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
				sqlSession.getMapper(PartyMapper.class).update(actor);
				Mail mail = new Mail();
				mail.setSubject("New Razor Password");
				mail.setContent("Your new Razor password is " + password + "\nPlease change it by clicking the Password link after you log in.");
				mail.setRecipients(actor.getEmailaddresses());
				MailService.send(mail);
				//LOG.error("PasswordCreate OK=" +  BCrypt.checkpw(password, actor.getPassword()) + ", " + password + ", " + actor.getEmailaddress() + ", " + actor.getPassword());
			}
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/** The Enum PasswordType is to constrain the way in which a random password is generated. */
	enum PasswordType {
		/** The LOWERCASE type is for lower case alphabetic passwords. */
		LOWERCASE,
		/** The UPPERCASE type is for upper case alphabetic passwords. */
		UPPERCASE,
		/** The NUMERIC type is for numeric passwords. */
		NUMERIC,
		/** The PRINTABLE type is for passwords having any printable character. */
		PRINTABLE,
		/** The ALPHANUMERIC type is for alphanumeric passwords. */
		ALPHANUMERIC}
	
	/*
	 * Creates a new password having the specified length and type.
	 * 
	 * @param type the constraint on characters to be used from the PasswordType enumeration.
	 * @param length the length of the password.
	 * @return the new password.
	 */
	private static final String getPassword(PasswordType type, int length) {
		StringBuilder sb = new StringBuilder();
		try {
			SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");

			char[] lowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
			char[] upperCase = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
			char[] numeric = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

			char[] printableAscii = new char[]{'!', '\"', '#', '$', '%', '(', ')', '*', '+', '-', '.', '/', '\'',
					'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':', '<', '=', '>', '?', '@',
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'[', '\\', ']', '^', '_', '`', '{', '|', '}', '~',
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

			char[] alphaNumberic = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

			for (int i = 0; i < length; i++) {
				switch (type) {
				case LOWERCASE: sb.append(lowerCase[wheel.nextInt(lowerCase.length)]); break;
				case UPPERCASE: sb.append(upperCase[wheel.nextInt(upperCase.length)]); break;
				case NUMERIC: sb.append(numeric[wheel.nextInt(numeric.length)]); break;
				case PRINTABLE: sb.append(printableAscii[wheel.nextInt(printableAscii.length)]); break;
				case ALPHANUMERIC: sb.append(alphaNumberic[wheel.nextInt(alphaNumberic.length)]); break;
				}
			}
		} catch (NoSuchAlgorithmException e) {System.out.print("No such algoritm " + e.toString());}
		return sb.toString();
	}

	/**
	 * Executes the NameIdAction action to search for a list of NameId values that match its value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the list of search results.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		table.setValue(sqlSession.getMapper(SessionMapper.class).search(action));
		return table;
	}

	/**
	 * Creates a new serial number from the specified organization and series.
	 *
	 * @param sqlSession the current SQL session.
	 * @param organizationid the ID of the organization for which the serial number is required.
	 * @param series the name of the series for which the serial number is to be created.
	 * @return the new serial number.
	 */
	public static final String pop (SqlSession sqlSession, String organizationid, String series) {

		Serial serial = new Serial(organizationid, series);
		serial = sqlSession.getMapper(SerialMapper.class).readbyexample(serial);

		if (serial == null) {			// create new series from parameters
			serial = new Serial(organizationid, series);
			serial.setFormat("100000");
			serial.setLast(1);
			sqlSession.getMapper(SerialMapper.class).create(serial);
			serial.setLast(0);
		}
		else {
			//TODO: a serial number has been previously pushed onto the stack so is popped
			//if (serial.getLast() < 0) {sqlSession.getMapper(SerialMapper.class).delete(serial.getId());}
			//else {					// new serial number is created
				Integer last = serial.getLast();
				serial.setLast(last + 1);
				sqlSession.getMapper(SerialMapper.class).update(serial);
				serial.setLast(last);
			//}
		}
		return serial.getFolio();
	}



	/**
	 * Generates RSA encryption keys which is used as follows:
	 * TripleDesKeyGenerator kg = new TripleDesKeyGenerator();
	 * String ks = kg.encodeKey(kg.generateNewKey());
	 *
	 * @return the key pair
	 */
	public static final KeyPair keyGeneratorRSA(){
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			return keyGen.generateKeyPair();
		}
		catch (NoSuchAlgorithmException e) {;}
		return null;
	}
}

// Tests the RSA key pair.
//	public String testRSA(KeyPair key){
//		try {
//			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
//			byte[] cipherText = cipher.doFinal("Test String".getBytes("UTF8"));
//
//			cipher.init(Cipher.DECRYPT_MODE, key.getPublic());
//			byte[] plainText = cipher.doFinal(cipherText);
//			return new String(plainText, "UTF8");
//		}
//		catch (NoSuchAlgorithmException e) {;}
//		catch (NoSuchPaddingException e) {;}
//		catch (InvalidKeyException e) {;}
//		catch (UnsupportedEncodingException e) {;}
//		catch (IllegalBlockSizeException e) {;}
//		catch (BadPaddingException e) {;}
//		return null;
//	}
//}