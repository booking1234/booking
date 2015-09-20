/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

/**
 * The Interface HasValue is implemented by types that have value.
 *
 * @param <T> the generic type of the value.
 */
public interface HasValue<T> {
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value.
	 */
	void setValue(T value);
	
	/**
	 * Gets the value.
	 *
	 * @return the value.
	 */
	T getValue();
	
	/**
	 * Checks if the type has no value.
	 *
	 * @return true, if the type has no value.
	 */
	boolean noValue();
	
	/** Fires a change event. */
	void fireChange();
}
