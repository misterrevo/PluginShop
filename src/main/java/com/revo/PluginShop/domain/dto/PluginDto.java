package com.revo.PluginShop.domain.dto;

import java.util.List;

public class PluginDto {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String type;
    private double price;
    private List<VersionDto> versions;
    private String videoUrl;
    private String icon;

    public PluginDto(Long id, Long userId, String name, String description, String type, double price, List<VersionDto> versions, String videoUrl, String icon) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.versions = versions;
        this.videoUrl = videoUrl;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<VersionDto> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionDto> versions) {
        this.versions = versions;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String name;
        private String description;
        private String type;
        private double price;
        private List<VersionDto> versions;
        private String videoUrl;
        private String icon;

        private Builder() {
        }

        public static Builder aPluginDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder versions(List<VersionDto> versions) {
            this.versions = versions;
            return this;
        }

        public Builder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public PluginDto build() {
            return new PluginDto(id, userId, name, description, type, price, versions, videoUrl, icon);
        }
    }
}
