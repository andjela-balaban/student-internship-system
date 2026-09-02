package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class TechnologyResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer technologyId;
    private String name;

    public TechnologyResponseDTO() {
    }

    public TechnologyResponseDTO(Integer technologyId, String name) {
        this.technologyId = technologyId;
        this.name = name;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
