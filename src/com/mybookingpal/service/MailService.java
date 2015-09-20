package com.mybookingpal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Mail;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.mail.MailSend;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class MailService is to send email messages using JavaMail and the Gmail SMTP server.
 * @see	<pre>http://www.velocityreviews.com/FORUMS/t141237-send-smtp-mail-using-javamail-with-gmail-account.html</pre>
 * @see <pre>http://forums.sun.com/thread.jspa?threadID=668779</pre>
 * @see	<pre>http://en.wikipedia.org/wiki/Web_bug</pre>
 * 
 * To compile and run, you must have mail.jar (from the JavaMail download) and activation.jar in classpath.
 * You need to replace email addresses and mail server with your values where noted.
 * User name and password are generally not needed to send e-mail although your ISP may still require it to prevent spam from going through its systems.
 * Turn on ("mail.debug") to see what is going on behind the scenes in JavaMail code.
 * For example, an e-mail sent to the address somebody@example.org can contain the embedded
 * image of URL <pre>http://example.com/bug.gif?somebody@example.org</pre>. Whenever the user reads the
 * e-mail, the image at this URL is requested. The part of the URL after the question mark
 * is ignored by the server for the purpose of determining which file to send, but the
 * complete URL is stored in the server's log file. As a result, the file bug.gif is sent
 * and shown in the e-mail reader; at the same time, the server stores the fact that the
 * particular e-mail sent to somebody@example.org has been read. Using this system, a
 * spammer or e-mail marketer can send similar e-mails to a large number of addresses
 * to check which ones are valid and read by the users.
 * 
 * A non-standard but widely used way to request return receipts is with the "Return-Receipt-To:"
 * (RRT) email header. An email address is specified as the content of the header. The first
 * time a user opens an email message containing this header, the client will typically prompt
 * the user whether or not to send a return receipt
 * 
 * The HTML email only needs the image tag with <pre>src="http://mysite/mytracker.php?user_id=</pre>;
 * and the mytracker.php would take the passed-in user_id or whatever, any number of parameters,
 * and write them to the db. then it would send a GIF content-type header, and output basically nothing.
 */
public class MailService
implements IsService {

	private static final Logger LOG = Logger.getLogger(MailService.class.getName());
	private static MailService service;
	private static String emailAddress = null;

	/**
	 * Gets the single instance of MailService to manage Mail instances.
	 * @see net.cbtltd.shared.Mail
	 *
	 * @return single instance of MailService
	 */
	public static synchronized MailService getInstance() {
		if (service == null) {service = new MailService();}
		return service;
	}

	/**
	 * Executes the MailSend action to send a Mail instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Mail execute(SqlSession sqlSession, MailSend action) {
		send(action);
		return action;
	}

	private static MimeMessage getMimeMessage(Mail mail) throws MessagingException, UnsupportedEncodingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,	new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply@mybookingpal.com","isaac123456");
			}
		});

		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress(mail.getSender()));
		message.setSubject(mail.getSubject());
		if (mail.getRecipients().indexOf(',') > 0) {message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipients()));}
		else {message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getRecipients()));}

		message.setFrom(new InternetAddress("noreply@mybookingpal.com"));
		
		return message;
	}

	/**
	 * Send the specified email message.
	 *
	 * @param mail the specified email message
	 * @return the email message.
	 */
	public static Mail send(Mail mail) {
		try {
			LOG.debug("send " + mail);
			MimeMessage message = getMimeMessage(mail);
			message.setContent(mail.getContent(), "text/plain");
			Transport.send(message);
			mail.setStatus(0);
		}
		catch (MessagingException x) {
			MonitorService.log(x);
			mail.setStatus(1);
			LOG.error("Error while sending the mail");
			} 
		catch (UnsupportedEncodingException x) {
			MonitorService.log(x);
			mail.setStatus(1);
			LOG.error("Error while sending the mail");
			}
		return mail;
	}

	/**
	 * Send the specified email message with a list of file attachments.
	 *
	 * @param mail the specified email message
	 * @param filenames the names of the files to be attached.
	 * @return the email message.
	 */
	public static Mail send(Mail mail, ArrayList<String> filenames) {
		try {
			MimeMessage message = getMimeMessage(mail);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mail.getContent());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Attachments
			for (String filename : filenames) {
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);
			LOG.debug("send " + mail + " "+ filenames);
			if (isMail()) {Transport.send(message);}
			else if(emailAddress != null && !emailAddress.isEmpty()) {
				mail.setRecipients(emailAddress);
			}
			else {LOG.error("do not send " + mail + " "+ filenames);}
		} catch (MessagingException x) {MonitorService.log(x);}
		catch (UnsupportedEncodingException x) {MonitorService.log(x);}
		return mail;
	}
	
	/**
	 * Checks if the mail.txt file exists
	 * 
	 * @return true if the mail.txt file exists
	 */
	public static boolean isMail() {
		boolean live = true;
		try {
			File emailFile = new File(HasUrls.APPLICATION_DIRECTORY + "net/cbtltd/server/mail.txt"); // 
			System.out.println(emailFile.getAbsolutePath());
			if(emailFile.exists()) {
				InputStreamReader fileReader = new InputStreamReader(new FileInputStream(emailFile), "UTF8");
				String email = "";
				int data = fileReader.read();
				while(data != -1) {
				  email += (char) data;
				  data = fileReader.read();
				}
				fileReader.close();
				if(Party.isEmailAddress(email)) {
					emailAddress = email;
					live = false;
				} else if (email.isEmpty()) {
					live = true;
				} else {
					live = false;
				}
			} else {
				live = false;
			}
//			Resources.getResourceAsReader("net/cbtltd/server/mail.txt");
		}
		catch (IOException x) {live = false;}
		return live;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
}