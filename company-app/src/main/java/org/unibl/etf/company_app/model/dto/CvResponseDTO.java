package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;
import java.util.List;

public class CvResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cvId;
    private String interests;
    private String address;
    private StudentResponseDTO student;
    private List<TechnologyResponseDTO> technologies;

    public CvResponseDTO() {
    }

    public CvResponseDTO(Integer cvId, String interests, String address, StudentResponseDTO student, List<TechnologyResponseDTO> technologies) {
        this.cvId = cvId;
        this.interests = interests;
        this.address = address;
        this.student = student;
        this.technologies = technologies;
    }

    public Integer getCvId() {
        return cvId;
    }

    public void setCvId(Integer cvId) {
        this.cvId = cvId;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StudentResponseDTO getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDTO student) {
        this.student = student;
    }

    public List<TechnologyResponseDTO> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyResponseDTO> technologies) {
        this.technologies = technologies;
    }
}
