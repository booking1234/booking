/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import static nl.captcha.Captcha.NAME;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.cbtltd.server.MonitorService;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.servlet.SimpleCaptchaServlet;

/** The Class CaptchaServlet responds to HTTP requests for captcha instances. */
public class CaptchaServlet extends SimpleCaptchaServlet {

	private static final long serialVersionUID = 6560171562324177699L;

	/* (non-Javadoc)
	 * @see nl.captcha.servlet.SimpleCaptchaServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();

			Captcha captcha = new Captcha.Builder(300, 20)
			.addText()
			.addBackground(new GradiatedBackgroundProducer())
			.gimp()
			.addNoise()
			.addBorder()
			.build();

			session.setAttribute(NAME, captcha);
			CaptchaServletUtil.writeImage(resp, captcha.getImage());
		}
		catch (Throwable x) {MonitorService.log(x);}
	}
}
