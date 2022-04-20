package com.kamaz.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "positions", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") })
public class Position {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;

	@JsonProperty("dateOf")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "dateOf")
	private Timestamp dateOf;

	@Column(name = "description")
	@Size(min = 3, max = 55)
	private String description;

	// @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	// @ManyToMany(fetch = FetchType.LAZY)
//	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
//	@JoinTable(name = "user_positions", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//	private Set<User> users = new HashSet<>();

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "user_positions", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//	private Set<User> users = new HashSet<>();

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_positions", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnoreProperties({ "roles", "rooms", "positions" })
	private Set<User> users = new HashSet<>();

	public Position() {

	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDateOf() {
		return dateOf;
	}

	public void setDateOf(Timestamp dateOf) {
		this.dateOf = dateOf;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
