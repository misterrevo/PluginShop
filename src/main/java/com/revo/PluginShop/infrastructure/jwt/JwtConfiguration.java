package com.revo.PluginShop.infrastructure.jwt;

import com.revo.PluginShop.domain.port.JwtPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:auth.properties")
class JwtConfiguration {

    @Bean
    public JwtPort jwtPort(){
        return new JwtAdapter();
    }
}
