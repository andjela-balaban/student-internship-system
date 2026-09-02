package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InternshipLogResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer logId;
    private StudentResponseDTO student;
    private InternshipResponseDTO internship;
    private FacultyResponseDTO faculty;
    private String content;
    private String companyComment;
    private String facultyComment;
    private LocalDateTime lastLogEntry;
    private Integer grade;

    public InternshipLogResponseDTO() {
    }

    public InternshipLogResponseDTO(Integer logId, StudentResponseDTO student, InternshipResponseDTO internship,
                                   FacultyResponseDTO faculty, String content, String companyComment,
                                   String facultyComment, LocalDateTime lastLogEntry, Integer grade) {
        this.logId = logId;
        this.student = student;
        this.internship = internship;
        this.faculty = faculty;
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

    public StudentResponseDTO getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDTO student) {
        this.student = student;
    }

    public InternshipResponseDTO getInternship() {
        return internship;
    }

    public void setInternship(InternshipResponseDTO internship) {
        this.internship = internship;
    }

    public FacultyResponseDTO getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyResponseDTO faculty) {
        this.faculty = faculty;
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
