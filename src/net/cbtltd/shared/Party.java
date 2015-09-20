/**
 * @author	abookingnet
 * @see		License at http://razor~cloud.com/razor/License.html
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasXsl;
import net.cbtltd.shared.party.Address;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "party")
@Description(value = "Party data", target = DocTarget.RESPONSE)
public class Party 
extends Entity
implements HasXsl {

	// Preferences
	public enum Preference {LATITUDE, LONGITUDE, PRICEUNIT, ZOOM};

	// Constants
	public static final String WIDGET = "0";
	public static final String ADMINISTRATOR = "1";
	public static final String NO_ACTOR = "2";
	public static final String CBT_LTD_PARTY = "4";
	public static final String ALL_ORGANIZATIONS = "-1";
	public static final String PASSWORD = "password";
	
	//Fields
	public static final String DAYPHONE = "party.dayphone";
	public static final String EMAILADDRESS = "party.emailaddress";
	public static final String MOBILEPHONE = "party.mobilephone";
	public static final String RANK = "party.rank";

	// States
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	
	public static final String[] STATES = {INITIAL, CREATED, SUSPENDED, FINAL};

	public static final String PHONE = "(###)###-####";
	//Date Formats
	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";
	public static final String MMDDYYYY = "MM-dd-yyyy";
	public static final String DDMMYYYY = "dd-MM-yyyy";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String HH_MM = " HH:mm";
	public static final String HH_MM_SS = " HH:mm:ss";

	public static final String[] DATE_FORMATS = {MM_DD_YYYY, DD_MM_YYYY, YYYY_MM_DD, MMDDYYYY, DDMMYYYY, YYYYMMDD};
	
	public static final String[] DEPOSITS = {"% Booking Value", "% Daily Rate", "Amount per Booking", "Amount per Day"};
			
	public static final String[] TIME_INTERVAL = {"0:DAY", "1:DAY", "3:DAY", "5:DAY", "7:DAY", "10:DAY", "2:WEE", "1:MON", "2:MON", "3:MON"};

	public enum Type {Actor, Affiliate, Agent, Customer, Employee, Employer, Jurisdiction, Organization, Owner, Supplier};
	
	public enum Value {Countunit, Currency, Distance, DistanceUnit, Deposit, DepositType, CancelType, CancelTime, CancelRefund, CancelFee, DamageCoverageType, DamageInsurance, Interhome, Latitude, Longitude, LookBookCount, Payfull, Payunit, Paytype, Priceunit, 
		ProgressActivity, ProgressActivityMax, ProgressAge, ProgressAgeMax, ProgressBrochure, ProgressBrochureMax, 
		ProgressConfirm, ProgressCreator, ProgressCreatorMax, ProgressValue, ProgressValueMax, ZoomLevel};

		/**
		 * Gets the specified name in natural order (first + last).
		 * @param name the specified name.
		 * @return the natural name.
		 */
		public static final String getNaturalName(String name) {
			if (name == null || name.isEmpty()){return BLANK;}
			String[] args = name.split(",");
			if (args.length < 2) {return name;}
			else {return args[1] + " " + args[0];}
		}

		/**
		 * Checks if the specified value is a valid email address.
		 *
		 * @param value the specified value.
		 * @return true, if a valid email address, otherwise false.
		 * @see <pre>http://www.regular-expressions.info/email.html</pre>
		 * Pattern = <pre>"[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}"</pre>
		 */
		public static final boolean isEmailAddress(String value) {
			if (value == null || value.trim().isEmpty()) {return false;}
			//if (value.toLowerCase().startsWith("unknown")) {return false;}
			String[] args = value.split("@");
			if (args.length != 2) {return false;}
			if (args[0].replaceAll("[A-Za-z0-9._+-]", "").length() > 0) {return false;}
			if (args[1].replaceAll("[A-Za-z0-9.-]", "").length() > 0) {return false;}
			args = args[1].split("\\.");
			if (args.length < 2) {return false;}
			return true;
		}

		/**
		 * Checks if the specified value is a valid phone number.
		 * 
		 * @param value the specified value.
		 * @return true, if a valid phone number, otherwise false.
		 */
		public static final boolean isPhoneNumber(String value)
		{
			if (value == null || value.trim().isEmpty()) {return true;}
			int len = value.length();
			int digit_count = 0;
			boolean bracket_opened = false;

			// the valid characters are digits, "+" (first character), "-", " ", "(" and ")"
			for (int i = 0; i < len; i++) {
				char c = value.charAt(i);
				if (Character.isDigit(c) == true){digit_count++;}
				else if (c == '+'){if (i > 0){return false;}}
				else if (c == '#'){digit_count++;}
				else if (c == '*'){digit_count++;}
				else if ((c == '-') || (c == ' ')){;}
				else if (c == '('){
					if (bracket_opened == true){return false;}
					bracket_opened = true;
				}
				else if (c == ')'){
					if (bracket_opened == false){return false;}
					bracket_opened = false;
				}
				else {return false;}
			}
			if (bracket_opened == true){return false;}
			if (digit_count < 2){return false;}
			return true;
		}

		/**
		 * Sets the specified string into US phone number format
		 *
		 * @param text the specified string
		 * @return the US phone number
		 */
		public static String setPhoneNumber(String text) {
		    String newtext = text.replaceAll("\\D+", "");
		    if (newtext.length() == 10) {return "(" + text.substring(0, 3) + ") " + text.substring(3, 6) + "-" + text.substring(6, 10);}
		    else {return text;}
		}

		protected String employerid;
		protected String creatorid;
		protected String financeid;
		protected String jurisdictionid;
		protected String oldstate;
		protected String extraname;
		protected String identitynumber;
		protected String taxnumber;
		protected String emailaddress;
		protected String webaddress;
		protected String dayphone;
		protected String nightphone;
		protected String faxphone;
		protected String mobilephone;
		protected String notes;
		protected String country;
		protected String language;
		protected String formatdate;
		protected String formatphone;
		protected Date birthdate;
		protected Integer rank;
		protected String password;
		protected String postalcode;
		private boolean manager;		
		private boolean agent;
		private String message;
		private String usertype;
		private String xsl; //NB HAS GET&SET = private
		private ArrayList<String> roles;
		private ArrayList<String> types;
		private boolean skipLicense;
		
		protected Address address = new Address("");

		public Party() {super(NameId.Type.Party.name());}

		public Service service() {return Service.PARTY;}

		
		public boolean isManager() {
			return manager;
		}

		public void setOrganization(boolean manager) {
			this.manager = manager;
		}

		public boolean isAgent() {
			return agent;
		}

		public void setAgent(boolean agent) {
			this.agent = agent;
		}

		@Description(value = "Identity of the employer of the party", target = DocTarget.METHOD)
		public String getEmployerid() {
			return employerid;
		}

		public void setEmployerid(String parentid) {
			this.employerid = parentid;
		}

		public boolean noEmployerid() {
			return employerid == null || employerid.isEmpty();
		}

		@XmlTransient
		public String getCreatorid() {
			return creatorid;
		}
		
		public void setCreatorid(String creatorid) {
			this.creatorid = creatorid;
		}
		
		public boolean noCreatorid(String creatorid) {
			return this.creatorid == null || creatorid == null || !this.creatorid.equalsIgnoreCase(creatorid);
		}
		
		public boolean hasCreatorid(String creatorid) {
			return !noCreatorid(creatorid);
		}
		
		@Description(value = "Identity of the primary bank account of the party", target = DocTarget.METHOD)
		public String getFinanceid() {
			return financeid;
		}

		public void setFinanceid(String financeid) {
			this.financeid = financeid;
		}

		public boolean noFinanceid() {
			return financeid == null || financeid.isEmpty() || financeid.equals(Model.ZERO);
		}

		@Description(value = "Identity of the jurisdiction (for tax) of the party", target = DocTarget.METHOD)
		public String getJurisdictionid() {
			return jurisdictionid;
		}

		public void setJurisdictionid(String jurisdictionid) {
			this.jurisdictionid = jurisdictionid;
		}

		@XmlTransient
		public String getOldstate() {
			return oldstate;
		}

		public void setOldstate(String oldstate) {
			this.oldstate = oldstate;
		}

		public boolean hasOldstate(String oldstate) {
			return this.oldstate != null && this.oldstate.equals(oldstate);
		}

		public void setName(String familyname, String firstname) {
			if (firstname == null || firstname.isEmpty()) {this.name = familyname;}
			else if (familyname == null || familyname.isEmpty()) {this.name = firstname;}
			else {this.name = familyname.trim() + ", " + firstname.trim();}
		}

		@Description(value = "First name of the party if an individual", target = DocTarget.METHOD)
		public String getFirstName() {
			if (name == null || name.isEmpty()) {return null;}
			String[] args = name.split(",");
			return args.length > 1 ? args[1].trim() : args[0].trim();
		}

		public boolean noFirstName() {
			return getFirstName() == null || getFirstName().trim().isEmpty();
		}

		@Description(value = "Name if an organization, or family or last name of the party if an individual", target = DocTarget.METHOD)
		public String getFamilyName() {
			if (name == null || name.isEmpty()){return null;}
			String[] args = name.split(",");
			return args[0].trim();
		}

		public boolean noFamilyName() {
			return getFamilyName() == null || getFamilyName().trim().isEmpty();
		}

		@Description(value = "Name of additional contact of the party", target = DocTarget.METHOD)
		public String getExtraname() {
			return extraname;
		}

		public void setExtraname(String extraname) {
			this.extraname = extraname;
		}

		@Description(value = "Identification number of the party", target = DocTarget.METHOD)
		public String getIdentitynumber() {
			return identitynumber;
		}

		public void setIdentitynumber(String identitynumber) {
			this.identitynumber = identitynumber;
		}

		@Description(value = "Tax number of the party in the jurisdiction", target = DocTarget.METHOD)
		public String getTaxnumber() {
			return taxnumber;
		}

		public void setTaxnumber(String taxnumber) {
			this.taxnumber = taxnumber;
		}

		public boolean noTaxnumber() {
			return taxnumber == null || taxnumber.trim().isEmpty();
		}

		public boolean hasTaxnumber() {
			return !noTaxnumber();
		}

		@Description(value = "Postal address of the party", target = DocTarget.METHOD)
		public String getPostaladdress() {
			return address.getPostalAddress();
		}

		public void setPostaladdress(String postaladdress) {
			address = new Address(postaladdress);
		}

		public boolean noPostaladdress() {
			return address == null;
		}

		public boolean hasPostaladdress() {
			return !noPostaladdress();
		}

