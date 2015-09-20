/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json;

import java.util.HashMap;


/**
 * The Interface Handler is implemented bu JSON request handlers.
 */
public interface Handler {
	String service();
	JSONResponse execute(final HashMap<String, String> parameters);
}
