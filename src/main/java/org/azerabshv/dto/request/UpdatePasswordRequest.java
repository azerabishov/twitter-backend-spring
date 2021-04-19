package org.azerabshv.dto.request;

public class UpdatePasswordRequest {
    private String currentPass;
    private String newPassword;
    private String newPasswordConfirmation;

    public UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(String currentPass, String newPassword, String newPasswordConfirmation) {
        this.currentPass = currentPass;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