//		public String getPostaladdress(int line) {
//			String[] address = postaladdress == null ? null : postaladdress.split("\\n");
//			return (address == null || address.length < line) ? null : address[line];
//		}

//		@Description(value = "Physical or street address lines of the property", target = DocTarget.METHOD)
//		public String[] getAddress() {
//			return postaladdress == null ? null : postaladdress.split("[\\r\\n]+");
//		}

		public void setAddress(String[] address) {
			//
		}

//		public String getAddress(int line) {
//			String[] address = getAddress();
//			return (address == null || line < 0 || line >= address.length) ? null : address[line];
//		}

//		public boolean noAddress(int line) {
//			return getAddress(line) == null || getAddress(line).isEmpty();
//		}
		
		public String getLocalAddress() {
			return address.getAddress();
		}
		
		public void setLocalAddress(String localAddress) {
			address.setAddress(localAddress);
		}
		
		@Description(value = "City or other location of the party", target = DocTarget.METHOD)
		public String getCity() {
			return address.getCity();
		}

		public void setCity(String city) {
			this.address.setCity(city);
		}

		@Description(value = "State, province, county or other region code of the party", target = DocTarget.METHOD)
		public String getRegion() {
			return address.getState();
		}

		public void setRegion(String region) {
			address.setState(region);
		}

		@Description(value = "Postal code or zip number of the party", target = DocTarget.METHOD)
		public String getPostalcode() {
			/*if(postalcode == null || postalcode.trim().equals("")) {
				return address.getZip();
			} else {*/
				return postalcode;
			/*}*/
		}

		public void setPostalcode(String postalcode) {
			//address.setZip(postalcode);
			this.postalcode = postalcode; 
		}

		@Description(value = "Three character ISO country code of the party", target = DocTarget.METHOD)
		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public boolean noCountry() {
			return country == null || country.trim().isEmpty();
		}

		@Description(value = "Two character ISO language code of the party", target = DocTarget.METHOD)
		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public boolean noLanguage() {
			return language == null || language.trim().isEmpty();
		}

		@XmlTransient
		public String getFormatdate() {
			return formatdate;
		}

		public void setFormatdate(String formatdate) {
			this.formatdate = formatdate;
		}

		@XmlTransient
		public String getFormatphone() {
			return formatphone;
		}

		public void setFormatphone(String formatphone) {
			this.formatphone = formatphone;
		}

