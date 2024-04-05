package com.sergiosantiago.conectados.models.auth;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String email;
	private String password;

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

	public JwtRequest(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtRequest other = (JwtRequest) obj;
		return Objects.equals(password, other.password) && Objects.equals(email, other.email);
	}

}