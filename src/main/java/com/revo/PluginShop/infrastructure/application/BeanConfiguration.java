package com.revo.PluginShop.infrastructure.application;

import com.revo.PluginShop.domain.PaymentService;
import com.revo.PluginShop.domain.PluginService;
import com.revo.PluginShop.domain.UserService;
import com.revo.PluginShop.domain.port.EncoderPort;
import com.revo.PluginShop.domain.port.PaymentServicePort;
import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import com.revo.PluginShop.domain.port.PluginServicePort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import com.revo.PluginShop.domain.port.UserServicePort;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.revo.PluginShop.infrastructure")
@EntityScan("com.revo.PluginShop.infrastructure")
@EnableJpaRepositories("com.revo.PluginShop.infrastructure")
@PropertySource("classpath:auth.properties")
class BeanConfiguration {

    @Bean
    public UserServicePort userServicePort(UserRepositoryPort userRepositoryPort, EncoderPort encoderPort){
        return new UserService(userRepositoryPort, encoderPort);
    }

    @Bean
    public PluginServicePort pluginServicePort(PluginRepositoryPort pluginRepositoryPort, UserRepositoryPort userRepositoryPort){
        return new PluginService(pluginRepositoryPort, userRepositoryPort);
    }

    @Bean
    public PaymentServicePort paymentServicePort(PluginServicePort pluginServicePort){
        return new PaymentService(pluginServicePort);
    }
}
