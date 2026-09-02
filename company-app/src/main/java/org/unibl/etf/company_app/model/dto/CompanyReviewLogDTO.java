package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class CompanyReviewLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String companyComment;

    public CompanyReviewLogDTO() {
    }

    public CompanyReviewLogDTO(String companyComment) {
        this.companyComment = companyComment;
    }

    public String getCompanyComment() {
        return companyComment;
    }

    public void setCompanyComment(String companyComment) {
        this.companyComment = companyComment;
    }
}
