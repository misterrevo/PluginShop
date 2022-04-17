package com.revo.PluginShop.domain.dto;

public class VersionDto {

    private Long id;
    private Long pluginId;
    private String version;
    private String file;
    private String changelog;

    public VersionDto(Long id, Long pluginId, String version, String file, String changelog) {
        this.id = id;
        this.pluginId = pluginId;
        this.version = version;
        this.file = file;
        this.changelog = changelog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPluginId() {
        return pluginId;
    }

    public void setPluginId(Long pluginId) {
        this.pluginId = pluginId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public static final class Builder {
        private Long id;
        private Long pluginId;
        private String version;
        private String file;
        private String changelog;

        private Builder() {
        }

        public static Builder aVersionDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder pluginId(Long pluginId) {
            this.pluginId = pluginId;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder file(String file) {
            this.file = file;
            return this;
        }

        public Builder changelog(String changelog) {
            this.changelog = changelog;
            return this;
        }

        public VersionDto build() {
            return new VersionDto(id, pluginId, version, file, changelog);
        }
    }
}
