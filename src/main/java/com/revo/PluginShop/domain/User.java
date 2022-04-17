package com.revo.PluginShop.domain;

import java.util.ArrayList;
import java.util.List;

class User {
    
    private Long id;
    private String email;
    private String password;
    private boolean locked;
    private boolean enabled;
    private List<Plugin> plugins;

    User(Long id, String email, String password, boolean locked, boolean enabled, List<Plugin> plugins) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.locked = locked;
        this.enabled = enabled;
        this.plugins = plugins;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    boolean isLocked() {
        return locked;
    }

    void setLocked(boolean locked) {
        this.locked = locked;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    List<Plugin> getPlugins() {
        return plugins;
    }

    void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    static final class Builder {
        private Long id;
        private String email;
        private String password;
        private boolean locked = false;
        private boolean enabled = true;
        private List<Plugin> plugins = new ArrayList<>();

        private Builder() {
        }

        static Builder anUser() {
            return new Builder();
        }

        Builder id(Long id) {
            this.id = id;
            return this;
        }

        Builder email(String email) {
            this.email = email;
            return this;
        }

        Builder password(String password) {
            this.password = password;
            return this;
        }

        Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        Builder plugins(List<Plugin> plugins) {
            this.plugins = plugins;
            return this;
        }

        User build() {
            return new User(id, email, password, locked, enabled, plugins);
        }
    }
}
