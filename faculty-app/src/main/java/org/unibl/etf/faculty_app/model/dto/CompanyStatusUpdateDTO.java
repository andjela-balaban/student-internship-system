package org.unibl.etf.faculty_app.model.dto;

import java.io.Serializable;

public class CompanyStatusUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean isActive;
	
	public CompanyStatusUpdateDTO() {
		
	}

	public CompanyStatusUpdateDTO(Boolean isActive) {
		super();
		this.isActive = isActive;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
