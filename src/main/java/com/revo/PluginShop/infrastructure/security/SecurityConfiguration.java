package com.revo.PluginShop.infrastructure.security;

import com.revo.PluginShop.domain.port.EncoderPort;
import com.revo.PluginShop.domain.port.JwtPort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager, AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler){
        return new LoginFilter(authenticationManager, authenticationFailureHandler, authenticationSuccessHandler);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtPort jwtPort){
        return new LoginSuccessHandler(jwtPort);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(DetailsService detailsService, EncoderPort encoderPort){
        return new AuthManager(detailsService, encoderPort);
    }

    @Bean
    public DetailsService detailsService(UserRepositoryPort userRepositoryPort){
        return new DetailsService(userRepositoryPort);
    }

    @Bean
    public BasicAuthenticationFilter basicAuthenticationFilter(AuthenticationManager authenticationManager, DetailsService detailsService){
        return new AuthorizationFilter(authenticationManager, detailsService);
    }
}
