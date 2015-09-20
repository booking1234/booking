/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Session
extends Model {

	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";

	public static final String LOGGED_OUT = "Logged Out";
	public static final String LOGGED_IN = "Logged In";
	public static final String SETTING_PASSWORD = "Setting Password";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = {LOGGED_OUT, LOGGED_IN, SETTING_PASSWORD, SUSPENDED};

	private String organizationname;
	private String actorid;
	private String actorname;
	private String emailaddress;
	private String country;
	private String currency;
	private String language;
	private String formatdate;
	private String formatphone;
	private Date login;
	private Date logout;
	private Integer rank = 3;
	private int status;
	private short[] permission;

	public Service service() {return Service.SESSION;}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public boolean noActorid() {
		return actorid == null || actorid.isEmpty();
	}

	public String getActorname() {
		return actorname;
	}

	public void setActorname(String actorname) {
		this.actorname = actorname;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean noLanguage() {
		return language == null || language.isEmpty();
	}

	public String getFormatdate() {
		return formatdate;
	}

	public void setFormatdate(String formatdate) {
		this.formatdate = formatdate;
	}

	public boolean noFormatdate() {
		return formatdate == null || formatdate.isEmpty();
	}

	public String getFormatphone() {
		return formatphone;
	}

	public void setFormatphone(String formatphone) {
		this.formatphone = formatphone;
	}

	public boolean noFormatphone() {
		return formatphone == null || formatphone.isEmpty();
	}

	public Date getLogin() {
		return login;
	}

	public void setLogin(Date login) {
		this.login = login;
	}

	public Date getLogout() {
		return logout;
	}

	public void setLogout(Date logout) {
		this.logout = logout;
	}

	public boolean noLogout() {
		return logout == null;
	}
	
	public boolean hasLogout() {
		return !noLogout();
	}
	
	public boolean isLoggedOut() {
		return hasLogout() || hasState(Session.LOGGED_OUT);
	}
	
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public short[] getPermission() {
		return permission;
	}

	public void setPermission(ArrayList<String> roles) {
		if (roles == null) {return;}
		this.permission = new short[roles.size()];
		int i = 0;
		for (String role : roles) {this.permission[i++] = Short.parseShort(role);}
	}

	public void setPermissions(short[] permission) {
		this.permission = permission;
	}

	public boolean noPermission() {
		return permission == null || permission.length == 0;
	}

	public boolean hasPermission() {
		return !noPermission();
	}

	public boolean hasPermission(short permission) {
		if (this.permission == null) {return true;} //TODO: fix
		for (int i = 0; i < this.permission.length; i++) {
			if (this.permission[i] == permission){return true;}
		}
		return false;
	}

	public boolean hasPermission(short[] role) {
		if (this.permission == null || role == null) {return false;} //TODO: fix
		for (int i = 0; i < this.permission.length; i++) {
			for (int j = 0; j < role.length; j++) {
				if (this.permission[i] == role[j]) {return true;}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Session [organizationname=");
		builder.append(organizationname);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", actorname=");
		builder.append(actorname);
		builder.append(", emailaddress=");
		builder.append(emailaddress);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", language=");
		builder.append(language);
		builder.append(", formatdate=");
		builder.append(formatdate);
		builder.append(", formatphone=");
		builder.append(formatphone);
		builder.append(", login=");
		builder.append(login);
		builder.append(", logout=");
		builder.append(logout);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", status=");
		builder.append(status);
		builder.append(", permission=");
		builder.append(Arrays.toString(permission));
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
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
