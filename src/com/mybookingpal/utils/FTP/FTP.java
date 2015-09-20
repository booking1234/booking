package com.mybookingpal.utils.FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

public class FTP {
	private String host;
	private String user;
	private String password;
	FTPClient ftpClient;
	
	
	public FTP() {}
	
	public FTP(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.ftpClient = new FTPClient();
	}
	
	/**
	 * @param source The directory of the file being sent
	 * @param fileName The name of the file being sent
	 */
	public void sendFile(String filePath, String fileName) {
		try {
			ftpClient.connect(host);
			ftpClient.enterLocalPassiveMode();
			if (!ftpClient.login(user, password)) {
				throw new Exception("Unable to login.");
			}
			FileInputStream fileToBeSent = new FileInputStream(filePath);
			ftpClient.storeFile(fileName, fileToBeSent);
			fileToBeSent.close();
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	
	public void sendFile(String filePath, String fileName, boolean deleteFile) {
		sendFile(filePath, fileName); 
		if (deleteFile) {
			File file = new File(filePath);
			file.delete();
		}
	}
	
	/**
	 * Retrieves the file specified in the current working directory.
	 * Returns true if the file was downloaded successfully, false
	 * if not or if an error was thrown
	 * @param filename The name of the file being retrieved
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public boolean retrieveFile(String filename, String saveDirectory) {
		FTPClient ftpClient = new FTPClient();
		OutputStream output;
		try {
			ftpClient.connect(host);
			if (!ftpClient.login(user, password)) {
				ftpClient.logout();
				return false;
			}
			output = new FileOutputStream(saveDirectory + System.getProperty("file.separator") + filename);
			ftpClient.enterLocalPassiveMode();
			ftpClient.retrieveFile(filename, output);
			output.close();
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (Exception e) {
			System.out.println("error while downloading files");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean retrieveFiles(List<String> files, String saveDirectory) {
		FTPClient ftpClient = new FTPClient();
		OutputStream output;
		try {
			ftpClient.connect(host);
			if (!ftpClient.login(user, password)) {
				ftpClient.logout();
				return false;
			}
			ftpClient.enterLocalPassiveMode();
			for (String file : files) {
				output = new FileOutputStream(saveDirectory + System.getProperty("file.separator") + file);
				ftpClient.retrieveFile(file, output);
				output.close();
			}
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (Exception e) {
			System.out.println("error while downloading files");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
