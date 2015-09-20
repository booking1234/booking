/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Alert;
import net.cbtltd.shared.alert.AlertTable;
import net.cbtltd.shared.api.HasAlert;

public interface AlertMapper
extends AbstractMapper<Alert> {
	void delete(String id);
	Alert duplicate(HasAlert action);
	ArrayList<Alert> exists(HasAlert action);
	Integer count(AlertTable action);
	ArrayList<Alert> list(AlertTable action);
}