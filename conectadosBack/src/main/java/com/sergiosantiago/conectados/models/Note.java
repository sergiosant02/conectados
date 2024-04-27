package com.sergiosantiago.conectados.models;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.sergiosantiago.conectados.dtos.NoteDTO;
import com.sergiosantiago.conectados.models.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
public class Note extends BaseEntity {

	private static final long serialVersionUID = -5964575588245105048L;

	public Note(Long id) {
		super(id);
	}

	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = true)
	private String body;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class, fetch = FetchType.LAZY)
	private User writer;

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Room.class, fetch = FetchType.LAZY)
	private Room writeIn;

	public Note() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(body, title, writeIn, writer);
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
		Note other = (Note) obj;
		return Objects.equals(body, other.body) && Objects.equals(title, other.title)
				&& Objects.equals(writeIn, other.writeIn) && Objects.equals(writer, other.writer);
	}

	public NoteDTO getDTO() {
		return new NoteDTO(this.getId(), this.title, this.body, this.writer.getId(), this.writeIn.getId());
	}

}
