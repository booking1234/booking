/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import net.cbtltd.shared.Serial;

public interface SerialMapper
extends AbstractMapper<Serial> {
	Serial readbyexample(Serial action);
	void delete(String id);
}