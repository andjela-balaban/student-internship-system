package org.unibl.etf.faculty_app.model.dto;

import java.io.*;

public class CompanyRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
    private String password;
    private String email;
    private String name;
    private Boolean isActive;
    
    public CompanyRequestDTO() {
    	
    }

	public CompanyRequestDTO(String username, String password, String email, String name, Boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.isActive = isActive;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
