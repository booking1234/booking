/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

public class Language
extends Model {

	public enum Code {de, en, es, fr, ru, tr, nl, pl};
	public static final String DE = "DE";
	public static final String EN = "EN";
	public static final String ES = "ES";
	public static final String FR = "FR"; // Kigo
	public static final String RU = "RU";
	public static final String TR = "TR";
    public static final String NL = "NL";
    public static final String PL = "PL";
    
	private static final NameId[] translatable = {
		new NameId("English","EN"),
		new NameId("Bulgarian","BG"),
		new NameId("Catalan","CA"),
		new NameId("Czech","CS"),
		new NameId("Danish","DA"),
		new NameId("German","DE"),
		new NameId("Greek","EL"),
		new NameId("Spanish","ES"),
		new NameId("Finnish","FI"),
		new NameId("French","FR"),
		new NameId("Hindi","HI"),
		new NameId("Croatian","HR"),
		new NameId("Hungarian","HU"),
		new NameId("Indonesian","IN"),
		new NameId("Italian","IT"),
		new NameId("Japanese","JA"),
		new NameId("Korean","KO"),
		new NameId("Lithuanian","LT"),
		new NameId("Latvian","LV"),
		new NameId("Dutch","NL"),
		new NameId("Norwegian","NO"),
		new NameId("Polish","PL"),
		new NameId("Portuguese","PT"),
		new NameId("Romanian","RO"),
		new NameId("Russian","RU"),
		new NameId("Slovak","SK"),
		new NameId("Slovenian","SL"),
		new NameId("Serbian","SR"),
		new NameId("Swedish","SV"),
		new NameId("Thai","TH"),
		new NameId("Turkish","TR"),
		new NameId("Vietnamese","VI"),
		new NameId("Chinese","ZH")
	};
	
	public Service service() {return Service.PARTY;}

	public static final String getTranslatableCdlist() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < translatable.length; i++) {
			sb.append(translatable[i].getId());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static final ArrayList<NameId> getTranslatableNameIds() {
		ArrayList<NameId> nameids = new ArrayList<NameId>();
		for (int i = 0; i < translatable.length; i++) {nameids.add(translatable[i]);}
		return nameids;
	}
	
	public static final boolean isTranslatable(String code) {
		if (EN.equalsIgnoreCase(code)) {return false;}
		for (int i = 0; i < translatable.length; i++) {
			if (translatable[i].getId().equalsIgnoreCase(code)) {return true;}
		}
		return false;
	}

	public static final NameId getLanguage(String code) {
		for (int i = 0; i < translatable.length; i++) {
			if (translatable[i].getId().equalsIgnoreCase(code)) {return translatable[i];}
		}
		return translatable[0];
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Language [organizationid=");
		builder.append(organizationid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}