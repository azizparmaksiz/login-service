package com.eteshis.dto;

import javax.validation.constraints.NotNull;

public class RequestConfirmEmailDto {

    @NotNull
    private String email;
    @NotNull
    private String verificationCode;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
