package com.sergiosantiago.conectados.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sergiosantiago.conectados.dtos.ShoppingListDTO;
import com.sergiosantiago.conectados.dtos.ext.ShoppingListDataExtDTO;
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
public class ShoppingList extends BaseNamedEntity {

	private static final long serialVersionUID = 7104958999553158565L;

	@Column(nullable = false)
	private Boolean completed = false;

	@OneToMany(mappedBy = "shoppingList", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ShoppingItem> items;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Room.class, fetch = FetchType.LAZY)
	private Room room;

	public ShoppingList() {
		super();
		this.items = new HashSet<ShoppingItem>();
	}

	public ShoppingListDTO getDTO() {
		ShoppingListDTO dto = new ShoppingListDTO();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setCompleted(this.getCompleted());
		dto.setItemIds(this.getItems().isEmpty() ? new HashSet<>()
				: this.getItems().stream().map(ShoppingItem::getId).collect(java.util.stream.Collectors.toSet()));
		dto.setRoomId(room.getId());
		return dto;

	}

	public ShoppingListDataExtDTO getExtDataDTO() {
		ShoppingListDataExtDTO dto = new ShoppingListDataExtDTO();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setCompleted(this.getCompleted());
		dto.setItems(this.getItems().isEmpty() ? new HashSet<>()
				: this.getItems().stream().map(ShoppingItem::getDTO).collect(java.util.stream.Collectors.toSet()));
		dto.setRoomId(room.getId());
		return dto;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(completed, items, room);
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
		ShoppingList other = (ShoppingList) obj;
		return Objects.equals(completed, other.completed) && Objects.equals(items, other.items)
				&& Objects.equals(room, other.room);
	}

}
