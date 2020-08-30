package com.cspro.authenticationapp.model;

public class AuthenticationResponse {
	private String jwt;

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public AuthenticationResponse() {
		super();
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
