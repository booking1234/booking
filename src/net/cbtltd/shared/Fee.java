package net.cbtltd.shared;

import java.util.Date;

public class Fee {

	public enum EntityTypeEnum {
		MANDATORY(1, "MANDATORY"), OPTIONAL(2, "OPTIONAL"), PETFEEMANDATORY(3, "PET_FEE_MANDATORY"), PET_FEE_OPTIONAL(4, "PET_FEE_OPTIONAL"),
		EXTRA(5, "EXTRA"), MANDATORY_PAL(6, "MANDATORY_PAL"), MANDATORY_PAL_DEPOSIT(7, "MANDATORY_PAL_DEPOSIT"), MANDATORY_DEPOSTI(8, "MANDATORY_DEPOSIT");

		private Integer value;
		private String name;

		EntityTypeEnum(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return this.value;
		}
		
		public String getName() {
			return this.name;
		}

		public static EntityTypeEnum getByInt(Integer value) {
			switch (value) {
			case 1:
				return MANDATORY;
			case 2:
				return OPTIONAL;
			default:
				return null;
			}
		}
	}
	
//	public enum StateEnum {
//		INITIAL(0), CREATED(1), FINAL(2);
//
//		private Integer value;
//
//		StateEnum(Integer value) {
//			this.value = value;
//		}
//
//		public Integer value() {
//			return this.value;
//		}
//
//		public static StateEnum getByInt(Integer value) {
//			switch (value) {
//			case 0:
//				return INITIAL;
//			case 1:
//				return CREATED;
//			case 2:
//				return FINAL;
//			default:
//				return null;
//			}
//		}
//	}

//	public enum TaxTypeEnum {
//		TAXABLE(0), NOT_TAXABLE(1);
//		
//		private Integer value;
//
//		TaxTypeEnum(Integer value) {
//			this.value = value;
//		}
//
//		public Integer value() {
//			return this.value;
//		}
//
//		public static TaxTypeEnum getByInt(Integer value) {
//			switch (value) {
//			case 0:
//				return TAXABLE;
//			case 1:
//				return NOT_TAXABLE;
//			default:
//				return null;
//			}
//		}
//	}

	public enum FeeUnitEnum {
		NOT_APPLICABLE(1, "NOT_APPLICABLE"), PER_DAY(2, "PER_DAY"), PER_PERSON(3, "PER_PERSON"), PER_DAY_PER_PERSON(4, "PER_DAY_PER_PERSON"); 
		
		private Integer value;
		private String name;

		FeeUnitEnum(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer value() {
			return this.value;
		}
		
		public String getName() {
			return this.name;
		}

		public static FeeUnitEnum getByInt(Integer value) {
			switch (value) {
			case 1:
				return NOT_APPLICABLE;
			case 2:
				return PER_DAY;
			case 3:
				return PER_PERSON;
			case 4:
				return PER_DAY_PER_PERSON;
			default:
				return null;
			}
		}
	}
	
//	public enum ValueTypeEnum {
//		FLAT(0), PERCENT(1);
//		
//		private Integer value;
//
//		ValueTypeEnum(Integer value) {
//			this.value = value;
//		}
//
//		public Integer value() {
//			return this.value;
//		}
//
//		public static ValueTypeEnum getByInt(Integer value) {
//			switch (value) {
//			case 0:
//				return FLAT;
//			case 1:
//				return PERCENT;
//			default:
//				return null;
//			}
//		}
//	}
	

	//entity types
	public final static int MANDATORY = 1;
	public final static int OPTIONAL = 2;
	public final static int PET_FEE_MANDATORY = 3;
	public final static int PET_FEE_OPTIONAL = 4;
	public final static int EXTRA = 5; //for fees which are mandatory, but should be paid when arriving, and not to us.
	public final static int MANDATORY_PAL= 6;
	public final static int MANDATORY_PAL_DEPOSIT= 7;
	public final static int MANDATORY_DEPOSTI= 8;
	
	
	// states
	public final static int INITIAL = 1;
	public final static int CREATED = 2;
	public final static int FINAL = 3;
	
	//tax type
	public final static int TAXABLE = 1;
	public final static int NOT_TAXABLE = 2;
	
	//unit
	public final static int NOT_APPLICABLE = 1;
	public final static int PER_DAY = 2;
	public final static int PER_PERSON = 3;
	public final static int PER_DAY_PER_PERSON = 4;

	//value types
	public final static int FLAT = 1;
	public final static int PERCENT = 2;
	

