package net.cbtltd.shared;

import java.io.Serializable;

public class Credentials implements Serializable {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Credentials() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Credentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Credentials [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		return builder.toString();
	}

}
