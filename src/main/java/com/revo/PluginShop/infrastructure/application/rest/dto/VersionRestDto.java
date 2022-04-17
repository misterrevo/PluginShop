package com.revo.PluginShop.infrastructure.application.rest.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class VersionRestDto {

    @Min(1)
    private Long pluginId;
    @Pattern(regexp = "\\d.\\d.\\d")
    private String version;
    @NotEmpty
    private String changelog;

}
