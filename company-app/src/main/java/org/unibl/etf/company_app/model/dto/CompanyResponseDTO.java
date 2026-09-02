package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class CompanyResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer companyId;
    private String name;
    private String email;
    private String username;
    private Boolean isActive;

    public CompanyResponseDTO() {
    }

    public CompanyResponseDTO(Integer companyId, String name, String email, String username, Boolean isActive) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.isActive = isActive;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
