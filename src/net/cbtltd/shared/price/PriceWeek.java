package net.cbtltd.shared.price;

import java.util.Date;

import net.cbtltd.shared.Price;

public class PriceWeek extends Price {
	private Date fromDate;
	private Date toDate;
	private String entityId;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceWeek [fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", entityId=");
		builder.append(entityId);
		builder.append(", altid=");
		builder.append(altid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", partyid=");
		builder.append(partyid);
		builder.append(", partyname=");
		builder.append(partyname);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", payer=");
		builder.append(payer);
		builder.append(", rule=");
		builder.append(rule);
		builder.append(", dateStr=");
		builder.append(dateStr);
		builder.append(", date=");
		builder.append(date);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", value=");
		builder.append(value);
		builder.append(", minimum=");
		builder.append(minimum);
		builder.append(", factor=");
		builder.append(factor);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", available=");
		builder.append(available);
		builder.append(", minStay=");
		builder.append(minStay);
		builder.append(", maxStay=");
		builder.append(maxStay);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", version=");
		builder.append(version);
		builder.append(", idFrom=");
		builder.append(idFrom);
		builder.append(", priceExt=");
		builder.append(priceExt);
		builder.append(", idTo=");
		builder.append(idTo);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", altpartyid=");
		builder.append(altpartyid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", options=");
		builder.append(options);
		builder.append(", status=");
		builder.append(status);
		builder.append(", values=");
		builder.append(values);
		builder.append(", keyvalues=");
		builder.append(keyvalues);
		builder.append(", attributemap=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", imageurls=");
		builder.append(imageurls);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}


	
	
}
