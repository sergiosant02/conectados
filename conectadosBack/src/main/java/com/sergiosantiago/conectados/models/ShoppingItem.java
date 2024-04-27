package com.sergiosantiago.conectados.models;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;

import com.sergiosantiago.conectados.dtos.ShoppingItemDTO;
import com.sergiosantiago.conectados.models.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
public class ShoppingItem extends BaseEntity {

	private static final long serialVersionUID = 4791135555935927713L;

	@DecimalMin("0.01")
	@NonNull
	private Double quantity;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = ShoppingList.class, fetch = FetchType.LAZY)
	private ShoppingList shoppingList;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Product.class, fetch = FetchType.LAZY)
	private Product product;

	@Column(nullable = true)
	@DecimalMin("0.01")
	private Double value;

	public ShoppingItemDTO getDTO() {
		return new ShoppingItemDTO(id, quantity, shoppingList.getId(), product.getId(), value, product.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(product, quantity, value);
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
		ShoppingItem other = (ShoppingItem) obj;
		return Objects.equals(product, other.product) && Objects.equals(quantity, other.quantity)
				&& Objects.equals(value, other.value);
	}

}
