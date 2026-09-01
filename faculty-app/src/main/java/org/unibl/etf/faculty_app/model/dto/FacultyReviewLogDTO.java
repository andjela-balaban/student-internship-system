package org.unibl.etf.faculty_app.model.dto;

import java.io.*;

public class FacultyReviewLogDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer grade;
	private String facultyComment;
	
	public FacultyReviewLogDTO() {
		
	}

	public FacultyReviewLogDTO(Integer grade, String facultyComment) {
		super();
		this.grade = grade;
		this.facultyComment = facultyComment;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getFacultyComment() {
		return facultyComment;
	}

	public void setFacultyComment(String facultyComment) {
		this.facultyComment = facultyComment;
	}
}
