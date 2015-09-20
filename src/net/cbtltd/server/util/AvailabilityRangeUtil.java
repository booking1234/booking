package net.cbtltd.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.response.AvailabilityRange;
import net.cbtltd.rest.response.CalendarElement;

import com.ibm.icu.util.Calendar;

public class AvailabilityRangeUtil {

	public static List<AvailabilityRange> getAvailabilityList(ArrayList<CalendarElement> elements,
			Date fromDate, Date toDate) throws ParseException {
		boolean[] availabilities = reverseCalendarElements(fromDate, toDate, elements);
		Map<Date, Boolean> availabilityMap = fillAvailabilityMap(availabilities, fromDate);
		List<AvailabilityRange> ranges = fillAvailabilityResponse(availabilityMap);
		return ranges;
	}
	
	private static List<AvailabilityRange> fillAvailabilityResponse(Map<Date, Boolean> availabilityMap) {
		List<AvailabilityRange> ranges = new ArrayList<AvailabilityRange>();
		boolean previousEntry = false;
		boolean firstEntry = true;
		Date start = null;
		Date previousDate = null;
		for(Map.Entry<Date, Boolean> entry : availabilityMap.entrySet()) {
			if(firstEntry) {
				if(!entry.getValue()) {
					start = entry.getKey();
				}
				firstEntry = false;
				previousEntry = entry.getValue();
				previousDate = entry.getKey();
				continue;
			}
			if(previousEntry != entry.getValue()) { // check if range changed
				if(!previousEntry) { // check if it is the end of available range
					AvailabilityRange range = new AvailabilityRange();
					range.setStartDate(JSONService.DF.format(start));
					range.setEndDate(JSONService.DF.format(previousDate));
					ranges.add(range);
				} else {
					start = entry.getKey();
				}
			}
			previousEntry = entry.getValue(); 
			previousDate = entry.getKey();
		}
		
		if(!previousEntry) {
			AvailabilityRange range = new AvailabilityRange();
			range.setStartDate(JSONService.DF.format(start));
			range.setEndDate(JSONService.DF.format(previousDate));
			ranges.add(range);
		}
		return ranges;
	}

	private static boolean[] reverseCalendarElements(Date fromDate,	Date toDate, ArrayList<CalendarElement> elements) throws ParseException {
		int delta = calculateDays(fromDate, toDate);
		boolean[] notAvailable = new boolean[delta];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int startDay = calendar.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < delta; i++) {
			if (i >= elements.size()) {
				break;
			}
			CalendarElement element = elements.get(i);
			Date elementDate = stringToDate(element.getDate());
			if(elementDate.after(toDate)) {
				continue;
			}
			Calendar elementCalendar = Calendar.getInstance();
			elementCalendar.setTime(elementDate);
			int index = (elementCalendar.get(Calendar.DAY_OF_YEAR) - startDay) + ((elementCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)) * 365);
			if(index < notAvailable.length) {
				notAvailable[index] = true;
			}
		}
		return notAvailable;
	}
	
	private static int calculateDays(Date from, Date to) {
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(from);
		Calendar toDate = Calendar.getInstance();
		toDate.setTime(to);
//		int yearDifference = toDate.get(Calendar.YEAR) - fromDate.get(Calendar.YEAR);
//		return (yearDifference * 365) + (Math.abs(toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR)));
		long fromMillis = from.getTime();
		long toMillis = to.getTime();
		long delta = toMillis - fromMillis;
		int days = (int) TimeUnit.MILLISECONDS.toDays(delta) + 1;
		return days;
	}

	private static Map<Date, Boolean> fillAvailabilityMap(boolean[] availabilities, Date fromDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		Map<Date, Boolean> availabilityMap = new TreeMap<Date, Boolean>();
		for(boolean availability : availabilities) {
			availabilityMap.put(calendar.getTime(), availability);
			calendar.add(Calendar.DATE, 1);
		}
		return availabilityMap;
	}
	
	private static Date stringToDate(String string) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(string);
	}
}
