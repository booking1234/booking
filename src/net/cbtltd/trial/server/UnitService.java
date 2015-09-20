/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import net.cbtltd.server.api.IsService;

/** The Class UnitService responds to unit of measure requests. */
public class UnitService
implements IsService {

	private static UnitService service;

	/**
	 * Gets the single instance of UnitService to manage Unit instances.
	 * @see net.cbtltd.shared.Unit
	 *
	 * @return single instance of UnitService.
	 */
	public static synchronized UnitService getInstance() {
		if (service == null) {service = new UnitService();}
		return service;
	}
}
