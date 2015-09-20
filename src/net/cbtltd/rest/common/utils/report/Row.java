package net.cbtltd.rest.common.utils.report;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Row")
public class Row {

	static int columnMaxCount=0; 
	String autoFitHeight="0";
	public Row() {
	}
	public Row(List<Cell> cells) {
		super();
		this.cells = cells;
	}

	List<Cell> cells;

	/**
	 * @return the cells
	 */
	@XmlElement(name="Cell")
	public List<Cell> getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(List<Cell> cells) {
		this.cells = cells;
		if(columnMaxCount<cells.size()) columnMaxCount=cells.size();
	}

	/**
	 * @return the autoFitHeight
	 */
	@XmlAttribute(name="ss:AutoFitHeight")
	public String getAutoFitHeight() {
		return autoFitHeight;
	}

	/**
	 * @param autoFitHeight the autoFitHeight to set
	 */
	public void setAutoFitHeight(String autoFitHeight) {
		this.autoFitHeight = autoFitHeight;
	}
	
}
