package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.VersionDto;

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
    void deletePluginById(Long id);
    void deleteVersionById(Long id);
    VersionDto getVersionById(Long id);
    VersionDto saveVersion(VersionDto version);
}
