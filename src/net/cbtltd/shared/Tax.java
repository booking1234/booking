/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.Fee.EntityTypeEnum;
import net.cbtltd.shared.api.HasServiceResponse;
import net.cbtltd.shared.api.HasState;

public class Tax
//extends Model {
extends NameId
implements HasState, HasServiceResponse {
	
	public static final String INITIAL = "Initial";
	public static final String CREATED = "Created";

	public static enum Type {
		IncomeTax(0), PayrollTax(1), 
		PurchaseTax(2), SalesTaxIncluded(3), 
		SalesTaxExcluded(4), SalesTaxOnTax(5);
		
		private Integer value;
		
		Type(Integer value) {
			this.value = value;
		}
		
		public Integer getValue() {
			return this.value;
		}
		
		public static Type getByName(String name) {
			
			if (name.equals(TYPES[0]))
				return IncomeTax;
			if (name.equals(TYPES[1]))
				return PayrollTax;
			if (name.equals(TYPES[2]))
				return PurchaseTax;
			if (name.equals(TYPES[3]))
				return SalesTaxIncluded;
			if (name.equals(TYPES[4]))
				return SalesTaxExcluded;
			if (name.equals(TYPES[5]))
				return SalesTaxOnTax;
			
			return null;
		}
		
		public static Type getByInt(Integer intValue) {
			switch (intValue) {
			case 0:
				return IncomeTax;
			case 1:
				return PayrollTax;
			case 2:
				return PurchaseTax;
			case 3:
				return SalesTaxIncluded;
			case 4:
				return SalesTaxExcluded;
			case 5:
				return SalesTaxOnTax;
			default:
				return null;
			}
		}
	};
	
	
	public static final String[] TYPES = {
		Tax.Type.IncomeTax.name(),  
		Tax.Type.PayrollTax.name(), 
		Tax.Type.PurchaseTax.name(),  
		Tax.Type.SalesTaxIncluded.name(),  
		Tax.Type.SalesTaxExcluded.name(),
		Tax.Type.SalesTaxOnTax.name()
	};
	
	public static enum MandatoryType {MandatoryTax, OptionalTax};
	public static final String[] MANDATORY_TYPES = {
		Tax.MandatoryType.MandatoryTax.name(),  
		Tax.MandatoryType.OptionalTax.name()  
	};	

	public static final String VAT_NORMAL = "VAT Normal";
	public static final String VAT_EXEMPT = "VAT Exempt";
	public static final String VAT_ZERO = "VAT Zero";
	public static final String[] VAT_TYPES = {VAT_NORMAL, VAT_EXEMPT, VAT_ZERO};

	public static final String TYPE = "tax.type";
	public static final String DATE = "tax.date";
	public static final String THRESHOLD = "tax.threshold";
	public static final String AMOUNT = "tax.amount";

	private String accountid;
	private String partyid;
	private String productId;
	private String partyname;
	private String entitytype;
	private String entityid;
	private String state;
	private String type;
	private String notes;
	private Date date;
	private Integer threshold;
	private Double amount;
	private String mandatoryType=Tax.MandatoryType.MandatoryTax.name();
	private String altId;
	private String optionValue;
	private int status = 0;
	private Date version; //latest change

	public Service service() {return Service.TAX;}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean hasEntitytype(String entitytype) {
		return this.entitytype != null && entitytype != null && this.entitytype.equalsIgnoreCase(entitytype);
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

    public final String getState() {
        return state;
    }

    public final void setState(String state) {
        this.state = state;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasType(Type type) {
		return this.type != null &&  type != null && this.type.equalsIgnoreCase(type.name());
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	//TODO: implement for PAYE etc
	public Double getTaxExcluded(Double value) {
		return round(value * amount / 100.0, 2);
	}

	public Double getTaxIncluded(Double value) {
		return round(value * amount / (100.0 + amount));
	}

	public String getMandatoryType() {
		return mandatoryType;
	}

	public void setMandatoryType(String mandatoryType) {
		this.mandatoryType = mandatoryType;
	}
	
	public boolean isMandatoryTax(){
		return this.mandatoryType!=null && this.mandatoryType.equalsIgnoreCase(Tax.MandatoryType.MandatoryTax.name());
	}
	
	public boolean isOptionalTax(){
		return this.mandatoryType!=null && this.mandatoryType.equalsIgnoreCase(Tax.MandatoryType.OptionalTax.name());
	}
	
	public boolean hasMandatoryType(MandatoryType mandatoryType) {
		return this.mandatoryType != null &&  mandatoryType != null && this.mandatoryType.equalsIgnoreCase(mandatoryType.name());
	}

	public String getAltId() {
		return altId;
	}

	public void setAltId(String altId) {
		this.altId = altId;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public final int getStatus() {
		return status;
	}

	public final void setStatus(int status) {
		this.status = status;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tax [accountid=");
		builder.append(accountid);
		builder.append(", partyid=");
		builder.append(partyid);
		builder.append(", productid=");
		builder.append(productId);
		builder.append(", partyname=");
		builder.append(partyname);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", date=");
		builder.append(date);
		builder.append(", threshold=");
		builder.append(threshold);
		builder.append(", amount=");
		builder.append(amount);
//		builder.append(", organizationid=");
//		builder.append(organizationid);
//		builder.append(", status=");
//		builder.append(status);
//		builder.append(", state=");
//		builder.append(state);
//		builder.append(", values=");
//		builder.append(values);
//		builder.append(", attributes=");
//		builder.append(attributemap);
//		builder.append(", texts=");
//		builder.append(texts);
//		builder.append(", images=");
//		builder.append(imageurls);
		builder.append(", name=");
		builder.append(name);
		builder.append(", altId=");
		builder.append(altId);
		builder.append(", mandatoryType=");
		builder.append(mandatoryType);
		builder.append(", optionValue=");
		builder.append(optionValue);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountid == null) ? 0 : accountid.hashCode());
		result = prime * result + ((altId == null) ? 0 : altId.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((entityid == null) ? 0 : entityid.hashCode());
		result = prime * result + ((mandatoryType == null) ? 0 : mandatoryType.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((optionValue == null) ? 0 : optionValue.hashCode());
		result = prime * result + ((partyid == null) ? 0 : partyid.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((threshold == null) ? 0 : threshold.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tax other = (Tax) obj;
		if (accountid == null) {
			if (other.accountid != null)
				return false;
		} else if (!accountid.equals(other.accountid))
			return false;
		if (altId == null) {
			if (other.altId != null)
				return false;
		} else if (!altId.equals(other.altId))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (mandatoryType == null) {
			if (other.mandatoryType != null)
				return false;
		} else if (!mandatoryType.equals(other.mandatoryType))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (optionValue == null) {
			if (other.optionValue != null)
				return false;
		} else if (!optionValue.equals(other.optionValue))
			return false;
		if (partyid == null) {
			if (other.partyid != null)
				return false;
		} else if (!partyid.equals(other.partyid))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (threshold == null) {
			if (other.threshold != null)
				return false;
		} else if (!threshold.equals(other.threshold))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		
		return true;
	}


	
	
}