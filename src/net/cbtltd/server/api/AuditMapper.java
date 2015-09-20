/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Audit;
import net.cbtltd.shared.audit.AuditTable;

public interface AuditMapper
extends AbstractMapper<Audit> {
	void delete(String id);
	Integer count(AuditTable action);
	ArrayList<Audit> list(AuditTable action);
}