/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.HashMap;

import net.cbtltd.shared.Text;

public interface HasTexts {
	HashMap<String, Text> getTexts();
	boolean noTexts();
	boolean hasTexts();
}
