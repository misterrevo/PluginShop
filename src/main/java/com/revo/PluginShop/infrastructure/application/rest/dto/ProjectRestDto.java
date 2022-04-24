package com.revo.PluginShop.infrastructure.application.rest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class ProjectRestDto {

    @NotEmpty
    private String phone;
    @Email
    private String email;
    @NotEmpty
    private String description;
}
