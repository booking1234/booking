package net.cbtltd.rest.util;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.cbtltd.json.JSONService;

public class XmlDateAdapter extends XmlAdapter<String, Date> {

	    @Override
	    public String marshal(Date value) throws Exception {
	        return JSONService.DF.format(value);
	    }

	    @Override
	    public Date unmarshal(String value) throws Exception {
	        return JSONService.DF.parse(value);
	    }

}
