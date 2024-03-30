package com.sergiosantiago.conectados.models.auth;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Object data;
	private final String error;

	public JwtResponse(String jwttoken, Object data) {
		this.jwttoken = jwttoken;
		this.data = data;
		this.error = null;
	}

	public JwtResponse(String error) {
		this.jwttoken = null;
		this.data = null;
		this.error = error;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, error, jwttoken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtResponse other = (JwtResponse) obj;
		return Objects.equals(data, other.data) && Objects.equals(error, other.error)
				&& Objects.equals(jwttoken, other.jwttoken);
	}

}
