package net.cbtltd.server.rapa;

import net.cbtltd.server.rapa.formatter.FormatHandler;
import net.cbtltd.server.rapa.formatter.XMLHandler;

public class FormatHandlerFactory {

	public FormatHandler create(String format) {
		FormatHandler handler = null;
		if (format.equalsIgnoreCase("xml")) {
			handler = new XMLHandler();
		} 
//		else if (format.equalsIgnoreCase("json")) {
//			handler = new JSonHandler();
//		} 
		else {
			throw new RuntimeException("Unsupported Format " + format);
		}
		return handler;
	}

}
