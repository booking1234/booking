package net.cbtltd.rest.atraveo.api;

public enum AtraveoError {
	ERR01("01","Property already occupied"),
	ERR02("02","Reservation blocked"),
	ERR03("03","Option not possible"),
	ERR04("04","Booking only possible by tour operator"),
	ERR05("05","Invalid choice of bed"),
	ERR06("06","Property is booked on request"),
	ERR07("07","Booking not possible"),
	ERR08("08","Cancellation not possible"),
	ERR09("09","Reservation system is not replying"),
	ERR10("10","Invalid request code"),
	ERR12("12","Invalid property id"),
	ERR13("13","Remark too long"),
	ERR14("14","Extended remark too long"),
	ERR15("15","No agency number delivered"),
	ERR16("16","Invalid agency number"),
	ERR18("18","No contract for this property"),
	ERR19("19","Rental conditions not met for this property"),
	ERR21("21","Invalid arrival date"),
	ERR22("22","Invalid travel duration"),
	ERR23("23","Already canceled"),
	ERR24("24","Invalid departure date"),
	ERR25("25","Internal error (error in modify)"),
	ERR26("26","Property is on request"),
	ERR27("27","Unvalid travel date"),
	ERR28("28","Arrival not possible"),
	ERR29("29","Arrival date too short term"),
	ERR30("30","Pets not allowed"),
	ERR31("31","Too many pets"),
	ERR32("32","Departure day not possible"),
	ERR33("33","Just full weeks bookable"),
	ERR34("34","Property or its' season calendar has not been released yet."),
	ERR35("35","Booking period is not valid with customer booking conditions"),
	ERR36("36","Unable to calculate rental price for property"),
	ERR37("37","The details in your query are incomplete"),
	ERR38("38","The price check returned an error."),
	ERR39("39","The username/ password you used is incorrect."),
	ERR40("40","Please book manually in MERLIN"),
	ERR50("50","Bank code incorrect or missing"),
	ERR51("51","Payment data incomplete"),
	ERR52("52","Method of payment incorrect or not accepted"),
	ERR53("53","Invalid booking number"),
	ERR54("54","Credit card expiry date exceeded"),
	ERR55("55","Credit card number incorrect"),
	ERR56("56","Payment by credit card is not possible"),
	ERR57("57","Credit card data incomplete"),
	ERR58("58","Kredit card not accepted"),
	ERR59("59","Account data incomplete"),
	ERR60("60","House-type incorrect resp. not available"),
	ERR61("61","Email adress incorrect"),
	ERR62("62","Number of persons incorrect"),
	ERR63("63","Email adress too long"),
	ERR64("64","Adress incomplete or incorrect"),
	ERR65("65","For interface reservation max. 9 persons possible"),
	ERR66("66","Telephone number not existing"),
	ERR67("67","Client is not of full age"),
	ERR68("68","The date of birth of the booker has not been concluded");
	String code;
	String description;
	private AtraveoError(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public String getCode(){
		return code;
	}
}
