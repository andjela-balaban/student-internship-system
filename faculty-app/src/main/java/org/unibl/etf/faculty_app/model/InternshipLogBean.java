package org.unibl.etf.faculty_app.model;

import java.io.*;
import java.time.*;

public class InternshipLogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer logId;
    private String studentFullName;
    private String studentIndex;
    private String internshipName;
    private String companyName;
    private String content;
    private String companyComment;
    private String facultyComment;
    private LocalDateTime lastLogEntry;
    private Integer grade;
    
	public InternshipLogBean() {
		
	}

	public InternshipLogBean(Integer logId, String studentFullName, String studentIndex, String internshipName,
			String companyName, String content, String companyComment, String facultyComment,
			LocalDateTime lastLogEntry, Integer grade) {
		super();
		this.logId = logId;
		this.studentFullName = studentFullName;
		this.studentIndex = studentIndex;
		this.internshipName = internshipName;
		this.companyName = companyName;
		this.content = content;
		this.companyComment = companyComment;
		this.facultyComment = facultyComment;
		this.lastLogEntry = lastLogEntry;
		this.grade = grade;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getStudentFullName() {
		return studentFullName;
	}

	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}

	public String getStudentIndex() {
		return studentIndex;
	}

	public void setStudentIndex(String studentIndex) {
		this.studentIndex = studentIndex;
	}

	public String getInternshipName() {
		return internshipName;
	}

	public void setInternshipName(String internshipName) {
		this.internshipName = internshipName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCompanyComment() {
		return companyComment;
	}

	public void setCompanyComment(String companyComment) {
		this.companyComment = companyComment;
	}

	public String getFacultyComment() {
		return facultyComment;
	}

	public void setFacultyComment(String facultyComment) {
		this.facultyComment = facultyComment;
	}
	
	public LocalDateTime getLastLogEntry() {
		return lastLogEntry;
	}

	public void setLastLogEntry(LocalDateTime lastLogEntry) {
		this.lastLogEntry = lastLogEntry;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
