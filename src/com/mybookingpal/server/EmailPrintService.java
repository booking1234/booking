package com.mybookingpal.server;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;
import com.mybookingpal.server.api.junit.EmailServiceTest;
import com.mybookingpal.wrapper.ReservationWrapper;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import com.mybookingpal.server.EmailService;

public class EmailPrintService extends HttpServlet {
	 Configuration cfg = new Configuration();
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("Email Type "+request.getParameter("emailType"));
		System.out.println("Reservation Id "+request.getParameter("reservationId"));
		String reservationId=request.getParameter("reservationId");
		String emailType=request.getParameter("emailType");
		StringBuilder emailContent=new StringBuilder("<body onload='window.print()'>");
		//do the Configuration and Guice injection
		 cfg.setClassForTemplateLoading(EmailServiceTest.class, "templates");
		    
		    // Some other recommended settings:
		    cfg.setDefaultEncoding("UTF-8");
		    cfg.setLocale(Locale.US);
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		    Injector injector = Guice.createInjector(new RazorModule());
		    //create the mail template
		    if(reservationId!=null){
		    	ReservationWrapper reservationWrapper=getReservationWrapper(reservationId); 	
		   
		    if("Reservation".equalsIgnoreCase(emailType)){
		    	emailContent.append(EmailService.templateEngine.buildReservationEmail(reservationWrapper)); 
		    }else if("Cancellation".equalsIgnoreCase(emailType)){
		    	emailContent.append(EmailService.templateEngine.buildCancellationEmail(reservationWrapper));
		    	 
		    }
		    emailContent.append("</body>");
		    }
		    System.out.println("emailContent "+emailContent);
		   response.getWriter().write(emailContent.toString());
		  
			System.out.println("After writing email content");
			
	}
	
	private ReservationWrapper getReservationWrapper(String reservationId) {
		SqlSession sqlSession = RazorServer.openSession();
		NameId action = new NameId();
		action.setId(reservationId);
		action.setName("5");
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class)
				.altread(action);
		
		System.out.println("Reservation object id " + reservation.getId());
		sqlSession.close();
				
		ReservationWrapper reservationWrapper = new ReservationWrapper();
		reservationWrapper.buildReservationWrapper(reservation,"1234");
		
		
		return reservationWrapper;
	}
	

}
