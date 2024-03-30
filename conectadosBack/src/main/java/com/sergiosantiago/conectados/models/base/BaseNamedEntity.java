package com.sergiosantiago.conectados.models.base;

import java.util.Objects;

import javax.jdo.annotations.Column;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseNamedEntity extends BaseEntity {

	private static final long serialVersionUID = -4116013557644825175L;

	@Column(allowsNull = "false", length = 255)
	protected String name;

	protected BaseNamedEntity(Long id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
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
		BaseNamedEntity other = (BaseNamedEntity) obj;
		return Objects.equals(name, other.name);
	}

}
