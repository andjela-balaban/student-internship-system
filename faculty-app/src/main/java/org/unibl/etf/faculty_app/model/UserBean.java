package org.unibl.etf.faculty_app.model;

import java.io.*;

public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
    private String username;
    private String password;
    private String email;
    private String jwtToken;
    private String role;
	
	public UserBean() {
		
	}

	public UserBean(Integer userId, String username, String password, String email, String jwtToken, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.jwtToken = jwtToken;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
