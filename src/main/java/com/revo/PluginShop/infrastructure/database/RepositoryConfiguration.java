package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RepositoryConfiguration {

    @Bean
    public PluginRepositoryPort pluginRepositoryPort(PluginRepository pluginRepository, VersionRepository versionRepository){
        return new PluginRepositoryAdapter(pluginRepository, versionRepository);
    }

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepository userRepository){
        return new UserRepositoryAdapter(userRepository);
    }

}
