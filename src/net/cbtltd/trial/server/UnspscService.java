/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import net.cbtltd.server.api.IsService;


/** The Class UnspscService responds to product UNSPSC code requests. */
public class UnspscService
implements IsService {

	private static UnspscService service;

	/**
	 * Gets the single instance of UnspscService to manage Unspsc instances.
	 * @see net.cbtltd.shared.Unspsc
	 *
	 * @return single instance of UnspscService.
	 */
	public static synchronized UnspscService getInstance() {
		if (service == null) {service = new UnspscService();}
		return service;
	}

}
