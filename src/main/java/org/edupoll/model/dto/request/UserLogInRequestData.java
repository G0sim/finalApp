package org.edupoll.model.dto.request;

public class UserLogInRequestData {

	String email;
	String password;

	public UserLogInRequestData() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
