package com.revo.PluginShop.infrastructure.application.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserCredentialsDto {

    @Email
    private String email;
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
