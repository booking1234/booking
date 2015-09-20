package com.mybookingpal.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mybookingpal.service.MailService;
import net.cbtltd.shared.Mail;

public class RegistrationService extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String cname = req.getParameter("cname");
		String pmsAccountId = req.getParameter("accid");
		String companyName = req.getParameter("compname");
		String fullName = req.getParameter("fullname");
		String country = req.getParameter("country");
		String address = req.getParameter("addr");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		String telNumber = req.getParameter("telno");
		String email = req.getParameter("email");
		
		Mail mail = new Mail();
		mail.setSubject(companyName + " " + cname + " Registration");
		StringBuilder sb = new StringBuilder();
		sb.append(cname + " Registration Information:\n");
		sb.append("Account ID: ");
		sb.append(pmsAccountId);
		sb.append("\nCompany Name: ");
		sb.append(companyName);
		sb.append("\nFirst Name: ");
		sb.append(fullName);
		sb.append("\nCountry: ");
		sb.append(country);
		sb.append("\nAddress: ");
		sb.append(address);
		sb.append("\nCity: ");
		sb.append(city);
		sb.append("\nState: ");
		sb.append(state);
		sb.append("\nZip: ");
		sb.append(zip);
		sb.append("\nTelephone Number: ");
		sb.append(telNumber);
		sb.append("\nEmail: ");
		sb.append(email);
		
		mail.setContent(sb.toString());
		String recipients = "ray@mybookingpal.com, brian@mybookingpal.com, alex@mybookingpal.com";

		mail.setRecipients(recipients);
		MailService.send(mail);
		
		if(mail.getStatus() == 0)
		{
			StringBuilder conf = new StringBuilder();

			final Mail confMail = new Mail();
			confMail.setSubject(cname + " Registration Confirmation");
			conf.append("Thank you for your registration request with BookingPal."); 
			conf.append(" We have received your information and will set up your account in our system."); 
			conf.append(" We will contact you shortly to complete the registration process.");
			conf.append(" We look forward to working with you.\n");
			conf.append("\nPlease contact Ray Karimi, at BookingPal, Inc. with any questions.");
			conf.append("\nE-mail: ray@mybookingpal.com");
			conf.append("\nTel. 949-379-8362\n\n\n\n\n");
			conf.append("\nWith BookingPal you can now have your inventory displayed on and distributed in over 50 ");
			conf.append("national and international channels as well as in front of large number of travel agents ");
			conf.append("that specialize in vacation rental properties. Expand your exposure and generate more ");
			conf.append("bookings from channels such as: Villas.com, Booking.com, Homes.com, Condos.com, ");
			conf.append("Walkscore, StopSleepGo and many more. Simply select the channels that you want to be ");
			conf.append("displayed on and BookingPal will automatically upload and update your property information, ");
			conf.append("availability calendars and rates. www.mybookingpal.com");
			
			confMail.setContent(conf.toString());
			confMail.setRecipients(email.trim());
			
			// send confirmation email in a separate thread
			new Thread() {
				public void run() {
					MailService.send(confMail);
				}
			}.start();
			
			req.setAttribute("result", "success");
				
		}
		else
		{
			req.setAttribute("result", "failed");
		}
		
		getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
	}
}
