package net.cbtltd.rest.reservation;

public class ReservationTestCaller {
	public static void main(String[] args) throws Exception {
		// TODO : getting configuration from DB
		ReservationBuilderConfiguration configuration = ReservationBuilderConfiguration.getConfigurationForPm(null);
		ReservationBuilder builder = ReservationBuilder.getInstance(configuration);
//		builder.buildResponse();
	}
}
