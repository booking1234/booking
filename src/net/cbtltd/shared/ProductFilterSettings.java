package net.cbtltd.shared;

import java.util.Arrays;
import java.util.List;

public class ProductFilterSettings {

	private String field;
	private List<String> fieldValues=null;
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return the fieldsValues
	 */
	public List<String> getFieldsValues() {
		return fieldValues;
	}
	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
	
	/**
	 * @param fieldValues the values to set
	 */
	public void setValues(String values) {
		if(values!=null&&values.length()>0)
			this.fieldValues = Arrays.asList(values.toLowerCase().split(","));
	}
}
