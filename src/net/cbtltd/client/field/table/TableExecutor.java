/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.table;

import net.cbtltd.shared.api.HasTableService;

public interface TableExecutor<T extends HasTableService> {
	void execute(T action);
}
