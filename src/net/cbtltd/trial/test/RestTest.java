/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.test;

import net.cbtltd.server.TextService;

/**
 * The Class RazorTest is the entry point for test RPC and JSONP messages.
 * The RPC mode uses the standard GWT asynchronous messages;which requires that the widget be in an iFrame in a host HTML page.
 * The JSONP mode uses JSONP messages, which requires that the widget JavaScript be served from the host server.
 */
public class RestTest {
	
	public static void main(String[] args) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("Nox Tourism was founded in September 2003 by Richard Marshall. We are market leaders on Cape Town's Atlantic Seaboard, and continue to innovate in terms of our service to both the travel trade and to the guests that stay in our villas and apartments. ");
			sb.append("\n");
			sb.append("All of our properties are in the four or five star market, and options range from one bedroom apartments to seven bedroom villas, from penthouse apartments to beach cottages. However different they may be, each property has what we call tourism potential and has something unique and exceptional about it, whether that be views, location, luxury or a combination of these and many other factors.");
			sb.append("\n");
			sb.append("We have an obsessive focus on the guest experience, and every guest's needs are catered to by our dedicated team of guest relations consultants, property managers and housekeepers.");
			sb.append("\n");
			sb.append("Telephone: +27 21 438 6440");
			sb.append("Website: www.noxrentals.co.za");
			sb.append("Email: info@noxrentals.co.za");
			System.out.println(TextService.translate(sb.toString(), "EN", "FR"));
		} 
		catch(Throwable x) {System.out.println("Error " + x.getMessage());}
	}
}
