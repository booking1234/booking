package net.cbtltd.server.integration.channel;

import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.response.CalendarResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.config.RazorConfig;

public class ChannelIntegrationUtils {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	public static CalendarResponse getAvailabilityCalendar(String productId) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dateTime = new DateTime();

		return ReservationRest.getCalendar(RazorConfig.getValue("pos.code"),
				productId, dateTimeFormatter.print(dateTime));
	}
}
