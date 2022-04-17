package com.revo.PluginShop.infrastructure.encoder;

import com.revo.PluginShop.domain.port.EncoderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;

@Configuration
class EncoderConfiguration {

    @Bean
    public EncoderPort encoderPort(){
        return new BCryptPasswordEncoderAdapter();
    }

}
