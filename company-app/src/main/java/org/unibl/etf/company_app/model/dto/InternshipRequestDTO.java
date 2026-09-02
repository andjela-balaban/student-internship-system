package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class InternshipRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String termsAndConditions;
    private Integer companyId;
    private List<Integer> technologyIds;

    public InternshipRequestDTO() {
    }

    public InternshipRequestDTO(String name, String description, LocalDate startDate, LocalDate endDate,
                                String termsAndConditions, Integer companyId, List<Integer> technologyIds) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termsAndConditions = termsAndConditions;
        this.companyId = companyId;
        this.technologyIds = technologyIds;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getTechnologyIds() {
        return technologyIds;
    }

    public void setTechnologyIds(List<Integer> technologyIds) {
        this.technologyIds = technologyIds;
    }
}
