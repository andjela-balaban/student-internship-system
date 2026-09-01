package org.unibl.etf.faculty_app.model.dto;

import java.io.*;

public class FacultyResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer facultyId;
	private String username;
	private String email;
	private String name;
	
	public FacultyResponseDTO() {
		
	}
	
	public FacultyResponseDTO(Integer facultyId, String username, String email, String name) {
		super();
		this.facultyId = facultyId;
		this.username = username;
		this.email = email;
		this.name = name;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
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
}
