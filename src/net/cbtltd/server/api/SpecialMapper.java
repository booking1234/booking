/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.rest.Quote;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Special;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.LookBook;

public interface SpecialMapper {
	void update(Special action);
	void deletequotecollision(Reservation reservation); //DeleteQuoteCollision
	void delete(String id);
	void deleteall(); //DeleteAll
	void refresh(Yield action);
	ArrayList<Special> readall(); //SelectAll
	Integer specialcount(LookBook available); //Special count
	ArrayList<AvailableItem> speciallist(LookBook available); //Special list
	ArrayList<Quote> quotesbylocationid(String[] locationids); //Quotes for REST
	ArrayList<Quote> quotesbyarea(Area area);
}
