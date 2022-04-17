package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.PluginDto;

import java.util.List;
import java.util.Optional;

public interface PluginRepositoryPort {

    List<PluginDto> getAllFreePlugins();
    List<PluginDto> getAllPremiumPlugins();
    Optional<PluginDto> getPluginById(Long id);
    PluginDto savePlugin(PluginDto pluginDto);
    boolean existsFileByName(String name);
    boolean existsIconByName(String name);
    List<PluginDto> getAllContainsName(String name);
    long getNextIdOfPlugin();
}
