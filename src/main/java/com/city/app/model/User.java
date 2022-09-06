package com.city.app.model;

import java.util.List;

public class User {
	String username;
	String password;
	List<String> role;
	
	public User() {
	}
	
	
	public User(String username, String password, List<String> role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}
	
	
	
}
