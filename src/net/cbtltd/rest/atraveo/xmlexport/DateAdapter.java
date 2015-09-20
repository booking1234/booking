package net.cbtltd.rest.atraveo.xmlexport;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private static String pattern = "yyyy-MM-dd";

    /**
	 * @return the pattern
	 */
	public static final String getPattern() {
		return pattern;
	}

	public String marshal(Date date) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date unmarshal(String dateString) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateString);
    }   
}