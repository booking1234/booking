/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class Partner extends Model {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	
	public static final String ANOTHER = "another";

	public static final String NAME = "name";
	public static final String PARTYNAME = "partyname";

	private static boolean noCron(String cron) {
		return cron == null || cron.split(" ").length != 5 || cron.split(" ")[2].equalsIgnoreCase("0");
	}
	
	private static boolean hasCron(String cron) {
		return !noCron(cron);
	}
	
	private String partyid;
	private String partyname;
	private String bookemailaddress;
	private String bookwebaddress;
	private String apikey;
	private String currency;
	private String dateformat;
	private String webaddress;
	private String alertcron;
	private String pricecron;
	private String productcron;
	private String schedulecron;
	private String specialcron;
	private Integer alertwait;
	private Integer pricewait;
	private Integer productwait;
	private Integer schedulewait;
	private Integer specialwait;
	private Double commission;
	private Double discount;
	private Double subscription;
	private Double transaction;
	private Boolean supportscreditcard;
	private Boolean sendconfirmationemails;
	/* Affects on pm registration process:
	 * 1 - allow pm registration without products;
	 * 0 - donot allow (default). 
	 */
	private Boolean registeremptyproperties;
	/* Affects on pm registration process:
	 * 1 - add record into partner table for new account;
	 * 0 - donot add (default). 
	 */
	private Boolean separatepmaccounts;
	private Boolean bookoffline;
	private String abbrevation;

	public Service service() {return Service.PARTNER;}

	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getBookemailaddress() {
		return bookemailaddress;
	}

	public void setBookemailaddress(String bookemailaddress) {
		this.bookemailaddress = bookemailaddress;
	}

	public String getBookwebaddress() {
		return bookwebaddress;
	}

	public void setBookwebaddress(String bookwebaddress) {
		this.bookwebaddress = bookwebaddress;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getAlertcron() {
		return alertcron;
	}

	public void setAlertcron(String alertcron) {
		this.alertcron = alertcron;
	}

	public boolean hasAlertcron() {
		return hasCron(alertcron);
	}

	public String getPricecron() {
		return pricecron;
	}

	public void setPricecron(String pricecron) {
		this.pricecron = pricecron;
	}

	public boolean hasPricecron() {
		return hasCron(pricecron);
	}

	public String getProductcron() {
		return productcron;
	}

	public void setProductcron(String productcron) {
		this.productcron = productcron;
	}

	public boolean hasProductcron() {
		return hasCron(productcron);
	}

	public String getSchedulecron() {
		return schedulecron;
	}

	public void setSchedulecron(String schedulecron) {
		this.schedulecron = schedulecron;
	}

	public boolean hasSchedulecron() {
		return hasCron(schedulecron);
	}

	public String getSpecialcron() {
		return specialcron;
	}

	public void setSpecialcron(String specialcron) {
		this.specialcron = specialcron;
	}

	public boolean hasSpecialcron() {
		return hasCron(specialcron);
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getSubscription() {
		return subscription;
	}

	public void setSubscription(Double subscription) {
		this.subscription = subscription;
	}

	public Double getTransaction() {
		return transaction;
	}

	public void setTransaction(Double transaction) {
		this.transaction = transaction;
	}

	public Boolean getBookoffline() {
		return bookoffline;
	}

	public void setBookoffline(Boolean bookoffline) {
		this.bookoffline = bookoffline;
	}

	public Integer getAlertwait() {
		return alertwait;
	}

	public void setAlertwait(Integer alertwait) {
		this.alertwait = alertwait;
	}

	public Integer getPricewait() {
		return pricewait;
	}

	public void setPricewait(Integer pricewait) {
		this.pricewait = pricewait;
	}

	public Integer getProductwait() {
		return productwait;
	}

	public void setProductwait(Integer productwait) {
		this.productwait = productwait;
	}

	public Integer getSchedulewait() {
		return schedulewait;
	}

	public void setSchedulewait(Integer schedulewait) {
		this.schedulewait = schedulewait;
	}

	public Integer getSpecialwait() {
		return specialwait;
	}

	public void setSpecialwait(Integer specialwait) {
		this.specialwait = specialwait;
	}
	

	public Boolean getSupportscreditcard() {
		return supportscreditcard;
	}

	public void setSupportscreditcard(Boolean supportscreditcard) {
		this.supportscreditcard = supportscreditcard;
	}

	public Boolean getSendconfirmationemails() {
		return sendconfirmationemails;
	}

	public void setSendconfirmationemails(Boolean sendconfirmationemails) {
		this.sendconfirmationemails = sendconfirmationemails;
	}
	
	public Boolean getRegisteremptyproperties() {
		return registeremptyproperties;
	}

	public void setRegisteremptyproperties(Boolean registeremptyproperties) {
		this.registeremptyproperties = registeremptyproperties;
	}

	public Boolean getSeparatepmaccounts() {
		return separatepmaccounts;
	}

	public void setSeparatepmaccounts(Boolean separatepmaccounts) {
		this.separatepmaccounts = separatepmaccounts;
	}

	public String getAbbrevation() {
		return abbrevation;
	}

	public void setAbbrevation(String abbrevation) {
		this.abbrevation = abbrevation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Partner [partyid=");
		builder.append(partyid);
		builder.append(", partyname=");
		builder.append(partyname);
		builder.append(", bookemailaddress=");
		builder.append(bookemailaddress);
		builder.append(", bookwebaddress=");
		builder.append(bookwebaddress);
		builder.append(", apikey=");
		builder.append(apikey);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", dateformat=");
		builder.append(dateformat);
		builder.append(", webaddress=");
		builder.append(webaddress);
		builder.append(", alertcron=");
		builder.append(alertcron);
		builder.append(", pricecron=");
		builder.append(pricecron);
		builder.append(", productcron=");
		builder.append(productcron);
		builder.append(", schedulecron=");
		builder.append(schedulecron);
		builder.append(", specialcron=");
		builder.append(specialcron);
		builder.append(", alertwait=");
		builder.append(alertwait);
		builder.append(", pricewait=");
		builder.append(pricewait);
		builder.append(", productwait=");
		builder.append(productwait);
		builder.append(", schedulewait=");
		builder.append(schedulewait);
		builder.append(", specialwait=");
		builder.append(specialwait);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", subscription=");
		builder.append(subscription);
		builder.append(", transaction=");
		builder.append(transaction);
		builder.append(", bookoffline=");
		builder.append(bookoffline);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", state=");
		builder.append(state);
		builder.append(",supportscreditcard =");
		builder.append(supportscreditcard);
		builder.append(", sendconfirmationemails=");
		builder.append(sendconfirmationemails);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	
}