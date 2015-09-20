package com.mybookingpal.marshaller.junit;

import static org.junit.Assert.*;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;

import net.cbtltd.rest.Images;
import net.cbtltd.rest.Property;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.junit.Before;
import org.junit.Test;

public class JSONMarshallerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testJettison() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Property.class);
		 
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        Customer customer = (Customer) unmarshaller.unmarshal(new File("src/blog/json/ns/input.xml"));
		
		Property property = new Property();
		
		List<String> imageList = new ArrayList<String>();
		imageList.add("http://www.mybookingpal.com/a");
		imageList.add("http://www.mybookingpal.com/b");
		Images images = new Images(imageList);
		property.setImages(images);
		
		
 
        Configuration config = new Configuration();
//        config.
        Map<String, String> xmlToJsonNamespaces = new HashMap<String,String>(1);
        xmlToJsonNamespaces.put("http://www.mybookingpal.org/package", "");
        xmlToJsonNamespaces.put("http://www.mybookingpal.org/property", "prop");
        config.setXmlToJsonNamespaces(xmlToJsonNamespaces);
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);
        Writer writer = new OutputStreamWriter(System.out);
        XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
 
        Marshaller marshaller;
		try {
			marshaller = jc.createMarshaller();
			marshaller.marshal(property, xmlStreamWriter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	@Test
	public void testJacskon() throws JAXBException {
				 
		Property property = new Property();
		
		List<String> imageList = new ArrayList<String>();
		imageList.add("http://www.mybookingpal.com/a");
//		imageList.add("http://www.mybookingpal.com/b");
		Images images = new Images(imageList);
		property.setImages(images);
		
		
		ObjectMapper mapper = new ObjectMapper();
		  AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		  // make deserializer use JAXB annotations (only)
		  mapper.getDeserializationConfig().setAnnotationIntrospector(introspector);
		  // make serializer use JAXB annotations (only)
		  SerializationConfig serializationConfig = mapper.getSerializationConfig();
		  serializationConfig.setAnnotationIntrospector(introspector);
//		  serializationConfig.set(Feature.INDENT_OUTPUT, true);
		  serializationConfig.set(Feature.WRITE_NULL_PROPERTIES, false);
//		  serializationConfig.set(Feature.AUTO_DETECT_FIELDS, false);
		  serializationConfig.set(Feature.WRAP_ROOT_VALUE, true);
		  StringWriter stringWriter = new StringWriter();
		  try {
		   mapper.writeValue(stringWriter, property);
		  } catch (Exception e) {
		   throw new RuntimeException(e);
		  }
		  System.out.println(stringWriter);
        
	}

}
