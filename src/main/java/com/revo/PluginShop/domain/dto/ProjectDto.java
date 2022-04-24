package com.revo.PluginShop.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ProjectDto {

    private String phone;
    private String email;
    private String description;

    public ProjectDto(String phone, String email, String description) {
        this.phone = phone;
        this.email = email;
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public static final class Builder {
        private String phone;
        private String email;
        private String description;

        private Builder() {
        }

        public static Builder aProjectDto() {
            return new Builder();
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectDto build() {
            return new ProjectDto(phone, email, description);
        }
    }
}
