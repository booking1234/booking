package net.cbtltd.rest.common.utils.report;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cell")
public class Cell {
	String rowSpan;
	String colSpan;
String style;
Data data;
public Cell() {
	
}
public Cell(String style,String value) {
	this.style=style;
	this.data=new Data("String", value);
}

public Cell(String value) {
	this.style=null;
	this.data=new Data("String", value);
}

/**
 * @return the data
 */
@XmlElement(name="Data")
public Data getData() {
	return data;
}

/**
 * @param data the data to set
 */
public void setData(Data data) {
	this.data = data;
}

/**
 * @return the style
 */
@XmlAttribute(name="ss:StyleID")
public String getStyle() {
	return style;
}

/**
 * @param style the style to set
 */
public void setStyle(String style) {
	this.style = style;
}
/**
 * @return the rowSpan
 */
@XmlAttribute(name="ss:MergeAcross")
public String getRowSpan() {
	return rowSpan;
}
/**
 * @param rowSpan the rowSpan to set
 */
public void setRowSpan(String rowSpan) {
	this.rowSpan = rowSpan;
}
/**
 * @param rowSpan the rowSpan to set
 */
public void setRowSpan(int rowSpan) {
	this.rowSpan = String.valueOf(rowSpan);
}
/**
 * @return the colSpan
 */
@XmlAttribute(name="ss:MergeDown")
public String getColSpan() {
	return colSpan;
}
/**
 * @param colSpan the colSpan to set
 */
public void setColSpan(String colSpan) {
	this.colSpan = colSpan;
}
/**
 * @param colSpan the colSpan to set
 */
public void setColSpan(int colSpan) {
	this.colSpan = String.valueOf(colSpan);
}

}
