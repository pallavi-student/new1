package com.casestudy.model;

public class AuthenticationResponse {
	
	private String jwt;
	private String success;

	public AuthenticationResponse(String jwt, String success) {
		super();
		this.jwt = jwt;
		this.success= success;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
		

}
