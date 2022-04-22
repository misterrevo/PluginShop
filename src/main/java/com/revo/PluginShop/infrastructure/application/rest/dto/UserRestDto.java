package com.revo.PluginShop.infrastructure.application.rest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class UserRestDto {

    @Email
    private String email;
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String password;
}
