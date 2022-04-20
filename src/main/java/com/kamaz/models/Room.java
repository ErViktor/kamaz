package com.kamaz.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rooms", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") })
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 8)
	private String name;

	@Column(name = "uuid", columnDefinition = "UUID default '00000000-0000-0000-0000-000000000000'")
	private UUID uuid;

	@Column(name = "locked", columnDefinition = "boolean default 'true'")
	private boolean locked;

	@Column(name = "isprivate", columnDefinition = "boolean default 'false'")
	private boolean isprivate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_rooms", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	public Room() {
	}

	public Room(String name, Boolean locked, Boolean isprivate, UUID uuid) {
		this.name = name;
		this.locked = locked;
		this.isprivate = isprivate;
		this.uuid = uuid;
	}

	public Long getRoomId() {
		return id;
	}

	public void setRoomId(Long id) {
		this.id = id;
	}

	public String getRoomName() {
		return name;
	}

	public void setRoomName(String name) {
		this.name = name;
	}

	public boolean getRoomLocked() {
		return locked;
	}

	public void setRoomLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean getRoomIsPrivate() {
		return isprivate;
	}

	public void setRoomIsPrivate(boolean isprivate) {
		this.isprivate = isprivate;
	}

	public UUID getRoid() {
		return uuid;
	}

	public void setRoid(UUID uuid) {
		this.uuid = uuid;
	}

}
