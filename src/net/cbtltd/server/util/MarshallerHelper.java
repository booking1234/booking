package net.cbtltd.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallerHelper {
	public static <T> String toXML(T t) throws IOException, JAXBException {
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
		 
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			m.marshal(t, outputStream);
			String xml = outputStream.toString();
			return xml;
		} finally {
			if(outputStream != null) {
				outputStream.close();
			}
		}
	}
	
	public static <T> T fromXml(Class<T> clazz, String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        Object obj = um.unmarshal(new StringReader(xml));
        return clazz.cast(obj);
	}
}
