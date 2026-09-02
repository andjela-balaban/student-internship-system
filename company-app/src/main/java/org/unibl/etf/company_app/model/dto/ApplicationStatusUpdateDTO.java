package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class ApplicationStatusUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status; // PENDING, ACCEPTED, REJECTED

    public ApplicationStatusUpdateDTO() {
    }

    public ApplicationStatusUpdateDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
