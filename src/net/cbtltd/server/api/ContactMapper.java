/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.reservation.Brochure;
import net.cbtltd.shared.task.Contact;

public interface ContactMapper
extends AbstractMapper<Contact> {
	Brochure brochure(String id);
	Integer countcontacts(HasTableService action);
	ArrayList<Contact> listcontacts(HasTableService action);
}