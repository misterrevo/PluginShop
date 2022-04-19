package com.revo.PluginShop.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LoginFilter loginFilter;
    private final AuthorizationFilter authorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        configureSecurity(http);
    }

    private void configureSecurity(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(loginFilter)
                .addFilter(authorizationFilter);
    }

}
