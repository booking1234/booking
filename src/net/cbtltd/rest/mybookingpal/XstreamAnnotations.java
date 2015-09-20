package net.cbtltd.rest.mybookingpal;

import com.thoughtworks.xstream.XStream;

public class XstreamAnnotations {


	public static  XStream getAnnotationsForDetailInformation(){

		XStream 	xstream = new XStream();
		xstream.alias("property", net.cbtltd.rest.Property.class);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(net.cbtltd.rest.Property.class);
		xstream.processAnnotations(net.cbtltd.rest.Values.class);
		xstream.processAnnotations(net.cbtltd.rest.Attributes.class);
		xstream.processAnnotations(net.cbtltd.rest.PriceList.class);
		xstream.processAnnotations(net.cbtltd.rest.PriceLists.class);
		xstream.processAnnotations(net.cbtltd.rest.PriceTable.class);
		xstream.processAnnotations(net.cbtltd.rest.Images.class);
		xstream.processAnnotations(net.cbtltd.shared.Entity.class);
		xstream.processAnnotations(net.cbtltd.shared.Product.class);
		xstream.processAnnotations(net.cbtltd.rest.flipkey.Reservations.class);//might not need. 
		xstream.processAnnotations(net.cbtltd.shared.Model.class);
		xstream.processAnnotations(net.cbtltd.shared.PropertyManagerCancellationRule.class);

		return xstream;
	}

	public static  XStream getAnnotationsForAvailableSchedule(){

		XStream xstream = new XStream();
		
		xstream.processAnnotations(net.cbtltd.rest.response.AvailabilityResponse.class);
		xstream.processAnnotations(net.cbtltd.rest.error.ApiResponse.class);
		xstream.processAnnotations(net.cbtltd.rest.response.AvailabilityRange.class);

		return xstream; 	
	}



	public static  XStream getAnnotationsForPricePerWeeklyRate(){

		XStream xstream = new XStream();

		xstream.processAnnotations(net.cbtltd.shared.price.PriceRange.class);
		xstream.processAnnotations(net.cbtltd.rest.response.WeeklyPriceResponse.class);

		return xstream; 	
	}

}