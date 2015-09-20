package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Season")
public class Season {

	@XmlAttribute(name = "DateFrom")
    @XmlSchemaType(name = "date")
    protected String dateFrom;
	@XmlAttribute(name = "DateTo")
    @XmlSchemaType(name = "date")
    protected String dateTo;
	@XmlElement(name = "Price", required = true)
	protected Double price;
	@XmlElement(name = "Extra")
	protected Double extra;
	@XmlElement(name = "LOSS")
	protected LOSS loss;
	@XmlElement(name = "EGPS")
	protected EGPS egps;
	
	/**
	 * @return the start of the range when a price is valid (YYYY-MM-DD format)
	 */
	public String getDateFrom() {
		return this.dateFrom;
	}
	
	/**
	 * set the start of the range when a price is valid (YYYY-MM-DD format)
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	/**
	 * @return the end of the range when a price is valid (YYYY-MM-DD format)
	 */
	public String getDateTo() {
		return this.dateTo;
	}
	
	/**
	 * set the end of the range when a price is valid (YYYY-MM-DD format)
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	/**
	 * @return the property price for standard number of guests defined for a property
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * set the property price for standard number of guests defined for a property
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return the extra fee for each number of guests greater than standard number of guests
	 */
	public double getExtra() {
		return this.extra;
	}
	
	/**
	 * set the extra fee for each number of guests greater than standard number of guests
	 */
	public void setExtra(double extra) {
		this.extra = extra;
	}
	
	/**
	 * @return the LOSS (collection of nightly price settings depending on length of stay)
	 */
	public LOSS getLOSS() {
		return this.loss;
	}
	
	/**
	 * set the LOSS (collection of nightly price settings depending on length of stay)
	 */
	public void setLOSS(LOSS loss) {
		this.loss = loss;
	}
	
	/**
	 * @return the EGPS (collection of extra person price settings depending on number of extra guests)
	 */
	public EGPS getEGPS() {
		return this.egps;
	}
	
	/**
	 * set the eGPS (collection of extra person price settings depending on number of extra guests)
	 */
	public void setEGPS(EGPS egps) {
		this.egps = egps;
	}
	
}
