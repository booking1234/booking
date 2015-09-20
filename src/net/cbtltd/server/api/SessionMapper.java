/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Session;

public interface SessionMapper
extends AbstractMapper<Session> {
	ArrayList<String> steps(String actorids); //Steps for cd list of actor(s)
	ArrayList<NameId> organizationsbyagent(Session session); //OrganizationByAgent
	ArrayList<NameId> search(NameIdAction action); //Search on type
}
