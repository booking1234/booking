package com.mybookingpal.utils;

/**
* this class acts as a container to our thread local variables.
* @author cshah
*
*/
public class BPThreadLocal {
 
	private static final ThreadLocal<Boolean> context = new ThreadLocal<Boolean>();
 
	public static void set(Boolean skipYielRules) {
		context.set(skipYielRules);
	}
 
	public static void unset() {
		context.remove();
	}

	public static Boolean get() {
		return context.get();
	}
}
