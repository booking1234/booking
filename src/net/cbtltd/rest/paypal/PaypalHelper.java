package net.cbtltd.rest.paypal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PaypalHelper {
	
	public static String getJsonFromString(String exceptionMessage) throws Exception {
		int from = exceptionMessage.indexOf("{");
		int to = exceptionMessage.lastIndexOf("}") + 1;
		if(from == -1 || to == -1) {
			throw new Exception("Wrong JSON object was received: " + exceptionMessage);
		}
		String json = exceptionMessage.substring(from, to);
		return json;
	}
	
	public static String getParamValueFromJson(String jsonString, String paramName) throws Exception {
		String error = getJsonFromString(jsonString);
		if(isWrappedAsJson(error)) {
			JsonElement root = new JsonParser().parse(error);
			JsonElement parameter = root.getAsJsonObject().get(paramName);
			if(parameter == null) {
				if(root.getAsJsonObject().get("error") != null && root.getAsJsonObject().get("error").getAsString().equals("invalid_client")){
					error = "Invalid PayPal client credentials";
				} else {
					JsonArray detailsArray = root.getAsJsonObject().get("details").getAsJsonArray();
					if(detailsArray != null) {
						parameter = detailsArray.get(0).getAsJsonObject().get(paramName);
					}
				}
			}
			String errorDescription = parameter == null ? error : parameter.getAsString(); 
			return errorDescription;
		} else {
			return error;
		}
	}
	
	private static boolean isWrappedAsJson(String json) {
		return json.trim().startsWith("{") && json.endsWith("}");
	}
}