//		@XmlTransient
		public String getEmailaddresses() {
			return emailaddress;
		}

		public void setEmailaddress(String emailaddress) {
			this.emailaddress = emailaddress;
		}

		public boolean noEmailaddress() {
			return emailaddress == null || emailaddress.trim().isEmpty() || !isEmailAddress(emailaddress);
		}

//		@XmlTransient
		public String getEmailaddress() {
			return noEmailaddress() ? null : emailaddress.split(",")[0];
		}

		@Description(value = "Home web address of the party", target = DocTarget.METHOD)
		public String getWebaddress() {
			return webaddress;
		}

		public void setWebaddress(String webaddress) {
			this.webaddress = webaddress;
		}

		@Description(value = "Primary phone number of the party during working hours", target = DocTarget.METHOD)
		public String getDayphone() {
			return dayphone;
		}

		public void setDayphone(String dayphone) {
			this.dayphone = dayphone;
		}

		@Description(value = "Primary phone number of the party out of working hours", target = DocTarget.METHOD)
		public String getNightphone() {
			return nightphone;
		}

		public void setNightphone(String nightphone) {
			this.nightphone = nightphone;
		}

		@Description(value = "Fax number of the party", target = DocTarget.METHOD)
		public String getFaxphone() {
			return faxphone;
		}

		public void setFaxphone(String faxphone) {
			this.faxphone = faxphone;
		}

		@Description(value = "Mobile phone number of the party", target = DocTarget.METHOD)
		public String getMobilephone() {
			return mobilephone;
		}

		public void setMobilephone(String mobilephone) {
			this.mobilephone = mobilephone;
		}

		public boolean noMobilephone() {
			return mobilephone == null || mobilephone.trim().isEmpty();
		}

		@Description(value = "Comments or notes about the party", target = DocTarget.METHOD)
		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		@Description(value = "Incorporation date if an organization, or date of birth if an individual", target = DocTarget.METHOD)
		public Date getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		@XmlTransient
		public Integer getRank() {
			return rank;
		}

		public void setRank(Integer rank) {
			this.rank = rank;
		}

		@XmlTransient
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean noPassword() {
			return password == null || password.isEmpty();
		}

		public boolean hasPassword() {
			return !noPassword();
		}

		@XmlTransient
		public ArrayList<String> getRoles() {
			return roles;
		}

		public void setRoles(ArrayList<String> roles) {
			this.roles = roles;
		}

		public boolean noRoles() {
			return roles == null || roles.isEmpty();
		}
		
		@Description(value = "List of party types", target = DocTarget.METHOD)
		public ArrayList<String> getTypes() {
			return types;
		}

		public String getType() {
			return types == null || types.isEmpty() ? null : types.get(0);
		}

		public void setType(String type) {
			if (types == null) {types = new ArrayList<String>();}
			types.add(type);
		}

		public boolean notType(String type) {
			return types == null || types.isEmpty() || type == null || !types.contains(type);
		}

		public void setTypes(ArrayList<String> types) {
			this.types = types;
		}

		public boolean noTypes() {
			return types == null || types.isEmpty();
		}
		
		public boolean hasTypes() {
			return !noTypes();
		}

		public boolean isSkipLicense() {
			return skipLicense;
		}

		public void setSkipLicense(boolean skipLicense) {
			this.skipLicense = skipLicense;
		}
		
		//---------------------------------------------------------------------------------
		// Public text shared among all organizations in several languages
		//---------------------------------------------------------------------------------
		public static final String getPublicId(String id) {
			return NameId.Type.Party.name() + id + Text.Code.Public.name();
		}
		
		public static Text getPublicText(String id, String language) {
			return new Text(NameId.Type.Party.name() + id + Text.Code.Public.name(), null, Text.Type.HTML, new Date(), null, language);
		}
		
		@Description(value = "Descriptive text about the party", target = DocTarget.METHOD)
		public Text getPublicText() {
			return getText(NameId.Type.Party, id, Text.Code.Public);
		}
		
		public void setPublicText(Text value) {
			setText(NameId.Type.Party, id, Text.Code.Public, value);
		}

		//---------------------------------------------------------------------------------
		// Public text shared among all organizations in several languages
		//---------------------------------------------------------------------------------
		public static final String getUrlId(String id) {
			return NameId.Type.Party.name() + id + Text.Code.Url.name();
		}
		
		public static Text getUrlText(String id, String language) {
			return new Text(NameId.Type.Party.name() + id + Text.Code.Url.name(), null, Text.Type.HTML, new Date(), null, language);
		}
		
		@Description(value = "Code or URL specific to the party", target = DocTarget.METHOD)
		public Text getUrlText() {
			return getText(NameId.Type.Party, id, Text.Code.Url);
		}
		
		public void setUrlText(Text value) {
			setText(NameId.Type.Party, id, Text.Code.Url, value);
		}

		//---------------------------------------------------------------------------------
		// Text is specific to an organization
		//---------------------------------------------------------------------------------
		public static Text getPrivateText(String organizationid, String id, String language) {
			return new Text(organizationid + NameId.Type.Party.name() + id, language);
		}
		
		public Text getPrivateText(String organizationid){
			return getText(organizationid, NameId.Type.Party, id);
		}

		public void setPrivateText(String organizationid, Text value){
			setText(organizationid, NameId.Type.Party, id, value);
		}

		//---------------------------------------------------------------------------------
		// Contract text in HTML format are shared among all organizations in several languages
		//---------------------------------------------------------------------------------
		public final String getContractId(){
			return NameId.Type.Party.name() + id + Text.Code.Contract.name();
		}

		public static final String getContractId(String id){
			return NameId.Type.Party.name() + id + Text.Code.Contract.name();
		}

		public static Text getContractText(String id, String language){
			return new Text(NameId.Type.Party.name() + id + Text.Code.Contract.name(), null, Text.Type.HTML, new Date(), null, language);
		}

		@Description(value = "Standard terms and conditions to do business with the party", target = DocTarget.METHOD)
		public Text getContractText(){
			return getText(NameId.Type.Party, id, Text.Code.Contract);
		}

		public void setContractText(Text value){
			setText(NameId.Type.Party, id, Text.Code.Contract, value);
		}

		@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getUsertype() {
			return usertype;
		}

		public void setUsertype(String usertype) {
			this.usertype = usertype;
		}
		
		//---------------------------------------------------------------------------------
		// Implements HasXsl interface
		//---------------------------------------------------------------------------------
		public String getXsl() {
			return xsl;
		}

		public void setXsl(String xsl) {
			this.xsl = xsl;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Party [employerid=");
			builder.append(employerid);
			builder.append(", \ncreatorid=");
			builder.append(creatorid);
			builder.append(", \nfinanceid=");
			builder.append(financeid);
			builder.append(", \njurisdictionid=");
			builder.append(jurisdictionid);
			builder.append(", \noldstate=");
			builder.append(oldstate);
			builder.append(", \nextraname=");
			builder.append(extraname);
			builder.append(", \nidentitynumber=");
			builder.append(identitynumber);
			builder.append(", \ntaxnumber=");
			builder.append(taxnumber);
			builder.append(", \nemailaddress=");
			builder.append(emailaddress);
			builder.append(", \nwebaddress=");
			builder.append(webaddress);
			builder.append(", \ndayphone=");
			builder.append(dayphone);
			builder.append(", \nnightphone=");
			builder.append(nightphone);
			builder.append(", \nfaxphone=");
			builder.append(faxphone);
			builder.append(", \nmobilephone=");
			builder.append(mobilephone);
			builder.append(", \nnotes=");
			builder.append(notes);
			builder.append(", \ncountry=");
			builder.append(country);
			builder.append(", \nlanguage=");
			builder.append(language);
			builder.append(", \nformatdate=");
			builder.append(formatdate);
			builder.append(", \nformatphone=");
			builder.append(formatphone);
			builder.append(", \nbirthdate=");
			builder.append(birthdate);
			builder.append(", \nrank=");
			builder.append(rank);
			builder.append(", \npassword=");
			builder.append(password);
			builder.append(", \nmanager=");
			builder.append(manager);
			builder.append(", \nagent=");
			builder.append(agent);
			builder.append(", \nroles=");
			builder.append(roles);
			builder.append(", \ntypes=");
			builder.append(types);
			builder.append(", \nentitytype=");
			builder.append(entitytype);
			builder.append(", \nlocationid=");
			builder.append(locationid);
			builder.append(", \ncurrency=");
			builder.append(currency);
			builder.append(", \nunit=");
			builder.append(unit);
			builder.append(", \nlatitude=");
			builder.append(latitude);
			builder.append(", \nlongitude=");
			builder.append(longitude);
			builder.append(", \naltitude=");
			builder.append(altitude);
			builder.append(", \norganizationid=");
			builder.append(organizationid);
			builder.append(", \nstatus=");
			builder.append(status);
			builder.append(", \nstate=");
			builder.append(state);
			builder.append(", \nattributes=");
			builder.append(attributemap);
			builder.append(", \nimages=");
			builder.append(imageurls);
			builder.append(", \nname=");
			builder.append(name);
			builder.append(", \nusertype=");
			builder.append(usertype);
			builder.append(", \nid=");
			builder.append(id);
			builder.append("]");
			return builder.toString();
		}



}