package org.unibl.etf.company_app.model.dto;

import java.io.Serializable;

public class ChangePasswordRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public ChangePasswordRequestDTO() {
    }

    public ChangePasswordRequestDTO(String oldPassword, String newPassword, String newPasswordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
