package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class InternshipResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer internshipId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String termsAndConditions;
    private CompanyResponseDTO company;
    private List<TechnologyResponseDTO> technologies;

    public InternshipResponseDTO() {
    }

    public InternshipResponseDTO(Integer internshipId, String name, String description, LocalDate startDate,
                                 LocalDate endDate, String termsAndConditions, CompanyResponseDTO company,
                                 List<TechnologyResponseDTO> technologies) {
        this.internshipId = internshipId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termsAndConditions = termsAndConditions;
        this.company = company;
        this.technologies = technologies;
    }

    public Integer getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Integer internshipId) {
        this.internshipId = internshipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public CompanyResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyResponseDTO company) {
        this.company = company;
    }

    public List<TechnologyResponseDTO> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyResponseDTO> technologies) {
        this.technologies = technologies;
    }
}
