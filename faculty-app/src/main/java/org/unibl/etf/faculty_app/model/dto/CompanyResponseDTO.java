package org.unibl.etf.faculty_app.model.dto;

import java.io.*;

public class CompanyResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer companyId;
	private String username;
	private String email;
	private String name;
	private Boolean isActive = false;
	
	public CompanyResponseDTO() {
		
	}

	public CompanyResponseDTO(Integer companyId, String username, String email, String name, Boolean isActive) {
		super();
		this.companyId = companyId;
		this.username = username;
		this.email = email;
		this.name = name;
		this.isActive = isActive;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
