package org.unibl.etf.faculty_app.model.dto;

import java.io.*;
import java.time.*;

public class StudentRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String studentIndex;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private Integer facultyId;
	
	public StudentRequestDTO() {
		
	}
	
	public StudentRequestDTO(String username, String email, String password, String firstName, String lastName,
			String studentIndex, LocalDate dateOfBirth, String phoneNumber, Integer facultyId) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentIndex = studentIndex;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStudentIndex() {
		return studentIndex;
	}

	public void setStudentIndex(String studentIndex) {
		this.studentIndex = studentIndex;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}
}
