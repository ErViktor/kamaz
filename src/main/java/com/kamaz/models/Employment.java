package com.kamaz.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "employments", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") })
public class Employment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long creatorId;

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

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_employments", joinColumns = @JoinColumn(name = "employment_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnoreProperties({ "roles", "rooms", "positions", "employments" })
	private User users;

	public Employment() {

	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
