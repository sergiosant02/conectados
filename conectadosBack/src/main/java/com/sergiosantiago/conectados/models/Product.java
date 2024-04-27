package com.sergiosantiago.conectados.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sergiosantiago.conectados.dtos.ProductDTO;
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

	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "products", fetch = FetchType.LAZY)
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
		result = prime * result + Objects.hash(picture, room);
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
		return Objects.equals(picture, other.picture);
	}

	public ProductDTO getDTO() {
		ProductDTO dto = new ProductDTO();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setPicture(this.picture);
		dto.setCategoryIds(this.categories.isEmpty() ? new HashSet<>()
				: this.categories.parallelStream().map(ProductCategory::getId).collect(Collectors.toSet()));
		dto.setRoomId(this.room.getId());
		dto.setUserId(this.registerBy.getId());
		dto.setShoppingItemIds(this.shoppingItems.isEmpty() ? new HashSet<>()
				: this.shoppingItems.parallelStream().map(ShoppingItem::getId).collect(Collectors.toSet()));
		return dto;
	}

}