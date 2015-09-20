package net.cbtltd.rest.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XmlType
public class ApiResponse {
	@XStreamOmitField
	private String errorMessage;
	@XStreamOmitField
	private Boolean error;

	public static final boolean ERROR = true;
	public static final boolean SUCCESS = false;
	
	public ApiResponse() {
		super();
		errorMessage = "";
		error = SUCCESS;
	}

	public ApiResponse(String errorMessage) {
		this.error = ERROR;
		this.errorMessage = errorMessage;
	}

	@XmlElement(name = "message")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.error = ERROR;
		this.errorMessage = errorMessage;
	}

	@XmlElement(name = "is_error")
	public Boolean isError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
}
