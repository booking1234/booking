package net.cbtltd.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;

/** This class is used to simplify work with FTP services */
public class FTPService {
	
	/**
	 * Returns Date of last modification for selected filepath
	 * @param filepath - filename, or path if it has subdirectories
	 * @param ftpClient - connected FTP client object, must be ready for use 
	 * @return Date object in yyyyMMddHHmmss format (eg. Tue Jan 21 08:52:01 EET 2014)
	 */
	public static Date getFileModificationDate(FTPClient ftpClient, String filepath) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date modificationTime = null;
		try{
			String time = ftpClient.getModificationTime(filepath);
			try {
				String timePart = time.split(" ")[1];
				modificationTime = dateFormat.parse(timePart);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return modificationTime;
	}

}