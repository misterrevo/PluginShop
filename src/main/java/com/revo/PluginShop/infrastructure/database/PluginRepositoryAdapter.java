package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.VersionDto;
import com.revo.PluginShop.domain.exception.VersionDoesNotExistsException;
import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.revo.PluginShop.infrastructure.database.Mapper.toDomain;

@RequiredArgsConstructor
@Component
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
        return toDomain(savedPlugin);
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

    @Override
    public void deletePluginById(Long id) {
        pluginRepository.deleteById(id);
    }

    @Override
    public void deleteVersionById(Long id) {
        versionRepository.deleteById(id);
    }

    @Override
    public VersionDto getVersionById(Long id) {
        var version = getVersion(id);
        return toDomain(version);
    }

    private VersionEntity getVersion(Long id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new VersionDoesNotExistsException(id));
    }
}
