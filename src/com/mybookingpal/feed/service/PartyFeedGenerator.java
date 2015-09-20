package com.mybookingpal.feed.service;

import net.cbtltd.rest.Items;
import net.cbtltd.rest.PartyRest;

public class PartyFeedGenerator extends PartyRest {

	private static PartyFeedGenerator partyFeedGenerator;
	
	private PartyFeedGenerator() {}
	private Integer ROWS = new Integer(100000);
	
	public static PartyFeedGenerator getInstance() {
		if(partyFeedGenerator == null) {
			partyFeedGenerator = new PartyFeedGenerator();
		}
		return partyFeedGenerator;
	}
	
	public Items generateProductValues( String pos) { // what is the POS parameter used for in this method?
		return  getPMNameIds(10000,null); // What is 10000?
	}
}

