package org.unibl.etf.faculty_app.model.dto;

import java.io.*;
import java.time.*;

public class StudentResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer studentId;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String studentIndex;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private FacultyResponseDTO faculty;
	
	public StudentResponseDTO() {
		
	}

	public StudentResponseDTO(Integer studentId, String username, String email, String firstName, String lastName,
			String studentIndex, LocalDate dateOfBirth, String phoneNumber, FacultyResponseDTO faculty) {
		super();
		this.studentId = studentId;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentIndex = studentIndex;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.faculty = faculty;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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

	public FacultyResponseDTO getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyResponseDTO faculty) {
		this.faculty = faculty;
	}	
}
