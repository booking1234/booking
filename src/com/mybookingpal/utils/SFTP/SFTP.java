package com.mybookingpal.utils.SFTP;

import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTP {
	private String host;
	private int port;
	private String user;
	private String password;
	private JSch jsch;
	
	public SFTP() {}
	
	/**
	 * 
	 * @param host the host of the SFTP server
	 * @param user the username
	 * @param password the password
	*/
	public SFTP(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = 22;
	}
	
	
	/**
	 * 
	 * @param host the host of the SFTP server
	 * @param user the username
	 * @param password the password
	 * @param port the port connecting to
	 */
	public SFTP(String host, String user, String password, int port) {
		this.host = host;
		this.user = user;
		this.password = password;
	}	
	
	/**
	 * Retrieves the file specified to the specified directory
	 * @param fileName the name of the file to be retrieved
	 * @param saveDirectory the directory to save the file
	 * @throws JSchException 
	 * @throws SftpException 
	 */
	public void retrieveFile(String fileName, String saveDirectory) throws JSchException, SftpException {
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		JSch jsch = new JSch();
		session = jsch.getSession(user, host, port);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		channelSftp = (ChannelSftp) channel;
		channelSftp.get(fileName, saveDirectory);
		session.disconnect();
		channelSftp.quit();
	}
	
	public void retrieveFiles(List<String> files, String saveDirectory) throws JSchException, SftpException {
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		JSch jsch = new JSch();
		session = jsch.getSession(user, host, port);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		channelSftp = (ChannelSftp) channel;
		for (String file : files)
			channelSftp.get(file, saveDirectory);
		session.disconnect();
		channelSftp.quit();
	}
	
	public void sendFile(String fileName, String filePath) {
		
	}
	
	
	
	
}
