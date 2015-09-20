package net.cbtltd.rest.common.utils.report;

import java.io.Serializable;

/**
 * Junit test case
 * @author nibodha
 *
 */
public class ComparableField {
String fieldName;
String expectedFieldValue;
String actualFieldValue;


public ComparableField(String fieldName, String expectedFieldValue,
		String actualFieldValue) {
	super();
	this.fieldName = fieldName;
	this.expectedFieldValue = expectedFieldValue==null?"":expectedFieldValue;
	this.actualFieldValue = actualFieldValue==null?"":actualFieldValue;

}
public ComparableField(String fieldName, Object expectedFieldValue,
		Object actualFieldValue) {
	super();
	this.fieldName = fieldName;
	this.expectedFieldValue = expectedFieldValue==null?"": expectedFieldValue.toString();
	this.actualFieldValue =actualFieldValue==null?"": actualFieldValue.toString();
}

/**
 * @return the fieldName
 */
public String getFieldName() {
	return fieldName;
}
/**
 * @param fieldName the fieldName to set
 */
public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
}
/**
 * @return the expectedFieldValue
 */
public String getExpectedFieldValue() {
	return expectedFieldValue;
}
/**
 * @param expectedFieldValue the expectedFieldValue to set
 */
public void setExpectedFieldValue(int expectedFieldValue) {
	this.expectedFieldValue = String.valueOf(expectedFieldValue);
}

/**
 * @param expectedFieldValue the expectedFieldValue to set
 */
public void setExpectedFieldValue(String expectedFieldValue) {
	this.expectedFieldValue = expectedFieldValue;
}
/**
 * @param expectedFieldValue the expectedFieldValue to set
 */
public void setExpectedFieldValue(Object expectedFieldValue) {
	this.expectedFieldValue = expectedFieldValue.toString();
}
/**
 * @return the actualFieldValue
 */
public String getActualFieldValue() {
	return actualFieldValue;
}
/**
 * @param actualFieldValue the actualFieldValue to set
 */
public void setActualFieldValue(String actualFieldValue) {
	this.actualFieldValue = actualFieldValue;
}
/**
 * @param actualFieldValue the actualFieldValue to set
 */
public void setActualFieldValue(int actualFieldValue) {
	this.actualFieldValue = String.valueOf(actualFieldValue);
}
/**
 * @param actualFieldValue the actualFieldValue to set
 */
public void setActualFieldValue(Object actualFieldValue) {
	this.actualFieldValue = actualFieldValue.toString();
}
public boolean getResult(){
	if(expectedFieldValue==null&&actualFieldValue==null) return true;
	if(expectedFieldValue==null||actualFieldValue==null) return false;
	return expectedFieldValue.equalsIgnoreCase(actualFieldValue);
}

public String getResultString(){
	return getResult()?"same":"diff";
}
public String getCompareString(){
	return "Field ["+ fieldName +"] :- "+ expectedFieldValue+"="+actualFieldValue+"?"+getResultString();
}
}
