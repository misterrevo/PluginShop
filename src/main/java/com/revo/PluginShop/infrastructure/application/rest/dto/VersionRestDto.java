package com.revo.PluginShop.infrastructure.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class VersionRestDto {

    @Pattern(regexp = "\\d.\\d.\\d")
    private String version;
    @NotEmpty
    private String changelog;
}
