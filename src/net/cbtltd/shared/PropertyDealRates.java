package net.cbtltd.shared;

public class PropertyDealRates extends ModelTable {
	private String rateId;
	private String propertyId;

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	@Override
	public Service service() {
		return Service.PRICE;
	}
}
