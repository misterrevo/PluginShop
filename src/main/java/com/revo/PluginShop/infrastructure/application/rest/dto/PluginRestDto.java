package com.revo.PluginShop.infrastructure.application.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PluginRestDto {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Pattern(regexp = "(FREE|PREMIUM)")
    private String type;
    @Min(1)
    private double price;
    @NotEmpty
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
