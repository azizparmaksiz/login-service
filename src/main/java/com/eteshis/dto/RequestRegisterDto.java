package com.eteshis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestRegisterDto {

    @NotNull
    @Pattern(regexp = "^((?![0-9\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\-\\[\\]\\{\\}\\;\\:\\\"\\\\/\\<\\>\\?\\.\\,\\ˇ\\^\\|\\°\\`\\˙\\˛\\˘\\´\\˝\\'\\¨\\¸]).)+$")
    private String name;


    @NotNull
    @Pattern(regexp = "^((?![0-9\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\-\\[\\]\\{\\}\\;\\:\\\"\\\\/\\<\\>\\?\\.\\,\\ˇ\\^\\|\\°\\`\\˙\\˛\\˘\\´\\˝\\'\\¨\\¸]).)+$")
    private String surname;

    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String username;

    @NotNull
//    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#?$ -+()&*^%{}./<>,!~]).{8,20})")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
