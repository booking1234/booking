package net.cbtltd.rest.common.utils.report;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Worksheet")
public class Worksheet {

	public Worksheet() {
		// TODO Auto-generated constructor stub
	}
	public Worksheet(String name) {
	this.name=name;
	}
	
	String name;
	Table table;
	
	@XmlAttribute(name="ss:Name")
    public String getName(){
        return name;
    }

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="Table")
    public Table getTable() {
        return table;
    }

	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}

}
