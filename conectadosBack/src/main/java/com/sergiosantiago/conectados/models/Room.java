package com.sergiosantiago.conectados.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.models.base.BaseNamedEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Getter
public class Room extends BaseNamedEntity {

	private static final long serialVersionUID = -7429691503881841195L;

	@Column(nullable = false, length = 15)
	private String code;

	@Column(nullable = false)
	private String description;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class, fetch = FetchType.LAZY)
	private User owner;

	@ManyToMany(cascade = CascadeType.PERSIST, targetEntity = User.class, mappedBy = "roomIn", fetch = FetchType.LAZY)
	private Set<User> belongTo;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class, mappedBy = "room", fetch = FetchType.LAZY)
	private Set<Product> products;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Note.class, mappedBy = "writeIn", fetch = FetchType.LAZY)
	private Set<Note> notes;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = ShoppingList.class, mappedBy = "room", fetch = FetchType.LAZY)
	private Set<ShoppingList> shoppingLists;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = ProductCategory.class, mappedBy = "room", fetch = FetchType.LAZY)
	private Set<ProductCategory> productCategories;

	public Set<User> getAllMembers() {
		Set<User> res = new HashSet<>();
		res.addAll(this.getBelongTo());
		res.add(this.getOwner());
		return res;
	}

	public Room() {
		super();
		this.belongTo = new HashSet<>();
		this.notes = new HashSet<>();
		this.products = new HashSet<>();
		this.shoppingLists = new HashSet<>();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code, description);
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
		Room other = (Room) obj;
		return Objects.equals(code, other.code)
				&& Objects.equals(description, other.description) && Objects.equals(notes, other.notes)
				&& Objects.equals(shoppingLists, other.shoppingLists);
	}

	public RoomDTO getDTO() {
		RoomDTO dto = new RoomDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setCode(code);
		dto.setDescription(description);
		dto.setProductIds(CollectionUtils.isEmpty(products) ? new HashSet<>()
				: products.parallelStream().map(Product::getId)
						.collect(Collectors.toSet()));
		dto.setBelongToUserIds(CollectionUtils.isEmpty(belongTo) ? new HashSet<>()
				: belongTo.parallelStream().map(User::getId)
						.collect(Collectors.toSet()));
		dto.setNoteIds(CollectionUtils.isEmpty(notes) ? new HashSet<>()
				: notes.parallelStream().map(Note::getId).collect(Collectors.toSet()));
		dto.setShoppingListIds(CollectionUtils.isEmpty(shoppingLists) ? new HashSet<>()
				: shoppingLists.parallelStream().map(ShoppingList::getId).collect(Collectors.toSet()));
		dto.setOwnerId(owner.getId());
		dto.setProductCategoryIds(CollectionUtils.isEmpty(productCategories) ? new HashSet<>()
				: productCategories.parallelStream().map(pc -> pc.getId()).collect(Collectors.toSet()));
		return dto;
	}

}
