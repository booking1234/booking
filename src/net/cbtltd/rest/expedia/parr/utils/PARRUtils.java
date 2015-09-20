package net.cbtltd.rest.expedia.parr.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.rest.expedia.parr.domain.ProductAvailRateRetrievalRQ;
import net.cbtltd.rest.expedia.utils.ExpediaUtils;

import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;

public class PARRUtils {
	private static final Logger LOG = Logger.getLogger(PARRUtils.class
			.getName());

	public void fetchProductDetails(String hotelId) throws Exception {
		ProductAvailRateRetrievalRQ productAvailRateRetrievalRQ = new ProductAvailRateRetrievalRQ();
		ProductAvailRateRetrievalRQ.Authentication authentication = new ProductAvailRateRetrievalRQ.Authentication();
		// set authentication details
		authentication.setUsername(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
		authentication.setPassword(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
		productAvailRateRetrievalRQ.setAuthentication(authentication);
		// set Hotel Details
		ProductAvailRateRetrievalRQ.Hotel hotel = new ProductAvailRateRetrievalRQ.Hotel();
		hotel.setId(hotelId);
		productAvailRateRetrievalRQ.setHotel(hotel);
		// set Product Retrival Details
		ProductAvailRateRetrievalRQ.ParamSet paramSet = new ProductAvailRateRetrievalRQ.ParamSet();
		ProductAvailRateRetrievalRQ.ParamSet.ProductRetrieval productRetrival = new ProductAvailRateRetrievalRQ.ParamSet.ProductRetrieval();
		productRetrival.setReturnCancelPolicy(true);
		productRetrival.setReturnRoomAttributes(true);
		productRetrival.setReturnRatePlanAttributes(true);
		productRetrival.setReturnCompensation(true);
		productRetrival.setReturnRateLink(true);

		paramSet.setProductRetrieval(productRetrival);
		productAvailRateRetrievalRQ.setParamSet(paramSet);
		LOG.info("fetchProductDetails Response is "
				+ this.callExpediaPARRAPI(productAvailRateRetrievalRQ));
	}

	public void fetchAvailRateDetails(String hotelId, String fromDate,
			String toDate) throws Exception {
		ProductAvailRateRetrievalRQ productAvailRateRetrievalRQ = new ProductAvailRateRetrievalRQ();
		ProductAvailRateRetrievalRQ.Authentication authentication = new ProductAvailRateRetrievalRQ.Authentication();
		// set authentication details
		authentication.setUsername(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_USERNAME, "testuser"));
		authentication.setPassword(RazorConfig.getValue(
				ExpediaUtils.EXPEDIA_PWD, "ECLPASS"));
		productAvailRateRetrievalRQ.setAuthentication(authentication);
		// set Hotel Details
		ProductAvailRateRetrievalRQ.Hotel hotel = new ProductAvailRateRetrievalRQ.Hotel();
		hotel.setId(hotelId);
		productAvailRateRetrievalRQ.setHotel(hotel);
		// set Product Retrival Details
		ProductAvailRateRetrievalRQ.ParamSet paramSet = new ProductAvailRateRetrievalRQ.ParamSet();
		ProductAvailRateRetrievalRQ.ParamSet.AvailRateRetrieval availRateRetrieval = new ProductAvailRateRetrievalRQ.ParamSet.AvailRateRetrieval();
		availRateRetrieval.setFrom(fromDate);
		availRateRetrieval.setTo(toDate);
		paramSet.setAvailRateRetrieval(availRateRetrieval);
		productAvailRateRetrievalRQ.setParamSet(paramSet);
		LOG.info("fetchAvailRateDetails Response is "
				+ this.callExpediaPARRAPI(productAvailRateRetrievalRQ));
	}

	private String callExpediaPARRAPI(
			ProductAvailRateRetrievalRQ productAvailRateRetrievalRQ)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(ProductAvailRateRetrievalRQ.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(productAvailRateRetrievalRQ, xmlWriter);
		LOG.info("Expedia PARR API Request " + xmlWriter);
		return ExpediaUtils
				.callExpediaAPI(
						xmlWriter.toString(),
						RazorConfig
								.getValue(ExpediaUtils.EXPEDIA_PARR_URL,
										"https://simulator.expediaquickconnect.com/connect/parr"),
						false);

	}
}
