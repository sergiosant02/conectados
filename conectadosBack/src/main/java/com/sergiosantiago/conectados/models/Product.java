package com.sergiosantiago.conectados.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sergiosantiago.conectados.models.base.BaseNamedEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Product extends BaseNamedEntity {

	private static final long serialVersionUID = -4116013557644825175L;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Product.class, fetch = FetchType.LAZY)
	private User registerBy;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Room.class, fetch = FetchType.LAZY)
	private Room room;

	@ManyToMany(cascade = CascadeType.PERSIST, targetEntity = ProductCategory.class, mappedBy = "products", fetch = FetchType.LAZY)
	private Set<ProductCategory> categories;

	@OneToMany(cascade = CascadeType.PERSIST, targetEntity = ShoppingItem.class, mappedBy = "product", fetch = FetchType.LAZY)
	private Set<ShoppingItem> shoppingItems;

	private String picture;

	public Product(Long id, String name, String picture) {
		super(id, name);
		this.picture = picture;
	}

	public Product(String name, String picture) {
		super(name);
		this.picture = picture;
	}

	public Product() {
		super();
		this.shoppingItems = new HashSet<>();
		this.categories = new HashSet<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(categories, picture, registerBy, room, shoppingItems);
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
		Product other = (Product) obj;
		return Objects.equals(categories, other.categories) && Objects.equals(picture, other.picture)
				&& Objects.equals(registerBy, other.registerBy) && Objects.equals(room, other.room)
				&& Objects.equals(shoppingItems, other.shoppingItems);
	}

}