package com.revo.PluginShop.domain.dto;

import java.util.List;

public class UserDto {

    private Long id;
    private String email;
    private String password;
    private boolean locked;
    private boolean enabled;
    private List<PluginDto> plugins;

    public UserDto(Long id, String email, String password, boolean locked, boolean enabled, List<PluginDto> plugins) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.locked = locked;
        this.enabled = enabled;
        this.plugins = plugins;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PluginDto> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<PluginDto> plugins) {
        this.plugins = plugins;
    }

    public static final class Builder {
        private Long id;
        private String email;
        private String password;
        private boolean locked;
        private boolean enabled;
        private List<PluginDto> plugins;

        private Builder() {
        }

        public static Builder anUserDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder plugins(List<PluginDto> plugins) {
            this.plugins = plugins;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, email, password, locked, enabled, plugins);
        }
    }
}
