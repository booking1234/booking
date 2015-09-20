package net.cbtltd.rest.edomizil.xmlexport;

public class XMLExportUtils {

	public void exportProductXML() {
		PropertyExportUtils propertyExportUtils = new PropertyExportUtils();
		propertyExportUtils.setUnitOwnerId("63353");
		propertyExportUtils.generatePropertyXML();
	}

	public void exportPricesXML() {
		PriceExportUtils priceExportUtils = new PriceExportUtils();
		priceExportUtils.setUnitOwnerId("63353");
		priceExportUtils.generatePriceXML();
	}

	public void exportAvailabilityXML() {
		AvailabilityExportUtils availabilityExportUtils = new AvailabilityExportUtils();
		availabilityExportUtils.setUnitOwnerId("63353");
		availabilityExportUtils.generateAvailabilityXML();
	}
}
