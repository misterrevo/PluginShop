package com.revo.PluginShop.domain;

import java.util.List;

class Plugin {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private PluginType type;
    private double price;
    private List<Version> versions;
    private Double minecraftVersion;
    private String videoUrl;
    private String icon;

    Plugin(Long id, Long userId, String name, String description, PluginType type, double price, List<Version> versions, double minecraftVersion, String videoUrl, String icon) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.versions = versions;
        this.minecraftVersion = minecraftVersion;
        this.videoUrl = videoUrl;
        this.icon = icon;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    PluginType getType() {
        return type;
    }

    void setType(PluginType type) {
        this.type = type;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Double getMinecraftVersion() {
        return minecraftVersion;
    }

    public void setMinecraftVersion(Double minecraftVersion) {
        this.minecraftVersion = minecraftVersion;
    }

    String getVideoUrl() {
        return videoUrl;
    }

    void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    String getIcon() {
        return icon;
    }

    void setIcon(String icon) {
        this.icon = icon;
    }

    Long getUserId() {
        return userId;
    }

    void setUserId(Long userId) {
        this.userId = userId;
    }

    static final class Builder {
        private Long id;
        private Long userId;
        private String name;
        private String description;
        private PluginType type;
        private double price;
        private List<Version> versions;
        private double minecraftVersion;
        private String videoUrl;
        private String icon;

        private Builder() {
        }

        static Builder aPlugin() {
            return new Builder();
        }

        Builder id(Long id) {
            this.id = id;
            return this;
        }

        Builder userId(Long userId){
            this.userId = userId;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder type(PluginType type) {
            this.type = type;
            return this;
        }

        Builder price(double price) {
            this.price = price;
            return this;
        }

        Builder versions(List<Version> versions) {
            this.versions = versions;
            return this;
        }

        Builder minecraftVersion(double minecraftVersion){
            this.minecraftVersion = minecraftVersion;
            return this;
        }

        Builder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        Plugin build() {
            return new Plugin(id, userId, name, description, type, price, versions, minecraftVersion, videoUrl, icon);
        }
    }
}
