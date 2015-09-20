/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import net.cbtltd.json.JSONResponse;


public class Response implements JSONResponse {

	private int API_VERSION;
	private int API_REVISION;
	private String API_METHOD;
	private String API_CALL_ID;
	private String API_RESULT_CODE;
	private String API_RESULT_TEXT;
	
	private enum Result {E_OK,E_INPUT,E_NOSUCH,E_CONFLICT,E_DEACTIVATED,E_ALREADY,E_EMPTY,E_LIMIT};
	
	Response () {}	//no args constructor
	
	public boolean isOK() {
		return API_RESULT_CODE.equalsIgnoreCase(Result.E_OK.name());
	}
	
	public Result getResult(String resultCode) {
		return Result.valueOf(resultCode);
	}

	public String getMessage() {
		return API_RESULT_TEXT;
	}
	
	public void setMessage(String message) {
		API_RESULT_TEXT = message;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [API_VERSION=");
		builder.append(API_VERSION);
		builder.append(", API_REVISION=");
		builder.append(API_REVISION);
		builder.append(", API_METHOD=");
		builder.append(API_METHOD);
		builder.append(", API_CALL_ID=");
		builder.append(API_CALL_ID);
		builder.append(", API_RESULT_CODE=");
		builder.append(API_RESULT_CODE);
		builder.append(", API_RESULT_TEXT=");
		builder.append(API_RESULT_TEXT);
		builder.append("]");
		return builder.toString();
	}
}