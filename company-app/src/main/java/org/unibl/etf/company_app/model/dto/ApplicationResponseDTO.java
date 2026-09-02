package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class ApplicationResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer applicationId;
    private String status; // PENDING, ACCEPTED, REJECTED
    private StudentResponseDTO student;
    private InternshipResponseDTO internship;

    public ApplicationResponseDTO() {
    }

    public ApplicationResponseDTO(Integer applicationId, String status, StudentResponseDTO student, InternshipResponseDTO internship) {
        this.applicationId = applicationId;
        this.status = status;
        this.student = student;
        this.internship = internship;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
