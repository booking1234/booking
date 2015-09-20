package net.cbtltd.trial.server;

import java.io.File;

import org.fax4j.FaxClient;
import org.fax4j.FaxClientFactory;
import org.fax4j.FaxJob;
import org.fax4j.FaxJob.FaxJobPriority;

/**
 * The Class FaxService uses the fax4j client factory to create fax and use client instances.<br>
 * Here is a sample code that creates a new fax client and submits a new fax job:<br>
 * <pre>
 * //get new instance of a fax client (based on internal + external fax4j.properties file data)
 * FaxClient faxClient=FaxClientFactory.createFaxClient();
 * 
 * //create a new fax job
 * FaxJob faxJob=faxClient.createFaxJob();
 * 
 * //set fax job values
 * faxJob.setFile(new File("./my_document.txt"));
 * faxJob.setPriority(FaxJobPriority.HIGH_PRIORITY);
 * faxJob.setTargetAddress("555-555");
 * faxJob.setTargetName("YourName");
 * faxJob.setSenderEmail("myemail@mycompany.com");
 * faxJob.setSenderName("MyName");
 * 
 * //submit fax job
 * faxClient.submitFaxJob(faxJob);
 * 
 * //print submitted fax job ID (may not be supported by all SPIs)
 * System.out.println("Fax job submitted, ID: "+faxJob.getID());
 * </pre>
 * The engine behind the client is the client SPI which is used internally.<br>
 * <br>
 * The configuration of the fax4j framework is made up of 3 layers.<br>
 * The configuration is based on simple properties.<br>
 * Each layer overrides the lower layers by adding/changing the property values.<br>
 * The first layer is the internal fax4j.properties file located in the fax4j jar.<br>
 * This layer contains the pre-configured values for the fax4j framework and can be changed
 * by updating these properties in the higher layers.<br>
 * The second layer is the external fax4j.properties file that is located on the classpath.<br>
 * This file is optional and provides the ability to override the internal configuration for the
 * entire fax4j framework.<br>
 * The top most layer is the optional java.util.Properties object provided by the external classes
 * when creating a new fax client.<br>
 * These properties enable to override the configuration of the lower 2 layers.<br>
 * <br>
 * Below table describes the configuration values relevant for this class,
 * for SPI specific configuration, see the relevant SPI class javadoc.<br>
 * <b>Configuration:</b>
 * <table border="1">
 * 	<tr>
 * 		<td>Name</td>
 * 		<td>Description</td>
 * 		<td>Preconfigured Value</td>
 * 		<td>Default Value</td>
 * 		<td>Mandatory</td>
 * </tr>
 * 	<tr>
 * 		<td>org.fax4j.spi.type.map.xxx</td>
 * 		<td>For each SPI type, there is a property that (with prefix: org.fax4j.spi.type.map. 
 * 			and type name for example: org.fax4j.spi.type.map.adapter) which defines the SPI class name
 * 			for the given SPI type.<br>
 * 			For example:<br>
 * 			org.fax4j.spi.type.map.adapter=org.fax4j.spi.adapter.AdapterFaxClientSpi<br>
 * 			Means that for SPI type adapter, the SPI class to be used is org.fax4j.spi.adapter.AdapterFaxClientSpi</td>
 * 		<td>
 * 			org.fax4j.spi.type.map.adapter=org.fax4j.spi.adapter.AdapterFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.windows=org.fax4j.spi.windows.WindowsFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.vbs=org.fax4j.spi.vbs.VBSFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.mail=org.fax4j.spi.email.MailFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.http=org.fax4j.spi.http.HTTPTemplateFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.process=org.fax4j.spi.process.ProcessFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.linux=org.fax4j.spi.linux.LinuxFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.mac=org.fax4j.spi.mac.MacFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.hylafax=org.fax4j.spi.hylafax.HylaFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.interfax=org.fax4j.spi.interfax.InterfaxMailFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.send2fax=org.fax4j.spi.send2fax.Send2FaxMailFaxClientSpi<br>
 * 			org.fax4j.spi.type.map.rfax=org.fax4j.spi.java4less.RFaxFaxClientSpi
 * 		</td>
 * 		<td>NA</td>
 * 		<td>NA</td>
 * </tr>
 * 	<tr>
 * 		<td>org.fax4j.spi.default.type</td>
 * 		<td>In case the SPI type was not provided in the createFaxClientSpi method, the type will be taken from
 * 			this property value.</td>
 * 		<td>adapter</td>
 * 		<td>none</td>
 * 		<td>false</td>
 * </tr>
 * 	<tr>
 * 		<td>org.fax4j.logger.class.name</td>
 * 		<td>Defines the internal fax4j logger class name.<br>
 * 			The logger must implement the org.fax4j.util.Logger interface and have an empty constructor.<br>
 * 			This property can only be set in the internal fax4j.properties and the fax4j.properties on the classpath,
 * 			providing these values as part of the java.util.Properties input of the createFaxClientSpi will have no affect.
 * 		</td>
 * 		<td>org.fax4j.util.SimpleLogger</td>
 * 		<td>org.fax4j.util.SimpleLogger</td>
 * 		<td>false</td>
 * </tr>
 * 	<tr>
 * 		<td>org.fax4j.logger.log.level</td>
 * 		<td>Defines the internal fax4j logger log level.<br>
 * 			The possible values are: DEBUG, INFO and ERROR.<br>
 * 			This property can only be set in the internal fax4j.properties and the fax4j.properties on the classpath,
 * 			providing these values as part of the java.util.Properties input of the createFaxClientSpi will have no affect.
 * 		</td>
 * 		<td>ERROR</td>
 * 		<td>ERROR</td>
 * 		<td>false</td>
 * </tr>
 * 	<tr>
 * 		<td>org.fax4j.client.class.name</td>
 * 		<td>Defines the fax client class name.<br>
 * 			Since the fax client redirects the operations to the SPI (the engine) classes, creating a
 * 			custom fax client is not required.
 * 		</td>
 * 		<td>org.fax4j.FaxClient</td>
 * 		<td>org.fax4j.FaxClient</td>
 * 		<td>false</td>
 * </tr>
 * </table>
 * <br>
 * 
 * @author 	Sagie Gur-Ari
 * @version 1.0
 * @since	0.1
 */
public class FaxService {

	public void sendFax() {
	//get new instance of a fax client (based on internal + external fax4j.properties file data)
	FaxClient faxClient = FaxClientFactory.createFaxClient();
	 
	//create a new fax job
	FaxJob faxJob = faxClient.createFaxJob();

	//set fax job values
	faxJob.setFile(new File("./my_document.txt"));
	faxJob.setPriority(FaxJobPriority.HIGH_PRIORITY);
	faxJob.setTargetAddress("555-555");
	faxJob.setTargetName("YourName");
	faxJob.setSenderEmail("myemail@mycompany.com");
	faxJob.setSenderName("MyName");

	//submit fax job
	faxClient.submitFaxJob(faxJob);

	//print submitted fax job ID (may not be supported by all SPIs)
	System.out.println("Fax job submitted, ID: "+faxJob.getID());
	}
}
