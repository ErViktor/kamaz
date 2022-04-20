package com.kamaz.models;

import java.sql.Timestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	private String fullname;

	@Column(name = "age")
	private String age;

	@JsonProperty("dateOf")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "dateOf")
	private Timestamp dateOf;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Size(max = 120)
	private String password;

	@Column(name = "userroom", columnDefinition = "UUID default '00000000-0000-0000-0000-000000000000'")
	private UUID userroom;

	@Column(name = "locked", columnDefinition = "boolean default 'false'")
	private boolean locked;

	@Column(name = "status", columnDefinition = "varchar default 'unavailable'")
	private String status;

	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_rooms", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
	private Set<Room> rooms = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_departaments", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "departament_id"))
	private Set<Departament> departaments = new HashSet<>();

	// employments
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_employments", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "employment_id"))
	@JsonIgnoreProperties("users")
	private Set<Employment> employments = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_positions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
	@JsonIgnoreProperties("users")
	private Set<Position> positions = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(Boolean locked) {
		this.locked = locked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}

	// position
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	// POSITIONS
	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	// EMPLOYMENTS
	public Set<Employment> getEmployments() {
		return employments;
	}

	public void setEmployments(Set<Employment> employments) {
		this.employments = employments;
	}

	// DEPARTAMENTSS
	public Set<Departament> getDepartaments() {
		return departaments;
	}

	public void setDepartaments(Set<Departament> departaments) {
		this.departaments = departaments;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public Timestamp getDateOf() {
		return dateOf;
	}

	public void setDateOf(Timestamp dateOf) {
		this.dateOf = dateOf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// positions

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UUID getUserroom() {
		return userroom;
	}

	public void setUserroom(UUID userroom) {
		this.userroom = userroom;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

}