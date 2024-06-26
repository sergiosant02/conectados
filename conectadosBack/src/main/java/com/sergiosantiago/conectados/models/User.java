package com.sergiosantiago.conectados.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.sergiosantiago.conectados.dtos.UserDTO;
import com.sergiosantiago.conectados.models.base.BaseNamedEntity;
import com.sergiosantiago.conectados.models.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, exclude = { "password" })
@AllArgsConstructor
@Entity
public class User extends BaseNamedEntity implements UserDetails {

	private static final long serialVersionUID = -9027185395534992417L;

	public User(Long id, String name) {
		super(id, name);

	}

	@Column(length = 100)
	private String lastName;

	private String picture;

	@Column(unique = true, length = 60)
	@Email(message = "{errors.invalidEmail}")
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = true)
	private String role;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private Boolean enabled = true;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Room.class)
	@JoinTable(name = "USER_ROOM_IN")
	private Set<Room> roomIn;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Room.class)
	private Set<Room> roomOwner;

	@OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Note.class)
	private Set<Note> notes;

	@OneToMany(mappedBy = "registerBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	private Set<Product> products;

	@OneToMany(mappedBy = "registerBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ProductCategory.class)
	private Set<ProductCategory> productCategories;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;

	public User() {
		super();
		this.notes = new HashSet<>();
		this.roomOwner = new HashSet<>();
		this.roomIn = new HashSet<>();
		this.productCategories = new HashSet<>();
		this.products = new HashSet<>();
	}

	public User(String name) {
		super(name);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] userRoles = { this.role };
		return AuthorityUtils.createAuthorityList(userRoles);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(email, enabled, lastName, password, picture, role);
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
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(enabled, other.enabled)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(notes, other.notes)
				&& Objects.equals(password, other.password) && Objects.equals(picture, other.picture)
				&& Objects.equals(role, other.role)
				&& Objects.equals(roomIn, other.roomIn) && Objects.equals(roomOwner, other.roomOwner);
	}

	public UserDTO getDTO() {
		UserDTO dto = new UserDTO();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setLastName(this.getLastName());
		dto.setPicture(this.getPicture());
		dto.setEmail(this.getEmail());
		dto.setRole(this.getRole());
		dto.setEnabled(this.isEnabled());
		dto.setGender(this.getGender());
		dto.setRoomInIds(this.roomIn.isEmpty() ? new HashSet<>()
				: this.roomIn.stream().map(Room::getId).collect(Collectors.toSet()));
		dto.setRoomOwnerIds(this.roomOwner.isEmpty() ? new HashSet<>()
				: this.roomOwner.stream().map(Room::getId).collect(Collectors.toSet()));
		dto.setNoteIds(this.notes.isEmpty() ? new HashSet<>()
				: this.notes.stream().map(Note::getId).collect(Collectors.toSet()));
		dto.setProductIds(this.products.isEmpty() ? new HashSet<>()
				: this.products.stream().map(Product::getId).collect(Collectors.toSet()));
		dto.setProductCategoryIds(this.productCategories.isEmpty() ? new HashSet<>()
				: this.productCategories.stream().map(pc -> pc.getId()).collect(Collectors.toSet()));
		return dto;
	}

}
