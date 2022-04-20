package com.kamaz.payload.response;

import java.util.List;
import java.util.UUID;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private UUID userroom;
	private String devicekeys;

	public JwtResponse(String accessToken, Long id, String username, String email, UUID userroom, List<String> roles,
			String devicekeys) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.userroom = userroom;
		this.devicekeys = devicekeys;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public UUID getUserroom() {
		return userroom;
	}

	public void setUserroom(UUID userroom) {
		this.userroom = userroom;
	}

	public String getDevicekeys() {
		return devicekeys;
	}

	public void setDevicekeys(String devicekeys) {
		this.devicekeys = devicekeys;
	}
	// devicekeys

}