	private int id;
	private String altId;
	private Integer entityType;
	private String productId;
	private String partyId;
	private String name;
	private Integer state;
	private String optionValue;
	private Integer taxType;
	private Date fromDate;
	private Date toDate;
	private Integer unit;
	private double value;
	private Integer valueType;
	private Integer weight=0;
	private String currency;
	private Date version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAltId() {
		return altId;
	}

	public void setAltId(String altId) {
		this.altId = altId;
	}

	public int getEntityType() {
		return entityType;
	}
	
	public boolean isTypeMandatory(){
		return this.getEntityType()==Fee.MANDATORY;
	}
	
	public boolean isTypeOptional(){
		return this.getEntityType()==Fee.OPTIONAL;
	}
	
	public boolean isTypePetFeeMandatory(){
		return this.getEntityType()==Fee.PET_FEE_MANDATORY;
	}
	
	public boolean isTypePetFeeOptional(){
		return this.getEntityType()==Fee.PET_FEE_OPTIONAL;
	}
	
	public boolean isTypeExtra(){
		return this.getEntityType()==Fee.EXTRA;
	}
	

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}
	
	public boolean isStateInitial(){
		return this.getState()==Fee.INITIAL;
	}
	
	public boolean isStateCreated(){
		return this.getState()==Fee.CREATED;
	}
	
	public boolean isStateFinal(){
		return this.getState()==Fee.FINAL;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public Integer getTaxType() {
		return taxType;
	}
	
	public boolean isTaxTypeTaxable(){
		return this.getTaxType()==Fee.TAXABLE;
	}
	
	public boolean isTaxTypeNotTaxable(){
		return this.getTaxType()==Fee.NOT_TAXABLE;
	}

	public void setTaxType(Integer taxType) {
		this.taxType = taxType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getUnit() {
		return unit;
	}
	
	public boolean isUnitNotApplicable(){
		return this.getUnit()==Fee.NOT_APPLICABLE;
	}
	
	public boolean isUnitPerDay(){
		return this.getUnit()==Fee.PER_DAY;
	}
	
	public boolean isUnitPerPerson(){
		return this.getUnit()==Fee.PER_PERSON;
	}
	
	public boolean isUnitPerDayPerPerson(){
		return this.getUnit()==Fee.PER_DAY_PER_PERSON;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Integer getValueType() {
		return valueType;
	}
	
	public boolean isValueTypeFlat(){
		return this.getValueType()==Fee.FLAT;
	}
	
	public boolean isValueTypePercent(){
		return this.getValueType()==Fee.PERCENT;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
		builder.append("Fee [id=");
		builder.append(id);
		builder.append(", altId=");
		builder.append(altId);
		builder.append(", entityType=");
		builder.append(entityType);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", partyId=");
		builder.append(partyId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", optionValue=");
		builder.append(optionValue);
		builder.append(", taxType=");
		builder.append(taxType);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", value=");
		builder.append(value);
		builder.append(", valueType=");
		builder.append(valueType);
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altId == null) ? 0 : altId.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((entityType == null) ? 0 : entityType.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((optionValue == null) ? 0 : optionValue.hashCode());
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((taxType == null) ? 0 : taxType.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Fee))
			return false;
		Fee other = (Fee) obj;
		if (altId == null) {
			if (other.altId != null)
				return false;
		} else if (!altId.equals(other.altId))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entityType == null) {
			if (other.entityType != null)
				return false;
		} else if (!entityType.equals(other.entityType))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (optionValue == null) {
			if (other.optionValue != null)
				return false;
		} else if (!optionValue.equals(other.optionValue))
			return false;
		if (partyId == null) {
			if (other.partyId != null)
				return false;
		} else if (!partyId.equals(other.partyId))
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
		if (taxType == null) {
			if (other.taxType != null)
				return false;
		} else if (!taxType.equals(other.taxType))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		if (valueType == null) {
			if (other.valueType != null)
				return false;
		} else if (!valueType.equals(other.valueType))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	public boolean equalsWithoutDates(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Fee))
			return false;
		Fee other = (Fee) obj;
		if (altId == null) {
			if (other.altId != null)
				return false;
		} else if (!altId.equals(other.altId))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entityType == null) {
			if (other.entityType != null)
				return false;
		} else if (!entityType.equals(other.entityType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (optionValue == null) {
			if (other.optionValue != null)
				return false;
		} else if (!optionValue.equals(other.optionValue))
			return false;
		if (partyId == null) {
			if (other.partyId != null)
				return false;
		} else if (!partyId.equals(other.partyId))
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
		if (taxType == null) {
			if (other.taxType != null)
				return false;
		} else if (!taxType.equals(other.taxType))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		if (valueType == null) {
			if (other.valueType != null)
				return false;
		} else if (!valueType.equals(other.valueType))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	
}