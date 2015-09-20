/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class Country
extends Model {
	public enum Code {AT,	AU,	BE,	CA,	CH, CZ,	DE,	DK,	ES, FI,	FR,	GB,	HR,	IT,	NL, NO,	PL, SE, US};
	public static final String US = "US";
	public static final String US_NAME = "United States";
	
    private String currency;
    private String language;
    
	public Country() {}

	public Country(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public Service service() {return Service.LOCATION;}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [currency=");
		builder.append(currency);
		builder.append(", language=");
		builder.append(language);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}