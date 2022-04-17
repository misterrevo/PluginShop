package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class PluginRepositoryAdapter implements PluginRepositoryPort {

    private static final String FREE_TYPE = "FREE";
    private static final String PREMIUM_TYPE = "PREMIUM";

    private final PluginRepository pluginRepository;
    private final VersionRepository versionRepository;

    @Override
    public List<PluginDto> getAllFreePlugins() {
        var plugins = pluginRepository.findAllByType(FREE_TYPE);
        return mapPluginsFromEntity(plugins);
    }

    private List<PluginDto> mapPluginsFromEntity(List<PluginEntity> plugins) {
        return plugins.stream()
                .map(Mapper::toDomain)
                .toList();
    }

    @Override
    public List<PluginDto> getAllPremiumPlugins() {
        var plugins = pluginRepository.findAllByType(PREMIUM_TYPE);
        return mapPluginsFromEntity(plugins);
    }

    @Override
    public Optional<PluginDto> getPluginById(Long id) {
        var plugin = pluginRepository.findById(id);
        return plugin.map(Mapper::toDomain);
    }

    @Override
    public PluginDto savePlugin(PluginDto pluginDto) {
        var pluginEntity = Mapper.toEntity(pluginDto);
        var savedPlugin = pluginRepository.save(pluginEntity);
        return Mapper.toDomain(savedPlugin);
    }

    @Override
    public boolean existsFileByName(String name) {
        return versionRepository.existsByFile(name);
    }

    @Override
    public boolean existsIconByName(String name) {
        return pluginRepository.existsByIcon(name);
    }

    @Override
    public List<PluginDto> getAllContainsName(String name) {
        var plugins = pluginRepository.findAllByNameContaining(name);
        return mapPluginsFromEntity(plugins);
    }

    @Override
    public long getNextIdOfPlugin() {
        return pluginRepository.getNextIdOfPlugin();
    }
}
