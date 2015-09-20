/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public enum Service {
	ACCOUNT ("AccountService"),
	ALERT ("AlertService"),
	ASSET ("AssetService"),
	ATTRIBUTE ("AttributeService"),
	AUDIT ("AuditService"),
	CONTRACT ("ContractService"),
	FINANCE ("FinanceService"),
	IMAGE ("ImageService"),
	IMAGETEXT ("ImageTextService"),
	JOURNAL ("JournalService"),
	LEASE ("LeaseService"),
	LICENSE ("LicenseService"),
	LOCATION ("LocationService"),
	MAIL ("MailService"),
	MONITOR ("MonitorService"),
	PARTNER ("PartnerService"),
	PARTY ("PartyService"),
	PRICE ("PriceService"),
	PRODUCT ("ProductService"),
	RATE ("RateService"),
	REPORT ("ReportService"),
	RESERVATION ("ReservationService"),
	RULE ("RuleService"),
	SESSION ("SessionService"),
	SMS ("SmsService"),
	TASK ("TaskService"),
	TAX ("TaxService"),
	TEXT ("TextService"),
	SERIAL ("SerialService"),
	TYPE ("TypeService"),
	UNIT ("UnitService"),
	WORKFLOW ("WorkflowService"),
	WEB ("WebService");

	private String path = "net.cbtltd.server.";
	private String classname;

	Service(String classname) {
		this.classname = classname;
	}

	public String classname() { return path + classname; }
}
