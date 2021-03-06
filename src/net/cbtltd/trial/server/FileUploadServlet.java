/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.api.HasUrls;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * The Class FileUploadServlet to upload files on the server.
 * Add the following to web.xml to enable.
  <servlet>
    <servlet-name>uploadServlet</servlet-name>
    <servlet-class>net.cbtltd.server.FileUploadServlet</servlet-class>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>uploadServlet</servlet-name>
    <url-pattern>/razor/upload</url-pattern>
  </servlet-mapping>
  */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  private static final String UPLOAD_DIRECTORY =  RazorServer.ROOT_DIRECTORY; //TODO:
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField()) continue;

					String fileName = item.getName();
					// get only the file name not whole path
					if (fileName != null) {fileName = FilenameUtils. getName(fileName);}

					File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
					if (uploadedFile.createNewFile()) {
						item.write(uploadedFile);
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print("The file was created successfully.");
						resp.flushBuffer();
					} 
					else {throw new IOException("The file already exists in repository.");}
				}
			} 
			catch (Exception e) {resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the file : " + e.getMessage()); }
		} 
		else {resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Request contents type is not supported by the servlet.");}
	}
}