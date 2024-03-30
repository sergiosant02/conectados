package com.sergiosantiago.conectados.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.sergiosantiago.conectados.models.base.BaseNamedEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
public class ProductCategory extends BaseNamedEntity {

	private static final long serialVersionUID = 63677053361144268L;

	@ManyToMany(mappedBy = "categories", cascade = CascadeType.PERSIST, targetEntity = Product.class, fetch = FetchType.LAZY)
	private Set<Product> products;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class, fetch = FetchType.LAZY)
	private User registerBy;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Room.class, fetch = FetchType.LAZY)
	private Room room;

	public ProductCategory() {
		super();
		this.products = new HashSet<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(products, registerBy);
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
		ProductCategory other = (ProductCategory) obj;
		return Objects.equals(products, other.products) && Objects.equals(registerBy, other.registerBy)
				&& Objects.equals(room, other.room);
	}

}
