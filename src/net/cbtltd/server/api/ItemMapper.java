/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Event;
import net.cbtltd.shared.api.HasItem;

public interface ItemMapper<T extends HasItem> {
	void create(T item);
	void update(T item);
	void create(Event<T> event, T item);
	void update(Event<T> event, T item);
	ArrayList<T> eventitem(Event<T> event);
}
