/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;


public class Rates {
	public Collection<Rate> Rate;

	public Rates(){}
	
	public Rates(Collection<Rate> rate) {
		super();
		this.Rate = rate;
	}
}
