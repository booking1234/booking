package net.cbtltd.json.culture;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.shared.JSONRequest;

/**
 * Handles the request for availability of a property for a range of dates.
 */
public class CultureHandler implements Handler {

	public String service() {return JSONRequest.CULTURE.name();}

	/**
	 * Gets the culture of the request.
	 * @return the culture.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		CultureWidgetItem result =new CultureWidgetItem();
		try {result.setCulture(parameters.get("culture"));}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		return result;
	}	

}
