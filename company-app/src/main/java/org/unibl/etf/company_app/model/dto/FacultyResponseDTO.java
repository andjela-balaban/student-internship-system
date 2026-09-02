package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class FacultyResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer facultyId;
    private String name;
    private String address;
    private String email;
    private String city;

    public FacultyResponseDTO() {
    }

    public FacultyResponseDTO(Integer facultyId, String name, String address, String email, String city) {
        this.facultyId = facultyId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.city = city;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
