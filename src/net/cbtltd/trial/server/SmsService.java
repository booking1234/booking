package net.cbtltd.trial.server;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Sms;
import net.cbtltd.shared.sms.SmsSend;

import org.apache.ibatis.session.SqlSession;

/**
 * The Class SmsService is to send SMS messages.
 * <p>
 * Amazon is excited to announce that Amazon Simple Notification Service now supports SMS text messages as a new method for 
 * notifications. SMS (also known as Short Message Service) is one of the most widely used messaging applications in the world. 
 * With support for SMS text messaging, Amazon SNS messages can be delivered to SMS-enabled cell phones and smart phones.
 * <p>
 * Amazon SNS is a web service that makes it easy to send notifications from the cloud to applications or people. 
 * It provides developers with a highly scalable, flexible, and cost-effective way to publish messages and have them 
 * immediately delivered to any number of subscribers or other applications. Previously Amazon SNS supported message 
 * deliveries via HTTP, email or to Amazon SQS queues.
 * <p>
 * Notifications can now be sent as text messages to cell phones, smart phones or any other device that supports SMS. 
 * Amazon CloudWatch users who monitor their applications running on AWS services can receive timely updates for any 
 * event of interest or alarm they set. Existing Amazon SNS users who receive content via email can now also have that 
 * content delivered to their mobile device. SMS notifications with Amazon SNS can also be used to relay time-critical 
 * application events to mobile applications and devices. Mobile applications increasingly deliver and integrate real-time 
 * information from a variety of sources, including weather, traffic, social-media updates, and even multi-player games. 
 * Developers can easily integrate mobile applications with Amazon SNS today, to send messages and receive notifications 
 * over SMS. This new Amazon SNS feature can be easily accessed from the AWS Management Console available at:
 * @see <pre>http://console.aws.amazon.com</pre>. While Amazon SNS is supported in all AWS regions, SMS notification support 
 * is currently supported in the US-East region, and will be available in other AWS regions and countries in the coming months.
 * <p>
 * To learn more about the usage and benefits of the service, you can visit the Amazon SNS detail page available at: 
 * @see <pre>http://aws.amazon.com/sns</pre>.
 */
public class SmsService 
implements IsService {
	
	private static SmsService service;

	/**
	 * Gets the single instance of SmsService to send SMS messages.
	 * @see net.cbtltd.shared.Account
	 *
	 * @return single instance of SmsService.
	 */
	public static synchronized SmsService getInstance() {
		if (service == null) {service = new SmsService();}
		return service;
	}

	/**
	 * Executes the SmsSend action to send an SMS instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Sms execute(SqlSession sqlSession, SmsSend action) {
		try {
			//TODO: comment following line to disable sms for testing
			throw new RuntimeException("SMS not yet implemented.");
		}
		catch (Throwable x) {MonitorService.log(x);}
		return action;
	}
}
