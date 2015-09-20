package net.cbtltd.rest.common.utils.report;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Table")
public class Table {
	String rowCount;
	String columnCount;
	String defaultRowHeight="15";
	List<Row> row=null;
	
	public Table() {
	}
	public Table(List<Row> rows) {
		super();
		this.row = rows;
	}
	/**
	 * @return the row
	 */
	@XmlElement(name="Row")
	public List<Row> getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(List<Row> row) {
		this.row = row;
	}
	/**
	 * @return the expandedColumnCount
	 */
	@XmlAttribute(name="ss:ExpandedColumnCount")
	public String getExpandedColumnCount() {
		if(columnCount!=null) return columnCount;
		return String.valueOf(Row.columnMaxCount);
	}
	
	/**
	 * @return the expandedRowCount
	 */
	@XmlAttribute(name="ss:ExpandedRowCount")
	public String getExpandedRowCount() {
		if(rowCount!=null) return rowCount;
		return String.valueOf(row.size());
	}
	
	/**
	 * @return the defaultRowHeight
	 */
	@XmlAttribute(name="ss:DefaultRowHeight")
	public String getDefaultRowHeight() {
		return defaultRowHeight;
	}
	/**
	 * @param defaultRowHeight the defaultRowHeight to set
	 */
	public void setDefaultRowHeight(String defaultRowHeight) {
		this.defaultRowHeight = defaultRowHeight;
	}
	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}
	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = String.valueOf(rowCount);
	}
	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = String.valueOf(columnCount);
	}
	
}
