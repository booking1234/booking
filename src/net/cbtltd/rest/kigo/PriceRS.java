package net.cbtltd.rest.kigo;

public class PriceRS extends Response {
	private String CURRENCY;
	private Double RENT_BASE_AMOUNT;
	private Double RENT_FEES_AMOUNT;
	private Double RENT_DISCOUNT_AMOUNT;
	private Double RENT_AMOUNT;
	private Double RENT_PMT_AMOUNT;
	private String RENT_PMT_DUE_DATE;
	private Double RENT_DOWN_PMT_AMOUNT;
	private String RENT_DOWN_PMT_DUE_DATE;
	private Double RENT_REM_PMT_AMOUNT;
	private String RENT_REM_PMT_DUE_DATE;
	private Double FEES_AMOUNT;
	private String FEES_DUE_DATE;
	private Double TOTAL_AMOUNT;
	private Double DEPOSIT_AMOUNT;
	private String DEPOSIT_DUE_DATE;

	public PriceRS() {
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public Double getRENT_LIST_AMOUNT() {
		return TOTAL_AMOUNT + RENT_DISCOUNT_AMOUNT;
	}

	public Double getRENT_BASE_AMOUNT() {
		return RENT_BASE_AMOUNT;
	}

	public Double getRENT_FEES_AMOUNT() {
		return RENT_FEES_AMOUNT;
	}

	public Double getRENT_DISCOUNT_AMOUNT() {
		return RENT_DISCOUNT_AMOUNT;
	}

	public Double getRENT_AMOUNT() {
		return RENT_AMOUNT;
	}

	public Double getRENT_PMT_AMOUNT() {
		return RENT_PMT_AMOUNT;
	}

	public String getRENT_PMT_DUE_DATE() {
		return RENT_PMT_DUE_DATE;
	}

	public Double getRENT_DOWN_PMT_AMOUNT() {
		return RENT_DOWN_PMT_AMOUNT;
	}

	public String getRENT_DOWN_PMT_DUE_DATE() {
		return RENT_DOWN_PMT_DUE_DATE;
	}

	public Double getRENT_REM_PMT_AMOUNT() {
		return RENT_REM_PMT_AMOUNT;
	}

	public String getRENT_REM_PMT_DUE_DATE() {
		return RENT_REM_PMT_DUE_DATE;
	}

	public Double getFEES_AMOUNT() {
		return FEES_AMOUNT;
	}

	public String getFEES_DUE_DATE() {
		return FEES_DUE_DATE;
	}

	public Double getTOTAL_AMOUNT() {
		return TOTAL_AMOUNT;
	}

	public Double getDEPOSIT_AMOUNT() {
		return DEPOSIT_AMOUNT;
	}

	public String getDEPOSIT_DUE_DATE() {
		return DEPOSIT_DUE_DATE;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Price [CURRENCY=");
		builder.append(CURRENCY);
		builder.append(", RENT_BASE_AMOUNT=");
		builder.append(RENT_BASE_AMOUNT);
		builder.append(", RENT_FEES_AMOUNT=");
		builder.append(RENT_FEES_AMOUNT);
		builder.append(", RENT_DISCOUNT_AMOUNT=");
		builder.append(RENT_DISCOUNT_AMOUNT);
		builder.append(", RENT_AMOUNT=");
		builder.append(RENT_AMOUNT);
		builder.append(", RENT_PMT_AMOUNT=");
		builder.append(RENT_PMT_AMOUNT);
		builder.append(", RENT_PMT_DUE_DATE=");
		builder.append(RENT_PMT_DUE_DATE);
		builder.append(", RENT_DOWN_PMT_AMOUNT=");
		builder.append(RENT_DOWN_PMT_AMOUNT);
		builder.append(", RENT_DOWN_PMT_DUE_DATE=");
		builder.append(RENT_DOWN_PMT_DUE_DATE);
		builder.append(", RENT_REM_PMT_AMOUNT=");
		builder.append(RENT_REM_PMT_AMOUNT);
		builder.append(", RENT_REM_PMT_DUE_DATE=");
		builder.append(RENT_REM_PMT_DUE_DATE);
		builder.append(", FEES_AMOUNT=");
		builder.append(FEES_AMOUNT);
		builder.append(", FEES_DUE_DATE=");
		builder.append(FEES_DUE_DATE);
		builder.append(", TOTAL_AMOUNT=");
		builder.append(TOTAL_AMOUNT);
		builder.append(", DEPOSIT_AMOUNT=");
		builder.append(DEPOSIT_AMOUNT);
		builder.append(", DEPOSIT_DUE_DATE=");
		builder.append(DEPOSIT_DUE_DATE);
		builder.append("]");
		return builder.toString();
	}
}
