package com.mybookingpal.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailMapService extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String longitude=req.getParameter("longitude");
		String latitude=req.getParameter("latitude");
	
		resp.sendRedirect("routeFinder.html?longitude="+longitude+"&latitude="+latitude);
	}
}
