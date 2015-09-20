/**
 * @author David Lepe
 */

package com.mybookingpal.utils.POSFinder;

import net.cbtltd.shared.Model;

// Given a party ID, will print out the POS


public class POSFinder {

	
	public static void findPOS(String partyID) {
		System.out.println(Model.encrypt(partyID));
	}
	
	public static void findPartyID(String pos) {
		System.out.println(Model.decrypt(pos));
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		findPOS("189853");
		findPOS("189790");

	}

}
