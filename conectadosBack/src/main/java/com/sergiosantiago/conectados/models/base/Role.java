package com.sergiosantiago.conectados.models.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.sergiosantiago.conectados.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Role extends BaseNamedEntity {

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = User.class)
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public Role(String name) {
		super(name);
		this.users = new ArrayList<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(users);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(users, other.users);
	}

}