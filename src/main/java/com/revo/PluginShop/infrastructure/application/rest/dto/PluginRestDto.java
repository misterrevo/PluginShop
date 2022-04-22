package com.revo.PluginShop.infrastructure.application.rest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class PluginRestDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Pattern(regexp = "(FREE|PREMIUM)")
    private String type;
    @Min(0)
    private double price;
    @NotEmpty
    private String videoUrl;
    @Min(1)
    private double minecraftVersion;
}
