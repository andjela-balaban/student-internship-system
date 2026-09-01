package org.unibl.etf.faculty_app.model;

import java.io.*;
import java.time.*;

public class InternshipBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer internshipId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String termsAndConditions;
    private String company;
	
	public InternshipBean() {
		
	}

	public InternshipBean(Integer internshipId, String name, String description, LocalDate startDate, LocalDate endDate,
			String termsAndConditions, String company) {
		super();
		this.internshipId = internshipId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.termsAndConditions = termsAndConditions;
		this.company = company;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
