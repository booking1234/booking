/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.party;

import java.util.ArrayList;

import net.cbtltd.shared.Party;
import net.cbtltd.shared.Workflow;

public class Organization
extends Party {

	public enum Paytype {None, Print, Email, SMS};
	private ArrayList<String> currencies;
	private ArrayList<String> languages;
	private ArrayList<Workflow> workflows;
	
	public ArrayList<Workflow> getWorkflows() {
		return workflows;
	}

	public void setWorkflows(ArrayList<Workflow> workflows) {
		this.workflows = workflows;
	}

	public ArrayList<String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(ArrayList<String> currencies) {
		this.currencies = currencies;
	}

	public boolean noCurrencies() {
		return currencies == null || currencies.isEmpty();
	}

	public ArrayList<String> getLanguages() {
		return languages;
	}

	public void setLanguages(ArrayList<String> languages) {
		this.languages = languages;
	}

	public boolean noLanguages() {
		return languages == null || languages.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Organization [currencies=");
		builder.append(currencies);
		builder.append(", languages=");
		builder.append(languages);
		builder.append(", workflows=");
		builder.append(workflows);
		builder.append(super.toString());
		return builder.toString();
	}
}
