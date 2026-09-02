package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private Integer userId;
    private String role;
    private String username;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Integer userId, String role, String username) {
        this.token = token;
        this.userId = userId;
        this.role = role;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
