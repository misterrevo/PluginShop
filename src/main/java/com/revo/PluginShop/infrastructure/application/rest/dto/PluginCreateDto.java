package com.revo.PluginShop.infrastructure.application.rest.dto;

public class PluginCreateDto {

    private String name;
    private String description;
    private String type;
    private double price;
    private String videoUrl;

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
